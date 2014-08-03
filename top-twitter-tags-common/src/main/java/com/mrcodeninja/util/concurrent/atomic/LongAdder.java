package com.mrcodeninja.util.concurrent.atomic;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementation for jsr166e.LongAdder.  Placeholder implementation used in preparation for Java SE 8 release.
 *
 * @author mrcodeninja
 * @see <a href="http://gee.cs.oswego.edu/dl/jsr166/dist/jsr166edocs/jsr166e/LongAdder.html">jsr166e.LongAdder</a>
 */
public class LongAdder extends Number implements Serializable {
    private AtomicLong value;

    /**
     * Creates a new adder with initial sum of zero.
     */
    public LongAdder() {
        value = new AtomicLong();
    }

    /**
     * Adds the given value.
     *
     * @param x the value to add
     */
    public void add(long x) {
        value.addAndGet(x);
    }

    /**
     * Equivalent to {@code add(-1)};
     */
    public void decrement() {
        value.decrementAndGet();
    }

    /**
     * Returns the {@link #sum()} as a double after a widening primitive conversion.
     *
     * @return the numeric value represented by this object after conversion to type {@code double}
     */
    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    /**
     * Returns the {@link #sum()} as a {@code float} after a widening primitive conversion.
     *
     * @return the numeric value represented by this object after conversion to type {@code float}
     */
    @Override
    public float floatValue() {
        return value.floatValue();
    }

    /**
     * Equivalent to {@code add(1)}.
     */
    public void increment() {
        value.incrementAndGet();
    }

    /**
     * Returns the {@link #sum()} as an {@code int} after a narrowing primitive conversion.
     *
     * @return the sum
     */
    @Override
    public int intValue() {
        return value.intValue();
    }

    /**
     * Equivalent to {@link #sum()}.
     *
     * @return the sum
     */
    @Override
    public long longValue() {
        return value.longValue();
    }

    /**
     * Resets variables maintaining the sum to zero. This method may be a useful alternative to creating a new adder,
     * but is only effective if there are no concurrent updates. Because this method is intrinsically racy, it should
     * only be used when it is known that no threads are concurrently updating.
     */
    public void reset() {
        value.set(0);
    }

    /**
     * Returns the current sum. The returned value is NOT an atomic snapshot: Invocation in the absence of concurrent
     * updates returns an accurate result, but concurrent updates that occur while the sum is being calculated might not
     * be incorporated.
     *
     * @return the sum
     */
    public long sum() {
        return value.longValue();
    }

    /**
     * Equivalent in effect to sum() followed by reset(). This method may apply for example during quiescent points
     * between multithreaded computations. If there are updates concurrent with this method, the returned value is not
     * guaranteed to be the final value occurring before the reset.
     *
     * @return the sum
     */
    public long sumThenReset() {
        return value.getAndSet(0);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
