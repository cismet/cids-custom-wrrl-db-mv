/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 therter
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
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkFgGroupSearch extends CidsServerSearch {

    //~ Static fields/initializers ---------------------------------------------

// private static final String QUERY = "SELECT g.value, gr.value " + //NOI18N
// "FROM WK_GROUP_AGGR g, wk_group_aggr_groups f, " + //NOI18N
// "     (SELECT value, gro.id FROM WK_GROUP gro, wk_group_fgs f WHERE" + //NOI18N
// "         gro.wk_fgs = f.wk_group_reference AND f.wk_fg = %1$s) gr " + //NOI18N
// "WHERE g.wk_groups = f.wk_group_aggr_reference " + //NOI18N
// "      AND f.wk_group = gr.id;"; // NOI18N
    private static final String QUERY = "SELECT value, gro.id FROM WK_GROUP gro, wk_group_fgs f WHERE" // NOI18N
                + "         gro.wk_fgs = f.wk_group_reference AND f.wk_fg = %1$s;";                    // NOI18N

    private static final String GROUP_QUERY = "SELECT g.value "    // NOI18N
                + "FROM WK_GROUP_AGGR g, wk_group_aggr_groups f "  // NOI18N
                + "WHERE g.wk_groups = f.wk_group_aggr_reference " // NOI18N
                + "      AND f.wk_group = %1$s;";                  // NOI18N

    //~ Instance fields --------------------------------------------------------

    private String wkFgId;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WkkSearch object.
     *
     * @param  wkFgId  geometry DOCUMENT ME!
     */
    public WkFgGroupSearch(final String wkFgId) {
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

                if ((lists.size() > 0) && (lists.get(0).size() > 0)) {
                    final String groupName = lists.get(0).get(0).toString();
                    String aggrGroupName = null;
                    query = String.format(GROUP_QUERY, lists.get(0).get(1).toString());

                    if (getLog().isDebugEnabled()) {
                        getLog().debug("query: " + query); // NOI18N
                    }

                    lists = ms.performCustomSearch(query);
                    if ((lists.size() > 0) && (lists.get(0).size() > 0)) {
                        aggrGroupName = lists.get(0).get(0).toString();
                    }

                    lists = new ArrayList<ArrayList>();
                    lists.add(new ArrayList<String>());
                    lists.get(0).add(groupName);
                    lists.get(0).add(aggrGroupName);

                    return lists;
                } else {
                    return null;
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
