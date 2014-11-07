package net.epita.caveavin.api;

import net.epita.caveavin.tools.Authenticator;
import net.epita.caveavin.tools.CaveStrings;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.GeneralSecurityException;

/**
 * Created by teboul_g
 */
@Path("/session")
public class SessionRESTService extends AbstractRESTService {

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @Context HttpHeaders httpHeaders,
            @FormParam("username") String username,
            @FormParam("password") String password) {

        Authenticator authenticator = Authenticator.getInstance();

        try {
            String authToken = authenticator.login(username, password);
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add(CaveStrings.AUTH_TOKEN, authToken);
            JsonObject jsonObj = jsonObjBuilder.build();
            return getNoCacheResponseBuilder(Response.Status.OK).entity(jsonObj.toString()).build();
        } catch (final LoginException ex) {
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add("message", "Problem matching service key, username and password");
            JsonObject jsonObj = jsonObjBuilder.build();
            return getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
        }
    }

    @GET
    @Path("test-get-method")
    @Produces(MediaType.APPLICATION_JSON)
    public Response demoGetMethod() {
        JsonObject jsonObj = Json.createObjectBuilder()
                .add("message", "Executed demoGetMethod")
                .build();

        return getNoCacheResponseBuilder(Response.Status.OK).entity(jsonObj.toString()).build();
    }

    @POST
    @Path("test-post-method")
    @Produces(MediaType.APPLICATION_JSON)
    public Response demoPostMethod() {
        JsonObject jsonObj = Json.createObjectBuilder()
                .add("message", "Executed demoPostMethod")
                .build();
        return getNoCacheResponseBuilder(Response.Status.ACCEPTED).entity(jsonObj.toString()).build();
    }

    @POST
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(
            @Context HttpHeaders httpHeaders) {
        try {
            Authenticator authenticator = Authenticator.getInstance();
            String authToken = httpHeaders.getHeaderString(CaveStrings.AUTH_TOKEN);
            authenticator.logout(authToken);

            return getNoCacheResponseBuilder(Response.Status.NO_CONTENT).build();
        } catch (final GeneralSecurityException ex) {
            return getNoCacheResponseBuilder(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

