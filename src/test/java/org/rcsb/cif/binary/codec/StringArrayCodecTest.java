package org.rcsb.cif.binary.codec;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.rcsb.cif.binary.codec.StringArrayCodec.STRING_ARRAY_CODEC;

public class StringArrayCodecTest {
    @Test
    public void testForward() {
        // create test case
        String[] plainArray = new String[] { "a", "AB", "a" };
        CodecData<String[]> plainData = CodecData.of(plainArray)
                .startEncoding(StringArrayCodec.KIND)
                .build();

        // encode
        CodecData<byte[]> encodedData = STRING_ARRAY_CODEC.encodeInternally(plainData);

        System.out.println(Arrays.toString(encodedData.getData()));

        // decode
        String[] decodedArray = STRING_ARRAY_CODEC.decodeInternally(encodedData);

        System.out.println(Arrays.toString(plainArray));
        System.out.println(Arrays.toString(decodedArray));
        assertArrayEquals(plainArray, decodedArray);
    }

    @Test
    public void testRoundtrip() {
        String[] plainArray = IntStream.range(0, 536)
                .mapToObj(i -> "n")
                .toArray(String[]::new);
        CodecData<String[]> plainData = CodecData.of(plainArray)
                .startEncoding(StringArrayCodec.KIND)
                .build();

        // encode
        CodecData<byte[]> encodedData = STRING_ARRAY_CODEC.encodeInternally(plainData);

        System.out.println(Arrays.toString(encodedData.getData()));

        // decode
        String[] decodedArray = STRING_ARRAY_CODEC.decodeInternally(encodedData);

        System.out.println(Arrays.toString(plainArray));
        System.out.println(Arrays.toString(decodedArray));
        assertArrayEquals(plainArray, decodedArray);
    }
}