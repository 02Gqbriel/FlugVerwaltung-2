package ch.gabrielegli.flugverwaltung.service;

import ch.gabrielegli.flugverwaltung.data.DataHandler;
import ch.gabrielegli.flugverwaltung.model.Airport;

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
 * Service for the Airport-Class
 */
@Path("airport")
public class AirportService {
    /**
     * Handles incoming get request and returns a list in json format
     *
     * @param sort the sort parameter by which the should be sorted
     * @return the json response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAirports(@CookieParam("userRole") String userRole, @QueryParam("sort") String sort) {
        if (!isUserPermitted(userRole, new String[]{"admin", "user"})) return Response.status(401).build();

        List<Airport> airports = DataHandler.readAllAirport();
        int status = 200;

        if (sort != null) {
            if (sort.equals("airportUUID") || sort.equals("location")) {
                airports.sort((f1, f2) -> {
                    switch (sort) {
                        case "airportUUID":
                            return f1.getAirportUUID().compareTo(f2.getAirportUUID());
                        case "location":
                            return f1.getLocation().compareTo(f2.getLocation());
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
                }} : airports)
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
    public Response getOneAirport(
            @CookieParam("userRole") String userRole,
            @QueryParam("uuid")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}") String uuid
    ) {
        if (!isUserPermitted(userRole, new String[]{"admin", "user"})) return Response.status(401).build();

        Airport airport = DataHandler.readAirportByUUID(uuid);
        int status = 200;

        if (airport == null) status = 404;

        return Response
                .status(status)
                .entity(status == 404 ? new HashMap<String, String>() {{
                    put("MESSAGE", "Not Found");
                }} : airport)
                .build();
    }

    /**
     * deletes a airport identified by its uuid
     *
     * @param uuid the key
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteOneAirport(
            @CookieParam("userRole") String userRole,
            @QueryParam("uuid")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
                    String uuid
    ) {
        if (!isUserPermitted(userRole, new String[]{"admin"})) return Response.status(401).build();

        int status = 200;

        if (!DataHandler.deleteAirport(uuid)) {
            status = 404;
        }

        return Response.status(status).entity(status == 404 ? "Not Found" : "Object deleted").build();
    }

    /**
     * inserts a new airport
     *
     * @param airport airport
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addOneAirport(
            @CookieParam("userRole") String userRole,
            @Valid @BeanParam Airport airport
    ) {
        if (!isUserPermitted(userRole, new String[]{"admin"})) return Response.status(401).build();

        if (airport.getAirportUUID() == null) {
            airport.setAirportUUID(UUID.randomUUID().toString());
        }

        DataHandler.insertAirport(airport);

        return Response.status(200).entity("Object added").build();
    }

    /**
     * updates a new airport
     *
     * @param airport airport
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateOneAirport(
            @CookieParam("userRole") String userRole,
            @Valid @BeanParam Airport airport,
            @FormParam("airportUUID")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}") String uuid
    ) {
        if (!isUserPermitted(userRole, new String[]{"admin"})) return Response.status(401).build();

        int status = 200;
        Airport oldAirport = DataHandler.readAirportByUUID(uuid);

        if (oldAirport != null) {
            oldAirport.setLocation(airport.getLocation());
            DataHandler.updateAirport();
        } else {
            status = 404;
        }

        return Response
                .status(status)
                .entity(status == 404 ? "Not Found" : "Object updated")
                .build();
    }

    private boolean isUserPermitted(String userRole, String[] roles) {
        if (userRole == null || userRole.length() == 0) return false;

        for (int i = 0; i < roles.length; i++) {
            if (roles[i].equalsIgnoreCase(userRole)) return true;
        }

        return false;
    }
}