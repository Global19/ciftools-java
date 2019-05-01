package org.rcsb.cif.model.generated;

import org.rcsb.cif.model.*;

import javax.annotation.Generated;
import java.util.Map;

/**
 * Data items in the DIFFRN_RADIATION_WAVELENGTH category
 * describe the wavelength of the radiation used to measure the
 * diffraction intensities. Items may be looped to identify
 * and assign weights to distinct components of a
 * polychromatic beam.
 */
@Generated("org.rcsb.cif.generator.SchemaGenerator")
public class DiffrnRadiationWavelength extends BaseCategory {
    public DiffrnRadiationWavelength(String name, Map<String, Column> columns) {
        super(name, columns);
    }

    public DiffrnRadiationWavelength(String name, int rowCount, Object[] encodedColumns) {
        super(name, rowCount, encodedColumns);
    }

    public DiffrnRadiationWavelength(String name) {
        super(name);
    }

    /**
     * The code identifying each value of
     * _diffrn_radiation_wavelength.wavelength.
     * Items in the DIFFRN_RADIATION_WAVELENGTH category are looped
     * when multiple wavelengths are used.
     * 
     * This code is used to link with the DIFFRN_REFLN category.
     * The _diffrn_refln.wavelength_id codes must match one of
     * the codes defined in this category.
     * @return SingleRowStrColumn
     */
    public SingleRowStrColumn getId() {
        return (SingleRowStrColumn) (isText ? textFields.computeIfAbsent("id", SingleRowStrColumn::new) :
                getBinaryColumn("id"));
    }

    /**
     * The radiation wavelength in angstroms.
     * @return SingleRowFloatColumn
     */
    public SingleRowFloatColumn getWavelength() {
        return (SingleRowFloatColumn) (isText ? textFields.computeIfAbsent("wavelength", SingleRowFloatColumn::new) :
                getBinaryColumn("wavelength"));
    }

    /**
     * The relative weight of a wavelength identified by the code
     * _diffrn_radiation_wavelength.id in the list of wavelengths.
     * @return SingleRowFloatColumn
     */
    public SingleRowFloatColumn getWt() {
        return (SingleRowFloatColumn) (isText ? textFields.computeIfAbsent("wt", SingleRowFloatColumn::new) :
                getBinaryColumn("wt"));
    }
}