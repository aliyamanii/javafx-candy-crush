package aliyamanii.javafxcandycrush;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Candy extends Rectangle {
    public enum CandyType {
        RED, GREEN, BLUE, YELLOW, ORANGE, PURPLE;
        private static final Random PRNG = new Random();

        public static CandyType randomType() {
            CandyType[] types = values();
            return types[PRNG.nextInt(types.length)];
        }

    }

    public CandyType type;

    public static Image getImage(CandyType type) {
        switch (type) {
            case RED -> {
                return new Image(GameMenu.path.toAbsolutePath() + "\\assets\\red.png");
            }
            case GREEN -> {
                return new Image(GameMenu.path.toAbsolutePath() + "\\assets\\green.png");
            }
            case BLUE -> {
                return new Image(GameMenu.path.toAbsolutePath() + "\\assets\\blue.png");
            }
            case YELLOW -> {
                return new Image(GameMenu.path.toAbsolutePath() + "\\assets\\yellow.png");
            }
            case ORANGE -> {
                return new Image(GameMenu.path.toAbsolutePath() + "\\assets\\orange.png");
            }
            case PURPLE -> {
                return new Image(GameMenu.path.toAbsolutePath() + "\\assets\\purple.png");
            }
        }
        return new Image(GameMenu.path.toAbsolutePath() + "\\assets\\red.png");
    }


    public Candy() {
        setWidth(50);
        setHeight(50);
        this.type = CandyType.randomType();
        setFill(new ImagePattern(getImage(this.type)));
    }


}