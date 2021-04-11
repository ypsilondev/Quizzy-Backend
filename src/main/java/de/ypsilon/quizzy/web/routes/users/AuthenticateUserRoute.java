package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.user.SessionToken;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.exception.UserAuthenticationException;
import de.ypsilon.quizzy.json.JsonCodecManager;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

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

        String loginName = json.getString("loginName");
        String cleartextPassword = json.getString("password");

        User user = User.getUserByDisplayName(loginName);
        if (user == null) {
            user = User.getUserByEmail(loginName);
        }
        if (user != null && user.isValidPassword(cleartextPassword)) {
            JSONObject responseJson = new JSONObject();
            responseJson.put("state", "login");
            responseJson.put("user", JsonCodecManager.getInstance().getEncoder(User.class).encode(user));
            RouteUtil.sendJsonMessage(context, responseJson.toString());
            setSessionToken(context, user);
        } else {
            throw new UserAuthenticationException("Invalid credentials!");
        }

    }

    static void setSessionToken(Context context, User user) {
        context.cookie(SESSION_TOKEN_COOKIE_NAME, SessionToken.createAndSaveSessionToken(user).getTokenString(), 1717917367);
    }
}
