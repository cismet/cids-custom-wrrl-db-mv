/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import java.text.DecimalFormat;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkeToStringConverter extends CustomToStringConverter {

    //~ Methods ----------------------------------------------------------------

    @Override
    public String createString() {
        final CidsBean stat09 = (CidsBean)cidsBean.getProperty("stat09");
        final String wert = new DecimalFormat("#.#").format((Double)stat09.getProperty("wert"));
        final CidsBean route = (CidsBean)stat09.getProperty("route");
        final String gwk = String.valueOf(route.getProperty("gwk"));
        return "Querbauwerk " + gwk + "@" + wert;
    }
}
