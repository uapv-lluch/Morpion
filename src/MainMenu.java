import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainMenu {

    @FXML
    private Parent root;
    @FXML
    private Button playBtn;
    @FXML
    private Button quitBtn;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton easyRadioBtn;
    @FXML
    private RadioButton mediumRadioBtn;
    @FXML
    private RadioButton hardRadioBtn;

    @FXML
    public void play(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/tictactoe.fxml"));
        Scene scene = new Scene(root);
        RadioButton radioButton = (RadioButton) toggleGroup.getSelectedToggle();
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
