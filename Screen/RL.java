/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager_pls5.Screen;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author heroh
 */
public class RL implements Initializable  {
 
    
    @FXML private Button d1;
    @FXML private Button d2;
    @FXML private Button d3;
    @FXML private Button d4;
    @FXML private Button d5;
    @FXML private Button d6;
    @FXML private Button d7;
    @FXML private Button d8;
    
    
       @Override
       public void initialize(URL location, ResourceBundle resources) {
     
        String occupiedRoomsData = getOccupiedRoomsString(); 
    
    
    List<Button> roomButtons = Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8);
    
    
    setRoomStatusViaSwitch(roomButtons, occupiedRoomsData);
    
    System.out.println("we sucsesfull");
    }
 
       
       
       private String getOccupiedRoomsString() {
    StringBuilder occupiedRooms = new StringBuilder("#");
    String fileName = "RData.txt"; 
    
    try (Scanner scanner = new Scanner(new File(fileName))) {
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim(); 
            if (line.isEmpty()) continue;
            
            
            String[] parts = line.split("\\s+");
            
            if (parts.length > 0) {
                
                occupiedRooms.append(parts[0].trim()).append("#"); 
            }
        }
    } catch (FileNotFoundException e) {
        System.err.println("❌ خطأ: ملف RData.txt غير موجود.");
    }
    
    return occupiedRooms.toString();
}
      
       
       
       
       
       private void setRoomStatusViaSwitch(List<Button> buttons, String occupiedRoomsString) {
    
    
    
    for (Button button : buttons) {
        String roomName = button.getId(); // d1, d2, ...
        String roomNumberToCheck = "";

    
        switch (roomName) {
            case "d1":
                roomNumberToCheck = "1";
                break;
            case "d2":
                roomNumberToCheck = "2";
                break;
            case "d3":
                roomNumberToCheck = "3";
                break;
            case "d4":
                roomNumberToCheck = "4";
                break;
            case "d5":
                roomNumberToCheck = "5";
                break;
            case "d6":
                roomNumberToCheck = "6";
                break;
            case "d7":
                roomNumberToCheck = "7";
                break;
            case "d8":
                roomNumberToCheck = "8";
                break;
            default:
                continue; 
        }

        
        boolean isOccupied = occupiedRoomsString.contains("#" + roomNumberToCheck + "#");

        
        if (isOccupied) {
            button.setDisable(true); 
            button.setText( " - unavailable"); 
            button.setStyle("-fx-background-color: #d5d5d5; -fx-font-weight: bold;");
        } else {
            button.setDisable(false); 
            button.setText( " - Available");
             
            button.setStyle("-fx-background-color: #fefffa; -fx-font-weight: bold;");
        }
    }
}
       
       
       
       
       
        
    
    public void OpenMasag(ActionEvent event) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/DolAdd.fxml"));

        
        Stage newStage = new Stage();
        
        
        newStage.initStyle(StageStyle.UNDECORATED); 
        
        
        newStage.setScene(new Scene(root));

        
        newStage.centerOnScreen();
        
        
        newStage.show(); 
        
      
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}
  
    
    
    
    public void switchToTRSScreen11(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/TRS.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); // ✅ استبدال الجذر
    } catch (Exception e) {
        e.printStackTrace();
    }
} 
    
    public void OpenMassag(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/DolAdd.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); // ✅ استبدال الجذر
    } catch (Exception e) {
        e.printStackTrace();
    }
} 

    
    
}
    
    
