package de.ypsilon.quizzy.json;

import java.util.List;

public interface JsonCodecRegistry {

    /**
     * Get all codecs from the codec registry.
     *
     * @return a unmodifiable list with all codecs.
     */
    List<JsonCodec<?>> getCodecs();

    /**
     * Get the codec from a specific class.
     *
     * @param aClass the class to look.
     * @param <T> the type from the codec.
     * @return the corresponding Codec or null.
     */
    <T> JsonCodec<T> get(Class<T> aClass);

}
