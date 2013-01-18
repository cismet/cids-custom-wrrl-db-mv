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
package de.cismet.cids.custom.wrrl_db_mv.util;

import de.cismet.cids.dynamics.CidsBean;
import java.util.Comparator;

/**
 * DOCUMENT ME!
 *
 * @author   gbaatz
 * @version  $Revision$, $Date$
 */
public class TeileComparator implements Comparator<CidsBean> {

    //~ Methods ----------------------------------------------------------------

    @Override
    public int compare(final CidsBean o1, final CidsBean o2) {
        final CidsBean von1 = (CidsBean)o1.getProperty("linie.von");
        final CidsBean von2 = (CidsBean)o2.getProperty("linie.von");

        if ((von1 != null) && (von2 != null)) {
            final Long gwk1 = (Long)von1.getProperty("route.gwk");
            final Long gwk2 = (Long)von2.getProperty("route.gwk");

            if ((gwk1 != null) && (gwk2 != null)) {
                if (!gwk1.equals(gwk2)) {
                    return gwk1.compareTo(gwk2);
                } else {
                    final Double vonWert1 = (Double)von1.getProperty("wert");
                    final Double vonWert2 = (Double)von2.getProperty("wert");
                    if ((vonWert1 != null) && (vonWert2 != null)) {
                        return vonWert1.compareTo(vonWert2);
                    }
                }
            }
        }
        return 0;
    }
}
