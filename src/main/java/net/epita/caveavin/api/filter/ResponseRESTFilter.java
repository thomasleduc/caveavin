package net.epita.caveavin.api.filter;

import net.epita.caveavin.tools.CaveStrings;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by teboul_g
 */
@Provider
@PreMatching
public class ResponseRESTFilter implements ContainerResponseFilter {

    private final static Logger log = Logger.getLogger(ResponseRESTFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {
        log.info("Filtering REST Response");
        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        responseCtx.getHeaders().add("Access-Control-Allow-Headers", CaveStrings.SERVICE_KEY + ", " + CaveStrings.AUTH_TOKEN);
        responseCtx.getHeaders().putSingle("X-Powered-By", "EPITADMS.");
    }
}

