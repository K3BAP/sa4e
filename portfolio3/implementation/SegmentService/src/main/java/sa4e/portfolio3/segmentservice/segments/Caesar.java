package sa4e.portfolio3.segmentservice.segments;

import sa4e.portfolio3.common.Chariot;

import java.util.concurrent.ExecutionException;

public class Caesar extends StandardSegment{
    @Override
    protected void sendToNextSegment(String chariotJson) throws ExecutionException, InterruptedException {
        Chariot chariot = Chariot.fromJson(chariotJson);
        System.out.println("Chariot greeted caesar: " + chariot.getChariotId());
        chariot.setHasGreetedCaesar(true);
        super.sendToNextSegment(chariot.toJson());
    }
}
