package aliyamanii.javafxcandycrush;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameMenu extends Application {
    public int screenWidth = 800;
    public int screenHeight = 600;
    public static final int offset = 1800;// The menu changer pixel value
    public static Path path = Paths.get("src"); // includes the path of our src folder



    @Override
    public void start(Stage menuStage) {
        // Create a root pane for the menu
        StackPane root = new StackPane();

        //Set background Image
        BackgroundImage backgroundImage = new BackgroundImage(
                new javafx.scene.image.Image(path.toAbsolutePath() + "\\assets\\BgImage.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root.setBackground(new Background(backgroundImage));

        // Create a VBox to hold the menu buttons
        VBox mainMenu = new VBox();
        mainMenu.setSpacing(20);
        mainMenu.setAlignment(Pos.CENTER);

        // Create the title text and style it
        Text title = new Text("Candy Crush");
        title.setStyle("""
                 -fx-font-family: 'Impact', sans-serif;
                    -fx-font-size: 72px;
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 5);
                    -fx-background-radius: 20px;
                    -fx-padding: 20px;\
                """);
        title.setFont(Font.font("Arial", 80));
        title.setFill(Color.DEEPPINK);

        // Create the Play button and add an event handler
        MenuButton playButton = new MenuButton("Play", Color.rgb(0, 191, 255), Color.rgb(30, 144, 255));
        Game playButtonEventHandler = new Game(menuStage);
        playButton.setAction(event -> playButtonEventHandler.handle());


        // Create the settings button and add an event handler
        MenuButton settingsButton = new MenuButton("Settings", Color.rgb(255, 215, 0), Color.rgb(255, 165, 0));
        SettingsHandler settingsButtonEventHandler = new SettingsHandler(menuStage);
        settingsButton.setAction(event -> settingsButtonEventHandler.handle());

        //Create the High Scores Button
        MenuButton highScoreButton = new MenuButton("High Score", Color.rgb(120, 10, 200), Color.rgb(120, 20, 100));

        // Create the Credits button and add an event handler
        MenuButton creditsButton = new MenuButton("Credits", Color.rgb(60, 179, 113), Color.rgb(50, 205, 50));


        MenuButton exitButton = new MenuButton("Exit", Color.rgb(220, 20, 60), Color.rgb(255, 0, 0));
        ExitHandler exitButtonEventHandler = new ExitHandler(menuStage);
        exitButton.setAction(event -> exitButtonEventHandler.handle());


        //Add all the components to the Menu Vbox
        mainMenu.getChildren().addAll(title, playButton, settingsButton, highScoreButton, creditsButton, exitButton);
        root.getChildren().add(mainMenu);



        Scene scene = new Scene(root, screenWidth, screenHeight);
        menuStage.setResizable(false);
        menuStage.getIcons().add(new Image(path.toAbsolutePath() + "\\assets\\icon.png"));
        menuStage.setTitle("Candy Crush!");
        menuStage.setScene(scene);
        menuStage.show();
    }

    public static void main(String[] args) {
        //Play background music
        File audioFile = new File(path.toAbsolutePath() + "\\assets\\bgMusic.wav");
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        launch(args);//launches the stage
    }
}