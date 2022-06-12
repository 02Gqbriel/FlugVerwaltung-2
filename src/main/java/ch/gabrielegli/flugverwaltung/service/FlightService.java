package ch.gabrielegli.flugverwaltung.service;

import ch.gabrielegli.flugverwaltung.data.DataHandler;
import ch.gabrielegli.flugverwaltung.model.Flight;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

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
        List<Flight> fluege = DataHandler.getInstance().readAllFlights();
        int status = 200;

        if (sort != null) {
            if (sort == "flightUUID" || sort == "arrivaleTime" || sort == "departureTime") {
                fluege.sort((f1, f2) -> {
                    switch (sort) {
                        case "flightUUID":
                            return f1.getFlightUUID().compareTo(f2.getFlightUUID());
                        case "arrivaleTime":
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
        Flight flight = DataHandler.getInstance().readFlightByUUID(uuid);
        int status = 200;

        if (flight == null) status = 404;

        return Response
                .status(status)
                .entity(status == 404 ? new HashMap<String, String>() {{
                    put("MESSAGE", "Not Found");
                }} : flight)
                .build();
    }
}