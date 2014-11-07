package net.epita.caveavin.api;

import net.epita.caveavin.biz.BottleBIZ;
import net.epita.caveavin.dbo.Bottle;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/bottle")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BottleRESTService extends AbstractRESTService {

    @Inject
    private BottleBIZ bottleBiz;

    @PUT
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Bottle add(Bottle entity) {
        Logger.getLogger(BottleRESTService.class.getSimpleName()).log(Level.INFO, null, "add bottle");
        System.out.println(entity.toString());
        return entity;
    }
}
