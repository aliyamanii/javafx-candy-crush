package aliyamanii.javafxcandycrush;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreditsHandler {
    public int screenWidth = 800;
    public int screenHeight = 600;
    private final Stage creditsStage;
    public CreditsHandler(Stage gameStage) {
        this.creditsStage = gameStage;
    }
    public void handle() {
        VBox croot = new VBox(20);
        croot.setAlignment(Pos.CENTER);
        Text creditsText = new Text("Thank you for observing...");
        creditsText.setStyle("""
                 -fx-font-family: 'Impact', sans-serif;
                    -fx-font-size: 25px;
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 5);
                    -fx-background-radius: 20px;
                    -fx-padding: 20px;\
                """);
        Text name = new Text("With love, Ali \"Talion\" Yamani");
        name.setStyle("""
                 -fx-font-family: 'Impact', sans-serif;
                    -fx-font-size: 25px;
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 5);
                    -fx-background-radius: 20px;
                    -fx-padding: 20px;\
                """);
        creditsText.setFill(Color.WHITE);
        name.setFill(Color.WHITE);

        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(GameMenu.path.toAbsolutePath() + "\\assets\\BgImage.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        croot.setBackground(new Background(backgroundImage));
        croot.getChildren().addAll(creditsText, name);

        Scene creditsScene = new Scene(croot, screenWidth, screenHeight);
        creditsStage.setScene(creditsScene);
        creditsStage.setTitle("Candy Crush");
        creditsStage.show();
    }
}
