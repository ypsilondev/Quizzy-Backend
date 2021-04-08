package de.ypsilon.jsoncodec;

import de.ypsilon.quizzy.json.JsonCodec;
import de.ypsilon.quizzy.json.JsonCodecRegistry;
import de.ypsilon.quizzy.json.JsonCodecRegistryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonCodecRegistryTest {

    private JsonCodecRegistry registry;

    @BeforeEach
    public void registerCodec() {
        this.registry = new JsonCodecRegistryImpl(new IntCodec(), new BoolCodec());
    }

    @Test
    public void testGetCodec() {
        JsonCodec<Integer> intCodec = registry.get(Integer.class);
        Assertions.assertEquals(Integer.class, intCodec.getEncoderClass());

        JsonCodec<Boolean> boolCodec = registry.get(Boolean.class);
        Assertions.assertEquals(Boolean.class, boolCodec.getEncoderClass());

        JsonCodec<String> nonExistedCodec = registry.get(String.class);
        Assertions.assertNull(nonExistedCodec);
    }

}
