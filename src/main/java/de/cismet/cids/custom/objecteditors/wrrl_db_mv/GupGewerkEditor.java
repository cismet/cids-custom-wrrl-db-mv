/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * GupMassnahmeSohle.java
 *
 * Created on 04.04.2012, 11:49:19
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaObject;

import java.awt.Color;

import java.util.Comparator;

import javax.swing.JOptionPane;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.util.ExpressionEvaluator;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultBindableCheckboxField;
import de.cismet.cids.editors.DefaultBindableColorChooser;
import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;
import de.cismet.cids.editors.FieldStateDecider;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupGewerkEditor extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupGewerkEditor.class);
    public static final Color SELECT_COLOR = new Color(212, 238, 94);
    public static final Color UNSELECT_COLOR = new Color(255, 66, 66);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private OperativeZieleDecider decider = new OperativeZieleDecider();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbArbeitsbreite;
    private javax.swing.JCheckBox cbBoeschungslaenge;
    private javax.swing.JCheckBox cbBoeschungsneigung;
    private javax.swing.JCheckBox cbCbmprom;
    private javax.swing.JCheckBox cbDeichkronenbreite;
    private javax.swing.JCheckBox cbDrei;
    private javax.swing.JCheckBox cbMZwei;
    private javax.swing.JCheckBox cbRandstreifenbreite;
    private javax.swing.JCheckBox cbSchnitttiefe;
    private javax.swing.JCheckBox cbSohlbreite;
    private javax.swing.JCheckBox cbSohle;
    private javax.swing.JCheckBox cbStueck;
    private javax.swing.JCheckBox cbStunden;
    private javax.swing.JCheckBox cbTeillaenge;
    private javax.swing.JCheckBox cbUfer;
    private javax.swing.JCheckBox cbUmfeld;
    private javax.swing.JCheckBox cbVorlandbreite;
    private de.cismet.cids.editors.DefaultBindableCheckboxField ccOperativeZiele;
    private de.cismet.cids.editors.DefaultBindableColorChooser dccColor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel jpAllgemein;
    private javax.swing.JPanel jpOperativeZiele;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblEinheit;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblKompartiment;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOptionaleFelder;
    private javax.swing.JLabel lblRegel;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panSpacingBottom;
    private javax.swing.JPanel panSpacingBottom1;
    private javax.swing.JTextField txtEinheit;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtRegel;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupGewerkEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupGewerkEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
        ccOperativeZiele.setBackgroundSelected(SELECT_COLOR);
        ccOperativeZiele.setBackgroundUnselected(UNSELECT_COLOR);

        if (readOnly) {
            RendererTools.makeReadOnly(txtEinheit);
            RendererTools.makeReadOnly(txtRegel);
            RendererTools.makeReadOnly(txtName);
            RendererTools.makeReadOnly(cbSohle);
            RendererTools.makeReadOnly(cbUfer);
            RendererTools.makeReadOnly(cbUmfeld);
            RendererTools.makeReadOnly(dccColor);
            RendererTools.makeReadOnly(cbBoeschungslaenge);
            RendererTools.makeReadOnly(cbBoeschungsneigung);
            RendererTools.makeReadOnly(cbDeichkronenbreite);
            RendererTools.makeReadOnly(cbRandstreifenbreite);
            RendererTools.makeReadOnly(cbSohlbreite);
            RendererTools.makeReadOnly(cbVorlandbreite);
            RendererTools.makeReadOnly(cbStueck);
            RendererTools.makeReadOnly(cbCbmprom);
            RendererTools.makeReadOnly(cbStunden);
            RendererTools.makeReadOnly(cbSchnitttiefe);
            RendererTools.makeReadOnly(cbMZwei);
            RendererTools.makeReadOnly(cbDrei);
            RendererTools.makeReadOnly(cbArbeitsbreite);
            RendererTools.makeReadOnly(cbTeillaenge);
            RendererTools.makeReadOnly(ccOperativeZiele);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpAllgemein = new javax.swing.JPanel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        lblRegel = new javax.swing.JLabel();
        txtRegel = new javax.swing.JTextField();
        txtEinheit = new javax.swing.JTextField();
        lblEinheit = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        cbSohle = new javax.swing.JCheckBox();
        cbUfer = new javax.swing.JCheckBox();
        cbUmfeld = new javax.swing.JCheckBox();
        lblKompartiment = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cbRandstreifenbreite = new javax.swing.JCheckBox();
        cbBoeschungslaenge = new javax.swing.JCheckBox();
        cbBoeschungsneigung = new javax.swing.JCheckBox();
        cbSohlbreite = new javax.swing.JCheckBox();
        cbDeichkronenbreite = new javax.swing.JCheckBox();
        cbVorlandbreite = new javax.swing.JCheckBox();
        cbCbmprom = new javax.swing.JCheckBox();
        cbStueck = new javax.swing.JCheckBox();
        cbStunden = new javax.swing.JCheckBox();
        cbSchnitttiefe = new javax.swing.JCheckBox();
        cbMZwei = new javax.swing.JCheckBox();
        cbDrei = new javax.swing.JCheckBox();
        cbArbeitsbreite = new javax.swing.JCheckBox();
        cbTeillaenge = new javax.swing.JCheckBox();
        lblOptionaleFelder = new javax.swing.JLabel();
        lblColor = new javax.swing.JLabel();
        dccColor = new de.cismet.cids.editors.DefaultBindableColorChooser();
        panSpacingBottom = new javax.swing.JPanel();
        jpOperativeZiele = new javax.swing.JPanel();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        ccOperativeZiele = new DefaultBindableCheckboxField(new CustomComparator());
        panSpacingBottom1 = new javax.swing.JPanel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(994, 500));
        setLayout(new java.awt.GridBagLayout());

        jpAllgemein.setOpaque(false);
        jpAllgemein.setLayout(new java.awt.GridBagLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblName.setText(org.openide.util.NbBundle.getMessage(GupGewerkEditor.class, "GupGewerkEditor.lblName.text")); // NOI18N
        lblName.setMaximumSize(new java.awt.Dimension(150, 17));
        lblName.setMinimumSize(new java.awt.Dimension(150, 17));
        lblName.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 15, 5, 5);
        jPanel2.add(lblName, gridBagConstraints);

        lblRegel.setText(org.openide.util.NbBundle.getMessage(GupGewerkEditor.class, "GupGewerkEditor.lblRegel.text")); // NOI18N
        lblRegel.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.lblRegel.toolTipText"));                                                               // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblRegel, gridBagConstraints);

        txtRegel.setMinimumSize(new java.awt.Dimension(550, 25));
        txtRegel.setPreferredSize(new java.awt.Dimension(550, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.aufmass_regel}"),
                txtRegel,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtRegel, gridBagConstraints);

        txtEinheit.setMinimumSize(new java.awt.Dimension(550, 25));
        txtEinheit.setPreferredSize(new java.awt.Dimension(550, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.einheit}"),
                txtEinheit,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtEinheit, gridBagConstraints);

        lblEinheit.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.lblEinheit.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblEinheit, gridBagConstraints);

        txtName.setMinimumSize(new java.awt.Dimension(550, 25));
        txtName.setPreferredSize(new java.awt.Dimension(550, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtName, gridBagConstraints);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridBagLayout());

        cbSohle.setText(org.openide.util.NbBundle.getMessage(GupGewerkEditor.class, "GupGewerkEditor.cbSohle.text")); // NOI18N
        cbSohle.setContentAreaFilled(false);
        cbSohle.setMaximumSize(new java.awt.Dimension(230, 22));
        cbSohle.setMinimumSize(new java.awt.Dimension(180, 22));
        cbSohle.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sohle}"),
                cbSohle,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        cbSohle.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbSohleActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel5.add(cbSohle, gridBagConstraints);

        cbUfer.setText(org.openide.util.NbBundle.getMessage(GupGewerkEditor.class, "GupGewerkEditor.cbUfer.text")); // NOI18N
        cbUfer.setContentAreaFilled(false);
        cbUfer.setMaximumSize(new java.awt.Dimension(230, 22));
        cbUfer.setMinimumSize(new java.awt.Dimension(180, 22));
        cbUfer.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ufer}"),
                cbUfer,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        cbUfer.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbUferActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel5.add(cbUfer, gridBagConstraints);

        cbUmfeld.setText(org.openide.util.NbBundle.getMessage(GupGewerkEditor.class, "GupGewerkEditor.cbUmfeld.text")); // NOI18N
        cbUmfeld.setContentAreaFilled(false);
        cbUmfeld.setMaximumSize(new java.awt.Dimension(230, 22));
        cbUmfeld.setMinimumSize(new java.awt.Dimension(180, 22));
        cbUmfeld.setPreferredSize(new java.awt.Dimension(140, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.umfeld}"),
                cbUmfeld,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        cbUmfeld.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbUmfeldActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel5.add(cbUmfeld, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jPanel5, gridBagConstraints);

        lblKompartiment.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.lblKompartiment.text")); // NOI18N
        lblKompartiment.setMaximumSize(new java.awt.Dimension(250, 17));
        lblKompartiment.setMinimumSize(new java.awt.Dimension(250, 17));
        lblKompartiment.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblKompartiment, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        cbRandstreifenbreite.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.cbRandstreifenbreite.text")); // NOI18N
        cbRandstreifenbreite.setContentAreaFilled(false);
        cbRandstreifenbreite.setMaximumSize(new java.awt.Dimension(230, 22));
        cbRandstreifenbreite.setMinimumSize(new java.awt.Dimension(180, 22));
        cbRandstreifenbreite.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.randstreifenbreite}"),
                cbRandstreifenbreite,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        cbRandstreifenbreite.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbRandstreifenbreiteActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(cbRandstreifenbreite, gridBagConstraints);

        cbBoeschungslaenge.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.cbBoeschungslaenge.text")); // NOI18N
        cbBoeschungslaenge.setContentAreaFilled(false);
        cbBoeschungslaenge.setMaximumSize(new java.awt.Dimension(230, 22));
        cbBoeschungslaenge.setMinimumSize(new java.awt.Dimension(180, 22));
        cbBoeschungslaenge.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.boeschungslaenge}"),
                cbBoeschungslaenge,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(cbBoeschungslaenge, gridBagConstraints);

        cbBoeschungsneigung.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.cbBoeschungsneigung.text")); // NOI18N
        cbBoeschungsneigung.setContentAreaFilled(false);
        cbBoeschungsneigung.setMaximumSize(new java.awt.Dimension(230, 22));
        cbBoeschungsneigung.setMinimumSize(new java.awt.Dimension(180, 22));
        cbBoeschungsneigung.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.boeschungsneigung}"),
                cbBoeschungsneigung,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(cbBoeschungsneigung, gridBagConstraints);

        cbSohlbreite.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.cbSohlbreite.text")); // NOI18N
        cbSohlbreite.setContentAreaFilled(false);
        cbSohlbreite.setMaximumSize(new java.awt.Dimension(230, 22));
        cbSohlbreite.setMinimumSize(new java.awt.Dimension(180, 22));
        cbSohlbreite.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sohlbreite}"),
                cbSohlbreite,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(cbSohlbreite, gridBagConstraints);

        cbDeichkronenbreite.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.cbDeichkronenbreite.text")); // NOI18N
        cbDeichkronenbreite.setContentAreaFilled(false);
        cbDeichkronenbreite.setMaximumSize(new java.awt.Dimension(230, 22));
        cbDeichkronenbreite.setMinimumSize(new java.awt.Dimension(180, 22));
        cbDeichkronenbreite.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.deichkronenbreite}"),
                cbDeichkronenbreite,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(cbDeichkronenbreite, gridBagConstraints);

        cbVorlandbreite.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.cbVorlandbreite.text")); // NOI18N
        cbVorlandbreite.setContentAreaFilled(false);
        cbVorlandbreite.setMaximumSize(new java.awt.Dimension(230, 22));
        cbVorlandbreite.setMinimumSize(new java.awt.Dimension(180, 22));
        cbVorlandbreite.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.vorlandbreite}"),
                cbVorlandbreite,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(cbVorlandbreite, gridBagConstraints);

        cbCbmprom.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.cbCbmprom.text")); // NOI18N
        cbCbmprom.setContentAreaFilled(false);
        cbCbmprom.setMaximumSize(new java.awt.Dimension(230, 22));
        cbCbmprom.setMinimumSize(new java.awt.Dimension(180, 22));
        cbCbmprom.setPreferredSize(new java.awt.Dimension(140, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.cbmprom}"),
                cbCbmprom,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(cbCbmprom, gridBagConstraints);

        cbStueck.setText(org.openide.util.NbBundle.getMessage(GupGewerkEditor.class, "GupGewerkEditor.cbStueck.text")); // NOI18N
        cbStueck.setContentAreaFilled(false);
        cbStueck.setMaximumSize(new java.awt.Dimension(230, 22));
        cbStueck.setMinimumSize(new java.awt.Dimension(180, 22));
        cbStueck.setPreferredSize(new java.awt.Dimension(140, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stueck}"),
                cbStueck,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(cbStueck, gridBagConstraints);

        cbStunden.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.cbStunden.text")); // NOI18N
        cbStunden.setContentAreaFilled(false);
        cbStunden.setMaximumSize(new java.awt.Dimension(230, 22));
        cbStunden.setMinimumSize(new java.awt.Dimension(180, 22));
        cbStunden.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stunden}"),
                cbStunden,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(cbStunden, gridBagConstraints);

        cbSchnitttiefe.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.cbSchnitttiefe.text")); // NOI18N
        cbSchnitttiefe.setContentAreaFilled(false);
        cbSchnitttiefe.setMaximumSize(new java.awt.Dimension(230, 22));
        cbSchnitttiefe.setMinimumSize(new java.awt.Dimension(180, 22));
        cbSchnitttiefe.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.schnitttiefe}"),
                cbSchnitttiefe,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(cbSchnitttiefe, gridBagConstraints);

        cbMZwei.setText(org.openide.util.NbBundle.getMessage(GupGewerkEditor.class, "GupGewerkEditor.cbMZwei.text")); // NOI18N
        cbMZwei.setContentAreaFilled(false);
        cbMZwei.setMaximumSize(new java.awt.Dimension(230, 22));
        cbMZwei.setMinimumSize(new java.awt.Dimension(180, 22));
        cbMZwei.setPreferredSize(new java.awt.Dimension(140, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.m_zwei}"),
                cbMZwei,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(cbMZwei, gridBagConstraints);

        cbDrei.setText(org.openide.util.NbBundle.getMessage(GupGewerkEditor.class, "GupGewerkEditor.cbDrei.text")); // NOI18N
        cbDrei.setContentAreaFilled(false);
        cbDrei.setMaximumSize(new java.awt.Dimension(230, 22));
        cbDrei.setMinimumSize(new java.awt.Dimension(180, 22));
        cbDrei.setPreferredSize(new java.awt.Dimension(140, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.m_drei}"),
                cbDrei,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(cbDrei, gridBagConstraints);

        cbArbeitsbreite.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.cbArbeitsbreite.text")); // NOI18N
        cbArbeitsbreite.setContentAreaFilled(false);
        cbArbeitsbreite.setMaximumSize(new java.awt.Dimension(230, 22));
        cbArbeitsbreite.setMinimumSize(new java.awt.Dimension(180, 22));
        cbArbeitsbreite.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.arbeitsbreite}"),
                cbArbeitsbreite,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        cbArbeitsbreite.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbArbeitsbreiteActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(cbArbeitsbreite, gridBagConstraints);

        cbTeillaenge.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.cbTeillaenge.text")); // NOI18N
        cbTeillaenge.setContentAreaFilled(false);
        cbTeillaenge.setMaximumSize(new java.awt.Dimension(230, 22));
        cbTeillaenge.setMinimumSize(new java.awt.Dimension(180, 22));
        cbTeillaenge.setPreferredSize(new java.awt.Dimension(180, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.teillaenge}"),
                cbTeillaenge,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel1.add(cbTeillaenge, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 10, 5);
        jPanel2.add(jPanel1, gridBagConstraints);

        lblOptionaleFelder.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.lblOptionaleFelder.text")); // NOI18N
        lblOptionaleFelder.setMaximumSize(new java.awt.Dimension(250, 17));
        lblOptionaleFelder.setMinimumSize(new java.awt.Dimension(250, 17));
        lblOptionaleFelder.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblOptionaleFelder, gridBagConstraints);

        lblColor.setText(org.openide.util.NbBundle.getMessage(GupGewerkEditor.class, "GupGewerkEditor.lblColor.text")); // NOI18N
        lblColor.setMaximumSize(new java.awt.Dimension(250, 17));
        lblColor.setMinimumSize(new java.awt.Dimension(250, 17));
        lblColor.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblColor, gridBagConstraints);

        dccColor.setMinimumSize(new java.awt.Dimension(250, 20));
        dccColor.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.color}"),
                dccColor,
                org.jdesktop.beansbinding.BeanProperty.create("color"));
        binding.setConverter(((DefaultBindableColorChooser)dccColor).getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanel2.add(dccColor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jPanel2, gridBagConstraints);

        panSpacingBottom.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panInfoContent.add(panSpacingBottom, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jpAllgemein.add(panInfo, gridBagConstraints);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.jpAllgemein.TabConstraints.tabTitle"),
            jpAllgemein); // NOI18N

        jpOperativeZiele.setOpaque(false);
        jpOperativeZiele.setLayout(new java.awt.GridBagLayout());

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                GupGewerkEditor.class,
                "GupGewerkEditor.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo1.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        ccOperativeZiele.setMinimumSize(new java.awt.Dimension(370, 340));
        ccOperativeZiele.setOpaque(false);
        ccOperativeZiele.setPreferredSize(new java.awt.Dimension(550, 340));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        jPanel3.add(ccOperativeZiele, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(jPanel3, gridBagConstraints);

        panSpacingBottom1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panInfoContent1.add(panSpacingBottom1, gridBagConstraints);

        panInfo1.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jpOperativeZiele.add(panInfo1, gridBagConstraints);

        jTabbedPane1.addTab("Regeln für Pflegeziele", jpOperativeZiele);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jTabbedPane1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbRandstreifenbreiteActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbRandstreifenbreiteActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbRandstreifenbreiteActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbArbeitsbreiteActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbArbeitsbreiteActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbArbeitsbreiteActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbUferActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbUferActionPerformed
        decider.setUfer(cbUfer.isSelected());
        ccOperativeZiele.refreshCheckboxState(decider, true, true);
    }                                                                          //GEN-LAST:event_cbUferActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbUmfeldActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbUmfeldActionPerformed
        decider.setUmfeld(cbUmfeld.isSelected());
        ccOperativeZiele.refreshCheckboxState(decider, true, true);
    }                                                                            //GEN-LAST:event_cbUmfeldActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbSohleActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbSohleActionPerformed
        decider.setSohle(cbSohle.isSelected());
        ccOperativeZiele.refreshCheckboxState(decider, true, true);
    }                                                                           //GEN-LAST:event_cbSohleActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            decider.setSohle((Boolean)cidsBean.getProperty("sohle"));
            decider.setUfer((Boolean)cidsBean.getProperty("ufer"));
            decider.setUmfeld((Boolean)cidsBean.getProperty("umfeld"));

            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
            ccOperativeZiele.refreshCheckboxState(decider, true, false);
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Gewerk: " + ((cidsBean.toString() == null) ? "unbenannt" : cidsBean.toString());
    }

    @Override
    public void setTitle(final String title) {
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
    }

    @Override
    public boolean prepareForSave() {
        String expression = txtRegel.getText();
        if ((expression != null) && !expression.equals("")) {
            expression = GupLosEditor.replaceVariablesFromExpression(expression, null);

            final ExpressionEvaluator eval = new ExpressionEvaluator();

            final boolean valid = eval.isValidExpression(expression);

            if (!valid || (eval.eval(expression) == null)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Ungültige Formel zur Berechnung des Aufmaßes. Die Maßnahme kann so nicht gespeichert werden.",
                    "Fehler!",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
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
            "gewerk",
            1,
            1280,
            1024);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomComparator implements Comparator<MetaObject> {

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final MetaObject o1, final MetaObject o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class OperativeZieleDecider implements FieldStateDecider {

        //~ Instance fields ----------------------------------------------------

        private boolean sohle = false;
        private boolean ufer = false;
        private boolean umfeld = false;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SubTypeDecider object.
         */
        public OperativeZieleDecider() {
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public boolean isCheckboxForClassActive(final MetaObject mo) {
            return (sohle && (mo.getBean().getProperty("sohle") != null)
                            && (Boolean)mo.getBean().getProperty("sohle"))
                        || (ufer && ((Boolean)mo.getBean().getProperty("ufer") != null)
                            && (Boolean)mo.getBean().getProperty("ufer"))
                        || (umfeld && ((Boolean)mo.getBean().getProperty("umfeld") != null)
                            && (Boolean)mo.getBean().getProperty("umfeld"));
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the sohle
         */
        public boolean isSohle() {
            return sohle;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  sohle  the sohle to set
         */
        public void setSohle(Boolean sohle) {
            sohle = ((sohle == null) ? false : sohle);
            this.sohle = sohle;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the ufer
         */
        public boolean isUfer() {
            return ufer;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  ufer  the ufer to set
         */
        public void setUfer(Boolean ufer) {
            ufer = ((ufer == null) ? false : ufer);
            this.ufer = ufer;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the umfeld
         */
        public boolean isUmfeld() {
            return umfeld;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  umfeld  the umfeld to set
         */
        public void setUmfeld(Boolean umfeld) {
            umfeld = ((umfeld == null) ? false : umfeld);
            this.umfeld = umfeld;
        }
    }
}
