package com.citytechinc.maven.plugins.cqpackage.http

import com.citytechinc.maven.plugins.cqpackage.log.TestLog
import com.citytechinc.maven.plugins.cqpackage.mojo.AbstractPackageMojo
import com.citytechinc.maven.plugins.cqpackage.mojo.InstallPackageMojo
import com.citytechinc.maven.plugins.cqpackage.mojo.UploadPackageMojo
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class PackageManagerHttpClientSpec extends Specification {

    def "upload package"() {
        setup:
        def mojo = new UploadPackageMojo()

        setCommonProperties(mojo)

        mojo.force = true

        def httpClient = new PackageManagerHttpClient(mojo)
        def packageFile = getPackageFile()

        expect:
        httpClient.getResponse(packageFile)
    }

    def "install package"() {
        setup:
        def mojo = new InstallPackageMojo()

        setCommonProperties(mojo)

        def httpClient = new PackageManagerHttpClient(mojo)
        def path = "/etc/packages/my_packages/invalid.zip"

        expect:
        httpClient.getResponse(path)
    }

    void setCommonProperties(AbstractPackageMojo mojo) {
        mojo.host = "localhost"
        mojo.port = 4502
        mojo.username = "admin"
        mojo.password = "admin"
        mojo.retryDelay = 1000
        mojo.retryLimit = 5
        mojo.log = new TestLog()
    }

    def getPackageFile() {
        def url = this.class.getResource("/invalid.zip")

        new File(url.toURI())
    }
}
