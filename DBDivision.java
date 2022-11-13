package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database calls for the division operations are made here.
 */
public class DBDivision {

    /**
     * Returns list of all divisions.
     */
    public static ObservableList<Division> getAll() {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            String q = "select * from first_level_divisions";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);

            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("division_id");
                String division = res.getString("division");
                int countryId = res.getInt("country_id");

                divisions.add(new Division(id, division, countryId));
            }
        } catch (SQLException e) {
            System.out.println("divisions getAll() error: " + e.getMessage());
        }
        return divisions;
    }

    /**
     * Returns matching country divisions.
     */
    public static ObservableList<Division> getAllByCountry(int id) {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            String q = "select * from first_level_divisions where country_id = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);
            ps.setInt(1, id);

            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int divId = res.getInt("division_id");
                String division = res.getString("division");

                divisions.add(new Division(divId, division, id));
            }
        } catch (SQLException e) {
            System.out.println("division getByCountry() error: " + e.getMessage());
        }
        return divisions;
    }

    /**
     * Returns matching occasion of Division.
     */
    public static Division get(int id) {
        try {
            String q = "select * from first_level_divisions " +
                    "where division_id = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);
            ps.setInt(1, id);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String division = res.getString("division");
                int countryId = res.getInt("country_id");

                return new Division(id, division, countryId);
            }
        } catch (SQLException e) {
            System.out.println("division get() error: " + e.getMessage());
        }
        return null;
    }

//    static String getDivisionfromDivisionId(int divisionCode) throws SQLException {
//        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID =?";
//        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
//        ps.setInt(1,divisionCode);
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()) {
//            return rs.getString("Division");
//        }
//        return null;


    /**
     * Returns matching occasion of Division.
     *
     * @param division name of division to find
     */
    public static Division get(String division) {
        try {
            String q = "select * from first_level_divisions " +
                    "where division = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);
            ps.setString(1, division);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int id = res.getInt("division_id");
                division = res.getString("division");
                int countryId = res.getInt("country_id");

                return new Division(id, division, countryId);
            }
        } catch (SQLException e) {
            System.out.println("division get() error: " + e.getMessage());
        }
        return null;
    }



}
