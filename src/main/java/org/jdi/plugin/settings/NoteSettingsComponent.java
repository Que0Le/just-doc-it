package org.jdi.plugin.settings;


import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

public class NoteSettingsComponent {

    private final JPanel mainPanel;
    private final TextFieldWithBrowseButton notesRootFolderField = new TextFieldWithBrowseButton();

    public NoteSettingsComponent() {
        notesRootFolderField.addBrowseFolderListener(
                null,
                FileChooserDescriptorFactory
                        .createSingleFolderDescriptor()
                        .withTitle("Select Notes Root Folder")
                        .withDescription("Choose the root folder where your note sources are stored")
        );

        mainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(
                        new JBLabel("Notes root folder:"),
                        notesRootFolderField, 1, false
                ).addComponentFillVertically(new JPanel(), 0).getPanel();
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return notesRootFolderField.getTextField();
    }

    public String getNotesRootFolder() {
        return notesRootFolderField.getText().trim();
    }

    public void setNotesRootFolder(String path) {
        notesRootFolderField.setText(path);
    }
}
