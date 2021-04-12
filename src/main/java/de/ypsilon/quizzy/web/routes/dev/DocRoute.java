package de.ypsilon.quizzy.web.routes.dev;

import com.shirkanesi.apidoc.ApiDocumentationManager;
import com.shirkanesi.apidoc.ApiEndpointHandler;
import com.shirkanesi.apidoc.DocumentedApiEndpoint;
import de.ypsilon.quizzy.QuizzyBackend;
import de.ypsilon.quizzy.web.Route;
import de.ypsilon.quizzy.web.WebManager;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

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

    @ApiEndpointHandler
    // @ApiEndpointResponse(statusCode = 200, description = "This HTML-Document telling you everything about the api", body = ApiResponses.HTML)
    @Override
    public void handle(@NotNull Context context) throws Exception {
        List<Class<?>> endpoints = QuizzyBackend.getQuizzyBackend().getWebManager().getRoutes().stream().map(Route::getClass).collect(Collectors.toList());
        ApiDocumentationManager adm = new ApiDocumentationManager(endpoints);
        context.html(adm.getDoc());
    }
}
