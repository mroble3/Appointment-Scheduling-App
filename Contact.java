package model;

/**
 * Contact class.
 */
public class Contact {

    private int id;
    private String name;
    private String email;

    /**
     * Constructor for the Contact.
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     *ID of contact returned.
     */
    public int getId() {
        return id;
    }

    /**
     *ID of contact set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Name of contact Set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Name of contact returned.
     */
    public String getName() {
        return name;
    }

    /**
     * Email address of contact Set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Email address of contact returned.
     */
    public String getEmail() {
        return email;
    }


    /**
     * Overrides the Object.toString() method to return readable format of Contact object, containing both name and ID.
     * (Maybe used more than once in ComboBox field)
     */
    @Override
    public String toString() {
        return id + " - " + name;
    }
}
