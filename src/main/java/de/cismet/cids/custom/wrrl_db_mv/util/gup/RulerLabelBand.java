/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class RulerLabelBand extends RulerBand {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            RulerLabelBand.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WKBand object.
     *
     * @param  min  DOCUMENT ME!
     * @param  max  DOCUMENT ME!
     */
    public RulerLabelBand(final double min, final double max) {
        super(min, max);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void setMember() {
        removeAllMember();
        distance = calcDistance((int)Math.abs(max - min));
        distance *= 5;

        for (double i = min + distance; i < max; i += distance) {
            final double puffer = 2 * (distance / 5);
            double end = i + puffer;
            if (end > max) {
                end = max;
            }
            addMember(new RulerLabelSection(String.valueOf((int)i), i - puffer, end));
        }
    }
}
