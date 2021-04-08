package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.User;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

public class AuthenticateUser implements Route {


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
            } else {
                context.html(String.format(STATE_JSON, "state", "fail"));
            }
        } else {
            failRequest(context);
            return;
        }
    }
}
