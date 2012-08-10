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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box.Filler;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupHydrologEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    PropertyChangeListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupHydrologEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private Filler f;
    private LinearReferencedLineEditor lrle;
    private List<CidsBean> others;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblAbflussM;
    private javax.swing.JLabel lblAbflussbeiwert;
    private javax.swing.JLabel lblAbflussbeiwertM;
    private javax.swing.JLabel lblAbflussspende;
    private javax.swing.JLabel lblBenUmfangM;
    private javax.swing.JLabel lblBenetzterUmfang;
    private javax.swing.JLabel lblBenetzterUmfangM;
    private javax.swing.JLabel lblBoeLae1;
    private javax.swing.JLabel lblBoeNei;
    private javax.swing.JLabel lblDurchflusskapazitaet;
    private javax.swing.JLabel lblDurchflusskapazitaetM;
    private javax.swing.JLabel lblEzg;
    private javax.swing.JLabel lblEzgM;
    private javax.swing.JLabel lblFliessgeschwindigkeit;
    private javax.swing.JLabel lblFliessgeschwindigkeitM;
    private javax.swing.JLabel lblFliessquerschnitt;
    private javax.swing.JLabel lblFliessquerschnittM;
    private javax.swing.JLabel lblGefaelle;
    private javax.swing.JLabel lblHHW;
    private javax.swing.JLabel lblHW;
    private javax.swing.JLabel lblHWM;
    private javax.swing.JLabel lblHhwM;
    private javax.swing.JLabel lblHqtM;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblJaehrlichkeit;
    private javax.swing.JLabel lblLab;
    private javax.swing.JLabel lblLaenge;
    private javax.swing.JLabel lblLaengeM;
    private javax.swing.JLabel lblLinkPegel;
    private javax.swing.JLabel lblMQ;
    private javax.swing.JLabel lblMW;
    private javax.swing.JLabel lblMqM;
    private javax.swing.JLabel lblMwM;
    private javax.swing.JLabel lblNhnA;
    private javax.swing.JLabel lblNhnE;
    private javax.swing.JLabel lblObBreite;
    private javax.swing.JLabel lblObBreiteM;
    private javax.swing.JLabel lblPegel;
    private javax.swing.JLabel lblSohlbreite;
    private javax.swing.JLabel lblTiefe;
    private javax.swing.JLabel lblTiefeM;
    private javax.swing.JLabel lblhqt;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panHydraulischeDaten;
    private javax.swing.JPanel panProfil;
    private javax.swing.JTextField tfAbflussbeiwert;
    private javax.swing.JTextField tfAbflussspende;
    private javax.swing.JTextField tfBenetzterUmfang;
    private javax.swing.JTextField tfBoeLae;
    private javax.swing.JTextField tfBoeNei;
    private javax.swing.JTextField tfDurchflusskapazitaet;
    private javax.swing.JTextField tfEzg;
    private javax.swing.JTextField tfFliessgeschwindigkeit;
    private javax.swing.JTextField tfFliessquerschnitt;
    private javax.swing.JTextField tfGefaelle;
    private javax.swing.JTextField tfHHW;
    private javax.swing.JTextField tfHqt;
    private javax.swing.JTextField tfHw;
    private javax.swing.JTextField tfJaehrlichkeit;
    private javax.swing.JTextField tfLaenge;
    private javax.swing.JTextField tfMQ;
    private javax.swing.JTextField tfMW;
    private javax.swing.JTextField tfNhnA;
    private javax.swing.JTextField tfNhnE;
    private javax.swing.JTextField tfObBreite;
    private javax.swing.JTextField tfSohlbreite;
    private javax.swing.JTextField tfTiefe;
    private javax.swing.JTabbedPane tpMain;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupHydrologEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupHydrologEditor(final boolean readOnly) {
        f = new Filler(new Dimension(1, 1), new Dimension(1, 1), new Dimension(1, 1)) {

                @Override
                public void paint(final Graphics g) {
                    g.setColor(new Color(65, 169, 208));
                    g.drawLine(0, 0, getWidth() - 1, getHeight() - 2);
                    g.drawLine(0, 1, getWidth() - 1, getHeight() - 1);
                }
            };
        lrle = readOnly ? new LinearReferencedLineRenderer() : new LinearReferencedLineEditor();
        this.readOnly = readOnly;
        initComponents();
        tpMain.setUI(new TabbedPaneUITransparent());
        linearReferencedLineEditor.setLineField("linie");
        linearReferencedLineEditor.setOtherLinesEnabled(false);
//        linearReferencedLineEditor.setShowOtherInDialog(true);

        RendererTools.makeReadOnly(tfLaenge);
        RendererTools.makeReadOnly(tfGefaelle);
        RendererTools.makeReadOnly(tfBenetzterUmfang);
        RendererTools.makeReadOnly(tfFliessgeschwindigkeit);
        RendererTools.makeReadOnly(tfFliessquerschnitt);
        RendererTools.makeReadOnly(tfDurchflusskapazitaet);
        RendererTools.makeReadOnly(tfBoeLae);
        RendererTools.makeReadOnly(tfBoeNei);

        if (readOnly) {
            RendererTools.makeReadOnly(tfNhnA);
            RendererTools.makeReadOnly(tfNhnE);
            RendererTools.makeReadOnly(tfObBreite);
            RendererTools.makeReadOnly(tfTiefe);
            RendererTools.makeReadOnly(tfSohlbreite);
            RendererTools.makeReadOnly(tfEzg);
            RendererTools.makeReadOnly(tfAbflussspende);
            RendererTools.makeReadOnly(tfAbflussbeiwert);
            RendererTools.makeReadOnly(tfMW);
            RendererTools.makeReadOnly(tfHw);
            RendererTools.makeReadOnly(tfHHW);
            RendererTools.makeReadOnly(tfMQ);
            RendererTools.makeReadOnly(tfHqt);
            RendererTools.makeReadOnly(tfJaehrlichkeit);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  others  DOCUMENT ME!
     */
    public void setOthers(final List<CidsBean> others) {
        this.others = others;
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

        tpMain = new javax.swing.JTabbedPane();
        panProfil = new javax.swing.JPanel();
        linearReferencedLineEditor = lrle;
        jPanel2 = new javax.swing.JPanel();
        lblNhnA = new javax.swing.JLabel();
        lblNhnE = new javax.swing.JLabel();
        tfNhnA = new javax.swing.JTextField();
        tfNhnE = new javax.swing.JTextField();
        tfLaenge = new javax.swing.JTextField();
        tfGefaelle = new javax.swing.JTextField();
        filler1 = f;
        lblLaenge = new javax.swing.JLabel();
        lblLaengeM = new javax.swing.JLabel();
        lblGefaelle = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(32767, 32767));
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblObBreite = new javax.swing.JLabel();
        tfObBreite = new javax.swing.JTextField();
        lblObBreiteM = new javax.swing.JLabel();
        lblBenUmfangM = new javax.swing.JLabel();
        tfBoeNei = new javax.swing.JTextField();
        lblBoeNei = new javax.swing.JLabel();
        lblSohlbreite = new javax.swing.JLabel();
        tfSohlbreite = new javax.swing.JTextField();
        tfTiefe = new javax.swing.JTextField();
        lblTiefe = new javax.swing.JLabel();
        lblTiefeM = new javax.swing.JLabel();
        tfBoeLae = new javax.swing.JTextField();
        lblBoeLae1 = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();
        panHydraulischeDaten = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblEzg = new javax.swing.JLabel();
        lblAbflussspende = new javax.swing.JLabel();
        tfEzg = new javax.swing.JTextField();
        tfAbflussspende = new javax.swing.JTextField();
        lblMW = new javax.swing.JLabel();
        tfMW = new javax.swing.JTextField();
        lblHW = new javax.swing.JLabel();
        tfHw = new javax.swing.JTextField();
        lblHHW = new javax.swing.JLabel();
        tfHHW = new javax.swing.JTextField();
        lblMQ = new javax.swing.JLabel();
        tfMQ = new javax.swing.JTextField();
        lblhqt = new javax.swing.JLabel();
        tfHqt = new javax.swing.JTextField();
        lblJaehrlichkeit = new javax.swing.JLabel();
        tfJaehrlichkeit = new javax.swing.JTextField();
        lblEzgM = new javax.swing.JLabel();
        lblAbflussM = new javax.swing.JLabel();
        lblMwM = new javax.swing.JLabel();
        lblHWM = new javax.swing.JLabel();
        lblHhwM = new javax.swing.JLabel();
        lblMqM = new javax.swing.JLabel();
        lblHqtM = new javax.swing.JLabel();
        lblPegel = new javax.swing.JLabel();
        lblLinkPegel = new javax.swing.JLabel();
        lblLab = new javax.swing.JLabel();
        lblAbflussbeiwert = new javax.swing.JLabel();
        tfAbflussbeiwert = new javax.swing.JTextField();
        lblAbflussbeiwertM = new javax.swing.JLabel();
        lblBenetzterUmfang = new javax.swing.JLabel();
        tfBenetzterUmfang = new javax.swing.JTextField();
        lblBenetzterUmfangM = new javax.swing.JLabel();
        lblFliessquerschnitt = new javax.swing.JLabel();
        tfFliessquerschnitt = new javax.swing.JTextField();
        lblFliessquerschnittM = new javax.swing.JLabel();
        lblFliessgeschwindigkeit = new javax.swing.JLabel();
        tfFliessgeschwindigkeit = new javax.swing.JTextField();
        lblFliessgeschwindigkeitM = new javax.swing.JLabel();
        lblDurchflusskapazitaet = new javax.swing.JLabel();
        tfDurchflusskapazitaet = new javax.swing.JTextField();
        lblDurchflusskapazitaetM = new javax.swing.JLabel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(994, 500));
        setLayout(new java.awt.GridBagLayout());

        tpMain.setMinimumSize(new java.awt.Dimension(1104, 460));

        panProfil.setOpaque(false);
        panProfil.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(25, 10, 15, 10);
        panProfil.add(linearReferencedLineEditor, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblNhnA.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblNhnA.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel2.add(lblNhnA, gridBagConstraints);

        lblNhnE.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblNhnE.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel2.add(lblNhnE, gridBagConstraints);

        tfNhnA.setMinimumSize(new java.awt.Dimension(70, 27));
        tfNhnA.setPreferredSize(new java.awt.Dimension(70, 27));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hoehe_anfang}"),
                tfNhnA,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        jPanel2.add(tfNhnA, gridBagConstraints);

        tfNhnE.setMinimumSize(new java.awt.Dimension(70, 27));
        tfNhnE.setPreferredSize(new java.awt.Dimension(70, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hoehe_ende}"),
                tfNhnE,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        jPanel2.add(tfNhnE, gridBagConstraints);

        tfLaenge.setMinimumSize(new java.awt.Dimension(70, 27));
        tfLaenge.setPreferredSize(new java.awt.Dimension(70, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel2.add(tfLaenge, gridBagConstraints);

        tfGefaelle.setMinimumSize(new java.awt.Dimension(70, 27));
        tfGefaelle.setPreferredSize(new java.awt.Dimension(70, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gefaelle}"),
                tfGefaelle,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(25, 0, 0, 0);
        jPanel2.add(tfGefaelle, gridBagConstraints);

        filler1.setMaximumSize(new java.awt.Dimension(32767, 0));
        filler1.setMinimumSize(new java.awt.Dimension(300, 27));
        filler1.setPreferredSize(new java.awt.Dimension(300, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        jPanel2.add(filler1, gridBagConstraints);

        lblLaenge.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblLaenge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel2.add(lblLaenge, gridBagConstraints);

        lblLaengeM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblLaengeM.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel2.add(lblLaengeM, gridBagConstraints);

        lblGefaelle.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblGefaelle.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(25, 0, 0, 5);
        jPanel2.add(lblGefaelle, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panProfil.add(jPanel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.weighty = 1.0;
        panProfil.add(filler2, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel4.setMaximumSize(new java.awt.Dimension(600, 166));
        jPanel4.setMinimumSize(new java.awt.Dimension(600, 166));
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(600, 186));
        jPanel4.setLayout(null);

        lblObBreite.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblObBreite.text")); // NOI18N
        jPanel4.add(lblObBreite);
        lblObBreite.setBounds(150, 45, 91, 17);

        tfObBreite.setMinimumSize(new java.awt.Dimension(70, 27));
        tfObBreite.setPreferredSize(new java.awt.Dimension(70, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.obere_breite}"),
                tfObBreite,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel4.add(tfObBreite);
        tfObBreite.setBounds(260, 40, 70, 27);

        lblObBreiteM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblObBreiteM.text")); // NOI18N
        jPanel4.add(lblObBreiteM);
        lblObBreiteM.setBounds(325, 160, 13, 17);

        lblBenUmfangM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblBenUmfangM.text")); // NOI18N
        jPanel4.add(lblBenUmfangM);
        lblBenUmfangM.setBounds(335, 45, 13, 17);

        tfBoeNei.setMinimumSize(new java.awt.Dimension(70, 27));
        tfBoeNei.setPreferredSize(new java.awt.Dimension(70, 27));
        jPanel4.add(tfBoeNei);
        tfBoeNei.setBounds(430, 110, 70, 27);

        lblBoeNei.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblBoeNei.text")); // NOI18N
        jPanel4.add(lblBoeNei);
        lblBoeNei.setBounds(420, 90, 88, 20);

        lblSohlbreite.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblSohlbreite.text")); // NOI18N
        jPanel4.add(lblSohlbreite);
        lblSohlbreite.setBounds(170, 160, 72, 17);

        tfSohlbreite.setMinimumSize(new java.awt.Dimension(70, 27));
        tfSohlbreite.setPreferredSize(new java.awt.Dimension(70, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sohlbreite}"),
                tfSohlbreite,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel4.add(tfSohlbreite);
        tfSohlbreite.setBounds(250, 155, 70, 27);

        tfTiefe.setMinimumSize(new java.awt.Dimension(70, 27));
        tfTiefe.setPreferredSize(new java.awt.Dimension(70, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.tiefe}"),
                tfTiefe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel4.add(tfTiefe);
        tfTiefe.setBounds(260, 80, 70, 27);

        lblTiefe.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblTiefe.text")); // NOI18N
        jPanel4.add(lblTiefe);
        lblTiefe.setBounds(150, 85, 90, 17);

        lblTiefeM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblTiefeM.text")); // NOI18N
        jPanel4.add(lblTiefeM);
        lblTiefeM.setBounds(335, 85, 13, 17);

        tfBoeLae.setMinimumSize(new java.awt.Dimension(70, 27));
        tfBoeLae.setPreferredSize(new java.awt.Dimension(70, 27));
        jPanel4.add(tfBoeLae);
        tfBoeLae.setBounds(22, 110, 70, 27);

        lblBoeLae1.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblBoeLae1.text")); // NOI18N
        jPanel4.add(lblBoeLae1);
        lblBoeLae1.setBounds(20, 90, 72, 17);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jPanel4, gridBagConstraints);

        lblImage.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/hydro.png"))); // NOI18N
        lblImage.setMaximumSize(new java.awt.Dimension(600, 166));
        lblImage.setMinimumSize(new java.awt.Dimension(600, 166));
        lblImage.setPreferredSize(new java.awt.Dimension(600, 166));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel3.add(lblImage, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        panProfil.add(jPanel3, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.panProfil.TabConstraints.tabTitle"),
            panProfil); // NOI18N

        panHydraulischeDaten.setOpaque(false);
        panHydraulischeDaten.setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(994, 500));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblEzg.setText(org.openide.util.NbBundle.getMessage(GupHydrologEditor.class, "GupHydrologEditor.lblEzg.text")); // NOI18N
        lblEzg.setMaximumSize(new java.awt.Dimension(270, 17));
        lblEzg.setMinimumSize(new java.awt.Dimension(270, 17));
        lblEzg.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblEzg, gridBagConstraints);

        lblAbflussspende.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblAbflussspende.text")); // NOI18N
        lblAbflussspende.setMaximumSize(new java.awt.Dimension(270, 17));
        lblAbflussspende.setMinimumSize(new java.awt.Dimension(270, 17));
        lblAbflussspende.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblAbflussspende, gridBagConstraints);

        tfEzg.setMinimumSize(new java.awt.Dimension(70, 27));
        tfEzg.setPreferredSize(new java.awt.Dimension(100, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ezg}"),
                tfEzg,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfEzg, gridBagConstraints);

        tfAbflussspende.setMinimumSize(new java.awt.Dimension(70, 27));
        tfAbflussspende.setPreferredSize(new java.awt.Dimension(100, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.abflussspende}"),
                tfAbflussspende,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfAbflussspende, gridBagConstraints);

        lblMW.setText(org.openide.util.NbBundle.getMessage(GupHydrologEditor.class, "GupHydrologEditor.lblMW.text")); // NOI18N
        lblMW.setMaximumSize(new java.awt.Dimension(270, 17));
        lblMW.setMinimumSize(new java.awt.Dimension(270, 17));
        lblMW.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblMW, gridBagConstraints);

        tfMW.setMinimumSize(new java.awt.Dimension(70, 27));
        tfMW.setPreferredSize(new java.awt.Dimension(100, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mw}"),
                tfMW,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfMW, gridBagConstraints);

        lblHW.setText(org.openide.util.NbBundle.getMessage(GupHydrologEditor.class, "GupHydrologEditor.lblHW.text")); // NOI18N
        lblHW.setMaximumSize(new java.awt.Dimension(170, 17));
        lblHW.setMinimumSize(new java.awt.Dimension(170, 17));
        lblHW.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblHW, gridBagConstraints);

        tfHw.setMinimumSize(new java.awt.Dimension(70, 27));
        tfHw.setPreferredSize(new java.awt.Dimension(100, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hw}"),
                tfHw,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfHw, gridBagConstraints);

        lblHHW.setText(org.openide.util.NbBundle.getMessage(GupHydrologEditor.class, "GupHydrologEditor.lblHHW.text")); // NOI18N
        lblHHW.setMaximumSize(new java.awt.Dimension(270, 17));
        lblHHW.setMinimumSize(new java.awt.Dimension(270, 17));
        lblHHW.setPreferredSize(new java.awt.Dimension(270, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblHHW, gridBagConstraints);

        tfHHW.setMinimumSize(new java.awt.Dimension(70, 27));
        tfHHW.setPreferredSize(new java.awt.Dimension(100, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hhw}"),
                tfHHW,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfHHW, gridBagConstraints);

        lblMQ.setText(org.openide.util.NbBundle.getMessage(GupHydrologEditor.class, "GupHydrologEditor.lblMQ.text")); // NOI18N
        lblMQ.setMaximumSize(new java.awt.Dimension(170, 17));
        lblMQ.setMinimumSize(new java.awt.Dimension(170, 17));
        lblMQ.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        jPanel1.add(lblMQ, gridBagConstraints);

        tfMQ.setMinimumSize(new java.awt.Dimension(70, 27));
        tfMQ.setPreferredSize(new java.awt.Dimension(100, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mq}"),
                tfMQ,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfMQ, gridBagConstraints);

        lblhqt.setText(org.openide.util.NbBundle.getMessage(GupHydrologEditor.class, "GupHydrologEditor.lblhqt.text")); // NOI18N
        lblhqt.setMaximumSize(new java.awt.Dimension(170, 17));
        lblhqt.setMinimumSize(new java.awt.Dimension(170, 17));
        lblhqt.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        jPanel1.add(lblhqt, gridBagConstraints);

        tfHqt.setMinimumSize(new java.awt.Dimension(70, 27));
        tfHqt.setPreferredSize(new java.awt.Dimension(100, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hq}"),
                tfHqt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfHqt, gridBagConstraints);

        lblJaehrlichkeit.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblJaehrlichkeit.text")); // NOI18N
        lblJaehrlichkeit.setMaximumSize(new java.awt.Dimension(170, 17));
        lblJaehrlichkeit.setMinimumSize(new java.awt.Dimension(170, 17));
        lblJaehrlichkeit.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        jPanel1.add(lblJaehrlichkeit, gridBagConstraints);

        tfJaehrlichkeit.setMinimumSize(new java.awt.Dimension(70, 27));
        tfJaehrlichkeit.setPreferredSize(new java.awt.Dimension(100, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.vollbord}"),
                tfJaehrlichkeit,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfJaehrlichkeit, gridBagConstraints);

        lblEzgM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblEzgM.text")); // NOI18N
        lblEzgM.setMaximumSize(new java.awt.Dimension(50, 17));
        lblEzgM.setMinimumSize(new java.awt.Dimension(50, 17));
        lblEzgM.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblEzgM, gridBagConstraints);

        lblAbflussM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblAbflussM.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblAbflussM, gridBagConstraints);

        lblMwM.setText(org.openide.util.NbBundle.getMessage(GupHydrologEditor.class, "GupHydrologEditor.lblMwM.text")); // NOI18N
        lblMwM.setMaximumSize(new java.awt.Dimension(50, 17));
        lblMwM.setMinimumSize(new java.awt.Dimension(50, 17));
        lblMwM.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblMwM, gridBagConstraints);

        lblHWM.setText(org.openide.util.NbBundle.getMessage(GupHydrologEditor.class, "GupHydrologEditor.lblHWM.text")); // NOI18N
        lblHWM.setMaximumSize(new java.awt.Dimension(50, 17));
        lblHWM.setMinimumSize(new java.awt.Dimension(50, 17));
        lblHWM.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblHWM, gridBagConstraints);

        lblHhwM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblHhwM.text")); // NOI18N
        lblHhwM.setMaximumSize(new java.awt.Dimension(50, 17));
        lblHhwM.setMinimumSize(new java.awt.Dimension(50, 17));
        lblHhwM.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblHhwM, gridBagConstraints);

        lblMqM.setText(org.openide.util.NbBundle.getMessage(GupHydrologEditor.class, "GupHydrologEditor.lblMqM.text")); // NOI18N
        lblMqM.setMaximumSize(new java.awt.Dimension(50, 17));
        lblMqM.setMinimumSize(new java.awt.Dimension(50, 17));
        lblMqM.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblMqM, gridBagConstraints);

        lblHqtM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblHqtM.text")); // NOI18N
        lblHqtM.setMaximumSize(new java.awt.Dimension(50, 17));
        lblHqtM.setMinimumSize(new java.awt.Dimension(50, 17));
        lblHqtM.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblHqtM, gridBagConstraints);

        lblPegel.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblPegel.text")); // NOI18N
        lblPegel.setMaximumSize(new java.awt.Dimension(270, 17));
        lblPegel.setMinimumSize(new java.awt.Dimension(270, 17));
        lblPegel.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 15, 5, 5);
        jPanel1.add(lblPegel, gridBagConstraints);

        lblLinkPegel.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblLinkPegel.text")); // NOI18N
        lblLinkPegel.setMaximumSize(new java.awt.Dimension(370, 17));
        lblLinkPegel.setMinimumSize(new java.awt.Dimension(370, 17));
        lblLinkPegel.setPreferredSize(new java.awt.Dimension(370, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 5, 5);
        jPanel1.add(lblLinkPegel, gridBagConstraints);

        lblLab.setFont(new java.awt.Font("DejaVu Sans", 1, 16));                                                        // NOI18N
        lblLab.setText(org.openide.util.NbBundle.getMessage(GupHydrologEditor.class, "GupHydrologEditor.lblLab.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.insets = new java.awt.Insets(20, 15, 20, 0);
        jPanel1.add(lblLab, gridBagConstraints);

        lblAbflussbeiwert.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblAbflussbeiwert.text")); // NOI18N
        lblAbflussbeiwert.setMaximumSize(new java.awt.Dimension(270, 17));
        lblAbflussbeiwert.setMinimumSize(new java.awt.Dimension(270, 17));
        lblAbflussbeiwert.setPreferredSize(new java.awt.Dimension(270, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblAbflussbeiwert, gridBagConstraints);

        tfAbflussbeiwert.setMinimumSize(new java.awt.Dimension(70, 27));
        tfAbflussbeiwert.setPreferredSize(new java.awt.Dimension(100, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.abflussbeiwert}"),
                tfAbflussbeiwert,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfAbflussbeiwert, gridBagConstraints);

        lblAbflussbeiwertM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblAbflussbeiwertM.text")); // NOI18N
        lblAbflussbeiwertM.setMaximumSize(new java.awt.Dimension(50, 17));
        lblAbflussbeiwertM.setMinimumSize(new java.awt.Dimension(50, 17));
        lblAbflussbeiwertM.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblAbflussbeiwertM, gridBagConstraints);

        lblBenetzterUmfang.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblBenetzterUmfang.text")); // NOI18N
        lblBenetzterUmfang.setMaximumSize(new java.awt.Dimension(270, 17));
        lblBenetzterUmfang.setMinimumSize(new java.awt.Dimension(270, 17));
        lblBenetzterUmfang.setPreferredSize(new java.awt.Dimension(270, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblBenetzterUmfang, gridBagConstraints);

        tfBenetzterUmfang.setEditable(false);
        tfBenetzterUmfang.setMinimumSize(new java.awt.Dimension(70, 27));
        tfBenetzterUmfang.setPreferredSize(new java.awt.Dimension(100, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfBenetzterUmfang, gridBagConstraints);

        lblBenetzterUmfangM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblBenetzterUmfangM.text")); // NOI18N
        lblBenetzterUmfangM.setMaximumSize(new java.awt.Dimension(50, 17));
        lblBenetzterUmfangM.setMinimumSize(new java.awt.Dimension(50, 17));
        lblBenetzterUmfangM.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblBenetzterUmfangM, gridBagConstraints);

        lblFliessquerschnitt.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblFliessquerschnitt.text")); // NOI18N
        lblFliessquerschnitt.setMaximumSize(new java.awt.Dimension(270, 17));
        lblFliessquerschnitt.setMinimumSize(new java.awt.Dimension(270, 17));
        lblFliessquerschnitt.setPreferredSize(new java.awt.Dimension(270, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblFliessquerschnitt, gridBagConstraints);

        tfFliessquerschnitt.setEditable(false);
        tfFliessquerschnitt.setMinimumSize(new java.awt.Dimension(70, 27));
        tfFliessquerschnitt.setPreferredSize(new java.awt.Dimension(100, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfFliessquerschnitt, gridBagConstraints);

        lblFliessquerschnittM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblFliessquerschnittM.text")); // NOI18N
        lblFliessquerschnittM.setMaximumSize(new java.awt.Dimension(50, 17));
        lblFliessquerschnittM.setMinimumSize(new java.awt.Dimension(50, 17));
        lblFliessquerschnittM.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblFliessquerschnittM, gridBagConstraints);

        lblFliessgeschwindigkeit.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblFliessgeschwindigkeit.text")); // NOI18N
        lblFliessgeschwindigkeit.setMaximumSize(new java.awt.Dimension(270, 17));
        lblFliessgeschwindigkeit.setMinimumSize(new java.awt.Dimension(270, 17));
        lblFliessgeschwindigkeit.setPreferredSize(new java.awt.Dimension(270, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblFliessgeschwindigkeit, gridBagConstraints);

        tfFliessgeschwindigkeit.setEditable(false);
        tfFliessgeschwindigkeit.setMinimumSize(new java.awt.Dimension(70, 27));
        tfFliessgeschwindigkeit.setPreferredSize(new java.awt.Dimension(100, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfFliessgeschwindigkeit, gridBagConstraints);

        lblFliessgeschwindigkeitM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblFliessgeschwindigkeitM.text")); // NOI18N
        lblFliessgeschwindigkeitM.setMaximumSize(new java.awt.Dimension(50, 17));
        lblFliessgeschwindigkeitM.setMinimumSize(new java.awt.Dimension(50, 17));
        lblFliessgeschwindigkeitM.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblFliessgeschwindigkeitM, gridBagConstraints);

        lblDurchflusskapazitaet.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblDurchflusskapazitaet.text")); // NOI18N
        lblDurchflusskapazitaet.setMaximumSize(new java.awt.Dimension(270, 17));
        lblDurchflusskapazitaet.setMinimumSize(new java.awt.Dimension(270, 17));
        lblDurchflusskapazitaet.setPreferredSize(new java.awt.Dimension(270, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblDurchflusskapazitaet, gridBagConstraints);

        tfDurchflusskapazitaet.setEditable(false);
        tfDurchflusskapazitaet.setMinimumSize(new java.awt.Dimension(70, 27));
        tfDurchflusskapazitaet.setPreferredSize(new java.awt.Dimension(100, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(tfDurchflusskapazitaet, gridBagConstraints);

        lblDurchflusskapazitaetM.setText(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.lblDurchflusskapazitaetM.text")); // NOI18N
        lblDurchflusskapazitaetM.setMaximumSize(new java.awt.Dimension(50, 17));
        lblDurchflusskapazitaetM.setMinimumSize(new java.awt.Dimension(50, 17));
        lblDurchflusskapazitaetM.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblDurchflusskapazitaetM, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panHydraulischeDaten.add(jPanel1, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                GupHydrologEditor.class,
                "GupHydrologEditor.panHydraulischeDaten.TabConstraints.tabTitle"),
            panHydraulischeDaten); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(tpMain, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     */
    private void calcFields() {
        final double sohlbreite = CidsBeanSupport.textToDouble(tfSohlbreite, 0.0);
        final double obereBreite = CidsBeanSupport.textToDouble(tfObBreite, 0.0);
        final double hoeheAnfang = CidsBeanSupport.textToDouble(tfNhnA, 0.0);
        final double hoeheEnde = CidsBeanSupport.textToDouble(tfNhnE, 0.0);
        double anfang = (Double)cidsBean.getProperty("linie.von.wert");
        double ende = (Double)cidsBean.getProperty("linie.bis.wert");
        final double tiefe = CidsBeanSupport.textToDouble(tfTiefe, 0.0);
        final double abflussbeiwert = CidsBeanSupport.textToDouble(tfAbflussbeiwert, 0.0);

        if (anfang > ende) {
            final double tmp = anfang;
            anfang = ende;
            ende = tmp;
        }

        final double laenge = ende - anfang;
        double gefaelle = 0;

        if (laenge > 0) {
            gefaelle = (hoeheAnfang - hoeheEnde) / laenge;
        }

        final double boeschungslaenge = Math.sqrt(Math.pow(tiefe, 2) + Math.pow((obereBreite - sohlbreite) / 2, 2));
        final double boeschungsneigung = Math.sqrt(Math.pow(boeschungslaenge, 2) - Math.pow(tiefe, 2)) / tiefe;
        final double benetzterUmfang = sohlbreite + (2 * boeschungslaenge);
        final double fliessquerschitt = (sohlbreite * tiefe) + (boeschungsneigung * tiefe * tiefe);
        double hydraulischerRadius = 0;

        if (benetzterUmfang != 0) {
            hydraulischerRadius = fliessquerschitt / benetzterUmfang;
        }

        final double fliessgeschwindigkeit = abflussbeiwert * Math.pow(hydraulischerRadius, 2.0 / 3.0)
                    * Math.sqrt(gefaelle);
        final double durchflusskapazitaet = fliessgeschwindigkeit * fliessquerschitt;
        final double finGefaelle = gefaelle;

        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    tfBoeLae.setText(String.valueOf(round(boeschungslaenge)));
                    tfBoeNei.setText(String.valueOf(round(boeschungsneigung)));
                    tfLaenge.setText(String.valueOf(round(laenge)));
                    tfGefaelle.setText(String.valueOf(round(finGefaelle)));
                    tfBenetzterUmfang.setText(String.valueOf(round(benetzterUmfang)));
                    tfFliessgeschwindigkeit.setText(String.valueOf(round(fliessgeschwindigkeit)));
                    tfFliessquerschnitt.setText(String.valueOf(round(fliessquerschitt)));
                    tfDurchflusskapazitaet.setText(String.valueOf(round(durchflusskapazitaet)));
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param   value  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private double round(final double value) {
        return Math.round(value * 10000.0) / 10000.0;
    }

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        if (cidsBean != null) {
            cidsBean.removePropertyChangeListener(this);
        }
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            if (this.others != null) {
                final List<CidsBean> lineBeans = new ArrayList<CidsBean>();
                final Object id = cidsBean.getProperty("linie.id");

                for (final CidsBean b : this.others) {
                    final CidsBean tmp = (CidsBean)b.getProperty("linie");

                    if ((tmp != null) && (!tmp.getProperty("id").equals(id))) {
                        lineBeans.add(tmp);
                    }
                }
                linearReferencedLineEditor.setOtherLines(lineBeans);
            }

            linearReferencedLineEditor.setCidsBean(cidsBean);
            cidsBean.addPropertyChangeListener(this);
            calcFields();
        }
    }

    @Override
    public void dispose() {
        if (cidsBean != null) {
            cidsBean.removePropertyChangeListener(this);
        }
        linearReferencedLineEditor.dispose();
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Gewässerprofil und hydraulische Daten";
    }

    @Override
    public void setTitle(final String title) {
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
    }

    @Override
    public boolean prepareForSave() {
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
            "gup_hydrolog",
            1,
            1280,
            1024);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        calcFields();
    }
}
