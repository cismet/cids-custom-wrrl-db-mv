/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import Sirius.navigator.connection.Connection;
import Sirius.navigator.connection.ConnectionFactory;
import Sirius.navigator.connection.ConnectionInfo;
import Sirius.navigator.connection.ConnectionSession;
import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.connection.proxy.ConnectionProxy;

import Sirius.server.middleware.interfaces.proxy.CatalogueService;
import Sirius.server.middleware.interfaces.proxy.MetaService;
import Sirius.server.middleware.interfaces.proxy.SearchService;
import Sirius.server.middleware.interfaces.proxy.UserService;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.newuser.User;
import Sirius.server.search.CidsServerSearch;

import java.awt.BorderLayout;

import java.rmi.Naming;
import java.rmi.Remote;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.QuerbautenSearchByStations;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.WkSearchByStations;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.Band;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class GUP_BandModellTester implements Band {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(
            GUP_BandModellTester.class);

    //~ Instance fields --------------------------------------------------------

    Collection<CidsBean> sohleMassnahmen;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GUP_BandModellTester object.
     *
     * @param  sohleMassnahmen  DOCUMENT ME!
     */
    public GUP_BandModellTester(final Collection<CidsBean> sohleMassnahmen) {
        this.sohleMassnahmen = sohleMassnahmen;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public double getMax() {
        return 0;
    }

    @Override
    public BandMember getMember(final int i) {
        return null;
    }

    @Override
    public double getMin() {
        return 0;
    }

    @Override
    public int getNumberOfMembers() {
        return 0;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
//        java.rmi.registry.Registry rmiRegistry = LocateRegistry.getRegistry(1099);
        System.out.println("start");
        // lookup des callservers
        final Remote r;
        final SearchService ss;
        final CatalogueService cat;
        final MetaService meta;
        final UserService us;
        final User u;

        Log4JQuickConfig.configure4LumbermillOnLocalhost();
        // rmi registry lokaliseren

        // ich weiss, dass die server von callserver implementiert werden

        final String domain = "WRRL_DB_MV";
        // lookup des callservers
        r = (Remote)Naming.lookup("rmi://localhost/callServer");
        System.out.println("server gefunden");
        ss = (SearchService)r;
        cat = (CatalogueService)r;
        meta = (MetaService)r;
        us = (UserService)r;
        u = us.getUser(domain, "Administratoren", domain, "admin", "sb");
        System.out.println("user angemeldet");
        ConnectionSession session = null;
        ConnectionProxy proxy = null;
        final ConnectionInfo info = new ConnectionInfo();
        info.setCallserverURL("rmi://localhost/callServer");
        info.setUsername("admin");
        info.setUsergroup("Administratoren");
        info.setPassword("sb");
        info.setUserDomain(domain);
        info.setUsergroupDomain(domain);

        final Connection connection = ConnectionFactory.getFactory()
                    .createConnection(
                        "Sirius.navigator.connection.RMIConnection",
                        info.getCallserverURL());

        session = ConnectionFactory.getFactory().createSession(connection,
                info, true);
        proxy = ConnectionFactory.getFactory()
                    .createProxy(
                            "Sirius.navigator.connection.proxy.DefaultConnectionProxyHandler",
                            session);
        System.out.println("sessionmanager initialisieren");
        SessionManager.init(proxy);

        ClassCacheMultiple.setInstance(domain);
        System.out.println("MO abfragem");
        final MetaObject mo = SessionManager.getConnection()
                    .getMetaObject(SessionManager.getSession().getUser(), 1, 257, domain);
        final CidsBean cidsBean = mo.getBean();
        System.out.println("MO empfangen");

        System.out.println("Bänder bauen");
        final MassnahmenBand rechts = new MassnahmenBand("rechts");
        rechts.setCidsBeans(mo.getBean().getBeanCollectionProperty("gup_massnahmen_ufer_rechts"));
        final MassnahmenBand sohle = new MassnahmenBand("Sohle");
        sohle.setCidsBeans(mo.getBean().getBeanCollectionProperty("gup_massnahmen_sohle"));
        final MassnahmenBand links = new MassnahmenBand("links");
        links.setCidsBeans(mo.getBean().getBeanCollectionProperty("gup_massnahmen_ufer_links"));
        final MassnahmenBand sonstige = new MassnahmenBand("sonstige");
        sonstige.setCidsBeans(mo.getBean().getBeanCollectionProperty("gup_massnahmen_sonstige"));
        System.out.println("Bänder gebaut");
        final JFrame jf = new JFrame("Test");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(new BorderLayout());

        final JBand jbdTest = new JBand();
        final SimpleBandModel sbm = new SimpleBandModel();

        sbm.addBand(links);
        sbm.addBand(sohle);
        sbm.addBand(rechts);
        sbm.addBand(sonstige);

        final CidsBean route = rechts.getRoute();
        final CidsServerSearch searchWK = new WkSearchByStations(sbm.getMin(),
                sbm.getMax(),
                String.valueOf(route.getProperty("gwk")));

        final Collection resWK = SessionManager.getProxy()
                    .customServerSearch(SessionManager.getSession().getUser(), searchWK);
        final ArrayList<ArrayList> resArrayWK = (ArrayList<ArrayList>)resWK;

        final WKBand wkband = new WKBand(sbm.getMin(), sbm.getMax(), resArrayWK);

        final CidsServerSearch searchQB = new QuerbautenSearchByStations(sbm.getMin(),
                sbm.getMax(),
                String.valueOf(route.getProperty("gwk")));

        final Collection resQB = SessionManager.getProxy()
                    .customServerSearch(SessionManager.getSession().getUser(), searchQB);
        final ArrayList<ArrayList> resArrayQB = (ArrayList<ArrayList>)resQB;

        final POIBand poiband = new POIBand();
        poiband.addQuerbauwerkeFromQueryResult(resArrayQB);

        sbm.insertBand(wkband, 0);

        sbm.addBand(poiband);

        jbdTest.setModel(sbm);

        jbdTest.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        jbdTest.setZoomFactor(
            1);
        jf.getContentPane().add(jbdTest, BorderLayout.CENTER);

        final JSlider jsl = new JSlider(0, 200);

        jsl.addChangeListener(
            new ChangeListener() {

                @Override
                public void stateChanged(final ChangeEvent ce) {
                    final double zfAdd = jsl.getValue() / 10.0;
                    jbdTest.setZoomFactor(1 + zfAdd);
                }
            });
        jsl.setValue(
            0);
        jf.getContentPane().add(jsl, BorderLayout.SOUTH);
        jf.setSize(
            300,
            400);
        jf.setVisible(
            true);
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        jf.setBounds(
            (screenSize.width - 800)
                    / 2,
            (screenSize.height - 222)
                    / 2,
            800,
            222);
    }
}
