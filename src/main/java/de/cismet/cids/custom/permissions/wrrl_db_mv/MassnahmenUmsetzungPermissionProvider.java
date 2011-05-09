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
public class MassnahmenUmsetzungPermissionProvider extends BasicGeometryFomFilePermissionProvider {

    //~ Methods ----------------------------------------------------------------

    @Override
    public Geometry getGeometry() {
        final Object additional = cidsBean.getProperty("additional_geom.geo_field");
        final Object statLine = cidsBean.getProperty("linie.geom.geo_field");
        final Geometry additionalGeom = (additional instanceof Geometry) ? (Geometry)additional : null;
        final Geometry statLineGeom = (statLine instanceof Geometry) ? (Geometry)statLine : null;

        if (additionalGeom != null) {
            if (statLineGeom != null) {
                return additionalGeom.union(additionalGeom);
            } else {
                return additionalGeom;
            }
        } else {
            return statLineGeom;
        }
    }
}
