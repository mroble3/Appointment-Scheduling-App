package controller;

import database.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Controller for appointment scheduling.
 */

public class SchedulerController2 implements Initializable {

    @FXML
    private Label headerTag;
    @FXML
    private Label headerDescLabel;
    @FXML
    private TextField idText;
    @FXML
    private TextField titleText;
    @FXML
    private TextField descText;
    @FXML
    private TextField locText;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private TextField typeField;
    @FXML
    private DatePicker startPicker;
    @FXML
    private ComboBox<String> startCombo;
    @FXML
    private DatePicker endPicker;
    @FXML
    private ComboBox<String> endCombo;
    @FXML
    private ComboBox<Customer> customerCombo;
    @FXML
    private ComboBox<User> userCombo;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;

    private boolean update;

    /**
     * Below is the Initialize of SchedulerController2.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCombo.setItems(DBCustomer.getAll());
        contactCombo.setItems(DBContact.getAll());
        userCombo.setItems(DBUser.getAll());
        userCombo.getSelectionModel().select(DBUser.getCurrentUser());

        ObservableList<String> times = DBAppointment.getValidTimes();

        startCombo.setItems(times);
        endCombo.setItems(times);

        update = false;

// lambda expression
        cancelButton.setOnAction(event -> {
            if (DialogSender.confirm((update ? "Update" : "Schedule") + " Appointment", "Are you sure you want to cancel " + (update ? "updating" : "scheduling") + " an appointment?")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/View.fxml"));
                    Scene scene = new Scene(loader.load());
                    ((ViewController)loader.getController()).setTab(0);
                    Stage stage = Main.getStage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Below will populate/create the form with appointment information.
     */
    public void populate(Appointment a) {
        if (a == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/View.fxml"));
                Scene scene = new Scene(loader.load());
                ((ViewController)loader.getController()).setTab(1);
                Stage stage = Main.getStage();
                stage.setScene(scene);
                stage.show();
                DialogSender.error("Update Appointment", "The selected appointment variable is not initialized. Try again.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        headerTag.setText("Update Appointment");
        headerDescLabel.setText("Enter the updated information for the appointment.");

        LocalDateTime start = a.getStart();
        LocalDateTime end = a.getEnd();

        idText.setPromptText(Integer.toString(a.getId()));
        titleText.setText(a.getTitle());
        descText.setText(a.getDescription());
        locText.setText(a.getLocation());
        contactCombo.getSelectionModel().select(DBContact.getContact(a.getContactId()));
        typeField.setText(a.getType());
        startPicker.setValue(start.toLocalDate());
        endPicker.setValue(end.toLocalDate());
        customerCombo.getSelectionModel().select(DBCustomer.getCustomer(a.getCustomerId()));
        userCombo.getSelectionModel().select(DBUser.getUser(a.getUserId()));

        LocalTime startTime = start.toLocalTime();
        LocalTime endTime = end.toLocalTime();

        startCombo.getSelectionModel().select(String.format("%02d", startTime.getHour()) + ":" + String.format("%02d", startTime.getMinute()));
        endCombo.getSelectionModel().select(String.format("%02d", endTime.getHour()) + ":" + String.format("%02d", endTime.getMinute()));

        update = true;
    }

    /**
     * Below validates the input and applies updates to appointments.
     */
    public void onSubmit(ActionEvent actionEvent) {
        errorLabel.setText("");
        if (titleText.getText().isBlank() || descText.getText().isBlank() || locText.getText().isBlank()
                || contactCombo.getSelectionModel().getSelectedItem() == null || typeField.getText().isBlank()
                || startPicker.getValue() == null || startCombo.getSelectionModel().getSelectedItem() == null
                || endPicker.getValue() == null || endCombo.getSelectionModel().getSelectedItem() == null
                || customerCombo.getSelectionModel().getSelectedItem() == null || userCombo.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Remember to complete all fields.");
            return;
        }
        LocalDateTime start = LocalDateTime.of(startPicker.getValue(), LocalTime.parse(startCombo.getSelectionModel().getSelectedItem()));
        LocalDateTime end = LocalDateTime.of(endPicker.getValue(), LocalTime.parse(endCombo.getSelectionModel().getSelectedItem()));
        if (end.isBefore(start) || end.isEqual(start)) {
            errorLabel.setText("The End time has to occur after start time.");
            return;
        }
        if (update ? DBAppointment.overlaps(customerCombo.getSelectionModel().getSelectedItem().getId(), start, end, Integer.parseInt(idText.getPromptText())) : DBAppointment.overlaps(customerCombo.getSelectionModel().getSelectedItem().getId(), start, end)) {
            errorLabel.setText("The existing appointment/appointments for the customer are overlapping with this Time.");
            return;
        }
        String title = titleText.getText();
        String desc = descText.getText();
        String loc = locText.getText();
        String type = typeField.getText();
        int custId = customerCombo.getSelectionModel().getSelectedItem().getId();
        int userId = userCombo.getSelectionModel().getSelectedItem().getId();
        int contId = contactCombo.getSelectionModel().getSelectedItem().getId();

        int id;
        if (update) {
            id = Integer.parseInt(idText.getPromptText());
            if (DBAppointment.update(id, title, desc, loc, type, start, end, custId, userId, contId) == -1) {
                DialogSender.error("Update Appointment", "Appointment could not be updated in the database.");
                return;
            }
        } else {
            id = DBAppointment.add(title, desc, loc, type, start, end, custId, userId, contId);
            if (id == -1) {
                DialogSender.error("Schedule Appointment", "Appointment could not be add to the database!");
                return;
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/View.fxml"));
            Scene scene = new Scene(loader.load());
            ((ViewController)loader.getController()).setTab(0);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
            DialogSender.inform("Schedule Appointment", "Appointment successfully " + (update ? "updated" : "scheduled") + " at ID " + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}