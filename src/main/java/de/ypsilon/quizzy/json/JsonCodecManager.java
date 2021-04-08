package de.ypsilon.quizzy.json;

import java.util.Collection;
import java.util.LinkedList;

public class JsonCodecManager {

    private final JsonCodecRegistry registry;

    /**
     * Create a new JSONCodecManager instance and initialize the registry.
     */
    public JsonCodecManager() {
        this.registry = new JsonCodecRegistryImpl(getCodecs());
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

    /**
     * Get the codecs that are registered in the project.
     *
     * @return a collection with all codecs from the project.
     */
    private Collection<JsonCodec<?>> getCodecs() {
        Collection<JsonCodec<?>> codecs = new LinkedList<>();

        return codecs;
    }

}
