package com.icfolson.maven.plugins.aempackage.mojo;

import com.google.common.collect.Maps;
import com.icfolson.maven.plugins.aempackage.enums.ResponseFormat;
import com.icfolson.maven.plugins.aempackage.http.PackageManagerHttpClient;
import com.icfolson.maven.plugins.aempackage.response.PackageManagerResponse;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Map;

@Mojo(name = "install", defaultPhase = LifecyclePhase.INSTALL)
@Execute(goal = "upload")
public final class InstallPackageMojo extends AbstractPackageMojo {

    @Parameter(property = "aem.package.install.recursive", defaultValue = "false")
    private boolean installRecursive;

    @Parameter(property = "aem.package.install.dependencyHandling", defaultValue = "required")
    protected String dependencyHandling;

    @Override
    public ResponseFormat getResponseFormat() {
        return ResponseFormat.JSON;
    }

    @Override
    public Map<String, String> getParameters() {
        final Map<String, String> parameters = Maps.newHashMap();

        parameters.put("recursive", Boolean.toString(installRecursive));
        parameters.put("dependencyHandling", dependencyHandling);

        return parameters;
    }

    @Override
    public PackageManagerResponse getPackageManagerResponse(final PackageManagerHttpClient httpClient) {
        final String path = (String) session.getUserProperties().get(PROPERTY_PACKAGE_PATH);

        return httpClient.installPackage(path);
    }
}
