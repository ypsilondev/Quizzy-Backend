package de.ypsilon.quizzy.web.routes.users.security;

import com.shirkanesi.apidoc.ApiEndpointHandler;
import com.shirkanesi.apidoc.ApiEndpointRequestParameter;
import com.shirkanesi.apidoc.ApiEndpointResponse;
import com.shirkanesi.apidoc.ApiRequestRequirement;
import com.shirkanesi.apidoc.ApiResponses;
import com.shirkanesi.apidoc.DocumentedApiEndpoint;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.dataset.user.VerificationCode;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

@DocumentedApiEndpoint(name = "Account-Verification", description = "Verifies the user's email-address.")
public class VerifyUserRoute implements Route {
    @Override
    public String getPath() {
        return "/users/security/verify";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.GET;
    }

    @ApiEndpointHandler
    @ApiRequestRequirement(requirement = "Valid session_token")
    @ApiEndpointRequestParameter(parameterName = "verificationNumber", description = "The verification-code the user got in the email", exampleValue = "123456", parameterType = Integer.class)
    @ApiEndpointResponse(statusCode = 200, description = "The new user was verified successfully", body = ApiResponses.SUCCESS_RESPONSE)
    @ApiEndpointResponse(statusCode = 400, description = "An error occurred while verifying the user (cause in response)", body = ApiResponses.FAIL_RESPONSE)
    @Override
    public void handle(@NotNull Context context) throws Exception {
        User user = RouteUtil.requireAuthenticatedUser(context);
        String verificationNumberString = context.queryParam("verificationNumber");
        RouteUtil.requireAllNotNull(verificationNumberString);
        int verificationNumber = RouteUtil.getInt(verificationNumberString);

        VerificationCode.verifyUser(user, verificationNumber);
        RouteUtil.sendSuccessMessage(context);
    }
}
