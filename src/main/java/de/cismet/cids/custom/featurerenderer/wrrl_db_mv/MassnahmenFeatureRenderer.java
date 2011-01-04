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

import de.cismet.cids.custom.util.StationToMapRegistry;

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

    //~ Methods ----------------------------------------------------------------

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
        final CidsBean stat_von = (CidsBean)cidsBean.getProperty("stat_von");
        final CidsBean stat_bis = (CidsBean)cidsBean.getProperty("stat_bis");

        if ((stat_von != null) && (stat_bis != null)) {
            final CidsBean route = (CidsBean)stat_von.getProperty("route");
            final Geometry routeGeometry = (Geometry)((CidsBean)route.getProperty("geom")).getProperty("geo_field");

            final LinearReferencedPointFeature stat_von_feature = new LinearReferencedPointFeature((Double)
                    stat_von.getProperty("wert"),
                    routeGeometry);
            final LinearReferencedPointFeature stat_bis_feature = new LinearReferencedPointFeature((Double)
                    stat_bis.getProperty("wert"),
                    routeGeometry);
            final LinearReferencedLineFeature f = new LinearReferencedLineFeature(stat_von_feature, stat_bis_feature);
            if (!CismapBroker.getInstance().getMappingComponent().getFeatureCollection().getAllFeatures().contains(f)) {
                CismapBroker.getInstance().getMappingComponent().getFeatureCollection().addFeature(f);
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
