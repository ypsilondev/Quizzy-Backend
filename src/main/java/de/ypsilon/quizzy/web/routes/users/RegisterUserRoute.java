package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.util.apidoc.ApiRequestParameter;
import de.ypsilon.quizzy.util.apidoc.ApiResponse;
import de.ypsilon.quizzy.util.apidoc.DocumentedApiEndpoint;
import de.ypsilon.quizzy.util.apidoc.ApiResponses;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

@DocumentedApiEndpoint(name = "Create User", description = "Creates a new user in the system")
public class RegisterUserRoute implements Route {

    @Override
    public String getPath() {
        return "/users/register";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.POST;
    }

    @ApiRequestParameter(parameterName = "displayName", exampleValue = "SomeUsername", description = "The username of the user", parameterType = String.class)
    @ApiRequestParameter(parameterName = "email", exampleValue = "someone@example.com", description = "The email of the user", parameterType = String.class)
    @ApiRequestParameter(parameterName = "password", exampleValue = "SuperSecurePassword", description = "The password of the user (in cleartext)", parameterType = String.class)
    @ApiRequestParameter(parameterName = "profileImage", exampleValue = "606f801770402029ae887153", description = "The file-id of the user image (upload in future api-version)", parameterType = String.class)

    @ApiResponse(statusCode = 201, description = "The new user was created successfully", body = ApiResponses.SUCCESS_RESPONSE)
    @ApiResponse(statusCode = 400, description = "An error occurred while creating user (cause in response)", body = ApiResponses.FAIL_RESPONSE)
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
