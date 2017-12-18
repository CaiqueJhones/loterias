package io.jhonesdeveloper.loterias;

import io.jhonesdeveloper.loterias.cdi.StartupScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

@SuppressWarnings("unused")
public class LoteriasApplication {

    @Inject
    private FXMLLoader loader;

    void start(@Observes @StartupScene Stage primaryStage) {
        try {
            Pane root = loader.load(getClass().getResourceAsStream("view/Main.fxml"));

            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            scene.getRoot().requestFocus();

            primaryStage.setTitle("Loterias");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load FXML login screen!", e);
        }
    }
}
