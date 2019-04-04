import ai.AI;
import ai.Data;
import ai.MultiLayerPerceptron;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TicTacToe {

//    private ArrayList<Double> stateOfTheGame;
    private double[] stateOfTheGame2 = new double[9];
    private boolean isPvP = true;
    private boolean isPlayer1Turn = true;
    private String mode = "vs Player";
    private String difficulty = "Medium";
    private AI ai;
    private String winner;
    private HashMap<double[], double[]> player1Map = new HashMap<>();
    private HashMap<double[], double[]> player2Map = new HashMap<>();

    @FXML
    private Parent root;
    @FXML
    private Button quitBtn;
    @FXML
    private GridPane ticTacToePane;
    @FXML
    private Text timerMinutes;
    @FXML
    private Text timerSeconds;
    @FXML
    private Text turnText;

    @FXML
    public void mainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/mainMenu.fxml"));
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    public void onBoxClick(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        ObservableList observableList = ticTacToePane.getChildren();
        double[] oldStateOfTheGame = stateOfTheGame2.clone(); // probleme d'adresse memoire
        if (isPvP) { //PVP
            if (isPlayer1Turn) {
                button.setText("X");
                stateOfTheGame2[observableList.indexOf(button)] = States.CROSS;
                player1Map.put(oldStateOfTheGame, stateOfTheGame2);
                turnText.setText("Player 2 turn O");
            } else {
                button.setText("O");
                stateOfTheGame2[observableList.indexOf(button)] = States.CIRCLE;
                player2Map.put(oldStateOfTheGame, stateOfTheGame2);
                turnText.setText("Player 1 turn X");
            }
            isPlayer1Turn = !isPlayer1Turn;
        } else { // AI
            button.setText("X");
            stateOfTheGame2[observableList.indexOf(button)] = States.CROSS;
            player1Map.put(oldStateOfTheGame, stateOfTheGame2);
            oldStateOfTheGame = stateOfTheGame2.clone();
            if (isWin()) {
                gameOver(winner);
            } else if (Arrays.stream(stateOfTheGame2).noneMatch(i -> i == 0.0)) {
                gameOver("Nobody");
            }
            int index = ai.play(stateOfTheGame2);
            System.out.println(index);
            if (index != -1) {
                Button box = (Button) observableList.get(index);
                box.setText("O");
                box.setMouseTransparent(true);
                stateOfTheGame2[observableList.indexOf(box)] = States.CIRCLE;
                player2Map.put(oldStateOfTheGame, stateOfTheGame2);
            }
        }
        button.setMouseTransparent(true);
        button.getParent().requestFocus();
        if (isWin()) {
            gameOver(winner);
        } else if (Arrays.stream(stateOfTheGame2).noneMatch(i -> i == 0.0)) {
            gameOver("Nobody");
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
        isPvP = mode.equals("vs Player");
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void init(String mode, String difficulty) {
        this.mode = mode;
        isPvP = mode.equals("vs Player");
        if (!isPvP) {
            this.difficulty = difficulty;
            ai = new AI(difficulty);
        } else {
            turnText.setText("Player 1 turn X");
        }
    }

    public boolean isWin() {
        // Diagonals test
        if (stateOfTheGame2[0] != States.EMPTY && stateOfTheGame2[0] == stateOfTheGame2[4] && stateOfTheGame2[0] == stateOfTheGame2[8]) {
            winner = stateOfTheGame2[0] == States.CROSS ? "Player 1" : (isPvP ? "Player 2" : "AI");
            return true;
        } else if (stateOfTheGame2[2] != States.EMPTY && stateOfTheGame2[2] == (stateOfTheGame2[4]) && stateOfTheGame2[2] == stateOfTheGame2[6]) {
            winner = stateOfTheGame2[2] == States.CROSS ? "Player 1" : (isPvP ? "Player 2" : "AI");
            return true;
        }
        // Columns tests
        for (int i = 0; i < 3; ++i) {
            if (stateOfTheGame2[i] != States.EMPTY && stateOfTheGame2[i] == stateOfTheGame2[i + 3] && stateOfTheGame2[i] == stateOfTheGame2[i + 6]) {
                winner = stateOfTheGame2[i] == States.CROSS ? "Player 1" : (isPvP ? "Player 2" : "AI");
                return true;
            }
        }
        // Rows test
        for (int i = 0; i < 9; i += 3) {
            if (stateOfTheGame2[i] != States.EMPTY && stateOfTheGame2[i]  == stateOfTheGame2[i + 1] && stateOfTheGame2[i] == stateOfTheGame2[i + 2]) {
                winner = stateOfTheGame2[i] == States.CROSS ? "Player 1" : (isPvP ? "Player 2" : "AI");
                return true;
            }
        }
        return false;
    }

    public void gameOver(String winner) throws IOException {
        if (winner.equals("Player 1")) {
            Data.saveData(player1Map);
        } else if (winner.equals("Player 2") || winner.equals("AI")) {
            Data.saveData(player2Map);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/gameOver.fxml"));
        Parent root = loader.load();
        GameOver controller = loader.getController();
        controller.setWinner(winner);
        controller.init(mode, difficulty);
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    public TicTacToe() {
//        stateOfTheGame = new ArrayList<>();
        /*for (int i = 0; i < 9; ++i) {
            stateOfTheGame.add(States.EMPTY);
        }*/
        for (int i = 0; i < 9; ++i) {
            stateOfTheGame2[i] = States.EMPTY;
        }

        // Timer
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        /*timeline.getKeyFrames().add(
                new KeyFrame(Duration.minutes(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int minutes = Integer.parseInt(timerMinutes.getText());
                        ++minutes;
                        timerMinutes.setText(Integer.toString(minutes));
                    }
                })
        );*/
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int seconds = Integer.parseInt(timerSeconds.getText());
                        int minutes = Integer.parseInt(timerMinutes.getText());
                        seconds = (seconds + 1) % 60;
                        String secondsStr = seconds < 10 ? "0" + Integer.toString(seconds) : Integer.toString(seconds);
                        if (seconds == 0) {
                            ++minutes;
                            timerMinutes.setText(Integer.toString(minutes));
                        }
                        timerSeconds.setText(secondsStr);
                    }
                })
        );
        timeline.playFromStart();
    }
}
