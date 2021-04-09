package de.ypsilon.quizzy.web.routes.users.security;

import de.ypsilon.quizzy.dataset.user.SessionToken;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

public class RevokeAllTokensRoute implements Route {
    @Override
    public String getPath() {
        return "/users/security/revokeAllTokens";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.POST;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        User user = RouteUtil.requireAuthenticatedUser(context);
        SessionToken.revokeAllSessionTokens(user);
        RouteUtil.sendSuccessMessage(context);
    }
}
