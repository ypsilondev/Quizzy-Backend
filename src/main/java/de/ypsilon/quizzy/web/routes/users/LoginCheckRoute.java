package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

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
        RouteUtil.requireAuthenticatedUser(context);
        RouteUtil.sendJsonMessage(context, String.format(RouteUtil.STATE_JSON, "state", "logged-in"));
    }
}
