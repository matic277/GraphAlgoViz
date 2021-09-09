package impl.tools;

import impl.Pair;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;

public class InputHelpers {
    
    // Collection of functions
    private InputHelpers() {}
    
    // Returns:
    // Pair<Parsed_value, isPercentage>
    // Throws exception if bad input
    public static Pair<Number, Boolean> performInputValidationDoubleOrInteger(String input) {
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
    
    public static double performInputValidationDouble(String input) {
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
    public static int performInputValidationInteger(String input) {
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
    
    public static boolean checkInputFileExists(String path) {
        File f = new File(path);
        return f.isFile() && f.exists();
    }
    
    public static boolean checkInputDecimal(String input) {
        return (input.contains(",") || input.contains(".")) &&
                !((input.contains(",") && input.contains(".")) &&
                        (NumberUtils.isNumber(input.replace(",", ""))));
    }
    
    public static boolean checkInputInteger(String input) {
        return !(input.contains(",") || input.contains(".")) && (
                NumberUtils.isNumber(input));
    }
}
