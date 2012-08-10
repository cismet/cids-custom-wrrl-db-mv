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

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupOperativesZielAbschnittEditor;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupOperativesZielRouteEditor;
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
        if ((bean.getProperty("operatives_ziel") == null) || (bean.getProperty("operatives_ziel.color") == null)) {
            setDefaultBackgound();
            return;
        }

        final String color = (String)bean.getProperty("operatives_ziel.color");

        if (color != null) {
            try {
                setBackgroundPainter(new MattePainter(Color.decode(color)));
            } catch (NumberFormatException e) {
                LOG.error("Error while parsing the color.", e);
                setDefaultBackgound();
            }
        }

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

        final int type = ((OperativesZielRWBand)getParentBand()).getType();
        int kompartiment = 0;

        if (type == GupOperativesZielRouteEditor.GUP_SOHLE) {
            kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_SOHLE;
        } else if (type == GupOperativesZielRouteEditor.GUP_UFER_LINKS) {
            kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UFER;
        } else if (type == GupOperativesZielRouteEditor.GUP_UFER_RECHTS) {
            kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UFER;
        } else if (type == GupOperativesZielRouteEditor.GUP_UMFELD_LINKS) {
            kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UMFELD;
        } else if (type == GupOperativesZielRouteEditor.GUP_UMFELD_RECHTS) {
            kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UMFELD;
        }

        menuItems = new JMenuItem[metaObjects.length];

        for (int i = 0; i < metaObjects.length; ++i) {
            if (OperativeZieleComboBox.isRelevantBean(kompartiment, metaObjects[i].getBean())) {
                menuItems[i] = new JMenuItem(metaObjects[i].getBean().toString());
                menuItems[i].addActionListener(this);
                menuItems[i].setActionCommand(String.valueOf(metaObjects[i].getID()));
                popup.add(menuItems[i]);
            }
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
