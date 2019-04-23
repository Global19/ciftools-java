package org.rcsb.cif.model.structconf;

import org.rcsb.cif.model.*;

import javax.annotation.Generated;

@Generated("org.rcsb.cif.schema.Schema")
public class BegAuthAsymId extends StrColumn {
    public BegAuthAsymId(String name, int rowCount, String data, int[] startToken, int[] endToken) {
        super(name, rowCount, data, startToken, endToken);
    }

    public BegAuthAsymId(String name, int rowCount, Object data, int[] mask) {
        super(name, rowCount, data, mask);
    }

    public BegAuthAsymId(String name) {
        super(name);
    }
}