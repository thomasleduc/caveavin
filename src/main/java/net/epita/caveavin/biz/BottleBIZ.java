package net.epita.caveavin.biz;

import lombok.Getter;
import net.epita.caveavin.dao.BottleDAO;
import net.epita.caveavin.dbo.Bottle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The Business class of Bottle.
 * @author teboul_g
 */
@RequestScoped
@Named
public class BottleBIZ extends AbstractBIZ<BottleDAO, Bottle, Long> {

    @Inject
    @Getter
    private BottleDAO DAO;

}
