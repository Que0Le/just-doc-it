package org.jdi.plugin.settings;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import org.jdi.plugin.common.LoggerFactory;
import org.jdi.plugin.common.PluginLogger;
import org.jdi.plugin.common.PluginParams;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class NoteSettingsConfigurable implements Configurable {
    private static final PluginLogger LOG = LoggerFactory.getLogger(NoteSettingsConfigurable.class);

    private NoteSettingsComponent settingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Just Doc It: Notes";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return settingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsComponent = new NoteSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        NoteSettings settings = NoteSettings.getInstance();
        return !settingsComponent.getNotesRootFolder()
                .equals(settings.getNotesRootFolder());
    }

    @Override
    public void apply() {
        NoteSettings settings = NoteSettings.getInstance();
        settings.setNotesRootFolder(settingsComponent.getNotesRootFolder());
        LOG.info("Hez: " + settingsComponent.getNotesRootFolder());
        System.out.println("Hez: " + settingsComponent.getNotesRootFolder());
    }

    @Override
    public void reset() {
        NoteSettings settings = NoteSettings.getInstance();
        settingsComponent.setNotesRootFolder(settings.getNotesRootFolder());
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
