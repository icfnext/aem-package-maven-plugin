package com.citytechinc.maven.plugins.cqpackage.mojo;

import com.citytechinc.maven.plugins.cqpackage.http.PackageManagerHttpClient;
import com.citytechinc.maven.plugins.cqpackage.response.PackageManagerResponse;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractPackageMojo extends AbstractMojo implements PackageMojo {

    protected static final String PROPERTY_PACKAGE_PATH = "cq.package.path";

    /**
     * CQ package name.
     */
    @Parameter(property = "cq.package.fileName",
        defaultValue = "${project.build.directory}/${project.build.finalName}.zip")
    protected String fileName;

    /**
     * CQ host name.
     */
    @Parameter(defaultValue = "localhost")
    protected String host;

    /**
     * CQ password.
     */
    @Parameter(defaultValue = "admin")
    protected String password;

    /**
     * CQ port number.
     */
    @Parameter(defaultValue = "4502")
    protected Integer port;

    /**
     * Quiet logging when executing package command.
     */
    @Parameter(property = "cq.package.quiet", defaultValue = "false")
    protected boolean quiet;

    /**
     * Delay in milliseconds before retrying package command.
     */
    @Parameter(defaultValue = "1000")
    protected Integer retryDelay;

    /**
     * Number of times to retry package command before aborting.
     */
    @Parameter(defaultValue = "5")
    protected Integer retryLimit;

    @Component
    protected MavenSession session;

    /**
     * Skip execution of the plugin.
     */
    @Parameter(property = "cq.package.skip", defaultValue = "false")
    protected boolean skip;

    /**
     * CQ user name.
     */
    @Parameter(defaultValue = "admin")
    protected String username;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping execution per configuration.");
        } else {
            final PackageManagerHttpClient httpClient = new PackageManagerHttpClient(this);
            final PackageManagerResponse response = getResponse(httpClient);

            if (response == null) {
                throw new MojoExecutionException("Error executing package command.");
            } else {
                if (response.isSuccess()) {
                    getLog().info(response.getMessage());

                    final String path = response.getPath();

                    if (path != null) {
                        session.getUserProperties().put(PROPERTY_PACKAGE_PATH, path);
                    }
                } else {
                    throw new MojoExecutionException(response.getMessage());
                }
            }
        }
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public int getRetryDelay() {
        return retryDelay;
    }

    @Override
    public int getRetryLimit() {
        return retryLimit;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isQuiet() {
        return quiet;
    }

    public abstract PackageManagerResponse getResponse(final PackageManagerHttpClient httpClient);
}
