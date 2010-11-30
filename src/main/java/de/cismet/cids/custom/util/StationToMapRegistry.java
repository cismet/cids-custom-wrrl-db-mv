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
public class StationToMapRegistry implements LinearReferencingConstants {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StationToMapRegistry.class);

    private static HashMap<String, Feature>featureReg = new HashMap<String, Feature>();
    private static HashMap<String, Integer> counterMap = new HashMap<String, Integer>();
    private static HashMap<String, Integer> NEW_ID_COUNTER = new HashMap<String, Integer>();

    private static StationToMapRegistry instance = new StationToMapRegistry();

    private StationToMapRegistry() {
        // Singleton
    }

    public static StationToMapRegistry getInstance() {
        return instance;
    }

    public static int getNewId(String mcName) {
        if (NEW_ID_COUNTER.get(mcName) == null) {
            NEW_ID_COUNTER.put(mcName, 0);
        }
        int id = NEW_ID_COUNTER.get(mcName) - 1;
        NEW_ID_COUNTER.put(mcName, id);
        return id;
    }

    private static String getHashKeyForLinRefLineFeature(String oblectTableName, int id) {
        return oblectTableName + ":" + id;
    }

    // >> ADD FEATURE

    private Feature addFeature(String key, int id, Feature feature) {
        String hash = getHashKeyForLinRefLineFeature(key, id);
        LOG.debug("add " + hash);
        if (!featureReg.containsKey(hash)) {
            featureReg.put(hash, feature);
            addFeatureToMap(feature);
        }

        incrementCounter(hash);
        return featureReg.get(hash);
    }

    public LinearReferencedPointFeature addStationFeature(int id, double value, Geometry geometry) {
        return (LinearReferencedPointFeature) addFeature(MC_STATION, id, new LinearReferencedPointFeature(value, geometry));
    }

    public PureNewFeature addRouteFeature(int id, Geometry geometry) {
        return (RouteFeature) addFeature(MC_ROUTE, id, new RouteFeature(geometry));
    }

    public LinearReferencedLineFeature addLinearReferencedLineFeature(String key, int id, LinearReferencedPointFeature from, LinearReferencedPointFeature to) {
        return (LinearReferencedLineFeature) addFeature(key, id, new LinearReferencedLineFeature(from, to));
    }

    // ADD FEATURE <<

    // >> REMOVE FEATURE

    private void removeFeature(String key, int id) {
        String hash = getHashKeyForLinRefLineFeature(key, id);
        LOG.debug("remove " + hash);
        if (decrementCounter(hash) <= 0) {
            if (featureReg.containsKey(hash)) {
                Feature feature = featureReg.remove(hash);
                LOG.debug("remove station from map");
                removeFeatureFromMap(feature);
            }
        }
    }

    public void removeLinearReferencedLineFeature(String key, int id) {
        removeFeature(key, id);
    }

    public void removeStationFeature(int id) {
        removeFeature(MC_STATION, id);
    }

    public void removeRouteFeature(int id) {
        removeFeature(MC_ROUTE, id);
    }

    // REMOVE FEATURE <<

    // >> COUNTER

    private int incrementCounter(String hash) {
        LOG.debug("increment counter for " + hash);
        int counter = getCounter(hash) + 1;
        LOG.debug("new value = " + counter);
        counterMap.put(hash,  counter);
        return counter;
    }

    private int decrementCounter(String hash) {
        LOG.debug("decrement counter for " + hash);
        int counter = getCounter(hash) - 1;
        LOG.debug("new value = " + counter);
        counterMap.put(hash,  counter);
        return counter;
    }

    private int getCounter(String hash) {
        LOG.debug("get counter for " + hash);
        if (!counterMap.containsKey(hash)) {
            LOG.debug("counter not exists, creating new counter with 0 value");
            counterMap.put(hash, new Integer(0));
        }
        return ((Integer) counterMap.get(hash));
    }

    // COUNTER <<

    // >> TO MAP

    private static void addFeatureToMap(final Feature feature) {
        FeatureCollection featureCollection = CismapBroker.getInstance().getMappingComponent().getFeatureCollection();
        featureCollection.addFeature(feature);
        featureCollection.holdFeature(feature);
    }

    private static void removeFeatureFromMap(final Feature feature) {
        FeatureCollection featureCollection = CismapBroker.getInstance().getMappingComponent().getFeatureCollection();
        if (featureCollection.getAllFeatures().contains(feature)) {
            featureCollection.removeFeature(feature);
        }
    }

    // << TO MAP

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
