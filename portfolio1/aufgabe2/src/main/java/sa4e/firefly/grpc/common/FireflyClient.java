package sa4e.firefly.grpc.common;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import sa4e.firefly.grpc.FireflyProto.FireflyRequest;
import sa4e.firefly.grpc.FireflyProto.FireflyReply;
import sa4e.firefly.grpc.FireflyServiceGrpc;

public class FireflyClient {// Create a channel to the gRPC server
    ManagedChannel channel;
    FireflyServiceGrpc.FireflyServiceBlockingStub stub;
    int port;

    public FireflyClient(int port) {
        this.port = port;
    }

    public void init() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", port)
                                                    .usePlaintext() // Disable TLS for local testing
                                                    .build();

        // Create a stub for calling the service
        this.stub = FireflyServiceGrpc.newBlockingStub(channel);

        System.out.println("Client for port " + port + " is initialized.");
    }

    public void notifyFirefly(boolean isFlashing) {
        try {
            // Create a request
            FireflyRequest request = FireflyRequest.newBuilder()
            .setIsflashing(isFlashing)
            .build();

            // Call the remote procedure
            FireflyReply response = stub.notifyFirefly(request);
        }
        catch (Exception ex) {
            System.out.println("Message could not be delivered to port " + port);
        }

    }
    
    public void close() {
        this.channel.shutdown();
    }
}
