package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.json.JsonCodecManager;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class LoginCheckRoute implements Route {

    @Override
    public String getPath() {
        return "/users/logincheck";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.GET;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        User user = RouteUtil.requireAuthenticatedUser(context);
        JSONObject json = new JSONObject();
        json.put("state", "logged-in");
        json.put("user", JsonCodecManager.getInstance().getEncoder(User.class).encode(user));
        RouteUtil.sendJsonMessage(context, json.toString());
    }
}
