module fr.romain.pingpong {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.romain.pingpong to javafx.fxml;
    exports fr.romain.pingpong;
}