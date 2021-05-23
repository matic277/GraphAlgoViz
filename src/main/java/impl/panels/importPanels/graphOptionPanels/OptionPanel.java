package impl.panels.importPanels.graphOptionPanels;

import core.GraphType;
import impl.Pair;
import impl.tools.Tools;
import impl.windows.ImportGraphWindow;
import impl.windows.SimulationWindow;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class OptionPanel extends JPanel {
    
    protected ImportGraphWindow parent;
    protected SimulationWindow simWindow;
    
    protected Dimension panelSize;
    List<JComponent> components;
    
    public OptionPanel(ImportGraphWindow parent) {
        this.parent = parent;
//        this.panelSize = new Dimension(300,200);
        this.setVisible(true);
        this.setPreferredSize(panelSize);
        this.setMaximumSize(panelSize);
        
        this.setOpaque(true);
        this.setBackground(Tools.bgColor);
        
        this.components = new ArrayList<>(10);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D gr = (Graphics2D) g;
        // anti-aliasing
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        g.setColor(Tools.GRAY3);
        gr.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        
        gr.setColor(Tools.GRAY);
        gr.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    
    protected void signalBadInput(String errorMsg, JLabel errorLbl) {
        CompletableFuture.runAsync(() -> {
            errorLbl.setText(" " + errorMsg + " ");
            errorLbl.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Tools.RED));
            errorLbl.setVisible(true);
            Tools.sleep(5000);
            errorLbl.setVisible(false);
        });
    }
    
    // Returns:
    // Pair<Parsed_value, isPercentage>
    // Throws exception if bad input
    public Pair<Number, Boolean> performInputValidationDoubleOrInteger(String input) {
        double parsedValue;
        input = input.trim().replace(",", ".");
        
        if (input.isEmpty() || input.isBlank() || (input.equals("%"))) {
            throw new RuntimeException("Empty value.");
        }
        
        boolean isPercentage = input.charAt(input.length()-1) == '%';
        
        if (isPercentage) {
            String number = input.substring(0, input.length()-1);
            if (!(checkInputDecimal(number) || checkInputInteger(number))) {
                throw new RuntimeException("Not a number.");
            }
            parsedValue = Double.parseDouble(number);
        } else {
            if (!checkInputInteger(input)) {
                throw new RuntimeException("Not an integer.");
            }
            parsedValue = Double.parseDouble(input);
        }
        
        if (parsedValue < 0) throw new RuntimeException("Cannot be negative.");
        
        return new Pair<>(parsedValue, isPercentage);
    }
    
    public double performInputValidationDouble(String input) {
        input = input.trim();
    
        if (input.isBlank() || input.isEmpty()) throw new RuntimeException("Empty input.");
    
        double parsedValue;
        input = input.trim();
    
        try {
            parsedValue = Double.parseDouble(input);
        } catch (Exception e) {
            throw new RuntimeException("Not an integer.");
        }
    
        if (parsedValue < 0) throw new RuntimeException("Cannot be negative.");
    
        return parsedValue;
    }
    
    // Returns:
    // Parsed integer
    // Throws exception if bad input
    public int performInputValidationInteger(String input) {
        input = input.trim();
        
        if (input.isBlank() || input.isEmpty()) throw new RuntimeException("Empty input.");
        
        int parsedValue;
        input = input.trim();
        
        try {
            parsedValue = Integer.parseInt(input);
        } catch (Exception e) {
            throw new RuntimeException("Not an integer.");
        }
    
        if (parsedValue < 0) throw new RuntimeException("Cannot be negative.");
        
        return parsedValue;
    }
    
    public boolean checkInputFileExists(String path) {
        File f = new File(path);
        return f.isFile() && f.exists();
    }
    
    public boolean checkInputDecimal(String input) {
        return (input.contains(",") || input.contains(".")) &&
                !((input.contains(",") && input.contains(".")) &&
                (NumberUtils.isNumber(input.replace(",", ""))));
    }
    
    public boolean checkInputInteger(String input) {
        return !(input.contains(",") || input.contains(".")) && (
                NumberUtils.isNumber(input));
    }
    
    protected JLabel getErrorLabel() {
        JLabel lbl = new JLabel();
        lbl.setForeground(Tools.RED);
        lbl.setOpaque(false);
        lbl.setVisible(false);
        return lbl;
    }
    
    public void setSimulationWindow(SimulationWindow simWindow) {
        this.simWindow = simWindow;
    }
    
    public abstract ActionListener getButtonAction(GraphType type, JFrame importWindow);
}
