package toolwindows;

import com.intellij.openapi.project.Project;
import javax.swing.*;
import java.awt.*;

public class MyToolWindowPanel extends JPanel {

    public MyToolWindowPanel(Project project) {
        setLayout(new BorderLayout());

        // Add your UI components here
        JLabel label = new JLabel("Hello from My Tool Window!");
        add(label, BorderLayout.CENTER);

        JButton button = new JButton("Click me");
        button.addActionListener(e -> {
            // handle action
        });
        add(button, BorderLayout.SOUTH);
    }
}