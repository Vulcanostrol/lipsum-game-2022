package gamejam.objects;

public interface Damageable {
    void damage(float damage);
    void heal(float hp);
    float getHealth();
    float getMaxHealth();
}
