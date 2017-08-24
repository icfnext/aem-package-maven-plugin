package com.icfolson.maven.plugins.aempackage.http

import com.icfolson.maven.plugins.aempackage.mojo.AbstractPackageMojo
import com.icfolson.maven.plugins.aempackage.mojo.InstallPackageMojo
import com.icfolson.maven.plugins.aempackage.mojo.UploadPackageMojo
import groovy.json.JsonBuilder
import net.jadler.Jadler
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class PackageManagerHttpClientSpec extends Specification {

    def setup() {
        Jadler.initJadlerListeningOn(8888)
    }

    def cleanup() {
        Jadler.closeJadler()
    }

    def "upload package"() {
        setup:
        setupMockServer("/", ["msg": "", "path": "", "success": true])

        def mojo = new UploadPackageMojo()

        init(mojo)

        mojo.force = true

        def httpClient = new PackageManagerHttpClient(mojo)

        when:
        httpClient.uploadPackage(packageFile)

        then:
        verifyRequests("/", 1)
    }

    def "install package"() {
        setup:
        def path = "/etc/packages/my_packages/valid.zip"

        setupMockServer(path, ["msg": "", "path": "", "success": true])

        def mojo = new InstallPackageMojo()

        init(mojo)

        mojo.dependencyHandling = "required"

        def httpClient = new PackageManagerHttpClient(mojo)

        when:
        httpClient.installPackage(path)

        then:
        verifyRequests(path, 1)
    }

    def setupMockServer(String path, json) {
        Jadler.onRequest()
            .havingMethodEqualTo("POST")
            .havingPathEqualTo("/crx/packmgr/service/.json$path")
            .respond()
            .withStatus(200)
            .withContentType("application/json")
            .withBody(new JsonBuilder(json).toString())
    }

    void verifyRequests(String path, int times) {
        Jadler.verifyThatRequest()
            .havingPathEqualTo("/crx/packmgr/service/.json$path")
            .receivedTimes(times)
    }

    void init(AbstractPackageMojo mojo) {
        mojo.host = "localhost"
        mojo.port = 8888
        mojo.username = "admin"
        mojo.password = "admin"
        mojo.retryDelay = 1000
        mojo.retryLimit = 5
        mojo.log = new MockLog()
    }

    File getPackageFile() {
        def url = this.class.getResource("/valid.zip")

        new File(url.toURI())
    }
}
