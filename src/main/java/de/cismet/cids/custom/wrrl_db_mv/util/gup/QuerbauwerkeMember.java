/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import de.cismet.tools.gui.jbands.SimpleSpotPanel;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.Spot;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkeMember extends JLabel implements BandMember, Spot {

    //~ Instance fields --------------------------------------------------------

    protected String name = "";

// 1     Stau, Wehr      1
// 2     Durchlass       2
// 3     Sohlgleite, Sohlschwelle, Rampe 3
// 4     Schleuse        4
// 5     Talsperre/Speicher      5
// 6     Wasserkraftanlage       6
// 7     Schöpfwerke auf der Route       7
// 8     andere  8
// 9     Fischaufstiegsanlagen   9
// 10    Sandfang        10
// 11    Brücken 11

    Color[] colors = {
            new Color(226, 223, 154),
            new Color(235, 229, 77),
            new Color(117, 116, 73),
            new Color(75, 73, 11),
            new Color(207, 240, 158),
            new Color(168, 219, 168),
            new Color(121, 189, 154),
            new Color(59, 134, 134),
            new Color(11, 72, 107),
            new Color(84, 36, 55),
            new Color(217, 91, 67)
        };

    private double station;
    private int art = 8;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new QuerbauwerkeMember object.
     */
    public QuerbauwerkeMember() {
        super();
    }

    /**
     * Creates a new QuerbauwerkeMember object.
     *
     * @param  result  DOCUMENT ME!
     */
    public QuerbauwerkeMember(final ArrayList result) {
        name = result.get(2).toString();
        station = (Double)(result.get(3));
        art = (Integer)(result.get(1)) - 1;
        System.out.println("QB:" + station + " " + name);
        setIcon(getIcon(getColor(), 10));
        setToolTipText(name);
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
        return colors[art];
    }
}
