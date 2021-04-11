package de.ypsilon.quizzy.util.apidoc;

import de.ypsilon.quizzy.QuizzyBackend;
import de.ypsilon.quizzy.web.routes.users.LoginCheckRoute;
import de.ypsilon.quizzy.web.routes.users.RegisterUserRoute;

public class ApiTest {

    public static void main(String[] args) {
        System.out.println(new ApiEndpointDocumentation(RegisterUserRoute.class).getDocumentation());
        System.out.println(new ApiEndpointDocumentation(LoginCheckRoute.class).getDocumentation());
    }

    public static String getDoc(){
        StringBuilder out = new StringBuilder();

        QuizzyBackend.getQuizzyBackend().getWebManager().getRoutes().forEach(route -> {
            out.append(new ApiEndpointDocumentation(route.getClass()).getDocumentation());
        });

        return out.toString();
    }
}
