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
public class EntwicklungszielRWBand extends LineBand implements BandSnappingPointProvider {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public EntwicklungszielRWBand(final String title, final String objectTableName) {
        this(1f, title, objectTableName);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public EntwicklungszielRWBand(final float heightWeight, final String objectTableName) {
        super(heightWeight, objectTableName);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public EntwicklungszielRWBand(final float heightWeight, final String title, final String objectTableName) {
        super(heightWeight, title, objectTableName);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected LineBandMember createBandMemberFromBean() {
        final EntwicklungszielRWBandMember m = new EntwicklungszielRWBandMember(this, readOnly);

        return m;
    }
}
