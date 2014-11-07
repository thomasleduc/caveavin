package net.epita.caveavin.api;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

/**
 * Created by leduc_t
 */
public class AbstractRESTService {

    protected Response.ResponseBuilder getNoCacheResponseBuilder(Response.Status status) {
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setMaxAge(-1);
        cc.setMustRevalidate(true);
        return Response.status(status).cacheControl(cc);
    }

}
