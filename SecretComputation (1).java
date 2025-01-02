import java.util.*;
import java.util.regex.*;
import java.math.*;

public class SecretComputation {
    
  
    public static int convertToDecimal(String value, int base) {
        int decimalValue = 0;
        int power = 0;
        for (int i = value.length() - 1; i >= 0; i--) {
            decimalValue += (value.charAt(i) - '0') * Math.pow(base, power);
            power++;
        }
        return decimalValue;
    }

 
    public static int lagrangeBasisAtZero(int x, List<Integer> xValues, int i) {
        int result = 1;
        for (int j = 0; j < xValues.size(); j++) {
            if (i != j) {
                result *= (0 - xValues.get(j));
                result /= (xValues.get(i) - xValues.get(j));
            }
        }
        return result;
    }

   
    public static int findConstantTerm(List<Integer> xValues, List<Integer> yValues) {
        int constantTerm = 0;
        for (int i = 0; i < xValues.size(); i++) {
            int basis = lagrangeBasisAtZero(xValues.get(i), xValues, i);
            System.out.println("L_" + i + "(0) = " + basis);  // Debugging print for Lagrange basis
            constantTerm += yValues.get(i) * basis;
        }
        return constantTerm;
    }

    
    public static int safeStoi(String str) {
        str = str.trim();  

       
        if (str.startsWith("\"") && str.endsWith("\"")) {
            str = str.substring(1, str.length() - 1);
        }

        
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("Invalid input for stoi: " + str);
            }
        }

        return Integer.parseInt(str);
    }

   
    public static void parseTestCase(String jsonString, List<Integer> xValues, List<Integer> yValues) {
        
        Pattern pattern = Pattern.compile("\"(\\d+)\": \\{\"base\": \"(\\d+)\", \"value\": \"(\\S+)\"\\}");
        Matcher matcher = pattern.matcher(jsonString);

        while (matcher.find()) {
            
            int xValue = Integer.parseInt(matcher.group(1));
            int base = Integer.parseInt(matcher.group(2));
            String valueStr = matcher.group(3);
            int value = convertToDecimal(valueStr, base);

            
            xValues.add(xValue);
            yValues.add(value);
        }
    }

    public static void main(String[] args) {
        
        String jsonString1 = "{\"keys\": {\"n\": 4, \"k\": 3}, \"1\": {\"base\": \"10\", \"value\": \"4\"}, \"2\": {\"base\": \"2\", \"value\": \"111\"}, \"3\": {\"base\": \"10\", \"value\": \"12\"}, \"6\": {\"base\": \"4\", \"value\": \"213\"}}";

        
        String jsonString2 = "{\"keys\": {\"n\": 5, \"k\": 3}, \"1\": {\"base\": \"10\", \"value\": \"5\"}, \"3\": {\"base\": \"8\", \"value\": \"10\"}, \"5\": {\"base\": \"2\", \"value\": \"101\"}}";

       
        List<Integer> xValues1 = new ArrayList<>();
        List<Integer> yValues1 = new ArrayList<>();
        List<Integer> xValues2 = new ArrayList<>();
        List<Integer> yValues2 = new ArrayList<>();

        
        parseTestCase(jsonString1, xValues1, yValues1);
        parseTestCase(jsonString2, xValues2, yValues2);

       
        int secret1 = findConstantTerm(xValues1, yValues1);
        int secret2 = findConstantTerm(xValues2, yValues2);

        /
        System.out.println("Secret for test case 1: " + secret1);
        System.out.println("Secret for test case 2: " + secret2);
    }
}
