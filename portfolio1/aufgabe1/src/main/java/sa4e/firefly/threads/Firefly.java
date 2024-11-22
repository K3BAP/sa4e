package sa4e.firefly.threads;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Firefly extends Rectangle implements Runnable {

    private final int clockLength = 10;
    private final double adherence = 0.1;

    private boolean pulsing = (Math.random() < 0.5);
    private double currentPhase = Math.random();

    private List<Firefly> neighbors = new ArrayList<>();

    public Firefly(double width, double height) {
        super(width, height, Color.WHITE);

        updateColor(pulsing ? Color.YELLOW : Color.BLACK);
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                Thread.sleep(clockLength);

                nextPhase();

                if (currentPhase > 1) {
                    currentPhase = 0;
                    pulsing = !pulsing;

                    if (pulsing) {
                        updateColor(Color.YELLOW);
                        this.neighbors.forEach(neighbor -> neighbor.iAmFlashing(true));
                    }
                    else {
                        updateColor(Color.BLACK);
                        this.neighbors.forEach(neighbor -> neighbor.iAmFlashing(false));
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

    public List<Firefly> getNeighbors() {
        return this.neighbors;
    }

    public void iAmFlashing(boolean flash) {
        if (flash != this.pulsing) {
            currentPhase += adherence;
        }
    }

    private void nextPhase() {
        currentPhase += 0.015;
    }
    
    
}
