package javablokus.blokus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class JavaBlokus extends Application {
    private static Stage stg;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaBlokus.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("JavaBlokus");
        stage.setScene(scene);
        stage.show();
        stg = stage;
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeView(Scene sn) {
        stg.setScene(sn);
    }
}