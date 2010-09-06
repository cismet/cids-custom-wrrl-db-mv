package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author stefan
 */
public final class ExcemptionToStringConverter extends CustomToStringConverter {

    @Override
    public String createString() {
        return "Ausnahme (" + processCat(cidsBean.getProperty("ex_cat")) + ", " + processTyp(cidsBean.getProperty("ex_typ")) + ")";
    }

    private static String processCat(Object in) {
        if (in == null) {
            return "keine Kategorie gewählt";
        } else {
            return in.toString();
        }
    }

    private static String processTyp(Object in) {
        if (in == null) {
            return "kein Typ gewählt";
        } else {
            return in.toString();
        }
    }
}
