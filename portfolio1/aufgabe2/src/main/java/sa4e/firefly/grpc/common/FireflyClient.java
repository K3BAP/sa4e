package sa4e.firefly.grpc.common;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import sa4e.firefly.grpc.FireflyProto.FireflyRequest;
import sa4e.firefly.grpc.FireflyProto.FireflyReply;
import sa4e.firefly.grpc.FireflyServiceGrpc;

public class FireflyClient {// Create a channel to the gRPC server
    ManagedChannel channel;
    FireflyServiceGrpc.FireflyServiceBlockingStub stub;
    public void init() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                                                  .usePlaintext() // Disable TLS for local testing
                                                  .build();

        // Create a stub for calling the service
        this.stub = FireflyServiceGrpc.newBlockingStub(channel);
    }
    
    public void notifyFirefly(boolean isFlashing) {
        // Create a request
        FireflyRequest request = FireflyRequest.newBuilder()
        .setIsflashing(isFlashing)
        .build();

        // Call the remote procedure
        FireflyReply response = stub.notifyFirefly(request);

        // Print the response
        System.out.println("Response from server: " + response.getReceived());
    }
    
    public void close() {
        this.channel.shutdown();
    }
}
