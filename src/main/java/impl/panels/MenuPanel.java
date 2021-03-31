package impl.panels;

import impl.Node;
import impl.tools.Tools;
import impl.windows.SimulationWindow;
import impl.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MenuPanel extends JPanel {
    
    Dimension panelSize;
    SimulationWindow parent;
    SimulationPanel simPanel;
    
    Button addNodeBtn;
    JButton undoBtn;
    JButton redoBtn;
    
    public MenuPanel(SimulationWindow parent, SimulationPanel simPanel, Dimension panelSize) {
        this.parent = parent;
        this.panelSize = panelSize;
        this.simPanel = simPanel;
        
        this.setPreferredSize(panelSize);
        this.setSize(panelSize);
        this.setLayout(new FlowLayout());
        this.setOpaque(true);
        this.setVisible(true);
        this.setBackground(Color.blue);
    
        undoBtn = new JButton("<");
        undoBtn.setToolTipText("Undo");
        undoBtn.setPreferredSize(Tools.menuButtonSize);
        this.add(undoBtn);
    
        redoBtn = new JButton(">");
        redoBtn.setToolTipText("Redo");
        redoBtn.setPreferredSize(Tools.menuButtonSize);
        this.add(redoBtn);
        
        // spacer
        this.add(Tools.getDumyPlaceholder());
        
        addNodeBtn = new Button("new node");
        addNodeBtn.setToolTipText("Add new node");
//        addNodeBtn.setMargin(new Insets(-0,-10,0,0));
//        addNodeBtn.setPreferredSize(Tools.menuButtonSize);
//        addNodeBtn.setSize(Tools.menuButtonSize);
        addNodeBtn.setOnClickAction(() -> {
            simPanel.addNode(new Node(50, 50, 5));
        });
        this.add(addNodeBtn);
        
        
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
