package manager_pls5.Screen;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * @author heroh
 * Controller for the Room Pricing Settings screen.
 */
public class Siting {
    
   
    @FXML
    private TextField normalRoomPriceField;
    
    @FXML
    private TextField vipRoomPriceField;

    private static final String PRICING_DATA_FILE = "PData.txt";
 
    
    @FXML
    private void handleSaveButton() {
        String normalPrice = normalRoomPriceField.getText().trim();
        String vipPrice = vipRoomPriceField.getText().trim();

   
        if (!isValidPrice(normalPrice) || !isValidPrice(vipPrice)) {
            System.err.println("Error: Please enter valid numeric values for prices.");
   
            return;
        }

     
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRICING_DATA_FILE))) {
   
            writer.write("VIP_PRICE: " + vipPrice);
            writer.newLine();
            writer.write("Normal_PRICE: " + normalPrice);
            System.out.println("Pricing data saved successfully to " + PRICING_DATA_FILE);
             
            
        } catch (IOException e) {
            System.err.println("Error saving pricing data: " + e.getMessage());
        }
    }

     
 
    
    @FXML
public void switchToTRSScreen(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/TRS.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root);  
    } catch (Exception e) {
        e.printStackTrace();
    }
} 
  
    
     
    private boolean isValidPrice(String price) {
        if (price.isEmpty()) return false;
        try {
            Double.parseDouble(price);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
     
    public static String getPrice(String roomType) {
        String key = (roomType.equalsIgnoreCase("VIP") ? "VIP_PRICE" : "Normal_PRICE");
        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File(PRICING_DATA_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith(key + ":")) {
                    return line.split(":")[1].trim();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading pricing data: " + e.getMessage());
        }
        return "N/A"; 
    }
}