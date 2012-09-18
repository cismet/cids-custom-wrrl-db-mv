/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.util.ArrayList;
import java.util.List;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupHelper {

    //~ Static fields/initializers ---------------------------------------------

    private static final List<String> PROPERTY_LIST = new ArrayList<String>();

    static {
        PROPERTY_LIST.add("massnahmen");
//        PROPERTY_LIST.add("gup_massnahmen_sohle");
//        PROPERTY_LIST.add("gup_massnahmen_umfeld_links");
//        PROPERTY_LIST.add("gup_massnahmen_ufer_links");
//        PROPERTY_LIST.add("gup_massnahmen_ufer_rechts");
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  die erste station_linie, die im gewaesser gefunden wird
     */
    public static CidsBean getStationLinie(final CidsBean cidsBean) {
        for (int i = 0; i < PROPERTY_LIST.size(); ++i) {
            final List<CidsBean> beans = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, PROPERTY_LIST.get(i));

            if ((beans != null) && (beans.size() > 0)) {
                return (CidsBean)beans.get(0).getProperty("linie");
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static double getMinStart(final CidsBean cidsBean) {
        double minStart = -1;

        for (int i = 0; i < PROPERTY_LIST.size(); ++i) {
            final List<CidsBean> beans = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, PROPERTY_LIST.get(i));

            if ((beans != null) && (beans.size() > 0)) {
                for (final CidsBean tmp : beans) {
                    final CidsBean line = (CidsBean)tmp.getProperty("linie");
                    if (line != null) {
                        final CidsBean start = (CidsBean)line.getProperty("von");
                        if (start != null) {
                            final Double value = (Double)start.getProperty("wert");

                            if (value != null) {
                                if ((minStart == -1) || (minStart > value.doubleValue())) {
                                    minStart = value.doubleValue();
                                }
                            }
                        }
                    }
                }
            }
        }

        return minStart;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static double getMaxEnd(final CidsBean cidsBean) {
        double maxEnd = -1;

        for (int i = 0; i < PROPERTY_LIST.size(); ++i) {
            final List<CidsBean> beans = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, PROPERTY_LIST.get(i));

            if ((beans != null) && (beans.size() > 0)) {
                for (final CidsBean tmp : beans) {
                    final CidsBean line = (CidsBean)tmp.getProperty("linie");
                    if (line != null) {
                        final CidsBean end = (CidsBean)line.getProperty("bis");
                        if (end != null) {
                            final Double value = (Double)end.getProperty("wert");

                            if (value != null) {
                                if ((maxEnd == -1) || (maxEnd < value.doubleValue())) {
                                    maxEnd = value.doubleValue();
                                }
                            }
                        }
                    }
                }
            }
        }

        return maxEnd;
    }
}
