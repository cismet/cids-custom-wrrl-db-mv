/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 jweintraut
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * WkGwPanTwo.java
 *
 * Created on 18.10.2010, 16:20:18
 */
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

/**
 * DOCUMENT ME!
 *
 * @author   jweintraut
 * @version  $Revision$, $Date$
 */
public class WkGwPanTwo extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAquiTypeKey;
    private javax.swing.JLabel lblAquiTypeValue;
    private javax.swing.JLabel lblAvgDepthKey;
    private javax.swing.JLabel lblAvgDepthValue;
    private javax.swing.JLabel lblAvgThickKey;
    private javax.swing.JLabel lblAvgThickValue;
    private javax.swing.JLabel lblCapacityKey;
    private javax.swing.JLabel lblCapacityValue;
    private javax.swing.JLabel lblDepthrangeKey;
    private javax.swing.JLabel lblDepthrangeValue;
    private javax.swing.JLabel lblExt10Key;
    private javax.swing.JLabel lblExt10Value;
    private javax.swing.JLabel lblGeolFormKey;
    private javax.swing.JLabel lblGeolFormValue;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHorizonKey;
    private javax.swing.JLabel lblHorizonValue;
    private javax.swing.JLabel lblLanduse1Key;
    private javax.swing.JLabel lblLanduse1Value;
    private javax.swing.JLabel lblLanduse2Key;
    private javax.swing.JLabel lblLanduse2Value;
    private javax.swing.JLabel lblLanduse3Key;
    private javax.swing.JLabel lblLanduse3Value;
    private javax.swing.JLabel lblLanduse4Key;
    private javax.swing.JLabel lblLanduse4Value;
    private javax.swing.JLabel lblLanduse5Key;
    private javax.swing.JLabel lblLanduse5Value;
    private javax.swing.JLabel lblLanduse6Key;
    private javax.swing.JLabel lblLanduse6Value;
    private javax.swing.JLabel lblLanduse7Key;
    private javax.swing.JLabel lblLanduse7Value;
    private javax.swing.JLabel lblLinkEcoKey;
    private javax.swing.JLabel lblLinkEcoValue;
    private javax.swing.JLabel lblLinkSwbKey;
    private javax.swing.JLabel lblLinkSwbValue;
    private javax.swing.JLabel lblOutofrbdKey;
    private javax.swing.JLabel lblOutofrbdValue;
    private javax.swing.JLabel lblRegionCdaKey;
    private javax.swing.JLabel lblRegionCdaValue;
    private javax.swing.JLabel lblSurface1Key;
    private javax.swing.JLabel lblSurface1Value;
    private javax.swing.JLabel lblSurface2Key;
    private javax.swing.JLabel lblSurface2Value;
    private javax.swing.JLabel lblSurface3Key;
    private javax.swing.JLabel lblSurface3Value;
    private javax.swing.JLabel lblSuwaAssoKey;
    private javax.swing.JLabel lblSuwaAssoValue;
    private javax.swing.JLabel lblVertOrienKey;
    private javax.swing.JLabel lblVertOrienValue;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panLandUse;
    private javax.swing.JPanel panSurface;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkGwPanTwo.
     */
    public WkGwPanTwo() {
        initComponents();
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
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        lblLinkEcoKey = new javax.swing.JLabel();
        lblSuwaAssoKey = new javax.swing.JLabel();
        lblOutofrbdKey = new javax.swing.JLabel();
        panLandUse = new javax.swing.JPanel();
        lblLanduse1Key = new javax.swing.JLabel();
        lblLanduse2Key = new javax.swing.JLabel();
        lblLanduse3Key = new javax.swing.JLabel();
        lblLanduse4Key = new javax.swing.JLabel();
        lblLanduse5Key = new javax.swing.JLabel();
        lblLanduse6Key = new javax.swing.JLabel();
        lblLanduse7Key = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblLanduse1Value = new javax.swing.JLabel();
        lblLanduse2Value = new javax.swing.JLabel();
        lblLanduse3Value = new javax.swing.JLabel();
        lblLanduse4Value = new javax.swing.JLabel();
        lblLanduse5Value = new javax.swing.JLabel();
        lblLanduse6Value = new javax.swing.JLabel();
        lblLanduse7Value = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblExt10Key = new javax.swing.JLabel();
        lblRegionCdaKey = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblLinkSwbKey = new javax.swing.JLabel();
        lblLinkSwbValue = new javax.swing.JLabel();
        lblLinkEcoValue = new javax.swing.JLabel();
        lblSuwaAssoValue = new javax.swing.JLabel();
        lblOutofrbdValue = new javax.swing.JLabel();
        lblExt10Value = new javax.swing.JLabel();
        lblRegionCdaValue = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblAquiTypeKey = new javax.swing.JLabel();
        lblHorizonKey = new javax.swing.JLabel();
        lblGeolFormKey = new javax.swing.JLabel();
        lblAvgDepthKey = new javax.swing.JLabel();
        lblAvgThickKey = new javax.swing.JLabel();
        lblDepthrangeKey = new javax.swing.JLabel();
        lblCapacityKey = new javax.swing.JLabel();
        lblVertOrienKey = new javax.swing.JLabel();
        panSurface = new javax.swing.JPanel();
        lblSurface1Key = new javax.swing.JLabel();
        lblSurface2Key = new javax.swing.JLabel();
        lblSurface3Key = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblSurface1Value = new javax.swing.JLabel();
        lblSurface2Value = new javax.swing.JLabel();
        lblSurface3Value = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblAquiTypeValue = new javax.swing.JLabel();
        lblHorizonValue = new javax.swing.JLabel();
        lblGeolFormValue = new javax.swing.JLabel();
        lblAvgDepthValue = new javax.swing.JLabel();
        lblAvgThickValue = new javax.swing.JLabel();
        lblDepthrangeValue = new javax.swing.JLabel();
        lblCapacityValue = new javax.swing.JLabel();
        lblVertOrienValue = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 25, 5);
        panInfoContent.add(jSeparator1, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblLinkEcoKey.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.lblLinkEcoKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblLinkEcoKey, gridBagConstraints);

        lblSuwaAssoKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblSuwaAssoKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblSuwaAssoKey, gridBagConstraints);

        lblOutofrbdKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblOutofrbdKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblOutofrbdKey, gridBagConstraints);

        panLandUse.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.panLandUse.border.title"))); // NOI18N
        panLandUse.setOpaque(false);
        panLandUse.setLayout(new java.awt.GridBagLayout());

        lblLanduse1Key.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblLanduse1Key.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse1Key, gridBagConstraints);

        lblLanduse2Key.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblLanduse2Key.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse2Key, gridBagConstraints);

        lblLanduse3Key.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblLanduse3Key.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse3Key, gridBagConstraints);

        lblLanduse4Key.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblLanduse4Key.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse4Key, gridBagConstraints);

        lblLanduse5Key.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblLanduse5Key.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse5Key, gridBagConstraints);

        lblLanduse6Key.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblLanduse6Key.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse6Key, gridBagConstraints);

        lblLanduse7Key.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblLanduse7Key.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse7Key, gridBagConstraints);

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        panLandUse.add(jPanel2, gridBagConstraints);

        lblLanduse1Value.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblLanduse1Value.setMinimumSize(new java.awt.Dimension(250, 20));
        lblLanduse1Value.setPreferredSize(new java.awt.Dimension(250, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.landuse_1}"),
                lblLanduse1Value,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse1Value, gridBagConstraints);

        lblLanduse2Value.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblLanduse2Value.setMinimumSize(new java.awt.Dimension(250, 20));
        lblLanduse2Value.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.landuse_2}"),
                lblLanduse2Value,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse2Value, gridBagConstraints);

        lblLanduse3Value.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblLanduse3Value.setMinimumSize(new java.awt.Dimension(250, 20));
        lblLanduse3Value.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.landuse_3}"),
                lblLanduse3Value,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse3Value, gridBagConstraints);

        lblLanduse4Value.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblLanduse4Value.setMinimumSize(new java.awt.Dimension(250, 20));
        lblLanduse4Value.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.landuse_4}"),
                lblLanduse4Value,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse4Value, gridBagConstraints);

        lblLanduse5Value.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblLanduse5Value.setMinimumSize(new java.awt.Dimension(250, 20));
        lblLanduse5Value.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.landuse_5}"),
                lblLanduse5Value,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse5Value, gridBagConstraints);

        lblLanduse6Value.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblLanduse6Value.setMinimumSize(new java.awt.Dimension(250, 20));
        lblLanduse6Value.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.landuse_6}"),
                lblLanduse6Value,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse6Value, gridBagConstraints);

        lblLanduse7Value.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblLanduse7Value.setMinimumSize(new java.awt.Dimension(250, 20));
        lblLanduse7Value.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.landuse_7}"),
                lblLanduse7Value,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panLandUse.add(lblLanduse7Value, gridBagConstraints);

        jLabel14.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel14.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panLandUse.add(jLabel14, gridBagConstraints);

        jLabel15.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel15.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panLandUse.add(jLabel15, gridBagConstraints);

        jLabel16.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel16.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panLandUse.add(jLabel16, gridBagConstraints);

        jLabel17.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel17.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panLandUse.add(jLabel17, gridBagConstraints);

        jLabel18.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel18.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panLandUse.add(jLabel18, gridBagConstraints);

        jLabel19.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel19.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panLandUse.add(jLabel19, gridBagConstraints);

        jLabel20.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel20.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panLandUse.add(jLabel20, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(panLandUse, gridBagConstraints);

        lblExt10Key.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.lblExt10Key.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblExt10Key, gridBagConstraints);

        lblRegionCdaKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblRegionCdaKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblRegionCdaKey, gridBagConstraints);

        jPanel6.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel6, gridBagConstraints);

        lblLinkSwbKey.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.lblLinkSwbKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblLinkSwbKey, gridBagConstraints);

        lblLinkSwbValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblLinkSwbValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.link_swb.name}"),
                lblLinkSwbValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblLinkSwbValue, gridBagConstraints);

        lblLinkEcoValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblLinkEcoValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.link_eco.name}"),
                lblLinkEcoValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblLinkEcoValue, gridBagConstraints);

        lblSuwaAssoValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblSuwaAssoValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suwa_asso.name}"),
                lblSuwaAssoValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblSuwaAssoValue, gridBagConstraints);

        lblOutofrbdValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblOutofrbdValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.outofrbd.name}"),
                lblOutofrbdValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblOutofrbdValue, gridBagConstraints);

        lblExt10Value.setMinimumSize(new java.awt.Dimension(250, 20));
        lblExt10Value.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ext10.name}"),
                lblExt10Value,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblExt10Value, gridBagConstraints);

        lblRegionCdaValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblRegionCdaValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.region_cda.name}"),
                lblRegionCdaValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblRegionCdaValue, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jPanel1, gridBagConstraints);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        lblAquiTypeKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblAquiTypeKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblAquiTypeKey, gridBagConstraints);

        lblHorizonKey.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.lblHorizonKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblHorizonKey, gridBagConstraints);

        lblGeolFormKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblGeolFormKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblGeolFormKey, gridBagConstraints);

        lblAvgDepthKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblAvgDepthKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblAvgDepthKey, gridBagConstraints);

        lblAvgThickKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblAvgThickKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblAvgThickKey, gridBagConstraints);

        lblDepthrangeKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblDepthrangeKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblDepthrangeKey, gridBagConstraints);

        lblCapacityKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblCapacityKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblCapacityKey, gridBagConstraints);

        lblVertOrienKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblVertOrienKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblVertOrienKey, gridBagConstraints);

        panSurface.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.panSurface.border.title"))); // NOI18N
        panSurface.setOpaque(false);
        panSurface.setLayout(new java.awt.GridBagLayout());

        lblSurface1Key.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblSurface1Key.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSurface.add(lblSurface1Key, gridBagConstraints);

        lblSurface2Key.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblSurface2Key.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSurface.add(lblSurface2Key, gridBagConstraints);

        lblSurface3Key.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanTwo.class,
                "WkGwPanTwo.lblSurface3Key.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSurface.add(lblSurface3Key, gridBagConstraints);

        jPanel3.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        panSurface.add(jPanel3, gridBagConstraints);

        lblSurface1Value.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblSurface1Value.setMinimumSize(new java.awt.Dimension(250, 20));
        lblSurface1Value.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.surface_1}"),
                lblSurface1Value,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSurface.add(lblSurface1Value, gridBagConstraints);

        lblSurface2Value.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblSurface2Value.setMinimumSize(new java.awt.Dimension(250, 20));
        lblSurface2Value.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.surface_2}"),
                lblSurface2Value,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSurface.add(lblSurface2Value, gridBagConstraints);

        lblSurface3Value.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblSurface3Value.setMinimumSize(new java.awt.Dimension(250, 20));
        lblSurface3Value.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.surface_3}"),
                lblSurface3Value,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSurface.add(lblSurface3Value, gridBagConstraints);

        jLabel11.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel11.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panSurface.add(jLabel11, gridBagConstraints);

        jLabel12.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel12.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panSurface.add(jLabel12, gridBagConstraints);

        jLabel13.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel13.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panSurface.add(jLabel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(panSurface, gridBagConstraints);

        jPanel5.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jPanel5, gridBagConstraints);

        lblAquiTypeValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblAquiTypeValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.aqui_type.name}"),
                lblAquiTypeValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblAquiTypeValue, gridBagConstraints);

        lblHorizonValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblHorizonValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblHorizonValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.horizon}"),
                lblHorizonValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblHorizonValue, gridBagConstraints);

        lblGeolFormValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblGeolFormValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.geol_form.name}"),
                lblGeolFormValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblGeolFormValue, gridBagConstraints);

        lblAvgDepthValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblAvgDepthValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblAvgDepthValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.avg_depth}"),
                lblAvgDepthValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblAvgDepthValue, gridBagConstraints);

        lblAvgThickValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblAvgThickValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblAvgThickValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.avg_thick}"),
                lblAvgThickValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblAvgThickValue, gridBagConstraints);

        lblDepthrangeValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblDepthrangeValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.depthrange.name}"),
                lblDepthrangeValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblDepthrangeValue, gridBagConstraints);

        lblCapacityValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblCapacityValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblCapacityValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.capacity}"),
                lblCapacityValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblCapacityValue, gridBagConstraints);

        lblVertOrienValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblVertOrienValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.vert_orien.name}"),
                lblVertOrienValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblVertOrienValue, gridBagConstraints);

        jLabel8.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel8.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jLabel8, gridBagConstraints);

        jLabel9.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel9.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jLabel9, gridBagConstraints);

        jLabel10.setText(org.openide.util.NbBundle.getMessage(WkGwPanTwo.class, "WkGwPanTwo.jLabel10.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jLabel10, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jPanel4, gridBagConstraints);

        jPanel7.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(jPanel7, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

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
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }
}
