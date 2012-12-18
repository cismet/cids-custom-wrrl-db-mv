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
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class VermeidungsgruppeMitGeom {

    //~ Instance fields --------------------------------------------------------

    private CidsBean vermeidungsgruppe;
    private CidsBean geschuetzteArt;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new VermeidungsgruppeMitGeom object.
     *
     * @param  vermeidungsgruppe  DOCUMENT ME!
     * @param  geschuetzteArt     DOCUMENT ME!
     */
    public VermeidungsgruppeMitGeom(final CidsBean vermeidungsgruppe, final CidsBean geschuetzteArt) {
        this.vermeidungsgruppe = vermeidungsgruppe;
        this.geschuetzteArt = geschuetzteArt;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the vermeidungsgruppe
     */
    public CidsBean getVermeidungsgruppe() {
        return vermeidungsgruppe;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  vermeidungsgruppe  the vermeidungsgruppe to set
     */
    public void setVermeidungsgruppe(final CidsBean vermeidungsgruppe) {
        this.vermeidungsgruppe = vermeidungsgruppe;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the geschuetzteArt
     */
    public CidsBean getGeschuetzteArt() {
        return geschuetzteArt;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  geschuetzteArt  the geschuetzteArt to set
     */
    public void setGeschuetzteArt(final CidsBean geschuetzteArt) {
        this.geschuetzteArt = geschuetzteArt;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CidsBean getStationLinie() {
        return (CidsBean)geschuetzteArt.getProperty("linie");
    }
}
