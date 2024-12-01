package sa4e.firefly.grpc.observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sa4e.firefly.grpc.FireflyObserver;

public class ProcessManager {
    private static List<Process> fireflyProcesses = new ArrayList<>();
    private static FireflyGrid grid = null;

    public static void createFrieflyProcess(FireflyData fireflyData) throws IOException {
        StringBuilder commandBuilder = new StringBuilder()
            .append("java -jar ./build/libs/firefly-grpc.jar ")
            .append(fireflyData.serverPort);

        for (int i = 0; i < fireflyData.clientPorts.length; i++) {
            commandBuilder
                .append(" ")
                .append(fireflyData.clientPorts[i]);
        }

        System.out.println(commandBuilder.toString());
        Process process = Runtime.getRuntime().exec(commandBuilder.toString());
        
        fireflyProcesses.add(process);
        //System.out.println("Processes in total: " + fireflyProcesses.size());
        // process.getInputStream().transferTo(System.out);
    }

    public static void killProcesses() {
        fireflyProcesses.forEach(Process::destroy);
        fireflyProcesses.clear();

        if (grid != null) grid.clear();
    }

    public static void createTorus(final int COLUMNS, final int ROWS) {
        // Determine Server Ports for the Fireflies
        int currentPort = 50052;
        FireflyData fireflies[][] = new FireflyData[COLUMNS][ROWS];
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                fireflies[i][j] = new FireflyData(currentPort++);
            }
        }

        // Assign neighbor ports
        Topology torusTopology = new TorusTopology();
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                List<FireflyData> neighbors = torusTopology.getNeighbours(fireflies, i, j);
                neighbors.add(0, new FireflyData(FireflyObserver.getPort())); // Port of Observer
                fireflies[i][j].clientPorts = new int[neighbors.size()];
                for (int k = 0; k < neighbors.size(); k++) {
                    fireflies[i][j].clientPorts[k] = neighbors.get(k).getServerPort();
                }
            }
        }

        // Create Firefly representations in grid (if defined)
        if (grid != null) {
            for (int j = 0; j < ROWS; j++) {
                for (int i = 0; i < COLUMNS; i++) {
                    grid.add(new FireflyRepresentation(fireflies[i][j].getServerPort()));
                }
            }
        }

        // Kick off processes
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                try {
					createFrieflyProcess(fireflies[i][j]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }

    }

    public static void setGrid(FireflyGrid fgrid) {
        grid = fgrid;
    }

    public static class FireflyData {
        private int serverPort;
        public int clientPorts[];

        public FireflyData(int serverPort) {
            this.serverPort = serverPort;
        }

        public int getServerPort() {
            return serverPort;
        }
    }
}
