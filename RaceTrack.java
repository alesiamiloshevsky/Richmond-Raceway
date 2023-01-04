package com.example.assigment8;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class RaceTrack extends Application {
    static final Image IMAGE;

    // creates a constant IMAGE with the filepath to the png of the car
    static {
        try {
            IMAGE = new Image(new FileInputStream("sportive-car.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Button start;
    private Button pause;
    private Button reset;

    private Rectangle rect1;
    private Rectangle rect2;
    private Rectangle rect3;

    private ImageView imageView1 = new ImageView(IMAGE);
    private ImageView imageView2 = new ImageView(IMAGE);
    private ImageView imageView3 = new ImageView(IMAGE);

    private int finishLine = 410;
    private boolean moving = false;

    private int xPos1;
    private int xPos2;
    private int xPos3;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // adds title to the window
        primaryStage.setTitle("Richmond Raceway");
        Group group = new Group();

        // methods to set up the GUI
        createButtons();
        createRects();
        createImage();

        // adds all the tracks, buttons, and cars to the group
        group.getChildren().addAll(rect1, rect2, rect3, start, pause, reset, imageView1, imageView2, imageView3);
        // creates the scene with all objects from group and sets size
        Scene scene = new Scene(group, 500, 200);

        // initializes the alert box
        Alert alert = new Alert(AlertType.INFORMATION);
        // sets title of alert box
        alert.setTitle("Message");

        start.setOnAction(actionEvent ->  {
            // disables the start button
            start.setDisable(true);
            // creates thread for car one
            Thread car1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    moving = true;
                    while(moving) {
                        // generates a random number from 0-10
                        int randomAdvance = random.nextInt(11);
                        // runLater method allows changes to the GUI from a non-GUI thread
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // gets the position of the car
                                xPos1 = (int) imageView1.getX();
                                // the car advances a random amount of pixels forward
                                imageView1.setX(randomAdvance + xPos1);
                                // if the car crosses the finish line then it is the winner
                                if(randomAdvance + xPos1 >= finishLine) {
                                    // stops the car from moving
                                    moving = false;
                                    // an alert it spawned

                                }
                            }
                        });
                        try {
                            // after advancing the car, the thread sleeps for 50 milliseconds
                            Thread.sleep(50);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            // creates thread for car two
            Thread car2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    moving = true;
                    while(moving) {
                        int randomAdvance = random.nextInt(11);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                xPos2 = (int) imageView2.getX();
                                imageView2.setX(randomAdvance + xPos2);
                                if(randomAdvance + xPos2 >= finishLine) {
                                    moving = false;
                                    alert.setContentText("Car Two Wins!");
                                    alert.show();
                                }
                            }
                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            // creates thread for car three
            Thread car3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    moving = true;
                    while(moving) {
                        int randomAdvance = random.nextInt(11);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                xPos3 = (int) imageView3.getX();
                                imageView3.setX(randomAdvance + xPos3);
                                if(randomAdvance + xPos3 >= finishLine) {
                                    moving = false;
                                    alert.setContentText("Car Three Wins!");
                                    alert.show();
                                }
                            }
                        });
                        try {
                            Thread.sleep(50);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            // starts all the threads
            car1.start();
            car2.start();
            car3.start();
        });

        pause.setOnAction(actionEvent ->  {
            // enables the start button
            start.setDisable(false);
            // stops the cars from moving
            moving = false;
        });

        reset.setOnAction(actionEvent ->  {
            start.setDisable(false);
            moving = false;
            // resets the default car positions
            createImage();
        });

        // sets the scene
        primaryStage.setScene(scene);
        // makes the stage not resizable
        primaryStage.setResizable(false);
        // shows the stage
        primaryStage.show();
    }

    // creates the start, pause, and reset button
    public void createButtons() {
        // initializes buttons
        start = new Button("Start");
        pause = new Button("Pause");
        reset = new Button("Reset");

        // sets the positions of the buttons
        start.setLayoutX(125);
        start.setLayoutY(5);
        pause.setLayoutX(225);
        pause.setLayoutY(5);
        reset.setLayoutX(325);
        reset.setLayoutY(5);
    }

    // creates the racetracks
    public void createRects() {
        // initializes rectangles
        rect1 = new Rectangle();
        rect2 = new Rectangle();
        rect3 = new Rectangle();

        // sets position
        rect1.setX(50);
        rect1.setY(50);
        // sets size
        rect1.setWidth(400);
        rect1.setHeight(12);
        // sets color
        rect1.setFill(Color.LIGHTGRAY);

        rect2.setX(50);
        rect2.setY(100);
        rect2.setWidth(400);
        rect2.setHeight(12);
        rect2.setFill(Color.LIGHTGRAY);

        rect3.setX(50);
        rect3.setY(150);
        rect3.setWidth(400);
        rect3.setHeight(12);
        rect3.setFill(Color.LIGHTGRAY);
    }

    // creates the cars
    public void createImage() {
        // initializes car images
        imageView1.setImage(IMAGE);
        imageView2.setImage(IMAGE);
        imageView3.setImage(IMAGE);

        // sets position
        imageView1.setX(10);
        imageView1.setY(35);
        // sets size
        imageView1.setFitHeight(40);
        imageView1.setFitWidth(40);

        imageView2.setX(10);
        imageView2.setY(85);
        imageView2.setFitHeight(40);
        imageView2.setFitWidth(40);

        imageView3.setX(10);
        imageView3.setY(135);
        imageView3.setFitHeight(40);
        imageView3.setFitWidth(40);
    }
}