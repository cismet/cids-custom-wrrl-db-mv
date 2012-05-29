/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import de.cismet.cids.dynamics.CidsBean;

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
public class UnterhaltungserfordernisBandMember extends AbschnittsinfoMember implements BandMemberSelectable,
    BandMemberMouseListeningComponent {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean selected;
    private Painter unselectedBackgroundPainter;
    private Painter selectedBackgroundPainter;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     */
    public UnterhaltungserfordernisBandMember() {
        setMinimumSize(new Dimension(1, 7));
        setPreferredSize(getMinimumSize());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
        this.cidsBean = cidsBean;
        setToolTipText(String.valueOf(cidsBean.getProperty("name_bezeichnung.name")));
        determineBackgroundColour();
        setBackgroundPainter(unselectedBackgroundPainter);
    }

    /**
     * DOCUMENT ME!
     */
    protected void determineBackgroundColour() {
        if (cidsBean.getProperty("name_bezeichnung") == null) {
            return;
        }
        final int art = (Integer)cidsBean.getProperty("name_bezeichnung.id");

        switch (art) {
            case 1: {
                // hydraulischen Querschnitt des Gewässerprofils erhalten
                unselectedBackgroundPainter = (new MattePainter(new Color(241, 220, 219)));
                break;
            }
            case 2: {
                // Wasserspiegellage erhalten (Entwässerung anliegender Flächen)
                unselectedBackgroundPainter = (new MattePainter(new Color(216, 216, 216)));
                break;
            }
            case 3: {
                // Anlage im/am Gewässer (Stau/Wehr/Durchlass/Sandfang) freihalten
                unselectedBackgroundPainter = (new MattePainter(new Color(209, 252, 207)));
                break;
            }
            case 4: {
                // Sohl-, Ufer- oder Deichsicherung
                unselectedBackgroundPainter = (new MattePainter(new Color(255, 100, 0)));

                break;
            }
            case 5: {
                // Sedimentationsabschnitt
                unselectedBackgroundPainter = (new MattePainter(new Color(197, 103, 13)));
                break;
            }
            case 6: {
                // Krautaufwuchs Sohle > 80%
                unselectedBackgroundPainter = (new MattePainter(new Color(0, 255, 0)));
                break;
            }
            case 7: {
                // Verkehrssicherung für Gehölze
                unselectedBackgroundPainter = (new MattePainter(new Color(254, 254, 0)));
                break;
            }
            case 8: {
                // andere Erfordernisse - bitte erläutern
                unselectedBackgroundPainter = (new MattePainter(new Color(254, 254, 0)));
                break;
            }
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
