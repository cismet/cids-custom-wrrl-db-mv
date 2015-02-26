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
package de.cismet.cids.custom.wrrl_db_mv.util;

import de.cismet.cids.dynamics.CidsBean;

/**
 * CisdsBean wrapper class, that makes the baen comparable.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ComparableCidsBean implements Comparable<ComparableCidsBean> {

    //~ Instance fields --------------------------------------------------------

    private CidsBean bean;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ComparableCidsBean object.
     *
     * @param  bean  DOCUMENT ME!
     */
    public ComparableCidsBean(final CidsBean bean) {
        this.bean = bean;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public int compareTo(final ComparableCidsBean o) {
        return ((Integer)getBean().getProperty("id")).compareTo((Integer)o.getBean().getProperty("id"));
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof ComparableCidsBean) {
            return compareTo((ComparableCidsBean)obj) == 0;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = (47 * hash) + ((this.bean != null) ? this.bean.hashCode() : 0);
        return hash;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the bean
     */
    public CidsBean getBean() {
        return bean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean  the bean to set
     */
    public void setBean(final CidsBean bean) {
        this.bean = bean;
    }
}
