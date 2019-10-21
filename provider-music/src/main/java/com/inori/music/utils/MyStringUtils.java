package com.inori.music.utils;

import org.springframework.util.StringUtils;

import java.util.function.Consumer;
import javafx.util.Pair;

public class MyStringUtils {
    public static Consumer<Consumer<String>> ifStringNotEmpty(String src) {
        Consumer<Consumer<String>> consumer = (action) -> {
            if (!StringUtils.isEmpty(src)) {
                action.accept(src);
            }
        };

        return consumer;
    }

    public static Consumer<Consumer<Pair<String, String>>>  getPairIfKeyValueNeitherEmpty(String key, String value) {
        return (action) -> {
            if (!StringUtils.isEmpty(value) && !StringUtils.isEmpty(key)) {
                action.accept(new Pair<>(key, value));
            }
        };
    }
}
