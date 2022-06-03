package gamejam.event;

import java.util.function.Consumer;

public class EventListener {

    private final Consumer<Event> callback;
    private final String eventType;

    /**
     * Maakt een Event Listener die koele dingen kan doen als er een bepaald event is gebeurd.
     * @param callback De functie die bij het afvuren van een event moet worden aangeroepen.
     * @param eventType moet corresponderen aan een {@link Event}.getType() - type van het event
     */
    public EventListener(Consumer<Event> callback, String eventType) {
        this.callback = callback;
        this.eventType = eventType;
    }

    public void onEvent(Event e) {
        this.callback.accept(e);
    }

    public String getEventType() {
        return eventType;
    }
}
