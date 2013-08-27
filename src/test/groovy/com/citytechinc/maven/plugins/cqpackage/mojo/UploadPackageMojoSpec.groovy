package com.citytechinc.maven.plugins.cqpackage.mojo

import com.citytechinc.maven.plugins.cqpackage.http.PackageManagerHttpClient

class UploadPackageMojoSpec extends AbstractPackageMojoSpec {

    def "upload package"() {
        setup:
        def mojo = new UploadPackageMojo()

        mojo.force = true

        setProperties(mojo)

        def httpClient = new PackageManagerHttpClient(mojo)

        def packageFile = getPackageFile()

        expect:
        httpClient.getResponse(packageFile)
    }

    def getPackageFile() {
        def url = this.class.getResource("/test.zip")

        new File(url.toURI())
    }
}
