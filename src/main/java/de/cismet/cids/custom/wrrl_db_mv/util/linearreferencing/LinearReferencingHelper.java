/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 jruiz
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
package de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing;

import Sirius.server.middleware.types.MetaClass;

import com.vividsolutions.jts.geom.Geometry;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class LinearReferencingHelper implements LinearReferencingConstants, LinearReferencingSingletonInstances {

    //~ Static fields/initializers ---------------------------------------------

    private static MetaClass MC_GEOM = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, CN_GEOM);
    private static MetaClass MC_STATIONLINIE = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            CN_STATIONLINE);
    private static MetaClass MC_STATION = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, CN_STATION);
    private static int NEW_STATION_ID = -1;
    private static int NEW_LINE_ID = -1;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new LinearReferencingHelper object.
     */
    private LinearReferencingHelper() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static double distanceOfStationGeomToRouteGeomFromStationBean(final CidsBean cidsBean) {
        final Geometry routeGeometry = getRouteGeometryFromStationBean(cidsBean);
        final Geometry pointGeometry = getPointGeometryFromStationBean(cidsBean);
        if (pointGeometry != null) {
            final double distance = pointGeometry.distance(routeGeometry);
            return distance;
        }
        return 0d;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   stationBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Geometry getRouteGeometryFromStationBean(final CidsBean stationBean) {
        return (Geometry)getRouteGeomBeanFromStationBean(stationBean).getProperty(PROP_GEOM_GEOFIELD);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   value        DOCUMENT ME!
     * @param   stationBean  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void setLinearValueToStationBean(final Double value, final CidsBean stationBean) throws Exception {
        if (stationBean == null) {
            return;
        }
        stationBean.setProperty(PROP_STATION_VALUE, value);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   stationBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Geometry getPointGeometryFromStationBean(final CidsBean stationBean) {
        return (Geometry)getPointGeomBeanFromStationBean(stationBean).getProperty(PROP_GEOM_GEOFIELD);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   point        DOCUMENT ME!
     * @param   stationBean  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void setPointGeometryToStationBean(final Geometry point, final CidsBean stationBean)
            throws Exception {
        getPointGeomBeanFromStationBean(stationBean).setProperty(PROP_GEOM_GEOFIELD, point);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   geometry     DOCUMENT ME!
     * @param   stationBean  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void setRouteGeometryToStationBean(final Geometry geometry, final CidsBean stationBean)
            throws Exception {
        getRouteGeomBeanFromStationBean(stationBean).setProperty(PROP_GEOM_GEOFIELD, geometry);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   stationBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Long getRouteGwkFromStationBean(final CidsBean stationBean) {
        return (Long)getRouteBeanFromStationBean(stationBean).getProperty(PROP_ROUTE_GWK);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   stationBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static CidsBean getPointGeomBeanFromStationBean(final CidsBean stationBean) {
        if (stationBean == null) {
            return null;
        }
        return (CidsBean)stationBean.getProperty(PROP_STATION_GEOM);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   stationBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean getRouteBeanFromStationBean(final CidsBean stationBean) {
        if (stationBean == null) {
            return null;
        }
        return (CidsBean)stationBean.getProperty(PROP_STATION_ROUTE);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   stationBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static CidsBean getRouteGeomBeanFromStationBean(final CidsBean stationBean) {
        return (CidsBean)getRouteBeanFromStationBean(stationBean).getProperty(PROP_ROUTE_GEOM);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   routeBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean createStationBeanFromRouteBean(final CidsBean routeBean) {
        return createStationBeanFromRouteBean(routeBean, 0d);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   routeBean  DOCUMENT ME!
     * @param   value      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean createStationBeanFromRouteBean(final CidsBean routeBean, final double value) {
        final CidsBean stationBean = MC_STATION.getEmptyInstance().getBean();
        final CidsBean geomBean = MC_GEOM.getEmptyInstance().getBean();

        try {
            stationBean.setProperty(PROP_STATION_ROUTE, routeBean);
            stationBean.setProperty(PROP_STATION_VALUE, value);
            stationBean.setProperty(PROP_STATION_GEOM, geomBean);

            stationBean.setProperty("id", NEW_STATION_ID);
            stationBean.getMetaObject().setID(NEW_STATION_ID);

            NEW_STATION_ID--;
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while filling bean", ex);
            }
        }
        return stationBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   routeBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean createLineBeanFromRouteBean(final CidsBean routeBean) {
        if (routeBean == null) {
            return null;
        }

        final CidsBean linieBean = MC_STATIONLINIE.getEmptyInstance().getBean();
        final CidsBean fromBean = createStationBeanFromRouteBean(routeBean);
        final CidsBean toBean = createStationBeanFromRouteBean(routeBean);
        final CidsBean geomBean = MC_GEOM.getEmptyInstance().getBean();

        try {
            toBean.setProperty(
                PROP_STATION_VALUE,
                ((Geometry)((CidsBean)routeBean.getProperty(PROP_ROUTE_GEOM)).getProperty(PROP_GEOM_GEOFIELD))
                            .getLength());

            linieBean.setProperty(PROP_STATIONLINIE_FROM, fromBean);
            linieBean.setProperty(PROP_STATIONLINIE_TO, toBean);
            linieBean.setProperty(PROP_STATIONLINIE_GEOM, geomBean);

            linieBean.setProperty("id", NEW_LINE_ID);
            linieBean.getMetaObject().setID(NEW_LINE_ID);

            NEW_LINE_ID--;
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while filling bean", ex);
            }
        }
        return linieBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   stationBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static double getLinearValueFromStationBean(final CidsBean stationBean) {
        if (stationBean == null) {
            return 0d;
        }
        return (Double)stationBean.getProperty(PROP_STATION_VALUE);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   lineBean  DOCUMENT ME!
     * @param   isFrom    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean getStationBeanFromLineBean(final CidsBean lineBean, final boolean isFrom) {
        if (lineBean == null) {
            return null;
        }
        final String stationField = (isFrom) ? PROP_STATIONLINIE_FROM : PROP_STATIONLINIE_TO;
        return (CidsBean)lineBean.getProperty(stationField);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   lineBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean getGeomBeanFromLineBean(final CidsBean lineBean) {
        if (lineBean == null) {
            return null;
        }
        return (CidsBean)lineBean.getProperty(PROP_STATIONLINIE_GEOM);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   line      DOCUMENT ME!
     * @param   lineBean  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void setGeometryToLineBean(final Geometry line, final CidsBean lineBean) throws Exception {
        final CidsBean geomBean = LinearReferencingHelper.getGeomBeanFromLineBean(lineBean);
        if (geomBean != null) {
            geomBean.setProperty(PROP_GEOM_GEOFIELD, line);
        }
    }
}
