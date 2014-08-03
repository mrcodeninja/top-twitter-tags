package com.mrcodeninja.toptwittertags.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * TODO: Proper dependency injection needed for this. Current implementation can't isolate tests to specific methods.
 *
 * @author mrcodeninja
 */
public class StringCountRepositoryTest {
    private StringCountRepository repository;

    private static final String KEY = "KeyString";
    private static final String KEY1 = "KeyString1";
    private static final String KEY2 = "KeyString2";

    @Before
    public void setUp() throws Exception {
        repository = new StringCountRepository();
    }

    @After
    public void tearDown() throws Exception {
        repository = null;
    }

    @Test
    public void add() {
        int expected = 1;

        repository.add(KEY);

        assertThat(repository.size(), is(expected));
    }

    @Test
    public void clear() {
        int expected = 0;
        repository.add(KEY);

        repository.clear();

        assertThat(repository.size(), is(expected));
    }

    @Test
    public void remove() {
        int expected = 0;
        repository.add(KEY);

        repository.remove(KEY);

        assertThat(repository.size(), is(expected));
    }

    @Test
    public void removeAll() {
        int expected = 0;
        repository.add(KEY1);
        repository.add(KEY2);

        repository.removeAll();

        assertThat(repository.size(), is(expected));
    }

    @Test
    public void size() {
        int expected = 2;

        repository.add(KEY1);
        repository.add(KEY2);

        assertThat(repository.size(), is(expected));
    }
}
