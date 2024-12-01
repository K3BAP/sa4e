package sa4e.firefly.grpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sa4e.firefly.grpc.common.FireflyCallable;
import sa4e.firefly.grpc.common.FireflyClient;
import sa4e.firefly.grpc.common.FireflyServer;

public class Firefly implements Runnable, FireflyCallable {

    private final int clockLength = 20;
    private final double coupling = 0.20;

    private boolean pulsing = (Math.random() < 0.5);
    private double currentPhase = Math.random();

    private FireflyServer fireflyServer;
    private List<FireflyClient> neighbors = new ArrayList<>();

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        int neighborPorts[] = new int[args.length - 1];

        for (int i = 1; i < args.length; i++) {
            neighborPorts[i-1] = Integer.parseInt(args[i]);
        }

        Firefly firefly = new Firefly(port, neighborPorts);
        Thread fireflyThread = new Thread(firefly);

        fireflyThread.start();
        try {
            fireflyThread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Firefly(int port, int neighborPorts[]) {
        this.fireflyServer = new FireflyServer(port);
        for (int i = 0; i < neighborPorts.length; i++) {
            this.neighbors.add(new FireflyClient(neighborPorts[i], port));
        }
    }

    @Override
    public void run() {
        try {
            this.fireflyServer.init(this);
            this.neighbors.forEach(FireflyClient::init);
            while(!Thread.interrupted()) {
                Thread.sleep(clockLength);
                nextPhase();

                if (currentPhase > 1) {
                    currentPhase = 0;
                    pulsing = !pulsing;
                    System.out.println("Flashing: " + pulsing);


                    // Notify Neighbors
                    this.neighbors.forEach(fireflyClient -> {fireflyClient.notifyFirefly(pulsing);});
                }

            }
        }
        catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
        
    }

    private void nextPhase() {
        currentPhase += 0.015;
    }

    @Override
    public void flashStatusChanged(boolean isFlashing, int port) {
        if (isFlashing != this.pulsing) {
            currentPhase += coupling;
        }
    } 
}


// import sa4e.firefly.grpc.common.FireflyClient;

// public class Firefly {
//     public static void main(String[] args) {
//         FireflyClient client = new FireflyClient();
//         client.init();
//         client.notifyFirefly(true);
//         client.close();
//     }
//}
