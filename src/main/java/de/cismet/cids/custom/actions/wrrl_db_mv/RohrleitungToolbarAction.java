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
package de.cismet.cids.custom.actions.wrrl_db_mv;

import org.openide.util.lookup.ServiceProvider;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.navigator.utils.AbstractNewObjectToolbarAction;
import de.cismet.cids.navigator.utils.CidsClientToolbarItem;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */

@ServiceProvider(service = CidsClientToolbarItem.class)
public class RohrleitungToolbarAction extends AbstractNewObjectToolbarAction {

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getSorterString() {
        return "G";
    }

    @Override
    public String getDomain() {
        return CidsBeanSupport.DOMAIN_NAME;
    }

    @Override
    public String getTableName() {
        return "rohrleitung";
    }

    @Override
    public String getTooltipString() {
        return "neue Rohrleitung anlegen";
    }
}
