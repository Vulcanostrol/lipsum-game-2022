package gamejam.event;

/**
 * Extend dit met events die betekenisvol zijn (bijv. InputEvent ofzo).
 */
public interface Event {
    /**
     * String die het type van het event beschrijft.
     * Bijoorbeeld "DeathEvent" voor een death event.
     * @return
     */
    String getType();
}
