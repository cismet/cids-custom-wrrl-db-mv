/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.search.CidsServerSearch;

import java.awt.BorderLayout;

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

import de.cismet.cids.tools.DevelopmentTools;

import de.cismet.tools.gui.jbands.EmptyAbsoluteHeightedBand;
import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.Band;
import de.cismet.tools.gui.jbands.interfaces.BandMember;

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
        final CidsBean cidsBean = DevelopmentTools.createCidsBeanFromRMIConnectionOnLocalhost(
                "WRRL_DB_MV",
                "Administratoren",
                "admin",
                "sb",
                "gup_gewaesserabschnitt",
                1);
        System.out.println("MO empfangen");

        System.out.println("Bänder bauen");
        final MassnahmenBand rechts = new MassnahmenBand("rechts");
        rechts.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_ufer_rechts"));
        final MassnahmenBand sohle = new MassnahmenBand("Sohle");
        sohle.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_sohle"));
        final MassnahmenBand links = new MassnahmenBand("links");
        links.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_ufer_links"));
        final MassnahmenBand sonstige = new MassnahmenBand("sonstige");
        sonstige.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_sonstige"));

        final UmlandnutzungsBand nutzungLinks = new UmlandnutzungsBand();
        nutzungLinks.setCidsBeans(cidsBean.getBeanCollectionProperty("abschnittsinfo"));

        final UmlandnutzungsBand nutzungRechts = new UmlandnutzungsBand();
        nutzungRechts.setCidsBeans(cidsBean.getBeanCollectionProperty("abschnittsinfo"));

        System.out.println("Bänder gebaut");
        final JFrame jf = new JFrame("Test");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(new BorderLayout());

        final JBand jbdTest = new JBand();
        final SimpleBandModel sbm = new SimpleBandModel();
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
        sbm.addBand(nutzungLinks);
        sbm.addBand(links);
        sbm.addBand(sohle);
        sbm.addBand(rechts);
        sbm.addBand(nutzungRechts);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
        sbm.addBand(sonstige);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
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
