

package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;


import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author therter
 */
public class IndikatorToStringConverter extends CustomToStringConverter {
    @Override
    public String createString() {
        return cidsBean.getProperty("indikator_nr").toString() + " - " + String.valueOf(cidsBean.getProperty("indikator"));
    }
}
