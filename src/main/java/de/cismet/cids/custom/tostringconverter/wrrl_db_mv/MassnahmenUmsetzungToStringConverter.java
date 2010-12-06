

package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author therter
 */
public class MassnahmenUmsetzungToStringConverter extends CustomToStringConverter {
    @Override
    public String createString() {
        Object besch = cidsBean.getProperty("mass_beschreibung");
        String beschreibung = (besch == null ? "" : String.valueOf( besch ) );

        return cidsBean.getProperty("id").toString() + " " + beschreibung;
    }
}
