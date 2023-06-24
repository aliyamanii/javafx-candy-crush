package aliyamanii.javafxcandycrush;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Heart extends Rectangle {
    public enum HeartType {
        FULL, EMPTY
    }

    public HeartType type = HeartType.FULL;

    public void setType(HeartType type) {
        this.type = type;
        updateImage();
    }

    public void updateImage() {
        if (HeartType.FULL == type) setFill(new ImagePattern(new Image(GameMenu.path.toAbsolutePath() + "\\assets\\fullHeart.png")));
        else setFill(new ImagePattern(new Image(GameMenu.path.toAbsolutePath() + "\\assets\\emptyHeart.png")));
    }


    public Heart(HeartType type) {
        setWidth(50);
        setHeight(50);
        this.type = type;
        updateImage();
    }


}