/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.util;

import Sirius.navigator.connection.SessionManager;
import Sirius.server.middleware.types.MetaClass;
import Sirius.server.newuser.User;
import com.vividsolutions.jts.geom.Geometry;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author srichter
 */
public final class CidsBeanSupport {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CidsBeanSupport.class);
    public static final String FIELD_NOT_SET = "<nicht gesetzt>";

    private CidsBeanSupport() {
        throw new AssertionError();
    }
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(CidsBeanSupport.class);
    public static final String DOMAIN_NAME = "WRRL_DB_MV";

    public static CidsBean createNewCidsBeanFromTableName(final String tableName, final Map<String, Object> initialProperties) throws Exception {
        final CidsBean newBean = createNewCidsBeanFromTableName(tableName);
        for (Entry<String, Object> property : initialProperties.entrySet()) {
            newBean.setProperty(property.getKey(), property.getValue());
        }
        return newBean;
    }

    public static CidsBean createNewCidsBeanFromTableName(final String tableName) throws Exception {
        if (tableName != null) {
            final MetaClass metaClass = ClassCacheMultiple.getMetaClass(DOMAIN_NAME, tableName);
            if (metaClass != null) {
                return metaClass.getEmptyInstance().getBean();
            }
        }
        throw new Exception("Could not find MetaClass for table " + tableName);
    }

    public static List<CidsBean> getBeanCollectionFromProperty(CidsBean bean, String collectionProperty) {
        if (bean != null && collectionProperty != null) {
            final Object colObj = bean.getProperty(collectionProperty);
            if (colObj instanceof Collection) {
                return (List<CidsBean>) colObj;
            }
        }
        return null;
    }

    public static boolean checkWritePermission(CidsBean bean) {
        User user = SessionManager.getSession().getUser();
        return bean.getHasWritePermission(user);
    }


    /**
     * Deletes the given proeprty from the given bean. The given property must be of type station.
     *
     * @param bean
     * @param propertyName
     * @param beansToDelete
     * @throws Exception
     */
    public static void deleteStationIfExists(CidsBean bean, String propertyName, List<CidsBean> beansToDelete) throws Exception {
        Object station = bean.getProperty(propertyName);

        if (station instanceof CidsBean) {
            CidsBean cbean = (CidsBean)station;
            deletePropertyIfExists(cbean, "real_point", beansToDelete);
            cbean.delete();
            beansToDelete.add(cbean);
        }
    }


    /**
     * Deletes the given proeprty from the given bean. Only the object behind the given property
     * will be deleted. No sub objects.
     *
     * @param bean
     * @param propertyName
     * @param beansToDelete
     * @throws Exception
     */
    public static void deletePropertyIfExists(CidsBean bean, String propertyName, List<CidsBean> beansToDelete) throws Exception {
        Object beanToDelete = bean.getProperty(propertyName);

        if (beanToDelete instanceof CidsBean) {
            ((CidsBean)beanToDelete).delete();
            beansToDelete.add(((CidsBean)beanToDelete));
        }
    }

    /**
     * This method will not create deep copy of the given station, but only a almost deep copy, because
     * the route object of the station will not be cloned to avoid multiple instances of the same
     * route in the database.
     * @param bean the station bean that should be cloned.
     * @return a "deep" copy of the given station
     * @throws Exception
     */
    public static CidsBean cloneStation(CidsBean bean) throws Exception {
        if (bean == null) {
            return null;
        }
        final CidsBean clone = bean.getMetaObject().getMetaClass().getEmptyInstance().getBean();

        clone.setProperty("wert", bean.getProperty("wert"));

        if (bean.getProperty("real_point") instanceof CidsBean) {
            clone.setProperty("real_point", cloneCidsBean((CidsBean)bean.getProperty("real_point")) );
        }
        clone.setProperty("route", bean.getProperty("route"));

        return clone;
    }

    /**
     * cloneCidsBean(CidsBean bean) was tested and works with the type geom.
     * Objects which have properties of a type that is not considered by the method, will not be returned as deep copy.
     * The results of this method can be used as a deep copy, if we assume, that the properties, which are not of
     * the type CidsBean, will not be changed in the future, but only replaced by other objects.
     * @param bean
     * @return a deep copy of the given object
     * @throws Exception
     */
    public static CidsBean cloneCidsBean(CidsBean bean) throws Exception {
        if (bean == null) {
            return null;
        }
        final CidsBean clone = bean.getMetaObject().getMetaClass().getEmptyInstance().getBean();

        for (String propName : bean.getPropertyNames()) {
            if (!propName.toLowerCase().equals("id")) {
                Object o = bean.getProperty(propName);

                if (o instanceof CidsBean) {
                    clone.setProperty(propName, cloneCidsBean( (CidsBean)o) );
                } else if (o instanceof Collection) {
                    List<CidsBean> list = (List<CidsBean>)o;
                    List<CidsBean> newList = new ArrayList<CidsBean>();

                    for (CidsBean tmpBean : list) {
                        newList.add( cloneCidsBean(tmpBean) );
                    }
                    clone.setProperty(propName, newList );
                } else if (o instanceof Geometry) {
                    clone.setProperty( propName, ((Geometry)o).clone());
                } else if (o instanceof Long) {
                    clone.setProperty( propName, new Long(o.toString())  );
                } else if (o instanceof Double) {
                    clone.setProperty( propName, new Double(o.toString()) );
                } else if (o instanceof Integer) {
                    clone.setProperty( propName, new Integer(o.toString()) );
                } else if (o instanceof String) {
                    clone.setProperty( propName, o);
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
