package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author therter
 */
public class SwstnToStringConverter extends CustomToStringConverter {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SwstnToStringConverter.class);

    @Override
    public String createString() {
        Object name = cidsBean.getProperty("name_stn");


        if (name != null) {
            return name.toString();
        } else {
            return "unbenannt";
        }
    }
}
