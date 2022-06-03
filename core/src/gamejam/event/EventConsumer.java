package gamejam.event;

import com.badlogic.gdx.Gdx;

public interface EventConsumer<T extends Event> {

    default void internalEventHandle(Event event) {
        try {
            T casted = (T) event;
            onEvent(casted);
        } catch (ClassCastException exception) {
            Gdx.app.log("EVENTS", "WARNING: Wrong cast when trying to handle event: " + event);
        }
    }

    void onEvent(T event);
}
