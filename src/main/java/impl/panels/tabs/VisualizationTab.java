package impl.panels.tabs;

import core.ComponentDrawer;
import core.GraphChangeObserver;
import impl.MyGraph;
import impl.Node;
import impl.tools.Edge;
import impl.tools.Tools;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.util.Hashtable;

public class VisualizationTab extends JPanel implements GraphChangeObserver {
    
    private final String TAB_NAME = "Visualization options";
    
    TabsPanel parent;
    MyGraph graph;
    
    JSlider nodeRadSlider;
    JSlider edgeOpacitySlider;
    
    JCheckBox idDrawerCheckBox;
    JCheckBox coordDrawerCheckBox;
    JCheckBox edgeDrawerCheckBox;
    JCheckBox stateDebugCheckBox;
    JCheckBox neighborsDebugCheckBox;
    
    public VisualizationTab(TabsPanel parent) {
        this.parent = parent;
        this.graph = MyGraph.getInstance();
        this.graph.addObserver(this);
        
        this.setOpaque(true);
        //this.setBackground(Tools.GRAY3);
//        WrapLayout layout = new WrapLayout(WrapLayout.LEFT);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JPanel selectedColorInfoContainer = new JPanel();
        selectedColorInfoContainer.setLayout(new BorderLayout());
        selectedColorInfoContainer.setOpaque(true);
        
        JLabel selectedColorTitle = new JLabel("Selected color");
        selectedColorTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel selectedColorContainer = new JPanel();
        selectedColorContainer.setLayout(new BorderLayout());
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(3, 10));
        JLabel spacer2 = new JLabel();
        spacer2.setPreferredSize(new Dimension(3, 10));
        selectedColorContainer.add(spacer, BorderLayout.EAST);
        selectedColorContainer.add(spacer2, BorderLayout.WEST);
        
        JLabel selectedColorInfo = new JLabel();
        selectedColorInfo.setOpaque(true);
        selectedColorInfo.setBorder(new LineBorder(Tools.UI_BORDER_COLOR_STANDARD, 1));
        selectedColorInfo.setPreferredSize(new Dimension(100, 112));
        selectedColorInfo.setMaximumSize(new Dimension(50, 112));
        
        selectedColorContainer.add(selectedColorInfo, BorderLayout.CENTER);
        selectedColorInfoContainer.add(selectedColorTitle, BorderLayout.NORTH);
        selectedColorInfoContainer.add(selectedColorContainer, BorderLayout.CENTER);
        
        // remove all panels except for second (indexed as 1)
        JColorChooser color = new JColorChooser();
        AbstractColorChooserPanel[] panels = color.getChooserPanels();
        for (int i=0; i<panels.length; i++) {
            if (i != 1) color.removeChooserPanel(panels[i]);
        }
        AbstractColorChooserPanel colorPanel = panels[1];
        colorPanel.setBorder(Tools.UI_BORDER_STANDARD);
        
        colorPanel.getColorSelectionModel().addChangeListener(c -> {
            selectedColorInfo.setBackground(colorPanel.getColorSelectionModel().getSelectedColor());
            this.repaint(); // needed otherwise selectedColorInfo gets a little glitchy
        });
        
        JButton setInformedClrBtn = new JButton("Set color as informed");
        setInformedClrBtn.addActionListener(a -> {
            Node.INFORMED_COLOR = colorPanel.getColorSelectionModel().getSelectedColor();
        });
        JButton setUninformedClrBtn = new JButton("Set color as uninformed");
        setUninformedClrBtn.addActionListener(a -> {
            Node.UNINFORMED_COLOR = colorPanel.getColorSelectionModel().getSelectedColor();
        });
        JButton setEdgeClrBtn = new JButton("Set color as edge color");
        setEdgeClrBtn.addActionListener(a -> {
            Edge.EDGE_COLOR = colorPanel.getColorSelectionModel().getSelectedColor();
        });
        
        JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new BorderLayout());
        btnContainer.setOpaque(true);
        btnContainer.add(setInformedClrBtn, BorderLayout.NORTH);
        btnContainer.add(setUninformedClrBtn, BorderLayout.CENTER);
        btnContainer.add(setEdgeClrBtn, BorderLayout.SOUTH);
        
        JPanel container = new JPanel();
        container.setBorder(Tools.UI_BORDER_STANDARD);
        container.setLayout(new BorderLayout());
        container.setOpaque(true);
        
        container.add(selectedColorInfoContainer, BorderLayout.NORTH);
        JLabel spacer3 = new JLabel(" ");
        spacer3.setOpaque(true);
        container.add(spacer3, BorderLayout.CENTER); // spacer
        container.add(btnContainer, BorderLayout.SOUTH);
        
        container.setPreferredSize(new Dimension(container.getPreferredSize().width, colorPanel.getPreferredSize().height));
        
        this.add(colorPanel);
        this.add(container);
        
        initNodeRadSlider();
        initEdgeOpacitySlider();
        initCheckboxes();
    }
    
    private void initNodeRadSlider() {
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        JLabel sliderInfo = new JLabel("Change node radius");
        sliderInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderInfo.setSize(new Dimension(30, 100));
        //sliderInfo.setFont(Tools.getFont(14));
        
        int sliderMin = 2, sliderMax = 100;
        nodeRadSlider = new JSlider(sliderMin, sliderMax, Node.rad);
        nodeRadSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        Hashtable<Integer, JLabel> sliderMap = new Hashtable<>();
        JLabel minLbl = new JLabel(sliderMin+"");
        JLabel maxLbl = new JLabel(sliderMax+"");
        sliderMap.put(sliderMin, minLbl);
        sliderMap.put(sliderMax, maxLbl);
        nodeRadSlider.setLabelTable(sliderMap);
        nodeRadSlider.setMajorTickSpacing(30);
        nodeRadSlider.setPaintTicks(true);
        nodeRadSlider.setPaintLabels(true);
        nodeRadSlider.setPreferredSize(new Dimension(150, 40));
        nodeRadSlider.setMaximumSize(new Dimension(150, 40));
        nodeRadSlider.setMinimumSize(new Dimension(150, 40));
        nodeRadSlider.setEnabled(false);
        nodeRadSlider.addChangeListener(c -> {
            Node.rad = nodeRadSlider.getValue();
            graph.getNodes().forEach(n -> { n.width = nodeRadSlider.getValue(); n.height = nodeRadSlider.getValue(); });
        });
        
        container.add(sliderInfo);
        container.add(nodeRadSlider);
        this.add(container);
    }
    
    public void initEdgeOpacitySlider() {
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        JLabel sliderInfo = new JLabel("Change edge opacity");
        sliderInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderInfo.setSize(new Dimension(30, 100));
        
        int sliderMin2 = 0, sliderMax2 = 255;
        edgeOpacitySlider = new JSlider(sliderMin2, sliderMax2, Node.rad);
        edgeOpacitySlider.setValue(sliderMax2);
        edgeOpacitySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        Hashtable<Integer, JLabel> sliderMap2 = new Hashtable<>();
        JLabel minLbl2 = new JLabel(sliderMin2+"");
        JLabel maxLbl2 = new JLabel(sliderMax2+"");
        sliderMap2.put(sliderMin2, minLbl2);
        sliderMap2.put(sliderMax2, maxLbl2);
        edgeOpacitySlider.setLabelTable(sliderMap2);
        edgeOpacitySlider.setMajorTickSpacing(30);
        edgeOpacitySlider.setPaintTicks(true);
        edgeOpacitySlider.setPaintLabels(true);
        edgeOpacitySlider.setPreferredSize(new Dimension(150, 40));
        edgeOpacitySlider.setMaximumSize(new Dimension(150, 40));
        edgeOpacitySlider.setMinimumSize(new Dimension(150, 40));
        edgeOpacitySlider.setEnabled(false);
        edgeOpacitySlider.addChangeListener(c -> Edge.opacity = edgeOpacitySlider.getValue());
    
        container.add(sliderInfo);
        container.add(edgeOpacitySlider);
        this.add(container);
    }
    
    private void initCheckboxes() {
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        idDrawerCheckBox = new JCheckBox("Draw node IDs"); // extra spaces so checkboxes are (almost!) aligned - flow layout sucks
        idDrawerCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        idDrawerCheckBox.setEnabled(false);
        idDrawerCheckBox.addActionListener(a -> {
            Node.idDrawer = idDrawerCheckBox.isSelected() ?
                    ComponentDrawer.getIdDrawer() : ComponentDrawer.getNullDrawer();
        });
        container.add(idDrawerCheckBox);
    
        coordDrawerCheckBox = new JCheckBox("Draw node coordinates");
        coordDrawerCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        coordDrawerCheckBox.setEnabled(false);
        coordDrawerCheckBox.addActionListener(a -> {
            Node.coordDrawer = coordDrawerCheckBox.isSelected() ?
                    ComponentDrawer.getCoordDrawer() : ComponentDrawer.getNullDrawer();
        });
        container.add(coordDrawerCheckBox);
    
        edgeDrawerCheckBox = new JCheckBox("Draw edges");
        edgeDrawerCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        edgeDrawerCheckBox.setSelected(true);
        edgeDrawerCheckBox.setEnabled(false);
        edgeDrawerCheckBox.addActionListener(a -> {
            graph.drawEdges(edgeDrawerCheckBox.isSelected());
        });
        container.add(edgeDrawerCheckBox);
    
        stateDebugCheckBox = new JCheckBox("Draw states (debug)");
        stateDebugCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        stateDebugCheckBox.setSelected(false);
        stateDebugCheckBox.setEnabled(false);
        stateDebugCheckBox.addActionListener(a -> {
            Node.stateDebugDrawer = stateDebugCheckBox.isSelected() ?
                    ComponentDrawer.getStateDebugDrawer() : ComponentDrawer.getNullDrawer();
        });
        container.add(stateDebugCheckBox);
    
        neighborsDebugCheckBox = new JCheckBox("Draw node neighbors");
        neighborsDebugCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        neighborsDebugCheckBox.setSelected(false);
        neighborsDebugCheckBox.setEnabled(false);
        neighborsDebugCheckBox.addActionListener(a -> {
            Node.neighborsDrawer = neighborsDebugCheckBox.isSelected() ?
                    ComponentDrawer.getNeighborsDrawer() : ComponentDrawer.getNullDrawer();
        });
        container.add(neighborsDebugCheckBox);
    
        this.add(container);
    }
    
    public String getNameOfTab() { return this.TAB_NAME; }
    
    @Override
    public void onGraphClear() {
        edgeOpacitySlider.setEnabled(false);
        nodeRadSlider.setEnabled(false);
        idDrawerCheckBox.setEnabled(false);
        coordDrawerCheckBox.setEnabled(false);
        edgeDrawerCheckBox.setEnabled(false);
        stateDebugCheckBox.setEnabled(false);
        neighborsDebugCheckBox.setEnabled(false);
    }
    
    @Override
    public void onGraphImport() {
        if (graph.getNodes().isEmpty()) return;
        
        edgeOpacitySlider.setEnabled(true);
        nodeRadSlider.setEnabled(true);
        idDrawerCheckBox.setEnabled(true);
        coordDrawerCheckBox.setEnabled(true);
        edgeDrawerCheckBox.setEnabled(true);
        stateDebugCheckBox.setEnabled(true);
        neighborsDebugCheckBox.setEnabled(true);
    }
    
    @Override public void onNewInformedNode() {}
    @Override public void onNewUninformedNode() {}
    @Override public void edgeAdded(GraphEdgeChangeEvent<Node, DefaultEdge> e) {}
    @Override public void edgeRemoved(GraphEdgeChangeEvent<Node, DefaultEdge> e) {}
    @Override public void vertexAdded(GraphVertexChangeEvent<Node> e) {}
    @Override public void vertexRemoved(GraphVertexChangeEvent<Node> e) {}
}
