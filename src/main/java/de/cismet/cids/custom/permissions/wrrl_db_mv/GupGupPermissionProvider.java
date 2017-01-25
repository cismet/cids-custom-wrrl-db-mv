/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.permissions.wrrl_db_mv;

import Sirius.server.newuser.User;

import de.cismet.cids.dynamics.AbstractCustomBeanPermissionProvider;
import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupGupPermissionProvider extends AbstractCustomBeanPermissionProvider {

    //~ Methods ----------------------------------------------------------------

    @Override
    public boolean getCustomWritePermissionDecisionforUser(final User u) {
        return true;
    }

    @Override
    public boolean getCustomReadPermissionDecisionforUser(final User u) {
        return checkBearbeiter(u, cidsBean);
    }

    /**
     * Checks, if the given user is allowed to modify the given gepp. Geometry permissions will be ignored.
     *
     * @param   u         DOCUMENT ME!
     * @param   cidsBean  a gepp object
     *
     * @return  true, if the user is in the group GEPP-Bearbeiter and the given gepp can be modified by the given user
     *          or the user is not in the group GEPP-Bearbeiter
     */
    public static boolean checkBearbeiter(final User u, final CidsBean cidsBean) {
        if (u.getUserGroup().getName().equals("GEPP-Bearbeiter")) {
            if (cidsBean == null) {
                return false;
            }

            final String bearbeiter = (String)cidsBean.getProperty("bearbeiter");

            if (bearbeiter != null) {
                return u.getName().equals(bearbeiter);
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
