package net.epita.caveavin.biz;

import net.epita.caveavin.dao.UserDAO;
import net.epita.caveavin.dbo.User;
import net.epita.caveavin.tools.CaveStrings;
import net.epita.caveavin.tools.OWASP;
import net.epita.caveavin.tools.exception.RegisterFailedException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Business class of ser.
 * @author teboul_g
 */
@RequestScoped
@Named
public class UserBIZ extends AbstractBIZ<UserDAO, User, Long> {

    @Inject
    private UserDAO userDAO;

    @Override
    public UserDAO getDAO() {
        return userDAO;
    }

    public User findWithName(String username) {
        return getDAO().findUnique(CaveStrings.SQL_USER_USERNAME, username);
    }

    public User findWithEmail(String email) {
        return getDAO().findUnique(CaveStrings.SQL_USER_EMAIL, email);
    }

    public Boolean login(String username, String password) {
        User user = findWithName(username);

        // if no user found
        if (user == null) return false;

        return passwordMatchUser(user, password);
    }

    /**
     * Create and save a new User in database with checking of the unique constrains.
     * @param username The new username
     * @param password The new no encrypt password
     * @param email The new email
     * @return The user created
     * @throws RegisterFailedException throw the exception if a unique constrains is broken
     */
    public User register(String username, String password, String email) throws RegisterFailedException {
        if (findWithEmail(email) != null) {
            throw new RegisterFailedException(CaveStrings.REGISTER_FAILED_EMAIL);
        }

        if (findWithName(username) != null) {
            throw new RegisterFailedException(CaveStrings.REGISTER_FAILED_USERNAME);
        }

        // If no unique constrains are broken try to add the user
        User newUser = new User(null, username, encryptPassword(password), email, null, new ArrayList<>());

        try {
            persist(newUser);
            return newUser;
        } catch (Exception sqlEx) {
            Logger.getLogger(UserBIZ.class.getName()).log(Level.SEVERE, sqlEx.getMessage());
            throw new RegisterFailedException(CaveStrings.REGISTER_FAILED_SQL);
        }
    }

    /**
     * @param user The user to test
     * @param password The password to test
     * @return if the no encrypted password match the hash in database.
     */
    private Boolean passwordMatchUser(User user, String password) {
        return user.getPassword().equals(encryptPassword(password));
    }

    /**
     * One way encryption with salt
     * @param password The string to encrypt
     * @return The encrypted string
     */
    private String encryptPassword(String password) {

        try {
            return OWASP.byteToBase64(OWASP.getHash(password, CaveStrings.SALT_PASSWORD.getBytes()));
        } catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
            // Never happen
            return "";
        }
    }
}
