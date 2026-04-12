
### Logging

Define log method in param. Use in each class:

```java
private static final PluginLogger LOG = LoggerFactory.getLogger(NoteSettingsConfigurable.class);
```
### etc

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


```text
For the tool window, we need a table view.
The window consisted of 3 main parts stack on each other: the search and navigate bar, the viewer area, and finally a thin
row at the bottom to act as a status bar.
The control area has a search area, a checkbox, a dropdown menu, and button. Fill some placeholder name and data in these elements.
View area has 2 table views stack on each other, top is called noteViewer and the other is pinnedNoteViewer.
Each viewer is a table view, each with id, brief, details. Each table entry can be expanded to show the rest of the meta data.
In the top viewer, user can right click on a note and choose to pin it, which will show this note in the pin area. 
Right click on the item in pinned viewer will show the menu to remove it from list.
in my opinion, the data source for each table viewer should be a separate subset of the note index arraylist.
```