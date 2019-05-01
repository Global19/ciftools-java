package org.rcsb.cif.model.generated;

import org.rcsb.cif.model.*;

import javax.annotation.Generated;
import java.util.Map;

/**
 * Data items in the AUDIT category record details about the
 * creation and subsequent updating of the data block.
 * 
 * Note that these items apply only to the creation and updating of
 * the data block, and should not be confused with the data items
 * in the JOURNAL category that record different stages in the
 * publication of the material in the data block.
 */
@Generated("org.rcsb.cif.generator.SchemaGenerator")
public class Audit extends BaseCategory {
    public Audit(String name, Map<String, Column> columns) {
        super(name, columns);
    }

    public Audit(String name, int rowCount, Object[] encodedColumns) {
        super(name, rowCount, encodedColumns);
    }

    public Audit(String name) {
        super(name);
    }

    /**
     * A date that the data block was created. The date format is
     * yyyy-mm-dd.
     * @return SingleRowStrColumn
     */
    public SingleRowStrColumn getCreationDate() {
        return (SingleRowStrColumn) (isText ? textFields.computeIfAbsent("creation_date", SingleRowStrColumn::new) :
                getBinaryColumn("creation_date"));
    }

    /**
     * A description of how data were entered into the data block.
     * @return SingleRowStrColumn
     */
    public SingleRowStrColumn getCreationMethod() {
        return (SingleRowStrColumn) (isText ? textFields.computeIfAbsent("creation_method", SingleRowStrColumn::new) :
                getBinaryColumn("creation_method"));
    }

    /**
     * The value of _audit.revision_id must uniquely identify a record
     * in the AUDIT list.
     * @return SingleRowStrColumn
     */
    public SingleRowStrColumn getRevisionId() {
        return (SingleRowStrColumn) (isText ? textFields.computeIfAbsent("revision_id", SingleRowStrColumn::new) :
                getBinaryColumn("revision_id"));
    }

    /**
     * A record of any changes to the data block. The update format is
     * a date (yyyy-mm-dd) followed by a description of the changes.
     * The latest update entry is added to the bottom of this record.
     * @return SingleRowStrColumn
     */
    public SingleRowStrColumn getUpdateRecord() {
        return (SingleRowStrColumn) (isText ? textFields.computeIfAbsent("update_record", SingleRowStrColumn::new) :
                getBinaryColumn("update_record"));
    }
}