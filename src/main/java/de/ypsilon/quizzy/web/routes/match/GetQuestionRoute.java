package de.ypsilon.quizzy.web.routes.match;

import com.shirkanesi.apidoc.ApiEndpointHandler;
import com.shirkanesi.apidoc.ApiEndpointRequestParameter;
import com.shirkanesi.apidoc.ApiEndpointResponse;
import com.shirkanesi.apidoc.ApiRequestRequirement;
import com.shirkanesi.apidoc.ApiResponses;
import com.shirkanesi.apidoc.DocumentedApiEndpoint;
import de.ypsilon.quizzy.QuizzyBackend;
import de.ypsilon.quizzy.dataset.Match;
import de.ypsilon.quizzy.dataset.question.ServableQuestion;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import de.ypsilon.quizzy.web.routes.users.AuthenticateUserRoute;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.UUID;

@DocumentedApiEndpoint(name = "Get current question", description = "Returns the current question of the authenticated user")
public class GetQuestionRoute implements Route {
    @Override
    public String getPath() {
        return "/match/getQuestion";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.GET;
    }

    @ApiEndpointHandler
    @ApiRequestRequirement(requirement = "Authenticated user")
    @ApiEndpointRequestParameter(parameterName = "matchId", description = "The id of the match the question was from", parameterType = String.class, exampleValue = "827453e0-6f32-43e3-af61-c3eee4398639")
    // FIXME
    // @ApiEndpointResponse(statusCode = 200, description = "Match was created successfully", body = ApiResponses.SUCCESS_RESPONSE)
    @Override
    public void handle(@NotNull Context context) throws Exception {
        User user = RouteUtil.requireAuthenticatedUser(context);

        JSONObject json = new JSONObject(context.body());

        String matchId = json.getString("matchId");

        Match match = QuizzyBackend.getQuizzyBackend().getMatchManager().getMatchById(UUID.fromString(matchId));
        ServableQuestion servableQuestion = match.serveCurrentQuestion(user);

        JSONObject responseJson = new JSONObject();
        responseJson.put("state", "ok");
        responseJson.put("question", servableQuestion.asJson());

        RouteUtil.sendJsonMessage(context, responseJson);
    }
}
