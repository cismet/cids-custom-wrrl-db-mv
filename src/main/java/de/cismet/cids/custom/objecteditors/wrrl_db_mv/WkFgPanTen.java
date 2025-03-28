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
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.IntegerConverter;
import de.cismet.cids.custom.wrrl_db_mv.util.QualityStatusCodeComparator;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.SaveVetoable;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkFgPanTen extends javax.swing.JPanel implements DisposableCidsBeanStore, SaveVetoable {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkFgPanTen.class);
    private static ConnectionContext CC = ConnectionContext.create(
            AbstractConnectionContext.Category.EDITOR,
            "WkFgPanTen");

    private static final MetaClass MC_STAMM = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "bio_mst_stammdaten",
            CC);

    //~ Instance fields --------------------------------------------------------

    private MetaClass mc = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "bio_mst_messungen", CC);
    private String[][] header = {
            { "MST", "messstelle.messstelle" },       // NOI18N
            { "WK", "messstelle.wk_fg.wk_k" },        // NOI18N
            { "Jahr", "messjahr" },                   // NOI18N
            { "GK MZB", "gk_mzb_gesamt" },            // NOI18N
            { "GK MP", "gk_mp_gesamt" },              // NOI18N
            { "GK PhyP", "gk_phytoplankton_gesamt" }, // NOI18N
            { "GK Fische", "gk_fische_gesamt" }       // NOI18N
        };
    private final MstTableModel model = new MstTableModel(mc, header);

    private CidsBean cidsBean;
    private boolean readOnly = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbBen_Inv;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbConfidence;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbFisch;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMacPhyto;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPhyto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbFishVorb;
    private javax.swing.JButton jbMacPhytoVorb;
    private javax.swing.JButton jbMzbMst;
    private javax.swing.JButton jbMzbMst1;
    private javax.swing.JButton jbMzbMst2;
    private javax.swing.JButton jbMzbMst3;
    private javax.swing.JButton jbMzbVorb;
    private javax.swing.JButton jbPhytoVorb;
    private javax.swing.JTable jtMstTab1;
    private javax.swing.JLabel lblBenInv;
    private javax.swing.JLabel lblBioGkBemerkung;
    private javax.swing.JLabel lblBioGkJahr;
    private javax.swing.JLabel lblFish;
    private javax.swing.JLabel lblGK;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblMacPhyto;
    private javax.swing.JLabel lblPhyto;
    private javax.swing.JLabel lblPhytoGkMst;
    private javax.swing.JLabel lblPlaus;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JTextField txtBenInvBemerk;
    private javax.swing.JTextField txtBenInvGkJahr;
    private javax.swing.JTextField txtBenInvMst;
    private javax.swing.JTextField txtFishBemerk;
    private javax.swing.JTextField txtFishGkJahr;
    private javax.swing.JTextField txtFishGkMst;
    private javax.swing.JTextField txtMacPhytoBemerk;
    private javax.swing.JTextField txtMacPhytoGkJahr;
    private javax.swing.JTextField txtMacPhytoGkMst;
    private javax.swing.JTextField txtPhytoBemerk;
    private javax.swing.JTextField txtPhytoGkJahr;
    private javax.swing.JTextField txtPhytoGkMst;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WkFgPanTen object.
     */
    public WkFgPanTen() {
        this(true);
    }

    /**
     * Creates new form WkFgPanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkFgPanTen(final boolean readOnly) {
        initComponents();
        this.readOnly = readOnly;

        RendererTools.makeReadOnly(cbConfidence);

        if (readOnly) {
            RendererTools.makeReadOnly(txtBenInvBemerk);
            RendererTools.makeReadOnly(txtBenInvGkJahr);
            RendererTools.makeReadOnly(txtBenInvMst);
            RendererTools.makeReadOnly(txtFishBemerk);
            RendererTools.makeReadOnly(txtFishGkJahr);
            RendererTools.makeReadOnly(txtFishGkMst);
            RendererTools.makeReadOnly(txtMacPhytoBemerk);
            RendererTools.makeReadOnly(txtMacPhytoGkJahr);
            RendererTools.makeReadOnly(txtMacPhytoGkMst);
            RendererTools.makeReadOnly(txtPhytoBemerk);
            RendererTools.makeReadOnly(txtPhytoGkJahr);
            RendererTools.makeReadOnly(txtPhytoGkMst);
            RendererTools.makeReadOnly(cbBen_Inv);
            RendererTools.makeReadOnly(cbFisch);
            RendererTools.makeReadOnly(cbMacPhyto);
            RendererTools.makeReadOnly(cbPhyto);
            txtBenInvMst.setForeground(Color.BLUE);
            txtFishGkMst.setForeground(Color.BLUE);
            txtMacPhytoGkMst.setForeground(Color.BLUE);
            txtPhytoGkMst.setForeground(Color.BLUE);
            txtBenInvMst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            txtFishGkMst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            txtMacPhytoGkMst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            txtPhytoGkMst.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            jbFishVorb.setVisible(false);
            jbMacPhytoVorb.setVisible(false);
            jbMzbVorb.setVisible(false);
            jbPhytoVorb.setVisible(false);
            jtMstTab1.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(final JTable table,
                            final Object value,
                            final boolean isSelected,
                            final boolean hasFocus,
                            final int row,
                            final int column) {
                        final Component c = super.getTableCellRendererComponent(
                                table,
                                value,
                                isSelected,
                                hasFocus,
                                row,
                                column);

                        c.setForeground(Color.BLUE);

                        return c;
                    }
                });

            jtMstTab1.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        int row = jtMstTab1.rowAtPoint(e.getPoint());

                        if (row != -1) {
                            int col = jtMstTab1.getTableHeader().getColumnModel().getColumnIndexAtX(e.getX());
                            col = jtMstTab1.convertColumnIndexToModel(col);
                            final String columnName = model.getColumnName(col);
                            row = jtMstTab1.convertRowIndexToModel(row);
                            final Object value = model.getValueAt(row, col);

                            if (columnName.equalsIgnoreCase("MST") && (row < model.getData().size())) {
                                final CidsBean mstBean = model.getData().get(row);

                                if ((mstBean != null) && (mstBean.getProperty("messstelle") instanceof CidsBean)) {
                                    ComponentRegistry.getRegistry()
                                            .getDescriptionPane()
                                            .gotoMetaObjectNode(
                                                new MetaObjectNode((CidsBean)mstBean.getProperty("messstelle")),
                                                false);
                                }
                            }
                        }
                    }
                });
        }
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
        panInfoContent = new javax.swing.JPanel();
        lblBioGkJahr = new javax.swing.JLabel();
        lblBioGkBemerkung = new javax.swing.JLabel();
        lblGK = new javax.swing.JLabel();
        cbConfidence = new ScrollableComboBox();
        txtPhytoGkJahr = new javax.swing.JTextField();
        cbPhyto = new ScrollableComboBox(new QualityStatusCodeComparator(), true);
        txtPhytoGkMst = new javax.swing.JTextField();
        lblPhyto = new javax.swing.JLabel();
        lblPhytoGkMst = new javax.swing.JLabel();
        txtPhytoBemerk = new javax.swing.JTextField();
        cbBen_Inv = new ScrollableComboBox(new QualityStatusCodeComparator(), true);
        lblBenInv = new javax.swing.JLabel();
        txtBenInvMst = new javax.swing.JTextField();
        txtBenInvGkJahr = new javax.swing.JTextField();
        txtBenInvBemerk = new javax.swing.JTextField();
        cbMacPhyto = new ScrollableComboBox(new QualityStatusCodeComparator(), true);
        lblMacPhyto = new javax.swing.JLabel();
        txtMacPhytoGkMst = new javax.swing.JTextField();
        txtMacPhytoGkJahr = new javax.swing.JTextField();
        txtMacPhytoBemerk = new javax.swing.JTextField();
        cbFisch = new ScrollableComboBox(new QualityStatusCodeComparator(), true);
        lblFish = new javax.swing.JLabel();
        txtFishGkMst = new javax.swing.JTextField();
        txtFishGkJahr = new javax.swing.JTextField();
        txtFishBemerk = new javax.swing.JTextField();
        jbMzbVorb = new javax.swing.JButton();
        jbMacPhytoVorb = new javax.swing.JButton();
        jbPhytoVorb = new javax.swing.JButton();
        jbFishVorb = new javax.swing.JButton();
        jbMzbMst = new javax.swing.JButton();
        jbMzbMst1 = new javax.swing.JButton();
        jbMzbMst2 = new javax.swing.JButton();
        jbMzbMst3 = new javax.swing.JButton();
        lblPlaus = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtMstTab1 = new javax.swing.JTable();

        setMinimumSize(new java.awt.Dimension(1150, 650));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1150, 650));
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setMaximumSize(new java.awt.Dimension(777, 400));
        panInfoContent.setMinimumSize(new java.awt.Dimension(777, 400));
        panInfoContent.setOpaque(false);
        panInfoContent.setPreferredSize(new java.awt.Dimension(777, 400));
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblBioGkJahr.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.lblBioGkJahr.text")); // NOI18N
        lblBioGkJahr.setMinimumSize(new java.awt.Dimension(40, 17));
        lblBioGkJahr.setPreferredSize(new java.awt.Dimension(40, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblBioGkJahr, gridBagConstraints);

        lblBioGkBemerkung.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanTen.class,
                "WkFgPanTen.lblBioGkBemerkung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblBioGkBemerkung, gridBagConstraints);

        lblGK.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanFive.lblGK.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblGK, gridBagConstraints);

        cbConfidence.setMinimumSize(new java.awt.Dimension(150, 20));
        cbConfidence.setPreferredSize(new java.awt.Dimension(150, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.confidence}"),
                cbConfidence,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbConfidence, gridBagConstraints);

        txtPhytoGkJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        txtPhytoGkJahr.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phyto_gk_jahr}"),
                txtPhytoGkJahr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("null");
        binding.setConverter(IntegerConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtPhytoGkJahr, gridBagConstraints);

        cbPhyto.setMaximumSize(new java.awt.Dimension(200, 20));
        cbPhyto.setMinimumSize(new java.awt.Dimension(200, 20));
        cbPhyto.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phyto}"),
                cbPhyto,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbPhyto, gridBagConstraints);

        txtPhytoGkMst.setMinimumSize(new java.awt.Dimension(100, 20));
        txtPhytoGkMst.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phyto_gk_mst}"),
                txtPhytoGkMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtPhytoGkMst.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    txtPhytoGkMstMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtPhytoGkMst, gridBagConstraints);

        lblPhyto.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.lblPhyto.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblPhyto, gridBagConstraints);

        lblPhytoGkMst.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.lblPhytoGkMst.text")); // NOI18N
        lblPhytoGkMst.setMinimumSize(new java.awt.Dimension(80, 20));
        lblPhytoGkMst.setPreferredSize(new java.awt.Dimension(80, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblPhytoGkMst, gridBagConstraints);

        txtPhytoBemerk.setMinimumSize(new java.awt.Dimension(300, 20));
        txtPhytoBemerk.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phyto_gk_bemerkung}"),
                txtPhytoBemerk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phyto_gk_bemerkung}"),
                txtPhytoBemerk,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtPhytoBemerk, gridBagConstraints);

        cbBen_Inv.setMaximumSize(new java.awt.Dimension(200, 20));
        cbBen_Inv.setMinimumSize(new java.awt.Dimension(200, 20));
        cbBen_Inv.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ben_inv}"),
                cbBen_Inv,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbBen_Inv, gridBagConstraints);

        lblBenInv.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.lblBenInv.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblBenInv, gridBagConstraints);

        txtBenInvMst.setMinimumSize(new java.awt.Dimension(100, 20));
        txtBenInvMst.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ben_inv_gk_mst}"),
                txtBenInvMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtBenInvMst.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    txtBenInvMstMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtBenInvMst, gridBagConstraints);

        txtBenInvGkJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        txtBenInvGkJahr.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ben_inv_gk_jahr}"),
                txtBenInvGkJahr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("null");
        binding.setConverter(IntegerConverter.getInstance());
        bindingGroup.addBinding(binding);

        txtBenInvGkJahr.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtBenInvGkJahrActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtBenInvGkJahr, gridBagConstraints);

        txtBenInvBemerk.setMinimumSize(new java.awt.Dimension(300, 20));
        txtBenInvBemerk.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ben_inv_gk_bemerkung}"),
                txtBenInvBemerk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ben_inv_gk_bemerkung}"),
                txtBenInvBemerk,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtBenInvBemerk, gridBagConstraints);

        cbMacPhyto.setMaximumSize(new java.awt.Dimension(200, 20));
        cbMacPhyto.setMinimumSize(new java.awt.Dimension(200, 20));
        cbMacPhyto.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mac_phyto}"),
                cbMacPhyto,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbMacPhyto, gridBagConstraints);

        lblMacPhyto.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.lblMacPhytoGk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblMacPhyto, gridBagConstraints);

        txtMacPhytoGkMst.setMinimumSize(new java.awt.Dimension(100, 20));
        txtMacPhytoGkMst.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mac_phyto_gk_mst}"),
                txtMacPhytoGkMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtMacPhytoGkMst.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    txtMacPhytoGkMstMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtMacPhytoGkMst, gridBagConstraints);

        txtMacPhytoGkJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        txtMacPhytoGkJahr.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mac_phyto_gk_jahr}"),
                txtMacPhytoGkJahr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("null");
        binding.setConverter(IntegerConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtMacPhytoGkJahr, gridBagConstraints);

        txtMacPhytoBemerk.setMinimumSize(new java.awt.Dimension(300, 20));
        txtMacPhytoBemerk.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mac_phyto_gk_bemerkung}"),
                txtMacPhytoBemerk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mac_phyto_gk_bemerkung}"),
                txtMacPhytoBemerk,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtMacPhytoBemerk, gridBagConstraints);

        cbFisch.setMaximumSize(new java.awt.Dimension(200, 20));
        cbFisch.setMinimumSize(new java.awt.Dimension(200, 20));
        cbFisch.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fish}"),
                cbFisch,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbFisch, gridBagConstraints);

        lblFish.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.lblFish.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblFish, gridBagConstraints);

        txtFishGkMst.setMinimumSize(new java.awt.Dimension(100, 20));
        txtFishGkMst.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fish_gk_mst}"),
                txtFishGkMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtFishGkMst.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    txtFishGkMstMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtFishGkMst, gridBagConstraints);

        txtFishGkJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        txtFishGkJahr.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fish_gk_jahr}"),
                txtFishGkJahr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("null");
        binding.setConverter(IntegerConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtFishGkJahr, gridBagConstraints);

        txtFishBemerk.setMinimumSize(new java.awt.Dimension(300, 20));
        txtFishBemerk.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fish_gk_bemerkung}"),
                txtFishBemerk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fish_gk_bemerkung}"),
                txtFishBemerk,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtFishBemerk, gridBagConstraints);

        jbMzbVorb.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.jbMzbVorb.text")); // NOI18N
        jbMzbVorb.setMinimumSize(new java.awt.Dimension(95, 20));
        jbMzbVorb.setPreferredSize(new java.awt.Dimension(95, 20));
        jbMzbVorb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbMzbVorbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jbMzbVorb, gridBagConstraints);

        jbMacPhytoVorb.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.jbMzbVorb.text")); // NOI18N
        jbMacPhytoVorb.setMinimumSize(new java.awt.Dimension(95, 20));
        jbMacPhytoVorb.setPreferredSize(new java.awt.Dimension(95, 20));
        jbMacPhytoVorb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbMacPhytoVorbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jbMacPhytoVorb, gridBagConstraints);

        jbPhytoVorb.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.jbMzbVorb.text")); // NOI18N
        jbPhytoVorb.setMinimumSize(new java.awt.Dimension(95, 20));
        jbPhytoVorb.setPreferredSize(new java.awt.Dimension(95, 20));
        jbPhytoVorb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbPhytoVorbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jbPhytoVorb, gridBagConstraints);

        jbFishVorb.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.jbMzbVorb.text")); // NOI18N
        jbFishVorb.setMinimumSize(new java.awt.Dimension(95, 20));
        jbFishVorb.setPreferredSize(new java.awt.Dimension(95, 20));
        jbFishVorb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbFishVorbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jbFishVorb, gridBagConstraints);

        jbMzbMst.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.jbMzbMst.text")); // NOI18N
        jbMzbMst.setMinimumSize(new java.awt.Dimension(120, 20));
        jbMzbMst.setPreferredSize(new java.awt.Dimension(120, 20));
        jbMzbMst.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbMzbMstActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jbMzbMst, gridBagConstraints);

        jbMzbMst1.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.jbMzbMst.text")); // NOI18N
        jbMzbMst1.setMinimumSize(new java.awt.Dimension(120, 20));
        jbMzbMst1.setPreferredSize(new java.awt.Dimension(120, 20));
        jbMzbMst1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbMzbMst1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jbMzbMst1, gridBagConstraints);

        jbMzbMst2.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.jbMzbMst.text")); // NOI18N
        jbMzbMst2.setMinimumSize(new java.awt.Dimension(120, 20));
        jbMzbMst2.setPreferredSize(new java.awt.Dimension(120, 20));
        jbMzbMst2.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbMzbMst2ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jbMzbMst2, gridBagConstraints);

        jbMzbMst3.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.jbMzbMst.text")); // NOI18N
        jbMzbMst3.setMinimumSize(new java.awt.Dimension(120, 20));
        jbMzbMst3.setPreferredSize(new java.awt.Dimension(120, 20));
        jbMzbMst3.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbMzbMst3ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jbMzbMst3, gridBagConstraints);

        lblPlaus.setText(org.openide.util.NbBundle.getMessage(WkFgPanTen.class, "WkFgPanTen.lblPlaus.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblPlaus, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weightx = 1.0;
        panInfoContent.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weightx = 1.0;
        panInfoContent.add(jLabel2, gridBagConstraints);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(800, 240));
        jScrollPane3.setOpaque(false);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(800, 240));

        jtMstTab1.setModel(model);
        jScrollPane3.setViewportView(jtMstTab1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        panInfoContent.add(jScrollPane3, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtBenInvGkJahrActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtBenInvGkJahrActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtBenInvGkJahrActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbMzbMstActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbMzbMstActionPerformed
        new Thread(new Runnable() {

                @Override
                public void run() {
                    model.refreshData("gk_mzb_gesamt", cidsBean);     // NOI18N
                }
            }).start();
    }                                                                 //GEN-LAST:event_jbMzbMstActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbMzbMst1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbMzbMst1ActionPerformed
        new Thread(new Runnable() {

                @Override
                public void run() {
                    model.refreshData("gk_mp_gesamt", cidsBean);     // NOI18N
                }
            }).start();
    }                                                                //GEN-LAST:event_jbMzbMst1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbMzbMst2ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbMzbMst2ActionPerformed
        new Thread(new Runnable() {

                @Override
                public void run() {
                    model.refreshData("gk_phytoplankton_gesamt", cidsBean);     // NOI18N
                }
            }).start();
    }                                                                           //GEN-LAST:event_jbMzbMst2ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbMzbMst3ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbMzbMst3ActionPerformed
        new Thread(new Runnable() {

                @Override
                public void run() {
                    model.refreshData("gk_fische_gesamt", cidsBean);     // NOI18N
                }
            }).start();
    }                                                                    //GEN-LAST:event_jbMzbMst3ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbMzbVorbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbMzbVorbActionPerformed
        new Thread(new Runnable() {

                @Override
                public void run() {
                    final CidsBean measurement = getLatestMeasurement("gk_mzb_gesamt");
                    if (measurement != null) {
                        cbBen_Inv.setSelectedItem(measurement.getProperty("gk_mzb_gesamt"));
                        txtBenInvGkJahr.setText(String.valueOf(measurement.getProperty("messjahr")));
                        txtBenInvBemerk.setText(String.valueOf(measurement.getProperty("bemerkung_mzb")));
                        txtBenInvMst.setText(String.valueOf(measurement.getProperty("messstelle.messstelle")));
                    }
                }
            }).start();
    } //GEN-LAST:event_jbMzbVorbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbMacPhytoVorbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbMacPhytoVorbActionPerformed
        new Thread(new Runnable() {

                @Override
                public void run() {
                    final CidsBean measurement = getLatestMeasurement("gk_mp_gesamt");

                    if (measurement != null) {
                        cbMacPhyto.setSelectedItem(measurement.getProperty("gk_mp_gesamt"));
                        txtMacPhytoGkJahr.setText(String.valueOf(measurement.getProperty("messjahr")));
                        txtMacPhytoBemerk.setText(String.valueOf(measurement.getProperty("bemerkung_mp")));
                        txtMacPhytoGkMst.setText(String.valueOf(measurement.getProperty("messstelle.messstelle")));
                    }
                }
            }).start();
    } //GEN-LAST:event_jbMacPhytoVorbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbPhytoVorbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbPhytoVorbActionPerformed
        new Thread(new Runnable() {

                @Override
                public void run() {
                    final CidsBean measurement = getLatestMeasurement("gk_phytoplankton_gesamt");

                    if (measurement != null) {
                        cbPhyto.setSelectedItem(measurement.getProperty("gk_phytoplankton_gesamt"));
                        txtPhytoGkJahr.setText(String.valueOf(measurement.getProperty("messjahr")));
                        txtPhytoBemerk.setText(String.valueOf(measurement.getProperty("bemerkung_phypl")));
                        txtPhytoGkMst.setText(String.valueOf(measurement.getProperty("messstelle.messstelle")));
                    }
                }
            }).start();
    } //GEN-LAST:event_jbPhytoVorbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbFishVorbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbFishVorbActionPerformed
        new Thread(new Runnable() {

                @Override
                public void run() {
                    final CidsBean measurement = getLatestMeasurement("gk_fische_gesamt");

                    if (measurement != null) {
                        cbFisch.setSelectedItem(measurement.getProperty("gk_fische_gesamt"));
                        txtFishGkJahr.setText(String.valueOf(measurement.getProperty("messjahr")));
                        txtFishBemerk.setText(String.valueOf(measurement.getProperty("bemerkung_fische")));
                        txtFishGkMst.setText(String.valueOf(measurement.getProperty("messstelle.messstelle")));
                    }
                }
            }).start();
    } //GEN-LAST:event_jbFishVorbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtBenInvMstMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_txtBenInvMstMouseClicked
        if ((evt.getClickCount() == 1) && readOnly && (cidsBean.getProperty("ben_inv_gk_mst") instanceof String)
                    && !cidsBean.getProperty("ben_inv_gk_mst").equals("")) {
            try {
                String query = "select " + MC_STAMM.getID() + ", m." + MC_STAMM.getPrimaryKey() + " from "
                            + MC_STAMM.getTableName();                           // NOI18N
                query += " m";                                                   // NOI18N
                query += " WHERE m.messstelle = '" + (String)cidsBean.getProperty("ben_inv_gk_mst") + "'";

                final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0, CC);

                if ((metaObjects != null) && (metaObjects.length == 1)) {
                    ComponentRegistry.getRegistry()
                            .getDescriptionPane()
                            .gotoMetaObjectNode(new MetaObjectNode(metaObjects[0].getBean()), false);
                }
            } catch (Exception e) {
                LOG.error("Error while retrieving class", e);
            }
        }
    } //GEN-LAST:event_txtBenInvMstMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtMacPhytoGkMstMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_txtMacPhytoGkMstMouseClicked
        if ((evt.getClickCount() == 1) && readOnly && (cidsBean.getProperty("mac_phyto_gk_mst") instanceof String)
                    && !cidsBean.getProperty("mac_phyto_gk_mst").equals("")) {
            try {
                String query = "select " + MC_STAMM.getID() + ", m." + MC_STAMM.getPrimaryKey() + " from "
                            + MC_STAMM.getTableName();                               // NOI18N
                query += " m";                                                       // NOI18N
                query += " WHERE m.messstelle = '" + (String)cidsBean.getProperty("mac_phyto_gk_mst") + "'";

                final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0, CC);

                if ((metaObjects != null) && (metaObjects.length == 1)) {
                    ComponentRegistry.getRegistry()
                            .getDescriptionPane()
                            .gotoMetaObjectNode(new MetaObjectNode(metaObjects[0].getBean()), false);
                }
            } catch (Exception e) {
                LOG.error("Error while retrieving class", e);
            }
        }
    } //GEN-LAST:event_txtMacPhytoGkMstMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtPhytoGkMstMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_txtPhytoGkMstMouseClicked
        if ((evt.getClickCount() == 1) && readOnly && (cidsBean.getProperty("phyto_gk_mst") instanceof String)
                    && !cidsBean.getProperty("phyto_gk_mst").equals("")) {
            try {
                String query = "select " + MC_STAMM.getID() + ", m." + MC_STAMM.getPrimaryKey() + " from "
                            + MC_STAMM.getTableName();                            // NOI18N
                query += " m";                                                    // NOI18N
                query += " WHERE m.messstelle = '" + (String)cidsBean.getProperty("phyto_gk_mst") + "'";

                final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0, CC);

                if ((metaObjects != null) && (metaObjects.length == 1)) {
                    ComponentRegistry.getRegistry()
                            .getDescriptionPane()
                            .gotoMetaObjectNode(new MetaObjectNode(metaObjects[0].getBean()), false);
                }
            } catch (Exception e) {
                LOG.error("Error while retrieving class", e);
            }
        }
    } //GEN-LAST:event_txtPhytoGkMstMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtFishGkMstMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_txtFishGkMstMouseClicked
        if ((evt.getClickCount() == 1) && readOnly && (cidsBean.getProperty("fish_gk_mst") instanceof String)
                    && !cidsBean.getProperty("fish_gk_mst").equals("")) {
            try {
                String query = "select " + MC_STAMM.getID() + ", m." + MC_STAMM.getPrimaryKey() + " from "
                            + MC_STAMM.getTableName();                           // NOI18N
                query += " m";                                                   // NOI18N
                query += " WHERE m.messstelle = '" + (String)cidsBean.getProperty("fish_gk_mst") + "'";

                final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0, CC);

                if ((metaObjects != null) && (metaObjects.length == 1)) {
                    ComponentRegistry.getRegistry()
                            .getDescriptionPane()
                            .gotoMetaObjectNode(new MetaObjectNode(metaObjects[0].getBean()), false);
                }
            } catch (Exception e) {
                LOG.error("Error while retrieving class", e);
            }
        }
    } //GEN-LAST:event_txtFishGkMstMouseClicked

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
            bindingGroup.bind();
        }
    }

    @Override
    public void dispose() {
        model.clearModel();
        bindingGroup.unbind();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   filedName  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getLatestMeasurement(final String filedName) {
        final List<CidsBean> measurements = model.getMeasurements(filedName, true, cidsBean);

        if ((measurements != null) && (measurements.size() > 0)) {
            return measurements.get(0);
        } else {
            JOptionPane.showMessageDialog(
                StaticSwingTools.getParentFrame(this),
                "Es wurden keine passenden Messergebnisse gefunden.",
                "keine Messergebnisse",
                JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    @Override
    public boolean isOkForSaving() {
        if (txtBenInvMst.getText().equals("") || txtFishGkMst.getText().equals("")
                    || txtMacPhytoGkMst.getText().equals("") || txtPhytoGkMst.getText().equals("")) {
            JOptionPane.showMessageDialog(
                this,
                "Es wurden nicht alle Messtellen belegt.",
                "Messtellen fehlen",
                JOptionPane.WARNING_MESSAGE);

            return false;
        }

        return true;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class MstTableModel extends AbstractTableModel {

        //~ Instance fields ----------------------------------------------------

        private MetaClass mc;
        private String[][] header;
        private List<CidsBean> data = new Vector<CidsBean>();
        private boolean isLoading = false;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new MstTableModel object.
         *
         * @param  mc      DOCUMENT ME!
         * @param  header  DOCUMENT ME!
         */
        public MstTableModel(final MetaClass mc, final String[][] header) {
            this.mc = mc;
            this.header = header;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            if (isLoading) {
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
            if (isLoading) {
                return "lade ...";
            } else if ((rowIndex < data.size()) && (columnIndex < header.length)) {
                final Object value = data.get(rowIndex).getProperty(header[columnIndex][1]);
                if (value != null) {
                    if (value instanceof CidsBean) {
                        return String.valueOf(((CidsBean)value).getProperty("name")); // NOI18N
                    } else {
                        return String.valueOf(value);
                    }
                } else {
                    return "keine Bewertung";                                         // NOI18N
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
         * @param  fieldName  DOCUMENT ME!
         * @param  cidsBean   DOCUMENT ME!
         */
        public void refreshData(final String fieldName, final CidsBean cidsBean) {
            isLoading = true;
            fireTableDataChanged();

            final List<CidsBean> measurements = getMeasurements(fieldName, false, cidsBean);
            data.clear();
            if (measurements != null) {
                data = measurements;
            }

            isLoading = false;
            fireTableDataChanged();
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
         *
         * @param   fieldName   DOCUMENT ME!
         * @param   onlyLatest  DOCUMENT ME!
         * @param   cidsBean    DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public List<CidsBean> getMeasurements(final String fieldName,
                final boolean onlyLatest,
                final CidsBean cidsBean) {
            final List<CidsBean> measurements = new Vector<CidsBean>();

            try {
                String query = "select " + mc.getID() + ", m." + mc.getPrimaryKey() + " from " + mc.getTableName(); // NOI18N
                query += " m, bio_mst_stammdaten s";                                                                // NOI18N
                query += " WHERE m.messstelle = s.id AND s.wk_fg = " + cidsBean.getProperty("id");                  // NOI18N
                query += " AND m." + fieldName + " is not null order by messjahr desc";                             // NOI18N

                if (onlyLatest) {
                    query += " limit 1";
                }

                final MetaObject[] MetaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

                for (final MetaObject mo : MetaObjects) {
                    measurements.add(mo.getBean());
                }

                return measurements;
            } catch (final ConnectionException e) {
                LOG.error("Error while trying to receive measurements.", e); // NOI18N
            }

            return null;
        }

        /**
         * DOCUMENT ME!
         */
        public void clearModel() {
            data.clear();
        }
    }
}
