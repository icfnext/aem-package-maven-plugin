package com.icfolson.maven.plugins.aempackage.mojo;

import com.icfolson.maven.plugins.aempackage.http.PackageManagerHttpClient;
import com.icfolson.maven.plugins.aempackage.response.PackageManagerResponse;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractPackageMojo extends AbstractMojo implements PackageMojo {

    protected static final String PROPERTY_PACKAGE_PATH = "aem.package.path";

    /**
     * AEM package name.
     */
    @Parameter(property = "aem.package.fileName",
        defaultValue = "${project.build.directory}/${project.build.finalName}.zip")
    protected String fileName;

    /**
     * AEM host name.
     */
    @Parameter(defaultValue = "localhost")
    protected String host;

    /**
     * AEM password.
     */
    @Parameter(defaultValue = "admin")
    protected String password;

    /**
     * AEM port number.
     */
    @Parameter(defaultValue = "4502")
    protected Integer port;

    /**
     * AEM context path.
     */
    @Parameter
    protected String contextPath;

    /**
     * Connect to Package Manager using https scheme.
     */
    @Parameter(defaultValue = "false")
    protected boolean secure;

    /**
     * Quiet logging when executing package command.
     */
    @Parameter(property = "aem.package.quiet", defaultValue = "false")
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

    /**
     * Read timeout in milliseconds.
     */
    @Parameter(defaultValue = "30000")
    protected Integer readTimeout;

    /**
     * Connection timeout in milliseconds.
     */
    @Parameter(defaultValue = "30000")
    protected Integer connectTimeout;

    @Component
    protected MavenSession session;

    /**
     * Skip execution of the plugin.
     */
    @Parameter(property = "aem.package.skip", defaultValue = "false")
    protected Boolean skip;

    /**
     * AEM user name.
     */
    @Parameter(defaultValue = "admin")
    protected String username;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping execution per configuration.");
        } else {
            final PackageManagerResponse response = getPackageManagerResponse(new PackageManagerHttpClient(this));

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
    public String getContextPath() {
        return contextPath == null ? "" : contextPath;
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
    public Integer getPort() {
        return port;
    }

    @Override
    public Integer getRetryDelay() {
        return retryDelay;
    }

    @Override
    public Integer getRetryLimit() {
        return retryLimit;
    }

    @Override
    public Integer getReadTimeout() {
        return readTimeout;
    }

    @Override
    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    @Override
    public String getScheme() {
        return secure ? "https" : "http";
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Boolean isQuiet() {
        return quiet;
    }

    public abstract PackageManagerResponse getPackageManagerResponse(final PackageManagerHttpClient httpClient);
}
