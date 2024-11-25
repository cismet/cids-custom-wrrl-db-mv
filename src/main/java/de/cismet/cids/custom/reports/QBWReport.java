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

import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.Reader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.FgskIdSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WKKSearchBySingleStation;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.ReportUtils;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public final class QBWReport extends AbstractJasperReportPrint {

    //~ Static fields/initializers ---------------------------------------------

    private static final String REPORT_URL = "/de/cismet/cids/custom/reports/Querbauwerke.jasper";
    private static final Logger LOG = Logger.getLogger(QBWReport.class);
    private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final String INDETERMINATE = "nicht ermittelbar";
    private static final ConnectionContext CC = ConnectionContext.create(
            AbstractConnectionContext.Category.ACTION,
            "QBWReport");

    //~ Instance fields --------------------------------------------------------

    private final MetaClass MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "querbauwerke");

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new StadtbildJasperReportPrint object.
     *
     * @param  beans  DOCUMENT ME!
     */
    public QBWReport(final Collection<CidsBean> beans) {
        this(null, beans);
    }

    /**
     * Creates a new StadtbildJasperReportPrint object.
     *
     * @param  bean  DOCUMENT ME!
     */
    public QBWReport(final CidsBean bean) {
        super(REPORT_URL, bean);
        setBeansCollection(false);
    }

    /**
     * Creates a new StadtbildJasperReportPrint object.
     *
     * @param  bean      DOCUMENT ME!
     * @param  filename  DOCUMENT ME!
     */
    public QBWReport(final CidsBean bean, final String filename) {
        super(REPORT_URL, bean, filename);
        setBeansCollection(false);
    }

    /**
     * Creates a new StadtbildJasperReportPrint object.
     *
     * @param  parent  DOCUMENT ME!
     * @param  beans   DOCUMENT ME!
     */
    public QBWReport(final JFrame parent, final Collection<CidsBean> beans) {
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
    private void retrieveQuerbauwerkParams(final CidsBean bean, final Map params) {
        String gew = CidsBeanSupport.FIELD_NOT_SET;
        String wkName = CidsBeanSupport.FIELD_NOT_SET;
        String gwk = CidsBeanSupport.FIELD_NOT_SET;
        String statFrom = CidsBeanSupport.FIELD_NOT_SET;
        String statTo = CidsBeanSupport.FIELD_NOT_SET;
        final String kartierer = CidsBeanSupport.FIELD_NOT_SET;
        String date = CidsBeanSupport.FIELD_NOT_SET;
        String wkk = CidsBeanSupport.FIELD_NOT_SET;

        final CidsBean statVon = (CidsBean)bean.getProperty("stat09");
        final CidsBean statBis = (CidsBean)bean.getProperty("stat09_bis");

        CidsBean route = null;

        if (statBis != null) {
            route = (CidsBean)statBis.getProperty("route");
            statTo = toString(statVon.getProperty("wert"));
        }

        if (statVon != null) {
            route = (CidsBean)statVon.getProperty("route");
            statFrom = toString(statVon.getProperty("wert"));
        }

        if (route != null) {
            gew = toString(route.getProperty("routenname"));
            gwk = toString(route.getProperty("gwk"));

            try {
                final CidsServerSearch search = new WKKSearchBySingleStation(toString(route.getProperty("id")),
                        ((statFrom != CidsBeanSupport.FIELD_NOT_SET) ? statFrom : statTo));
                final Collection res = SessionManager.getProxy()
                            .customServerSearch(SessionManager.getSession().getUser(), search, CC);
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
                        } else {
                            wkName = INDETERMINATE;
                        }
                    }
                } else {
                    LOG.error("Server error in getWk_k(). Cids server search return null. " // NOI18N
                                + "See the server logs for further information");     // NOI18N
                }
            } catch (final Exception e) {
                LOG.error("Error while determining the water body", e);
            }
        }

        if (bean.getProperty("kartierdatum") != null) {
            date = toString(DF.format(bean.getProperty("kartierdatum")));
        }

        params.put("gew", toString(gew)); // Gewässername
        params.put("gwk", toString(gwk)); // Gewässerkennzahl
        params.put("wkk", toString(wkk)); // Wasserkörper
        params.put("wkName", toString(wkName));
        params.put("statFrom", toString(statFrom));
        params.put("statTo", toString(statTo));
        params.put("kartierer", toString(bean.getProperty("kartierer")));
        params.put("date", toString(date));
        params.put("bwNummer", toString(bean.getProperty("bauwerksnummer")));
        params.put("anlagename", toString(bean.getProperty("anlagename")));
        params.put("bauwerksart", toString(bean.getProperty("detailtyp.value")));
        params.put("material", toString(bean.getProperty("material")));
        params.put("laenge", toString(bean.getProperty("laenge")));
        params.put("durchmesser", toString(bean.getProperty("durchmesser")));
        params.put("ueberdeckung", toString(bean.getProperty("ueberdeckung")));
        params.put("stauhoehe", toString(bean.getProperty("stauhoehe")));
        params.put("sohlbefestigung_laenge", toString(bean.getProperty("sohlbefestigung_laenge")));
        params.put("sohlbefestigung_uh_material", toString(bean.getProperty("sohlbefestigung_uh_material.name")));
        params.put("sohlbefestigung_oh_material", toString(bean.getProperty("sohlbefestigung_oh_material.name")));
        params.put("substrat", toString(bean.getProperty("substrat.value")));
        params.put("regulierbarkeit", toString(bean.getProperty("regulierbarkeit.value")));
        params.put("dgk_fische", toString(bean.getProperty("dgk_fische.name")));
        params.put("dgk_wirbel", toString(bean.getProperty("dgk_wirbel.name")));
        params.put("dgk_otter", toString(bean.getProperty("dgk_otter.name")));
        params.put("stand", "Stand: " + DF.format(new Date()));
    }

    @Override
    public Map generateReportParam(final CidsBean current) {
        final HashMap params = new HashMap();

        this.retrieveQuerbauwerkParams(current, params);

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
                            final QBWReport r = new QBWReport(bean, filename);
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

            // init log4J
            final Properties p = new Properties();
            p.put("log4j.appender.Console", "org.apache.log4j.ConsoleAppender");   // NOI18N
            p.put("log4j.appender.Console.layout", "org.apache.log4j.TTCCLayout"); // NOI18N
            p.put("log4j.rootLogger", "ERROR,Console");                            // NOI18N
            org.apache.log4j.PropertyConfigurator.configure(p);

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
