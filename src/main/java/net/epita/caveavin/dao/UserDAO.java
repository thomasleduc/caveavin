package net.epita.caveavin.dao;

import net.epita.caveavin.dbo.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * The DAO class of User.
 * @author teboul_g
 */
@RequestScoped
@Named
public class UserDAO extends AbstractDAO<User, Long> {
    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
