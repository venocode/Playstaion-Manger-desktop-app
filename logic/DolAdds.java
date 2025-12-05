/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager_pls5.Screen;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
/**
 *
 * @author heroh
 */

public class DolAdds implements Initializable {

    
    @FXML
    private RadioButton RV;
    
    @FXML
    private RadioButton RN;
    
 
    @FXML
    private ChoiceBox<Integer> roomNumberChoiceBox; 
 
    @FXML
    private Spinner<Integer> joystickSpinner;   
    @FXML
    private Spinner<Integer> joystickSpinnerHour;  
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {

        SpinnerValueFactory<Integer> joystickFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 2);  
        joystickSpinner.setValueFactory(joystickFactory);
        
        ObservableList<Integer> roomNumbers = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8);
       roomNumberChoiceBox.setItems(roomNumbers);
        SpinnerValueFactory<Integer> hourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);  
        joystickSpinnerHour.setValueFactory(hourFactory);
        
        
        ToggleGroup group = new ToggleGroup();
        RV.setToggleGroup(group);
        RN.setToggleGroup(group);
        
    }
    
    
    
    
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); 
        alert.setContentText(message);
        alert.showAndWait();
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
             if (!e.getMessage().contains("No such file or directory")) {
                 System.err.println("Error reading RData file: " + e.getMessage());
             }
        }
        return false;
    }
    
    
    
    private void saveRoomDataWithCheck(String roomNumberString, String dataString) {
        String fileName = "RData.txt";

        
        if (isRoomNumberAlreadyExists(roomNumberString)) {
            showAlert("Input Error", 
                      "Room number " + roomNumberString + " is already occupied. Please choose a different number.", 
                      AlertType.ERROR);
            return; 
        }

        
        try (
            FileWriter fw = new FileWriter(fileName, true); 
            PrintWriter pw = new PrintWriter(fw);
        ) {
            pw.println(dataString);
            
            // Show Success Alert
            showAlert("Success", 
                      "Room number " + roomNumberString + " has been added successfully.", 
                      AlertType.INFORMATION);
            
        } catch (IOException e) {
            System.err.println("An error occurred while saving data to file: " + e.getMessage());
            showAlert("System Error", 
                      "An error occurred while trying to save data: " + e.getMessage(), 
                      AlertType.ERROR);
        }
    }
 
    
    

    @FXML
    public void addAction(ActionEvent event) {
        
    
        Integer rnValue = roomNumberChoiceBox.getSelectionModel().getSelectedItem();
        if (rnValue == null) {
            showAlert("Input Error", "Please select a room number.", AlertType.ERROR);
            return;
        }
        String roomNumberString = rnValue.toString();
        
    
        String roomType;
        if (RV.isSelected()) {
            roomType = "Vip";
        } else if (RN.isSelected()) {
            roomType = "Normal";
        } else {
            showAlert("Input Error", "Please select a Room Type (VIP or Normal).", AlertType.ERROR);
            return;
        }
        
        Integer joystickCount = joystickSpinner.getValue(); 
    
        Integer hourValue = joystickSpinnerHour.getValue();
        String timeValue = String.format("%d:00", hourValue); // تحويل الساعات إلى صيغة الوقت 1:00

        String dataString = String.format("%s %s %s %s", 
                roomNumberString,
                roomType,
                timeValue,
                joystickCount);


        saveRoomDataWithCheck(roomNumberString, dataString);
    }
    
    
    
    
    
    
    public void switchToTRSScreenx(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/TRS.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); // ✅ استبدال الجذر
    } catch (Exception e) {
        e.printStackTrace();
    }
} 
    
    
}
