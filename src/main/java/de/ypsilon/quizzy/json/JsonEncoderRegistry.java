package de.ypsilon.quizzy.json;

public interface JsonEncoderRegistry {

    <T> JsonEncoder<T> get(Class<T> aClass);

}
