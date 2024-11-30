package sa4e.firefly.grpc.observer;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.GridPane;

public class FireflyGrid extends GridPane {
    private List<FireflyRepresentation> fireflies = new ArrayList<>();

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

        redraw();
    }
}
