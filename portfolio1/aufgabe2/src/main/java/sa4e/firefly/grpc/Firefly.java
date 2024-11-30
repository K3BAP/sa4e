package sa4e.firefly.grpc;

import sa4e.firefly.grpc.common.FireflyClient;

public class Firefly {
    public static void main(String[] args) {
        FireflyClient client = new FireflyClient();
        client.init();
        client.notifyFirefly(true);
        client.close();
    }
}
