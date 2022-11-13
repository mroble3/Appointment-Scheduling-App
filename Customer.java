package model;

/**
 * Customer class.
 */
public class Customer {

    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private Division division;
    private Country country;

    /**
     * Constructor for Customer object.
     *
     * @param id customer ID
     * @param name customer name
     * @param address customer address
     * @param postal customer postal code
     * @param phone customer phone
     * @param division customer division
     * @param country customer country
     */
    public Customer(int id, String name, String address, String postal, String phone, Division division, Country country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    /**
     * setter for Id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter for Id.
     */
    public int getId() {
        return id;
    }

    /**
     * setter for customer Name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for customer Name.
     */
    public String getName() {
        return name;
    }

    /**
     * setter for customer Address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * getter for customer Address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * getter for customer Postal code.
     */
    public String getPostal() {
        return postal;
    }

    /**
     * setter for customer Postal code.
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     * setter for customer Phone number.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * getter for customer Phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * getter for customer Division instance.
     */
    public Division getDivision() {
        return division;
    }

    /**
     * setter for customer Division instance.
     */
    public void setDivision(Division division) {
        this.division = division;
    }

    /**
     * getter for Country instance.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * setter for Country instance.
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Object.toString() is overridden to return readable format of Contact object.
     * (Maybe used more than once in ComboBox field)
     *
     */
    @Override
    public String toString() {
        return id + " - " + name;
    }
}
