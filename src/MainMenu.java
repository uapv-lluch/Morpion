import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @FXML
    private Button playBtn;
    @FXML
    private Button quitBtn;

    @FXML
    public void actionQuitBtn(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/mainMenu.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        /*quitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getSource());
            }
        });*/

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
