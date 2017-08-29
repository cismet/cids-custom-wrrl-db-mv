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
import java.awt.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.ExpressionEvaluator;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.*;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupMassnahmenartEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    BeanInitializerProvider {

    //~ Static fields/initializers ---------------------------------------------

    public static final Color SELECT_COLOR = new Color(212, 238, 94);
    public static final Color UNSELECT_COLOR = new Color(255, 66, 66);

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupMassnahmenartEditor.class);
    private static final int INTERVAL_TWO_TIMES = 4;
    private static GupMassnahmenartEditor lastInstance = null;

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private OperativeZieleDecider decider = new OperativeZieleDecider();
    private VermeidungsgruppenDecider vdecider = new VermeidungsgruppenDecider();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbAllAnf;
    private javax.swing.JCheckBox cbArbeitsbreite;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbAusfuehrungszeitraum;
    private javax.swing.JCheckBox cbBoeschungslaenge;
    private javax.swing.JCheckBox cbBoeschungsneigung;
    private javax.swing.JCheckBox cbCbmprom;
    private javax.swing.JCheckBox cbDeichkronenbreite;
    private javax.swing.JCheckBox cbDrei;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEinsatzvariante;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGeraet;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGewerk;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbInterval;
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
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbVerbleib;
    private javax.swing.JCheckBox cbVorlandbreite;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbZweiterAusfuehrungszeitraum;
    private de.cismet.cids.editors.DefaultBindableCheckboxField ccOperativeZiele;
    private de.cismet.cids.editors.DefaultBindableCheckboxField ccVermeidungsgruppen;
    private de.cismet.cids.editors.DefaultBindableColorChooser dccColor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel jpAllgemein;
    private javax.swing.JPanel jpGeschuetzteArten;
    private javax.swing.JPanel jpOperativeZiele;
    private javax.swing.JScrollPane jsLeistungstext;
    private javax.swing.JTextArea jtLeistungstext;
    private javax.swing.JLabel lblAusfuehrungszeitraum;
    private javax.swing.JLabel lblBeschreibung;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblEinheit;
    private javax.swing.JLabel lblEinsatzvariante;
    private javax.swing.JLabel lblGeraet;
    private javax.swing.JLabel lblGewerk;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblInterval;
    private javax.swing.JLabel lblKompartiment;
    private javax.swing.JLabel lblLeistungstext;
    private javax.swing.JLabel lblOptionaleFelder;
    private javax.swing.JLabel lblRegel;
    private javax.swing.JLabel lblUrl;
    private javax.swing.JLabel lblUrlSmall;
    private javax.swing.JLabel lblVerbleib;
    private javax.swing.JLabel lblZweiterAusfuehrungszeitraum;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo2;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent2;
    private javax.swing.JPanel panSpacingBottom;
    private javax.swing.JPanel panSpacingBottom1;
    private javax.swing.JPanel panSpacingBottom2;
    private javax.swing.JLabel txtAlAnf;
    private javax.swing.JTextField txtBeschreibung;
    private javax.swing.JTextField txtEinheit;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtRegel;
    private javax.swing.JTextField txtUrl;
    private javax.swing.JTextField txtUrlSmall;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupMassnahmenartEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupMassnahmenartEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
        ccOperativeZiele.setBackgroundSelected(SELECT_COLOR);
        ccOperativeZiele.setBackgroundUnselected(UNSELECT_COLOR);
        ccVermeidungsgruppen.setBackgroundSelected(SELECT_COLOR);
        ccVermeidungsgruppen.setBackgroundUnselected(UNSELECT_COLOR);
        jTabbedPane1.setUI(new TabbedPaneUITransparent());

        if (readOnly) {
            RendererTools.makeReadOnly(jtLeistungstext);
            RendererTools.makeReadOnly(txtBeschreibung);
            RendererTools.makeReadOnly(txtId);
            RendererTools.makeReadOnly(cbBoeschungslaenge);
            RendererTools.makeReadOnly(cbBoeschungsneigung);
            RendererTools.makeReadOnly(cbDeichkronenbreite);
            RendererTools.makeReadOnly(cbRandstreifenbreite);
            RendererTools.makeReadOnly(cbSohlbreite);
            RendererTools.makeReadOnly(cbVorlandbreite);
            RendererTools.makeReadOnly(cbStueck);
            RendererTools.makeReadOnly(cbCbmprom);
            RendererTools.makeReadOnly(cbSohle);
            RendererTools.makeReadOnly(cbUfer);
            RendererTools.makeReadOnly(cbUmfeld);
            RendererTools.makeReadOnly(dccColor);
            RendererTools.makeReadOnly(cbGeraet);
            RendererTools.makeReadOnly(cbGewerk);
            RendererTools.makeReadOnly(cbEinsatzvariante);
            RendererTools.makeReadOnly(cbInterval);
            RendererTools.makeReadOnly(cbAusfuehrungszeitraum);
            RendererTools.makeReadOnly(cbVerbleib);
            RendererTools.makeReadOnly(cbZweiterAusfuehrungszeitraum);
            RendererTools.makeReadOnly(ccOperativeZiele);
            RendererTools.makeReadOnly(ccVermeidungsgruppen);
            RendererTools.makeReadOnly(txtRegel);
            RendererTools.makeReadOnly(txtEinheit);
            RendererTools.makeReadOnly(cbStunden);
            RendererTools.makeReadOnly(cbSchnitttiefe);
            RendererTools.makeReadOnly(txtUrl);
            RendererTools.makeReadOnly(txtUrlSmall);
            RendererTools.makeReadOnly(cbAllAnf);
            RendererTools.makeReadOnly(cbMZwei);
            RendererTools.makeReadOnly(cbDrei);
            RendererTools.makeReadOnly(cbArbeitsbreite);
            RendererTools.makeReadOnly(cbTeillaenge);
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
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblBeschreibung = new javax.swing.JLabel();
        txtBeschreibung = new javax.swing.JTextField();
        lblLeistungstext = new javax.swing.JLabel();
        jsLeistungstext = new javax.swing.JScrollPane();
        jtLeistungstext = new javax.swing.JTextArea();
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
        lblKompartiment = new javax.swing.JLabel();
        dccColor = new de.cismet.cids.editors.DefaultBindableColorChooser();
        lblColor = new javax.swing.JLabel();
        lblInterval = new javax.swing.JLabel();
        cbInterval = new ScrollableComboBox();
        lblAusfuehrungszeitraum = new javax.swing.JLabel();
        cbAusfuehrungszeitraum = new ScrollableComboBox();
        lblGewerk = new javax.swing.JLabel();
        lblEinsatzvariante = new javax.swing.JLabel();
        lblGeraet = new javax.swing.JLabel();
        cbGewerk = new ScrollableComboBox();
        cbEinsatzvariante = new ScrollableComboBox();
        cbGeraet = new ScrollableComboBox();
        lblVerbleib = new javax.swing.JLabel();
        cbVerbleib = new ScrollableComboBox();
        lblZweiterAusfuehrungszeitraum = new javax.swing.JLabel();
        cbZweiterAusfuehrungszeitraum = new ScrollableComboBox();
        lblRegel = new javax.swing.JLabel();
        txtRegel = new javax.swing.JTextField();
        lblEinheit = new javax.swing.JLabel();
        txtEinheit = new javax.swing.JTextField();
        lblUrlSmall = new javax.swing.JLabel();
        lblUrl = new javax.swing.JLabel();
        txtUrlSmall = new javax.swing.JTextField();
        txtUrl = new javax.swing.JTextField();
        txtAlAnf = new javax.swing.JLabel();
        cbAllAnf = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        cbSohle = new javax.swing.JCheckBox();
        cbUfer = new javax.swing.JCheckBox();
        cbUmfeld = new javax.swing.JCheckBox();
        panSpacingBottom = new javax.swing.JPanel();
        jpOperativeZiele = new javax.swing.JPanel();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        ccOperativeZiele = new DefaultBindableCheckboxField(new CustomComparator());
        panSpacingBottom1 = new javax.swing.JPanel();
        jpGeschuetzteArten = new javax.swing.JPanel();
        panInfo2 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        ccVermeidungsgruppen = new DefaultBindableCheckboxField(new CustomComparator());
        panSpacingBottom2 = new javax.swing.JPanel();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jpAllgemein.setOpaque(false);
        jpAllgemein.setLayout(new java.awt.GridBagLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblId.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblId.text")); // NOI18N
        lblId.setMaximumSize(new java.awt.Dimension(250, 17));
        lblId.setMinimumSize(new java.awt.Dimension(250, 17));
        lblId.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 5, 5);
        jPanel2.add(lblId, gridBagConstraints);

        txtId.setMinimumSize(new java.awt.Dimension(550, 25));
        txtId.setPreferredSize(new java.awt.Dimension(550, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massnahmen_id}"),
                txtId,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel2.add(txtId, gridBagConstraints);

        lblBeschreibung.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblBeschreibung.text")); // NOI18N
        lblBeschreibung.setMaximumSize(new java.awt.Dimension(250, 17));
        lblBeschreibung.setMinimumSize(new java.awt.Dimension(250, 17));
        lblBeschreibung.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblBeschreibung, gridBagConstraints);

        txtBeschreibung.setMinimumSize(new java.awt.Dimension(300, 25));
        txtBeschreibung.setPreferredSize(new java.awt.Dimension(300, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtBeschreibung,
                org.jdesktop.beansbinding.BeanProperty.create("text"),
                "");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtBeschreibung, gridBagConstraints);

        lblLeistungstext.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblLeistungstext.text")); // NOI18N
        lblLeistungstext.setMaximumSize(new java.awt.Dimension(250, 17));
        lblLeistungstext.setMinimumSize(new java.awt.Dimension(250, 17));
        lblLeistungstext.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblLeistungstext, gridBagConstraints);

        jsLeistungstext.setMaximumSize(new java.awt.Dimension(327, 320));
        jsLeistungstext.setMinimumSize(new java.awt.Dimension(327, 120));

        jtLeistungstext.setColumns(20);
        jtLeistungstext.setRows(4);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.leistungstext}"),
                jtLeistungstext,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jsLeistungstext.setViewportView(jtLeistungstext);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jsLeistungstext, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        cbRandstreifenbreite.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbRandstreifenbreite.text")); // NOI18N
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
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbBoeschungslaenge.text")); // NOI18N
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
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbBoeschungsneigung.text")); // NOI18N
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
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbSohlbreite.text")); // NOI18N
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
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbDeichkronenbreite.text")); // NOI18N
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
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbVorlandbreite.text")); // NOI18N
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
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbCbmprom.text")); // NOI18N
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

        cbStueck.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbStueck.text")); // NOI18N
        cbStueck.setContentAreaFilled(false);
        cbStueck.setMaximumSize(new java.awt.Dimension(230, 22));
        cbStueck.setMinimumSize(new java.awt.Dimension(180, 22));
        cbStueck.setPreferredSize(new java.awt.Dimension(140, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stueck}"),
                cbStueck,
                org.jdesktop.beansbinding.BeanProperty.create("selected"),
                "StueckId");
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
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbStunden.text")); // NOI18N
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
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbSchnitttiefe.text")); // NOI18N
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

        cbMZwei.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbMZwei.text")); // NOI18N
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

        cbDrei.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbDrei.text")); // NOI18N
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
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbArbeitsbreite.text")); // NOI18N
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
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbTeillaenge.text")); // NOI18N
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
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 10, 0);
        jPanel2.add(jPanel1, gridBagConstraints);

        lblOptionaleFelder.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblOptionaleFelder.text")); // NOI18N
        lblOptionaleFelder.setMaximumSize(new java.awt.Dimension(250, 17));
        lblOptionaleFelder.setMinimumSize(new java.awt.Dimension(250, 17));
        lblOptionaleFelder.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblOptionaleFelder, gridBagConstraints);

        lblKompartiment.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblKompartiment.text")); // NOI18N
        lblKompartiment.setMaximumSize(new java.awt.Dimension(250, 17));
        lblKompartiment.setMinimumSize(new java.awt.Dimension(250, 17));
        lblKompartiment.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblKompartiment, gridBagConstraints);

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
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanel2.add(dccColor, gridBagConstraints);

        lblColor.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblColor.text")); // NOI18N
        lblColor.setMaximumSize(new java.awt.Dimension(250, 17));
        lblColor.setMinimumSize(new java.awt.Dimension(250, 17));
        lblColor.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblColor, gridBagConstraints);

        lblInterval.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblInterval.text")); // NOI18N
        lblInterval.setMaximumSize(new java.awt.Dimension(250, 17));
        lblInterval.setMinimumSize(new java.awt.Dimension(250, 17));
        lblInterval.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblInterval, gridBagConstraints);

        cbInterval.setMaximumSize(new java.awt.Dimension(290, 20));
        cbInterval.setMinimumSize(new java.awt.Dimension(290, 20));
        cbInterval.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.intervall}"),
                cbInterval,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbInterval.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbIntervalItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbInterval, gridBagConstraints);

        lblAusfuehrungszeitraum.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblAusfuehrungszeitraum.text")); // NOI18N
        lblAusfuehrungszeitraum.setMaximumSize(new java.awt.Dimension(250, 17));
        lblAusfuehrungszeitraum.setMinimumSize(new java.awt.Dimension(250, 17));
        lblAusfuehrungszeitraum.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblAusfuehrungszeitraum, gridBagConstraints);

        cbAusfuehrungszeitraum.setMaximumSize(new java.awt.Dimension(290, 20));
        cbAusfuehrungszeitraum.setMinimumSize(new java.awt.Dimension(290, 20));
        cbAusfuehrungszeitraum.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ausfuehrungszeitpunkt}"),
                cbAusfuehrungszeitraum,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbAusfuehrungszeitraum, gridBagConstraints);

        lblGewerk.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblGewerk.text")); // NOI18N
        lblGewerk.setMaximumSize(new java.awt.Dimension(250, 17));
        lblGewerk.setMinimumSize(new java.awt.Dimension(250, 17));
        lblGewerk.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblGewerk, gridBagConstraints);

        lblEinsatzvariante.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblEinsatzvariante.text")); // NOI18N
        lblEinsatzvariante.setMaximumSize(new java.awt.Dimension(250, 17));
        lblEinsatzvariante.setMinimumSize(new java.awt.Dimension(250, 17));
        lblEinsatzvariante.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblEinsatzvariante, gridBagConstraints);

        lblGeraet.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblGeraet.text")); // NOI18N
        lblGeraet.setMaximumSize(new java.awt.Dimension(250, 17));
        lblGeraet.setMinimumSize(new java.awt.Dimension(250, 17));
        lblGeraet.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblGeraet, gridBagConstraints);

        cbGewerk.setMaximumSize(new java.awt.Dimension(290, 20));
        cbGewerk.setMinimumSize(new java.awt.Dimension(290, 20));
        cbGewerk.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewerk}"),
                cbGewerk,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGewerk.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbGewerkItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbGewerk, gridBagConstraints);

        cbEinsatzvariante.setMaximumSize(new java.awt.Dimension(290, 20));
        cbEinsatzvariante.setMinimumSize(new java.awt.Dimension(290, 20));
        cbEinsatzvariante.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.einsatzvariante}"),
                cbEinsatzvariante,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbEinsatzvariante.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbEinsatzvarianteItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbEinsatzvariante, gridBagConstraints);

        cbGeraet.setMaximumSize(new java.awt.Dimension(290, 20));
        cbGeraet.setMinimumSize(new java.awt.Dimension(290, 20));
        cbGeraet.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.erlaubte_geraete}"),
                cbGeraet,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGeraet.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbGeraetItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbGeraet, gridBagConstraints);

        lblVerbleib.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblVerbleib.text")); // NOI18N
        lblVerbleib.setMaximumSize(new java.awt.Dimension(250, 17));
        lblVerbleib.setMinimumSize(new java.awt.Dimension(250, 17));
        lblVerbleib.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblVerbleib, gridBagConstraints);

        cbVerbleib.setMaximumSize(new java.awt.Dimension(290, 20));
        cbVerbleib.setMinimumSize(new java.awt.Dimension(290, 20));
        cbVerbleib.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.verbleib}"),
                cbVerbleib,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbVerbleib, gridBagConstraints);

        lblZweiterAusfuehrungszeitraum.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblZweiterAusfuehrungszeitraum.text")); // NOI18N
        lblZweiterAusfuehrungszeitraum.setMaximumSize(new java.awt.Dimension(250, 17));
        lblZweiterAusfuehrungszeitraum.setMinimumSize(new java.awt.Dimension(250, 17));
        lblZweiterAusfuehrungszeitraum.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblZweiterAusfuehrungszeitraum, gridBagConstraints);

        cbZweiterAusfuehrungszeitraum.setMaximumSize(new java.awt.Dimension(290, 20));
        cbZweiterAusfuehrungszeitraum.setMinimumSize(new java.awt.Dimension(290, 20));
        cbZweiterAusfuehrungszeitraum.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zweiter_ausfuehrungszeitpunkt}"),
                cbZweiterAusfuehrungszeitraum,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbZweiterAusfuehrungszeitraum, gridBagConstraints);

        lblRegel.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblRegel.text"));        // NOI18N
        lblRegel.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblRegel.toolTipText")); // NOI18N
        lblRegel.setMaximumSize(new java.awt.Dimension(250, 17));
        lblRegel.setMinimumSize(new java.awt.Dimension(250, 17));
        lblRegel.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblRegel, gridBagConstraints);

        txtRegel.setMinimumSize(new java.awt.Dimension(300, 25));
        txtRegel.setPreferredSize(new java.awt.Dimension(300, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.aufmass_regel}"),
                txtRegel,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtRegel, gridBagConstraints);

        lblEinheit.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblEinheit.text"));        // NOI18N
        lblEinheit.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblEinheit.toolTipText")); // NOI18N
        lblEinheit.setMaximumSize(new java.awt.Dimension(250, 17));
        lblEinheit.setMinimumSize(new java.awt.Dimension(250, 17));
        lblEinheit.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblEinheit, gridBagConstraints);

        txtEinheit.setMinimumSize(new java.awt.Dimension(300, 25));
        txtEinheit.setPreferredSize(new java.awt.Dimension(300, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.einheit}"),
                txtEinheit,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtEinheit, gridBagConstraints);

        lblUrlSmall.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblUrlSmall.text")); // NOI18N
        lblUrlSmall.setMaximumSize(new java.awt.Dimension(250, 17));
        lblUrlSmall.setMinimumSize(new java.awt.Dimension(250, 17));
        lblUrlSmall.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblUrlSmall, gridBagConstraints);

        lblUrl.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblUrl.text")); // NOI18N
        lblUrl.setMaximumSize(new java.awt.Dimension(250, 17));
        lblUrl.setMinimumSize(new java.awt.Dimension(250, 17));
        lblUrl.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblUrl, gridBagConstraints);

        txtUrlSmall.setMinimumSize(new java.awt.Dimension(300, 25));
        txtUrlSmall.setPreferredSize(new java.awt.Dimension(300, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.urlSmall}"),
                txtUrlSmall,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtUrlSmall, gridBagConstraints);

        txtUrl.setMinimumSize(new java.awt.Dimension(300, 25));
        txtUrl.setPreferredSize(new java.awt.Dimension(300, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.url}"),
                txtUrl,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtUrl, gridBagConstraints);

        txtAlAnf.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.txtAlAnf.text"));        // NOI18N
        txtAlAnf.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.txtAlAnf.toolTipText")); // NOI18N
        txtAlAnf.setMaximumSize(new java.awt.Dimension(250, 17));
        txtAlAnf.setMinimumSize(new java.awt.Dimension(250, 17));
        txtAlAnf.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(txtAlAnf, gridBagConstraints);

        cbAllAnf.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbAllAnf.text")); // NOI18N
        cbAllAnf.setContentAreaFilled(false);
        cbAllAnf.setMaximumSize(new java.awt.Dimension(230, 22));
        cbAllAnf.setMinimumSize(new java.awt.Dimension(230, 22));
        cbAllAnf.setPreferredSize(new java.awt.Dimension(230, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.erfuellt_al_anf}"),
                cbAllAnf,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbAllAnf, gridBagConstraints);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridBagLayout());

        cbSohle.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbSohle.text")); // NOI18N
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
        binding.setSourceUnreadableValue(false);
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

        cbUfer.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbUfer.text")); // NOI18N
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
        binding.setSourceUnreadableValue(false);
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

        cbUmfeld.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.cbUmfeld.text")); // NOI18N
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
        binding.setSourceUnreadableValue(false);
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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jPanel5, gridBagConstraints);

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
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.jpAllgemein.title"),
            jpAllgemein); // NOI18N

        jpOperativeZiele.setOpaque(false);
        jpOperativeZiele.setLayout(new java.awt.GridBagLayout());

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo1.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        ccOperativeZiele.setMinimumSize(new java.awt.Dimension(370, 340));
        ccOperativeZiele.setOpaque(false);
        ccOperativeZiele.setPreferredSize(new java.awt.Dimension(550, 340));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.operative_ziele}"),
                ccOperativeZiele,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

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

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.jpOperativeZiele.title"),
            jpOperativeZiele); // NOI18N

        jpGeschuetzteArten.setOpaque(false);
        jpGeschuetzteArten.setLayout(new java.awt.GridBagLayout());

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblHeading2.text")); // NOI18N
        panHeadInfo2.add(lblHeading2);

        panInfo2.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        ccVermeidungsgruppen.setMinimumSize(new java.awt.Dimension(550, 320));
        ccVermeidungsgruppen.setOpaque(false);
        ccVermeidungsgruppen.setPreferredSize(new java.awt.Dimension(550, 320));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.vermeidungsgruppen}"),
                ccVermeidungsgruppen,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        jPanel4.add(ccVermeidungsgruppen, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent2.add(jPanel4, gridBagConstraints);

        panSpacingBottom2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panInfoContent2.add(panSpacingBottom2, gridBagConstraints);

        panInfo2.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jpGeschuetzteArten.add(panInfo2, gridBagConstraints);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor"),
            jpGeschuetzteArten); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
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
    private void cbGewerkItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbGewerkItemStateChanged
        final Object selectedItem = evt.getItem();
        final String rule = txtRegel.getText();
        final String maesure = txtEinheit.getText();

        if ((selectedItem instanceof CidsBean) && ((rule == null) || rule.equals(""))) {
            final CidsBean bean = (CidsBean)selectedItem;
            txtRegel.setToolTipText("Geerbte Regel: " + String.valueOf(bean.getProperty("aufmass_regel")));
        } else {
            txtRegel.setToolTipText("");
        }

        if ((selectedItem instanceof CidsBean) && ((maesure == null) || maesure.equals(""))) {
            final CidsBean bean = (CidsBean)selectedItem;
            txtEinheit.setToolTipText("Geerbte Einheit: " + String.valueOf(bean.getProperty("einheit")));
        } else {
            txtEinheit.setToolTipText("");
        }
    } //GEN-LAST:event_cbGewerkItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbEinsatzvarianteItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbEinsatzvarianteItemStateChanged
        // TODO add your handling code here:
    } //GEN-LAST:event_cbEinsatzvarianteItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbGeraetItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbGeraetItemStateChanged
        // TODO add your handling code here:
    } //GEN-LAST:event_cbGeraetItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbIntervalItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbIntervalItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                final CidsBean bean = (CidsBean)evt.getItem();
                if (!readOnly) {
                    cbZweiterAusfuehrungszeitraum.setEnabled((bean != null)
                                && bean.getProperty("id").equals(INTERVAL_TWO_TIMES));
                }
            }
        }
    }                                                                             //GEN-LAST:event_cbIntervalItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbSohleActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbSohleActionPerformed
        decider.setSohle(cbSohle.isSelected());
        ccOperativeZiele.refreshCheckboxState(decider, true, true);
    }                                                                           //GEN-LAST:event_cbSohleActionPerformed

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
    private void cbArbeitsbreiteActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbArbeitsbreiteActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbArbeitsbreiteActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbRandstreifenbreiteActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbRandstreifenbreiteActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbRandstreifenbreiteActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        dispose();
        this.cidsBean = cidsBean;

        if (!readOnly) {
            lastInstance = this;
        }

        if (cidsBean != null) {
            decider.setSohle((Boolean)cidsBean.getProperty("sohle"));
            decider.setUfer((Boolean)cidsBean.getProperty("ufer"));
            decider.setUmfeld((Boolean)cidsBean.getProperty("umfeld"));
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            final CidsBean bean = (CidsBean)cidsBean.getProperty("intervall");
            if (!readOnly) {
                cbZweiterAusfuehrungszeitraum.setEnabled((bean != null)
                            && bean.getProperty("id").equals(INTERVAL_TWO_TIMES));
            }
            ccOperativeZiele.refreshCheckboxState(decider, true, false);
            ccVermeidungsgruppen.refreshCheckboxState(vdecider, true, false);
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Maßnahmenart: " + ((cidsBean.getProperty("name") != null) ? cidsBean.toString() : "");
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
            "n!emal$99",
            "gup_massnahmenart",
            11,
            1280,
            1024);
    }

    @Override
    public BeanInitializer getBeanInitializer() {
        return new MassnahmenartInitializer(cidsBean);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class MassnahmenartInitializer extends DefaultBeanInitializer implements BeanInitializerForcePaste {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new KartierabschnittInitializer object.
         *
         * @param  cidsBean  DOCUMENT ME!
         */
        public MassnahmenartInitializer(final CidsBean cidsBean) {
            super(cidsBean);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void initializeBean(final CidsBean beanToInit) throws Exception {
            super.initializeBean(beanToInit);

            if (lastInstance != null) {
                lastInstance.setCidsBean(beanToInit);
            }
        }

        @Override
        protected void processSimpleProperty(final CidsBean beanToInit,
                final String propertyName,
                final Object simpleValueToProcess) throws Exception {
            super.processSimpleProperty(beanToInit, propertyName, simpleValueToProcess);
        }

        @Override
        protected void processArrayProperty(final CidsBean beanToInit,
                final String propertyName,
                final Collection<CidsBean> arrayValueToProcess) throws Exception {
            final List<CidsBean> beans = CidsBeanSupport.getBeanCollectionFromProperty(
                    beanToInit,
                    propertyName);
            beans.clear();

            for (final CidsBean tmp : arrayValueToProcess) {
                beans.add(tmp);
            }
        }

        @Override
        protected void processComplexProperty(final CidsBean beanToInit,
                final String propertyName,
                final CidsBean complexValueToProcess) throws Exception {
            // flat copy
            beanToInit.setProperty(propertyName, complexValueToProcess);
        }
    }

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

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class VermeidungsgruppenDecider implements FieldStateDecider {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SubTypeDecider object.
         */
        public VermeidungsgruppenDecider() {
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public boolean isCheckboxForClassActive(final MetaObject mo) {
            return true;
        }
    }
}
