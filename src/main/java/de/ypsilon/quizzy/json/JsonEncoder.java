package de.ypsilon.quizzy.json;

import org.json.JSONObject;

public interface JsonEncoder<T> {

    /**
     * Encode a object to the JSON value.
     *
     * @param data the object to encode.
     */
    JSONObject encode(T data);

    /**
     * The class that is going to be encoded.
     *
     * @return a class.
     */
    Class<T> getEncoderClass();

}
