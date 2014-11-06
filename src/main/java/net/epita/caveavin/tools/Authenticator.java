package net.epita.caveavin.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.security.GeneralSecurityException;
import javax.security.auth.login.LoginException;

/**
 * Created by teboul_g
 */
public final class Authenticator {
    private static Authenticator authenticator = null;
    // A user storage which stores <username, password>
    private final Map<String, String> usersStorage = new HashMap<>();
    // An authentication token storage which stores <auth_token, username>.
    private final Map<String, String> authorizationTokensStorage = new HashMap<>();

    private Authenticator() {
        // The usersStorage pretty much represents a user table in the database
        usersStorage.put("username1", "passwordForUser1");
        usersStorage.put("username2", "passwordForUser2");
        usersStorage.put("username3", "passwordForUser3");
    }

    public static Authenticator getInstance() {
        if (authenticator == null) {
            authenticator = new Authenticator();
        }
        return authenticator;
    }

    public String login(String username, String password) throws LoginException {
        if (usersStorage.containsKey(username)) {
            String passwordMatch = usersStorage.get(username);
            if (passwordMatch.equals(password)) {
                /**
                 * Once all params are matched, the authToken will be
                 * generated and will be stored in the
                 * authorizationTokensStorage. The authToken will be needed
                 * for every REST API invocation and is only valid within
                 * the login session
                 **/
                String authToken = UUID.randomUUID().toString();
                authorizationTokensStorage.put(authToken, username);
                return authToken;
            }
        }
        throw new LoginException("Sorry man, you don't exist");
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

