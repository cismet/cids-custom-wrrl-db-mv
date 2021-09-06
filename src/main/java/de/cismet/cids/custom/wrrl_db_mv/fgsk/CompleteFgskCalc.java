/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.fgsk;

/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/

import Sirius.navigator.connection.ConnectionFactory;
import Sirius.navigator.connection.ConnectionInfo;
import Sirius.navigator.connection.ConnectionSession;
import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.connection.proxy.ConnectionProxy;
import Sirius.navigator.resource.PropertyManager;

import Sirius.server.ServerExit;
import Sirius.server.ServerExitError;
import Sirius.server.middleware.impls.domainserver.DomainServerImpl;
import Sirius.server.middleware.impls.proxy.StartProxy;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.newuser.User;
import Sirius.server.newuser.UserGroup;
import Sirius.server.property.ServerProperties;
import Sirius.server.registry.Registry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittEditor;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.netutil.Proxy;
import de.cismet.netutil.ProxyHandler;

import de.cismet.tools.PasswordEncrypter;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class CompleteFgskCalc {

    //~ Static fields/initializers ---------------------------------------------

    private static final String SERVER_CONFIG_FILE = "completeFgskCalc.properties";       // NOI18N
    private static final String CLIENT_CONFIG_FILE = "completeFgskCalcClient.properties"; // NOI18N

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static enum Status {

        //~ Enum constants -----------------------------------------------------

        OK, NO_ENTRY, MISSING_DATA, MISSING_DATA_IN_CIDS, NO_RATING_MATCH, NO_CRITERIA_MATCH, EXCEPTION
    }

    //~ Instance fields --------------------------------------------------------

    private final transient Registry registry;
    private final transient StartProxy proxy;
    private final transient DomainServerImpl server;
    private final transient Connection con;
    private final transient ConnectionSession session;

    private final transient int classId;

    private final transient List<Integer> noCalcIds;

    private transient int missingDataCounter;

    private final transient List<Integer> unexpectedErrorIds;
    private final transient List<Integer> kaWoWBTypeIds;

    private transient int kaWoWBTypeCounter;

    private transient int overallRatingZeroCounter;
    private transient int exceptionCounter;
    private final transient List<Integer> exceptionIds;
    private final transient List<Integer> vorkartIds;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CompleteCalcTestReport object.
     *
     * @throws  Throwable  DOCUMENT ME!
     */
    public CompleteFgskCalc() throws Throwable {
        final Properties p = new Properties();
        p.put("log4j.appender.Remote", "org.apache.log4j.net.SocketAppender");
        p.put("log4j.appender.Remote.remoteHost", "localhost");
        p.put("log4j.appender.Remote.port", "4445");
        p.put("log4j.appender.Remote.locationInfo", "true");
        p.put("log4j.rootLogger", "ALL,Remote");
        org.apache.log4j.PropertyConfigurator.configure(p);

        final ServerProperties props = new ServerProperties(SERVER_CONFIG_FILE);
        final Properties clientProps = new Properties();
        final FileInputStream fis = new FileInputStream(CLIENT_CONFIG_FILE);
        clientProps.load(fis);

        registry = Sirius.server.registry.Registry.getServerInstance(1097);
        proxy = StartProxy.getInstance(SERVER_CONFIG_FILE);
        server = new DomainServerImpl(props);

        final PropertyManager propertyManager = PropertyManager.getManager();
        final ConnectionInfo info = propertyManager.getConnectionInfo();
        final String encryptedPassword = clientProps.getProperty("password");
        final String decryptedPassword = new String(PasswordEncrypter.decrypt(encryptedPassword.toCharArray(), false));
        info.setCallserverURL(clientProps.getProperty("callserverUrl"));
        info.setUsername(clientProps.getProperty("username"));
        info.setUsergroup(clientProps.getProperty("usergroup"));
        info.setPassword(decryptedPassword);
        info.setUserDomain(clientProps.getProperty("domain"));
        info.setUsergroupDomain(clientProps.getProperty("domain"));

        final Sirius.navigator.connection.Connection connection = ConnectionFactory.getFactory()
                    .createConnection(propertyManager.getConnectionClass(),
                        info.getCallserverURL(),
                        ProxyHandler.getInstance().getProxy());
        session = ConnectionFactory.getFactory().createSession(connection, info, true);
        final ConnectionProxy conProxy = ConnectionFactory.getFactory()
                    .createProxy(propertyManager.getConnectionProxyClass(), session);
        SessionManager.init(conProxy);

        con = server.getConnectionPool().getConnection();

        classId = 229;

        missingDataCounter = 0;
        kaWoWBTypeCounter = 0;
        overallRatingZeroCounter = 0;
        exceptionCounter = 0;
        noCalcIds = new ArrayList<Integer>();
        kaWoWBTypeIds = new ArrayList<Integer>();
        unexpectedErrorIds = new ArrayList<Integer>();
        exceptionIds = new ArrayList<Integer>();
        vorkartIds = new ArrayList<Integer>();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @throws  Throwable  DOCUMENT ME!
     */
    private void shutdown() throws Throwable {
        try {
            server.shutdown();
        } catch (final ServerExit e) {
            // success
        } catch (final ServerExitError e) {
            System.err.println("exit error");
        }
        try {
            proxy.shutdown();
        } catch (final ServerExit e) {
            // success
        } catch (final ServerExitError e) {
            System.err.println("exit error");
        }
        try {
            registry.shutdown();
        } catch (final ServerExit e) {
            // success
        } catch (final ServerExitError e) {
            System.err.println("exit error");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void generateReport() throws Exception {
        final Statement stmtCids = con.createStatement();
        final ResultSet setCids = stmtCids.executeQuery("select id from public.fgsk_kartierabschnitt order by id");

        int idCids = -1;
        int cidsCounter = 0;
        int unexpectedErrors = 0;
        int noCalcCounter = 0;
        int vorkCounter = 0;

        while (setCids.next()) {
            try {
                cidsCounter++;
                idCids = setCids.getInt(1);

                final MetaObject mo = server.getMetaObject(session.getUser(), idCids, classId);

                if (mo == null) {
                    throw new IllegalStateException("cannot fetch cids metaobject: " + idCids);
                }

                System.out.println();
                System.out.println("Kartierabschnitt: " + cidsCounter + " mit id " + idCids);
                System.out.println();
                try {
                    Calc.getInstance().removeAllRatings(mo.getBean());
                } catch (final IllegalStateException ex) {
                    System.err.println("cannot remove all ratings"); // NOI18N
                    ex.printStackTrace();
                }

                if (FgskKartierabschnittEditor.isException(mo.getBean())) {
                    ++exceptionCounter;
                    exceptionIds.add(idCids);
                    saveBean(mo.getBean());
                    continue;
                }
                final Boolean vork = (Boolean)mo.getBean().getProperty("vorkatierung");

                if ((vork != null) && vork.booleanValue()) {
                    ++vorkCounter;
                    vorkartIds.add(idCids);
                    saveBean(mo.getBean());
                    continue;
                }

                if (calcAll(mo.getBean())) {
                    ++noCalcCounter;
                    noCalcIds.add(idCids);
                }

                saveBean(mo.getBean());

                System.out.println();
                System.out.println("---------------------------------------------------------------------------------");
            } catch (final Exception e) {
                if ((e instanceof IllegalStateException)
                            && e.getMessage().contains("kartierabschnitt bean without water body type")) {
                    System.out.println("KARTIERABSCHNITT OHNE WASSERKÖRPERTYP NICHT BERECHENBAR");
                    kaWoWBTypeCounter++;
                    kaWoWBTypeIds.add(idCids);
                } else {
                    System.out.println(
                        "=================================================================================");
                    System.out.println();
                    e.printStackTrace();
                    System.out.println(
                        "=================================================================================");
                    unexpectedErrorIds.add(idCids);
                    unexpectedErrors++;
                }
            }
        }

        System.out.println();
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Anzahl FGSK Kartierabschnitte:                     " + cidsCounter);
        System.out.println("Anzahl nicht berechenbarer Kartierabschnitte:      " + noCalcCounter);
        System.out.println("Anzahl nicht berechenbarer Gesamtpunktzahlen:      " + overallRatingZeroCounter);
        System.out.println("Anzahl Sonderfälle:                                " + exceptionCounter);
        System.out.println("Anzahl Kartierabschnitte ohne Wasserkörpertyp:     " + kaWoWBTypeCounter);
        System.out.println("Anzahl unerwarteter Fehler:                        " + unexpectedErrors);
        System.out.println("Anzahl Validierungsdiskrepanz Teilberechnung:      " + missingDataCounter);
        System.out.println();
        System.out.println("=================================================================================");
        System.out.println("=================================================================================");
        System.out.println("=================================================================================");

        printIds();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean  DOCUMENT ME!
     */
    private void saveBean(final CidsBean bean) {
        try {
            bean.persist();
        } catch (final Exception e) {
            System.out.println();
            System.out.println("COULD NOT STORE RATING DATA FOR BEAN: " + bean.getProperty("id"));
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean calcAll(final CidsBean cidsBean) {
        boolean calcError = false;

        try {
            Calc.getInstance().calcWBEnvRating(cidsBean);
        } catch (final ValidationException ex) {
            System.err.println("cannot calculate wb env rating"); // NOI18N
            ex.printStackTrace();
            calcError = true;
        }

        try {
            Calc.getInstance().calcWBLongProfileRating(cidsBean);
        } catch (final ValidationException ex) {
            System.err.println("cannot calculate long profile rating"); // NOI18N
            ex.printStackTrace();
            calcError = true;
        }

        try {
            Calc.getInstance().calcCourseEvoRating(cidsBean);
        } catch (final ValidationException ex) {
            System.err.println("cannot calculate course evolution rating"); // NOI18N
            ex.printStackTrace();
            calcError = true;
        }

        try {
            Calc.getInstance().calcWBCrossProfileRating(cidsBean);
        } catch (final ValidationException ex) {
            System.err.println("cannot calculate cross profile rating"); // NOI18N
            ex.printStackTrace();
            calcError = true;
        }

        roundAll(cidsBean);
        try {
            Calc.getInstance().calcBedStructureRating(cidsBean);
        } catch (final ValidationException ex) {
            System.err.println("cannot calculate bed structure rating"); // NOI18N
            ex.printStackTrace();
            calcError = true;
        }

        try {
            Calc.getInstance().calcBankStructureRating(cidsBean);
        } catch (final ValidationException ex) {
            System.err.println("cannot calculate bank structure rating"); // NOI18N
            ex.printStackTrace();
            calcError = true;
        }

        try {
            Calc.getInstance().calcBedRating(cidsBean);
        } catch (final ValidationException ex) {
            System.err.println("cannot calculate bed rating"); // NOI18N
            ex.printStackTrace();
            calcError = true;
        }

        try {
            Calc.getInstance().calcBankRating(cidsBean);
        } catch (final ValidationException ex) {
            System.err.println("cannot calculate bank rating"); // NOI18N
            ex.printStackTrace();
            calcError = true;
        }

        try {
            Calc.getInstance().calcEnvRating(cidsBean);
        } catch (final ValidationException ex) {
            System.err.println("cannot calculate env rating"); // NOI18N
            ex.printStackTrace();
            calcError = true;
        }

        try {
            Calc.getInstance().calcOverallRating(cidsBean);
        } catch (final ValidationException ex) {
            System.err.println("cannot calculate overall rating"); // NOI18N
            ex.printStackTrace();
            calcError = true;
        }

        return calcError;
    }

    /**
     * See editor.
     *
     * @param  bean  DOCUMENT ME!
     */
    private void roundAll(final CidsBean bean) {
        final String[] postfixes = { "ton", "san", "kie", "ste", "blo", "sch", "tor", "tot", "wur", "kue" };

        for (final String tmp : postfixes) {
            try {
                final String propName = "sohlensubstrat_" + tmp;
                final Double value = ((bean.getProperty(propName) != null) ? (Double)bean.getProperty(propName) : 0.0);
                bean.setProperty(propName, (Double)((double)Math.round(value)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void printIds() {
        System.out.println();
        System.out.println("APPENDIX Kartierabschnitt ids ");
        System.out.println();
        System.out.println("Nicht berechenbare Datensätze");
        System.out.println(Arrays.toString(noCalcIds.toArray()));
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Vorkartierung");
        System.out.println(Arrays.toString(vorkartIds.toArray()));
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Sonderfälle");
        System.out.println(Arrays.toString(exceptionIds.toArray()));
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Datensätze ohne Wasserkörpertyp");
        System.out.println(Arrays.toString(kaWoWBTypeIds.toArray()));
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Unerwartete Fehler");
        System.out.println(Arrays.toString(unexpectedErrorIds.toArray()));
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Throwable  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Throwable {
        CompleteFgskCalc r = null;
        PrintStream ps = null;
        final PrintStream out = System.out;
        final PrintStream err = System.err;
        try {
            ps = new PrintStream(new FileOutputStream(new File("/var/log/recalc.txt")), false, "UTF-8");
//            ps = new PrintStream(new FileOutputStream(new File("/home/therter/recalc.txt")), false, "UTF-8");
            r = new CompleteFgskCalc();

            System.setOut(ps);
            System.setErr(ps);

            r.generateReport();
        } catch (final Throwable t) {
            t.printStackTrace();
        } finally {
            System.setOut(out);
            System.setErr(err);

            r.shutdown();
            ps.close();
        }

        System.out.println("FINISHED");
    }
}
