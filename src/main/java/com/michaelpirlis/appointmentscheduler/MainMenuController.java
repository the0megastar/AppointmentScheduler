package com.michaelpirlis.appointmentscheduler;

import com.michaelpirlis.appointmentscheduler.model.Appointment;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.michaelpirlis.appointmentscheduler.dao.AppointmentSQL.allAppointments;

public class MainMenuController extends Application implements Initializable {

    @FXML private TableColumn<Object, Object> appointmentIdColumn;
    @FXML private TableColumn<Object, Object> appointmentTitleColumn;
    @FXML private TableColumn<Object, Object> appointmentDescriptionColumn;
    @FXML private TableColumn<Object, Object> appointmentLocationColumn;
    @FXML private TableColumn<Object, Object> appointmentContactColumn;
    @FXML private TableColumn<Object, Object> appointmentTypeColumn;
    @FXML private TableColumn<Object, Object> appointmentStartColumn;
    @FXML private TableColumn<Object, Object> appointmentEndColumn;
    @FXML private TableView<Appointment> allAppointmentTable;

    @FXML private TableColumn<Object, Object> customerIdColumn;
    @FXML private TableColumn<Object, Object> customerNameColumn;
    @FXML private TableColumn<Object, Object> customerStreetColumn;
    @FXML private TableColumn<Object, Object> customerStateColumn;
    @FXML private TableColumn<Object, Object> customerCountryColumn;
    @FXML private TableColumn<Object, Object> customerPostalColumn;
    @FXML private TableColumn<Object, Object> customerPhoneColumn;
    @FXML private TableView<Appointment> allCustomerTable;

    @FXML private ComboBox<String> appointmentFilter;
    @FXML private Button addAppointmentButton;
    @FXML private Button modifyAppointmentButton;
    @FXML private Button deleteAppointmentButton;
    @FXML private Button addCustomerButton;
    @FXML private Button modifyCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button reportsButton;
    @FXML private Button exitButton;


    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> options = FXCollections.observableArrayList("All", "Weekly", "Monthly");
        appointmentFilter.setItems(options);
        appointmentFilter.getSelectionModel().selectFirst();

        appointmentTableSetup(allAppointmentTable, appointmentIdColumn, appointmentTitleColumn,
                appointmentDescriptionColumn, appointmentLocationColumn, appointmentContactColumn,
                appointmentTypeColumn, appointmentStartColumn, appointmentEndColumn);
    }

    static void appointmentTableSetup(TableView<Appointment> allAppointmentTable,
                                      TableColumn<Object, Object> appointmentIdColumn,
                                      TableColumn<Object, Object> appointmentTitleColumn,
                                      TableColumn<Object, Object> appointmentDescriptionColumn,
                                      TableColumn<Object, Object> appointmentLocationColumn,
                                      TableColumn<Object, Object> appointmentContactColumn,
                                      TableColumn<Object, Object> appointmentTypeColumn,
                                      TableColumn<Object, Object> appointmentStartColumn,
                                      TableColumn<Object, Object> appointmentEndColumn) {
        allAppointmentTable.setItems(allAppointments());
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        appointmentContactColumn.setCellValueFactory(new PropertyValueFactory<>("customerContact"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
    }

    /**
     * Loads the inventory FXML file for the application, sets up the scene, and displays the primary stage.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenuController.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void exitButtonClicked() {
        Platform.exit();
    }

}
