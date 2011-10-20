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

import de.cismet.tools.gui.jbands.SimpleTextSection;
import de.cismet.tools.gui.jbands.interfaces.Band;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandPrefixProvider;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class WKBand implements Band, BandPrefixProvider {

    //~ Instance fields --------------------------------------------------------

    JLabel label = new JLabel("WK");
    private final double max;
    private final double min;
    private final ArrayList<ArrayList> inputResulSet;
    private ArrayList<SimpleTextSection> members = new ArrayList<SimpleTextSection>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WKBand object.
     *
     * @param  min            DOCUMENT ME!
     * @param  max            DOCUMENT ME!
     * @param  inputResulSet  DOCUMENT ME!
     */
    public WKBand(final double min, final double max, final ArrayList<ArrayList> inputResulSet) {
        this.max = max;
        this.min = min;
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
            members.add(new SimpleTextSection(wk_k, von, bis, openLeft, openRight));
        }
        label.setHorizontalAlignment(JLabel.RIGHT);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public double getMax() {
        return max;
    }

    @Override
    public BandMember getMember(final int i) {
        return members.get(i);
    }

    @Override
    public double getMin() {
        return min;
    }

    @Override
    public int getNumberOfMembers() {
        return members.size();
    }

    @Override
    public JComponent getPrefixComponent() {
        return label;
    }
}
