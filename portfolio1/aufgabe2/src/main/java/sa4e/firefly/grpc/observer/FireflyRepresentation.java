package sa4e.firefly.grpc.observer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sa4e.firefly.grpc.FireflyObserver;

public class FireflyRepresentation extends Rectangle {
    private int port;

    public FireflyRepresentation(int port) {
        super(FireflyObserver.SQUARE_SIZE, FireflyObserver.SQUARE_SIZE);

        this.port = port;
        this.setFill(Color.WHITE);
    }

    public int getPort() {
        return this.port;
    }

    public void setIsFlashing(boolean isFlashing) {
        setFill(isFlashing ? Color.YELLOW : Color.DARKBLUE);
    }
}
