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
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaObject;

import java.util.Objects;

import de.cismet.cids.editors.FieldStateDecider;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class VermeidungsgruppenDecider implements FieldStateDecider {

    //~ Instance fields --------------------------------------------------------

    private final boolean ausfuehrungszeitpunkt;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new SubTypeDecider object.
     *
     * @param  ausfuehrungszeitpunkt  DOCUMENT ME!
     */
    public VermeidungsgruppenDecider(final boolean ausfuehrungszeitpunkt) {
        this.ausfuehrungszeitpunkt = ausfuehrungszeitpunkt;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public boolean isCheckboxForClassActive(final MetaObject mo) {
        return Objects.equals(this.ausfuehrungszeitpunkt, (Boolean)mo.getBean().getProperty("ausfuehrungszeitpunkt"));
    }
}
