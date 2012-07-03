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
public class OperativesZielRWBandMember extends LineBandMember {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass OPERATIVES_ZIEL = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "GUP_OPERATIVES_ZIEL");

    //~ Instance fields --------------------------------------------------------

    private JMenuItem[] menuItems;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent  DOCUMENT ME!
     */
    public OperativesZielRWBandMember(final OperativesZielRWBand parent) {
        super(parent);
        lineFieldName = "linie";
    }

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent    DOCUMENT ME!
     * @param  readOnly  DOCUMENT ME!
     */
    public OperativesZielRWBandMember(final OperativesZielRWBand parent, final boolean readOnly) {
        super(parent, readOnly);
        lineFieldName = "linie";
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
        setToolTipText(bean.getProperty("operatives_ziel.name") + "");
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void determineBackgroundColour() {
        if (bean.getProperty("operatives_ziel") == null) {
            setDefaultBackgound();
            return;
        }
        final int art = (Integer)bean.getProperty("operatives_ziel.id");

        switch (art) {
            case 1: {
                // derzeitigen Zustand mindestens erhalten
                unselectedBackgroundPainter = (new MattePainter(new Color(241, 220, 219)));
                break;
            }
            case 2: {
                // Gewässer im Entwicklungsraum gestalten
                unselectedBackgroundPainter = (new MattePainter(new Color(216, 216, 216)));
                break;
            }
            case 3: {
                // Gewässer im vorhandenen Profil entwickeln
                unselectedBackgroundPainter = (new MattePainter(new Color(209, 252, 207)));
                break;
            }
            case 4: {
                // Zustand wegen Restriktion belassen
                unselectedBackgroundPainter = (new MattePainter(new Color(255, 100, 0)));

                break;
            }
            case 5: {
                // Gewässerunterhaltung anpassen/modifizieren
                unselectedBackgroundPainter = (new MattePainter(new Color(197, 103, 13)));
                break;
            }
            case 6: {
                // Gewässerunterhaltung anpassen/modifizieren
                unselectedBackgroundPainter = (new MattePainter(new Color(229, 252, 194)));
                break;
            }
            case 7: {
                // Gewässerunterhaltung anpassen/modifizieren
                unselectedBackgroundPainter = (new MattePainter(new Color(89, 79, 79)));
                break;
            }
            case 8: {
                // Gewässerunterhaltung anpassen/modifizieren
                unselectedBackgroundPainter = (new MattePainter(new Color(157, 224, 173)));
                break;
            }
            case 9: {
                // Gewässerunterhaltung anpassen/modifizieren
                unselectedBackgroundPainter = (new MattePainter(new Color(69, 173, 168)));
                break;
            }
            case 10: {
                // Gewässerunterhaltung anpassen/modifizieren
                unselectedBackgroundPainter = (new MattePainter(new Color(84, 121, 128)));
                break;
            }
            case 11: {
                // Gewässerunterhaltung anpassen/modifizieren
                unselectedBackgroundPainter = (new MattePainter(new Color(157, 203, 13)));
                break;
            }
            case 12: {
                // Gewässerunterhaltung anpassen/modifizieren
                unselectedBackgroundPainter = (new MattePainter(new Color(197, 203, 13)));
                break;
            }
            case 13: {
                // Gewässerunterhaltung anpassen/modifizieren
                unselectedBackgroundPainter = (new MattePainter(new Color(197, 103, 213)));
                break;
            }
            case 14: {
                // Gewässerunterhaltung anpassen/modifizieren
                unselectedBackgroundPainter = (new MattePainter(new Color(97, 203, 113)));
                break;
            }
            case 15: {
                // Gewässerunterhaltung anpassen/modifizieren
                unselectedBackgroundPainter = (new MattePainter(new Color(197, 103, 13)));
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
        setBackgroundPainter(unselectedBackgroundPainter);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    private void setOperativesZiel(final String id) {
        final String query = "select " + OPERATIVES_ZIEL.getID() + "," + OPERATIVES_ZIEL.getPrimaryKey() + " from "
                    + OPERATIVES_ZIEL.getTableName() + " where id = " + id; // NOI18N
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
            bean.setProperty("operatives_ziel", b);
        } catch (Exception e) {
            LOG.error("Error while setting property massnahme.", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void configurePopupMenu() {
        final String query = "select " + OPERATIVES_ZIEL.getID() + "," + OPERATIVES_ZIEL.getPrimaryKey() + " from "
                    + OPERATIVES_ZIEL.getTableName(); // NOI18N
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
                setOperativesZiel(tmp.getActionCommand());
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
        if (evt.getPropertyName().equals("operatives_ziel")) {
            determineBackgroundColour();
            setSelected(isSelected);
            setToolTipText(bean.getProperty("operatives_ziel.name") + "");
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
