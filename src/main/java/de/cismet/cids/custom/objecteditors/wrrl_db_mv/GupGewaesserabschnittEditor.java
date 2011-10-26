/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * WkFgEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.ui.RequestsFullSizeComponent;

import Sirius.server.search.CidsServerSearch;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import de.cismet.cids.custom.wrrl_db_mv.server.search.AnsprechpartnerSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.NaturschutzgebietSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.QuerbautenSearchByStations;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkSearchByStations;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.AbstimmungsvermerkeBand;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.AnwohnerBand;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.MassnahmenBand;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.MassnahmenBandMember;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.NaturschutzBand;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.QuerbauwerksBand;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.UmlandnutzungsBand;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.WKBand;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.DevelopmentTools;
import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.RoundedPanel;
import de.cismet.tools.gui.jbands.BandModelEvent;
import de.cismet.tools.gui.jbands.EmptyAbsoluteHeightedBand;
import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandModelListener;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class GupGewaesserabschnittEditor extends JPanel implements CidsBeanRenderer,
    FooterComponentProvider,
    RequestsFullSizeComponent {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupGewaesserabschnittEditor.class);

    //~ Instance fields --------------------------------------------------------

    MassnahmenBand rechtesUferBand = new MassnahmenBand("rechts");
    MassnahmenBand sohleBand = new MassnahmenBand("Sohle");
    MassnahmenBand linkesUferBand = new MassnahmenBand("links");
    MassnahmenBand sonstigeMassnahmenBand = new MassnahmenBand("sonstige");
    UmlandnutzungsBand nutzungLinksBand = new UmlandnutzungsBand();
    UmlandnutzungsBand nutzungRechtsBand = new UmlandnutzungsBand();
    NaturschutzBand naturchutzBand = new NaturschutzBand();
    AnwohnerBand anwohnerLinksBand = new AnwohnerBand(0.5f, "Anwohner");
    AnwohnerBand anwohnerRechtsBand = new AnwohnerBand(0.5f, "Anwohner");
    WKBand wkband;
    QuerbauwerksBand querbauwerksband = new QuerbauwerksBand();
    AbstimmungsvermerkeBand abstimmungsvermerkeBand = new AbstimmungsvermerkeBand();
    final JBand jband = new JBand();
    final SimpleBandModel sbm = new SimpleBandModel();
    private final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    private CidsBean cidsBean;
    private GupAbschnittsinfoEditor abschnittsinfoEditor = new GupAbschnittsinfoEditor();
    private GupMassnahmeSohleEditor massnahmeSohleEditor = new GupMassnahmeSohleEditor();
    private GupMassnahmeUferEditor massnahmeUferEditor = new GupMassnahmeUferEditor();
    private GupMassnahmeSonstigeEditor massnahmeSonstigeEditor = new GupMassnahmeSonstigeEditor();
    private GupGewaesserabschnittAllgemein allgemeinEditor = new GupGewaesserabschnittAllgemein();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrpDetails;
    private javax.swing.JCheckBox chkAbstimmungsvermerke;
    private javax.swing.JCheckBox chkBesonderePunkte;
    private javax.swing.JCheckBox chkEigentuemer;
    private javax.swing.JCheckBox chkMassnahmen;
    private javax.swing.JCheckBox chkNaturschutz;
    private javax.swing.JCheckBox chkQuerbauwerke;
    private javax.swing.JCheckBox chkSonstigeMassnahmen;
    private javax.swing.JCheckBox chkUmlandnutzung;
    private javax.swing.JCheckBox chkWasserkoerper;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JPanel panAbschnittsinfo;
    private javax.swing.JPanel panAllgemein;
    private javax.swing.JPanel panBand;
    private javax.swing.JPanel panBandControl;
    private javax.swing.JPanel panControls;
    private javax.swing.JPanel panEmpty;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private javax.swing.JPanel panHeader;
    private javax.swing.JPanel panHeaderInfo;
    private javax.swing.JPanel panHydro;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panMassnahmeSohle;
    private javax.swing.JPanel panMassnahmeSonstige;
    private javax.swing.JPanel panMassnahmeUfer;
    private javax.swing.JPanel panWRRL;
    private javax.swing.JSlider sldZoom;
    private javax.swing.JToggleButton togAllgemeinInfo;
    private javax.swing.JToggleButton togHydroInfo;
    private javax.swing.JToggleButton togWRRLInfo;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public GupGewaesserabschnittEditor() {
        initComponents();

        naturchutzBand.setEnabled(false);
        querbauwerksband.setEnabled(false);
        abstimmungsvermerkeBand.setEnabled(false);
        anwohnerLinksBand.setEnabled(false);
        anwohnerRechtsBand.setEnabled(false);

        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
        sbm.addBand(abstimmungsvermerkeBand);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
        sbm.addBand(nutzungLinksBand);
        sbm.addBand(anwohnerLinksBand);
        sbm.addBand(linkesUferBand);
        sbm.addBand(sohleBand);
        sbm.addBand(rechtesUferBand);
        sbm.addBand(anwohnerRechtsBand);
        sbm.addBand(nutzungRechtsBand);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
        sbm.addBand(sonstigeMassnahmenBand);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));

        jband.setModel(sbm);

        panBand.add(jband, BorderLayout.CENTER);
