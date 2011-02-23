/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;

import java.sql.Timestamp;

import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.util.TimestampConverter;
import de.cismet.cids.custom.util.UIUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkSgEditor extends JPanel implements CidsBeanRenderer, EditorSaveListener, FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(WkSgEditor.class);
    private static final MetaClass AUSNAHME_MC;

    static {
        AUSNAHME_MC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "EXCEMPTION");
    }
    // private final DefaultComboBoxModel qualityStatusCodeModel;

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAusnahme;
    private javax.swing.JButton btnRemAusnahme;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.ExcemptionEditor excemptionEditor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeadingAusnahme;
    private javax.swing.JList lstAusnahmen;
    private javax.swing.JPanel panAllgemeines;
    private javax.swing.JPanel panAusnahmen;
    private javax.swing.JPanel panContrAusnahmen;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private javax.swing.JPanel panMeld;
    private javax.swing.JPanel panQualitaet;
    private javax.swing.JPanel panRisiken;
    private javax.swing.JLabel panSpace;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private javax.swing.JScrollPane scpAusnahmen;
    private javax.swing.JTabbedPane tpMain;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanEight wkSgPanEight;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanFive wkSgPanFive;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanFour wkSgPanFour;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanNine wkSgPanNine;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanOne wkSgPanOne;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanSeven wkSgPanSeven;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanSix wkSgPanSix;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanThree wkSgPanThree;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanTwo wkSgPanTwo;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public WkSgEditor() {
//        try {
//            MetaClass qscMetaClass = ClassCacheMultiple.getMetaClass("WRRL_DB_MV", "quality_status_code");
//            qualityStatusCodeModel = DefaultBindableReferenceCombo.getModelByMetaClass(qscMetaClass, false);
        initComponents();
        tpMain.setUI(new TabbedPaneUITransparent());
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            wkSgPanOne.setCidsBean(cidsBean);
            wkSgPanTwo.setCidsBean(cidsBean);
            wkSgPanThree.setCidsBean(cidsBean);
            wkSgPanFour.setCidsBean(cidsBean);
            wkSgPanFive.setCidsBean(cidsBean);
            wkSgPanSix.setCidsBean(cidsBean);
            wkSgPanSeven.setCidsBean(cidsBean);
            wkSgPanEight.setCidsBean(cidsBean);
            wkSgPanNine.setCidsBean(cidsBean);
            bindingGroup.bind();
            lstAusnahmen.setSelectedIndex((lstAusnahmen.getModel().getSize() == 0) ? -1 : 0);
            Object avUser = cidsBean.getProperty("av_user");
            Object avTime = cidsBean.getProperty("av_date");

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
        tpMain = new javax.swing.JTabbedPane();
        panAllgemeines = new javax.swing.JPanel();
        wkSgPanOne = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanOne();
        panRisiken = new javax.swing.JPanel();
        wkSgPanSeven = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanSeven();
        wkSgPanSix = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanSix();
        panQualitaet = new javax.swing.JPanel();
        wkSgPanTwo = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanTwo();
        wkSgPanThree = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanThree();
        wkSgPanFour = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanFour();
        wkSgPanFive = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanFive();
        panSpace = new javax.swing.JLabel();
        wkSgPanEight = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanEight();
        panMeld = new javax.swing.JPanel();
        wkSgPanNine = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanNine();
        panAusnahmen = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        excemptionEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.ExcemptionEditor();
        roundedPanel1 = new de.cismet.tools.gui.RoundedPanel();
        scpAusnahmen = new javax.swing.JScrollPane();
        lstAusnahmen = new javax.swing.JList();
        panContrAusnahmen = new javax.swing.JPanel();
        btnAddAusnahme = new javax.swing.JButton();
        btnRemAusnahme = new javax.swing.JButton();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeadingAusnahme = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1200, 736));
        setPreferredSize(new java.awt.Dimension(1200, 736));
        setLayout(new java.awt.BorderLayout());

        panAllgemeines.setOpaque(false);
        panAllgemeines.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panAllgemeines.add(wkSgPanOne, gridBagConstraints);

        tpMain.addTab("Allgemeines", panAllgemeines);

        panRisiken.setOpaque(false);
        panRisiken.setLayout(new java.awt.GridBagLayout());

        wkSgPanSeven.setPreferredSize(new java.awt.Dimension(1050, 250));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 10, 0);
        panRisiken.add(wkSgPanSeven, gridBagConstraints);

        wkSgPanSix.setPreferredSize(new java.awt.Dimension(1050, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panRisiken.add(wkSgPanSix, gridBagConstraints);

        tpMain.addTab("Beschreibung und Risiken", panRisiken);

        panQualitaet.setOpaque(false);
        panQualitaet.setLayout(new java.awt.GridBagLayout());

        wkSgPanTwo.setMinimumSize(new java.awt.Dimension(520, 250));
        wkSgPanTwo.setPreferredSize(new java.awt.Dimension(520, 250));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        panQualitaet.add(wkSgPanTwo, gridBagConstraints);

        wkSgPanThree.setMinimumSize(new java.awt.Dimension(450, 320));
        wkSgPanThree.setPreferredSize(new java.awt.Dimension(450, 320));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        panQualitaet.add(wkSgPanThree, gridBagConstraints);

        wkSgPanFour.setMinimumSize(new java.awt.Dimension(450, 300));
        wkSgPanFour.setPreferredSize(new java.awt.Dimension(450, 300));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        panQualitaet.add(wkSgPanFour, gridBagConstraints);

        wkSgPanFive.setMinimumSize(new java.awt.Dimension(450, 250));
        wkSgPanFive.setPreferredSize(new java.awt.Dimension(450, 250));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        panQualitaet.add(wkSgPanFive, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1.0;
        panQualitaet.add(panSpace, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 10, 0);
        panQualitaet.add(wkSgPanEight, gridBagConstraints);

        tpMain.addTab("Qualitätsinformationen", panQualitaet);

        panMeld.setOpaque(false);
        panMeld.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panMeld.add(wkSgPanNine, gridBagConstraints);

        tpMain.addTab("Melderelevante Informationen", panMeld);

        panAusnahmen.setOpaque(false);
        panAusnahmen.setLayout(new java.awt.BorderLayout());

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 0);
        jPanel3.add(excemptionEditor, gridBagConstraints);

        roundedPanel1.setMinimumSize(new java.awt.Dimension(500, 282));
        roundedPanel1.setPreferredSize(new java.awt.Dimension(500, 282));
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

        panContrAusnahmen.setOpaque(false);
        panContrAusnahmen.setLayout(new java.awt.GridBagLayout());

        btnAddAusnahme.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddAusnahme.setText(org.openide.util.NbBundle.getMessage(
                WkSgEditor.class,
                "WkFgPanOne.btnAddImpactSrc.text"));                                                           // NOI18N
        btnAddAusnahme.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddAusnahmeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrAusnahmen.add(btnAddAusnahme, gridBagConstraints);

        btnRemAusnahme.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemAusnahme.setText(org.openide.util.NbBundle.getMessage(
                WkSgEditor.class,
                "WkFgPanOne.btnRemImpactSrc.text"));                                                              // NOI18N
        btnRemAusnahme.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemAusnahmeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrAusnahmen.add(btnRemAusnahme, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        roundedPanel1.add(panContrAusnahmen, gridBagConstraints);

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeadingAusnahme.setForeground(new java.awt.Color(255, 255, 255));
        lblHeadingAusnahme.setText(org.openide.util.NbBundle.getMessage(
                WkSgEditor.class,
                "WkFgPanOne.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeadingAusnahme);

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
        jPanel3.add(roundedPanel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jLabel1, gridBagConstraints);

        panAusnahmen.add(jPanel3, java.awt.BorderLayout.CENTER);

        tpMain.addTab("Ausnahmen", panAusnahmen);

        add(tpMain, java.awt.BorderLayout.PAGE_START);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddAusnahmeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddAusnahmeActionPerformed
        try {
            final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName("EXCEMPTION");
            final Collection<CidsBean> excemptionCollection = CidsBeanSupport.getBeanCollectionFromProperty(
                    cidsBean,
                    "ausnahmen");
            excemptionCollection.add(newBean);
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }                                                                                  //GEN-LAST:event_btnAddAusnahmeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstAusnahmenValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_lstAusnahmenValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object selObj = lstAusnahmen.getSelectedValue();
            if (selObj instanceof CidsBean) {
                excemptionEditor.setCidsBean((CidsBean)selObj);
            }
        }
    }                                                                                       //GEN-LAST:event_lstAusnahmenValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemAusnahmeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemAusnahmeActionPerformed
        final Object selection = lstAusnahmen.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Soll die Ausnahme wirklich gelöscht werden?",
                    "Ausnahme entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("ausnahmen");
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                  //GEN-LAST:event_btnRemAusnahmeActionPerformed

    @Override
    public void dispose() {
        wkSgPanOne.dispose();
        wkSgPanTwo.dispose();
        wkSgPanThree.dispose();
        wkSgPanFour.dispose();
        wkSgPanFive.dispose();
        excemptionEditor.dispose();
        wkSgPanSix.dispose();
        wkSgPanSeven.dispose();
        wkSgPanEight.dispose();
        wkSgPanNine.dispose();
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

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        // TODO ?
    }

    @Override
    public boolean prepareForSave() {
        if (cidsBean != null) {
            try {
                cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());
                cidsBean.setProperty("av_date", new java.sql.Timestamp(System.currentTimeMillis()));
            } catch (Exception ex) {
                log.error(ex, ex);
            }
        }
        return true;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }
}
