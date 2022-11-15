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

import org.jdesktop.beansbinding.Converter;

import org.openide.util.NbBundle;
import org.openide.util.WeakListeners;

import java.awt.EventQueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.cismet.cids.custom.wrrl_db_mv.fgsk.Calc;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.CalcCache;
import de.cismet.cids.custom.wrrl_db_mv.util.RoundedNumberConverter;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class FgskKartierabschnittErgebnisse extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    private final transient PropertyChangeListener criteraCountChangeL;

    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jpGesamt;
    private javax.swing.JPanel jpLegende;
    private javax.swing.JPanel jpTeilbewertungSohle;
    private javax.swing.JPanel jpTeilbewertungUfer;
    private javax.swing.JPanel jpTeilbewertungUfer1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.KartierabschnittUebersicht kartierabschnittUebersicht1;
    private javax.swing.JLabel lblBankRating;
    private javax.swing.JLabel lblBankRatingLeft;
    private javax.swing.JLabel lblBankRatingRight;
    private javax.swing.JLabel lblBankStructCritCountLeft;
    private javax.swing.JLabel lblBankStructCritCountOverall;
    private javax.swing.JLabel lblBankStructCritCountRight;
    private javax.swing.JLabel lblBankStructRatingLeft;
    private javax.swing.JLabel lblBankStructRatingOverall;
    private javax.swing.JLabel lblBankStructRatingRight;
    private javax.swing.JLabel lblBedRating;
    private javax.swing.JLabel lblBedStructureCritCount;
    private javax.swing.JLabel lblBedStructureRating;
    private javax.swing.JLabel lblBedSubDiv;
    private javax.swing.JLabel lblCourseEvoCritCount;
    private javax.swing.JLabel lblCourseEvoRating;
    private javax.swing.JLabel lblCrossProfileCritCount;
    private javax.swing.JLabel lblCrossProfileRating;
    private javax.swing.JLabel lblEnvRating;
    private javax.swing.JLabel lblGewaessername;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblHeading3;
    private javax.swing.JLabel lblHeading4;
    private javax.swing.JLabel lblHeading5;
    private javax.swing.JLabel lblLongProfileCritCount;
    private javax.swing.JLabel lblLongProfileRating;
    private javax.swing.JLabel lblSpacing1;
    private javax.swing.JLabel lblSpacing3;
    private javax.swing.JLabel lblSpacing4;
    private javax.swing.JLabel lblSpacing5;
    private javax.swing.JLabel lblTest;
    private javax.swing.JLabel lblWBEnvCritCountLeft;
    private javax.swing.JLabel lblWBEnvCritCountOverall;
    private javax.swing.JLabel lblWBEnvCritCountRight;
    private javax.swing.JLabel lblWBEnvOverallRatingLeft;
    private javax.swing.JLabel lblWBEnvOverallRatingRight;
    private javax.swing.JLabel lblWBEnvRatingLeft;
    private javax.swing.JLabel lblWBEnvRatingOverall;
    private javax.swing.JLabel lblWBEnvRatingRight;
    private javax.swing.JLabel lblWBRating;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo3;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo4;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo5;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo3;
    private de.cismet.tools.gui.RoundedPanel panInfo4;
    private de.cismet.tools.gui.RoundedPanel panInfo5;
    private de.cismet.tools.gui.RoundedPanel panInfo6;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent2;
    private javax.swing.JPanel panInfoContent3;
    private javax.swing.JPanel panInfoContent4;
    private javax.swing.JPanel panInfoContent5;
    private javax.swing.JTextField txtBankRating;
    private javax.swing.JTextField txtBankRatingLeft;
    private javax.swing.JTextField txtBankRatingRight;
    private javax.swing.JTextField txtBankStructCritCountLeft;
    private javax.swing.JTextField txtBankStructCritCountOverall;
    private javax.swing.JTextField txtBankStructCritCountRight;
    private javax.swing.JTextField txtBankStructRatingLeft;
    private javax.swing.JTextField txtBankStructRatingOverall;
    private javax.swing.JTextField txtBankStructRatingRight;
    private javax.swing.JTextField txtBedRating;
    private javax.swing.JTextField txtBedStructureCritCount;
    private javax.swing.JTextField txtBedStructureRating;
    private javax.swing.JTextField txtBedSubDiv;
    private javax.swing.JTextField txtCourseEvoCritCount;
    private javax.swing.JTextField txtCourseEvoRating;
    private javax.swing.JTextField txtCrossProfileCritCount;
    private javax.swing.JTextField txtCrossProfileRating;
    private javax.swing.JTextField txtGueteklasse;
    private javax.swing.JTextField txtLongProfileCritCount;
    private javax.swing.JTextField txtLongProfileRating;
    private javax.swing.JTextField txtWBEnvCritCountLeft;
    private javax.swing.JTextField txtWBEnvCritCountOverall;
    private javax.swing.JTextField txtWBEnvCritCountRight;
    private javax.swing.JTextField txtWBEnvOverallRatingLeft;
    private javax.swing.JTextField txtWBEnvOverallRatingRight;
    private javax.swing.JTextField txtWBEnvRating;
    private javax.swing.JTextField txtWBEnvRatingLeft;
    private javax.swing.JTextField txtWBEnvRatingOverall;
    private javax.swing.JTextField txtWBEnvRatingRight;
    private javax.swing.JTextField txtWBRating;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public FgskKartierabschnittErgebnisse() {
        this.criteraCountChangeL = new CriteriaCountChangeL();

        initComponents();
        setOpaque(false);
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
        kartierabschnittUebersicht1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.KartierabschnittUebersicht();
        jpGesamt = new javax.swing.JPanel();
        panInfo2 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        lblSpacing1 = new javax.swing.JLabel();
        lblGewaessername = new javax.swing.JLabel();
        txtGueteklasse = new javax.swing.JTextField();
        lblWBRating = new javax.swing.JLabel();
        txtWBRating = new javax.swing.JTextField();
        lblBedRating = new javax.swing.JLabel();
        txtBedRating = new javax.swing.JTextField();
        lblBankRating = new javax.swing.JLabel();
        txtBankRating = new javax.swing.JTextField();
        lblEnvRating = new javax.swing.JLabel();
        txtWBEnvRating = new javax.swing.JTextField();
        lblBankRatingLeft = new javax.swing.JLabel();
        lblBankRatingRight = new javax.swing.JLabel();
        txtBankRatingRight = new javax.swing.JTextField();
        txtBankRatingLeft = new javax.swing.JTextField();
        lblWBEnvOverallRatingLeft = new javax.swing.JLabel();
        lblWBEnvOverallRatingRight = new javax.swing.JLabel();
        txtWBEnvOverallRatingLeft = new javax.swing.JTextField();
        txtWBEnvOverallRatingRight = new javax.swing.JTextField();
        jpLegende = new javax.swing.JPanel();
        panInfo3 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        lblTest = new javax.swing.JLabel();
        jpTeilbewertungSohle = new javax.swing.JPanel();
        panInfo4 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo3 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading3 = new javax.swing.JLabel();
        panInfoContent3 = new javax.swing.JPanel();
        lblSpacing3 = new javax.swing.JLabel();
        lblCourseEvoRating = new javax.swing.JLabel();
        txtCourseEvoRating = new javax.swing.JTextField();
        lblCourseEvoCritCount = new javax.swing.JLabel();
        txtCourseEvoCritCount = new javax.swing.JTextField();
        lblLongProfileRating = new javax.swing.JLabel();
        txtLongProfileRating = new javax.swing.JTextField();
        lblLongProfileCritCount = new javax.swing.JLabel();
        txtLongProfileCritCount = new javax.swing.JTextField();
        lblBedStructureRating = new javax.swing.JLabel();
        lblBedStructureCritCount = new javax.swing.JLabel();
        txtBedStructureRating = new javax.swing.JTextField();
        txtBedStructureCritCount = new javax.swing.JTextField();
        lblBedSubDiv = new javax.swing.JLabel();
        txtBedSubDiv = new javax.swing.JTextField();
        jpTeilbewertungUfer = new javax.swing.JPanel();
        panInfo5 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo4 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading4 = new javax.swing.JLabel();
        panInfoContent4 = new javax.swing.JPanel();
        lblSpacing4 = new javax.swing.JLabel();
        lblBankStructRatingOverall = new javax.swing.JLabel();
        lblBankStructRatingLeft = new javax.swing.JLabel();
        txtBankStructRatingOverall = new javax.swing.JTextField();
        txtBankStructCritCountOverall = new javax.swing.JTextField();
        lblBankStructCritCountLeft = new javax.swing.JLabel();
        lblBankStructRatingRight = new javax.swing.JLabel();
        lblBankStructCritCountRight = new javax.swing.JLabel();
        lblBankStructCritCountOverall = new javax.swing.JLabel();
        txtBankStructCritCountLeft = new javax.swing.JTextField();
        txtBankStructRatingRight = new javax.swing.JTextField();
        txtBankStructCritCountRight = new javax.swing.JTextField();
        txtBankStructRatingLeft = new javax.swing.JTextField();
        txtCrossProfileRating = new javax.swing.JTextField();
        txtCrossProfileCritCount = new javax.swing.JTextField();
        lblCrossProfileRating = new javax.swing.JLabel();
        lblCrossProfileCritCount = new javax.swing.JLabel();
        jpTeilbewertungUfer1 = new javax.swing.JPanel();
        panInfo6 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo5 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading5 = new javax.swing.JLabel();
        panInfoContent5 = new javax.swing.JPanel();
        lblSpacing5 = new javax.swing.JLabel();
        lblWBEnvRatingOverall = new javax.swing.JLabel();
        txtWBEnvRatingOverall = new javax.swing.JTextField();
        lblWBEnvCritCountOverall = new javax.swing.JLabel();
        txtWBEnvCritCountOverall = new javax.swing.JTextField();
        lblWBEnvRatingRight = new javax.swing.JLabel();
        txtWBEnvCritCountLeft = new javax.swing.JTextField();
        lblWBEnvRatingLeft = new javax.swing.JLabel();
        txtWBEnvRatingLeft = new javax.swing.JTextField();
        lblWBEnvCritCountRight = new javax.swing.JLabel();
        lblWBEnvCritCountLeft = new javax.swing.JLabel();
        txtWBEnvRatingRight = new javax.swing.JTextField();
        txtWBEnvCritCountRight = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(1100, 650));
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1100, 650));
        setLayout(new java.awt.BorderLayout());

        panInfo.setLayout(new java.awt.GridBagLayout());

        kartierabschnittUebersicht1.setMinimumSize(new java.awt.Dimension(1100, 100));
        kartierabschnittUebersicht1.setPreferredSize(new java.awt.Dimension(1100, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panInfo.add(kartierabschnittUebersicht1, gridBagConstraints);

        jpGesamt.setMinimumSize(new java.awt.Dimension(727, 230));
        jpGesamt.setOpaque(false);
        jpGesamt.setPreferredSize(new java.awt.Dimension(727, 230));
        jpGesamt.setLayout(new java.awt.BorderLayout());

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo2.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent1.add(lblSpacing1, gridBagConstraints);

        lblGewaessername.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblGewaessername.text")); // NOI18N
        lblGewaessername.setMaximumSize(new java.awt.Dimension(130, 17));
        lblGewaessername.setMinimumSize(new java.awt.Dimension(130, 17));
        lblGewaessername.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent1.add(lblGewaessername, gridBagConstraints);

        txtGueteklasse.setEditable(false);
        txtGueteklasse.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtGueteklasse.setBorder(null);
        txtGueteklasse.setMinimumSize(new java.awt.Dimension(170, 20));
        txtGueteklasse.setPreferredSize(new java.awt.Dimension(170, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent1.add(txtGueteklasse, gridBagConstraints);

        lblWBRating.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblWBRating.text")); // NOI18N
        lblWBRating.setMaximumSize(new java.awt.Dimension(130, 17));
        lblWBRating.setMinimumSize(new java.awt.Dimension(130, 17));
        lblWBRating.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblWBRating, gridBagConstraints);

        txtWBRating.setEditable(false);
        txtWBRating.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtWBRating.setBorder(null);
        txtWBRating.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWBRating.setPreferredSize(new java.awt.Dimension(170, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.punktzahl_gesamt}"),
                txtWBRating,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(RoundedNumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(txtWBRating, gridBagConstraints);

        lblBedRating.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBedRating.text")); // NOI18N
        lblBedRating.setMaximumSize(new java.awt.Dimension(130, 17));
        lblBedRating.setMinimumSize(new java.awt.Dimension(130, 17));
        lblBedRating.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblBedRating, gridBagConstraints);

        txtBedRating.setEditable(false);
        txtBedRating.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBedRating.setBorder(null);
        txtBedRating.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBedRating.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.punktzahl_sohle}"),
                txtBedRating,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(RoundedNumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(txtBedRating, gridBagConstraints);

        lblBankRating.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBankRating.text")); // NOI18N
        lblBankRating.setMaximumSize(new java.awt.Dimension(130, 17));
        lblBankRating.setMinimumSize(new java.awt.Dimension(130, 17));
        lblBankRating.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblBankRating, gridBagConstraints);

        txtBankRating.setEditable(false);
        txtBankRating.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBankRating.setBorder(null);
        txtBankRating.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBankRating.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.punktzahl_ufer}"),
                txtBankRating,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(RoundedNumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(txtBankRating, gridBagConstraints);

        lblEnvRating.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblEnvRating.text")); // NOI18N
        lblEnvRating.setMaximumSize(new java.awt.Dimension(130, 17));
        lblEnvRating.setMinimumSize(new java.awt.Dimension(130, 17));
        lblEnvRating.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblEnvRating, gridBagConstraints);

        txtWBEnvRating.setEditable(false);
        txtWBEnvRating.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtWBEnvRating.setBorder(null);
        txtWBEnvRating.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWBEnvRating.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.punktzahl_land}"),
                txtWBEnvRating,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(RoundedNumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(txtWBEnvRating, gridBagConstraints);

        lblBankRatingLeft.setText(NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBankRatingLeft.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblBankRatingLeft, gridBagConstraints);

        lblBankRatingRight.setText(NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBankRatingRight.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblBankRatingRight, gridBagConstraints);

        txtBankRatingRight.setEditable(false);
        txtBankRatingRight.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBankRatingRight.setBorder(null);
        txtBankRatingRight.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBankRatingRight.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.punktzahl_ufer_rechts}"),
                txtBankRatingRight,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(RoundedNumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(txtBankRatingRight, gridBagConstraints);

        txtBankRatingLeft.setEditable(false);
        txtBankRatingLeft.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBankRatingLeft.setBorder(null);
        txtBankRatingLeft.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBankRatingLeft.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.punktzahl_ufer_links}"),
                txtBankRatingLeft,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(RoundedNumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(txtBankRatingLeft, gridBagConstraints);

        lblWBEnvOverallRatingLeft.setText(NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblWBEnvOverallRatingLeft.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblWBEnvOverallRatingLeft, gridBagConstraints);

        lblWBEnvOverallRatingRight.setText(NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblWBEnvOverallRatingRight.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblWBEnvOverallRatingRight, gridBagConstraints);

        txtWBEnvOverallRatingLeft.setEditable(false);
        txtWBEnvOverallRatingLeft.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtWBEnvOverallRatingLeft.setBorder(null);
        txtWBEnvOverallRatingLeft.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWBEnvOverallRatingLeft.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.punktzahl_land_links}"),
                txtWBEnvOverallRatingLeft,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(txtWBEnvOverallRatingLeft, gridBagConstraints);

        txtWBEnvOverallRatingRight.setEditable(false);
        txtWBEnvOverallRatingRight.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtWBEnvOverallRatingRight.setBorder(null);
        txtWBEnvOverallRatingRight.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWBEnvOverallRatingRight.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.punktzahl_land_rechts}"),
                txtWBEnvOverallRatingRight,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(txtWBEnvOverallRatingRight, gridBagConstraints);

        panInfo2.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        jpGesamt.add(panInfo2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panInfo.add(jpGesamt, gridBagConstraints);

        jpLegende.setMinimumSize(new java.awt.Dimension(353, 230));
        jpLegende.setOpaque(false);
        jpLegende.setPreferredSize(new java.awt.Dimension(353, 230));
        jpLegende.setLayout(new java.awt.BorderLayout());

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblHeading2.text")); // NOI18N
        panHeadInfo2.add(lblHeading2);

        panInfo3.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        lblTest.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/fgsk_legende.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panInfoContent2.add(lblTest, gridBagConstraints);

        panInfo3.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        jpLegende.add(panInfo3, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
        panInfo.add(jpLegende, gridBagConstraints);

        jpTeilbewertungSohle.setMinimumSize(new java.awt.Dimension(353, 260));
        jpTeilbewertungSohle.setOpaque(false);
        jpTeilbewertungSohle.setPreferredSize(new java.awt.Dimension(354, 260));
        jpTeilbewertungSohle.setLayout(new java.awt.BorderLayout());

        panHeadInfo3.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo3.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setLayout(new java.awt.FlowLayout());

        lblHeading3.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading3.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblHeading3.text")); // NOI18N
        panHeadInfo3.add(lblHeading3);

        panInfo4.add(panHeadInfo3, java.awt.BorderLayout.NORTH);

        panInfoContent3.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent3.setOpaque(false);
        panInfoContent3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent3.add(lblSpacing3, gridBagConstraints);

        lblCourseEvoRating.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblCourseEvoRating.text")); // NOI18N
        lblCourseEvoRating.setMaximumSize(new java.awt.Dimension(230, 17));
        lblCourseEvoRating.setMinimumSize(new java.awt.Dimension(230, 17));
        lblCourseEvoRating.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent3.add(lblCourseEvoRating, gridBagConstraints);

        txtCourseEvoRating.setEditable(false);
        txtCourseEvoRating.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtCourseEvoRating.setBorder(null);
        txtCourseEvoRating.setMinimumSize(new java.awt.Dimension(170, 20));
        txtCourseEvoRating.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laufentwicklung_summe_punktzahl}"),
                txtCourseEvoRating,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        binding.setConverter(new MaxRatingConverter("laufentwicklung_anzahl_kriterien"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent3.add(txtCourseEvoRating, gridBagConstraints);

        lblCourseEvoCritCount.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblCourseEvoCritCount.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent3.add(lblCourseEvoCritCount, gridBagConstraints);

        txtCourseEvoCritCount.setEditable(false);
        txtCourseEvoCritCount.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtCourseEvoCritCount.setBorder(null);
        txtCourseEvoCritCount.setMinimumSize(new java.awt.Dimension(170, 20));
        txtCourseEvoCritCount.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laufentwicklung_anzahl_kriterien}"),
                txtCourseEvoCritCount,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent3.add(txtCourseEvoCritCount, gridBagConstraints);

        lblLongProfileRating.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblLongProfileRating.text")); // NOI18N
        lblLongProfileRating.setMaximumSize(new java.awt.Dimension(230, 17));
        lblLongProfileRating.setMinimumSize(new java.awt.Dimension(230, 17));
        lblLongProfileRating.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent3.add(lblLongProfileRating, gridBagConstraints);

        txtLongProfileRating.setEditable(false);
        txtLongProfileRating.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtLongProfileRating.setBorder(null);
        txtLongProfileRating.setMinimumSize(new java.awt.Dimension(170, 20));
        txtLongProfileRating.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laengsprofil_summe_punktzahl}"),
                txtLongProfileRating,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        binding.setConverter(new MaxRatingConverter("laengsprofil_anzahl_kriterien"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent3.add(txtLongProfileRating, gridBagConstraints);

        lblLongProfileCritCount.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblLongProfileCritCount.text")); // NOI18N
        lblLongProfileCritCount.setMaximumSize(new java.awt.Dimension(230, 17));
        lblLongProfileCritCount.setMinimumSize(new java.awt.Dimension(230, 17));
        lblLongProfileCritCount.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent3.add(lblLongProfileCritCount, gridBagConstraints);

        txtLongProfileCritCount.setEditable(false);
        txtLongProfileCritCount.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtLongProfileCritCount.setBorder(null);
        txtLongProfileCritCount.setMinimumSize(new java.awt.Dimension(170, 20));
        txtLongProfileCritCount.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laengsprofil_anzahl_kriterien}"),
                txtLongProfileCritCount,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent3.add(txtLongProfileCritCount, gridBagConstraints);

        lblBedStructureRating.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBedStructureRating.text")); // NOI18N
        lblBedStructureRating.setMaximumSize(new java.awt.Dimension(230, 17));
        lblBedStructureRating.setMinimumSize(new java.awt.Dimension(230, 17));
        lblBedStructureRating.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent3.add(lblBedStructureRating, gridBagConstraints);

        lblBedStructureCritCount.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBedStructureCritCount.text")); // NOI18N
        lblBedStructureCritCount.setMaximumSize(new java.awt.Dimension(230, 17));
        lblBedStructureCritCount.setMinimumSize(new java.awt.Dimension(230, 17));
        lblBedStructureCritCount.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent3.add(lblBedStructureCritCount, gridBagConstraints);

        txtBedStructureRating.setEditable(false);
        txtBedStructureRating.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBedStructureRating.setBorder(null);
        txtBedStructureRating.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBedStructureRating.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sohlenstruktur_summe_punktzahl}"),
                txtBedStructureRating,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        binding.setConverter(new MaxRatingConverter("sohlenstruktur_anzahl_kriterien"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent3.add(txtBedStructureRating, gridBagConstraints);

        txtBedStructureCritCount.setEditable(false);
        txtBedStructureCritCount.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBedStructureCritCount.setBorder(null);
        txtBedStructureCritCount.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBedStructureCritCount.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sohlenstruktur_anzahl_kriterien}"),
                txtBedStructureCritCount,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent3.add(txtBedStructureCritCount, gridBagConstraints);

        lblBedSubDiv.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBedSubDiv.text")); // NOI18N
        lblBedSubDiv.setMaximumSize(new java.awt.Dimension(230, 17));
        lblBedSubDiv.setMinimumSize(new java.awt.Dimension(230, 17));
        lblBedSubDiv.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent3.add(lblBedSubDiv, gridBagConstraints);

        txtBedSubDiv.setEditable(false);
        txtBedSubDiv.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBedSubDiv.setBorder(null);
        txtBedSubDiv.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBedSubDiv.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bewertung_substratdiversitaet}"),
                txtBedSubDiv,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent3.add(txtBedSubDiv, gridBagConstraints);

        panInfo4.add(panInfoContent3, java.awt.BorderLayout.CENTER);

        jpTeilbewertungSohle.add(panInfo4, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panInfo.add(jpTeilbewertungSohle, gridBagConstraints);

        jpTeilbewertungUfer.setMinimumSize(new java.awt.Dimension(353, 260));
        jpTeilbewertungUfer.setOpaque(false);
        jpTeilbewertungUfer.setPreferredSize(new java.awt.Dimension(353, 260));
        jpTeilbewertungUfer.setLayout(new java.awt.BorderLayout());

        panHeadInfo4.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo4.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setLayout(new java.awt.FlowLayout());

        lblHeading4.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading4.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblHeading4.text")); // NOI18N
        panHeadInfo4.add(lblHeading4);

        panInfo5.add(panHeadInfo4, java.awt.BorderLayout.NORTH);

        panInfoContent4.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent4.setOpaque(false);
        panInfoContent4.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent4.add(lblSpacing4, gridBagConstraints);

        lblBankStructRatingOverall.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBankStructRatingOverall.text")); // NOI18N
        lblBankStructRatingOverall.setMaximumSize(new java.awt.Dimension(230, 17));
        lblBankStructRatingOverall.setMinimumSize(new java.awt.Dimension(230, 17));
        lblBankStructRatingOverall.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent4.add(lblBankStructRatingOverall, gridBagConstraints);

        lblBankStructRatingLeft.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBankStructRatingLeft.text")); // NOI18N
        lblBankStructRatingLeft.setMaximumSize(new java.awt.Dimension(230, 17));
        lblBankStructRatingLeft.setMinimumSize(new java.awt.Dimension(230, 17));
        lblBankStructRatingLeft.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent4.add(lblBankStructRatingLeft, gridBagConstraints);

        txtBankStructRatingOverall.setEditable(false);
        txtBankStructRatingOverall.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBankStructRatingOverall.setBorder(null);
        txtBankStructRatingOverall.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBankStructRatingOverall.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_summe_punktzahl}"),
                txtBankStructRatingOverall,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        binding.setConverter(new MaxRatingConverter("uferstruktur_anzahl_kriterien"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent4.add(txtBankStructRatingOverall, gridBagConstraints);

        txtBankStructCritCountOverall.setEditable(false);
        txtBankStructCritCountOverall.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBankStructCritCountOverall.setBorder(null);
        txtBankStructCritCountOverall.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBankStructCritCountOverall.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_anzahl_kriterien}"),
                txtBankStructCritCountOverall,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent4.add(txtBankStructCritCountOverall, gridBagConstraints);

        lblBankStructCritCountLeft.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBankStructCritCountLeft.text")); // NOI18N
        lblBankStructCritCountLeft.setMaximumSize(new java.awt.Dimension(230, 17));
        lblBankStructCritCountLeft.setMinimumSize(new java.awt.Dimension(230, 17));
        lblBankStructCritCountLeft.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent4.add(lblBankStructCritCountLeft, gridBagConstraints);

        lblBankStructRatingRight.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBankStructRatingRight.text")); // NOI18N
        lblBankStructRatingRight.setMaximumSize(new java.awt.Dimension(230, 17));
        lblBankStructRatingRight.setMinimumSize(new java.awt.Dimension(230, 17));
        lblBankStructRatingRight.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent4.add(lblBankStructRatingRight, gridBagConstraints);

        lblBankStructCritCountRight.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBankStructCritCountRight.text")); // NOI18N
        lblBankStructCritCountRight.setMaximumSize(new java.awt.Dimension(230, 17));
        lblBankStructCritCountRight.setMinimumSize(new java.awt.Dimension(230, 17));
        lblBankStructCritCountRight.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent4.add(lblBankStructCritCountRight, gridBagConstraints);

        lblBankStructCritCountOverall.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblBankStructCritCountOverall.text")); // NOI18N
        lblBankStructCritCountOverall.setMaximumSize(new java.awt.Dimension(230, 17));
        lblBankStructCritCountOverall.setMinimumSize(new java.awt.Dimension(230, 17));
        lblBankStructCritCountOverall.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent4.add(lblBankStructCritCountOverall, gridBagConstraints);

        txtBankStructCritCountLeft.setEditable(false);
        txtBankStructCritCountLeft.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBankStructCritCountLeft.setBorder(null);
        txtBankStructCritCountLeft.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBankStructCritCountLeft.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_anzahl_kriterien_links}"),
                txtBankStructCritCountLeft,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent4.add(txtBankStructCritCountLeft, gridBagConstraints);

        txtBankStructRatingRight.setEditable(false);
        txtBankStructRatingRight.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBankStructRatingRight.setBorder(null);
        txtBankStructRatingRight.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBankStructRatingRight.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_summe_punktzahl_rechts}"),
                txtBankStructRatingRight,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        binding.setConverter(new MaxRatingConverter("uferstruktur_anzahl_kriterien_rechts"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent4.add(txtBankStructRatingRight, gridBagConstraints);

        txtBankStructCritCountRight.setEditable(false);
        txtBankStructCritCountRight.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBankStructCritCountRight.setBorder(null);
        txtBankStructCritCountRight.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBankStructCritCountRight.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_anzahl_kriterien_rechts}"),
                txtBankStructCritCountRight,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent4.add(txtBankStructCritCountRight, gridBagConstraints);

        txtBankStructRatingLeft.setEditable(false);
        txtBankStructRatingLeft.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBankStructRatingLeft.setBorder(null);
        txtBankStructRatingLeft.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBankStructRatingLeft.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_summe_punktzahl_links}"),
                txtBankStructRatingLeft,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        binding.setConverter(new MaxRatingConverter("uferstruktur_anzahl_kriterien_links"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent4.add(txtBankStructRatingLeft, gridBagConstraints);

        txtCrossProfileRating.setEditable(false);
        txtCrossProfileRating.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtCrossProfileRating.setBorder(null);
        txtCrossProfileRating.setMinimumSize(new java.awt.Dimension(170, 20));
        txtCrossProfileRating.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.querprofil_summe_punktzahl}"),
                txtCrossProfileRating,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        binding.setConverter(new MaxRatingConverter("querprofil_anzahl_kriterien"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent4.add(txtCrossProfileRating, gridBagConstraints);

        txtCrossProfileCritCount.setEditable(false);
        txtCrossProfileCritCount.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtCrossProfileCritCount.setBorder(null);
        txtCrossProfileCritCount.setMinimumSize(new java.awt.Dimension(170, 20));
        txtCrossProfileCritCount.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.querprofil_anzahl_kriterien}"),
                txtCrossProfileCritCount,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent4.add(txtCrossProfileCritCount, gridBagConstraints);

        lblCrossProfileRating.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblCrossProfileRating.text")); // NOI18N
        lblCrossProfileRating.setMaximumSize(new java.awt.Dimension(230, 17));
        lblCrossProfileRating.setMinimumSize(new java.awt.Dimension(230, 17));
        lblCrossProfileRating.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent4.add(lblCrossProfileRating, gridBagConstraints);

        lblCrossProfileCritCount.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblCrossProfileCritCount.text")); // NOI18N
        lblCrossProfileCritCount.setMaximumSize(new java.awt.Dimension(230, 17));
        lblCrossProfileCritCount.setMinimumSize(new java.awt.Dimension(230, 17));
        lblCrossProfileCritCount.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent4.add(lblCrossProfileCritCount, gridBagConstraints);

        panInfo5.add(panInfoContent4, java.awt.BorderLayout.CENTER);

        jpTeilbewertungUfer.add(panInfo5, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
        panInfo.add(jpTeilbewertungUfer, gridBagConstraints);

        jpTeilbewertungUfer1.setMinimumSize(new java.awt.Dimension(353, 260));
        jpTeilbewertungUfer1.setOpaque(false);
        jpTeilbewertungUfer1.setPreferredSize(new java.awt.Dimension(353, 260));
        jpTeilbewertungUfer1.setLayout(new java.awt.BorderLayout());

        panHeadInfo5.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo5.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo5.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo5.setLayout(new java.awt.FlowLayout());

        lblHeading5.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading5.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblHeading5.text")); // NOI18N
        panHeadInfo5.add(lblHeading5);

        panInfo6.add(panHeadInfo5, java.awt.BorderLayout.NORTH);

        panInfoContent5.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent5.setOpaque(false);
        panInfoContent5.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent5.add(lblSpacing5, gridBagConstraints);

        lblWBEnvRatingOverall.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblWBEnvRatingOverall.text")); // NOI18N
        lblWBEnvRatingOverall.setMaximumSize(new java.awt.Dimension(255, 17));
        lblWBEnvRatingOverall.setMinimumSize(new java.awt.Dimension(255, 17));
        lblWBEnvRatingOverall.setPreferredSize(new java.awt.Dimension(255, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent5.add(lblWBEnvRatingOverall, gridBagConstraints);

        txtWBEnvRatingOverall.setEditable(false);
        txtWBEnvRatingOverall.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtWBEnvRatingOverall.setBorder(null);
        txtWBEnvRatingOverall.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWBEnvRatingOverall.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewaesserumfeld_summe_punktzahl}"),
                txtWBEnvRatingOverall,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        binding.setConverter(new MaxRatingConverter("gewaesserumfeld_anzahl_kriterien"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent5.add(txtWBEnvRatingOverall, gridBagConstraints);

        lblWBEnvCritCountOverall.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblWBEnvCritCountOverall.text")); // NOI18N
        lblWBEnvCritCountOverall.setMaximumSize(new java.awt.Dimension(255, 17));
        lblWBEnvCritCountOverall.setMinimumSize(new java.awt.Dimension(255, 17));
        lblWBEnvCritCountOverall.setPreferredSize(new java.awt.Dimension(255, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent5.add(lblWBEnvCritCountOverall, gridBagConstraints);

        txtWBEnvCritCountOverall.setEditable(false);
        txtWBEnvCritCountOverall.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtWBEnvCritCountOverall.setBorder(null);
        txtWBEnvCritCountOverall.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWBEnvCritCountOverall.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewaesserumfeld_anzahl_kriterien}"),
                txtWBEnvCritCountOverall,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent5.add(txtWBEnvCritCountOverall, gridBagConstraints);

        lblWBEnvRatingRight.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblWBEnvRatingRight.text")); // NOI18N
        lblWBEnvRatingRight.setMaximumSize(new java.awt.Dimension(255, 17));
        lblWBEnvRatingRight.setMinimumSize(new java.awt.Dimension(255, 17));
        lblWBEnvRatingRight.setPreferredSize(new java.awt.Dimension(255, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent5.add(lblWBEnvRatingRight, gridBagConstraints);

        txtWBEnvCritCountLeft.setEditable(false);
        txtWBEnvCritCountLeft.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtWBEnvCritCountLeft.setBorder(null);
        txtWBEnvCritCountLeft.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWBEnvCritCountLeft.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewaesserumfeld_anzahl_kriterien_links}"),
                txtWBEnvCritCountLeft,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent5.add(txtWBEnvCritCountLeft, gridBagConstraints);

        lblWBEnvRatingLeft.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblWBEnvRatingLeft.text")); // NOI18N
        lblWBEnvRatingLeft.setMaximumSize(new java.awt.Dimension(255, 17));
        lblWBEnvRatingLeft.setMinimumSize(new java.awt.Dimension(255, 17));
        lblWBEnvRatingLeft.setPreferredSize(new java.awt.Dimension(255, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent5.add(lblWBEnvRatingLeft, gridBagConstraints);

        txtWBEnvRatingLeft.setEditable(false);
        txtWBEnvRatingLeft.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtWBEnvRatingLeft.setBorder(null);
        txtWBEnvRatingLeft.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWBEnvRatingLeft.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewaesserumfeld_summe_punktzahl_links}"),
                txtWBEnvRatingLeft,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        binding.setConverter(new MaxRatingConverter("gewaesserumfeld_anzahl_kriterien_links"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent5.add(txtWBEnvRatingLeft, gridBagConstraints);

        lblWBEnvCritCountRight.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblWBEnvCritCountRight.text")); // NOI18N
        lblWBEnvCritCountRight.setMaximumSize(new java.awt.Dimension(255, 17));
        lblWBEnvCritCountRight.setMinimumSize(new java.awt.Dimension(255, 17));
        lblWBEnvCritCountRight.setPreferredSize(new java.awt.Dimension(255, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent5.add(lblWBEnvCritCountRight, gridBagConstraints);

        lblWBEnvCritCountLeft.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittErgebnisse.class,
                "FgskKartierabschnittErgebnisse.lblWBEnvCritCountLeft.text")); // NOI18N
        lblWBEnvCritCountLeft.setMaximumSize(new java.awt.Dimension(255, 17));
        lblWBEnvCritCountLeft.setMinimumSize(new java.awt.Dimension(255, 17));
        lblWBEnvCritCountLeft.setPreferredSize(new java.awt.Dimension(255, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent5.add(lblWBEnvCritCountLeft, gridBagConstraints);

        txtWBEnvRatingRight.setEditable(false);
        txtWBEnvRatingRight.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtWBEnvRatingRight.setBorder(null);
        txtWBEnvRatingRight.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWBEnvRatingRight.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewaesserumfeld_summe_punktzahl_rechts}"),
                txtWBEnvRatingRight,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        binding.setConverter(new MaxRatingConverter("gewaesserumfeld_anzahl_kriterien_rechts"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent5.add(txtWBEnvRatingRight, gridBagConstraints);

        txtWBEnvCritCountRight.setEditable(false);
        txtWBEnvCritCountRight.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtWBEnvCritCountRight.setBorder(null);
        txtWBEnvCritCountRight.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWBEnvCritCountRight.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewaesserumfeld_anzahl_kriterien_rechts}"),
                txtWBEnvCritCountRight,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht berechnet>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent5.add(txtWBEnvCritCountRight, gridBagConstraints);

        panInfo6.add(panInfoContent5, java.awt.BorderLayout.CENTER);

        jpTeilbewertungUfer1.add(panInfo6, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
        panInfo.add(jpTeilbewertungUfer1, gridBagConstraints);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

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
            this.cidsBean.addPropertyChangeListener(WeakListeners.propertyChange(criteraCountChangeL, this.cidsBean));

            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                this.cidsBean);
            bindingGroup.bind();
            kartierabschnittUebersicht1.setCidsBean(cidsBean);
            refreshGueteklasse();
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        FgskKartierabschnittErgebnisse.this.requestFocus();
                    }
                });
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   rating      DOCUMENT ME!
     * @param   critCountO  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getRatingString(final Object rating, final Object critCountO) {
        return getRatingString(rating, critCountO, null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   rating      DOCUMENT ME!
     * @param   critCountO  DOCUMENT ME!
     * @param   bonus       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getRatingString(final Object rating, final Object critCountO, final Object bonus) {
        if (rating instanceof Double) {
            final StringBuilder sb = new StringBuilder();
            sb.append(rating);

            if (critCountO instanceof Integer) {
                double maxPoints = ((Integer)critCountO) * 5;
                if (bonus instanceof Number) {
                    maxPoints += ((Number)bonus).doubleValue();
                }

                sb.append(" / ").append(maxPoints); // NOI18N
            }

            return sb.toString();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkk  DOCUMENT ME!
     */
    public void setWkk(final String wkk) {
        kartierabschnittUebersicht1.setWkk(wkk);
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
        kartierabschnittUebersicht1.dispose();
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshGueteklasse() {
        String gueteklasse = "nicht bewertet";

        final Double p = (Double)cidsBean.getProperty("punktzahl_gesamt");
        final CidsBean exception = (CidsBean)cidsBean.getProperty(Calc.PROP_EXCEPTION);

        if ((exception != null) && Integer.valueOf(1).equals(exception.getProperty(Calc.PROP_VALUE))) {
            gueteklasse = "5";
        } else if ((p != null) && (p > 0.0)) {
            gueteklasse = String.valueOf(CalcCache.getQualityClass(p));
        }

        txtGueteklasse.setText(gueteklasse);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class CriteriaCountChangeL implements PropertyChangeListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            final Object value = evt.getNewValue();
            if (value instanceof Integer) {
                if (Calc.PROP_COURSE_EVO_SUM_CRIT.equals(evt.getPropertyName())) {
                    // NOTE: this is not especially good design, you have to know that bonuses may be applied
                    final String ratingString = getRatingString(cidsBean.getProperty(Calc.PROP_COURSE_EVO_SUM_RATING),
                            value,
                            2.0);

                    assert ratingString != null : "illegal bind: rating string is null"; // NOI18N

                    txtCourseEvoRating.setText(ratingString);
                } else if (Calc.PROP_LONG_PROFILE_SUM_CRIT.equals(evt.getPropertyName())) {
                    final String ratingString = getRatingString(cidsBean.getProperty(Calc.PROP_LONG_PROFILE_SUM_RATING),
                            value);

                    assert ratingString != null : "illegal bind: rating string is null"; // NOI18N

                    txtLongProfileRating.setText(ratingString);
                } else if (Calc.PROP_BED_STRUCTURE_SUM_CRIT.equals(evt.getPropertyName())) {
                    // NOTE: this is not especially good design, you have to know that bonuses may be applied
                    final String ratingString = getRatingString(cidsBean.getProperty(
                                Calc.PROP_BED_STRUCTURE_SUM_RATING),
                            value,
                            2.0);

                    assert ratingString != null : "illegal bind: rating string is null"; // NOI18N

                    txtBedStructureRating.setText(ratingString);
                } else if (Calc.PROP_BANK_STRUCTURE_SUM_CRIT.equals(evt.getPropertyName())) {
                    final String ratingString = getRatingString(cidsBean.getProperty(
                                Calc.PROP_BANK_STRUCTURE_SUM_RATING),
                            value);

                    assert ratingString != null : "illegal bind: rating string is null"; // NOI18N

                    txtBankStructRatingOverall.setText(ratingString);
                } else if (Calc.PROP_BANK_STRUCTURE_SUM_CRIT_LE.equals(evt.getPropertyName())) {
                    final String ratingString = getRatingString(cidsBean.getProperty(
                                Calc.PROP_BANK_STRUCTURE_SUM_RATING_LE),
                            value);

                    assert ratingString != null : "illegal bind: rating string is null"; // NOI18N

                    txtBankStructRatingLeft.setText(ratingString);
                } else if (Calc.PROP_BANK_STRUCTURE_SUM_CRIT_RI.equals(evt.getPropertyName())) {
                    final String ratingString = getRatingString(cidsBean.getProperty(
                                Calc.PROP_BANK_STRUCTURE_SUM_RATING_RI),
                            value);

                    assert ratingString != null : "illegal bind: rating string is null"; // NOI18N

                    txtBankStructRatingRight.setText(ratingString);
                } else if (Calc.PROP_CROSS_PROFILE_SUM_CRIT.equals(evt.getPropertyName())) {
                    final String ratingString = getRatingString(cidsBean.getProperty(
                                Calc.PROP_CROSS_PROFILE_SUM_RATING),
                            value);

                    assert ratingString != null : "illegal bind: rating string is null"; // NOI18N

                    txtCrossProfileRating.setText(ratingString);
                } else if (Calc.PROP_WB_ENV_SUM_CRIT.equals(evt.getPropertyName())) {
                    final String ratingString = getRatingString(cidsBean.getProperty(Calc.PROP_WB_ENV_SUM_RATING),
                            value);

                    assert ratingString != null : "illegal bind: rating string is null"; // NOI18N

                    txtWBEnvRatingOverall.setText(ratingString);
                } else if (Calc.PROP_WB_ENV_SUM_CRIT_LE.equals(evt.getPropertyName())) {
                    final String ratingString = getRatingString(cidsBean.getProperty(Calc.PROP_WB_ENV_SUM_RATING_LE),
                            value);

                    assert ratingString != null : "illegal bind: rating string is null"; // NOI18N

                    txtWBEnvRatingLeft.setText(ratingString);
                } else if (Calc.PROP_WB_ENV_SUM_CRIT_RI.equals(evt.getPropertyName())) {
                    final String ratingString = getRatingString(cidsBean.getProperty(Calc.PROP_WB_ENV_SUM_RATING_RI),
                            value);

                    assert ratingString != null : "illegal bind: rating string is null"; // NOI18N

                    txtWBEnvRatingRight.setText(ratingString);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class MaxRatingConverter extends Converter<Double, String> {

        //~ Instance fields ----------------------------------------------------

        private final transient String criteriaCountProperty;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new MaxRatingConverter object.
         *
         * @param  criteriaCountProperty  DOCUMENT ME!
         */
        public MaxRatingConverter(final String criteriaCountProperty) {
            this.criteriaCountProperty = criteriaCountProperty;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public String convertForward(final Double value) {
            final Object critCountO = cidsBean.getProperty(criteriaCountProperty);

            return getRatingString(value, critCountO);
        }

        @Override
        public Double convertReverse(final String value) {
            if (value == null) {
                return null;
            } else {
                final String[] split = value.split(" / "); // NOI18N

                return Double.parseDouble(split[0]);
            }
        }
    }
}
