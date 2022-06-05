package gamejam.factories;

import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.EntityDeathEvent;

public class EntityDeathManager {

    private EntityDeathManager() {
        // Private constructor to avoid instantiation
    }

    public static void init() {
        EventConsumer<EntityDeathEvent> entityDeathEventEventConsumer = EntityDeathManager::onEntityDeathEvent;
        EventQueue.getInstance().registerConsumer(entityDeathEventEventConsumer, EventType.ENTITY_DEATH);
    }

    public static void onEntityDeathEvent(EntityDeathEvent entityDeathEvent) {
        EntityFactory.getInstance().removeManagedObject(entityDeathEvent.getEntity());
    }
}
