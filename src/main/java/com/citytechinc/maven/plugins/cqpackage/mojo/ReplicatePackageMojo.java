package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.enums.Command;
import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;
import com.citytechinc.maven.plugins.cqpackage.http.PackageManagerHttpClient;
import com.citytechinc.maven.plugins.cqpackage.response.PackageManagerResponse;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "replicate", defaultPhase = LifecyclePhase.INSTALL)
@Execute(goal = "install")
public final class ReplicatePackageMojo extends AbstractPackageMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping execution per configuration.");
        } else {
            final PackageManagerResponse response = new PackageManagerHttpClient(this).getResponse();

            if (response == null) {
                throw new MojoExecutionException("Error replicating package.");
            } else {
                if (response.isSuccess()) {
                    getLog().info(response.getMessage());
                } else {
                    throw new MojoExecutionException(response.getMessage());
                }
            }
        }
    }

    @Override
    public Command getCommand() {
        return Command.REPLICATE;
    }

    @Override
    public String getPath() {
        return (String) session.getUserProperties().get(PROPERTY_PACKAGE_PATH);
    }

    @Override
    public ResponseFormat getResponseFormat() {
        return ResponseFormat.JSON;
    }
}
