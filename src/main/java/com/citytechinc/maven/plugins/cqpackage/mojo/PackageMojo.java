package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.enums.Command;
import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;
import org.apache.maven.plugin.logging.Log;

public interface PackageMojo {

    Command getCommand();

    String getFileName();

    String getHost();

    Log getLog();

    String getPassword();

    String getPath();

    int getPort();

    ResponseFormat getResponseFormat();

    int getRetryDelay();

    int getRetryLimit();

    String getUsername();

    boolean isForce();

    boolean isQuiet();
}
