package manager_pls5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController implements Initializable {

    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> idCol;
    @FXML private TableColumn<Booking, String> nameCol;
    @FXML private TableColumn<Booking, String> startCol;
    @FXML private TableColumn<Booking, String> endCol;
    @FXML private TableColumn<Booking, String> roomCol;
    @FXML private TableColumn<Booking, String> phoneCol;
    @FXML private TableColumn<Booking, String> dateCol;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (idCol != null && nameCol != null && startCol != null && 
            endCol != null && roomCol != null && phoneCol != null && bookingTable != null) {
            
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

            loadBookingsFromFile();
        }
    }
    
    
    public static class Booking {
        private String id;
        private String name;
        private String start;
        private String end;
        private String room;
        private String phone;
        private String date;

        public Booking(String id, String name, String date, String start, String end, String room, String phone) {
            this.id = id;
            this.name = name;
            this.start = start;
            this.date = date;
            this.end = end;
            this.room = room;
            this.phone = phone;
            
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getStart() { return start; }
        public String getEnd() { return end; }
        public String getRoom() { return room; }
        public String getPhone() { return phone; }
        public String getDate() { return date; }
    }

    
    private void loadBookingsFromFile() {
               ObservableList<Booking> bookings = FXCollections.observableArrayList();
    DateTimeFormatter formatterIn = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm"); 
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 

    try (BufferedReader reader = new BufferedReader(new FileReader("Revarsion.txt"))) {
        String line;
        int id = 1;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");

            if (parts.length >= 5) {
                
                String room = parts[0];
                String name = parts[1];
                String phone = parts[2];
                String startStr = parts[3];
                String endStr = parts[4];

                LocalDateTime startDT = LocalDateTime.parse(startStr, formatterIn);
                LocalDateTime endDT = LocalDateTime.parse(endStr, formatterIn);
                
                String dateString = startDT.format(formatterDate); 

                bookings.add(new Booking(
                    String.valueOf(id++),
                    name,
                    dateString,
                    startDT.format(formatterTime),
                    endDT.format(formatterTime),   
                    room,
                    phone
                ));
            }
        }
    } catch (Exception e) {
        System.out.println("Error loading reservations: " + e.getMessage());
    }

    bookingTable.setItems(bookings);
}
 
     
     
    public void handleButtonAction(MouseEvent event) {}

    public void handleCancelOrder(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

 
@FXML
public void Roml(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/RoomList.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); 
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@FXML
public void Atd(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/Atend.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); 
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@FXML
public void AdR(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/AddRooms.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root);  
        
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void Rizz(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/NewR.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); 
    } catch (Exception e) {
        e.printStackTrace();
    }
}
@FXML
private void openPaymentScreen(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/PaymentScreen.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); 
    } catch (Exception e) {
        e.printStackTrace();
    }
}


@FXML
public void swRSScreen(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/Histry.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); 
    } catch (Exception e) {
        e.printStackTrace();
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
  public void switchToTRSScreen2(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/PaymentScreen.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); 
    } catch (Exception e) {
        e.printStackTrace();
    }
}   
  public void switchToTRSScreen5(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/siting.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.getScene().setRoot(root); 
    } catch (Exception e) {
        e.printStackTrace();
    }
}     
    
}