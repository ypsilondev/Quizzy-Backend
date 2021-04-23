package de.ypsilon.quizzy.web.routes.match;

import com.shirkanesi.apidoc.ApiEndpointHandler;
import com.shirkanesi.apidoc.ApiEndpointRequestParameter;
import com.shirkanesi.apidoc.ApiRequestRequirement;
import com.shirkanesi.apidoc.DocumentedApiEndpoint;
import de.ypsilon.quizzy.QuizzyBackend;
import de.ypsilon.quizzy.dataset.Match;
import de.ypsilon.quizzy.dataset.user.User;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.UUID;

@DocumentedApiEndpoint(name = "Submit answer for current question", description = "Used to submit the answer of the current question")
public class SubmitQuestionAnswerRoute implements Route {
    @Override
    public String getPath() {
        return "/match/submitQuestion";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.GET;
    }

    @ApiEndpointHandler
    @ApiRequestRequirement(requirement = "Authenticated user")
    @ApiEndpointRequestParameter(parameterName = "matchId", description = "The id of the match the question was from", parameterType = String.class, exampleValue = "827453e0-6f32-43e3-af61-c3eee4398639")
    @ApiEndpointRequestParameter(parameterName = "answerID", description = "The answer the user pressed", parameterType = Integer.class, exampleValue = "0")
    @Override
    public void handle(@NotNull Context context) throws Exception {
        User user = RouteUtil.requireAuthenticatedUser(context);
        JSONObject json = new JSONObject(context.body());
        String matchId = json.getString("matchId");
        int answerID = json.getInt("answerID");

        Match match = QuizzyBackend.getQuizzyBackend().getMatchManager().getMatchById(UUID.fromString(matchId));
        boolean correct = match.submitAnswer(user, answerID);

        JSONObject responseJson = new JSONObject();
        responseJson.put("state", "ok");
        responseJson.put("correctAnswer", correct);

        RouteUtil.sendJsonMessage(context, responseJson);
    }
}
