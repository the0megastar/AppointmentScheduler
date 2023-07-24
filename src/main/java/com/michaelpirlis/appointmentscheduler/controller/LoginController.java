package com.michaelpirlis.appointmentscheduler.controller;

import com.michaelpirlis.appointmentscheduler.model.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static com.michaelpirlis.appointmentscheduler.controller.MainMenuController.displayScene;
import static com.michaelpirlis.appointmentscheduler.dao.UserSQL.getUser;
import static com.michaelpirlis.appointmentscheduler.dao.UserSQL.loginCheck;

public class LoginController extends Application implements Initializable {

    private static final ResourceBundle messages = ResourceBundle.getBundle("com.michaelpirlis.appointmentscheduler.MessagesBundle", Locale.getDefault());
    public static int currentUserID;
    public static String currentUsername;
    private Logger log;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label zoneLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button exitButton;
    @FXML
    private Button loginButton;
    private String alertMessage;

    @Override
    public void start(Stage stage) throws IOException {
        displayScene("login.fxml", stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activityLogger();
        setLanguage();
        systemTime(zoneLabel);
    }

    // from code repository. Changed to all Levels with level.INFO
    private void activityLogger() {
        log = Logger.getLogger("login_activity.txt");

        try {
            FileHandler fh = new FileHandler("login_activity.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.addHandler(fh);
            log.setLevel(Level.INFO); // Log all info level messages

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void systemTime(Label timeLabel) {
        ZoneId currentZone = ZoneId.systemDefault();
        timeLabel.setText(currentZone.getId());
    }

    private String formatTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId zoneId = ZoneId.systemDefault();
        String currentTime = ZonedDateTime.now(zoneId).format(formatter);

        return currentTime + " [" + zoneId.getId() + "]";
    }

    private void setLanguage() {
        // Set the text of the labels and buttons
        usernameLabel.setText(messages.getString("usernameLabel"));
        passwordLabel.setText(messages.getString("passwordLabel"));
        usernameField.setPromptText(messages.getString("usernamePrompt"));
        passwordField.setPromptText(messages.getString("passwordPrompt"));
        exitButton.setText(messages.getString("exitButton"));
        loginButton.setText(messages.getString("loginButton"));
        alertMessage = messages.getString("alertMessage");
    }

    private void incorrectLogin() {
        Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
        noSelection.setTitle("No Selection");
        noSelection.setHeaderText(null);
        noSelection.setContentText(alertMessage);
        noSelection.showAndWait();
    }

    public void loginButton(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean loginSuccess = loginCheck(username, password);
        String currentTime = formatTime();

        if (!loginSuccess) {
            log.info("Unsuccessful login attempt by user '" + username + "' at " + currentTime);
            incorrectLogin();

        } else {
            log.info("Successful login by user '" + username + "' at " + currentTime);

            User currentUser = getUser(username);
            currentUserID = Objects.requireNonNull(currentUser).getUserID();
            currentUsername = Objects.requireNonNull(currentUser).getUsername();

            MainMenuController.appointmentCheck();

            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            displayScene("main-menu.fxml", appStage);
        }
    }

    @FXML
    private void exitButton() {
        Platform.exit();
    }

}
