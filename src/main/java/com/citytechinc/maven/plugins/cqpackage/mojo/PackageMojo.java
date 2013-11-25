package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.enums.Command;
import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;
import org.apache.maven.plugin.logging.Log;

import java.util.Map;

public interface PackageMojo {

    Command getCommand();

    String getContextPath();

    String getHost();

    Log getLog();

    String getPassword();

    int getPort();

    ResponseFormat getResponseFormat();

    Map<String, String> getParameters();

    int getRetryDelay();

    int getRetryLimit();

    String getUsername();

    boolean isQuiet();

    boolean isSecure();
}
