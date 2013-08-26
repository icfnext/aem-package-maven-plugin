package com.citytechinc.maven.plugins.cqpackage.http

import com.citytechinc.maven.plugins.cqpackage.enums.ResponseFormat
import com.citytechinc.maven.plugins.cqpackage.mojo.PackageMojo
import org.apache.maven.plugin.logging.Log
import spock.lang.Specification

class PackageManagerHttpClientSpec extends Specification {

    def "upload package"() {
        setup:
        def mojo = createMockMojo()
        def packageFile = getPackageFile()

        def httpClient = new PackageManagerHttpClient(mojo, packageFile)

        expect:
        httpClient.response
    }

    def getPackageFile() {
        def url = this.class.getResource("/test.zip")

        new File(url.toURI())
    }

    def createMockMojo() {
        def mojo = Mock(PackageMojo)

        mojo.host >> "localhost"
        mojo.port >> 4502
        mojo.username >> "admin"
        mojo.password >> "admin"
        mojo.retryDelay >> 1000
        mojo.retryLimit >> 5
        mojo.force >> true
        mojo.command >> "upload"
        mojo.fileName >> ""
        mojo.responseFormat >> ResponseFormat.JSON
        mojo.log >> Mock(Log)

        mojo
    }
}
