package com.satyam.inventorymanagementsystem;

import java.io.IOException;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
 public  Stage stage;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    
    
    
    
    @FXML
    private void login() throws IOException {
        
       try{
        DAO dao=new DAO();
        
        User dbUser=dao.getUser(username.getText());
        if(dbUser==null)
        {
           Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid username");
        alert.showAndWait();  
        }
        else
        {
            User user=new User();
            user.setPassword(password.getText());
            if(!(dbUser.getPassword().equals(user.getPassword())))
            {Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Password");
            alert.showAndWait();  
            }
            else
            {
                dbUser.setLastLogin(new Date());
                dao.addNewUser(dbUser);
                
            App.stage.setWidth(1280);
            App.stage.setHeight(720);
            App.stage.setX(30);
            App.stage.setY(20);
            App.stage.setTitle("Inventory Management System");
            User.user.setUsername(dbUser.getUsername());
            User.user.setAccessType(dbUser.getAccessType());
            App.setRoot("main");       
            }
        }
       }catch(Exception e)
       {
           System.err.println(e);
           Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.toString());
            alert.showAndWait(); 
       }
      
      
        
    }
}
