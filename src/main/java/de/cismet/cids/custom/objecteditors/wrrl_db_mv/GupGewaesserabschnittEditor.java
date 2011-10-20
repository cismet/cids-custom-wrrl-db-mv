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

import Sirius.server.search.CidsServerSearch;

import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.QuerbautenSearchByStations;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.server.search.WkSearchByStations;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.MassnahmenBand;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.POIBand;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.WKBand;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class GupGewaesserabschnittEditor extends JPanel implements CidsBeanRenderer, FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupGewaesserabschnittEditor.class);

    //~ Instance fields --------------------------------------------------------

    final JBand jband = new JBand();
    final SimpleBandModel sbm = new SimpleBandModel();
    private final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrpDetails;
    private javax.swing.JLabel blbSpace;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JPanel panBand;
    private javax.swing.JPanel panControls;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private javax.swing.JPanel panHeader;
    private javax.swing.JPanel panHeaderInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JSlider sldZoom;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.Unterhaltungsabschnittsfeld unterhaltungsabschnittsfeld1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public GupGewaesserabschnittEditor() {
        initComponents();
        panBand.add(jband, BorderLayout.CENTER);
        jband.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
//        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            final SimpleBand empty=new SimpleBand();
            
            final MassnahmenBand rechts = new MassnahmenBand("rechts");
            rechts.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_ufer_rechts"));
            final MassnahmenBand sohle = new MassnahmenBand("Sohle");
            sohle.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_sohle"));
            final MassnahmenBand links = new MassnahmenBand("links");
            links.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_ufer_links"));
            final MassnahmenBand sonstige = new MassnahmenBand("sonstige");
            sonstige.setCidsBeans(cidsBean.getBeanCollectionProperty("gup_massnahmen_sonstige"));

            sbm.addBand(links);
            sbm.addBand(sohle);
            sbm.addBand(rechts);
            sbm.addBand(sonstige);
            try {
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
            } catch (Exception e) {
                log.error("Problem beim Suchen der Wasserkoerper", e);
            }

            jband.setModel(sbm);

            // DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
            // bindingGroup,
            // cidsBean);
            // bindingGroup.bind();
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
        blbSpace = new javax.swing.JLabel();
        unterhaltungsabschnittsfeld1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.Unterhaltungsabschnittsfeld();
        panHeader = new javax.swing.JPanel();
        panHeaderInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panControls = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        sldZoom = new javax.swing.JSlider();
        panBand = new javax.swing.JPanel();

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
        panInfoContent.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(blbSpace, gridBagConstraints);

        unterhaltungsabschnittsfeld1.setMinimumSize(new java.awt.Dimension(10, 50));
        unterhaltungsabschnittsfeld1.setOpaque(false);
        unterhaltungsabschnittsfeld1.setPreferredSize(new java.awt.Dimension(10, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        panInfoContent.add(unterhaltungsabschnittsfeld1, gridBagConstraints);

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

        jLabel1.setText("GUP:");

        jLabel2.setText("GUP Warnow");

        jLabel3.setText("Gewässer:");

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
                        132,
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

        jToggleButton1.setText("Allgemeine Infos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        panControls.add(jToggleButton1, gridBagConstraints);

        jToggleButton2.setText("WRRL");
        panControls.add(jToggleButton2, new java.awt.GridBagConstraints());

        jToggleButton3.setText("Hydrologie");
        panControls.add(jToggleButton3, new java.awt.GridBagConstraints());

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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        panHeader.add(panControls, gridBagConstraints);

        panBand.setOpaque(false);
        panBand.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panHeader.add(panBand, gridBagConstraints);

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
}
