import ai.AI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.File;


public class Options {

    @FXML
    private Parent root;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button easyLearnBtn;
    @FXML
    private Button mediumLearnBtn;
    @FXML
    private Button hardLearnBtn;
    @FXML
    private Button backBtn;

    @FXML
    public void back(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/mainMenu.fxml"));
        Parent root = loader.load();
        MainMenu controller = loader.getController();
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    public void learnEasy(ActionEvent actionEvent) throws Exception {
        progressBar.setProgress(0);
        AI ai = new AI("Easy");
        File file = new File("data/data");
        ai.trainFromDataWithProgressBar(file, 1000, progressBar);
    }

    @FXML
    public void learnMedium(ActionEvent actionEvent) throws Exception {
        progressBar.setProgress(0);
        AI ai = new AI("Medium");
        File file = new File("data/data");
        ai.trainFromDataWithProgressBar(file, 10000, progressBar);
    }

    @FXML
    public void learnHard(ActionEvent actionEvent) throws Exception {
        progressBar.setProgress(0);
        AI ai = new AI("Hard");
        File file = new File("data/data");
        ai.trainFromDataWithProgressBar(file, 100000, progressBar);
    }
}
