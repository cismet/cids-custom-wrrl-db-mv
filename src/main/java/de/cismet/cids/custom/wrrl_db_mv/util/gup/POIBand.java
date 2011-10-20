/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;

import de.cismet.tools.gui.jbands.DefaultBand;
import de.cismet.tools.gui.jbands.SimpleBand;
import de.cismet.tools.gui.jbands.interfaces.Band;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandPrefixProvider;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class POIBand extends DefaultBand {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new POIBand object.
     */
    public POIBand() {
        this("Querbauwerke");
    }

    /**
     * Creates a new POIBand object.
     *
     * @param  title  DOCUMENT ME!
     */
    public POIBand(final String title) {
        this(1f, title);
    }

    /**
     * Creates a new POIBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     */
    public POIBand(final float heightWeight) {
        this(heightWeight, "");
    }

    /**
     * Creates a new POIBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     * @param  title         DOCUMENT ME!
     */
    public POIBand(final float heightWeight, final String title) {
        super(heightWeight, title);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  inputResulSet  DOCUMENT ME!
     */
    public void addQuerbauwerkeFromQueryResult(final ArrayList<ArrayList> inputResulSet) {
        for (final ArrayList zeile : inputResulSet) {
//            final QuerbauwerkeMember qbm = new QuerbauwerkeMember(zeile);
            final QuerbauwerkeMember qbm = new QuerbauwerkeSmallMember(zeile);
            addMember(qbm);
        }
    }
}
