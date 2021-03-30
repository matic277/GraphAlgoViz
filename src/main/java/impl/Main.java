package impl;

import impl.windows.SimulationWindow;
import impl.windows.StartupWindow;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        
        // intro win
//        SwingUtilities.invokeLater(StartupWindow::new);
        
        // sim win
        SwingUtilities.invokeLater(() -> {
            new SimulationWindow(new Dimension(1000, 800));
        });
        
        
        // TESTS
//        JFrame f = new JFrame();
//        f.setLayout(new BorderLayout());
//        JPanel p1 = new JPanel();
//        JPanel p2 = new JPanel();
//        p1.setLayout(null);
//        p1.setSize(100, 100);
//        p1.setPreferredSize(new Dimension(500, 100));
////        p1.setMaximumSize(new Dimension(100, 200));
////        p1.setBounds(0,0,100,100);
//        p1.setOpaque(true);
//        p1.setBackground(Color.red);
//
//
//        p2.setLayout(null);
//        p2.setSize(100, 200);
//        p2.setPreferredSize(new Dimension(100, 100));
////        p2.setBounds(100,0,100,100);
//        p2.setOpaque(true);
//        p2.setBackground(Color.blue);
//
//        f.add(p1, BorderLayout.WEST); f.add(p2, BorderLayout.CENTER);
////        f.pack();
//        f.setVisible(true);
//        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
//        new Window1();
//
//        JFrame frame = new JFrame();
//        JPanel panel = new JPanel();
//        frame = new JFrame();
//        frame.setSize(600, 500);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
////        frame.setLayout(null);
//        frame.setVisible(true);
//
//        panel = new JPanel();
//        panel.setOpaque(true);
//        panel.setBackground(Color.black);
//        panel.setSize(500, 500);
////        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//        panel.setLayout(null);
//        frame.add(panel);
//
//        JLabel l = new JLabel("<html>Vizualizer</html>", SwingUtilities.CENTER);
//        l.setFont(new Font("Tahoma", Font.BOLD, 40));
//        l.setOpaque(true);
//        l.setBackground(Color.lightGray);
//        l.setVisible(true);
//        l.setBounds(100, 150, 400, 50);
//        panel.add(l);
//
//        JComboBox<String> c = new JComboBox<>(new String[]{"one", "two", "three"});
//        c.setOpaque(true);
//        c.setBounds(100,200, 150, 50);
//        c.setBackground(Color.red);
//        panel.add(c);
//
//        panel.updateUI();
    
    }
}
