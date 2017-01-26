package com.icfolson.maven.plugins.aempackage.response

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
