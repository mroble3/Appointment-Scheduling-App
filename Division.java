package model;

/**
 * Division Class.
 */
public class Division {

    private int id;
    private String division;
    private int countryId;

    /**
     * Constructor for Division object.
     *
     * @param id division ID
     * @param countryId country ID
     */
    public Division(int id, String division, int countryId) {
        this.id = id;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * setter for division id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter for division id.
     */
    public int getId() {
        return id;
    }

    /**
     * setter for division id.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * getter for division name.
     */
    public String getDivision() {
        return division;
    }

    /**
     * setter for division Country Id.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * getter for division Country Id.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Object.toString() is overridden to return readable format of Contact object.
     * (Maybe used more than once in ComboBox field).
     */
    @Override
    public String toString() {
        return id + " - " + division;
    }
}
