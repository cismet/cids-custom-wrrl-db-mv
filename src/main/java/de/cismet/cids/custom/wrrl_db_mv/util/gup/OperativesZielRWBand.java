/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.tools.gui.jbands.interfaces.BandSnappingPointProvider;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class OperativesZielRWBand extends LineBand implements BandSnappingPointProvider {

    //~ Instance fields --------------------------------------------------------

    private int type;
    private PflegezieleValidator validator;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public OperativesZielRWBand(final String title, final String objectTableName) {
        this(1f, title, objectTableName);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public OperativesZielRWBand(final float heightWeight, final String objectTableName) {
        super(heightWeight, objectTableName);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public OperativesZielRWBand(final float heightWeight, final String title, final String objectTableName) {
        super(heightWeight, title, objectTableName);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected LineBandMember createBandMemberFromBean() {
        final OperativesZielRWBandMember m = new OperativesZielRWBandMember(this, readOnly, validator);

        return m;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the type
     */
    public int getType() {
        return type;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  type  the type to set
     */
    public void setType(final int type) {
        this.type = type;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the validator
     */
    public PflegezieleValidator getValidator() {
        return validator;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  validator  the validator to set
     */
    public void setValidator(final PflegezieleValidator validator) {
        this.validator = validator;

        for (int i = 0; i < getNumberOfMembers(); ++i) {
            final OperativesZielRWBandMember bm = (OperativesZielRWBandMember)getMember(i);

            bm.setValidator(validator);
        }
    }
}
