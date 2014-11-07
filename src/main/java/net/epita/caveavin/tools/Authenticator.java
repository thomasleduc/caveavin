package net.epita.caveavin.tools;

import net.epita.caveavin.biz.UserBIZ;
import net.epita.caveavin.tools.exception.RegisterFailedException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.security.auth.login.LoginException;

/**
 * Created by teboul_g
 */
@Singleton
public final class Authenticator {
    private static Authenticator authenticator = null;
    // An authentication token storage which stores <auth_token, username>.
    private final Map<String, String> authorizationTokensStorage = new HashMap<>();

    @Inject
    private UserBIZ userBIZ;

    @PostConstruct
    private void init() {

    }

    public String login(String username, String password) throws LoginException {

        if (userBIZ.login(username, password)) {
                String authToken = UUID.randomUUID().toString();
                authorizationTokensStorage.put(authToken, username);
                return authToken;
        }
        throw new LoginException("Sorry man, you don't exist");
    }

    /**
     * Check the parameter to registration, save the user and do a login
     **/
    public String register(String username, String password, String email) throws RegisterFailedException {
        Logger.getLogger(Authenticator.class.getName()).log(Level.INFO, (userBIZ == null) ? "too bad" : "correctly injected");
        userBIZ.register(username, password, email); // possible throw of registerFailedException

        String authToken = UUID.randomUUID().toString();
        authorizationTokensStorage.put(authToken, username);
        return authToken;
    }

    /**
     * The method that pre-validates if the client which invokes the REST API is
     * from an authorized and authenticated source.
     *
     * @param authToken The authorization token generated after login
     * @return TRUE for acceptance and FALSE for denied.
     */
    public boolean isAuthTokenValid(String authToken) {
        return authorizationTokensStorage.containsKey(authToken);
    }

    /**
     * Invalidate the token
     * @param authToken
     * @throws GeneralSecurityException
     */
    public void logout(String authToken) throws GeneralSecurityException {
        if (isAuthTokenValid(authToken)) {
            throw new GeneralSecurityException("Invalid service key and authorization token match.");
        }
        authorizationTokensStorage.remove(authToken);
    }
}

