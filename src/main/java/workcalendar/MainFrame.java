package workcalendar;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static JPanel mainPanel = new JPanel(new GridLayout((int)2,1));
    public MainFrame() {
        SharedVariables shared = new SharedVariables();
        setSize(shared.screenWidth, shared.screenHeight);
        setLocation(shared.screenWidth -shared.screenWidth +50, shared.screenHeight -shared.screenHeight +50);
        setResizable(false);
        setTitle("Work Calendar 2023");
        MonthPanels panelMonth = new MonthPanels(2023);
        QueryFunctionPanels panelQuery = new QueryFunctionPanels();
        mainPanel.add(panelMonth);
        mainPanel.add(panelQuery);
        add(mainPanel);
    }
}
