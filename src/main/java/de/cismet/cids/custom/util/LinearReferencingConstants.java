/*
 *  Copyright (C) 2010 jruiz
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

package de.cismet.cids.custom.util;

/**
 *
 * @author jruiz
 */
public interface LinearReferencingConstants {

    static final String MC_STATION = "station";    // NOI18N
    static final String MC_ROUTE = "route";    // NOI18N
    static final String MC_GEOM = "geom";    // NOI18N

    static final String PROP_ID = "id";    // NOI18N

    static final String PROP_STATION_VALUE = "wert";    // NOI18N
    static final String PROP_STATION_ROUTE = "route";    // NOI18N
    static final String PROP_STATION_GEOM = "real_point";    // NOI18N

    static final String PROP_ROUTE_GWK = "gwk";    // NOI18N
    static final String PROP_ROUTE_GEOM = "geom";    // NOI18N
    
    static final String PROP_GEOM_GEOFIELD = "geo_field";    // NOI18N

    static final boolean FROM = true;
    static final boolean TO = false;

}
