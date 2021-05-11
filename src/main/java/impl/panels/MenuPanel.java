package impl.panels;

import core.ComponentDrawer;
import impl.*;
import impl.tools.Tools;
import impl.windows.ImportGraphWindow;
import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class MenuPanel extends JPanel {
    
    TopPanel parent;
//    Dimension panelSize;
    SimulationWindow simWindow;
    SimulationPanel simPanel;
    
    MyGraph graph;
    
    JButton importBtn;
    JButton clearBtn;
    JButton addNodeBtn;
    public static JButton nextBtn; // TODO: could these not be static
    public static JButton prevBtn;
    
    JLabel pauseInfo;
    JButton pauseBtn;
    
    JLabel sliderInfo;
    JSlider nodeRadSlider;
    
    JCheckBox idDrawerCheckBox;
    JCheckBox coordDrawerCheckBox;
    JCheckBox edgeDrawerCheckBox;
    JCheckBox stateDebugCheckBox;
    JCheckBox neighborsDebugCheckBox;
    
    public MenuPanel(TopPanel parent) {
        this.parent = parent;
        this.simWindow = parent.getSimulationWindow();
        this.simPanel  = parent.getSimulationPanel();
        this.graph = MyGraph.getInstance();
    
        // so that this panel can be squished, hiding its components
        // otherwise components dictate smallest possible size
        this.setMinimumSize(new Dimension(0, 0));
        
//        this.setPreferredSize(panelSize);
//        this.setSize(panelSize);
        Dimension menuPanelSize = new Dimension(Tools.INITIAL_LEFT_MENU_WIDTH, parent.getHeight());
        this.setSize(menuPanelSize);
        this.setPreferredSize(menuPanelSize);
        
        this.setLayout(new FlowLayout());
        this.setOpaque(true);
        this.setVisible(true);
        this.setBackground(Color.blue);
        
        importBtn = new JButton("Import graph");
        importBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE_WIDE);
        importBtn.addActionListener(a -> {
            SwingUtilities.invokeLater(() -> new ImportGraphWindow(this.parent.getSimulationWindow()));
        });
        this.add(importBtn);
    
        clearBtn = new JButton("Clear graph");
        clearBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE_WIDE);
        clearBtn.addActionListener(a -> {
            this.graph.clearGraph();
            this.parent.getSimulationWindow().getAlgorithmController().assignTasks();
            AlgorithmController.totalStates = 1;
            AlgorithmController.currentStateIndex = 0;
            parent.getMainPanel().onNewGraphImport();
        });
        this.add(clearBtn);
        
        
        // spacers
        this.add(Tools.getDumyPlaceholder());
        
        
        prevBtn = new JButton("<");
        prevBtn.setToolTipText("Previous round");
        prevBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE);
        prevBtn.setEnabled(false);
        prevBtn.addActionListener(a -> {
            AlgorithmController.currentStateIndex = Math.max(0, AlgorithmController.currentStateIndex - 1);
            parent.getMainPanel().getBottomPanel().getTabsPanel().getStateHistoryTab().setCurrentActiveState(AlgorithmController.currentStateIndex);
        });
        this.add(prevBtn);
    
        nextBtn = new JButton(">");
        nextBtn.setToolTipText("Next round");
        nextBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE);
        nextBtn.setEnabled(false);
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
            AlgorithmController.currentStateIndex = AlgorithmController.totalStates - 1;
            
            synchronized (AlgorithmController.PAUSE_LOCK) {
                AlgorithmController.NEXT_ROUND_BUTTON_PRESSED.set(true);
                AlgorithmController.PAUSE_LOCK.notify();
            }
        });
        this.add(nextBtn);
        
        addNodeBtn = new JButton("new node");
        addNodeBtn.setToolTipText("Add new node");
        addNodeBtn.setSize(Tools.MENU_BUTTON_SIZE);
        addNodeBtn.addActionListener(a -> {
//            Node newNode = new Node(50, 50, graph.getNextNodeId()); // TODO
            Node newNode = MyGraph.getNode();
            newNode.x = 50;
            newNode.y = 50;
            
            // when node is added in the middle of the simulation
            // prefill its history (state list) with uninformed states!
            // (one state is automatically made in constructor, so do 1 less)
            for (int i=0; i<AlgorithmController.currentStateIndex; i++) {
                newNode.addState(new State(0));
            }
            
            // clear future history
            AlgorithmController.totalStates = AlgorithmController.currentStateIndex + 1;
            this.parent.getSimulationWindow().getMainPanel().getBottomPanel().getTabsPanel().getStateHistoryTab()
                    .setCurrentActiveState(AlgorithmController.currentStateIndex);
            
            // TODO move this to some method in MyGraph, like onNodeRemoveOrAdd()
            //   same lambda is in deleteNodeBtn action method!
            // clear future history of states of nodes
            this.graph.getNodes().forEach(n -> {
                // optimization?
                // remove elements from back to middle
                // instead of from middle to the back
                for (int i=n.states.size()-1; i>=AlgorithmController.totalStates; i--) {
                    n.states.remove(i);
                }
            });
            
            // adding new node takes calling 2 separate methods
            // - not ideal, should refactor
            // Maybe AlgoCtrl should be subscribed (observer) to
            // graph, and graph should notify AlgoCtrl about new
            // node inserts?
            graph.addNode(newNode);
            simWindow.getAlgorithmController().addNewNode(newNode);
            
            // TODO
            //  calling onNewGraphImport is convient but not nice
            //  (buttons get enabled if the graph is not empty - and
            //  in this case it won't be since a node has just been added)
            this.parent.getSimulationWindow().getMainPanel().onNewGraphImport();
            System.out.println("new node added");
        });
        this.add(addNodeBtn);
        
        
        // spacer
        this.add(Tools.getDumyPlaceholder());
        
        
        pauseInfo = new JLabel("Pause/continue simulation");
        pauseInfo.setSize(new Dimension(30, 100));
        pauseInfo.setFont(Tools.getFont(12));
        this.add(pauseInfo);
        
        pauseBtn = new JButton("CONTINUE");
        pauseBtn.setToolTipText("Pause or continue simulation.");
        pauseBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE_WIDE);
        pauseBtn.setEnabled(false);
        pauseBtn.addActionListener(a -> {
            // Thread safe atomic boolean flip
            // flip the value of PAUSE
            boolean temp;
            do { temp = AlgorithmController.PAUSE.get(); }
            while(!AlgorithmController.PAUSE.compareAndSet(temp, !temp));
            
            // disable/enable inform/uniform, delete node buttons
            this.simPanel.getPanelListener().informBtn.setEnabled(!this.simPanel.getPanelListener().informBtn.isEnabled());
            this.simPanel.getPanelListener().deleteNodeBtn.setEnabled(!this.simPanel.getPanelListener().deleteNodeBtn.isEnabled());
            // disable/enable add node button
            this.addNodeBtn.setEnabled(!this.addNodeBtn.isEnabled());
            // disable/enable next/previous state buttons
            nextBtn.setEnabled(AlgorithmController.PAUSE.get());
            prevBtn.setEnabled(AlgorithmController.PAUSE.get());
            
            // when pressing continue, jump to latest state
            // TODO
            //  program crash due to node getting drawn from  state 1 when only 1 state existed
            //  crashed on node.draw on line
            //  g.setColor(states.get(AlgorithmController.currentStateIndex).getState() == 0 ? UNINFORMED : INFORMED);
            //  (index 1 out of range of size of list 1)
            //  -> Happened once, can't reproduce.
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
        nodeRadSlider.setEnabled(false);
        nodeRadSlider.addChangeListener(c -> Node.rad = nodeRadSlider.getValue());
        this.add(nodeRadSlider);
        
        
        this.add(Tools.getDumyPlaceholder());
        
        
        idDrawerCheckBox = new JCheckBox("Draw node IDs     "); // extra spaces so checkboxes are (almost!) aligned - flow layout sucks
        idDrawerCheckBox.setPreferredSize(Tools.MENU_CHECKBOX_SIZE);
        idDrawerCheckBox.setFont(Tools.getFont(12));
        idDrawerCheckBox.setEnabled(false);
        idDrawerCheckBox.addActionListener(a -> {
            Node.idDrawer = idDrawerCheckBox.isSelected() ?
                    ComponentDrawer.getIdDrawer() : ComponentDrawer.getNullDrawer();
        });
        this.add(idDrawerCheckBox);
        
        coordDrawerCheckBox = new JCheckBox("Draw node coords");
        coordDrawerCheckBox.setPreferredSize(Tools.MENU_CHECKBOX_SIZE);
        coordDrawerCheckBox.setFont(Tools.getFont(12));
        coordDrawerCheckBox.setEnabled(false);
        coordDrawerCheckBox.addActionListener(a -> {
            Node.coordDrawer = coordDrawerCheckBox.isSelected() ?
                    ComponentDrawer.getCoordDrawer() : ComponentDrawer.getNullDrawer();
        });
        this.add(coordDrawerCheckBox);
        
        edgeDrawerCheckBox = new JCheckBox("Draw edges");
        edgeDrawerCheckBox.setPreferredSize(Tools.MENU_CHECKBOX_SIZE);
        edgeDrawerCheckBox.setFont(Tools.getFont(12));
        edgeDrawerCheckBox.setSelected(true);
        edgeDrawerCheckBox.setEnabled(false);
        edgeDrawerCheckBox.addActionListener(a -> {
            graph.drawEdges(edgeDrawerCheckBox.isSelected());
        });
        this.add(edgeDrawerCheckBox);
        
        stateDebugCheckBox = new JCheckBox("Draw states (debug)");
        stateDebugCheckBox.setPreferredSize(Tools.MENU_CHECKBOX_SIZE);
        stateDebugCheckBox.setFont(Tools.getFont(12));
        stateDebugCheckBox.setSelected(false);
        stateDebugCheckBox.setEnabled(false);
        stateDebugCheckBox.addActionListener(a -> {
            Node.stateDebugDrawer = stateDebugCheckBox.isSelected() ?
                    ComponentDrawer.getStateDebugDrawer() : ComponentDrawer.getNullDrawer();
        });
        this.add(stateDebugCheckBox);
        
        neighborsDebugCheckBox = new JCheckBox("Draw node neighbors");
        neighborsDebugCheckBox.setPreferredSize(Tools.MENU_CHECKBOX_SIZE);
        neighborsDebugCheckBox.setFont(Tools.getFont(12));
        neighborsDebugCheckBox.setSelected(false);
        neighborsDebugCheckBox.setEnabled(false);
        neighborsDebugCheckBox.addActionListener(a -> {
            Node.neighborsDrawer = neighborsDebugCheckBox.isSelected() ?
                    ComponentDrawer.getNeighborsDrawer() : ComponentDrawer.getNullDrawer();
        });
        this.add(neighborsDebugCheckBox);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // anti-aliasing
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        gr.setColor(Tools.MEUN_COLORS);
        gr.fillRect(0, 0, getWidth(), getHeight());
    }
    
    public void onNewGraphImport() {
        if (graph.getNodes().isEmpty()) {
            pauseBtn.setEnabled(false);
            prevBtn.setEnabled(false);
            nextBtn.setEnabled(false);
    
            nodeRadSlider.setEnabled(false);
            idDrawerCheckBox.setEnabled(false);
            coordDrawerCheckBox.setEnabled(false);
            edgeDrawerCheckBox.setEnabled(false);
            stateDebugCheckBox.setEnabled(false);
            neighborsDebugCheckBox.setEnabled(false);
            return;
        }
        
        pauseBtn.setEnabled(true);
        prevBtn.setEnabled(true);
        nextBtn.setEnabled(true);
        
        nodeRadSlider.setEnabled(true);
        idDrawerCheckBox.setEnabled(true);
        coordDrawerCheckBox.setEnabled(true);
        edgeDrawerCheckBox.setEnabled(true);
        stateDebugCheckBox.setEnabled(true);
        neighborsDebugCheckBox.setEnabled(true);
    }
}
