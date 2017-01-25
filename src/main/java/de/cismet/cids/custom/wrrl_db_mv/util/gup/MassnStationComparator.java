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

import java.util.Comparator;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnStationComparator implements Comparator<CidsBean> {

    //~ Methods ----------------------------------------------------------------

    @Override
    public int compare(final CidsBean o1, final CidsBean o2) {
        final Double von1 = (Double)o1.getProperty("linie.von.wert");
        final Double von2 = (Double)o2.getProperty("linie.von.wert");
        final Double bis1 = (Double)o1.getProperty("linie.bis.wert");
        final Double bis2 = (Double)o2.getProperty("linie.bis.wert");

        if ((von1 != null) && (von2 != null)) {
            if (von1.equals(von2)) {
                if ((bis1 != null) && (bis2 != null)) {
                    if (von1.equals(von2)) {
                        return ((Integer)o1.getProperty("id")).compareTo((Integer)o2.getProperty("id"));
                    } else {
                        return bis1.compareTo(bis2);
                    }
                } else {
                    if ((bis1 == null) && (bis2 == null)) {
                        return 0;
                    } else {
                        return ((bis1 == null) ? -1 : 1);
                    }
                }
            } else {
                if (von1.equals(von2)) {
                    return ((Integer)o1.getProperty("id")).compareTo((Integer)o2.getProperty("id"));
                } else {
                    return von1.compareTo(von2);
                }
            }
        } else {
            if ((von1 == null) && (von2 == null)) {
                return 0;
            } else {
                return ((von1 == null) ? -1 : 1);
            }
        }
    }
}
