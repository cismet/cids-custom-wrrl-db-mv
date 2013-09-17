/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.permissions.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.index.strtree.STRtree;

import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.navigatorplugin.CidsFeature;

import de.cismet.tools.configuration.StartupHook;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = StartupHook.class)
public class CidsRestrictionGeometryStore implements StartupHook {

    //~ Static fields/initializers ---------------------------------------------

    public static final String DOMAIN = "WRRL_DB_MV";
    public static final String TABLE = "CCS_RESTRICTIONINFO";
    private static final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(
            CidsRestrictionGeometryStore.class);
    private static HashMap<String, STRtree> restrictions = new HashMap<String, STRtree>();
    private static HashMap<String, ArrayList<String>> restrictionKeys = new HashMap<String, ArrayList<String>>();

    //~ Methods ----------------------------------------------------------------

    @Override
    public void applicationStarted() {
        try {
            if (log.isDebugEnabled()) {
                log.debug("CidsRestrictionGeometryStore initialization started");
            }
            final MetaClass mc = ClassCacheMultiple.getMetaClass(DOMAIN, TABLE);
            final MetaObject[] metaObjects = SessionManager.getConnection()
                        .getMetaObjectByQuery(SessionManager.getSession().getUser(),
                            "SELECT "
                            + mc.getID()
                            + ", "
                            + mc.getPrimaryKey()
                            + " FROM "
                            + mc.getTableName()
                            + " order by "
                            + mc.getPrimaryKey());

            for (final MetaObject mo : metaObjects) {
                final CidsBean cb = mo.getBean();

                final String group = (String)cb.getProperty("usergroup.name");
                STRtree restrictionfeatures = restrictions.get(group);
                ArrayList<String> grouprestrictionKeys = restrictionKeys.get(group);

                if (restrictionfeatures == null) {
                    restrictionfeatures = new STRtree();
                    restrictions.put(group, restrictionfeatures);
                }

                if (grouprestrictionKeys == null) {
                    grouprestrictionKeys = new ArrayList<String>();
                    restrictionKeys.put(group, grouprestrictionKeys);
                }

                final CidsFeature feature = new CidsFeature(mo);
                if ((feature != null) && (feature.getGeometry() != null)) {
                    restrictionfeatures.insert(feature.getGeometry().getEnvelopeInternal(), feature);
                }
                grouprestrictionKeys.add((String)cb.getProperty("restrictionkeys"));
            }
            if (log.isDebugEnabled()) {
                log.debug("CidsRestrictionGeometryStore initialized with " + metaObjects.length + " Object"
                            + ((metaObjects.length == 1) ? "" : "s") + ".");
            }
        } catch (Exception e) {
            log.warn("Error during initialization of restriction objects.", e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static HashMap<String, STRtree> getRestrictions() {
        return restrictions;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static HashMap<String, ArrayList<String>> getRestrictionKeys() {
        return restrictionKeys;
    }
}
