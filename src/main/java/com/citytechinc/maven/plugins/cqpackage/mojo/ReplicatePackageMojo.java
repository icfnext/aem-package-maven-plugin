package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;
import com.citytechinc.maven.plugins.cqpackage.http.PackageManagerHttpClient;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "replicate", defaultPhase = LifecyclePhase.INSTALL)
@Execute(goal = "install")
public final class ReplicatePackageMojo extends AbstractPackageMojo {

    private static final String COMMAND = "replicate";

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping execution per configuration.");
        } else {


            final PackageManagerHttpClient httpClient = new PackageManagerHttpClient(this);



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
