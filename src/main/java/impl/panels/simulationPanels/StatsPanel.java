package impl.panels.simulationPanels;

import core.GraphChangeObserver;
import impl.MyGraph;
import impl.tools.Tools;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class StatsPanel extends JScrollPane implements GraphChangeObserver {
    
    BottomPanel parent;
    
    JPanel mainPanel;
    MyGraph graph = MyGraph.getInstance();
    
    int informedNum, uninformedNum;
    double informedPercent;
    
    JTable table;
    DefaultTableModel tableModel;
    
    enum TableKey {
        // Natural ordering, as is specified in source-code
        // In order to change ordering displayed in GUI, simply swap rows here!
        NODES_NUMBER(0, "Number of nodes"),
        EDGES_NUMBER(1, "Number of edges"),
        INFORMED_NODES(2, "Number of informed nodes"),
        UNINFORMED_NODES(3, "Number of uninformed nodes"),
        INFORMED_PERCENTAGE(4, "Percentage of informed nodes"),
        ;
        
        int id; String displayStr;
        TableKey(int id_, String displayStr_) { id=id_; displayStr=displayStr_; }
    }
    
    public StatsPanel(BottomPanel parent) {
        this.parent = parent;
        graph.addObserver(this);
        
        Dimension panelSize = new Dimension(Tools.INITIAL_STATS_PANEL_WIDTH, parent.getHeight());
        this.setSize(panelSize);
        this.setPreferredSize(panelSize);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Tools.GRAY3);
        
        JLabel title = new JLabel(" Graph statistics");
        title.setFont(Tools.getBoldFont(14));
        title.setOpaque(true);
        title.setBackground(Tools.GRAY2);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setPreferredSize(new Dimension(title.getPreferredSize().width, 30));
        title.setBorder(new MatteBorder(0, 0, 1, 0, Color.lightGray));
    
        initTable();
        
        JPanel tablePanel = new JPanel();
        tablePanel.setOpaque(false);
        tablePanel.add(table);
        
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        this.setViewportView(mainPanel);
        
        // so that this panel can be squished, hiding its components
        // otherwise components dictate smallest possible size
        this.setMinimumSize(new Dimension(0, 0));
    }
    
    public void initTable() {
        // INIT RENDERING
        // thanks to https://stackoverflow.com/questions/8002445/trying-to-create-jtable-with-proper-row-header/8005006#8005006
        // Custom class and other stuff is shortened to line => table.getTableHeader().setDefaultRenderer(table.getTableHeader().getDefaultRenderer());
        // Change table head rendered so that header is displayed on the left
        // (instead of on top)
        table = new JTable() {
            @Override public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                return col == 0 ?
                        this.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(
                            this, this.getValueAt(row, col), false, false, row, col)
                        :
                        super.prepareRenderer(renderer, row, col);
            }
            @Override public boolean isCellEditable(int row, int column) { return false; } // disable editing
        };
        table.getTableHeader().setDefaultRenderer(table.getTableHeader().getDefaultRenderer());
        table.setAutoCreateRowSorter(false); // disable sorting
        
        // INIT HEADERS and data, sorting might be excessive, enums are naturally ordered as specified in source code?
        Object[] header = Arrays.stream(TableKey.values()).sorted(Comparator.comparingInt(k -> k.id)).map(key -> key.displayStr).toArray();
        Object[][] data = new String[header.length][2];
        // header rows, 2 columns            (header                | value)
        for (int i=0; i<header.length; i++) { data[i][0] = header[i]; data[i][1] = "0"; }
        
        //                                 contents, num of columns and its content(leave empty)
        tableModel = new DefaultTableModel(data, new String[2]);
        table.setModel(tableModel);
        table.setFont(Tools.getFont(14));
        table.getTableHeader().setFont(Tools.getBoldFont(14));
        
        table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(75);
        table.setRowHeight(25);
        table.setBorder(new MatteBorder(1,1, 1, 1,Color.darkGray));
    
//        table.getTableHeader().getColumnModel().getColumn(0).setPreferredHeight(250);
//        table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(75);
    }
    
    public void onNewGraphImport() {
        table.setValueAt(graph.getGraph().vertexSet().size(), TableKey.NODES_NUMBER.id, 1);
        table.setValueAt(graph.getGraph().edgeSet().size(), TableKey.EDGES_NUMBER.id, 1);
        
        double informed = graph.getNumberOfInformedNodes();
        double uninformed = graph.getGraph().vertexSet().size() - informed;
        double perc = uninformed == 0 ? 1 : informed / uninformed;
        
        table.setValueAt((int)informed, TableKey.INFORMED_NODES.id, 1);
        table.setValueAt(perc, TableKey.INFORMED_PERCENTAGE.id, 1);
        table.setValueAt((int)uninformed, TableKey.UNINFORMED_NODES.id, 1);
        
        table.repaint();
    }
    
    @Override
    public void onNodeAdded() {
        table.setValueAt(graph.getGraph().vertexSet().size(), TableKey.NODES_NUMBER.id, 1);
        table.repaint();
    }
    
    @Override
    public void onNodeDeleted() {
        onNodeAdded();
    }
    
    @Override
    public void onEdgeAdded() {
        table.setValueAt(graph.getGraph().edgeSet().size(), TableKey.EDGES_NUMBER.id, 1);
        table.repaint();
    }
    
    @Override
    public void onNewInformedNode() {
        double informed = graph.getNumberOfInformedNodes();
        double uninformed = graph.getGraph().vertexSet().size() - informed;
        double perc = uninformed == 0 ? 1 : informed / uninformed;
    
        table.setValueAt((int)informed, TableKey.INFORMED_NODES.id, 1);
        table.setValueAt(perc, TableKey.INFORMED_PERCENTAGE.id, 1);
        table.setValueAt((int)uninformed, TableKey.UNINFORMED_NODES.id, 1);
        
        table.repaint();
    }
    
    // TODO these methods are useless, rename and combine onNewUninformed & onNewInformed -> onInformedChange
    @Override
    public void onNewUninformedNode() {
        onNewInformedNode();
    }
    @Override
    public void onGraphClear() {
        table.setValueAt(0, TableKey.NODES_NUMBER.id, 1);
        table.setValueAt(0, TableKey.EDGES_NUMBER.id, 1);
        table.setValueAt(0, TableKey.INFORMED_NODES.id, 1);
        table.setValueAt(0, TableKey.INFORMED_PERCENTAGE.id, 1);
        table.setValueAt(0, TableKey.UNINFORMED_NODES.id, 1);
        
        table.repaint();
    
        System.out.println("cleared");
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
}
