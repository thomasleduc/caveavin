package net.epita.caveavin.api;

import lombok.Getter;
import net.epita.caveavin.biz.BottleBIZ;
import net.epita.caveavin.biz.CellarBIZ;
import net.epita.caveavin.biz.UserBIZ;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/public")
public class PublicRESTService extends AbstractRESTService{

    @Inject
    @Getter
    private BottleBIZ bottleBIZ;
    @Inject
    @Getter
    private CellarBIZ cellarBIZ;
    @Inject
    @Getter
    private UserBIZ userBIZ;

    @GET
    @Path("stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response statistic() {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();

        jsonObjBuilder.add("nb_user_created", userBIZ.count());
        jsonObjBuilder.add("nb_cellar_created", cellarBIZ.count());
        jsonObjBuilder.add("nb_bottle_created", bottleBIZ.count());

        JsonObject jsonObj = jsonObjBuilder.build();

        return getNoCacheResponseBuilder(Response.Status.OK).entity(jsonObj.toString()).build();
    }
}
