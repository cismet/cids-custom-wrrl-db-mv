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
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;
import java.awt.event.ActionEvent;

import java.beans.PropertyChangeEvent;

import javax.swing.JMenuItem;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class HydrologRWBandMember extends LineBandMember {

    //~ Instance fields --------------------------------------------------------

// private static final MetaClass HYDROLOG = ClassCacheMultiple.getMetaClass(
// WRRLUtil.DOMAIN_NAME,
// "GUP_HYDROLOG_NAME");

    private JMenuItem[] menuItems;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent  DOCUMENT ME!
     */
    public HydrologRWBandMember(final HydrologRWBand parent) {
        super(parent);
        lineFieldName = "linie";
    }

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent    DOCUMENT ME!
     * @param  readOnly  DOCUMENT ME!
     */
    public HydrologRWBandMember(final HydrologRWBand parent, final boolean readOnly) {
        super(parent, readOnly);
        lineFieldName = "linie";
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
//        setToolTipText(bean.getProperty("name_bHydrologRWBandezeichnung.name") + "");
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void determineBackgroundColour() {
//        if (bean.getProperty("name_bezeichnung") == null) {
//            return;
//        }
//        final int art = (Integer)bean.getProperty("name_bezeichnung.id");
//
//        switch (art) {
//            case 9: {
//                // derzeitigen Zustand mindestens erhalten
        unselectedBackgroundPainter = (new MattePainter(new Color(241, 220, 219)));
//                break;
//            }
//            case 10: {
//                // Gewässer im Entwicklungsraum gestalten
//                unselectedBackgroundPainter = (new MattePainter(new Color(216, 216, 216)));
//                break;
//            }
//            case 11: {
//                // Gewässer im vorhandenen Profil entwickeln
//                unselectedBackgroundPainter = (new MattePainter(new Color(209, 252, 207)));
//                break;
//            }
//            case 12: {
//                // Zustand wegen Restriktion belassen
//                unselectedBackgroundPainter = (new MattePainter(new Color(255, 100, 0)));
//
//                break;
//            }
//            case 13: {
//                // Gewässerunterhaltung anpassen/modifizieren
//                unselectedBackgroundPainter = (new MattePainter(new Color(197, 103, 13)));
//                break;
//            }
//        }
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

//    /**
//     * DOCUMENT ME!
//     *
//     * @param  id  DOCUMENT ME!
//     */
//    private void setEntwicklungsziel(final String id) {
//        final String query = "select " + ENTWICKLUNGSZIEL.getID() + "," + ENTWICKLUNGSZIEL.getPrimaryKey() + " from "
//                    + ENTWICKLUNGSZIEL.getTableName() + " where id = " + id; // NOI18N
//        final MetaObject[] metaObjects = MetaObjectCache.getInstance().getMetaObjectByQuery(query);
//        CidsBean b = null;
//
//        if (metaObjects != null) {
//            for (final MetaObject tmp : metaObjects) {
//                if (tmp.getBean().getProperty("id").toString().equals(id)) {
//                    b = tmp.getBean();
//                    break;
//                }
//            }
//        }
//        try {
//            bean.setProperty("name_bezeichnung", b);
//        } catch (Exception e) {
//            LOG.error("Error while setting property massnahme.", e);
//        }
//    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void configurePopupMenu() {
//        final String query = "select " + ENTWICKLUNGSZIEL.getID() + "," + ENTWICKLUNGSZIEL.getPrimaryKey() + " from "
//                    + ENTWICKLUNGSZIEL.getTableName(); // NOI18N
//        final MetaObject[] metaObjects = MetaObjectCache.getInstance().getMetaObjectByQuery(query);
//
//        menuItems = new JMenuItem[metaObjects.length];
//
//        for (int i = 0; i < metaObjects.length; ++i) {
//            menuItems[i] = new JMenuItem(metaObjects[i].getBean().toString());
//            menuItems[i].addActionListener(this);
//            menuItems[i].setActionCommand(String.valueOf(metaObjects[i].getID()));
//            popup.add(menuItems[i]);
//        }
//
//        popup.addSeparator();

        super.configurePopupMenu();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
//        boolean found = false;
//
//        for (final JMenuItem tmp : menuItems) {
//            if (e.getSource() == tmp) {
//                found = true;
//                setEntwicklungsziel(tmp.getActionCommand());
//                fireBandMemberChanged(false);
//                break;
//            }
//        }
//
//        if (!found) {
        super.actionPerformed(e);
//        }

        newMode = false;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("name_bezeichnung")) {
            determineBackgroundColour();
            setSelected(isSelected);
            if (bean.getProperty("name_bezeichnung.name") != null) {
                setToolTipText(bean.getProperty("name_bezeichnung.name") + "");
            } else {
                setToolTipText("");
            }
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
