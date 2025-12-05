package manager_pls5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Users {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;
    
    private final String UDATA_FILE = "/manager_pls5/UData.txt";
    
    
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Button clicked!");
    }
    
    @FXML
    private void handleLoginButton(ActionEvent event) {
        String inputEmail = emailField.getText().trim();
        String inputPassword = passwordField.getText().trim();

     
        
        
        
        if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
            showAlert("خطأ", "الرجاء إدخال البريد الإلكتروني وكلمة المرور.", Alert.AlertType.WARNING);
            return;
        }

        
        if (!isValidEmail(inputEmail)) {
            showAlert("خطأ", "صيغة البريد الإلكتروني غير صحيحة.", Alert.AlertType.WARNING);
            return;
        }

        // 3. البحث عن المستخدم
        boolean loginSuccess = isUserValid(inputEmail, inputPassword);

        if (loginSuccess) {
          
            openMainScreen(event); 
            
        } else {
            showAlert("فشل التسجيل", "البريد الإلكتروني أو كلمة المرور غير صحيحة.", Alert.AlertType.ERROR);
        }
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
    
    
    
    private boolean isUserValid(String email, String password) {
    
    String[][] users = {
        {"user1@app.com", "1234"},
        {"manager@app.com", "4321"}, 
        {"admin@app.com", "9876"}
    };
    
    for (String[] user : users) {
        if (user[0].equalsIgnoreCase(email) && user[1].equals(password)) {
            return true;
        }
    }
    return false;
}
    
    private void openMainScreen(ActionEvent event) {
    try {
        System.out.println("Attempting to load TRS.fxml...");
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/manager_pls5/Screen/TRS.fxml"));
        System.out.println("FXML Loader created");
        
        Parent root = loader.load();
        System.out.println("FXML loaded successfully");
        
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        System.out.println("Stage obtained");
        
        Scene scene = new Scene(root, 1270.0, 855.0);
        stage.setScene(scene);
        stage.setTitle("Main Application Dashboard");
         stage.centerOnScreen();
        stage.show();
        
        System.out.println("Main screen opened successfully");
        
    } catch (IOException e) {
        e.printStackTrace();
        System.err.println("Error details: " + e.getMessage());
        showAlert("خطأ", "تعذر تحميل الشاشة الرئيسية: " + e.getMessage(), Alert.AlertType.ERROR);
    }
}
   
    
    
    
    
    
    
    // دالة مساعدة للانتقال إلى شاشة التسجيل إذا كنت تحتاجها
    @FXML
    private void handleRegisterButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Screen/Register.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("خطأ", "تعذر تحميل شاشة التسجيل.", Alert.AlertType.ERROR);
        }
    }

    // الدالة المساعدة لعرض التنبيهات
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}