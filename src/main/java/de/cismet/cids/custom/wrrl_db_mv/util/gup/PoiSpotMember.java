/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import org.jdesktop.swingx.JXPanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.Spot;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class PoiSpotMember extends JLabel implements BandMember, Spot {

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
    Double station;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new QuerbauwerkeMember object.
     */
    public PoiSpotMember() {
        super();
    }

    /**
     * Creates a new QuerbauwerkeMember object.
     *
     * @param  result  DOCUMENT ME!
     */
    public PoiSpotMember(final ArrayList result) {
        station = (Double)result.get(5);
        art_id = (Integer)result.get(3);
        name = String.valueOf(result.get(1));
        art = String.valueOf(result.get(4));
        setToolTipText(art + "," + name);
        setIcon(getIcon(getColor(), 10));
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JComponent getBandMemberComponent() {
        return this;
    }

    @Override
    public double getMax() {
        return station;
    }

    @Override
    public double getMin() {
        return station;
    }

    @Override
    public double getPosition() {
        return station;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   c     DOCUMENT ME!
     * @param   size  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Icon getIcon(final Color c, final int size) {
        final BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics = (Graphics2D)bi.getGraphics();
        graphics.setColor(c);
        graphics.fillOval(0, 0, size, size);
        return new ImageIcon(bi);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected Color getColor() {
        return colors[art_id - 1];
    }
}
