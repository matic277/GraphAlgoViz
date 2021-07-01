package impl.panels.simulationPanels;

import core.ComponentDrawer;
import impl.*;
import impl.tools.Edge;
import impl.tools.Tools;
import impl.windows.ImportGraphWindow;
import impl.windows.SimulationWindow;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class MenuPanel extends JPanel {
    
    TopPanel parent;
    SimulationWindow simWindow;
    SimulationPanel simPanel;
    
    MyGraph graph;
    
    JButton importBtn;
    JButton clearBtn;
    JButton addNodeBtn;
    public static JButton nextBtn; // TODO: could these not be static
    public static JButton prevBtn;
    
    JButton pauseBtn;
    
    JSlider nodeRadSlider;
    JSlider edgeOpacitySlider;
    
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
        
        Dimension menuPanelSize = new Dimension(Tools.INITIAL_LEFT_MENU_WIDTH, parent.getHeight());
        this.setSize(menuPanelSize);
        this.setPreferredSize(menuPanelSize);
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        JPanel MAIN_PANEL = new JPanel();
        MAIN_PANEL.setOpaque(true);
        MAIN_PANEL.setBackground(Tools.GRAY3);
        MAIN_PANEL.setLayout(new BoxLayout(MAIN_PANEL, BoxLayout.Y_AXIS));
        this.add(MAIN_PANEL, BorderLayout.CENTER);
        
        JPanel graphOptionsPnl = new JPanel();
        graphOptionsPnl.setOpaque(false);
        graphOptionsPnl.setLayout(new BoxLayout(graphOptionsPnl, BoxLayout.Y_AXIS));
        graphOptionsPnl.add(getSeparator());
        MAIN_PANEL.add(graphOptionsPnl);
        
        JLabel graphOptionsTitle = new JLabel(" Graph options ");
        graphOptionsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        graphOptionsTitle.setFont(Tools.getBoldFont(16));
        graphOptionsPnl.add(graphOptionsTitle);
        graphOptionsPnl.add(getSeparator());
        
        importBtn = new JButton("Import graph");
        importBtn.setFont(Tools.getFont(14));
//        UIManager.getLookAndFeel().getDefaults().getFont()
        importBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        importBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE_WIDE);
        importBtn.setSize(Tools.MENU_BUTTON_SIZE_WIDE);
        importBtn.setMaximumSize(Tools.MENU_BUTTON_SIZE_WIDE);
        importBtn.setMinimumSize(Tools.MENU_BUTTON_SIZE_WIDE);
        importBtn.addActionListener(a -> {
            SwingUtilities.invokeLater(() -> new ImportGraphWindow(this.parent.getSimulationWindow()));
        });
        graphOptionsPnl.add(importBtn);
        
        clearBtn = new JButton("Clear graph");
        clearBtn.setFont(Tools.getFont(14));
        clearBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE_WIDE);
        clearBtn.setSize(Tools.MENU_BUTTON_SIZE_WIDE);
        clearBtn.setMaximumSize(Tools.MENU_BUTTON_SIZE_WIDE);
        clearBtn.setMinimumSize(Tools.MENU_BUTTON_SIZE_WIDE);
        clearBtn.addActionListener(a -> {
            this.graph.clearGraph();
            this.parent.getSimulationWindow().getAlgorithmController().assignTasks();
            AlgorithmController.totalStates = 1;
            AlgorithmController.currentStateIndex = 0;
            parent.getMainPanel().onNewGraphImport();
        });
        graphOptionsPnl.add(clearBtn);
        
        addNodeBtn = new JButton("Add node");
        addNodeBtn.setFont(Tools.getFont(14));
        addNodeBtn.setToolTipText("Adds a new node to graph");
        addNodeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addNodeBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE_WIDE);
        addNodeBtn.setSize(Tools.MENU_BUTTON_SIZE_WIDE);
        addNodeBtn.setMaximumSize(Tools.MENU_BUTTON_SIZE_WIDE);
        addNodeBtn.setMinimumSize(Tools.MENU_BUTTON_SIZE_WIDE);
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
            
            AlgorithmController.totalStates = AlgorithmController.currentStateIndex + 1;
            this.parent.getSimulationWindow().getMainPanel().getBottomPanel().getTabsPanel().getStateHistoryTab()
                    .setCurrentActiveState(AlgorithmController.currentStateIndex);
            this.parent.getSimulationWindow().getMainPanel().getBottomPanel().getTabsPanel().getStateHistoryTab()
                    .deleteFutureHistory();
            
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
            //this.parent.getSimulationWindow().getMainPanel().onNewGraphImport();
            System.out.println("new node added");
        });
        graphOptionsPnl.add(addNodeBtn);
        
        
        
        // HISTORY PANEL
        
        JPanel historyOptionsPnl = new JPanel();
        historyOptionsPnl.setOpaque(false);
        historyOptionsPnl.setLayout(new BoxLayout(historyOptionsPnl, BoxLayout.Y_AXIS));
        MAIN_PANEL.add(historyOptionsPnl);
        
        JLabel historyOptionsTitle = new JLabel(" History and simulation options ");
        historyOptionsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyOptionsTitle.setFont(Tools.getBoldFont(14));
        historyOptionsPnl.add(getSeparator());
        historyOptionsPnl.add(historyOptionsTitle);
        historyOptionsPnl.add(getSeparator());
        
        pauseBtn = new JButton("CONTINUE");
        pauseBtn.setFont(Tools.getFont(14));
        pauseBtn.setToolTipText("Pause or continue simulation.");
        pauseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        pauseBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE_WIDE);
        pauseBtn.setMaximumSize(Tools.MENU_BUTTON_SIZE_WIDE);
        pauseBtn.setMinimumSize(Tools.MENU_BUTTON_SIZE_WIDE);
        pauseBtn.setEnabled(false);
        pauseBtn.addActionListener(a -> {
            // Thread safe atomic boolean flip
            // flip the value of PAUSE
            boolean temp;
            do {
                temp = AlgorithmController.PAUSE.get();
            }
            while (!AlgorithmController.PAUSE.compareAndSet(temp, !temp));

            // disable/enable inform/uniform, delete node buttons
            this.simPanel.getPanelListener().informBtn.setEnabled(!this.simPanel.getPanelListener().informBtn.isEnabled());
            this.simPanel.getPanelListener().deleteNodeBtn.setEnabled(!this.simPanel.getPanelListener().deleteNodeBtn.isEnabled());
            // disable/enable add node button
            this.addNodeBtn.setEnabled(!this.addNodeBtn.isEnabled());
            // disable/enable next/previous state buttons
            nextBtn.setEnabled(AlgorithmController.PAUSE.get());
            prevBtn.setEnabled(AlgorithmController.PAUSE.get());

            this.importBtn.setEnabled(!this.importBtn.isEnabled());
            this.clearBtn.setEnabled(!this.clearBtn.isEnabled());

            // when pressing continue, jump to latest state
            // TODO
            //  program crash due to node getting drawn from  state 1 when only 1 state existed
            //  crashed on node.draw on line
            //  g.setColor(states.get(AlgorithmController.currentStateIndex).getState() == 0 ? UNINFORMED : INFORMED);
            //  (index 1 out of range of size of list 1)
            //  -> Happened once, can't reproduce.
            AlgorithmController.currentStateIndex = AlgorithmController.totalStates - 1;

            synchronized (AlgorithmController.PAUSE_LOCK) {
                AlgorithmController.PAUSE_LOCK.notify();
            }
            pauseBtn.setText(pauseBtn.getText().equals("CONTINUE") ? "PAUSE" : "CONTINUE");
        });
        historyOptionsPnl.add(pauseBtn);
        
        JPanel leftRightPnl = new JPanel();
        leftRightPnl.setOpaque(false);
        leftRightPnl.setLayout(new FlowLayout());
        historyOptionsPnl.add(leftRightPnl);
    
        prevBtn = new JButton("<");
        prevBtn.setFont(Tools.getFont(14));
        prevBtn.setToolTipText("Previous round");
        prevBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE);
        prevBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        prevBtn.setEnabled(false);
        prevBtn.addActionListener(a -> {
            AlgorithmController.currentStateIndex = Math.max(0, AlgorithmController.currentStateIndex - 1);
            parent.getMainPanel().getBottomPanel().getTabsPanel().getStateHistoryTab().setCurrentActiveState(AlgorithmController.currentStateIndex);
        });
        leftRightPnl.add(prevBtn);
        
        nextBtn = new JButton(">");
        nextBtn.setFont(Tools.getFont(14));
        nextBtn.setToolTipText("Next round");
        nextBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE);
        nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        leftRightPnl.add(nextBtn);
        leftRightPnl.setMaximumSize(new Dimension(150, 45)); // needed to limit height for some reason, or this component gets vertically stretched
    
    
        // DRAWING OPTIONS PANEL
        
        JPanel drawingOptionsPnl = new JPanel();
        drawingOptionsPnl.setOpaque(false);
        drawingOptionsPnl.setLayout(new BoxLayout(drawingOptionsPnl, BoxLayout.Y_AXIS));
        MAIN_PANEL.add(drawingOptionsPnl);
        
        JLabel drawingOptionsTitle = new JLabel(" Graph drawing options ");
        drawingOptionsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        drawingOptionsTitle.setFont(Tools.getBoldFont(14));
        drawingOptionsPnl.add(getSeparator());
        drawingOptionsPnl.add(drawingOptionsTitle);
        drawingOptionsPnl.add(getSeparator());
        
        JLabel sliderInfo = new JLabel("Change node radius");
        sliderInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderInfo.setSize(new Dimension(30, 100));
        sliderInfo.setFont(Tools.getFont(14));
        drawingOptionsPnl.add(sliderInfo);
        
        int sliderMin = 2, sliderMax = 100;
        nodeRadSlider = new JSlider(sliderMin, sliderMax, Node.rad);
        nodeRadSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        nodeRadSlider.setPreferredSize(new Dimension(150, 40));
        nodeRadSlider.setMaximumSize(new Dimension(150, 40));
        nodeRadSlider.setMinimumSize(new Dimension(150, 40));
        nodeRadSlider.setFont(Tools.getFont(14));
        nodeRadSlider.setEnabled(false);
        nodeRadSlider.addChangeListener(c -> {
            Node.rad = nodeRadSlider.getValue();
            graph.getNodes().forEach(n -> { n.width = nodeRadSlider.getValue(); n.height = nodeRadSlider.getValue(); });
        });
        drawingOptionsPnl.add(nodeRadSlider);
        
        
        JLabel sliderInfo2 = new JLabel("Change edge opacity");
        sliderInfo2.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderInfo2.setSize(new Dimension(30, 100));
        sliderInfo2.setFont(Tools.getFont(14));
        drawingOptionsPnl.add(sliderInfo2);
    
        int sliderMin2 = 0, sliderMax2 = 255;
        edgeOpacitySlider = new JSlider(sliderMin2, sliderMax2, Node.rad);
        edgeOpacitySlider.setValue(sliderMax2);
        edgeOpacitySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        Hashtable<Integer, JLabel> sliderMap2 = new Hashtable<>();
        Font lblFont2 = Tools.getFont(12);
        JLabel minLbl2 = new JLabel(sliderMin2+""); minLbl2.setFont(lblFont2);
        JLabel maxLbl2 = new JLabel(sliderMax2+""); maxLbl2.setFont(lblFont2);
        sliderMap2.put(sliderMin2, minLbl2);
        sliderMap2.put(sliderMax2, maxLbl2);
        edgeOpacitySlider.setLabelTable(sliderMap2);
        edgeOpacitySlider.setMajorTickSpacing(30);
        edgeOpacitySlider.setPaintTicks(true);
        edgeOpacitySlider.setPaintLabels(true);
        edgeOpacitySlider.setPreferredSize(new Dimension(150, 40));
        edgeOpacitySlider.setMaximumSize(new Dimension(150, 40));
        edgeOpacitySlider.setMinimumSize(new Dimension(150, 40));
        edgeOpacitySlider.setFont(Tools.getFont(14));
        edgeOpacitySlider.setEnabled(false);
        edgeOpacitySlider.addChangeListener(c -> Edge.opacity = edgeOpacitySlider.getValue());
        drawingOptionsPnl.add(edgeOpacitySlider);
        
        drawingOptionsPnl.add(getSeparator());
        
        JPanel chekcBoxPnl = new JPanel();
        chekcBoxPnl.setOpaque(false);
        chekcBoxPnl.setLayout(new BoxLayout(chekcBoxPnl, BoxLayout.Y_AXIS));
        chekcBoxPnl.setAlignmentX(Component.CENTER_ALIGNMENT);
        drawingOptionsPnl.add(chekcBoxPnl);
        
        idDrawerCheckBox = new JCheckBox("Draw node IDs"); // extra spaces so checkboxes are (almost!) aligned - flow layout sucks
        idDrawerCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        idDrawerCheckBox.setPreferredSize(Tools.MENU_CHECKBOX_SIZE);
        idDrawerCheckBox.setFont(Tools.getFont(14));
        idDrawerCheckBox.setEnabled(false);
        idDrawerCheckBox.addActionListener(a -> {
            Node.idDrawer = idDrawerCheckBox.isSelected() ?
                    ComponentDrawer.getIdDrawer() : ComponentDrawer.getNullDrawer();
        });
        chekcBoxPnl.add(idDrawerCheckBox);
        
        coordDrawerCheckBox = new JCheckBox("Draw node coordinates");
        coordDrawerCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
