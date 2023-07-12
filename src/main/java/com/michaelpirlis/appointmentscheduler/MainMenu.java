package com.michaelpirlis.appointmentscheduler;

import com.michaelpirlis.appointmentscheduler.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** The Main Menu class launches the main GUI of the Appointment Scheduler system. */
public class MainMenu extends Application implements Initializable {

    /** This method loads the main-menu FXML file for the application, sets up the scene, and displays the primary stage.*/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /** Utilized to initialize prior to the start of the stage. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /** Opens the Database connection, launches main arguments, closes the connection after the main has ended. */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}