package com.icfolson.maven.plugins.aempackage.mojo;

import com.icfolson.maven.plugins.aempackage.enums.ResponseFormat;
import org.apache.maven.plugin.logging.Log;

import java.util.Map;

public interface PackageMojo {

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
