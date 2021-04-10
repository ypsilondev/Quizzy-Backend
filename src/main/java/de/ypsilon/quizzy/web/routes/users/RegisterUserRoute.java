package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

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
        JSONObject json = new JSONObject(context.body());

        String displayName = json.getString("displayName");
        String email = json.getString("email");
        String cleartextPassword = json.getString("password");
        String profileImageId = json.getString("profileImage");

        User user = User.createAndStoreUser(displayName, email, cleartextPassword, new ObjectId(profileImageId));
        AuthenticateUserRoute.setSessionToken(context, user);
        RouteUtil.sendSuccessMessage(context);
        context.status(201);
    }
}
