package controller;

import database.DialogSender;
import database.DBCountry;
import database.DBCustomer;
import database.DBDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Customer Controller class declaration.
 */
public class CustomerController implements Initializable {

    @FXML
    private Label headerTag;
    @FXML
    private Label descLabel;

    @FXML
    private TextField idText;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField postalField;
    @FXML
    private TextField phoneField;

    @FXML
    private Label divisionLabel;
    @FXML
    private ComboBox<Division> divisionCombo;
    @FXML
    private ComboBox<Country> countryCombo;

    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;

    private boolean update;

    /**
     * The Initializes for the function.
     *
     * Method here initializes update to false and completes the form's ComboBox fields.
     *
     * JUSTIFICATION FOR LAMBDA EXPRESSIONS:
     *
     * The first one specifies the functionality of Cancel button, which returns user to View screen.
     * Our other one specifies the purpose of the Country ComboBox, which updates the Division ComboBox.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(DBCountry.getAll());
        countryCombo.setPromptText("Select country");
        submitButton.setLayoutY(divisionCombo.getLayoutY());
        cancelButton.setLayoutY(divisionCombo.getLayoutY());
        errorLabel.setLayoutY(divisionCombo.getLayoutY() + 4);
        divisionLabel.setVisible(false);
        divisionCombo.setVisible(false);

        // lambda expression
        countryCombo.setOnAction(event -> {
            divisionLabel.setVisible(true);
            divisionCombo.setVisible(true);
            divisionCombo.setItems(DBDivision.getAllByCountry(countryCombo.getSelectionModel().getSelectedItem().getId()));
            divisionCombo.getSelectionModel().select(null);
            divisionCombo.setPromptText("Select division");
            submitButton.setLayoutY(divisionCombo.getLayoutY() + 50);
            cancelButton.setLayoutY(divisionCombo.getLayoutY() + 50);
            errorLabel.setLayoutY(divisionCombo.getLayoutY() + 54);
        });

        update = false;

        // lambda expression
        cancelButton.setOnAction(event -> {
            if (DialogSender.confirm((update ? "Update" : "Add") + " Customer", "Are you sure you want to cancel " + (update ? "updating" : "adding") + " this customer?")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/View.fxml"));
                    Scene scene = new Scene(loader.load());
                    ((ViewController)loader.getController()).setTab(1);
                    Stage stage = Main.getStage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    public void initialize() throws SQLException {
//        CustomerFormTable.getItems().clear();
//        CustomerDB.customerList.clear();
//        DivisionDB.pullDivisionList();
//        customerFormTable.getItems().clear();
//        customerDB.pullCustomers();
//        customerFormTable.setItems(CustomerDAO.customerList);
//        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
//        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));





    /**
     * Fills form with pre-selected customer data.
     *
     * @param customer selected customer
     */
    public void populate(Customer customer) {
        if (customer == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/View.fxml"));
                Scene scene = new Scene(loader.load());
                ((ViewController)loader.getController()).setTab(1);
                Stage stage = Main.getStage();
                stage.setScene(scene);
                stage.show();
                DialogSender.error("Update Customer", "The variable for selected customer is not initialized. Try again.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        headerTag.setText("Customer Update");
        descLabel.setText("The updated customer information needs to be Entered.");

        idText.setPromptText(Integer.toString(customer.getId()));
        nameField.setText(customer.getName());
        addressField.setText(customer.getAddress());
        postalField.setText(customer.getPostal());
        phoneField.setText(customer.getPhone());

        countryCombo.setItems(DBCountry.getAll());
        countryCombo.getSelectionModel().select(customer.getCountry());
        submitButton.setLayoutY(divisionCombo.getLayoutY() + 50);
        cancelButton.setLayoutY(divisionCombo.getLayoutY() + 50);
        errorLabel.setLayoutY(divisionCombo.getLayoutY() + 54);
        divisionCombo.setVisible(true);
        divisionLabel.setVisible(true);
        divisionCombo.setItems(DBDivision.getAllByCountry(countryCombo.getSelectionModel().getSelectedItem().getId()));
        divisionCombo.getSelectionModel().select(customer.getDivision());

        update = true;
    }

    /**
     * Updates customer.
     * Checks for blank input and successful update in database. Once successful it returns to the main screen.
     * @param actionEvent action event
     */
    public void onSubmit(ActionEvent actionEvent) {
        String error = "";
        if (nameField.getText().isBlank()
                || addressField.getText().isBlank()
                || postalField.getText().isBlank()
                || phoneField.getText().isBlank()
                || countryCombo.getSelectionModel().getSelectedItem() == null
                || divisionCombo.getSelectionModel().getSelectedItem() == null)
            error += "Remember to complete all fields.";
        errorLabel.setText(error);
        if (!errorLabel.getText().isBlank()) return;

        int id;
        if (update) {
            id = Integer.parseInt(idText.getPromptText());
            if (DBCustomer.update(id, nameField.getText(), addressField.getText(), postalField.getText(), phoneField.getText(), divisionCombo.getSelectionModel().getSelectedItem().getId()) == -1) {
                DialogSender.error("Update Customer", "Customer could not be updated in database.");
                return;
            }
        } else {
            id = DBCustomer.add(nameField.getText(), addressField.getText(), postalField.getText(), phoneField.getText(), divisionCombo.getSelectionModel().getSelectedItem().getId());
            if (id == -1) {
                DialogSender.error("Add Customer", "Customer could not be added in database.");
                return;
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/View.fxml"));
            Scene scene = new Scene(loader.load());
            ((ViewController)loader.getController()).setTab(1);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
            DialogSender.inform((update ? "Update" : "Add") + " Customer", "Customer successfully " + (update ? "updated" : "added") + " at ID " + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
