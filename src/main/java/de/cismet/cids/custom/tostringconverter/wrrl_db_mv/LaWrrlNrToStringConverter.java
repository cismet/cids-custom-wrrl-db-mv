package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author therter
 */
public class LaWrrlNrToStringConverter extends CustomToStringConverter {
    @Override
    public String createString() {
        return String.valueOf(cidsBean.getProperty("description"));
    }
}