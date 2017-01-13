package com.citytechinc.maven.plugins.cqpackage.response

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.json.JsonBuilder

class PackageManagerResponse {

    @JsonProperty("msg")
    String message

    String path

    boolean success

    @Override
    String toString() {
        new JsonBuilder(this).toString()
    }
}
