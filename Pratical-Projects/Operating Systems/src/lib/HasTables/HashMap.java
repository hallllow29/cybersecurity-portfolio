package lib.HasTables;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.ListADT;
import lib.interfaces.MapADT;
import lib.interfaces.UnorderedListADT;
import lib.lists.LinkedUnorderedList;

import java.util.NoSuchElementException;

public class HashMap<K, V> implements MapADT<K, V> {

    private final double LOAD_FACTOR = 0.75;
    private final static int INITIAL_CAPACITY = 16;
    private UnorderedListADT<TwoTypePair<K, V>>[] map;
    private int capacity;
    private int size;

    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    public HashMap(int initialCapacity) {
        capacity = initialCapacity;
        map = new UnorderedListADT[capacity];
        size = 0;
    }

    private void resize() {
        this.capacity *= 2;
        this.size = 0;
        UnorderedListADT<TwoTypePair<K, V>>[] oldEntries = this.map;
        this.map = new UnorderedListADT[this.capacity];
        for (UnorderedListADT<TwoTypePair<K, V>> entryList : oldEntries) {
            if (entryList != null) {
                for (TwoTypePair<K, V> entry : entryList) {
                    this.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    @Override
    public void put(K key, V value) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = Math.abs(key.hashCode()) % capacity;
        if (map[index] == null) {
            map[index] = new LinkedUnorderedList<>();
        }
        for (TwoTypePair<K, V> entry : map[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        map[index].addToRear(new TwoTypePair<>(key, value));
        size++;
        if (size > capacity * LOAD_FACTOR) {
            resize();
        }
    }

    @Override
    public V remove(K key) throws EmptyCollectionException, NoSuchElementException, IllegalArgumentException, ElementNotFoundException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (isEmpty()) {
            throw new EmptyCollectionException("HashMap");
        }

        int index = Math.abs(key.hashCode()) % capacity;
        if (map[index] == null) {
            throw new NoSuchElementException("HashMap");
        }

        for (TwoTypePair<K, V> entry : map[index]) {
            if (entry.getKey().equals(key)) {
                V removedValue = entry.getValue();
                map[index].remove(entry);
                size--;
                return removedValue;
            }
        }
        throw new NoSuchElementException("HashMap");
    }

    @Override
    public V get(K key) throws EmptyCollectionException, NoSuchElementException, IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (isEmpty()) {
            throw new EmptyCollectionException("HashMap");
        }

        int index = Math.abs(key.hashCode()) % capacity;
        if (map[index] == null) {
            throw new NoSuchElementException("HashMap");
        }

        for (TwoTypePair<K, V> entry : map[index]) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        throw new NoSuchElementException("HashMap");
    }

    @Override
    public boolean containsKey(K key) throws IllegalArgumentException {
        try {
            get(key);
            return true;
        } catch (EmptyCollectionException e) {
            return false;
        }
    }

    @Override
    public boolean containsValue(V value) throws IllegalArgumentException {
        if (isEmpty()) {
            return false;
        }

        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        for (UnorderedListADT<TwoTypePair<K, V>> entryList : map) {
            if (entryList != null) {
                for (TwoTypePair<K, V> entry : entryList) {
                    if (entry.getValue().equals(value)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public ListADT<K> getKeys() {
        UnorderedListADT<K> keys = new LinkedUnorderedList<>();
        for (UnorderedListADT<TwoTypePair<K, V>> entryList : map) {
            if (entryList != null) {
                for (TwoTypePair<K, V> entry : entryList) {
                    keys.addToRear(entry.getKey());
                }
            }
        }
        return keys;
    }

    @Override
    public ListADT<K> getKeys(V value) {
        UnorderedListADT<K> keys = new LinkedUnorderedList<>();
        for (UnorderedListADT<TwoTypePair<K, V>> entryList : map) {
            if (entryList != null) {
                for (TwoTypePair<K, V> entry : entryList) {
                    if (entry.getValue().equals(value)) {
                        keys.addToRear(entry.getKey());
                    }
                }
            }
        }
        return keys;
    }

    @Override
    public ListADT<V> getValues() {
        UnorderedListADT<V> values = new LinkedUnorderedList<>();
        for (UnorderedListADT<TwoTypePair<K, V>> entryList : map) {
            if (entryList != null) {
                for (TwoTypePair<K, V> entry : entryList) {
                    values.addToRear(entry.getValue());
                }
            }
        }
        return values;
    }

    @Override
    public ListADT<TwoTypePair<K, V>> entrySet() {
        UnorderedListADT<TwoTypePair<K, V>> entrySet = new LinkedUnorderedList<>();
        for (UnorderedListADT<TwoTypePair<K, V>> entryList : map) {
            if (entryList != null) {
                for (TwoTypePair<K, V> entry : entryList) {
                    entrySet.addToRear(entry);
                }
            }
        }
        return entrySet;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.map = new UnorderedListADT[capacity];
        size = 0;
    }

    public V putIfAbsent(K key, V value) throws EmptyCollectionException {

		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}

		if (isEmpty()) {
			this.put(key, value);
			return null;
		}

		V existingValue = null;

		try {
			existingValue = this.get(key);
		} catch (NoSuchElementException e) {
			existingValue = null;
		}

        return existingValue;
}

    @Override
    public String toString() {
        String result = "HashMap { ";
        for (UnorderedListADT<TwoTypePair<K, V>> entryList : map) {
            if (entryList != null) {
                for (TwoTypePair<K, V> entry : entryList) {
                    result += entry.toString() + ", ";
                }
            }
        }
        return result + "}";
    }
}
