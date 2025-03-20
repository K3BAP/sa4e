package sa4e.portfolio3.segmentservice.segments;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import sa4e.portfolio3.common.Chariot;
import sa4e.portfolio3.common.Segment;
import sa4e.portfolio3.common.TimetableEntry;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StartAndGoal extends StandardSegment{
    private Integer rounds = 3;

    @Override
    public void init(Segment segment) throws RuntimeException {
        super.init(segment);
        consumer.subscribe(List.of(segmentData.getSegmentId(), "timetable"));
    }

    @Override
    protected void handleRecord(ConsumerRecord<String, String> record) throws ExecutionException, InterruptedException {
        if (record.topic().equals("timetable")) {
            handleTimetableEntry(record);
            return;
        }

        Chariot chariot = Chariot.fromJson(record.value());
        chariot.setRoundsPassed(chariot.getRoundsPassed() + 1);

        ProducerRecord<String, String> producerRecord = null;
        if (chariot.getRoundsPassed() >= rounds) {
            // Chariot has finished: Add timetable entry
            TimetableEntry entry = new TimetableEntry(TimetableEntry.TYPE_FINISHED, chariot.getChariotId(), System.currentTimeMillis());
            producerRecord = new ProducerRecord<>(
                    "timetable",
                    entry.toJson(),
                    entry.toJson()

            );
        }
        else {
            // Chariot continues: act as normal segment
            super.handleRecord(record);
        }

        producer.send(producerRecord).get();
    }

    private void handleTimetableEntry(ConsumerRecord<String, String> record) throws ExecutionException, InterruptedException {
        TimetableEntry entry = TimetableEntry.fromJson(record.value());
        if (entry.getType().equals(TimetableEntry.TYPE_STARTED)) {
            Chariot chariot = new Chariot("chariot_" + segmentData.getSegmentId());
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                    segmentData.getSegmentId(),
                    chariot.toJson(),
                    chariot.toJson()
            );

            producer.send(producerRecord).get();
        }
    }

    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }
}
