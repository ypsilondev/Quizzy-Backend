package de.ypsilon.quizzy.web.routes.users.security;

import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.dataset.user.VerificationCode;
import de.ypsilon.quizzy.exception.QuizzyWebException;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

public class VerifyUserRoute implements Route {
    @Override
    public String getPath() {
        return "/users/security/verify";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.POST;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        User user = RouteUtil.requireAuthenticatedUser(context);
        String verificationNumberString = context.formParam("verificationNumber");
        RouteUtil.requireAllNotNull(verificationNumberString);
        int verificationNumber = RouteUtil.getInt(verificationNumberString);
        VerificationCode.verifyUser(user, verificationNumber);
        RouteUtil.sendSuccessMessage(context);
    }
}
