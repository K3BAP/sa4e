package sa4e.firefly.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import sa4e.firefly.grpc.FireflyProto.FireflyRequest;
import sa4e.firefly.grpc.FireflyProto.FireflyReply;


public class Firefly {
    public static void main(String[] args) {
        // Create a channel to the gRPC server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                                                      .usePlaintext() // Disable TLS for local testing
                                                      .build();

        // Create a stub for calling the service
        FireflyServiceGrpc.FireflyServiceBlockingStub stub = FireflyServiceGrpc.newBlockingStub(channel);

        // Create a request
        FireflyRequest request = FireflyRequest.newBuilder()
                                           .setName("World")
                                           .build();

        // Call the remote procedure
        FireflyReply response = stub.sayHello(request);

        // Print the response
        System.out.println("Response from server: " + response.getMessage());

        // Shutdown the channel
        channel.shutdown();
    }
}
