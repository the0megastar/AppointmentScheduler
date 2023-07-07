module com.michaelpirlis.appointmentscheduler {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.michaelpirlis.appointmentscheduler to javafx.fxml;
    exports com.michaelpirlis.appointmentscheduler;
}