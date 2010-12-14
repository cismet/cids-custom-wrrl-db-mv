

package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;


import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author therter
 */
public class ProjekteIndikatorenToStringConverter extends CustomToStringConverter {

    @Override
    public String createString() {
        CidsBean indikator = (CidsBean)cidsBean.getProperty("indikator_schluessel");

        if (indikator != null) {
            return indikator.getProperty("indikator_nr").toString() + " - " + String.valueOf(indikator.getProperty("indikator"));
        } else {
            return "Kein Indikatorschlüssel zugewiesen";
        }
    }
}
