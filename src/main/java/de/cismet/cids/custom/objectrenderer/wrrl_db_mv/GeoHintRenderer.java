/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 jweintraut
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
 * GeoHintRenderer.java
 *
 * Created on 01.03.2011, 09:03:53
 */
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import de.cismet.cids.custom.wrrl_db_mv.util.TimestampConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * A renderer for entities of class GEO_HINT.
 *
 * @author   jweintraut
 * @version  $Revision$, $Date$
 */
public class GeoHintRenderer extends javax.swing.JPanel implements CidsBeanRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final TimestampConverter TIMESTAMP_CONVERTER = new TimestampConverter();

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblCommentKey;
    private javax.swing.JLabel lblPriorityKey;
    private javax.swing.JLabel lblPriorityValue;
    private javax.swing.JLabel lblTimestampKey;
    private javax.swing.JLabel lblTimestampValue;
    private javax.swing.JLabel lblUsrKey;
    private javax.swing.JLabel lblUsrValue;
    private javax.swing.JScrollPane scpComment;
    private javax.swing.JTextArea taCommentValue;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GeoHintRenderer.
     */
    public GeoHintRenderer() {
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

        lblUsrKey = new javax.swing.JLabel();
        lblPriorityValue = new javax.swing.JLabel();
        lblUsrValue = new javax.swing.JLabel();
        lblPriorityKey = new javax.swing.JLabel();
        lblCommentKey = new javax.swing.JLabel();
        lblTimestampValue = new javax.swing.JLabel();
        lblTimestampKey = new javax.swing.JLabel();
        scpComment = new javax.swing.JScrollPane();
        taCommentValue = new javax.swing.JTextArea();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        lblUsrKey.setText(org.openide.util.NbBundle.getMessage(
                GeoHintRenderer.class,
                "GeoHintRenderer.lblUsrKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblUsrKey, gridBagConstraints);

        lblPriorityValue.setMinimumSize(new java.awt.Dimension(200, 20));
        lblPriorityValue.setPreferredSize(new java.awt.Dimension(200, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.priority.name}"),
                lblPriorityValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblPriorityValue, gridBagConstraints);

        lblUsrValue.setMinimumSize(new java.awt.Dimension(500, 20));
        lblUsrValue.setPreferredSize(new java.awt.Dimension(500, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.usr}"),
                lblUsrValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblUsrValue, gridBagConstraints);

        lblPriorityKey.setText(org.openide.util.NbBundle.getMessage(
                GeoHintRenderer.class,
                "GeoHintRenderer.lblPriorityKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblPriorityKey, gridBagConstraints);

        lblCommentKey.setText(org.openide.util.NbBundle.getMessage(
                GeoHintRenderer.class,
                "GeoHintRenderer.lblCommentKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblCommentKey, gridBagConstraints);

        lblTimestampValue.setMinimumSize(new java.awt.Dimension(200, 20));
        lblTimestampValue.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.timestamp}"),
                lblTimestampValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(TIMESTAMP_CONVERTER);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblTimestampValue, gridBagConstraints);

        lblTimestampKey.setText(org.openide.util.NbBundle.getMessage(
                GeoHintRenderer.class,
                "GeoHintRenderer.lblTimestampKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblTimestampKey, gridBagConstraints);

        scpComment.setMaximumSize(new java.awt.Dimension(200, 75));
        scpComment.setMinimumSize(new java.awt.Dimension(200, 75));
        scpComment.setPreferredSize(new java.awt.Dimension(500, 75));

        taCommentValue.setColumns(20);
        taCommentValue.setEditable(false);
        taCommentValue.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        taCommentValue.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.comment}"),
                taCommentValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        scpComment.setViewportView(taCommentValue);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(scpComment, gridBagConstraints);

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
            bindingGroup.bind();
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return org.openide.util.NbBundle.getMessage(GeoHintRenderer.class, "GeoHintRenderer.title")
                    + String.valueOf(cidsBean.getProperty("id"));
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }
}
