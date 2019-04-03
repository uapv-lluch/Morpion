import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOver {

    private String winner = "";

    @FXML
    private Parent root;
    @FXML
    private Button restartBtn;
    @FXML
    private Button mainMenuBtn;
    @FXML
    private Button quitBtn;
    @FXML
    private Text winnerText;

    @FXML
    public void restart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/ticTacToe.fxml"));
//        Scene scene = new Scene(root);
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.getScene().setRoot(root);
//        stage.setScene(scene);
    }

    @FXML
    public void mainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/mainMenu.fxml"));
//        Scene scene = new Scene(root);
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.getScene().setRoot(root);
//        stage.setScene(scene);
    }

    @FXML
    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void setWinner(String winner) {
        this.winner = winner;
        winnerText.setText(winner + " is the winner");
    }
}
