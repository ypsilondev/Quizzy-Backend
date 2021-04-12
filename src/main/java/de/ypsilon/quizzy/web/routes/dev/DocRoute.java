package de.ypsilon.quizzy.web.routes.dev;

import de.ypsilon.quizzy.util.apidoc.ApiEndpointResponse;
import de.ypsilon.quizzy.util.apidoc.ApiResponses;
import de.ypsilon.quizzy.util.apidoc.ApiTest;
import de.ypsilon.quizzy.util.apidoc.DocumentedApiEndpoint;
import de.ypsilon.quizzy.web.Route;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

@DocumentedApiEndpoint(name = "Documentation-Endpoint", description = "Provides this HTML-Document containing all documented information about the API.")
public class DocRoute implements Route {
    @Override
    public String getPath() {
        return "/doc";
    }

    @Override
    public HandlerType getType() {
        return HandlerType.GET;
    }

    @ApiEndpointResponse(statusCode = 200, description = "This HTML-Document telling you everything about the api", body = ApiResponses.HTML)
    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.html(ApiTest.getDoc());
    }
}
