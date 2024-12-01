package org.a6;

 
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

import org.a6.util.Message;
import org.a6.util.Point;

public class A6WorldController {

    // So the simulation can now get a hold of the PriorityBlockinQueue and push
    // messages to it
    public static final PriorityBlockingQueue<Message> messageQueue = new PriorityBlockingQueue<>();
    private HashMap<Integer, ImagePattern> critterOrientationImageMap = new HashMap<Integer, ImagePattern>();
    private ImagePattern rockImagePattern;
    private ImagePattern foodImagePattern;

    ContinuousQueueReadingService<Message> service;

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
        hexHashMap.get(new Point(0, 1)).setFill(rockImagePattern);
        hexHashMap.get(new Point(1, 1)).setFill(foodImagePattern);
        hexHashMap.get(new Point(2, 1)).setFill(foodImagePattern);
    }

    @FXML
    void startService(MouseEvent event) {
        service.start();
    }

    @FXML
    void stopService(MouseEvent event) {
        service.cancel();
    }

    private void resetMessages() {
        messages.setText("");
    }

    private void setHexToEmpty(int x, int y) {
        hexHashMap.get(new Point(x, y)).setFill(Color.ANTIQUEWHITE);
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
        foodImagePattern = new ImagePattern(
                new Image(new File("src/main/resources/org/a6/food.png").toURI().toString()));
        rockImagePattern = new ImagePattern(
                new Image(new File("src/main/resources/org/a6/rock.png").toURI().toString()));

        worldAnchorPane = new WorldAnchorPane();
        regenerateTileMap();
        // (hexHashMap.get(new Point(0, 0))).setFill(Color.BLUE);
        // Create the service
        service = new ContinuousQueueReadingService<>(messageQueue);

        // Add a listener to the value property to get notified when new items are
        // available
        service.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Thread in value property listener: " + Thread.currentThread().getName());
                System.out.println(">>>New item from queue: " + newValue.getMessage());
                switch (newValue.getMessage()) {
                    case "First-Message":
                        hexHashMap.get(new Point(5, 4)).setFill(critterOrientationImageMap.get(0));
                        break;
                    case "Second-Message":
                        setHexToEmpty(5, 4);
                        hexHashMap.get(new Point(5, 3)).setFill(critterOrientationImageMap.get(0));
                        break;
                    case "Third-Message":
                        hexHashMap.get(new Point(5, 3)).setFill(critterOrientationImageMap.get(90));
                        break;
                    default:
                        break;
                }
                return;
                // Process the new item, update UI, etc.
            }
        });

        // Start the service
        // service.start();

        // Add items to the queue from other parts of your application - this is a
        // sample
        messageQueue.put(new Message("First-Message"));
        messageQueue.put(new Message("Second-Message"));
        messageQueue.put(new Message("Third-Message"));
        messageQueue.put(new Message("Fourth-Message"));
    }

    private void regenerateTileMap() {
        worldcontainer.getChildren().removeIf(child -> AnchorPane.class.isInstance(child));
        AnchorPane newTileMap = worldAnchorPane.createWorldAnchorPane(3, 5);
        hexHashMap = worldAnchorPane.getHexHashMap();
        worldcontainer.getChildren().add(newTileMap);
    }

    private static class ContinuousQueueReadingService<T> extends Service<Message> {

        private final PriorityBlockingQueue<Message> queue;

        public ContinuousQueueReadingService(PriorityBlockingQueue<Message> queue) {
            this.queue = queue;
        }

        @Override
        protected Task<Message> createTask() {
            return new Task<>() {
                @Override
                protected Message call() throws Exception {
                    while (!isCancelled()) {
                        // Read from the queue (this will block if the queue is empty)
                        Message item = queue.take();

                        // Process the item (replace with your actual logic)
                        System.out.println("Thread in call(): " + Thread.currentThread().getName());
                        System.out.println("Processing item: " + item.getMessage());

                        // This SLEEP has important context here. As the queue is read in a tight loop
                        // the updateValue may happend
                        // but since The valueProperty is a single-value container. It's designed to
                        // hold the current value of the Task.
                        // When you call updateValue() in a loop, you're essentially changing the value
                        // of this container with each iteration.
                        // By the time the loop finishes and you access the valueProperty, it will
                        // naturally hold the last value that was set.
                        // The sleep here gives enough time to the UI to consume the event
                        // Note: to improve UI efficiency we can reduce this sleep or better still
                        // Messages added to queue may be slow anyway
                        Thread.sleep(1000);

                        // Return the item
                        updateValue(item);
                    }
                    return null;
                }
            };
        }
    }

}
