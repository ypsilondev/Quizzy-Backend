package de.ypsilon.quizzy.util;

import de.ypsilon.quizzy.dataset.user.SessionToken;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.exception.QuizzyExcepton;
import de.ypsilon.quizzy.exception.QuizzyWebException;
import de.ypsilon.quizzy.exception.QuizzyWebIllegalArgumentException;
import de.ypsilon.quizzy.exception.UserAuthenticationException;
import de.ypsilon.quizzy.web.Route;
import de.ypsilon.quizzy.web.routes.users.AuthenticateUser;
import io.javalin.http.Context;

import java.util.Arrays;
import java.util.Objects;

public class RouteUtil {

    public static boolean requireAllNotNull(Object... objs) throws QuizzyWebIllegalArgumentException {
        Arrays.stream(objs).forEach(System.out::println);
        if (Arrays.stream(objs).allMatch(Objects::nonNull)) {
            return true;
        } else {
            throw new QuizzyWebIllegalArgumentException();
        }
    }

    public static void failRequest(Context context) {
        context.html(Route.ERROR_IN_REQUEST);
        context.status(400);
    }

    public static void failRequest(Context context, String cause) {
        context.html(getErrorJson(cause));
        context.status(400);
    }

    private static String getErrorJson(String cause) {
        return String.format("{\"state\":\"fail\",\"cause\":\"%s\"}", cause);
    }

    public static User requireAuthenticatedUser(Context context) throws QuizzyWebException {
        String sessionToken = context.cookie(AuthenticateUser.SESSION_TOKEN_COOKIE_NAME);
        if (sessionToken == null) {
            throw new UserAuthenticationException("No session-token found.");
        }
        User user = SessionToken.retrieveUserFromSessionTokenString(sessionToken);
        if (user == null) {
            throw new UserAuthenticationException("Invalid, expired or revoked session-token.");
        }
        return user;
    }

    public static SessionToken getSessionToken(Context context) throws UserAuthenticationException {
        String sessionToken = context.cookie(AuthenticateUser.SESSION_TOKEN_COOKIE_NAME);
        if (sessionToken == null) {
            throw new UserAuthenticationException("No session-token found.");
        }
        return SessionToken.getSessionTokenByString(sessionToken);
    }

    public static int getInt(String numberString) throws QuizzyWebException {
        try {
            return Integer.parseInt(numberString);
        } catch (NumberFormatException e) {
            throw new QuizzyWebException("The provided number could not be parsed!");
        }
    }
}
