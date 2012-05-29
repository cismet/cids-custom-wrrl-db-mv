/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 thorsten
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.permissions.wrrl_db_mv;

import com.vividsolutions.jts.geom.Geometry;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkePermissionProvider extends BasicGeometryFromCidsObjectPermissionProvider {

    //~ Methods ----------------------------------------------------------------

    @Override
    public Geometry getGeometry() {
        final Object stat09 = cidsBean.getProperty("stat09.real_point.geo_field");
        final Object stat09Bis = cidsBean.getProperty("stat09bis.real_point.geo_field");
        final Geometry stat09Geom = (stat09 instanceof Geometry) ? (Geometry)stat09 : null;
        final Geometry stat09BisGeom = (stat09Bis instanceof Geometry) ? (Geometry)stat09Bis : null;

        if (stat09Geom != null) {
            if (stat09BisGeom != null) {
                return stat09Geom.union(stat09Geom);
            } else {
                return stat09Geom;
            }
        } else {
            return stat09BisGeom;
        }
    }

    @Override
    public String getKey() {
        return "fgsk";
    }
}
