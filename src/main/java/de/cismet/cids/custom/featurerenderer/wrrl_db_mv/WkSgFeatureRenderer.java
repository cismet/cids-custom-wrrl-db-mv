package de.cismet.cids.custom.featurerenderer.wrrl_db_mv;

import de.cismet.cids.featurerenderer.CustomCidsFeatureRenderer;
import de.cismet.cismap.navigatorplugin.CidsFeature;
import java.awt.Color;
import java.awt.Paint;

/**
 *
 * @author stefan
 */
public class WkSgFeatureRenderer extends CustomCidsFeatureRenderer {

    private static final Color SEA_COLOR = new Color(101, 156, 239);


    @Override
    public Paint getLinePaint(CidsFeature subFeature) {
        return SEA_COLOR;
    }

    @Override
    public Paint getFillingStyle() {
        return SEA_COLOR;
    }

    @Override
    public void assign() {
        //NOP
    }
}
