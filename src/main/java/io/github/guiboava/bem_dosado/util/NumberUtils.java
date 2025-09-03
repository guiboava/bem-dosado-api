package io.github.guiboava.bem_dosado.util;

import org.mapstruct.Named;

public class NumberUtils {

    public NumberUtils() {} // impede instanciação

    @Named("onlyDigits")
    public static String onlyDigits(String value) {
        return value != null ? value.replaceAll("\\D", "") : null;
    }
}