package aliyamanii.javafxcandycrush;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsHandler {
    public int screenWidth = 800;
    public int screenHeight = 600;
    private final Stage settingsStage;
    private TextField playerNameTextField;
    private ComboBox<String> sizeComboBox;
    public SettingsHandler(Stage settingsStage) {
        this.settingsStage = settingsStage;
    }

    public void handle() {

        VBox sroot = new VBox(20);
        sroot.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Player Name:");
        playerNameTextField = new TextField("Player Name");

        Label sizeLabel = new Label("Size:");
        sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll("6", "7", "8");
        sizeComboBox.setValue("Board Size");

        Button startButton = new Button("Save");
        startButton.setOnAction(e -> {
            String playerName = playerNameTextField.getText();
            String selectedSize = sizeComboBox.getValue();

        });

        HBox nameBox = new HBox(10);
        nameBox.setAlignment(Pos.CENTER);
        nameBox.getChildren().addAll(nameLabel, playerNameTextField);

        HBox sizeBox = new HBox(10);
        sizeBox.setAlignment(Pos.CENTER);
        sizeBox.getChildren().addAll(sizeLabel, sizeComboBox);

        sroot.getChildren().addAll(nameBox, sizeBox, startButton);

        Scene settingsScene = new Scene(sroot, screenWidth, screenHeight);
        settingsStage.setResizable(false);
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(GameMenu.path.toAbsolutePath() + "\\assets\\bgImage.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        sroot.setBackground(new Background(backgroundImage));

        settingsStage.setScene(settingsScene);
        settingsStage.setTitle("Candy Crush");
        settingsStage.show();
    }

    private void writeChoicesToFile(String playerName, String selectedSize) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("game_settings.txt"))) {
            writer.write("Player Name: " + playerName);
            writer.newLine();
            writer.write("Selected Size: " + selectedSize);
            writer.newLine();
            // Add more choices if needed
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
