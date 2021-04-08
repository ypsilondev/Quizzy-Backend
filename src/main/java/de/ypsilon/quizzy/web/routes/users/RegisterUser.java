package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.exception.UserCreationException;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

public class RegisterUser implements Route {
    @Override
    public String getPath() {
        return "/users/register";
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

        if (allNotNull(displayName, email, cleartextPassword)) {
            try {
                User.createAndStoreUser(displayName, email, cleartextPassword);
            } catch (UserCreationException e) {
                context.html(String.format("{\"%s\":\"%s\"}", "error", e.getMessage()));
            }
        } else {
            failRequest(context);
            return;
        }
    }
}
