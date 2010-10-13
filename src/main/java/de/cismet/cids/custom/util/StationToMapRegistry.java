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
    private HashMap<String, LinearReferencedLineFeature> LinearRefenrecedLineReg = new HashMap<String, LinearReferencedLineFeature>();
    private HashMap<Integer, PureNewFeature> routeReg = new HashMap<Integer, PureNewFeature>();
    
    private HashMap<Integer, Integer> stationCounterMap = new HashMap<Integer, Integer>();
    private HashMap<String, Integer> LinearReferencedLineCounterMap = new HashMap<String, Integer>();
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

    private String getHashKeyForLinRefLineFeature(String oblectTableName, int id) {
        return oblectTableName + " " + id;
    }

    public LinearReferencedLineFeature addLinearReferencedLineFeature(String oblectTableName, int id, LinearReferencedPointFeature from, LinearReferencedPointFeature to) {       
        String key = getHashKeyForLinRefLineFeature(oblectTableName, id);

        LOG.debug("add " + key);
        if (!LinearRefenrecedLineReg.containsKey(key)) {
            LinearReferencedLineFeature feature = new LinearReferencedLineFeature(from, to);
            LinearRefenrecedLineReg.put(key, feature);
            addToMap(feature);
        }
        
        incrementLinearReferencedLineCounter(key);
        return LinearRefenrecedLineReg.get(key);
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

    private int incrementLinearReferencedLineCounter(String key) {
        LOG.debug("increment counter for " + key);
        int counter = getLinearReferencedLineCounter(key) + 1;
        LOG.debug("new value = " + counter);
        LinearReferencedLineCounterMap.put(key,  counter);
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

    private int decrementLinearReferencedLineCounter(String key) {
        LOG.debug("decrement counter for " + key);
        int counter = getLinearReferencedLineCounter(key) - 1;
        LOG.debug("new value = " + counter);
        LinearReferencedLineCounterMap.put(key,  counter);
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

    private int getLinearReferencedLineCounter(String key) {
        LOG.debug("get counter for " + key);
        if (!LinearReferencedLineCounterMap.containsKey(key)) {
            LinearReferencedLineCounterMap.put(key, new Integer(0));
        }
        return ((Integer) LinearReferencedLineCounterMap.get(key));
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

    public void removeLinearReferencedLineFeature(String oblectTableName, int id) {
        LOG.debug("remove LinearReferencedLine with id = " + id);
        String key = getHashKeyForLinRefLineFeature(oblectTableName, id);
        if (decrementLinearReferencedLineCounter(key) <= 0) {
            if (LinearRefenrecedLineReg.containsKey(key)) {
                LinearReferencedLineFeature feature = LinearRefenrecedLineReg.remove(key);
                LOG.debug("remove LinearReferencedLine from map");
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
