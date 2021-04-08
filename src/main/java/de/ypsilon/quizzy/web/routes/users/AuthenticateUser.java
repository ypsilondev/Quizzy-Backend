package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.user.SessionToken;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

public class AuthenticateUser implements Route {

    private static final String SESSION_TOKEN_COOKIE_NAME = "session_token";


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

        if (cleartextPassword != null) {
            User user;
            if (displayName != null) {
                user = User.getUserByDisplayName(displayName);
            } else if (email != null) {
                user = User.getUserByEmail(email);
            } else {
                failRequest(context);
                return;
            }
            if (user.isValidPassword(cleartextPassword)) {
                context.html(String.format(STATE_JSON, "state", "login"));
                // TODO create user session etc...
                if(context.cookie(SESSION_TOKEN_COOKIE_NAME) == null){
                    context.cookie(SESSION_TOKEN_COOKIE_NAME, SessionToken.createAndSaveSessionToken(user).getTokenString(), 1717917367);
                }
            } else {
                context.html(String.format(STATE_JSON, "state", "fail"));
            }
        } else {
            failRequest(context);
            return;
        }
    }
}
