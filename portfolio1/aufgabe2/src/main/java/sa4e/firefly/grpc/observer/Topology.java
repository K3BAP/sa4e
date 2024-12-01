package sa4e.firefly.grpc.observer;

import java.util.List;

import sa4e.firefly.grpc.observer.ProcessManager.FireflyData;

public interface Topology {

    public List<FireflyData> getNeighbours(FireflyData[][] maze, int x, int y);
}
