/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.actions.wrrl_db_mv;

import org.openide.util.lookup.ServiceProvider;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.navigator.utils.AbstractNewObjectToolbarAction;
import de.cismet.cids.navigator.utils.CidsClientToolbarItem;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = CidsClientToolbarItem.class)
public class GupUnterhaltungserfordernisRouteToolbarAction extends AbstractNewObjectToolbarAction {

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getSorterString() {
        return "R";
    }

    @Override
    public String getDomain() {
        return WRRLUtil.DOMAIN_NAME;
    }

    @Override
    public String getTableName() {
        return "gup_unterhaltungserfordernis_route";
    }

    @Override
    public String getTooltipString() {
        return "neue Unterhaltungserfordernisse anlegen";
    }
}
