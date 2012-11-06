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

import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.server.search.WkSearchByStations;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.*;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.server.search.AbstractCidsServerSearch;
import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.jbands.BandModelEvent;
import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandModelListener;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupOperativesZielRouteEditor extends JPanel implements CidsBeanRenderer,
    FooterComponentProvider,
    EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    public static final String COLLECTION_PROPERTY = "ziele";

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupOperativesZielRouteEditor.class);
    private static final String GUP_OPERATIVES_ZIEL = "gup_operatives_ziel_abschnitt";
    public static final int GUP_UFER_LINKS = 1;
    public static final int GUP_UFER_RECHTS = 2;
    public static final int GUP_UMFELD_RECHTS = 3;
    public static final int GUP_UMFELD_LINKS = 4;
    public static final int GUP_SOHLE = 5;

    //~ Instance fields --------------------------------------------------------

    private List<CidsBean> rechtesUferList = new ArrayList<CidsBean>();
    private List<CidsBean> sohleList = new ArrayList<CidsBean>();
    private List<CidsBean> linkesUferList = new ArrayList<CidsBean>();
    private List<CidsBean> rechtesUmfeldList = new ArrayList<CidsBean>();
    private List<CidsBean> linkesUmfeldList = new ArrayList<CidsBean>();
    private OperativesZielRWBand uferLinksBand = new OperativesZielRWBand("Ufer links", GUP_OPERATIVES_ZIEL);
    private OperativesZielRWBand uferRechtsBand = new OperativesZielRWBand("Ufer rechts", GUP_OPERATIVES_ZIEL);
    private OperativesZielRWBand umfeldLinksBand = new OperativesZielRWBand("Umfeld links", GUP_OPERATIVES_ZIEL);
    private OperativesZielRWBand umfeldRechtsBand = new OperativesZielRWBand("Umfeld rechts", GUP_OPERATIVES_ZIEL);
    private OperativesZielRWBand sohleBand = new OperativesZielRWBand("Sohle", GUP_OPERATIVES_ZIEL);
    private WKBand wkband;
    private final JBand jband;
    private final BandModelListener modelListener = new GupOperativesZielBandModelListener();
    private final SimpleBandModel sbm = new SimpleBandModel();
    private CidsBean cidsBean;
    private GupOperativesZielAbschnittEditor operativesZielEditor;
    private boolean readOnly = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrpDetails;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton jbApply;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblSubTitle;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panBand;
    private javax.swing.JPanel panControls;
    private javax.swing.JPanel panEmpty;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private javax.swing.JPanel panHeader;
    private javax.swing.JPanel panHeaderInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panNew;
    private javax.swing.JPanel panZiele;
    private javax.swing.JSlider sldZoom;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GupGewaesserabschnittEditor object.
     */
    public GupOperativesZielRouteEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupOperativesZielRouteEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        jband = new JBand(readOnly);
        initComponents();

        uferLinksBand.setReadOnly(readOnly);
        uferRechtsBand.setReadOnly(readOnly);
        umfeldLinksBand.setReadOnly(readOnly);
        umfeldRechtsBand.setReadOnly(readOnly);
        sohleBand.setReadOnly(readOnly);
        uferLinksBand.setType(GUP_UFER_LINKS);
        uferRechtsBand.setType(GUP_UFER_RECHTS);
        umfeldLinksBand.setType(GUP_UMFELD_LINKS);
        umfeldRechtsBand.setType(GUP_UMFELD_RECHTS);
        sohleBand.setType(GUP_SOHLE);
        operativesZielEditor = new GupOperativesZielAbschnittEditor(readOnly);
        sbm.addBand(umfeldRechtsBand);
        sbm.addBand(uferRechtsBand);
        sbm.addBand(sohleBand);
        sbm.addBand(uferLinksBand);
        sbm.addBand(umfeldLinksBand);

        jband.setModel(sbm);

        panBand.add(jband, BorderLayout.CENTER);
        jband.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        switchToForm("empty");
        lblHeading.setText("Allgemeine Informationen");
        panZiele.add(operativesZielEditor, BorderLayout.CENTER);

        sbm.addBandModelListener(modelListener);

        sldZoom.setPaintTrack(false);
    }

    //~ Methods ----------------------------------------------------------------

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
        lblHeading.setText("");

        if (cidsBean != null) {
            if (cidsBean.getProperty("linie") == null) {
                panBand.removeAll();
                panBand.add(panNew, BorderLayout.CENTER);
                linearReferencedLineEditor.setLineField("linie");
                linearReferencedLineEditor.setCidsBean(cidsBean);
            } else {
                setNamesAndBands();
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setNamesAndBands() {
        final CidsBean route = LinearReferencingHelper.getRouteBeanFromStationBean((CidsBean)cidsBean.getProperty(
                    "linie.von"));
        final double from = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)cidsBean.getProperty(
                    "linie.von"));
        final double till = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)cidsBean.getProperty(
                    "linie.bis"));
        sbm.setMin(from);
        sbm.setMax(till);
        wkband = new WKBand(sbm.getMin(), sbm.getMax());
        jband.setMinValue(from);
        jband.setMaxValue(till);
        uferLinksBand.setRoute(route);
        uferRechtsBand.setRoute(route);
        sohleBand.setRoute(route);
        umfeldRechtsBand.setRoute(route);
        umfeldLinksBand.setRoute(route);

        final List<CidsBean> all = cidsBean.getBeanCollectionProperty(COLLECTION_PROPERTY);
        rechtesUferList = new ArrayList<CidsBean>();
        sohleList = new ArrayList<CidsBean>();
        linkesUferList = new ArrayList<CidsBean>();
        rechtesUmfeldList = new ArrayList<CidsBean>();
        linkesUmfeldList = new ArrayList<CidsBean>();

        for (final CidsBean tmp : all) {
            final Integer kind = (Integer)tmp.getProperty("wo.id");

            switch (kind) {
                case GupPlanungsabschnittEditor.GUP_MASSNAHME_UFER_LINKS: {
                    linkesUferList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_MASSNAHME_UFER_RECHTS: {
                    rechtesUferList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_MASSNAHME_UMFELD_LINKS: {
                    linkesUmfeldList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_MASSNAHME_UMFELD_RECHTS: {
                    rechtesUmfeldList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_MASSNAHME_SOHLE: {
                    sohleList.add(tmp);
                    break;
                }
            }
        }

        // Massnahmen extrahieren
        rechtesUferList = ObservableCollections.observableList(rechtesUferList);
        linkesUferList = ObservableCollections.observableList(linkesUferList);
        sohleList = ObservableCollections.observableList(sohleList);
        rechtesUmfeldList = ObservableCollections.observableList(rechtesUmfeldList);
        linkesUmfeldList = ObservableCollections.observableList(linkesUmfeldList);

        ((ObservableList<CidsBean>)rechtesUferList).addObservableListListener(new MassnBezugListListener(
                GupPlanungsabschnittEditor.GUP_MASSNAHME_UFER_RECHTS,
                cidsBean,
                COLLECTION_PROPERTY));
        ((ObservableList<CidsBean>)linkesUferList).addObservableListListener(new MassnBezugListListener(
                GupPlanungsabschnittEditor.GUP_MASSNAHME_UFER_LINKS,
                cidsBean,
                COLLECTION_PROPERTY));
        ((ObservableList<CidsBean>)rechtesUmfeldList).addObservableListListener(new MassnBezugListListener(
                GupPlanungsabschnittEditor.GUP_MASSNAHME_UMFELD_RECHTS,
                cidsBean,
                COLLECTION_PROPERTY));
        ((ObservableList<CidsBean>)linkesUmfeldList).addObservableListListener(new MassnBezugListListener(
                GupPlanungsabschnittEditor.GUP_MASSNAHME_UMFELD_LINKS,
                cidsBean,
                COLLECTION_PROPERTY));
        ((ObservableList<CidsBean>)sohleList).addObservableListListener(new MassnBezugListListener(
                GupPlanungsabschnittEditor.GUP_MASSNAHME_SOHLE,
                cidsBean,
                COLLECTION_PROPERTY));

        uferRechtsBand.setCidsBeans(rechtesUferList);
        sohleBand.setCidsBeans(sohleList);
        uferLinksBand.setCidsBeans(linkesUferList);
        umfeldRechtsBand.setCidsBeans(rechtesUmfeldList);
        umfeldLinksBand.setCidsBeans(linkesUmfeldList);

//        uferLinksBand.setCidsBeans(cidsBean.getBeanCollectionProperty("ufer_links"));
//        uferRechtsBand.setCidsBeans(cidsBean.getBeanCollectionProperty("ufer_rechts"));
//        umfeldLinksBand.setCidsBeans(cidsBean.getBeanCollectionProperty("umfeld_links"));
//        umfeldRechtsBand.setCidsBeans(cidsBean.getBeanCollectionProperty("umfeld_rechts"));
//        sohleBand.setCidsBeans(cidsBean.getBeanCollectionProperty("sohle"));

        final String rname = String.valueOf(route.getProperty("routenname"));

        lblSubTitle.setText(rname + " [" + (int)sbm.getMin() + "," + (int)sbm.getMax() + "]");

        wkband.fillAndInsertBand(sbm, String.valueOf(route.getProperty("gwk")), jband);
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
        panNew = new javax.swing.JPanel();
        linearReferencedLineEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor();
        jbApply = new javax.swing.JButton();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panZiele = new javax.swing.JPanel();
        panEmpty = new javax.swing.JPanel();
        panHeader = new javax.swing.JPanel();
        panHeaderInfo = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblSubTitle = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        sldZoom = new javax.swing.JSlider();
        panControls = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panBand = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        panFooter.setMinimumSize(new java.awt.Dimension(1050, 48));
        panFooter.setOpaque(false);
        panFooter.setPreferredSize(new java.awt.Dimension(1050, 48));
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        panNew.setOpaque(false);
        panNew.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        panNew.add(linearReferencedLineEditor, gridBagConstraints);

        jbApply.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupGewaesserabschnitt")); // NOI18N
        jbApply.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbApplyActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panNew.add(jbApply, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1050, 800));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1050, 800));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(640, 310));
        panInfo.setPreferredSize(new java.awt.Dimension(640, 310));

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

        panZiele.setOpaque(false);
        panZiele.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panZiele, "ziele");

        panEmpty.setOpaque(false);
        panEmpty.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panEmpty, "empty");

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo, gridBagConstraints);

        panHeader.setOpaque(false);
        panHeader.setLayout(new java.awt.GridBagLayout());

        panHeaderInfo.setMinimumSize(new java.awt.Dimension(531, 102));
        panHeaderInfo.setOpaque(false);
        panHeaderInfo.setPreferredSize(new java.awt.Dimension(531, 102));
        panHeaderInfo.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel3.setText("Gewässer:");
        jLabel3.setMinimumSize(new java.awt.Dimension(91, 22));
        panHeaderInfo.add(jLabel3);
        jLabel3.setBounds(12, 40, 92, 22);

        lblSubTitle.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        lblSubTitle.setText("Warnow KM 0 - 4711");
        panHeaderInfo.add(lblSubTitle);
        lblSubTitle.setBounds(110, 40, 350, 22);

        jLabel5.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        jLabel5.setText("Zoom:");
        jLabel5.setMaximumSize(new java.awt.Dimension(92, 22));
        jLabel5.setMinimumSize(new java.awt.Dimension(92, 22));
        jLabel5.setPreferredSize(new java.awt.Dimension(92, 22));
        panHeaderInfo.add(jLabel5);
        jLabel5.setBounds(12, 68, 80, 20);

        sldZoom.setMaximum(200);
        sldZoom.setValue(0);
        sldZoom.addChangeListener(new javax.swing.event.ChangeListener() {

                @Override
                public void stateChanged(final javax.swing.event.ChangeEvent evt) {
                    sldZoomStateChanged(evt);
                }
            });
        panHeaderInfo.add(sldZoom);
        sldZoom.setBounds(110, 72, 350, 16);

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

        jPanel4.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        panControls.add(jPanel4, gridBagConstraints);

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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panHeader, gridBagConstraints);

        jPanel3.setMinimumSize(new java.awt.Dimension(1050, 1));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(1050, 1));

        jLabel4.setMaximumSize(new java.awt.Dimension(1050, 1));
        jLabel4.setMinimumSize(new java.awt.Dimension(1050, 1));
        jLabel4.setPreferredSize(new java.awt.Dimension(1050, 1));

        final javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                1050,
                Short.MAX_VALUE).addGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                    jPanel3Layout.createSequentialGroup().addGap(0, 5, Short.MAX_VALUE).addComponent(
                        jLabel4,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        1040,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 5, Short.MAX_VALUE))));
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1, Short.MAX_VALUE)
                        .addGroup(
                            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                jPanel3Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(
                                    jLabel4,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE))));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(jPanel3, gridBagConstraints);
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
    private void jbApplyActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbApplyActionPerformed
        panBand.removeAll();
        panBand.add(jband, BorderLayout.CENTER);
        setNamesAndBands();
        linearReferencedLineEditor.dispose();
    }//GEN-LAST:event_jbApplyActionPerformed

    @Override
    public void dispose() {
        sbm.removeBandModelListener(modelListener);
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
            "x",
            "gup_gewaesserabschnitt",
            1,
            1280,
            1024);
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        operativesZielEditor.dispose();
        linearReferencedLineEditor.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        return linearReferencedLineEditor.prepareForSave();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class GupOperativesZielBandModelListener implements BandModelListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void bandModelChanged(final BandModelEvent e) {
        }

        @Override
        public void bandModelSelectionChanged(final BandModelEvent e) {
            final BandMember bm = jband.getSelectedBandMember();
            operativesZielEditor.dispose();
            jband.setRefreshAvoided(true);

            if (bm != null) {
                bgrpDetails.clearSelection();
                switchToForm("empty");
                lblHeading.setText("");

                if (bm instanceof OperativesZielRWBandMember) {
                    switchToForm("ziele");
                    lblHeading.setText("Operatives Ziel");
                    final int type = ((OperativesZielRWBand)((OperativesZielRWBandMember)bm).getParentBand()).getType();
                    int kompartiment = 0;
                    List<CidsBean> otherBeans = null;

                    if (type == GUP_SOHLE) {
                        kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_SOHLE;
                        otherBeans = sohleList;
                    } else if (type == GUP_UFER_LINKS) {
                        kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UFER;
                        otherBeans = linkesUferList;
                    } else if (type == GUP_UFER_RECHTS) {
                        kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UFER;
                        otherBeans = rechtesUferList;
                    } else if (type == GUP_UMFELD_LINKS) {
                        kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UMFELD;
                        otherBeans = linkesUmfeldList;
                    } else if (type == GUP_UMFELD_RECHTS) {
                        kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UMFELD;
                        otherBeans = rechtesUmfeldList;
                    }

                    operativesZielEditor.setKompartiment(kompartiment);
                    operativesZielEditor.setOthers(otherBeans);
                    operativesZielEditor.setCidsBean(((OperativesZielRWBandMember)bm).getCidsBean());
                }
            } else {
                switchToForm("empty");
                lblHeading.setText("");
            }

            jband.setRefreshAvoided(false);
            jband.bandModelChanged(null);
        }

        @Override
        public void bandModelValuesChanged(final BandModelEvent e) {
        }
    }
}
