package de.ypsilon.quizzy.util.apidoc;


import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ApiRequestRequirements.class)
public @interface ApiRequestRequirement {
    String requirement();
}
