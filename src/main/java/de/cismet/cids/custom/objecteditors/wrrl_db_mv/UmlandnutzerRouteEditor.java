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
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import de.cismet.cids.client.tools.DevelopmentTools;

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
public class UmlandnutzerRouteEditor extends JPanel implements CidsBeanRenderer,
    FooterComponentProvider,
    EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

// RequestsFullSizeComponent,

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            UmlandnutzerRouteEditor.class);
    private static final String UMLANDNUTZER = "umlandnutzer";
    private static final String COLLECTION_PROPERTY = "umlandnutzer";

    //~ Instance fields --------------------------------------------------------

    private UmlandnutzerRWBand umlandnutzer_links = new UmlandnutzerRWBand(
            "Umlandnutzer links",
            UMLANDNUTZER);
    private UmlandnutzerRWBand umlandnutzer_rechts = new UmlandnutzerRWBand(
            "Umlandnutzer rechts",
            UMLANDNUTZER);
    private WKBand wkband;
    private VermessungBandElementEditor vermessungsEditor = new VermessungBandElementEditor();
    private VermessungsbandHelper vermessungsband;
    private final JBand jband;
    private final BandModelListener modelListener = new UmlandnutzerBandModelListener();
    private final SimpleBandModel sbm = new SimpleBandModel();
    private CidsBean cidsBean;
    private UmlandnutzerEditor umlandnutzerEditor;
    private boolean readOnly = false;
    private List<CidsBean> rechtesUferList = new ArrayList<CidsBean>();
    private List<CidsBean> linkesUferList = new ArrayList<CidsBean>();

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
    private javax.swing.JButton jbApply1;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblSubTitle;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panApply;
    private javax.swing.JPanel panApplyBand;
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
    private javax.swing.JPanel panUmlandnutzer;
    private javax.swing.JPanel panVermessung;
    private javax.swing.JSlider sldZoom;
    private javax.swing.JToggleButton togApplyStats;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GupGewaesserabschnittEditor object.
     */
    public UmlandnutzerRouteEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public UmlandnutzerRouteEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        jband = new JBand(readOnly);
        initComponents();

        umlandnutzerEditor = new UmlandnutzerEditor(readOnly);
        umlandnutzer_rechts.setReadOnly(readOnly);
        umlandnutzer_rechts.setSide(UmlandnutzungRWBand.RIGHT);
        sbm.addBand(umlandnutzer_rechts);
        umlandnutzer_links.setReadOnly(readOnly);
        umlandnutzer_links.setSide(UmlandnutzungRWBand.LEFT);
        sbm.addBand(umlandnutzer_links);
        panVermessung.add(vermessungsEditor, BorderLayout.CENTER);

        jband.setModel(sbm);

        panBand.add(jband, BorderLayout.CENTER);
        jband.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        switchToForm("empty");
        lblHeading.setText("Umlandnutzer");
        panUmlandnutzer.add(umlandnutzerEditor, BorderLayout.CENTER);

        sbm.addBandModelListener(modelListener);

        sldZoom.setPaintTrack(false);
        if (!readOnly) {
            vermessungsband = new VermessungsbandHelper(
                    jband,
                    modelListener,
                    panBand,
                    panApplyBand,
                    panApply,
                    togApplyStats);
        } else {
            togApplyStats.setVisible(false);
        }
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
            if (!readOnly) {
                vermessungsband.setCidsBean(cidsBean);
            }
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
        wkband = new WKBand(from, till);
        if (!readOnly) {
            vermessungsband.setVwkBand(new WKBand(sbm.getMin(), sbm.getMax()));
        }
        jband.setMinValue(from);
        jband.setMaxValue(till);
        umlandnutzer_links.setRoute(route);
        umlandnutzer_rechts.setRoute(route);

        // extract Umlandnutzer
        final List<CidsBean> all = cidsBean.getBeanCollectionProperty(COLLECTION_PROPERTY);
        rechtesUferList = new ArrayList<CidsBean>();
        linkesUferList = new ArrayList<CidsBean>();

        for (final CidsBean tmp : all) {
            final Integer kind = (Integer)tmp.getProperty("wo.id");

            switch (kind) {
                case GupPlanungsabschnittEditor.GUP_UFER_LINKS: {
                    linkesUferList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_UFER_RECHTS: {
                    rechtesUferList.add(tmp);
                    break;
                }
            }
        }

        rechtesUferList = ObservableCollections.observableList(rechtesUferList);
        linkesUferList = ObservableCollections.observableList(linkesUferList);

        ((ObservableList<CidsBean>)rechtesUferList).addObservableListListener(new MassnBezugListListener(
                GupPlanungsabschnittEditor.GUP_UFER_RECHTS,
                cidsBean,
                COLLECTION_PROPERTY));
        ((ObservableList<CidsBean>)linkesUferList).addObservableListListener(new MassnBezugListListener(
                GupPlanungsabschnittEditor.GUP_UFER_LINKS,
                cidsBean,
                COLLECTION_PROPERTY));

        umlandnutzer_rechts.setCidsBeans(rechtesUferList);
        umlandnutzer_links.setCidsBeans(linkesUferList);

        final String rname = String.valueOf(route.getProperty("routenname"));

        lblSubTitle.setText(rname + " [" + (int)sbm.getMin() + "," + (int)sbm.getMax() + "]");

        wkband.fillAndInsertBand(sbm, String.valueOf(route.getProperty("gwk")), jband, vermessungsband);
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
        panApply = new javax.swing.JPanel();
        jbApply1 = new javax.swing.JButton();
        panApplyBand = new javax.swing.JPanel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panUmlandnutzer = new javax.swing.JPanel();
        panEmpty = new javax.swing.JPanel();
        panVermessung = new javax.swing.JPanel();
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
        togApplyStats = new javax.swing.JToggleButton();
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

        jbApply.setText(org.openide.util.NbBundle.getMessage(UmlandnutzerRouteEditor.class, "GupGewaesserabschnitt")); // NOI18N
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

        panApply.setOpaque(false);
        panApply.setLayout(new java.awt.GridBagLayout());

        jbApply1.setText(org.openide.util.NbBundle.getMessage(UmlandnutzerRouteEditor.class, "GupGewaesserabschnitt")); // NOI18N
        jbApply1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbApply1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 5, 5);
        panApply.add(jbApply1, gridBagConstraints);

        panApplyBand.setOpaque(false);
        panApplyBand.setPreferredSize(new java.awt.Dimension(300, 100));
        panApplyBand.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panApply.add(panApplyBand, gridBagConstraints);

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

        panUmlandnutzer.setOpaque(false);
        panUmlandnutzer.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panUmlandnutzer, "umlandnutzer");

        panEmpty.setOpaque(false);
        panEmpty.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panEmpty, "empty");

        panVermessung.setOpaque(false);
        panVermessung.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panVermessung, "vermessung");

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

        togApplyStats.setText("Vermessen");
        togApplyStats.setPreferredSize(new java.awt.Dimension(117, 25));
        togApplyStats.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    togApplyStatsActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 7);
        panHeader.add(togApplyStats, gridBagConstraints);

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
    private void sldZoomStateChanged(final javax.swing.event.ChangeEvent evt) { //GEN-FIRST:event_sldZoomStateChanged
        final double zoom = sldZoom.getValue() / 10d;
        jband.setZoomFactor(zoom);
        vermessungsband.setZoomFactor(zoom);
    }                                                                           //GEN-LAST:event_sldZoomStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbApplyActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbApplyActionPerformed
        panBand.removeAll();
        panBand.add(jband, BorderLayout.CENTER);
        setNamesAndBands();
        linearReferencedLineEditor.dispose();
        if (!readOnly) {
            vermessungsband.showRoute();
            togApplyStats.setEnabled(true);
        }
    }                                                                           //GEN-LAST:event_jbApplyActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void togApplyStatsActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_togApplyStatsActionPerformed
        if (togApplyStats.isSelected()) {
            vermessungsband.showVermessungsband();
        } else {
            vermessungsband.hideVermessungsband();
        }
        updateUI();
        repaint();
    }                                                                                 //GEN-LAST:event_togApplyStatsActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbApply1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbApply1ActionPerformed
        final UmlandnutzerRWBand[] bands = new UmlandnutzerRWBand[2];
        bands[0] = umlandnutzer_links;
        bands[1] = umlandnutzer_rechts;
        vermessungsband.applyStats(this, bands, UMLANDNUTZER);
    }                                                                            //GEN-LAST:event_jbApply1ActionPerformed

    @Override
    public void dispose() {
        vermessungsEditor.dispose();
        sbm.removeBandModelListener(modelListener);
        if (!readOnly) {
            vermessungsband.dispose();
        }
        umlandnutzerEditor.dispose();
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
        linearReferencedLineEditor.editorClosed(event);
        vermessungsband.editorClosed(event);
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
    class UmlandnutzerBandModelListener implements BandModelListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void bandModelChanged(final BandModelEvent e) {
        }

        @Override
        public void bandModelSelectionChanged(final BandModelEvent e) {
            BandMember bm;

            if (togApplyStats.isSelected()) {
                bm = vermessungsband.getSelectedMember();
                vermessungsband.setRefreshAvoided(true);
            } else {
                bm = jband.getSelectedBandMember();
                jband.setRefreshAvoided(true);
            }
            umlandnutzerEditor.dispose();

            if (bm != null) {
                bgrpDetails.clearSelection();
                switchToForm("empty");
                lblHeading.setText("");

                if (bm instanceof UmlandnutzerRWBandMember) {
                    switchToForm("umlandnutzer");
                    lblHeading.setText("Umlandnutzer");

                    final UmlandnutzerRWBand band = (UmlandnutzerRWBand)((UmlandnutzerRWBandMember)bm).getParentBand();
                    final List<CidsBean> otherBeans;

                    if (band.getSide() == UmlandnutzungRWBand.RIGHT) {
                        otherBeans = CidsBeanSupport.getBeanCollectionFromProperty(
                                cidsBean,
                                "umlandnutzer_rechts");
                    } else {
                        otherBeans = CidsBeanSupport.getBeanCollectionFromProperty(
                                cidsBean,
                                "umlandnutzer_links");
                    }

                    umlandnutzerEditor.setOthers(otherBeans);
                    umlandnutzerEditor.setCidsBean(((UmlandnutzerRWBandMember)bm).getCidsBean());
                } else if (bm instanceof VermessungsbandMember) {
                    switchToForm("vermessung");
                    lblHeading.setText("Vermessungselement");
                    final List<CidsBean> others = vermessungsband.getAllMembers();
                    vermessungsEditor.setOthers(others);
                    vermessungsEditor.setCidsBean(((VermessungsbandMember)bm).getCidsBean());
                }
            } else {
                switchToForm("empty");
                lblHeading.setText("");
            }

            if (togApplyStats.isSelected()) {
                vermessungsband.setRefreshAvoided(false);
                vermessungsband.bandModelChanged();
            } else {
                jband.setRefreshAvoided(false);
                jband.bandModelChanged(null);
            }
        }

        @Override
        public void bandModelValuesChanged(final BandModelEvent e) {
        }
    }
}
