package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author therter
 */
public class MassnahmenToStringConverter extends CustomToStringConverter {
    @Override
    public String createString() {
        String wkk = "";
        CidsBean wk = (CidsBean)cidsBean.getProperty("wk_fg");

        //todo: auch andere Wasserkoerper beruecksichtigen. z.B. wk_sg, wk_gw, ...
        if (wk != null) {
            wkk = wk.getProperty("wk_k").toString();
        }

        return wkk + "_M" + String.valueOf(cidsBean.getProperty("massn_wk_lfdnr"));
    }
}
