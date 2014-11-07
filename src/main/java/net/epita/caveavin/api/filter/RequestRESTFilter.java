package net.epita.caveavin.api.filter;

import net.epita.caveavin.tools.Authenticator;
import net.epita.caveavin.tools.CaveStrings;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by teboul_g
 */
@Provider
@PreMatching
public class RequestRESTFilter implements ContainerRequestFilter {

    @Inject
    Authenticator authenticator;

    private final static Logger log = Logger.getLogger(RequestRESTFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestCtx) throws IOException {
        String path = requestCtx.getUriInfo().getPath();
        log.info("Filtering request path: " + path);

        /**
         * Sometimes, browsers ping the webservice before make the real request.
         * So let's answer OK to these little boys
         * (For more detail see on the pre-flight of the browsers :
         * http://www.chromium.org/developers/tree-sheriffs/sheriff-details-chromium-os/pre-flight-queue-faq)
         */
        if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
            requestCtx.abortWith(Response.status(Response.Status.OK).build());
            return;
        }

        // For any other methods besides login, the authToken must be verified
        if (!path.startsWith("/session")) {
            String authToken = requestCtx.getHeaderString(CaveStrings.AUTH_TOKEN);
            // if it isn't valid, just kick them out.
            if (!authenticator.isAuthTokenValid(authToken)) {
                requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }
}

