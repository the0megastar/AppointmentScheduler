package com.michaelpirlis.appointmentscheduler.controller;

import com.michaelpirlis.appointmentscheduler.MainMenuController;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentAddController extends MainMenuController implements Initializable {

    @Override
    public void start(Stage stage) throws IOException {
        displayScene("appointment-add.fxml", stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
