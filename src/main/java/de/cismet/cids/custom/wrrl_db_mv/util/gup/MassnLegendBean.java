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
public class MassnLegendBean implements Comparable<MassnLegendBean> {

    //~ Instance fields --------------------------------------------------------

    private String massnahme;
    private String geraet;
    private String gewerk;
    private String einsatzvariante;
    private String verbleib;

    //~ Methods ----------------------------------------------------------------

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
     * @return  the geraet
     */
    public String getGeraet() {
        return geraet;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  geraet  the geraet to set
     */
    public void setGeraet(final String geraet) {
        this.geraet = geraet;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the gewerk
     */
    public String getGewerk() {
        return gewerk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  gewerk  the gewerk to set
     */
    public void setGewerk(final String gewerk) {
        this.gewerk = gewerk;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the einsatzvariante
     */
    public String getEinsatzvariante() {
        return einsatzvariante;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  einsatzvariante  the einsatzvariante to set
     */
    public void setEinsatzvariante(final String einsatzvariante) {
        this.einsatzvariante = einsatzvariante;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the verbleib
     */
    public String getVerbleib() {
        return verbleib;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  verbleib  the verbleib to set
     */
    public void setVerbleib(final String verbleib) {
        this.verbleib = verbleib;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MassnLegendBean) {
            return ((MassnLegendBean)obj).massnahme.equals(massnahme);
        }

        return false;
    }

    @Override
    public int compareTo(final MassnLegendBean o) {
        if (o != null) {
            return massnahme.compareTo(o.massnahme);
        } else {
            return 1;
        }
    }
}
