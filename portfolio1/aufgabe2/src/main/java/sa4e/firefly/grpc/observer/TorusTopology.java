package sa4e.firefly.grpc.observer;

import java.util.ArrayList;
import java.util.List;

import sa4e.firefly.grpc.observer.ProcessManager.FireflyData;

public class TorusTopology implements Topology {

    @Override
    public List<FireflyData> getNeighbours(FireflyData[][] maze, int x, int y) {
        final int MAX_X = maze.length - 1;
        final int MAX_Y = maze[0].length - 1;

        List<FireflyData> neighbours = new ArrayList<>(4);

        // Get left neighbour
        if (x == 0) {
            // Torus
            neighbours.add(maze[MAX_X][y]);
        }
        else {
            neighbours.add(maze[x - 1][y]);
        }

        // Get right neighbour
        if (x == MAX_X) {
            // Torus
            neighbours.add(maze[0][y]);
        }
        else {
            neighbours.add(maze[x+1][y]);
        }

        // Get top neighbour
        if (y > 0) {
            neighbours.add(maze[x][y-1]);
        }

        // Get bottom neighbour
        if (y < MAX_Y) {
            neighbours.add(maze[x][y+1]);
        }

        return neighbours;
    }
    
}
