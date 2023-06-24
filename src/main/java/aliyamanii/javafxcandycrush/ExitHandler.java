package aliyamanii.javafxcandycrush;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ExitHandler {

    private Stage primaryStage;

    public ExitHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void handle() {
        // Create a new stage for the confirmation dialog
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);
        dialogStage.setTitle("Exit Game");

        // Create the message label
        Label messageLabel = new Label("Are you sure? O_o");
        messageLabel.setStyle("-fx-font-size: 24px;");

        // Create the Yes and No buttons
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(event -> {
            dialogStage.close();
            primaryStage.close();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(event -> dialogStage.close());

        // Create an HBox to hold the buttons
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(yesButton, noButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Create a VBox to hold the message label and buttonBox
        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(messageLabel, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(50));

        BorderPane root = new BorderPane();// Create a BorderPane to hold the vbox
        root.setCenter(vbox);

        Scene dialogScene = new Scene(root, 400, 150);// Create a scene for the dialog
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(GameMenu.path.toAbsolutePath() + "\\assets\\bgImage.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root.setBackground(new Background(backgroundImage));


        //Fade transitions
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), root);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeIn.play();

        // Set the scene for the dialog stage
        dialogStage.setScene(dialogScene);

        // Close the dialog stage with fade-out effect when the "No" button is clicked
        noButton.setOnAction(event -> {
            fadeOut.setOnFinished(fadeEvent -> dialogStage.close());
            fadeOut.play();
        });

        yesButton.setOnAction(event -> {
            fadeOut.setOnFinished(fadeEvent -> {
                dialogStage.close();
                primaryStage.close();
            });
            fadeOut.play();
        });

        dialogStage.showAndWait();  // Show the dialog stage

    }
}
