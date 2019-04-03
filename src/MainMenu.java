import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class MainMenu {

    @FXML
    private Parent root;
    @FXML
    private Button playBtn;
    @FXML
    private Button quitBtn;
    @FXML
    private ToggleGroup difficultyGroup;
    @FXML
    private ToggleGroup modeGroup;
    @FXML
    private RadioButton easyRadioBtn;
    @FXML
    private RadioButton mediumRadioBtn;
    @FXML
    private RadioButton hardRadioBtn;

    @FXML
    public void play(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/ticTacToe.fxml"));
        Parent root = loader.load();
        TicTacToe controller = loader.getController();
        RadioButton modeSelectedBtn = (RadioButton) modeGroup.getSelectedToggle();
        RadioButton difficultySelectedBtn = (RadioButton) difficultyGroup.getSelectedToggle();
        controller.init(modeSelectedBtn.getText(), difficultySelectedBtn.getText());
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
