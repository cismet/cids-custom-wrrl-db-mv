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
public class POIBand extends SimpleBand implements Band, BandPrefixProvider {

    //~ Instance fields --------------------------------------------------------

    JLabel label = new JLabel("Querbauwerke");

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WKBand object.
     */
    public POIBand() {
        label.setHorizontalAlignment(JLabel.RIGHT);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  inputResulSet  DOCUMENT ME!
     */
    public void addQuerbauwerkeFromQueryResult(final ArrayList<ArrayList> inputResulSet) {
        for (final ArrayList zeile : inputResulSet) {
            final QuerbauwerkeMember qbm = new QuerbauwerkeMember(zeile);
            members.add(qbm);
        }
    }

    @Override
    public JComponent getPrefixComponent() {
        return label;
    }
}
