package impl.panels.simulationPanels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import core.GraphChangeObserver;
import impl.*;
import impl.tools.Tools;
import impl.windows.ImportGraphWindow;
import impl.windows.SimulationWindow;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel implements GraphChangeObserver {
    
    TopPanel parent;
    SimulationWindow simWindow;
    SimulationPanel simPanel;
    
    MyGraph graph;
    
    FlatButton importBtn;
    FlatButton clearBtn;
    FlatButton addNodeBtn;
    public static FlatButton nextBtn; // TODO: could these not be static
    public static FlatButton prevBtn;
    
    FlatButton pauseBtn;
    
    FlatSVGIcon playIcon;
    FlatSVGIcon pauseIcon;
    
    public MenuPanel(TopPanel parent) {
        this.parent = parent;
        this.simWindow = parent.getSimulationWindow();
        this.simPanel  = parent.getSimulationPanel();
        this.graph = MyGraph.getInstance();
        this.graph.addObserver(this);
        
        // so that this panel can be squished, hiding its components
        // otherwise components dictate smallest possible size
        //this.setMinimumSize(new Dimension(0, 0));
        
        //Dimension menuPanelSize = new Dimension(Tools.INITIAL_LEFT_MENU_WIDTH, parent.getHeight());
        //this.setSize(menuPanelSize);
        //this.setPreferredSize(menuPanelSize);
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        JPanel MAIN_PANEL = new JPanel();
        MAIN_PANEL.setOpaque(true);
        //MAIN_PANEL.setBackground(Tools.GRAY3);
        MAIN_PANEL.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.add(MAIN_PANEL, BorderLayout.CENTER);
        
        this.setBorder(Tools.UI_BORDER_STANDARD);
        
        Dimension iconSize = new Dimension(20, 20);
        
        FlatSVGIcon nextBtnIcon     = new FlatSVGIcon("icons/flatlaf/forward.svg")                .derive(iconSize.width, iconSize.height);
        FlatSVGIcon prevBtnIcon     = new FlatSVGIcon("icons/flatlaf/back.svg")                   .derive(iconSize.width, iconSize.height);
        FlatSVGIcon importIcon      = new FlatSVGIcon("icons/flatlaf/FileChooserUpFolderIcon.svg").derive(iconSize.width, iconSize.height);
        FlatSVGIcon clearGraphIcon  = new FlatSVGIcon("icons/flaticons/trash3.svg")               .derive(iconSize.width, iconSize.height);
        FlatSVGIcon addNodeIcon     = new FlatSVGIcon("icons/flatlaf/add.svg")                    .derive(iconSize.width, iconSize.height);
        playIcon                    = new FlatSVGIcon("icons/flatlaf/execute.svg")                .derive(iconSize.width, iconSize.height);
        pauseIcon                   = new FlatSVGIcon("icons/flatlaf/suspend.svg")                .derive(iconSize.width, iconSize.height);
        
        importBtn = new FlatButton();
        importBtn.setIcon(importIcon);
        importBtn.setBorderPainted(false);
        importBtn.setToolTipText("Import graph");
        //importBtn.setFont(Tools.getFont(14));
//        UIManager.getLookAndFeel().getDefaults().getFont()
//        importBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        //importBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE_WIDE);
        importBtn.setSize(Tools.MENU_BUTTON_SIZE_WIDE);
        //importBtn.setMaximumSize(Tools.MENU_BUTTON_SIZE_WIDE);
        //importBtn.setMinimumSize(Tools.MENU_BUTTON_SIZE_WIDE);
        importBtn.addActionListener(a -> {
            SwingUtilities.invokeLater(() -> new ImportGraphWindow(this.parent.getSimulationWindow()));
        });
        MAIN_PANEL.add(importBtn);
        importBtn.setPreferredSize(new Dimension(iconSize.width+10, iconSize.height+10));
        //importBtn.setPreferredSize(iconSize);
        
        clearBtn = new FlatButton();
        clearBtn.setIcon(clearGraphIcon);
        clearBtn.setBorderPainted(false);
        clearBtn.setToolTipText("Clear graph");
        clearBtn.addActionListener(a -> this.graph.clearGraph());
        MAIN_PANEL.add(clearBtn);
        
        
        addNodeBtn = new FlatButton();
        addNodeBtn.setIcon(addNodeIcon);
        addNodeBtn.setBorderPainted(false);
        addNodeBtn.setToolTipText("Adds a new node to graph");
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
            
            // TODO move this to some method in MyGraph, like onNodeRemoveOrAdd()
            //   same lambda is in deleteNodeBtn action method!
            // clear future history of states of nodes
            this.graph.getNodes().forEach(n -> {
                if (n.states.size() > AlgorithmController.totalStates) {
                    n.states.subList(AlgorithmController.totalStates, n.states.size()).clear();
                }
            });
            
            graph.addNode(newNode);
        });
        MAIN_PANEL.add(addNodeBtn);
        
        
        // separator
        MAIN_PANEL.add(new JLabel("   "));
        
        
        pauseBtn = new FlatButton();
        pauseBtn.setIcon(playIcon);
        pauseBtn.setBorderPainted(false);
        pauseBtn.setToolTipText("Start or stop simulation.");
        //pauseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        //pauseBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE_WIDE);
        //pauseBtn.setMaximumSize(Tools.MENU_BUTTON_SIZE_WIDE);
        //pauseBtn.setMinimumSize(Tools.MENU_BUTTON_SIZE_WIDE);
        pauseBtn.setEnabled(false);
        var playOrPause = new Object() { boolean play = false; };
        pauseBtn.addActionListener(a -> {
            // Thread safe atomic boolean flip
            // flip the value of PAUSE
            boolean temp;
            do { temp = AlgorithmController.PAUSE.get(); }
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
            pauseBtn.setIcon(AlgorithmController.PAUSE.get() ? playIcon : pauseIcon);
            //playOrPause.play = !playOrPause.play;
        });
        MAIN_PANEL.add(pauseBtn);
        
        
        prevBtn = new FlatButton();
        prevBtn.setIcon(prevBtnIcon);
        prevBtn.setBorderPainted(false);
        prevBtn.setToolTipText("Previous round");
        //prevBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE);
        prevBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        prevBtn.setEnabled(false);
        prevBtn.addActionListener(a -> {
            AlgorithmController.currentStateIndex = Math.max(0, AlgorithmController.currentStateIndex - 1);
            parent.getMainPanel().getBottomPanel().getTabsPanel().getStateHistoryTab().setCurrentActiveState(AlgorithmController.currentStateIndex);
        });
        MAIN_PANEL.add(prevBtn);
        
        nextBtn = new FlatButton();
        nextBtn.setIcon(nextBtnIcon);
        nextBtn.setBorderPainted(false);
        nextBtn.setToolTipText("Next round");
        //nextBtn.setPreferredSize(Tools.MENU_BUTTON_SIZE);
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
        MAIN_PANEL.add(nextBtn);
        //leftRightPnl.setMaximumSize(new Dimension(150, 45)); // needed to limit height for some reason, or this component gets vertically stretched
        
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
    
    
    @Override
    public void onGraphClear() {
        pauseBtn.setEnabled(false);
        prevBtn.setEnabled(false);
        nextBtn.setEnabled(false);
    }
    
    @Override
    public void onGraphImport() {
        // if imported graph is empty then we shouldn't enable buttons...
        if (graph.getGraph().vertexSet().isEmpty()) return;
        
        pauseBtn.setEnabled(true);
        prevBtn.setEnabled(true);
        nextBtn.setEnabled(true);
    }
    
    @Override public void onNewInformedNode() {}
    @Override public void onNewUninformedNode() {}
    @Override public void edgeAdded(GraphEdgeChangeEvent<Node, DefaultEdge> e) {}
    @Override public void edgeRemoved(GraphEdgeChangeEvent<Node, DefaultEdge> e) {}
    @Override public void vertexAdded(GraphVertexChangeEvent<Node> e) {}
    @Override public void vertexRemoved(GraphVertexChangeEvent<Node> e) {}
}
