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
package de.cismet.cids.custom.util;

import java.awt.Component;

import java.util.List;

import javax.swing.JOptionPane;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class RouteWBDropBehavior implements LinearReferencedLineEditorDropBehavior {

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
                    final CidsBean stat = (CidsBean)bean.getProperty("von");

                    if (stat != null) {
                        final CidsBean route = (CidsBean)stat.getProperty("route");
                        if (route != null) {
                            final int id = (Integer)route.getProperty("id");
                            if (id == droppedRouteId) {
                                routeChanged = true;
                                return true;
                            }
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                comp,
                "Es wurde noch kein Fließgewässer zugeordnet.",
                "kein Fließgewässer",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        JOptionPane.showMessageDialog(
            comp,
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
