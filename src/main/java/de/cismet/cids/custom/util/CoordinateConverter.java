package de.cismet.cids.custom.util;


import com.vividsolutions.jts.geom.Point;
import de.cismet.cids.dynamics.CidsBean;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author therter
 */
public class CoordinateConverter extends Converter<CidsBean, String> {
    /**
     * @param value an instance of the class de.cismet.cids.dynamics.Geom is expected
     */
    @Override
    public String convertForward(CidsBean value) {
        Object geo = value.getProperty("geo_field");

        if (geo instanceof Point) {
            Point point = (Point)geo;
            return point.getX() + " / " + point.getY();
        } else {
            return "geomerty of type " + geo.getClass().getName() + " found";
        }
    }

    @Override
    public CidsBean convertReverse(String value) {
        //this method should not be used
        return null;
    }
}
