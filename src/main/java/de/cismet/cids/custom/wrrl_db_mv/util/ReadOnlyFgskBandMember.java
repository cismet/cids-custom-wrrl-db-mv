/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.log4j.Logger;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.SimSimulationsabschnittEditor;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.Calc;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.AbschnittsinfoMember;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.features.DefaultStyledFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.PFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.gui.jbands.JBandCursorManager;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandMemberMouseListeningComponent;
import de.cismet.tools.gui.jbands.interfaces.BandMemberSelectable;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ReadOnlyFgskBandMember extends AbschnittsinfoMember implements BandMemberSelectable,
    BandMemberMouseListeningComponent {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(ReadOnlyFgskBandMember.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean selected;
    private Painter unselectedBackgroundPainter;
    private Painter selectedBackgroundPainter;
    private String lineFieldName = "linie";

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     */
    public ReadOnlyFgskBandMember() {
        setMinimumSize(new Dimension(1, 7));
        setPreferredSize(getMinimumSize());
        setMemberBorder(true);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
        this.cidsBean = cidsBean;

        determineBackgroundColour();

        setBackgroundPainter(unselectedBackgroundPainter);
    }

    /**
     * DOCUMENT ME!
     */
    protected void determineBackgroundColour() {
        Integer cl = null;

        if (cidsBean != null) {
            final Double p = (Double)cidsBean.getProperty("punktzahl_gesamt");
            final CidsBean exception = (CidsBean)cidsBean.getProperty(Calc.PROP_EXCEPTION);

            if ((exception != null) && !Integer.valueOf(0).equals(exception.getProperty(Calc.PROP_VALUE))) {
                cl = 5;
            } else if ((p != null) && (p > 0.0)) {
                if (p != null) {
                    cl = SimSimulationsabschnittEditor.convertPointsToClass(p);
                }
            }
        }
        unselectedBackgroundPainter = getBackgroundPainterForClass(cl);

        selectedBackgroundPainter = new CompoundPainter(
                unselectedBackgroundPainter,
                new RectanglePainter(
                    3,
                    3,
                    3,
                    3,
                    3,
                    3,
                    true,
                    new Color(100, 100, 100, 100),
                    2f,
                    new Color(50, 50, 50, 100)));
        setBackgroundPainter(unselectedBackgroundPainter);
    }

    /**
     * DOCUMENT ME!
     */
    private void setDefaultBackground() {
        setBackgroundPainter(new MattePainter(new Color(229, 0, 0)));
        unselectedBackgroundPainter = getBackgroundPainter();
        selectedBackgroundPainter = new CompoundPainter(
                unselectedBackgroundPainter,
                new RectanglePainter(
                    3,
                    3,
                    3,
                    3,
                    3,
                    3,
                    true,
                    new Color(100, 100, 100, 100),
                    2f,
                    new Color(50, 50, 50, 100)));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cl  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private MattePainter getBackgroundPainterForClass(final Integer cl) {
        switch (cl) {
            case 1: {
                return (new MattePainter(new Color(10, 12, 149)));
            }
            case 2: {
                return (new MattePainter(new Color(11, 142, 12)));
            }
            case 3: {
                return (new MattePainter(new Color(253, 252, 4)));
            }
            case 4: {
                return (new MattePainter(new Color(214, 162, 79)));
            }
            case 5: {
                return (new MattePainter(new Color(165, 40, 34)));
            }
            default: {
                return (new MattePainter(new Color(192, 192, 192)));
            }
        }
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(final boolean selection) {
        this.selected = selection;

        if (selected) {
            setBackgroundPainter(selectedBackgroundPainter);
        } else {
            setBackgroundPainter(unselectedBackgroundPainter);
        }
    }

    @Override
    public boolean isSelectable() {
        return true;
    }

    @Override
    public BandMember getBandMember() {
        return this;
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if ((e.getClickCount() == 2) && (cidsBean != null)) {
            final Geometry g = (Geometry)(cidsBean.getProperty(lineFieldName + ".geom.geo_field"));
            final MappingComponent mc = CismapBroker.getInstance().getMappingComponent();
            final XBoundingBox xbb = new XBoundingBox(g);

            mc.gotoBoundingBoxWithHistory(new XBoundingBox(
                    g.getEnvelope().buffer((xbb.getWidth() + xbb.getHeight()) / 2 * 0.1)));
            final DefaultStyledFeature dsf = new DefaultStyledFeature();
            dsf.setGeometry(g);
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
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        if (JBandCursorManager.getInstance().isLocked()) {
            JBandCursorManager.getInstance().setCursor(this);
        }
        setAlpha(1f);
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        setAlpha(0.8f);
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        if (!JBandCursorManager.getInstance().isLocked()) {
            JBandCursorManager.getInstance().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            JBandCursorManager.getInstance().setCursor(this);
        } else {
            JBandCursorManager.getInstance().setCursor(this);
        }
    }
}
