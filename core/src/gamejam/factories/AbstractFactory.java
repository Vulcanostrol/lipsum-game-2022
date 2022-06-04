package gamejam.factories;

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

    protected final Class<T> objectType;

    public AbstractFactory(Class<T> objectType) {
        this.objectType = objectType;
    }

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
     * Will try to delegate objects to lower factories.
     * @param managedObject the object to add
     */
    public void addManagedObject(T managedObject) {
        if (managedObject.getClass().isAssignableFrom(getAcceptedClass()) || subFactories.size() == 0) {
            // If we are the factory to handle this type, or there are no subfactories, manage this object
            forceAddManagedObjectToThisFactory(managedObject);
        } else {
            // Try to look for a subfactory that is more specifically designed for this type
            boolean foundBetterSubFactory = false;
            for (AbstractFactory factory: subFactories) {
                if (factory.getAcceptedClass().isAssignableFrom(managedObject.getClass())) {
                    factory.addManagedObject(managedObject);
                    foundBetterSubFactory = true;
                    break;
                }
            }

            // If we didn't find a good subfactory, add it to our objects
            if (!foundBetterSubFactory) {
                forceAddManagedObjectToThisFactory(managedObject);
            }
        }
    }

    /**
     * WARNING: Only use when you do NOT want to add the entity to the correct subfactory.
     * Will forcefully add the object to this factory without looking to subfactories.
     * @param managedObject the object to add
     */
    public void forceAddManagedObjectToThisFactory(T managedObject) {
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
     * Removes all objects from the managedObjects set
     */
    public void removeManagedObjects(){
        managedObjects.clear();
    }

    /**
     * Removes all objects from the managedObjects set and also does this for all the subFactories
     */
    public void recursiveRemoveManagedObjects(){
        removeManagedObjects();
        for (AbstractFactory factory: subFactories) {
            factory.recursiveRemoveManagedObjects();
        }
    }

    /**
     * Adds a sub-factory to this factory. Please don't make cycles.
     * @param subFactory the sub-factory to add
     */
    public void addSubFactory(AbstractFactory<? extends T> subFactory) {
        subFactories.add(subFactory);
    }

    public Class<T> getAcceptedClass() {
        return objectType;
    }
}
