/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.util.fgsk.eval;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.search.CidsServerSearch;

import java.util.ArrayList;
import java.util.Collection;

import de.cismet.cids.custom.util.EvaluationSearch;
import de.cismet.cids.custom.util.IncompleteDataException;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public abstract class FgSkCalculations {

    //~ Static fields/initializers ---------------------------------------------

    protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            FgSkCalculations.class);
    static final String[][] SCHAE_UMFELD_LINKS = {
            { "1", "s_umfeldstrukturen_ag_links" },
            { "2", "s_umfeldstrukturen_ft_links" },
            { "3", "s_umfeldstrukturen_gua_links" },
            { "4", "s_umfeldstrukturen_bv_links" },
            { "5", "s_umfeldstrukturen_ma_links" },
            { "6", "s_umfeldstrukturen_hw_links" },
            { "7", "s_umfeldstrukturen_so_links" }
        };
    static final String[][] SCHAE_UMFELD_RECHTS = {
            { "1", "s_umfeldstrukturen_ag_rechts" },
            { "2", "s_umfeldstrukturen_ft_rechts" },
            { "3", "s_umfeldstrukturen_gua_rechts" },
            { "4", "s_umfeldstrukturen_bv_rechts" },
            { "5", "s_umfeldstrukturen_ma_rechts" },
            { "6", "s_umfeldstrukturen_hw_rechts" },
            { "7", "s_umfeldstrukturen_so_rechts" }
        };
    static final String SCHAE_UMFELD_QUERY =
        "select bewertung from fgsk.schaedlicheumfeldstrukturen_auswertung where id_gewaessertyp = 11 and id_schaedlicheumfeldstrukturen = %1$s";

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  kartierabschnitt  DOCUMENT ME!
     */
    public static void recalculate_total_points_kartierabschnitt_func(final CidsBean kartierabschnitt) {
        // todo: implement
//    PERFORM calculate_points_gewaesserumfeld_func(kartierabsch_id);
//    PERFORM calculate_points_uferstruktur_func(kartierabsch_id);
//    PERFORM calculate_points_sohlenstruktur_func(kartierabsch_id);
//    PERFORM calculate_points_querprofil_func(kartierabsch_id);
//    PERFORM calculate_points_laengsprofil_func(kartierabsch_id);
//    PERFORM calculate_points_laufentwicklung_func(kartierabsch_id);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kartierabschnitt  DOCUMENT ME!
     *
     * @throws  IncompleteDataException  DOCUMENT ME!
     */
    public abstract void recalculate_points(CidsBean kartierabschnitt) throws IncompleteDataException;

    /**
     * DOCUMENT ME!
     *
     * @param   query  DOCUMENT ME!
     * @param   param  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected static String createUnion(final String query, final String[] param) {
        StringBuilder builder = null;

        for (final String tmp : param) {
            if (builder != null) {
                builder.append("\n UNION ALL \n");
            } else {
                builder = new StringBuilder();
            }
            builder.append(String.format(query, tmp));
        }

        return ((builder != null) ? builder.toString() : null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected static int getBewId(final CidsBean bean) {
        return (Integer)bean.getProperty("value");
    }

    /**
     * DOCUMENT ME!
     *
     * @param   query  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected static Integer getBewertung(final String query) {
        try {
            final CidsServerSearch search = new EvaluationSearch(query);
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

            if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                final Object o = resArray.get(0).get(0);

                if (o instanceof Integer) {
                    return (Integer)o;
                }
            } else {
                LOG.error("Server error in EvaluationSearch. Cids server search return null. " // NOI18N
                            + "See the server logs for further information. The query was: " + query); // NOI18N
            }
        } catch (final Exception e) {
            LOG.error("Error while executing the following query: " + query, e);
        }

        return null;
    }
}
