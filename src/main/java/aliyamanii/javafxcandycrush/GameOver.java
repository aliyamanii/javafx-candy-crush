package aliyamanii.javafxcandycrush;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOver {
    private Stage endStage;

    public GameOver(Stage endStage) {
        this.endStage = endStage;
    }

    public void handle() {
        Text endText = new Text("YOU LOST");
        endText.setStyle("""
                 -fx-font-family: 'Impact', sans-serif;
                    -fx-font-size: 72px;
                    -fx-background-color: linear-gradient(to bottom right, #ffb6c1, #ff69b4, #9400d3, #4b0082);
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 5);
                    -fx-background-color: linear-gradient(to bottom, #000, #333);
                    -fx-background-radius: 20px;
                    -fx-padding: 20px;\
                """);

        MenuButton playButton = new MenuButton("Play Again", Color.rgb(255, 30, 30), Color.rgb(196, 44, 44));
        Game playButtonEventHandler = new Game(endStage);
        playButton.setAction(event -> playButtonEventHandler.handle());

        endText.setFont(Font.font("Arial", 80));
        endText.setFill(Color.RED);
        Text scoreText = new Text();
        System.out.println();

        VBox endBox = new VBox(endText, playButton);
        endBox.setAlignment(Pos.TOP_CENTER);
        endBox.setSpacing(10);
        endBox.setPadding(Insets.EMPTY);
        StackPane root = new StackPane(endBox);
        Scene gameScene = new Scene(root, 450, 450);
        endStage.setResizable(false);
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(GameMenu.path.toAbsolutePath() + "\\assets\\cryGlob.gif"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        root.setBackground(new Background(backgroundImage));


        // Set game board scene on gameStage
        endStage.setScene(gameScene);
        endStage.setTitle("Bruh You Lost");
        endStage.show();
    }
}
