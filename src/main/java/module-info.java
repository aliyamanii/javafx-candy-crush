module aliyamanii.javafxcandycrush {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens aliyamanii.javafxcandycrush to javafx.fxml;
    exports aliyamanii.javafxcandycrush;
}