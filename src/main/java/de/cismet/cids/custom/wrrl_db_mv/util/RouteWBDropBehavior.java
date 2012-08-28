/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 therter
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.wrrl_db_mv.util;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.awt.Component;

import java.util.List;

import javax.swing.JOptionPane;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LineEditorDropBehavior;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class RouteWBDropBehavior implements LineEditorDropBehavior {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            RouteWBDropBehavior.class);
    private static final MetaClass MC_WK_FG = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");

    //~ Instance fields --------------------------------------------------------

    private CidsBean wkFg = null;
    private Component comp = null;
    private boolean routeChanged = false;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new RouteWBDropBehavior object.
     *
     * @param  comp  DOCUMENT ME!
     */
    public RouteWBDropBehavior(final Component comp) {
        this.comp = comp;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  id  wkFg DOCUMENT ME!
     */
    public void setWkFgById(final Integer id) {
        this.routeChanged = false;
        String query = "select " + MC_WK_FG.getID() + ", m." + MC_WK_FG.getPrimaryKey() + " from "
                    + MC_WK_FG.getTableName(); // NOI18N
        query += " m";                         // NOI18N
        query += " WHERE m.id = " + id;        // NOI18N

        try {
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            if (metaObjects.length == 1) {
                this.wkFg = metaObjects[0].getBean();
            } else {
                LOG.error(
                    metaObjects.length
                            + " water bodies found, but exactly one water body should be found.");
            }
        } catch (ConnectionException e) {
            LOG.error("Error while loading the water body object with query: " + query, e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkFg  DOCUMENT ME!
     */
    public void setWkFg(final CidsBean wkFg) {
        this.routeChanged = false;
        this.wkFg = wkFg;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CidsBean getWkFg() {
        return wkFg;
    }

    @Override
    public boolean checkForAdding(final CidsBean cidsBean) {
        final int droppedRouteId = (Integer)cidsBean.getProperty("id");

        if (wkFg != null) {
            final List<CidsBean> teile = CidsBeanSupport.getBeanCollectionFromProperty(wkFg, "teile");

            if (teile != null) {
                for (final CidsBean bean : teile) {
                    final CidsBean line = (CidsBean)bean.getProperty("linie");
                    if (line != null) {
                        final CidsBean stat = (CidsBean)line.getProperty(
                                LinearReferencingConstants.PROP_STATIONLINIE_FROM);
                        if (stat != null) {
                            final CidsBean route = (CidsBean)stat.getProperty(
                                    LinearReferencingConstants.PROP_STATION_ROUTE);
                            if (route != null) {
                                final int id = (Integer)route.getProperty(LinearReferencingConstants.PROP_ID);
                                if (id == droppedRouteId) {
                                    routeChanged = true;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                StaticSwingTools.getParentFrame(comp),
                "Es wurde noch kein Fließgewässer zugeordnet.",
                "Kein Fließgewässer",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        JOptionPane.showMessageDialog(
            StaticSwingTools.getParentFrame(comp),
            "Die gewählte Route gehört nicht zum eingestellten Fließgewässer.",
            "Route ungültig",
            JOptionPane.ERROR_MESSAGE);
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isRouteChanged() {
        return routeChanged;
    }
}
