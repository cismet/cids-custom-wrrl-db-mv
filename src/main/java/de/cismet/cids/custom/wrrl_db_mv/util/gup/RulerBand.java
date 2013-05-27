/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import de.cismet.tools.gui.jbands.MinimumHeightBand;
import de.cismet.tools.gui.jbands.interfaces.BandSnappingPointProvider;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class RulerBand extends MinimumHeightBand implements BandSnappingPointProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            RulerBand.class);

    //~ Instance fields --------------------------------------------------------

    protected double max;
    protected double min;
    protected int distance = 100;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WKBand object.
     *
     * @param  min  DOCUMENT ME!
     * @param  max  DOCUMENT ME!
     */
    public RulerBand(final double min, final double max) {
        this.min = min;
        this.max = max;
        setMember();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    protected void setMember() {
        removeAllMember();
        int no = 0;

        distance = calcDistance((int)Math.abs(max - min));

        for (double i = min; i < max; i += getDistance()) {
            double end = i + getDistance();

            ++no;
            if (end > max) {
                end = max;
            }
            addMember(new RulerSection("", i, end, ((no % 5) == 1)));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   totalDistance  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected int calcDistance(final int totalDistance) {
        int distance = getNextPossibleDistance(0);

        while ((totalDistance / distance) > 30) {
            distance = getNextPossibleDistance(distance);
        }

        return distance;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   lastDistance  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected int getNextPossibleDistance(final int lastDistance) {
        if (lastDistance == 0) {
            return 100;
        } else if ((lastDistance / Math.pow(10, Math.floor(Math.log10(lastDistance)))) == 5) {
            return lastDistance * 2;
        } else {
            return lastDistance * 5;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  min  DOCUMENT ME!
     * @param  max  DOCUMENT ME!
     */
    public void setMinMax(final double min, final double max) {
        this.min = min;
        this.max = max;
        setMember();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the distance
     */
    public int getDistance() {
        return distance;
    }
}
