/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.fgsk;

import Sirius.server.sql.DBConnectionPool;
import de.cismet.tools.ScriptRunner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import Sirius.navigator.connection.ConnectionFactory;
import Sirius.navigator.connection.ConnectionInfo;
import Sirius.navigator.connection.ConnectionSession;
import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.connection.proxy.ConnectionProxy;
import Sirius.navigator.resource.PropertyManager;

import Sirius.server.ServerExit;
import Sirius.server.ServerExitError;
import Sirius.server.localserver.attribute.Attribute;
import Sirius.server.localserver.attribute.ObjectAttribute;
import Sirius.server.middleware.impls.domainserver.DomainServerImpl;
import Sirius.server.middleware.impls.proxy.StartProxy;
import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.TypeVisitor;
import Sirius.server.newuser.User;
import Sirius.server.newuser.UserGroup;
import Sirius.server.property.ServerProperties;
import Sirius.server.registry.Registry;

import Sirius.util.Mapable;

import org.apache.log4j.Logger;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.fromstring.FromStringCreator;

import de.cismet.netutil.Proxy;

//import de.cismet.remotetesthelper.RemoteTestHelperService;

//import de.cismet.remotetesthelper.ws.rest.RemoteTestHelperClient;

import java.util.Arrays;
import org.junit.Ignore;
import static org.junit.Assert.*;

import static de.cismet.cids.custom.wrrl_db_mv.fgsk.Calc.*;
import java.util.LinkedHashMap;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
// NOTE: this test class is usually not activated because of its very time-consuming tests, don't forget to activate it
// if you alter calculations
public class CalcTest {

    //~ Static fields/initializers ---------------------------------------------

    private static final String TEST_DB_NAME = "calc_test_db";
//    private static final RemoteTestHelperService SERVICE = new RemoteTestHelperClient();
    private static final String SERVER_CONFIG =
        "src/test/resources/de/cismet/cids/custom/wrrl_db_mv/fgsk/runtime.properties"; // NOI18N

