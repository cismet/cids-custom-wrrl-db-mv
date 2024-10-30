/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.wrrl_db_mv.server.search.QbwInUseSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.IntegerConverter;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.TimestampConverter;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkePanOne extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuerbauwerkePanOne.class);
    private static final ConnectionContext CC = ConnectionContext.create(
            AbstractConnectionContext.Category.EDITOR,
            "Querbauwerke");

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbAdjustment;
    private javax.swing.JComboBox<String> cbBaujahr;
    private javax.swing.JCheckBox cbChange;
    private javax.swing.JComboBox<String> cbOpt;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbTyp;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo11;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo2;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo3;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo4;
    private de.cismet.cids.editors.DefaultBindableTimestampChooser defaultBindableTimestampChooser1;
    private de.cismet.cids.editors.DefaultBindableTimestampChooser dtPruefungZeit;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JLabel lblBaujahr;
    private javax.swing.JLabel lblBauwerksnummerKey;
    private javax.swing.JLabel lblDgh;
    private javax.swing.JLabel lblFotoKey;
    private javax.swing.JLabel lblFotoRichtungKey;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblKartierdatumKey;
    private javax.swing.JLabel lblKartierdatumKey2;
    private javax.swing.JLabel lblKartierdatumKey3;
    private javax.swing.JLabel lblKartiererKey;
    private javax.swing.JLabel lblKartiererKey2;
    private javax.swing.JLabel lblLandkreisKey;
    private javax.swing.JLabel lblLastDate;
    private javax.swing.JLabel lblLastEditor;
    private javax.swing.JLabel lblOptJahr;
    private javax.swing.JLabel lblPruefungZeit;
    private javax.swing.JLabel lblQbwIdKey;
    private javax.swing.JLabel lblQbwLageKey;
    private javax.swing.JLabel lblStaluKey;
    private javax.swing.JLabel lblStaluKey1;
    private javax.swing.JLabel lblStaluKey2;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTyp;
    private javax.swing.JLabel lblWaKoerperKey2;
    private javax.swing.JLabel lblWaKoerperKey3;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JTextField txtPruefungBearbeiter;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form QuerbauwerkePanOne.
     */
    public QuerbauwerkePanOne() {
        this(false);
    }

    /**
     * Creates new form QuerbauwerkePanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public QuerbauwerkePanOne(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();

        final List<Integer> years = new ArrayList<Integer>();
        final Integer currentYear = new GregorianCalendar().get(GregorianCalendar.YEAR);
        years.add(null);
        years.add(0);

        for (int year = currentYear; year >= 1900; --year) {
            years.add(year);
        }

        cbBaujahr.setModel(new DefaultComboBoxModel(years.toArray(new Integer[years.size()])));
        cbOpt.setModel(new DefaultComboBoxModel(years.toArray(new Integer[years.size()])));

        lblTime.setVisible(readOnly);
        defaultBindableTimestampChooser1.setVisible(!readOnly);
        lblPruefungZeit.setVisible(readOnly);
        dtPruefungZeit.setVisible(!readOnly);

        if (readOnly) {
            RendererTools.makeReadOnly(cbBaujahr);
            RendererTools.makeReadOnly(cbOpt);
            RendererTools.makeReadOnly(jTextField12);
            RendererTools.makeReadOnly(jTextField14);
            RendererTools.makeReadOnly(jTextField15);
            RendererTools.makeReadOnly(jTextField26);
            RendererTools.makeReadOnly(jTextField27);
            RendererTools.makeReadOnly(defaultBindableReferenceCombo11);
            RendererTools.makeReadOnly(defaultBindableReferenceCombo2);
            RendererTools.makeReadOnly(defaultBindableReferenceCombo3);
            RendererTools.makeReadOnly(defaultBindableReferenceCombo4);
            RendererTools.makeReadOnly(cbTyp);
            RendererTools.makeReadOnly(txtPruefungBearbeiter);
            RendererTools.makeReadOnly(cbChange);
            RendererTools.makeReadOnly(cbAdjustment);
            defaultBindableTimestampChooser1.setEnabled(false);
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
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        lblQbwIdKey = new javax.swing.JLabel();
        lblBauwerksnummerKey = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblLandkreisKey = new javax.swing.JLabel();
        lblStaluKey = new javax.swing.JLabel();
        lblStaluKey1 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        lblStaluKey2 = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        lblWaKoerperKey2 = new javax.swing.JLabel();
        defaultBindableReferenceCombo2 = new ScrollableComboBox(true);
        lblWaKoerperKey3 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        lblQbwLageKey = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        defaultBindableReferenceCombo3 = new ScrollableComboBox(true);
        defaultBindableReferenceCombo4 = new ScrollableComboBox(true);
        lblDgh = new javax.swing.JLabel();
        lblBaujahr = new javax.swing.JLabel();
        cbBaujahr = new javax.swing.JComboBox<>();
        lblOptJahr = new javax.swing.JLabel();
        cbOpt = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblKartiererKey = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        lblKartierdatumKey = new javax.swing.JLabel();
        defaultBindableTimestampChooser1 = new de.cismet.cids.editors.DefaultBindableTimestampChooser();
        lblTime = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lblLastEditor = new javax.swing.JLabel();
        lblLastDate = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblFotoKey = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        lblFotoRichtungKey = new javax.swing.JLabel();
        defaultBindableReferenceCombo11 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        jPanel13 = new javax.swing.JPanel();
        lblKartiererKey2 = new javax.swing.JLabel();
        lblKartierdatumKey2 = new javax.swing.JLabel();
        lblKartierdatumKey3 = new javax.swing.JLabel();
        cbChange = new javax.swing.JCheckBox();
        cbAdjustment = new javax.swing.JCheckBox();
        txtPruefungBearbeiter = new javax.swing.JTextField();
        dtPruefungZeit = new de.cismet.cids.editors.DefaultBindableTimestampChooser();
        lblPruefungZeit = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lblTyp = new javax.swing.JLabel();
        cbTyp = new DefaultBindableReferenceCombo(true);
        jSeparator1 = new javax.swing.JSeparator();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(jPanel2, gridBagConstraints);

        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.GridBagLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridBagLayout());

        lblQbwIdKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblQbwIdKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblQbwIdKey, gridBagConstraints);

        lblBauwerksnummerKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblBauwerksnummerKey.text")); // NOI18N
        lblBauwerksnummerKey.setMinimumSize(new java.awt.Dimension(150, 17));
        lblBauwerksnummerKey.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblBauwerksnummerKey, gridBagConstraints);

        jTextField14.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextField14.setPreferredSize(new java.awt.Dimension(250, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bauwerksnummer}"),
                jTextField14,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jTextField14, gridBagConstraints);

        jLabel3.setMinimumSize(new java.awt.Dimension(100, 25));
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.id}"),
                jLabel3,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel3, gridBagConstraints);

        lblLandkreisKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblLandkreisKey.text")); // NOI18N
        lblLandkreisKey.setMinimumSize(new java.awt.Dimension(150, 17));
        lblLandkreisKey.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblLandkreisKey, gridBagConstraints);

        lblStaluKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblStaluKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblStaluKey, gridBagConstraints);

        lblStaluKey1.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblStaluKey1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblStaluKey1, gridBagConstraints);

        jTextField26.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextField26.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.staeun_}"),
                jTextField26,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jTextField26, gridBagConstraints);

        lblStaluKey2.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblStaluKey2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblStaluKey2, gridBagConstraints);

        jTextField27.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextField27.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wasserbuch}"),
                jTextField27,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jTextField27, gridBagConstraints);

        lblWaKoerperKey2.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblWaKoerperKey2.text")); // NOI18N
        lblWaKoerperKey2.setMinimumSize(new java.awt.Dimension(150, 17));
        lblWaKoerperKey2.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblWaKoerperKey2, gridBagConstraints);

        defaultBindableReferenceCombo2.setMinimumSize(new java.awt.Dimension(250, 25));
        defaultBindableReferenceCombo2.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zweck}"),
                defaultBindableReferenceCombo2,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(defaultBindableReferenceCombo2, gridBagConstraints);

        lblWaKoerperKey3.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblWaKoerperKey3.text")); // NOI18N
        lblWaKoerperKey3.setMinimumSize(new java.awt.Dimension(150, 34));
        lblWaKoerperKey3.setPreferredSize(new java.awt.Dimension(150, 34));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblWaKoerperKey3, gridBagConstraints);

        jCheckBox1.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.jCheckBox1.text")); // NOI18N
        jCheckBox1.setContentAreaFilled(false);
        jCheckBox1.setMinimumSize(new java.awt.Dimension(250, 25));
        jCheckBox1.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gwk_wrrl}"),
                jCheckBox1,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jCheckBox1, gridBagConstraints);

        lblQbwLageKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblQbwLageKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblQbwLageKey, gridBagConstraints);

        jLabel4.setMinimumSize(new java.awt.Dimension(250, 25));
        jLabel4.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel4, gridBagConstraints);

        defaultBindableReferenceCombo3.setMinimumSize(new java.awt.Dimension(250, 25));
        defaultBindableReferenceCombo3.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.landkreis_cat}"),
                defaultBindableReferenceCombo3,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(defaultBindableReferenceCombo3, gridBagConstraints);

        defaultBindableReferenceCombo4.setMinimumSize(new java.awt.Dimension(250, 25));
        defaultBindableReferenceCombo4.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stalu_cat}"),
                defaultBindableReferenceCombo4,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(defaultBindableReferenceCombo4, gridBagConstraints);

        lblDgh.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblDgh.text",
                new Object[] {})); // NOI18N
        lblDgh.setMinimumSize(new java.awt.Dimension(140, 25));
        lblDgh.setPreferredSize(new java.awt.Dimension(140, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanel8.add(lblDgh, gridBagConstraints);

        lblBaujahr.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblBaujahr.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblBaujahr, gridBagConstraints);

        cbBaujahr.setMinimumSize(new java.awt.Dimension(200, 25));
        cbBaujahr.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.baujahr}"),
                cbBaujahr,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(cbBaujahr, gridBagConstraints);

        lblOptJahr.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblOptJahr.text")); // NOI18N
        lblOptJahr.setToolTipText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblOptJahr.toolTipText",
                new Object[] {}));                      // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblOptJahr, gridBagConstraints);

        cbOpt.setMinimumSize(new java.awt.Dimension(200, 25));
        cbOpt.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.opt_jahr}"),
                cbOpt,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(cbOpt, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(13, 7, 5, 7);
        jPanel9.add(jPanel8, gridBagConstraints);

        jPanel1.setOpaque(false);

        final javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel9.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        panInfoContent.add(jPanel9, gridBagConstraints);

        jPanel10.setOpaque(false);
        jPanel10.setLayout(new java.awt.GridBagLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblKartiererKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblKartiererKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblKartiererKey, gridBagConstraints);

        jTextField15.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextField15.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kartierer}"),
                jTextField15,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jTextField15, gridBagConstraints);

        lblKartierdatumKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblKartierdatumKey.text")); // NOI18N
        lblKartierdatumKey.setMinimumSize(new java.awt.Dimension(150, 17));
        lblKartierdatumKey.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblKartierdatumKey, gridBagConstraints);

        defaultBindableTimestampChooser1.setMinimumSize(new java.awt.Dimension(250, 25));
        defaultBindableTimestampChooser1.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kartierdatum}"),
                defaultBindableTimestampChooser1,
                org.jdesktop.beansbinding.BeanProperty.create("timestamp"));
        binding.setConverter(defaultBindableTimestampChooser1.getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(defaultBindableTimestampChooser1, gridBagConstraints);

        lblTime.setMinimumSize(new java.awt.Dimension(250, 25));
        lblTime.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kartierdatum}"),
                lblTime,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setConverter(TimestampConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblTime, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 7);
        jPanel10.add(jPanel3, gridBagConstraints);

        jPanel11.setOpaque(false);
        jPanel11.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel10.add(jPanel11, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridBagLayout());

        lblLastEditor.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblLastEditor.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblLastEditor, gridBagConstraints);

        lblLastDate.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblLastDate.text")); // NOI18N
        lblLastDate.setMinimumSize(new java.awt.Dimension(150, 17));
        lblLastDate.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblLastDate, gridBagConstraints);

        jLabel5.setMinimumSize(new java.awt.Dimension(250, 25));
        jLabel5.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.av_user}"),
                jLabel5,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel5, gridBagConstraints);

        jLabel6.setMinimumSize(new java.awt.Dimension(250, 25));
        jLabel6.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.av_time}"),
                jLabel6,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        binding.setConverter(new TimestampConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(jLabel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 7);
        jPanel10.add(jPanel7, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    QuerbauwerkePanOne.class,
                    "QuerbauwerkePanOne.jPanel6.border.title"))); // NOI18N
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.GridBagLayout());

        lblFotoKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblFotoKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(lblFotoKey, gridBagConstraints);

        jTextField12.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextField12.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.foto}"),
                jTextField12,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jTextField12, gridBagConstraints);

        lblFotoRichtungKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblFotoRichtungKey.text")); // NOI18N
        lblFotoRichtungKey.setMinimumSize(new java.awt.Dimension(150, 17));
        lblFotoRichtungKey.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(lblFotoRichtungKey, gridBagConstraints);

        defaultBindableReferenceCombo11.setMinimumSize(new java.awt.Dimension(250, 25));
        defaultBindableReferenceCombo11.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.foto_richtung}"),
                defaultBindableReferenceCombo11,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(defaultBindableReferenceCombo11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel10.add(jPanel6, gridBagConstraints);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel13.setOpaque(false);
        jPanel13.setLayout(new java.awt.GridBagLayout());

        lblKartiererKey2.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblKartiererKey2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(lblKartiererKey2, gridBagConstraints);

        lblKartierdatumKey2.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblKartierdatumKey2.text")); // NOI18N
        lblKartierdatumKey2.setMinimumSize(new java.awt.Dimension(150, 17));
        lblKartierdatumKey2.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(lblKartierdatumKey2, gridBagConstraints);

        lblKartierdatumKey3.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblKartierdatumKey3.text")); // NOI18N
        lblKartierdatumKey3.setMinimumSize(new java.awt.Dimension(150, 17));
        lblKartierdatumKey3.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(lblKartierdatumKey3, gridBagConstraints);

        cbChange.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.cbChange.text",
                new Object[] {})); // NOI18N
        cbChange.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.keine_aenderung}"),
                cbChange,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(cbChange, gridBagConstraints);

        cbAdjustment.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.cbAdjustment.text",
                new Object[] {})); // NOI18N
        cbAdjustment.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.anpassung}"),
                cbAdjustment,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(cbAdjustment, gridBagConstraints);

        txtPruefungBearbeiter.setMinimumSize(new java.awt.Dimension(250, 25));
        txtPruefungBearbeiter.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bearbeiter_ueberpruefung}"),
                txtPruefungBearbeiter,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(txtPruefungBearbeiter, gridBagConstraints);

        dtPruefungZeit.setMinimumSize(new java.awt.Dimension(250, 25));
        dtPruefungZeit.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.datum_ueberpruefung}"),
                dtPruefungZeit,
                org.jdesktop.beansbinding.BeanProperty.create("timestamp"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(dtPruefungZeit, gridBagConstraints);

        lblPruefungZeit.setMinimumSize(new java.awt.Dimension(250, 25));
        lblPruefungZeit.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.datum_ueberpruefung}"),
                lblPruefungZeit,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel13.add(lblPruefungZeit, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 7);
        jPanel10.add(jPanel13, gridBagConstraints);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel12.setOpaque(false);
        jPanel12.setLayout(new java.awt.GridBagLayout());

        lblTyp.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanOne.class,
                "QuerbauwerkePanOne.lblTyp.text")); // NOI18N
        lblTyp.setMinimumSize(new java.awt.Dimension(150, 17));
        lblTyp.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanel12.add(lblTyp, gridBagConstraints);

        cbTyp.setMinimumSize(new java.awt.Dimension(250, 25));
        cbTyp.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.faa_typ}"),
                cbTyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel12.add(cbTyp, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 5, 7);
        jPanel10.add(jPanel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        panInfoContent.add(jPanel10, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 15, 5);
        panInfoContent.add(jSeparator1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  qbwId  DOCUMENT ME!
     */
    public void setQbwLage(final String qbwId) {
        jLabel4.setText(qbwId);
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

        setDghId();
    }

    /**
     * DOCUMENT ME!
     */
    private void setDghId() {
        final SwingWorker<Integer, Void> sw = new SwingWorker<Integer, Void>() {

                @Override
                protected Integer doInBackground() throws Exception {
                    final QbwInUseSearch search = new QbwInUseSearch(cidsBean.getPrimaryKeyValue());
                    search.initWithConnectionContext(CC);

                    final ArrayList<ArrayList> res = (ArrayList<ArrayList>)SessionManager.getProxy()
                                .customServerSearch(SessionManager.getSession().getUser(),
                                        search,
                                        CC);

                    if ((res != null) && (res.size() > 0)) {
                        return (Integer)res.get(0).get(0);
                    }

                    return -1;
                }

                @Override
                protected void done() {
                    try {
                        final Integer id = get();

                        lblDgh.setText("DGH-ID:   " + id);
                    } catch (Exception e) {
                        LOG.error("Error while determine dgh id", e);
                        lblDgh.setText("DGH-ID:   error");
                    }
                }
            };

        sw.execute();
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }
}
