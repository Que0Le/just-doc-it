package org.jdi.plugin.common;

public class DevLogger implements PluginLogger {

    private final String className;

    public DevLogger(Class<?> clazz) {
        this.className = clazz.getSimpleName();
    }

    private String location() {
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        if (st.length > 3) {
            StackTraceElement e = st[3];
            // matches IntelliJ's clickable stack trace pattern
            return "\tat " + e.getClassName() + "." + e.getMethodName()
                    + "(" + e.getFileName() + ":" + e.getLineNumber() + ")\n";
        }
        return "(Unknown)";
    }

    @Override
    public void info(String message) {
        System.out.println("[INFO]  [" + className + "] " + location() + " -> " + message);
    }

    @Override
    public void debug(String message) {
        System.out.println("[DEBUG] [" + className + "] " + location() + " -> " + message);
    }

    @Override
    public void warn(String message) {
        System.out.println("[WARN]  [" + className + "] " + location() + " - " + message);
    }

    @Override
    public void error(String message) {
        System.err.println("[ERROR] [" + className + "] " + location() + " - " + message);
    }

    @Override
    public void error(String message, Throwable t) {
        System.err.println("[ERROR] [" + className + "] " + location() + " - " + message);
        t.printStackTrace(System.err);
    }
}