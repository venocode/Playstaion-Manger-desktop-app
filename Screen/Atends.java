/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager_pls5.Screen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import manager_pls5.FXMLDocumentController.Booking;

/**
 *
 * @author heroh
 */
public class Atends implements Initializable {

     
     

    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> id;
    @FXML private TableColumn<Booking, String> name;
    @FXML private TableColumn<Booking, String> InTime;
    @FXML private TableColumn<Booking, String> outTime;
    @FXML private TableColumn<Booking, String> Date;
    @FXML private TableColumn<Booking, String> workH;
    
    
    @FXML private ChoiceBox<String> employeeChoiceBox; 

    private static final String DATA_FILE = "EData.txt"; 

    

    public static class Booking {
        private String id;
        private String name;
        private String InTime; // وقت الدخول
        private String outTime; // وقت الخروج
        private String Date;    // التاريخ
        private String workH;   // ساعات العمل

        public Booking(String id, String name, String inTime, String outTime, String date, String workH) {
            this.id = id;
            this.name = name;
            this.InTime = inTime;
            this.outTime = outTime;
            this.Date = date;
            this.workH = workH;
        }


        public String getId() { return id; }
        public String getName() { return name; }
        public String getInTime() { return InTime; }
        public String getOutTime() { return outTime; }
        public String getDate() { return Date; }
        public String getWorkH() { return workH; }
    }
    
    
    
    @Override
     public void initialize(URL location, ResourceBundle resources) 
    {
       
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        InTime.setCellValueFactory(new PropertyValueFactory<>("inTime")); // يجب أن يتطابق مع اسم Getter: getInTime()
        outTime.setCellValueFactory(new PropertyValueFactory<>("outTime")); // يجب أن يتطابق مع اسم Getter: getOutTime()
        Date.setCellValueFactory(new PropertyValueFactory<>("date")); // يجب أن يتطابق مع اسم Getter: getDate()
        workH.setCellValueFactory(new PropertyValueFactory<>("workH")); // يجب أن يتطابق مع اسم Getter: getWorkH()

       
        bookingTable.setItems(FXCollections.observableArrayList(readAndProcessData()));
    }
    
    
    
    
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

        private String calculateWorkHours(String timeInFull, String timeOutFull) {
        try {
            LocalDateTime entry = LocalDateTime.parse(timeInFull, FORMATTER);
            LocalDateTime exit = LocalDateTime.parse(timeOutFull, FORMATTER);
           
            Duration duration = Duration.between(entry, exit);
           
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            
            return String.format("%d س %d د", hours, minutes);
        } catch (Exception e) {
            return "خطأ";
        }
    } 
 
        
    private List<Booking> readAndProcessData() {
        
        Map<String, Map<String, String>> dailyAttendance = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String name = parts[0].trim();
                    String type = parts[1].trim();
                    String time = parts[2].trim();
                    String date = parts[3].trim();
                    String fullTime = date + " " + time; 
                    String key = name + "_" + date;

                    dailyAttendance.putIfAbsent(key, new HashMap<>());
                    
                    if (type.equals("دخول")) {
                        dailyAttendance.get(key).put("Name", name);
                        dailyAttendance.get(key).put("Date", date);
                        dailyAttendance.get(key).put("InFull", fullTime);
                        dailyAttendance.get(key).put("InDisplay", time);
                    } else if (type.equals("خروج")) {
                        dailyAttendance.get(key).put("OutFull", fullTime);
                        dailyAttendance.get(key).put("OutDisplay", time);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("خطأ في قراءة الملف: " + e.getMessage());
            return new ArrayList<>();
        }
        
        List<Booking> finalRecords = new ArrayList<>();
        int idCounter = 1; 
        
        for (Map.Entry<String, Map<String, String>> entry : dailyAttendance.entrySet()) {
            Map<String, String> data = entry.getValue();
            
            String name = data.getOrDefault("Name", "غير معروف");
            String date = data.getOrDefault("Date", "غير متوفر");
            String timeInFull = data.get("InFull");
            String timeOutFull = data.get("OutFull");
            
            String timeInDisplay = data.getOrDefault("InDisplay", "غير متوفر");
            String timeOutDisplay = data.getOrDefault("OutDisplay", "غير متوفر");

            String totalHours = "غير مكتمل";

            if (timeInFull != null && timeOutFull != null) {
                totalHours = calculateWorkHours(timeInFull, timeOutFull);
            }

            finalRecords.add(new Booking(
                String.valueOf(idCounter++),
                name, 
                timeInDisplay, 
                timeOutDisplay, 
                date,
                totalHours
            ));
        }
        return finalRecords;
    }
     
 
     
     @FXML
      public void refreshTable(ActionEvent event) {
       
//        bookingTable.getItems().clear(); 
//        
//        
//        List<Booking> newData = readAndProcessData();
//        
//         
//        bookingTable.setItems(FXCollections.observableArrayList(newData));
//        
        // (اختياري) يمكنك إضافة رسالة تنبيه بسيطة
        System.out.println("we wil worked");
    }
      
     
     
     
     
     public void switchToTRSScreen(ActionEvent event) {
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
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/DolEmployes.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        currentStage.getScene().setRoot(root); //   استبدال الجذر
    } catch (Exception e) {
        e.printStackTrace();
    }
}
     
     
     
}