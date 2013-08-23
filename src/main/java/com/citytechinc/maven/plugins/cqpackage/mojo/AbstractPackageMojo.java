package com.citytechinc.maven.plugins.cqpackage.mojo;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractPackageMojo extends AbstractMojo implements PackageMojo {

    protected static final String PROPERTY_PACKAGE_PATH = "cq.package.path";

    /**
     * CQ host name.
     */
    @Parameter(defaultValue = "localhost")
    protected String host;

    /**
     * CQ port number.
     */
    @Parameter(defaultValue = "4502")
    protected Integer port;

    /**
     * CQ user name.
     */
    @Parameter(defaultValue = "admin")
    protected String username;

    /**
     * CQ password.
     */
    @Parameter(defaultValue = "admin")
    protected String password;

    /**
     * Force upload of CQ package even if it already exists.
     */
    @Parameter(property = "cq.package.force", defaultValue = "true")
    protected boolean force;

    /**
     * CQ package name.
     */
    @Parameter(property = "cq.package.fileName", defaultValue = "${project.build.directory}/${project.build.finalName}.zip")
    protected String fileName;

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

    /**
     * Skip execution of the plugin.
     */
    @Parameter(property = "cq.package.skip", defaultValue = "false")
    protected boolean skip;

    /**
     * Quiet logging when executing package command.
     */
    @Parameter(property = "cq.package.quiet", defaultValue = "false")
    protected boolean quiet;

    @Component
    protected MavenSession session;

    @Override
    public String getFileName() {
        return fileName;
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
    public boolean isForce() {
        return force;
    }
}
