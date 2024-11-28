package org.a6;

 
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import org.a6.util.Point;

public class A6WorldController {

    @FXML
    private AnchorPane worldcontainer;

    @FXML
    private Label messages;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Font x3;

    @FXML
    void zoomIn(ActionEvent event) {
        resetMessages();
        double r = worldAnchorPane.getHexInnerRadius();
        double newR = r * 1.1;
        if (newR >= 50) {
            newR = r;
            messages.setText("Maximum zoom level reached");
        }
        worldAnchorPane.setHexInnerRadius(newR);
        regenerateTileMap();
    }

    @FXML
    void zoomOut(ActionEvent event) {
        resetMessages();
        double r = worldAnchorPane.getHexInnerRadius();
        double newR = r * 0.9;
        if (newR <= 20) {
            newR = r;
            messages.setText("Minimum zoom level reached");
        }
        worldAnchorPane.setHexInnerRadius(newR);
        regenerateTileMap();
    }

    private void resetMessages() {
        messages.setText("");
    }

    WorldAnchorPane worldAnchorPane;
    AnchorPane tileMap;
    HashMap<Point, Polygon> hexHashMap;

    @FXML

    public void initialize() {
        worldAnchorPane = new WorldAnchorPane();
        regenerateTileMap();
        (hexHashMap.get(new Point(0, 0))).setFill(Color.BLUE);
    }

    private void regenerateTileMap() {
        if (worldcontainer.getChildren().contains(tileMap)) {
            worldcontainer.getChildren().remove(tileMap);
        }
        tileMap = getWorldAnchorPane();
        hexHashMap = worldAnchorPane.getHexHashMap();
        worldcontainer.getChildren().add(tileMap);
    }

    private AnchorPane getWorldAnchorPane() {
        return worldAnchorPane.createWorldAnchorPane();
    }

}
