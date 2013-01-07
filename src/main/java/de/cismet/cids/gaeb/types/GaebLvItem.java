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
package de.cismet.cids.gaeb.types;

import java.math.BigDecimal;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GaebLvItem {

    //~ Instance fields --------------------------------------------------------

    private String id;
    private BigDecimal amount;
    private String measure;
    private String description;
    private String descriptionShort;
    private String oz;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  amount  the amount to set
     */
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the measure
     */
    public String getMeasure() {
        return measure;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  measure  the measure to set
     */
    public void setMeasure(final String measure) {
        this.measure = measure;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  description  the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the descriptionShort
     */
    public String getDescriptionShort() {
        return descriptionShort;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  descriptionShort  the descriptionShort to set
     */
    public void setDescriptionShort(final String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the id
     */
    public String getId() {
        return id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id  the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the oz
     */
    public String getOz() {
        return oz;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  oz  the oz to set
     */
    public void setOz(final String oz) {
        this.oz = oz;
    }
}
