package de.cismet.cids.custom.featurerenderer.wrrl_db_mv;

import de.cismet.cids.featurerenderer.CustomCidsFeatureRenderer;
import java.awt.Paint;
import java.awt.Stroke;
import de.cismet.cismap.commons.gui.piccolo.CustomFixedWidthStroke;
import de.cismet.cismap.navigatorplugin.CidsFeature;
import java.awt.Color;



/**
 *
 * @author therter
 */
public class LawaFeatureRenderer extends CustomCidsFeatureRenderer {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LawaFeatureRenderer.class);
    private static final Stroke STROKE_SMALL= new CustomFixedWidthStroke(3f);
    private static final Stroke STROKE_BIG = new CustomFixedWidthStroke(4f);

    
    @Override
    public Paint getLinePaint(CidsFeature subFeature) {
        int code = getLawaNr(subFeature);
        switch (code) {
            case 11:
                return new Color(0, 255, 0);
            case 12:
                return new Color(1, 100, 0);
            case 14:
                return new Color(255, 255, 00);
            case 15:
                return new Color(220, 206, 0);
            case 16:
                return new Color(254, 90, 0);
            case 17:
                return new Color(220, 0, 0);
            case 20:
                return new Color(0, 255, 255);
            case 21:
                return new Color(100, 0, 0);
            case 23:
                return new Color(0, 37, 100);
            default:
                return new Color(0, 0, 0);
        }
    }

    @Override
    public Stroke getLineStyle(CidsFeature subFeature) {
        int code = getLawaNr(subFeature);

        if (code == 11 || code == 14 || code == 16) {
            return STROKE_SMALL;
        } else {
            return STROKE_BIG;
        }
    }


    private int getLawaNr(CidsFeature subFeature) {
        Object lawaNr = null;
        int code = 0;

        try {
            lawaNr = metaObject.getBean().getProperty("lawa_nr.code");
        } catch (NullPointerException e) {
            log.error("Cannot retrieve field lawa_nr.code from lawa type object.", e);
        }

        if (lawaNr != null && lawaNr instanceof Integer) {
            code = (Integer)lawaNr;
        } else {
            log.error("Field lawa_nr.code from lawa type object is no integer.");
        }

        return code;
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
