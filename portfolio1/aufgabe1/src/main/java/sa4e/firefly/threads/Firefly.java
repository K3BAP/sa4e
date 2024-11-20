package sa4e.firefly.threads;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Firefly extends Rectangle implements Runnable {

    private final int phaseLength = 10;
    private final int clockLength = 100;

    private boolean pulsing = (Math.random() < 0.5);
    private int currentPhase = (int) (Math.random() * phaseLength);

    public Firefly(double width, double height) {
        super(width, height, Color.WHITE);

        updateColor(pulsing ? Color.YELLOW : Color.BLACK);
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                Thread.sleep(clockLength);

                if (currentPhase++ > phaseLength) {
                    currentPhase = 0;
                    pulsing = !pulsing;

                    if (pulsing) {
                        updateColor(Color.YELLOW);
                    }
                    else {
                        updateColor(Color.BLACK);
                    }
                }

            }
        }
        catch (InterruptedException ex) {}
        
    }

    private void updateColor(Paint fill) {
        Platform.runLater(() -> {
            this.setFill(fill);
        });
    }
    
    
}
