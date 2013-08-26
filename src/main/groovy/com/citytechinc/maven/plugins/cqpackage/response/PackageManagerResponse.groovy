package com.citytechinc.maven.plugins.cqpackage.response

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

@ToString(includeNames = true)
class PackageManagerResponse {

    @JsonProperty("msg")
    String message

    String path

    boolean success
}
