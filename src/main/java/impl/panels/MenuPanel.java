package impl.panels;

import impl.AlgorithmController;
import impl.Node;
import impl.tools.Tools;
import impl.windows.SimulationWindow;
import impl.MyButton;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class MenuPanel extends JPanel {
    
    Dimension panelSize;
    SimulationWindow parent;
    SimulationPanel simPanel;
    
    MyButton addNodeBtn;
    public static JButton nextBtn; // TODO: could these not be static
    public static JButton prevBtn;
    
    JLabel pauseInfo;
    MyButton pauseBtn;
    
    JLabel sliderInfo;
    JSlider nodeRadSlider;
    
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
        
        prevBtn = new JButton("<");
        prevBtn.setToolTipText("Previous round");
        prevBtn.setPreferredSize(Tools.menuButtonSize);
        this.add(prevBtn);
    
        nextBtn = new JButton(">");
        nextBtn.setToolTipText("Next round");
        nextBtn.setPreferredSize(Tools.menuButtonSize);
        nextBtn.addActionListener(a -> {
            // when button is pressed, it disables itself
            // when the round is finished, AlgoController
            // will enable it back on (same with prevBtn)
            nextBtn.setEnabled(false);
            prevBtn.setEnabled(false);
    
            // when pressing continue, jump to latest state
            // (just like pause/cont button)
            // TODO: ^
            //  if states of nodes have been altered, history(future, if any)
            //  must be deleted or handled somehow
            AlgorithmController.currentStateIndex = AlgorithmController.totalStates;
            
            synchronized (AlgorithmController.PAUSE_LOCK) {
                AlgorithmController.NEXT_ROUND_BUTTON_PRESSED.set(true);
                AlgorithmController.PAUSE_LOCK.notify();
            }
        });
        this.add(nextBtn);
        
        addNodeBtn = new MyButton("new node");
        addNodeBtn.setToolTipText("Add new node");
        addNodeBtn.setSize(Tools.menuButtonSize);
        
        addNodeBtn.addActionListener(a -> {
            // TODO:
            // thread safety
            // add node to some executioner to handle
            Node newNode = new Node(50, 50, simPanel.getGraph().getNextNodeId());
//            AlgorithmController.
            simPanel.getGraph().addNode(newNode);
            System.out.println("new node added");
        });
        this.add(addNodeBtn);
    
        // spacer
        this.add(Tools.getDumyPlaceholder());
        
        pauseInfo = new JLabel("Pause/continue simulation");
        pauseInfo.setSize(new Dimension(30, 100));
        pauseInfo.setFont(Tools.getFont(12));
        this.add(pauseInfo);
        
        pauseBtn = new MyButton("CONTINUE");
        pauseBtn.setToolTipText("Pause or continue simulation.");
        pauseBtn.setPreferredSize(Tools.wideMenuButtonSize);
        pauseBtn.addActionListener(a -> {
            // Thread safe atomic boolean flip
            // flip the value of PAUSE
            boolean temp;
            do { temp = AlgorithmController.PAUSE.get(); }
            while(!AlgorithmController.PAUSE.compareAndSet(temp, !temp));
            
            // disable/enable inform/uniform button
            this.simPanel.getPanelListener().innerInfoBtn.setEnabled(!this.simPanel.getPanelListener().innerInfoBtn.isEnabled());
            // disable/enable add node button
            this.addNodeBtn.setEnabled(!this.addNodeBtn.isEnabled());
            // disable/enable next/previous state buttons
            nextBtn.setEnabled(AlgorithmController.PAUSE.get());
            prevBtn.setEnabled(AlgorithmController.PAUSE.get());
            
            // when pressing continue, jump to latest state
            AlgorithmController.currentStateIndex = AlgorithmController.totalStates-1;
            
            synchronized (AlgorithmController.PAUSE_LOCK) {
                AlgorithmController.PAUSE_LOCK.notify();
            }
            pauseBtn.setText(pauseBtn.getText().equals("CONTINUE") ? "PAUSE" : "CONTINUE");
        });
        this.add(pauseBtn);
        
        // spacer
        this.add(Tools.getDumyPlaceholder());
        
        sliderInfo = new JLabel("Change node radius");
        sliderInfo.setSize(new Dimension(30, 100));
        sliderInfo.setFont(Tools.getFont(12));
        
        this.add(sliderInfo);
        
        
        int sliderMin = 5, sliderMax = 100;
        nodeRadSlider = new JSlider(5, 100, Node.rad);
        nodeRadSlider.addChangeListener(c -> {
            System.out.println(nodeRadSlider.getValue());
            Node.rad = nodeRadSlider.getValue();
        });
        Hashtable<Integer, JLabel> sliderMap = new Hashtable<>();
        Font lblFont = Tools.getFont(12);
        JLabel minLbl = new JLabel(sliderMin+""); minLbl.setFont(lblFont);
        JLabel maxLbl = new JLabel(sliderMax+""); maxLbl.setFont(lblFont);
        sliderMap.put(sliderMin, minLbl);
        sliderMap.put(sliderMax, maxLbl);
        nodeRadSlider.setLabelTable(sliderMap);
        nodeRadSlider.setMajorTickSpacing(30);
        nodeRadSlider.setPaintTicks(true);
        nodeRadSlider.setPaintLabels(true);
        nodeRadSlider.setPreferredSize(new Dimension(120, 40));
        nodeRadSlider.setFont(Tools.getFont(12));
        this.add(nodeRadSlider);
        
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
}
