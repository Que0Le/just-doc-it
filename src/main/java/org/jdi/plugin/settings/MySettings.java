package org.jdi.plugin.settings;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "MyPluginSettings",
        storages = @Storage("myPlugin.xml")  // saved in .idea/myPlugin.xml
)
@Service(Service.Level.PROJECT)  // project-level settings
public final class MySettings implements PersistentStateComponent<MySettings.State> {

    // --- Inner state class (all fields are persisted) ---
    public static class State {
        public boolean enabled = true;
        public String apiKey = "";
        public String baseUrl = "https://api.example.com";
        public int maxResults = 10;
        public String outputDirectory = "";
    }

    private State state = new State();

    // --- Access singleton instance ---
    public static MySettings getInstance(@NotNull Project project) {
        return project.getService(MySettings.class);
    }

    // --- PersistentStateComponent interface ---
    @Override
    public @Nullable State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }

    // --- Convenience getters/setters ---
    public boolean isEnabled() { return state.enabled; }
    public void setEnabled(boolean enabled) { state.enabled = enabled; }

    public String getApiKey() { return state.apiKey; }
    public void setApiKey(String apiKey) { state.apiKey = apiKey; }

    public String getBaseUrl() { return state.baseUrl; }
    public void setBaseUrl(String url) { state.baseUrl = url; }

    public int getMaxResults() { return state.maxResults; }
    public void setMaxResults(int max) { state.maxResults = max; }

    public String getOutputDirectory() { return state.outputDirectory; }
    public void setOutputDirectory(String path) { state.outputDirectory = path; }
}