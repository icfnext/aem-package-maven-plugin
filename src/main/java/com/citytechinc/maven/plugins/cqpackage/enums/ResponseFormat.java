package com.citytechinc.maven.plugins.cqpackage.enums;

public enum ResponseFormat {
    JSON(".json"),
    HTML(".html");

    private String extension;

    private ResponseFormat(final String extension) {
        this.extension = extension;
    }

    private String getExtension() {
        return extension;
    }
}
