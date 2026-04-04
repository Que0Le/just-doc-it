package org.jdi.plugin.common;

public class PluginParams {
    private PluginParams() {
        throw new UnsupportedOperationException("PluginParams is a utility class.");
    }

    public static final class App {
        private App() {
        }

        public static final String NAME = "Just Doc It!";
    }

    public static final class Logging {

        public enum LoggerMode {
            DEV,        // custom DevLogger: prints to Run console via sout, with location info
            INTELLIJ    // IntelliJ Logger: writes to idea.log, clickable in host IDE
        }

        public static final LoggerMode LOGGER_MODE = LoggerMode.DEV;

        private Logging() {

        }
    }
}
