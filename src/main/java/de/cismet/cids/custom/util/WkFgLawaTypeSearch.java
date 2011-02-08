/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 therter
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

import Sirius.server.middleware.interfaces.domainserver.MetaService;
import Sirius.server.search.CidsServerSearch;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Searchs for the LAWA types which are contained within a WK-FG.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkFgLawaTypeSearch extends CidsServerSearch {

    //~ Static fields/initializers ---------------------------------------------

    private static final String QUERY = "select "                                                                            // NOI18N
                + "  nr.code, "                                                                                              // NOI18N
                + "  nr.description, "                                                                                       // NOI18N
                + "  wk_fg_total_length.length as wk_fg_length, "                                                            // NOI18N
                + "  length(st_intersection(wk_fg_total_geom, lg.geo_field)) as intersection_length "                        // NOI18N
                + "from "                                                                                                    // NOI18N
                + "  wk_fg fg,"                                                                                              // NOI18N
                + "  lawa l,"                                                                                                // NOI18N
                + "  geom lg,"                                                                                               // NOI18N
                + "  la_lawa_nr nr,"                                                                                         // NOI18N
                + "  (select sum(length(geo_field)) as length, geomunion(geo_field) as wk_fg_total_geom, "                   // NOI18N
                + "      extent(geo_field) as wk_fg_total_geom_ext from wk_fg fg, wk_fg_teile teile, wk_teil teil, geom g "  // NOI18N
                + "   where fg.id = %1$s AND teil.id = teile.teil AND teile.wk_fg_reference = fg.id AND "                    // NOI18N
                + "         g.id = teil.real_geom) wk_fg_total_length "                                                      // NOI18N
                + "where fg.id = %1$s AND l.real_geom = lg.id AND l.lawa_nr = nr.id AND "                                    // NOI18N
                + "      fast_intersects(lg.geo_field, wk_fg_total_geom_ext, wk_fg_total_geom) "                             // NOI18N
                + "UNION "                                                                                                   // NOI18N
                + "select -1, 'kein Typ', "                                                                                  // NOI18N
                + "  coalesce( st_numGeometries( st_LineMerge(st_Difference(wk_fg_total_geom, st_union(lg.geo_field)))), 1)" // NOI18N
                + "  , st_length(st_LineMerge(st_Difference(wk_fg_total_geom, st_union(lg.geo_field)))) "                    // NOI18N
                + "from wk_fg fg, lawa l, geom lg, la_lawa_nr nr, "                                                          // NOI18N
                + "  (select sum(length(geo_field)) as length, geomunion(geo_field) as wk_fg_total_geom, "                   // NOI18N
                + "    extent(geo_field) as wk_fg_total_geom_ext "                                                           // NOI18N
                + "   from wk_fg fg, wk_fg_teile teile, wk_teil teil, geom g "                                               // NOI18N
                + "   where fg.id = %1$s AND teil.id = teile.teil AND teile.wk_fg_reference = fg.id AND "                    // NOI18N
                + "         g.id = teil.real_geom) wk_fg_total_length "                                                      // NOI18N
                + "where fg.id = %1$s AND l.real_geom = lg.id AND l.lawa_nr = nr.id AND "                                    // NOI18N
                + "  fast_intersects(lg.geo_field, wk_fg_total_geom_ext, wk_fg_total_geom) "                                 // NOI18N
                + "group by wk_fg_total_geom;";                                                                              // NOI18N

    private static final String QUERY_WITHOUT_LAWA =
        " select -1, 'kein Typ', sum(length(geo_field)) as wk_fg_length, sum(length(geo_field)) as intersection_length" // NOI18N
                + " from wk_fg fg, wk_fg_teile teile, wk_teil teil, geom g"                                             // NOI18N
                + " where fg.id = %1$s AND teil.id = teile.teil AND teile.wk_fg_reference = fg.id AND"                  // NOI18N
                + " g.id = teil.real_geom";                                                                             // NOI18N

    //~ Instance fields --------------------------------------------------------

    private String wkFgId;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new LawaTypeNeighbourSearch object.
     *
     * @param  wkFgId  lawaTypeId DOCUMENT ME!
     */
    public WkFgLawaTypeSearch(final String wkFgId) {
        this.wkFgId = wkFgId;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Collection performServerSearch() {
        final MetaService ms = (MetaService)getActiveLoaclServers().get(CidsBeanSupport.DOMAIN_NAME);

        if (ms != null) {
            try {
                String query = String.format(QUERY, wkFgId);
                if (getLog().isDebugEnabled()) {
                    getLog().debug("query: " + query); // NOI18N
                }
                ArrayList<ArrayList> lists = ms.performCustomSearch(query);

                if (!lists.isEmpty()) {
                    return lists;
                } else {
                    query = String.format(QUERY_WITHOUT_LAWA, wkFgId);
                    if (getLog().isDebugEnabled()) {
                        getLog().debug("query: " + query); // NOI18N
                    }
                    lists = ms.performCustomSearch(query);

                    return lists;
                }
            } catch (RemoteException ex) {
                getLog().error(ex.getMessage(), ex);
            }
        } else {
            getLog().error("active local server not found"); // NOI18N
        }

        return null;
    }
}
