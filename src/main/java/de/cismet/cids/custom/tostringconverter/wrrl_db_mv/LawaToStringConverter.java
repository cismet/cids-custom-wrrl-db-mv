package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author therter
 */
public class LawaToStringConverter extends CustomToStringConverter {

    @Override
    public String createString() {
        Object name = cidsBean.getProperty("wk_k");
        return name == null ? "LAWA " + cidsBean.getProperty("code_geo") : "LAWA " + name.toString();
    }
}
