package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.user.SessionToken;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.exception.UserAuthenticationException;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.servlet.http.Cookie;

public class AuthenticateUserRoute implements Route {

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
        JSONObject json = new JSONObject(context.body());

        String displayName = null;
        String email = null;

        if (!json.isNull("displayName")) {
            displayName = json.getString("displayName");
        }

        if (!json.isNull("email")) {
            email = json.getString("email");
        }

        String cleartextPassword = json.getString("password");

        User user = null;
        if (displayName != null) {
            user = User.getUserByDisplayName(displayName);
        } else if (email != null) {
            user = User.getUserByEmail(email);
        }
        if (user != null && user.isValidPassword(cleartextPassword)) {
            RouteUtil.sendJsonMessage(context, String.format(RouteUtil.STATE_JSON, "state", "login"));
            setSessionToken(context, user);
        } else {
            throw new UserAuthenticationException("Invalid credentials!");
        }

    }

    static void setSessionToken(Context context, User user) {
        Cookie cookie = new Cookie(SESSION_TOKEN_COOKIE_NAME, SessionToken.createAndSaveSessionToken(user).getTokenString());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1717917367);
        context.cookie(cookie);
    }
}
