package ch.gabrielegli.flugverwaltung.service;

import ch.gabrielegli.flugverwaltung.data.DataHandler;
import ch.gabrielegli.flugverwaltung.model.User;

import javax.annotation.security.PermitAll;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("user")
public class UserService {

    @PermitAll
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

        return Response.status(status).entity(status == 404 ? "login failed" : "login successful").cookie(userRoleCookie).build();
    }

    @PermitAll
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
}
