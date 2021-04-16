package de.ypsilon.quizzy.web.routes.match;

import com.shirkanesi.apidoc.ApiEndpointHandler;
import com.shirkanesi.apidoc.ApiEndpointResponse;
import com.shirkanesi.apidoc.ApiRequestRequirement;
import com.shirkanesi.apidoc.ApiResponses;
import com.shirkanesi.apidoc.DocumentedApiEndpoint;
import de.ypsilon.quizzy.dataset.Match;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

@DocumentedApiEndpoint(name = "Create match", description = "Creates a new match between players")
public class CreateMatchRoute implements Route {

    @Override
    public String getPath() {
        return "/match/create";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.GET;
    }


    @ApiEndpointHandler
    @ApiRequestRequirement(requirement = "Authenticated user")
    @ApiEndpointResponse(statusCode = 200, description = "Match was created successfully", body = ApiResponses.SUCCESS_RESPONSE)
    @Override
    public void handle(@NotNull Context context) throws Exception {
        // TODO
        User user = RouteUtil.requireAuthenticatedUser(context);
        Match match = new Match();
        match.addUser(user);

        JSONObject responseJson = new JSONObject();
        responseJson.put("state", "ok");
        responseJson.put("match", match.asJson());

        RouteUtil.sendJsonMessage(context, responseJson);
    }
}
