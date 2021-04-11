package de.ypsilon.quizzy.util.apidoc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DocumentedApiEndpoint {
    String name();
    String description();
}
