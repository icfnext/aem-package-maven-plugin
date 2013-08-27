package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.enums.Command;
import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;
import com.citytechinc.maven.plugins.cqpackage.http.PackageManagerHttpClient;
import com.citytechinc.maven.plugins.cqpackage.response.PackageManagerResponse;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "upload", defaultPhase = LifecyclePhase.INSTALL)
public final class UploadPackageMojo extends AbstractPackageMojo {

    private static final String PATH = "/";

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping execution per configuration.");
        } else {
            final PackageManagerResponse response = new PackageManagerHttpClient(this).getResponse();

            if (response == null) {
                throw new MojoExecutionException("Error uploading package.");
            } else {
                if (response.isSuccess()) {
                    getLog().info(response.getMessage());

                    session.getUserProperties().put(PROPERTY_PACKAGE_PATH, response.getPath());
                } else {
                    throw new MojoExecutionException(response.getMessage());
                }
            }
        }
    }

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
}
