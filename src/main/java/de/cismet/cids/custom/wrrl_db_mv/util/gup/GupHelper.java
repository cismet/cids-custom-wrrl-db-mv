/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.NaturschutzgebietSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.QuerbautenSearchByStations;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.tools.CalculationCache;
import de.cismet.tools.Calculator;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupHelper {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GupHelper.class);

    public static final CalculationCache<List, MetaObject[]> schutzgebietCache =
        new CalculationCache<List, MetaObject[]>(
            new SchutzgebietCalculator());
    public static final CalculationCache<List, MetaObject[]> umlandCache = new CalculationCache<List, MetaObject[]>(
            new UmlandnutzungCalculator());
    public static final CalculationCache<List, MetaObject[]> umlandnutzerCache =
        new CalculationCache<List, MetaObject[]>(
            new UmlandnutzerCalculator());
    public static final CalculationCache<List, MetaObject[]> entwicklungszielCache =
        new CalculationCache<List, MetaObject[]>(new EntwicklungszielCalculator());
    public static final CalculationCache<List, ArrayList<ArrayList>> querbauwerkCache =
        new CalculationCache<List, ArrayList<ArrayList>>(new QuerbauwerkeCalculator());
    public static final CalculationCache<List, MetaObject[]> unterhaltungserfordernisCache =
        new CalculationCache<List, MetaObject[]>(new UnterhaltungserfordernisCalculator());
    public static final CalculationCache<List, MetaObject[]> verbreitungsraumCache =
        new CalculationCache<List, MetaObject[]>(new VerbreitungsraumCalculator());
    public static final CalculationCache<List, MetaObject[]> operativeZieleCache =
        new CalculationCache<List, MetaObject[]>(
            new OperativesZielCalculator());
    public static final CalculationCache<List, MetaObject[]> unterhaltungshinweiseCache =
        new CalculationCache<List, MetaObject[]>(
            new UnterhaltungshinweiseCalculator());
    public static final CalculationCache<List, MetaObject[]> hydrologieCache = new CalculationCache<List, MetaObject[]>(
            new HydrologieCalculator());
    public static final CalculationCache<List, MetaObject[]> unterhaltungsmassnahmeCache =
        new CalculationCache<List, MetaObject[]>(
            new UnterhaltungsmassnahmeCalculator());

    private static final List<String> PROPERTY_LIST = new ArrayList<String>();

    static {
        PROPERTY_LIST.add("massnahmen");
//        PROPERTY_LIST.add("gup_massnahmen_sohle");
//        PROPERTY_LIST.add("gup_massnahmen_umfeld_links");
//        PROPERTY_LIST.add("gup_massnahmen_ufer_links");
//        PROPERTY_LIST.add("gup_massnahmen_ufer_rechts");
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  metaObjects  DOCUMENT ME!
     * @param  min          DOCUMENT ME!
     * @param  max          DOCUMENT ME!
     */
    private static void adjustBorders(final MetaObject[] metaObjects, final double min, final double max) {
        if (metaObjects != null) {
            for (final MetaObject tmp : metaObjects) {
                double von = (Double)tmp.getBean().getProperty("linie.von.wert");
                double bis = (Double)tmp.getBean().getProperty("linie.bis.wert");

                if (von > bis) {
                    // swap von and bis
                    try {
                        tmp.getBean().setProperty("linie.von.wert", bis);
                        tmp.getBean().setProperty("linie.bis.wert", von);
                        final double tmpValue = von;
                        von = bis;
                        bis = tmpValue;
                    } catch (Exception e) {
                        LOG.error("Cannot swap von and bis value", e);
                    }
                }

                try {
                    if (von < min) {
                        tmp.getBean().setProperty("linie.von.wert", min);
                    }
                    if (bis > max) {
                        tmp.getBean().setProperty("linie.bis.wert", max);
                    }
                } catch (Exception e) {
                    LOG.error("Cannot adjust the station value", e);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  die erste station_linie, die im gewaesser gefunden wird
     */
    public static CidsBean getStationLinie(final CidsBean cidsBean) {
        for (int i = 0; i < PROPERTY_LIST.size(); ++i) {
            final List<CidsBean> beans = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, PROPERTY_LIST.get(i));

            if ((beans != null) && (beans.size() > 0)) {
                return (CidsBean)beans.get(0).getProperty("linie");
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static double getMinStart(final CidsBean cidsBean) {
        double minStart = -1;

        for (int i = 0; i < PROPERTY_LIST.size(); ++i) {
            final List<CidsBean> beans = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, PROPERTY_LIST.get(i));

            if ((beans != null) && (beans.size() > 0)) {
                for (final CidsBean tmp : beans) {
                    final CidsBean line = (CidsBean)tmp.getProperty("linie");
                    if (line != null) {
                        final CidsBean start = (CidsBean)line.getProperty("von");
                        if (start != null) {
                            final Double value = (Double)start.getProperty("wert");

                            if (value != null) {
                                if ((minStart == -1) || (minStart > value.doubleValue())) {
                                    minStart = value.doubleValue();
                                }
                            }
                        }
                    }
                }
            }
        }

        return minStart;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static double getMaxEnd(final CidsBean cidsBean) {
        double maxEnd = -1;

        for (int i = 0; i < PROPERTY_LIST.size(); ++i) {
            final List<CidsBean> beans = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, PROPERTY_LIST.get(i));

            if ((beans != null) && (beans.size() > 0)) {
                for (final CidsBean tmp : beans) {
                    final CidsBean line = (CidsBean)tmp.getProperty("linie");
                    if (line != null) {
                        final CidsBean end = (CidsBean)line.getProperty("bis");
                        if (end != null) {
                            final Double value = (Double)end.getProperty("wert");

                            if (value != null) {
                                if ((maxEnd == -1) || (maxEnd < value.doubleValue())) {
                                    maxEnd = value.doubleValue();
                                }
                            }
                        }
                    }
                }
            }
        }

        return maxEnd;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class NaturschutzCalculator implements Calculator<List, ArrayList<ArrayList>> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public ArrayList<ArrayList> calculate(final List input) throws Exception {
            final CidsServerSearch searchNSG = new NaturschutzgebietSearch((Double)input.get(0),
                    (Double)input.get(1),
                    String.valueOf(input.get(2)));
            final Collection resNSG = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), searchNSG);
            return (ArrayList<ArrayList>)resNSG;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class UmlandnutzungCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass UMLANDNUTZUNG = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GUP_UMLANDNUTZUNG");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + UMLANDNUTZUNG.getID() + ", u." + UMLANDNUTZUNG.getPrimaryKey()
                        + " from "
                        + UMLANDNUTZUNG.getTableName()
                        + " u, umlandnutzung_route_umlandnutzung route, station_linie sl, station von, station bis, route r "
                        + "WHERE route.umlandnutzung = u.id and u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Umlandnutzung: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            // Passe die Groessen der Umfeldnutzung an die Groesse des GUP an. Da keine Verknuepfung zwischen
            // der Umfeldnutzung und dem Gewaesser besteht, werden diese Aenderungen auch nicht gespeichert
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class UmlandnutzerCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass UMLANDNUTZER = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "UMLANDNUTZER");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + UMLANDNUTZER.getID() + ", u." + UMLANDNUTZER.getPrimaryKey()
                        + " from "
                        + UMLANDNUTZER.getTableName()
                        + " u, umlandnutzer_route_umlandnutzer route, station_linie sl, station von, station bis, route r "
                        + "WHERE route.umlandnutzer = u.id and u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Umlandnutzer: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            // Passe die Groessen der Umfeldnutzer an die Groesse des GUP an. Da keine Verknuepfung zwischen
            // den Umfeldnutzern und dem Gewaesser besteht, werden diese Aenderungen auch nicht gespeichert
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class HydrologieCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass HYDROLOGIE = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GUP_HYDROLOG");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + HYDROLOGIE.getID() + ", u." + HYDROLOGIE.getPrimaryKey()
                        + " from "
                        + HYDROLOGIE.getTableName()
                        + " u, hydrolog_route_hydrolog route, station_linie sl, station von, station bis, route r "
                        + "WHERE route.hydrologie = u.id and u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Hydrologie: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            // Passe die Groessen der Umfeldnutzer an die Groesse des GUP an. Da keine Verknuepfung zwischen
            // den Umfeldnutzern und dem Gewaesser besteht, werden diese Aenderungen auch nicht gespeichert
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class SchutzgebietCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass SCHUTZGEBIET = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "SCHUTZGEBIET");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + SCHUTZGEBIET.getID() + ", u." + SCHUTZGEBIET.getPrimaryKey()
                        + " from "
                        + SCHUTZGEBIET.getTableName()
                        + " u, station_linie sl, station von, station bis, route r "
                        + "WHERE u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Schutzgebiete: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            // Passe die Groessen der SChutzgebiete an die Groesse des GUP an. Da keine Verknuepfung zwischen
            // den Schutzgebieten und dem Gewaesser besteht, werden diese Aenderungen auch nicht gespeichert
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class OperativesZielCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass OPERATIVES_ZIEL = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GUP_OPERATIVES_ZIEL_ABSCHNITT");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + OPERATIVES_ZIEL.getID() + ", u." + OPERATIVES_ZIEL.getPrimaryKey()
                        + " from "
                        + OPERATIVES_ZIEL.getTableName()
                        + " u, gup_operatives_ziel_route_abschnitt route, station_linie sl, station von, station bis, route r "
                        + "WHERE route.abschnitt = u.id and u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for operatives Ziel: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            // Passe die Groessen der operativen Ziele an die Groesse des GUP an. Da keine Verknuepfung zwischen
            // den operativen Zielen und dem Gewaesser besteht, werden diese Aenderungen auch nicht gespeichert
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class VerbreitungsraumCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass GESCHUETZTE_ART_ABSCHNITT = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GESCHUETZTE_ART_ABSCHNITT");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + GESCHUETZTE_ART_ABSCHNITT.getID() + ", u."
                        + GESCHUETZTE_ART_ABSCHNITT.getPrimaryKey()
                        + " from "
                        + GESCHUETZTE_ART_ABSCHNITT.getTableName()
                        + " u, verbreitungsraum_geschuetzte_art_abschnitt route, station_linie sl, station von, station bis, route r "
                        + "WHERE route.abschnitt = u.id and u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Verbreitungsraum: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            // Passe die Groessen der Verbreitungsraeume an die Groesse des GUP an. Da keine Verknuepfung zwischen
            // den Verbreitungsraeumen und dem Gewaesser besteht, werden diese Aenderungen auch nicht gespeichert
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class EntwicklungszielCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass ENTWICKLUNGSZIEL = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GUP_ENTWICKLUNGSZIEL");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + ENTWICKLUNGSZIEL.getID() + ", u."
                        + ENTWICKLUNGSZIEL.getPrimaryKey()
                        + " from "
                        + ENTWICKLUNGSZIEL.getTableName()
                        + " u, entwicklungsziel_route_entwicklungsziel route, station_linie sl, station von, station bis, route r "
                        + "WHERE route.entwicklungsziel = u.id and u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N
            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Entwicklungsziel: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }
    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class UnterhaltungshinweiseCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass GUP_POI = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GUP_poi");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + GUP_POI.getID() + ", u."
                        + GUP_POI.getPrimaryKey()
                        + " from "
                        + GUP_POI.getTableName()
                        + " u, gup_poi_route route, station_linie sl, station von, station bis, route r "
                        + "WHERE route.id = u.poi_route and u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N
            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Gup_POIs: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class UnterhaltungserfordernisCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass UNTERHALTUNGSERFORDERNIS = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GUP_UNTERHALTUNGSERFORDERNIS");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + UNTERHALTUNGSERFORDERNIS.getID() + ", u."
                        + UNTERHALTUNGSERFORDERNIS.getPrimaryKey()
                        + " from "
                        + UNTERHALTUNGSERFORDERNIS.getTableName()
                        + " u, gup_unterhaltungserfordernis_route_unterhaltungserfordernis route, station_linie sl, station von, station bis, route r "
                        + "WHERE route.unterhaltungserfordernis = u.id and u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N
            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Situationstypen: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class UnterhaltungsmassnahmeCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass UNTERHALTUNGSMASSNAHME = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GUP_UNTERHALTUNGSMASSNAHME");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + UNTERHALTUNGSMASSNAHME.getID() + ", u."
                        + UNTERHALTUNGSMASSNAHME.getPrimaryKey()
                        + " from "
                        + UNTERHALTUNGSMASSNAHME.getTableName()
                        + " u, GUP_PLANUNGSABSCHNITT_GUP_UNTERHALTUNGSMASSNAHME route, station_linie sl, station von, station bis, route r "
                        + "WHERE route.gup_unterhaltungsmassnahme = u.id and u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N
            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Unterhaltungsmassnahmen: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class QuerbauwerkeCalculator implements Calculator<List, ArrayList<ArrayList>> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public ArrayList<ArrayList> calculate(final List input) throws Exception {
            final CidsServerSearch searchQB = new QuerbautenSearchByStations((Double)input.get(0),
                    (Double)input.get(1),
                    String.valueOf(input.get(2)));
            final Collection resQB = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), searchQB);
            return (ArrayList<ArrayList>)resQB;
        }
    }
}
