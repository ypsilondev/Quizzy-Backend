package de.ypsilon.quizzy.web.routes.match;

import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

public class CreateMatchRoute implements Route {

    @Override
    public String getPath() {
        return "/match/create";
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
