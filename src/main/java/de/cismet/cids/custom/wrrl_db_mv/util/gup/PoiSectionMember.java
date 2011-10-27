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
public class PoiSectionMember extends SimpleSection {

    //~ Instance fields --------------------------------------------------------

    Color[] colors = {
            new Color(0x4D4B6F), // Zuwegung
            new Color(0x39A528), // Krautentnahmestelle
            new Color(0x88A6C2), // Vernaessungsstelle
            new Color(0x5DDCFE), // Pegel
            new Color(0x5DDCFE), //
            new Color(0x9E0000), // Mauer
            new Color(0xE70B1F)  // schlechte Standsicherheit
        };
    String name;
    String art;
    JXPanel component = new JXPanel();
    int art_id;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new NaturschutzBandMember object.
     *
     * @param  queryResult  DOCUMENT ME!
     */
    public PoiSectionMember(final ArrayList queryResult) {
        super((Double)queryResult.get(5), (Double)queryResult.get(6));

        art_id = (Integer)queryResult.get(3);
        name = String.valueOf(queryResult.get(1));
        art = String.valueOf(queryResult.get(4));

        component.setBackgroundPainter(new MattePainter(colors[art_id - 1]));
        component.setToolTipText(art + "," + name);
        component.setBorder(new LineBorder(new Color(0x252D30), 1));
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JComponent getBandMemberComponent() {
        return component;
    }
}
