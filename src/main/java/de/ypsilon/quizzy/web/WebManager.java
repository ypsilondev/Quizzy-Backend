package de.ypsilon.quizzy.web;

import de.ypsilon.quizzy.QuizzyBackend;
import de.ypsilon.quizzy.exception.QuizzyWebException;
import de.ypsilon.quizzy.util.EnvironmentVariablesUtil;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.routes.dev.DevRoute;
import de.ypsilon.quizzy.web.routes.dev.DocRoute;
import de.ypsilon.quizzy.web.routes.match.CreateMatchRoute;
import de.ypsilon.quizzy.web.routes.match.RetrieveCurrentMatchQuestionRoute;
import de.ypsilon.quizzy.web.routes.questions.AddQuestionRoute;
import de.ypsilon.quizzy.web.routes.users.AuthenticateUserRoute;
import de.ypsilon.quizzy.web.routes.users.LoginCheckRoute;
import de.ypsilon.quizzy.web.routes.users.RegisterUserRoute;
import de.ypsilon.quizzy.web.routes.users.security.RevokeAllTokensRoute;
import de.ypsilon.quizzy.web.routes.users.security.RevokeTokenRoute;
import de.ypsilon.quizzy.web.routes.users.security.VerifyUserRoute;
import io.javalin.Javalin;
import org.json.JSONException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WebManager {

    private final int port;
    private final Javalin application;

    public WebManager() {
        EnvironmentVariablesUtil evw = EnvironmentVariablesUtil.getInstance();
        this.port = Integer.parseInt(evw.getenv("web.port"));

        // initialize JavaLin
        this.application = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        }).start(port);

        // register all routes
        for (Route route : getRoutes()) {
            registerRoute(route);
        }
        this.application.exception(QuizzyWebException.class, (e, context) ->
                RouteUtil.failRequest(context, e.getMessage())
        );
        this.application.exception(JSONException.class, (e, context) ->
                RouteUtil.failRequest(context, e.getMessage())
        );
    }

    /**
     * Register a route in JavaLin.
     *
     * @param route the route to register.
     */
    private void registerRoute(Route route) {
        QuizzyBackend.LOGGER.info(String.format("register %s route %s", route.getType().name(), route.getPath()));
        this.application.addHandler(route.getType(), route.getPath(), route);
    }

    /**
     * Get all routes from the project.
     *
     * @return a list with all routes.
     */
    public Collection<Route> getRoutes() {
        List<Route> routes = new LinkedList<>();

        // DEV-ROUTES, only for debugging-use
        // /dev
        routes.add(new DevRoute());
        routes.add(new DocRoute());

        // /match
        routes.add(new CreateMatchRoute());
        routes.add(new RetrieveCurrentMatchQuestionRoute());

        // /questions
        routes.add(new AddQuestionRoute());

        // /users
        routes.add(new RegisterUserRoute());
        routes.add(new AuthenticateUserRoute());
        routes.add(new LoginCheckRoute());

        // /users/security
        routes.add(new RevokeAllTokensRoute());
        routes.add(new RevokeTokenRoute());
        routes.add(new VerifyUserRoute());

        return routes;
    }

    /**
     * Get the port the web api is running on.
     *
     * @return a positive integer.
     */
    public int getPort() {
        return port;
    }

    /**
     * Get the JavaLin instance.
     *
     * @return the JavaLin instance.
     */
    public Javalin getApplication() {
        return application;
    }
}
