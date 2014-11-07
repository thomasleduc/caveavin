package net.epita.caveavin.dao;

import net.epita.caveavin.dbo.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

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

    @Override
    public Long count() {
        return super.count() - 1; // Ignore the admin user
    }
}