//        panBand.add(new JButton("kjdhfkjsdfhkjdsfhkjfsd"), BorderLayout.CENTER);
        jband.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        switchToForm("allgemein");
        panAbschnittsinfo.add(abschnittsinfoEditor, BorderLayout.CENTER);
        panMassnahmeSohle.add(massnahmeSohleEditor, BorderLayout.CENTER);
        panMassnahmeUfer.add(massnahmeUferEditor, BorderLayout.CENTER);
        panMassnahmeSonstige.add(massnahmeSonstigeEditor, BorderLayout.CENTER);
        panAllgemein.add(allgemeinEditor, BorderLayout.CENTER);

        normalizeDimensions(abschnittsinfoEditor);
        normalizeDimensions(massnahmeSohleEditor);
        normalizeDimensions(massnahmeUferEditor);
        normalizeDimensions(massnahmeSonstigeEditor);

        sbm.addBandModelListener(new BandModelListener() {

                @Override
                public void bandModelChanged(final BandModelEvent e) {
                }

                @Override
                public void bandModelSelectionChanged(final BandModelEvent e) {
                    final BandMember bm = jband.getSelectedBandMember();
                    if (bm != null) {
                        bgrpDetails.clearSelection();
                        switchToForm("empty");
                        if (bm instanceof MassnahmenBandMember) {
                            final CidsBean bean = ((MassnahmenBandMember)bm).getCidsBean();

                            if (bean.getMetaObject().getMetaClass().getTableName().equalsIgnoreCase(
                                            "gup_massnahme_sohle")) {
                                switchToForm("massnahmesohle");
                                massnahmeSohleEditor.setCidsBean(bean);
                            } else if (bean.getMetaObject().getMetaClass().getTableName().equalsIgnoreCase(
                                            "gup_massnahme_sonstige")) {
                                switchToForm("massnahmesonstige");
                                massnahmeSonstigeEditor.setCidsBean(bean);
                            } else if (bean.getMetaObject().getMetaClass().getTableName().equalsIgnoreCase(
                                            "gup_massnahme_ufer")) {
                                switchToForm("massnahmeufer");
                                massnahmeUferEditor.setCidsBean(bean);
                            }
                        }
                    } else {
                        switchToForm("empty");
                    }
                }

                @Override
                public void bandModelValuesChanged(final BandModelEvent e) {
                }
            });

        sldZoom.setPaintTrack(false);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  c  DOCUMENT ME!
     */
    private void normalizeDimensions(final JComponent c) {
        c.setMinimumSize(new Dimension(1, 1));
        c.setPreferredSize(new Dimension(1, 1));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    private void switchToForm(final String id) {
        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    ((CardLayout)panInfoContent.getLayout()).show(panInfoContent, id);
                }
            };
        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            EventQueue.invokeLater(r);
        }
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
//        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        switchToForm("empty");

        if (cidsBean != null) {
            abstimmungsvermerkeBand.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_abstimmungsvermerke"));
            rechtesUferBand.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_ufer_rechts"));
            sohleBand.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_sohle"));
            linkesUferBand.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_ufer_links"));
            sonstigeMassnahmenBand.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_sonstige"));
            nutzungLinksBand.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_umlandinfo_links"));
            nutzungRechtsBand.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_umlandinfo_rechts"));

            final CidsBean route = rechtesUferBand.getRoute();

            try {
                final CidsServerSearch searchWK = new WkSearchByStations(sbm.getMin(),
                        sbm.getMax(),
                        String.valueOf(route.getProperty("gwk")));

                final Collection resWK = SessionManager.getProxy()
                            .customServerSearch(SessionManager.getSession().getUser(), searchWK);
                final ArrayList<ArrayList> resArrayWK = (ArrayList<ArrayList>)resWK;

                wkband = new WKBand(sbm.getMin(), sbm.getMax(), resArrayWK);
                sbm.insertBand(wkband, 0);
            } catch (Exception e) {
                log.error("Problem beim Suchen der Wasserkoerper", e);
            }
            System.out.println("WK Ready");

            de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<ArrayList<ArrayList>, Void>() {

                    @Override
                    protected ArrayList<ArrayList> doInBackground() throws Exception {
                        final CidsServerSearch searchQB = new QuerbautenSearchByStations(
                                sbm.getMin(),
                                sbm.getMax(),
                                String.valueOf(route.getProperty("gwk")));
                        final Collection resQB = SessionManager.getProxy()
                                    .customServerSearch(SessionManager.getSession().getUser(), searchQB);
                        return (ArrayList<ArrayList>)resQB;
                    }

                    @Override
                    protected void done() {
                        try {
                            chkQuerbauwerke.setEnabled(true);
                            querbauwerksband.addQuerbauwerkeFromQueryResult(get());
                            sbm.addBand(querbauwerksband);
                        } catch (Exception e) {
                            log.error("Exception in Background Thread", e);
                        }
                    }
                });
            try {
            } catch (Exception e) {
                log.error("Problem beim Suchen der Querbauwerke", e);
            }

            de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<ArrayList<ArrayList>, Void>() {

                    @Override
                    protected ArrayList<ArrayList> doInBackground() throws Exception {
                        final CidsServerSearch searchNSG = new NaturschutzgebietSearch(
                                sbm.getMin(),
                                sbm.getMax(),
                                String.valueOf(route.getProperty("gwk")));
                        final Collection resNSG = SessionManager.getProxy()
                                    .customServerSearch(SessionManager.getSession().getUser(), searchNSG);
                        return (ArrayList<ArrayList>)resNSG;
                    }

                    @Override
                    protected void done() {
                        try {
                            chkNaturschutz.setEnabled(true);
                            naturchutzBand.addMembersFromQueryResult(get());
                            sbm.insertBand(naturchutzBand, 2);
                            sbm.insertBand(new EmptyAbsoluteHeightedBand(5), 3);
                        } catch (Exception e) {
                            log.error("Problem beim Suchen der Naturschutzgebiete", e);
                        }
                    }
                });

            de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<ArrayList<ArrayList>, Void>() {

                    @Override
                    protected ArrayList<ArrayList> doInBackground() throws Exception {
                        final CidsServerSearch search = new AnsprechpartnerSearch(
                                sbm.getMin(),
                                sbm.getMax(),
                                String.valueOf(route.getProperty("gwk")));
                        final Collection res = SessionManager.getProxy()
                                    .customServerSearch(SessionManager.getSession().getUser(), search);
                        return (ArrayList<ArrayList>)res;
                    }

                    @Override
                    protected void done() {
                        try {
                            final ArrayList<ArrayList> result = get();
                            anwohnerLinksBand.addAnwohnerFromQueryResult(result, AnwohnerBand.Seite.LINKS);
                            anwohnerRechtsBand.addAnwohnerFromQueryResult(result, AnwohnerBand.Seite.RECHTS);
                            chkEigentuemer.setEnabled(true);
                        } catch (Exception e) {
                            log.error("Problem beim Suchen der Ansprechpartner", e);
                        }
                    }
                });
        }
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        bgrpDetails = new javax.swing.ButtonGroup();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panMassnahmeSohle = new javax.swing.JPanel();
        panMassnahmeUfer = new javax.swing.JPanel();
        panMassnahmeSonstige = new javax.swing.JPanel();
        panAbschnittsinfo = new javax.swing.JPanel();
        panEmpty = new javax.swing.JPanel();
        panWRRL = new javax.swing.JPanel();
        panHydro = new javax.swing.JPanel();
        panAllgemein = new javax.swing.JPanel();
        panHeader = new javax.swing.JPanel();
        panHeaderInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panControls = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        sldZoom = new javax.swing.JSlider();
        jPanel4 = new javax.swing.JPanel();
        togAllgemeinInfo = new javax.swing.JToggleButton();
        togWRRLInfo = new javax.swing.JToggleButton();
        togHydroInfo = new javax.swing.JToggleButton();
        panBand = new javax.swing.JPanel();
        panBandControl = new RoundedPanel();
        chkMassnahmen = new javax.swing.JCheckBox();
        chkSonstigeMassnahmen = new javax.swing.JCheckBox();
        chkWasserkoerper = new javax.swing.JCheckBox();
        chkUmlandnutzung = new javax.swing.JCheckBox();
        chkQuerbauwerke = new javax.swing.JCheckBox();
        chkNaturschutz = new javax.swing.JCheckBox();
        chkBesonderePunkte = new javax.swing.JCheckBox();
        chkAbstimmungsvermerke = new javax.swing.JCheckBox();
        chkEigentuemer = new javax.swing.JCheckBox();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(640, 100));
        panInfo.setPreferredSize(new java.awt.Dimension(640, 100));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Informationen");
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.CardLayout());

        panMassnahmeSohle.setOpaque(false);
        panMassnahmeSohle.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panMassnahmeSohle, "massnahmesohle");

        panMassnahmeUfer.setOpaque(false);
        panMassnahmeUfer.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panMassnahmeUfer, "massnahmeufer");

        panMassnahmeSonstige.setOpaque(false);
        panMassnahmeSonstige.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panMassnahmeSonstige, "massnahmesonstige");

        panAbschnittsinfo.setOpaque(false);
        panAbschnittsinfo.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panAbschnittsinfo, "abschnittsinfo");

        panEmpty.setOpaque(false);
        panEmpty.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panEmpty, "empty");

        panWRRL.setOpaque(false);
        panWRRL.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panWRRL, "wrrl");

        panHydro.setOpaque(false);
        panHydro.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panHydro, "hydro");

        panAllgemein.setOpaque(false);
        panAllgemein.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panAllgemein, "allgemein");

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo, gridBagConstraints);

        panHeader.setOpaque(false);
        panHeader.setLayout(new java.awt.GridBagLayout());

        panHeaderInfo.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18));
        jLabel1.setText("GUP:");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 18));
        jLabel2.setText("GUP Warnow");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 18));
        jLabel3.setText("Gewässer:");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 18));
        jLabel4.setText("Warnow KM 0 - 4711");

        final javax.swing.GroupLayout panHeaderInfoLayout = new javax.swing.GroupLayout(panHeaderInfo);
        panHeaderInfo.setLayout(panHeaderInfoLayout);
        panHeaderInfoLayout.setHorizontalGroup(
            panHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panHeaderInfoLayout.createSequentialGroup().addContainerGap().addGroup(
                    panHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        jLabel3).addComponent(jLabel1)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        jLabel2,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        185,
                        Short.MAX_VALUE).addComponent(
                        jLabel4,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)).addContainerGap()));
        panHeaderInfoLayout.setVerticalGroup(
            panHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panHeaderInfoLayout.createSequentialGroup().addGap(10, 10, 10).addGroup(
                    panHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        jLabel1).addComponent(jLabel2)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(
                        jLabel3).addComponent(jLabel4)).addContainerGap(
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        panHeader.add(panHeaderInfo, gridBagConstraints);

        panControls.setOpaque(false);
        panControls.setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        panControls.add(jPanel1, gridBagConstraints);

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        panControls.add(jPanel2, gridBagConstraints);

        jPanel3.setOpaque(false);

        jLabel5.setText("Zoom:");
        jPanel3.add(jLabel5);

        sldZoom.setMaximum(200);
        sldZoom.setValue(0);
        sldZoom.addChangeListener(new javax.swing.event.ChangeListener() {

                @Override
                public void stateChanged(final javax.swing.event.ChangeEvent evt) {
                    sldZoomStateChanged(evt);
                }
            });
        jPanel3.add(sldZoom);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panControls.add(jPanel3, gridBagConstraints);

        jPanel4.setOpaque(false);

        bgrpDetails.add(togAllgemeinInfo);
        togAllgemeinInfo.setText("Allgemeine Infos");
        togAllgemeinInfo.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    togAllgemeinInfoActionPerformed(evt);
                }
            });
        jPanel4.add(togAllgemeinInfo);

        bgrpDetails.add(togWRRLInfo);
        togWRRLInfo.setText("WRRL");
        togWRRLInfo.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    togWRRLInfoActionPerformed(evt);
                }
            });
        jPanel4.add(togWRRLInfo);

        bgrpDetails.add(togHydroInfo);
        togHydroInfo.setText("Hydrologie");
        togHydroInfo.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    togHydroInfoActionPerformed(evt);
                }
            });
        jPanel4.add(togHydroInfo);

        panControls.add(jPanel4, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        panHeader.add(panControls, gridBagConstraints);

        panBand.setOpaque(false);
        panBand.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panHeader.add(panBand, gridBagConstraints);

        panBandControl.setPreferredSize(new java.awt.Dimension(100, 100));

        chkMassnahmen.setSelected(true);
        chkMassnahmen.setText("Maßnahmen (links, Sohle, rechts)");
        chkMassnahmen.setContentAreaFilled(false);
        chkMassnahmen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkMassnahmenActionPerformed(evt);
                }
            });

        chkSonstigeMassnahmen.setSelected(true);
        chkSonstigeMassnahmen.setText("sonstige Maßnahmen");
        chkSonstigeMassnahmen.setContentAreaFilled(false);
        chkSonstigeMassnahmen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkSonstigeMassnahmenActionPerformed(evt);
                }
            });

        chkWasserkoerper.setSelected(true);
        chkWasserkoerper.setText("Wasserkörper");
        chkWasserkoerper.setContentAreaFilled(false);
        chkWasserkoerper.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkWasserkoerperActionPerformed(evt);
                }
            });

        chkUmlandnutzung.setSelected(true);
        chkUmlandnutzung.setText("Umlandnutzung");
        chkUmlandnutzung.setContentAreaFilled(false);
        chkUmlandnutzung.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkUmlandnutzungActionPerformed(evt);
                }
            });

        chkQuerbauwerke.setText("Querbauwerke");
        chkQuerbauwerke.setContentAreaFilled(false);
        chkQuerbauwerke.setEnabled(false);
        chkQuerbauwerke.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkQuerbauwerkeActionPerformed(evt);
                }
            });

        chkNaturschutz.setText("Naturschutz");
        chkNaturschutz.setContentAreaFilled(false);
        chkNaturschutz.setEnabled(false);
        chkNaturschutz.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkNaturschutzActionPerformed(evt);
                }
            });

        chkBesonderePunkte.setText("besondere Punkte");
        chkBesonderePunkte.setContentAreaFilled(false);
        chkBesonderePunkte.setEnabled(false);

        chkAbstimmungsvermerke.setText("Abstimmungsvermerke");
        chkAbstimmungsvermerke.setContentAreaFilled(false);
        chkAbstimmungsvermerke.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkAbstimmungsvermerkeActionPerformed(evt);
                }
            });

        chkEigentuemer.setText("Eigentümer/Ansprechpartner");
        chkEigentuemer.setContentAreaFilled(false);
        chkEigentuemer.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkEigentuemerActionPerformed(evt);
                }
            });

        final javax.swing.GroupLayout panBandControlLayout = new javax.swing.GroupLayout(panBandControl);
        panBandControl.setLayout(panBandControlLayout);
        panBandControlLayout.setHorizontalGroup(
            panBandControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panBandControlLayout.createSequentialGroup().addContainerGap().addGroup(
                    panBandControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        chkMassnahmen).addComponent(chkSonstigeMassnahmen).addComponent(chkWasserkoerper))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(
                    panBandControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        chkUmlandnutzung).addComponent(chkNaturschutz).addComponent(chkQuerbauwerke)).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(
                    panBandControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        chkAbstimmungsvermerke).addComponent(chkEigentuemer).addComponent(chkBesonderePunkte))
                            .addContainerGap(2747, Short.MAX_VALUE)));
        panBandControlLayout.setVerticalGroup(
            panBandControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panBandControlLayout.createSequentialGroup().addContainerGap(17, Short.MAX_VALUE).addGroup(
                    panBandControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                        panBandControlLayout.createSequentialGroup().addGroup(
                            panBandControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(chkUmlandnutzung).addComponent(chkAbstimmungsvermerke)).addGroup(
                            panBandControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(chkNaturschutz).addComponent(chkEigentuemer)).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                            panBandControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(chkQuerbauwerke).addComponent(chkBesonderePunkte))).addGroup(
                        panBandControlLayout.createSequentialGroup().addComponent(chkMassnahmen).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(chkSonstigeMassnahmen)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                            chkWasserkoerper))).addGap(23, 23, 23)));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 22, 4, 4);
        panHeader.add(panBandControl, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panHeader, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void sldZoomStateChanged(final javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldZoomStateChanged
        final double zoom = sldZoom.getValue() / 10d;
        jband.setZoomFactor(zoom);
    }//GEN-LAST:event_sldZoomStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void togAllgemeinInfoActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togAllgemeinInfoActionPerformed
        switchToForm("allgemein");
    }//GEN-LAST:event_togAllgemeinInfoActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void togWRRLInfoActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togWRRLInfoActionPerformed
        switchToForm("wrrl");
    }//GEN-LAST:event_togWRRLInfoActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void togHydroInfoActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togHydroInfoActionPerformed
        switchToForm("hydro");
        jband.bandModelChanged(null);
    }//GEN-LAST:event_togHydroInfoActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkQuerbauwerkeActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkQuerbauwerkeActionPerformed
        querbauwerksband.setEnabled(chkQuerbauwerke.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkQuerbauwerkeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkMassnahmenActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMassnahmenActionPerformed
        linkesUferBand.setEnabled(chkMassnahmen.isSelected());
        rechtesUferBand.setEnabled(chkMassnahmen.isSelected());
        sohleBand.setEnabled(chkMassnahmen.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkMassnahmenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkSonstigeMassnahmenActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSonstigeMassnahmenActionPerformed
        sonstigeMassnahmenBand.setEnabled(chkSonstigeMassnahmen.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkSonstigeMassnahmenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkWasserkoerperActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkWasserkoerperActionPerformed
        wkband.setEnabled(chkWasserkoerper.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkWasserkoerperActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkUmlandnutzungActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkUmlandnutzungActionPerformed
        nutzungLinksBand.setEnabled(chkUmlandnutzung.isSelected());
        nutzungRechtsBand.setEnabled(chkUmlandnutzung.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkUmlandnutzungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkNaturschutzActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkNaturschutzActionPerformed
        naturchutzBand.setEnabled(chkNaturschutz.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkNaturschutzActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkAbstimmungsvermerkeActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAbstimmungsvermerkeActionPerformed
        abstimmungsvermerkeBand.setEnabled((chkAbstimmungsvermerke.isSelected()));
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkAbstimmungsvermerkeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkEigentuemerActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEigentuemerActionPerformed
        anwohnerLinksBand.setEnabled(chkEigentuemer.isSelected());
        anwohnerRechtsBand.setEnabled(chkEigentuemer.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkEigentuemerActionPerformed

    @Override
    public void dispose() {
//        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return cidsBean.toString();
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "sb",
            "gup_gewaesserabschnitt",
            1,
            1280,
            1024);
    }
}
