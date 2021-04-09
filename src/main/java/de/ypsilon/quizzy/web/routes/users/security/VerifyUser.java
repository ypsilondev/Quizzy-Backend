package de.ypsilon.quizzy.web.routes.users.security;

import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.dataset.user.VerificationCode;
import de.ypsilon.quizzy.exception.QuizzyWebException;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

public class VerifyUser implements Route {
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
        int verificationNumber;
        try {
            verificationNumber = Integer.parseInt(verificationNumberString);
        } catch (NumberFormatException e) {
            throw new QuizzyWebException("The provided number could not be parsed!");
        }
        VerificationCode.verifyUser(user, verificationNumber);
    }
}
