package net.epita.caveavin.tools;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.Produces;
import java.util.logging.Logger;

/**
 * Created by leduc_t
 */
public class LoggerProducer {

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
