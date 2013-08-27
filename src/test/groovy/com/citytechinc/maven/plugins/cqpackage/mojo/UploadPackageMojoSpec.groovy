package com.citytechinc.maven.plugins.cqpackage.mojo

import com.citytechinc.maven.plugins.cqpackage.http.PackageManagerHttpClient

class UploadPackageMojoSpec extends AbstractPackageMojoSpec {

    def "upload package"() {
        setup:
        def mojo = new UploadPackageMojo()

        setProperties(mojo)

        def httpClient = new PackageManagerHttpClient(mojo, getPackageFile())

        expect:
        httpClient.response
    }

    def getPackageFile() {
        def url = this.class.getResource("/test.zip")

        new File(url.toURI())
    }
}
