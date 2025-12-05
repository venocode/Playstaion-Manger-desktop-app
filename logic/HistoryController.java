package manager_pls5.Screen;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HistoryController implements Initializable {

    @FXML private TableView<HistoryRecord> historyTable;
    @FXML private TableColumn<HistoryRecord, String> colId;
    @FXML private TableColumn<HistoryRecord, String> colRoomType;
    @FXML private TableColumn<HistoryRecord, String> colDuration;
    @FXML private TableColumn<HistoryRecord, String> colDate;
    @FXML private TableColumn<HistoryRecord, String> colStartTime;
    @FXML private TableColumn<HistoryRecord, String> colJoysticks;

    private ObservableList<HistoryRecord> historyData = FXCollections.observableArrayList();

     public static class HistoryRecord {
        private final String id, roomType, duration, date, startTime, joysticks;
       
        public HistoryRecord(String id, String roomType, String duration, String date, String startTime, String joysticks) {
            this.id = id; this.roomType = roomType; this.duration = duration;
            this.date = date; this.startTime = startTime; this.joysticks = joysticks;
        }
        public String getId() { return id; }
        public String getRoomType() { return roomType; }
        public String getDuration() { return duration; }
        public String getDate() { return date; }
        public String getStartTime() { return startTime; }
        public String getJoysticks() { return joysticks; }
    }
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        loadHistoryData();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colJoysticks.setCellValueFactory(new PropertyValueFactory<>("joysticks"));
        
        historyTable.setItems(historyData);
    }

    
    
    
    private void loadHistoryData() {
        try {
            File file = new File("HData.txt");
            if (!file.exists()) {
                System.out.println("HData.txt file not found");
                return;
            }

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    HistoryRecord record = parseHistoryRecord(line);
                    if (record != null) {
                        historyData.add(record);
                    }
                }
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error reading HData.txt: " + e.getMessage());
        }
    }

 private HistoryRecord parseHistoryRecord(String dataLine) {
    try {
        String[] parts = dataLine.split("\\s+");
        
        if (parts.length >= 6) {
            String id = parts[0];
            String roomType = parts[1];
            String duration = parts[2];
            String joysticks = parts[3];
            String date = parts[4];
            String startTime = parts[5];
            
            
            startTime =  startTime;
            
            return new HistoryRecord(id, roomType, duration, date, startTime, joysticks);
        }
    } catch (Exception e) {
        System.out.println("Error parsing history record: " + e.getMessage());
    }
    return null;
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
}