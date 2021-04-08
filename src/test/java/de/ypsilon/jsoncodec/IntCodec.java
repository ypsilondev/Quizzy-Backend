package de.ypsilon.jsoncodec;

import de.ypsilon.quizzy.json.JsonCodec;
import org.json.JSONObject;

public class IntCodec implements JsonCodec<Integer> {

    @Override
    public Integer decode(JSONObject data) {
        return null;
    }

    @Override
    public JSONObject encode(Integer data) {
        return null;
    }

    @Override
    public Class<Integer> getEncoderClass() {
        return Integer.class;
    }
}
