/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.util.linearreferencing;

import com.vividsolutions.jts.geom.Geometry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.DrawSelectionFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class FeatureRegistry implements LinearReferencingConstants {

    //~ Static fields/initializers ---------------------------------------------

    private static FeatureRegistry instance = new FeatureRegistry();

    //~ Instance fields --------------------------------------------------------

    private HashMap<CidsBean, Feature> featureReg = new HashMap<CidsBean, Feature>();
    private HashMap<Feature, CidsBean> cidsBeanReg = new HashMap<Feature, CidsBean>();
    private HashMap<CidsBean, Integer> counterMap = new HashMap<CidsBean, Integer>();
    private HashMap<CidsBean, Collection<FeatureRegistryListener>> listenerMap =
        new HashMap<CidsBean, Collection<FeatureRegistryListener>>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new FeatureRegistry object.
     */
    private FeatureRegistry() {
        // static
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static FeatureRegistry getInstance() {
        return instance;
    }

    /**
     * >> LISTENERS.
     *
     * @param   cidsBean  DOCUMENT ME!
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean addListener(final CidsBean cidsBean, final FeatureRegistryListener listener) {
        if (listenerMap.get(cidsBean) == null) {
            listenerMap.put(cidsBean, new CopyOnWriteArrayList<FeatureRegistryListener>());
        }
        return listenerMap.get(cidsBean).add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean removeListener(final CidsBean cidsBean, final FeatureRegistryListener listener) {
        final Collection<FeatureRegistryListener> listeners = listenerMap.get(cidsBean);
        if (listeners != null) {
            return listeners.remove(listener);
        } else {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    private void fireFeatureCountChanged(final CidsBean cidsBean) {
        final Collection<FeatureRegistryListener> listeners = listenerMap.get(cidsBean);
        if (listeners != null) {
            for (final FeatureRegistryListener listener : listeners) {
                listener.FeatureCountChanged();
            }
        }
    }

    /**
     * LISTENERS << >> ADD FEATURE.
     *
     * @param   cidsBean  DOCUMENT ME!
     * @param   feature   DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Feature addFeature(final CidsBean cidsBean, final Feature feature) {
        if (!featureReg.containsKey(cidsBean)) {
            featureReg.put(cidsBean, feature);
        }
        if (!cidsBeanReg.containsKey(feature)) {
            cidsBeanReg.put(feature, cidsBean);
        }

        if (getCounter(cidsBean) == 0) {
            addFeatureToMap(feature);
        }

        incrementCounter(cidsBean);
        return featureReg.get(cidsBean);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LinearReferencedPointFeature addStationFeature(final CidsBean cidsBean) {
        final double value = LinearReferencingHelper.getLinearValueFromStationBean(cidsBean);

        final Geometry routeGeometry = LinearReferencingHelper.getRouteGeometryFromStationBean(cidsBean);
        addRouteFeature(LinearReferencingHelper.getRouteBeanFromStationBean(cidsBean), routeGeometry);

        final LinearReferencedPointFeature linRefPoint = new LinearReferencedPointFeature(value, routeGeometry);

        return (LinearReferencedPointFeature)addFeature(cidsBean, linRefPoint);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     * @param   geometry  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private PureNewFeature addRouteFeature(final CidsBean cidsBean, final Geometry geometry) {
        return (RouteFeature)addFeature(cidsBean, new RouteFeature(geometry));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     * @param   from      DOCUMENT ME!
     * @param   to        DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LinearReferencedLineFeature addLinearReferencedLineFeature(final CidsBean cidsBean,
            final LinearReferencedPointFeature from,
            final LinearReferencedPointFeature to) {
        final LinearReferencedLineFeature linRefLine = new LinearReferencedLineFeature(from, to);

        return (LinearReferencedLineFeature)addFeature(cidsBean, linRefLine);
    }

    /**
     * ADD FEATURE << >> REMOVE FEATURE.
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Feature removeFeature(final CidsBean cidsBean) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("remove " + cidsBean, new CurrentStackTrace());
        }
        if (decrementCounter(cidsBean) <= 0) {
            if (featureReg.containsKey(cidsBean)) {
                final Feature feature = featureReg.remove(cidsBean);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("remove station from map");
                }
                removeFeatureFromMap(feature);
                return feature;
            }
        }
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LinearReferencedLineFeature removeLinearReferencedLineFeature(final CidsBean cidsBean) {
        return (LinearReferencedLineFeature)removeFeature(cidsBean);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LinearReferencedPointFeature removeStationFeature(final CidsBean cidsBean) {
        removeRouteFeature(LinearReferencingHelper.getRouteBeanFromStationBean(cidsBean));
        return (LinearReferencedPointFeature)removeFeature(cidsBean);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    private void removeRouteFeature(final CidsBean cidsBean) {
        removeFeature(cidsBean);
    }

    /**
     * REMOVE FEATURE << >> COUNTER.
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int incrementCounter(final CidsBean cidsBean) {
        final int counter = getCounter(cidsBean) + 1;
        counterMap.put(cidsBean, counter);
        logCounterStatus("after increment " + cidsBean);
        fireFeatureCountChanged(cidsBean);
        return counter;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int decrementCounter(final CidsBean cidsBean) {
        final int counter = getCounter(cidsBean) - 1;
        counterMap.put(cidsBean, counter);
        logCounterStatus("after decrement " + cidsBean);
        fireFeatureCountChanged(cidsBean);
        return counter;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getCounter(final CidsBean cidsBean) {
        if (!counterMap.containsKey(cidsBean)) {
            counterMap.put(cidsBean, new Integer(0));
        }
        return ((Integer)counterMap.get(cidsBean));
    }

    /**
     * COUNTER << >> TO MAP.
     *
     * @param  feature  DOCUMENT ME!
     */
    private void addFeatureToMap(final Feature feature) {
        final FeatureCollection featureCollection = CismapBroker.getInstance()
                    .getMappingComponent()
                    .getFeatureCollection();
        featureCollection.addFeature(feature);
        featureCollection.holdFeature(feature);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  feature  DOCUMENT ME!
     */
    private void removeFeatureFromMap(final Feature feature) {
        final FeatureCollection featureCollection = CismapBroker.getInstance()
                    .getMappingComponent()
                    .getFeatureCollection();
        if (featureCollection.getAllFeatures().contains(feature)) {
            featureCollection.removeFeature(feature);
        }
    }

    /**
     * << TO MAP.
     *
     * @param   feature  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CidsBean getCidsBean(final Feature feature) {
        return cidsBeanReg.get(feature);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Feature getFeature(final CidsBean cidsBean) {
        return featureReg.get(cidsBean);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  string  DOCUMENT ME!
     */
    private void logCounterStatus(final String string) {
        final StringBuilder sb = new StringBuilder("counterStatus ").append(string)
                    .append(":<br/>\n=============<br/>\n");
        for (final Entry<CidsBean, Integer> entry : counterMap.entrySet()) {
            sb.append(entry.getKey())
                    .append("-")
                    .append(entry.getKey().getMetaObject().getId())
                    .append(" => ")
                    .append(entry.getValue())
                    .append("<br/>\n");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(sb.toString());
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class RouteFeature extends PureNewFeature implements DrawSelectionFeature {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new RouteFeature object.
         *
         * @param  geomety  DOCUMENT ME!
         */
        public RouteFeature(final Geometry geomety) {
            super(geomety);
            setEditable(false);
            setCanBeSelected(false);
            setName("Route");
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public String getType() {
            return "Route";
        }

        @Override
        public boolean isDrawingSelection() {
            return false;
        }
    }
}
