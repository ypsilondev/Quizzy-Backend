package de.ypsilon.quizzy.web.routes.users;

import de.ypsilon.quizzy.dataset.user.SessionToken;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.exception.UserAuthenticationException;
import de.ypsilon.quizzy.json.JsonCodecManager;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.util.apidoc.ApiEndpointRequestParameter;
import de.ypsilon.quizzy.util.apidoc.ApiEndpointResponse;
import de.ypsilon.quizzy.util.apidoc.ApiResponses;
import de.ypsilon.quizzy.util.apidoc.DocumentedApiEndpoint;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

@DocumentedApiEndpoint(name = "Log-In", description = "Authenticates a user in the system")
public class AuthenticateUserRoute implements Route {

    public static final String SESSION_TOKEN_COOKIE_NAME = "session_token";

    @Override
    public String getPath() {
        return "/users/authenticate";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.POST;
    }

    @ApiEndpointRequestParameter(parameterName = "loginName", description = "The login-name of the user, either displayName or email. Lower/Upper-case ignored.", exampleValue = "shirkanesi", parameterType = String.class)
    @ApiEndpointRequestParameter(parameterName = "password", description = "The user's password (in plaintext, SSL/TLS does provide the security)", exampleValue = "someP4ssw0rd", parameterType = String.class)

    @ApiEndpointResponse(statusCode = 200, description = "The new user was authenticated successfully", body = ApiResponses.LOGIN_SUCCESSFUL)
    @ApiEndpointResponse(statusCode = 400, description = "An error occurred while authenticated user (cause in response)", body = ApiResponses.FAIL_RESPONSE)
    @Override
    public void handle(@NotNull Context context) throws Exception {
        JSONObject json = new JSONObject(context.body());

        String loginName = json.getString("loginName");
        String cleartextPassword = json.getString("password");

        User user = User.getUserByDisplayName(loginName);
        if (user == null) {
            user = User.getUserByEmail(loginName);
        }
        if (user != null && user.isValidPassword(cleartextPassword)) {
            JSONObject responseJson = new JSONObject();
            responseJson.put("state", "login");
            responseJson.put("user", JsonCodecManager.getInstance().getEncoder(User.class).encode(user));
            RouteUtil.sendJsonMessage(context, responseJson.toString());
            setSessionToken(context, user);
        } else {
            throw new UserAuthenticationException("Invalid credentials!");
        }

    }

    static void setSessionToken(Context context, User user) {
        context.cookie(SESSION_TOKEN_COOKIE_NAME, SessionToken.createAndSaveSessionToken(user).getTokenString(), 1717917367);
    }
}
