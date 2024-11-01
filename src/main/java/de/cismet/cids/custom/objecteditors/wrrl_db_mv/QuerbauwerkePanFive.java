/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 jruiz
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
 * QuerbauwerkePanOne.java
 *
 * Created on 26.10.2010, 13:57:21
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;

import javax.swing.JLabel;

import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.YesNoConverter;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkePanFive extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuerbauwerkePanOne.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    PropertyChangeListener bauwerkeListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent pce) {
                if (pce.getPropertyName().equals("bauwerk")) {
                    final String value = (String)cidsBean.getProperty("bauwerk.value");
                    if (value != null) {
                        defaultBindableReferenceCombo13.setVisible(value.equals("1") || value.equals("3"));
                        defaultBindableReferenceCombo14.setVisible(value.equals("2"));
                        jLabel3.setVisible(
                            defaultBindableReferenceCombo13.isVisible()
                                    || defaultBindableReferenceCombo14.isVisible());
                    }
                }
            }
        };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo13;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo14;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblHeading;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form QuerbauwerkePanOne.
     */
    public QuerbauwerkePanFive() {
        this(false);
    }

    /**
     * Creates new form QuerbauwerkePanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public QuerbauwerkePanFive(final boolean readOnly) {
        initComponents();

        if (!readOnly) {
            try {
                new CidsBeanDropTarget(jLabel4);
            } catch (final Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
                }
            }
        } else {
            RendererTools.makeReadOnly(defaultBindableReferenceCombo13);
            RendererTools.makeReadOnly(defaultBindableReferenceCombo14);
            RendererTools.makeReadOnly(jCheckBox1);
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
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        defaultBindableReferenceCombo13 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        defaultBindableReferenceCombo14 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new MassnIdLabel();
        jLabel5 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanFive.class,
                "QuerbauwerkePanFive.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jPanel3.setOpaque(false);

        final javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                738,
                Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                24,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(jPanel3, gridBagConstraints);

        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanFive.class,
                "QuerbauwerkePanFive.jLabel3.text")); // NOI18N
        jLabel3.setMinimumSize(new java.awt.Dimension(125, 25));
        jLabel3.setPreferredSize(new java.awt.Dimension(125, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel3, gridBagConstraints);

        defaultBindableReferenceCombo13.setMinimumSize(new java.awt.Dimension(250, 25));
        defaultBindableReferenceCombo13.setPreferredSize(new java.awt.Dimension(250, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn1}"),
                defaultBindableReferenceCombo13,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(defaultBindableReferenceCombo13, gridBagConstraints);

        defaultBindableReferenceCombo14.setMinimumSize(new java.awt.Dimension(250, 25));
        defaultBindableReferenceCombo14.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn2}"),
                defaultBindableReferenceCombo14,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(defaultBindableReferenceCombo14, gridBagConstraints);

        jLabel8.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanFive.class,
                "QuerbauwerkePanFive.jLabel8.text")); // NOI18N
        jLabel8.setMinimumSize(new java.awt.Dimension(125, 25));
        jLabel8.setPreferredSize(new java.awt.Dimension(125, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel8, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel8.add(jPanel1, gridBagConstraints);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanFive.class,
                "QuerbauwerkePanFive.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel1, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_ref.massn_fin}"),
                jLabel2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("-");
        binding.setConverter(new YesNoConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel2, gridBagConstraints);

        jLabel4.setToolTipText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanFive.class,
                "QuerbauwerkePanFive.jLabel4.toolTipText")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_ref.massn_id}"),
                jLabel4,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("keine Maßnahme zugewiesen");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel4, gridBagConstraints);

        jLabel5.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanFive.class,
                "QuerbauwerkePanFive.jLabel5.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel5, gridBagConstraints);

        jCheckBox1.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanFive.class,
                "QuerbauwerkePanFive.jCheckBox1.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zurueckgebaut}"),
                jCheckBox1,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jCheckBox1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jPanel8, gridBagConstraints);

        jPanel9.setOpaque(false);

        final javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                738,
                Short.MAX_VALUE));
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                24,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(jPanel9, gridBagConstraints);

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
            try {
                cidsBean.addPropertyChangeListener(bauwerkeListener);
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error while autosetting properties", ex);
                }
            }
            bindingGroup.bind();
        }
    }

    @Override
    public void dispose() {
        if (cidsBean != null) {
            cidsBean.removePropertyChangeListener(bauwerkeListener);
        }
        bindingGroup.unbind();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class MassnIdLabel extends JLabel implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            CidsBean toAdd = null;
            for (final CidsBean bean : beans) {
                if (bean.getMetaObject().getMetaClass().getName().equals("MASSNAHMEN")) {
                    toAdd = bean;
                    break;
                }
            }

            if (toAdd != null) {
                try {
                    cidsBean.setProperty("massn_ref", toAdd);
                } catch (Exception ex) {
                    LOG.fatal("error while setting massn_ref", ex);
                }
            }
        }
    }
}
