package controller;

import database.DialogSender;
import database.DBAppointment;
import database.DBContact;
import database.DBCustomer;
import database.DBUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * controller for View screen.
 */
public class ViewController implements Initializable {

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab customerTab;
    @FXML
    private Tab reportTab;
    @FXML
    private Tab apptTab;
    
    // Appointment tab
    @FXML
    private javafx.scene.control.TableView<Appointment> appointmentTable;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, Integer> apptIdColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptTitleColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptDescriptionColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptLocationColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptContactColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptTypeColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptStartColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptEndColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, Integer> apptCustomerCol;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, Integer> apptUserColumn;
    @FXML
    private RadioButton aViewAllRadio;
    @FXML
    private ToggleGroup aViewByToggle;
    @FXML
    private RadioButton aViewMonthRadio;
    @FXML
    private RadioButton aViewWeekRadio;
    @FXML
    private Button aScheduleButton;
    @FXML
    private Button aUpdateButton;
    @FXML
    private Button aCancelButton;

    // Customer tab
    @FXML
    private javafx.scene.control.TableView<Customer> customerTable;
    @FXML
    private javafx.scene.control.TableColumn<Customer, String> customerAddressColumn;
    @FXML
    private javafx.scene.control.TableColumn<Customer, String> customerPostalColumn;
    @FXML
    private javafx.scene.control.TableColumn<Customer, String> customerPhoneColumn;
    @FXML
    private javafx.scene.control.TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private javafx.scene.control.TableColumn<Customer, String> customerNameColumn;
    @FXML
    private javafx.scene.control.TableColumn<Customer, Division> customerDivisionColumn;
    @FXML
    private javafx.scene.control.TableColumn<Customer, Country> customerCountryColumn;
    @FXML
    private Button cAddButton;
    @FXML
    private Button cDeleteButton;
    @FXML
    private Button cUpdateButton;
    
    // Type month report
    @FXML
    private Button tmCalcButton;
    @FXML
    private Label tmCalcLabel;
    @FXML
    private ComboBox<String> tmTypeCombo;
    @FXML
    private ComboBox<String> tmMonthCombo;


    // Contact schedule report
    @FXML
    private ComboBox<Contact> acCombo;
    @FXML
    private javafx.scene.control.TableView<Appointment> apptContactTable;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> acContactColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptContactTypeColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptContactStartColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, Integer> apptContactIdColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptContactTitleColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptContactDescColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> appointmentContactLocColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, String> apptContactEndColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, Integer> apptContactCustomerColumn;
    @FXML
    private javafx.scene.control.TableColumn<Appointment, Integer> apptContactUserColumn;

    // User customer report
    @FXML
    private ComboBox<Customer> ucCustCombo;
    @FXML
    private ComboBox<User> ucUserCombo;
    @FXML
    private Label ucCalcLabel;
    @FXML
    private Button ucCalcButton;

