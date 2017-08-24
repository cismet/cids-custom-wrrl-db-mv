/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import org.openide.util.Exceptions;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import java.util.HashMap;

import javax.mail.Session;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.tools.Converter;

import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class TestGupPlanungsabschnittPerformance {

    //~ Static fields/initializers ---------------------------------------------

    private static final HashMap<Integer, MetaClass> classMap = new HashMap<>();

    private static final Logger LOG = Logger.getLogger(TestGupPlanungsabschnittPerformance.class);

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        try {
            Log4JQuickConfig.configure4LumbermillOnLocalhost();

            DevelopmentTools.initSessionManagerFromRestfulConnection(
                "http://localhost:9986/callserver/binary",
                "WRRL_DB_MV",
                "Administratoren",
                "admin",
                "n!emal$99",
                true);

            System.out.println("\n### start test");

//            System.out.println("| occurences | tableName | classId | objectId | bytes | timeMetaObject | timeBean |");
//            System.out.println("| --- |--- |--- | --- | --- | --- | --- |");
            System.out.println(
                "tableName (classId) ; objectId ; bytes ; getMetaObject (ms) ; getBean (ms) ; total (ms)");

            int bytes = 0;
//bytes+=test(1,1,3904799);
////bytes+=test(1,181,607532);
////bytes+=test(1,181,607534);
////bytes+=test(1,181,607536);
////bytes+=test(1,181,607538);
////bytes+=test(1,181,607540);
////bytes+=test(1,181,607542);
////bytes+=test(1,181,607544);
////bytes+=test(1,181,607546);
////bytes+=test(1,181,607548);
////bytes+=test(1,181,607550);
////bytes+=test(1,181,607552);
////bytes+=test(1,181,607554);
////bytes+=test(1,181,607555);
////bytes+=test(1,181,607556);
////bytes+=test(1,181,607558);
////bytes+=test(1,181,607559);
////bytes+=test(1,181,607561);
////bytes+=test(1,181,607563);
////bytes+=test(1,181,607565);
////bytes+=test(1,181,607567);
////bytes+=test(1,181,607569);
////bytes+=test(1,181,607571);
////bytes+=test(1,181,607573);
////bytes+=test(1,181,607575);
////bytes+=test(1,181,607577);
////bytes+=test(1,181,607579);
////bytes+=test(1,181,607581);
////bytes+=test(1,181,607582);
////bytes+=test(1,181,607583);
////bytes+=test(1,181,607585);
////bytes+=test(1,181,607586);
////bytes+=test(1,181,607588);
////bytes+=test(1,181,607590);
////bytes+=test(1,181,607592);
////bytes+=test(1,181,607594);
////bytes+=test(1,181,607596);
////bytes+=test(1,181,607598);
////bytes+=test(1,181,607600);
////bytes+=test(1,181,607602);
////bytes+=test(1,181,607604);
////bytes+=test(1,181,607606);
////bytes+=test(1,181,607608);
////bytes+=test(1,181,607609);
////bytes+=test(1,181,607610);
////bytes+=test(1,181,607612);
////bytes+=test(1,181,607613);
////bytes+=test(1,181,607614);
////bytes+=test(1,181,607616);
////bytes+=test(1,181,607617);
//bytes+=test(1,1,8691388);
//bytes+=test(1,1,8691389);
//bytes+=test(1,1,8691390);
//bytes+=test(1,1,8691391);
//bytes+=test(1,1,8691392);
//bytes+=test(1,1,8691393);
//bytes+=test(1,1,8691394);
//bytes+=test(1,1,8691395);
//bytes+=test(1,1,8691396);
//bytes+=test(1,1,8691397);
//bytes+=test(1,1,8691398);
//bytes+=test(1,1,8691399);
//bytes+=test(1,1,8691400);
//bytes+=test(1,1,8691401);
//bytes+=test(1,1,8691402);
//bytes+=test(1,1,8691403);
//bytes+=test(1,1,8691404);
//bytes+=test(1,1,8691405);
//bytes+=test(1,1,8691406);
//bytes+=test(1,1,8691407);
//bytes+=test(1,1,8691410);
//bytes+=test(1,1,8691411);
//bytes+=test(1,1,8691413);
//bytes+=test(1,1,8691416);
//bytes+=test(1,1,8691417);
//bytes+=test(1,1,8691418);
//bytes+=test(1,1,8691419);
//bytes+=test(1,1,8691420);
//bytes+=test(1,1,8691421);
//bytes+=test(1,1,8691422);
//bytes+=test(1,1,8691423);
//bytes+=test(1,1,8691424);
//bytes+=test(1,1,8691425);
//bytes+=test(1,1,8691426);
//bytes+=test(1,1,8691427);
//bytes+=test(1,1,8691428);
//bytes+=test(1,1,8691429);
//bytes+=test(1,1,8691430);
//bytes+=test(1,1,8691431);
//bytes+=test(1,1,8691432);
//bytes+=test(1,1,8691433);
//bytes+=test(1,1,8691434);
//bytes+=test(1,1,8691435);
//bytes+=test(1,1,8691438);
//bytes+=test(1,1,8691439);
//bytes+=test(1,1,8691441);
//bytes+=test(1,1,8691443);
//bytes+=test(1,1,8691444);
//bytes+=test(1,1,8691446);
//bytes+=test(1,1,8691447);
//bytes+=test(1,1,8691448);
//bytes+=test(1,1,8691449);
//bytes+=test(1,1,8691450);
//bytes+=test(1,1,8691451);
//bytes+=test(1,1,8691452);
//bytes+=test(1,1,8691453);
//bytes+=test(1,1,8691454);
//bytes+=test(1,1,8691455);
//bytes+=test(1,1,8691457);
//bytes+=test(1,1,8691458);
//bytes+=test(1,1,8691459);
//bytes+=test(1,1,8691460);
//bytes+=test(1,1,8691461);
//bytes+=test(1,1,8691462);
//bytes+=test(1,1,8691463);
//bytes+=test(1,1,8691466);
//bytes+=test(1,1,8691467);
//bytes+=test(1,1,8691469);
//bytes+=test(1,1,8691470);
//bytes+=test(1,1,8691471);
//bytes+=test(1,1,8691472);
//bytes+=test(1,1,8691474);
//bytes+=test(1,1,8691476);
//bytes+=test(1,1,8691478);
//bytes+=test(1,1,8691480);
//bytes+=test(1,1,8691482);
//bytes+=test(1,1,8691484);
//bytes+=test(1,1,8691486);
//bytes+=test(1,1,8691488);
//bytes+=test(1,1,8691490);
//bytes+=test(1,1,8691492);
//bytes+=test(1,1,8691494);
//bytes+=test(1,1,8691495);
//bytes+=test(1,1,8691496);
//bytes+=test(1,1,8691498);
//bytes+=test(1,1,8691499);
//bytes+=test(1,1,8691501);
//bytes+=test(1,1,8691503);
//bytes+=test(1,1,8691505);
//bytes+=test(1,1,8691507);
//bytes+=test(1,1,8691509);
//bytes+=test(1,1,8691511);
//bytes+=test(1,1,8691513);
//bytes+=test(1,1,8691515);
//bytes+=test(1,1,8691517);
//bytes+=test(1,1,8691519);
//bytes+=test(1,1,8691521);
//bytes+=test(1,1,8691522);
//bytes+=test(1,1,8691523);
//bytes+=test(1,1,8691525);
//bytes+=test(1,1,8691526);
//bytes+=test(1,1,8691528);
//bytes+=test(1,1,8691530);
//bytes+=test(1,1,8691532);
//bytes+=test(1,1,8691534);
//bytes+=test(1,1,8691536);
//bytes+=test(1,1,8691538);
//bytes+=test(1,1,8691540);
//bytes+=test(1,1,8691542);
//bytes+=test(1,1,8691544);
//bytes+=test(1,1,8691546);
//bytes+=test(1,1,8691548);
//bytes+=test(1,1,8691549);
//bytes+=test(1,1,8691550);
//bytes+=test(1,1,8691552);
//bytes+=test(1,1,8691553);
//bytes+=test(1,1,8691557);
//bytes+=test(1,1,8691558);
//bytes+=test(1,1,8691561);
//bytes+=test(1,1,8691562);
//bytes+=test(1,1,8691567);
////bytes+=test(1,250,3288);
////bytes+=test(1,257,132);
////bytes+=test(1,258,126);
////bytes+=test(1,26,8232);
////bytes+=test(1,28,1294102);
////bytes+=test(1,28,1294103);
////bytes+=test(1,28,1294104);
////bytes+=test(1,28,1294105);
////bytes+=test(1,28,1294106);
////bytes+=test(1,28,1294107);
////bytes+=test(1,28,1294108);
////bytes+=test(1,28,1294109);
////bytes+=test(1,28,1294110);
////bytes+=test(1,28,1294111);
////bytes+=test(1,28,1294112);
////bytes+=test(1,28,1294113);
////bytes+=test(1,28,1294114);
////bytes+=test(1,28,1294115);
////bytes+=test(1,28,1294116);
////bytes+=test(1,28,1294117);
////bytes+=test(1,28,1294118);
////bytes+=test(1,28,1294119);
////bytes+=test(1,28,1294120);
////bytes+=test(1,28,1294121);
////bytes+=test(1,28,1294124);
////bytes+=test(1,28,1294125);
////bytes+=test(1,28,1294127);
////bytes+=test(1,28,1294130);
////bytes+=test(1,28,1294131);
////bytes+=test(1,28,1294132);
////bytes+=test(1,28,1294133);
////bytes+=test(1,28,1294134);
////bytes+=test(1,28,1294135);
////bytes+=test(1,28,1294136);
////bytes+=test(1,28,1294137);
////bytes+=test(1,28,1294138);
////bytes+=test(1,28,1294139);
////bytes+=test(1,28,1294140);
////bytes+=test(1,28,1294141);
////bytes+=test(1,28,1294142);
////bytes+=test(1,28,1294143);
////bytes+=test(1,28,1294144);
////bytes+=test(1,28,1294145);
////bytes+=test(1,28,1294146);
////bytes+=test(1,28,1294147);
////bytes+=test(1,28,1294148);
////bytes+=test(1,28,1294149);
////bytes+=test(1,28,1294152);
////bytes+=test(1,28,1294153);
////bytes+=test(1,28,1294155);
////bytes+=test(1,28,1294157);
////bytes+=test(1,28,1294158);
////bytes+=test(1,28,1294160);
////bytes+=test(1,28,1294161);
////bytes+=test(1,28,1294162);
////bytes+=test(1,28,1294163);
////bytes+=test(1,28,1294164);
////bytes+=test(1,28,1294165);
////bytes+=test(1,28,1294166);
////bytes+=test(1,28,1294167);
////bytes+=test(1,28,1294168);
////bytes+=test(1,28,1294169);
////bytes+=test(1,28,1294171);
////bytes+=test(1,28,1294172);
////bytes+=test(1,28,1294173);
////bytes+=test(1,28,1294174);
////bytes+=test(1,28,1294175);
////bytes+=test(1,28,1294176);
////bytes+=test(1,28,1294177);
////bytes+=test(1,28,1294180);
////bytes+=test(1,28,1294181);
////bytes+=test(1,28,1294183);
////bytes+=test(1,28,1294184);
////bytes+=test(1,28,1294185);
////bytes+=test(1,28,1294189);
////bytes+=test(1,28,1294191);
////bytes+=test(1,308,1);
////bytes+=test(1,308,10);
////bytes+=test(1,308,11);
////bytes+=test(1,308,12);
////bytes+=test(1,308,13);
////bytes+=test(1,308,14);
////bytes+=test(1,308,15);
////bytes+=test(1,308,16);
////bytes+=test(1,308,2);
////bytes+=test(1,308,4);
////bytes+=test(1,308,5);
////bytes+=test(1,308,6);
////bytes+=test(1,308,7);
////bytes+=test(1,308,8);
////bytes+=test(1,308,9);
////bytes+=test(1,309,12);
////bytes+=test(1,309,13);
////bytes+=test(1,309,15);
////bytes+=test(1,309,16);
////bytes+=test(1,309,17);
////bytes+=test(1,309,171);
////bytes+=test(1,309,172);
////bytes+=test(1,309,174);
////bytes+=test(1,309,180);
////bytes+=test(1,309,183);
////bytes+=test(1,309,19);
////bytes+=test(1,309,209);
////bytes+=test(1,309,21);
////bytes+=test(1,309,210);
////bytes+=test(1,309,211);
////bytes+=test(1,309,212);
////bytes+=test(1,309,213);
////bytes+=test(1,309,214);
////bytes+=test(1,309,215);
////bytes+=test(1,309,22);
////bytes+=test(1,309,23);
////bytes+=test(1,324,1369);
////bytes+=test(1,324,1371);
////bytes+=test(1,324,1373);
////bytes+=test(1,324,1375);
////bytes+=test(1,324,1377);
////bytes+=test(1,324,1379);
////bytes+=test(1,324,1381);
////bytes+=test(1,324,1383);
////bytes+=test(1,324,1385);
////bytes+=test(1,324,1387);
////bytes+=test(1,324,1389);
////bytes+=test(1,324,1390);
////bytes+=test(1,324,1391);
////bytes+=test(1,324,1393);
////bytes+=test(1,324,1394);
////bytes+=test(1,324,1396);
////bytes+=test(1,324,1398);
////bytes+=test(1,324,1400);
////bytes+=test(1,324,1402);
////bytes+=test(1,324,1404);
////bytes+=test(1,324,1406);
////bytes+=test(1,324,1408);
////bytes+=test(1,324,1410);
////bytes+=test(1,324,1412);
////bytes+=test(1,324,1414);
////bytes+=test(1,324,1416);
////bytes+=test(1,324,1417);
////bytes+=test(1,324,1418);
////bytes+=test(1,324,1420);
////bytes+=test(1,324,1421);
////bytes+=test(1,324,1423);
////bytes+=test(1,324,1425);
////bytes+=test(1,324,1427);
////bytes+=test(1,324,1429);
////bytes+=test(1,324,1431);
////bytes+=test(1,324,1433);
////bytes+=test(1,324,1435);
////bytes+=test(1,324,1437);
////bytes+=test(1,324,1439);
////bytes+=test(1,324,1441);
////bytes+=test(1,324,1443);
////bytes+=test(1,324,1444);
////bytes+=test(1,324,1445);
////bytes+=test(1,324,1447);
////bytes+=test(1,324,1448);
////bytes+=test(1,324,1449);
////bytes+=test(1,324,1451);
////bytes+=test(1,324,1452);
////bytes+=test(1,365,67);
////bytes+=test(1,367,12);
////bytes+=test(1,367,19);
////bytes+=test(1,378,1);
////bytes+=test(1,379,1);
////bytes+=test(1,384,103);
////bytes+=test(1,384,12);
////bytes+=test(1,384,124);
////bytes+=test(1,384,25);
////bytes+=test(1,384,27);
////bytes+=test(1,384,29);
////bytes+=test(1,384,34);
////bytes+=test(1,384,35);
////bytes+=test(1,384,44);
////bytes+=test(1,384,45);
////bytes+=test(1,384,50);
////bytes+=test(1,384,6);
////bytes+=test(1,384,66);
////bytes+=test(1,384,67);
////bytes+=test(1,384,71);
////bytes+=test(1,384,81);
////bytes+=test(1,384,82);
////bytes+=test(1,384,86);
////bytes+=test(1,384,98);
////bytes+=test(1,384,99);
////bytes+=test(1,389,12);
////bytes+=test(1,389,14);
////bytes+=test(1,389,15);
////bytes+=test(1,389,5);
////bytes+=test(1,389,8);
////bytes+=test(1,393,12);
////bytes+=test(1,395,3);
//bytes+=test(2,1,8691387);
//bytes+=test(2,1,8691408);
//bytes+=test(2,1,8691409);
//bytes+=test(2,1,8691412);
//bytes+=test(2,1,8691415);
//bytes+=test(2,1,8691436);
//bytes+=test(2,1,8691437);
//bytes+=test(2,1,8691440);
//bytes+=test(2,1,8691445);
//bytes+=test(2,1,8691456);
//bytes+=test(2,1,8691464);
//bytes+=test(2,1,8691465);
//bytes+=test(2,1,8691468);
////bytes+=test(2,250,1706);
////bytes+=test(2,251,12);
////bytes+=test(2,28,1294101);
////bytes+=test(2,28,1294122);
////bytes+=test(2,28,1294123);
////bytes+=test(2,28,1294126);
////bytes+=test(2,28,1294129);
////bytes+=test(2,28,1294150);
////bytes+=test(2,28,1294151);
////bytes+=test(2,28,1294154);
////bytes+=test(2,28,1294159);
////bytes+=test(2,28,1294170);
////bytes+=test(2,28,1294178);
////bytes+=test(2,28,1294179);
////bytes+=test(2,28,1294182);
////bytes+=test(2,389,7);
////bytes+=test(2,395,1);
////bytes+=test(2,395,2);
////bytes+=test(3,250,3015);
////bytes+=test(3,341,22);
////bytes+=test(3,346,8);
////bytes+=test(3,365,28);
////bytes+=test(3,367,1);
////bytes+=test(3,372,3);
////bytes+=test(3,372,49);
////bytes+=test(4,250,1735);
////bytes+=test(4,372,16);
////bytes+=test(4,372,41);
////bytes+=test(4,372,61);
////bytes+=test(4,372,62);
////bytes+=test(4,372,63);
////bytes+=test(4,389,3);
////bytes+=test(6,250,2425);
////bytes+=test(6,250,2779);
////bytes+=test(6,250,3008);
////bytes+=test(6,250,3077);
////bytes+=test(6,250,3265);
////bytes+=test(6,365,31);
////bytes+=test(6,365,33);
////bytes+=test(6,365,43);
////bytes+=test(6,365,62);
////bytes+=test(6,365,63);
////bytes+=test(6,365,84);
////bytes+=test(8,250,3297);
////bytes+=test(8,365,78);
////bytes+=test(9,251,13);
////bytes+=test(9,341,23);
////bytes+=test(9,389,6);
////bytes+=test(10,367,9);
////bytes+=test(10,372,64);
////bytes+=test(12,278,17);
////bytes+=test(12,278,23);
////bytes+=test(12,341,5);
////bytes+=test(12,346,24);
////bytes+=test(15,251,10);
////bytes+=test(15,359,1);
////bytes+=test(16,360,1);
////bytes+=test(16,360,2);
////bytes+=test(16,360,5);
////bytes+=test(16,372,2);
////bytes+=test(18,346,22);
////bytes+=test(18,346,28);
////bytes+=test(18,346,4);
////bytes+=test(18,346,7);
////bytes+=test(19,346,33);
////bytes+=test(22,251,11);
////bytes+=test(24,278,22);
////bytes+=test(24,341,8);
////bytes+=test(24,346,3);
////bytes+=test(24,346,36);
////bytes+=test(24,422,1);
////bytes+=test(24,422,3);
////bytes+=test(25,346,26);
////bytes+=test(25,346,9);
////bytes+=test(30,346,23);
////bytes+=test(30,346,35);
////bytes+=test(31,346,25);
////bytes+=test(31,346,27);
////bytes+=test(31,346,34);
////bytes+=test(32,346,31);
////bytes+=test(36,346,32);
////bytes+=test(37,346,29);
////bytes+=test(40,372,55);
////bytes+=test(40,372,65);
////bytes+=test(45,346,19);
////bytes+=test(45,346,30);
////bytes+=test(48,366,55);
////bytes+=test(198,308,3);

            System.out.println("total bytes: " + bytes);

            bytes += test(0, 257, 132);
            bytes += test(0, 257, 132);
            bytes += test(0, 257, 132);
            bytes += test(0, 257, 132);

            System.out.println("test done");
            System.exit(0);
        } catch (final Throwable t) {
            Exceptions.printStackTrace(t);
            System.exit(1);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   occurences  DOCUMENT ME!
     * @param   classId     DOCUMENT ME!
     * @param   objectId    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private static int test(final int occurences, final int classId, final int objectId) throws Exception {
        final MetaClass mc;
        if (classMap.containsKey(classId)) {
            mc = classMap.get(classId);
        } else {
            mc = ClassCacheMultiple.getMetaClass("WRRL_DB_MV", classId);
            classMap.put(classId, mc);
        }

//        final PrintStream stdOut = System.out;
//        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(baos));

        final long msMetaObject = System.currentTimeMillis();
        final MetaObject mo = SessionManager.getConnection()
                    .getMetaObject(SessionManager.getSession().getUser(), objectId, mc.getId(), "WRRL_DB_MV");
        final long timeMo = System.currentTimeMillis() - msMetaObject;
        final int bytes = Converter.serialiseToGzip(mo).length;

        final CidsBean cidsBean;
        final long timeBean;
        if (mo != null) {
            final long msBean = System.currentTimeMillis();
            cidsBean = mo.getBean();
            timeBean = System.currentTimeMillis() - msBean;
        } else {
            timeBean = -1;
            cidsBean = null;
        }

//        final BufferedReader reader = new BufferedReader(new StringReader(baos.toString()));
//        String line;
//        int bytes = 0;
//        while ((line = reader.readLine()) != null) {
//            bytes += Integer.parseInt(line);
//        }

//        System.setOut(stdOut);
        final long timeTotal = timeMo + timeBean;
        System.out.println(mc.getTableName() + " (" + classId + ") ; " + objectId + " ; "
                    + bytes + " ; " + timeMo + " ; " + timeBean + "; " + timeTotal);

        cidsBean.setProperty("name", "Graben aus Gnevsdorf");
        final long msUpdate = System.currentTimeMillis();
        SessionManager.getConnection()
                .updateMetaObject(SessionManager.getSession().getUser(), cidsBean.getMetaObject(), "WRRL_DB_MV");
        final long timeUpdate = System.currentTimeMillis() - msUpdate;
        System.out.println("timeUpdate: " + timeUpdate);
//
        return occurences;
    }
}
