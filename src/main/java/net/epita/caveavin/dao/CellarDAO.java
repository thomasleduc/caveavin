package net.epita.caveavin.dao;

import net.epita.caveavin.dbo.Cellar;
import net.epita.caveavin.dbo.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * The DAO class of Cellar.
 * @author teboul_g
 */
@RequestScoped
@Named
public class CellarDAO extends AbstractDAO<Cellar, Long> {

    @Override
    public Class<Cellar> getEntityClass() {
        return Cellar.class;
    }
}
