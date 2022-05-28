package javablokus.blokus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ViewController {
    final int v = 800;
    final int v1 = 600;
    public void changeView(String fxmlPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(parent, 800, 600);
        JavaBlokus.setView(scene);
        System.out.println("Change View to " + fxmlPath);
    }
}
