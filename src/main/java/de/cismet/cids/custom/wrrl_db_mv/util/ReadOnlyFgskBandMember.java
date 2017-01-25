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
import org.jdesktop.swingx.painter.PinstripePainter;
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import java.util.Arrays;
import java.util.List;

import javax.swing.border.MatteBorder;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.SimSimulationsabschnittEditor;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.Calc;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.CalcCache;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.AbschnittsinfoMember;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.features.DefaultStyledFeature;
import de.cismet.cismap.commons.features.Feature;
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
    private final String lineFieldName = "linie";
    private List<CidsBean> massnahmen;
    private Color backgroundColor = null;
    private DefaultStyledFeature fgskFeature = null;
    private int bottomBorder = 1;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  massnahmen  DOCUMENT ME!
     * @param  complete    DOCUMENT ME!
     */
    public ReadOnlyFgskBandMember(final List<CidsBean> massnahmen, final Boolean complete) {
        setMinimumSize(new Dimension(1, 7));
        setPreferredSize(getMinimumSize());
        this.massnahmen = massnahmen;
        if ((complete == null) || complete) {
            bottomBorder = 1;
        } else {
            bottomBorder = 5;
        }
        setBorder(new MatteBorder(new Insets(1, 1, bottomBorder, 1), Color.BLACK));
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

        refresh(getMassnahmen());
    }

    /**
     * DOCUMENT ME!
     */
    protected void determineBackgroundColour() {
        Integer cl = null;

        if (cidsBean != null) {
            Double p = null;

            try {
                p = SimSimulationsabschnittEditor.calc(cidsBean, getMassnahmen(), false, null);
            } catch (Exception e) {
                LOG.warn("FGSK calculation error", e);
            }

            final CidsBean exception = (CidsBean)cidsBean.getProperty(Calc.PROP_EXCEPTION);

            if ((p == null) && (exception != null)
                        && Integer.valueOf(1).equals(exception.getProperty(Calc.PROP_VALUE))) {
                cl = 5;
            } else if ((p != null) && (p > 0.0)) {
                cl = CalcCache.getQualityClass(p);
            }
        }

        if ((getMassnahmen() == null) || (getMassnahmen().isEmpty())) {
            unselectedBackgroundPainter = getBackgroundPainterForClass(cl);
        } else {
            unselectedBackgroundPainter = new CompoundPainter(
                    getBackgroundPainterForClass(cl),
                    new PinstripePainter(new Color(255, 66, 66), 45, 2, 5));
        }

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
        if (cl != null) {
            switch (cl) {
                case 1: {
                    backgroundColor = new Color(0, 0, 255);
                    break;
                }
                case 2: {
                    backgroundColor = new Color(0, 153, 0);
                    break;
                }
                case 3: {
                    backgroundColor = new Color(255, 255, 0);
                    break;
                }
                case 4: {
                    backgroundColor = new Color(255, 153, 0);
                    break;
                }
                case 5: {
                    backgroundColor = new Color(255, 0, 0);
                    break;
                }
                default: {
                    backgroundColor = new Color(193, 193, 193);
                    break;
                }
            }
        } else {
            backgroundColor = new Color(193, 193, 193);
        }

        return (new MattePainter(backgroundColor));
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(final boolean selection) {
        this.selected = selection;

        if (selected) {
            fgskFeature = new DefaultStyledFeature();
            fgskFeature.setGeometry((Geometry)cidsBean.getProperty(lineFieldName + ".geom.geo_field"));
            fgskFeature.setLinePaint(backgroundColor);
            fgskFeature.setLineWidth(4);
            CismapBroker.getInstance().getMappingComponent().addFeaturesToMap(new Feature[] { fgskFeature });

            setBackgroundPainter(selectedBackgroundPainter);
            setBorder(new MatteBorder(new Insets(3, 1, bottomBorder, 1), Color.BLACK));
        } else {
            if (fgskFeature != null) {
                CismapBroker.getInstance()
                        .getMappingComponent()
                        .removeFeatures(Arrays.asList(new Feature[] { fgskFeature }));
                fgskFeature = null;
            }
            setBackgroundPainter(unselectedBackgroundPainter);
            setBorder(new MatteBorder(new Insets(1, 1, bottomBorder, 1), Color.BLACK));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  complete  DOCUMENT ME!
     */
    public void setComplete(final Boolean complete) {
        if ((complete == null) || complete) {
            bottomBorder = 1;
        } else {
            bottomBorder = 5;
        }
        setBorder(new MatteBorder(new Insets(1, 1, bottomBorder, 1), Color.BLACK));
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

    /**
     * DOCUMENT ME!
     */
    public void dispose() {
        if (fgskFeature != null) {
            CismapBroker.getInstance()
                    .getMappingComponent()
                    .removeFeatures(Arrays.asList(new Feature[] { fgskFeature }));
            fgskFeature = null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  massnahmen  DOCUMENT ME!
     */
    public void refresh(final List<CidsBean> massnahmen) {
        this.massnahmen = massnahmen;
        determineBackgroundColour();
        setBackgroundPainter(unselectedBackgroundPainter);
        setSelected(selected);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the massnahmen
     */
    public List<CidsBean> getMassnahmen() {
        return massnahmen;
    }
}
