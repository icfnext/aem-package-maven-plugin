package com.citytechinc.maven.plugins.cqpackage.mojo
import com.citytechinc.maven.plugins.cqpackage.log.TestLog
import spock.lang.Specification

abstract class AbstractPackageMojoSpec extends Specification {

    def setProperties(AbstractPackageMojo mojo) {
        mojo.host = "localhost"
        mojo.port = 4502
        mojo.username = "admin"
        mojo.password = "admin"
        mojo.retryDelay = 1000
        mojo.retryLimit = 5
        mojo.force = true
        mojo.log = new TestLog()
    }
}
