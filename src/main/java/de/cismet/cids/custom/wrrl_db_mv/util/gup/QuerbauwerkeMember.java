/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import com.vividsolutions.jts.geom.Geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.features.DefaultStyledFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.PFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandMemberMouseListeningComponent;
import de.cismet.tools.gui.jbands.interfaces.Spot;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkeMember extends JLabel implements BandMember, Spot, BandMemberMouseListeningComponent {

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
            new Color(106, 90, 205),
            new Color(255, 140, 0),
            new Color(139, 26, 26),
            new Color(255, 215, 0),
            new Color(0, 229, 238),
            new Color(255, 222, 173),
            new Color(255, 182, 193),
            new Color(211, 211, 211),
            new Color(255, 0, 255),
            new Color(244, 164, 96),
            new Color(255, 0, 0)
        };
    Geometry geom = null;

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
        if (result.get(2) != null) {
            name = result.get(2).toString();
        } else {
            name = "unbekannt";
        }

        if (result.get(1) != null) {
            art = (Integer)(result.get(1)) - 1;
        }

        station = (Double)(result.get(3));
        setIcon(getIcon(getColor(), 10));
        setToolTipText(name);
        geom = (Geometry)(result.get(4));
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

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (e.getClickCount() == 2) {
            final MappingComponent mc = CismapBroker.getInstance().getMappingComponent();
            final XBoundingBox xbb = new XBoundingBox(geom.buffer(75));

            mc.gotoBoundingBoxWithHistory(xbb);
            final DefaultStyledFeature dsf = new DefaultStyledFeature();
            dsf.setGeometry(geom.buffer(10));
            dsf.setCanBeSelected(false);
            dsf.setLinePaint(Color.YELLOW);
            dsf.setLineWidth(6);
            final PFeature highlighter = new PFeature(dsf, mc);
            mc.getHighlightingLayer().addChild(highlighter);
            highlighter.animateToTransparency(0.1f, 2000);
            de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        Thread.currentThread().sleep(2500);
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            mc.getHighlightingLayer().removeChild(highlighter);
                        } catch (Exception e) {
                        }
                    }
                });
        }
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
    }
}
