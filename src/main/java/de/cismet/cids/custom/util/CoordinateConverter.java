/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.util;

import com.vividsolutions.jts.geom.Point;

import org.jdesktop.beansbinding.Converter;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class CoordinateConverter extends Converter<CidsBean, String> {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   value  an instance of the class de.cismet.cids.dynamics.Geom is expected
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String convertForward(final CidsBean value) {
        final Object geo = value.getProperty("geo_field");

        if (geo instanceof Point) {
            final Point point = (Point)geo;
            return ((int)point.getX()) + " / " + ((int)point.getY());
        } else {
            return "geomerty of type " + geo.getClass().getName() + " found";
        }
    }

    @Override
    public CidsBean convertReverse(final String value) {
        // this method should not be used
        return null;
    }
}
