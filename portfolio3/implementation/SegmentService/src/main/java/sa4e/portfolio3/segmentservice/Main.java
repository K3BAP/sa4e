package sa4e.portfolio3.segmentservice;

import sa4e.portfolio3.common.Segment;
import sa4e.portfolio3.segmentservice.segments.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar segmentservice-jar.jar <json_data>");
            System.exit(1);
        }

        System.out.println("Starting SegmentService for segment " + args[0]);
        Segment segmentData = Segment.fromJson(args[0]);
        SegmentRoutine routine = null;

        switch (segmentData.getType()) {
            case "normal":
                routine = new StandardSegment();
                break;
            case "start-goal":
                routine = new StartAndGoal();
                break;
            case "bottleneck":
                routine = new Bottleneck();
                break;
            case "caesar":
                routine = new Caesar();
                break;
            default:
                System.out.println("Unknown segment type: " + segmentData.getType());
                System.exit(1);
        }

        try {
            routine.init(segmentData);
            routine.mainLoop();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            routine.close();
        }

    }
}
