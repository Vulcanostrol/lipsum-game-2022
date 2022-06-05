package gamejam.rooms;

import gamejam.objects.collidable.enemies.AbstractEnemy;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class SpawnTableCollection {
    private final NavigableMap<Double, Class<? extends AbstractEnemy>> map = new TreeMap<Double, Class<? extends AbstractEnemy>>();
    private final Random random;
    private double total = 0;

    public SpawnTableCollection() {
        this(new Random());
    }

    public SpawnTableCollection(Random random) {
        this.random = random;
    }

    public SpawnTableCollection add(double weight, Class<? extends AbstractEnemy> result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    public Class<? extends AbstractEnemy> next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }

    public void clear() {
        map.clear();
        total = 0;
    }
}
