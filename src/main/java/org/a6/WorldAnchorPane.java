package org.a6;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.HashMap;

import org.a6.util.Point;

public class WorldAnchorPane {

  double r = 30; // the inner radius from hexagon center to outer corner
  double n; // the inner radius from hexagon center to middle of the axis
  double TILE_HEIGHT;
  double TILE_WIDTH;

  public WorldAnchorPane() {
    setHexInnerRadius(30);
  }

  private static HashMap<Point, Polygon> tileHashMap = new HashMap<Point, Polygon>();

  public void setHexInnerRadius(double r) {
    this.r = r;
    n = Math.sqrt(r * r * 0.75);
    TILE_HEIGHT = 2 * r;
    TILE_WIDTH = 2 * n;
  }

  public double getHexInnerRadius() {
    return r;
  }

  public AnchorPane createWorldAnchorPane() {

    AnchorPane tileMap = new AnchorPane();

    int minHexPerRow = 4;
    int maxHexPerRow = 7;
    int totalRows = (maxHexPerRow - minHexPerRow) * 2 + 1;
    int xStartOffset = 40; // offsets the entire field to the right
    int yStartOffset = 40; // offsets the entire fiels downwards

    int hexInRow = minHexPerRow;
    int increment = 1;
    for (int x = 0; x < totalRows; x++) {
      for (int y = 0; y < hexInRow; y++) {
        double xCoord = y * TILE_WIDTH + xStartOffset + (maxHexPerRow - hexInRow) * n;
        double yCoord = x * TILE_HEIGHT * 0.75 + yStartOffset;
        Polygon tile = new WorldAnchorPane.Tile(xCoord, yCoord, x, y);
        tileMap.getChildren().add(tile);
        tileHashMap.put(new Point(x, y), tile);
      }

      if (hexInRow == maxHexPerRow)
        increment = -1;
      hexInRow += increment;
    }
    return tileMap;
  }

  public HashMap<Point, Polygon> getHexHashMap() {
    return tileHashMap;
  }

  class Tile extends Polygon {
    Tile(double x, double y, int row, int col) {
      // creates the polygon using the corner coordinates
      getPoints().addAll(
          x, y,
          x, y + r,
          x + n, y + r * 1.5,
          x + TILE_WIDTH, y + r,
          x + TILE_WIDTH, y,
          x + n, y - r * 0.5);

      // set up the visuals and a click listener for the tile
      setFill(Color.ANTIQUEWHITE);
      setStrokeWidth(1);
      setStroke(Color.BLACK);
      setOnMouseClicked(e -> {
        System.out.println("Clicked: (row, col) = (" + row + ", " + col + ")");
        (tileHashMap.get(new Point(row, col)))
            .setFill(Color.RED);
      });
    }
  }
}
