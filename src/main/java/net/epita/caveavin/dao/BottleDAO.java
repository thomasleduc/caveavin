package net.epita.caveavin.dao;

import net.epita.caveavin.dbo.Bottle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * The DAO class of Bottle.
 * @author teboul_g
 */
@RequestScoped
@Named
public class BottleDAO extends AbstractDAO<Bottle, Long> {

    @Override
    public Class<Bottle> getEntityClass() {
        return Bottle.class;
    }

}