    /**
     * Here Initializes the ViewController.
     *
     * Using an Array stream on the Month values array, we can use (forEach) lambda expression.
     * This lambda expression fills the months ObservableList that will be used for the month report ComboBox data.
     * It is more readable as it makes the for-each loop into a single line.
     *
     * The other lambda expressions specify uses for GUI items.
     * Such as:
     * Appointments by Contact ComboBox
     * User/Customer Calculation Button
     * Type/Month Calculation Button
     * @param resourceBundle resource bundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAppointmentTable();
        initCustomerTable();
        initReportTable();

        ObservableList<String> months = FXCollections.observableArrayList();
        // lambda expression
        Arrays.stream(Month.values()).forEach(m -> months.add(m.name()));
        tmMonthCombo.setItems(months);

        // lambda expression
        ucCalcButton.setOnAction(e -> {
            if (ucUserCombo.getSelectionModel().getSelectedItem() != null
                    && ucCustCombo.getSelectionModel().getSelectedItem() != null) {
                int count = DBAppointment.countByUserAndCustomer(ucUserCombo.getSelectionModel().getSelectedItem(),
                        ucCustCombo.getSelectionModel().getSelectedItem());
                if (count == 0) ucCalcLabel.setText("No matching appointments found.");
                else if (count == 1) ucCalcLabel.setText("1 matching appointment found.");
                else ucCalcLabel.setText(count + " matching appointments found.");
            } else ucCalcLabel.setText("Please select user and customer.");
        });

        // lambda expression
        tmCalcButton.setOnAction(e -> {
            if (tmTypeCombo.getSelectionModel().getSelectedItem() != null
                    && tmMonthCombo.getSelectionModel().getSelectedItem() != null) {
                int count = DBAppointment.countByTypeAndMonth(tmTypeCombo.getSelectionModel().getSelectedItem(),
                        Month.valueOf(tmMonthCombo.getSelectionModel().getSelectedItem()));
                if (count == 0) tmCalcLabel.setText("No matching appointments found.");
                else if (count == 1) tmCalcLabel.setText("1 matching appointment found.");
                else tmCalcLabel.setText(count + " matching appointments found.");
            } else tmCalcLabel.setText("Please select type and month.");
        });

        // lambda expression
        aViewAllRadio.setOnAction(e -> {
            appointmentTable.setItems(DBAppointment.getAll());
            appointmentTable.getSortOrder().add(apptIdColumn);
        });

        // lambda expression
        acCombo.setOnAction(e -> {
            if (acCombo.getSelectionModel().getSelectedItem() != null) {
                apptContactTable.setItems(DBAppointment.getByContact(acCombo.getSelectionModel().getSelectedItem().getId()));
                apptContactTable.getSortOrder().add(apptContactIdColumn);
            }
        });

        // lambda expression
        aViewMonthRadio.setOnAction(e -> {
            appointmentTable.setItems(DBAppointment.getThisMonth());
            appointmentTable.getSortOrder().add(apptIdColumn);
        });

        // lambda expression
        aViewWeekRadio.setOnAction(e -> {
            appointmentTable.setItems(DBAppointment.getThisWeek());
            appointmentTable.getSortOrder().add(apptIdColumn);
        });

        // lambda expression
        reportTab.setOnSelectionChanged(e -> {
            if (reportTab.isSelected()) resetReportTab();
        });
    }

    /**
     * Open tab needed in View screen.
     */
    public void setTab(int tab) {
        if (tab >= 0 && tabPane.getTabs().size() > tab) tabPane.getSelectionModel().select(tab);
    }

    /**
     * Initialize for main appointment table.
     * Table columns are set to corresponding values.
     */
    public void initAppointmentTable() {
        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        apptEndColumn.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        apptCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        appointmentTable.setItems(DBAppointment.getAll());
        appointmentTable.getSortOrder().add(apptIdColumn);
    }

