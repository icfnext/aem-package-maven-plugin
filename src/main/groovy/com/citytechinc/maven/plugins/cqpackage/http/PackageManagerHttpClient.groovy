package com.citytechinc.maven.plugins.cqpackage.http

import com.citytechinc.maven.plugins.cqpackage.mojo.PackageMojo
import com.citytechinc.maven.plugins.cqpackage.response.PackageManagerResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientHandlerException
import com.sun.jersey.api.client.UniformInterfaceException
import com.sun.jersey.api.client.WebResource
import com.sun.jersey.api.client.config.DefaultClientConfig
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter
import com.sun.jersey.multipart.FormDataMultiPart
import com.sun.jersey.multipart.MultiPart
import com.sun.jersey.multipart.file.FileDataBodyPart
import org.apache.maven.plugin.MojoExecutionException

import javax.ws.rs.core.MediaType

class PackageManagerHttpClient {

    private static final ObjectMapper MAPPER = new ObjectMapper()

    private final PackageMojo mojo

    private final WebResource resource

    PackageManagerHttpClient(PackageMojo mojo) {
        this.mojo = mojo

        resource = client.resource("${mojo.scheme}://${mojo.host}:${mojo.port}")
    }

    PackageManagerResponse getResponse(String path) {
        def url = buildUrl(path)
        def entity = buildRequestEntity(null)

        getResponseInternal(url, entity)
    }

    PackageManagerResponse getResponse(File packageFile) {
        def url = buildUrl("/")
        def entity = buildRequestEntity(packageFile)

        getResponseInternal(url, entity)
    }

    private String buildUrl(path) {
        if (!path) {
            throw new MojoExecutionException("Package has not been uploaded.")
        }

        def url = "${mojo.contextPath}/crx/packmgr/service/${mojo.responseFormat.extension}$path"

        mojo.log.debug "Package Manager URL = $url"

        url
    }

    private PackageManagerResponse getResponseInternal(String url, MultiPart entity) {
        def retryLimit = mojo.retryLimit
        def retryDelay = mojo.retryDelay
        def retryCount = 0

        def response = executeRequest(url, entity)

        while (!response && retryCount < retryLimit) {
            if (!mojo.quiet) {
                mojo.log.info "Error getting response from Package Manager, retrying..."
            }

            Thread.sleep(retryDelay)

            response = executeRequest(url, entity)

            retryCount++
        }

        response
    }

    private MultiPart buildRequestEntity(File packageFile) {
        def multiPart = new FormDataMultiPart()

        multiPart.field("cmd", mojo.command.parameter)

        mojo.parameters.each { name, value ->
            multiPart.field(name, value)
        }

        if (packageFile) {
            multiPart.bodyPart(new FileDataBodyPart("package", packageFile))
        }

        multiPart
    }

    private PackageManagerResponse executeRequest(String url, MultiPart entity) {
        def packageManagerResponse = null

        try {
            packageManagerResponse = resource.path(url)
                .type(MediaType.MULTIPART_FORM_DATA_TYPE)
                .post(PackageManagerResponse, entity)

            mojo.log.debug("Package Manager response = $packageManagerResponse")
        } catch (UniformInterfaceException e) {
            if (!mojo.quiet) {
                mojo.log.info("Error getting response from Package Manager, status code = ${e.response.status}")
            }
        } catch (ClientHandlerException e) {
            if (!mojo.quiet) {
                mojo.log.info("Error processing Package Manager response as JSON")
            }
        }

        packageManagerResponse
    }

    private Client getClient() {
        def clientConfig = new DefaultClientConfig()

        clientConfig.singletons.add(new JacksonJsonProvider(MAPPER))

        def client = Client.create(clientConfig)

        client.addFilter(new HTTPBasicAuthFilter(mojo.username, mojo.password))
        client.connectTimeout = mojo.connectTimeout
        client.readTimeout = mojo.readTimeout

        client
    }
}