    private static Registry registry;
    private static StartProxy proxy;
    private static DomainServerImpl server;
    private static User user;

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @throws  Throwable  DOCUMENT ME!
     */
//    @BeforeClass
    public static void setUpClass() throws Throwable {
        final Properties p = new Properties();
        p.put("log4j.appender.Remote", "org.apache.log4j.net.SocketAppender");
        p.put("log4j.appender.Remote.remoteHost", "localhost");
        p.put("log4j.appender.Remote.port", "4445");
        p.put("log4j.appender.Remote.locationInfo", "true");
        p.put("log4j.rootLogger", "ALL,Remote");
        org.apache.log4j.PropertyConfigurator.configure(p);

//        if (!Boolean.valueOf(SERVICE.initCidsSystem(TEST_DB_NAME))) {
//            throw new IllegalStateException("cannot initilise test db");
//        }
        final ServerProperties props = new ServerProperties(SERVER_CONFIG);
        final DBConnectionPool pool = new DBConnectionPool(props);
        final Connection con = pool.getConnection();
        final ScriptRunner runner = new ScriptRunner(con, true, false);
        runner.setLogWriter(null);
        System.out.println("Inserting test data, may take some time");
        runner.runScript(new BufferedReader(new InputStreamReader(
                CalcTest.class.getResourceAsStream("CalcTestInit.sql"))));
        System.out.println("Test data inserted");
        con.close();
        pool.closeConnections();

        registry = Sirius.server.registry.Registry.getServerInstance(1099);
        proxy = StartProxy.getInstance(SERVER_CONFIG);
        server = new DomainServerImpl(props);
        user = new User(1, "dummy", "WRRL_DB_MV", new UserGroup(1, "dummy", "WRRL_DB_MV"));

        final PropertyManager propertyManager = PropertyManager.getManager();
        final ConnectionInfo info = propertyManager.getConnectionInfo();
        info.setCallserverURL("rmi://localhost/callServer");
        info.setUsername("admin");
        info.setUsergroup("Administratoren");
        info.setPassword("cismet");
        info.setUserDomain("WRRL_DB_MV");
        info.setUsergroupDomain("WRRL_DB_MV");

        final Sirius.navigator.connection.Connection connection = ConnectionFactory.getFactory()
                    .createConnection(propertyManager.getConnectionClass(),
                        info.getCallserverURL(),
                        Proxy.fromPreferences());
        final ConnectionSession session = ConnectionFactory.getFactory().createSession(connection, info, true);
        final ConnectionProxy conProxy = ConnectionFactory.getFactory()
                    .createProxy(propertyManager.getConnectionProxyClass(), session);
        SessionManager.init(conProxy);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Throwable  Exception DOCUMENT ME!
     */
//    @AfterClass
    public static void tearDownClass() throws Throwable {
        // server shuts down all the other instances since we'return interface simple mode
        try {
            server.shutdown();
        } catch (final ServerExit e) {
            // success
        } catch (final ServerExitError e) {
            System.err.println("exit error");
        }

//        if (!Boolean.valueOf(SERVICE.dropDatabase(TEST_DB_NAME))) {
//            throw new IllegalStateException("could not drop test db");
//        }
    }

    /**
     * DOCUMENT ME!
     */
//    @Before
    public void setUp() {
    }

    /**
     * DOCUMENT ME!
     */
//    @After
    public void tearDown() {
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getCurrentMethodName() {
        return new Throwable().getStackTrace()[1].getMethodName();
    }
    
    // This is only a dummy test so that junit won't complain that there is nothing to do, moreover it serves as a 
    // reminder for the skipped tests
    @Ignore
    @Test
    public void testDummy(){}

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
//    @Test
    public void testCalcWBEnvRating() throws Exception {
        System.out.println("TEST " + getCurrentMethodName());

        // kartierabschnitt id 33
        int kartierabschnitt = 33;
        CidsBean bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(11));
        bean.setProperty(PROP_LAND_USE_LE, new FakeBean(1));
        bean.setProperty(PROP_LAND_USE_RI, new FakeBean(1));
        bean.setProperty(PROP_WB_TRIMMING_LE, new FakeBean(2));
        bean.setProperty(PROP_WB_TRIMMING_RI, new FakeBean(2));
        bean.setProperty(PROP_BAD_ENV_STRUCT_AG_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_AG_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_FT_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_FT_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_GUA_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_GUA_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_BV_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_BV_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_MA_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_MA_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_HW_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_HW_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_SO_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_SO_RI, 0d);

        Calc.getInstance().calcWBEnvRating(bean);

        assertNotNull(bean.getProperty(PROP_WB_ENV_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_WB_ENV_SUM_CRIT));

        final Connection con = server.getConnectionPool().getConnection();
        final Statement stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_gewaesserumfeld_func(" + kartierabschnitt + ")"));

        ResultSet set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM gewaesserumfeld WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        double rating = set.getDouble(1);
        int criteria = set.getInt(2);

        assertEquals("found different wb env rating", rating, bean.getProperty(PROP_WB_ENV_SUM_RATING));
        assertEquals("found different wb env criteria", criteria, bean.getProperty(PROP_WB_ENV_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 120
        kartierabschnitt = 120;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(12));
        bean.setProperty(PROP_LAND_USE_LE, new FakeBean(4));
        bean.setProperty(PROP_LAND_USE_RI, new FakeBean(5));
        bean.setProperty(PROP_WB_TRIMMING_LE, new FakeBean(1));
        bean.setProperty(PROP_WB_TRIMMING_RI, new FakeBean(1));
        bean.setProperty(PROP_BAD_ENV_STRUCT_AG_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_AG_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_FT_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_FT_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_GUA_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_GUA_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_BV_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_BV_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_MA_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_MA_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_HW_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_HW_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_SO_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_SO_RI, 0d);

        Calc.getInstance().calcWBEnvRating(bean);

        assertNotNull(bean.getProperty(PROP_WB_ENV_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_WB_ENV_SUM_CRIT));

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_gewaesserumfeld_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM gewaesserumfeld WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals("found different wb env rating", rating, bean.getProperty(PROP_WB_ENV_SUM_RATING));
        assertEquals("found different wb env criteria", criteria, bean.getProperty(PROP_WB_ENV_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1878
        kartierabschnitt = 1878;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(14));
        bean.setProperty(PROP_LAND_USE_LE, new FakeBean(6));
        bean.setProperty(PROP_LAND_USE_RI, new FakeBean(6));
        bean.setProperty(PROP_WB_TRIMMING_LE, new FakeBean(3));
        bean.setProperty(PROP_WB_TRIMMING_RI, new FakeBean(2));
        bean.setProperty(PROP_BAD_ENV_STRUCT_AG_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_AG_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_FT_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_FT_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_GUA_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_GUA_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_BV_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_BV_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_MA_LE, 2d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_MA_RI, 3d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_HW_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_HW_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_SO_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_SO_RI, 0d);

        Calc.getInstance().calcWBEnvRating(bean);

        assertNotNull(bean.getProperty(PROP_WB_ENV_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_WB_ENV_SUM_CRIT));

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_gewaesserumfeld_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM gewaesserumfeld WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals("found different wb env rating", rating, bean.getProperty(PROP_WB_ENV_SUM_RATING));
        assertEquals("found different wb env criteria", criteria, bean.getProperty(PROP_WB_ENV_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 530
        kartierabschnitt = 530;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(16));
        bean.setProperty(PROP_LAND_USE_LE, new FakeBean(4));
        bean.setProperty(PROP_LAND_USE_RI, new FakeBean(8));
        bean.setProperty(PROP_WB_TRIMMING_LE, new FakeBean(3));
        bean.setProperty(PROP_WB_TRIMMING_RI, new FakeBean(2));
        bean.setProperty(PROP_BAD_ENV_STRUCT_AG_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_AG_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_FT_LE, 1d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_FT_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_GUA_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_GUA_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_BV_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_BV_RI, 3d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_MA_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_MA_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_HW_LE, 5d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_HW_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_SO_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_SO_RI, 0d);

        Calc.getInstance().calcWBEnvRating(bean);

        assertNotNull(bean.getProperty(PROP_WB_ENV_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_WB_ENV_SUM_CRIT));

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_gewaesserumfeld_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM gewaesserumfeld WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals("found different wb env rating", rating, bean.getProperty(PROP_WB_ENV_SUM_RATING));
        assertEquals("found different wb env criteria", criteria, bean.getProperty(PROP_WB_ENV_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1430
        kartierabschnitt = 1430;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(23));
        bean.setProperty(PROP_LAND_USE_LE, new FakeBean(2));
        bean.setProperty(PROP_LAND_USE_RI, new FakeBean(13));
        bean.setProperty(PROP_WB_TRIMMING_LE, new FakeBean(3));
        bean.setProperty(PROP_WB_TRIMMING_RI, new FakeBean(3));
        bean.setProperty(PROP_BAD_ENV_STRUCT_AG_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_AG_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_FT_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_FT_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_GUA_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_GUA_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_BV_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_BV_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_MA_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_MA_RI, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_HW_LE, 1d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_HW_RI, 1d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_SO_LE, 0d);
        bean.setProperty(PROP_BAD_ENV_STRUCT_SO_RI, 0d);

        Calc.getInstance().calcWBEnvRating(bean);

        assertNotNull(bean.getProperty(PROP_WB_ENV_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_WB_ENV_SUM_CRIT));

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_gewaesserumfeld_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM gewaesserumfeld WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getInt(1);
        criteria = set.getInt(2);

        assertEquals("found different wb env rating", rating, bean.getProperty(PROP_WB_ENV_SUM_RATING));
        assertEquals("found different wb env criteria", criteria, bean.getProperty(PROP_WB_ENV_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
//    @Test
    public void testCalcOverallRating() throws Exception {
        System.out.println("TEST " + getCurrentMethodName());

        // kartierabschnitt id 33
        int kartierabschnitt = 33;
        CidsBean bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_COURSE_EVO_SUM_RATING, Double.valueOf(7d));
        bean.setProperty(PROP_COURSE_EVO_SUM_CRIT, 3);
        bean.setProperty(PROP_LONG_PROFILE_SUM_RATING, Double.valueOf(3d));
        bean.setProperty(PROP_LONG_PROFILE_SUM_CRIT, 1);
        bean.setProperty(PROP_BED_STRUCTURE_SUM_RATING, Double.valueOf(5.5d));
        bean.setProperty(PROP_BED_STRUCTURE_SUM_CRIT, 2);
        bean.setProperty(PROP_CROSS_PROFILE_SUM_RATING, Double.valueOf(9d));
        bean.setProperty(PROP_CROSS_PROFILE_SUM_CRIT, 3);
        bean.setProperty(PROP_BANK_STRUCTURE_SUM_RATING, Double.valueOf(14d));
        bean.setProperty(PROP_BANK_STRUCTURE_SUM_CRIT, 4);
        bean.setProperty(PROP_WB_ENV_SUM_RATING, Double.valueOf(20d));
        bean.setProperty(PROP_WB_ENV_SUM_CRIT, 4);

        Calc.getInstance().calcOverallRating(bean);

        assertNotNull(bean.getProperty(PROP_WB_BED_RATING));
        assertNotNull(bean.getProperty(PROP_WB_BANK_RATING));
        assertNotNull(bean.getProperty(PROP_WB_ENV_RATING));
        assertNotNull(bean.getProperty(PROP_WB_OVERALL_RATING));

        Connection con = server.getConnectionPool().getConnection();
        Statement stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_gewaesserumfeld_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laengsprofil_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laufentwicklung_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_querprofil_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_uferstruktur_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_sohlenstruktur_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT update_points_kartierabschnitt_func(" + kartierabschnitt + ")"));

        ResultSet set = stmt.executeQuery(
                "SELECT punktzahl_sohle, punktzahl_ufer, punktzahl_land, punktzahl_gesamt FROM kartierabschnitt WHERE id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        double ratingBed = set.getDouble(1);
        double ratingBank = set.getDouble(2);
        double ratingEnv = set.getDouble(3);
        double ratingOverall = set.getDouble(4);

        assertEquals(
            "found different bed rating",
            ratingBed,
            bean.getProperty(PROP_WB_BED_RATING));
        assertEquals(
            "found different bank rating",
            ratingBank,
            bean.getProperty(PROP_WB_BANK_RATING));
        assertEquals(
            "found different env rating",
            ratingEnv,
            bean.getProperty(PROP_WB_ENV_RATING));
        assertEquals(
            "found different overall rating",
            ratingOverall,
            bean.getProperty(PROP_WB_OVERALL_RATING));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 120
        kartierabschnitt = 120;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_COURSE_EVO_SUM_RATING, Double.valueOf(2d));
        bean.setProperty(PROP_COURSE_EVO_SUM_CRIT, 2);
        bean.setProperty(PROP_LONG_PROFILE_SUM_RATING, Double.valueOf(3d));
        bean.setProperty(PROP_LONG_PROFILE_SUM_CRIT, 1);
        bean.setProperty(PROP_BED_STRUCTURE_SUM_RATING, Double.valueOf(4d));
        bean.setProperty(PROP_BED_STRUCTURE_SUM_CRIT, 2);
        bean.setProperty(PROP_CROSS_PROFILE_SUM_RATING, Double.valueOf(5d));
        bean.setProperty(PROP_CROSS_PROFILE_SUM_CRIT, 3);
        bean.setProperty(PROP_BANK_STRUCTURE_SUM_RATING, Double.valueOf(6d));
        bean.setProperty(PROP_BANK_STRUCTURE_SUM_CRIT, 4);
        bean.setProperty(PROP_WB_ENV_SUM_RATING, Double.valueOf(8d));
        bean.setProperty(PROP_WB_ENV_SUM_CRIT, 4);

        Calc.getInstance().calcOverallRating(bean);

        assertNotNull(bean.getProperty(PROP_WB_BED_RATING));
        assertNotNull(bean.getProperty(PROP_WB_BANK_RATING));
        assertNotNull(bean.getProperty(PROP_WB_ENV_RATING));
        assertNotNull(bean.getProperty(PROP_WB_OVERALL_RATING));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_gewaesserumfeld_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laengsprofil_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laufentwicklung_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_querprofil_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_uferstruktur_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_sohlenstruktur_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT update_points_kartierabschnitt_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT punktzahl_sohle, punktzahl_ufer, punktzahl_land, punktzahl_gesamt FROM kartierabschnitt WHERE id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

         ratingBed = set.getDouble(1);
        ratingBank = set.getDouble(2);
        ratingEnv = set.getDouble(3);
        ratingOverall = set.getDouble(4);

        assertEquals(
            "found different bed rating",
            ratingBed,
            bean.getProperty(PROP_WB_BED_RATING));
        assertEquals(
            "found different bank rating",
            ratingBank,
            bean.getProperty(PROP_WB_BANK_RATING));
        assertEquals(
            "found different env rating",
            ratingEnv,
            bean.getProperty(PROP_WB_ENV_RATING));
        assertEquals(
            "found different overall rating",
            ratingOverall,
            bean.getProperty(PROP_WB_OVERALL_RATING));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1878
        kartierabschnitt = 1878;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_COURSE_EVO_SUM_RATING, Double.valueOf(7d));
        bean.setProperty(PROP_COURSE_EVO_SUM_CRIT, 4);
        bean.setProperty(PROP_LONG_PROFILE_SUM_RATING, Double.valueOf(5d));
        bean.setProperty(PROP_LONG_PROFILE_SUM_CRIT, 3);
        bean.setProperty(PROP_BED_STRUCTURE_SUM_RATING, Double.valueOf(7.5d));
        bean.setProperty(PROP_BED_STRUCTURE_SUM_CRIT, 2);
        bean.setProperty(PROP_CROSS_PROFILE_SUM_RATING, Double.valueOf(11d));
        bean.setProperty(PROP_CROSS_PROFILE_SUM_CRIT, 4);
        bean.setProperty(PROP_BANK_STRUCTURE_SUM_RATING, Double.valueOf(12d));
        bean.setProperty(PROP_BANK_STRUCTURE_SUM_CRIT, 4);
        bean.setProperty(PROP_WB_ENV_SUM_RATING, Double.valueOf(10d));
        bean.setProperty(PROP_WB_ENV_SUM_CRIT, 4);

        Calc.getInstance().calcOverallRating(bean);

        assertNotNull(bean.getProperty(PROP_WB_BED_RATING));
        assertNotNull(bean.getProperty(PROP_WB_BANK_RATING));
        assertNotNull(bean.getProperty(PROP_WB_ENV_RATING));
        assertNotNull(bean.getProperty(PROP_WB_OVERALL_RATING));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_gewaesserumfeld_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laengsprofil_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laufentwicklung_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_querprofil_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_uferstruktur_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_sohlenstruktur_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT update_points_kartierabschnitt_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT punktzahl_sohle, punktzahl_ufer, punktzahl_land, punktzahl_gesamt FROM kartierabschnitt WHERE id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

         ratingBed = set.getDouble(1);
        ratingBank = set.getDouble(2);
        ratingEnv = set.getDouble(3);
        ratingOverall = set.getDouble(4);

        assertEquals(
            "found different bed rating",
            ratingBed,
            bean.getProperty(PROP_WB_BED_RATING));
        assertEquals(
            "found different bank rating",
            ratingBank,
            bean.getProperty(PROP_WB_BANK_RATING));
        assertEquals(
            "found different env rating",
            ratingEnv,
            bean.getProperty(PROP_WB_ENV_RATING));
        assertEquals(
            "found different overall rating",
            ratingOverall,
            bean.getProperty(PROP_WB_OVERALL_RATING));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 530
        kartierabschnitt = 530;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_COURSE_EVO_SUM_RATING, Double.valueOf(11d));
        bean.setProperty(PROP_COURSE_EVO_SUM_CRIT, 4);
        bean.setProperty(PROP_LONG_PROFILE_SUM_RATING, Double.valueOf(11d));
        bean.setProperty(PROP_LONG_PROFILE_SUM_CRIT, 4);
        bean.setProperty(PROP_BED_STRUCTURE_SUM_RATING, Double.valueOf(8.5d));
        bean.setProperty(PROP_BED_STRUCTURE_SUM_CRIT, 3);
        bean.setProperty(PROP_CROSS_PROFILE_SUM_RATING, Double.valueOf(14d));
        bean.setProperty(PROP_CROSS_PROFILE_SUM_CRIT, 4);
        bean.setProperty(PROP_BANK_STRUCTURE_SUM_RATING, Double.valueOf(12d));
        bean.setProperty(PROP_BANK_STRUCTURE_SUM_CRIT, 5);
        bean.setProperty(PROP_WB_ENV_SUM_RATING, Double.valueOf(12.5d));
        bean.setProperty(PROP_WB_ENV_SUM_CRIT, 4);

        Calc.getInstance().calcOverallRating(bean);

        assertNotNull(bean.getProperty(PROP_WB_BED_RATING));
        assertNotNull(bean.getProperty(PROP_WB_BANK_RATING));
        assertNotNull(bean.getProperty(PROP_WB_ENV_RATING));
        assertNotNull(bean.getProperty(PROP_WB_OVERALL_RATING));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_gewaesserumfeld_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laengsprofil_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laufentwicklung_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_querprofil_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_uferstruktur_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_sohlenstruktur_func(" + kartierabschnitt + ")"));
        assertTrue(
            "could not execute function",
            stmt.execute("SELECT update_points_kartierabschnitt_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT punktzahl_sohle, punktzahl_ufer, punktzahl_land, punktzahl_gesamt FROM kartierabschnitt WHERE id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

         ratingBed = set.getDouble(1);
        ratingBank = set.getDouble(2);
        ratingEnv = set.getDouble(3);
        ratingOverall = set.getDouble(4);

        assertEquals(
            "found different bed rating",
            ratingBed,
            (Double)bean.getProperty(PROP_WB_BED_RATING), 
            0.001d);
        assertEquals(
            "found different bank rating",
            ratingBank,
            (Double)bean.getProperty(PROP_WB_BANK_RATING), 
            0.001d);
        assertEquals(
            "found different env rating",
            ratingEnv,
            (Double)bean.getProperty(PROP_WB_ENV_RATING), 
            0.001d);
        assertEquals(
            "found different overall rating",
            ratingOverall,
            (Double)bean.getProperty(PROP_WB_OVERALL_RATING), 
            0.001d);

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1430
        // won't be used here because it contains null ratings
    }
    
//    @Test(expected=ValidationException.class)
    public void testCalcOverallRatingZeroProperty() throws Exception{
        // kartierabschnitt id 1430
        int kartierabschnitt = 1430;
        CidsBean bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_COURSE_EVO_SUM_RATING, Double.valueOf(5d));
        bean.setProperty(PROP_COURSE_EVO_SUM_CRIT, 2);
        bean.setProperty(PROP_LONG_PROFILE_SUM_RATING, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_PROFILE_SUM_CRIT, 0);
        bean.setProperty(PROP_BED_STRUCTURE_SUM_RATING, Double.valueOf(4.5d));
        bean.setProperty(PROP_BED_STRUCTURE_SUM_CRIT, 1);
        bean.setProperty(PROP_CROSS_PROFILE_SUM_RATING, Double.valueOf(10d));
        bean.setProperty(PROP_CROSS_PROFILE_SUM_CRIT, 3);
        bean.setProperty(PROP_BANK_STRUCTURE_SUM_RATING, Double.valueOf(19.5d));
        bean.setProperty(PROP_BANK_STRUCTURE_SUM_CRIT, 5);
        bean.setProperty(PROP_WB_ENV_SUM_RATING, Double.valueOf(17d));
        bean.setProperty(PROP_WB_ENV_SUM_CRIT, 4);

        Calc.getInstance().calcOverallRating(bean);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
//    @Test
    public void testCalcWBLongProfileRating() throws Exception {
        System.out.println("TEST " + getCurrentMethodName());

        // kartierabschnitt id 33
        int kartierabschnitt = 33;
        CidsBean bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(11));
        bean.setProperty(PROP_FLOW_VELOCITY, new FakeBean(3));
        bean.setProperty(PROP_FLOW_DIVERSITY, new FakeBean(2));
        bean.setProperty(PROP_DEPTH_VARIANCE, new FakeBean(2));
        bean.setProperty(PROP_DEPTH_EROSION, new FakeBean(1));
        bean.setProperty(PROP_CROSS_BENCH_COUNT, Double.valueOf(1.0d));

        CidsBean stationBean = new FakeBean();
        CidsBean toBean = new FakeBean();
        CidsBean fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(1545d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(1336d));

        Calc.getInstance().calcWBLongProfileRating(bean);

        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        Connection con = server.getConnectionPool().getConnection();
        Statement stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laengsprofil_func(" + kartierabschnitt + ")"));

        ResultSet set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM laengsprofil WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        double rating = set.getDouble(1);
        int criteria = set.getInt(2);

        assertEquals(
            "found different long profile rating",
            rating,
            bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertEquals(
            "found different long profile criteria",
            criteria,
            bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 120
        kartierabschnitt = 120;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(12));
        bean.setProperty(PROP_FLOW_VELOCITY, new FakeBean(3));
        bean.setProperty(PROP_FLOW_DIVERSITY, new FakeBean(1));
        bean.setProperty(PROP_DEPTH_VARIANCE, new FakeBean(1));
        bean.setProperty(PROP_DEPTH_EROSION, new FakeBean(1));
        bean.setProperty(PROP_CROSS_BENCH_COUNT, Double.valueOf(0.0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(63129d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(62855d));

        Calc.getInstance().calcWBLongProfileRating(bean);

        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laengsprofil_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM laengsprofil WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different long profile rating",
            rating,
            bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertEquals(
            "found different long profile criteria",
            criteria,
            bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1878
        kartierabschnitt = 1878;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(14));
        bean.setProperty(PROP_FLOW_VELOCITY, new FakeBean(2));
        bean.setProperty(PROP_FLOW_DIVERSITY, new FakeBean(2));
        bean.setProperty(PROP_DEPTH_VARIANCE, new FakeBean(2));
        bean.setProperty(PROP_DEPTH_EROSION, new FakeBean(1));
        bean.setProperty(PROP_CROSS_BENCH_COUNT, Double.valueOf(0.0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(13416d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(13294d));

        Calc.getInstance().calcWBLongProfileRating(bean);

        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laengsprofil_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM laengsprofil WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different long profile rating",
            rating,
            bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertEquals(
            "found different long profile criteria",
            criteria,
            bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 530
        kartierabschnitt = 530;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(16));
        bean.setProperty(PROP_FLOW_VELOCITY, new FakeBean(2));
        bean.setProperty(PROP_FLOW_DIVERSITY, new FakeBean(3));
        bean.setProperty(PROP_DEPTH_VARIANCE, new FakeBean(4));
        bean.setProperty(PROP_DEPTH_EROSION, new FakeBean(2));
        bean.setProperty(PROP_CROSS_BENCH_COUNT, Double.valueOf(0.0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(366d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(60d));

        Calc.getInstance().calcWBLongProfileRating(bean);

        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laengsprofil_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM laengsprofil WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different long profile rating",
            rating,
            bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertEquals(
            "found different long profile criteria",
            criteria,
            bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1430
        kartierabschnitt = 1430;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(23));
        bean.setProperty(PROP_FLOW_VELOCITY, new FakeBean(2));
        bean.setProperty(PROP_FLOW_DIVERSITY, new FakeBean(1));
        bean.setProperty(PROP_DEPTH_VARIANCE, new FakeBean(1));
        bean.setProperty(PROP_DEPTH_EROSION, new FakeBean(1));
        bean.setProperty(PROP_CROSS_BENCH_COUNT, Double.valueOf(0.0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(885d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(636d));

        Calc.getInstance().calcWBLongProfileRating(bean);

        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laengsprofil_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM laengsprofil WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different long profile rating",
            rating,
            bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertEquals(
            "found different long profile criteria",
            criteria,
            bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());
        
        // kartierabschnitt id 1517
        kartierabschnitt = 1517;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(14));
        bean.setProperty(PROP_FLOW_VELOCITY, new FakeBean(2));
        bean.setProperty(PROP_FLOW_DIVERSITY, new FakeBean(1));
        bean.setProperty(PROP_DEPTH_VARIANCE, new FakeBean(1));
        bean.setProperty(PROP_DEPTH_EROSION, new FakeBean(1));
        bean.setProperty(PROP_CROSS_BENCH_COUNT, Double.valueOf(0.0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(2900d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(2725d));

        Calc.getInstance().calcWBLongProfileRating(bean);

        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laengsprofil_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM laengsprofil WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different long profile rating",
            rating,
            bean.getProperty(PROP_LONG_PROFILE_SUM_RATING));
        assertEquals(
            "found different long profile criteria",
            criteria,
            bean.getProperty(PROP_LONG_PROFILE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
//    @Test
    public void testCalcCourseEvoRating() throws Exception {
        System.out.println("TEST " + getCurrentMethodName());

        // kartierabschnitt id 33
        int kartierabschnitt = 33;
        CidsBean bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(11));
        bean.setProperty(PROP_COURSE_LOOP, new FakeBean(5));
        bean.setProperty(PROP_LOOP_EROSION, new FakeBean(4));
        bean.setProperty(PROP_COURSE_STRUCTURE_TV, Double.valueOf(1d));
        bean.setProperty(PROP_COURSE_STRUCTURE_SB, Double.valueOf(4d));
        bean.setProperty(PROP_COURSE_STRUCTURE_IBI, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LW, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LV, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LG, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_UFKG, Double.valueOf(0.5d));
        bean.setProperty(PROP_LONG_BENCH_IB, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_MB, Double.valueOf(0d));

        CidsBean stationBean = new FakeBean();
        CidsBean toBean = new FakeBean();
        CidsBean fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(1545d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(1336d));

        Calc.getInstance().calcCourseEvoRating(bean);

        assertNotNull(bean.getProperty(PROP_COURSE_EVO_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_COURSE_EVO_SUM_CRIT));

        Connection con = server.getConnectionPool().getConnection();
        Statement stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laufentwicklung_func(" + kartierabschnitt + ")"));

        ResultSet set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM laufentwicklung WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        double rating = set.getDouble(1);
        int criteria = set.getInt(2);

        assertEquals(
            "found different course evo rating",
            rating,
            bean.getProperty(PROP_COURSE_EVO_SUM_RATING));
        assertEquals(
            "found different course evo criteria",
            criteria,
            bean.getProperty(PROP_COURSE_EVO_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 120
        kartierabschnitt = 120;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(12));
        bean.setProperty(PROP_COURSE_LOOP, new FakeBean(7));
        bean.setProperty(PROP_LOOP_EROSION, new FakeBean(5));
        bean.setProperty(PROP_COURSE_STRUCTURE_TV, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_SB, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_IBI, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LW, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LV, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LG, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_UFKG, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_IB, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_MB, Double.valueOf(0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(63129d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(62855d));

        Calc.getInstance().calcCourseEvoRating(bean);

        assertNotNull(bean.getProperty(PROP_COURSE_EVO_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_COURSE_EVO_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laufentwicklung_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM laufentwicklung WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different course evo rating",
            rating,
            bean.getProperty(PROP_COURSE_EVO_SUM_RATING));
        assertEquals(
            "found different course evo criteria",
            criteria,
            bean.getProperty(PROP_COURSE_EVO_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1878
        kartierabschnitt = 1878;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(14));
        bean.setProperty(PROP_COURSE_LOOP, new FakeBean(6));
        bean.setProperty(PROP_LOOP_EROSION, new FakeBean(5));
        bean.setProperty(PROP_COURSE_STRUCTURE_TV, Double.valueOf(4.0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_SB, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_IBI, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LW, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LV, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LG, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_UFKG, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_IB, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_MB, Double.valueOf(0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(13416d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(13294d));

        Calc.getInstance().calcCourseEvoRating(bean);

        assertNotNull(bean.getProperty(PROP_COURSE_EVO_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_COURSE_EVO_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laufentwicklung_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM laufentwicklung WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different course evo rating",
            rating,
            bean.getProperty(PROP_COURSE_EVO_SUM_RATING));
        assertEquals(
            "found different course evo criteria",
            criteria,
            bean.getProperty(PROP_COURSE_EVO_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 530
        kartierabschnitt = 530;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(16));
        bean.setProperty(PROP_COURSE_LOOP, new FakeBean(5));
        bean.setProperty(PROP_LOOP_EROSION, new FakeBean(4));
        bean.setProperty(PROP_COURSE_STRUCTURE_TV, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_SB, Double.valueOf(4.0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_IBI, Double.valueOf(1.0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LW, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LV, Double.valueOf(1.0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LG, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_UFKG, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_IB, Double.valueOf(1.0d));
        bean.setProperty(PROP_LONG_BENCH_MB, Double.valueOf(0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(366d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(60d));

        Calc.getInstance().calcCourseEvoRating(bean);

        assertNotNull(bean.getProperty(PROP_COURSE_EVO_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_COURSE_EVO_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laufentwicklung_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM laufentwicklung WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different course evo rating",
            rating,
            bean.getProperty(PROP_COURSE_EVO_SUM_RATING));
        assertEquals(
            "found different course evo criteria",
            criteria,
            bean.getProperty(PROP_COURSE_EVO_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1430
        kartierabschnitt = 1430;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(23));
        bean.setProperty(PROP_COURSE_LOOP, new FakeBean(5));
        bean.setProperty(PROP_LOOP_EROSION, new FakeBean(5));
        bean.setProperty(PROP_COURSE_STRUCTURE_TV, Double.valueOf(1.0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_SB, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_IBI, Double.valueOf(0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LW, Double.valueOf(1.0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LV, Double.valueOf(1.0d));
        bean.setProperty(PROP_COURSE_STRUCTURE_LG, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_UFKG, Double.valueOf(2.0d));
        bean.setProperty(PROP_LONG_BENCH_IB, Double.valueOf(0d));
        bean.setProperty(PROP_LONG_BENCH_MB, Double.valueOf(0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(885d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(636d));

        Calc.getInstance().calcCourseEvoRating(bean);

        assertNotNull(bean.getProperty(PROP_COURSE_EVO_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_COURSE_EVO_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_laufentwicklung_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM laufentwicklung WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different course evo rating",
            rating,
            bean.getProperty(PROP_COURSE_EVO_SUM_RATING));
        assertEquals(
            "found different course evo criteria",
            criteria,
            bean.getProperty(PROP_COURSE_EVO_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
//    @Test
    public void testCalcWBCrossProfileRating() throws Exception {
        System.out.println("TEST " + getCurrentMethodName());

        // kartierabschnitt id 33
        int kartierabschnitt = 33;
        CidsBean bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(11));
        bean.setProperty(PROP_PROFILE_TYPE, new FakeBean(2));
        bean.setProperty(PROP_BREADTH_VARIANCE, new FakeBean(3));
        bean.setProperty(PROP_BREADTH_EROSION, new FakeBean(3));
        bean.setProperty(PROP_UPPER_PROFILE_BREADTH, Double.valueOf(2.0));
        bean.setProperty(PROP_INCISION_DEPTH, Double.valueOf(0.2999999999999999999d));
        bean.setProperty(PROP_WATER_DEPTH, Double.valueOf(0.2999999999999999999d));

        Calc.getInstance().calcWBCrossProfileRating(bean);

        assertNotNull(bean.getProperty(PROP_CROSS_PROFILE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_CROSS_PROFILE_SUM_CRIT));

        Connection con = server.getConnectionPool().getConnection();
        Statement stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_querprofil_func(" + kartierabschnitt + ")"));

        ResultSet set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM querprofil WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        double rating = set.getDouble(1);
        int criteria = set.getInt(2);

        assertEquals(
            "found different cross profile rating",
            rating,
            bean.getProperty(PROP_CROSS_PROFILE_SUM_RATING));
        assertEquals(
            "found different cross profile criteria",
            criteria,
            bean.getProperty(PROP_CROSS_PROFILE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 120
        kartierabschnitt = 120;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(12));
        bean.setProperty(PROP_PROFILE_TYPE, new FakeBean(5));
        bean.setProperty(PROP_BREADTH_VARIANCE, new FakeBean(1));
        bean.setProperty(PROP_BREADTH_EROSION, new FakeBean(4));
        bean.setProperty(PROP_UPPER_PROFILE_BREADTH, Double.valueOf(9.0));
        bean.setProperty(PROP_INCISION_DEPTH, Double.valueOf(0.80000000000000004d));
        bean.setProperty(PROP_WATER_DEPTH, Double.valueOf(0.80000000000000004d));

        Calc.getInstance().calcWBCrossProfileRating(bean);

        assertNotNull(bean.getProperty(PROP_CROSS_PROFILE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_CROSS_PROFILE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_querprofil_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM querprofil WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different cross profile rating",
            rating,
            bean.getProperty(PROP_CROSS_PROFILE_SUM_RATING));
        assertEquals(
            "found different cross profile criteria",
            criteria,
            bean.getProperty(PROP_CROSS_PROFILE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1878
        kartierabschnitt = 1878;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(14));
        bean.setProperty(PROP_PROFILE_TYPE, new FakeBean(4));
        bean.setProperty(PROP_BREADTH_VARIANCE, new FakeBean(1));
        bean.setProperty(PROP_BREADTH_EROSION, new FakeBean(3));
        bean.setProperty(PROP_UPPER_PROFILE_BREADTH, Double.valueOf(8.5));
        bean.setProperty(PROP_INCISION_DEPTH, Double.valueOf(2.0d));
        bean.setProperty(PROP_WATER_DEPTH, Double.valueOf(1.0d));

        Calc.getInstance().calcWBCrossProfileRating(bean);

        assertNotNull(bean.getProperty(PROP_CROSS_PROFILE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_CROSS_PROFILE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_querprofil_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM querprofil WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different cross profile rating",
            rating,
            bean.getProperty(PROP_CROSS_PROFILE_SUM_RATING));
        assertEquals(
            "found different cross profile criteria",
            criteria,
            bean.getProperty(PROP_CROSS_PROFILE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 530
        kartierabschnitt = 530;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(16));
        bean.setProperty(PROP_PROFILE_TYPE, new FakeBean(2));
        bean.setProperty(PROP_BREADTH_VARIANCE, new FakeBean(4));
        bean.setProperty(PROP_BREADTH_EROSION, new FakeBean(2));
        bean.setProperty(PROP_UPPER_PROFILE_BREADTH, Double.valueOf(8.9000000000000004d));
        bean.setProperty(PROP_INCISION_DEPTH, Double.valueOf(1.8999999999999999d));
        bean.setProperty(PROP_WATER_DEPTH, Double.valueOf(0.5d));

        Calc.getInstance().calcWBCrossProfileRating(bean);

        assertNotNull(bean.getProperty(PROP_CROSS_PROFILE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_CROSS_PROFILE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_querprofil_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM querprofil WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different cross profile rating",
            rating,
            bean.getProperty(PROP_CROSS_PROFILE_SUM_RATING));
        assertEquals(
            "found different cross profile criteria",
            criteria,
            bean.getProperty(PROP_CROSS_PROFILE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1430
        kartierabschnitt = 1430;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(23));
        bean.setProperty(PROP_PROFILE_TYPE, new FakeBean(4));
        bean.setProperty(PROP_BREADTH_VARIANCE, new FakeBean(2));
        bean.setProperty(PROP_BREADTH_EROSION, new FakeBean(4));
        bean.setProperty(PROP_UPPER_PROFILE_BREADTH, Double.valueOf(27.0d));
        bean.setProperty(PROP_INCISION_DEPTH, Double.valueOf(1.8d));
        bean.setProperty(PROP_WATER_DEPTH, Double.valueOf(0.90000000000000002d));

        Calc.getInstance().calcWBCrossProfileRating(bean);

        assertNotNull(bean.getProperty(PROP_CROSS_PROFILE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_CROSS_PROFILE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_querprofil_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM querprofil WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different cross profile rating",
            rating,
            bean.getProperty(PROP_CROSS_PROFILE_SUM_RATING));
        assertEquals(
            "found different cross profile criteria",
            criteria,
            bean.getProperty(PROP_CROSS_PROFILE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
//    @Test
    public void testCalcBedStructureRating() throws Exception {
        System.out.println("TEST " + getCurrentMethodName());

        // kartierabschnitt id 33
        int kartierabschnitt = 33;
        CidsBean bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(11));
        bean.setProperty(PROP_BED_FITMENT, new FakeBean(4));
        bean.setProperty(PROP_Z_BED_FITMENT, new FakeBean(4));
        bean.setProperty(PROP_BED_SUBSTRATE_TON, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_SAN, Double.valueOf(10d));
        bean.setProperty(PROP_BED_SUBSTRATE_KIE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_STE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_BLO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_SCH, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_TOR, Double.valueOf(80d));
        bean.setProperty(PROP_BED_SUBSTRATE_TOT, Double.valueOf(5d));
        bean.setProperty(PROP_BED_SUBSTRATE_WUR, Double.valueOf(5d));
        bean.setProperty(PROP_BED_SUBSTRATE_KUE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_RIP, Double.valueOf(1d));
        bean.setProperty(PROP_BED_STRUCTURE_TH, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_WU, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_KO, Double.valueOf(1d));
        bean.setProperty(PROP_BED_CONTAMINATION_MUE, Double.valueOf(2d));
        bean.setProperty(PROP_BED_CONTAMINATION_ST, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_ABW, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_VO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_SA, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_SO, Double.valueOf(0d));

        CidsBean stationBean = new FakeBean();
        CidsBean toBean = new FakeBean();
        CidsBean fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(1545d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(1336d));

        Calc.getInstance().calcBedStructureRating(bean);

        assertNotNull(bean.getProperty(PROP_BED_STRUCTURE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_BED_STRUCTURE_SUM_CRIT));

        Connection con = server.getConnectionPool().getConnection();
        Statement stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_sohlenstruktur_func(" + kartierabschnitt + ")"));

        ResultSet set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM sohlenstruktur WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        double rating = set.getDouble(1);
        int criteria = set.getInt(2);

        assertEquals(
            "found different bed structure rating",
            rating,
            bean.getProperty(PROP_BED_STRUCTURE_SUM_RATING));
        assertEquals(
            "found different bed structure criteria",
            criteria,
            bean.getProperty(PROP_BED_STRUCTURE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 120
        kartierabschnitt = 120;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(12));
        bean.setProperty(PROP_BED_FITMENT, new FakeBean(4));
        bean.setProperty(PROP_Z_BED_FITMENT, new FakeBean(4));
        bean.setProperty(PROP_BED_SUBSTRATE_TON, Double.valueOf(10d));
        bean.setProperty(PROP_BED_SUBSTRATE_SAN, Double.valueOf(75d));
        bean.setProperty(PROP_BED_SUBSTRATE_KIE, Double.valueOf(10d));
        bean.setProperty(PROP_BED_SUBSTRATE_STE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_BLO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_SCH, Double.valueOf(5d));
        bean.setProperty(PROP_BED_SUBSTRATE_TOR, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_TOT, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_WUR, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_KUE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_RIP, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_TH, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_WU, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_KO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_MUE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_ST, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_ABW, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_VO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_SA, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_SO, Double.valueOf(0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(63129d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(62855d));

        Calc.getInstance().calcBedStructureRating(bean);

        assertNotNull(bean.getProperty(PROP_BED_STRUCTURE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_BED_STRUCTURE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_sohlenstruktur_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM sohlenstruktur WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different bed structure rating",
            rating,
            bean.getProperty(PROP_BED_STRUCTURE_SUM_RATING));
        assertEquals(
            "found different bed structure criteria",
            criteria,
            bean.getProperty(PROP_BED_STRUCTURE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1878
        kartierabschnitt = 1878;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(14));
        bean.setProperty(PROP_BED_FITMENT, new FakeBean(4));
        bean.setProperty(PROP_Z_BED_FITMENT, new FakeBean(4));
        bean.setProperty(PROP_BED_SUBSTRATE_TON, Double.valueOf(5d));
        bean.setProperty(PROP_BED_SUBSTRATE_SAN, Double.valueOf(65d));
        bean.setProperty(PROP_BED_SUBSTRATE_KIE, Double.valueOf(15d));
        bean.setProperty(PROP_BED_SUBSTRATE_STE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_BLO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_SCH, Double.valueOf(5d));
        bean.setProperty(PROP_BED_SUBSTRATE_TOR, Double.valueOf(5d));
        bean.setProperty(PROP_BED_SUBSTRATE_TOT, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_WUR, Double.valueOf(5d));
        bean.setProperty(PROP_BED_SUBSTRATE_KUE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_RIP, Double.valueOf(1d));
        bean.setProperty(PROP_BED_STRUCTURE_TH, Double.valueOf(3d));
        bean.setProperty(PROP_BED_STRUCTURE_WU, Double.valueOf(4d));
        bean.setProperty(PROP_BED_STRUCTURE_KO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_MUE, Double.valueOf(1d));
        bean.setProperty(PROP_BED_CONTAMINATION_ST, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_ABW, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_VO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_SA, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_SO, Double.valueOf(0d));

        final FakeBean subtypeBean = new FakeBean();
        subtypeBean.setProperty(PROP_VALUE, "M");
        bean.setProperty(PROP_WB_SUB_TYPE, Arrays.asList(subtypeBean));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(13416d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(13294d));

        Calc.getInstance().calcBedStructureRating(bean);

        assertNotNull(bean.getProperty(PROP_BED_STRUCTURE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_BED_STRUCTURE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_sohlenstruktur_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM sohlenstruktur WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different bed structure rating",
            rating,
            bean.getProperty(PROP_BED_STRUCTURE_SUM_RATING));
        assertEquals(
            "found different bed structure criteria",
            criteria,
            bean.getProperty(PROP_BED_STRUCTURE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 530
        kartierabschnitt = 530;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(16));
        bean.setProperty(PROP_BED_FITMENT, new FakeBean(1));
        bean.setProperty(PROP_Z_BED_FITMENT, new FakeBean(2));
        bean.setProperty(PROP_BED_SUBSTRATE_TON, Double.valueOf(15d));
        bean.setProperty(PROP_BED_SUBSTRATE_SAN, Double.valueOf(50d));
        bean.setProperty(PROP_BED_SUBSTRATE_KIE, Double.valueOf(30d));
        bean.setProperty(PROP_BED_SUBSTRATE_STE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_BLO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_SCH, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_TOR, Double.valueOf(50d));
        bean.setProperty(PROP_BED_SUBSTRATE_TOT, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_WUR, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_KUE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_RIP, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_TH, Double.valueOf(6d));
        bean.setProperty(PROP_BED_STRUCTURE_WU, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_KO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_MUE, Double.valueOf(3d));
        bean.setProperty(PROP_BED_CONTAMINATION_ST, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_ABW, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_VO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_SA, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_SO, Double.valueOf(0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(366d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(60d));

        Calc.getInstance().calcBedStructureRating(bean);

        assertNotNull(bean.getProperty(PROP_BED_STRUCTURE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_BED_STRUCTURE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_sohlenstruktur_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM sohlenstruktur WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different bed structure rating",
            rating,
            bean.getProperty(PROP_BED_STRUCTURE_SUM_RATING));
        assertEquals(
            "found different bed structure criteria",
            criteria,
            bean.getProperty(PROP_BED_STRUCTURE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1430
        kartierabschnitt = 1430;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(23));
        bean.setProperty(PROP_BED_FITMENT, new FakeBean(4));
        bean.setProperty(PROP_Z_BED_FITMENT, new FakeBean(4));
        bean.setProperty(PROP_BED_SUBSTRATE_TON, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_SAN, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_KIE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_STE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_BLO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_SCH, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_TOR, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_TOT, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_WUR, Double.valueOf(0d));
        bean.setProperty(PROP_BED_SUBSTRATE_KUE, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_RIP, Double.valueOf(0d));
        bean.setProperty(PROP_BED_STRUCTURE_TH, Double.valueOf(17d));
        bean.setProperty(PROP_BED_STRUCTURE_WU, Double.valueOf(11d));
        bean.setProperty(PROP_BED_STRUCTURE_KO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_MUE, Double.valueOf(3d));
        bean.setProperty(PROP_BED_CONTAMINATION_ST, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_ABW, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_VO, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_SA, Double.valueOf(0d));
        bean.setProperty(PROP_BED_CONTAMINATION_SO, Double.valueOf(0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(885d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(636d));

        Calc.getInstance().calcBedStructureRating(bean);

        assertNotNull(bean.getProperty(PROP_BED_STRUCTURE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_BED_STRUCTURE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_sohlenstruktur_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM sohlenstruktur WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different bed structure rating",
            rating,
            bean.getProperty(PROP_BED_STRUCTURE_SUM_RATING));
        assertEquals(
            "found different bed structure criteria",
            criteria,
            bean.getProperty(PROP_BED_STRUCTURE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());
    }

    /**
     * DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
//    @Test
    public void testCalcBankStructureRating() throws Exception {
        System.out.println("TEST " + getCurrentMethodName());

        // kartierabschnitt id 33
        int kartierabschnitt = 33;
        CidsBean bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(11));
        bean.setProperty(PROP_BANK_VEGETATION_LE, new FakeBean(2));
        bean.setProperty(PROP_BANK_VEGETATION_RI, new FakeBean(2));
        bean.setProperty(PROP_BANK_VEGETATION_TYPICAL_LE, Boolean.TRUE);
        bean.setProperty(PROP_BANK_VEGETATION_TYPICAL_RI, Boolean.TRUE);
        bean.setProperty(PROP_BANK_FITMENT_LE, new FakeBean(8));
        bean.setProperty(PROP_BANK_FITMENT_RI, new FakeBean(8));
        bean.setProperty(PROP_Z_BANK_FITMENT_LE, new FakeBean(4));
        bean.setProperty(PROP_Z_BANK_FITMENT_RI, new FakeBean(4));
        bean.setProperty(PROP_BANK_CONTAMINATION_MUE_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_MUE_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_ST_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_ST_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_TS_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_TS_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_EL_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_EL_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_SO_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_SO_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_BU_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_BU_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_PB_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_PB_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_US_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_US_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SB_LE, Double.valueOf(3d));
        bean.setProperty(PROP_BANK_STRUCTURE_SB_RI, Double.valueOf(2d));
        bean.setProperty(PROP_BANK_STRUCTURE_HA_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_HA_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_NBOE_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_NBOE_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SO_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SO_RI, Double.valueOf(0d));

        CidsBean stationBean = new FakeBean();
        CidsBean toBean = new FakeBean();
        CidsBean fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(1545d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(1336d));

        Calc.getInstance().calcBankStructureRating(bean);

        assertNotNull(bean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT));

        Connection con = server.getConnectionPool().getConnection();
        Statement stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_uferstruktur_func(" + kartierabschnitt + ")"));

        ResultSet set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM uferstruktur WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        double rating = set.getDouble(1);
        int criteria = set.getInt(2);

        assertEquals(
            "found different bank structure rating",
            rating,
            bean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING));
        assertEquals(
            "found different bank structure criteria",
            criteria,
            bean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 120
        kartierabschnitt = 120;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(12));
        bean.setProperty(PROP_BANK_VEGETATION_LE, new FakeBean(8));
        bean.setProperty(PROP_BANK_VEGETATION_RI, new FakeBean(8));
        bean.setProperty(PROP_BANK_VEGETATION_TYPICAL_LE, Boolean.FALSE);
        bean.setProperty(PROP_BANK_VEGETATION_TYPICAL_RI, Boolean.FALSE);
        bean.setProperty(PROP_BANK_FITMENT_LE, new FakeBean(8));
        bean.setProperty(PROP_BANK_FITMENT_RI, new FakeBean(8));
        bean.setProperty(PROP_Z_BANK_FITMENT_LE, new FakeBean(4));
        bean.setProperty(PROP_Z_BANK_FITMENT_RI, new FakeBean(4));
        bean.setProperty(PROP_BANK_CONTAMINATION_MUE_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_MUE_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_ST_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_ST_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_TS_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_TS_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_EL_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_EL_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_SO_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_SO_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_BU_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_BU_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_PB_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_PB_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_US_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_US_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SB_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SB_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_HA_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_HA_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_NBOE_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_NBOE_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SO_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SO_RI, Double.valueOf(0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(63129d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(62855d));

        Calc.getInstance().calcBankStructureRating(bean);

        assertNotNull(bean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_uferstruktur_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM uferstruktur WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different bank structure rating",
            rating,
            bean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING));
        assertEquals(
            "found different bank structure criteria",
            criteria,
            bean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1878
        kartierabschnitt = 1878;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(14));
        bean.setProperty(PROP_BANK_VEGETATION_LE, new FakeBean(6));
        bean.setProperty(PROP_BANK_VEGETATION_RI, new FakeBean(4));
        bean.setProperty(PROP_BANK_VEGETATION_TYPICAL_LE, Boolean.FALSE);
        bean.setProperty(PROP_BANK_VEGETATION_TYPICAL_RI, Boolean.FALSE);
        bean.setProperty(PROP_BANK_FITMENT_LE, new FakeBean(8));
        bean.setProperty(PROP_BANK_FITMENT_RI, new FakeBean(8));
        bean.setProperty(PROP_Z_BANK_FITMENT_LE, new FakeBean(4));
        bean.setProperty(PROP_Z_BANK_FITMENT_RI, new FakeBean(4));
        bean.setProperty(PROP_BANK_CONTAMINATION_MUE_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_MUE_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_ST_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_ST_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_TS_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_TS_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_EL_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_EL_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_SO_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_SO_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_BU_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_BU_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_PB_LE, Double.valueOf(1d));
        bean.setProperty(PROP_BANK_STRUCTURE_PB_RI, Double.valueOf(3d));
        bean.setProperty(PROP_BANK_STRUCTURE_US_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_US_RI, Double.valueOf(5d));
        bean.setProperty(PROP_BANK_STRUCTURE_SB_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SB_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_HA_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_HA_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_NBOE_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_NBOE_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SO_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SO_RI, Double.valueOf(0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(13416d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(13294d));

        Calc.getInstance().calcBankStructureRating(bean);

        assertNotNull(bean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_uferstruktur_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM uferstruktur WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different bank structure rating",
            rating,
            bean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING));
        assertEquals(
            "found different bank structure criteria",
            criteria,
            bean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 530
        kartierabschnitt = 530;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(16));
        bean.setProperty(PROP_BANK_VEGETATION_LE, new FakeBean(3));
        bean.setProperty(PROP_BANK_VEGETATION_RI, new FakeBean(4));
        bean.setProperty(PROP_BANK_VEGETATION_TYPICAL_LE, Boolean.TRUE);
        bean.setProperty(PROP_BANK_VEGETATION_TYPICAL_RI, Boolean.TRUE);
        bean.setProperty(PROP_BANK_FITMENT_LE, new FakeBean(3));
        bean.setProperty(PROP_BANK_FITMENT_RI, new FakeBean(2));
        bean.setProperty(PROP_Z_BANK_FITMENT_LE, new FakeBean(2));
        bean.setProperty(PROP_Z_BANK_FITMENT_RI, new FakeBean(2));
        bean.setProperty(PROP_BANK_CONTAMINATION_MUE_LE, Double.valueOf(3d));
        bean.setProperty(PROP_BANK_CONTAMINATION_MUE_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_ST_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_ST_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_TS_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_TS_RI, Double.valueOf(2d));
        bean.setProperty(PROP_BANK_CONTAMINATION_EL_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_EL_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_SO_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_SO_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_BU_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_BU_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_PB_LE, Double.valueOf(2d));
        bean.setProperty(PROP_BANK_STRUCTURE_PB_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_US_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_US_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SB_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SB_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_HA_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_HA_RI, Double.valueOf(4d));
        bean.setProperty(PROP_BANK_STRUCTURE_NBOE_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_NBOE_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SO_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SO_RI, Double.valueOf(0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(366d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(60d));

        Calc.getInstance().calcBankStructureRating(bean);

        assertNotNull(bean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_uferstruktur_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM uferstruktur WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different bank structure rating",
            rating,
            bean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING));
        assertEquals(
            "found different bank structure criteria",
            criteria,
            bean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());

        // kartierabschnitt id 1430
        kartierabschnitt = 1430;
        bean = new FakeBean(kartierabschnitt);
        bean.setProperty(PROP_WB_TYPE, new FakeBean(23));
        bean.setProperty(PROP_BANK_VEGETATION_LE, new FakeBean(5));
        bean.setProperty(PROP_BANK_VEGETATION_RI, new FakeBean(5));
        bean.setProperty(PROP_BANK_VEGETATION_TYPICAL_LE, Boolean.TRUE);
        bean.setProperty(PROP_BANK_VEGETATION_TYPICAL_RI, Boolean.TRUE);
        bean.setProperty(PROP_BANK_FITMENT_LE, new FakeBean(6));
        bean.setProperty(PROP_BANK_FITMENT_RI, new FakeBean(8));
        bean.setProperty(PROP_Z_BANK_FITMENT_LE, new FakeBean(2));
        bean.setProperty(PROP_Z_BANK_FITMENT_RI, new FakeBean(4));
        bean.setProperty(PROP_BANK_CONTAMINATION_MUE_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_MUE_RI, Double.valueOf(2d));
        bean.setProperty(PROP_BANK_CONTAMINATION_ST_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_ST_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_TS_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_TS_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_EL_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_EL_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_SO_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_CONTAMINATION_SO_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_BU_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_BU_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_PB_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_PB_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_US_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_US_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SB_LE, Double.valueOf(3d));
        bean.setProperty(PROP_BANK_STRUCTURE_SB_RI, Double.valueOf(2d));
        bean.setProperty(PROP_BANK_STRUCTURE_HA_LE, Double.valueOf(4d));
        bean.setProperty(PROP_BANK_STRUCTURE_HA_RI, Double.valueOf(3d));
        bean.setProperty(PROP_BANK_STRUCTURE_NBOE_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_NBOE_RI, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SO_LE, Double.valueOf(0d));
        bean.setProperty(PROP_BANK_STRUCTURE_SO_RI, Double.valueOf(0d));

        stationBean = new FakeBean();
        toBean = new FakeBean();
        fromBean = new FakeBean();
        stationBean.setProperty(PROP_TO, toBean);
        stationBean.setProperty(PROP_FROM, fromBean);
        bean.setProperty(PROP_LINE, stationBean);

        toBean.setProperty(PROP_WERT, Double.valueOf(885d));
        fromBean.setProperty(PROP_WERT, Double.valueOf(636d));

        Calc.getInstance().calcBankStructureRating(bean);

        assertNotNull(bean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING));
        assertNotNull(bean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT));

        con = server.getConnectionPool().getConnection();
        stmt = con.createStatement();

        assertTrue(
            "could not execute function",
            stmt.execute("SELECT calculate_points_uferstruktur_func(" + kartierabschnitt + ")"));

        set = stmt.executeQuery(
                "SELECT summe_punktzahl, anzahl_kriterien FROM uferstruktur WHERE kartierabschnitt_id = "
                        + kartierabschnitt);

        assertTrue("not result found", set.next());

        rating = set.getDouble(1);
        criteria = set.getInt(2);

        assertEquals(
            "found different bank structure rating",
            rating,
            bean.getProperty(PROP_BANK_STRUCTURE_SUM_RATING));
        assertEquals(
            "found different bank structure criteria",
            criteria,
            bean.getProperty(PROP_BANK_STRUCTURE_SUM_CRIT));

        assertFalse("too many results found for kartierabschnitt " + kartierabschnitt, set.next());
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static final class FakeBean extends CidsBean {

        //~ Instance fields ----------------------------------------------------

        private final transient Map<String, Object> props;

        private final transient FakeMO mo;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new FakeBean object.
         */
        public FakeBean() {
            this(-1);
        }

        /**
         * Creates a new FakeBean object.
         *
         * @param  id  DOCUMENT ME!
         */
        public FakeBean(final int id) {
            this.props = new HashMap<String, Object>();
            this.mo = new FakeMO(id);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public Object getProperty(final String name) {
            return props.get(name);
        }

        @Override
        public void setProperty(final String name, final Object value) throws Exception {
            props.put(name, value);
        }

        @Override
        public MetaObject getMetaObject() {
            return mo;
        }

        //~ Inner Classes ------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @version  $Revision$, $Date$
         */
        private static final class FakeMO implements MetaObject {

            //~ Instance fields ------------------------------------------------

            private final transient int id;

            //~ Constructors ---------------------------------------------------

            /**
             * Creates a new FakeMO object.
             *
             * @param  id  DOCUMENT ME!
             */
            public FakeMO(final int id) {
                this.id = id;
            }

            //~ Methods --------------------------------------------------------


            @Override
            public HashMap getAllClasses() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public CidsBean getBean() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getClassKey() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getComplexEditor() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getDebugString() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getDescription() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getDomain() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getEditor() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getGroup() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getId() {
                return id;
            }

            @Override
            public Logger getLogger() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public MetaClass getMetaClass() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getPropertyString() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getRenderer() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getSimpleEditor() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Collection getURLs(final Collection classKeys) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Collection getURLsByName(final Collection classKeys, final Collection urlNames) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean isChanged() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean propertyEquals(final MetaObject tester) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setAllClasses(final HashMap classes) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setAllClasses() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setAllStatus(final int status) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setArrayKey2PrimaryKey() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setChanged(final boolean changed) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setEditor(final String editor) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setMetaClass(final MetaClass metaClass) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean setPrimaryKey(final Object key) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setRenderer(final String renderer) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String toString(final HashMap classes) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void addAllAttributes(final ObjectAttribute[] objectAttributes) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void addAttribute(final ObjectAttribute anyAttribute) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object constructKey(final Mapable m) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Sirius.server.localserver.object.Object filter(final User u) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object fromString(final String objectRepresentation, final Object mo) throws Exception {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public ObjectAttribute[] getAttribs() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public ObjectAttribute getAttribute(final String key) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public ObjectAttribute getAttributeByFieldName(final String fieldname) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Collection<ObjectAttribute> getAttributeByName(final String name, final int maxResult) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public LinkedHashMap<Object, ObjectAttribute> getAttributes() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Collection getAttributesByName(final Collection names) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Collection getAttributesByType(final Class c, final int recursionDepth) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Collection getAttributesByType(final Class c) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getClassID() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getID() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object getKey() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public ObjectAttribute getPrimaryKey() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public ObjectAttribute getReferencingObjectAttribute() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getStatus() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getStatusDebugString() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Collection getTraversedAttributesByType(final Class c) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean isDummy() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean isPersistent() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean isStringCreateable() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void removeAttribute(final ObjectAttribute anyAttribute) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setDummy(final boolean dummy) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setID(final int objectID) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setPersistent(final boolean persistent) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setPrimaryKeysNull() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setReferencingObjectAttribute(final ObjectAttribute referencingObjectAttribute) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setStatus(final int status) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setValuesNull() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public FromStringCreator getObjectCreator() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean hasObjectWritePermission(User user) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void forceStatus(int status) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean hasObjectReadPermission(User user) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
    }
}