    /**
     * Initializes appointment by contact report table.
     */
    public void initReportTable() {
        apptContactIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptContactTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptContactDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentContactLocColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        acContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptContactTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptContactStartColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        apptContactEndColumn.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        apptContactCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptContactUserColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * Tab Fields Reset.
     */
    public void resetReportTab() {
        tmTypeCombo.setItems(DBAppointment.getTypes());
        tmCalcLabel.setText("");

        ucUserCombo.setItems(DBUser.getAll());
        ucCustCombo.setItems(DBCustomer.getAll());
        ucCalcLabel.setText("");

        acCombo.setItems(DBContact.getAll());
        apptContactTable.getItems().clear();
    }

    /**
     * Here initialization of main customer table, displays customers by ID.
     */
    public void initCustomerTable() {
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalColumn.setCellValueFactory(new PropertyValueFactory<>("postal"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        customerTable.setItems(DBCustomer.getAll());
        customerTable.getSortOrder().add(customerIdColumn);
    }

    /**
     * Cancels selected appointment, along with attempting to delete from database.
     *
     * @param actionEvent action event
     */
    public void onCancelAppointment(ActionEvent actionEvent) {
        if (DBAppointment.getAll().size() == 0) {
            DialogSender.inform("Cancel Appointment", "There are no scheduled appointments.");
            return;
        }
        if (appointmentTable.getSelectionModel().isEmpty()) {
            DialogSender.warn("Cancel Appointment", "Please select the appointment you want to cancel.");
            return;
        }
        Appointment a = appointmentTable.getSelectionModel().getSelectedItem();
        if (DialogSender.confirm("Cancel Appointment", "Are you sure you want to cancel '" + a.getType() + "' appointment at ID " + a.getId() + "?")) {
            if (DBAppointment.delete(a.getId()) == -1) {
                DialogSender.error("Cancel Appointment", "Could not delete appointment from database!");
                return;
            }
            if (aViewByToggle.getSelectedToggle() == aViewAllRadio) appointmentTable.setItems(DBAppointment.getAll());
            else if (aViewByToggle.getSelectedToggle() == aViewWeekRadio) appointmentTable.setItems(DBAppointment.getThisWeek());
            else if (aViewByToggle.getSelectedToggle() == aViewMonthRadio) appointmentTable.setItems(DBAppointment.getThisMonth());
            appointmentTable.getSortOrder().add(apptIdColumn);
            DialogSender.inform("Cancel Appointment", "Appointment ID " + a.getId() + " with type '" + a.getType() + "' successfully canceled!");
        }
    }


    /**
     * Becomes schedule appointment form along with launching the Appointment form.
     *
     * @param actionEvent action event
     */
    public void onAddAppointment(ActionEvent actionEvent) {
        if (DBCustomer.getAll().size() == 0) {
            DialogSender.inform("Schedule Appointment", "There are no customers to schedule appointments with.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Scheduler2.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes to update appointment form.
     *
     * @param actionEvent action event
     */
    public void onUpdateAppointment(ActionEvent actionEvent) {
        if (DBAppointment.getAll().size() == 0) {
            DialogSender.inform("Update Appointment", "There are no scheduled appointments.");
            return;
        }
        if (appointmentTable.getSelectionModel().isEmpty()) {
            DialogSender.warn("Update Appointment", "Please select the appointment you want to update.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Scheduler2.fxml"));
            Scene scene = new Scene(loader.load());
            ((SchedulerController2)loader.getController()).populate(appointmentTable.getSelectionModel().getSelectedItem());
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Changes to the customer registration form.
     *
     * @param actionEvent action event
     */
    public void onAddCustomer(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Customer.fxml")));
            Scene scene = new Scene(root);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes to the customer update form.
     * @param actionEvent action event
     */
    public void onUpdateCustomer(ActionEvent actionEvent) {
        if (DBCustomer.getAll().size() == 0) {
            DialogSender.inform("Update Customer", "There are no customers to update.");
            return;
        }
        if (customerTable.getSelectionModel().isEmpty()) {
            DialogSender.warn("Update Customer", "Please select the customer you want to update.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Customer.fxml"));
            Scene scene = new Scene(loader.load());
            ((CustomerController)loader.getController()).populate(customerTable.getSelectionModel().getSelectedItem());
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes selected customer - checks customer's appointments, - then tries to delete customer from database.
     *
     * @param actionEvent action event
     */
    public void onDeleteCustomer(ActionEvent actionEvent) {
        if (DBCustomer.getAll().size() == 0) {
            DialogSender.inform("Delete Customer", "There are no customers to delete.");
            return;
        }
        if (customerTable.getSelectionModel().isEmpty()) {
            DialogSender.warn("Delete Customer", "Please select the customer you want to delete.");
            return;
        }
        Customer c = customerTable.getSelectionModel().getSelectedItem();
        if (DialogSender.confirm("Delete Customer", "Are you sure you want to delete '" + c.getName() + "' at ID " + c.getId() + "?")) {
            if (DBAppointment.hasCustomer(c.getId()))
                if (!DialogSender.confirm("Delete Customer", "'" + c.getName() + "' has scheduled appointments.\nThe system will cancel the appointments on confirmation.\nWould you still like to continue?"))
                    return;
            if (DBCustomer.delete(c.getId()) == -1) {
                DialogSender.error("Delete Customer", "Could not delete customer from database!");
                return;
            }
            if (aViewByToggle.getSelectedToggle() == aViewAllRadio) appointmentTable.setItems(DBAppointment.getAll());
            else if (aViewByToggle.getSelectedToggle() == aViewMonthRadio) appointmentTable.setItems(DBAppointment.getThisMonth());
            else if (aViewByToggle.getSelectedToggle() == aViewWeekRadio) appointmentTable.setItems(DBAppointment.getThisWeek());
            appointmentTable.getSortOrder().add(apptIdColumn);
            customerTable.setItems(DBCustomer.getAll());
            customerTable.getSortOrder().add(customerIdColumn);
            DialogSender.inform("Delete Customer", "Customer successfully deleted at ID " + c.getId());
        }
        customerTable.getSelectionModel().clearSelection();
    }

}
