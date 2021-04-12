package de.ypsilon.quizzy.util;

import de.ypsilon.quizzy.dataset.user.SessionToken;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.exception.QuizzyWebException;
import de.ypsilon.quizzy.exception.QuizzyWebIllegalArgumentException;
import de.ypsilon.quizzy.exception.UserAuthenticationException;
import de.ypsilon.quizzy.web.routes.users.AuthenticateUserRoute;
import io.javalin.http.Context;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class RouteUtil {

    public static String SUCCESS_JSON = "{\"state\":\"ok\"}";
    public static String STATE_JSON = "{\"%s\":\"%s\"}";
    public static String ERROR_IN_REQUEST = "There is at least one error in your request!";

    /**
     * Checks whether all passed objects are not null
     *
     * @param objs the objects to check
     * @return true, if all objects are not null, false will never be returned.
     * @throws QuizzyWebIllegalArgumentException thrown, when at least one argument was null
     */
    public static boolean requireAllNotNull(Object... objs) throws QuizzyWebIllegalArgumentException {
        if (Arrays.stream(objs).allMatch(Objects::nonNull)) {
            return true;
        } else {
            throw new QuizzyWebIllegalArgumentException();
        }
    }

    /**
     * Makes a request failing
     *
     * @param context the context of the request
     */
    public static void failRequest(Context context) {
        JSONObject json = new JSONObject();
        json.put("state", "fail");
        json.put("cause", ERROR_IN_REQUEST);
        sendJsonMessage(context, json);
        context.status(400);
    }

    /**
     * Makes a request failing
     *
     * @param context the context of the request
     * @param cause   a (short) cause describing the issue
     */
    public static void failRequest(Context context, String cause) {
        context.html(getErrorJson(cause));
        context.contentType("application/json");
        context.status(400);
    }

    /**
     * Sends a success-json back to the client
     *
     * @param context the context of the request
     */
    public static void sendSuccessMessage(Context context) {
        sendJsonMessage(context, SUCCESS_JSON);
        context.status(200);
    }

    /**
     * Sends a json-encoded string as result of the request
     *
     * @param context    the context of the request
     * @param jsonString the json to be send
     */
    public static void sendJsonMessage(Context context, String jsonString) {
        context.html(jsonString);
        context.contentType("application/json");
    }

    public static void sendJsonMessage(Context context, JSONObject json) {
        sendJsonMessage(context, json.toString());
    }

    /**
     * Builds an error-json containing the error-cause.
     *
     * @param cause
     * @return
     */
    private static String getErrorJson(String cause) {
        return String.format("{\"state\":\"fail\",\"cause\":\"%s\"}", cause);
    }

    /**
     * Loads the logged in user based on its session-token
     *
     * @param context the context of the request to receive the session-token
     * @return the logged-in user
     * @throws QuizzyWebException thrown when no (valid) login exists.
     */
    public static User requireAuthenticatedUser(Context context) throws QuizzyWebException {
        String sessionToken = context.cookie(AuthenticateUserRoute.SESSION_TOKEN_COOKIE_NAME);
        if (sessionToken == null) {
            throw new UserAuthenticationException("No session-token found.");
        }
        User user = SessionToken.retrieveUserFromSessionTokenString(sessionToken);
        if (user == null) {
            throw new UserAuthenticationException("Invalid, expired or revoked session-token.");
        }
        return user;
    }

    /**
     * Retrieves the session-token from the request
     *
     * @param context the context of the request
     * @return the {@link SessionToken}
     * @throws UserAuthenticationException thrown, when no valid token was found.
     */
    public static SessionToken getSessionToken(Context context) throws UserAuthenticationException {
        String sessionToken = context.cookie(AuthenticateUserRoute.SESSION_TOKEN_COOKIE_NAME);
        if (sessionToken == null) {
            throw new UserAuthenticationException("No session-token found.");
        }
        return SessionToken.getSessionTokenByString(sessionToken);
    }

    /**
     * Tries to parse an integer from a string (basically just changing {@link NumberFormatException} to {@link QuizzyWebException}
     *
     * @param numberString the string containing the number
     * @return the number contained in the string
     * @throws QuizzyWebException thrown, when the string does not only contain the number.
     */
    public static int getInt(String numberString) throws QuizzyWebException {
        try {
            return Integer.parseInt(numberString);
        } catch (NumberFormatException e) {
            throw new QuizzyWebException("The provided number could not be parsed!");
        }
    }
}
