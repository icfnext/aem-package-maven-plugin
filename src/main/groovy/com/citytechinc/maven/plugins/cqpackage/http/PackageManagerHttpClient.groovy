package com.citytechinc.maven.plugins.cqpackage.http

import com.citytechinc.maven.plugins.cqpackage.mojo.PackageMojo
import com.citytechinc.maven.plugins.cqpackage.response.PackageManagerResponse
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.HttpResponseException
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.impl.auth.BasicScheme
import org.apache.http.impl.client.BasicAuthCache
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.protocol.BasicHttpContext
import org.apache.maven.plugin.MojoExecutionException

import static org.apache.http.client.protocol.ClientContext.AUTH_CACHE

class PackageManagerHttpClient {

    static final def MAPPER = new ObjectMapper()

    PackageMojo mojo

    PackageManagerHttpClient(PackageMojo mojo) {
        this.mojo = mojo
    }

    PackageManagerResponse getResponse(String path) {
        def url = buildUrl(path)
        def method = buildMethod(url, null)

        getResponseInternal(method)
    }

    PackageManagerResponse getResponse(File packageFile) {
        def url = buildUrl("/")
        def method = buildMethod(url, packageFile)

        getResponseInternal(method)
    }

    private def getResponseInternal(method) {
        def retryLimit = mojo.retryLimit
        def retryDelay = mojo.retryDelay
        def retryCount = 0

        def httpClient = new DefaultHttpClient()

        httpClient.credentialsProvider.setCredentials(new AuthScope(mojo.host, mojo.port),
            new UsernamePasswordCredentials(mojo.username, mojo.password))

        def host = new HttpHost(mojo.host, mojo.port, mojo.secure ? "https" : "http")

        def context = buildContext(host)

        def response = null

        try {
            response = executeRequest(httpClient, host, method, context)

            while (!response && retryCount < retryLimit) {
                if (!mojo.quiet) {
                    mojo.log.info "Error getting response from Package Manager, retrying..."
                }

                Thread.sleep(retryDelay)

                response = executeRequest(httpClient, host, method, context)

                retryCount++
            }
        } finally {
            httpClient.connectionManager.shutdown()
        }

        response
    }

    private def buildUrl(path) {
        if (!path) {
            throw new MojoExecutionException("Package has not been uploaded.")
        }

        def url = "${mojo.contextPath}/crx/packmgr/service/${mojo.responseFormat.extension}$path"

        mojo.log.debug "Package Manager URL = $url"

        url
    }

    private def buildMethod(String url, File packageFile) {
        def method = new HttpPost(url)

        def requestEntity = new MultipartEntity()

        requestEntity.addPart("cmd", new StringBody(mojo.command.parameter))

        mojo.parameters.each { name, value ->
            requestEntity.addPart(name, new StringBody(value))
        }

        if (packageFile) {
            requestEntity.addPart("package", new FileBody(packageFile))
        }

        method.setEntity(requestEntity)

        method
    }

    private def buildContext(host) {
        def context = new BasicHttpContext()

        def cache = new BasicAuthCache()
        def scheme = new BasicScheme()

        cache.put(host, scheme)

        context.setAttribute(AUTH_CACHE, cache)

        context
    }

    private def executeRequest(httpClient, host, method, context) {
        def packageManagerResponse = null

        try {
            def responseBody = httpClient.execute(host, method, new BasicResponseHandler(), context)

            mojo.log.debug "Package Manager response = $responseBody"

            packageManagerResponse = MAPPER.readValue(responseBody, PackageManagerResponse)
        } catch (HttpResponseException e) {
            if (!mojo.quiet) {
                mojo.log.info("Error getting response from Package Manager, status code = ${e.statusCode}")
            }
        } catch (JsonProcessingException e) {
            if (!mojo.quiet) {
                mojo.log.info("Error processing Package Manager response as JSON");
            }
        }

        packageManagerResponse
    }
}
