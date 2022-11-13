package controller;

import database.DialogSender;
import database.DBAppointment;
import database.DBUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Appointment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for Login screen.
 *
 */
public class LoginController implements Initializable {

    @FXML
    private Label loginTag;
    @FXML
    private Label usernameTag;
    @FXML
    private Label passwordTag;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
    @FXML
    private Label locationLabel;
    @FXML
    private Label zoneIdLabel;

    private ResourceBundle rb;

    /**
     * LoginController Initializer.
     * Method finds user's location.
     * @param resourceBundle used for language translation
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rb = resourceBundle;

        ZoneId zoneId = ZoneId.systemDefault();
        zoneIdLabel.setText(zoneId.toString());
        loginTag.setText(resourceBundle.getString("loginButtonText"));
        usernameTag.setText(resourceBundle.getString("usernameTag"));
        passwordTag.setText(resourceBundle.getString("passwordTag"));
        submitButton.setText(resourceBundle.getString("submit"));
        locationLabel.setText(resourceBundle.getString("locationLabel"));
    }

    /**
     * submit button click input logged here.
     * When the Login is successful it will go to the View screen and display any appointments that are upcoming.
     *
     * @param actionEvent action event
     */
    public void onSubmit(ActionEvent actionEvent) {
        System.out.println("Login information being Submitted.");
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            log(false, username, password);
            DialogSender.error(rb.getString("title"), rb.getString("emptyFields"));
            System.out.println("Cancelling Login as some fields are empty.");
            return;
        }
        boolean valid = DBUser.getUser(username) != null;
        if (!valid) {
            log(false, username, password);
            DialogSender.error(rb.getString("title"), rb.getString("usernameNotFound"));
            System.out.println("Cancelling login as Username was not found.");
            return;
        }
        boolean matches = DBUser.passwordMatches(username, password);
        log(matches, username, password);
        if (!matches) {
            DialogSender.error(rb.getString("title"), rb.getString("passwordIncorrect"));
            System.out.println("Cancelling Login as Password is incorrect. Username was located.");
            return;
        }
        DBUser.setCurrentUser(DBUser.getUser(username));
        System.out.println("Username and password match! Logging in...");
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/View.fxml")));
            Scene scene = new Scene(root);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.setTitle("Scheduling System");
            stage.show();
            showUpcoming();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void initialize(){
//        ZoneIDField.setText(ZoneId.systemDefault().getId());
//        FxError.setVisible(false);
//    }



    /**
     * Shows upcoming appointment dialog box if there are any.
     * Lambda expression (forEach) was used to include any upcoming appointments
     */
    public void showUpcoming() {
        ObservableList<Appointment> upcoming = DBAppointment.getUpcoming();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        if (upcoming.size() == 0) {
            DialogSender.inform("Appointments", "There are no upcoming appointments to display.");
            return;
        }
        StringBuilder alert = new StringBuilder();
        alert.append(upcoming.size() > 1 ? "There are " + upcoming.size() + " upcoming appointments:\n" : "There is a single upcoming appointment:\n");

        //lambda expression location is here
        upcoming.forEach(appt -> alert.append("\n\t").append(appt.getId()).append(" - '").append(appt.getTitle()).append("' at ").append(appt.getStart().format(dtf)));

        DialogSender.warn("Appointments", alert.toString());
    }

    /**
     * Documents login attempts details log file.
     *
     * @param username the username for the login try
     * @param password the password for the login try
     * @param success true if login Achieved, false if not.
     */
    private void log(boolean success, String username, String password) {
        try {
            FileWriter fw = new FileWriter("login_activity.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
            String time = LocalDateTime.now().format(dtf);
            pw.println(time + (success ? " [SUCCESS] " : " [FAILURE] ") + "username: \"" + username + "\", password: \"" + password + "\"");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}