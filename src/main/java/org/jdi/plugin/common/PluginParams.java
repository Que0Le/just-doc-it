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
        private Logging() {

        }
    }
}
