package com.citytechinc.maven.plugins.cqpackage.log

import org.apache.maven.plugin.logging.Log
import org.codehaus.groovy.runtime.StackTraceUtils

class TestLog implements Log {

    @Override
    boolean isDebugEnabled() {
        return true
    }

    @Override
    void debug(final CharSequence charSequence) {
        println charSequence
    }

    @Override
    void debug(final CharSequence charSequence, final Throwable throwable) {
        println charSequence

        StackTraceUtils.sanitize(throwable).printStackTrace()
    }

    @Override
    void debug(final Throwable throwable) {
        StackTraceUtils.sanitize(throwable).printStackTrace()
    }

    @Override
    boolean isInfoEnabled() {
        return true
    }

    @Override
    void info(final CharSequence charSequence) {
        println charSequence
    }

    @Override
    void info(final CharSequence charSequence, final Throwable throwable) {
        println charSequence

        StackTraceUtils.sanitize(throwable).printStackTrace()
    }

    @Override
    void info(final Throwable throwable) {
        StackTraceUtils.sanitize(throwable).printStackTrace()
    }

    @Override
    boolean isWarnEnabled() {
        return true
    }

    @Override
    void warn(final CharSequence charSequence) {
        println charSequence
    }

    @Override
    void warn(final CharSequence charSequence, final Throwable throwable) {
        println charSequence

        StackTraceUtils.sanitize(throwable).printStackTrace()
    }

    @Override
    void warn(final Throwable throwable) {
        StackTraceUtils.sanitize(throwable).printStackTrace()
    }

    @Override
    boolean isErrorEnabled() {
        return true
    }

    @Override
    void error(final CharSequence charSequence) {
        println charSequence
    }

    @Override
    void error(final CharSequence charSequence, final Throwable throwable) {
        println charSequence

        StackTraceUtils.sanitize(throwable).printStackTrace()
    }

    @Override
    void error(final Throwable throwable) {
        StackTraceUtils.sanitize(throwable).printStackTrace()
    }
}
