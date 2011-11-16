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
public class QuerbauwerksBand extends DefaultBand {

    //~ Instance fields --------------------------------------------------------

    private final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new QuerbauwerksBand object.
     */
    public QuerbauwerksBand() {
        this("Querbauwerke");
    }

    /**
     * Creates a new QuerbauwerksBand object.
     *
     * @param  title  DOCUMENT ME!
     */
    public QuerbauwerksBand(final String title) {
        this(1f, title);
    }

    /**
     * Creates a new QuerbauwerksBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     */
    public QuerbauwerksBand(final float heightWeight) {
        this(heightWeight, "");
    }

    /**
     * Creates a new QuerbauwerksBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     * @param  title         DOCUMENT ME!
     */
    public QuerbauwerksBand(final float heightWeight, final String title) {
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
            try {
                final QuerbauwerkeMember qbm = new QuerbauwerkeMember(zeile);
//            final QuerbauwerkeMember qbm = new QuerbauwerkeSmallMember(zeile);
                addMember(qbm);
            } catch (Exception e) {
                log.warn("Fehler beim Erzeugen eines Querbaus", e);
            }
        }
    }
}
