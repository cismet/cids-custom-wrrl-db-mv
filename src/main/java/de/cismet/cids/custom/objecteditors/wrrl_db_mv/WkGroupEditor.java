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
 * WkGroupEditor.java
 *
 * Created on 21.10.2010, 15:45:02
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultBindableColorChooser;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class WkGroupEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkGroupEditor.class);
    private static final MetaClass MC_WK_FG = ClassCacheMultiple.getMetaClass(
            CidsBeanSupport.DOMAIN_NAME,
            "wk_fg");
    private static final MetaClass MC_WK_GROUP_AGGR = ClassCacheMultiple.getMetaClass(
            CidsBeanSupport.DOMAIN_NAME,
            "wk_group_aggr");

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private final CidsBeanDropTarget dropTarget = new CidsBeanDropTarget(this);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRemWkFg;
    private de.cismet.cids.editors.DefaultBindableColorChooser defaultBindableColorChooser1;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JList lstWkFgs;
    private javax.swing.JPanel panWkGroupFgs;
    private javax.swing.JScrollPane scpWkFgs;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkGroupEditor.
     */
    public WkGroupEditor() {
        initComponents();

        jComboBox1.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent e) {
                    if (cidsBean != null) {
                        try {
                            cidsBean.setProperty("wk_group_aggr", (CidsBean)jComboBox1.getSelectedItem());
                        } catch (Exception ex) {
                            LOG.error("error while setting wk_group_aggr", ex);
                        }
                    }
                }
            });
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        updateWkGroupAggrCb();
        retrieveWkFgBeansFor(cidsBean);

        if (cidsBean != null) {
            bindingGroup.bind();
        }
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

        jDialog1 = new javax.swing.JDialog();
        jColorChooser1 = new javax.swing.JColorChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        defaultBindableColorChooser1 = new de.cismet.cids.editors.DefaultBindableColorChooser();
        scpWkFgs = new javax.swing.JScrollPane();
        lstWkFgs = new javax.swing.JList();
        panWkGroupFgs = new javax.swing.JPanel();
        btnRemWkFg = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();

        jDialog1.getContentPane().setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jDialog1.getContentPane().add(jColorChooser1, gridBagConstraints);

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(org.openide.util.NbBundle.getMessage(WkGroupEditor.class, "WkGroupEditor.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel1, gridBagConstraints);

        jTextField1.setMinimumSize(new java.awt.Dimension(400, 20));
        jTextField1.setPreferredSize(new java.awt.Dimension(400, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.value}"),
                jTextField1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jTextField1, gridBagConstraints);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(WkGroupEditor.class, "WkGroupEditor.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel2, gridBagConstraints);

        defaultBindableColorChooser1.setMinimumSize(new java.awt.Dimension(250, 20));
        defaultBindableColorChooser1.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.color}"),
                defaultBindableColorChooser1,
                org.jdesktop.beansbinding.BeanProperty.create("color"));
        binding.setConverter(((DefaultBindableColorChooser)defaultBindableColorChooser1).getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanel2.add(defaultBindableColorChooser1, gridBagConstraints);

        scpWkFgs.setMinimumSize(new java.awt.Dimension(400, 200));
        scpWkFgs.setPreferredSize(new java.awt.Dimension(400, 200));

        lstWkFgs.setModel(new DefaultListModel());
        lstWkFgs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpWkFgs.setViewportView(lstWkFgs);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(scpWkFgs, gridBagConstraints);

        panWkGroupFgs.setOpaque(false);
        panWkGroupFgs.setLayout(new java.awt.GridBagLayout());

        btnRemWkFg.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemWkFg.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemWkFgActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panWkGroupFgs.add(btnRemWkFg, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel2.add(panWkGroupFgs, gridBagConstraints);

        jLabel5.setText(org.openide.util.NbBundle.getMessage(WkGroupEditor.class, "WkGroupEditor.jLabel5.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel3.setText(org.openide.util.NbBundle.getMessage(WkGroupEditor.class, "WkGroupEditor.jLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        jComboBox1.setModel(new DefaultComboBoxModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jComboBox1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jPanel2, gridBagConstraints);

        jPanel1.setOpaque(false);

        final javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemWkFgActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemWkFgActionPerformed
        final Object selection = lstWkFgs.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Soll der Eintrag wirklich gelöscht werden?",
                    "Eintrag entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                getWkFgModel().removeElement(selection);
            }
        }
    }                                                                              //GEN-LAST:event_btnRemWkFgActionPerformed

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "WK-Gruppe" + String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        if (event.getStatus().equals(EditorSaveStatus.SAVE_SUCCESS)) {
            for (final Object object : getWkFgModel().toArray()) {
                final CidsBean wkFgBean = (CidsBean)object;
                try {
                    wkFgBean.setProperty("wk_group", cidsBean);
                    wkFgBean.persist();
                } catch (Exception ex) {
                    LOG.error("error while setting wk_group to wk_fg", ex);
                }
            }
        }
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        for (final CidsBean bean : beans) {
            if (bean.getMetaObject().getMetaClass().getTableName().equals("WK_FG")) {
                getWkFgModel().addElement(bean);
                cidsBean.setArtificialChangeFlag(true);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private DefaultListModel getWkFgModel() {
        return (DefaultListModel)lstWkFgs.getModel();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private DefaultComboBoxModel getWkGroupAggrModel() {
        return (DefaultComboBoxModel)jComboBox1.getModel();
    }

    /**
     * DOCUMENT ME!
     */
    private void updateWkGroupAggrCb() {
        getWkGroupAggrModel().removeAllElements();
        final String wkFgQuery = "SELECT "
                    + "   " + MC_WK_GROUP_AGGR.getID() + ", "
                    + "   " + MC_WK_GROUP_AGGR.getPrimaryKey() + " "
                    + "FROM "
                    + "   " + MC_WK_GROUP_AGGR.getTableName() + " "
                    + ";";
        try {
            final MetaObject[] wkGroupAggrMos = SessionManager.getProxy().getMetaObjectByQuery(wkFgQuery, 0);

            getWkGroupAggrModel().addElement(null);
            for (final MetaObject wkGroupAggrMo : wkGroupAggrMos) {
                getWkGroupAggrModel().addElement(wkGroupAggrMo.getBean());
            }
        } catch (Exception ex) {
            LOG.error("error while loading wkGroupAggrs", ex);
        }
        if (cidsBean != null) {
            getWkGroupAggrModel().setSelectedItem(cidsBean.getProperty("wk_group_aggr"));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkGroupBean  DOCUMENT ME!
     */
    private void retrieveWkFgBeansFor(final CidsBean wkGroupBean) {
        getWkFgModel().clear();
        if (wkGroupBean != null) {
            final int wkGroupId = (Integer)wkGroupBean.getProperty("id");
            final String wkFgQuery = "SELECT "
                        + "   " + MC_WK_FG.getID() + ", "
                        + "   " + MC_WK_FG.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + MC_WK_FG.getTableName() + " "
                        + "WHERE "
                        + "   wk_group = " + wkGroupId + " "
                        + ";";
            try {
                final MetaObject[] wkFgMos = SessionManager.getProxy().getMetaObjectByQuery(wkFgQuery, 0);

                for (final MetaObject wkFgMo : wkFgMos) {
                    getWkFgModel().addElement(wkFgMo.getBean());
                }
            } catch (Exception ex) {
                LOG.error("error while loading wkFgs", ex);
            }
        }
    }
}
