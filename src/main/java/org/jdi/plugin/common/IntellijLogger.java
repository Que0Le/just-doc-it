package org.jdi.plugin.common;

import com.intellij.openapi.diagnostic.Logger;

public class IntellijLogger implements PluginLogger {

    private final Logger log;

    public IntellijLogger(Class<?> clazz) {
        this.log = Logger.getInstance(clazz);
    }

    @Override
    public void info(String message) {
        log.info(message);
    }

    @Override
    public void debug(String message) {
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }

    @Override
    public void warn(String message) {
        log.warn(message);
    }

    @Override
    public void error(String message) {
        log.error(message);
    }

    @Override
    public void error(String message, Throwable t) {
        log.error(message, t);
    }
}