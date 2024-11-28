package org.a6;

 
import java.io.File;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import org.a6.util.Point;

public class A6WorldController {

    private HashMap<Integer, ImagePattern> critterOrientationImageMap = new HashMap<Integer, ImagePattern>();

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
        loadWorld(event);
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
        loadWorld(event);
    }

    @FXML
    void loadWorld(ActionEvent event) {
        // world will be read from the file but let us set some randon hexes for demo
        // here

        hexHashMap.get(new Point(3, 2)).setFill(critterOrientationImageMap.get(0));
        hexHashMap.get(new Point(2, 0)).setFill(critterOrientationImageMap.get(45));
        hexHashMap.get(new Point(5, 4)).setFill(critterOrientationImageMap.get(270));

    }

    private void resetMessages() {
        messages.setText("");
    }

    WorldAnchorPane worldAnchorPane;
    HashMap<Point, Polygon> hexHashMap;

    @FXML
    public void initialize() {
        critterOrientationImageMap.put(0, (new ImagePattern(
                new Image((new File("src/main/resources/org/a6/critter_0.png")).toURI().toString()))));
        critterOrientationImageMap.put(45, (new ImagePattern(
                new Image((new File("src/main/resources/org/a6/critter_45.png")).toURI().toString()))));
        critterOrientationImageMap.put(90, (new ImagePattern(
                new Image((new File("src/main/resources/org/a6/critter_90.png")).toURI().toString()))));
        critterOrientationImageMap.put(135, (new ImagePattern(
                new Image((new File("src/main/resources/org/a6/critter_135.png")).toURI().toString()))));
        critterOrientationImageMap.put(180, (new ImagePattern(
                new Image((new File("src/main/resources/org/a6/critter_180.png")).toURI().toString()))));
        critterOrientationImageMap.put(225, (new ImagePattern(
                new Image((new File("src/main/resources/org/a6/critter_225.png")).toURI().toString()))));
        critterOrientationImageMap.put(270, (new ImagePattern(
                new Image((new File("src/main/resources/org/a6/critter_270.png")).toURI().toString()))));
        critterOrientationImageMap.put(315, (new ImagePattern(
                new Image((new File("src/main/resources/org/a6/critter_315.png")).toURI().toString()))));

        worldAnchorPane = new WorldAnchorPane();
        regenerateTileMap();
        // (hexHashMap.get(new Point(0, 0))).setFill(Color.BLUE);
    }

    private void regenerateTileMap() {
        worldcontainer.getChildren().removeIf(child -> AnchorPane.class.isInstance(child));
        AnchorPane newTileMap = worldAnchorPane.createWorldAnchorPane(4, 7);
        hexHashMap = worldAnchorPane.getHexHashMap();
        worldcontainer.getChildren().add(newTileMap);
    }
}
