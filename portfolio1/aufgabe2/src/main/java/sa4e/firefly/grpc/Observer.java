package sa4e.firefly.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import sa4e.firefly.grpc.FireflyProto.HelloRequest;
import sa4e.firefly.grpc.FireflyProto.HelloReply;

public class Observer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                                     .addService(new GreeterImpl())
                                     .build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started on port 50051");

        server.awaitTermination();
    }

    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
            String name = request.getName();
            String message = "Hello, " + name + "!";

            HelloReply reply = HelloReply.newBuilder()
                                         .setMessage(message)
                                         .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}