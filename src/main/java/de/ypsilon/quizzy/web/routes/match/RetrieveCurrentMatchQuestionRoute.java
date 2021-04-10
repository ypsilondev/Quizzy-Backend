package de.ypsilon.quizzy.web.routes.match;

import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

/**
 * This class models the endpoint to receive the current question for a player in a match
 */
public class RetrieveCurrentMatchQuestionRoute implements Route {

    @Override
    public String getPath() {
        return "/match/retrieveQuestion";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.POST;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        // TODO
    }
}
