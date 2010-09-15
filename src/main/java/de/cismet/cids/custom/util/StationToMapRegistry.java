package de.cismet.cids.custom.util;

import com.vividsolutions.jts.geom.Geometry;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.DrawSelectionFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;
import java.util.HashMap;

/**
 *
 * @author jruiz
 */
public class StationToMapRegistry {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StationToMapRegistry.class);

    private HashMap<Integer, LinearReferencedPointFeature> stationReg = new HashMap<Integer, LinearReferencedPointFeature>();
    private HashMap<Integer, LinearReferencedLineFeature> wkTeilReg = new HashMap<Integer, LinearReferencedLineFeature>();
    private HashMap<Integer, PureNewFeature> routeReg = new HashMap<Integer, PureNewFeature>();
    
    private HashMap<Integer, Integer> stationCounterMap = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> wkTeilCounterMap = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> routeCounterMap = new HashMap<Integer, Integer>();

    private static StationToMapRegistry instance = new StationToMapRegistry();

    private StationToMapRegistry() {
        // Singleton
    }

    public static StationToMapRegistry getInstance() {
        return instance;
    }

    public LinearReferencedPointFeature addStationFeature(int id, double value, Geometry geometry) {
        LOG.debug("add station with id = " + id);
        if (!stationReg.containsKey(id)) {
            LinearReferencedPointFeature feature = new LinearReferencedPointFeature(value, geometry);
            stationReg.put(id, feature);
            addToMap(feature);
        }
        
        incrementStationCounter(id);        
        return stationReg.get(id);
    }

    public LinearReferencedLineFeature addWkTeilFeature(int id, LinearReferencedPointFeature from, LinearReferencedPointFeature to) {
        LOG.debug("add wk_teil with id = " + id);
        if (!wkTeilReg.containsKey(id)) {
            LinearReferencedLineFeature feature = new LinearReferencedLineFeature(from, to);
            wkTeilReg.put(id, feature);
            addToMap(feature);
        }
        
        incrementWkTeilCounter(id);
        return wkTeilReg.get(id);
    }

    public PureNewFeature addRouteFeature(int id, Geometry geometry) {
        LOG.debug("add route with id = " + id);
        if (!routeReg.containsKey(id)) {
            RouteFeature feature = new RouteFeature(geometry);
            routeReg.put(id, feature);
            addToMap(feature);
        }

        incrementRouteCounter(id);
        return routeReg.get(id);
    }

    private int incrementStationCounter(int id) {
        LOG.debug("increment counter for station with id = " + id);
        int counter = getStationCounter(id) + 1;
        LOG.debug("new value = " + counter);
        stationCounterMap.put(id,  counter);
        return counter;
    }

    private int incrementWkTeilCounter(int id) {
        LOG.debug("increment counter for wk_teil with id = " + id);
        int counter = getWkTeilCounter(id) + 1;
        LOG.debug("new value = " + counter);
        wkTeilCounterMap.put(id,  counter);
        return counter;
    }

    private int incrementRouteCounter(int id) {
        LOG.debug("increment counter for route with id = " + id);
        int counter = getRouteCounter(id) + 1;
        LOG.debug("new value = " + counter);
        routeCounterMap.put(id,  counter);
        return counter;
    }

    private int decrementStationCounter(int id) {
        LOG.debug("decrement counter for station with id = " + id);
        int counter = getStationCounter(id) - 1;
        LOG.debug("new value = " + counter);
        stationCounterMap.put(id,  counter);
        return counter;
    }

    private int decrementWkTeilCounter(int id) {
        LOG.debug("decrement counter for wk_teil with id = " + id);
        int counter = getWkTeilCounter(id) - 1;
        LOG.debug("new value = " + counter);
        wkTeilCounterMap.put(id,  counter);
        return counter;
    }

    private int decrementRouteCounter(int id) {
        LOG.debug("decrement counter for route with id = " + id);
        int counter = getRouteCounter(id) - 1;
        LOG.debug("new value = " + counter);
        routeCounterMap.put(id,  counter);
        return counter;
    }

    private int getStationCounter(int id) {
        LOG.debug("get counter for station with id = " + id);
        if (!stationCounterMap.containsKey(id)) {
            stationCounterMap.put(id, new Integer(0));
        }
        return ((Integer) stationCounterMap.get(id));
    }

    private int getWkTeilCounter(int id) {
        LOG.debug("get counter for wk_teil with id = " + id);
        if (!wkTeilCounterMap.containsKey(id)) {
            wkTeilCounterMap.put(id, new Integer(0));
        }
        return ((Integer) wkTeilCounterMap.get(id));
    }

    private int getRouteCounter(int id) {
        LOG.debug("get counter for route with id = " + id);
        if (!routeCounterMap.containsKey(id)) {
            routeCounterMap.put(id, new Integer(0));
        }
        return ((Integer) routeCounterMap.get(id));
    }

    public void removeStationFeature(int id) {
        LOG.debug("remove station with id = " + id);
        if (decrementStationCounter(id) <= 0) {
            if (stationReg.containsKey(id)) {
                LinearReferencedPointFeature feature = stationReg.remove(id);
                LOG.debug("remove station from map");
                removeFromMap(feature);
            }
        }
    }

    public void removeWkTeilFeature(int id) {
        LOG.debug("remove wk_teil with id = " + id);
        if (decrementWkTeilCounter(id) <= 0) {
            if (wkTeilReg.containsKey(id)) {
                LinearReferencedLineFeature feature = wkTeilReg.remove(id);
                LOG.debug("remove wk_teil from map");
                removeFromMap(feature);
            }
        }
    }

    public void removeRouteFeature(int id) {
        LOG.debug("remove route with id = " + id);
        if (decrementRouteCounter(id) <= 0) {
            if (routeReg.containsKey(id)) {
                PureNewFeature feature = routeReg.remove(id);
                LOG.debug("remove route from map");
                removeFromMap(feature);
            }
        }
    }

    private static void addToMap(final Feature feature) {
        FeatureCollection featureCollection = CismapBroker.getInstance().getMappingComponent().getFeatureCollection();
        featureCollection.addFeature(feature);
        featureCollection.holdFeature(feature);
    }

    private static void removeFromMap(final Feature feature) {
        FeatureCollection featureCollection = CismapBroker.getInstance().getMappingComponent().getFeatureCollection();
        if (featureCollection.getAllFeatures().contains(feature)) {
            featureCollection.removeFeature(feature);
        }
    }

    class RouteFeature extends PureNewFeature implements DrawSelectionFeature {

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
