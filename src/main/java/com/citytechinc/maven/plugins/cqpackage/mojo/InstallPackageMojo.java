package com.citytechinc.maven.plugins.cqpackage.mojo;

import java.util.Map;

import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.citytechinc.maven.plugins.cqpackage.enums.Command;
import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat;
import com.citytechinc.maven.plugins.cqpackage.http.PackageManagerHttpClient;
import com.citytechinc.maven.plugins.cqpackage.response.PackageManagerResponse;
import com.google.common.collect.Maps;

@Mojo(name = "install", defaultPhase = LifecyclePhase.INSTALL)
@Execute(goal = "upload")
public final class InstallPackageMojo extends AbstractPackageMojo {

	@Parameter(property = "cq.package.install.recursive", defaultValue = "false")
	private boolean installRecursive;

	@Override
	public Command getCommand() {
		return Command.INSTALL;
	}

	@Override
	public ResponseFormat getResponseFormat() {
		return ResponseFormat.JSON;
	}

	@Override
	public Map<String, String> getParameters() {
		final Map<String, String> parameters = Maps.newHashMap();

		parameters.put("recursive", Boolean.toString(installRecursive));

		return parameters;
	}

	@Override
	public PackageManagerResponse getResponse(final PackageManagerHttpClient httpClient) {
		final String path = (String) session.getUserProperties().get(PROPERTY_PACKAGE_PATH);

		return httpClient.getResponse(path);
	}
}
