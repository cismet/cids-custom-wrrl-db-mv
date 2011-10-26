/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;

import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

import de.cismet.tools.gui.jbands.SimpleSection;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class AnwohnerMember extends SimpleSection {

    //~ Instance fields --------------------------------------------------------

    String name;
    String tel;
    JXPanel component = new JXPanel();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new NaturschutzBandMember object.
     *
     * @param  queryResult  DOCUMENT ME!
     */
    public AnwohnerMember(final ArrayList queryResult) {
        super((Double)queryResult.get(5), (Double)queryResult.get(6));
        component.setBackgroundPainter(new MattePainter(new Color(0x22718F)));
        name = String.valueOf(queryResult.get(3));
        tel = String.valueOf(queryResult.get(4));
        component.setToolTipText(name + " (" + tel + ")");
        component.setBorder(new LineBorder(new Color(0x252D30), 1));
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JComponent getBandMemberComponent() {
        return component;
    }
}
