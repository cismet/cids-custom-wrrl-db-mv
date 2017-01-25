/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * WkFgPanOne.java
 *
 * Created on 04.08.2010, 13:44:05
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.QualityStatusCodeComparator;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkFgPanThirteen extends javax.swing.JPanel implements DisposableCidsBeanStore, ListSelectionListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkFgPanTen.class);

    //~ Instance fields --------------------------------------------------------

    private final MstTableModel model = new MstTableModel();
    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbChemicalstatusnitrat;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEco_stat;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEco_stat1;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEqsOthpl;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEqsPestic;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbIndPol;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbNonComp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jtMstTab1;
    private javax.swing.JLabel lblBemerk;
    private javax.swing.JLabel lblBemerkMst;
    private javax.swing.JLabel lblChemStat;
    private javax.swing.JLabel lblChemicalstatusnitrat;
    private javax.swing.JLabel lblEqsHm;
    private javax.swing.JLabel lblEqsHmMst;
    private javax.swing.JLabel lblEqsHmMstVal;
    private javax.swing.JLabel lblEqsIndPol;
    private javax.swing.JLabel lblEqsIndPolMst;
    private javax.swing.JLabel lblEqsOthpl;
    private javax.swing.JLabel lblEqsOthplMst;
    private javax.swing.JLabel lblEqsOthplVal;
    private javax.swing.JLabel lblEqsPestic;
    private javax.swing.JLabel lblEqsPesticMst;
    private javax.swing.JLabel lblEqsPesticMstVal;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblIndPolMstVal;
    private javax.swing.JLabel lblNonComp;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JLabel lblUEcoBemerkMst;
    private javax.swing.JLabel lblUEcoMst;
    private javax.swing.JLabel lblUEcoMstVal;
    private javax.swing.JLabel lblYesNo;
    private javax.swing.JLabel lblYesNoMst;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JLabel txtEqsHmMst;
    private javax.swing.JTextField txtEqsOthplBemerkung;
    private javax.swing.JLabel txtEqsOthplBemerkungMst;
    private javax.swing.JTextField txtEqsPesticBemerk;
    private javax.swing.JLabel txtEqsPesticBemerkMst;
    private javax.swing.JTextField txtHymoGkBemerkung1;
    private javax.swing.JTextField txtHymoGkBemerkung2;
    private javax.swing.JTextField txtHymoGkBemerkung3;
    private javax.swing.JTextField txtIndpolBemerk;
    private javax.swing.JLabel txtIndpolBemerkMst;
    private javax.swing.JTextField txtNonCompBemerk;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkFgPanThirteen() {
        initComponents();
        jScrollPane1.getViewport().setOpaque(false);
        jtMstTab1.getSelectionModel().addListSelectionListener(this);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panInfoContent = new javax.swing.JPanel();
        lblSpace = new javax.swing.JLabel();
        lblYesNo = new javax.swing.JLabel();
        lblBemerk = new javax.swing.JLabel();
        txtHymoGkBemerkung1 = new javax.swing.JTextField();
        cbEco_stat = new ScrollableComboBox(new QualityStatusCodeComparator());
        lblEqsHm = new javax.swing.JLabel();
        lblEqsPestic = new javax.swing.JLabel();
        cbEqsPestic = new ScrollableComboBox(new QualityStatusCodeComparator());
        txtEqsPesticBemerk = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblEqsHmMst = new javax.swing.JLabel();
        lblEqsPesticMst = new javax.swing.JLabel();
        lblEqsOthplMst = new javax.swing.JLabel();
        lblEqsIndPolMst = new javax.swing.JLabel();
        lblUEcoMst = new javax.swing.JLabel();
        lblEqsHmMstVal = new javax.swing.JLabel();
        lblEqsPesticMstVal = new javax.swing.JLabel();
        lblEqsOthplVal = new javax.swing.JLabel();
        lblIndPolMstVal = new javax.swing.JLabel();
        lblUEcoMstVal = new javax.swing.JLabel();
        txtEqsHmMst = new javax.swing.JLabel();
        txtEqsPesticBemerkMst = new javax.swing.JLabel();
        txtEqsOthplBemerkungMst = new javax.swing.JLabel();
        txtIndpolBemerkMst = new javax.swing.JLabel();
        lblUEcoBemerkMst = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblYesNoMst = new javax.swing.JLabel();
        lblBemerkMst = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtMstTab1 = new javax.swing.JTable();
        lblEqsIndPol = new javax.swing.JLabel();
        cbIndPol = new ScrollableComboBox(new QualityStatusCodeComparator());
        txtIndpolBemerk = new javax.swing.JTextField();
        lblEqsOthpl = new javax.swing.JLabel();
        cbEqsOthpl = new ScrollableComboBox(new QualityStatusCodeComparator());
        txtEqsOthplBemerkung = new javax.swing.JTextField();
        lblNonComp = new javax.swing.JLabel();
        cbNonComp = new ScrollableComboBox();
        txtNonCompBemerk = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        lblChemStat = new javax.swing.JLabel();
        cbEco_stat1 = new ScrollableComboBox(new QualityStatusCodeComparator());
        txtHymoGkBemerkung2 = new javax.swing.JTextField();
        txtHymoGkBemerkung3 = new javax.swing.JTextField();
        lblChemicalstatusnitrat = new javax.swing.JLabel();
        cbChemicalstatusnitrat = new ScrollableComboBox();

        setMinimumSize(new java.awt.Dimension(910, 650));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(910, 650));
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        panInfoContent.setMaximumSize(new java.awt.Dimension(888, 400));
        panInfoContent.setMinimumSize(new java.awt.Dimension(888, 400));
        panInfoContent.setOpaque(false);
        panInfoContent.setPreferredSize(new java.awt.Dimension(888, 400));
        panInfoContent.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(lblSpace, gridBagConstraints);

        lblYesNo.setText(org.openide.util.NbBundle.getMessage(WkFgPanThirteen.class, "WkFgPanThirteen.lblYesNo.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblYesNo, gridBagConstraints);

        lblBemerk.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblBemerk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblBemerk, gridBagConstraints);

        txtHymoGkBemerkung1.setMinimumSize(new java.awt.Dimension(300, 20));
        txtHymoGkBemerkung1.setPreferredSize(new java.awt.Dimension(300, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_hm_bemerkung}"),
                txtHymoGkBemerkung1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtHymoGkBemerkung1, gridBagConstraints);

        cbEco_stat.setMinimumSize(new java.awt.Dimension(150, 20));
        cbEco_stat.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_hm}"),
                cbEco_stat,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbEco_stat.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbEco_statActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEco_stat, gridBagConstraints);

        lblEqsHm.setText(org.openide.util.NbBundle.getMessage(WkFgPanThirteen.class, "WkFgPanThirteen.EqsHm.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblEqsHm, gridBagConstraints);

        lblEqsPestic.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblEqsPestic.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblEqsPestic, gridBagConstraints);

        cbEqsPestic.setMaximumSize(new java.awt.Dimension(200, 25));
        cbEqsPestic.setMinimumSize(new java.awt.Dimension(150, 20));
        cbEqsPestic.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_pestic}"),
                cbEqsPestic,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEqsPestic, gridBagConstraints);

        txtEqsPesticBemerk.setMinimumSize(new java.awt.Dimension(300, 20));
        txtEqsPesticBemerk.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_pestic_bemerkung}"),
                txtEqsPesticBemerk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtEqsPesticBemerk, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblEqsHmMst.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblEqsHmMst.text")); // NOI18N
        lblEqsHmMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblEqsHmMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblEqsHmMst, gridBagConstraints);

        lblEqsPesticMst.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblEqsPesticMst.text")); // NOI18N
        lblEqsPesticMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblEqsPesticMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblEqsPesticMst, gridBagConstraints);

        lblEqsOthplMst.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblEqsOthplMst.text")); // NOI18N
        lblEqsOthplMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblEqsOthplMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblEqsOthplMst, gridBagConstraints);

        lblEqsIndPolMst.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblEqsIndPolMst.text")); // NOI18N
        lblEqsIndPolMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblEqsIndPolMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblEqsIndPolMst, gridBagConstraints);

        lblUEcoMst.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblNonCompMst.text")); // NOI18N
        lblUEcoMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblUEcoMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblUEcoMst, gridBagConstraints);

        lblEqsHmMstVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblEqsHmMstVal.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblEqsHmMstVal, gridBagConstraints);

        lblEqsPesticMstVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblEqsPesticMstVal.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblEqsPesticMstVal, gridBagConstraints);

        lblEqsOthplVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblEqsOthplVal.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblEqsOthplVal, gridBagConstraints);

        lblIndPolMstVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblIndPolMstVal.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblIndPolMstVal, gridBagConstraints);

        lblUEcoMstVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblUEcoMstVal.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblUEcoMstVal, gridBagConstraints);

        txtEqsHmMst.setMinimumSize(new java.awt.Dimension(330, 20));
        txtEqsHmMst.setPreferredSize(new java.awt.Dimension(330, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtEqsHmMst, gridBagConstraints);

        txtEqsPesticBemerkMst.setMinimumSize(new java.awt.Dimension(330, 20));
        txtEqsPesticBemerkMst.setPreferredSize(new java.awt.Dimension(330, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtEqsPesticBemerkMst, gridBagConstraints);

        txtEqsOthplBemerkungMst.setMinimumSize(new java.awt.Dimension(330, 20));
        txtEqsOthplBemerkungMst.setPreferredSize(new java.awt.Dimension(330, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtEqsOthplBemerkungMst, gridBagConstraints);

        txtIndpolBemerkMst.setMinimumSize(new java.awt.Dimension(330, 20));
        txtIndpolBemerkMst.setPreferredSize(new java.awt.Dimension(330, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtIndpolBemerkMst, gridBagConstraints);

        lblUEcoBemerkMst.setMinimumSize(new java.awt.Dimension(330, 20));
        lblUEcoBemerkMst.setPreferredSize(new java.awt.Dimension(330, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblUEcoBemerkMst, gridBagConstraints);

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        lblYesNoMst.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblYesNoMst.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblYesNoMst.text")); // NOI18N
        lblYesNoMst.setMinimumSize(new java.awt.Dimension(200, 20));
        lblYesNoMst.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblYesNoMst, gridBagConstraints);

        lblBemerkMst.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBemerkMst.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblBemerkMst.text")); // NOI18N
        lblBemerkMst.setMinimumSize(new java.awt.Dimension(330, 20));
        lblBemerkMst.setPreferredSize(new java.awt.Dimension(330, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblBemerkMst, gridBagConstraints);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(800, 170));
        jScrollPane3.setOpaque(false);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(900, 170));

        jtMstTab1.setModel(model);
        jScrollPane3.setViewportView(jtMstTab1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        jPanel1.add(jScrollPane3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(jPanel1, gridBagConstraints);

        lblEqsIndPol.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblEqsIndpol.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblEqsIndPol, gridBagConstraints);

        cbIndPol.setMaximumSize(new java.awt.Dimension(200, 25));
        cbIndPol.setMinimumSize(new java.awt.Dimension(150, 20));
        cbIndPol.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_indpol}"),
                cbIndPol,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbIndPol, gridBagConstraints);

        txtIndpolBemerk.setMinimumSize(new java.awt.Dimension(300, 20));
        txtIndpolBemerk.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_indpol_bemerkung}"),
                txtIndpolBemerk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtIndpolBemerk, gridBagConstraints);

        lblEqsOthpl.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblEqsOthpl.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblEqsOthpl, gridBagConstraints);

        cbEqsOthpl.setMaximumSize(new java.awt.Dimension(200, 25));
        cbEqsOthpl.setMinimumSize(new java.awt.Dimension(150, 20));
        cbEqsOthpl.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_othpl}"),
                cbEqsOthpl,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEqsOthpl, gridBagConstraints);

        txtEqsOthplBemerkung.setMinimumSize(new java.awt.Dimension(300, 20));
        txtEqsOthplBemerkung.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_othpl_bemerkung}"),
                txtEqsOthplBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtEqsOthplBemerkung, gridBagConstraints);

        lblNonComp.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblNonComp.text"));     // NOI18N
        lblNonComp.setToolTipText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgThirteen.lblNonComp.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblNonComp, gridBagConstraints);

        cbNonComp.setMaximumSize(new java.awt.Dimension(200, 25));
        cbNonComp.setMinimumSize(new java.awt.Dimension(150, 20));
        cbNonComp.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.non_comp}"),
                cbNonComp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbNonComp, gridBagConstraints);

        txtNonCompBemerk.setMinimumSize(new java.awt.Dimension(300, 20));
        txtNonCompBemerk.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.non_comp_bemerkung}"),
                txtNonCompBemerk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtNonCompBemerk, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        panInfoContent.add(jSeparator1, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblChemStat.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblChemStat.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        jPanel3.add(lblChemStat, gridBagConstraints);

        cbEco_stat1.setMinimumSize(new java.awt.Dimension(150, 20));
        cbEco_stat1.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.chem_stat}"),
                cbEco_stat1,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbEco_stat1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbEco_stat1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel3.add(cbEco_stat1, gridBagConstraints);

        txtHymoGkBemerkung2.setMinimumSize(new java.awt.Dimension(100, 20));
        txtHymoGkBemerkung2.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.chem_stat_jahr}"),
                txtHymoGkBemerkung2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel3.add(txtHymoGkBemerkung2, gridBagConstraints);

        txtHymoGkBemerkung3.setMinimumSize(new java.awt.Dimension(200, 20));
        txtHymoGkBemerkung3.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.chem_stat_bemerkung}"),
                txtHymoGkBemerkung3,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel3.add(txtHymoGkBemerkung3, gridBagConstraints);

        lblChemicalstatusnitrat.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblChemicalstatusnitrat.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel3.add(lblChemicalstatusnitrat, gridBagConstraints);

        cbChemicalstatusnitrat.setMinimumSize(new java.awt.Dimension(150, 20));
        cbChemicalstatusnitrat.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.chemicalstatusnitrat}"),
                cbChemicalstatusnitrat,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbChemicalstatusnitrat.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbChemicalstatusnitratActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        jPanel3.add(cbChemicalstatusnitrat, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panInfoContent.add(jPanel3, gridBagConstraints);

        jScrollPane1.setViewportView(panInfoContent);

        panInfo.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbEco_statActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbEco_statActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbEco_statActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbEco_stat1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbEco_stat1ActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbEco_stat1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbChemicalstatusnitratActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbChemicalstatusnitratActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbChemicalstatusnitratActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        if (cidsBean != null) {
            this.cidsBean = cidsBean;
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                this.cidsBean);
            model.fireTableDataChanged();

            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        model.refreshData(cidsBean);
                    }
                }).start();
            bindingGroup.bind();
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (jtMstTab1.getSelectedRow() > -1) {
                final CidsBean sbean = model.getData().get(jtMstTab1.getSelectedRow());
                setMst(sbean, lblEqsHmMstVal, "u_schwermetalle", txtEqsHmMst, "u_schwermetalle_welche");
                setMst(sbean, lblEqsPesticMstVal, "u_psm", txtEqsPesticBemerkMst, "u_psm_welche");
                setMst(sbean, lblIndPolMstVal, "u_ind_stoffe", txtIndpolBemerkMst, "u_ind_stoffe_welche");
                setMst(sbean, lblEqsOthplVal, "u_andere_stoffe", txtEqsOthplBemerkungMst, "u_andere_stoffe_welche");
                setMst(sbean, lblUEcoMstVal, "u_eco_stoffe", lblUEcoBemerkMst, "u_eco_stoffe_welche");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  sbean    DOCUMENT ME!
     * @param  lblVal   DOCUMENT ME!
     * @param  valName  DOCUMENT ME!
     * @param  lbl      DOCUMENT ME!
     * @param  name     DOCUMENT ME!
     */
    private void setMst(final CidsBean sbean,
            final JLabel lblVal,
            final String valName,
            final JLabel lbl,
            final String name) {
        Object val = sbean.getProperty(valName);

        if (val != null) {
            lblVal.setText(((Boolean)val).booleanValue() ? "ja" : "nein");
        } else {
            lblVal.setText(CidsBeanSupport.FIELD_NOT_SET);
        }

        val = sbean.getProperty(name);

        if (val != null) {
            lbl.setText(val.toString());
        } else {
            lbl.setText(CidsBeanSupport.FIELD_NOT_SET);
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class MstTableModel extends AbstractTableModel {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "chemie_mst_messungen");

        //~ Instance fields ----------------------------------------------------

        private String[][] header = {
                { "MST", "messstelle.messstelle" },            // NOI18N
                { "WK", "messstelle.wk_fg.wk_k" },             // NOI18N
                { "Jahr", "messjahr" },                        // NOI18N
                { "Ü SM?", "u_schwermetalle" },                // NOI18N
                { "Ü PSM?", "u_psm" },                         // NOI18N
                { "Ü IndStoffe?", "u_ind_stoffe" },            // NOI18N
                { "Ü Andere PrioStoffe?", "u_andere_stoffe" }, // NOI18N
                { "Ü gefährl Prio?", "u_eco_stoffe" }          // NOI18N
            };
        private List<CidsBean> data = new Vector<CidsBean>();
        private Boolean isInitialised = false;

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            if (!isInitialised) {
                return 1;
            } else {
                return data.size();
            }
        }

        @Override
        public int getColumnCount() {
            return header.length;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            if (columnIndex < header.length) {
                return header[columnIndex][0];
            } else {
                return "";
            }
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return Object.class;
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            if (!isInitialised) {
                return "lade ...";
            } else if ((rowIndex < data.size()) && (columnIndex < header.length)) {
                final Object value = data.get(rowIndex).getProperty(header[columnIndex][1]);
                if (value != null) {
                    if (value instanceof CidsBean) {
                        return String.valueOf(((CidsBean)value).getProperty("name")); // NOI18N
                    } else if (value instanceof Boolean) {
                        return (((Boolean)value).booleanValue() ? "Ja" : "Nein");
                    } else {
                        return String.valueOf(value);
                    }
                } else {
                    return "-";                                                       // NOI18N
                }
            } else {
                return "";                                                            // NOI18N
            }
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            // nothing to do, because it is not allowed to modify columns
        }

        /**
         * DOCUMENT ME!
         *
         * @param  cidsBean  DOCUMENT ME!
         */
        public void refreshData(final CidsBean cidsBean) {
            try {
                data.clear();
                String query = "select " + MC.getID() + ", m." + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
                query += " m, chemie_mst_stammdaten s";                                                             // NOI18N
                query += " WHERE m.messstelle = s.id AND s.wk_fg = " + cidsBean.getProperty("id");                  // NOI18N
                query += " order by messjahr desc";                                                                 // NOI18N

                final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

                for (final MetaObject mo : metaObjects) {
                    data.add(mo.getBean());
                }

                isInitialised = true;
                fireTableDataChanged();
            } catch (final ConnectionException e) {
                LOG.error("Error while trying to receive measurements.", e); // NOI18N
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public List<CidsBean> getData() {
            return data;
        }

        /**
         * DOCUMENT ME!
         */
        public void clearModel() {
            data.clear();
        }
    }
}
