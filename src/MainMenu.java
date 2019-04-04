import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenu {

    @FXML
    private Parent root;
    @FXML
    private Button playBtn;
    @FXML
    private Button optionBtn;
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
    private Text difficultyText;

    @FXML
    public void showDifficulty() {
        FadeTransition ftText = new FadeTransition(Duration.millis(400), difficultyText);
        ftText.setFromValue(0.0);
        ftText.setToValue(1.0);
        ftText.play();
        for (Toggle t : difficultyGroup.getToggles()) {
            RadioButton r = (RadioButton) t;
            FadeTransition ft = new FadeTransition(Duration.millis(400), r);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
//            r.setVisible(true);
        }
    }

    @FXML
    public void hideDifficulty() {
        FadeTransition ftText = new FadeTransition(Duration.millis(400), difficultyText);
        ftText.setFromValue(1.0);
        ftText.setToValue(0.0);
        ftText.play();
        for (Toggle t : difficultyGroup.getToggles()) {
            RadioButton r = (RadioButton) t;
            FadeTransition ft = new FadeTransition(Duration.millis(400), r);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.play();
//            r.setVisible(false);
        }
    }

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
    public void options(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/options.fxml"));
        Parent root = loader.load();
        Options controller = loader.getController();
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
