/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JComponent;

import de.cismet.tools.gui.jbands.SimpleSectionPanel;
import de.cismet.tools.gui.jbands.interfaces.Section;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkeSmallMember extends QuerbauwerkeMember implements Section {

    //~ Instance fields --------------------------------------------------------

    SimpleSectionPanel comp = new SimpleSectionPanel();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new QuerbauwerkeSmallMember object.
     */
    public QuerbauwerkeSmallMember() {
        super();
    }
    /**
     * Creates a new QuerbauwerkeSmallMember object.
     *
     * @param  result  DOCUMENT ME!
     */
    public QuerbauwerkeSmallMember(final ArrayList result) {
        super(result);
        comp.setBackground(getColor());
        comp.setToolTipText(name);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public double getFrom() {
        return super.getMin();
    }

    @Override
    public double getTo() {
        return super.getMax();
    }

    @Override
    public JComponent getBandMemberComponent() {
        return comp;
    }
}
