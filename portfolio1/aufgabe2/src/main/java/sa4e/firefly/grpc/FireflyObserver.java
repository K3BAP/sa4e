package sa4e.firefly.grpc;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class FireflyObserver extends Application {

    private static final int N = 5; // Number of rows
    private static final int M = 5; // Number of columns
    public static final double SQUARE_SIZE = 50.0; // Size of each square

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Firefly Visualization");

        BorderPane root = new BorderPane();

        // Create a GridPane to hold the squares
        GridPane squaresPane = new GridPane();

        // Populate the grid with rectangles
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
                square.setFill(Color.WHITE); // Initial color
                square.setStroke(Color.BLACK); // Border color
                squaresPane.add(square, j, i);
            }
        }

        squaresPane.setAlignment(Pos.CENTER);

        HBox toolbar = new HBox();
        toolbar.setPadding(new Insets(15, 12, 15, 12));
        toolbar.setSpacing(10);
        toolbar.setAlignment(Pos.CENTER);

        Button generateTorusButton = new Button("Generate Torus of Fireflies");

        toolbar.getChildren().add(generateTorusButton);


        root.setTop(toolbar);
        root.setCenter(squaresPane);

        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        // Set up the scene and show the stage
        Scene scene = new Scene(root, M * SQUARE_SIZE, N * SQUARE_SIZE + 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

// import sa4e.firefly.grpc.common.FireflyCallable;
// import sa4e.firefly.grpc.common.FireflyServer;

// public class Observer implements FireflyCallable{
//     public static void main(String[] args) throws Exception {
//         FireflyServer server = new FireflyServer(50051);
//         server.init(new Observer());
//         server.awaitTermination();
//     }

//     @Override
//     public void flashStatusChanged(boolean isFlashing, int port) {
//         System.out.printf("[%d]: %s\n", port, (isFlashing ? "FLASH" : "-----"));
//     }
// }