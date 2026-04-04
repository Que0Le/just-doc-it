package org.jdi.plugin.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MySettingsConfigurable implements Configurable {

    private final Project project;
    private TextFieldWithBrowseButton outputDirectoryField;

    public MySettingsConfigurable(Project project) {
        this.project = project;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Just Doc It";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        outputDirectoryField = new TextFieldWithBrowseButton();
        outputDirectoryField.addBrowseFolderListener(
                project,
                FileChooserDescriptorFactory.createSingleFolderDescriptor()
                        .withTitle("Select Output Directory")
                        .withDescription("Choose the directory where documentation will be generated")
        );

        return FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Output directory:"), outputDirectoryField, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    @Override
    public boolean isModified() {
        MySettings settings = MySettings.getInstance(project);
        return !outputDirectoryField.getText().equals(settings.getOutputDirectory());
    }

    @Override
    public void apply() {
        MySettings settings = MySettings.getInstance(project);
        settings.setOutputDirectory(outputDirectoryField.getText());
    }

    @Override
    public void reset() {
        MySettings settings = MySettings.getInstance(project);
        outputDirectoryField.setText(settings.getOutputDirectory());
    }
}
