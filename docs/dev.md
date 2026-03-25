


How to use settings class:

Use anywhere (e.g. in your panel)
```java
// Read
MySettings settings = MySettings.getInstance(project);
String key = settings.getApiKey();

// Write (auto-saved by IntelliJ)
settings.setApiKey("new-key-123");
```

App-level (IDE-wide) settings
If you want settings shared across all projects, change to:
```java
//project level:

java@Service(Service.Level.APP)
public class MyAppSettings implements PersistentStateComponent<MyAppSettings.State> {

    public static MyAppSettings getInstance() {
        return ApplicationManager.getApplication().getService(MyAppSettings.class);
    }
    // rest is the same...
}
//And in plugin.xml:
//xml<applicationService serviceImplementation="com.example.myplugin.settings.MyAppSettings"/>
//        IntelliJ handles all serialization/deserialization automatically — settings survive IDE restarts with zero extra code.
```


```c
Storage options
@Storage value
Where it's saved
- "myPlugin.xml".idea/myPlugin.xml (project-level, can be git-committed)
- StoragePathMacros.WORKSPACE_FILE.idea/workspace.xml (per-user, not committed)
- (use @Service(Level.APP))Global IDE settings, shared across all projects
```