//        coordDrawerCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        coordDrawerCheckBox.setPreferredSize(Tools.MENU_CHECKBOX_SIZE);
        coordDrawerCheckBox.setFont(Tools.getFont(14));
        coordDrawerCheckBox.setEnabled(false);
        coordDrawerCheckBox.addActionListener(a -> {
            Node.coordDrawer = coordDrawerCheckBox.isSelected() ?
                    ComponentDrawer.getCoordDrawer() : ComponentDrawer.getNullDrawer();
        });
        chekcBoxPnl.add(coordDrawerCheckBox);
        
        edgeDrawerCheckBox = new JCheckBox("Draw edges");
        edgeDrawerCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        edgeDrawerCheckBox.setPreferredSize(Tools.MENU_CHECKBOX_SIZE);
        edgeDrawerCheckBox.setFont(Tools.getFont(14));
        edgeDrawerCheckBox.setSelected(true);
        edgeDrawerCheckBox.setEnabled(false);
        edgeDrawerCheckBox.addActionListener(a -> {
            graph.drawEdges(edgeDrawerCheckBox.isSelected());
        });
        chekcBoxPnl.add(edgeDrawerCheckBox);
        
        stateDebugCheckBox = new JCheckBox("Draw states (debug)");
        stateDebugCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        stateDebugCheckBox.setPreferredSize(Tools.MENU_CHECKBOX_SIZE);
        stateDebugCheckBox.setFont(Tools.getFont(14));
        stateDebugCheckBox.setSelected(false);
        stateDebugCheckBox.setEnabled(false);
        stateDebugCheckBox.addActionListener(a -> {
            Node.stateDebugDrawer = stateDebugCheckBox.isSelected() ?
                    ComponentDrawer.getStateDebugDrawer() : ComponentDrawer.getNullDrawer();
        });
        chekcBoxPnl.add(stateDebugCheckBox);
        
        neighborsDebugCheckBox = new JCheckBox("Draw node neighbors");
        neighborsDebugCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        neighborsDebugCheckBox.setPreferredSize(Tools.MENU_CHECKBOX_SIZE);
        neighborsDebugCheckBox.setFont(Tools.getFont(14));
        neighborsDebugCheckBox.setSelected(false);
        neighborsDebugCheckBox.setEnabled(false);
        neighborsDebugCheckBox.addActionListener(a -> {
            Node.neighborsDrawer = neighborsDebugCheckBox.isSelected() ?
                    ComponentDrawer.getNeighborsDrawer() : ComponentDrawer.getNullDrawer();
        });
        chekcBoxPnl.add(neighborsDebugCheckBox);
    }
    
    private JLabel getSeparator() {
        JLabel l = Tools.getDumyPlaceholder();
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        l.setText(" . : : : : : : .");
        l.setForeground(Color.darkGray);
        l.setOpaque(false);
        l.setPreferredSize(new Dimension(l.getWidth(), 10));
        return l;
    }
    
//    @Override
//    public void paintComponent(Graphics g) {
//        // anti-aliasing
//        Graphics2D gr = (Graphics2D) g;
//        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
////
////        gr.setColor(Tools.MEUN_COLORS);
////        gr.fillRect(0, 0, getWidth(), getHeight());
//    }
    
    public void onNewGraphImport() {
        if (graph.getNodes().isEmpty()) {
            pauseBtn.setEnabled(false);
            prevBtn.setEnabled(false);
            nextBtn.setEnabled(false);
    
            edgeOpacitySlider.setEnabled(false);
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
    
        edgeOpacitySlider.setEnabled(true);
        nodeRadSlider.setEnabled(true);
        idDrawerCheckBox.setEnabled(true);
        coordDrawerCheckBox.setEnabled(true);
        edgeDrawerCheckBox.setEnabled(true);
        stateDebugCheckBox.setEnabled(true);
        neighborsDebugCheckBox.setEnabled(true);
    }
}
