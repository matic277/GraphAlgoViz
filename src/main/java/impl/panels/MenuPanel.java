package impl.panels;

import impl.AlgorithmController;
import impl.Node;
import impl.tools.Tools;
import impl.windows.SimulationWindow;
import impl.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MenuPanel extends JPanel {
    
    Dimension panelSize;
    SimulationWindow parent;
    SimulationPanel simPanel;
    
    MyButton addNodeBtn;
    JButton undoBtn;
    JButton redoBtn;
    
    MyButton pauseBtn;
    
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
        
        // spacers
        this.add(Tools.getDumyPlaceholder());
        
        undoBtn = new JButton("<");
        undoBtn.setToolTipText("Undo");
        undoBtn.setPreferredSize(Tools.menuButtonSize);
        this.add(undoBtn);
        
        redoBtn = new JButton(">");
        redoBtn.setToolTipText("Redo");
        redoBtn.setPreferredSize(Tools.menuButtonSize);
        this.add(redoBtn);
        
        addNodeBtn = new MyButton("new node");
        addNodeBtn.setToolTipText("Add new node");
        addNodeBtn.setSize(Tools.menuButtonSize);
        
        final var ref = new Object() {
            int idCounter = 5;
        };
        addNodeBtn.setOnClickAction(() -> {
            simPanel.getGraph().addNode(new Node(50, 50, ref.idCounter++));
//            simPanel.repaint();
        });
        this.add(addNodeBtn);
    
        // spacer
        this.add(Tools.getDumyPlaceholder());
    
        pauseBtn = new MyButton("CONTINUE");
        pauseBtn.setToolTipText("Pause or continue simulation.");
        pauseBtn.setPreferredSize(Tools.wideMenuButtonSize);
        pauseBtn.addActionListener(a -> {
            AlgorithmController.PAUSE = !AlgorithmController.PAUSE;
            synchronized (AlgorithmController.PAUSE_LOCK) {
                AlgorithmController.PAUSE_LOCK.notify();
            }
            pauseBtn.setText(pauseBtn.getText().equals("CONTINUE") ? "PAUSE" : "CONTINUE");
        });
        this.add(pauseBtn);
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
        gr.drawString("MENU", getWidth()/2, getHeight()/2);
//        gr.drawRect(0, 0, this.getWidth(), this.getHeight());
//        System.out.println(this.getBounds());
        
        Tools.sleep(1000/144);
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
