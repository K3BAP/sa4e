package sa4e.firefly.threads;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Firefly extends Rectangle implements Runnable {

    public Firefly(double width, double height, Paint fill) {
        super(width, height, fill);
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                updateColor(Color.YELLOW);
                Thread.sleep(1000);
                updateColor(Color.WHITE);
                Thread.sleep(1000);
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
