module com.michaelpirlis.appointmentscheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.michaelpirlis.appointmentscheduler to javafx.fxml;
    exports com.michaelpirlis.appointmentscheduler;
    exports com.michaelpirlis.appointmentscheduler.controller;
    opens com.michaelpirlis.appointmentscheduler.controller to javafx.fxml;
    opens com.michaelpirlis.appointmentscheduler.model to javafx.base;
}