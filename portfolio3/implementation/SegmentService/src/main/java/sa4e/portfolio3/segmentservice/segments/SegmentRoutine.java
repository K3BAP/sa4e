package sa4e.portfolio3.segmentservice.segments;

import sa4e.portfolio3.common.Segment;

public interface SegmentRoutine {
    void mainLoop() throws RuntimeException;
    void init(Segment segment);
    void close();
}
