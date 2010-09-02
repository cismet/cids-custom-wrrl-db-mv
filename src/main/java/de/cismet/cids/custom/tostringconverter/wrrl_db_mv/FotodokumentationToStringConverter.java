package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author stefan
 */
public class FotodokumentationToStringConverter extends CustomToStringConverter {

    @Override
    public String createString() {
        Object name = cidsBean.getProperty("name");
        return name == null ? "Neue Fotodokumentation" : name.toString();
    }
}
