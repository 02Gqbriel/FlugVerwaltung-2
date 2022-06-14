package ch.gabrielegli.flugverwaltung.service;

import ch.gabrielegli.flugverwaltung.data.DataHandler;
import ch.gabrielegli.flugverwaltung.model.Airplane;

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
 * Service for the Airplane-Class
 */
@Path("airplane")
public class AirplaneService {

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
        List<Airplane> airplanes = DataHandler.readAllAirplanes();
        int status = 200;

        if (sort != null) {
            if (sort.equals("airplaneUUID") || sort.equals("airline") || sort.equals("modelName") || sort.equals("flightNumber")) {
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
                status = 400;
            }
        }

        return Response
                .status(status)
                .entity(status == 400 ? new HashMap<String, String>() {{
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
        Airplane airplane = DataHandler.readAirplaneByUUID(uuid);
        int status = 200;

        if (airplane == null) status = 404;

        return Response
                .status(status)
                .entity(status == 404 ? new HashMap<String, String>() {{
                    put("MESSAGE", "Not Found");
                }} : airplane)
                .build();
    }

    /**
     * deletes a airplane identified by its uuid
     *
     * @param uuid the key
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response DeleteOneAirplane(@QueryParam("uuid")
                                      @NotEmpty
                                      @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
                                              String uuid) {
        int status = 200;

        if (!DataHandler.deleteAirplane(uuid)) {
            status = 404;
        }

        return Response.status(status).entity(status == 404 ? "Not Found" : "Object deleted").build();
    }

    /**
     * inserts a new airplane
     *
     * @param airplane airplane
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response AddOneAirplane(
            @Valid @BeanParam Airplane airplane
    ) {

        if (airplane.getAirplaneUUID() == null) {
            airplane.setAirplaneUUID(UUID.randomUUID().toString());
        }

        DataHandler.insertAirplane(airplane);

        return Response.status(200).entity("Object added").build();
    }

    /**
     * updates a new airplane
     *
     * @param airplane airplane
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response UpdateOneAirplane(
            @Valid @BeanParam Airplane airplane
    ) {
        int status = 200;
        Airplane old = DataHandler.readAirplaneByUUID(airplane.getAirplaneUUID());

        if (old != null) {
            old.setAirline(airplane.getAirline());
            old.setFlightNumber(airplane.getFlightNumber());
            old.setModelName(airplane.getModelName());
            DataHandler.updateAirplane();
        } else {
            status = 404;
        }

        return Response
                .status(status)
                .entity(status == 404 ? "Not Found" : "Object updated")
                .build();
    }
}