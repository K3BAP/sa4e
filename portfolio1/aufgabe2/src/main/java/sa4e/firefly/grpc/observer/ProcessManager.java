package sa4e.firefly.grpc.observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessManager {
    private static List<Process> fireflyProcesses = new ArrayList<>();

    public static void createProcess() throws IOException {
        Process process = Runtime.getRuntime().exec("java -jar ./build/libs/firefly-grpc.jar 50052 50051");
        fireflyProcesses.add(process);
        // process.getInputStream().transferTo(System.out);
    }

    public static void killProcesses() {
        fireflyProcesses.forEach(Process::destroy);
    }

    public static void createTorus(int ROWS, int COLUMNS) {
        
    }
}
