/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import de.cismet.tools.gui.jbands.DefaultBand;
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
public class POIBand extends DefaultBand {

    public POIBand(String title) {
        this(1f,title);
    }

    public POIBand(float heightWeight, String title) {
        super(heightWeight, title);
    }

    public POIBand(float heightWeight) {
        this(heightWeight,"");
    }

    public POIBand() {
        this("Querbauwerke");
    }


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
