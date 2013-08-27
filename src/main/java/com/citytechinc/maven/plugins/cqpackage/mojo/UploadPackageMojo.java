package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.enums.Command;
import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;
import com.citytechinc.maven.plugins.cqpackage.http.PackageManagerHttpClient;
import com.citytechinc.maven.plugins.cqpackage.response.PackageManagerResponse;
import com.google.common.collect.Maps;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Map;

@Mojo(name = "upload", defaultPhase = LifecyclePhase.INSTALL)
public final class UploadPackageMojo extends AbstractPackageMojo {

    private static final String PATH = "/";

    /**
     * Force upload of CQ package even if it already exists.
     */
    @Parameter(property = "cq.package.force", defaultValue = "true")
    protected boolean force;

    @Override
    public Command getCommand() {
        return Command.UPLOAD;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public ResponseFormat getResponseFormat() {
        return ResponseFormat.JSON;
    }

    @Override
    public Map<String, String> getParameters() {
        final Map<String, String> parameters = Maps.newHashMap();

        parameters.put("force", Boolean.toString(force));

        return parameters;
    }

    @Override
    public PackageManagerResponse getResponse(final PackageManagerHttpClient httpClient) {
        final File packageFile = new File(fileName);

        return httpClient.getResponse(packageFile);
    }

    @Override
    public void handleSuccess(final PackageManagerResponse response) {
        session.getUserProperties().put(PROPERTY_PACKAGE_PATH, response.getPath());
    }
}
