package aliyamanii.javafxcandycrush;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuButton extends StackPane {
    Rectangle button = new Rectangle(200, 50);
    Text label;
    public MenuButton(String title, Color base, Color hover){
        button.setFill(base);
        button.setArcWidth(20);
        button.setArcHeight(20);

        label = new Text(title);
        label.setFont(Font.font("Arial", 24));
        label.setFill(javafx.scene.paint.Color.WHITE);
        ObservableList<Node> buttonList = this.getChildren();
        buttonList.addAll(button, label);
        button.setOnMouseEntered(event -> {
            button.setFill(hover);
        });
        button.setOnMouseExited(event -> {
            button.setFill(base);
        });
        label.setOnMouseEntered(event -> {
            button.setFill(hover);
        });
        label.setOnMouseExited(event -> {
            button.setFill(base);
        });
    }
    public void setAction(EventHandler<? super MouseEvent> var1){
        button.setOnMouseClicked(var1);
        label.setOnMouseClicked(var1);
    }
}
