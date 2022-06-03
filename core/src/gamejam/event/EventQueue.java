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

    public static EventQueue getInstance() {
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
        EventQueue.getInstance().eventQueue.add(event);
        if (!this.listenersByType.containsKey(event.getType())) {
            this.listenersByType.put(event.getType(), new ArrayList<EventListener>());
        }
    }

    /**
     * haal een event uit de queue, En passt hem door naar alle listeners.
     * @return true als er een event werd gehandeld, anders false.
     */
    public boolean handleNext() {
        final Event e = EventQueue.getInstance().eventQueue.poll();
        if (e == null) {
            return false;
        }
        final ArrayList<EventListener> listeners = listenersByType.get(e.getType());
        for (EventListener listener : listeners) {
            listener.onEvent(e);
        }
        return true;
    }

    /**
     * Haalt alle events uit de queue, en passt ze door naar de listeners.
     */
    public void handleAll() {
        // is niet een typo.
        while(this.handleNext());
    }

    /**
     * Voeg een nieuwe listener toe die iets gaat doen met een event als ie langskomt
     * @param eventListener De listener (wooh documentatie).
     * @param eventType moet corresponderen aan een {@link Event}.getType() - type van het event
     */
    public void registerListener(EventListener eventListener, String eventType) {
        if (this.listenersByType.containsKey(eventType)) {
            ArrayList<EventListener> currentListeners = this.listenersByType.get(eventType);
            currentListeners.add(eventListener);
            this.listenersByType.put(eventType, currentListeners);
            return;
        }
        ArrayList<EventListener> listWithTheListener = new ArrayList<>();
        listWithTheListener.add(eventListener);
        this.listenersByType.put(eventType, listWithTheListener);
    }

    /**
     * Verwijder de eventlistener (ref naar de instance die opgeslagen staat) van de event listeners.
     * @param eventListener De listener (wooh documentatie).
     * @param eventType moet corresponderen aan een {@link Event}.getType() - type van het event
     */
    public void deregisterListener(EventListener eventListener, String eventType) {
        ArrayList<EventListener> listeners = this.listenersByType.get(eventType);
        if (listeners.contains(eventListener)) {
            listeners.remove(eventListener);
            this.listenersByType.put(eventType, listeners);
        }
    }
}
