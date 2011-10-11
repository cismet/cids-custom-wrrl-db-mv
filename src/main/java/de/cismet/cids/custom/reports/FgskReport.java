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

import Sirius.navigator.connection.SessionManager;

import Sirius.server.ServerExit;
import Sirius.server.middleware.impls.domainserver.DomainServerImpl;
import Sirius.server.middleware.impls.proxy.StartProxy;
import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.newuser.User;
import Sirius.server.newuser.UserGroup;
import Sirius.server.property.ServerProperties;
import Sirius.server.registry.Registry;
import Sirius.server.search.CidsServerSearch;

import com.vividsolutions.jts.geom.Geometry;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

import org.apache.log4j.Logger;

import org.openide.util.Exceptions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.math.BigDecimal;

import java.rmi.RemoteException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.WkkSearch;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   jweintraut
 * @version  $Revision$, $Date$
 */
public final class FgskReport extends AbstractJasperReportPrint {

    //~ Static fields/initializers ---------------------------------------------

    private static final String REPORT_URL = "/de/cismet/cids/custom/reports/fgsk.jasper";
    private static transient DomainServerImpl server;
    private static User user;
    private static final String SERVER_CONFIG = "/opt/cidsdistribution/server/linux/kif_WRRLDBMV/runtime.properties";
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wk_fg");
    private static final Logger LOG = Logger.getLogger(FgskReport.class);
    private static Registry registry;
    private static StartProxy proxy;

