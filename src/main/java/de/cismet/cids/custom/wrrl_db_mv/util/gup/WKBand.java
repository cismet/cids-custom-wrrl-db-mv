/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.util.ArrayList;

import de.cismet.tools.gui.jbands.MinimumHeightBand;
import de.cismet.tools.gui.jbands.SimpleTextSection;
import de.cismet.tools.gui.jbands.interfaces.BandSnappingPointProvider;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class WKBand extends MinimumHeightBand implements BandSnappingPointProvider {

    //~ Instance fields --------------------------------------------------------

    private double max;
    private double min;
    private ArrayList<ArrayList> inputResulSet;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WKBand object.
     *
     * @param  min  DOCUMENT ME!
     * @param  max  DOCUMENT ME!
     */
    public WKBand(final double min, final double max) {
        this(min, max, (ArrayList<ArrayList>)null);
    }

    /**
     * Creates a new WKBand object.
     *
     * @param  min            DOCUMENT ME!
     * @param  max            DOCUMENT ME!
     * @param  inputResulSet  DOCUMENT ME!
     */
    public WKBand(final double min, final double max, final ArrayList<ArrayList> inputResulSet) {
        this(min, max, inputResulSet, "WK");
    }
    /**
     * Creates a new WKBand object.
     *
     * @param  min    DOCUMENT ME!
     * @param  max    DOCUMENT ME!
     * @param  title  heightWeight DOCUMENT ME!
     */
    public WKBand(final double min,
            final double max,
            final String title) {
        this(min, max, null, title);
    }
    /**
     * Creates a new WKBand object.
     *
     * @param  min            DOCUMENT ME!
     * @param  max            DOCUMENT ME!
     * @param  inputResulSet  DOCUMENT ME!
     * @param  title          heightWeight DOCUMENT ME!
     */
    public WKBand(final double min,
            final double max,
            final ArrayList<ArrayList> inputResulSet,
            final String title) {
        super(title);
        this.max = max;
        this.min = min;

        if (inputResulSet != null) {
            setWK(inputResulSet);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  inputResulSet  DOCUMENT ME!
     */
    public void setWK(final ArrayList<ArrayList> inputResulSet) {
        removeAllMember();
        this.inputResulSet = inputResulSet;
        for (final ArrayList zeile : inputResulSet) {
            final String wk_k = String.valueOf(zeile.get(0));
            double von = (Double)zeile.get(1);
            double bis = (Double)zeile.get(2);
            boolean openLeft = false;
            boolean openRight = false;
            if (von < min) {
                von = min;
                openLeft = true;
            }

            if (bis > max) {
                bis = max;
                openRight = true;
            }
            addMember(new SimpleTextSection(wk_k, von, bis, openLeft, openRight));
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

        if (inputResulSet != null) {
            setWK(inputResulSet);
        }
    }
}
