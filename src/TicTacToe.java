import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class TicTacToe {

    private ArrayList<Integer> stateOfTheGame;
    private boolean isPvP;
    private boolean isPlayer1Turn;
    @FXML
    private Parent root;
    @FXML
    private Button quitBtn;
    @FXML
    private GridPane ticTacToePane;

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

    @FXML
    public void onSquareClick(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        ObservableList observableList = ticTacToePane.getChildren();
        if (isPvP) {
            if (isPlayer1Turn) {
                button.setText("X");
                stateOfTheGame.set(observableList.indexOf(button), 1);
            } else {
                button.setText("O");
                stateOfTheGame.set(observableList.indexOf(button), 2);
            }
            isPlayer1Turn = !isPlayer1Turn;
        }
        button.setMouseTransparent(true);
        button.getParent().requestFocus();
        if (isWin()) {
            Parent root = FXMLLoader.load(getClass().getResource("view/gameOver.fxml"));
//            Scene scene = new Scene(root);
            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.getScene().setRoot(root);
//            stage.setScene(scene);
        }
    }

    public boolean isWin() {
        // Diagonals test
        if (stateOfTheGame.get(0) != 0 && stateOfTheGame.get(0).equals(stateOfTheGame.get(4)) && stateOfTheGame.get(0).equals(stateOfTheGame.get(8))) {
            return true;
        } else if (stateOfTheGame.get(2) != 0 && stateOfTheGame.get(2).equals(stateOfTheGame.get(4)) && stateOfTheGame.get(2).equals(stateOfTheGame.get(6))) {
            return true;
        }
        // Columns tests
        for (int i = 0; i < 3; ++i) {
            if (stateOfTheGame.get(i) != 0 && stateOfTheGame.get(i).equals(stateOfTheGame.get(i + 3)) && stateOfTheGame.get(i).equals(stateOfTheGame.get(i + 6))) {
                return true;
            }
        }
        // Rows test
        for (int i = 0; i < 9; i += 3) {
            if (stateOfTheGame.get(i) != 0 && stateOfTheGame.get(i).equals(stateOfTheGame.get(i + 1)) && stateOfTheGame.get(i).equals(stateOfTheGame.get(i + 2))) {
                return true;
            }
        }
        return false;
    }

    public TicTacToe() {
        isPvP = true;
        isPlayer1Turn = true;
        stateOfTheGame = new ArrayList<>();
        for (int i = 0; i < 9; ++i) {
            stateOfTheGame.add(0);
        }
    }
}