    private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new StadtbildJasperReportPrint object.
     *
     * @param  beans  DOCUMENT ME!
     */
    public FgskReport(final Collection<CidsBean> beans) {
        super(REPORT_URL, beans);
        setBeansCollection(false);
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

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    public void showReport(final CidsBean cidsBean) {
        try {
            final ArrayList<String> reports = new ArrayList<String>();
            reports.add(REPORT_URL);
            final HashMap<String, JRDataSource> dataSourcesMap = new HashMap<String, JRDataSource>(reports.size());
            dataSourcesMap.put(REPORT_URL, new JREmptyDataSource(1));

            final ReportSwingWorker worker = new ReportSwingWorker(
                    reports,
                    dataSourcesMap,
                    generateReportParam(cidsBean),
                    true,
                    new JFrame(),
                    "/tmp");
            // worker.execute();
            worker.doInBackground();

            System.out.println("worker executed");
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

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
     */
    private double convertBiGDecimalToDouble(final BigDecimal obj) {
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
    private String convertBiGDecimalToString(final BigDecimal obj) {
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
        String wkType = CidsBeanSupport.FIELD_NOT_SET;
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
                            wkType = toString(metaObjects[0].getBean().getProperty("typ_k"));
                        } else {
                            wkName = "nicht ermittelbar";
                            wkType = "nicht ermittelbar";
                        }
                    }
                } else {
                    LOG.error("Server error in getWk_k(). Cids server search return null. " // NOI18N
                                + "See the server logs for further information");     // NOI18N
                }
            } catch (final Exception e) {
                LOG.error("Error while determining the water body", e);
            }

            params.put("gew", gew);       // Gewässername
            params.put("gwk", gwk);       // Gewässerkennzahl
            params.put("wkk", wkk);       // Wasserkörper
            params.put("wkName", wkName); // Wasserkörpername TODO: benötigt?
            params.put("wkType", wkType); // Wk-Type
            params.put("stationierung", statString);

            params.put("gewaesser_abschnitt", toString(bean.getProperty("gewaesser_abschnitt")));
            params.put("bearbeiter", toString(bean.getProperty("bearbeiter")));
            params.put("erfassungsdatum", DF.format(bean.getProperty("erfassungsdatum")));
            params.put("fliessrichtung_id", toString(bean.getProperty("fliessrichtung_id")));

            params.put("wasserfuehrung_id", toString(bean.getProperty("wasserfuehrung_id")));
            params.put("foto_nr", toString(bean.getProperty("foto_nr")));

            Boolean unterk = null;
            final Object unterkObj = bean.getProperty("unterhaltungerkennbar");
            if (unterkObj != null) {
                unterk = (Boolean)unterkObj;
                params.put("unterhaltungerkennbar", unterk ? "ja" : "nein");
            }

            final Integer sonderfallId = (Integer)bean.getProperty("sonderfall_id.value");

            params.put("sonderfall_id", (sonderfallId == null) ? Integer.valueOf(-1) : sonderfallId);
            params.put("erlaeuterung", toString(bean.getProperty("erlaeuterung")));
            params.put("gewaesserbreite_id", bean.getProperty("gewaesserbreite_id.value"));
            params.put("gewaessertyp_id", toString(bean.getProperty("gewaessertyp_id.value")));

            final List<CidsBean> subTypes = (List<CidsBean>)bean.getProperty("gewaessersubtyp");
            final ArrayList<String> subTypesStringList = new ArrayList<String>(subTypes.size());

            for (final CidsBean subTypeBean : subTypes) {
                subTypesStringList.add(toString(subTypeBean.getProperty("value")));
            }

            params.put("gewaessersubtyp", subTypesStringList);

            final Boolean seeausfluss = (Boolean)bean.getProperty("seeausfluss");
            params.put("seeausfluss", (seeausfluss == null) ? Boolean.FALSE : seeausfluss);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean    DOCUMENT ME!
     * @param  params  DOCUMENT ME!
     */
    private void retrieveLaufentwicklungParams(final CidsBean bean, final Map params) {
        params.put("laufkruemmung_id", bean.getProperty("laufkruemmung_id.value"));         // Integer: 1 -
                                                                                            // mäandrierend usw.
        params.put("kruemmungserosion_id", bean.getProperty("kruemmungserosion_id.value")); // Integer: 1 - häufig
                                                                                            // stark usw.

        // -- Handling Längsbänke

        final BigDecimal laengsbaenkeUFGK = (BigDecimal)bean.getProperty("laengsbaenke_ufkg");
        final BigDecimal laengsbaenkeIB = (BigDecimal)bean.getProperty("laengsbaenke_ib");
        final BigDecimal laengsbaenkeMB = (BigDecimal)bean.getProperty("laengsbaenke_mb");

        final double laengsbaenkeSum = this.convertBiGDecimalToDouble(laengsbaenkeIB)
                    + this.convertBiGDecimalToDouble(laengsbaenkeMB)
                    + this.convertBiGDecimalToDouble(laengsbaenkeUFGK);

        params.put(
            "laengsbaenke_ufkg",
            (laengsbaenkeSum == 0.0) ? "" : this.convertBiGDecimalToString(laengsbaenkeUFGK));
        params.put("laengsbaenke_ib", (laengsbaenkeSum == 0.0) ? "" : this.convertBiGDecimalToString(laengsbaenkeIB));
        params.put("laengsbaenke_mb", (laengsbaenkeSum == 0.0) ? "" : this.convertBiGDecimalToString(laengsbaenkeMB));
        params.put("laengsbaenke_keine", (laengsbaenkeSum == 0.0) ? "X" : "");

        // -- Handling Laufstrukturen

        final BigDecimal laufstrukturenTV = (BigDecimal)bean.getProperty("laufstrukturen_tv");
        final BigDecimal laufstrukturenSB = (BigDecimal)bean.getProperty("laufstrukturen_sb");
        final BigDecimal laufstrukturenIBI = (BigDecimal)bean.getProperty("laufstrukturen_ibi");
        final BigDecimal laufstrukturenLW = (BigDecimal)bean.getProperty("laufstrukturen_lw");
        final BigDecimal laufstrukturenLV = (BigDecimal)bean.getProperty("laufstrukturen_lv");
        final BigDecimal laufstrukturenLG = (BigDecimal)bean.getProperty("laufstrukturen_lg");

        final double laufstrukturenSum = this.convertBiGDecimalToDouble(laufstrukturenTV)
                    + this.convertBiGDecimalToDouble(laufstrukturenSB)
                    + this.convertBiGDecimalToDouble(laufstrukturenIBI)
                    + this.convertBiGDecimalToDouble(laufstrukturenLW)
                    + this.convertBiGDecimalToDouble(laufstrukturenLV)
                    + this.convertBiGDecimalToDouble(laufstrukturenLG);

        params.put(
            "laufstrukturen_tv",
            (laufstrukturenSum == 0.0) ? "" : this.convertBiGDecimalToString(laufstrukturenTV));
        params.put(
            "laufstrukturen_sb",
            (laufstrukturenSum == 0.0) ? "" : this.convertBiGDecimalToString(laufstrukturenSB));
        params.put(
            "laufstrukturen_ibi",
            (laufstrukturenSum == 0.0) ? "" : this.convertBiGDecimalToString(laufstrukturenIBI));
        params.put(
            "laufstrukturen_lw",
            (laufstrukturenSum == 0.0) ? "" : this.convertBiGDecimalToString(laufstrukturenLW));
        params.put(
            "laufstrukturen_lv",
            (laufstrukturenSum == 0.0) ? "" : this.convertBiGDecimalToString(laufstrukturenLV));
        params.put(
            "laufstrukturen_lg",
            (laufstrukturenSum == 0.0) ? "" : this.convertBiGDecimalToString(laufstrukturenLG));
        params.put("laufstrukturen_keine", (laufstrukturenSum == 0.0) ? "X" : "");
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean    DOCUMENT ME!
     * @param  params  DOCUMENT ME!
     */
    private void retrieveLaengsprofilParams(final CidsBean bean, final Map params) {
        params.put("fliessgeschwindigkeit_id", bean.getProperty("fliessgeschwindigkeit_id.value"));
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
        params.put("profiltyp_id", bean.getProperty("profiltyp_id.value"));
        params.put("breitenvarianz_id", bean.getProperty("breitenvarianz_id.value"));
        params.put("breitenerosion_id", bean.getProperty("breitenerosion_id.value"));

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
        params.put("sohlenverbau_id", bean.getProperty("sohlenverbau_id.value"));
        params.put("z_sohlenverbau_id", toString(bean.getProperty("z_sohlenverbau_id")));

        // ---

        final BigDecimal sohlensubstratTON = (BigDecimal)bean.getProperty("sohlensubstrat_ton");
        final BigDecimal sohlensubstratSAN = (BigDecimal)bean.getProperty("sohlensubstrat_san");
        final BigDecimal sohlensubstratKIE = (BigDecimal)bean.getProperty("sohlensubstrat_kie");
        final BigDecimal sohlensubstratSTE = (BigDecimal)bean.getProperty("sohlensubstrat_ste");
        final BigDecimal sohlensubstratBLO = (BigDecimal)bean.getProperty("sohlensubstrat_blo");
        final BigDecimal sohlensubstratSCH = (BigDecimal)bean.getProperty("sohlensubstrat_sch");
        final BigDecimal sohlensubstratTOR = (BigDecimal)bean.getProperty("sohlensubstrat_tor");
        final BigDecimal sohlensubstratTOT = (BigDecimal)bean.getProperty("sohlensubstrat_tot");
        final BigDecimal sohlensubstratWUR = (BigDecimal)bean.getProperty("sohlensubstrat_wur");
        final BigDecimal sohlensubstratKUE = (BigDecimal)bean.getProperty("sohlensubstrat_kue");

        final double sohlensubstratSUM = this.convertBiGDecimalToDouble(sohlensubstratTON)
                    + this.convertBiGDecimalToDouble(sohlensubstratSAN)
                    + this.convertBiGDecimalToDouble(sohlensubstratKIE)
                    + this.convertBiGDecimalToDouble(sohlensubstratSTE)
                    + this.convertBiGDecimalToDouble(sohlensubstratBLO)
                    + this.convertBiGDecimalToDouble(sohlensubstratSCH)
                    + this.convertBiGDecimalToDouble(sohlensubstratTOR)
                    + this.convertBiGDecimalToDouble(sohlensubstratTOT)
                    + this.convertBiGDecimalToDouble(sohlensubstratWUR)
                    + this.convertBiGDecimalToDouble(sohlensubstratKUE);

        params.put(
            "sohlensubstrat_ton",
            (sohlensubstratSUM == 0.0) ? "" : this.convertBiGDecimalToString(sohlensubstratTON));
        params.put(
            "sohlensubstrat_san",
            (sohlensubstratSUM == 0.0) ? "" : this.convertBiGDecimalToString(sohlensubstratSAN));
        params.put(
            "sohlensubstrat_kie",
            (sohlensubstratSUM == 0.0) ? "" : this.convertBiGDecimalToString(sohlensubstratKIE));
        params.put(
            "sohlensubstrat_ste",
            (sohlensubstratSUM == 0.0) ? "" : this.convertBiGDecimalToString(sohlensubstratSTE));
        params.put(
            "sohlensubstrat_blo",
            (sohlensubstratSUM == 0.0) ? "" : this.convertBiGDecimalToString(sohlensubstratBLO));
        params.put(
            "sohlensubstrat_sch",
            (sohlensubstratSUM == 0.0) ? "" : this.convertBiGDecimalToString(sohlensubstratSCH));
        params.put(
            "sohlensubstrat_tor",
            (sohlensubstratSUM == 0.0) ? "" : this.convertBiGDecimalToString(sohlensubstratTOR));
        params.put(
            "sohlensubstrat_tot",
            (sohlensubstratSUM == 0.0) ? "" : this.convertBiGDecimalToString(sohlensubstratTOT));
        params.put(
            "sohlensubstrat_wur",
            (sohlensubstratSUM == 0.0) ? "" : this.convertBiGDecimalToString(sohlensubstratWUR));
        params.put(
            "sohlensubstrat_kue",
            (sohlensubstratSUM == 0.0) ? "" : this.convertBiGDecimalToString(sohlensubstratKUE));
        params.put("sohlensubstrat_ne", (sohlensubstratSUM == 0.0) ? "X" : "");

        // ---

        final BigDecimal sohlenstrukturenRIP = (BigDecimal)bean.getProperty("sohlenstrukturen_rip");
        final BigDecimal sohlenstrukturenTH = (BigDecimal)bean.getProperty("sohlenstrukturen_th");
        final BigDecimal sohlenstrukturenWU = (BigDecimal)bean.getProperty("sohlenstrukturen_wu");
        final BigDecimal sohlenstrukturenKO = (BigDecimal)bean.getProperty("sohlenstrukturen_ko");

        final double sohlenstrukturenSum = this.convertBiGDecimalToDouble(sohlenstrukturenRIP)
                    + this.convertBiGDecimalToDouble(sohlenstrukturenTH)
                    + this.convertBiGDecimalToDouble(sohlenstrukturenWU)
                    + this.convertBiGDecimalToDouble(sohlenstrukturenKO);

        params.put(
            "sohlenstrukturen_rip",
            (sohlenstrukturenSum == 0.0) ? "" : this.convertBiGDecimalToString(sohlenstrukturenRIP));
        params.put(
            "sohlenstrukturen_th",
            (sohlenstrukturenSum == 0.0) ? "" : this.convertBiGDecimalToString(sohlenstrukturenTH));
        params.put(
            "sohlenstrukturen_wu",
            (sohlenstrukturenSum == 0.0) ? "" : this.convertBiGDecimalToString(sohlenstrukturenWU));
        params.put(
            "sohlenstrukturen_ko",
            (sohlenstrukturenSum == 0.0) ? "" : this.convertBiGDecimalToString(sohlenstrukturenKO));
        params.put("sohlenstrukturen_kein", (sohlenstrukturenSum == 0.0) ? "X" : "");

        // ---

        final BigDecimal belastungSohleMUE = (BigDecimal)bean.getProperty("belastung_sohle_mue");
        final BigDecimal belastungSohleST = (BigDecimal)bean.getProperty("belastung_sohle_st");
        final BigDecimal belastungSohleABW = (BigDecimal)bean.getProperty("belastung_sohle_abw");
        final BigDecimal belastungSohleVO = (BigDecimal)bean.getProperty("belastung_sohle_vo");
        final BigDecimal belastungSohleSA = (BigDecimal)bean.getProperty("belastung_sohle_sa");
        final BigDecimal belastungSohleSO = (BigDecimal)bean.getProperty("belastung_sohle_so");

        final double belastungSohleSUM = this.convertBiGDecimalToDouble(belastungSohleMUE)
                    + this.convertBiGDecimalToDouble(belastungSohleST)
                    + this.convertBiGDecimalToDouble(belastungSohleABW)
                    + this.convertBiGDecimalToDouble(belastungSohleVO)
                    + this.convertBiGDecimalToDouble(belastungSohleSA)
                    + this.convertBiGDecimalToDouble(belastungSohleSO);

        params.put(
            "belastung_sohle_mue",
            (belastungSohleSUM == 0.0) ? "" : this.convertBiGDecimalToString(belastungSohleMUE));
        params.put(
            "belastung_sohle_st",
            (belastungSohleSUM == 0.0) ? "" : this.convertBiGDecimalToString(belastungSohleST));
        params.put(
            "belastung_sohle_abw",
            (belastungSohleSUM == 0.0) ? "" : this.convertBiGDecimalToString(belastungSohleABW));
        params.put(
            "belastung_sohle_vo",
            (belastungSohleSUM == 0.0) ? "" : this.convertBiGDecimalToString(belastungSohleVO));
        params.put(
            "belastung_sohle_sa",
            (belastungSohleSUM == 0.0) ? "" : this.convertBiGDecimalToString(belastungSohleSA));
        params.put(
            "belastung_sohle_so",
            (belastungSohleSUM == 0.0) ? "" : this.convertBiGDecimalToString(belastungSohleSO));
        params.put("belastung_sohle_keine", (belastungSohleSUM == 0.0) ? "X" : "");
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

        final Boolean isUferVegetationLinksTypical = (Boolean)bean.getProperty("ufervegetation_links_typical");
        final Boolean isUferVegetationRechtsTypical = (Boolean)bean.getProperty("ufervegetation_rechts_typical");
        params.put("ufervegetation_links_typical", isUferVegetationLinksTypical ? "Ja" : "Nein");
        params.put("ufervegetation_rechts_typical", isUferVegetationRechtsTypical ? "Ja" : "Nein");

        // ---

        params.put("uferverbau_links_id", (Integer)bean.getProperty("uferverbau_links_id.value"));
        params.put("uferverbau_rechts_id", (Integer)bean.getProperty("uferverbau_rechts_id.value"));
        params.put("z_uferverbau_links_id", toString(bean.getProperty("z_uferverbau_links_id")));
        params.put("z_uferverbau_rechts_id", toString(bean.getProperty("z_uferverbau_rechts_id")));

        // ---

        final BigDecimal uferstrukturLinksBU = (BigDecimal)bean.getProperty("uferstruktur_bu_links");
        final BigDecimal uferstrukturLinksPB = (BigDecimal)bean.getProperty("uferstruktur_pb_links");
        final BigDecimal uferstrukturLinksUS = (BigDecimal)bean.getProperty("uferstruktur_us_links");
        final BigDecimal uferstrukturLinksSB = (BigDecimal)bean.getProperty("uferstruktur_sb_links");
        final BigDecimal uferstrukturLinksHA = (BigDecimal)bean.getProperty("uferstruktur_ha_links");
        final BigDecimal uferstrukturLinksNBOE = (BigDecimal)bean.getProperty("uferstruktur_nboe_links");
        final BigDecimal uferstrukturLinksSO = (BigDecimal)bean.getProperty("uferstruktur_so_links");

        final double uferstrukturLinksSum = this.convertBiGDecimalToDouble(uferstrukturLinksBU)
                    + this.convertBiGDecimalToDouble(uferstrukturLinksPB)
                    + this.convertBiGDecimalToDouble(uferstrukturLinksUS)
                    + this.convertBiGDecimalToDouble(uferstrukturLinksSB)
                    + this.convertBiGDecimalToDouble(uferstrukturLinksHA)
                    + this.convertBiGDecimalToDouble(uferstrukturLinksNBOE)
                    + this.convertBiGDecimalToDouble(uferstrukturLinksSO);

        params.put(
            "uferstruktur_bu_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturLinksBU));
        params.put(
            "uferstruktur_pb_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturLinksPB));
        params.put(
            "uferstruktur_us_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturLinksUS));
        params.put(
            "uferstruktur_sb_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturLinksSB));
        params.put(
            "uferstruktur_ha_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturLinksHA));
        params.put(
            "uferstruktur_nboe_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturLinksNBOE));
        params.put(
            "uferstruktur_so_links",
            (uferstrukturLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturLinksSO));
        params.put("uferstruktur_keine_links", (uferstrukturLinksSum == 0.0) ? "X" : "");

        // ---

        final BigDecimal uferstrukturRechtsBU = (BigDecimal)bean.getProperty("uferstruktur_bu_rechts");
        final BigDecimal uferstrukturRechtsPB = (BigDecimal)bean.getProperty("uferstruktur_pb_rechts");
        final BigDecimal uferstrukturRechtsUS = (BigDecimal)bean.getProperty("uferstruktur_us_rechts");
        final BigDecimal uferstrukturRechtsSB = (BigDecimal)bean.getProperty("uferstruktur_sb_rechts");
        final BigDecimal uferstrukturRechtsHA = (BigDecimal)bean.getProperty("uferstruktur_ha_rechts");
        final BigDecimal uferstrukturRechtsNBOE = (BigDecimal)bean.getProperty("uferstruktur_nboe_rechts");
        final BigDecimal uferstrukturRechtsSO = (BigDecimal)bean.getProperty("uferstruktur_so_rechts");

        final double uferstrukturRechtsSum = this.convertBiGDecimalToDouble(uferstrukturRechtsBU)
                    + this.convertBiGDecimalToDouble(uferstrukturRechtsPB)
                    + this.convertBiGDecimalToDouble(uferstrukturRechtsUS)
                    + this.convertBiGDecimalToDouble(uferstrukturRechtsSB)
                    + this.convertBiGDecimalToDouble(uferstrukturRechtsHA)
                    + this.convertBiGDecimalToDouble(uferstrukturRechtsNBOE)
                    + this.convertBiGDecimalToDouble(uferstrukturRechtsSO);

        params.put(
            "uferstruktur_bu_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturRechtsBU));
        params.put(
            "uferstruktur_pb_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturRechtsPB));
        params.put(
            "uferstruktur_us_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturRechtsUS));
        params.put(
            "uferstruktur_sb_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturRechtsSB));
        params.put(
            "uferstruktur_ha_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturRechtsHA));
        params.put(
            "uferstruktur_nboe_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturRechtsNBOE));
        params.put(
            "uferstruktur_so_rechts",
            (uferstrukturRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferstrukturRechtsSO));
        params.put("uferstruktur_keine_rechts", (uferstrukturRechtsSum == 0.0) ? "X" : "");

        // ---

        final BigDecimal uferbelastungenLinksMUE = (BigDecimal)bean.getProperty("uferbelastungen_mue_links");
        final BigDecimal uferbelastungenLinksST = (BigDecimal)bean.getProperty("uferbelastungen_st_links");
        final BigDecimal uferbelastungenLinksTS = (BigDecimal)bean.getProperty("uferbelastungen_ts_links");
        final BigDecimal uferbelastungenLinksEL = (BigDecimal)bean.getProperty("uferbelastungen_el_links");
        final BigDecimal uferbelastungenLinksSO = (BigDecimal)bean.getProperty("uferbelastungen_so_links");

        final double uferbelastungenLinksSum = this.convertBiGDecimalToDouble(uferbelastungenLinksMUE)
                    + this.convertBiGDecimalToDouble(uferbelastungenLinksST)
                    + this.convertBiGDecimalToDouble(uferbelastungenLinksTS)
                    + this.convertBiGDecimalToDouble(uferbelastungenLinksEL)
                    + this.convertBiGDecimalToDouble(uferbelastungenLinksSO);

        params.put(
            "uferbelastungen_mue_links",
            (uferbelastungenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferbelastungenLinksMUE));
        params.put(
            "uferbelastungen_st_links",
            (uferbelastungenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferbelastungenLinksST));
        params.put(
            "uferbelastungen_ts_links",
            (uferbelastungenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferbelastungenLinksTS));
        params.put(
            "uferbelastungen_el_links",
            (uferbelastungenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferbelastungenLinksEL));
        params.put(
            "uferbelastungen_so_links",
            (uferbelastungenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(uferbelastungenLinksSO));
        params.put("uferbelastungen_keine_links", (uferbelastungenLinksSum == 0.0) ? "X" : "");

        // ---

        final BigDecimal uferbelastungenRechtsMUE = (BigDecimal)bean.getProperty("uferbelastungen_mue_rechts");
        final BigDecimal uferbelastungenRechtsST = (BigDecimal)bean.getProperty("uferbelastungen_st_rechts");
        final BigDecimal uferbelastungenRechtsTS = (BigDecimal)bean.getProperty("uferbelastungen_ts_rechts");
        final BigDecimal uferbelastungenRechtsEL = (BigDecimal)bean.getProperty("uferbelastungen_el_rechts");
        final BigDecimal uferbelastungenRechtsSO = (BigDecimal)bean.getProperty("uferbelastungen_so_rechts");

        final double uferbelastungenRechtsSum = this.convertBiGDecimalToDouble(uferbelastungenRechtsMUE)
                    + this.convertBiGDecimalToDouble(uferbelastungenRechtsST)
                    + this.convertBiGDecimalToDouble(uferbelastungenRechtsTS)
                    + this.convertBiGDecimalToDouble(uferbelastungenRechtsEL)
                    + this.convertBiGDecimalToDouble(uferbelastungenRechtsSO);

        params.put(
            "uferbelastungen_mue_rechts",
            (uferbelastungenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferbelastungenRechtsMUE));
        params.put(
            "uferbelastungen_st_rechts",
            (uferbelastungenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferbelastungenRechtsST));
        params.put(
            "uferbelastungen_ts_rechts",
            (uferbelastungenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferbelastungenRechtsTS));
        params.put(
            "uferbelastungen_el_rechts",
            (uferbelastungenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferbelastungenRechtsEL));
        params.put(
            "uferbelastungen_so_rechts",
            (uferbelastungenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(uferbelastungenRechtsSO));
        params.put("uferbelastungen_keine_rechts", (uferbelastungenRechtsSum == 0.0) ? "X" : "");

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

        final BigDecimal umfeldStrukturenLinksFM = (BigDecimal)bean.getProperty("umfeldstrukturen_fm_links");
        final BigDecimal umfeldStrukturenLinksQ = (BigDecimal)bean.getProperty("umfeldstrukturen_q_links");
        final BigDecimal umfeldStrukturenLinksAA = (BigDecimal)bean.getProperty("umfeldstrukturen_aa_links");
        final BigDecimal umfeldStrukturenLinksAW = (BigDecimal)bean.getProperty("umfeldstrukturen_aw_links");
        final BigDecimal umfeldStrukturenLinksW = (BigDecimal)bean.getProperty("umfeldstrukturen_w_links");
        final BigDecimal umfeldStrukturenLinksSO = (BigDecimal)bean.getProperty("umfeldstrukturen_so_links");

        final double umfeldStrukturenLinksSum = this.convertBiGDecimalToDouble(umfeldStrukturenLinksFM)
                    + this.convertBiGDecimalToDouble(umfeldStrukturenLinksQ)
                    + this.convertBiGDecimalToDouble(umfeldStrukturenLinksAA)
                    + this.convertBiGDecimalToDouble(umfeldStrukturenLinksAW)
                    + this.convertBiGDecimalToDouble(umfeldStrukturenLinksW)
                    + this.convertBiGDecimalToDouble(umfeldStrukturenLinksSO);

        params.put(
            "umfeldstrukturen_fm_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenLinksFM));
        params.put(
            "umfeldstrukturen_q_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenLinksQ));
        params.put(
            "umfeldstrukturen_aa_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenLinksAA));
        params.put(
            "umfeldstrukturen_aw_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenLinksAW));
        params.put(
            "umfeldstrukturen_w_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenLinksW));
        params.put(
            "umfeldstrukturen_so_links",
            (umfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenLinksSO));
        params.put("umfeldstrukturen_keine_links", (umfeldStrukturenLinksSum == 0.0) ? "X" : "");

        // ---

        final BigDecimal umfeldStrukturenRechtsFM = (BigDecimal)bean.getProperty("umfeldstrukturen_fm_rechts");
        final BigDecimal umfeldStrukturenRechtsQ = (BigDecimal)bean.getProperty("umfeldstrukturen_q_rechts");
        final BigDecimal umfeldStrukturenRechtsAA = (BigDecimal)bean.getProperty("umfeldstrukturen_aa_rechts");
        final BigDecimal umfeldStrukturenRechtsAW = (BigDecimal)bean.getProperty("umfeldstrukturen_aw_rechts");
        final BigDecimal umfeldStrukturenRechtsW = (BigDecimal)bean.getProperty("umfeldstrukturen_w_rechts");
        final BigDecimal umfeldStrukturenRechtsSO = (BigDecimal)bean.getProperty("umfeldstrukturen_so_rechts");

        final double umfeldStrukturenRechtsSum = this.convertBiGDecimalToDouble(umfeldStrukturenRechtsFM)
                    + this.convertBiGDecimalToDouble(umfeldStrukturenRechtsQ)
                    + this.convertBiGDecimalToDouble(umfeldStrukturenRechtsAA)
                    + this.convertBiGDecimalToDouble(umfeldStrukturenRechtsAW)
                    + this.convertBiGDecimalToDouble(umfeldStrukturenRechtsW)
                    + this.convertBiGDecimalToDouble(umfeldStrukturenRechtsSO);

        params.put(
            "umfeldstrukturen_fm_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenRechtsFM));
        params.put(
            "umfeldstrukturen_q_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenRechtsQ));
        params.put(
            "umfeldstrukturen_aa_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenRechtsAA));
        params.put(
            "umfeldstrukturen_aw_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenRechtsAW));
        params.put(
            "umfeldstrukturen_w_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenRechtsW));
        params.put(
            "umfeldstrukturen_so_rechts",
            (umfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(umfeldStrukturenRechtsSO));
        params.put("umfeldstrukturen_keine_rechts", (umfeldStrukturenRechtsSum == 0.0) ? "X" : "");

        // ---

        final BigDecimal sUmfeldStrukturenLinksAG = (BigDecimal)bean.getProperty("s_umfeldstrukturen_ag_links");
        final BigDecimal sUmfeldStrukturenLinksFT = (BigDecimal)bean.getProperty("s_umfeldstrukturen_ft_links");
        final BigDecimal sUmfeldStrukturenLinksGUA = (BigDecimal)bean.getProperty("s_umfeldstrukturen_gua_links");
        final BigDecimal sUmfeldStrukturenLinksBV = (BigDecimal)bean.getProperty("s_umfeldstrukturen_bv_links");
        final BigDecimal sUmfeldStrukturenLinksMA = (BigDecimal)bean.getProperty("s_umfeldstrukturen_ma_links");
        final BigDecimal sUmfeldStrukturenLinksHW = (BigDecimal)bean.getProperty("s_umfeldstrukturen_hw_links");
        final BigDecimal sUmfeldStrukturenLinksSO = (BigDecimal)bean.getProperty("s_umfeldstrukturen_so_links");

        final double sUmfeldStrukturenLinksSum = this.convertBiGDecimalToDouble(sUmfeldStrukturenLinksAG)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenLinksFT)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenLinksGUA)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenLinksBV)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenLinksMA)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenLinksHW)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenLinksSO);

        params.put(
            "s_umfeldstrukturen_ag_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenLinksAG));
        params.put(
            "s_umfeldstrukturen_ft_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenLinksFT));
        params.put(
            "s_umfeldstrukturen_gua_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenLinksGUA));
        params.put(
            "s_umfeldstrukturen_bv_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenLinksBV));
        params.put(
            "s_umfeldstrukturen_ma_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenLinksMA));
        params.put(
            "s_umfeldstrukturen_hw_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenLinksHW));
        params.put(
            "s_umfeldstrukturen_so_links",
            (sUmfeldStrukturenLinksSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenLinksSO));
        params.put("s_umfeldstrukturen_keine_links", (sUmfeldStrukturenLinksSum == 0.0) ? "X" : "");

        // ---

        final BigDecimal sUmfeldStrukturenRechtsAG = (BigDecimal)bean.getProperty("s_umfeldstrukturen_ag_rechts");
        final BigDecimal sUmfeldStrukturenRechtsFT = (BigDecimal)bean.getProperty("s_umfeldstrukturen_ft_rechts");
        final BigDecimal sUmfeldStrukturenRechtsGUA = (BigDecimal)bean.getProperty("s_umfeldstrukturen_gua_rechts");
        final BigDecimal sUmfeldStrukturenRechtsBV = (BigDecimal)bean.getProperty("s_umfeldstrukturen_bv_rechts");
        final BigDecimal sUmfeldStrukturenRechtsMA = (BigDecimal)bean.getProperty("s_umfeldstrukturen_ma_rechts");
        final BigDecimal sUmfeldStrukturenRechtsHW = (BigDecimal)bean.getProperty("s_umfeldstrukturen_hw_rechts");
        final BigDecimal sUmfeldStrukturenRechtsSO = (BigDecimal)bean.getProperty("s_umfeldstrukturen_so_rechts");

        final double sUmfeldStrukturenRechtsSum = this.convertBiGDecimalToDouble(sUmfeldStrukturenRechtsAG)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenRechtsFT)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenRechtsGUA)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenRechtsBV)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenRechtsMA)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenRechtsHW)
                    + this.convertBiGDecimalToDouble(sUmfeldStrukturenRechtsSO);

        params.put(
            "s_umfeldstrukturen_ag_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenRechtsAG));
        params.put(
            "s_umfeldstrukturen_ft_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenRechtsFT));
        params.put(
            "s_umfeldstrukturen_gua_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenRechtsGUA));
        params.put(
            "s_umfeldstrukturen_bv_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenRechtsBV));
        params.put(
            "s_umfeldstrukturen_ma_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenRechtsMA));
        params.put(
            "s_umfeldstrukturen_hw_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenRechtsHW));
        params.put(
            "s_umfeldstrukturen_so_rechts",
            (sUmfeldStrukturenRechtsSum == 0.0) ? "" : this.convertBiGDecimalToString(sUmfeldStrukturenRechtsSO));
        params.put("s_umfeldstrukturen_keine_rechts", (sUmfeldStrukturenRechtsSum == 0.0) ? "X" : "");

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

//        System.out.println("\n------set params--------\n" + params.toString().replaceAll(",", "\n")
//                    + "\n--------------------------\n");
//
//        System.out.println("\n------all params--------\n");
//        for (final String key : current.getPropertyNames()) {
//            System.out.println(key + " = " + current.getProperty(key));
//        }
//        System.out.println("\n--------------------------\n");

        return params;
    }

    @Override
    public Map generateReportParam(final Collection<CidsBean> beans) {
        return Collections.EMPTY_MAP;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Throwable  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Throwable {
//        final Properties p = new Properties();
//        p.put("log4j.appender.Remote", "org.apache.log4j.net.SocketAppender");
//        p.put("log4j.appender.Remote.remoteHost", "localhost");
//        p.put("log4j.appender.Remote.port", "4445");
//        p.put("log4j.appender.Remote.locationInfo", "true");
//        p.put("log4j.rootLogger", "ALL,Remote");
//        org.apache.log4j.PropertyConfigurator.configure(p);
//
//        initServer();
//
//        final MetaObject mo1 = server.getMetaObject(user, 1, 229);
//
//        // TODO: so früh, wie möglich, verwenden!!!
//        final FgskReport report = new FgskReport(mo1.getBean());
//        report.showReport(mo1.getBean());
//
////        report.print();
//
//        stopServer();
//
//        System.out.println("ENDE");
//
//        System.exit(0);

        final JFrame frame = new JFrame("TEST");
        final JPanel panel = new JPanel();
        final JButton button = new JButton("PRINT");

        panel.add(button);
        frame.add(panel);

        frame.setSize(100, 100);

        button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent ae) {
                    try {
                        final Properties p = new Properties();
                        p.put("log4j.appender.Remote", "org.apache.log4j.net.SocketAppender");
                        p.put("log4j.appender.Remote.remoteHost", "localhost");
                        p.put("log4j.appender.Remote.port", "4445");
                        p.put("log4j.appender.Remote.locationInfo", "true");
                        p.put("log4j.rootLogger", "ALL,Remote");
                        org.apache.log4j.PropertyConfigurator.configure(p);
                        try {
                            initServer();
                        } catch (Throwable ex) {
                            Exceptions.printStackTrace(ex);
                        }

                        final MetaObject mo1 = server.getMetaObject(user, 1, 229);

                        // TODO: so früh, wie möglich, verwenden!!!
                        final FgskReport report = new FgskReport(mo1.getBean());
//                   report.showReport(mo1.getBean());
                        report.print();
                    } catch (RemoteException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            });

        frame.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(final WindowEvent we) {
                    try {
                        stopServer();
                    } catch (Throwable ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    System.exit(0);
                }
            });

        frame.setVisible(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Throwable  DOCUMENT ME!
     */
    private static void initServer() throws Throwable {
        registry = new Registry(1099);
        proxy = StartProxy.getInstance(SERVER_CONFIG);
        server = new DomainServerImpl(new ServerProperties(SERVER_CONFIG));
        user = new User(1, "admin", "LOCAL", new UserGroup(1, "Administratoren", "LOCAL"));
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Throwable  DOCUMENT ME!
     */
    private static void stopServer() throws Throwable {
        try {
            proxy.shutdown();
            registry.shutdown();

            server.shutdown();
        } catch (final ServerExit e) {
            // success
        }
    }
}
