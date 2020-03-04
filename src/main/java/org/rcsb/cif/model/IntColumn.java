package org.rcsb.cif.model;

import java.util.stream.IntStream;

/**
 * A column that provides int data.
 */
public interface IntColumn extends Column<int[]> {
    /**
     * Type-safe access to the native type of data stored in this column.
     * @param row the index to retrieve
     * @return an int value
     */
    int get(int row);

    /**
     * An IntStream of all registered values.
     * @return ints
     */
    default IntStream values() {
        return IntStream.range(0, getRowCount())
                .map(this::get);
    }

    /**
     * This is how all IntColumns parse their data.
     * @param text the raw data
     * @return an int
     */
    static int parseInt(String text) {
        if (text.isEmpty() || ".".equals(text) || "?".equals(text)) {
            return 0;
        }
        return Integer.parseInt(text);
    }
}
