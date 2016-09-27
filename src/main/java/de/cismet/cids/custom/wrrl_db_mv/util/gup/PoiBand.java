/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.util.ArrayList;

import de.cismet.tools.gui.jbands.DefaultBand;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class PoiBand extends DefaultBand {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AnwohnerBand object.
     */
    public PoiBand() {
        this("besondere Punkte");
    }

    /**
     * Creates a new AnwohnerBand object.
     *
     * @param  title  DOCUMENT ME!
     */
    public PoiBand(final String title) {
        this(1f, title);
    }

    /**
     * Creates a new AnwohnerBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     */
    public PoiBand(final float heightWeight) {
        this(heightWeight, "");
    }

    /**
     * Creates a new AnwohnerBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     * @param  title         DOCUMENT ME!
     */
    public PoiBand(final float heightWeight, final String title) {
        super(heightWeight, title);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  inputResulSet  DOCUMENT ME!
     */
    public void addPoisFromQueryResult(final ArrayList<ArrayList> inputResulSet) {
        for (final ArrayList zeile : inputResulSet) {
            if (zeile.get(5).equals(zeile.get(6))) {
                final PoiSpotMember spot = new PoiSpotMember(zeile);
                addMember(spot);
            } else {
                final PoiSectionMember section = new PoiSectionMember(zeile);
                addMember(section);
            }
        }
    }
}
