package com.icfolson.maven.plugins.cqpackage.mojo;

import com.icfolson.maven.plugins.cqpackage.enums.ResponseFormat;
import com.icfolson.maven.plugins.cqpackage.http.PackageManagerHttpClient;
import com.icfolson.maven.plugins.cqpackage.response.PackageManagerResponse;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.Collections;
import java.util.Map;

@Mojo(name = "replicate", defaultPhase = LifecyclePhase.INSTALL)
@Execute(goal = "install")
public final class ReplicatePackageMojo extends AbstractPackageMojo {

    @Override
    public ResponseFormat getResponseFormat() {
        return ResponseFormat.JSON;
    }

    @Override
    public Map<String, String> getParameters() {
        return Collections.emptyMap();
    }

    @Override
    public PackageManagerResponse getPackageManagerResponse(final PackageManagerHttpClient httpClient) {
        final String path = (String) session.getUserProperties().get(PROPERTY_PACKAGE_PATH);

        return httpClient.replicatePackage(path);
    }
}
