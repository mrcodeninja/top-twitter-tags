package com.mrcodeninja.toptwittertags.service;

import com.mrcodeninja.toptwittertags.data.StringCountRepository;
import com.mrcodeninja.util.concurrent.atomic.LongAdder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Worker that prints a configurable number of string occurrence records to stdout.
 *
 * @author mrcodeninja
 */
public class StringCountPrinter implements Runnable {
    private final StringCountRepository repository;
    private final long printCount;

    private static final ArrayDeque<Map.Entry<String, LongAdder>> OUTPUT_STACK = new ArrayDeque<Map.Entry<String, LongAdder>>();
    private static final DateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final String OUTPUT_SEPARATOR = "========================================================";

    /**
     * Creates a new {@link StringCountPrinter} with the given repository and count.
     *
     * @param repository the repository containing string occurrence counts
     * @param printCount used to print the first {@code printCount} records, based on the repository's sort order
     */
    public StringCountPrinter(StringCountRepository repository, long printCount) {
        this.repository = repository;
        this.printCount = printCount;
    }

    @Override
    public void run() {
        Iterator<Map.Entry<String, LongAdder>> iterator = repository.iterator();

        for (long i = 0; i < printCount; i++) {
            if (!iterator.hasNext()) {
                break;
            }
            OUTPUT_STACK.push(iterator.next());
        }
        repository.removeAll();

        System.out.println(OUTPUT_SEPARATOR);
        System.out.println(OUTPUT_DATE_FORMAT.format(new Date()));
        System.out.println(OUTPUT_SEPARATOR);

        Map.Entry<String, LongAdder> record;
        while (!OUTPUT_STACK.isEmpty()) {
            record = OUTPUT_STACK.pop();
            System.out.println(String.format("%d\t%s", record.getValue().longValue(), record.getKey()));
        }
        System.out.println(OUTPUT_SEPARATOR);

        OUTPUT_STACK.clear();
    }
}
