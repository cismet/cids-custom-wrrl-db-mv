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

import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import de.cismet.cids.custom.wrrl_db_mv.fgsk.Calc;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;
import de.cismet.cids.editors.FieldStateDecider;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class FgskKartierabschnittKartierabschnitt extends javax.swing.JPanel implements DisposableCidsBeanStore,
    EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient Logger LOG = Logger.getLogger(FgskKartierabschnittKartierabschnitt.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private SubTypeDecider decider = new SubTypeDecider();
    private boolean readOnly = false;

    private final transient PropertyChangeListener subTypeL;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbSeeausfluss;
    private de.cismet.cids.editors.DefaultBindableCheckboxField ccGewaesserSubtyp;
    private de.cismet.tools.gui.RoundedPanel glassPanel;
    private de.cismet.tools.gui.RoundedPanel glassPanel1;
    private de.cismet.tools.gui.RoundedPanel glassPanel2;
    private de.cismet.tools.gui.RoundedPanel glassPanel3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private de.cismet.tools.gui.RoundedPanel jpGewTyp;
    private javax.swing.JPanel jpGroesse;
    private javax.swing.JPanel jpUnterh;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.KartierabschnittStammEditor kartierabschnittStammEditor1;
    private javax.swing.JLabel lblGewaesserbreite;
    private javax.swing.JLabel lblGewaessersubtyp;
    private javax.swing.JLabel lblGewaessertyp;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblHeadingTyp;
    private javax.swing.JLabel lblSeeausfluss;
    private javax.swing.JLabel lblSonderfall;
    private javax.swing.JLabel lblSpacing;
    private javax.swing.JLabel lblSpacing2;
    private javax.swing.JLabel lblTalform;
    private javax.swing.JLabel lblTalform1;
    private javax.swing.JLabel lblUnterhaltung;
    private javax.swing.JLabel lblWasserfuehrung;
    private javax.swing.JLabel lblfliessrichtung;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo2;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent2;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdBreiteBis20;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdLauftyp;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdSonderfall;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdTalform;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdWasserfuehrung;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdbWBType;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField referencedRadioButtonField2;
    private javax.swing.JSeparator sepMiddle;
    private javax.swing.JSeparator sepMiddle1;
    private javax.swing.JSeparator sepMiddle2;
    private javax.swing.JTextArea taErlaeuterung;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public FgskKartierabschnittKartierabschnitt() {
        this(false);
    }

    /**
     * Creates new form WkFgPanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public FgskKartierabschnittKartierabschnitt(final boolean readOnly) {
        this.readOnly = readOnly;
        this.subTypeL = new SubtypeCheckBoxRadioButtonBehaviourListener();

        initComponents();
        setOpaque(false);

        if (readOnly) {
            setReadOnly(readOnly);

            glassPanel.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        final Point p = new Point((int)e.getX(), (int)e.getY() - 15);
                        Component c = kartierabschnittStammEditor1.getComponentAt(p);

                        while (c instanceof JPanel) {
                            if (c == c.getComponentAt(p)) {
                                break;
                            }
                            c = c.getComponentAt(p);
                        }

                        if ((c instanceof JTextField) && c.equals(kartierabschnittStammEditor1.getTxtWk())) {
                            c.dispatchEvent(e);
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
        glassPanel = new de.cismet.tools.gui.RoundedPanel();
        glassPanel1 = new de.cismet.tools.gui.RoundedPanel();
        glassPanel2 = new de.cismet.tools.gui.RoundedPanel();
        kartierabschnittStammEditor1 = new KartierabschnittStammEditor(readOnly);
        jpUnterh = new javax.swing.JPanel();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        glassPanel3 = new de.cismet.tools.gui.RoundedPanel();
        lblWasserfuehrung = new javax.swing.JLabel();
        lblUnterhaltung = new javax.swing.JLabel();
        lblSonderfall = new javax.swing.JLabel();
        lblSpacing = new javax.swing.JLabel();
        sepMiddle = new javax.swing.JSeparator();
        lblfliessrichtung = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taErlaeuterung = new javax.swing.JTextArea();
        jCheckBox1 = new javax.swing.JCheckBox();
        rdWasserfuehrung = new de.cismet.cids.editors.DefaultBindableRadioButtonField();
        rdSonderfall = new de.cismet.cids.editors.DefaultBindableRadioButtonField();
        jpGewTyp = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeadingTyp = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        lblGewaessertyp = new javax.swing.JLabel();
        lblSeeausfluss = new javax.swing.JLabel();
        sepMiddle1 = new javax.swing.JSeparator();
        lblGewaessersubtyp = new javax.swing.JLabel();
        cbSeeausfluss = new javax.swing.JCheckBox();
        ccGewaesserSubtyp = new de.cismet.cids.editors.DefaultBindableCheckboxField();
        rdbWBType = new de.cismet.cids.editors.DefaultBindableRadioButtonField(true);
        sepMiddle2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        lblTalform = new javax.swing.JLabel();
        rdBreiteBis20 = new de.cismet.cids.editors.DefaultBindableRadioButtonField();
        lblTalform1 = new javax.swing.JLabel();
        rdTalform = new de.cismet.cids.editors.DefaultBindableRadioButtonField();
        rdLauftyp = new de.cismet.cids.editors.DefaultBindableRadioButtonField();
        jpGroesse = new javax.swing.JPanel();
        panInfo2 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        lblGewaesserbreite = new javax.swing.JLabel();
        lblSpacing2 = new javax.swing.JLabel();
        referencedRadioButtonField2 = new de.cismet.cids.editors.DefaultBindableRadioButtonField();

        setMaximumSize(new java.awt.Dimension(1100, 710));
        setMinimumSize(new java.awt.Dimension(1100, 710));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1100, 710));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setLayout(new java.awt.GridBagLayout());

        glassPanel.setAlpha(0);
        glassPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panInfo.add(glassPanel, gridBagConstraints);

        glassPanel1.setAlpha(0);
        glassPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panInfo.add(glassPanel1, gridBagConstraints);

        glassPanel2.setAlpha(0);
        glassPanel2.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panInfo.add(glassPanel2, gridBagConstraints);

        kartierabschnittStammEditor1.setMinimumSize(new java.awt.Dimension(1100, 290));
        kartierabschnittStammEditor1.setPreferredSize(new java.awt.Dimension(1100, 290));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        panInfo.add(kartierabschnittStammEditor1, gridBagConstraints);

        jpUnterh.setMinimumSize(new java.awt.Dimension(745, 160));
        jpUnterh.setOpaque(false);
        jpUnterh.setPreferredSize(new java.awt.Dimension(745, 160));
        jpUnterh.setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo1.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        glassPanel3.setAlpha(0);
        glassPanel3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(glassPanel3, gridBagConstraints);

        lblWasserfuehrung.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblWasserfuehrung.text")); // NOI18N
        lblWasserfuehrung.setMaximumSize(new java.awt.Dimension(120, 17));
        lblWasserfuehrung.setMinimumSize(new java.awt.Dimension(120, 17));
        lblWasserfuehrung.setPreferredSize(new java.awt.Dimension(120, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblWasserfuehrung, gridBagConstraints);

        lblUnterhaltung.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblUnterhaltung.text")); // NOI18N
        lblUnterhaltung.setMinimumSize(new java.awt.Dimension(120, 17));
        lblUnterhaltung.setPreferredSize(new java.awt.Dimension(120, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblUnterhaltung, gridBagConstraints);

        lblSonderfall.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblSonderfall.text")); // NOI18N
        lblSonderfall.setMinimumSize(new java.awt.Dimension(120, 17));
        lblSonderfall.setPreferredSize(new java.awt.Dimension(120, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblSonderfall, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(lblSpacing, gridBagConstraints);

        sepMiddle.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        panInfoContent.add(sepMiddle, gridBagConstraints);

        lblfliessrichtung.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblfliessrichtung.text")); // NOI18N
        lblfliessrichtung.setMinimumSize(new java.awt.Dimension(120, 17));
        lblfliessrichtung.setPreferredSize(new java.awt.Dimension(120, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblfliessrichtung, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(230, 90));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(230, 90));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.erlaeuterung}"),
                taErlaeuterung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(taErlaeuterung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent.add(jScrollPane1, gridBagConstraints);

        jCheckBox1.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.unterhaltungerkennbar}"),
                jCheckBox1,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panInfoContent.add(jCheckBox1, gridBagConstraints);

        rdWasserfuehrung.setMinimumSize(new java.awt.Dimension(170, 40));
        rdWasserfuehrung.setOpaque(false);
        rdWasserfuehrung.setPreferredSize(new java.awt.Dimension(170, 40));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wasserfuehrung_id}"),
                rdWasserfuehrung,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent.add(rdWasserfuehrung, gridBagConstraints);

        rdSonderfall.setMinimumSize(new java.awt.Dimension(170, 40));
        rdSonderfall.setOpaque(false);
        rdSonderfall.setPreferredSize(new java.awt.Dimension(170, 40));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sonderfall_id}"),
                rdSonderfall,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(rdSonderfall, gridBagConstraints);

        panInfo1.add(panInfoContent, java.awt.BorderLayout.CENTER);

        jpUnterh.add(panInfo1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panInfo.add(jpUnterh, gridBagConstraints);

        jpGewTyp.setMinimumSize(new java.awt.Dimension(1100, 200));
        jpGewTyp.setPreferredSize(new java.awt.Dimension(1100, 300));

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeadingTyp.setForeground(new java.awt.Color(255, 255, 255));
        lblHeadingTyp.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblHeadingTyp.text")); // NOI18N
        panHeadInfo1.add(lblHeadingTyp);

        jpGewTyp.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        lblGewaessertyp.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblGewaessertyp.text")); // NOI18N
        lblGewaessertyp.setMaximumSize(new java.awt.Dimension(100, 17));
        lblGewaessertyp.setMinimumSize(new java.awt.Dimension(100, 17));
        lblGewaessertyp.setPreferredSize(new java.awt.Dimension(100, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent1.add(lblGewaessertyp, gridBagConstraints);

        lblSeeausfluss.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblSeeausfluss.text")); // NOI18N
        lblSeeausfluss.setMaximumSize(new java.awt.Dimension(100, 17));
        lblSeeausfluss.setMinimumSize(new java.awt.Dimension(100, 17));
        lblSeeausfluss.setPreferredSize(new java.awt.Dimension(100, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 5);
        panInfoContent1.add(lblSeeausfluss, gridBagConstraints);

        sepMiddle1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent1.add(sepMiddle1, gridBagConstraints);

        lblGewaessersubtyp.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblGewaessersubtyp.text")); // NOI18N
        lblGewaessersubtyp.setMaximumSize(new java.awt.Dimension(130, 17));
        lblGewaessersubtyp.setMinimumSize(new java.awt.Dimension(130, 17));
        lblGewaessersubtyp.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent1.add(lblGewaessersubtyp, gridBagConstraints);

        cbSeeausfluss.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.seeausfluss}"),
                cbSeeausfluss,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 10);
        panInfoContent1.add(cbSeeausfluss, gridBagConstraints);

        ccGewaesserSubtyp.setMinimumSize(new java.awt.Dimension(170, 120));
        ccGewaesserSubtyp.setName(""); // NOI18N
        ccGewaesserSubtyp.setOpaque(false);
        ccGewaesserSubtyp.setPreferredSize(new java.awt.Dimension(170, 120));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewaessersubtyp}"),
                ccGewaesserSubtyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(ccGewaesserSubtyp, gridBagConstraints);

        rdbWBType.setMinimumSize(new java.awt.Dimension(250, 120));
        rdbWBType.setOpaque(false);
        rdbWBType.setPreferredSize(new java.awt.Dimension(250, 120));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewaessertyp_id}"),
                rdbWBType,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        rdbWBType.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(final java.beans.PropertyChangeEvent evt) {
                    rdbWBTypePropertyChange(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent1.add(rdbWBType, gridBagConstraints);

        sepMiddle2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent1.add(sepMiddle2, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblTalform.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblTalform.text")); // NOI18N
        lblTalform.setMaximumSize(new java.awt.Dimension(240, 17));
        lblTalform.setMinimumSize(new java.awt.Dimension(240, 17));
        lblTalform.setPreferredSize(new java.awt.Dimension(240, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel1.add(lblTalform, gridBagConstraints);

        rdBreiteBis20.setMinimumSize(new java.awt.Dimension(230, 80));
        rdBreiteBis20.setOpaque(false);
        rdBreiteBis20.setPreferredSize(new java.awt.Dimension(230, 80));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.talform_id}"),
                rdBreiteBis20,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        rdBreiteBis20.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(final java.beans.PropertyChangeEvent evt) {
                    rdBreiteBis20PropertyChange(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanel1.add(rdBreiteBis20, gridBagConstraints);

        lblTalform1.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblTalform1.text")); // NOI18N
        lblTalform1.setMaximumSize(new java.awt.Dimension(240, 17));
        lblTalform1.setMinimumSize(new java.awt.Dimension(240, 17));
        lblTalform1.setPreferredSize(new java.awt.Dimension(240, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel1.add(lblTalform1, gridBagConstraints);

        rdTalform.setMinimumSize(new java.awt.Dimension(240, 40));
        rdTalform.setOpaque(false);
        rdTalform.setPreferredSize(new java.awt.Dimension(350, 40));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.talform_kl_id}"),
                rdTalform,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        rdTalform.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(final java.beans.PropertyChangeEvent evt) {
                    rdTalformPropertyChange(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanel1.add(rdTalform, gridBagConstraints);

        rdLauftyp.setMinimumSize(new java.awt.Dimension(240, 40));
        rdLauftyp.setOpaque(false);
        rdLauftyp.setPreferredSize(new java.awt.Dimension(400, 40));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.lauftyp_id}"),
                rdLauftyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        rdLauftyp.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(final java.beans.PropertyChangeEvent evt) {
                    rdLauftypPropertyChange(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        jPanel1.add(rdLauftyp, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        panInfoContent1.add(jPanel1, gridBagConstraints);

        jpGewTyp.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panInfo.add(jpGewTyp, gridBagConstraints);

        jpGroesse.setMinimumSize(new java.awt.Dimension(335, 160));
        jpGroesse.setOpaque(false);
        jpGroesse.setPreferredSize(new java.awt.Dimension(335, 160));
        jpGroesse.setLayout(new java.awt.BorderLayout());

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblHeading2.text")); // NOI18N
        panHeadInfo2.add(lblHeading2);

        panInfo2.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        lblGewaesserbreite.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittKartierabschnitt.class,
                "FgskKartierabschnittKartierabschnitt.lblGewaesserbreite.text")); // NOI18N
        lblGewaesserbreite.setMaximumSize(new java.awt.Dimension(120, 17));
        lblGewaesserbreite.setMinimumSize(new java.awt.Dimension(120, 17));
        lblGewaesserbreite.setPreferredSize(new java.awt.Dimension(120, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent2.add(lblGewaesserbreite, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent2.add(lblSpacing2, gridBagConstraints);

        referencedRadioButtonField2.setMinimumSize(new java.awt.Dimension(150, 100));
        referencedRadioButtonField2.setOpaque(false);
        referencedRadioButtonField2.setPreferredSize(new java.awt.Dimension(150, 100));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewaesserbreite_id}"),
                referencedRadioButtonField2,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent2.add(referencedRadioButtonField2, gridBagConstraints);

        panInfo2.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        jpGroesse.add(panInfo2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
        panInfo.add(jpGroesse, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        add(panInfo, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * Colo DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rdbWBTypePropertyChange(final java.beans.PropertyChangeEvent evt) { //GEN-FIRST:event_rdbWBTypePropertyChange
        if ((evt.getPropertyName().equals("selectedElements"))) {
            final CidsBean newValue = (CidsBean)evt.getNewValue();
            decider.setType(newValue);
            ccGewaesserSubtyp.refreshCheckboxState(decider, true);
        }
    }                                                                                //GEN-LAST:event_rdbWBTypePropertyChange

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rdBreiteBis20PropertyChange(final java.beans.PropertyChangeEvent evt) { //GEN-FIRST:event_rdBreiteBis20PropertyChange
        // TODO add your handling code here:
    } //GEN-LAST:event_rdBreiteBis20PropertyChange

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rdTalformPropertyChange(final java.beans.PropertyChangeEvent evt) { //GEN-FIRST:event_rdTalformPropertyChange
        // TODO add your handling code here:
    } //GEN-LAST:event_rdTalformPropertyChange

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rdLauftypPropertyChange(final java.beans.PropertyChangeEvent evt) { //GEN-FIRST:event_rdLauftypPropertyChange
        // TODO add your handling code here:
    } //GEN-LAST:event_rdLauftypPropertyChange

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
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
            kartierabschnittStammEditor1.setCidsBean(cidsBean);
            decider.setType((CidsBean)cidsBean.getProperty("gewaessertyp_id"));
            ccGewaesserSubtyp.refreshCheckboxState(decider, false);
            if (!readOnly) {
                this.cidsBean.addPropertyChangeListener(subTypeL);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public void setReadOnly(final boolean readOnly) {
        if (readOnly) {
//            glassPanel.addMouseListener(new MouseAdapter() {
//                });
            glassPanel1.addMouseListener(new MouseAdapter() {
                });
            glassPanel2.addMouseListener(new MouseAdapter() {
                });
            glassPanel3.addMouseListener(new MouseAdapter() {
                });
            RendererTools.makeReadOnly(taErlaeuterung);
        } else {
            for (final MouseListener ml : glassPanel.getMouseListeners()) {
                glassPanel.removeMouseListener(ml);
                glassPanel1.removeMouseListener(ml);
                glassPanel2.removeMouseListener(ml);
                glassPanel3.removeMouseListener(ml);
            }
            RendererTools.makeWritable(taErlaeuterung);
        }

        this.readOnly = readOnly;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkk  DOCUMENT ME!
     */
    public void setWkk(final String wkk) {
        kartierabschnittStammEditor1.setWkk(wkk);
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
        kartierabschnittStammEditor1.dispose();
        rdSonderfall.dispose();
        rdWasserfuehrung.dispose();
        ccGewaesserSubtyp.dispose();
        rdbWBType.dispose();
        referencedRadioButtonField2.dispose();
        if (cidsBean != null) {
            cidsBean.removePropertyChangeListener(subTypeL);
        }
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        kartierabschnittStammEditor1.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        return kartierabschnittStammEditor1.prepareForSave();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class SubtypeCheckBoxRadioButtonBehaviourListener implements PropertyChangeListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (Calc.PROP_WB_SUB_TYPE.equals(evt.getPropertyName())) {
                final CidsBean wbType = (CidsBean)rdbWBType.getSelectedElements();

                if (wbType == null) {
                    LOG.warn("no selected wb type"); // NOI18N

                    return;
                }

                final Integer typeId = (Integer)wbType.getProperty(Calc.PROP_VALUE);

                if ((typeId != null) && ((typeId >= 14) && (typeId <= 17))) {
                    final List<CidsBean> newBeans = (List)evt.getNewValue();
                    final List<CidsBean> oldBeans = (List)evt.getOldValue();

                    // we just need to check, if a new entry is added
                    if (newBeans.size() > oldBeans.size()) {
                        if ((newBeans.size() - oldBeans.size()) != 1) {
                            // should never occur
                            LOG.warn("cannot determine which property shall be deactivated");

                            return;
                        }

                        CidsBean newBean = null;
                        for (final CidsBean bean : newBeans) {
                            if (!oldBeans.contains(bean)) {
                                newBean = bean;
                                break;
                            }
                        }

                        final CidsBean opposite = getOpposite(newBeans, newBean);
                        if (opposite != null) {
                            // we're aware of the fact that we're in the edt already, but we want this action to take
                            // place after the listener invokations
                            EventQueue.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        cidsBean.removePropertyChangeListener(subTypeL);
                                        ((List)cidsBean.getProperty(Calc.PROP_WB_SUB_TYPE)).remove(opposite);
                                        cidsBean.addPropertyChangeListener(subTypeL);
                                        ccGewaesserSubtyp.refreshCheckboxState(decider, false);
                                    }
                                });
                        }
                    }
                }
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param   beans  DOCUMENT ME!
         * @param   bean   DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private CidsBean getOpposite(final List<CidsBean> beans, final CidsBean bean) {
            final String value = (String)bean.getProperty(Calc.PROP_VALUE);
            final String oppositeValue;
            if ("M".equals(value)) {
                oppositeValue = "S";
            } else if ("S".equals(value)) {
                oppositeValue = "M";
            } else if ("g".equals(value)) {
                oppositeValue = "f";
            } else if ("f".equals(value)) {
                oppositeValue = "g";
            } else {
                oppositeValue = null;
            }

            if (oppositeValue != null) {
                for (final CidsBean candidate : beans) {
                    if (oppositeValue.equals(candidate.getProperty(Calc.PROP_VALUE))) {
                        return candidate;
                    }
                }
            }

            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class SubTypeDecider implements FieldStateDecider {

        //~ Instance fields ----------------------------------------------------

        List<CidsBean> supportedSubTypes;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SubTypeDecider object.
         */
        public SubTypeDecider() {
        }

        /**
         * Creates a new SubTypeDecider object.
         *
         * @param  type  DOCUMENT ME!
         */
        public SubTypeDecider(final CidsBean type) {
            supportedSubTypes = CidsBeanSupport.getBeanCollectionFromProperty(type, "gewaessersubtyp");
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public boolean isCheckboxForClassActive(final MetaObject mo) {
            if (supportedSubTypes == null) {
                return false;
            } else {
                return supportedSubTypes.contains(mo.getBean());
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param  type  the typ to set
         */
        public void setType(final CidsBean type) {
            supportedSubTypes = CidsBeanSupport.getBeanCollectionFromProperty(type, "gewaessersubtyp");
        }
    }
}
