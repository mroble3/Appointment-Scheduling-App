package model;

/**
 * Country class declaration.
 */
public class Country {

    private int id;
    private String country;

    /**
     * Country class Constructor.
     * @param country country name
     * @param id country ID
     */
    public Country(int id, String country) {
        this.id = id;
        this.country = country;
    }

    /**
     * ID for country is Set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * ID country is Returned.
     */
    public int getId() {
        return id;
    }

    /**
     * Name of country is Returned.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Name of country is Set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Object.toString() is overridden to return readable format of Contact object
     * (Maybe used more than once in ComboBox field)
     */
    @Override
    public String toString() {
        return id + " - " + country;
    }
}

