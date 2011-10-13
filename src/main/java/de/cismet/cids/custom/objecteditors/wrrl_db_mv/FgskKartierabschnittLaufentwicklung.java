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

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class FgskKartierabschnittLaufentwicklung extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbNe;
    private javax.swing.JCheckBox cbNe1;
    private de.cismet.tools.gui.RoundedPanel glassPanel;
    private javax.swing.JPanel jpBesLaufstrukturen;
    private javax.swing.JPanel jpKruemmung;
    private javax.swing.JPanel jpLaengsbaenke;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.KartierabschnittUebersicht kartierabschnittUebersicht1;
    private javax.swing.JLabel lbSb;
    private javax.swing.JLabel lblErosion;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblIb;
    private javax.swing.JLabel lblIbi;
    private javax.swing.JLabel lblLauf;
    private javax.swing.JLabel lblLg;
    private javax.swing.JLabel lblLv;
    private javax.swing.JLabel lblLw;
    private javax.swing.JLabel lblMb;
    private javax.swing.JLabel lblNe;
    private javax.swing.JLabel lblNe1;
    private javax.swing.JLabel lblSpacing;
    private javax.swing.JLabel lblSpacing1;
    private javax.swing.JLabel lblSpacing2;
    private javax.swing.JLabel lblTv;
    private javax.swing.JLabel lblUfkg;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo3;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent2;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdErosion;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdLaufkruemmung;
    private javax.swing.JSeparator sepMiddle;
    private javax.swing.JTextField tfIb;
    private javax.swing.JTextField tfIbi;
    private javax.swing.JTextField tfLg;
    private javax.swing.JTextField tfLv;
    private javax.swing.JTextField tfLw;
    private javax.swing.JTextField tfMb;
    private javax.swing.JTextField tfSb;
    private javax.swing.JTextField tfTv;
    private javax.swing.JTextField tfUfkg;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public FgskKartierabschnittLaufentwicklung() {
        initComponents();
        setOpaque(false);
        final HashMap<Object, Icon> icons = new HashMap<Object, Icon>();
        ImageIcon icon = new ImageIcon(getClass().getResource(
                    "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/maeandr_crop.png"));
        icons.put(1, icon);
        icon = new ImageIcon(getClass().getResource(
                    "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/geschlaengelt_crop.png"));
        icons.put(2, icon);
        icon = new ImageIcon(getClass().getResource(
                    "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/stark_geschwungen_crop.png"));
        icons.put(3, icon);
        icon = new ImageIcon(getClass().getResource(
                    "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/maessig_geschwungen_crop.png"));
        icons.put(4, icon);
        icon = new ImageIcon(getClass().getResource(
                    "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/schwach_geschwungen_crop.png"));
        icons.put(5, icon);
        icon = new ImageIcon(getClass().getResource(
                    "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/gestreckt_crop.png"));
        icons.put(6, icon);
        icon = new ImageIcon(getClass().getResource(
                    "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/geradlinig_crop.png"));
        icons.put(7, icon);
        rdLaufkruemmung.setIcons(icons, "value");

        final FocusListener lisLae = new FocusListener() {

                @Override
                public void focusGained(final FocusEvent e) {
                }

                @Override
                public void focusLost(final FocusEvent e) {
                    fillNELae();
                }
            };

        tfUfkg.addFocusListener(lisLae);
        tfIb.addFocusListener(lisLae);
        tfMb.addFocusListener(lisLae);

        final FocusListener lisLst = new FocusListener() {

                @Override
                public void focusGained(final FocusEvent e) {
                }

                @Override
                public void focusLost(final FocusEvent e) {
                    fillNELst();
                }
            };

        tfTv.addFocusListener(lisLst);
        tfSb.addFocusListener(lisLst);
        tfIbi.addFocusListener(lisLst);
        tfLw.addFocusListener(lisLst);
        tfLv.addFocusListener(lisLst);
        tfLg.addFocusListener(lisLst);
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

        glassPanel = new de.cismet.tools.gui.RoundedPanel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        kartierabschnittUebersicht1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.KartierabschnittUebersicht();
        jpKruemmung = new javax.swing.JPanel();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        lblLauf = new javax.swing.JLabel();
        lblSpacing = new javax.swing.JLabel();
        sepMiddle = new javax.swing.JSeparator();
        lblErosion = new javax.swing.JLabel();
        rdLaufkruemmung = new de.cismet.cids.editors.DefaultBindableRadioButtonField();
        rdErosion = new de.cismet.cids.editors.DefaultBindableRadioButtonField();
        jpLaengsbaenke = new javax.swing.JPanel();
        panInfo2 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        lblUfkg = new javax.swing.JLabel();
        lblSpacing1 = new javax.swing.JLabel();
        tfUfkg = new javax.swing.JTextField();
        lblIb = new javax.swing.JLabel();
        tfIb = new javax.swing.JTextField();
        lblMb = new javax.swing.JLabel();
        tfMb = new javax.swing.JTextField();
        lblNe = new javax.swing.JLabel();
        cbNe = new javax.swing.JCheckBox();
        jpBesLaufstrukturen = new javax.swing.JPanel();
        panInfo3 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        lblTv = new javax.swing.JLabel();
        lblSpacing2 = new javax.swing.JLabel();
        tfTv = new javax.swing.JTextField();
        lbSb = new javax.swing.JLabel();
        tfSb = new javax.swing.JTextField();
        lblIbi = new javax.swing.JLabel();
        tfIbi = new javax.swing.JTextField();
        lblLg = new javax.swing.JLabel();
        tfLg = new javax.swing.JTextField();
        lblLv = new javax.swing.JLabel();
        tfLv = new javax.swing.JTextField();
        lblLw = new javax.swing.JLabel();
        tfLw = new javax.swing.JTextField();
        lblNe1 = new javax.swing.JLabel();
        cbNe1 = new javax.swing.JCheckBox();

        setMaximumSize(new java.awt.Dimension(1100, 650));
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1100, 650));
        setLayout(new java.awt.GridBagLayout());

        glassPanel.setAlpha(0);
        glassPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(glassPanel, gridBagConstraints);

        panInfo.setLayout(new java.awt.GridBagLayout());

        kartierabschnittUebersicht1.setMinimumSize(new java.awt.Dimension(1100, 100));
        kartierabschnittUebersicht1.setPreferredSize(new java.awt.Dimension(1100, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panInfo.add(kartierabschnittUebersicht1, gridBagConstraints);

        jpKruemmung.setMinimumSize(new java.awt.Dimension(1100, 230));
        jpKruemmung.setOpaque(false);
        jpKruemmung.setPreferredSize(new java.awt.Dimension(1100, 230));
        jpKruemmung.setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo1.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblLauf.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblLauf.text")); // NOI18N
        lblLauf.setMaximumSize(new java.awt.Dimension(120, 17));
        lblLauf.setMinimumSize(new java.awt.Dimension(130, 17));
        lblLauf.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblLauf, gridBagConstraints);
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
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 5, 25);
        panInfoContent.add(sepMiddle, gridBagConstraints);

        lblErosion.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblErosion.text")); // NOI18N
        lblErosion.setMinimumSize(new java.awt.Dimension(130, 17));
        lblErosion.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblErosion, gridBagConstraints);

        rdLaufkruemmung.setMinimumSize(new java.awt.Dimension(170, 180));
        rdLaufkruemmung.setOpaque(false);
        rdLaufkruemmung.setPreferredSize(new java.awt.Dimension(170, 180));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laufkruemmung_id}"),
                rdLaufkruemmung,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent.add(rdLaufkruemmung, gridBagConstraints);

        rdErosion.setMinimumSize(new java.awt.Dimension(170, 140));
        rdErosion.setOpaque(false);
        rdErosion.setPreferredSize(new java.awt.Dimension(170, 140));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kruemmungserosion_id}"),
                rdErosion,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent.add(rdErosion, gridBagConstraints);

        panInfo1.add(panInfoContent, java.awt.BorderLayout.CENTER);

        jpKruemmung.add(panInfo1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panInfo.add(jpKruemmung, gridBagConstraints);

        jpLaengsbaenke.setMinimumSize(new java.awt.Dimension(540, 250));
        jpLaengsbaenke.setOpaque(false);
        jpLaengsbaenke.setPreferredSize(new java.awt.Dimension(540, 250));
        jpLaengsbaenke.setLayout(new java.awt.BorderLayout());

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo2.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        lblUfkg.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblUfkg.text")); // NOI18N
        lblUfkg.setMaximumSize(new java.awt.Dimension(250, 17));
        lblUfkg.setMinimumSize(new java.awt.Dimension(240, 17));
        lblUfkg.setPreferredSize(new java.awt.Dimension(240, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent1.add(lblUfkg, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent1.add(lblSpacing1, gridBagConstraints);

        tfUfkg.setMinimumSize(new java.awt.Dimension(50, 20));
        tfUfkg.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laengsbaenke_ufkg}"),
                tfUfkg,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent1.add(tfUfkg, gridBagConstraints);

        lblIb.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblIb.text")); // NOI18N
        lblIb.setMaximumSize(new java.awt.Dimension(250, 17));
        lblIb.setMinimumSize(new java.awt.Dimension(240, 17));
        lblIb.setPreferredSize(new java.awt.Dimension(240, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblIb, gridBagConstraints);

        tfIb.setMinimumSize(new java.awt.Dimension(50, 20));
        tfIb.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laengsbaenke_ib}"),
                tfIb,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(tfIb, gridBagConstraints);

        lblMb.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblMb.text")); // NOI18N
        lblMb.setMaximumSize(new java.awt.Dimension(250, 17));
        lblMb.setMinimumSize(new java.awt.Dimension(240, 17));
        lblMb.setPreferredSize(new java.awt.Dimension(240, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblMb, gridBagConstraints);

        tfMb.setMinimumSize(new java.awt.Dimension(50, 20));
        tfMb.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laengsbaenke_mb}"),
                tfMb,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(tfMb, gridBagConstraints);

        lblNe.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblNe.text")); // NOI18N
        lblNe.setMaximumSize(new java.awt.Dimension(120, 17));
        lblNe.setMinimumSize(new java.awt.Dimension(140, 17));
        lblNe.setPreferredSize(new java.awt.Dimension(140, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblNe, gridBagConstraints);

        cbNe.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.cbNe.text")); // NOI18N
        cbNe.setContentAreaFilled(false);
        cbNe.addChangeListener(new javax.swing.event.ChangeListener() {

                @Override
                public void stateChanged(final javax.swing.event.ChangeEvent evt) {
                    cbNeStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 5, 10);
        panInfoContent1.add(cbNe, gridBagConstraints);

        panInfo2.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        jpLaengsbaenke.add(panInfo2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panInfo.add(jpLaengsbaenke, gridBagConstraints);

        jpBesLaufstrukturen.setMinimumSize(new java.awt.Dimension(540, 250));
        jpBesLaufstrukturen.setOpaque(false);
        jpBesLaufstrukturen.setPreferredSize(new java.awt.Dimension(540, 250));
        jpBesLaufstrukturen.setLayout(new java.awt.BorderLayout());

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblHeading2.text")); // NOI18N
        panHeadInfo2.add(lblHeading2);

        panInfo3.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        lblTv.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblTv.text")); // NOI18N
        lblTv.setMaximumSize(new java.awt.Dimension(250, 17));
        lblTv.setMinimumSize(new java.awt.Dimension(240, 17));
        lblTv.setPreferredSize(new java.awt.Dimension(240, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent2.add(lblTv, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent2.add(lblSpacing2, gridBagConstraints);

        tfTv.setMinimumSize(new java.awt.Dimension(50, 20));
        tfTv.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laufstrukturen_tv}"),
                tfTv,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent2.add(tfTv, gridBagConstraints);

        lbSb.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lbSb.text")); // NOI18N
        lbSb.setMaximumSize(new java.awt.Dimension(250, 17));
        lbSb.setMinimumSize(new java.awt.Dimension(240, 17));
        lbSb.setPreferredSize(new java.awt.Dimension(240, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent2.add(lbSb, gridBagConstraints);

        tfSb.setMinimumSize(new java.awt.Dimension(50, 20));
        tfSb.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laufstrukturen_sb}"),
                tfSb,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent2.add(tfSb, gridBagConstraints);

        lblIbi.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblIbi.text")); // NOI18N
        lblIbi.setMaximumSize(new java.awt.Dimension(250, 17));
        lblIbi.setMinimumSize(new java.awt.Dimension(240, 17));
        lblIbi.setPreferredSize(new java.awt.Dimension(240, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent2.add(lblIbi, gridBagConstraints);

        tfIbi.setMinimumSize(new java.awt.Dimension(50, 20));
        tfIbi.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laufstrukturen_ibi}"),
                tfIbi,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent2.add(tfIbi, gridBagConstraints);

        lblLg.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblLg.text")); // NOI18N
        lblLg.setMaximumSize(new java.awt.Dimension(250, 17));
        lblLg.setMinimumSize(new java.awt.Dimension(240, 17));
        lblLg.setPreferredSize(new java.awt.Dimension(240, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent2.add(lblLg, gridBagConstraints);

        tfLg.setMinimumSize(new java.awt.Dimension(50, 20));
        tfLg.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laufstrukturen_lg}"),
                tfLg,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent2.add(tfLg, gridBagConstraints);

        lblLv.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblLv.text")); // NOI18N
        lblLv.setMaximumSize(new java.awt.Dimension(250, 17));
        lblLv.setMinimumSize(new java.awt.Dimension(240, 17));
        lblLv.setPreferredSize(new java.awt.Dimension(240, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent2.add(lblLv, gridBagConstraints);

        tfLv.setMinimumSize(new java.awt.Dimension(50, 20));
        tfLv.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laufstrukturen_lv}"),
                tfLv,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent2.add(tfLv, gridBagConstraints);

        lblLw.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblLw.text")); // NOI18N
        lblLw.setMaximumSize(new java.awt.Dimension(250, 17));
        lblLw.setMinimumSize(new java.awt.Dimension(240, 17));
        lblLw.setPreferredSize(new java.awt.Dimension(240, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent2.add(lblLw, gridBagConstraints);

        tfLw.setMinimumSize(new java.awt.Dimension(50, 20));
        tfLw.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laufstrukturen_lw}"),
                tfLw,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent2.add(tfLw, gridBagConstraints);

        lblNe1.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.lblNe1.text")); // NOI18N
        lblNe1.setMaximumSize(new java.awt.Dimension(120, 17));
        lblNe1.setMinimumSize(new java.awt.Dimension(140, 17));
        lblNe1.setPreferredSize(new java.awt.Dimension(140, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent2.add(lblNe1, gridBagConstraints);

        cbNe1.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittLaufentwicklung.class,
                "FgskKartierabschnittLaufentwicklung.cbNe1.text")); // NOI18N
        cbNe1.setContentAreaFilled(false);
        cbNe1.addChangeListener(new javax.swing.event.ChangeListener() {

                @Override
                public void stateChanged(final javax.swing.event.ChangeEvent evt) {
                    cbNe1StateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 5, 10);
        panInfoContent2.add(cbNe1, gridBagConstraints);

        panInfo3.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        jpBesLaufstrukturen.add(panInfo3, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
        panInfo.add(jpBesLaufstrukturen, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(panInfo, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbNeStateChanged(final javax.swing.event.ChangeEvent evt) { //GEN-FIRST:event_cbNeStateChanged
        if (cbNe.isSelected()) {
            boolean nothing = true;
            nothing &= (CidsBeanSupport.textToDouble(tfUfkg, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfIb, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfMb, 0.0) == 0.0);

            if (nothing) {
                tfUfkg.setText("0");
                tfIb.setText("0");
                tfMb.setText("0");
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Es sind bereits Felder auf einen Wert ungleich Null gesetzt.",
                    "Felder gesetzt",
                    JOptionPane.INFORMATION_MESSAGE);
                cbNe.setSelected(false);
            }
        }
    } //GEN-LAST:event_cbNeStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbNe1StateChanged(final javax.swing.event.ChangeEvent evt) { //GEN-FIRST:event_cbNe1StateChanged
        if (cbNe1.isSelected()) {
            boolean nothing = true;
            nothing &= (CidsBeanSupport.textToDouble(tfTv, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfSb, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfIbi, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfLw, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfLv, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfLg, 0.0) == 0.0);

            if (nothing) {
                tfTv.setText("0");
                tfSb.setText("0");
                tfIbi.setText("0");
                tfLw.setText("0");
                tfLv.setText("0");
                tfLg.setText("0");
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Es sind bereits Felder auf einen Wert ungleich Null gesetzt.",
                    "Felder gesetzt",
                    JOptionPane.INFORMATION_MESSAGE);
                cbNe1.setSelected(false);
            }
        }
    } //GEN-LAST:event_cbNe1StateChanged

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
            kartierabschnittUebersicht1.setCidsBean(cidsBean);
            fillNELae();
            fillNELst();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public void setReadOnly(final boolean readOnly) {
        if (readOnly) {
            glassPanel.addMouseListener(new MouseAdapter() {
                });
        } else {
            for (final MouseListener ml : glassPanel.getMouseListeners()) {
                glassPanel.removeMouseListener(ml);
            }
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
        kartierabschnittUebersicht1.dispose();
    }

    /**
     * DOCUMENT ME!
     */
    private void fillNELae() {
        boolean nothing = true;
        nothing &= (CidsBeanSupport.textToDouble(tfUfkg, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfIb, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfMb, 1.0) == 0.0);

        cbNe.setSelected(nothing);
    }

    /**
     * DOCUMENT ME!
     */
    private void fillNELst() {
        boolean nothing = true;
        nothing &= (CidsBeanSupport.textToDouble(tfTv, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfSb, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfIbi, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfLw, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfLv, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfLg, 1.0) == 0.0);

        cbNe1.setSelected(nothing);
    }
}
