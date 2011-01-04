/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class LawaToStringConverter extends CustomToStringConverter {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LawaToStringConverter.class);

    //~ Methods ----------------------------------------------------------------

    @Override
    public String createString() {
        Object gwk = cidsBean.getProperty("stat_von.route.gwk");
        Object stat_von = cidsBean.getProperty("stat_von.wert");
        Object stat_bis = cidsBean.getProperty("stat_bis.wert");

        if (stat_von == null) {
            stat_von = "unbekannt";
        }

        if (stat_bis == null) {
            stat_bis = "unbekannt";
        }

        if (gwk == null) {
            gwk = "unbekannt";
        }

        if ((stat_von instanceof Double) && (stat_bis instanceof Double)) {
            final int von = ((Double)stat_von).intValue();
            final int bis = ((Double)stat_bis).intValue();
            return gwk.toString() + " [" + von + " - " + bis + "]";
        } else {
            return gwk.toString() + " [" + stat_von.toString() + " - " + stat_bis.toString() + "]";
        }
    }
}
