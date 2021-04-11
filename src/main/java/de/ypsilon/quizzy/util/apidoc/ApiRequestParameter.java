package de.ypsilon.quizzy.util.apidoc;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ApiEndpointRequestParameters.class)
public @interface ApiRequestParameter {
    String parameterName();
    Class<?> parameterType();
    String exampleValue();
    String description();
}
