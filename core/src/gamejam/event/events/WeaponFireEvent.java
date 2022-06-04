package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;
import gamejam.weapons.Weapon;

public class WeaponFireEvent implements Event {

    private final Weapon weapon;

    public WeaponFireEvent(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public String getType() {
        return EventType.WEAPON_FIRED;
    }
}
