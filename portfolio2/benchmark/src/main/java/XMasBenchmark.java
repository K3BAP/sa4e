import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class XMasBenchmark {
    public static final int recursions = 40;
    public static void main(String[] args) {
        List<Long> results = new ArrayList<>(recursions);
        try {
            for (int i = 0; i < recursions; i++) {
                results.add(runBenchmark());
            }

            exportScores(results, "./results.txt");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long runBenchmark() throws IOException, InterruptedException{
        String url = "http://localhost:3000/api/getWishlists"; 
        long startTime = System.currentTimeMillis();
        long requestCount = 0;

        HttpClient client = HttpClient.newHttpClient();


        while (System.currentTimeMillis() - startTime < 15000) { // 60 seconds
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) { 
                requestCount++;
            }
        }


        System.out.println("Sent " + requestCount + " requests in 15 seconds.");
        return requestCount;
    }

    private static void exportScores(List<Long> scores, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Long score : scores) {
                writer.write(score.toString());
                writer.newLine();
            }
            System.out.println("Latencies exported to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
