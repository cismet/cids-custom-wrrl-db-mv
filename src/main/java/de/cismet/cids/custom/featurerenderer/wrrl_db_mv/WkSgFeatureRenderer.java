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

import de.cismet.cids.featurerenderer.CustomCidsFeatureRenderer;

import de.cismet.cismap.navigatorplugin.CidsFeature;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class WkSgFeatureRenderer extends CustomCidsFeatureRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final Color SEA_COLOR = new Color(101, 156, 239);

    //~ Methods ----------------------------------------------------------------

    @Override
    public Paint getLinePaint(final CidsFeature subFeature) {
        return SEA_COLOR;
    }

    @Override
    public Paint getFillingStyle() {
        return SEA_COLOR;
    }

    @Override
    public void assign() {
        // NOP
    }
}
