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
import com.vividsolutions.jts.index.strtree.STRtree;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.cismet.cids.dynamics.AbstractCustomBeanPermissionProvider;
import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.navigatorplugin.CidsFeature;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public abstract class BasicGeometryFromCidsObjectPermissionProvider extends AbstractCustomBeanPermissionProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(
            BasicGeometryFromCidsObjectPermissionProvider.class);

    //~ Methods ----------------------------------------------------------------

    @Override
    public boolean getCustomReadPermissionDecisionforUser(final User u) {
        return true;
    }

    @Override
    public boolean getCustomWritePermissionDecisionforUser(final User u) {
        if (u.getUserGroup().getName().equalsIgnoreCase("administratoren")) {
            if (log.isDebugEnabled()) {
                log.debug("member of admin group. permission is granted");
            }
            return true;
        }

        final Geometry objectGeom = getGeometry();
        if (objectGeom == null) {
            log.info(getGeometry() + "delivered a null value. access is granted");
            return true;
        }

        final String userGroup = u.getUserGroup().getName();

        final STRtree restrictionfeatures = CidsRestrictionGeometryStore.getRestrictions().get(userGroup);
        final ArrayList<String> restrictionKeys = CidsRestrictionGeometryStore.getRestrictionKeys().get(userGroup);

        boolean keyWordHitsAvailable = false;

        if (restrictionKeys == null) {
            return true;
        }

        for (final String keyentry : restrictionKeys) {
            if (keyentry.toLowerCase().indexOf(getKey().toLowerCase()) > -1) {
                keyWordHitsAvailable = true;
                break;
            }
        }

        final List selectedFeatures = restrictionfeatures.query(objectGeom.getEnvelopeInternal());
        for (final Object hit : selectedFeatures) {
            final CidsFeature cf = (CidsFeature)hit;
            final Geometry hitGeom = cf.getGeometry();
            final CidsBean cb = cf.getMetaObject().getBean();
            final Timestamp ts = (Timestamp)cb.getProperty("validuntil");
            final Date now = new Date();
            final String restrictionkeys = (String)cb.getProperty("restrictionkeys");

            final boolean geometryHit = hitGeom.contains(objectGeom);
            final boolean keyWordHit = restrictionkeys.toLowerCase().indexOf(getKey().toLowerCase()) > -1;
            final boolean timestampHit = ((ts == null) || (ts.getTime() > now.getTime()));

            if (keyWordHit && geometryHit && timestampHit) {
                return true;
            }
        }
        if (keyWordHitsAvailable) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract Geometry getGeometry();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public abstract String getKey();
}
