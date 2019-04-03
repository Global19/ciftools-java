package org.rcsb.cif.binary;

import org.rcsb.cif.binary.codec.MessagePackCodec;
import org.rcsb.cif.binary.old.codec.Codec;
import org.rcsb.cif.model.BinaryCifCategory;
import org.rcsb.cif.model.CifBlock;
import org.rcsb.cif.model.CifCategory;
import org.rcsb.cif.model.CifFile;
import org.rcsb.cif.CifReader;
import org.rcsb.cif.ParsingException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.rcsb.cif.binary.codec.MessagePackCodec.MESSAGE_PACK_CODEC;

public class BinaryCifReader implements CifReader {
    private final MessagePackCodec messagePackCodec;

    public BinaryCifReader() {
        this.messagePackCodec = MESSAGE_PACK_CODEC;
    }

    @Override
    public CifFile parse(InputStream inputStream) throws ParsingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] byteArray = buffer.toByteArray();
        buffer.close();

        return parseBinary(byteArray);
    }

    @SuppressWarnings("unchecked")
    public CifFile parseBinary(byte[] data) throws ParsingException {
        if (data.length == 0) {
            throw new ParsingException("Cannot parse empty file.");
        }

        Map<String, Object> unpacked;
        try {
            unpacked = messagePackCodec.decode(data);
        } catch (ClassCastException e) {
            throw new ParsingException("File seems to be not in binary CIF. Encountered unexpected cast.", e);
        } catch (Exception e) {
            throw new ParsingException("Parsing failed.", e);
        }

        String versionString = (String) unpacked.get("version");
        if (!versionString.startsWith(Codec.MIN_VERSION)) {
            throw new ParsingException("Unsupported format version. Current " + versionString +
                    ", required " + Codec.MIN_VERSION + ".");
        }

        try {
            List<CifBlock> dataBlocks = Stream.of((Object[]) (unpacked.get("dataBlocks")))
                    .map(entry -> {
                        Map<String, Object> map = (Map<String, Object>) entry;
                        String header = (String) map.get("header");
                        Map<String, CifCategory> categories = new LinkedHashMap<>();

                        for (Object o : (Object[]) map.get("categories")) {
                            Map<String, Object> cat = (Map<String, Object>) o;
                            String name = (String) cat.get("name");
                            categories.put(name.substring(1), createCategory(cat));
                        }

                        return new CifBlock(categories, header, new ArrayList<>());
                    })
                    .collect(Collectors.toList());

            return new CifFile(dataBlocks);
        } catch (Exception e) {
            throw new ParsingException("Parsing failed.", e);
        }
    }

    private CifCategory createCategory(Map<String, Object> encodedCategory) {
        String name = (String) encodedCategory.get("name");
        int rowCount = (int) encodedCategory.get("rowCount");
        Object[] encodedFields = (Object[]) encodedCategory.get("columns");
        return new BinaryCifCategory(name, rowCount, encodedFields);
    }
}