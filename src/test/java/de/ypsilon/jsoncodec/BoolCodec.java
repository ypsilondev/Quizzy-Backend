package de.ypsilon.jsoncodec;

import de.ypsilon.quizzy.json.JsonCodec;
import org.json.JSONObject;

public class BoolCodec implements JsonCodec<Boolean> {

    @Override
    public Boolean decode(JSONObject data) {
        return null;
    }

    @Override
    public JSONObject encode(Boolean data) {
        return null;
    }

    @Override
    public Class<Boolean> getEncoderClass() {
        return Boolean.class;
    }
}
