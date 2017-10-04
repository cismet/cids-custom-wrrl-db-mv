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
    private VermeidungsgruppenDecider vdecider = new VermeidungsgruppenDecider();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbAllAnf;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbAusfuehrungszeitraum;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEinsatzvariante;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGeraet;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGewerk;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbInterval;
    private javax.swing.JCheckBox cbSohle;
    private javax.swing.JCheckBox cbUfer;
    private javax.swing.JCheckBox cbUmfeld;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbVerbleib;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbZweiterAusfuehrungszeitraum;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jsLeistungstext;
    private javax.swing.JTextArea jtLeistungstext;
    private javax.swing.JLabel lblAusfuehrungszeitraum;
    private javax.swing.JLabel lblBeschreibung;
    private javax.swing.JLabel lblEinheit;
    private javax.swing.JLabel lblEinsatzvariante;
    private javax.swing.JLabel lblGeraet;
    private javax.swing.JLabel lblGewerk;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblInterval;
    private javax.swing.JLabel lblKompartiment;
    private javax.swing.JLabel lblLeistungstext;
    private javax.swing.JLabel lblRegel;
    private javax.swing.JLabel lblUrl;
    private javax.swing.JLabel lblUrlSmall;
    private javax.swing.JLabel lblVerbleib;
    private javax.swing.JLabel lblZweiterAusfuehrungszeitraum;
    private javax.swing.JPanel panSpacingBottom;
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

        if (readOnly) {
            RendererTools.makeReadOnly(jtLeistungstext);
            RendererTools.makeReadOnly(txtBeschreibung);
            RendererTools.makeReadOnly(txtId);
            RendererTools.makeReadOnly(cbSohle);
            RendererTools.makeReadOnly(cbUfer);
            RendererTools.makeReadOnly(cbUmfeld);
            RendererTools.makeReadOnly(cbGeraet);
            RendererTools.makeReadOnly(cbGewerk);
            RendererTools.makeReadOnly(cbEinsatzvariante);
            RendererTools.makeReadOnly(cbInterval);
            RendererTools.makeReadOnly(cbAusfuehrungszeitraum);
            RendererTools.makeReadOnly(cbVerbleib);
            RendererTools.makeReadOnly(cbZweiterAusfuehrungszeitraum);
            RendererTools.makeReadOnly(txtRegel);
            RendererTools.makeReadOnly(txtEinheit);
            RendererTools.makeReadOnly(txtUrl);
            RendererTools.makeReadOnly(txtUrlSmall);
            RendererTools.makeReadOnly(cbAllAnf);
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

        jPanel2 = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblBeschreibung = new javax.swing.JLabel();
        txtBeschreibung = new javax.swing.JTextField();
        lblLeistungstext = new javax.swing.JLabel();
        jsLeistungstext = new javax.swing.JScrollPane();
        jtLeistungstext = new javax.swing.JTextArea();
        lblKompartiment = new javax.swing.JLabel();
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

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.gridy = 0;
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
        gridBagConstraints.gridy = 0;
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
        gridBagConstraints.gridy = 1;
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
        gridBagConstraints.gridy = 1;
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
        gridBagConstraints.gridy = 12;
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
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jsLeistungstext, gridBagConstraints);

        lblKompartiment.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblKompartiment.text")); // NOI18N
        lblKompartiment.setMaximumSize(new java.awt.Dimension(250, 17));
        lblKompartiment.setMinimumSize(new java.awt.Dimension(250, 17));
        lblKompartiment.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(lblKompartiment, gridBagConstraints);

        lblInterval.setText(org.openide.util.NbBundle.getMessage(
                GupMassnahmenartEditor.class,
                "GupMassnahmenartEditor.lblInterval.text")); // NOI18N
        lblInterval.setMaximumSize(new java.awt.Dimension(250, 17));
        lblInterval.setMinimumSize(new java.awt.Dimension(250, 17));
        lblInterval.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
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
        gridBagConstraints.gridy = 6;
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
        gridBagConstraints.gridy = 7;
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
        gridBagConstraints.gridy = 7;
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
        gridBagConstraints.gridy = 3;
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
        gridBagConstraints.gridy = 4;
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
        gridBagConstraints.gridy = 5;
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
        gridBagConstraints.gridy = 3;
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
        gridBagConstraints.gridy = 4;
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
        gridBagConstraints.gridy = 5;
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
        gridBagConstraints.gridy = 9;
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
        gridBagConstraints.gridy = 9;
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
        gridBagConstraints.gridy = 8;
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
        gridBagConstraints.gridy = 8;
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
        gridBagConstraints.gridy = 13;
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
        gridBagConstraints.gridy = 13;
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
        gridBagConstraints.gridy = 14;
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
        gridBagConstraints.gridy = 14;
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
        gridBagConstraints.gridy = 10;
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
        gridBagConstraints.gridy = 11;
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
        gridBagConstraints.gridy = 10;
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
        gridBagConstraints.gridy = 11;
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
        gridBagConstraints.gridy = 15;
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
        gridBagConstraints.gridy = 15;
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
        gridBagConstraints.gridy = 2;
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
        add(jPanel2, gridBagConstraints);

        panSpacingBottom.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(panSpacingBottom, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbUmfeldActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbUmfeldActionPerformed
        vdecider.setSohle(cbUmfeld.isSelected());
    }                                                                            //GEN-LAST:event_cbUmfeldActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbUferActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbUferActionPerformed
        vdecider.setSohle(cbUfer.isSelected());
    }                                                                          //GEN-LAST:event_cbUferActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbSohleActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbSohleActionPerformed
        vdecider.setSohle(cbSohle.isSelected());
    }                                                                           //GEN-LAST:event_cbSohleActionPerformed

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
    private void cbEinsatzvarianteItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbEinsatzvarianteItemStateChanged
        // TODO add your handling code here:
    } //GEN-LAST:event_cbEinsatzvarianteItemStateChanged

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
            vdecider.setSohle((Boolean)cidsBean.getProperty("sohle"));
            vdecider.setUfer((Boolean)cidsBean.getProperty("ufer"));
            vdecider.setUmfeld((Boolean)cidsBean.getProperty("umfeld"));
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            final CidsBean bean = (CidsBean)cidsBean.getProperty("intervall");
            if (!readOnly) {
                cbZweiterAusfuehrungszeitraum.setEnabled((bean != null)
                            && bean.getProperty("id").equals(INTERVAL_TWO_TIMES));
            }
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
            "x",
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
    private class VermeidungsgruppenDecider implements FieldStateDecider {

        //~ Instance fields ----------------------------------------------------

        private boolean sohle = false;
        private boolean ufer = false;
        private boolean umfeld = false;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SubTypeDecider object.
         */
        public VermeidungsgruppenDecider() {
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
