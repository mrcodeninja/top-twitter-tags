package com.mrcodeninja.util.concurrent.atomic;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation for jsr166e.LongAdderTable.  Placeholder implementation used in preparation for Java SE 8 release.
 *
 * @author mrcodeninja
 * @see <a href="http://gee.cs.oswego.edu/dl/jsr166/dist/jsr166edocs/jsr166e/LongAdderTable.html">jsr166e.LongAdderTable</a>
 */
public class LongAdderTable<K> implements Serializable {
    private final ConcurrentHashMap<K, LongAdder> map;

    /**
     * Creates a new empty table.
     */
    public LongAdderTable() {
        map = new ConcurrentHashMap<K, LongAdder>();
    }

    /**
     * Adds the given value to the sum associated with the given key. If the key does not already exist in the table, it
     * is inserted.
     *
     * @param key the key
     * @param x   the value to add
     */
    public void add(K key, long x) {
        map.putIfAbsent(key, new LongAdder());
        map.get(key).add(x);
    }

    /**
     * Decrements the sum associated with the given key. If the key does not already exist in the table, it is inserted.
     *
     * @param key the key
     */
    public void decrement(K key) {
        add(key, -1);
    }

    /**
     * Returns the current set of key-value mappings.
     *
     * @return the current set of key-value mappings
     */
    public Set<Map.Entry<K, LongAdder>> entrySet() {
        return map.entrySet();
    }

    /**
     * Increments the sum associated with the given key. If the key does not already exist in the table, it is inserted.
     *
     * @param key the key
     */
    public void increment(K key) {
        map.putIfAbsent(key, new LongAdder());
        map.get(key).increment();
    }

    /**
     * If the given key does not already exist in the table, inserts the key with initial sum of zero; in either case
     * returning the adder associated with this key.
     *
     * @param key the key
     * @return the adder associated with the key
     */
    public LongAdder install(K key) {
        map.putIfAbsent(key, new LongAdder());
        return map.get(key);
    }

    /**
     * Returns the current set of keys.
     *
     * @return the current set of keys
     */
    public Set<K> keySet() {
        return map.keySet();
    }

    /**
     * Removes the given key from the table.
     *
     * @param key the key
     */
    public void remove(K key) {
        map.remove(key);
    }

    /**
     * Removes all keys from the table.
     */
    public void removeAll() {
        map.clear();
    }

    /**
     * Returns the sum associated with the given key, or zero if the key does not currently exist in the table.
     *
     * @param key the key
     * @return the sum associated with the key, or zero if the key is not in the table
     */
    public long sum(K key) {
        return (map.containsKey(key) ? map.get(key).sum() : 0);
    }

    /**
     * Returns the sum totalled across all keys.
     *
     * @return the sum totalled across all keys
     */
    public long sumAll() {
        long sum = 0;

        for (LongAdder longAdder : map.values()) {
            sum += longAdder.longValue();
        }

        return sum;
    }

    /**
     * Resets the sum associated with the given key to zero if the key exists in the table. This method does NOT add or
     * remove the key from the table (see {@link #remove(Object)}).
     *
     * @param key the key
     * @return the previous sum, or zero if the key is not in the table
     */
    public long sumThemReset(K key) {
        long sum = sum(key);
        map.replace(key, new LongAdder());
        return sum;
    }

    /**
     * Totals, then resets, the sums associated with all keys.
     *
     * @return the sum totalled across all keys
     */
    public long sumThenResetAll() {
        long sum = sumAll();
        Iterator<K> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            map.replace(iterator.next(), new LongAdder());
        }
        return sum;
    }
}
