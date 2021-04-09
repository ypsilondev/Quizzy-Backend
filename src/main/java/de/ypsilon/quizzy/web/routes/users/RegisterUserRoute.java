package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

public class RegisterUserRoute implements Route {

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
        String profileImageId = context.formParam("profileImage");

        RouteUtil.requireAllNotNull(displayName, email, cleartextPassword, profileImageId);
        User user = User.createAndStoreUser(displayName, email, cleartextPassword, new ObjectId(profileImageId));
        AuthenticateUserRoute.setSessionToken(context, user);
        RouteUtil.sendSuccessMessage(context);
    }
}
