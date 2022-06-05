package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;

public class ScoreEvent implements Event {
    private int points;
    public ScoreEvent(int points){
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String getType() {
        return EventType.SCORE_EVENT;
    }

    @Override
    public String toString() {
        return "ScoreEvent{" +
                "points=" + points +
                '}';
    }
}
