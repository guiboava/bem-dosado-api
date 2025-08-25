package io.github.guiboava.bem_dosado.entity.model.enums;

public enum Dependency {

    G1("Independente"),
    G2("Dependência Parcial"),
    G3("Dependência Total");

    private final String fullDependency;

    Dependency(String dependency) {
        this.fullDependency = dependency;
    }

    public String getFullDependency() {
        return fullDependency;
    }

}
