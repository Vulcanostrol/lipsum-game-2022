package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;
import gamejam.objects.Entity;

public class EntityDeathEvent implements Event {
    private final Entity entity;

    public EntityDeathEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public String getType() {
        return EventType.ENTITY_DEATH;
    }
}
