package org.jdi.plugin.startup;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jdi.plugin.common.PluginParams;
import org.jdi.plugin.services.MyProjectService;
import org.jdi.plugin.settings.MySettings;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyStartupActivity implements ProjectActivity {

    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {

        // 1. Load/initialize persistent settings
        MySettings settings = MySettings.getInstance(project);

        if (settings.isEnabled()) {
            String apiKey = settings.getApiKey();
            String baseUrl = settings.getBaseUrl();

            // use settings to initialize your service...
            MyProjectService service = project.getService(MyProjectService.class);
            service.init(apiKey, baseUrl);
        }

        // 2. Scan project files on startup
        VirtualFile baseDir = ProjectUtil.guessProjectDir(project);
        // scan files, index them, etc.

        // 3. Start a background task
        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Initializing...") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                indicator.setText("Loading data...");
                // heavy work here, won't block UI
            }
        });

        // 4. Show a notification
        NotificationGroupManager.getInstance()
                .getNotificationGroup("JustDocIt")
                .createNotification(String.format("Plugin '%s' ready!", PluginParams.App.NAME), NotificationType.INFORMATION)
                .notify(project);

        // 5. Register file listeners
        project.getMessageBus().connect().subscribe(
                VirtualFileManager.VFS_CHANGES,
                new BulkFileListener() {
                    @Override
                    public void after(@NotNull List<? extends VFileEvent> events) {
                        // react to file changes
                    }
                }
        );

        // 6. Open tool window
        ToolWindowManager.getInstance(project).invokeLater(() -> {
            ToolWindow tw = ToolWindowManager.getInstance(project).getToolWindow("My Tool Window");
            if (tw != null) tw.show();
        });

        return null;
    }
}