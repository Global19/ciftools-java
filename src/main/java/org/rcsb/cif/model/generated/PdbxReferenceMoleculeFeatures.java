package org.rcsb.cif.model.generated;

import org.rcsb.cif.model.*;

import javax.annotation.Generated;
import java.util.Map;

/**
 * Additional features associated with the reference entity.
 */
@Generated("org.rcsb.cif.internal.generator.SchemaGenerator")
public class PdbxReferenceMoleculeFeatures extends BaseCategory {
    public PdbxReferenceMoleculeFeatures(String name, Map<String, Column> columns) {
        super(name, columns);
    }

    public PdbxReferenceMoleculeFeatures(String name, int rowCount, Object[] encodedColumns) {
        super(name, rowCount, encodedColumns);
    }

    public PdbxReferenceMoleculeFeatures(String name) {
        super(name);
    }

    /**
     * The value of _pdbx_reference_molecule_features.family_prd_id is a reference to
     * _pdbx_reference_molecule_list.family_prd_id in category PDBX_REFERENCE_MOLECULE_FAMILY_LIST.
     * @return StrColumn
     */
    public StrColumn getFamilyPrdId() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("family_prd_id", StrColumn::new) :
                getBinaryColumn("family_prd_id"));
    }

    /**
     * The value of _pdbx_reference_molecule_features.prd_id is a reference
     * _pdbx_reference_molecule.prd_id in the  PDBX_REFERENCE_MOLECULE category.
     * @return StrColumn
     */
    public StrColumn getPrdId() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("prd_id", StrColumn::new) :
                getBinaryColumn("prd_id"));
    }

    /**
     * The value of _pdbx_reference_molecule_features.ordinal distinguishes
     * each feature for this entity.
     * @return IntColumn
     */
    public IntColumn getOrdinal() {
        return (IntColumn) (isText ? textFields.computeIfAbsent("ordinal", IntColumn::new) :
                getBinaryColumn("ordinal"));
    }

    /**
     * The value of _pdbx_reference_molecule_features.source_ordinal provides
     * the priority order of features from a particular source or database.
     * @return IntColumn
     */
    public IntColumn getSourceOrdinal() {
        return (IntColumn) (isText ? textFields.computeIfAbsent("source_ordinal", IntColumn::new) :
                getBinaryColumn("source_ordinal"));
    }

    /**
     * The entity feature type.
     * @return StrColumn
     */
    public StrColumn getType() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("type", StrColumn::new) :
                getBinaryColumn("type"));
    }

    /**
     * The entity feature value.
     * @return StrColumn
     */
    public StrColumn getValue() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("value", StrColumn::new) :
                getBinaryColumn("value"));
    }

    /**
     * The information source for the component feature.
     * @return StrColumn
     */
    public StrColumn getSource() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("source", StrColumn::new) :
                getBinaryColumn("source"));
    }

    /**
     * For entities represented as single molecules, the identifier
     * corresponding to the chemical definition for the molecule.
     * @return StrColumn
     */
    public StrColumn getChemCompId() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("chem_comp_id", StrColumn::new) :
                getBinaryColumn("chem_comp_id"));
    }
}
