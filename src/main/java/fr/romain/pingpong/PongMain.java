package fr.romain.pingpong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class PongMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PongMain.class.getResource("pong.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
        stage.setResizable(false);
        stage.setTitle("JavaFX Pong");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}