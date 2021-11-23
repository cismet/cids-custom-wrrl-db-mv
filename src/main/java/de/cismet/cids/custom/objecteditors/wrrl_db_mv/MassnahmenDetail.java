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

import java.awt.Color;
import java.awt.Component;

import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultBindableReferenceCombo.WhereOption;
import de.cismet.cids.editors.DefaultCustomObjectEditor;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnahmenDetail extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuerbauwerkePanOne.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private List<CidsBean> impacts;
    private MassnahmenEditor parent;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassn_schl1;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassn_schl2;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassn_schl3;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassn_schl4;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassn_schl5;
    private javax.swing.JLabel lblMassn_Schl1;
    private javax.swing.JLabel lblMassn_Schl2;
    private javax.swing.JLabel lblMassn_Schl3;
    private javax.swing.JLabel lblMassn_Schl4;
    private javax.swing.JLabel lblMassn_Schl5;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenDetail object.
     */
    public MassnahmenDetail() {
        this(true);
    }

    /**
     * Creates new form QuerbauwerkePanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public MassnahmenDetail(final boolean readOnly) {
        initComponents();

        if (readOnly) {
            RendererTools.makeReadOnly(cbMassn_schl1);
            RendererTools.makeReadOnly(cbMassn_schl2);
            RendererTools.makeReadOnly(cbMassn_schl3);
            RendererTools.makeReadOnly(cbMassn_schl4);
            RendererTools.makeReadOnly(cbMassn_schl5);
        }

        final boolean isAdmin = true; // SessionManager.getSession().getUser().getName().equalsIgnoreCase("admin");

        cbMassn_schl2.setVisible(isAdmin);
        lblMassn_Schl2.setVisible(isAdmin);

        cbMassn_schl1.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(final JList<?> list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    final Component result = super.getListCellRendererComponent(
                            list,
                            value,
                            index,
                            isSelected,
                            cellHasFocus);

                    if ((result instanceof JLabel) && (value instanceof CidsBean)) {
                        final CidsBean bean = (CidsBean)value;

                        final String text = bean.getProperty("code") + " - " + bean.getProperty("bedeutung_1");
                        ((JLabel)result).setText(text);

                        if ((bean != null) && !isPressureContained(bean)) {
                            ((JLabel)result).setForeground(Color.GRAY);
                        }
                    }

                    return result;
                }
            });
        cbMassn_schl2.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(final JList<?> list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    final Component result = super.getListCellRendererComponent(
                            list,
                            value,
                            index,
                            isSelected,
                            cellHasFocus);

                    if ((result instanceof JLabel) && (value instanceof CidsBean)) {
                        final CidsBean bean = (CidsBean)value;

                        if (bean.getProperty("value") == null) {
                            final String text = bean.getProperty("cas_nr") + " - " + bean.getProperty("name");
                            ((JLabel)result).setText(text);
                        } else {
                            final String text = bean.getProperty("value") + " - " + bean.getProperty("name");
                            ((JLabel)result).setText(text);
                        }
                    }

                    return result;
                }
            });
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

        lblMassn_Schl5 = new javax.swing.JLabel();
        cbMassn_schl5 = new ScrollableComboBox();
        cbMassn_schl4 = new ScrollableComboBox();
        lblMassn_Schl4 = new javax.swing.JLabel();
        lblMassn_Schl3 = new javax.swing.JLabel();
        cbMassn_schl3 = new ScrollableComboBox();
        cbMassn_schl2 = new ScrollableComboBox(new WhereOption("value is not null and name is not null"));
        lblMassn_Schl2 = new javax.swing.JLabel();
        cbMassn_schl1 = new ScrollableComboBox();
        lblMassn_Schl1 = new javax.swing.JLabel();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        lblMassn_Schl5.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenDetail.class,
                "MassnahmenDetail.lblMassn_Schl5.text",
                new Object[] {}));                               // NOI18N
        lblMassn_Schl5.setToolTipText(org.openide.util.NbBundle.getMessage(
                MassnahmenDetail.class,
                "MassnahmenDetail.lblMassn_Schl5.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 5, 10);
        add(lblMassn_Schl5, gridBagConstraints);

        cbMassn_schl5.setMinimumSize(new java.awt.Dimension(200, 25));
        cbMassn_schl5.setPreferredSize(new java.awt.Dimension(200, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.relfd}"),
                cbMassn_schl5,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        add(cbMassn_schl5, gridBagConstraints);

        cbMassn_schl4.setMinimumSize(new java.awt.Dimension(200, 25));
        cbMassn_schl4.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.relnat2000}"),
                cbMassn_schl4,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        add(cbMassn_schl4, gridBagConstraints);

        lblMassn_Schl4.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenDetail.class,
                "MassnahmenDetail.lblMassn_Schl4.text",
                new Object[] {}));                               // NOI18N
        lblMassn_Schl4.setToolTipText(org.openide.util.NbBundle.getMessage(
                MassnahmenDetail.class,
                "MassnahmenDetail.lblMassn_Schl4.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 5, 10);
        add(lblMassn_Schl4, gridBagConstraints);

        lblMassn_Schl3.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenDetail.class,
                "MassnahmenDetail.lblMassn_Schl3.text",
                new Object[] {}));                               // NOI18N
        lblMassn_Schl3.setToolTipText(org.openide.util.NbBundle.getMessage(
                MassnahmenDetail.class,
                "MassnahmenDetail.lblMassn_Schl3.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 5, 10);
        add(lblMassn_Schl3, gridBagConstraints);

        cbMassn_schl3.setMinimumSize(new java.awt.Dimension(200, 25));
        cbMassn_schl3.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.relevance}"),
                cbMassn_schl3,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        add(cbMassn_schl3, gridBagConstraints);

        cbMassn_schl2.setMinimumSize(new java.awt.Dimension(200, 25));
        cbMassn_schl2.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.substance}"),
                cbMassn_schl2,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        add(cbMassn_schl2, gridBagConstraints);

        lblMassn_Schl2.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenDetail.class,
                "MassnahmenDetail.lblMassn_Schl2.text",
                new Object[] {}));                               // NOI18N
        lblMassn_Schl2.setToolTipText(org.openide.util.NbBundle.getMessage(
                MassnahmenDetail.class,
                "MassnahmenDetail.lblMassn_Schl2.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 5, 10);
        add(lblMassn_Schl2, gridBagConstraints);

        cbMassn_schl1.setMinimumSize(new java.awt.Dimension(200, 25));
        cbMassn_schl1.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.pressure}"),
                cbMassn_schl1,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbMassn_schl1.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbMassn_schl1ItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        add(cbMassn_schl1, gridBagConstraints);

        lblMassn_Schl1.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenDetail.class,
                "MassnahmenDetail.lblMassn_Schl1.text",
                new Object[] {}));                               // NOI18N
        lblMassn_Schl1.setToolTipText(org.openide.util.NbBundle.getMessage(
                MassnahmenDetail.class,
                "MassnahmenDetail.lblMassn_Schl1.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 5, 10);
        add(lblMassn_Schl1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbMassn_schl1ItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbMassn_schl1ItemStateChanged
        if (parent != null) {
            parent.repaintImpacts();
        }
    }                                                                                //GEN-LAST:event_cbMassn_schl1ItemStateChanged

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

    /**
     * DOCUMENT ME!
     *
     * @param  parent  DOCUMENT ME!
     */
    public void setParent(final MassnahmenEditor parent) {
        this.parent = parent;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  impacts  parentBean DOCUMENT ME!
     */
    public void setImpacts(final List<CidsBean> impacts) {
        this.impacts = impacts;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   pressure  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isPressureContained(final CidsBean pressure) {
        if (impacts == null) {
            return true;
        }

        for (final CidsBean pBean : impacts) {
            if ((pBean != null) && (pBean.getMetaObject().getId() == pressure.getMetaObject().getId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }
}
