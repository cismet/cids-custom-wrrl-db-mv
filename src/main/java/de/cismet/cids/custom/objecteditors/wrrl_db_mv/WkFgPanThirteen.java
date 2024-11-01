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

import Sirius.navigator.tools.CacheException;
import Sirius.navigator.tools.MetaObjectCache;
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;

import org.jdesktop.swingx.JXTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.QualityStatusCodeComparator;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.tools.BrowserLauncher;

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
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEco_stat1;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPropOhne;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jtMstTab1;
    private javax.swing.JLabel lblChemGesStat;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblPropOhne;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUQN8;
    private javax.swing.JPanel panAnl8;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JTextField txtHymoGkBemerkung3;
    private javax.swing.JTextField txtPropOhne;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WkFgPanThirteen object.
     */
    public WkFgPanThirteen() {
        this(false);
    }

    /**
     * Creates new form WkFgPanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkFgPanThirteen(final boolean readOnly) {
        initComponents();

        if (readOnly) {
//            RendererTools.makeReadOnly(txtEqsOthplBemerkung);
            RendererTools.makeReadOnly(txtPropOhne);
//            RendererTools.makeReadOnly(txtHymoGkBemerkung2);
            RendererTools.makeReadOnly(txtHymoGkBemerkung3);
//            RendererTools.makeReadOnly(txtIndpolBemerk);
//            RendererTools.makeReadOnly(txtNonCompBemerk);
//            RendererTools.makeReadOnly(cbChemicalstatusnitrat);
            RendererTools.makeReadOnly(cbPropOhne);
            RendererTools.makeReadOnly(cbEco_stat1);
//            RendererTools.makeReadOnly(cbEqsOthpl);
//            RendererTools.makeReadOnly(cbIndPol);
//            RendererTools.makeReadOnly(cbNonComp);
        }

        jScrollPane1.getViewport().setOpaque(false);
        jtMstTab1.getSelectionModel().addListSelectionListener(this);

        if (readOnly) {
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
        jScrollPane1 = new javax.swing.JScrollPane();
        panInfoContent = new javax.swing.JPanel();
        lblSpace = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        txtPropOhne = new javax.swing.JTextField();
        cbPropOhne = new ScrollableComboBox(new QualityStatusCodeComparator(), true);
        lblPropOhne = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtMstTab1 = new javax.swing.JTable();
        lblUQN8 = new javax.swing.JLabel();
        panAnl8 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        lblChemGesStat = new javax.swing.JLabel();
        cbEco_stat1 = new ScrollableComboBox(new QualityStatusCodeComparator(), true);
        txtHymoGkBemerkung3 = new javax.swing.JTextField();

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

        lblTitle.setText(org.openide.util.NbBundle.getMessage(WkFgPanThirteen.class, "WkFgPanThirteen.lblTitle.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblTitle, gridBagConstraints);

        txtPropOhne.setMinimumSize(new java.awt.Dimension(300, 20));
        txtPropOhne.setPreferredSize(new java.awt.Dimension(300, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.chem_uqn_bemerkung}"),
                txtPropOhne,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtPropOhne, gridBagConstraints);

        cbPropOhne.setMinimumSize(new java.awt.Dimension(150, 20));
        cbPropOhne.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.cstnu}"),
                cbPropOhne,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        cbPropOhne.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbPropOhneActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbPropOhne, gridBagConstraints);

        lblPropOhne.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblPropOhne.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblPropOhne, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

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
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        jPanel1.add(jScrollPane3, gridBagConstraints);

        lblUQN8.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "ChemieMstMessungenEditor.lblUQN6.text",
                new Object[] {})); // NOI18N
        lblUQN8.setToolTipText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "ChemieMstMessungenEditor.lblUQN6.toolTipText",
                new Object[] {})); // NOI18N
        lblUQN8.setMinimumSize(new java.awt.Dimension(380, 20));
        lblUQN8.setPreferredSize(new java.awt.Dimension(380, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblUQN8, gridBagConstraints);

        panAnl8.setOpaque(false);
        panAnl8.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(panAnl8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(jPanel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        panInfoContent.add(jSeparator1, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblChemGesStat.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblChemGesStat.text")); // NOI18N
        lblChemGesStat.setToolTipText(org.openide.util.NbBundle.getMessage(
                WkFgPanThirteen.class,
                "WkFgPanThirteen.lblChemGesStat.toolTipText",
                new Object[] {}));                       // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        jPanel3.add(lblChemGesStat, gridBagConstraints);

        cbEco_stat1.setMinimumSize(new java.awt.Dimension(150, 20));
        cbEco_stat1.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.chem_stat}"),
                cbEco_stat1,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
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

        txtHymoGkBemerkung3.setMinimumSize(new java.awt.Dimension(200, 20));
        txtHymoGkBemerkung3.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.chem_ges_bemerkung}"),
                txtHymoGkBemerkung3,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel3.add(txtHymoGkBemerkung3, gridBagConstraints);

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
    private void cbPropOhneActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbPropOhneActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbPropOhneActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbEco_stat1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbEco_stat1ActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbEco_stat1ActionPerformed

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

            final String chemGesBem = (String)cidsBean.getProperty("chem_ges_bemerkung");

            if ((chemGesBem != null) && chemGesBem.startsWith("http")) {
                txtHymoGkBemerkung3.setForeground(new Color(28, 72, 227));
                txtHymoGkBemerkung3.addMouseListener(new MouseAdapter() {

                        boolean isHandCursor = false;

                        @Override
                        public void mouseClicked(final MouseEvent e) {
                            if (chemGesBem != null) {
                                try {
                                    BrowserLauncher.openURL(chemGesBem);
                                } catch (Exception ex) {
                                    LOG.warn(ex, ex);
                                }
                            }
                        }
                    });
            }

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

                final int mstId = (Integer)sbean.getProperty("messstelle.id");
                final Integer messjahr = (Integer)sbean.getProperty("messjahr");

                final List<CidsBean> anl8 = model.getStoff(mstId, messjahr, false);

                panAnl8.removeAll();

                if ((anl8 == null) || anl8.isEmpty()) {
                    final JLabel lblUQN8Val = new JLabel();
                    lblUQN8Val.setMinimumSize(new java.awt.Dimension(380, 20));
                    lblUQN8Val.setPreferredSize(new java.awt.Dimension(380, 20));
                    lblUQN8Val.setText("keine UQN Überschreitungen");
                    panAnl8.add(lblUQN8Val, BorderLayout.CENTER);
                } else {
                    final JXTable tab = new UQNTable(anl8);
                    final JScrollPane scrollPane = new JScrollPane(tab);
                    scrollPane.setMinimumSize(new java.awt.Dimension(580, 200));
                    scrollPane.setPreferredSize(new java.awt.Dimension(580, 200));
                    scrollPane.setOpaque(false);
                    panAnl8.add(scrollPane, BorderLayout.CENTER);
                }
            }
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class MstTableModel extends AbstractTableModel {

        //~ Instance fields ----------------------------------------------------

        private String[][] header = {
                { "MST", "messstelle.messstelle" }, // NOI18N
                { "WK", "wk_k" },                   // NOI18N
                { "Jahr", "messjahr" },             // NOI18N
                { "UQN-Überschreitung", "" },       // NOI18N
            };
        private List<CidsBean> data = new ArrayList<CidsBean>();
        private List<CidsBean> stoffData = new ArrayList<CidsBean>();
        private Boolean isInitialised = false;
        private CidsBean wkBean;
        private boolean anl6 = false;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new MstTableModel object.
         */
        public MstTableModel() {
        }

        /**
         * Creates a new MstTableModel object.
         *
         * @param  anl6  DOCUMENT ME!
         */
        public MstTableModel(final boolean anl6) {
            this.anl6 = anl6;
        }

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
            return String.class;
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
                if (columnIndex == 3) {
                    if (hasStoff(
                                    (Integer)data.get(rowIndex).getProperty("messstelle.id"),
                                    (Integer)data.get(rowIndex).getProperty("messjahr"))) {
                        return "ja";
                    } else {
                        return "nein";
                    }
                } else if (columnIndex == 1) {
                    if (wkBean != null) {
                        return (String)wkBean.getProperty("wk_k");
                    } else {
                        return "";
                    }
                } else {
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
                }
            } else {
                return "";                                                                // NOI18N
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
            wkBean = cidsBean;
            data.clear();
            data.addAll(WkFgPanSix.getChemMst(cidsBean));
            stoffData.addAll(getChemMst(getIdsFromMstList(data)));
            isInitialised = true;
            fireTableDataChanged();
        }

        /**
         * DOCUMENT ME!
         *
         * @param   mst   DOCUMENT ME!
         * @param   year  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private boolean hasStoff(final Integer mst, final Integer year) {
            if ((mst == null) || (year == null)) {
                return false;
            }

            for (final CidsBean stoff : stoffData) {
                if ((stoff.getProperty("mst.id") != null) && stoff.getProperty("mst.id").equals(mst)) {
                    final Integer mstYear = (Integer)stoff.getProperty("jahr_messung");

                    if ((mstYear != null) && mstYear.equals(year)) {
                        final Boolean anl6Val = (Boolean)stoff.getProperty("substance_code.anl_6");
                        final Boolean anl8Val = (Boolean)stoff.getProperty("substance_code.anl_8");

                        if ((anl6 && (anl6Val != null) && anl6Val) || (!anl6 && (anl8Val != null) && anl8Val)) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        /**
         * DOCUMENT ME!
         *
         * @param   mst   DOCUMENT ME!
         * @param   year  DOCUMENT ME!
         * @param   anl6  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public List<CidsBean> getStoff(final Integer mst, final Integer year, final boolean anl6) {
            final List<CidsBean> hits = new ArrayList<CidsBean>();

            if ((mst == null) || (year == null)) {
                return hits;
            }

            for (final CidsBean stoff : stoffData) {
                if (stoff.getProperty("mst.id").equals(mst)) {
                    final Integer mstYear = (Integer)stoff.getProperty("jahr_messung");

                    if ((mstYear != null) && mstYear.equals(year)) {
                        final Boolean anl6Val = (Boolean)stoff.getProperty("substance_code.anl_6");
                        final Boolean anl8Val = (Boolean)stoff.getProperty("substance_code.anl_8");

                        if ((anl6 && (anl6Val != null) && anl6Val) || (!anl6 && (anl8Val != null) && anl8Val)) {
                            hits.add(stoff);
                        }
                    }
                }
            }

            return hits;
        }

        /**
         * DOCUMENT ME!
         *
         * @param   mstList  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private String getIdsFromMstList(final List<CidsBean> mstList) {
            StringBuilder sb = null;

            for (final CidsBean mst : mstList) {
                if (sb == null) {
                    sb = new StringBuilder(String.valueOf(mst.getProperty("messstelle.id")));
                } else {
                    sb.append(",").append(String.valueOf(mst.getProperty("messstelle.id")));
                }
            }

            return (sb == null) ? null : sb.toString();
        }

        /**
         * DOCUMENT ME!
         *
         * @param   mstIds  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private List<CidsBean> getChemMst(final String mstIds) {
            final List<CidsBean> data = new ArrayList<CidsBean>();

            if (mstIds == null) {
                return data;
            }

            try {
                final MetaClass MC = ClassCacheMultiple.getMetaClass(
                        WRRLUtil.DOMAIN_NAME,
                        "chemie_mst_stoff");

                if (MC != null) {
                    String query = "select " + MC.getID() + ", m." + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
                    query += " m";                                                                                      // NOI18N
                    query += " WHERE mst in (" + mstIds + ")";

                    final MetaObject[] metaObjects = MetaObjectCache.getInstance()
                                .getMetaObjectsByQuery(query, MC, false, WkFgEditor.CONNECTION_CONTEXT);

                    for (final MetaObject mo : metaObjects) {
                        data.add(mo.getBean());
                    }
                }
            } catch (final CacheException e) {
                LOG.error("Error while trying to receive measurements.", e); // NOI18N
            }

            return data;
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

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class UQNTable extends JXTable {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new UQNTable object.
         *
         * @param  beans  DOCUMENT ME!
         */
        public UQNTable(final List<CidsBean> beans) {
            setModel(new CustomStoffTableModel(beans));
            initRenderer();
        }

        /**
         * Creates a new UQNTable object.
         *
         * @param  beans   DOCUMENT ME!
         * @param  header  DOCUMENT ME!
         */
        public UQNTable(final List<CidsBean> beans, final String[][] header) {
            setModel(new CustomStoffTableModel(beans, header));
            initRenderer();
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         */
        private void initRenderer() {
            final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(final JTable table,
                            final Object value,
                            final boolean isSelected,
                            final boolean hasFocus,
                            final int row,
                            final int column) {
                        final Component com = super.getTableCellRendererComponent(
                                table,
                                value,
                                isSelected,
                                hasFocus,
                                row,
                                column);
                        if ((value instanceof Number) && (com instanceof JLabel)) {
                            final DecimalFormat formatter = new DecimalFormat("0.########");

                            ((JLabel)com).setText(formatter.format(value));
                        }

                        return com;
                    }
                };

            getColumn(1).setCellRenderer(renderer);
            getColumn(3).setCellRenderer(renderer);
        }

        //~ Inner Classes ------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @version  $Revision$, $Date$
         */
        private class CustomStoffTableModel extends AbstractTableModel {

            //~ Instance fields ------------------------------------------------

            private String[][] header = {
                    { "Stoffname", "substance_code.name_de" }, // NOI18N
                    { "Messwert", "messwert" },                // NOI18N
                    { "Einheit", "einheit" },                  // NOI18N
                    { "Grenzwert", "grenzwert" },              // NOI18N
                    { "UQN-Art", "uqn_type.value" }            // NOI18N
                };
            private List<CidsBean> data = new ArrayList<CidsBean>();

            //~ Constructors ---------------------------------------------------

            /**
             * Creates a new CustomStoffTableModel object.
             *
             * @param  beans  DOCUMENT ME!
             */
            public CustomStoffTableModel(final List<CidsBean> beans) {
                this.data = beans;
            }

            /**
             * Creates a new CustomStoffTableModel object.
             *
             * @param  beans   DOCUMENT ME!
             * @param  header  DOCUMENT ME!
             */
            public CustomStoffTableModel(final List<CidsBean> beans, final String[][] header) {
                this.data = beans;
                this.header = header;
            }

            //~ Methods --------------------------------------------------------

            @Override
            public int getRowCount() {
                return data.size();
            }

            @Override
            public int getColumnCount() {
                return header.length;
            }

            @Override
            public String getColumnName(final int columnIndex) {
                return header[columnIndex][0];
            }

            @Override
            public Class<?> getColumnClass(final int columnIndex) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(final int rowIndex, final int columnIndex) {
                final Object value = data.get(rowIndex).getProperty(header[columnIndex][1]);

                if (value instanceof Number) {
                    final DecimalFormat formatter = new DecimalFormat("0.########");

                    return formatter.format(value);
                }

                return (value == null) ? null : String.valueOf(value);
            }

            @Override
            public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            }
        }
    }
}
