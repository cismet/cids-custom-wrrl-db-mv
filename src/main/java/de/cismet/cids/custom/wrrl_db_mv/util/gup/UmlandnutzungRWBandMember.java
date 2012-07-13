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
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;
import java.awt.event.ActionEvent;

import java.beans.PropertyChangeEvent;

import java.util.List;

import javax.swing.JMenuItem;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class UmlandnutzungRWBandMember extends LineBandMember {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass OBERGRUPPE_ART = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "GUP_UMLANDNUTZUNGSOBERGRUPPE");
    private static final MetaClass UMLANDNUTZUNG = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "GUP_UMLANDNUTZUNGSGRUPPE");

    //~ Instance fields --------------------------------------------------------

    private JMenuItem[] menuItems;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent  DOCUMENT ME!
     */
    public UmlandnutzungRWBandMember(final UmlandnutzungRWBand parent) {
        super(parent);
        lineFieldName = "linie";
    }

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent    DOCUMENT ME!
     * @param  readOnly  DOCUMENT ME!
     */
    public UmlandnutzungRWBandMember(final UmlandnutzungRWBand parent, final boolean readOnly) {
        super(parent, readOnly);
        lineFieldName = "linie";
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
        setToolTipText(bean.getProperty("art.name") + "");
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void determineBackgroundColour() {
        if (bean.getProperty("art") == null) {
            setDefaultBackgound();
            return;
        }
        final CidsBean artBean = (CidsBean)bean.getProperty("art");
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
        setBackgroundPainter(unselectedBackgroundPainter);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    private void setUmlandnutzung(final String id) {
        final String query = "select " + UMLANDNUTZUNG.getID() + "," + UMLANDNUTZUNG.getPrimaryKey() + " from "
                    + UMLANDNUTZUNG.getTableName() + " where id = " + id; // NOI18N
        final MetaObject[] metaObjects = MetaObjectCache.getInstance().getMetaObjectByQuery(query);
        CidsBean b = null;

        if (metaObjects != null) {
            for (final MetaObject tmp : metaObjects) {
                if (tmp.getBean().getProperty("id").toString().equals(id)) {
                    b = tmp.getBean();
                    break;
                }
            }
        }
        try {
            bean.setProperty("art", b);
        } catch (Exception e) {
            LOG.error("Error while setting property massnahme.", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void configurePopupMenu() {
        final String query = "select " + UMLANDNUTZUNG.getID() + "," + UMLANDNUTZUNG.getPrimaryKey() + " from "
                    + UMLANDNUTZUNG.getTableName(); // NOI18N
        final MetaObject[] metaObjects = MetaObjectCache.getInstance().getMetaObjectByQuery(query);

        menuItems = new JMenuItem[metaObjects.length];

        for (int i = 0; i < metaObjects.length; ++i) {
            menuItems[i] = new JMenuItem(metaObjects[i].getBean().toString());
            menuItems[i].addActionListener(this);
            menuItems[i].setActionCommand(String.valueOf(metaObjects[i].getID()));
            popup.add(menuItems[i]);
        }

        popup.addSeparator();

        super.configurePopupMenu();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        boolean found = false;

        for (final JMenuItem tmp : menuItems) {
            if (e.getSource() == tmp) {
                found = true;
                setUmlandnutzung(tmp.getActionCommand());
                fireBandMemberChanged(false);
                break;
            }
        }

        if (!found) {
            super.actionPerformed(e);
        }

        newMode = false;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("art")) {
            determineBackgroundColour();
            setSelected(isSelected);
            setToolTipText(bean.getProperty("art.name") + "");
        } else {
            super.propertyChange(evt);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    @Override
    protected CidsBean cloneBean(final CidsBean bean) throws Exception {
        return CidsBeanSupport.cloneCidsBean(bean, false);
    }
}
