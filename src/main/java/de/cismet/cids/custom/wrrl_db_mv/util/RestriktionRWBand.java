/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import de.cismet.cids.custom.wrrl_db_mv.util.gup.LineBand;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.LineBandMember;

import de.cismet.tools.gui.jbands.interfaces.BandSnappingPointProvider;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class RestriktionRWBand extends LineBand implements BandSnappingPointProvider {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public RestriktionRWBand(final String title, final String objectTableName) {
        this(1f, title, objectTableName);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public RestriktionRWBand(final float heightWeight, final String objectTableName) {
        super(heightWeight, objectTableName);
        setOnlyAcceptNewBeanWithValue(false);
        lineFieldName = "ausdehnung";
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public RestriktionRWBand(final float heightWeight, final String title, final String objectTableName) {
        super(heightWeight, title, objectTableName);
        setOnlyAcceptNewBeanWithValue(false);
        lineFieldName = "ausdehnung";
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected LineBandMember createBandMemberFromBean() {
        final RestriktionRWBandMember m = new RestriktionRWBandMember(this, readOnly);

        return m;
    }
}
