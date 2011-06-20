/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.featurerenderer.wrrl_db_mv;

import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.Geometry;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;

import java.util.ArrayList;
import java.util.List;

import de.cismet.cids.custom.util.linearreferencing.LinearReferencingConstants;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.featurerenderer.CustomCidsFeatureRenderer;

import de.cismet.cismap.commons.gui.piccolo.CustomFixedWidthStroke;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.CidsFeature;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnahmenFeatureRenderer extends CustomCidsFeatureRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final Color RIVER_COLOR = new Color(101, 156, 239);
    private static final Stroke RIVER_STROKE = new CustomFixedWidthStroke(5f);
    private static List<LinearReferencedLineFeature> createdFeatures = new ArrayList<LinearReferencedLineFeature>();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    public static synchronized void clear() {
        for (final LinearReferencedLineFeature tmp : createdFeatures) {
            CismapBroker.getInstance().getMappingComponent().getFeatureCollection().removeFeature(tmp);
        }
        createdFeatures.clear();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  f  DOCUMENT ME!
     */
    private static synchronized void add(final LinearReferencedLineFeature f) {
        CismapBroker.getInstance().getMappingComponent().getFeatureCollection().addFeature(f);
        createdFeatures.add(f);
    }

    @Override
    public Paint getLinePaint(final CidsFeature subFeature) {
        return RIVER_COLOR;
    }

    @Override
    public Stroke getLineStyle(final CidsFeature subFeature) {
        return RIVER_STROKE;
    }

    @Override
    public void assign() {
        final CidsBean lineBean = (CidsBean)cidsBean.getProperty("linie");
        CidsBean vonBean = null;
        CidsBean bisBean = null;

        if (lineBean != null) {
            vonBean = (CidsBean)lineBean.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_FROM);
            bisBean = (CidsBean)lineBean.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_TO);
        }

        if ((vonBean != null) && (bisBean != null)) {
            final CidsBean route = (CidsBean)vonBean.getProperty(LinearReferencingConstants.PROP_STATION_ROUTE);
            final Geometry routeGeometry = (Geometry)
                ((CidsBean)route.getProperty(LinearReferencingConstants.PROP_ROUTE_GEOM)).getProperty(
                    LinearReferencingConstants.PROP_GEOM_GEOFIELD);

            final LinearReferencedPointFeature vonFeature = new LinearReferencedPointFeature((Double)
                    vonBean.getProperty(LinearReferencingConstants.PROP_STATION_VALUE),
                    routeGeometry);
            final LinearReferencedPointFeature bisFeature = new LinearReferencedPointFeature((Double)
                    bisBean.getProperty(LinearReferencingConstants.PROP_STATION_VALUE),
                    routeGeometry);
            final LinearReferencedLineFeature f = new LinearReferencedLineFeature(vonFeature, bisFeature);
            if (!CismapBroker.getInstance().getMappingComponent().getFeatureCollection().getAllFeatures().contains(f)) {
                add(f);
            }
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class LineStatFeature extends CidsFeature {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new LineStatFeature object.
         *
         * @param  mo  DOCUMENT ME!
         */
        public LineStatFeature(final MetaObject mo) {
            super(mo);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public String getName() {
            return "Stationierte Linie";
        }

        @Override
        public String getType() {
            return "Stationierte Linie";
        }
    }
}
