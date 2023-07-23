package com.michaelpirlis.appointmentscheduler;

import com.michaelpirlis.appointmentscheduler.controller.MainMenuController;
import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login extends Application implements Initializable {

    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
    /**
     * This method loads the main-menu FXML file for the application,
     * sets up the scene, and displays the primary stage.
     */
    @Override
    public void start(Stage stage) throws IOException {
        MainMenuController.displayScene("login.fxml", stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
