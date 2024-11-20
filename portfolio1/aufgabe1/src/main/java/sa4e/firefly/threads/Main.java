package sa4e.firefly.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int N = 5; // Number of rows
    private static final int M = 5; // Number of columns
    private static final double SQUARE_SIZE = 50.0; // Size of each square

    // Two-dimensional array to store the squares
    private Firefly[][] fireflies = new Firefly[N][M];
    private List<Thread> fireflyThreads = new ArrayList<Thread>(N*M);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Firefly Pulse Visualizer");

        // Create a GridPane to hold the squares
        GridPane gridPane = new GridPane();

        // Initialize the grid with white rectangles
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Firefly firefly = new Firefly(SQUARE_SIZE, SQUARE_SIZE);
                fireflies[i][j] = firefly;
                gridPane.add(firefly, j, i);
                Thread thisFireflyThread = new Thread(firefly);
                thisFireflyThread.start();
                fireflyThreads.add(thisFireflyThread);
            }
        }

        // Set up the scene and show the stage
        Scene scene = new Scene(gridPane, M * SQUARE_SIZE, N * SQUARE_SIZE);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((event) -> {
            fireflyThreads.forEach(Thread::interrupt);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
