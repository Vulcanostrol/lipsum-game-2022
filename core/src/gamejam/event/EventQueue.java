package gamejam.event;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Singleton die {@link Event}s bewaart om geprocest te worden.
 */
public class EventQueue {
    private final ConcurrentLinkedQueue<Event> eventQueue;
    private final HashMap<EventConsumer<? extends Event>, String> toRegisterConsumersByType;
    private final HashMap<EventConsumer<? extends Event>, String> toDeregisterConsumersByType;
    private final HashMap<String, ArrayList<EventConsumer<? extends Event>>> eventConsumersByType;
    private static EventQueue instance;

    private boolean handling;

    private EventQueue() {
        this.eventQueue = new ConcurrentLinkedQueue<>();
        this.toRegisterConsumersByType = new HashMap<>();
        this.toDeregisterConsumersByType = new HashMap<>();
        this.eventConsumersByType = new HashMap<>();
    }

    public static EventQueue getInstance() {
        if (EventQueue.instance != null) {
            return EventQueue.instance;
        }
        EventQueue.instance = new EventQueue();
        return EventQueue.instance;
    }

    /**
     * Invokes an event, should use invoke(...) now.
     */
    @Deprecated
    public void add(Event event) {
        invoke(event);
    }

    public void invoke(Event event) {
        EventQueue.getInstance().eventQueue.add(event);
        if (!this.eventConsumersByType.containsKey(event.getType())) {
            this.eventConsumersByType.put(event.getType(), new ArrayList<>());
        }
    }

    /**
     * haal een event uit de queue, En passt hem door naar alle consumers.
     * @return true als er een event werd gehandeld, anders false.
     */
    public boolean handleNext() {
        final Event e = EventQueue.getInstance().eventQueue.poll();
        if (e == null) {
            return false;
        }
        final ArrayList<EventConsumer<? extends Event>> consumers = eventConsumersByType.get(e.getType());
        for (EventConsumer<? extends Event> consumer : consumers) {
            consumer.internalEventHandle(e);
        }
        return true;
    }

    /**
     * Haalt alle events uit de queue, en passt ze door naar de consumers.
     */
    public void handleAll() {
        // is niet een typo.
        handling = true;
        while(this.handleNext());
        handling = false;
        handleRegistries();
    }

    public void handleRegistries() {
        for (EventConsumer<? extends Event> consumer : toRegisterConsumersByType.keySet()) {
            registerConsumer(consumer, toRegisterConsumersByType.get(consumer));
        }
        for (EventConsumer<? extends Event> consumer : toDeregisterConsumersByType.keySet()) {
            deregisterConsumer(consumer, toDeregisterConsumersByType.get(consumer));
        }

        toRegisterConsumersByType.clear();
        toDeregisterConsumersByType.clear();
    }

    public void registerConsumer(EventConsumer<? extends Event> consumer, String eventType) {
        if (!handling) {
            if (this.eventConsumersByType.containsKey(eventType)) {
                ArrayList<EventConsumer<? extends Event>> currentConsumer = this.eventConsumersByType.get(eventType);
                currentConsumer.add(consumer);
                this.eventConsumersByType.put(eventType, currentConsumer);
                return;
            }
            ArrayList<EventConsumer<? extends Event>> listWithTheConsumer = new ArrayList<>();
            listWithTheConsumer.add(consumer);
            this.eventConsumersByType.put(eventType, listWithTheConsumer);
        } else {
            toRegisterConsumersByType.put(consumer, eventType);
        }
    }

    /**
     * Verwijder de eventConsumer (ref naar de instance die opgeslagen staat) van de event consumers.
     * @param eventConsumer De consumer (wooh documentatie).
     * @param eventType moet corresponderen aan een {@link Event}.getType() - type van het event
     */
    public void deregisterConsumer(EventConsumer<? extends Event> eventConsumer, String eventType) {
        if (!handling) {
            ArrayList<EventConsumer<? extends Event>> consumers = this.eventConsumersByType.get(eventType);
            if (consumers.contains(eventConsumer)) {
                consumers.remove(eventConsumer);
                this.eventConsumersByType.put(eventType, consumers);
            }
        }
        else {
            toDeregisterConsumersByType.put(eventConsumer, eventType);
        }
    }
}
