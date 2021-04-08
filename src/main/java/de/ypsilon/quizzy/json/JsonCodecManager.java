package de.ypsilon.quizzy.json;

import de.ypsilon.quizzy.json.encoders.QuestionJsonEncoder;

import java.util.*;

public class JsonCodecManager {

    private static JsonCodecManager instance;

    private final JsonCodecRegistry registry;
    private final JsonEncoderRegistry encoderRegistry;

    /**
     * Create a new JSONCodecManager instance and initialize the registry.
     */
    public JsonCodecManager() {
        instance = this;

        this.registry = new JsonCodecRegistryImpl(getCodecs());

        List<JsonEncoder<?>> encoders = new ArrayList<>(getCodecs());
        encoders.addAll(getEncoders());
        this.encoderRegistry = new JsonEncoderRegistryImpl(encoders);
    }

    public static JsonCodecManager getInstance() {
        return instance;
    }

    /**
     * Get the codec from a class or null if the class has no codec.
     *
     * @param aClass the class to search the codec for.
     * @param <T> the codec type.
     * @return a JsonCodec or null.
     */
    public <T> JsonCodec<T> getCodec(Class<T> aClass) {
        return this.registry.get(aClass);
    }

    public <T> JsonEncoder<T> getEncoder(Class<T> aClass) {
        return this.encoderRegistry.get(aClass);
    }

    /**
     * Get the codecs that are registered in the project.
     *
     * @return a collection with all codecs from the project.
     */
    private Collection<JsonCodec<?>> getCodecs() {
        Collection<JsonCodec<?>> codecs = new LinkedList<>();

        return codecs;
    }

    /**
     * Get all encoders that are registered in the encoder registry.
     *
     * @return a collection with encoders.
     */
    private Collection<JsonEncoder<?>> getEncoders() {
        Collection<JsonEncoder<?>> encoders = new LinkedList<>();

        encoders.add(new QuestionJsonEncoder());

        return encoders;
    }

}
