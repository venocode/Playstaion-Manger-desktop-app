/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager_pls5.Screen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
public class AR implements Initializable {
    
    @FXML
    private Label Number;
    @FXML
    private Label Times;
    @FXML
    private Label jsk;
    @FXML
    private Label TM;
    @FXML
    private Label TP;
    @FXML
    private Label TPP;
    @FXML
    private ComboBox<Integer> myComboBox; // لـ VIP Room
    
    @FXML
    private ComboBox<Integer> normalComboBox;
      
    private int joystickCounter = 2;
    private int T1 = 1;
    private int oystickCounter = 2;
    private int T = 1;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
     
        Number.setText(String.valueOf(joystickCounter));
        Times.setText(String.valueOf(T1));
        jsk.setText(String.valueOf(oystickCounter));
        TM.setText(String.valueOf(T));
        
        myComboBox.setItems(FXCollections.observableArrayList(1, 2));
         
        normalComboBox.setItems(FXCollections.observableArrayList(3, 4,5,6,7,8));
        
         
        System.out.println("we sucsesfull");
    }
    
    
    
    private void showAlert(String title, String message, AlertType type)
    {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);  
    alert.setContentText(message);
    alert.showAndWait();
   }
    
    
    public void addVipRoom(ActionEvent event) {
    
    Integer rnValue = myComboBox.getSelectionModel().getSelectedItem();
    String roomType = TP.getText(); 
    String timeValue = Times.getText();
    String joystickCount = Number.getText();
    
    String registrationTime = getCurrentTime();
    
    String dataString = String.format("%s %s %s %s %s", 
            rnValue != null ? rnValue.toString() : "N/A",
            roomType,
            timeValue, 
            joystickCount,
            registrationTime);  

    saveRoomDataWithCheck(rnValue.toString(), dataString); 
}

    
@FXML
public void addNormalRoom(ActionEvent event) {
    
    Integer rnValue = normalComboBox.getSelectionModel().getSelectedItem();
    String timeValue = TM.getText();
    String roomType = TPP.getText(); 
    String joystickCount = jsk.getText();
    String registrationTime = getCurrentTime();
     
    String dataString = String.format("%s %s %s %s %s", 
            rnValue != null ? rnValue.toString() : "N/A",  
            roomType,
            timeValue,  
            joystickCount,
            registrationTime);  

    saveRoomDataWithCheck(rnValue.toString(), dataString); 
}

 
private String getCurrentTime() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return now.format(formatter);
}
 

    private void saveRoomDataWithCheck(String roomNumberString, String dataString) {
    String fileName = "RData.txt";

    if (isRoomNumberAlreadyExists(roomNumberString)) {
        showAlert("Input Error", 
                "Room number " + roomNumberString + " is already occupied.", 
                AlertType.ERROR);
        return;
    }
    
    try (FileWriter fw = new FileWriter(fileName, true); 
         PrintWriter pw = new PrintWriter(fw)) {
         pw.println(dataString);
        
        showAlert("Success", 
                "Room number " + roomNumberString + " has been added successfully.\n" +
                "Registration time: " + getCurrentTime(), 
                AlertType.INFORMATION);
        
    } catch (IOException e) {
        System.err.println("An error occurred while saving data: " + e.getMessage());
    }
}


   
private boolean isRoomNumberAlreadyExists(String roomNumberToCheck) {
        String fileName = "RData.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                if (line.trim().isEmpty()) continue;
                
               
                String[] parts = line.split("\\s+");  
                
                if (parts.length > 0) {
                    String existingRoomNumber = parts[0].trim();
                    
                     
                    if (existingRoomNumber.equals(roomNumberToCheck)) {
                        return true; 
                    }
                }
            }
        } catch (IOException e) {
          
            if (!e.getMessage().contains("No such file or directory") && !e.getMessage().contains("امرفوض")) {
                 System.err.println("Error reading RData file: " + e.getMessage());
            }
        }
        return false; 
    }
 
 

    
    @FXML
    private void increaseJoystickCount(ActionEvent event) {
        joystickCounter++;
        Number.setText(String.valueOf(joystickCounter));
    }
    
    @FXML
    private void increaseJoystickCount2(ActionEvent event) {
        T1++;
        Times.setText(String.valueOf(T1));
    }
      
    @FXML
    private void decreaseJoystickCount(ActionEvent event) {
        if (joystickCounter > 1) {
            joystickCounter--;
            Number.setText(String.valueOf(joystickCounter));
        }
    }

    @FXML
    private void decreaseJoystickCount2(ActionEvent event) {
        if (T1 > 1) { 
            T1--;
            Times.setText(String.valueOf(T1));
        }   
    }
    
    @FXML
    private void increaseJsk(ActionEvent event) {
        oystickCounter++;
        jsk.setText(String.valueOf(oystickCounter));
    }
    
    @FXML
    private void decreaseJsk(ActionEvent event) {
        if (oystickCounter > 1) {
            oystickCounter--;
            jsk.setText(String.valueOf(oystickCounter));
        }
    }

    @FXML
    private void increaseTM(ActionEvent event) {
        T++;
        TM.setText(String.valueOf(T));
    }
    
    @FXML
    private void decreaseTM(ActionEvent event) {
        if (T > 1) {
            T--;
            TM.setText(String.valueOf(T));
        }
    } 
      
    public void switchToTRSScreen(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/TRS.fxml"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    

   
    
    
    
}