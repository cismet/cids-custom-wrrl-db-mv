/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
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

import org.mortbay.log.Log;

import java.io.FileReader;
import java.io.Reader;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.TeilgebieteSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkFgIdSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.LawaTableModel;
import de.cismet.cids.custom.wrrl_db_mv.util.TeileComparator;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.utils.jasperreports.ReportSwingWorker;

import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.layerwidget.ActiveLayerModel;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.configuration.ConfigurationManager;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class WkFgReport {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkFgReport.class);
    private static ConfigurationManager configManager = new ConfigurationManager();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    public static void showReport(final CidsBean cidsBean) {
        final Collection<CidsBean> coll = new ArrayList<CidsBean>();
        coll.add(cidsBean);

        final ArrayList<Collection<CidsBean>> beans = new ArrayList<Collection<CidsBean>>();
        beans.add(coll);
        beans.add(getMassnahmen((Integer)cidsBean.getProperty("id")));

        final ArrayList<String> reports = new ArrayList<String>();
        reports.add("/de/cismet/cids/custom/reports/wk_fg.jasper");
        reports.add("/de/cismet/cids/custom/reports/wk_fg_massnahmen.jasper");

        final HashMap parameters = new HashMap();
        parameters.put("STATIONIERUNGEN", getStationierungen(cidsBean));
        parameters.put("GEWAESSERKENNZAHLEN", getGewaesserkennzahlen(cidsBean));
        parameters.put("LAWA-DETAILTYP", getLawaDetailTyp(cidsBean));
        parameters.put("BEWIRTSCHAFTUNGSBEREICHE", getBewirtschaftungsbereiche(cidsBean));

        final ReportSwingWorker worker = new ReportSwingWorker(
                beans,
                reports,
                true,
                StaticSwingTools.getParentFrame(CismapBroker.getInstance().getMappingComponent()),
                CismapBroker.getInstance().getCismapFolderPath(),
                parameters);
        worker.execute();
    }

    /**
     * Creates the report for the given cids bean and saves the file in the given directory.
     *
     * @param  directory  the directory to save the reports
     * @param  cidsBean   the report will be created for this wk_fg bean
     * @param  executor   the report thread will be executed with this executor, if it is not null
     */
    public static void createReport(final String directory, final CidsBean cidsBean, final Executor executor) {
        final Collection<CidsBean> coll = new ArrayList<CidsBean>();
        coll.add(cidsBean);

        final ArrayList<Collection<CidsBean>> beans = new ArrayList<Collection<CidsBean>>();
        final Collection<CidsBean> massnahmenUmgesetzt = getMassnahmenUmgesetzt((Integer)cidsBean.getProperty("id"));
        beans.add(coll);
        beans.add(getMassnahmen((Integer)cidsBean.getProperty("id")));
        if ((massnahmenUmgesetzt != null) && !massnahmenUmgesetzt.isEmpty()) {
            beans.add(massnahmenUmgesetzt);
        }

        final ArrayList<String> reports = new ArrayList<String>();
        reports.add("/de/cismet/cids/custom/reports/wk_fg.jasper");
        reports.add("/de/cismet/cids/custom/reports/wk_fg_massnahmen.jasper");
        if ((massnahmenUmgesetzt != null) && !massnahmenUmgesetzt.isEmpty()) {
            reports.add("/de/cismet/cids/custom/reports/wk_fg_massnahmenUmgesetzt.jasper");
        }

        final HashMap parameters = new HashMap();
        parameters.put("STATIONIERUNGEN", getStationierungen(cidsBean));
        parameters.put("GEWAESSERKENNZAHLEN", getGewaesserkennzahlen(cidsBean));
        parameters.put("LAWA-DETAILTYP", getLawaDetailTyp(cidsBean));
        parameters.put("BEWIRTSCHAFTUNGSBEREICHE", getBewirtschaftungsbereiche(cidsBean));

        final String reportName = String.valueOf(cidsBean.getProperty("wk_k"));

        final ReportSwingWorker worker = new ReportSwingWorker(
                beans,
                reports,
                false,
                null,
                directory,
                parameters,
                reportName);

        if (executor == null) {
            worker.execute();
        } else {
            executor.execute(worker);
        }
    }

    /**
     * Creates all wk_fg reports and saves the files in the given directory.
     *
     * @param  directory   the directory to save the reports
     * @param  expression  DOCUMENT ME!
     */
    public static void createAllReports(final String directory, final String expression) {
        final MetaClass wkFgMc = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");
        final String query = "SELECT " + wkFgMc.getID() + ", " + wkFgMc.getPrimaryKey() + " FROM "
                    + wkFgMc.getTableName();
        final ArrayList ids = new ArrayList();

        try {
            final CidsServerSearch search = new WkFgIdSearch(expression);
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

        final ExecutorService executor = Executors.newFixedThreadPool(5);
//        final ExecutorService executor = CismetExecutors.newCachedThreadPool();

//        if (expression != null) {
//            query += " WHERE wk_k ilike '" + expression + "'";
//        }

        try {
            for (final Object id : ids) {
                final Integer oId = (Integer)id;
                final MetaObject wkFgObject = SessionManager.getProxy()
                            .getMetaObject(oId.intValue(), wkFgMc.getID(), WRRLUtil.DOMAIN_NAME);
                createReport(directory, wkFgObject.getBean(), executor);
            }
        } catch (Exception e) {
            LOG.error("Error while creating all wk_fg reports", e);
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
     * @param   id  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Collection<CidsBean> getMassnahmen(final int id) {
        try {
            final MetaClass mcMassnahmen = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "massnahmen");

            final String query = "SELECT "
                        + "   " + mcMassnahmen.getID() + ", "
                        + "   " + mcMassnahmen.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + mcMassnahmen.getTableName() + " "
                        + "WHERE "
                        + "   wk_fg = " + String.valueOf(id)
                        + " and massn_fin = false "
                        + "ORDER BY massn_id"
                        + ";";

            return getBeansFromQuery(query);
        } catch (Exception ex) {
            LOG.error("Error while getting massnahmen for wk-fg with id " + String.valueOf(id), ex);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Collection<CidsBean> getMassnahmenUmgesetzt(final int id) {
        try {
            final MetaClass mcMassnahmen = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "massnahmen");

            final String query = "SELECT "
                        + "   " + mcMassnahmen.getID() + ", "
                        + "   " + mcMassnahmen.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + mcMassnahmen.getTableName() + " "
                        + "WHERE "
                        + "   wk_fg = " + String.valueOf(id)
                        + " and massn_fin = true "
                        + "ORDER BY massn_id"
                        + ";";

            return getBeansFromQuery(query);
        } catch (Exception ex) {
            LOG.error("Error while getting massnahmen umgesetzt for wk-fg with id " + String.valueOf(id), ex);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   query  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static Collection<CidsBean> getBeansFromQuery(final String query) {
        final ArrayList<CidsBean> collection = new ArrayList<CidsBean>();
        try {
            for (final MetaObject mo : SessionManager.getProxy().getMetaObjectByQuery(query, 0)) {
                collection.add(mo.getBean());
            }
        } catch (ConnectionException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while fetching metaobject", ex);
            }
        }

        return collection;
    }

    /**
     * gets the Gewaesserkennzahlen from a WK_FG and converts it to a String, with the format "gwk, gwk, ... gwk"
     *
     * @param   wk_fgBean  DOCUMENT ME!
     *
     * @return  Gewaesserkennzahlen from a WK_FG as String, with the format "gwk, gwk, ... gwk"
     */
    private static String getGewaesserkennzahlen(final CidsBean wk_fgBean) {
        String gewaesserkennzahlen = "";
        final Collection<CidsBean> teile = (Collection<CidsBean>)wk_fgBean.getProperty("teile");
        for (final CidsBean teil : teile) {
            final CidsBean linie = (CidsBean)teil.getProperty("linie");
            final CidsBean station_von = (CidsBean)linie.getProperty("von");
            final CidsBean route = (CidsBean)station_von.getProperty("route");
            final Long gewaesserkennzahl = (Long)route.getProperty("gwk");

            gewaesserkennzahlen += gewaesserkennzahl.toString() + ", ";
        }
        if (!gewaesserkennzahlen.equals("")) {
            gewaesserkennzahlen = gewaesserkennzahlen.substring(0, gewaesserkennzahlen.length() - 2);
        }

        return gewaesserkennzahlen;
    }

    /**
     * fetches the LAWA_Detailtyp from a WK_FG and converts it to a String with the format "typ1# (xx.x%), ... kein Typ
     * (xx.x%)"
     *
     * @param   wk_fgBean  DOCUMENT ME!
     *
     * @return  LAWA_Detailtyp from a WK_FG as String, with the format "typ1# (xx.x%), ... kein Typ (xx.x%)"
     */
    private static String getLawaDetailTyp(final CidsBean wk_fgBean) {
        final LawaTableModel model = new LawaTableModel();
        model.setCidsBean(wk_fgBean);
        final List<CidsBean> teile = CidsBeanSupport.getBeanCollectionFromProperty(wk_fgBean, "teile");
        if (teile != null) {
            Collections.sort(teile, new TeileComparator());
        }
        model.setTeile(teile);
        model.refreshData();

        final int typIndex = 0;
        final int anteilIndex = 1;
        final int amountDifferentTypes = model.getRowCount();

        String lawaDetailTyp = null;

        for (int i = 0; i < (amountDifferentTypes - 1); i++) {
            final String typ = (String)model.getValueAt(i, typIndex);
            final String typ_number = typ.split("-")[0];
            final String typ_name = typ.substring(typ.indexOf("-") + 1);
            final String anteil = (String)model.getValueAt(i, anteilIndex);

            if (lawaDetailTyp == null) {
                lawaDetailTyp = typ_name + " (Typ " + typ_number + ", " + anteil + "% der Länge), ";
            } else {
                lawaDetailTyp += "<br />" + typ_name + " (Typ " + typ_number + ", " + anteil + "% der Länge), ";
            }
        }
        if ((lawaDetailTyp != null) && !lawaDetailTyp.equals("")) {
            lawaDetailTyp = lawaDetailTyp.substring(0, lawaDetailTyp.length() - 2);
        }

        return lawaDetailTyp;
    }

    /**
     * gets the Stationierungen from a WK_FG as String, with the format "von - bis, von - bis, ... von - bis"
     *
     * @param   wk_fgBean  DOCUMENT ME!
     *
     * @return  Stationierungen from a WK_FG as String, with the format "von - bis, von - bis, ... von - bis"
     */
    private static String getStationierungen(final CidsBean wk_fgBean) {
        String stationierungen = "";
        final Collection<CidsBean> teile = (Collection<CidsBean>)wk_fgBean.getProperty("teile");
        final DecimalFormat df = new DecimalFormat(",##0");
        for (final CidsBean teil : teile) {
            final Double wert_von = (Double)teil.getProperty("linie.von.wert");
            final Double wert_bis = (Double)teil.getProperty("linie.bis.wert");

            stationierungen += df.format(wert_von) + " - " + df.format(wert_bis) + ", ";
        }
        if (!stationierungen.equals("")) {
            stationierungen = stationierungen.substring(0, stationierungen.length() - 2);
        }

        return stationierungen;
    }

    /**
     * gets the Bewirtschaftungsbereiche from a WK_FG with format "von - bis, von - bis, ... von - bis". each
     * Bewirtschaftungsbereich has usually the same von and bis stations as one Teil. Nevertheless it might finish
     * earlier, but the starting station is always the same.
     *
     * @param   wk_fgBean  a WK_FG CidsBean
     *
     * @return  Bewirtschaftungsbereiche from a WK_FG as String, with the format "von - bis, von - bis, ... von - bis"
     */
    private static String getBewirtschaftungsbereiche(final CidsBean wk_fgBean) {
        String stationierungen = "";
        final Collection<CidsBean> teile = (Collection<CidsBean>)wk_fgBean.getProperty("teile");
        final DecimalFormat df = new DecimalFormat(",##0");
        for (final CidsBean teil : teile) {
            final Double bewirtschaftung_von = (Double)teil.getProperty("linie.von.wert");

            final Collection<CidsBean> bewirtschaftungsende_coll = getBewirtschaftungsende(teil);
            Double bewirtschaftung_bis;

            if (bewirtschaftungsende_coll.isEmpty()) {
                bewirtschaftung_bis = (Double)teil.getProperty("linie.bis.wert");
            } else if (bewirtschaftungsende_coll.size() == 1) {
                final CidsBean bewirtschaftungsende = bewirtschaftungsende_coll.iterator().next();
                bewirtschaftung_bis = (Double)bewirtschaftungsende.getProperty("stat.wert");
            } else { // ??? bewirtschaftungsende should contain only none or one CidsBean?
                final CidsBean bewirtschaftungsende = bewirtschaftungsende_coll.iterator().next();
                bewirtschaftung_bis = (Double)bewirtschaftungsende.getProperty("stat.wert");
                Log.warn("Teil " + teil.getProperty("ID") + " hat mehrere Bewirtschaftungsenden.");
            }
            stationierungen += df.format(bewirtschaftung_von) + " - " + df.format(bewirtschaftung_bis) + ", ";
        }
        if (!stationierungen.equals("")) {
            stationierungen = stationierungen.substring(0, stationierungen.length() - 2);
        }

        return stationierungen;
    }

    /**
     * fetches the Bewirtschaftungsende of one Teil.
     *
     * @param   teilBean  a CidsBean representing a Teil
     *
     * @return  Bewirtschaftungsende of one Teil
     */
    private static Collection<CidsBean> getBewirtschaftungsende(final CidsBean teilBean) {
        try {
            final MetaClass BEW_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "bewirtschaftungsende");

            String query = "SELECT " + BEW_MC.getID() + ", b." + BEW_MC.getPrimaryKey() + " ";
            query += "FROM " + BEW_MC.getTableName() + " b JOIN station s ON b.stat = s.id ";
            query += "WHERE route = " + teilBean.getProperty("linie.von.route.id") + " and s.wert > "
                        + teilBean.getProperty("linie.von.wert") + " and s.wert < "
                        + teilBean.getProperty("linie.bis.wert") + ";";

            return getBeansFromQuery(query);
        } catch (Exception ex) {
            LOG.error("Error while getting bewirtschaftungsende for wk-fg teil: " + String.valueOf(teilBean), ex);

            return null;
        }
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

//            expression = "WANE-0300";

            // read the properties
            final Properties properties = new Properties();
//            final Reader propertiesReader = new FileReader(
//                    "/home/therter/ApplicationData/flexDist/client/wrrl-db-mvLocalMv/config/wkReports.properties");
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
                            false,
                            properties.getProperty("connectionClass"),
                            properties.getProperty("callserver"),
                            null);
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

            initCismap();

            // init log4J
            final Properties p = new Properties();
            p.put("log4j.appender.Console", "org.apache.log4j.ConsoleAppender");   // NOI18N
            p.put("log4j.appender.Console.layout", "org.apache.log4j.TTCCLayout"); // NOI18N
            p.put("log4j.rootLogger", "ERROR,Console");                            // NOI18N
            org.apache.log4j.PropertyConfigurator.configure(p);

            // create the reports
            createAllReports(properties.getProperty("report_directory"), expression);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialises the MappingComponent.
     */
    private static void initCismap() {
        final MappingComponent mappingComponent = new MappingComponent();

        configManager.setDefaultFileName("de/cismet/cids/custom/reports/defaultCismapProperties.xml");
        configManager.setFallBackFileName("de/cismet/cids/custom/reports/defaultCismapProperties.xml");

        final ActiveLayerModel mappingModel = new ActiveLayerModel();
        configManager.addConfigurable((ActiveLayerModel)mappingModel);
        configManager.addConfigurable(mappingComponent);

        configManager.configure(mappingModel);
        mappingComponent.preparationSetMappingModel(mappingModel);
        configManager.configure(mappingComponent);

        mappingComponent.setMappingModel(mappingModel);

        CismapBroker.getInstance().setMappingComponent(mappingComponent);
    }
}
