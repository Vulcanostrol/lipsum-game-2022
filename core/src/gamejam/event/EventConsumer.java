package gamejam.event;

public interface EventConsumer<T extends Event> {

    default void internalEventHandle(Event event) {
        onEvent((T) event);
    }

    void onEvent(T event);
}
