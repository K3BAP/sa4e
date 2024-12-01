package sa4e.firefly.grpc.observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import sa4e.firefly.grpc.common.FireflyCallable;
import sa4e.firefly.grpc.common.FireflyServer;

public class FireflyGrid extends GridPane implements FireflyCallable {
    private List<FireflyRepresentation> fireflies = new ArrayList<>();
    private Map<Integer, FireflyRepresentation> firefliesByPort = new HashMap<>();
    private FireflyServer server;

    public FireflyGrid(int port) {
        server = new FireflyServer(port);
    }

    public void startListening() throws IOException, InterruptedException {
        server.init(this);
    }

    public void clear() {
        this.fireflies.clear();
        this.firefliesByPort.clear();
        redraw();
    }

    private void redraw() {
        this.getChildren().clear();

        int squareSize = (int)Math.ceil(Math.sqrt(fireflies.size()));

        int thisColumn = 0;
        int thisRow = 0;
        for (int i = 0; i < fireflies.size(); i++) {
            this.add(fireflies.get(i), thisColumn++, thisRow);
            if (thisColumn >= squareSize) {
                thisRow++;
                thisColumn = 0;
            }
        }
    }

    public void add(FireflyRepresentation firefly) {
        fireflies.add(firefly);
        firefliesByPort.put(firefly.getPort(), firefly);

        Platform.runLater(() -> {
            redraw();
        });
    }

    @Override
    public void flashStatusChanged(boolean isFlashing, int originPort) {
        if (firefliesByPort.containsKey(originPort)) {
            firefliesByPort.get(originPort).setIsFlashing(isFlashing);
        }
        else {
            // First message from that firefly: Add it to the grid
            FireflyRepresentation newFirefly = new FireflyRepresentation(originPort);
            newFirefly.setIsFlashing(isFlashing);
            add(newFirefly);
        }
    }
}
