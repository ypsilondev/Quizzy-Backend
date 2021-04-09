package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.user.SessionToken;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.exception.UserAuthenticationException;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

public class AuthenticateUser implements Route {

    public static final String SESSION_TOKEN_COOKIE_NAME = "session_token";


    @Override
    public String getPath() {
        return "/users/authenticate";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.POST;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        String displayName = context.formParam("displayName");
        String email = context.formParam("email");
        String cleartextPassword = context.formParam("password");

        RouteUtil.requireAllNotNull(cleartextPassword);


        User user = null;
        if (displayName != null) {
            user = User.getUserByDisplayName(displayName);
        } else if (email != null) {
            user = User.getUserByEmail(email);
        }
        if (user == null) {
            throw new UserAuthenticationException("Invalid credentials!");
        }
        if (user.isValidPassword(cleartextPassword)) {
            context.html(String.format(STATE_JSON, "state", "login"));
            // TODO create user session etc...
            if (context.cookie(SESSION_TOKEN_COOKIE_NAME) == null) {
                context.cookie(SESSION_TOKEN_COOKIE_NAME, SessionToken.createAndSaveSessionToken(user).getTokenString(), 1717917367);
            }
        } else {
            throw new UserAuthenticationException("Invalid credentials!");
        }

    }
}
