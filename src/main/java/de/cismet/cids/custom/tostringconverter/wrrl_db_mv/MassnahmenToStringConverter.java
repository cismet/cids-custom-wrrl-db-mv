package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author therter
 */
public class MassnahmenToStringConverter extends CustomToStringConverter {
    @Override
    public String createString() {
        Object massn_id = cidsBean.getProperty("massn_id");

        return (massn_id == null ? "keine id zugewiesen" : massn_id.toString());
    }
}
