package com.citytechinc.maven.plugins.cqpackage.enums;

public enum Command {
    UPLOAD("upload"),
    INSTALL("install"),
    REPLICATE("replicate");

    private final String parameter;

    Command(final String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
