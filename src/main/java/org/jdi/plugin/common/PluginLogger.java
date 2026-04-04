package org.jdi.plugin.common;

public interface PluginLogger {
    void info(String message);
    void debug(String message);
    void warn(String message);
    void error(String message);
    void error(String message, Throwable t);
}