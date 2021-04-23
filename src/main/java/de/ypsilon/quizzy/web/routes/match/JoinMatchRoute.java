package de.ypsilon.quizzy.web.routes.match;

import com.shirkanesi.apidoc.ApiEndpointHandler;
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

public class JoinMatchRoute implements Route {
    @Override
    public String getPath() {
        return "/match/join";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.GET;
    }

    @ApiEndpointHandler
    @Override
    public void handle(@NotNull Context context) throws Exception {
        User user = RouteUtil.requireAuthenticatedUser(context);
        JSONObject json = new JSONObject(context.body());
        String matchId = json.getString("matchId");

        Match match = QuizzyBackend.getQuizzyBackend().getMatchManager().getMatchById(UUID.fromString(matchId));

        match.addUser(user);

        JSONObject responseJson = new JSONObject();
        responseJson.put("state", "ok");
        responseJson.put("match", match.asJson());

        RouteUtil.sendJsonMessage(context, responseJson);
    }
}
