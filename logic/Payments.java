/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager_pls5.Screen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
 
public class Payments implements Initializable {
    
     @Override
    public void initialize(URL location, ResourceBundle resources) 
    {}
    
    @FXML private Label roomNumberLabel; 
@FXML private Label typ;            
@FXML private Label jskss;          
@FXML private Label t;              
@FXML private Label st; 
@FXML private Label  DT ; 

@FXML private Label totalPriceLabel;
@FXML private Label FnlP;
@FXML private Label pricePerHourLabel;
@FXML  private TextField TXFF;
    


  private String currentRoomData; 
    
  private void showAlert(AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
     
    @FXML
    public void STR() {
        String roomNumber = TXFF.getText().trim();
        
        if (roomNumber.isEmpty()) {
            showAlert(AlertType.WARNING, "مدخلات ناقصة", "الرجاء إدخال رقم غرفة");
        clearLabels("الرجاء إدخال رقم غرفة");
            return;
        }
        
        String roomData = findRoomData(roomNumber);
        currentRoomData = roomData;
        
        if (roomData.equals("Room not found") || roomData.equals("Data file not found")) {
            clearLabels(roomData);
        } else {
            updateRoomDetailsLabels(roomData);
        }
    }
    
    
    @FXML
    public void completePayment(ActionEvent event) {
        if (currentRoomData == null || currentRoomData.isEmpty() || 
            currentRoomData.equals("Room not found") || 
            currentRoomData.equals("Data file not found")) {
            System.out.println("No room data to process payment");
            return;
        }
        
        try {
    
            transferToHData(currentRoomData);
            
    
            deleteFromRData(currentRoomData);
            
            System.out.println("Payment completed successfully! Data transferred to HData.txt");
            
            showAlert(AlertType.WARNING, "مبروك", "تمت العملية بنجاح");
            clearLabels("تم الدفع بنجاح");
            TXFF.setText("");
            currentRoomData = null;
            
        } catch (IOException e) {
            System.out.println("Error processing payment: " + e.getMessage());
        }
    }
    
    
    private void transferToHData(String roomData) throws IOException {
        File hDataFile = new File("HData.txt");
        
        try (FileWriter fw = new FileWriter(hDataFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
        
            String timestamp = new java.util.Date().toString();
            String paidData = roomData + " PAID_AT: " + timestamp;
            
            out.println(paidData);
            System.out.println("Data transferred to HData: " + paidData);
        }
    }
    
         private void deleteFromRData(String roomDataToDelete) throws IOException {
        File rDataFile = new File("RData.txt");
        File tempFile = new File("RData_temp.txt");
        
        String roomNumberToDelete = roomDataToDelete.split("\\s+")[0];
        
        try (BufferedReader reader = new BufferedReader(new FileReader(rDataFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            
            String currentLine;
            boolean found = false;
            
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split("\\s+");
                if (parts.length > 0 && !parts[0].equals(roomNumberToDelete)) {
                    writer.write(currentLine + System.lineSeparator());
                } else {
                    found = true;
                    System.out.println("Removed from RData: " + currentLine);
                }
            }
            
            if (!found) {
                System.out.println("Room data not found in RData.txt");
            }
        }
        
        
        if (rDataFile.delete()) {
            if (!tempFile.renameTo(rDataFile)) {
                throw new IOException("Could not rename temp file");
            }
        } else {
            throw new IOException("Could not delete original file");
        }
    }

  private void updateRoomDetailsLabels(String dataLine) {
    String[] parts = dataLine.split("\\s+"); 
    
    
    if (parts.length >= 6) {
        String roomNum = parts[0];
        String roomType = parts[1];       
        String guests = parts[2];         
        String Duration = parts[3];       
        String startDate = parts[4];      
        String startTimeOnly = parts[5];  
        
        roomNumberLabel.setText(roomNum);
        typ.setText(roomType);         
        jskss.setText(guests);         
        t.setText(Duration );      
        st.setText(startTimeOnly);     
        DT.setText(startDate);    
        String price = Siting.getPrice(roomType);
        pricePerHourLabel.setText(price +"$" );
        TPrice(price, Duration);
        
    } else {
        System.out.println("بيانات غير كافية: " + parts.length + " أجزاء");
        clearLabels("تنسيق بيانات غير صحيح");
    }
} 
  
    @FXML 
    public void TPrice(String pricePerHour, String durationHours) {
        try {
        
            double price = Double.parseDouble(pricePerHour);
            double duration = Double.parseDouble(durationHours);
            
        
            double totalPrice = price * duration;
            
        
            totalPriceLabel.setText(String.format("%.1f", totalPrice)); 
             FnlP.setText(String.format("%.2f", totalPrice)); 
            
        } catch (NumberFormatException e) {
            totalPriceLabel.setText("N/A");
            FnlP.setText("N/A");
            System.err.println("Error in TPrice: Invalid number format for price or duration. " + e.getMessage());
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
  
    @FXML 
public void openAddProductScreen(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/TEST.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        currentStage.getScene().setRoot(root); // ✅ استبدال الجذر
    } catch (Exception e) {
        e.printStackTrace();
    }
}



    public String findRoomData(String roomNumber) {
    try {
        
        File file = new File("RData.txt");
        String roomData;
        try (Scanner scanner = new Scanner(file)) {
            roomData = null;
        
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");
                if (parts.length > 0 && parts[0].equals(roomNumber)) {
                    roomData = line;
                    break;
                }
            }
        }
        
        // إذا لم يتم العثور على البيانات
        if (roomData == null) {
            return "Room not found";
        }
        
        return roomData;
        
    } catch (FileNotFoundException e) {
        return "Data file not found";
    }
}
    
 private void clearLabels(String message) {
        
        roomNumberLabel.setText(message); 
                
        typ.setText("");
        jskss.setText("");
        t.setText("");
        st.setText("");
        
    }   
    
    
}
