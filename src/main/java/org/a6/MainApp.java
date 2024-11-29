package org.a6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("a6world.fxml"));

        Scene scene = new Scene(root, 1200, 800);

        stage.setTitle("A6 World View");
        // System.out.println("Thread in start: " + Thread.currentThread().getName());
        stage.setScene(scene);
        stage.show();

    }

}
