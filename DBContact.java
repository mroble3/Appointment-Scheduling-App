package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database calls are made for the operations of contact.
 */
    public interface DBContact {
        ObservableList<String> contactNameList = FXCollections.observableArrayList();


    /**
     * List of all contacts are returned.
     *
     */
    public static ObservableList<Contact> getAll() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        try {
            String q = "select * from contacts";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);

            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("contact_id");
                String name = res.getString("contact_name");
                String email = res.getString("email");

                contacts.add(new Contact(id, name, email));
            }
        } catch (SQLException e) {
            System.out.println("contact getAll() error: " + e.getMessage());
        }
        return contacts;
    }

    public static void getContactNameList() throws SQLException {
        String sql = "SELECT * FROM Contacts";
        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String contactName = rs.getString("Contact_Name");
            contactNameList.add(contactName);
        }
    }

//        try {
//            String sql = "SELECT Contact_ID FROM CONTACTS WHERE Contact_Name = ?";
//            PreparedStatement ps = DatabaseConnection.getConnection();
//            ps.setString(1,name);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()){
//                return rs.getInt("Contact_ID");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return -1;



    /**
     * Returns matching contact.
     *
     * @param id ID of contact find
     */
    public static Contact getContact(int id) {
        try {
            String q = "select * from contacts where contact_id = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);
            ps.setInt(1, id);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String name = res.getString("contact_name");
                String email = res.getString("email");

                return new Contact(id, name, email);
            }
        } catch (SQLException e) {
            System.out.println("getContact() error: " + e.getMessage());
        }
        return null;
    }

}






