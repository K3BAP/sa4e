package sa4e.firefly.grpc;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FireflyObserver extends Frame {
    private static final int N = 5; // Number of rows
    private static final int M = 5; // Number of columns
    private static final int RECT_WIDTH = 100;
    private static final int RECT_HEIGHT = 100;
    private static final int X_OFFSET = 50;
    private static final int Y_OFFSET = 20;
    private static final int GAP = 3;

    private final ArrayList<Rectangle> rectangles = new ArrayList<>();
    private int nextRow = 0;
    private int nextCol = 0;

    public FireflyObserver() {
        super("Firefly Observer");

        // Set up the frame
        setSize(M * (RECT_WIDTH + GAP) + 2 * Y_OFFSET, N * (RECT_HEIGHT + GAP) + 2 * X_OFFSET);
        setLayout(new BorderLayout());
        setVisible(true);

        // Panel for the button
        Panel controlPanel = new Panel();
        Button addButton = new Button("Add existing firefly");
        Button createButton = new Button("Create torus of fireflies");
        controlPanel.add(addButton);
        controlPanel.add(createButton);
        add(controlPanel, BorderLayout.SOUTH);

        // Add action listener to the button
        addButton.addActionListener(e -> {
            addRectangleToGrid();
            repaint();
        });

        // Window close handler
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void addRectangleToGrid() {
        // Ensure we don't exceed the grid capacity
        if (nextRow >= N) {
            System.out.println("Grid is full! No more rectangles can be added.");
            return;
        }

        // Calculate the position of the next rectangle
        int x = nextCol * (RECT_WIDTH + GAP) + Y_OFFSET;
        int y = nextRow * (RECT_HEIGHT + GAP) + X_OFFSET;

        rectangles.add(new Rectangle(x, y, RECT_WIDTH, RECT_HEIGHT));

        // Move to the next cell in the grid
        nextCol++;
        if (nextCol >= M) {
            nextCol = 0;
            nextRow++;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw all rectangles in the grid
        for (Rectangle rect : rectangles) {
            g.drawRect(rect.x, rect.y, rect.width, rect.height);
        }
    }

    public static void main(String[] args) {
        new FireflyObserver();
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