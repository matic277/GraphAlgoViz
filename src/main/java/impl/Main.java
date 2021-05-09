package impl;

import impl.windows.SimulationWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimulationWindow::new);
    }
}
