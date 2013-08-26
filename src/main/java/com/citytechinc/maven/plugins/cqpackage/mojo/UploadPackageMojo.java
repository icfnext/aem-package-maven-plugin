package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;
import com.citytechinc.maven.plugins.cqpackage.http.PackageManagerHttpClient;
import com.citytechinc.maven.plugins.cqpackage.response.PackageManagerResponse;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "upload", defaultPhase = LifecyclePhase.INSTALL)
public final class UploadPackageMojo extends AbstractPackageMojo {

    private static final String COMMAND = "upload";

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping execution per configuration.");
        } else {
            final PackageManagerResponse response = new PackageManagerHttpClient(this).getResponse();

            if (response.isSuccess()) {


                final String packagePath = response.getPath();

                session.getUserProperties().put(PROPERTY_PACKAGE_PATH, packagePath);
            } else {

            }
        }
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    @Override
    public ResponseFormat getResponseFormat() {
        return ResponseFormat.JSON;
    }
}
