package osdi.exchange;

import java.util.Iterator;

/*
 * You may modify this as you see fit
 */
/*
 * you may not use anything in java.util.concurrent.* you may only use locks from osdi.locks.*
 */
public class NumberRange implements Iterable<Long> {
    private final long maxValue;
    private final long startValue;

    public NumberRange(long startValue, long maxValue) {
        if(maxValue <= startValue) {
            throw new IllegalStateException("start value < max value");
        }
        this.startValue = startValue;
        this.maxValue = maxValue;
    }
    public long maxValue() {
        return maxValue;
    }


    @Override
    public Iterator<Long> iterator() {
        return new NumberIterator(startValue, maxValue);
    }

    private static class NumberIterator implements Iterator<Long> {
        private final long maxValue;
        private long currentValue;

        private NumberIterator(long startValue, long maxValue) {
            this.maxValue = maxValue;
            this.currentValue = startValue;
        }

        @Override
        public boolean hasNext() {
            return currentValue <= maxValue;
        }

        @Override
        public Long next() {
            return (currentValue <= maxValue) ? currentValue++ : Long.MIN_VALUE;
        }
    }
}