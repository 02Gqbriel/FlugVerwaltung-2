package ch.gabrielegli.flugverwaltung.service;

import ch.gabrielegli.flugverwaltung.data.DataHandler;
import ch.gabrielegli.flugverwaltung.model.Airport;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

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
    public Response GetAllAirports(@QueryParam("sort") String sort) {
        List<Airport> flugahafens = DataHandler.getInstance().readAllAirport();
        int status = 200;

        if (sort != null) {
            if (sort == "airportUUID" && sort == "location") {
                flugahafens.sort((f1, f2) -> {
                    switch (sort) {
                        case "flughafenUUID":
                            return f1.getAirportUUID().compareTo(f2.getAirportUUID());
                        case "ort":
                            return f1.getLocation().compareTo(f2.getLocation());
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
                }} : flugahafens)
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
        Airport airport = DataHandler.getInstance().readAirportByUUID(uuid);
        int status = 200;

        if (airport == null) status = 404;

        return Response
                .status(status)
                .entity(status == 404 ? new HashMap<String, String>() {{
                    put("MESSAGE", "Not Found");
                }} : airport)
                .build();
    }
}