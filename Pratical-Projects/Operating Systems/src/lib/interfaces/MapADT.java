package lib.interfaces;

import lib.HasTables.TwoTypePair;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.NoSuchElementException;

public interface MapADT<K, V> {


    /**
     * Adds the given key-value pair to the map and returns true if the key did not already exist in the map.
     * If the key already exists, the value is updated and false is returned.
     *
     * @param key   the key to add
     * @param value the value to add
     */
    void put(K key, V value) throws IllegalArgumentException;

    /**
     * Removes the key-value pair with the given key from the map and returns the value.
     * If the key does not exist, null is returned.
     *
     * @param key the key to remove
     * @return the value associated with the removed key
     */
    V remove(K key) throws EmptyCollectionException, NoSuchElementException, IllegalArgumentException, ElementNotFoundException;

    /**
     * Returns the value associated with the given key.
     * If the key does not exist, null is returned.
     *
     * @param key the key to search for
     * @return the value associated with the given key
     */
    V get(K key) throws EmptyCollectionException, NoSuchElementException, IllegalArgumentException;

    /**
     * Returns true if the map contains the given key.
     *
     * @param key the key to search for
     * @return true if the map contains the given key
     */
    boolean containsKey(K key) throws IllegalArgumentException;

    /**
     * Returns true if the map contains the given value.
     *
     * @param value the value to search for
     * @return true if the map contains the given value
     */
    boolean containsValue(V value) throws IllegalArgumentException;

    /**
     * Returns an array of the keys in the map.
     *
     * @return an array of the keys in the map
     */
    ListADT<K> getKeys();

    /**
     * Returns an array of the keys in the map that are associated with the given value.
     *
     * @param value the value to search for
     * @return an array of the keys in the map that are associated with the given value
     */
    ListADT<K> getKeys(V value);

    /**
     * Returns an array of the values in the map.
     *
     * @return an array of the values in the map
     */
    ListADT<V> getValues();

    /**
     * Returns an array of the key-value pairs in the map
     *
     * @return an array of the key-value pairs in the map
     */
    ListADT<TwoTypePair<K, V>> entrySet();

    /**
     * Returns true if the map contains no key-value pairs.
     *
     * @return true if the map contains no key-value pairs
     */
    boolean isEmpty();

    /**
     * Returns the number of key-value pairs in the map.
     *
     * @return the number of key-value pairs in the map
     */
    int size();

    /**
     * Clears the map.
     */
    void clear();

    /**
     * Returns a string representation of the map.
     *
     * @return a string representation of the map
     */
    String toString();
}
