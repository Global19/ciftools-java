package org.rcsb.cif.schema.mm;

import org.rcsb.cif.model.*;
import org.rcsb.cif.schema.*;

import javax.annotation.Generated;

/**
 * Record details about each phasing set: (Note: the phasing
 * set is different from data set. for example: if there are
 * three data sets, the inflection point (IP), the peak (PK)
 * and the high remote (HR), the combination of the phasing
 * set will be IP_iso, PK_iso (the isomorphous repleacement
 * with HR as 'native'), IP_ano, PK_ano and HR_ano (the
 * anomalous difference with itself). Therefore, there are
 * five set used for phasing.
 */
@Generated("org.rcsb.cif.schema.generator.SchemaGenerator")
public class PdbxPhasingMADSet extends DelegatingCategory {
    public PdbxPhasingMADSet(Category delegate) {
        super(delegate);
    }

    @Override
    protected Column createDelegate(String columnName, Column column) {
        switch (columnName) {
            case "id":
                return getId();
            case "d_res_low":
                return getDResLow();
            case "d_res_high":
                return getDResHigh();
            case "number_of_sites":
                return getNumberOfSites();
            case "reflns_acentric":
                return getReflnsAcentric();
            case "reflns_centric":
                return getReflnsCentric();
            case "reflns":
                return getReflns();
            case "fom_acentric":
                return getFomAcentric();
            case "fom_centric":
                return getFomCentric();
            case "fom":
                return getFom();
            case "R_cullis_centric":
                return getRCullisCentric();
            case "R_cullis_acentric":
                return getRCullisAcentric();
            case "R_cullis":
                return getRCullis();
            case "R_kraut_centric":
                return getRKrautCentric();
            case "R_kraut_acentric":
                return getRKrautAcentric();
            case "R_kraut":
                return getRKraut();
            case "loc_centric":
                return getLocCentric();
            case "loc_acentric":
                return getLocAcentric();
            case "loc":
                return getLoc();
            case "power_centric":
                return getPowerCentric();
            case "power_acentric":
                return getPowerAcentric();
            case "power":
                return getPower();
            default:
                return new DelegatingColumn(column);
        }
    }

    /**
     * _pdbx_phasing_MAD_set.id records phase set name
     * for MAD phasing.
     * @return StrColumn
     */
    public StrColumn getId() {
        return delegate.getColumn("id", DelegatingStrColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.d_res_low records the lowerest
     * resolution for phasing set.
     * @return FloatColumn
     */
    public FloatColumn getDResLow() {
        return delegate.getColumn("d_res_low", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.d_res_high records the highest resolution
     * for the phasing set.
     * @return FloatColumn
     */
    public FloatColumn getDResHigh() {
        return delegate.getColumn("d_res_high", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.number_of_sites records the number of site
     * refined for the phasing set.
     * @return IntColumn
     */
    public IntColumn getNumberOfSites() {
        return delegate.getColumn("number_of_sites", DelegatingIntColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.reflns_acentric records the number of
     * acentric reflections for MAD phasing.
     * @return IntColumn
     */
    public IntColumn getReflnsAcentric() {
        return delegate.getColumn("reflns_acentric", DelegatingIntColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.reflns_centric records the number of
     * centric reflections for MAD phasing.
     * @return IntColumn
     */
    public IntColumn getReflnsCentric() {
        return delegate.getColumn("reflns_centric", DelegatingIntColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.reflns records the number of
     * reflections used for MAD phasing.
     * @return IntColumn
     */
    public IntColumn getReflns() {
        return delegate.getColumn("reflns", DelegatingIntColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.fom_acentric  records the figure of merit
     * using acentric data for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getFomAcentric() {
        return delegate.getColumn("fom_acentric", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.fom_centric  records the figure of merit
     * using centric data for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getFomCentric() {
        return delegate.getColumn("fom_centric", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.fom records the figure of merit
     * for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getFom() {
        return delegate.getColumn("fom", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.R_cullis_centric records R_cullis
     * using centric data for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getRCullisCentric() {
        return delegate.getColumn("R_cullis_centric", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.R_cullis_acentric records R_cullis
     * using acentric data for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getRCullisAcentric() {
        return delegate.getColumn("R_cullis_acentric", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.R_cullis records R_cullis
     * for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getRCullis() {
        return delegate.getColumn("R_cullis", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.R_kraut_centric records r_kraut
     * using centric data for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getRKrautCentric() {
        return delegate.getColumn("R_kraut_centric", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.r_kraut_acentric records r_kraut
     * using acentric data for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getRKrautAcentric() {
        return delegate.getColumn("R_kraut_acentric", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.R_kraut records R_kraut
     * for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getRKraut() {
        return delegate.getColumn("R_kraut", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.loc_centric records lack of closure
     * using centric data for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getLocCentric() {
        return delegate.getColumn("loc_centric", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.loc_acentric records lack of closure
     * using acentric data for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getLocAcentric() {
        return delegate.getColumn("loc_acentric", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.loc records lack of closure
     * for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getLoc() {
        return delegate.getColumn("loc", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.power_centric records phasing powe
     * using centric data for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getPowerCentric() {
        return delegate.getColumn("power_centric", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.power_acentric records phasing powe
     * using acentric data for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getPowerAcentric() {
        return delegate.getColumn("power_acentric", DelegatingFloatColumn::new);
    }

    /**
     * _pdbx_phasing_MAD_set.power records phasing power
     * for MAD phasing.
     * @return FloatColumn
     */
    public FloatColumn getPower() {
        return delegate.getColumn("power", DelegatingFloatColumn::new);
    }

}