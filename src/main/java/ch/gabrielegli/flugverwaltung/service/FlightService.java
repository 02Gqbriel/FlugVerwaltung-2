package ch.gabrielegli.flugverwaltung.service;

import ch.gabrielegli.flugverwaltung.data.DataHandler;
import ch.gabrielegli.flugverwaltung.model.Flight;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Service for the Flight-Class
 */
@Path("flight")
public class FlightService {

    /**
     * Handles incoming get request and returns a list in json format
     *
     * @param sort the sort parameter by which the should be sorted
     * @return the json response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFlights(@CookieParam("userRole") String userRole, @QueryParam("sort") String sort) {
        if (!isUserPermitted(userRole, new String[]{"admin", "user"})) return Response.status(401).build();

        List<Flight> flights = DataHandler.readAllFlights();
        int status = 200;

        if (sort != null) {
            if (sort.equals("flightUUID") || sort.equals("arrivalTime") || sort.equals("departureTime")) {
                flights.sort((f1, f2) -> {
                    switch (sort) {
                        case "flightUUID":
                            return f1.getFlightUUID().compareTo(f2.getFlightUUID());
                        case "arrivalTime":
                            return f1.getArrivalTime().compareTo(f2.getArrivalTime());
                        case "departureTime":
                            return f1.getDepartureTime().compareTo(f2.getDepartureTime());
                        default:
                            return 0;
                    }
                });
            } else {
                status = 400;
            }
        }


        return Response
                .status(status)
                .entity(status == 400 ? new HashMap<String, String>() {{
                    put("MESSAGE", "Parameter Error: " + sort + " Not Valid Parameter");
                }} : flights)
                .build();
    }

    /**
     * handles request with query param and returns single json object
     *
     * @param uuid the query uuid which is requested
     * @return the json response
     */
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneFlight(
            @CookieParam("userRole") String userRole,
            @QueryParam("uuid")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}") String uuid
    ) {
        if (!isUserPermitted(userRole, new String[]{"admin", "user"})) return Response.status(401).build();

        Flight flight = DataHandler.readFlightByUUID(uuid);
        int status = 200;

        if (flight == null) status = 404;

        return Response
                .status(status)
                .entity(status == 404 ? new HashMap<String, String>() {{
                    put("MESSAGE", "Not Found");
                }} : flight)
                .build();
    }

    /**
     * deletes a flight identified by its uuid
     *
     * @param uuid the key
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteOneFlight(
            @CookieParam("userRole") String userRole,
            @QueryParam("uuid")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}") String uuid
    ) {
        if (!isUserPermitted(userRole, new String[]{"admin"})) return Response.status(401).build();

        int status = 200;

        if (!DataHandler.deleteFlight(uuid)) {
            status = 404;
        }

        return Response.status(status).entity(status == 404 ? "Not Found" : "Object deleted").build();
    }

    /**
     * inserts a new flight
     *
     * @param flight flight
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addOneFlight(
            @CookieParam("userRole") String userRole,
            @BeanParam @Valid Flight flight
    ) {
        if (!isUserPermitted(userRole, new String[]{"admin"})) return Response.status(401).build();

        if (flight.getFlightUUID() == null) {
            flight.setFlightUUID(UUID.randomUUID().toString());
        }

        DataHandler.insertFlight(flight);

        return Response.status(200).entity("Object added").build();
    }

    /**
     * updates a new flight
     *
     * @param flight flight
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateOneFlight(
            @CookieParam("userRole") String userRole,
            @BeanParam @Valid Flight flight,
            @FormParam("flightUUID")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}") String uuid
    ) {
        if (!isUserPermitted(userRole, new String[]{"admin"})) return Response.status(401).build();

        int status = 200;
        Flight flight2 = DataHandler.readFlightByUUID(uuid);

        if (flight2 != null) {
            flight2.setArrivalTime(flight.getArrivalTime());
            flight2.setDepartureTime(flight.getDepartureTime());
            DataHandler.updateFlight();
        } else {
            status = 404;
        }

        return Response
                .status(status)
                .entity(status == 404 ? "Not Found" : "Object updated")
                .build();
    }

    private boolean isUserPermitted(String userRole, String[] roles) {
        if (userRole == null || userRole.equals(" ") || userRole.equals("")) return false;

        for (int i = 0; i < roles.length; i++) {
            if (roles[i].equalsIgnoreCase(userRole)) return true;
        }

        return false;
    }
}