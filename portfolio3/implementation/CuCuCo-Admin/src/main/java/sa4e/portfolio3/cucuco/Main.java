package sa4e.portfolio3.cucuco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import sa4e.portfolio3.common.CourseDefinition;
import sa4e.portfolio3.common.KafkaConfig;
import sa4e.portfolio3.common.Segment;
import sa4e.portfolio3.common.TimetableEntry;

public class Main {
    private static List<Process> processes = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java -jar CuCuCo-Admin.jar <path_to_json>");
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Loading JSON file: " + args[0]);
        CourseDefinition course = loadCourseDefinition(args[0]);

        System.out.println("Creating Kafka topics");
        createTopicsFromSegments(course);
        KafkaUtil.createTopics(List.of("timetable"));

        try {
            System.out.println("Starting processes");
            processes = createSegmentProcesses(course);

            System.out.println("Press enter to start race (For better results: wait until kafka finished processing the inputs)");
            scanner.nextLine();

            long startTimestamp = startRace();
            Thread monitorThread = new Thread(() -> monitorRace(startTimestamp, countChariots(course)));
            monitorThread.start();

            System.out.println("Press enter to stop race");
            scanner.nextLine();
            monitorThread.interrupt();
            monitorThread.join();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Shutting down...");
            for (Process p : processes) {
                p.destroyForcibly().waitFor();
                System.out.println(p + " destroyed: " + !p.isAlive());
            }
            deleteTopicsFromSegments(course);
            KafkaUtil.deleteTopics(List.of("timetable"));
        }
    }

    private static CourseDefinition loadCourseDefinition(String filename) throws IOException {
        String jsonString = Files.readString(Paths.get(filename));
        return CourseDefinition.fromJSON(jsonString);
    }

    private static void createTopicsFromSegments(CourseDefinition course) {
        KafkaUtil.createTopics(getAllSegmentIds(course));
    }

    private static void deleteTopicsFromSegments(CourseDefinition course) {
        KafkaUtil.deleteTopics(getAllSegmentIds(course));
    }

    private static List<String> getAllSegmentIds(CourseDefinition course) {
        return getAllSegmentsStream(course)
            .map(Segment::getSegmentId)
            .toList();
    }

    private static Stream<Segment> getAllSegmentsStream(CourseDefinition course) {
        return course.getTracks().stream()
                .flatMap(track -> track.getSegments().stream());

    }

    private static List<Process> createSegmentProcesses(CourseDefinition course) {
        return getAllSegmentsStream(course)
                .map(segment -> {
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder(
                                "java",
                                "-jar",
                                "../SegmentService/build/libs/SegmentService.jar",
                                segment.toJson()
                        );

                        // Redirect output to the parent process's stdout
                        //processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                        //processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT); // Also inherit stderr

                        // Start the process
                        return processBuilder.start();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    private static long countChariots(CourseDefinition course) {
        return getAllSegmentsStream(course)
                .filter(segment -> segment.getType().equals("start-goal"))
                .count();
    }

    private static long startRace() {
        long start = System.currentTimeMillis();
        TimetableEntry entry = new TimetableEntry(TimetableEntry.TYPE_STARTED, null, start);
        KafkaUtil.send("timetable", entry.toJson(), entry.toJson());
        return start;
    }

    private static void monitorRace(Long startTimestamp, Long chariotCount) {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaConfig.getConsumerProps("admin"))) {
            consumer.subscribe(Collections.singletonList("timetable"));
            while (chariotCount > 0) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    TimetableEntry entry = TimetableEntry.fromJson(record.value());
                    if (entry.getType().equals(TimetableEntry.TYPE_FINISHED)) {
                        System.out.println(entry.getChariotId() + " finished at time " + entry.getTimestamp()
                                + "\nElapsed time " + (entry.getTimestamp() - startTimestamp) + "ms");
                        chariotCount--;
                    }
                    else if (entry.getType().equals(TimetableEntry.TYPE_FAILED)) {
                        System.out.println(entry.getChariotId() + " failed to greet caesar. Finish time " + entry.getTimestamp()
                                + "\nElapsed time " + (entry.getTimestamp() - startTimestamp) + "ms"
                                + "\nThe lions shall decide on his fate.");
                        chariotCount--;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Race is over. Press Enter to exit");
    }
}
