package net.epita.caveavin.biz;

import net.epita.caveavin.dao.UserDAO;
import net.epita.caveavin.dbo.Cellar;
import net.epita.caveavin.dbo.User;
import net.epita.caveavin.tools.CaveStrings;
import net.epita.caveavin.tools.exception.RegisterFailedException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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

        // if no unique constrains are broken try to add the user
        User newUser = new User(null, username, encryptPassword(password), email, new ArrayList<Cellar>());

        try {
            persist(newUser);
        } catch (Exception sqlEx) {
            throw new RegisterFailedException(CaveStrings.REGISTER_FAILED_SQL);
        }

        return newUser;
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

        password = password.concat(CaveStrings.SALT_PASSWORD);

        try {
            MessageDigest hash = MessageDigest.getInstance("SHA1");

            ByteArrayInputStream bIn = new ByteArrayInputStream(password.getBytes());
            DigestInputStream dIn = new DigestInputStream(bIn, hash);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();

            int ch;
            while ((ch = dIn.read()) >= 0) {
                bOut.write(ch);
            }
            byte[] newInput = bOut.toByteArray();

            DigestOutputStream dOut = new DigestOutputStream(new ByteArrayOutputStream(), hash);
            dOut.write(newInput);
            dOut.close();

            return new String(dOut.getMessageDigest().digest());
        } catch (NoSuchAlgorithmException|IOException nsae) {
            // this is not gonna happen ...
            return "";
        }
    }
}
