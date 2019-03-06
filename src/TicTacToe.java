import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TicTacToe extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/tictactoe.fxml"));
        primaryStage.setTitle("Test");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Button topLeftBtn = (Button) scene.lookup("#topLeftBtn");
        Button topButton = (Button) scene.lookup("#topButton");
        Button topRightBtn = (Button) scene.lookup("#topRightBtn");
        Button leftBtn = (Button) scene.lookup("#leftBtn");
        Button middleBtn = (Button) scene.lookup("#middleBtn");
        Button rightBtn = (Button) scene.lookup("#rightBtn");
        Button bottomLeftBtn = (Button) scene.lookup("#bottomLeftBtn");
        Button bottomBtn = (Button) scene.lookup("#bottomBtn");
        Button bottomRightBtn = (Button) scene.lookup("#bottomRightBtn");

        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(topLeftBtn);
        buttons.add(topButton);
        buttons.add(topRightBtn);
        buttons.add(leftBtn);
        buttons.add(middleBtn);
        buttons.add(rightBtn);
        buttons.add(bottomLeftBtn);
        buttons.add(bottomBtn);
        buttons.add(bottomRightBtn);

        for (Button button: buttons) {
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (button.getText().equals("O")) {
                        button.setText("X");
                    } else {
                        button.setText("O");
                    }
                }
            });
        }

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
