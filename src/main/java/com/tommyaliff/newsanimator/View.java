package com.tommyaliff.livenews;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class View extends BorderPane {

    private final Model model;

    ImageView imageView = new ImageView();

    Text headlines = new Text("Click a button to get news!");
    Text description = new Text("");

    Button getButton = new Button("Real News");
    Button fakeButton = new Button("Fake News");

    StackPane topPane = new StackPane(headlines);
    StackPane centerPane = new StackPane(imageView);
    StackPane bottomPane = new StackPane(description);
    StackPane leftPane = new StackPane(getButton);
    StackPane rightPane = new StackPane(fakeButton);


    public View(Model model) throws IOException {
        this.model = model;
        layItOut();
    }

    private void layItOut() {

        this.getButton.setPadding(new javafx.geometry.Insets(5));
        this.fakeButton.setPadding(new javafx.geometry.Insets(5));

        this.headlines.setFont(javafx.scene.text.Font.font("Verdana", 18));
        this.description.setFont(javafx.scene.text.Font.font("Verdana", 14));

        this.headlines.setWrappingWidth(475);
        this.description.setWrappingWidth(475);

        this.headlines.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        this.setTop(topPane);
        this.setCenter(centerPane);
        this.setBottom(bottomPane);
        this.setLeft(leftPane);
        this.setRight(rightPane);

        this.topPane.setPrefHeight(100);
        this.bottomPane.setPrefHeight(100);
        this.imageView.setFitHeight(350);
        this.imageView.setPreserveRatio(true);
        this.rightPane.setMinWidth(100);
        this.leftPane.setMinWidth(100);
        this.setAlignment(topPane, javafx.geometry.Pos.CENTER);
        this.setAlignment(bottomPane, javafx.geometry.Pos.CENTER);

    }


}
