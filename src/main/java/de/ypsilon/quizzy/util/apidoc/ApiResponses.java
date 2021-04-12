package de.ypsilon.quizzy.util.apidoc;

import org.json.JSONObject;

public enum ApiResponses {


    SUCCESS_RESPONSE(new JSONObject() {{
        put("state", "ok");
    }}),

    FAIL_RESPONSE(new JSONObject() {{
        put("state", "fail");
        put("cause", "An error message");
    }}),

    LOGIN_SUCCESSFUL(new JSONObject(){{
        put("state", "login");
        put("user", new JSONObject(){{
            put("displayName", "UserName");
            put("_id", "60734daad6d0b153d8d27b77");
            put("profileImage", "606f801770402029ae887153");
            put("totalScore", 1337);
        }});
    }}),

    LOGGED_IN_USER(new JSONObject() {{
        put("state", "logged-in");
        put("user", new JSONObject(){{
            put("displayName", "UserName");
            put("_id", "60734daad6d0b153d8d27b77");
            put("profileImage", "606f801770402029ae887153");
            put("totalScore", 1337);
        }});
    }}),

    HTML(new JSONObject());



    private final JSONObject json;

    ApiResponses(JSONObject json) {
        this.json = json;
    }

    public JSONObject getJson() {
        return json;
    }

}
