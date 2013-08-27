package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.enums.Command;
import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;
import com.citytechinc.maven.plugins.cqpackage.http.PackageManagerHttpClient;
import com.citytechinc.maven.plugins.cqpackage.response.PackageManagerResponse;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.Collections;
import java.util.Map;

@Mojo(name = "install", defaultPhase = LifecyclePhase.INSTALL)
@Execute(goal = "upload")
public final class InstallPackageMojo extends AbstractPackageMojo {

    @Override
    public Command getCommand() {
        return Command.INSTALL;
    }

    @Override
    public ResponseFormat getResponseFormat() {
        return ResponseFormat.JSON;
    }

    @Override
    public Map<String, String> getParameters() {
        return Collections.emptyMap();
    }

    @Override
    public PackageManagerResponse getResponse(final PackageManagerHttpClient httpClient) {
        final String path = (String) session.getUserProperties().get(PROPERTY_PACKAGE_PATH);

        return httpClient.getResponse(path);
    }
}
