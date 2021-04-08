package de.ypsilon.quizzy.json;

import java.util.*;

public class JsonCodecRegistryImpl implements JsonCodecRegistry {

    private final List<JsonCodec<?>> codecs = new ArrayList<>();

    /**
     * Initialize a new Json Codec registry with json codecs.
     *
     * @param codecs the codecs that should be added.
     */
    public JsonCodecRegistryImpl(JsonCodec<?>... codecs) {
        this(Arrays.asList(codecs));
    }

    /**
     * Initialize a new Json Codec registry with json codecs.
     *
     * @param codecs the codecs that should be added.
     */
    public JsonCodecRegistryImpl(Collection<JsonCodec<?>> codecs) {
        this.codecs.addAll(codecs);
    }

    /**
     * Initialize a new Json Codec registry by providing existing codec registries and merging them.
     *
     * @param registries the other codec registries.
     */
    public JsonCodecRegistryImpl(JsonCodecRegistry... registries) {
        for (JsonCodecRegistry registry : registries) {
            this.codecs.addAll(registry.getCodecs());
        }
    }

    /**
     * Get all codecs from the codec registry.
     *
     * @return a unmodifiable list with all codecs.
     */
    @Override
    public List<JsonCodec<?>> getCodecs() {
        return Collections.unmodifiableList(codecs);
    }

    /**
     * Get the codec from a specific class.
     *
     * @param aClass the class to look.
     * @param <T> the type from the codec.
     * @return the corresponding Codec or null.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> JsonCodec<T> get(Class<T> aClass) {
        for (JsonCodec<?> codec : codecs) {
            if (codec.getEncoderClass().equals(aClass)) {
                return (JsonCodec<T>) codec;
            }
        }
        return null;
    }
}
