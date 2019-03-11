import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainMenu {

    private Scene scene;

    @FXML
    private Button playBtn;
    @FXML
    private Button quitBtn;

    @FXML
    public void play(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/tictactoe.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) playBtn.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void openScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
    }
}
