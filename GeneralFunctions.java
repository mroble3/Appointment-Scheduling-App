//package util;
//
//import database.DBAppointment;
//import database.DBDivision;
//import model.Division;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//
//import java.sql.*;
//import java.time.*;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//import java.util.List;
//
///**
// * General function interface declaration
// */
//public interface GeneralFunctions {
//    ZoneId utcZoneId = ZoneId.of("UTC");
//    ZoneId myZoneId = ZoneId.systemDefault();
//    LocalDateTime userDateTime = LocalDateTime.now(myZoneId);
//    LocalDate userDate = LocalDate.now(myZoneId);
//
//    /**
//     * alertError() - takes generic string as (title), and generic string as (header) which then shows an alert with the text as an error message
//     * @param Title title to show
//     * @param Header header to show
//     */
//    static void alertError(String Title, String Header) {
//        Alert error = new Alert(Alert.AlertType.ERROR);
//        error.setTitle(Title);
//        error.setHeaderText(Header);
//        error.show();
//    }
//    /**
//     * successMessage() - takes generic string as (title), and generic string as (header) which then shows an alert with the text as a success message
//     * @param Title title to show
//     * @param Header header to show
//     */
//    static void successMessage(String Title, String Header) {
//        Alert success = new Alert(Alert.AlertType.INFORMATION);
//        success.setTitle(Title);
//        success.setHeaderText(Header);
//        success.show();
//    }
//
//    /**
//     * confirmationMessage() - passes strings (title), (header), and (context) then generates a confirmation message
//     * to user, then returns true if user selects true, or false if user selects cancel/no.
//     * @param title title to show
//     * @param header header to show
//     * @param context context to show
//     */
//    static boolean confirmationMessage(String title, String header, String context) {
//        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
//        confirmation.setTitle(title);
//        confirmation.setHeaderText(header);
//        confirmation.setContentText(context);
//
//        Optional<ButtonType> btn = confirmation.showAndWait();
//        if (btn.isPresent()) {
//            if (btn.get() == ButtonType.OK) {
//                return true;
//            } else if (btn.get() == ButtonType.CANCEL) {
//                return false;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * getStartTimes() - gets user time zone to match the business hours, then it creates a list of times
//     * with 15 min advances for the hours the user is able to schedule the appointment (in local time zone).
//     */
//    static List<LocalTime> getStartTimes() {
//        Integer userOffset = myZoneId.getRules().getOffset(Instant.now()).getTotalSeconds();
//        ZoneId business = ZoneId.of("America/Ohio");
//        Integer businessOffset = business.getRules().getOffset(Instant.now()).getTotalSeconds();
//        int diff = (businessOffset - userOffset) / 3600;
//
//        String[] mins = {"00", "15", "30", "45"};
//        List<LocalTime> times = new ArrayList<LocalTime>();
//
//        for (int i = 8 - diff; i < 22 - diff; i++) {
//            for (int j = 0; j < 4; j++) {
//                String time = i + ":" + mins[j];
//                if (i < 10) {
//                    time = "0" + time;
//                }
//                times.add(LocalTime.parse(time));
//            }
//        }
//        return (times);
//    }
//
//    /**
//     * String of the entered date and time in UTC
//     * @param date entered date as a local date
//     * @param time entered time as a local time
//     * @return
//     */
//    static String UserTimeToUTC(LocalDate date, LocalTime time) {
//        LocalDateTime ldt = LocalDateTime.of(date, time);
//        ZonedDateTime zdt = ZonedDateTime.of(ldt, myZoneId);
//        ZonedDateTime utczdt = ZonedDateTime.ofInstant(zdt.toInstant(), utcZoneId);
//        return (utczdt.toLocalDate().toString() + " " + utczdt.toLocalTime().toString());
//    }
//
//    /**
//     * Converts User time as a string.
//     * @param dbUTCTime Passed UTC time as a string
//     */
//    static String UTCTimeToUserTime(String dbUTCTime) {
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
//        LocalDateTime ldt = LocalDateTime.parse(dbUTCTime, df);
//        ZonedDateTime utczdt = ZonedDateTime.of(ldt, utcZoneId);
//        ZonedDateTime myzdt = ZonedDateTime.ofInstant(utczdt.toInstant(), myZoneId);
//        return (myzdt.toLocalDate().toString() + " " + myzdt.toLocalTime().toString());
//    }
//
//    /**
//     * datePickerFromSelection() - takes a string date value and converts to local date.
//     * @param datetime passed date time as a string
//     */
//    static LocalDate datePickerFromSelection(String datetime) {
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
//        LocalDateTime ldt = LocalDateTime.parse(datetime, df);
//        return ldt.toLocalDate();
//    }
//
//    /**
//     * timeFromSelection() - takes a datetime value as string and returns it as local time.
//     * @param datetime entered date time (string)
//     */
//    static LocalTime timeFromSelection(String datetime) {
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
//        LocalDateTime ldt = LocalDateTime.parse(datetime, df);
//        return ldt.toLocalTime();
//    }
//
//    /**
//     * Lambda function
//     *
//     * divisionsFromDivisionList() - returns an Observable list of strings that consist of Divisions in specific country,
//     * defined by country string name.
//     * @param countryName Entered country name
//     * @return Observable list of divisions for the particular country.
//     */
//    static ObservableList<String> divisionsFromDivisionList(String countryName) {
//        ObservableList<String> filteredList = FXCollections.observableArrayList();
//        DBDivision.DivisionsList.forEach(Division -> {
//            if (Objects.equals(Division.getCountryName(), countryName)) {
//                filteredList.add(Division.getDivisionName());
//            }
//        });
//        return filteredList;
//    }
//
//    /**
//     * GetDivIdFromInput() - takes country name and state then returns the division ID.
//     * @param countryName country name
//     * @param state First level division
//     */
//    static int getDivIdFromInput(String countryName, String state) {
//        for (Division div : DBDivision.DivisionsList) {
//            if ((div.getCountryName().equals(countryName)) && (div.getDivisionName().equals(state))) {
//                return div.getDivisionId();
//            }
//        }
//
//        return -1;
//    }
//
//    /**
//     * checkWeek() - checks to see if the passed datetime (string) is within a week of current user time.
//     * @param datetime entered datetime as a string
//     * @return true if date is within a week, false if is not
//     */
//    static boolean checkWeek(String datetime) {
//        LocalDate date = datePickerFromSelection(datetime);
//        return ((date.isAfter(userDate) || date.isEqual(userDate)) && date.isBefore(userDate.plusWeeks(1)));
//    }
//
//    /**
//     * checkMonth() - checks to see if the passed datetime (string) is within a month of current user time.
//     * @param datetime entered datetime as a string
//     * @return true if date is within a month, false if is not
//     */
//    static boolean checkMonth(String datetime) {
//        LocalDate date = datePickerFromSelection(datetime);
//        return ((date.isAfter(userDate) || date.isEqual(userDate)) && date.isBefore(userDate.plusMonths(1)));
//    }
//
//    /**
//     * DoesOverlap() - checks to see if the appointment that the user is wanting to save overlaps with a time that another
//     * appointment
//     * @param startTime start time of the appointment
//     * @param endTime end time of the appointment
//     * @param date date of the appointment
//     * @param custId customer ID of the person the appointment is with
//     * @return appointment ID if date does overlaps, if not then return -1
//     */
//    static int DoesOverlap(LocalTime startTime, LocalTime endTime, LocalDate date, int custId, int appointmentId) {
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
//        LocalDateTime start = LocalDateTime.of(date, startTime);
//        LocalDateTime end = LocalDateTime.of(date, endTime);
//        for (Appointments appt : DBAppointment.apptoblist) {
//            LocalDateTime exitingStart = LocalDateTime.parse(appt.getStart(), df);
//            LocalDateTime existingEnd = LocalDateTime.parse(appt.getEnd(), df);
//            LocalDate existingDate = exitingStart.toLocalDate();
//
//            if (custId == appt.getCustomerId() && (appointmentId != appt.getAppointmentId())) {
//                if (date.isEqual(existingDate)) {
//                    if (start.isAfter(exitingStart) && start.isBefore(existingEnd) || start.isEqual(exitingStart)) {
//                        return appt.getAppointmentId();
//                    }
//                    if (end.isAfter(exitingStart) && end.isBefore(existingEnd) || end.isEqual(existingEnd)) {
//                        return appt.getAppointmentId();
//                    }
//                }
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * checkUpcomingAppt() - checks to see if there is an appointment within 15 minutes of the user's login time.
//     * @return 1 if there is an appointment within 15 minutes of the user logging in, return -1 if there is no appt
//     * @throws SQLException SQLException
//     */
//    static int checkUpcomingAppt() throws SQLException {
//        int check = -1;
//        DBAppointment.apptoblist.clear();
//        DBAppointment.pullAppointments();
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
//        LocalDateTime nowPlusFifteenMin =  userDateTime.plusMinutes(15);
//        for (Appointments appt: DBAppointment.apptoblist){
//            LocalDateTime exitingStart = LocalDateTime.parse(appt.getStart(), df);
//            if(exitingStart.toLocalDate().isEqual(userDate)){
//                if(exitingStart.isAfter(userDateTime) && exitingStart.isBefore(nowPlusFifteenMin)){
//                    System.out.println(" there is an appointment within 15min");
//                    int apptId = appt.getAppointmentId();
//                    LocalDate date = datePickerFromSelection(appt.getStart());
//                    LocalTime time = timeFromSelection(appt.getStart());
//                    alertError("Upcoming or Current Appointment", "There is an appointment happening within 15min\n"+
//                            "Appointment ID: " + apptId +
//                            "\nDate: " + date +
//                            "\nTime: " + time );
//                    check =1;
//                }
//            }
//        }
//        return check;
//    }
//}
