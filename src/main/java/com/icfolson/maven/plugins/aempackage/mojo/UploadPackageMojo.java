package com.icfolson.maven.plugins.aempackage.mojo;

import com.google.common.collect.Maps;
import com.icfolson.maven.plugins.aempackage.enums.ResponseFormat;
import com.icfolson.maven.plugins.aempackage.http.PackageManagerHttpClient;
import com.icfolson.maven.plugins.aempackage.response.PackageManagerResponse;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Map;

@Mojo(name = "upload", defaultPhase = LifecyclePhase.INSTALL)
public final class UploadPackageMojo extends AbstractPackageMojo {

    /**
     * Force upload of CQ package even if it already exists.
     */
    @Parameter(property = "cq.package.force", defaultValue = "true")
    protected boolean force;

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
    public PackageManagerResponse getPackageManagerResponse(final PackageManagerHttpClient httpClient) {
        final File packageFile = new File(fileName);

        return httpClient.uploadPackage(packageFile);
    }
}
