package sa4e.firefly.grpc.common;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import sa4e.firefly.grpc.FireflyProto.FireflyRequest;
import sa4e.firefly.grpc.FireflyServiceGrpc;

public class FireflyClient {// Create a channel to the gRPC server
    ManagedChannel channel;
    FireflyServiceGrpc.FireflyServiceBlockingStub stub;
    int serverPort;
    int thisPort;

    public FireflyClient(int port, int thisPort) {
        this.serverPort = port;
        this.thisPort = thisPort;
    }

    public void init() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", serverPort)
                                                    .usePlaintext() // Disable TLS for local testing
                                                    .build();

        // Create a stub for calling the service
        this.stub = FireflyServiceGrpc.newBlockingStub(channel);

        System.out.println("Client for port " + serverPort + " is initialized.");
    }

    public void notifyFirefly(boolean isFlashing) {
        try {
            // Create a request
            FireflyRequest request = FireflyRequest.newBuilder()
            .setIsflashing(isFlashing)
            .setPort(thisPort)
            .build();

            // Call the remote procedure
            stub.notifyFirefly(request);
        }
        catch (Exception ex) {
            System.out.println("Message could not be delivered to port " + serverPort);
        }

    }
    
    public void close() {
        this.channel.shutdown();
    }
}
