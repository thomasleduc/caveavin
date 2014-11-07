package net.epita.caveavin.biz;

import lombok.Getter;
import net.epita.caveavin.dao.UserDAO;
import net.epita.caveavin.dbo.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The Business class of ser.
 * @author teboul_g
 */
@RequestScoped
@Named
public class UserBIZ extends AbstractBIZ<UserDAO, User, Long> {

    @Inject
    @Getter
    private UserDAO DAO;

}
