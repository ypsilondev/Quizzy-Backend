package de.ypsilon.quizzy.web;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;

import java.util.Arrays;
import java.util.Objects;

/**
 * Interface for a new route object.
 * @see io.javalin.http.Handler for usage from the handler.
 *
 * @version 1.0
 */
public interface Route extends Handler {

    String SUCCESS_JSON = "{\"state\":\"ok\"}";
    String STATE_JSON = "{\"%s\":\"%s\"}";
    String ERROR_IN_REQUEST = "There is at least one error in your request!";

    /**
     * Get the path for the route. E.g. /users
     *
     * @return a string.
     */
    String getPath();

    /**
     * Get the handler type for the route. E.g. POST or GET
     * @return a Handler Type
     */
    HandlerType getType();

    default boolean allNotNull(Object... objs) {
        Arrays.stream(objs).forEach(System.out::println);
        return Arrays.stream(objs).allMatch(Objects::nonNull);
    }

    default void failRequest(Context context) {
        context.html(ERROR_IN_REQUEST);
        context.status(400);
    }

}
