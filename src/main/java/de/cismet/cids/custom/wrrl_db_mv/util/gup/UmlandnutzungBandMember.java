/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import java.util.List;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

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
public class UmlandnutzungBandMember extends AbschnittsinfoMember implements BandMemberSelectable,
    BandMemberMouseListeningComponent {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass OBERGRUPPE_ART = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "GUP_UMLANDNUTZUNGSOBERGRUPPE");

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean selected;
    private Painter unselectedBackgroundPainter;
    private Painter selectedBackgroundPainter;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     */
    public UmlandnutzungBandMember() {
        setMinimumSize(new Dimension(1, 7));
        setPreferredSize(getMinimumSize());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
        this.cidsBean = cidsBean;
        setToolTipText(cidsBean.getProperty("art.name") + "");
        determineBackgroundColour();
        setBackgroundPainter(unselectedBackgroundPainter);
    }

    /**
     * DOCUMENT ME!
     */
    protected void determineBackgroundColour() {
        if (cidsBean.getProperty("art") == null) {
            return;
        }
        final CidsBean artBean = (CidsBean)cidsBean.getProperty("art");
        final int art = (Integer)cidsBean.getProperty("art.id");

        final String query = "select " + OBERGRUPPE_ART.getID() + "," + OBERGRUPPE_ART.getPrimaryKey() + " from "
                    + OBERGRUPPE_ART.getTableName(); // NOI18N
        final MetaObject[] metaObjects = MetaObjectCache.getInstance().getMetaObjectByQuery(query);
        int action = -1;

        for (final MetaObject tmp : metaObjects) {
            final List<CidsBean> beans = tmp.getBean().getBeanCollectionProperty("gruppen");
            if ((beans != null) && beans.contains(artBean)) {
                action = tmp.getId();
                break;
            }
        }

        switch (action) {
            case 1: {
                // Gebaeude und Freiflaechen
                unselectedBackgroundPainter = (new MattePainter(new Color(241, 220, 219)));
                break;
            }
            case 2: {
                // Betriebsflaechen
                unselectedBackgroundPainter = (new MattePainter(new Color(216, 216, 216)));
                break;
            }
            case 3: {
                // Erholungsflaechen
                unselectedBackgroundPainter = (new MattePainter(new Color(209, 252, 207)));
                break;
            }
            case 4: {
                // Verkehrsflaechen
                unselectedBackgroundPainter = (new MattePainter(new Color(255, 100, 0)));

                break;
            }
            case 5: {
                // Acker
                unselectedBackgroundPainter = (new MattePainter(new Color(197, 103, 13)));
                break;
            }
            case 6: {
                // Gruenland
                unselectedBackgroundPainter = (new MattePainter(new Color(0, 255, 0)));
                break;
            }
            case 7: {
                // Sonderkultur
                unselectedBackgroundPainter = (new MattePainter(new Color(254, 254, 0)));
                break;
            }
            case 8: {
                // LF Sonstige
                unselectedBackgroundPainter = (new MattePainter(new Color(254, 254, 0)));
                break;
            }
            case 9: {
                // waldflaechen
                unselectedBackgroundPainter = (new MattePainter(new Color(51, 151, 51)));
            }
            case 10: {
                // Wasserflaechen
                unselectedBackgroundPainter = (new MattePainter(new Color(79, 131, 189)));
            }
            case 11: {
                // Sonstige Flaechen
                unselectedBackgroundPainter = (new MattePainter(new Color(199, 195, 212)));
            }
            default: {
                // Sonstige Flaechen
                unselectedBackgroundPainter = (new MattePainter(new Color(199, 195, 212)));
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
