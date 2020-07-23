/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util;

import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.log4j.Logger;

import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class CidsBeanNormalizer {

    //~ Instance fields --------------------------------------------------------

    private final Logger LOG = Logger.getLogger(CidsBeanNormalizer.class);
    private final Map<CidsBeanWrapper, CidsBean> beanCache = new HashMap<CidsBeanWrapper, CidsBean>();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public CidsBean normalizeCidsBean(final CidsBean bean) throws Exception {
        return normalizeCidsBean(bean, true);
    }

    /**
     * No CidsBean that was returned by this method uses a different instances of the same sub bean as an other CidsBean
     * that was returned by this method.
     *
     * @param   bean      DOCUMENT ME!
     * @param   useCache  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  a bean that represents the same object as the given bean, but it uses the same sub beans as
     *                     the bean which are former used with this method
     */
    public CidsBean normalizeCidsBean(final CidsBean bean, final boolean useCache) throws Exception {
        if (bean == null) {
            return null;
        }
        CidsBean cachedBean = null;

        if (useCache) {
            cachedBean = beanCache.get(new CidsBeanWrapper(
                        bean.getMetaObject().getMetaClass().getId(),
                        bean.getMetaObject().getId()));
        }

        if (cachedBean == null) {
            for (final String propName : bean.getPropertyNames()) {
                final Object o = bean.getProperty(propName);

                if (o instanceof CidsBean) {
                    bean.setProperty(propName, normalizeCidsBean((CidsBean)o, true));
                } else if (o instanceof List) {
                    List<CidsBean> beanList = (List<CidsBean>)o;
                    final List<CidsBean> listCopy = new ArrayList<CidsBean>(beanList);
                    beanList = ObservableCollections.observableList(new ArrayList<CidsBean>());
                    bean.setProperty(propName, beanList);

                    for (final CidsBean b : listCopy) {
                        beanList.add(normalizeCidsBean(b, true));
                    }
                }
            }

            cachedBean = cloneCidsBean(bean);
            cachedBean.getMetaObject().setID(bean.getMetaObject().getId());
            if (bean.getMetaObject().getReferencingObjectAttribute() != null) {
                cachedBean.getMetaObject()
                        .setReferencingObjectAttribute(bean.getMetaObject().getReferencingObjectAttribute());
            }
            cachedBean.getMetaObject().forceStatus(MetaObject.NO_STATUS);

            if (useCache) {
                beanCache.put(new CidsBeanWrapper(
                        bean.getMetaObject().getMetaClass().getId(),
                        bean.getMetaObject().getId()),
                    cachedBean);
            }
        } else {
            final int a = 0;
            // System.out.println("from cache");
        }

        return cachedBean;
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
    public static CidsBean cloneCidsBean(final CidsBean bean) throws Exception {
        if (bean == null) {
            return null;
        }
        final CidsBean clone = CidsBean.createNewCidsBeanFromTableName(
                WRRLUtil.DOMAIN_NAME,
                bean.getMetaObject().getMetaClass().getTableName());

        for (final String propName : bean.getPropertyNames()) {
            final Object o = bean.getProperty(propName);

            if (o instanceof CidsBean) {
                clone.setProperty(propName, (CidsBean)o);
            } else if (o instanceof Collection) {
                final List<CidsBean> list = (List<CidsBean>)o;
                final List<CidsBean> newList = clone.getBeanCollectionProperty(propName);

                for (final CidsBean tmpBean : list) {
                    newList.add(tmpBean);
                }
            } else if (o instanceof Geometry) {
                clone.setProperty(propName, ((Geometry)o).clone());
            } else if (o instanceof Long) {
                clone.setProperty(propName, new Long(o.toString()));
            } else if (o instanceof Double) {
                clone.setProperty(propName, new Double(o.toString()));
            } else if (o instanceof Integer) {
                clone.setProperty(propName, new Integer(o.toString()));
            } else if (o instanceof Boolean) {
                clone.setProperty(propName, new Boolean(o.toString()));
            } else if (o instanceof String) {
                clone.setProperty(propName, o);
            } else {
//                if (o != null) {
//                    LOG.error("unknown property type: " + o.getClass().getName());
//                }
                clone.setProperty(propName, o);
            }
        }

        return clone;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CidsBeanWrapper {

        //~ Instance fields ----------------------------------------------------

        private int classId;
        private int objectId;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CidsBeanWrapper object.
         *
         * @param  classId   DOCUMENT ME!
         * @param  objectId  DOCUMENT ME!
         */
        public CidsBeanWrapper(final int classId, final int objectId) {
            this.classId = classId;
            this.objectId = objectId;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof CidsBeanWrapper) {
                final CidsBeanWrapper other = (CidsBeanWrapper)obj;

                return (classId == other.classId) && (objectId == other.objectId);
            }

            return false;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = (19 * hash) + this.classId;
            hash = (19 * hash) + this.objectId;
            return hash;
        }
    }
}
