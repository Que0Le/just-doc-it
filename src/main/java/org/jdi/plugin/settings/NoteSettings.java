package org.jdi.plugin.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "org.jdi.plugin.settings.NoteSettings",
        storages = @Storage("JdiNoteSettings.xml")
)
public class NoteSettings implements PersistentStateComponent<NoteSettings.State> {

    public static class State {
        public String notesRootFolder = "";
    }

    private State myState = new State();

    public static NoteSettings getInstance() {
        return ApplicationManager.getApplication().getService(NoteSettings.class);
    }

    @Nullable
    @Override
    public State getState() {
        return myState;
    }

    @Override
    public void loadState(@NotNull State state) {
        myState = state;
    }

    public String getNotesRootFolder() {
        return myState.notesRootFolder;
    }

    public void setNotesRootFolder(String path) {
        myState.notesRootFolder = path;
    }
}