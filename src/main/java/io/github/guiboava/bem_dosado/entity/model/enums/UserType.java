package io.github.guiboava.bem_dosado.entity.model.enums;

public enum UserType {
    F("Familiar"),
    C("Cuidador");

    private final String fullUserType;

    UserType(String fullUserType) {
        this.fullUserType = fullUserType;
    }

    public String getfullUserType() {
        return fullUserType;
    }

}
