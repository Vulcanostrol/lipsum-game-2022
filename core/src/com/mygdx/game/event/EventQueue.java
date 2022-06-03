package com.mygdx.game.event;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Singleton die {@link Event}s bewaart om geprocest te worden.
 */
public class EventQueue {
    private ConcurrentLinkedQueue<Event> eventQueue = null;
    private static EventQueue instance;


    private EventQueue() {
        this.eventQueue = new ConcurrentLinkedQueue<>();
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
     * haal een event uit de queue.
     * @return Optional met de volgende Event uit de queue of een empty optional.
     */
    public Optional<Event> get() {
        final var e = this.getInstance().eventQueue.poll();
        if (e != null) {
            return Optional.of(e);
        }
        return Optional.empty();
    }
}
