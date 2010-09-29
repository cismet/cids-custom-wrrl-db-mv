package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author stefan
 */
public final class RouteToStringConverter extends CustomToStringConverter {

    @Override
    public String createString() {
        return "Route: " + cidsBean.getProperty("gwk");
    }

}
