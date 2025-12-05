package manager_pls5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Revarsion implements Initializable {

   
    @FXML private TextField clientNameField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField RoomIDField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> startHourCombo;
    @FXML private ComboBox<String> startMinuteCombo;
    @FXML private ComboBox<String> endHourCombo;
    @FXML private ComboBox<String> endMinuteCombo;

    final String FILENAME = "D:\\javaprj\\Manager_pls5\\Revarsion.txt";

     
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

     
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

     
        ObservableList<String> hours = FXCollections.observableArrayList();
        IntStream.rangeClosed(0, 23).forEach(i -> hours.add(String.format("%02d", i)));

        ObservableList<String> minutes = FXCollections.observableArrayList();
        IntStream.rangeClosed(0, 59).forEach(i -> minutes.add(String.format("%02d", i)));

        startHourCombo.setItems(hours);
        endHourCombo.setItems(hours);
        startMinuteCombo.setItems(minutes);
        endMinuteCombo.setItems(minutes);

     
    }

    
    @FXML
    public void handleCanceled(ActionEvent event) {

        clientNameField.clear();
        phoneNumberField.clear();
        datePicker.setValue(null); //Date 
        startHourCombo.getSelectionModel().clearSelection();
        startMinuteCombo.getSelectionModel().clearSelection();
        endHourCombo.getSelectionModel().clearSelection();
        endMinuteCombo.getSelectionModel().clearSelection();

        showAlert(Alert.AlertType.INFORMATION, "تم الإلغاء", "تم مسح البيانات");

         switchToTRSScreen3(event);
    }

   
    @FXML
    public void handleSave(ActionEvent event) {

        String client = clientNameField.getText().trim();
        String phone = phoneNumberField.getText().trim();
        String room = RoomIDField.getText().trim();
         

        if (client.isEmpty() || phone.isEmpty() || room.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "خطأ", "يرجى ملء كل الحقول.");
            return;
        }

        if (datePicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "خطأ", "اختر التاريخ.");
            return;
        }

        if (startHourCombo.getValue() == null || startMinuteCombo.getValue() == null ||
            endHourCombo.getValue() == null || endMinuteCombo.getValue() == null) {

            showAlert(Alert.AlertType.ERROR, "خطأ", "حدد وقت البداية والنهاية.");
            return;
        }

        try {

            String date = datePicker.getValue().toString();

            String startStr = date + " " + startHourCombo.getValue() + ":" + startMinuteCombo.getValue();
            String endStr = date + " " + endHourCombo.getValue() + ":" + endMinuteCombo.getValue();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");   //  "2025-12-05 09:30"
            
                  

            var startDT = java.time.LocalDateTime.parse(startStr, formatter);
            var endDT = java.time.LocalDateTime.parse(endStr, formatter);

//            becouse conver frome string \ any think to >> obj
            
            
            if (endDT.isBefore(startDT))
                endDT = endDT.plusDays(1);   //             becouse res..at night


            long totalMinutes = java.time.Duration.between(startDT, endDT).toMinutes();
 
            String line = String.format("%s,%s,%s,%s,%s,%d%n",   
                    room, client, phone,
                    startDT.format(formatter),
                    endDT.format(formatter),
                    totalMinutes
            );
                          
                                                                                  // btw...  d% = long \ int 
            
            
            if (saveBookingToFile(line)) {

                showAlert(Alert.AlertType.INFORMATION, "نجاح",
                        "تم الحجز بنجاح!\nالمدة: " + totalMinutes + " دقيقة");

              switchToTRSScreen3(event);
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "خطأ", "خطأ في تحليل الوقت.");
        }
    }

    
    private boolean saveBookingToFile(String dataLine) {

        try (FileWriter writer = new FileWriter(FILENAME, true)) {
            writer.write(dataLine);
            return true;

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "خطأ", "فشل حفظ الملف.");
            return false;
        }
    }
    
    
    
    
    
    
    public void switchToTRSScreen3(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/TRS.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); // ✅ استبدال الجذر
    } catch (IOException e) {
    }
}
    
    
     
    
    
}
