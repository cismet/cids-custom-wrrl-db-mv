/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 jruiz
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
package de.cismet.cids.custom.wrrl_db_mv.util;

import java.util.Collection;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class MapUtil {

    //~ Static fields/initializers ---------------------------------------------

    private static final MappingComponent MAPPING_COMPONENT = CismapBroker.getInstance().getMappingComponent();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  collection  DOCUMENT ME!
     */
    public static void zoomToFeatureCollection(final Collection<Feature> collection) {
        zoomToFeatureCollection(collection, true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  collection   DOCUMENT ME!
     * @param  withHistory  DOCUMENT ME!
     */
    public static void zoomToFeatureCollection(final Collection<Feature> collection, final boolean withHistory) {
        if (!MAPPING_COMPONENT.isFixedMapExtent()) {
            if (!collection.isEmpty()) {
                MAPPING_COMPONENT.zoomToAFeatureCollection(
                    collection,
                    withHistory,
                    MAPPING_COMPONENT.isFixedMapScale());
            }
        }
    }
}
