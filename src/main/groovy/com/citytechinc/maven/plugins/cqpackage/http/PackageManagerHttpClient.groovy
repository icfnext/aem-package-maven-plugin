package com.citytechinc.maven.plugins.cqpackage.http
import com.citytechinc.maven.plugins.cqpackage.mojo.PackageMojo
import com.citytechinc.maven.plugins.cqpackage.response.PackageManagerResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.HttpResponseException
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.maven.plugin.MojoExecutionException

class PackageManagerHttpClient {

    static final def MAPPER = new ObjectMapper()

    def mojo

    def packageFile

    PackageManagerHttpClient(PackageMojo mojo) {
        this(mojo, new File(mojo.fileName))
    }

    PackageManagerHttpClient(PackageMojo mojo, File packageFile) {
        this.mojo = mojo
        this.packageFile = packageFile
    }

    PackageManagerResponse getResponse() {
        def retryLimit = mojo.retryLimit
        def retryDelay = mojo.retryDelay
        def retryCount = 0

        def response = null







        /*
        while (retryCount < retryLimit) {
            if (!mojo.quiet) {


                if (status) {
                    mojo.log.info "Bundle is $status, retrying..."
                } else {
                    mojo.log.info "Bundle not found, retrying..."
                }
            }

            def result = executePackageCommand()

            // TODO check response
            if (result) {
                break
            }

            Thread.sleep(retryDelay)

            retryCount++
        }
        */

        executePackageCommand()
    }

    def executePackageCommand() {
        def packageManagerUrl = "/crx/packmgr/service/${mojo.responseFormat.extension}/?cmd=${mojo.command}"

        println "package manager url = $packageManagerUrl"

        def method = new HttpPost(packageManagerUrl)

        def requestEntity = new MultipartEntity()

        requestEntity.addPart("package", new FileBody(packageFile))
        requestEntity.addPart("force", new StringBody("${mojo.force}"))

        method.setEntity(requestEntity)

        def packageManagerResponse

        def httpClient = new DefaultHttpClient()

        httpClient.credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(mojo.username,
            mojo.password))

        def host = new HttpHost(mojo.host, mojo.port)

        try {
            def responseBody = httpClient.execute(host, method, new BasicResponseHandler())

            println "response body = $responseBody"

            packageManagerResponse = MAPPER.readValue(responseBody, PackageManagerResponse)

            println "package manager response = $packageManagerResponse"
        } catch (HttpResponseException e) {
            throw new MojoExecutionException("Error getting response from Package Manager.")
        } finally {
            httpClient.connectionManager.shutdown()
        }

        packageManagerResponse
    }
}
