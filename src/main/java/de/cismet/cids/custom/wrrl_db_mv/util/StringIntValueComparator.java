/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import java.util.Comparator;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class StringIntValueComparator implements Comparator<CidsBean> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            StringIntValueComparator.class);

    //~ Methods ----------------------------------------------------------------

    @Override
    public int compare(final CidsBean o1, final CidsBean o2) {
        final String stringValue1 = (String)o1.getProperty("value");
        final String stringValue2 = (String)o2.getProperty("value");
        Integer value1 = null;
        Integer value2 = null;

        try {
            value1 = Integer.parseInt(stringValue1);
        } catch (NumberFormatException e) {
            LOG.info("No String", e);
        }

        try {
            value2 = Integer.parseInt(stringValue2);
        } catch (NumberFormatException e) {
            LOG.info("No String", e);
        }

        if ((value1 == null) && (value2 == null)) {
            return 0;
        } else if (value1 == null) {
            return -1;
        } else if (value2 == null) {
            return 1;
        } else {
            return value1.compareTo(value2);
        }
    }
}
