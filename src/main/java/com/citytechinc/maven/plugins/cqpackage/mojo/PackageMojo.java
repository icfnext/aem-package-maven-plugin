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

    Integer getPort();

    ResponseFormat getResponseFormat();

    Map<String, String> getParameters();

    Integer getRetryDelay();

    Integer getRetryLimit();

    Integer getConnectTimeout();

    Integer getReadTimeout();

    String getScheme();

    String getUsername();

    Boolean isQuiet();
}
