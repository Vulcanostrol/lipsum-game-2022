package gamejam.event;

import java.util.function.Function;

public class EventListener {
    private Function<Event, ?> callback;
    private String eventType;

    public void onEvent(Event e) {
        this.callback.apply(e);
    }

    /**
     * Maakt een Event Listener die koele dingen kan doen als er een bepaald event is gebeurd.
     * @param callback De functie die bij het afvuren van een event moet worden aangeroepen.
     * @param eventType moet corresponderen aan een {@link Event}.getType() - type van het event
     */
    public EventListener(Function<Event, ?> callback, String eventType) {
        this.callback = callback;
        this.eventType = eventType;
    }
}
