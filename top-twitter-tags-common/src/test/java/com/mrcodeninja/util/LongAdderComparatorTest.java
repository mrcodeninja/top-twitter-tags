package com.mrcodeninja.util;

import com.mrcodeninja.util.concurrent.atomic.LongAdder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author mrcodeninja
 */
public class LongAdderComparatorTest {
    private static final String ENTRY_STRING_1 = "String 1";
    private static final String ENTRY_STRING_2 = "String 2";

    private LongAdder adder1 = new LongAdder();
    private LongAdder adder2 = new LongAdder();

    Map.Entry<String, LongAdder> entry1;
    Map.Entry<String, LongAdder> entry2;

    @Before
    public void setUp() throws Exception {
        adder1 = new LongAdder();
        adder2 = new LongAdder();
    }

    @After
    public void tearDown() throws Exception {
        adder1 = null;
        adder2 = null;
    }

    @Test
    public void compareGreaterThan() throws Exception {
        adder1.add(1);
        adder2.add(2);
        entry1 = new AbstractMap.SimpleEntry<String, LongAdder>(ENTRY_STRING_1, adder1);
        entry2 = new AbstractMap.SimpleEntry<String, LongAdder>(ENTRY_STRING_2, adder2);
        final int GREATER_THAN = 1;
        int comparison;

        comparison = new LongAdderComparator<String>().compare(entry1, entry2);

        assertThat(comparison, is(GREATER_THAN));
    }

    @Test
    public void compareCountsEqualKeyGreaterThan() throws Exception {
        adder1.add(1);
        adder2.add(1);
        entry1 = new AbstractMap.SimpleEntry<String, LongAdder>(ENTRY_STRING_1, adder1);
        entry2 = new AbstractMap.SimpleEntry<String, LongAdder>(ENTRY_STRING_2, adder2);
        final int GREATER_THAN = 1;
        int comparison;

        comparison = new LongAdderComparator<String>().compare(entry1, entry2);

        assertThat(comparison, is(GREATER_THAN));
    }

    @Test
    public void compareCountsEqualKeyLessThan() throws Exception {
        adder2.add(1);
        adder1.add(1);
        entry2 = new AbstractMap.SimpleEntry<String, LongAdder>(ENTRY_STRING_2, adder2);
        entry1 = new AbstractMap.SimpleEntry<String, LongAdder>(ENTRY_STRING_1, adder1);
        final int LESS_THAN = -1;
        int comparison;

        comparison = new LongAdderComparator<String>().compare(entry2, entry1);

        assertThat(comparison, is(LESS_THAN));
    }

    @Test
    public void compareLessThan() throws Exception {
        adder2.add(2);
        adder1.add(1);
        entry2 = new AbstractMap.SimpleEntry<String, LongAdder>(ENTRY_STRING_2, adder2);
        entry1 = new AbstractMap.SimpleEntry<String, LongAdder>(ENTRY_STRING_1, adder1);
        final int LESS_THAN = -1;
        int comparison;

        comparison = new LongAdderComparator<String>().compare(entry2, entry1);

        assertThat(comparison, is(LESS_THAN));
    }
}
