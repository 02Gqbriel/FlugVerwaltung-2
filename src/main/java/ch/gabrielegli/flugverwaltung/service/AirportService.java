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
    public Response getAllAirports(@QueryParam("sort") String sort) {
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
            @QueryParam("uuid")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}") String uuid
    ) {
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
            @QueryParam("uuid")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
                    String uuid
    ) {
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
            @Valid @BeanParam Airport airport
    ) {

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
            @Valid @BeanParam Airport airport,
            @FormParam("airportUUID")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}") String uuid
    ) {
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
}