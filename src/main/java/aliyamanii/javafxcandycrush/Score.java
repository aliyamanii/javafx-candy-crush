package aliyamanii.javafxcandycrush;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Score extends Text {
    private int score;
    private String playerName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Score() {
        this.score = 0;

        this.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        this.setFill(Color.WHITE);

        updateScoreText();
    }

    public void increaseScore(int points) {
        score += points;
        updateScoreText();
    }

    public void setScore(int score) {
        this.score = score;
        updateScoreText();
    }

    public int getScore() {
        return score;
    }


    private void updateScoreText() {
        this.setText("Score: " + score);
    }
}
