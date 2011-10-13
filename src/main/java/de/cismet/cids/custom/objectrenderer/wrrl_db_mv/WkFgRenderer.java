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
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import org.jdesktop.beansbinding.Converter;

import java.sql.Timestamp;

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.cids.custom.reports.WkFgReport;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.wrrl_db_mv.util.TimestampConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.TitleComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class WkFgRenderer extends JPanel implements CidsBeanRenderer, TitleComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(WkFgRenderer.class);

    // private final DefaultComboBoxModel qualityStatusCodeModel;

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReport;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.ExcemptionRenderer excemptionEditor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList lstAusnahmen;
    private javax.swing.JPanel panAllgemeines;
    private javax.swing.JPanel panAusnahmen;
    private javax.swing.JPanel panBiol;
    private javax.swing.JPanel panChem;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private javax.swing.JPanel panHydro;
    private javax.swing.JPanel panMeldInf;
    private javax.swing.JPanel panOeko;
    private javax.swing.JPanel panQualitaet3;
    private javax.swing.JPanel panTitle;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private javax.swing.JScrollPane scpAusnahmen;
    private javax.swing.JTabbedPane tpMain;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanEleven wkFgPanEleven1;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanOne wkFgPanOne;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanSeven wkFgPanSeven1;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanSix wkFgPanSix1;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanTen wkFgPanTen1;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanThirteen wkFgPanThirteen1;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanTwelve wkFgPanTwelve1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public WkFgRenderer() {
        initComponents();
        tpMain.setUI(new TabbedPaneUITransparent());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public JComponent getTitleComponent() {
        return panTitle;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            wkFgPanOne.setCidsBean(cidsBean);
            wkFgPanEleven1.setCidsBean(cidsBean);
            wkFgPanSeven1.setCidsBean(cidsBean);
            wkFgPanTen1.setCidsBean(cidsBean);
            wkFgPanThirteen1.setCidsBean(cidsBean);
            wkFgPanTwelve1.setCidsBean(cidsBean);
            wkFgPanSix1.setCidsBean(cidsBean);
            bindingGroup.bind();
            lstAusnahmen.setSelectedIndex((lstAusnahmen.getModel().getSize() == 0) ? -1 : 0);
            Object avUser = cidsBean.getProperty("av_user");
            Object avTime = cidsBean.getProperty("av_time");

            if (avUser == null) {
                avUser = "(unbekannt)";
            }
            if (avTime instanceof Timestamp) {
                avTime = TimestampConverter.getInstance().convertForward((Timestamp)avTime);
            } else {
                avTime = "(unbekannt)";
            }

            lblFoot.setText("Zuletzt bearbeitet von " + avUser + " am " + avTime);
        } else {
            lblFoot.setText("");
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        btnReport = new javax.swing.JButton();
        tpMain = new javax.swing.JTabbedPane();
        panAllgemeines = new javax.swing.JPanel();
        wkFgPanOne = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanOne();
        panOeko = new javax.swing.JPanel();
        wkFgPanTwelve1 = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanTwelve();
        panBiol = new javax.swing.JPanel();
        wkFgPanTen1 = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanTen();
        panQualitaet3 = new javax.swing.JPanel();
        wkFgPanSix1 = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanSix();
        panHydro = new javax.swing.JPanel();
        wkFgPanEleven1 = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanEleven();
        panChem = new javax.swing.JPanel();
        wkFgPanThirteen1 = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanThirteen();
        panMeldInf = new javax.swing.JPanel();
        wkFgPanSeven1 = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkFgPanSeven();
        panAusnahmen = new javax.swing.JPanel();
        excemptionEditor = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.ExcemptionRenderer();
        roundedPanel1 = new de.cismet.tools.gui.RoundedPanel();
        scpAusnahmen = new javax.swing.JScrollPane();
        lstAusnahmen = new javax.swing.JList();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        panTitle.setOpaque(false);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18));
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));

        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean}"),
                lblTitle,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new CidsbeanToStringConverter());
        bindingGroup.addBinding(binding);

        btnReport.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wrrl_db_mv/printer.png")));         // NOI18N
        btnReport.setText(org.openide.util.NbBundle.getMessage(
                WkFgRenderer.class,
                "FotodokumentationRenderer.btnReport.text"));                                                     // NOI18N
        btnReport.setBorder(null);
        btnReport.setBorderPainted(false);
        btnReport.setContentAreaFilled(false);
        btnReport.setFocusPainted(false);
        btnReport.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wrrl_db_mv/printer_pressed.png"))); // NOI18N
        btnReport.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnReportActionPerformed(evt);
                }
            });

        final javax.swing.GroupLayout panTitleLayout = new javax.swing.GroupLayout(panTitle);
        panTitle.setLayout(panTitleLayout);
        panTitleLayout.setHorizontalGroup(
            panTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panTitleLayout.createSequentialGroup().addContainerGap().addComponent(lblTitle).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                    232,
                    Short.MAX_VALUE).addComponent(btnReport)));
        panTitleLayout.setVerticalGroup(
            panTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lblTitle)
                        .addComponent(btnReport));

        setMinimumSize(new java.awt.Dimension(1200, 735));
        setPreferredSize(new java.awt.Dimension(1200, 735));
        setLayout(new java.awt.BorderLayout());

        panAllgemeines.setOpaque(false);
        panAllgemeines.setLayout(new java.awt.GridBagLayout());

        wkFgPanOne.setMinimumSize(new java.awt.Dimension(1015, 550));
        wkFgPanOne.setPreferredSize(new java.awt.Dimension(1015, 550));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panAllgemeines.add(wkFgPanOne, gridBagConstraints);

        tpMain.addTab("Allgemeines", panAllgemeines);

        panOeko.setOpaque(false);
        panOeko.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panOeko.add(wkFgPanTwelve1, gridBagConstraints);

        tpMain.addTab("Ökologischer Zustand", panOeko);

        panBiol.setMinimumSize(new java.awt.Dimension(910, 650));
        panBiol.setOpaque(false);
        panBiol.setPreferredSize(new java.awt.Dimension(910, 650));
        panBiol.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panBiol.add(wkFgPanTen1, gridBagConstraints);

        tpMain.addTab("Biologischer Zustand", panBiol);

        panQualitaet3.setMinimumSize(new java.awt.Dimension(910, 650));
        panQualitaet3.setOpaque(false);
        panQualitaet3.setPreferredSize(new java.awt.Dimension(910, 650));
        panQualitaet3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panQualitaet3.add(wkFgPanSix1, gridBagConstraints);

        tpMain.addTab("Physikalisch-chemische QK", panQualitaet3);

        panHydro.setMinimumSize(new java.awt.Dimension(910, 650));
        panHydro.setOpaque(false);
        panHydro.setPreferredSize(new java.awt.Dimension(910, 650));
        panHydro.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panHydro.add(wkFgPanEleven1, gridBagConstraints);

        tpMain.addTab("Hydromorphologische QK", panHydro);

        panChem.setMinimumSize(new java.awt.Dimension(910, 650));
        panChem.setOpaque(false);
        panChem.setPreferredSize(new java.awt.Dimension(910, 650));
        panChem.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panChem.add(wkFgPanThirteen1, gridBagConstraints);

        tpMain.addTab("Chemischer Zustand", panChem);

        panMeldInf.setMinimumSize(new java.awt.Dimension(910, 650));
        panMeldInf.setOpaque(false);
        panMeldInf.setPreferredSize(new java.awt.Dimension(910, 650));
        panMeldInf.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panMeldInf.add(wkFgPanSeven1, gridBagConstraints);

        tpMain.addTab("Melderelevante Informationen", panMeldInf);

        panAusnahmen.setOpaque(false);
        panAusnahmen.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 0);
        panAusnahmen.add(excemptionEditor, gridBagConstraints);

        roundedPanel1.setMinimumSize(new java.awt.Dimension(450, 244));
        roundedPanel1.setPreferredSize(new java.awt.Dimension(500, 244));
        roundedPanel1.setLayout(new java.awt.GridBagLayout());

        scpAusnahmen.setMinimumSize(new java.awt.Dimension(350, 175));
        scpAusnahmen.setPreferredSize(new java.awt.Dimension(350, 175));

        lstAusnahmen.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        final org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.ausnahmen}");
        final org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstAusnahmen);
        bindingGroup.addBinding(jListBinding);

        lstAusnahmen.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    lstAusnahmenValueChanged(evt);
                }
            });
        scpAusnahmen.setViewportView(lstAusnahmen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        roundedPanel1.add(scpAusnahmen, gridBagConstraints);

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ausnahmen");
        panHeadInfo.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        roundedPanel1.add(panHeadInfo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 10);
        panAusnahmen.add(roundedPanel1, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        panAusnahmen.add(jPanel1, gridBagConstraints);

        tpMain.addTab("Ausnahmen", panAusnahmen);

        add(tpMain, java.awt.BorderLayout.PAGE_START);
        tpMain.getAccessibleContext().setAccessibleName("Ökologischer Zustand");

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstAusnahmenValueChanged(final javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstAusnahmenValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object selObj = lstAusnahmen.getSelectedValue();
            if (selObj instanceof CidsBean) {
                excemptionEditor.setCidsBean((CidsBean)selObj);
            }
        }
    }//GEN-LAST:event_lstAusnahmenValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnReportActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        WkFgReport.showReport(cidsBean);
    }//GEN-LAST:event_btnReportActionPerformed

    @Override
    public void dispose() {
        wkFgPanOne.dispose();
        wkFgPanEleven1.dispose();
        wkFgPanSeven1.dispose();
        wkFgPanSix1.dispose();
        wkFgPanTen1.dispose();
        wkFgPanThirteen1.dispose();
        wkFgPanTwelve1.dispose();
        excemptionEditor.dispose();
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Wasserkörper " + String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class CidsbeanToStringConverter extends Converter<CidsBean, String> {

        //~ Methods ------------------------------------------------------------

        @Override
        public String convertForward(final CidsBean s) {
            return getTitle();
        }

        @Override
        public CidsBean convertReverse(final String t) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
