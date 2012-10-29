/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import Sirius.navigator.tools.CacheException;
import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.RectanglePainter;

import java.awt.Color;
import java.awt.event.ActionEvent;

import java.beans.PropertyChangeEvent;

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
public class VermeidungsgruppeRWBandMember extends LineBandMember {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass VERMEIDUNGSGRUPPE = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "VERMEIDUNGSGRUPPE");

    //~ Instance fields --------------------------------------------------------

    private JMenuItem[] menuItems;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent  DOCUMENT ME!
     */
    public VermeidungsgruppeRWBandMember(final VermeidungsgruppeRWBand parent) {
        super(parent);
        lineFieldName = "linie";
    }

    /**
     * Creates new form MassnahmenBandMember.
     *
     * @param  parent    DOCUMENT ME!
     * @param  readOnly  DOCUMENT ME!
     */
    public VermeidungsgruppeRWBandMember(final VermeidungsgruppeRWBand parent, final boolean readOnly) {
        super(parent, readOnly);
        lineFieldName = "linie";
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        super.setCidsBean(cidsBean);
        setToolTipText(bean.getProperty("vermeidungsgruppe.name") + "");
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void determineBackgroundColour() {
        if ((bean.getProperty("vermeidungsgruppe") == null) || (bean.getProperty("vermeidungsgruppe.color") == null)) {
            setDefaultBackground();
            return;
        }

        final String color = (String)bean.getProperty("vermeidungsgruppe.color");

        if (color != null) {
            try {
                setBackgroundPainter(new MattePainter(Color.decode(color)));
            } catch (NumberFormatException e) {
                LOG.error("Error while parsing the color.", e);
                setDefaultBackground();
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
    private void setVermeidungsgruppe(final String id) {
        try {
            final String query = "select " + VERMEIDUNGSGRUPPE.getID() + "," + VERMEIDUNGSGRUPPE.getPrimaryKey()
                        + " from "
                        + VERMEIDUNGSGRUPPE.getTableName(); // NOI18N
            final MetaObject[] metaObjects = MetaObjectCache.getInstance().getMetaObjectsByQuery(query);
            CidsBean b = null;

            if (metaObjects != null) {
                for (final MetaObject tmp : metaObjects) {
                    if (tmp.getBean().getProperty("id").toString().equals(id)) {
                        b = tmp.getBean();
                        break;
                    }
                }
            }
            bean.setProperty("vermeidungsgruppe", b);
        } catch (Exception e) {
            LOG.error("Error while setting property massnahme.", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void configurePopupMenu() {
        try {
            final String query = "select " + VERMEIDUNGSGRUPPE.getID() + "," + VERMEIDUNGSGRUPPE.getPrimaryKey()
                        + " from "
                        + VERMEIDUNGSGRUPPE.getTableName(); // NOI18N
            final MetaObject[] metaObjects = MetaObjectCache.getInstance().getMetaObjectsByQuery(query);

            menuItems = new JMenuItem[metaObjects.length];

            for (int i = 0; i < metaObjects.length; ++i) {
                menuItems[i] = new JMenuItem(metaObjects[i].getBean().toString());
                menuItems[i].addActionListener(this);
                menuItems[i].setActionCommand(String.valueOf(metaObjects[i].getID()));
                popup.add(menuItems[i]);
            }
        } catch (CacheException e) {
            LOG.error("Cache Exception", e);
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
                setVermeidungsgruppe(tmp.getActionCommand());
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
        if (evt.getPropertyName().equals("vermeidungsgruppe")) {
            determineBackgroundColour();
            setSelected(isSelected);
            setToolTipText(bean.getProperty("vermeidungsgruppe.name") + "");
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
