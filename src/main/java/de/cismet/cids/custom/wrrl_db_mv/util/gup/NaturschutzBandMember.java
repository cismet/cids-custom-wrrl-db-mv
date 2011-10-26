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

import de.cismet.tools.gui.jbands.SimpleSection;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class NaturschutzBandMember extends SimpleSection {

    //~ Instance fields --------------------------------------------------------

    String typ;
    JXPanel component = new JXPanel();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new NaturschutzBandMember object.
     *
     * @param  queryResult  DOCUMENT ME!
     */
    public NaturschutzBandMember(final ArrayList queryResult) {
        super((Double)queryResult.get(1), (Double)queryResult.get(2));
        typ = queryResult.get(0).toString();
        component.setBackgroundPainter(getBackgroundPainterforTyp(typ));
        component.setToolTipText(typ);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JComponent getBandMemberComponent() {
        return component;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   typ  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    private Painter getBackgroundPainterforTyp(final String typ) {
        assert (typ != null);
        if (typ.equalsIgnoreCase("NSG")) {
            return new MattePainter(new Color(0x58AD00));
        } else if (typ.equalsIgnoreCase("Naturpark")) {
            return new MattePainter(new Color(0x9CDB48));
        } else if (typ.equalsIgnoreCase("FFH")) {
            return new MattePainter(new Color(0xC2F364));
        } else if (typ.equalsIgnoreCase("LSG")) {
            return new MattePainter(new Color(0xE0FF9C));
        } else if (typ.equalsIgnoreCase("P20")) {
            return new MattePainter(new Color(0xEFF3DD));
        } else if (typ.equalsIgnoreCase("Eu Vogelschutzgebiet")) {
            return new MattePainter(new Color(0xC1995B));
        }
        throw new IllegalArgumentException(
            "Typ ist nicht in: NSG,Nationalpark,Naturpark,FFH,LSG,P20,Eu Vogelschutzgebiet");
    }
}
