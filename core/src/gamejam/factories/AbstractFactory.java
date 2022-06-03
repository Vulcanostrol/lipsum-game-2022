package gamejam.factories;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.objects.Entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Describes a generic factory.
 * It is advised to make factories singletons
 * @param <T> The type of object in this factory
 */
public abstract class AbstractFactory<T> {

    private final Set<T> managedObjects = new HashSet<>();
    private final Set<AbstractFactory<? extends T>> subFactories = new HashSet<>();

    /**
     * @return all managed objects of this factory and all sub-factories
     */
    public Stream<T> getAllManagedObjects() {
        Stream<T> outputStream = managedObjects.stream();
        Stream<T> subFactoryStream = subFactories.stream().flatMap(AbstractFactory::getAllManagedObjects);
        return Stream.concat(outputStream, subFactoryStream);

    }

    /**
     * Adds an object to the set of managed objects of this factory.
     * @param managedObject the object to add
     */
    public void addManagedObject(T managedObject) {
        managedObjects.add(managedObject);
    }

    /**
     * Removes the managed object. If it isn't found, this method will try to remove it from sub-factories
     * @param managedObject the object to remove
     * @return whether the removal was successful at some level
     */
    public boolean removeManagedObject(T managedObject) {
        boolean isRemoved = managedObjects.remove(managedObject);
        if (!isRemoved) {
            // Ignore these warnings, we know it'll be fine
            for (AbstractFactory factory: subFactories) {
                isRemoved = factory.removeManagedObject(managedObject);
                if (isRemoved) {
                    break;
                }
            }
        }
        return isRemoved;
    }

    /**
     * Adds a sub-factory to this factory. Please don't make cycles.
     * @param subFactory the sub-factory to add
     */
    public void addSubFactory(AbstractFactory<? extends T> subFactory) {
        subFactories.add(subFactory);
    }
}
