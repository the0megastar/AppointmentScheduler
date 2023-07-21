package com.michaelpirlis.appointmentscheduler.controller;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.michaelpirlis.appointmentscheduler.MainMenuController.displayScene;

public class AppointmentUpdateController extends AppointmentAddController implements Initializable {

    @Override
    public void start(Stage stage) throws IOException {
        displayScene("appointment-update.fxml", stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
