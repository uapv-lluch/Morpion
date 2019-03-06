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

    @FXML
    private Button playBtn;
    @FXML
    private Button quitBtn;

    @FXML
    public void play(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/tictactoe.fxml"));
        Scene scene = new Scene(root);
        stage = (Stage) playBtn.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/mainMenu.fxml"));
        Parent root = loader.load();
        MainMenu controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
