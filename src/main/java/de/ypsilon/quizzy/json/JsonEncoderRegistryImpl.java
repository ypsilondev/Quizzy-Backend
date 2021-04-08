package de.ypsilon.quizzy.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JsonEncoderRegistryImpl implements JsonEncoderRegistry {

    public final List<JsonEncoder<?>> encoders = new ArrayList<>();

    public JsonEncoderRegistryImpl(Collection<JsonEncoder<?>> encoders) {
        this.encoders.addAll(encoders);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> JsonEncoder<T> get(Class<T> aClass) {
        for (JsonEncoder<?> codec : encoders) {
            if (codec.getEncoderClass().equals(aClass)) {
                return (JsonEncoder<T>) codec;
            }
        }
        return null;
    }
}
