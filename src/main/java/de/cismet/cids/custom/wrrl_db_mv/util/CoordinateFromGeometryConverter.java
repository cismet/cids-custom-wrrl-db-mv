/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import org.jdesktop.beansbinding.Converter;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class CoordinateFromGeometryConverter extends Converter<Geometry, String> {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   value  an instance of the class de.cismet.cids.dynamics.Geom is expected
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String convertForward(final Geometry value) {
        if (value instanceof Point) {
            final Point point = (Point)value;
            return ((int)point.getX()) + " / " + ((int)point.getY());
        } else {
            return "geometry of type " + value.getClass().getName() + " found";
        }
    }

    @Override
    public Geometry convertReverse(final String value) {
        // this method should not be used
        return null;
    }
}
