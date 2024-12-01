package sa4e.firefly.grpc;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sa4e.firefly.grpc.observer.FireflyGrid;
import sa4e.firefly.grpc.observer.ProcessManager;

public class FireflyObserver extends Application {

    private static final int N = 6; // Number of rows
    private static final int M = 6; // Number of columns
    public static final double SQUARE_SIZE = 50.0; // Size of each square

    private static int port = 50051;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Firefly Visualization");

        BorderPane root = new BorderPane();

        // Create a GridPane to hold the squares
        FireflyGrid fireflyGrid = new FireflyGrid(port);

        fireflyGrid.setAlignment(Pos.CENTER);

        ProcessManager.setGrid(fireflyGrid);

        HBox toolbar = new HBox();
        toolbar.setPadding(new Insets(15, 12, 15, 12));
        toolbar.setSpacing(10);
        toolbar.setAlignment(Pos.CENTER);

        Button generateTorusButton = new Button("Generate Torus");
        generateTorusButton.setOnMouseClicked(event -> {
            generateTorusButton.setVisible(false);
            ProcessManager.createTorus(M, N);
        });

        Button killAllButton = new Button("Kill all");
        killAllButton.setOnMouseClicked(event -> {
            ProcessManager.killProcesses();
            generateTorusButton.setVisible(true);
        });

        toolbar.getChildren().add(generateTorusButton);
        toolbar.getChildren().add(killAllButton);


        root.setTop(toolbar);
        root.setCenter(fireflyGrid);

        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        // Set up the scene and show the stage
        Scene scene = new Scene(root, M * SQUARE_SIZE, N * SQUARE_SIZE + 100);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> ProcessManager.killProcesses());

        primaryStage.show();

        try {
            fireflyGrid.startListening();
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        if (args.length == 1) port = Integer.parseInt(args[0]);
        launch(args);
    }

    public static int getPort() {
        return port;
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