/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.featurerenderer.wrrl_db_mv;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;

import javax.swing.JComponent;

import de.cismet.cids.featurerenderer.CustomCidsFeatureRenderer;

import de.cismet.cismap.commons.Refreshable;
import de.cismet.cismap.commons.gui.piccolo.CustomFixedWidthStroke;
import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;

import de.cismet.cismap.navigatorplugin.CidsFeature;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class WkFgFeatureRenderer extends CustomCidsFeatureRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final Color RIVER_COLOR = new Color(101, 156, 239);
    private static final Stroke RIVER_STROKE = new CustomFixedWidthStroke(5f);

    //~ Methods ----------------------------------------------------------------

    @Override
    public JComponent getInfoComponent(final Refreshable refresh, final CidsFeature subFeature) {
        return null;
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
    public FeatureAnnotationSymbol getPointSymbol(final CidsFeature subFeature) {
        return null;
    }

    @Override
    public float getTransparency(final CidsFeature subFeature) {
        return 0.6f;
    }

    @Override
    public Paint getFillingStyle() {
        return getFillingStyle(null);
    }

    @Override
    public JComponent getInfoComponent(final Refreshable refresh) {
        return getInfoComponent(refresh, null);
    }

    @Override
    public Paint getLinePaint() {
        return getLinePaint(null);
    }

    @Override
    public Stroke getLineStyle() {
        return getLineStyle(null);
    }

    @Override
    public FeatureAnnotationSymbol getPointSymbol() {
        return getPointSymbol(null);
    }

    @Override
    public float getTransparency() {
        return getTransparency(null);
    }

    @Override
    public void assign() {
        // NOP
    }
}
