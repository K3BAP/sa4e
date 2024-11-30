package sa4e.firefly.grpc.common;


import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import sa4e.firefly.grpc.FireflyProto.FireflyRequest;
import sa4e.firefly.grpc.FireflyProto.FireflyReply;
import sa4e.firefly.grpc.*;

public class FireflyServer {
    private Server server;
    private int port;
    public FireflyServer(int port) {
        this.port = port;
    }

    public void init(FireflyCallable callback) throws IOException, InterruptedException {
        server = ServerBuilder.forPort(port)
                                     .addService(new FireflyServiceImpl(callback))
                                     .build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started on port 50051");

        //server.awaitTermination();
    }

    public void awaitTermination() throws InterruptedException {
        this.server.awaitTermination();
    }

    static class FireflyServiceImpl extends FireflyServiceGrpc.FireflyServiceImplBase {
        private FireflyCallable callback;
        public FireflyServiceImpl(FireflyCallable callback) {
            super();
            this.callback = callback;
        }

        @Override
        public void notifyFirefly(FireflyRequest request, StreamObserver<FireflyReply> responseObserver) {
            Boolean isFlashing = request.getIsflashing();
            callback.flashStatusChanged(isFlashing);

            FireflyReply reply = FireflyReply.newBuilder()
                                         .setReceived(true)
                                         .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}