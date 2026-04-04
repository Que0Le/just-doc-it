package org.jdi.plugin.common;

public class LoggerFactory {

    public static PluginLogger getLogger(Class<?> clazz) {
        switch (PluginParams.Logging.LOGGER_MODE) {
            case DEV:      return new DevLogger(clazz);
            case INTELLIJ: return new IntellijLogger(clazz);
            default:       return new IntellijLogger(clazz);
        }
    }
}