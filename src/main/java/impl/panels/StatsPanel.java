package impl.panels;

import core.GraphChangeObserver;
import impl.MyGraph;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;

public class StatsPanel extends JScrollPane implements GraphChangeObserver {
    
    BottomPanel parent;
    
    Content content;
    MyGraph graph = MyGraph.getInstance();
    
    int informedNum, uninformedNum;
    double informedPercent;
    
    public StatsPanel(BottomPanel parent, Content content) {
        super(content);
        this.content = content;
        this.parent = parent;
        
        Dimension panelSize = new Dimension(Tools.INITIAL_STATS_PANEL_WIDTH, parent.getHeight());
        this.setSize(panelSize);
        this.setPreferredSize(panelSize);
        
        graph.addObserver(this);
        
        // so that this panel can be squished, hiding its components
        // otherwise components dictate smallest possible size
        this.setMinimumSize(new Dimension(0, 0));
    }
    
    private JLabel dummySeparator() {
        Dimension size = new Dimension(this.getWidth(), 20);
        JLabel lbl = new JLabel();
        lbl.setSize(size);
        lbl.setPreferredSize(size);
        lbl.setMinimumSize(size);
        lbl.setMaximumSize(size);
        lbl.setBackground(Color.black);
        lbl.setOpaque(true);
        return lbl;
    }
    
    public void onNewGraphImport() {
        // TODO
    }
    
    @Override
    public void onNodeAdded() {
        this.content.nodesNumLbl.setText("   " + this.graph.getNodes().size());
    }
    
    @Override
    public void onNodeDeleted() {
        this.content.nodesNumLbl.setText("   " + this.graph.getNodes().size());
        this.onEdgeAdded();
    }
    
    @Override
    public void onEdgeAdded() {
        this.content.edgesNumLbl.setText("   " + this.graph.getGraph().edgeSet().size());
    }
    
    @Override
    public void onNewInformedNode() {
        this.content.totalInformedNumLbl.setText("   " + this.graph.getNumberOfInformedNodes());
        this.content.percentInformedNumLbl.setText("   " + (int)(100*((double) this.graph.getNumberOfInformedNodes() / this.graph.numOfNodes)) + " %");
        System.out.println((long) this.graph.getNumberOfInformedNodes() + " / "+ this.graph.numOfNodes);
    }
    
    // TODO these methods are usless, rename and combine onNewUninformed & onNewInformed -> onInformedChange
    @Override
    public void onNewUninformedNode() {
//        informedNodes--;
//        this.content.totalInformedNumLbl.setText("   " + informedNodes);
    }
    
    @Override
    public void onGraphClear() {
        this.content.nodesNumLbl.setText("   0");
        this.content.edgesNumLbl.setText("   0");
        this.content.totalInformedNumLbl.setText("   " + this.graph.getNumberOfInformedNodes());
        this.content.percentInformedNumLbl.setText("   0 %");
    }
}
