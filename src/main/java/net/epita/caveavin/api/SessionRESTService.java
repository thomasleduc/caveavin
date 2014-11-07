package net.epita.caveavin.api;

import net.epita.caveavin.tools.Authenticator;
import net.epita.caveavin.tools.CaveStrings;
import net.epita.caveavin.tools.exception.RegisterFailedException;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.GeneralSecurityException;

@Path("/session")
public class SessionRESTService extends AbstractRESTService {

    @Inject
    Authenticator authenticator;

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @Context HttpHeaders httpHeaders,
            @FormParam("username") String username,
            @FormParam("password") String password) {

        try {
            String authToken = authenticator.login(username, password);
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add(CaveStrings.AUTH_TOKEN, authToken);
            JsonObject jsonObj = jsonObjBuilder.build();
            return getNoCacheResponseBuilder(Response.Status.OK).entity(jsonObj.toString()).build();
        } catch (final LoginException ex) {
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add("message", "Bad username and/or password format");
            JsonObject jsonObj = jsonObjBuilder.build();
            return getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
        }
    }

    @GET
    @Path("test-get-method")
    @Produces(MediaType.APPLICATION_JSON)
    public Response demoGetMethod() {
        JsonObject jsonObj = Json.createObjectBuilder()
                .add("message", "Executed testGetMethod")
                .build();

        return getNoCacheResponseBuilder(Response.Status.OK).entity(jsonObj.toString()).build();
    }

    @POST
    @Path("test-post-method")
    @Produces(MediaType.APPLICATION_JSON)
    public Response demoPostMethod() {
        JsonObject jsonObj = Json.createObjectBuilder()
                .add("message", "Executed testPostMethod")
                .build();
        return getNoCacheResponseBuilder(Response.Status.ACCEPTED).entity(jsonObj.toString()).build();
    }

    @POST
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(
            @Context HttpHeaders httpHeaders) {
        try {
            String authToken = httpHeaders.getHeaderString(CaveStrings.AUTH_TOKEN);
            authenticator.logout(authToken);

            return getNoCacheResponseBuilder(Response.Status.NO_CONTENT).build();
        } catch (final GeneralSecurityException ex) {
            return getNoCacheResponseBuilder(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(
            @Context HttpHeaders httpHeaders,
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("email") String email) {

        try {
            String authToken = authenticator.register(username, password, email);
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add(CaveStrings.AUTH_TOKEN, authToken);
            JsonObject jsonObj = jsonObjBuilder.build();
            return getNoCacheResponseBuilder(Response.Status.OK).entity(jsonObj.toString()).build();
        } catch (RegisterFailedException e) {
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add("message", e.getMessage());
            JsonObject jsonObj = jsonObjBuilder.build();
            return getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
        }
    }
}

