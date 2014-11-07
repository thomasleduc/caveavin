package net.epita.caveavin.api;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by leduc_t
 */
public class AbstractRESTService {

    public Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }

    protected Response.ResponseBuilder getNoCacheResponseBuilder(Response.Status status) {
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setMaxAge(-1);
        cc.setMustRevalidate(true);
        return Response.status(status).cacheControl(cc);
    }
}
