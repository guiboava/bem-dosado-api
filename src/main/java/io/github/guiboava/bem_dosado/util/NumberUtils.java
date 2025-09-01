package io.github.guiboava.bem_dosado.util;

import lombok.experimental.UtilityClass;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

public class NumberUtils {

    public NumberUtils() {} // impede instanciação

    @Named("onlyDigits")
    public static String onlyDigits(String value) {
        return value != null ? value.replaceAll("\\D", "") : null;
    }
}