package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;

public interface PackageMojo {

    String getCommand();

    String getFileName();

    String getHost();

    String getPassword();

    int getPort();

    ResponseFormat getResponseFormat();

    int getRetryDelay();

    int getRetryLimit();

    String getUsername();

    boolean isForce();
}
