package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "install", defaultPhase = LifecyclePhase.INSTALL)
@Execute(goal = "upload")
public final class InstallPackageMojo extends AbstractPackageMojo {

    private static final String COMMAND = "install";

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping execution per configuration.");
        } else {

        }
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    @Override
    public ResponseFormat getResponseFormat() {
        return ResponseFormat.HTML;
    }
}
