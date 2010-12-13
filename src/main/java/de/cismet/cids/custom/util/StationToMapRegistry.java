package de.cismet.cids.custom.util;

import com.vividsolutions.jts.geom.Geometry;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.StationEditor;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.DrawSelectionFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;
import de.cismet.tools.CurrentStackTrace;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author jruiz
 */
public class StationToMapRegistry implements LinearReferencingConstants {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StationToMapRegistry.class);

    private HashMap<CidsBean, Feature> featureReg = new HashMap<CidsBean, Feature>();
    private HashMap<Feature, CidsBean> cidsBeanReg = new HashMap<Feature, CidsBean>();
    private HashMap<CidsBean, Integer> counterMap = new HashMap<CidsBean, Integer>();
    private HashMap<CidsBean, Collection<StationToMapRegistryListener>> listenerMap = new HashMap<CidsBean, Collection<StationToMapRegistryListener>>();

    private static final Color[] COLORS = new Color[] {
        new Color(41, 86, 178), new Color(101, 156, 239), new Color(125, 189, 0), new Color(220, 246, 0), new Color(255, 91, 0)
    };

    private int colorIndex = 0;

    private static StationToMapRegistry instance = new StationToMapRegistry();

    private StationToMapRegistry() {
        // static
    }

    private Color getNextColor() {
        colorIndex = (colorIndex + 1) % COLORS.length;
        return COLORS[colorIndex];
    }

    public static StationToMapRegistry getInstance() {
        return instance;
    }

    // >> LISTENERS

    public boolean addListener(CidsBean cidsBean, StationToMapRegistryListener listener) {
        if (listenerMap.get(cidsBean) == null) {
            listenerMap.put(cidsBean, new ArrayList<StationToMapRegistryListener>());
        }
        return listenerMap.get(cidsBean).add(listener);
    }

    public boolean removeListener(CidsBean cidsBean, StationToMapRegistryListener listener) {
        Collection<StationToMapRegistryListener> listeners = listenerMap.get(cidsBean);
        if (listeners != null) {
            return listeners.remove(listener);
        } else {
            return false;
        }
    }

    private void fireFeatureCountChanged(CidsBean cidsBean) {
        Collection<StationToMapRegistryListener> listeners = listenerMap.get(cidsBean);
        if (listeners != null) {
            for (StationToMapRegistryListener listener : listeners) {
                listener.FeatureCountChanged();
            }
        }
    }

    // LISTENERS <<

    // >> ADD FEATURE

    private Feature addFeature(CidsBean cidsBean, Feature feature) {
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

    public LinearReferencedPointFeature addStationFeature(CidsBean cidsBean) {
        double value = StationEditor.getLinearValue(cidsBean);

        Geometry routeGeometry = StationEditor.getRouteGeometry(cidsBean);
        addRouteFeature(StationEditor.getRouteBean(cidsBean), routeGeometry);

        LinearReferencedPointFeature linRefPoint = new LinearReferencedPointFeature(value, routeGeometry);

        return (LinearReferencedPointFeature) addFeature(cidsBean, linRefPoint);
    }

    private PureNewFeature addRouteFeature(CidsBean cidsBean, Geometry geometry) {
        return (RouteFeature) addFeature(cidsBean, new RouteFeature(geometry));
    }

    public LinearReferencedLineFeature addLinearReferencedLineFeature(CidsBean cidsBean, LinearReferencedPointFeature from, LinearReferencedPointFeature to) {
        LinearReferencedLineFeature linRefLine = new LinearReferencedLineFeature(from, to);

        LinearReferencedPointFeature linRefFromPoint = linRefLine.getStationFeature(FROM);
        LinearReferencedPointFeature linRefToPoint = linRefLine.getStationFeature(TO);

        linRefLine.setLinePaint(getNextColor());

        return (LinearReferencedLineFeature) addFeature(cidsBean, linRefLine);
    }

    // ADD FEATURE <<

    // >> REMOVE FEATURE

    private Feature removeFeature(CidsBean cidsBean) {
        LOG.debug("remove " + cidsBean, new CurrentStackTrace());
        if (decrementCounter(cidsBean) <= 0) {
            if (featureReg.containsKey(cidsBean)) {
                Feature feature = featureReg.remove(cidsBean);
                LOG.debug("remove station from map");
                removeFeatureFromMap(feature);
                return feature;
            }
        }
        return null;
    }

    public LinearReferencedLineFeature removeLinearReferencedLineFeature(CidsBean cidsBean) {
        return (LinearReferencedLineFeature) removeFeature(cidsBean);
    }

    public LinearReferencedPointFeature removeStationFeature(CidsBean cidsBean) {
        removeRouteFeature(StationEditor.getRouteBean(cidsBean));
        return (LinearReferencedPointFeature) removeFeature(cidsBean);
    }

    private void removeRouteFeature(CidsBean cidsBean) {
        removeFeature(cidsBean);
    }

    // REMOVE FEATURE <<

    // >> COUNTER

    private int incrementCounter(CidsBean cidsBean) {
        int counter = getCounter(cidsBean) + 1;
        counterMap.put(cidsBean,  counter);
        logCounterStatus("after increment " + cidsBean);
        fireFeatureCountChanged(cidsBean);
        return counter;
    }

    private int decrementCounter(CidsBean cidsBean) {
        int counter = getCounter(cidsBean) - 1;
        counterMap.put(cidsBean,  counter);
        logCounterStatus("after decrement " + cidsBean);
        fireFeatureCountChanged(cidsBean);
        return counter;
    }

    public int getCounter(CidsBean cidsBean) {
        if (!counterMap.containsKey(cidsBean)) {
            counterMap.put(cidsBean, new Integer(0));
        }
        return ((Integer) counterMap.get(cidsBean));
    }

    // COUNTER <<

    // >> TO MAP

    private void addFeatureToMap(final Feature feature) {
        FeatureCollection featureCollection = CismapBroker.getInstance().getMappingComponent().getFeatureCollection();
        featureCollection.addFeature(feature);
        featureCollection.holdFeature(feature);
    }

    private void removeFeatureFromMap(final Feature feature) {
        FeatureCollection featureCollection = CismapBroker.getInstance().getMappingComponent().getFeatureCollection();
        if (featureCollection.getAllFeatures().contains(feature)) {
            featureCollection.removeFeature(feature);
        }
    }

    // << TO MAP

    public CidsBean getCidsBean(Feature feature) {
        return cidsBeanReg.get(feature);
    }

    public Feature getFeature(CidsBean cidsBean) {
        return featureReg.get(cidsBean);
    }

    private void logCounterStatus(String string) {
        StringBuilder sb = new StringBuilder("counterStatus ").append(string).append(":<br/>\n=============<br/>\n");
        for (Entry<CidsBean, Integer> entry : counterMap.entrySet()) {
            sb.append(entry.getKey()).append("-").append(entry.getKey().getMetaObject().getId()).append(" => ").append(entry.getValue()).append("<br/>\n");
        }
        LOG.debug(sb.toString());
    }

    public class RouteFeature extends PureNewFeature implements DrawSelectionFeature {

        public RouteFeature(Geometry geomety) {
            super(geomety);
            setEditable(false);
            setCanBeSelected(false);
            setName("Route");
        }

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
