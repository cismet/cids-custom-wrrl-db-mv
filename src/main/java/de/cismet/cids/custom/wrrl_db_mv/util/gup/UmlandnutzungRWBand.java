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
public class UmlandnutzungRWBand extends LineBand implements BandSnappingPointProvider {

    //~ Static fields/initializers ---------------------------------------------

    public static final int RIGHT = 1;
    public static final int LEFT = 2;

    //~ Instance fields --------------------------------------------------------

    private int side = RIGHT;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public UmlandnutzungRWBand(final String title, final String objectTableName) {
        this(1f, title, objectTableName);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public UmlandnutzungRWBand(final float heightWeight, final String objectTableName) {
        super(heightWeight, objectTableName);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public UmlandnutzungRWBand(final float heightWeight, final String title, final String objectTableName) {
        super(heightWeight, title, objectTableName);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected LineBandMember createBandMemberFromBean() {
        final UmlandnutzungRWBandMember m = new UmlandnutzungRWBandMember(this, readOnly);

        return m;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  side  DOCUMENT ME!
     */
    public void setSide(final int side) {
        this.side = side;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getSide() {
        return side;
    }
}
