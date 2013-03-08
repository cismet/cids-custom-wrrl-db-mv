/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.permissions.wrrl_db_mv;

import com.vividsolutions.jts.geom.Geometry;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class GupUnterhaltungserfordernisPermissionProvider extends BasicGeometryFromCidsObjectPermissionProvider {

    //~ Methods ----------------------------------------------------------------

    @Override
    public Geometry getGeometry() {
        return (Geometry)cidsBean.getProperty("linie.geom.geo_field");
    }

    @Override
    public String getKey() {
        return "GUP-Planer";
    }
}
