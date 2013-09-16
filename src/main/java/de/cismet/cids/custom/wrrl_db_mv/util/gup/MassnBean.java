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

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnBean {

    //~ Instance fields --------------------------------------------------------

    private Integer nummer;
    private String stationierung;
    private String massnahme;
    private String menge;
    private String bemerkung;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the nummer
     */
    public Integer getNummer() {
        return nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  nummer  the nummer to set
     */
    public void setNummer(final Integer nummer) {
        this.nummer = nummer;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the stationierung
     */
    public String getStationierung() {
        return stationierung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stationierung  the stationierung to set
     */
    public void setStationierung(final String stationierung) {
        this.stationierung = stationierung;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the massnahme
     */
    public String getMassnahme() {
        return massnahme;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  massnahme  the massnahme to set
     */
    public void setMassnahme(final String massnahme) {
        this.massnahme = massnahme;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the menge
     */
    public String getMenge() {
        return menge;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  menge  the menge to set
     */
    public void setMenge(final String menge) {
        this.menge = menge;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the bemerkung
     */
    public String getBemerkung() {
        return bemerkung;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bemerkung  the bemerkung to set
     */
    public void setBemerkung(final String bemerkung) {
        this.bemerkung = bemerkung;
    }
}
