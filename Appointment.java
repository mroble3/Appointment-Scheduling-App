package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Appointment object.
 */
public class Appointment {

    private int id;
    private String title;
    private int appointmentId;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;

    /**
     * Appointment Constructor.
     *
     * @param id appointment ID
     * @param title appointment title
     * @param description appointment description
     * @param location appointment location
     * @param type appointment type
     * @param start appointment start date/time, expressed in local timezone
     * @param end appointment end date/time, expressed in local timezone
     * @param customerId customer ID
     * @param userId user ID
     * @param contactId contact ID
     * @param contactName contact name
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId, String contactName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Returns appointment ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for appointment ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns/getter title for appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets description for appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns location for appointment. (getter)
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for appointment title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns description for appointment.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Sets location for appointment. (setter)
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns type of appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type of appointment. (setter)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return appointment start date and time.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets start date and time of appointment.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * @return appointment end date and time.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets ID for contact for the appointment. (setter)
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Returns name of contact for that is assigned the appointment.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets end date and time for appointment.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * ID of customer assigned to the appointment is Returned.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets ID of customer for the appointment. (setter)
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns ID for user assigned appointment.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets ID for user linked to appointment. (setter)
     */
    public void setUserID(int userID) {
        this.userId = userID;
    }

    /**
     * Returns ID for the contact for the appointment.
     */
    public int getContactId() {
        return contactId;
    }


//    public void setStart(String start) {
//        this.start = start;
//    }



    /**
     * Sets name of contact assigned to the appointment.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Returns formatted string of start date and time
     */
    public String getFormattedStart() {
        return start.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
    }

    /**
     * Returns formatted end of start date and time
     */
    public String getFormattedEnd() {
        return end.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
    }

    public int getAppointmentId() {
        return appointmentId;
    }
}
