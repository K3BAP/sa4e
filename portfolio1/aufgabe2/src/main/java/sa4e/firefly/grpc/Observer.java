package sa4e.firefly.grpc;

import sa4e.firefly.grpc.common.FireflyCallable;
import sa4e.firefly.grpc.common.FireflyServer;

public class Observer implements FireflyCallable{
    public static void main(String[] args) throws Exception {
        FireflyServer server = new FireflyServer(50051);
        server.init(new Observer());
        server.awaitTermination();
    }

    @Override
    public void flashStatusChanged(boolean isFlashing) {
        System.out.println("Flash status: " + isFlashing);
    }
}