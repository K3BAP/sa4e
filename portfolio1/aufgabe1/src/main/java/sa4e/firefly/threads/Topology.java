package sa4e.firefly.threads;

import java.util.List;

public interface Topology {

    public List<Firefly> getNeighbours(Firefly[][] maze, int x, int y);
}
