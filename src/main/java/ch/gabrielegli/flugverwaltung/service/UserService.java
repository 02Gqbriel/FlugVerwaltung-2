package ch.gabrielegli.flugverwaltung.service;

import ch.gabrielegli.flugverwaltung.data.DataHandler;
import ch.gabrielegli.flugverwaltung.model.User;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("user")
public class UserService {

    @Path("login")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginUser(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        int status = 200;

        User user = DataHandler.readUser(username, password);

        if (user.getRole().equals("guest")) {
            status = 404;
        }

        NewCookie userRoleCookie = new NewCookie(
                "userRole",
                user.getRole(),
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );

        return Response.status(status).entity(user.getRole()).cookie(userRoleCookie).build();
    }

    @Path("logout")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response logoutUser(
    ) {
        NewCookie userRoleCookie = new NewCookie(
                "userRole",
                "guest",
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );

        return Response.status(200).entity("logout successful").cookie(userRoleCookie).build();
    }

    @PermitAll
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserList() {
        return Response.status(200).entity(DataHandler.getUserList()).build();
    }
}
