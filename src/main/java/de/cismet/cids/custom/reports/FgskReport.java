/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 jweintraut
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.reports;

import Sirius.navigator.connection.Connection;
import Sirius.navigator.connection.ConnectionFactory;
import Sirius.navigator.connection.ConnectionInfo;
import Sirius.navigator.connection.ConnectionSession;
import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.connection.proxy.ConnectionProxy;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.newuser.UserException;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.Reader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.FgskIdSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkkSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.ReportUtils;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public final class FgskReport extends AbstractJasperReportPrint {

    //~ Static fields/initializers ---------------------------------------------

    private static final String REPORT_URL = "/de/cismet/cids/custom/reports/fgsk.jasper";
    private static final Logger LOG = Logger.getLogger(FgskReport.class);
    private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final String INDETERMINATE = "nicht ermittelbar";

    //~ Instance fields --------------------------------------------------------

    private final MetaClass MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new StadtbildJasperReportPrint object.
     *
     * @param  beans  DOCUMENT ME!
     */
    public FgskReport(final Collection<CidsBean> beans) {
        this(null, beans);
    }

    /**
     * Creates a new StadtbildJasperReportPrint object.
     *
     * @param  bean  DOCUMENT ME!
     */
    public FgskReport(final CidsBean bean) {
        super(REPORT_URL, bean);
        setBeansCollection(false);
    }

    /**
     * Creates a new StadtbildJasperReportPrint object.
     *
     * @param  bean      DOCUMENT ME!
     * @param  filename  DOCUMENT ME!
     */
    public FgskReport(final CidsBean bean, final String filename) {
        super(REPORT_URL, bean, filename);
        setBeansCollection(false);
    }

    /**
     * Creates a new StadtbildJasperReportPrint object.
     *
     * @param  parent  DOCUMENT ME!
     * @param  beans   DOCUMENT ME!
     */
    public FgskReport(final JFrame parent, final Collection<CidsBean> beans) {
        super(parent, REPORT_URL, beans);
        setBeansCollection(false);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   obj  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String toString(final Object obj) {
        if (obj == null) {
            return "";
        }

        return String.valueOf(obj);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   obj  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    private Integer toInteger(final Object obj) {
        if (obj == null) {
            return Integer.valueOf(-1);
        }

        if (!(obj instanceof Integer)) {
            throw new IllegalArgumentException();
        }

        return (Integer)obj;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   obj  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private double convertNumberToDouble(final Number obj) {
        if (obj == null) {
            return 0.0;
        }

        return obj.doubleValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   obj  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String convertNumberToString(final Number obj) {
        if (obj == null) {
            return "0";
        }

        return String.valueOf(obj);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean    DOCUMENT ME!
     * @param  params  DOCUMENT ME!
     */
    private void retrieveKartierabschnittParams(final CidsBean bean, final Map params) {
        String gew = CidsBeanSupport.FIELD_NOT_SET;
        String gwk = CidsBeanSupport.FIELD_NOT_SET;
        String wkName = CidsBeanSupport.FIELD_NOT_SET;
//        String wkType = CidsBeanSupport.FIELD_NOT_SET;
        String wkTypeId = CidsBeanSupport.FIELD_NOT_SET;
        String statString = CidsBeanSupport.FIELD_NOT_SET;

        final CidsBean statLine = (CidsBean)bean.getProperty("linie");
        CidsBean route = null;

        if (statLine != null) {
            final CidsBean statVon = (CidsBean)statLine.getProperty("von");
            final CidsBean statBis = (CidsBean)statLine.getProperty("bis");

            if (statVon != null) {
                route = (CidsBean)statVon.getProperty("route");
                if (route != null) {
                    gew = toString(route.getProperty("routenname"));
                    gwk = toString(route.getProperty("gwk"));
                }

                if (statBis != null) {
                    statString = toString(statVon.getProperty("wert")) + " - "
                                + toString(statBis.getProperty("wert"));
                }
            }

            String wkk = CidsBeanSupport.FIELD_NOT_SET;
            try {
                final CidsBean geomEntry = (CidsBean)statLine.getProperty("geom");
                final Geometry geom = ((geomEntry != null) ? (Geometry)geomEntry.getProperty("geo_field") : null);
                final String geomString = geom.toText();
                final CidsServerSearch search = new WkkSearch(geomString, toString(route.getProperty("id")));
                final Collection res = SessionManager.getProxy()
                            .customServerSearch(SessionManager.getSession().getUser(), search);
                final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

                if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                    final Object o = resArray.get(0).get(0);

                    if (o instanceof String) {
                        wkk = o.toString();
                        final String query = "select " + MC.getID() + ", " + MC.getPrimaryKey() + " from "
                                    + MC.getTableName() + " where wk_k = '" + wkk + "'";
                        final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
                        if (metaObjects.length == 1) {
                            wkName = (String)metaObjects[0].getBean().getProperty("wk_n");
//                            wkType = toString(metaObjects[0].getBean().getProperty("typ_k.name"));
                            wkTypeId = toString(metaObjects[0].getBean().getProperty("typ_k.value"));
                        } else {
                            wkName = INDETERMINATE;
//                            wkType = INDETERMINATE;
                            wkTypeId = INDETERMINATE;
                        }
                    }
                } else {
                    LOG.error("Server error in getWk_k(). Cids server search return null. " // NOI18N
                                + "See the server logs for further information");     // NOI18N
                }
            } catch (final Exception e) {
                LOG.error("Error while determining the water body", e);
            }

            params.put("gew", toString(gew));       // Gewässername
            params.put("gwk", toString(gwk));       // Gewässerkennzahl
            params.put("wkk", toString(wkk));       // Wasserkörper
            params.put("wkName", toString(wkName)); // Wasserkörpername TODO: benötigt?
            params.put("wkType", wkTypeId);         // Wk-Type
            params.put("stationierung", toString(statString));

            params.put("gewaesser_abschnitt", toString(bean.getProperty("gewaesser_abschnitt")));
            params.put("bearbeiter", toString(bean.getProperty("bearbeiter")));
            params.put("erfassungsdatum", toString(DF.format(bean.getProperty("erfassungsdatum"))));
            params.put("fliessrichtung_id", toString(bean.getProperty("fliessrichtung_id")));

            params.put("wasserfuehrung_id", toString(bean.getProperty("wasserfuehrung_id")));
            params.put("foto_nr", toString(bean.getProperty("foto_nr")));

            final Boolean unterhaltungerkennbar = (Boolean)bean.getProperty("unterhaltungerkennbar");
            if (unterhaltungerkennbar != null) {
                params.put("unterhaltungerkennbar", unterhaltungerkennbar ? "ja" : "nein");
            }

            params.put("sonderfall_id", toInteger(bean.getProperty("sonderfall_id.value")));
            params.put("erlaeuterung", toString(bean.getProperty("erlaeuterung")));
            params.put("gewaesserbreite_id", toInteger(bean.getProperty("gewaesserbreite_id.value")));
            params.put("gewaessertyp_id", toString(bean.getProperty("gewaessertyp_id.value")));

            final List<CidsBean> subTypes = (List<CidsBean>)bean.getProperty("gewaessersubtyp");
            final ArrayList<String> subTypesStringList = new ArrayList<String>(subTypes.size());

            for (final CidsBean subTypeBean : subTypes) {
                subTypesStringList.add(toString(subTypeBean.getProperty("value")));
            }

            params.put("gewaessersubtyp", subTypesStringList);

            final Boolean seeausfluss = (Boolean)bean.getProperty("seeausfluss");
            params.put("seeausfluss", (seeausfluss == null) ? Boolean.FALSE : seeausfluss);

            params.put("beschattung_id", toInteger(bean.getProperty("beschattung_id.value"))); // Integer: 1 - häufig
            params.put("lauftyp_id", toInteger(bean.getProperty("lauftyp_id.value")));         // Integer: 1 - häufig
            params.put("talform_id", toInteger(bean.getProperty("talform_id.value")));         // Integer: 1 - häufig
            params.put("talform_kl_id", toInteger(bean.getProperty("talform_kl_id.value")));   // Integer: 1 - häufig

            final Boolean aeussereKolmation = (Boolean)bean.getProperty("aeussere_kolmation");
            params.put("aeussere_kolmation", (aeussereKolmation == null) ? Boolean.FALSE : aeussereKolmation);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean    DOCUMENT ME!
     * @param  params  DOCUMENT ME!
     */
    private void retrieveLaufentwicklungParams(final CidsBean bean, final Map params) {
        params.put("laufkruemmung_id", toInteger(bean.getProperty("laufkruemmung_id.value"))); // Integer: 1 -
        // mäandrierend usw.
        params.put("kruemmungserosion_id", toInteger(bean.getProperty("kruemmungserosion_id.value"))); // Integer: 1 - häufig
        // stark usw.

        // -- Handling Längsbänke

        final Number laengsbaenkeUFGK = (Number)bean.getProperty("laengsbaenke_ufkg");
        final Number laengsbaenkeIB = (Number)bean.getProperty("laengsbaenke_ib");
        final Number laengsbaenkeMB = (Number)bean.getProperty("laengsbaenke_mb");

        final double laengsbaenkeSum = this.convertNumberToDouble(laengsbaenkeIB)
                    + this.convertNumberToDouble(laengsbaenkeMB)
                    + this.convertNumberToDouble(laengsbaenkeUFGK);

        params.put(
            "laengsbaenke_ufkg",
            (laengsbaenkeSum == 0.0) ? "" : this.convertNumberToString(laengsbaenkeUFGK));
        params.put("laengsbaenke_ib", (laengsbaenkeSum == 0.0) ? "" : this.convertNumberToString(laengsbaenkeIB));
        params.put("laengsbaenke_mb", (laengsbaenkeSum == 0.0) ? "" : this.convertNumberToString(laengsbaenkeMB));

        boolean isNotNull = (laengsbaenkeIB != null) && (laengsbaenkeMB != null) && (laengsbaenkeUFGK != null);

        params.put("laengsbaenke_keine", (isNotNull && (laengsbaenkeSum == 0.0)) ? "X" : "");

        // -- Handling Laufstrukturen

        final Number laufstrukturenTV = (Number)bean.getProperty("laufstrukturen_tv");
        final Number laufstrukturenSB = (Number)bean.getProperty("laufstrukturen_sb");
        final Number laufstrukturenIBI = (Number)bean.getProperty("laufstrukturen_ibi");
        final Number laufstrukturenLW = (Number)bean.getProperty("laufstrukturen_lw");
        final Number laufstrukturenLV = (Number)bean.getProperty("laufstrukturen_lv");
        final Number laufstrukturenLG = (Number)bean.getProperty("laufstrukturen_lg");

        final double laufstrukturenSum = this.convertNumberToDouble(laufstrukturenTV)
                    + this.convertNumberToDouble(laufstrukturenSB)
                    + this.convertNumberToDouble(laufstrukturenIBI)
                    + this.convertNumberToDouble(laufstrukturenLW)
                    + this.convertNumberToDouble(laufstrukturenLV)
                    + this.convertNumberToDouble(laufstrukturenLG);

        params.put(
            "laufstrukturen_tv",
            (laufstrukturenSum == 0.0) ? "" : this.convertNumberToString(laufstrukturenTV));
        params.put(
            "laufstrukturen_sb",
            (laufstrukturenSum == 0.0) ? "" : this.convertNumberToString(laufstrukturenSB));
        params.put(
            "laufstrukturen_ibi",
            (laufstrukturenSum == 0.0) ? "" : this.convertNumberToString(laufstrukturenIBI));
        params.put(
            "laufstrukturen_lw",
            (laufstrukturenSum == 0.0) ? "" : this.convertNumberToString(laufstrukturenLW));
        params.put(
            "laufstrukturen_lv",
            (laufstrukturenSum == 0.0) ? "" : this.convertNumberToString(laufstrukturenLV));
        params.put(
            "laufstrukturen_lg",
            (laufstrukturenSum == 0.0) ? "" : this.convertNumberToString(laufstrukturenLG));

        isNotNull = (laufstrukturenIBI != null)
                    && (laufstrukturenLG != null)
                    && (laufstrukturenLV != null)
                    && (laufstrukturenLW != null)
                    && (laufstrukturenSB != null)
                    && (laufstrukturenTV != null);

        params.put("laufstrukturen_keine", (isNotNull && (laufstrukturenSum == 0.0)) ? "X" : "");
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean    DOCUMENT ME!
     * @param  params  DOCUMENT ME!
     */
    private void retrieveLaengsprofilParams(final CidsBean bean, final Map params) {
        params.put("fliessgeschwindigkeit_id", toInteger(bean.getProperty("fliessgeschwindigkeit_id.value")));
        params.put("stroemungsdiversitaet_id", toString(bean.getProperty("stroemungsdiversitaet_id")));
        params.put("tiefenvarianz_id", toString(bean.getProperty("tiefenvarianz_id")));
        params.put("tiefenerosion_id", toString(bean.getProperty("tiefenerosion_id")));
        params.put("querbaenke_anzahl", toString(bean.getProperty("querbaenke_anzahl")));

        final List<CidsBean> besondWasserFuehrungBeans = (List<CidsBean>)bean.getProperty(
                "besonderheiten_wasserfuehrung");
        final ArrayList<Integer> besondWasserFuehrungIds = new ArrayList<Integer>(besondWasserFuehrungBeans.size());
        for (final CidsBean tmpBean : besondWasserFuehrungBeans) {
            besondWasserFuehrungIds.add((Integer)tmpBean.getProperty("value"));
        }

        params.put("besonderheiten_wasserfuehrung", besondWasserFuehrungIds);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean    DOCUMENT ME!
     * @param  params  DOCUMENT ME!
     */
    private void retrieveQuerprofilParams(final CidsBean bean, final Map params) {
        params.put("profiltyp_id", toInteger(bean.getProperty("profiltyp_id.value")));
        params.put("breitenvarianz_id", toInteger(bean.getProperty("breitenvarianz_id.value")));
        params.put("breitenerosion_id", toInteger(bean.getProperty("breitenerosion_id.value")));

        params.put("einschnitttiefe", toString(bean.getProperty("einschnitttiefe")));
        params.put("wassertiefe", toString(bean.getProperty("wassertiefe")));
        params.put("wasserspiegelbreite", toString(bean.getProperty("wasserspiegelbreite")));
        params.put("sohlenbreite", toString(bean.getProperty("sohlenbreite")));
        params.put("obere_profilbreite", toString(bean.getProperty("obere_profilbreite")));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean    DOCUMENT ME!
     * @param  params  DOCUMENT ME!
     */
    private void retrieveSohlenstrukturParams(final CidsBean bean, final Map params) {
        params.put("sohlenverbau_id", toInteger(bean.getProperty("sohlenverbau_id.value")));
        params.put("z_sohlenverbau_id", toString(bean.getProperty("z_sohlenverbau_id")));

        // ---

        final Number sohlensubstratTON = (Number)bean.getProperty("sohlensubstrat_ton");
        final Number sohlensubstratSAN = (Number)bean.getProperty("sohlensubstrat_san");
        final Number sohlensubstratKIE = (Number)bean.getProperty("sohlensubstrat_kie");
        final Number sohlensubstratSTE = (Number)bean.getProperty("sohlensubstrat_ste");
        final Number sohlensubstratBLO = (Number)bean.getProperty("sohlensubstrat_blo");
        final Number sohlensubstratSCH = (Number)bean.getProperty("sohlensubstrat_sch");
        final Number sohlensubstratTOR = (Number)bean.getProperty("sohlensubstrat_tor");
        final Number sohlensubstratTOT = (Number)bean.getProperty("sohlensubstrat_tot");
        final Number sohlensubstratWUR = (Number)bean.getProperty("sohlensubstrat_wur");
        final Number sohlensubstratKUE = (Number)bean.getProperty("sohlensubstrat_kue");

        final double sohlensubstratSUM = this.convertNumberToDouble(sohlensubstratTON)
                    + this.convertNumberToDouble(sohlensubstratSAN)
                    + this.convertNumberToDouble(sohlensubstratKIE)
                    + this.convertNumberToDouble(sohlensubstratSTE)
                    + this.convertNumberToDouble(sohlensubstratBLO)
                    + this.convertNumberToDouble(sohlensubstratSCH)
                    + this.convertNumberToDouble(sohlensubstratTOR)
                    + this.convertNumberToDouble(sohlensubstratTOT)
                    + this.convertNumberToDouble(sohlensubstratWUR)
                    + this.convertNumberToDouble(sohlensubstratKUE);

        params.put(
            "sohlensubstrat_ton",
            (sohlensubstratSUM == 0.0) ? "" : this.convertNumberToString(sohlensubstratTON));
        params.put(
            "sohlensubstrat_san",
            (sohlensubstratSUM == 0.0) ? "" : this.convertNumberToString(sohlensubstratSAN));
        params.put(
            "sohlensubstrat_kie",
            (sohlensubstratSUM == 0.0) ? "" : this.convertNumberToString(sohlensubstratKIE));
        params.put(
            "sohlensubstrat_ste",
            (sohlensubstratSUM == 0.0) ? "" : this.convertNumberToString(sohlensubstratSTE));
        params.put(
            "sohlensubstrat_blo",
            (sohlensubstratSUM == 0.0) ? "" : this.convertNumberToString(sohlensubstratBLO));
        params.put(
            "sohlensubstrat_sch",
            (sohlensubstratSUM == 0.0) ? "" : this.convertNumberToString(sohlensubstratSCH));
        params.put(
            "sohlensubstrat_tor",
            (sohlensubstratSUM == 0.0) ? "" : this.convertNumberToString(sohlensubstratTOR));
        params.put(
            "sohlensubstrat_tot",
            (sohlensubstratSUM == 0.0) ? "" : this.convertNumberToString(sohlensubstratTOT));
        params.put(
            "sohlensubstrat_wur",
            (sohlensubstratSUM == 0.0) ? "" : this.convertNumberToString(sohlensubstratWUR));
        params.put(
            "sohlensubstrat_kue",
            (sohlensubstratSUM == 0.0) ? "" : this.convertNumberToString(sohlensubstratKUE));

        boolean isNotNull = (sohlensubstratTON != null)
                    && (sohlensubstratSAN != null)
                    && (sohlensubstratKIE != null)
                    && (sohlensubstratSTE != null)
                    && (sohlensubstratBLO != null)
                    && (sohlensubstratSCH != null)
                    && (sohlensubstratTOR != null)
                    && (sohlensubstratTOT != null)
                    && (sohlensubstratWUR != null)
                    && (sohlensubstratKUE != null);

        params.put("sohlensubstrat_ne", (isNotNull && (sohlensubstratSUM == 0.0)) ? "X" : "");

        // ---

        final Number sohlenstrukturenRIP = (Number)bean.getProperty("sohlenstrukturen_rip");
        final Number sohlenstrukturenTH = (Number)bean.getProperty("sohlenstrukturen_th");
        final Number sohlenstrukturenWU = (Number)bean.getProperty("sohlenstrukturen_wu");
        final Number sohlenstrukturenKO = (Number)bean.getProperty("sohlenstrukturen_ko");

        final double sohlenstrukturenSum = this.convertNumberToDouble(sohlenstrukturenRIP)
                    + this.convertNumberToDouble(sohlenstrukturenTH)
                    + this.convertNumberToDouble(sohlenstrukturenWU)
                    + this.convertNumberToDouble(sohlenstrukturenKO);

        params.put(
            "sohlenstrukturen_rip",
            (sohlenstrukturenSum == 0.0) ? "" : this.convertNumberToString(sohlenstrukturenRIP));
        params.put(
            "sohlenstrukturen_th",
            (sohlenstrukturenSum == 0.0) ? "" : this.convertNumberToString(sohlenstrukturenTH));
        params.put(
            "sohlenstrukturen_wu",
            (sohlenstrukturenSum == 0.0) ? "" : this.convertNumberToString(sohlenstrukturenWU));
        params.put(
            "sohlenstrukturen_ko",
            (sohlenstrukturenSum == 0.0) ? "" : this.convertNumberToString(sohlenstrukturenKO));

        isNotNull = (sohlenstrukturenRIP != null)
                    && (sohlenstrukturenTH != null)
                    && (sohlenstrukturenWU != null)
                    && (sohlenstrukturenKO != null);

        params.put("sohlenstrukturen_kein", (isNotNull && (sohlenstrukturenSum == 0.0)) ? "X" : "");

        // ---

        final Number belastungSohleMUE = (Number)bean.getProperty("belastung_sohle_mue");
        final Number belastungSohleST = (Number)bean.getProperty("belastung_sohle_st");
        final Number belastungSohleABW = (Number)bean.getProperty("belastung_sohle_abw");
        final Number belastungSohleVO = (Number)bean.getProperty("belastung_sohle_vo");
        final Number belastungSohleSA = (Number)bean.getProperty("belastung_sohle_sa");
        final Number belastungSohleSO = (Number)bean.getProperty("belastung_sohle_so");

        final double belastungSohleSUM = this.convertNumberToDouble(belastungSohleMUE)
                    + this.convertNumberToDouble(belastungSohleST)
                    + this.convertNumberToDouble(belastungSohleABW)
                    + this.convertNumberToDouble(belastungSohleVO)
                    + this.convertNumberToDouble(belastungSohleSA)
                    + this.convertNumberToDouble(belastungSohleSO);

        params.put(
            "belastung_sohle_mue",
            (belastungSohleSUM == 0.0) ? "" : this.convertNumberToString(belastungSohleMUE));
        params.put(
            "belastung_sohle_st",
            (belastungSohleSUM == 0.0) ? "" : this.convertNumberToString(belastungSohleST));
        params.put(
            "belastung_sohle_abw",
            (belastungSohleSUM == 0.0) ? "" : this.convertNumberToString(belastungSohleABW));
        params.put(
            "belastung_sohle_vo",
            (belastungSohleSUM == 0.0) ? "" : this.convertNumberToString(belastungSohleVO));
        params.put(
            "belastung_sohle_sa",
            (belastungSohleSUM == 0.0) ? "" : this.convertNumberToString(belastungSohleSA));
        params.put(
            "belastung_sohle_so",
            (belastungSohleSUM == 0.0) ? "" : this.convertNumberToString(belastungSohleSO));

        isNotNull = (belastungSohleMUE != null)
                    && (belastungSohleST != null)
                    && (belastungSohleABW != null)
                    && (belastungSohleVO != null)
                    && (belastungSohleSA != null)
                    && (belastungSohleSO != null);

        params.put("belastung_sohle_keine", (isNotNull && (belastungSohleSUM == 0.0)) ? "X" : "");
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean    DOCUMENT ME!
     * @param  params  DOCUMENT ME!
     */
    private void retrieveUferstrukturGewaesserumfeldParams(final CidsBean bean, final Map params) {
        params.put("ufervegetation_links_id", toString(bean.getProperty("ufervegetation_links_id")));
        params.put("ufervegetation_rechts_id", toString(bean.getProperty("ufervegetation_rechts_id")));

        // ---

        Boolean isUferVegetationLinksTypical = (Boolean)bean.getProperty("ufervegetation_links_typical");
        Boolean isUferVegetationRechtsTypical = (Boolean)bean.getProperty("ufervegetation_rechts_typical");

        isUferVegetationLinksTypical = (isUferVegetationLinksTypical == null) ? Boolean.FALSE
                                                                              : isUferVegetationLinksTypical;
        isUferVegetationRechtsTypical = (isUferVegetationRechtsTypical == null) ? Boolean.FALSE
                                                                                : isUferVegetationRechtsTypical;

        if (isUferVegetationLinksTypical != null) {
            params.put("ufervegetation_links_typical", isUferVegetationLinksTypical ? "Ja" : "Nein");
        } else {
            params.put("ufervegetation_links_typical", "ja / nein");
        }

        if (isUferVegetationRechtsTypical != null) {
            params.put("ufervegetation_rechts_typical", isUferVegetationRechtsTypical ? "Ja" : "Nein");
        } else {
            params.put("ufervegetation_rechts_typical", "ja / nein");
        }
        // ---

        params.put("uferverbau_links_id", toInteger(bean.getProperty("uferverbau_links_id.value")));
        params.put("uferverbau_rechts_id", toInteger(bean.getProperty("uferverbau_rechts_id.value")));
        params.put("z_uferverbau_links_id", toString(bean.getProperty("z_uferverbau_links_id")));
        params.put("z_uferverbau_rechts_id", toString(bean.getProperty("z_uferverbau_rechts_id")));

        // ---

        final Number uferstrukturLinksBU = (Number)bean.getProperty("uferstruktur_bu_links");
        final Number uferstrukturLinksPB = (Number)bean.getProperty("uferstruktur_pb_links");
        final Number uferstrukturLinksUS = (Number)bean.getProperty("uferstruktur_us_links");
        final Number uferstrukturLinksSB = (Number)bean.getProperty("uferstruktur_sb_links");
        final Number uferstrukturLinksHA = (Number)bean.getProperty("uferstruktur_ha_links");
        final Number uferstrukturLinksNBOE = (Number)bean.getProperty("uferstruktur_nboe_links");
        final Number uferstrukturLinksSO = (Number)bean.getProperty("uferstruktur_so_links");

        final double uferstrukturLinksSum = this.convertNumberToDouble(uferstrukturLinksBU)
                    + this.convertNumberToDouble(uferstrukturLinksPB)
                    + this.convertNumberToDouble(uferstrukturLinksUS)
                    + this.convertNumberToDouble(uferstrukturLinksSB)
                    + this.convertNumberToDouble(uferstrukturLinksHA)
                    + this.convertNumberToDouble(uferstrukturLinksNBOE)
                    + this.convertNumberToDouble(uferstrukturLinksSO);

        params.put(
            "uferstruktur_bu_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertNumberToString(uferstrukturLinksBU));
        params.put(
            "uferstruktur_pb_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertNumberToString(uferstrukturLinksPB));
        params.put(
            "uferstruktur_us_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertNumberToString(uferstrukturLinksUS));
        params.put(
            "uferstruktur_sb_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertNumberToString(uferstrukturLinksSB));
        params.put(
            "uferstruktur_ha_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertNumberToString(uferstrukturLinksHA));
        params.put(
            "uferstruktur_nboe_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertNumberToString(uferstrukturLinksNBOE));
        params.put(
            "uferstruktur_so_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertNumberToString(uferstrukturLinksSO));

        boolean isNotNull = (uferstrukturLinksBU != null)
                    && (uferstrukturLinksPB != null)
                    && (uferstrukturLinksUS != null)
                    && (uferstrukturLinksSB != null)
                    && (uferstrukturLinksHA != null)
                    && (uferstrukturLinksNBOE != null)
                    && (uferstrukturLinksSO != null);

        params.put("uferstruktur_keine_links", (isNotNull && (uferstrukturLinksSum == 0.0)) ? "X" : "");

        // ---

        final Number uferstrukturRechtsBU = (Number)bean.getProperty("uferstruktur_bu_rechts");
        final Number uferstrukturRechtsPB = (Number)bean.getProperty("uferstruktur_pb_rechts");
        final Number uferstrukturRechtsUS = (Number)bean.getProperty("uferstruktur_us_rechts");
        final Number uferstrukturRechtsSB = (Number)bean.getProperty("uferstruktur_sb_rechts");
        final Number uferstrukturRechtsHA = (Number)bean.getProperty("uferstruktur_ha_rechts");
        final Number uferstrukturRechtsNBOE = (Number)bean.getProperty("uferstruktur_nboe_rechts");
        final Number uferstrukturRechtsSO = (Number)bean.getProperty("uferstruktur_so_rechts");

        final double uferstrukturRechtsSum = this.convertNumberToDouble(uferstrukturRechtsBU)
                    + this.convertNumberToDouble(uferstrukturRechtsPB)
                    + this.convertNumberToDouble(uferstrukturRechtsUS)
                    + this.convertNumberToDouble(uferstrukturRechtsSB)
                    + this.convertNumberToDouble(uferstrukturRechtsHA)
                    + this.convertNumberToDouble(uferstrukturRechtsNBOE)
                    + this.convertNumberToDouble(uferstrukturRechtsSO);

        params.put(
            "uferstruktur_bu_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertNumberToString(uferstrukturRechtsBU));
        params.put(
            "uferstruktur_pb_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertNumberToString(uferstrukturRechtsPB));
        params.put(
            "uferstruktur_us_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertNumberToString(uferstrukturRechtsUS));
        params.put(
            "uferstruktur_sb_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertNumberToString(uferstrukturRechtsSB));
        params.put(
            "uferstruktur_ha_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertNumberToString(uferstrukturRechtsHA));
        params.put(
            "uferstruktur_nboe_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertNumberToString(uferstrukturRechtsNBOE));
        params.put(
            "uferstruktur_so_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertNumberToString(uferstrukturRechtsSO));

        isNotNull = (uferstrukturRechtsBU != null)
                    && (uferstrukturRechtsPB != null)
                    && (uferstrukturRechtsUS != null)
                    && (uferstrukturRechtsSB != null)
                    && (uferstrukturRechtsHA != null)
                    && (uferstrukturRechtsNBOE != null)
                    && (uferstrukturRechtsSO != null);

        params.put("uferstruktur_keine_rechts", (isNotNull && (uferstrukturRechtsSum == 0.0)) ? "X" : "");

        // ---

        final Number uferbelastungenLinksMUE = (Number)bean.getProperty("uferbelastungen_mue_links");
        final Number uferbelastungenLinksST = (Number)bean.getProperty("uferbelastungen_st_links");
        final Number uferbelastungenLinksTS = (Number)bean.getProperty("uferbelastungen_ts_links");
        final Number uferbelastungenLinksEL = (Number)bean.getProperty("uferbelastungen_el_links");
        final Number uferbelastungenLinksSO = (Number)bean.getProperty("uferbelastungen_so_links");

        final double uferbelastungenLinksSum = this.convertNumberToDouble(uferbelastungenLinksMUE)
                    + this.convertNumberToDouble(uferbelastungenLinksST)
                    + this.convertNumberToDouble(uferbelastungenLinksTS)
                    + this.convertNumberToDouble(uferbelastungenLinksEL)
                    + this.convertNumberToDouble(uferbelastungenLinksSO);

        params.put(
            "uferbelastungen_mue_links",
            (uferbelastungenLinksSum == 0.0) ? "" : this.convertNumberToString(uferbelastungenLinksMUE));
        params.put(
            "uferbelastungen_st_links",
            (uferbelastungenLinksSum == 0.0) ? "" : this.convertNumberToString(uferbelastungenLinksST));
        params.put(
            "uferbelastungen_ts_links",
            (uferbelastungenLinksSum == 0.0) ? "" : this.convertNumberToString(uferbelastungenLinksTS));
        params.put(
            "uferbelastungen_el_links",
            (uferbelastungenLinksSum == 0.0) ? "" : this.convertNumberToString(uferbelastungenLinksEL));
        params.put(
            "uferbelastungen_so_links",
            (uferbelastungenLinksSum == 0.0) ? "" : this.convertNumberToString(uferbelastungenLinksSO));

        isNotNull = (uferbelastungenLinksMUE != null)
                    && (uferbelastungenLinksST != null)
                    && (uferbelastungenLinksTS != null)
                    && (uferbelastungenLinksEL != null)
                    && (uferbelastungenLinksSO != null);

        params.put("uferbelastungen_keine_links", (isNotNull && (uferbelastungenLinksSum == 0.0)) ? "X" : "");

        // ---

        final Number uferbelastungenRechtsMUE = (Number)bean.getProperty("uferbelastungen_mue_rechts");
        final Number uferbelastungenRechtsST = (Number)bean.getProperty("uferbelastungen_st_rechts");
        final Number uferbelastungenRechtsTS = (Number)bean.getProperty("uferbelastungen_ts_rechts");
        final Number uferbelastungenRechtsEL = (Number)bean.getProperty("uferbelastungen_el_rechts");
        final Number uferbelastungenRechtsSO = (Number)bean.getProperty("uferbelastungen_so_rechts");

        final double uferbelastungenRechtsSum = this.convertNumberToDouble(uferbelastungenRechtsMUE)
                    + this.convertNumberToDouble(uferbelastungenRechtsST)
                    + this.convertNumberToDouble(uferbelastungenRechtsTS)
                    + this.convertNumberToDouble(uferbelastungenRechtsEL)
                    + this.convertNumberToDouble(uferbelastungenRechtsSO);

        params.put(
            "uferbelastungen_mue_rechts",
            (uferbelastungenRechtsSum == 0.0) ? "" : this.convertNumberToString(uferbelastungenRechtsMUE));
        params.put(
            "uferbelastungen_st_rechts",
            (uferbelastungenRechtsSum == 0.0) ? "" : this.convertNumberToString(uferbelastungenRechtsST));
        params.put(
            "uferbelastungen_ts_rechts",
            (uferbelastungenRechtsSum == 0.0) ? "" : this.convertNumberToString(uferbelastungenRechtsTS));
        params.put(
            "uferbelastungen_el_rechts",
            (uferbelastungenRechtsSum == 0.0) ? "" : this.convertNumberToString(uferbelastungenRechtsEL));
        params.put(
            "uferbelastungen_so_rechts",
            (uferbelastungenRechtsSum == 0.0) ? "" : this.convertNumberToString(uferbelastungenRechtsSO));

        isNotNull = (uferbelastungenRechtsMUE != null)
                    && (uferbelastungenRechtsST != null)
                    && (uferbelastungenRechtsTS != null)
                    && (uferbelastungenRechtsEL != null)
                    && (uferbelastungenRechtsSO != null);

        params.put("uferbelastungen_keine_rechts", (isNotNull && (uferbelastungenRechtsSum == 0.0)) ? "X" : "");

        // ---

        params.put("flaechennutzung_links_id", toString(bean.getProperty("flaechennutzung_links_id")));
        params.put("flaechennutzung_rechts_id", toString(bean.getProperty("flaechennutzung_rechts_id")));

        // ---

        params.put(
            "gewaesserrandstreifen_links_id",
            toString(bean.getProperty("gewaesserrandstreifen_links_id")));
        params.put(
            "gewaesserrandstreifen_rechts_id",
            toString(bean.getProperty("gewaesserrandstreifen_rechts_id")));

        // ---

        final Number umfeldStrukturenLinksFM = (Number)bean.getProperty("umfeldstrukturen_fm_links");
        final Number umfeldStrukturenLinksQ = (Number)bean.getProperty("umfeldstrukturen_q_links");
        final Number umfeldStrukturenLinksAA = (Number)bean.getProperty("umfeldstrukturen_aa_links");
        final Number umfeldStrukturenLinksAW = (Number)bean.getProperty("umfeldstrukturen_aw_links");
        final Number umfeldStrukturenLinksW = (Number)bean.getProperty("umfeldstrukturen_w_links");
        final Number umfeldStrukturenLinksSO = (Number)bean.getProperty("umfeldstrukturen_so_links");

        final double umfeldStrukturenLinksSum = this.convertNumberToDouble(umfeldStrukturenLinksFM)
                    + this.convertNumberToDouble(umfeldStrukturenLinksQ)
                    + this.convertNumberToDouble(umfeldStrukturenLinksAA)
                    + this.convertNumberToDouble(umfeldStrukturenLinksAW)
                    + this.convertNumberToDouble(umfeldStrukturenLinksW)
                    + this.convertNumberToDouble(umfeldStrukturenLinksSO);

        params.put(
            "umfeldstrukturen_fm_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenLinksFM));
        params.put(
            "umfeldstrukturen_q_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenLinksQ));
        params.put(
            "umfeldstrukturen_aa_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenLinksAA));
        params.put(
            "umfeldstrukturen_aw_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenLinksAW));
        params.put(
            "umfeldstrukturen_w_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenLinksW));
        params.put(
            "umfeldstrukturen_so_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenLinksSO));

        isNotNull = (umfeldStrukturenLinksFM != null)
                    && (umfeldStrukturenLinksQ != null)
                    && (umfeldStrukturenLinksAA != null)
                    && (umfeldStrukturenLinksAW != null)
                    && (umfeldStrukturenLinksW != null)
                    && (umfeldStrukturenLinksSO != null);

        params.put("umfeldstrukturen_keine_links", (isNotNull && (umfeldStrukturenLinksSum == 0.0)) ? "X" : "");

        // ---

        final Number umfeldStrukturenRechtsFM = (Number)bean.getProperty("umfeldstrukturen_fm_rechts");
        final Number umfeldStrukturenRechtsQ = (Number)bean.getProperty("umfeldstrukturen_q_rechts");
        final Number umfeldStrukturenRechtsAA = (Number)bean.getProperty("umfeldstrukturen_aa_rechts");
        final Number umfeldStrukturenRechtsAW = (Number)bean.getProperty("umfeldstrukturen_aw_rechts");
        final Number umfeldStrukturenRechtsW = (Number)bean.getProperty("umfeldstrukturen_w_rechts");
        final Number umfeldStrukturenRechtsSO = (Number)bean.getProperty("umfeldstrukturen_so_rechts");

        final double umfeldStrukturenRechtsSum = this.convertNumberToDouble(umfeldStrukturenRechtsFM)
                    + this.convertNumberToDouble(umfeldStrukturenRechtsQ)
                    + this.convertNumberToDouble(umfeldStrukturenRechtsAA)
                    + this.convertNumberToDouble(umfeldStrukturenRechtsAW)
                    + this.convertNumberToDouble(umfeldStrukturenRechtsW)
                    + this.convertNumberToDouble(umfeldStrukturenRechtsSO);

        params.put(
            "umfeldstrukturen_fm_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenRechtsFM));
        params.put(
            "umfeldstrukturen_q_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenRechtsQ));
        params.put(
            "umfeldstrukturen_aa_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenRechtsAA));
        params.put(
            "umfeldstrukturen_aw_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenRechtsAW));
        params.put(
            "umfeldstrukturen_w_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenRechtsW));
        params.put(
            "umfeldstrukturen_so_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(umfeldStrukturenRechtsSO));

        isNotNull = (umfeldStrukturenRechtsFM != null)
                    && (umfeldStrukturenRechtsQ != null)
                    && (umfeldStrukturenRechtsAA != null)
                    && (umfeldStrukturenRechtsAW != null)
                    && (umfeldStrukturenRechtsW != null)
                    && (umfeldStrukturenRechtsSO != null);

        params.put("umfeldstrukturen_keine_rechts", (isNotNull && (umfeldStrukturenRechtsSum == 0.0)) ? "X" : "");

        // ---

        final Number sUmfeldStrukturenLinksAG = (Number)bean.getProperty("s_umfeldstrukturen_ag_links");
        final Number sUmfeldStrukturenLinksFT = (Number)bean.getProperty("s_umfeldstrukturen_ft_links");
        final Number sUmfeldStrukturenLinksGUA = (Number)bean.getProperty("s_umfeldstrukturen_gua_links");
        final Number sUmfeldStrukturenLinksBV = (Number)bean.getProperty("s_umfeldstrukturen_bv_links");
        final Number sUmfeldStrukturenLinksMA = (Number)bean.getProperty("s_umfeldstrukturen_ma_links");
        final Number sUmfeldStrukturenLinksHW = (Number)bean.getProperty("s_umfeldstrukturen_hw_links");
        final Number sUmfeldStrukturenLinksSO = (Number)bean.getProperty("s_umfeldstrukturen_so_links");

        final double sUmfeldStrukturenLinksSum = this.convertNumberToDouble(sUmfeldStrukturenLinksAG)
                    + this.convertNumberToDouble(sUmfeldStrukturenLinksFT)
                    + this.convertNumberToDouble(sUmfeldStrukturenLinksGUA)
                    + this.convertNumberToDouble(sUmfeldStrukturenLinksBV)
                    + this.convertNumberToDouble(sUmfeldStrukturenLinksMA)
                    + this.convertNumberToDouble(sUmfeldStrukturenLinksHW)
                    + this.convertNumberToDouble(sUmfeldStrukturenLinksSO);

        params.put(
            "s_umfeldstrukturen_ag_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenLinksAG));
        params.put(
            "s_umfeldstrukturen_ft_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenLinksFT));
        params.put(
            "s_umfeldstrukturen_gua_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenLinksGUA));
        params.put(
            "s_umfeldstrukturen_bv_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenLinksBV));
        params.put(
            "s_umfeldstrukturen_ma_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenLinksMA));
        params.put(
            "s_umfeldstrukturen_hw_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenLinksHW));
        params.put(
            "s_umfeldstrukturen_so_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenLinksSO));

        isNotNull = (sUmfeldStrukturenLinksAG != null)
                    && (sUmfeldStrukturenLinksFT != null)
                    && (sUmfeldStrukturenLinksGUA != null)
                    && (sUmfeldStrukturenLinksBV != null)
                    && (sUmfeldStrukturenLinksMA != null)
                    && (sUmfeldStrukturenLinksHW != null)
                    && (sUmfeldStrukturenLinksSO != null);

        params.put("s_umfeldstrukturen_keine_links", (isNotNull && (sUmfeldStrukturenLinksSum == 0.0)) ? "X" : "");

        // ---

        final Number sUmfeldStrukturenRechtsAG = (Number)bean.getProperty("s_umfeldstrukturen_ag_rechts");
        final Number sUmfeldStrukturenRechtsFT = (Number)bean.getProperty("s_umfeldstrukturen_ft_rechts");
        final Number sUmfeldStrukturenRechtsGUA = (Number)bean.getProperty("s_umfeldstrukturen_gua_rechts");
        final Number sUmfeldStrukturenRechtsBV = (Number)bean.getProperty("s_umfeldstrukturen_bv_rechts");
        final Number sUmfeldStrukturenRechtsMA = (Number)bean.getProperty("s_umfeldstrukturen_ma_rechts");
        final Number sUmfeldStrukturenRechtsHW = (Number)bean.getProperty("s_umfeldstrukturen_hw_rechts");
        final Number sUmfeldStrukturenRechtsSO = (Number)bean.getProperty("s_umfeldstrukturen_so_rechts");

        final double sUmfeldStrukturenRechtsSum = this.convertNumberToDouble(sUmfeldStrukturenRechtsAG)
                    + this.convertNumberToDouble(sUmfeldStrukturenRechtsFT)
                    + this.convertNumberToDouble(sUmfeldStrukturenRechtsGUA)
                    + this.convertNumberToDouble(sUmfeldStrukturenRechtsBV)
                    + this.convertNumberToDouble(sUmfeldStrukturenRechtsMA)
                    + this.convertNumberToDouble(sUmfeldStrukturenRechtsHW)
                    + this.convertNumberToDouble(sUmfeldStrukturenRechtsSO);

        params.put(
            "s_umfeldstrukturen_ag_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenRechtsAG));
        params.put(
            "s_umfeldstrukturen_ft_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenRechtsFT));
        params.put(
            "s_umfeldstrukturen_gua_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenRechtsGUA));
        params.put(
            "s_umfeldstrukturen_bv_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenRechtsBV));
        params.put(
            "s_umfeldstrukturen_ma_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenRechtsMA));
        params.put(
            "s_umfeldstrukturen_hw_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenRechtsHW));
        params.put(
            "s_umfeldstrukturen_so_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertNumberToString(sUmfeldStrukturenRechtsSO));

        isNotNull = (sUmfeldStrukturenRechtsAG != null)
                    && (sUmfeldStrukturenRechtsFT != null)
                    && (sUmfeldStrukturenRechtsGUA != null)
                    && (sUmfeldStrukturenRechtsBV != null)
                    && (sUmfeldStrukturenRechtsMA != null)
                    && (sUmfeldStrukturenRechtsHW != null)
                    && (sUmfeldStrukturenRechtsSO != null);

        params.put("s_umfeldstrukturen_keine_rechts", (isNotNull && (sUmfeldStrukturenRechtsSum == 0.0)) ? "X" : "");

        // ---

        final Object beschreibung = bean.getProperty("beschreibung");
        params.put("beschreibung", (beschreibung == null) ? "" : toString(beschreibung));
    }

    @Override
    public Map generateReportParam(final CidsBean current) {
        final HashMap params = new HashMap();

        this.retrieveKartierabschnittParams(current, params);
        this.retrieveLaufentwicklungParams(current, params);
        this.retrieveLaengsprofilParams(current, params);
        this.retrieveQuerprofilParams(current, params);
        this.retrieveSohlenstrukturParams(current, params);
        this.retrieveUferstrukturGewaesserumfeldParams(current, params);

        return params;
    }

    @Override
    public Map generateReportParam(final Collection<CidsBean> beans) {
        return Collections.EMPTY_MAP;
    }

    /**
     * Creates all wk_fg reports and saves the files in the given directory.
     *
     * @param  directory   the directory to save the reports
     * @param  expression  DOCUMENT ME!
     */
    public static void createAllReports(final String directory, final String expression) {
        final MetaClass fgskMc = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "fgsk_kartierabschnitt");
        final ArrayList ids = new ArrayList();

        try {
            final CidsServerSearch search = new FgskIdSearch(expression);
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

            if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                for (final ArrayList l : resArray) {
                    ids.add(l.get(0));
                }
            }
        } catch (ConnectionException e) {
        }

        ExecutorService executor = Executors.newFixedThreadPool(5);
        int index = 1;

        try {
            for (final Object id : ids) {
                final Integer oId = (Integer)id;
                final MetaObject wkFgObject = SessionManager.getProxy()
                            .getMetaObject(oId, fgskMc.getID(), WRRLUtil.DOMAIN_NAME);
                final CidsBean bean = wkFgObject.getBean();
                final String filename = (directory.endsWith("/") ? directory : (directory + "/")) + bean.toString()
                            + ".pdf";

                executor.execute(new Runnable() {

                        @Override
                        public void run() {
                            final FgskReport r = new FgskReport(bean, filename);
                            r.print();
                        }
                    });
                ++index;

                if ((index % 100) == 0) {
                    executor.shutdown();
                    executor.awaitTermination(1, TimeUnit.DAYS);
                    executor = Executors.newFixedThreadPool(5);
                }
            }
        } catch (Exception e) {
            LOG.error("Error while creating all fgsk reports", e);
            System.exit(1);
        }

        try {
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            // nothing to do
        }

        System.exit(0);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  the command line arguments
     */
    public static void main(final String[] args) {
        try {
            String expression = null;

            if (args.length == 0) {
                System.out.println("Als Parameter muss der Pfad zur Konfigurationsdatei angegeben werden");
                System.exit(1);
            }

            if (args.length == 2) {
                expression = args[1];
            }

            // read the properties
            final Properties properties = new Properties();
            final Reader propertiesReader = new FileReader(args[0]);
            properties.load(propertiesReader);

            // login
            final ConnectionInfo connectionInfo = new ConnectionInfo();
            connectionInfo.setCallserverURL(properties.getProperty("callserver"));
            connectionInfo.setPassword(properties.getProperty("password"));
            connectionInfo.setUserDomain(properties.getProperty("userDomain"));
            connectionInfo.setUsergroup(properties.getProperty("userGroup"));
            connectionInfo.setUsergroupDomain(properties.getProperty("userGroupDomain"));
            connectionInfo.setUsername(properties.getProperty("username"));

            final Connection connection = ConnectionFactory.getFactory()
                        .createConnection(
                            properties.getProperty("connectionClass"),
                            properties.getProperty("callserver"),
                            null,
                            "true".equals(properties.getProperty("compressionEnabled")));
            final ConnectionSession session;
            final ConnectionProxy proxy;

            try {
                session = ConnectionFactory.getFactory().createSession(connection, connectionInfo, true);
                proxy = ConnectionFactory.getFactory()
                            .createProxy("Sirius.navigator.connection.proxy.DefaultConnectionProxyHandler", session);
                SessionManager.init(proxy);
            } catch (UserException uexp) {
                LOG.error("autologin failed", uexp); // NOI18N
                System.exit(1);
            }

            ReportUtils.initCismap();

            // create the reports
            createAllReports(properties.getProperty("report_directory"), expression);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
