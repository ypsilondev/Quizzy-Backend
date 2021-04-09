package de.ypsilon.quizzy.web;

import de.ypsilon.quizzy.QuizzyBackend;
import de.ypsilon.quizzy.exception.QuizzyWebException;
import de.ypsilon.quizzy.util.EnvironmentVariableWrapper;
import de.ypsilon.quizzy.util.RouteUtil;
import de.ypsilon.quizzy.web.routes.questions.AddQuestionRoute;
import de.ypsilon.quizzy.web.routes.users.AuthenticateUser;
import de.ypsilon.quizzy.web.routes.users.RegisterUser;
import de.ypsilon.quizzy.web.routes.users.security.RevokeAllTokens;
import de.ypsilon.quizzy.web.routes.users.security.RevokeToken;
import de.ypsilon.quizzy.web.routes.users.security.VerifyUser;
import io.javalin.Javalin;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WebManager {

    private final int port;
    private final Javalin application;

    public WebManager() {
        EnvironmentVariableWrapper evw = EnvironmentVariableWrapper.getInstance();
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
    private Collection<Route> getRoutes() {
        List<Route> routes = new LinkedList<>();

        // /questions
        routes.add(new AddQuestionRoute());

        // /users
        routes.add(new RegisterUser());
        routes.add(new AuthenticateUser());

        // /users/security
        routes.add(new RevokeAllTokens());
        routes.add(new RevokeToken());
        routes.add(new VerifyUser());

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
