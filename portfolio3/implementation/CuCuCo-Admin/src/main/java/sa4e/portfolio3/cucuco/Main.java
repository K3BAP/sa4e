package sa4e.portfolio3.cucuco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import sa4e.portfolio3.common.CourseDefinition;
import sa4e.portfolio3.common.KafkaUtil;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java -jar portfolio3.jar <path_to_json>");
            System.exit(1);
        }

        CourseDefinition course = loadCourseDefinition(args[0]);

        createTopicsFromSegments(course);
        KafkaUtil.createTopics(List.of("timetable"));

        // TODO: Create subprocesses

        // TODO: Start race

        // TODO: Display results

        deleteTopicsFromSegments(course);
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
        return course.getTracks().stream()
            .flatMap(track -> track.getSegments().stream())
            .map(segment -> segment.getSegmentId())
            .toList();
    }
}
