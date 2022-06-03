package gamejam.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Singleton die {@link Event}s bewaart om geprocest te worden.
 */
public class EventQueue {
    private ConcurrentLinkedQueue<Event> eventQueue;
    private HashMap<String, ArrayList<EventListener>> listenersByType;
    private static EventQueue instance;

    private EventQueue() {
        this.eventQueue = new ConcurrentLinkedQueue<>();
        this.listenersByType = new HashMap<>();
    }

    private EventQueue getInstance() {
        if (EventQueue.instance != null) {
            return EventQueue.instance;
        }
        EventQueue.instance = new EventQueue();
        return EventQueue.instance;
    }

    /**
     * voeg een event toe aan de queue.
     * @param event
     */
    public void add(Event event) {
        this.getInstance().eventQueue.add(event);
    }

    /**
     * haal een event uit de queue, En passt hem door naar alle listeners.
     * @return Optional met de volgende Event uit de queue of een empty optional. Dit
     * zodat je als aanroepende mainloop ook nog iets met een event kan doen als je wil.
     */
    public Optional<Event> get() {
        final var e = this.getInstance().eventQueue.poll();
        if (e == null) {
            return Optional.empty();

        }
        final ArrayList<EventListener> listeners = listenersByType.get(e.getType());
        for (var listener : listeners) {
            listener.onEvent(e);
        }
        return Optional.of(e);
    }

    /**
     * Voeg een nieuwe listener toe die iets gaat doen met een event als ie langskomt
     * @param eventListener De listener (wooh documentatie).
     * @param eventType moet corresponderen aan een {@link Event}.getType() - type van het event
     */
    public void registerListener(EventListener eventListener, String eventType) {
        if (this.listenersByType.containsKey(eventType)) {
            var currentListeners = this.listenersByType.get(eventType);
            currentListeners.add(eventListener);
            this.listenersByType.put(eventType, currentListeners);
            return;
        }
        var listWithTheListener = new ArrayList<EventListener>();
        listWithTheListener.add(eventListener);
        this.listenersByType.put(eventType, listWithTheListener);
    }

    /**
     * Verwijder de eventlistener (ref naar de instance die opgeslagen staat) van de event listeners.
     * @param eventListener De listener (wooh documentatie).
     * @param eventType moet corresponderen aan een {@link Event}.getType() - type van het event
     */
    public void deregisterListener(EventListener eventListener, String eventType) {
        var listeners = this.listenersByType.get(eventType);
        if (listeners.contains(eventListener)) {
            listeners.remove(eventListener);
            this.listenersByType.put(eventType, listeners);
        }
    }
}
