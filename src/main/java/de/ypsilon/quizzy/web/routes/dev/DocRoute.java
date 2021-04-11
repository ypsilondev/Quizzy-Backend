package de.ypsilon.quizzy.web.routes.dev;

import de.ypsilon.quizzy.util.apidoc.ApiTest;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

public class DocRoute implements Route {
    @Override
    public String getPath() {
        return "/doc";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.GET;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.html(ApiTest.getDoc());
    }
}
