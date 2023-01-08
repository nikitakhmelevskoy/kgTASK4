module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;
    requires org.junit.jupiter.api;
    requires org.assertj.core;


    opens com.cgvsu to javafx.fxml;
    exports com.cgvsu;
    exports com.cgvsu.scene;
    opens com.cgvsu.scene to javafx.fxml;
    exports com.cgvsu.model;
    opens com.cgvsu.model to javafx.fxml;
}