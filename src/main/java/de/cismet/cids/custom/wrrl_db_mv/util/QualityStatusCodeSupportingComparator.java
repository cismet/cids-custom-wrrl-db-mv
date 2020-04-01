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

import java.util.Comparator;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class QualityStatusCodeSupportingComparator implements Comparator<CidsBean> {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CustomElementComparator object.
     */
    public QualityStatusCodeSupportingComparator() {
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public int compare(final CidsBean o1, final CidsBean o2) {
        if ((o1 != null) && (o2 != null)) {
            final Integer value1 = (Integer)o1.getProperty("id");
            final Integer value2 = (Integer)o2.getProperty("id");

            if ((value1 != null) && (value2 != null)) {
                return value1 - value2;
            } else {
                if ((value1 == null) && (value2 == null)) {
                    return 0;
                } else {
                    return ((value1 == null) ? -1 : 1);
                }
            }
        } else {
            if ((o1 == null) && (o2 == null)) {
                return 0;
            } else {
                return ((o1 == null) ? -1 : 1);
            }
        }
    }
}
