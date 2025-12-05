/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package manager_pls5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author heroh
 */
public class Manager_pls5 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception { 
        
        
Parent root = FXMLLoader.load(getClass().getResource("/manager_pls5/Screen/TRS.fxml"));

  stage.initStyle(StageStyle.UNDECORATED);
             Scene scene = new Scene(root);
             
              stage.setScene(scene);
              stage.centerOnScreen();
                   
        stage.show();
      
    }
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
