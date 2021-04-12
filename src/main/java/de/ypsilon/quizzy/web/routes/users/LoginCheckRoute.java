package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.json.JsonCodecManager;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.util.apidoc.ApiEndpointResponse;
import de.ypsilon.quizzy.util.apidoc.ApiResponses;
import de.ypsilon.quizzy.util.apidoc.DocumentedApiEndpoint;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

@DocumentedApiEndpoint(name = "Login-Test", description = "Tests, whether the user is authenticated in the system")
public class LoginCheckRoute implements Route {

    @Override
    public String getPath() {
        return "/users/logincheck";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.GET;
    }

    @ApiEndpointResponse(statusCode = 200, description = "User is authenticated. The user is sent back.", body = ApiResponses.LOGGED_IN_USER)
    @ApiEndpointResponse(statusCode = 400, description = "User is not authenticated (cause in response)", body = ApiResponses.FAIL_RESPONSE)
    @Override
    public void handle(@NotNull Context context) throws Exception {
        User user = RouteUtil.requireAuthenticatedUser(context);
        JSONObject json = new JSONObject();
        json.put("state", "logged-in");
        json.put("user", JsonCodecManager.getInstance().getEncoder(User.class).encode(user));
        RouteUtil.sendJsonMessage(context, json.toString());
    }
}
