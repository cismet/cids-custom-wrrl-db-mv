/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.util;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.newuser.User;

import com.vividsolutions.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   srichter
 * @version  $Revision$, $Date$
 */
public final class CidsBeanSupport {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CidsBeanSupport.class);
    public static final String FIELD_NOT_SET = "<nicht gesetzt>";
    public static final String DOMAIN_NAME = "WRRL_DB_MV";

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CidsBeanSupport object.
     *
     * @throws  AssertionError  DOCUMENT ME!
     */
    private CidsBeanSupport() {
        throw new AssertionError();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   tableName          DOCUMENT ME!
     * @param   initialProperties  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static CidsBean createNewCidsBeanFromTableName(final String tableName,
            final Map<String, Object> initialProperties) throws Exception {
        final CidsBean newBean = createNewCidsBeanFromTableName(tableName);
        for (final Entry<String, Object> property : initialProperties.entrySet()) {
            newBean.setProperty(property.getKey(), property.getValue());
        }
        return newBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   tableName  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static CidsBean createNewCidsBeanFromTableName(final String tableName) throws Exception {
        if (tableName != null) {
            final MetaClass metaClass = ClassCacheMultiple.getMetaClass(DOMAIN_NAME, tableName);
            if (metaClass != null) {
                return metaClass.getEmptyInstance().getBean();
            }
        }
        throw new Exception("Could not find MetaClass for table " + tableName);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean                DOCUMENT ME!
     * @param   collectionProperty  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static List<CidsBean> getBeanCollectionFromProperty(final CidsBean bean, final String collectionProperty) {
        if ((bean != null) && (collectionProperty != null)) {
            final Object colObj = bean.getProperty(collectionProperty);
            if (colObj instanceof Collection) {
                return (List<CidsBean>)colObj;
            }
        }
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static boolean checkWritePermission(final CidsBean bean) {
        final User user = SessionManager.getSession().getUser();
        return bean.getHasWritePermission(user);
    }

    /**
     * Deletes the given proeprty from the given bean. The given property must be of type station.
     *
     * @param   bean           DOCUMENT ME!
     * @param   propertyName   DOCUMENT ME!
     * @param   beansToDelete  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void deleteStationIfExists(final CidsBean bean,
            final String propertyName,
            final List<CidsBean> beansToDelete) throws Exception {
        final Object station = bean.getProperty(propertyName);

        if (station instanceof CidsBean) {
            final CidsBean cbean = (CidsBean)station;
            deletePropertyIfExists(cbean, LinearReferencingConstants.PROP_STATION_GEOM, beansToDelete);
            cbean.delete();
            beansToDelete.add(cbean);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean           DOCUMENT ME!
     * @param   propertyName   DOCUMENT ME!
     * @param   beansToDelete  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void deleteStationlineIfExists(final CidsBean bean,
            final String propertyName,
            final List<CidsBean> beansToDelete) throws Exception {
        final Object line = bean.getProperty(propertyName);

        if (line instanceof CidsBean) {
            final CidsBean cbean = (CidsBean)line;
            CidsBeanSupport.deleteStationIfExists(
                cbean,
                LinearReferencingConstants.PROP_STATIONLINIE_FROM,
                beansToDelete); // NOI18N
            CidsBeanSupport.deleteStationIfExists(
                cbean,
                LinearReferencingConstants.PROP_STATIONLINIE_TO,
                beansToDelete); // NOI18N
            CidsBeanSupport.deletePropertyIfExists(
                cbean,
                LinearReferencingConstants.PROP_STATIONLINIE_GEOM,
                beansToDelete); // NOI18N
            cbean.delete();
            beansToDelete.add(cbean);
        }
    }

    /**
     * Deletes the given proeprty from the given bean. Only the object behind the given property will be deleted. No sub
     * objects.
     *
     * @param   bean           DOCUMENT ME!
     * @param   propertyName   DOCUMENT ME!
     * @param   beansToDelete  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void deletePropertyIfExists(final CidsBean bean,
            final String propertyName,
            final List<CidsBean> beansToDelete) throws Exception {
        final Object beanToDelete = bean.getProperty(propertyName);

        if (beanToDelete instanceof CidsBean) {
            ((CidsBean)beanToDelete).delete();
            beansToDelete.add(((CidsBean)beanToDelete));
        }
    }

    /**
     * This method will not create deep copy of the given station, but only a almost deep copy, because the route object
     * of the station will not be cloned to avoid multiple instances of the same route in the database.
     *
     * @param   bean  the station bean that should be cloned.
     *
     * @return  a "deep" copy of the given station
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static CidsBean cloneStation(final CidsBean bean) throws Exception {
        if (bean == null) {
            return null;
        }
        final CidsBean clone = bean.getMetaObject().getMetaClass().getEmptyInstance().getBean();

        clone.setProperty(
            LinearReferencingConstants.PROP_STATION_VALUE,
            bean.getProperty(LinearReferencingConstants.PROP_STATION_VALUE));

        final Object geom = bean.getProperty(LinearReferencingConstants.PROP_STATION_GEOM);
        if (geom instanceof CidsBean) {
            clone.setProperty(LinearReferencingConstants.PROP_STATION_GEOM, cloneCidsBean((CidsBean)geom));
        }
        clone.setProperty(
            LinearReferencingConstants.PROP_STATION_ROUTE,
            bean.getProperty(LinearReferencingConstants.PROP_STATION_ROUTE));

        return clone;
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
    public static CidsBean cloneStationline(final CidsBean bean) throws Exception {
        if (bean == null) {
            return null;
        }
        final CidsBean clone = bean.getMetaObject().getMetaClass().getEmptyInstance().getBean();

        final Object fromBean = bean.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_FROM);
        if (fromBean instanceof CidsBean) {
            clone.setProperty(LinearReferencingConstants.PROP_STATIONLINIE_FROM, cloneStation((CidsBean)fromBean));
        }
        final Object toBean = bean.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_TO);
        if (toBean instanceof CidsBean) {
            clone.setProperty(LinearReferencingConstants.PROP_STATIONLINIE_TO, cloneStation((CidsBean)toBean));
        }
        final Object geomBean = bean.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_GEOM);
        if (geomBean instanceof CidsBean) {
            clone.setProperty(LinearReferencingConstants.PROP_STATIONLINIE_GEOM, cloneCidsBean((CidsBean)geomBean));
        }

        return clone;
    }

    /**
     * cloneCidsBean(CidsBean bean) was tested and works with the type geom. Objects which have properties of a type
     * that is not considered by the method, will not be returned as deep copy. The results of this method can be used
     * as a deep copy, if we assume, that the properties, which are not of the type CidsBean, will not be changed in the
     * future, but only replaced by other objects.
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  a deep copy of the given object
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static CidsBean cloneCidsBean(final CidsBean bean) throws Exception {
        if (bean == null) {
            return null;
        }
        final CidsBean clone = bean.getMetaObject().getMetaClass().getEmptyInstance().getBean();

        for (final String propName : bean.getPropertyNames()) {
            if (!propName.toLowerCase().equals("id")) {
                final Object o = bean.getProperty(propName);

                if (o instanceof CidsBean) {
                    clone.setProperty(propName, cloneCidsBean((CidsBean)o));
                } else if (o instanceof Collection) {
                    final List<CidsBean> list = (List<CidsBean>)o;
                    final List<CidsBean> newList = new ArrayList<CidsBean>();

                    for (final CidsBean tmpBean : list) {
                        newList.add(cloneCidsBean(tmpBean));
                    }
                    clone.setProperty(propName, newList);
                } else if (o instanceof Geometry) {
                    clone.setProperty(propName, ((Geometry)o).clone());
                } else if (o instanceof Long) {
                    clone.setProperty(propName, new Long(o.toString()));
                } else if (o instanceof Double) {
                    clone.setProperty(propName, new Double(o.toString()));
                } else if (o instanceof Integer) {
                    clone.setProperty(propName, new Integer(o.toString()));
                } else if (o instanceof String) {
                    clone.setProperty(propName, o);
                } else {
                    if (o != null) {
                        LOG.error("unknown property type: " + o.getClass().getName());
                    }
                    clone.setProperty(propName, o);
                }
            }
        }

        return clone;
    }
}
