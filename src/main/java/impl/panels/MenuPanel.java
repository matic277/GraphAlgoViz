package impl.panels;

import impl.tools.Tools;
import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MenuPanel extends JPanel {
    
    Dimension panelSize;
    SimulationWindow parent;
    
    public MenuPanel(SimulationWindow parent, Dimension panelSize) {
        this.parent = parent;
        this.panelSize = panelSize;
        
        this.setPreferredSize(panelSize);
        this.setSize(panelSize);
        this.setLayout(new FlowLayout());
        this.setOpaque(true);
        this.setVisible(true);
        this.setBackground(Color.blue);
        
        
        JButton b1 = new JButton("B1");
        b1.setPreferredSize(new Dimension(100, 50));
        this.add(b1);
        JButton b2 = new JButton("B2");
        this.add(b2);
        JButton b3 = new JButton("B3");
        this.add(b3);
        JButton b4 = new JButton("B4");
        this.add(b4);
        JButton b5 = new JButton("B5");
        this.add(b5);
        
//        Listener l = new Listener();
//        this.addMouseMotionListener(l);
//        this.addMouseListener(l);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // anti-aliasing
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        gr.setColor(Color.ORANGE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        
        gr.setColor(Color.BLACK);
        gr.drawString("MENU", 10,20);
//        gr.drawRect(0, 0, this.getWidth(), this.getHeight());
//        System.out.println(this.getBounds());
        
        Tools.sleep(1000/60);
        super.repaint();
    }
    
    class Listener implements MouseListener, MouseMotionListener {
        
        @Override
        public void mouseClicked(MouseEvent e) {
        }
    
        @Override
        public void mousePressed(MouseEvent e) {
        }
    
        @Override
        public void mouseReleased(MouseEvent e) {
        }
    
        @Override
        public void mouseEntered(MouseEvent e) {
        }
    
        @Override
        public void mouseExited(MouseEvent e) {
        }
    
        @Override
        public void mouseDragged(MouseEvent e) {
        }
    
        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }
}
