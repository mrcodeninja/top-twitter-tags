package com.mrcodeninja.toptwittertags.data;

import com.mrcodeninja.util.LongAdderComparator;
import com.mrcodeninja.util.concurrent.atomic.LongAdder;
import com.mrcodeninja.util.concurrent.atomic.LongAdderTable;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Repository abstraction for managing string occurrence counting.
 *
 * @author mrcodeninja
 */
public final class StringCountRepository {
    private final LongAdderTable<String> table;

    /**
     * Creates a new {@link StringCountRepository} with an empty collection of strings.
     */
    public StringCountRepository() {
        table = new LongAdderTable<String>();
    }

    /**
     * Adds a string occurrence to the underlying data store.
     *
     * @param s the string
     */
    public void add(String s) {
        table.increment(s);
    }

    /**
     * Removes all recorded string occurrences.
     */
    public void clear() {
        table.removeAll();
    }

    /**
     * Returns an {@link Iterator} over the recorded strings along with their occurrence counters.
     *
     * @return an {@link Iterator} over the recorded strings along with their occurrence counters
     */
    public Iterator<Map.Entry<String, LongAdder>> iterator() {
        SortedSet<Map.Entry<String, LongAdder>> sortedSet = new TreeSet<Map.Entry<String, LongAdder>>(new LongAdderComparator<String>());
        sortedSet.addAll(table.entrySet());
        return sortedSet.iterator();
    }

    /**
     * Removes the given {@link String} from the underlying data store.
     *
     * @param s the {@link String} to remove
     */
    public void remove(String s) {
        table.remove(s);
    }

    /**
     * Removes all recorded strings from the underlying data store.
     */
    public void removeAll() {
        table.removeAll();
    }

    /**
     * Returns the number of unique {@link String}s managed by the underlying data store.
     *
     * @return the number of unique {@link String}s managed by the underlying data store
     */
    public int size() {
        return table.entrySet().size();
    }
}
