package io.github.guiboava.bem_dosado.entity.model.enums;

public enum Gender {
    
    M("Homem"),
    F("Mulher");

    private final String fullGender;

    Gender(String fullGender) {
        this.fullGender = fullGender;
    }

    public String getGender() {
        return fullGender;
    }

}