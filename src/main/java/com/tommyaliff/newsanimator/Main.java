package com.tommyaliff.livenews;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Tommy Aliff 11/6/23
 * */

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Model model = new Model();
        View view = new View(model);
        Scene scene = new Scene(view);
        Presenter presenter = new Presenter(view, model);
        view.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: #4242a3;" +
                "-fx-background-color: beige;");
        stage.setTitle("Deez News");
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(900);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}