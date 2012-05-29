/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.featurerenderer.wrrl_db_mv;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;

import de.cismet.cids.featurerenderer.CustomCidsFeatureRenderer;

import de.cismet.cismap.commons.gui.piccolo.CustomFixedWidthStroke;

import de.cismet.cismap.navigatorplugin.CidsFeature;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupGewaesserabschnittFeatureRenderer extends CustomCidsFeatureRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final Stroke STROKE_SMALL = new CustomFixedWidthStroke(3f);

    //~ Methods ----------------------------------------------------------------

    @Override
    public Paint getLinePaint(final CidsFeature subFeature) {
        return Color.BLUE;
    }

    @Override
    public Stroke getLineStyle(final CidsFeature subFeature) {
        return STROKE_SMALL;
    }

    @Override
    public Stroke getLineStyle() {
        return getLineStyle(null);
    }

    @Override
    public Paint getLinePaint() {
        return getLinePaint(null);
    }

    @Override
    public void assign() {
    }
}
