/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import de.cismet.tools.gui.jbands.interfaces.BandSnappingPointProvider;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class UmlandnutzerRWBand extends LineBand implements BandSnappingPointProvider {

    //~ Instance fields --------------------------------------------------------

    private int side;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public UmlandnutzerRWBand(final String title, final String objectTableName) {
        this(1f, title, objectTableName);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public UmlandnutzerRWBand(final float heightWeight, final String objectTableName) {
        super(heightWeight, objectTableName);
        setOnlyAcceptNewBeanWithValue(false);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public UmlandnutzerRWBand(final float heightWeight, final String title, final String objectTableName) {
        super(heightWeight, title, objectTableName);
        setOnlyAcceptNewBeanWithValue(false);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected LineBandMember createBandMemberFromBean() {
        final UmlandnutzerRWBandMember m = new UmlandnutzerRWBandMember(this, readOnly);

        return m;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the side
     */
    public int getSide() {
        return side;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  side  the side to set
     */
    public void setSide(final int side) {
        this.side = side;
    }
}
