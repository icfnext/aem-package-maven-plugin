package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;
import org.apache.maven.plugin.logging.Log;

public interface PackageMojo {

    String getCommand();

    String getFileName();

    String getHost();

    Log getLog();

    String getPassword();

    int getPort();

    ResponseFormat getResponseFormat();

    int getRetryDelay();

    int getRetryLimit();

    String getUsername();

    boolean isForce();

    boolean isQuiet();
}
