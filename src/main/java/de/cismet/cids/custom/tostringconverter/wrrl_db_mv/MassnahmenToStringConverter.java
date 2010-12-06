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
        CidsBean wk_f = (CidsBean)cidsBean.getProperty("wk_fg");
        CidsBean wk_s = (CidsBean)cidsBean.getProperty("wk_sg");
        CidsBean wk_k = (CidsBean)cidsBean.getProperty("wk_kg");
        CidsBean wk_g = (CidsBean)cidsBean.getProperty("wk_gw");

        if (wk_f != null) {
            wkk = wk_f.getProperty("wk_k").toString();
        } else if (wk_s != null) {
            wkk = wk_s.getProperty("wk_k").toString();
        } else if (wk_k != null) {
            wkk = wk_k.getProperty("name").toString();
        } else if (wk_g != null) {
            wkk = wk_g.getProperty("name").toString();
        }

        return wkk + "_M" + String.valueOf(cidsBean.getProperty("massn_wk_lfdnr"));
    }
}
