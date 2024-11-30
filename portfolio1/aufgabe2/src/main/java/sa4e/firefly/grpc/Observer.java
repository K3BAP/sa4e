package sa4e.firefly.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import sa4e.firefly.grpc.FireflyProto.FireflyRequest;
import sa4e.firefly.grpc.FireflyProto.FireflyReply;

public class Observer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                                     .addService(new FireflyServiceImpl())
                                     .build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started on port 50051");

        server.awaitTermination();
    }

    static class FireflyServiceImpl extends FireflyServiceGrpc.FireflyServiceImplBase {
        @Override
        public void sayHello(FireflyRequest request, StreamObserver<FireflyReply> responseObserver) {
            String name = request.getName();
            String message = "Hello, " + name + "!";

            FireflyReply reply = FireflyReply.newBuilder()
                                         .setMessage(message)
                                         .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}