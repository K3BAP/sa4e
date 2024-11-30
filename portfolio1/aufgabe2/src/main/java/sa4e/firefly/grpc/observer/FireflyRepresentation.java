package sa4e.firefly.grpc.observer;

import javafx.scene.shape.Rectangle;
import sa4e.firefly.grpc.FireflyObserver;

public class FireflyRepresentation extends Rectangle {
    public FireflyRepresentation() {
        super(FireflyObserver.SQUARE_SIZE, FireflyObserver.SQUARE_SIZE);
    }
}
