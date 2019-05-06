package org.rcsb.cif.model.generated;

import org.rcsb.cif.model.*;

import javax.annotation.Generated;
import java.util.Map;

/**
 * Data items in the DIFFRN_SOURCE category record details of
 * the source of radiation used in the diffraction experiment.
 */
@Generated("org.rcsb.cif.generator.SchemaGenerator")
public class DiffrnSource extends BaseCategory {
    public DiffrnSource(String name, Map<String, Column> columns) {
        super(name, columns);
    }

    public DiffrnSource(String name, int rowCount, Object[] encodedColumns) {
        super(name, rowCount, encodedColumns);
    }

    public DiffrnSource(String name) {
        super(name);
    }

    /**
     * The current in milliamperes at which the radiation source
     * was operated.
     * @return FloatColumn
     */
    public FloatColumn getCurrent() {
        return (FloatColumn) (isText ? textFields.computeIfAbsent("current", FloatColumn::new) :
                getBinaryColumn("current"));
    }

    /**
     * A description of special aspects of the radiation source used.
     * @return StrColumn
     */
    public StrColumn getDetails() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("details", StrColumn::new) :
                getBinaryColumn("details"));
    }

    /**
     * This data item is a pointer to _diffrn.id in the DIFFRN
     * category.
     * @return StrColumn
     */
    public StrColumn getDiffrnId() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("diffrn_id", StrColumn::new) :
                getBinaryColumn("diffrn_id"));
    }

    /**
     * The power in kilowatts at which the radiation source
     * was operated.
     * @return FloatColumn
     */
    public FloatColumn getPower() {
        return (FloatColumn) (isText ? textFields.computeIfAbsent("power", FloatColumn::new) :
                getBinaryColumn("power"));
    }

    /**
     * The dimensions of the source as viewed from the sample.
     * @return StrColumn
     */
    public StrColumn getSize() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("size", StrColumn::new) :
                getBinaryColumn("size"));
    }

    /**
     * The general class of the radiation source.
     * @return StrColumn
     */
    public StrColumn getSource() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("source", StrColumn::new) :
                getBinaryColumn("source"));
    }

    /**
     * The chemical element symbol for the X-ray target
     * (usually the anode) used to generate X-rays.
     * This can also be used for spallation sources.
     * @return StrColumn
     */
    public StrColumn getTarget() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("target", StrColumn::new) :
                getBinaryColumn("target"));
    }

    /**
     * The make, model or name of the source of radiation.
     * @return StrColumn
     */
    public StrColumn getType() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("type", StrColumn::new) :
                getBinaryColumn("type"));
    }

    /**
     * The voltage in kilovolts at which the radiation source was
     * operated.
     * @return FloatColumn
     */
    public FloatColumn getVoltage() {
        return (FloatColumn) (isText ? textFields.computeIfAbsent("voltage", FloatColumn::new) :
                getBinaryColumn("voltage"));
    }

    /**
     * The complement of the angle in degrees between the normal
     * to the surface of the X-ray tube target and the primary
     * X-ray beam for beams generated by traditional X-ray tubes.
     * @return FloatColumn
     */
    public FloatColumn getTake_offAngle() {
        return (FloatColumn) (isText ? textFields.computeIfAbsent("take-off_angle", FloatColumn::new) :
                getBinaryColumn("take-off_angle"));
    }

    /**
     * Comma separated list of wavelengths or wavelength range.
     * @return StrColumn
     */
    public StrColumn getPdbxWavelengthList() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("pdbx_wavelength_list", StrColumn::new) :
                getBinaryColumn("pdbx_wavelength_list"));
    }

    /**
     * Wavelength of radiation.
     * @return StrColumn
     */
    public StrColumn getPdbxWavelength() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("pdbx_wavelength", StrColumn::new) :
                getBinaryColumn("pdbx_wavelength"));
    }

    /**
     * Synchrotron beamline.
     * @return StrColumn
     */
    public StrColumn getPdbxSynchrotronBeamline() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("pdbx_synchrotron_beamline", StrColumn::new) :
                getBinaryColumn("pdbx_synchrotron_beamline"));
    }

    /**
     * Synchrotron site.
     * @return StrColumn
     */
    public StrColumn getPdbxSynchrotronSite() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("pdbx_synchrotron_site", StrColumn::new) :
                getBinaryColumn("pdbx_synchrotron_site"));
    }

    /**
     * Yes/No if synchrotron source was used or not.
     * @return StrColumn
     */
    public StrColumn getPdbxSynchrotronYN() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("pdbx_synchrotron_y_n", StrColumn::new) :
                getBinaryColumn("pdbx_synchrotron_y_n"));
    }

    /**
     * Beamline for synchrotron source.
     * @return StrColumn
     */
    public StrColumn getPdbxSourceSpecificBeamline() {
        return (StrColumn) (isText ? textFields.computeIfAbsent("pdbx_source_specific_beamline", StrColumn::new) :
                getBinaryColumn("pdbx_source_specific_beamline"));
    }
}
