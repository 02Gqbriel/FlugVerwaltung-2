package ch.gabrielegli.flugverwaltung.service;

import ch.gabrielegli.flugverwaltung.data.DataHandler;
import ch.gabrielegli.flugverwaltung.model.Airplane;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

/**
 * Service for the Airplane-Class
 */
@Path("airplane")
public class AirplaneService {
    private static int status = 200;

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
        List<Airplane> airplanes = DataHandler.getInstance().readAllAirplanes();
        status = 200;

        if (sort != null) {
            if (sort == "airplaneUUID" || sort == "airline" || sort == "modelName" || sort == "flightNumber") {
                airplanes.sort((f1, f2) -> {
                    switch (sort) {
                        case "airplaneUUID":
                            return f1.getAirplaneUUID().compareTo(f2.getAirplaneUUID());
                        case "airline":
                            return f1.getAirline().compareTo(f2.getAirline());
                        case "modelName":
                            return f1.getModelName().compareTo(f2.getModelName());
                        case "flightNumber":
                            return f1.getFlightNumber().compareTo(f2.getFlightNumber());
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
                }} : airplanes)
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
    public Response GetOnePlane(@QueryParam("uuid") String uuid) {
        Airplane airplane = DataHandler.getInstance().readAirplaneByUUID(uuid);
        status = 200;

        if (airplane == null) status = 404;

        return Response
                .status(status)
                .entity(status == 404 ? new HashMap<String, String>() {{
                    put("MESSAGE", "Not Found");
                }} : airplane)
                .build();
    }
}