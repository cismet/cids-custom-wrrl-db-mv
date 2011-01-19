/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import java.text.DecimalFormat;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class RohrleitungToStringConverter extends CustomToStringConverter {

    //~ Methods ----------------------------------------------------------------

    @Override
    public String createString() {
        final CidsBean stationVon = (CidsBean)cidsBean.getProperty("station_von");
        final CidsBean stationBis = (CidsBean)cidsBean.getProperty("station_bis");
        final String wertVon = new DecimalFormat("#.#").format((Double)stationVon.getProperty("wert"));
        final String wertBis = new DecimalFormat("#.#").format((Double)stationBis.getProperty("wert"));
        final CidsBean route = (CidsBean)stationVon.getProperty("route");
        final String gwk = String.valueOf(route.getProperty("gwk"));

        return String.valueOf(gwk + "@" + wertVon + "-" + wertBis);
    }
}
