package ch.gabrielegli.flugverwaltung.service;

import ch.gabrielegli.flugverwaltung.data.DataHandler;
import ch.gabrielegli.flugverwaltung.model.Flight;

import javax.validation.Valid;
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
    public Response GetAllPlanes(@QueryParam("sort") String sort) {
        List<Flight> fluege = DataHandler.readAllFlights();
        int status = 200;

        if (sort != null) {
            if (sort.equals("flightUUID") || sort.equals("arrivalTime") || sort.equals("departureTime")) {
                fluege.sort((f1, f2) -> {
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
                status = 404;
            }
        }


        return Response
                .status(status)
                .entity(status == 404 ? new HashMap<String, String>() {{
                    put("MESSAGE", "Parameter Error: " + sort + " Not Valid Parameter");
                }} : fluege)
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
    public Response GetOneAirport(@QueryParam("uuid") String uuid) {
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
    public Response DeleteOneFlight(@QueryParam("uuid") String uuid) {
        int status = 200;

        if (!DataHandler.deleteFlight(uuid)) {
            status = 404;
        }

        return Response.status(status).entity(status == 404 ? "Not Found" : "Success").build();
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
    public Response AddOneFlight(
            @BeanParam @Valid Flight flight
    ) {
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
    public Response UpdateOneFlight(
            @BeanParam @Valid Flight flight
    ) {
        int status = 200;
        Flight flight2 = DataHandler.readFlightByUUID(flight.getFlightUUID());

        if (flight2 != null) {
            flight2.setArrivalTime(flight.getArrivalTime());
            flight2.setDepartureTime(flight.getDepartureTime());
            DataHandler.updateFlight();
        } else {
            status = 401;
        }

        return Response
                .status(status)
                .entity("")
                .build();
    }
}