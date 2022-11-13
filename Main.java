package main;
import database.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ResourceBundle;

/**
 * @author Mohamed Roble
 */

public class Main extends Application {

    private static Stage stage;

    /**
     * Beginning of our program - which deals with the starting/closing the database/starting the JavaFX.
     */
    public static void main(String[] args) {
        DatabaseConnection.openConnection();
        if (DatabaseConnection.getConnection() == null) {
            System.out.println("Connection failed! Not launching program...");
            System.exit(-1);
        }
        launch(args);
        DatabaseConnection.closeConnection();
    }

//    public static void main(String[] args) {
//        Logger.createLog();
//        DatabaseConnection.openConnection();
//        launch(args);
//        DatabaseConnection.closeConnection();
//    }


    /**
     * @throws Exception exception
     * Scheduling System application launch.
     * Specifies (Resource Bundle) for French and English translations.
     */
    @Override
    public void start(Stage stage) throws Exception {
        setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        loader.setResources(ResourceBundle.getBundle("util/lang"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(loader.getResources().getString("title"));
        stage.setResizable(false);
        stage.show();
    }

//    public static void setStage(Stage stage) {
//        Main.stage = stage;
//    }


    /**
     * Provides/Gets main stage.
     */
    public static Stage getStage() {
        return Main.stage;
    }

    /**
     * Sets main stage.
     *
     * @param stage the main stage
     */
    public static void setStage(Stage stage) {
        Main.stage = stage;
    }

}
