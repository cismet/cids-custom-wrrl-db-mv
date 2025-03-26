/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.fgsk;

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
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.netutil.Proxy;
import de.cismet.netutil.ProxyHandler;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class CompleteCalcTestReport {

    //~ Static fields/initializers ---------------------------------------------

    private static final String SERVER_CONFIG =
        "src/test/resources/de/cismet/cids/custom/wrrl_db_mv/fgsk/completeCalcTestReport.properties"; // NOI18N

    private static final double EPSILON = 0.000000000001d;

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
    private final transient User user;
    private final transient Connection con;

    private final transient int classId;

    private transient int badCalcCounter;
    private final transient List<Integer> badCalcIds;
    private final transient List<Integer> noCalcIds;
    private final transient List<Integer> missingCidsDataIds;

    private transient int missingDataCounter;

    private final transient List<Integer> unexpectedErrorIds;
    private final transient List<Integer> kaWoWBTypeIds;

    private transient int kaWoWBTypeCounter;

    private transient int overallRatingMissingDataCounter;
    private final transient List<Integer> overallRatingmissingDataIds;
    private transient int overallRatingZeroCounter;
    private final transient List<Integer> overallRatingZeroIds;
    private transient int exceptionCounter;
    private final transient List<Integer> exceptionIds;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CompleteCalcTestReport object.
     *
     * @throws  Throwable  DOCUMENT ME!
     */
    public CompleteCalcTestReport() throws Throwable {
        final ServerProperties props = new ServerProperties(SERVER_CONFIG);

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
                        ProxyHandler.getInstance().getProxy());
        final ConnectionSession session = ConnectionFactory.getFactory().createSession(connection, info, true);
        final ConnectionProxy conProxy = ConnectionFactory.getFactory()
                    .createProxy(propertyManager.getConnectionProxyClass(), session);
        SessionManager.init(conProxy);

        con = server.getConnectionPool().getConnection();
        final Statement s = con.createStatement();
        s.execute("set search_path to fgsk,public;");
        s.close();
        
        final Statement stmt = con.createStatement();
        stmt.execute("update fgsk.gewaesserumfeld set summe_punktzahl = -1, anzahl_kriterien = -1;"
                    + "update fgsk.laengsprofil set summe_punktzahl = -1, anzahl_kriterien = -1;"
                    + "update fgsk.laufentwicklung set summe_punktzahl = -1, anzahl_kriterien = -1;"
                    + "update fgsk.querprofil set summe_punktzahl = -1, anzahl_kriterien = -1;"
                    + "update fgsk.sohlenstruktur set summe_punktzahl = -1, anzahl_kriterien = -1;"
                    + "update fgsk.uferstruktur set summe_punktzahl = -1, anzahl_kriterien = -1;"
                    + "update fgsk.kartierabschnitt set punktzahl_gesamt = -1, punktzahl_sohle = -1, punktzahl_ufer = -1, punktzahl_land = -1;");
        stmt.close();

        classId = 229;

        badCalcCounter = 0;
        missingDataCounter = 0;
        kaWoWBTypeCounter = 0;
        overallRatingMissingDataCounter = 0;
        overallRatingZeroCounter = 0;
        exceptionCounter = 0;
        badCalcIds = new ArrayList<Integer>();
        missingCidsDataIds = new ArrayList<Integer>();
        noCalcIds = new ArrayList<Integer>();
        kaWoWBTypeIds = new ArrayList<Integer>();
        unexpectedErrorIds = new ArrayList<Integer>();
        overallRatingmissingDataIds = new ArrayList<Integer>();
        overallRatingZeroIds = new ArrayList<Integer>();
        exceptionIds = new ArrayList<Integer>();
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
        final Statement stmtFgsk = con.createStatement();
        final ResultSet setFgsk = stmtFgsk.executeQuery("select id from fgsk.kartierabschnitt order by id");
        final Statement stmtCids = con.createStatement();
        final ResultSet setCids = stmtCids.executeQuery("select id from public.fgsk_kartierabschnitt order by id");

        int fgskCounter = 0;
        int cidsCounter = 0;
        int unexpectedErrors = 0;
        int noCalcCounter = 0;

        while (setFgsk.next()) {
            fgskCounter++;

            int idFgsk = -1;
            try {
                if (!setCids.next()) {
                    throw new IllegalStateException("cids does not contain as many entries as fgsk"); // NOI18N
                }

                cidsCounter++;

                idFgsk = setFgsk.getInt(1);
                final int idCids = setCids.getInt(1);

                if (idFgsk != idCids) {
                    throw new IllegalStateException("cids id not equal to fgsk id: [fgskid=" + idFgsk + "|cidsid="
                                + idCids
                                + "]");
                }

                final MetaObject mo = server.getMetaObject(user, idFgsk, classId);

                if (mo == null) {
                    throw new IllegalStateException("cannot fetch cids metaobject: " + idFgsk);
                }

                System.out.println();
                System.out.println("---------------------------------------------------------------------------------");
                System.out.println();
                System.out.println("Kartierabschnitt: " + idFgsk);
                System.out.println();
                boolean overall = true;
                overall &= handleStatus(printCrossProfile(idFgsk, mo.getBean()), mo.getBean());
                overall &= handleStatus(printLongProfile(idFgsk, mo.getBean()), mo.getBean());
                overall &= handleStatus(printCourseEvolution(idFgsk, mo.getBean()), mo.getBean());
                overall &= handleStatus(printBedStructure(idFgsk, mo.getBean()), mo.getBean());
                overall &= handleStatus(printBankStrukture(idFgsk, mo.getBean()), mo.getBean());
                overall &= handleStatus(printWBEnv(idFgsk, mo.getBean()), mo.getBean());

                if (overall) {
                    final Status status = printOverall(idFgsk, mo.getBean());
                    if (Status.MISSING_DATA_IN_CIDS == status) {
                        overallRatingMissingDataCounter++;
                        overallRatingmissingDataIds.add(idFgsk);
                    } else if (Status.MISSING_DATA == status) {
                        overallRatingZeroCounter++;
                        overallRatingZeroIds.add(idFgsk);
                    } else {
                        handleStatus(status, mo.getBean());
                    }
                } else {
                    if (isException(mo.getBean())) {
                        System.out.println("Punktzahl          Gesamt:\t\tSONDERFALL");
                    } else {
                        System.out.println("Punktzahl          Gesamt:\t\tNICHT BERECHENBAR");
                        if(!badCalcIds.contains(mo.getId()) && !noCalcIds.contains(mo.getId())){
                            noCalcIds.add(mo.getId());
                            noCalcCounter++;
                        }
                    }
                }

                try {
//                    mo.getBean().persist();
                } catch (final Exception e) {
                    System.out.println();
                    System.out.println("COULD NOT STORE RATING DATA FOR BEAN: " + mo.getId());
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    System.out.println();
                }

                System.out.println();
                System.out.println("---------------------------------------------------------------------------------");
            } catch (final Exception e) {
                if ((e instanceof IllegalStateException)
                            && e.getMessage().contains("kartierabschnitt bean without water body type")) {
                    System.out.println("KARTIERABSCHNITT OHNE WASSERKÖRPERTYP NICHT BERECHENBAR");
                    kaWoWBTypeCounter++;
                    kaWoWBTypeIds.add(idFgsk);
                } else {
                    System.out.println(
                        "=================================================================================");
                    System.out.println();
                    e.printStackTrace();
                    System.out.println();
                    System.out.println(
                        "=================================================================================");
                    unexpectedErrorIds.add(idFgsk);
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
        System.out.println("Anzahl FGSK Kartierabschnitte:                     " + fgskCounter);
        System.out.println("Anzahl cids Kartierabschnitte:                     " + cidsCounter);
        System.out.println("Anzahl fehlerhafter Berechnungen:                  " + badCalcCounter);
        System.out.println("Anzahl nicht berechenbarer Kartierabschnitte:      " + noCalcCounter);
        System.out.println("Anzahl nicht berechenbarer Gesamtpunktzahlen:      " + overallRatingZeroCounter);
        System.out.println("Anzahl Sonderfälle:                                " + exceptionCounter);
        System.out.println("Anzahl Kartierabschnitte ohne Wasserkörpertyp:     " + kaWoWBTypeCounter);
        System.out.println("Anzahl unerwarteter Fehler:                        " + unexpectedErrors);
        System.out.println("Anzahl Validierungsdiskrepanz Teilberechnung:      " + missingDataCounter);
        System.out.println("Anzahl Validierungsdiskrepanz Gesamtberechnung:    " + overallRatingMissingDataCounter);
        System.out.println("Vergleichsgenauigkeit (Epsilon):                   " + EPSILON);
        System.out.println();
        System.out.println("=================================================================================");
        System.out.println("=================================================================================");
        System.out.println("=================================================================================");

        printIds();
    }

    /**
     * DOCUMENT ME!
     */
    private void printIds() {
        System.out.println();
        System.out.println("APPENDIX Kartierabschnitt ids ");
        System.out.println();
        System.out.println("Fehlerhafte Berechnungen: ");
        System.out.println(Arrays.toString(badCalcIds.toArray()));
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Validierungsdiskrepanz Teilberechnung");
        System.out.println(Arrays.toString(missingCidsDataIds.toArray()));
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Validierungsdiskrepanz Gesamtberechnung");
        System.out.println(Arrays.toString(overallRatingmissingDataIds.toArray()));
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Nicht berechenbare Datensätze");
        System.out.println(Arrays.toString(noCalcIds.toArray()));
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Nicht berechenbare Gesamtpunktzahlen");
        System.out.println(Arrays.toString(overallRatingZeroIds.toArray()));
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
     * @param   status  DOCUMENT ME!
     * @param   bean    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean handleStatus(final Status status, final CidsBean bean) {
        switch (status) {
            case NO_ENTRY: {
                return false;
            }
            case MISSING_DATA: {
                return false;
            }
            case EXCEPTION: {
                if (!exceptionIds.contains(bean.getMetaObject().getId())) {
                    exceptionCounter++;
                    exceptionIds.add(bean.getMetaObject().getId());
                }
                return false;
            }
            case MISSING_DATA_IN_CIDS: {
                missingDataCounter++;
                if (!missingCidsDataIds.contains(bean.getMetaObject().getId())) {
                    missingCidsDataIds.add(bean.getMetaObject().getId());
                }
                return false;
            }
            case NO_CRITERIA_MATCH:
            case NO_RATING_MATCH: {
                if(!badCalcIds.contains(bean.getMetaObject().getId())){
                    badCalcCounter++;
                    badCalcIds.add(bean.getMetaObject().getId());
                }
                
                return false;
            }
            case OK:
            default: {
                return true;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    private boolean isException(final CidsBean kaBean) {
        if (kaBean == null) {
            throw new IllegalArgumentException("cidsBean must not be null"); // NOI18N
        }

        final CidsBean exception = (CidsBean)kaBean.getProperty(Calc.PROP_EXCEPTION);
        if ((exception != null) && !Integer.valueOf(0).equals(exception.getProperty(Calc.PROP_VALUE))) {
            return true;
        }

        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id    DOCUMENT ME!
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception              DOCUMENT ME!
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private Status printCrossProfile(final int id, final CidsBean bean) throws Exception {
        Statement stmt = null;
        ResultSet set = null;
        try {
            if (isException(bean)) {
                System.out.println("Querprofil               :\t\tSONDERFALL");

                return Status.EXCEPTION;
            }

            stmt = con.createStatement();

            if (!stmt.execute("SELECT fgsk.calculate_points_querprofil_func(" + id + ")")) {
                throw new IllegalStateException("could not execute cross profile rating for id: " + id);
            }

            set = stmt.executeQuery(
                    "SELECT summe_punktzahl, anzahl_kriterien FROM fgsk.querprofil WHERE kartierabschnitt_id = "
                            + id);

            if (!set.next()) {
                System.out.println("Querprofil               :\t\tNICHT VORHANDEN");

                return Status.NO_ENTRY;
            }

            final double fgskRating = set.getDouble(1);
            final int fgskCriteria = set.getInt(2);

            try {
                Calc.getInstance().calcWBCrossProfileRating(bean);
            } catch (final ValidationException ex) {
                if (ex.isException() && isException(bean)) {
                    System.out.println("Querprofil               :\t\tSONDERFALL");

                    return Status.EXCEPTION;
                } else {
                    if ((fgskRating == -1) && (fgskCriteria == -1)) {
                        System.out.println("Querprofil               :\t\tUNZUREICHEND AUSGEFÜLLT");

                        return Status.MISSING_DATA;
                    } else {
                        System.out.println("Querprofil               :\t\tVALIDIERUNGSDISKREPANZ");

                        return Status.MISSING_DATA_IN_CIDS;
                    }
                }
            }

            final double cidsRating = (Double)bean.getProperty(Calc.PROP_CROSS_PROFILE_SUM_RATING);
            final int cidsCriteria = (Integer)bean.getProperty(Calc.PROP_CROSS_PROFILE_SUM_CRIT);

            System.out.println("Querprofil      Bewertung:\t\t" + fgskRating + "\t" + cidsRating);
            System.out.println("Querprofil      Kriterien:\t\t" + fgskCriteria + "\t" + cidsCriteria);

            if (fgskRating != cidsRating) {
                System.out.println("Querprofil      Bewertung:\t\tNICHT IDENTISCH");
                return Status.NO_RATING_MATCH;
            }
            if (fgskCriteria != cidsCriteria) {
                System.out.println("Querprofil      Kriterien:\t\tNICHT IDENTISCH");
                return Status.NO_CRITERIA_MATCH;
            }

            return Status.OK;
        } finally {
            if (set != null) {
                set.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id    DOCUMENT ME!
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception              DOCUMENT ME!
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private Status printLongProfile(final int id, final CidsBean bean) throws Exception {
        Statement stmt = null;
        ResultSet set = null;
        try {
            if (isException(bean)) {
                System.out.println("Längsprofil              :\t\tSONDERFALL");

                return Status.EXCEPTION;
            }
            stmt = con.createStatement();
            if (!stmt.execute("SELECT fgsk.calculate_points_laengsprofil_func(" + id + ")")) {
                throw new IllegalStateException("could not execute long profile rating for id: " + id);
            }

            set = stmt.executeQuery(
                    "SELECT summe_punktzahl, anzahl_kriterien FROM fgsk.laengsprofil WHERE kartierabschnitt_id = "
                            + id);

            if (!set.next()) {
                System.out.println("Längsprofil              :\t\tNICHT VORHANDEN");

                return Status.NO_ENTRY;
            }

            final double fgskRating = set.getDouble(1);
            final int fgskCriteria = set.getInt(2);

            try {
                Calc.getInstance().calcWBLongProfileRating(bean);
            } catch (final ValidationException ex) {
                if (ex.isException() && isException(bean)) {
                    System.out.println("Längsprofil              :\t\tSONDERFALL");

                    return Status.EXCEPTION;
                } else {
                    if ((fgskRating == -1) && (fgskCriteria == -1)) {
                        System.out.println("Längsprofil              :\t\tUNZUREICHEND AUSGEFÜLLT");

                        return Status.MISSING_DATA;
                    } else {
                        System.out.println("Längsprofil              :\t\tVALIDIERUNGSDISKREPANZ");

                        return Status.MISSING_DATA_IN_CIDS;
                    }
                }
            }

            final double cidsRating = (Double)bean.getProperty(Calc.PROP_LONG_PROFILE_SUM_RATING);
            final int cidsCriteria = (Integer)bean.getProperty(Calc.PROP_LONG_PROFILE_SUM_CRIT);

            System.out.println("Längsprofil     Bewertung:\t\t" + fgskRating + "\t" + cidsRating);
            System.out.println("Längsprofil     Kriterien:\t\t" + fgskCriteria + "\t" + cidsCriteria);

            if (fgskRating != cidsRating) {
                System.out.println("Längsprofil     Bewertung:\t\tNICHT IDENTISCH");
                return Status.NO_RATING_MATCH;
            }
            if (fgskCriteria != cidsCriteria) {
                System.out.println("Längsprofil     Kriterien:\t\tNICHT IDENTISCH");
                return Status.NO_CRITERIA_MATCH;
            }

            return Status.OK;
        } finally {
            if (set != null) {
                set.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id    DOCUMENT ME!
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception              DOCUMENT ME!
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private Status printCourseEvolution(final int id, final CidsBean bean) throws Exception {
        Statement stmt = null;
        ResultSet set = null;
        try {
            if (isException(bean)) {
                System.out.println("Laufentwicklung          :\t\tSONDERFALL");

                return Status.EXCEPTION;
            }
            stmt = con.createStatement();
            if (!stmt.execute("SELECT fgsk.calculate_points_laufentwicklung_func(" + id + ")")) {
                throw new IllegalStateException("could not execute course evolution rating for id: " + id);
            }

            set = stmt.executeQuery(
                    "SELECT summe_punktzahl, anzahl_kriterien FROM fgsk.laufentwicklung WHERE kartierabschnitt_id = "
                            + id);

            if (!set.next()) {
                System.out.println("Laufentwicklung          :\t\tNICHT VORHANDEN");

                return Status.NO_ENTRY;
            }

            final double fgskRating = set.getDouble(1);
            final int fgskCriteria = set.getInt(2);

            try {
                Calc.getInstance().calcCourseEvoRating(bean);
            } catch (final ValidationException ex) {
                if (ex.isException() && isException(bean)) {
                    System.out.println("Laufentwicklung          :\t\tSONDERFALL");
                    return Status.EXCEPTION;
                } else {
                    if ((fgskRating == -1) && (fgskCriteria == -1)) {
                        System.out.println("Laufentwicklung          :\t\tUNZUREICHEND AUSGEFÜLLT");

                        return Status.MISSING_DATA;
                    } else {
                        System.out.println("Laufentwicklung          :\t\tVALIDIERUNGSDISKREPANZ");

                        return Status.MISSING_DATA_IN_CIDS;
                    }
                }
            }

            final double cidsRating = (Double)bean.getProperty(Calc.PROP_COURSE_EVO_SUM_RATING);
            final int cidsCriteria = (Integer)bean.getProperty(Calc.PROP_COURSE_EVO_SUM_CRIT);

            System.out.println("Laufentwicklung Bewertung:\t\t" + fgskRating + "\t" + cidsRating);
            System.out.println("Laufentwicklung Kriterien:\t\t" + fgskCriteria + "\t" + cidsCriteria);

            if (fgskRating != cidsRating) {
                System.out.println("Laufentwicklung Bewertung:\t\tNICHT IDENTISCH");
                return Status.NO_RATING_MATCH;
            }
            if (fgskCriteria != cidsCriteria) {
                System.out.println("Laufentwicklung Kriterien:\t\tNICHT IDENTISCH");
                return Status.NO_CRITERIA_MATCH;
            }

            return Status.OK;
        } finally {
            if (set != null) {
                set.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id    DOCUMENT ME!
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception              DOCUMENT ME!
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private Status printBedStructure(final int id, final CidsBean bean) throws Exception {
        Statement stmt = null;
        ResultSet set = null;
        try {
            if (isException(bean)) {
                System.out.println("Sohlenstruktur           :\t\tSONDERFALL");

                return Status.EXCEPTION;
            }
            stmt = con.createStatement();
            if (!stmt.execute("SELECT fgsk.calculate_points_sohlenstruktur_func(" + id + ")")) {
                throw new IllegalStateException("could not execute bed structure rating for id: " + id);
            }

            set = stmt.executeQuery(
                    "SELECT summe_punktzahl, anzahl_kriterien FROM fgsk.sohlenstruktur WHERE kartierabschnitt_id = "
                            + id);

            if (!set.next()) {
                System.out.println("Sohlenstruktur           :\t\tNICHT VORHANDEN");

                return Status.NO_ENTRY;
            }

            final double fgskRating = set.getDouble(1);
            final int fgskCriteria = set.getInt(2);
            try {
                Calc.getInstance().calcBedStructureRating(bean);
            } catch (final ValidationException ex) {
                if (ex.isException() && isException(bean)) {
                    System.out.println("Sohlenstruktur           :\t\tSONDERFALL");
                    return Status.EXCEPTION;
                } else {
                    if ((fgskRating == -1) && (fgskCriteria == -1)) {
                        System.out.println("Sohlenstruktur           :\t\tUNZUREICHEND AUSGEFÜLLT");

                        return Status.MISSING_DATA;
                    } else {
                        System.out.println("Sohlenstruktur           :\t\tVALIDIERUNGSDISKREPANZ");

                        return Status.MISSING_DATA_IN_CIDS;
                    }
                }
            }

            final double cidsRating = (Double)bean.getProperty(Calc.PROP_BED_STRUCTURE_SUM_RATING);
            final int cidsCriteria = (Integer)bean.getProperty(Calc.PROP_BED_STRUCTURE_SUM_CRIT);

            System.out.println("Sohlenstruktur  Bewertung:\t\t" + fgskRating + "\t" + cidsRating);
            System.out.println("Sohlenstruktur  Kriterien:\t\t" + fgskCriteria + "\t" + cidsCriteria);

            if (fgskRating != cidsRating) {
                System.out.println("Sohlenstruktur  Bewertung:\t\tNICHT IDENTISCH");
                return Status.NO_RATING_MATCH;
            }
            if (fgskCriteria != cidsCriteria) {
                System.out.println("Sohlenstruktur  Kriterien:\t\tNICHT IDENTISCH");
                return Status.NO_CRITERIA_MATCH;
            }

            return Status.OK;
        } finally {
            if (set != null) {
                set.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id    DOCUMENT ME!
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception              DOCUMENT ME!
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private Status printBankStrukture(final int id, final CidsBean bean) throws Exception {
        Statement stmt = null;
        ResultSet set = null;
        try {
            if (isException(bean)) {
                System.out.println("Uferstruktur             :\t\tSONDERFALL");

                return Status.EXCEPTION;
            }
            stmt = con.createStatement();
            if (!stmt.execute("SELECT fgsk.calculate_points_uferstruktur_func(" + id + ")")) {
                throw new IllegalStateException("could not execute bank structure rating for id: " + id);
            }

            set = stmt.executeQuery(
                    "SELECT summe_punktzahl, anzahl_kriterien FROM fgsk.uferstruktur WHERE kartierabschnitt_id = "
                            + id);

            if (!set.next()) {
                System.out.println("Uferstruktur             :\t\tNICHT VORHANDEN");

                return Status.NO_ENTRY;
            }

            final double fgskRating = set.getDouble(1);
            final int fgskCriteria = set.getInt(2);
            try {
                Calc.getInstance().calcBankStructureRating(bean);
            } catch (final ValidationException ex) {
                if (ex.isException() && isException(bean)) {
                    System.out.println("Uferstruktur             :\t\tSONDERFALL");
                    return Status.EXCEPTION;
                } else {
                    if ((fgskRating == -1) && (fgskCriteria == -1)) {
                        System.out.println("Uferstruktur             :\t\tUNZUREICHEND AUSGEFÜLLT");

                        return Status.MISSING_DATA;
                    } else {
                        System.out.println("Uferstruktur             :\t\tVALIDIERUNGSDISKREPANZ");

                        return Status.MISSING_DATA_IN_CIDS;
                    }
                }
            }

            final double cidsRating = (Double)bean.getProperty(Calc.PROP_BANK_STRUCTURE_SUM_RATING);
            final int cidsCriteria = (Integer)bean.getProperty(Calc.PROP_BANK_STRUCTURE_SUM_CRIT);

            System.out.println("Uferstruktur    Bewertung:\t\t" + fgskRating + "\t" + cidsRating);
            System.out.println("Uferstruktur    Kriterien:\t\t" + fgskCriteria + "\t" + cidsCriteria);

            if (fgskRating != cidsRating) {
                System.out.println("Uferstruktur    Bewertung:\t\tNICHT IDENTISCH");
                return Status.NO_RATING_MATCH;
            }
            if (fgskCriteria != cidsCriteria) {
                System.out.println("Uferstruktur    Kriterien:\t\tNICHT IDENTISCH");
                return Status.NO_CRITERIA_MATCH;
            }

            return Status.OK;
        } finally {
            if (set != null) {
                set.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id    DOCUMENT ME!
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception              DOCUMENT ME!
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private Status printWBEnv(final int id, final CidsBean bean) throws Exception {
        Statement stmt = null;
        ResultSet set = null;
        try {
            if (isException(bean)) {
                System.out.println("Gewässerumfeld           :\t\tSONDERFALL");

                return Status.EXCEPTION;
            }
            stmt = con.createStatement();
            if (!stmt.execute("SELECT fgsk.calculate_points_gewaesserumfeld_func(" + id + ")")) {
                throw new IllegalStateException("could not execute wb env rating for id: " + id);
            }

            set = stmt.executeQuery(
                    "SELECT summe_punktzahl, anzahl_kriterien FROM fgsk.gewaesserumfeld WHERE kartierabschnitt_id = "
                            + id);

            if (!set.next()) {
                System.out.println("Gewässerumfeld           :\t\tNICHT VORHANDEN");

                return Status.NO_ENTRY;
            }

            final double fgskRating = set.getDouble(1);
            final int fgskCriteria = set.getInt(2);

            try {
                Calc.getInstance().calcWBEnvRating(bean);
            } catch (final ValidationException ex) {
                if (ex.isException() && isException(bean)) {
                    System.out.println("Gewässerumfeld           :\t\tSONDERFALL");
                    return Status.EXCEPTION;
                } else {
                    if ((fgskRating == -1) && (fgskCriteria == -1)) {
                        System.out.println("Gewässerumfeld           :\t\tUNZUREICHEND AUSGEFÜLLT");

                        return Status.MISSING_DATA;
                    } else {
                        System.out.println("Gewässerumfeld           :\t\tVALIDIERUNGSDISKREPANZ");

                        return Status.MISSING_DATA_IN_CIDS;
                    }
                }
            }

            final double cidsRating = (Double)bean.getProperty(Calc.PROP_WB_ENV_SUM_RATING);
            final int cidsCriteria = (Integer)bean.getProperty(Calc.PROP_WB_ENV_SUM_CRIT);

            System.out.println("Gewässerumfeld  Bewertung:\t\t" + fgskRating + "\t" + cidsRating);
            System.out.println("Gewässerumfeld  Kriterien:\t\t" + fgskCriteria + "\t" + cidsCriteria);

            if (fgskRating != cidsRating) {
                System.out.println("Gewässerumfeld  Bewertung:\t\tNICHT IDENTISCH");
                return Status.NO_RATING_MATCH;
            }
            if (fgskCriteria != cidsCriteria) {
                System.out.println("Gewässerumfeld  Kriterien:\t\tNICHT IDENTISCH");
                return Status.NO_CRITERIA_MATCH;
            }

            return Status.OK;
        } finally {
            if (set != null) {
                set.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id    DOCUMENT ME!
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception              DOCUMENT ME!
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    private Status printOverall(final int id, final CidsBean bean) throws Exception {
        Statement stmt = null;
        ResultSet set = null;
        try {
            stmt = con.createStatement();
            if (!stmt.execute("SELECT fgsk.update_points_kartierabschnitt_func(" + id + ")")) {
                throw new IllegalStateException("could not execute overall rating for id: " + id);
            }

            set = stmt.executeQuery(
                    "SELECT punktzahl_gesamt, punktzahl_sohle, punktzahl_ufer, punktzahl_land FROM fgsk.kartierabschnitt WHERE id = "
                            + id);

            if (!set.next()) {
                throw new IllegalStateException("could not fetch fgsk calc results");
            }

            final double fgskOverallRating = set.getDouble(1);
            final double fgskBedRating = set.getDouble(2);
            final double fgskBankRating = set.getDouble(3);
            final double fgskEnvRating = set.getDouble(4);

            try {
                Calc.getInstance().calcOverallRating(bean);
            } catch (final ValidationException ex) {
                if ((fgskOverallRating == -1) && (fgskBedRating == -1) && (fgskBankRating == -1)
                            && (fgskEnvRating == -1)) {
                    System.out.println("Punktzahl          Gesamt:\t\tTEILBERECHNUNGEN ENTHALTEN 0 WERTE");

                    return Status.MISSING_DATA;
                } else {
                    System.out.println("Punktzahl          Gesamt:\t\tVALIDIERUNGSDISKREPANZ");

                    return Status.MISSING_DATA_IN_CIDS;
                }
            }

            final double cidsOverallRating = (Double)bean.getProperty(Calc.PROP_WB_OVERALL_RATING);
            final double cidsBedRating = (Double)bean.getProperty(Calc.PROP_WB_BED_RATING);
            final double cidsBankRating = (Double)bean.getProperty(Calc.PROP_WB_BANK_RATING);
            final double cidsEnvRating = (Double)bean.getProperty(Calc.PROP_WB_ENV_RATING);

            System.out.println("Punktzahl          Gesamt:\t\t" + fgskOverallRating + "\t" + cidsOverallRating);
            System.out.println("Punktzahl          Sohle :\t\t" + fgskBedRating + "\t" + cidsBedRating);
            System.out.println("Punktzahl          Ufer  :\t\t" + fgskBankRating + "\t" + cidsBankRating);
            System.out.println("Punktzahl          Land  :\t\t" + fgskEnvRating + "\t" + cidsEnvRating);

            if (!equalsEpsilon(fgskBedRating, cidsBedRating, EPSILON)) {
                System.out.println("Punktzahl          Sohle :\t\tNICHT IDENTISCH");
                return Status.NO_RATING_MATCH;
            }
            if (!equalsEpsilon(fgskBankRating, cidsBankRating, EPSILON)) {
                System.out.println("Punktzahl          Ufer  :\t\tNICHT IDENTISCH");
                return Status.NO_RATING_MATCH;
            }
            if (!equalsEpsilon(fgskEnvRating, cidsEnvRating, EPSILON)) {
                System.out.println("Punktzahl          Land  :\t\tNICHT IDENTISCH");
                return Status.NO_RATING_MATCH;
            }
            if (!equalsEpsilon(fgskOverallRating, cidsOverallRating, EPSILON)) {
                System.out.println("Punktzahl          Gesamt:\t\tNICHT IDENTISCH");
                return Status.NO_RATING_MATCH;
            }

            return Status.OK;
        } finally {
            if (set != null) {
                set.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   d1  DOCUMENT ME!
     * @param   d2  DOCUMENT ME!
     * @param   e   DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean equalsEpsilon(final double d1, final double d2, final double e) {
        return (d1 == d2) ? true : (Math.abs(d1 - d2) < e);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Throwable  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Throwable {
        if (false) {
            final CompleteCalcTestReport r = new CompleteCalcTestReport();
            r.singleDebug(27);
        } else {
            // only works if wrrl db on kif contains the old and the new schema
            CompleteCalcTestReport r = null;
            PrintStream ps = null;
            final PrintStream out = System.out;
            final PrintStream err = System.err;
            try {
                ps = new PrintStream(new FileOutputStream(new File("FGSK_report_both_math_round.txt")), false, "UTF-8");
                r = new CompleteCalcTestReport();

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

    /**
     * DOCUMENT ME!
     *
     * @param   id  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public void singleDebug(final int id) throws Exception {
        final MetaObject mo = server.getMetaObject(user, id, classId);
        Calc.getInstance().calcWBCrossProfileRating(mo.getBean());
        System.out.println("");
    }
}
