package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author therter
 */
public class MaTypToStringConverter extends CustomToStringConverter {
    @Override
    public String createString() {
        return String.valueOf(cidsBean.getProperty("code")) + "-" + String.valueOf(cidsBean.getProperty("description"));
    }
}
