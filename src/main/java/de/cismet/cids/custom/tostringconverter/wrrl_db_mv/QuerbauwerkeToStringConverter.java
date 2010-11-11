package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.tools.CustomToStringConverter;
import java.text.DecimalFormat;

/**
 *
 * @author jruiz
 */
public class QuerbauwerkeToStringConverter extends CustomToStringConverter {

    @Override
    public String createString() {
        CidsBean stat09 = (CidsBean)cidsBean.getProperty("stat09");
        String wert = new DecimalFormat("#.#").format((Double)stat09.getProperty("wert"));
        CidsBean route = (CidsBean)stat09.getProperty("route");
        String gwk = String.valueOf(route.getProperty("gwk"));
        return "Querbauwerk " + gwk + "@" + wert;
    }
}
