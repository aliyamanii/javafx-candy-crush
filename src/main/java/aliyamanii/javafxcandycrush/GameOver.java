package aliyamanii.javafxcandycrush;

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
        Text endText = new Text("");
        endText.setStyle("""
                 -fx-font-family: 'Impact', sans-serif;
                    -fx-font-size: 72px;
                    -fx-background-color: linear-gradient(to bottom right, #ffb6c1, #ff69b4, #9400d3, #4b0082);
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 5);
                    -fx-background-color: linear-gradient(to bottom, #000, #333);
                    -fx-background-radius: 20px;
                    -fx-padding: 20px;\
                """);

        MenuButton playButton = new MenuButton("Play Again", Color.rgb(0, 191, 255), Color.rgb(30, 144, 255));
        Game playButtonEventHandler = new Game(endStage);
        playButton.setAction(event -> playButtonEventHandler.handle());

        endText.setFont(Font.font("Arial", 80));
        endText.setFill(Color.RED);
        Text scoreText = new Text();
        System.out.println();

        VBox endBox = new VBox(endText, playButton);
        endBox.setAlignment(Pos.BOTTOM_CENTER);
        StackPane root = new StackPane(endBox);
        Scene gameScene = new Scene(root, 800, 450);
        endStage.setResizable(false);
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(GameMenu.path.toAbsolutePath() + "\\assets\\gameOver.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root.setBackground(new Background(backgroundImage));


        // Set game board scene on gameStage
        endStage.setScene(gameScene);
        endStage.setTitle("Bruh You Lost");
        endStage.show();
    }
}
