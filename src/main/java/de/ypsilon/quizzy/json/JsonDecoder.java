package de.ypsilon.quizzy.json;

import org.json.JSONObject;

public interface JsonDecoder<T> {

    /**
     * Decode a JSON Object to the native object.
     *
     * @param data the json data.
     * @return a object of type T.
     */
    T decode(JSONObject data);

}
