package com.mrcodeninja.util;

import com.mrcodeninja.util.concurrent.atomic.LongAdder;

import java.util.Comparator;
import java.util.Map;

/**
 * {@link Comparator} used to sort {@code TKey} count entries by occurrence count in descending order.
 *
 * @author mrcodeninja
 */
public class LongAdderComparator<TKey extends Comparable<TKey>> implements Comparator<Map.Entry<TKey, LongAdder>> {
    @Override
    public int compare(Map.Entry<TKey, LongAdder> o1, Map.Entry<TKey, LongAdder> o2) {
        int result = Long.valueOf(o2.getValue().longValue()).compareTo(o1.getValue().longValue());

        return (result != 0 ? result : o2.getKey().compareTo(o1.getKey()));
    }
}
