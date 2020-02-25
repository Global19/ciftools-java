package org.rcsb.cif.binary.encoding;

import org.rcsb.cif.binary.data.IntArray;
import org.rcsb.cif.binary.data.SignedIntArray;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Stores the input integer array as an array of consecutive differences.</p>
 *
 * <pre>
 * Delta {
 *     kind = "Delta"
 *     origin: number
 *     srcType: int[]
 * }
 * </pre>
 */
public class DeltaEncoding implements Encoding<SignedIntArray, SignedIntArray> {
    private int origin;
    private int srcType;

    public DeltaEncoding() {

    }

    public DeltaEncoding(int origin, int srcType) {
        this.origin = origin;
        this.srcType = srcType;
    }

    @Override
    public Map<String, Object> getMapRepresentation() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("kind", "Delta");
        map.put("origin", origin);
        map.put("srcType", srcType);
        return map;
    }

    @Override
    public SignedIntArray decode(SignedIntArray data) {
        int[] input = data.getData();
        SignedIntArray output = (SignedIntArray) IntArray.create(srcType, input.length);
        output.setEncoding(data.getEncoding());

        int n = input.length;
        if (n == 0) {
            return output;
        }

        int[] outputArray = output.getData();
        outputArray[0] = input[0] + origin;
        for (int i = 1; i < n; ++i) {
            outputArray[i] = input[i] + outputArray[i - 1];
        }

        return output;
    }

    @Override
    public SignedIntArray encode(SignedIntArray data) {
        int srcType = data.getType();
        int[] inputArray = data.getData();
        if (inputArray.length == 0) {
            IntArray output = IntArray.create(srcType, 0);

            return create(data, srcType, output, 0);
        }

        IntArray output = IntArray.create(srcType, inputArray.length);
        int[] outputArray = output.getData();
        int origin = inputArray[0];
        outputArray[0] = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) {
            outputArray[i] = inputArray[i] - inputArray[i - 1];
        }
        outputArray[0] = 0;

        return create(data, srcType, output, origin);
    }

    @SuppressWarnings("unchecked")
    private <T extends SignedIntArray> T create(T data, int srcType, IntArray output, int origin) {
        Deque<Encoding<?, ?>> enc = new ArrayDeque<>(data.getEncoding());
        this.origin = origin;
        this.srcType = srcType;
        enc.add(this);
        output.setEncoding(enc);
        return (T) output;
    }

    @Override
    public String toString() {
        return "DeltaEncoding{" +
                "origin=" + origin +
                ", srcType=" + srcType +
                '}';
    }
}
