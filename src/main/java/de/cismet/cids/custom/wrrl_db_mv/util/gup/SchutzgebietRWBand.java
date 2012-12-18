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
public class SchutzgebietRWBand extends LineBand implements BandSnappingPointProvider {

    //~ Static fields/initializers ---------------------------------------------

    public static final int RIGHT = 1;
    public static final int LEFT = 2;
    public static final int SOHLE = 3;

    //~ Instance fields --------------------------------------------------------

    private int type;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public SchutzgebietRWBand(final String title, final String objectTableName) {
        this(1f, title, objectTableName);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public SchutzgebietRWBand(final float heightWeight, final String objectTableName) {
        super(heightWeight, objectTableName);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight     DOCUMENT ME!
     * @param  title            DOCUMENT ME!
     * @param  objectTableName  DOCUMENT ME!
     */
    public SchutzgebietRWBand(final float heightWeight, final String title, final String objectTableName) {
        super(heightWeight, title, objectTableName);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected LineBandMember createBandMemberFromBean() {
        final SchutzgebietRWBandMember m = new SchutzgebietRWBandMember(this, readOnly);

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
}
