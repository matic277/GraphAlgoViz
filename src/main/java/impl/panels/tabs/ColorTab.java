package impl.panels.tabs;

import impl.MyGraph;
import impl.Node;
import impl.tools.Edge;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class ColorTab extends JPanel {
    
    TabsPanel parent;
    
    JButton openBtn;
    
    MyGraph graph;
    
    JFrame colorFrame;
    
    public ColorTab(TabsPanel parent) {
        this.parent = parent;
        this.graph = MyGraph.getInstance();
        
        this.setOpaque(true);
        this.setBackground(Tools.GRAY3);
//        WrapLayout layout = new WrapLayout(WrapLayout.LEFT);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JPanel selectedColorInfoContainer = new JPanel();
        selectedColorInfoContainer.setLayout(new BorderLayout());
        selectedColorInfoContainer.setOpaque(true);
        selectedColorInfoContainer.setBackground(Tools.GRAY2);
        
        JLabel selectedColorTitle = new JLabel("Selected color");
        selectedColorTitle.setFont(Tools.getFont(14));
        selectedColorTitle.setBackground(Tools.TITLE_BACKGROUND);
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
        selectedColorInfo.setBackground(Color.white);
        selectedColorInfo.setBorder(new LineBorder(Color.black, 1));
        selectedColorInfo.setPreferredSize(new Dimension(100, 130));
        selectedColorInfo.setMaximumSize(new Dimension(50, 130));
        
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
        colorPanel.setBorder(new LineBorder(Color.black, 1));
        colorPanel.setBackground(Tools.GRAY2);
        colorPanel.getColorSelectionModel().addChangeListener(c -> {
            selectedColorInfo.setBackground(colorPanel.getColorSelectionModel().getSelectedColor());
            this.repaint(); // needed otherwise selectedColorInfo gets a little glitchy
        });
        
        JButton setInformedClrBtn = new JButton("Set color as informed");
        setInformedClrBtn.setFont(Tools.getFont(14));
        setInformedClrBtn.addActionListener(a -> {
            Node.INFORMED_COLOR = colorPanel.getColorSelectionModel().getSelectedColor();
        });
        JButton setUninformedClrBtn = new JButton("Set color as uninformed");
        setUninformedClrBtn.setFont(Tools.getFont(14));
        setUninformedClrBtn.addActionListener(a -> {
            Node.UNINFORMED_COLOR = colorPanel.getColorSelectionModel().getSelectedColor();
        });
        JButton setEdgeClrBtn = new JButton("Set color as edge color");
        setEdgeClrBtn.setFont(Tools.getFont(14));
        setEdgeClrBtn.addActionListener(a -> {
            Edge.EDGE_COLOR = colorPanel.getColorSelectionModel().getSelectedColor();
        });
        
        JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new BorderLayout());
        btnContainer.setOpaque(true);
        btnContainer.setBackground(Tools.GRAY2);
        btnContainer.add(setInformedClrBtn, BorderLayout.NORTH);
        btnContainer.add(setUninformedClrBtn, BorderLayout.CENTER);
        btnContainer.add(setEdgeClrBtn, BorderLayout.SOUTH);
        
        JPanel container = new JPanel();
        container.setBorder(new LineBorder(Color.black, 1));
        container.setLayout(new BorderLayout());
        container.setOpaque(true);
        container.setBackground(Tools.GRAY2);
        
        container.add(selectedColorInfoContainer, BorderLayout.NORTH);
        container.add(new JLabel(" "), BorderLayout.CENTER); // space
        container.add(btnContainer, BorderLayout.SOUTH);
        
        container.setPreferredSize(new Dimension(container.getPreferredSize().width, colorPanel.getPreferredSize().height));
        
        
        this.add(colorPanel);
        this.add(container);
    }
    
//    @Override
//    public void paintComponent(Graphics g) {
//        Tools.sleep(1000/60);
//        super.paintComponent(g);
//    }
    
}
