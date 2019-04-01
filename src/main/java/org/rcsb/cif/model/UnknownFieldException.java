package org.rcsb.cif.model;

import org.rcsb.cif.parser.ParsingException;

public class UnknownFieldException extends ParsingException {
    public UnknownFieldException(String fieldName) {
        super("Cannot find field with name: " + fieldName);
    }
}