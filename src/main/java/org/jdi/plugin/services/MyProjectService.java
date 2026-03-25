package org.jdi.plugin.services;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Service(Service.Level.PROJECT)
public final class MyProjectService {

    private final Project project;

    // --- State / cache ---
    private boolean initialized = false;
    private String apiKey;
    private String baseUrl;
    private final List<String> cachedItems = new ArrayList<>();

    // --- Constructor (IntelliJ injects Project automatically) ---
    public MyProjectService(@NotNull Project project) {
        this.project = project;
    }

    // --- Singleton access ---
    public static MyProjectService getInstance(@NotNull Project project) {
        return project.getService(MyProjectService.class);
    }

    // --- Called from MyStartupActivity ---
    public void init(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;

        loadData();
        this.initialized = true;
    }

    // --- Business logic ---
    private void loadData() {
        // e.g. call an API, scan project files, load a cache
        cachedItems.clear();
        cachedItems.add("Item 1");
        cachedItems.add("Item 2");
        // or: fetchFromApi(apiKey, baseUrl);
    }

    public void refresh() {
        loadData();
    }

    public List<String> getItems() {
        return cachedItems;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public Project getProject() {
        return project;
    }
}