/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager_pls5.Screen;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class DolEmployes implements Initializable{
    
      @FXML
    private ChoiceBox<String> EmployesChoiceBox; 
    @FXML
    private Button checkOutButton;
    @FXML
    private Button checkInButton; 
    @FXML
    private Label TMN;
    @FXML
    private Label currentDateLabel;
    
    
    
    
      @Override
     public void initialize(URL location, ResourceBundle resources) 
    {
        EmployesChoiceBox.getItems().addAll("أحمد هزاع", "سارة محمد", "خالد فهد" ,"محمد نور");
    
        updateDateTimeLabels();
    }
     
     
     
     private void updateDateTimeLabels() {
DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a"); 
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    TMN.setText(LocalTime.now().format(timeFormatter));
    currentDateLabel.setText(LocalDate.now().format(dateFormatter));
    } 
    
    
    
    @FXML
    private void handleCheckAction(ActionEvent event) {
       String employeeName = EmployesChoiceBox.getSelectionModel().getSelectedItem();
    if (employeeName == null) {
        JOptionPane.showMessageDialog(null, "الرجاء اختيار اسم الموظف أولاً.", "خطأ", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String actionType;
    if (event.getSource() == checkInButton) {
        actionType = "دخول";
    } else if (event.getSource() == checkOutButton) {
        actionType = "خروج";
    } else {
        return;
    }

    
    
    
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a"); 
    String currentTime = LocalTime.now().format(timeFormatter);
    
    
    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    
    String dataLine = employeeName + "," + actionType + "," + currentTime + "," + currentDate + "\n";
    
        try (FileWriter fw = new FileWriter("EData.txt", true)) {
    
            fw.write(dataLine);

    
            JOptionPane.showMessageDialog(null, "تم تسجيل عملية " + actionType + " للموظف: " + employeeName + " بنجاح.", "نجاح", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
    
            JOptionPane.showMessageDialog(null, "حدث خطأ أثناء حفظ البيانات في الملف: " + e.getMessage(), "خطأ في الحفظ", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

     
     public void switchToTRSScreen4(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/Atend.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); 
    } catch (IOException e) {
         System.err.println("خطأ في تحميل الواجهة: " + e.getMessage());
    }

     }
    
    
    
   
    
    
    
    
    
    
    
}
