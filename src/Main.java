import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;


public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/mainMenu.fxml"));
        Parent root = loader.load();
        MainMenu controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setTitle("Tic Tac Toe");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
