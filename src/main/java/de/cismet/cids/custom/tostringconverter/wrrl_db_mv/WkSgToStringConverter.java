package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author stefan
 */
public class WkSgToStringConverter extends CustomToStringConverter {

    @Override
    public String createString() {
        return "(" + cidsBean.getProperty("wk_k") + ") " + cidsBean.getProperty("lw_name");
    }
}
