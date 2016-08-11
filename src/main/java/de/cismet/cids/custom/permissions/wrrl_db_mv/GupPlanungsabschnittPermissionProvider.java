/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.permissions.wrrl_db_mv;

import Sirius.server.newuser.User;

import com.vividsolutions.jts.geom.Geometry;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class GupPlanungsabschnittPermissionProvider extends BasicGeometryFromCidsObjectPermissionProvider {

    //~ Methods ----------------------------------------------------------------

    @Override
    public boolean getCustomWritePermissionDecisionforUser(final User u) {
        final CidsBean gup = (CidsBean)cidsBean.getProperty("gup");

        if (!GupGupPermissionProvider.checkBearbeiter(u, gup)) {
            return false;
        }

        return super.getCustomWritePermissionDecisionforUser(u);
    }

    @Override
    public Geometry getGeometry() {
        return (Geometry)cidsBean.getProperty("linie.geom.geo_field");
    }

    @Override
    public String getKey() {
        return "GUP-Planer";
    }
}
