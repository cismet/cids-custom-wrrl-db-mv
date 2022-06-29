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

import org.jdesktop.swingx.JXTable;

import java.awt.BorderLayout;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.cismet.cids.custom.wrrl_db_mv.util.IntegerConverter;
import de.cismet.cids.custom.wrrl_db_mv.util.QualityStatusCodeComparator;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkFgPanTwelve extends javax.swing.JPanel implements DisposableCidsBeanStore,
    PropertyChangeListener,
    ListSelectionListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkFgPanTwelve.class);
    private static final int MAX_YEAR = 2027;

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private final WkFgPanThirteen.MstTableModel model = new WkFgPanThirteen.MstTableModel(true);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEcoPot;
    private javax.swing.JComboBox<String> cbEcoPotJahr;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEcoStat;
    private javax.swing.JComboBox<String> cbEcoStatJahr;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbWorstCase;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbVorb;
    private javax.swing.JTable jtMstTab1;
    private javax.swing.JLabel lblBemerk;
    private javax.swing.JLabel lblBewertung;
    private javax.swing.JLabel lblEcoPot;
    private javax.swing.JLabel lblEcoStat;
    private javax.swing.JLabel lblFische;
    private javax.swing.JLabel lblFische1;
    private javax.swing.JLabel lblFische2;
    private javax.swing.JLabel lblGenCond;
    private javax.swing.JLabel lblGk;
    private javax.swing.JLabel lblGkHm;
    private javax.swing.JLabel lblGkPcQk;
    private javax.swing.JLabel lblGkPcQk1;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHymoJahr;
    private javax.swing.JLabel lblHymoQk;
    private javax.swing.JLabel lblJahr;
    private javax.swing.JLabel lblJahrCol;
    private javax.swing.JLabel lblMakrophyt;
    private javax.swing.JLabel lblMakrophyt1;
    private javax.swing.JLabel lblMakrophyt2;
    private javax.swing.JLabel lblMakrozoobenthos;
    private javax.swing.JLabel lblMakrozoobenthos1;
    private javax.swing.JLabel lblMakrozoobenthos2;
    private javax.swing.JLabel lblPhysChemJahr;
    private javax.swing.JLabel lblPhyto;
    private javax.swing.JLabel lblPhyto1;
    private javax.swing.JLabel lblPhyto2;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JLabel lblUQN;
    private javax.swing.JLabel lblUQN6;
    private javax.swing.JPanel panAnl6;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WkFgPanTwelve object.
     */
    public WkFgPanTwelve() {
        this(false);
    }

    /**
     * Creates new form WkFgPanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkFgPanTwelve(final boolean readOnly) {
        initComponents();
        lblGkPcQk.setVisible(false);
        jScrollPane1.getViewport().setOpaque(false);

        if (readOnly) {
            RendererTools.makeReadOnly(cbEcoPot);
            RendererTools.makeReadOnly(cbEcoStat);
//            RendererTools.makeReadOnly(txtEcoPotBemerk);
            RendererTools.makeReadOnly(cbEcoPotJahr);
//            RendererTools.makeReadOnly(txtEcoStatBemerkung);
            RendererTools.makeReadOnly(cbEcoStatJahr);
            RendererTools.makeReadOnly(cbWorstCase);
            jbVorb.setVisible(false);
        }
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
        lblGk = new javax.swing.JLabel();
        lblJahr = new javax.swing.JLabel();
        lblBemerk = new javax.swing.JLabel();
        cbEcoStatJahr = new javax.swing.JComboBox<>();
        cbEcoStat = new ScrollableComboBox(new QualityStatusCodeComparator(), true);
        lblEcoStat = new javax.swing.JLabel();
        lblEcoPot = new javax.swing.JLabel();
        cbEcoPotJahr = new javax.swing.JComboBox<>();
        cbEcoPot = new ScrollableComboBox(new QualityStatusCodeComparator(), true);
        jbVorb = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblMakrozoobenthos = new javax.swing.JLabel();
        lblPhyto = new javax.swing.JLabel();
        lblMakrophyt = new javax.swing.JLabel();
        lblFische = new javax.swing.JLabel();
        lblMakrozoobenthos1 = new javax.swing.JLabel();
        lblPhyto1 = new javax.swing.JLabel();
        lblMakrophyt1 = new javax.swing.JLabel();
        lblFische1 = new javax.swing.JLabel();
        lblMakrozoobenthos2 = new javax.swing.JLabel();
        lblPhyto2 = new javax.swing.JLabel();
        lblMakrophyt2 = new javax.swing.JLabel();
        lblFische2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblBewertung = new javax.swing.JLabel();
        lblJahrCol = new javax.swing.JLabel();
        lblHymoQk = new javax.swing.JLabel();
        lblGkHm = new javax.swing.JLabel();
        lblHymoJahr = new javax.swing.JLabel();
        lblGenCond = new javax.swing.JLabel();
        lblGkPcQk = new javax.swing.JLabel();
        lblPhysChemJahr = new javax.swing.JLabel();
        lblUQN = new javax.swing.JLabel();
        lblGkPcQk1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtMstTab1 = new javax.swing.JTable();
        lblUQN6 = new javax.swing.JLabel();
        panAnl6 = new javax.swing.JPanel();
        cbWorstCase = new ScrollableComboBox(true);

        setMinimumSize(new java.awt.Dimension(910, 650));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(910, 650));
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanTwelve.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        panInfoContent.setMaximumSize(new java.awt.Dimension(777, 400));
        panInfoContent.setMinimumSize(new java.awt.Dimension(777, 400));
        panInfoContent.setOpaque(false);
        panInfoContent.setPreferredSize(new java.awt.Dimension(777, 400));
        panInfoContent.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(lblSpace, gridBagConstraints);

        lblGk.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanEleven.lblHydromorph.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblGk, gridBagConstraints);

        lblJahr.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPaneleven.lblHymoGkJahr.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblJahr, gridBagConstraints);

        lblBemerk.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanEleven.lblBemerk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblBemerk, gridBagConstraints);

        cbEcoStatJahr.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbEcoStatJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        cbEcoStatJahr.setPreferredSize(new java.awt.Dimension(100, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eco_stat_jahr}"),
                cbEcoStatJahr,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEcoStatJahr, gridBagConstraints);

        cbEcoStat.setMinimumSize(new java.awt.Dimension(200, 20));
        cbEcoStat.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eco_stat}"),
                cbEcoStat,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbEcoStat.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbEcoStatActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEcoStat, gridBagConstraints);

        lblEcoStat.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanEleven.lblEcoStat.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblEcoStat, gridBagConstraints);

        lblEcoPot.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanEleven.lblEcoPat.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblEcoPot, gridBagConstraints);

        cbEcoPotJahr.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbEcoPotJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        cbEcoPotJahr.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eco_pot_jahr}"),
                cbEcoPotJahr,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEcoPotJahr, gridBagConstraints);

        cbEcoPot.setMaximumSize(new java.awt.Dimension(200, 25));
        cbEcoPot.setMinimumSize(new java.awt.Dimension(200, 20));
        cbEcoPot.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eco_pot}"),
                cbEcoPot,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEcoPot, gridBagConstraints);

        jbVorb.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanTwelve.jbVorb.text")); // NOI18N
        jbVorb.setMinimumSize(new java.awt.Dimension(100, 20));
        jbVorb.setPreferredSize(new java.awt.Dimension(100, 20));
        jbVorb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbVorbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jbVorb, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblMakrozoobenthos.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanTwelve.class,
                "WkFgPanTwelve.lblMakrozoobenthos.text")); // NOI18N
        lblMakrozoobenthos.setMinimumSize(new java.awt.Dimension(230, 20));
        lblMakrozoobenthos.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblMakrozoobenthos, gridBagConstraints);

        lblPhyto.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanTwelve.lblPhyto.text")); // NOI18N
        lblPhyto.setMinimumSize(new java.awt.Dimension(230, 20));
        lblPhyto.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblPhyto, gridBagConstraints);

        lblMakrophyt.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanTwelve.class,
                "WkFgPanTwelve.lblMakrophyt.text")); // NOI18N
        lblMakrophyt.setMinimumSize(new java.awt.Dimension(230, 20));
        lblMakrophyt.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblMakrophyt, gridBagConstraints);

        lblFische.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanTwelve.lblFische.text")); // NOI18N
        lblFische.setMinimumSize(new java.awt.Dimension(230, 20));
        lblFische.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblFische, gridBagConstraints);

        lblMakrozoobenthos1.setMinimumSize(new java.awt.Dimension(200, 20));
        lblMakrozoobenthos1.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ben_inv.name}"),
                lblMakrozoobenthos1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblMakrozoobenthos1, gridBagConstraints);

        lblPhyto1.setMinimumSize(new java.awt.Dimension(200, 20));
        lblPhyto1.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phyto.name}"),
                lblPhyto1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblPhyto1, gridBagConstraints);

        lblMakrophyt1.setMinimumSize(new java.awt.Dimension(200, 20));
        lblMakrophyt1.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mac_phyto.name}"),
                lblMakrophyt1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblMakrophyt1, gridBagConstraints);

        lblFische1.setMinimumSize(new java.awt.Dimension(200, 20));
        lblFische1.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fish.name}"),
                lblFische1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblFische1, gridBagConstraints);

        lblMakrozoobenthos2.setMinimumSize(new java.awt.Dimension(130, 20));
        lblMakrozoobenthos2.setPreferredSize(new java.awt.Dimension(130, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ben_inv_gk_jahr}"),
                lblMakrozoobenthos2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblMakrozoobenthos2, gridBagConstraints);

        lblPhyto2.setMinimumSize(new java.awt.Dimension(130, 20));
        lblPhyto2.setPreferredSize(new java.awt.Dimension(130, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phyto_gk_jahr}"),
                lblPhyto2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblPhyto2, gridBagConstraints);

        lblMakrophyt2.setMinimumSize(new java.awt.Dimension(130, 20));
        lblMakrophyt2.setPreferredSize(new java.awt.Dimension(130, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mac_phyto_gk_jahr}"),
                lblMakrophyt2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblMakrophyt2, gridBagConstraints);

        lblFische2.setMinimumSize(new java.awt.Dimension(130, 20));
        lblFische2.setPreferredSize(new java.awt.Dimension(130, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fish_gk_jahr}"),
                lblFische2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblFische2, gridBagConstraints);

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        lblBewertung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBewertung.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanTwelve.class,
                "WkFgPanTwelve.lblBewertung.text")); // NOI18N
        lblBewertung.setMinimumSize(new java.awt.Dimension(200, 20));
        lblBewertung.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblBewertung, gridBagConstraints);

        lblJahrCol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblJahrCol.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanTwelve.lblJahrCol.text")); // NOI18N
        lblJahrCol.setMinimumSize(new java.awt.Dimension(130, 20));
        lblJahrCol.setPreferredSize(new java.awt.Dimension(130, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblJahrCol, gridBagConstraints);

        lblHymoQk.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanTwelve.lblHymoQk.text")); // NOI18N
        lblHymoQk.setMinimumSize(new java.awt.Dimension(230, 20));
        lblHymoQk.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblHymoQk, gridBagConstraints);

        lblGkHm.setMinimumSize(new java.awt.Dimension(200, 20));
        lblGkHm.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hydromorph.name}"),
                lblGkHm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblGkHm, gridBagConstraints);

        lblHymoJahr.setMinimumSize(new java.awt.Dimension(130, 20));
        lblHymoJahr.setPreferredSize(new java.awt.Dimension(130, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hymo_gk_jahr}"),
                lblHymoJahr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblHymoJahr, gridBagConstraints);

        lblGenCond.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanTwelve.lblGenCond.text")); // NOI18N
        lblGenCond.setMinimumSize(new java.awt.Dimension(230, 20));
        lblGenCond.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblGenCond, gridBagConstraints);

        lblGkPcQk.setMinimumSize(new java.awt.Dimension(200, 20));
        lblGkPcQk.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblGkPcQk, gridBagConstraints);

        lblPhysChemJahr.setMinimumSize(new java.awt.Dimension(130, 20));
        lblPhysChemJahr.setPreferredSize(new java.awt.Dimension(130, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.jahr_pcqk}"),
                lblPhysChemJahr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblPhysChemJahr, gridBagConstraints);

        lblUQN.setText(org.openide.util.NbBundle.getMessage(WkFgPanTwelve.class, "WkFgPanTwelve.lblUQN.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblUQN, gridBagConstraints);

        lblGkPcQk1.setMinimumSize(new java.awt.Dimension(200, 20));
        lblGkPcQk1.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fgss.name}"),
                lblGkPcQk1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblGkPcQk1, gridBagConstraints);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(800, 170));
        jScrollPane3.setOpaque(false);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(900, 170));

        jtMstTab1.setModel(model);
        jScrollPane3.setViewportView(jtMstTab1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        jPanel1.add(jScrollPane3, gridBagConstraints);

        lblUQN6.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanTwelve.class,
                "WkFgPanTwelve.lblUQN8.text",
                new Object[] {})); // NOI18N
        lblUQN6.setToolTipText(org.openide.util.NbBundle.getMessage(
                WkFgPanTwelve.class,
                "WkFgPanTwelve.lblUQN6.toolTipText",
                new Object[] {})); // NOI18N
        lblUQN6.setMinimumSize(new java.awt.Dimension(380, 20));
        lblUQN6.setPreferredSize(new java.awt.Dimension(380, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblUQN6, gridBagConstraints);

        panAnl6.setOpaque(false);
        panAnl6.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(panAnl6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(jPanel1, gridBagConstraints);

        cbWorstCase.setMinimumSize(new java.awt.Dimension(200, 20));
        cbWorstCase.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.worst_case}"),
                cbWorstCase,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbWorstCase.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbWorstCaseActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbWorstCase, gridBagConstraints);

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
    private void cbEcoStatActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbEcoStatActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbEcoStatActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbVorbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbVorbActionPerformed
        setValues();
    }                                                                          //GEN-LAST:event_jbVorbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbWorstCaseActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbWorstCaseActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbWorstCaseActionPerformed

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (jtMstTab1.getSelectedRow() > -1) {
                final CidsBean sbean = model.getData().get(jtMstTab1.getSelectedRow());

                final int mstId = (Integer)sbean.getProperty("messstelle.id");
                final Integer messjahr = (Integer)sbean.getProperty("messjahr");

                final List<CidsBean> anl6 = model.getStoff(mstId, messjahr, true);

                panAnl6.removeAll();

                if ((anl6 == null) || anl6.isEmpty()) {
                    final JLabel lblUQN6Val = new JLabel();
                    lblUQN6Val.setMinimumSize(new java.awt.Dimension(380, 20));
                    lblUQN6Val.setPreferredSize(new java.awt.Dimension(380, 20));
                    lblUQN6Val.setText("keine UQN Überschreitungen");
                    panAnl6.add(lblUQN6Val, BorderLayout.CENTER);
                } else {
                    final JXTable tab = new WkFgPanThirteen.UQNTable(anl6);
                    final JScrollPane scrollPane = new JScrollPane(tab);
                    scrollPane.setMinimumSize(new java.awt.Dimension(580, 200));
                    scrollPane.setPreferredSize(new java.awt.Dimension(580, 200));
                    scrollPane.setOpaque(false);
                    panAnl6.add(scrollPane, BorderLayout.CENTER);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setValues() {
//        final String[] props = { "bio_gk", "ben_inv", "mac_phyto", "phyto", "fish", "hydromorph", "gk_pc_qk" };
        final String[] props = { "bio_gk", "ben_inv", "mac_phyto", "phyto", "fish", "hydromorph" };
        int worstCase = -1;
        CidsBean worstCaseObject = null;

        for (final String tmp : props) {
            final CidsBean obj = (CidsBean)cidsBean.getProperty(tmp);
            if (obj != null) {
                final String valString = (String)obj.getProperty("value");

                if (valString != null) {
                    try {
                        final int val = Integer.parseInt(valString);
                        if (val > worstCase) {
                            worstCase = val;
                            worstCaseObject = obj;
                        }
                    } catch (final NumberFormatException e) {
                        if (valString.equals("U")) {
                            worstCase = 100;
                            worstCaseObject = obj;
                        } else {
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
        }

        try {
            if (worstCase != -1) {
                if (worstCaseObject != null) {
                    cidsBean.setProperty("eco_stat", worstCaseObject);
                    cidsBean.setProperty("eco_pot", worstCaseObject);

                    final CidsBean uqn = (CidsBean)cidsBean.getProperty("non_comp");

                    if ((uqn != null) && (worstCase != 100)) {
                        final String val = (String)uqn.getProperty("value");
                        if ((val != null) && val.equals("N")) {
                            if (worstCase <= 2) {
                                final ComboBoxModel cbm = cbEcoStat.getModel();
                                for (int i = 0; i < cbm.getSize(); ++i) {
                                    final CidsBean bean = (CidsBean)cbm.getElementAt(i);
                                    final Object va = bean.getProperty("value");
                                    if ((va instanceof String) && va.equals("3")) {
                                        cidsBean.setProperty("eco_stat", bean);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Es sind keine Bewertungen für eine Vorbelegung vorhanden.",
                    "Keine Bewertungen",
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (final Exception e) {
            LOG.error("Error while setting eco_stat and eco_pot.", e);
        }
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        if (cidsBean != null) {
            final List<Integer> years = new ArrayList<Integer>();
            final Integer currentYear = new GregorianCalendar().get(GregorianCalendar.YEAR);
            years.add(null);

            Integer currentValue = (Integer)cidsBean.getProperty("eco_pot_jahr");

            if ((currentValue != null) && (currentValue < currentYear)) {
                years.add(currentValue);
            }

            for (int year = currentYear; year <= MAX_YEAR; ++year) {
                years.add(year);
            }

            if ((currentValue != null) && (currentValue > MAX_YEAR)) {
                years.add(currentValue);
            }

            cbEcoPotJahr.setModel(new DefaultComboBoxModel(years.toArray(new Integer[years.size()])));
            years.clear();
            years.add(null);

            currentValue = (Integer)cidsBean.getProperty("eco_stat_jahr");

            if ((currentValue != null) && (currentValue < currentYear)) {
                years.add(currentValue);
            }

            for (int year = currentYear; year <= MAX_YEAR; ++year) {
                years.add(year);
            }

            if ((currentValue != null) && (currentValue > MAX_YEAR)) {
                years.add(currentValue);
            }

            cbEcoStatJahr.setModel(new DefaultComboBoxModel(years.toArray(new Integer[years.size()])));

            this.cidsBean = cidsBean;
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                this.cidsBean);
            bindingGroup.bind();

            final String b9ausw = (String)cidsBean.getProperty("b9ausw");

            if (b9ausw != null) {
                activateGUIElements(b9ausw.equals("natürlich"));
            }

            cidsBean.addPropertyChangeListener(this);
            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        model.refreshData(cidsBean);
                    }
                }).start();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ecoStat  DOCUMENT ME!
     */
    private void activateGUIElements(final boolean ecoStat) {
        lblEcoStat.setVisible(ecoStat);
        cbEcoStat.setVisible(ecoStat);
        cbEcoStatJahr.setVisible(ecoStat);
//        txtEcoStatBemerkung.setVisible(ecoStat);

        lblEcoPot.setVisible(!ecoStat);
        cbEcoPot.setVisible(!ecoStat);
//        txtEcoPotBemerk.setVisible(!ecoStat);
        cbEcoPotJahr.setVisible(!ecoStat);

        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = ((ecoStat) ? 1 : 2);
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.remove(jbVorb);
        panInfoContent.add(jbVorb, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.gridy = ((ecoStat) ? 1 : 2);
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.remove(cbWorstCase);
        panInfoContent.add(cbWorstCase, gridBagConstraints);
        panInfoContent.validate();
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
        cidsBean.removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("b9ausw")) {
            final String b9ausw = (String)cidsBean.getProperty("b9ausw");

            if (b9ausw != null) {
                activateGUIElements(b9ausw.equals("natürlich"));
            }
        }
    }
}
