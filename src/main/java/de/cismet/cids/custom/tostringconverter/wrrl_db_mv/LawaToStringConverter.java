package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author therter
 */
public class LawaToStringConverter extends CustomToStringConverter {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LawaToStringConverter.class);

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

        return gwk.toString() + " " + stat_von.toString() + "-" + stat_bis.toString();
    }
}
