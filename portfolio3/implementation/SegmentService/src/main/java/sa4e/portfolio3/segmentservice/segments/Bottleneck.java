package sa4e.portfolio3.segmentservice.segments;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.ExecutionException;

public class Bottleneck extends StandardSegment{
    @Override
    protected void handleRecord(ConsumerRecord<String, String> record) throws ExecutionException, InterruptedException {
        Thread.sleep(this.rand.nextInt(100, 500));
        super.handleRecord(record);
    }
}
