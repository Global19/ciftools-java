package org.rcsb.cif.model.structconn;

import org.rcsb.cif.model.*;

import javax.annotation.Generated;

@Generated("org.rcsb.cif.schema.generator.SchemaGenerator")
public class PdbxPtnr2PDBInsCode extends StrColumn {
    public PdbxPtnr2PDBInsCode(String name, int rowCount, String data, int[] startToken, int[] endToken) {
        super(name, rowCount, data, startToken, endToken);
    }

    public PdbxPtnr2PDBInsCode(String name, int rowCount, Object data, int[] mask) {
        super(name, rowCount, data, mask);
    }

    public PdbxPtnr2PDBInsCode(String name) {
        super(name);
    }
}