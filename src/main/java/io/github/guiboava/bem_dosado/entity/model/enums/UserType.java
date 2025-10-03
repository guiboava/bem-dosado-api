package io.github.guiboava.bem_dosado.entity.model.enums;

public enum UserType {

    F("Familiar", "FAMILY"),
    C("Cuidador", "CAREGIVER"),
    A("Administrador", "ADMIN");


    private final String fullUserType;
    private final String fullRoleUserType;

    UserType(String fullUserType, String fullRoleUserType) {
        this.fullUserType = fullUserType;
        this.fullRoleUserType = fullRoleUserType;
    }


    public boolean isAdmin() {
        return this.getRoleName().equals("ADMIN");
    }

    public String getFullUserType() {
        return fullUserType;
    }

    public String getRoleName() {
        return fullRoleUserType;
    }

}
