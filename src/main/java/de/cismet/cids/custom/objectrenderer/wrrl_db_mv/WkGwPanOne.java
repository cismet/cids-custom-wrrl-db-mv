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
 * WkGwPanOne.java
 *
 * Created on 18.10.2010, 16:16:43
 */
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanOne;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.PressureImpactsProposals;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.DefaultBindableScrollableComboboxCellEditor;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.TimestampConverter;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.BrowserLauncher;

import static javax.swing.SwingConstants.TOP;

/**
 * DOCUMENT ME!
 *
 * @author   jweintraut
 * @version  $Revision$, $Date$
 */
public class WkGwPanOne extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(WkGwPanOne.class);
    private static final MetaClass IMPACT_CODE_MC;
    private static final MetaClass DRIVER_MC;
    private static final MetaClass SUBSTANCE_CODE_MC;
    private static Map<Integer, Set<Integer>> pressureImpactMap = new HashMap<>();
    private static Map<WkFgPanOne.PressureImpact, Set<Integer>> pressureImpactDriverMap = new HashMap<>();
    private static final ConnectionContext CC = ConnectionContext.create(
            AbstractConnectionContext.Category.EDITOR,
            "Wk_SG");

    static {
        IMPACT_CODE_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.impact_code");
        DRIVER_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.driver_code");
        SUBSTANCE_CODE_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.codelist_substance_code");

        new Thread("Init proposals") {

                @Override
                public void run() {
                    initProposals();
                }
            }.start();
    }

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = true;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labWebside;
    private javax.swing.JLabel lblGbPredecKey;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblInsByKey;
    private javax.swing.JLabel lblInsByValue;
    private javax.swing.JLabel lblInsWhenKey;
    private javax.swing.JLabel lblInsWhenValue;
    private javax.swing.JLabel lblNameKey;
    private javax.swing.JLabel lblNameValue;
    private javax.swing.JLabel lblStatDateKey1;
    private javax.swing.JLabel lblStatDateValue;
    private javax.swing.JLabel lblWebside;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panPressure;
    private javax.swing.JPanel panSpacingBottom1;
    private org.jdesktop.swingx.JXTable tabPressure;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkGwPanOne.
     */
    public WkGwPanOne() {
        initComponents();
        RendererTools.makeReadOnly(jTextArea1);
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
        jPanel1 = new javax.swing.JPanel();
        lblNameKey = new javax.swing.JLabel();
        lblNameValue = new javax.swing.JLabel();
        lblGbPredecKey = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        labWebside = new javax.swing.JLabel();
        lblWebside = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        lblInsWhenKey = new javax.swing.JLabel();
        lblInsWhenValue = new javax.swing.JLabel();
        lblInsByKey = new javax.swing.JLabel();
        lblInsByValue = new javax.swing.JLabel();
        lblStatDateKey1 = new javax.swing.JLabel();
        lblStatDateValue = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panSpacingBottom1 = new javax.swing.JPanel();
        panPressure = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabPressure = new org.jdesktop.swingx.JXTable();

        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(WkGwPanOne.class, "WkGwPanOne.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblNameKey.setText(org.openide.util.NbBundle.getMessage(WkGwPanOne.class, "WkGwPanOne.lblNameKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblNameKey, gridBagConstraints);

        lblNameValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblNameValue.setPreferredSize(new java.awt.Dimension(250, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                lblNameValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblNameValue, gridBagConstraints);

        lblGbPredecKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanOne.class,
                "WkGwPanOne.lblGbPredecKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblGbPredecKey, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(250, 150));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gbn_predecs}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        labWebside.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labWebside.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    labWebsideMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        jPanel1.add(labWebside, gridBagConstraints);

        lblWebside.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanOne.class,
                "WkGwPanOne.lblWebside.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(lblWebside, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jPanel1, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 15, 5);
        panInfoContent.add(jSeparator1, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblInsWhenKey.setText(org.openide.util.NbBundle.getMessage(WkGwPanOne.class, "WkGwPanOne.lblInsWhenKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblInsWhenKey, gridBagConstraints);

        lblInsWhenValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblInsWhenValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ins_when}"),
                lblInsWhenValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        binding.setConverter(TimestampConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblInsWhenValue, gridBagConstraints);

        lblInsByKey.setText(org.openide.util.NbBundle.getMessage(WkGwPanOne.class, "WkGwPanOne.lblInsByKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblInsByKey, gridBagConstraints);

        lblInsByValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblInsByValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ins_by}"),
                lblInsByValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblInsByValue, gridBagConstraints);

        lblStatDateKey1.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanOne.class,
                "WkGwPanOne.lblStatDateKey1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblStatDateKey1, gridBagConstraints);

        lblStatDateValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblStatDateValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stat_date}"),
                lblStatDateValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        binding.setConverter(TimestampConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblStatDateValue, gridBagConstraints);

        jPanel3.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jPanel2, gridBagConstraints);

        panSpacingBottom1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(panSpacingBottom1, gridBagConstraints);

        panPressure.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    WkGwPanOne.class,
                    "WkGwPanOne.panPressure.border.title",
                    new Object[] {}))); // NOI18N
        panPressure.setMinimumSize(new java.awt.Dimension(110, 300));
        panPressure.setOpaque(false);
        panPressure.setPreferredSize(new java.awt.Dimension(110, 300));
        panPressure.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setViewportView(tabPressure);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panPressure.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(panPressure, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void labWebsideMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_labWebsideMouseClicked
        try {
            BrowserLauncher.openURL(
                "https://fis-wasser-mv.de/charts/steckbriefe/gw/gw_wk.php?gw="
                        + String.valueOf(cidsBean.getProperty("name")));
        } catch (Exception ex) {
            LOG.warn(ex, ex);
        }
    }                                                                          //GEN-LAST:event_labWebsideMouseClicked

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
            labWebside.setText("<html><a href=\"https://fis-wasser-mv.de/charts/steckbriefe/gw/gw_wk.php?gw="
                        + String.valueOf(cidsBean.getProperty("name")) + "\">Webseite</a></html>");
        } else {
            labWebside.setText("");
        }

        setModel();
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    /**
     * DOCUMENT ME!
     */
    private static void initProposals() {
        try {
            final PressureImpactsProposals search = new PressureImpactsProposals();
            search.initWithConnectionContext(CC);
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search, CC);

            final ArrayList<ArrayList<Integer>> list = (ArrayList<ArrayList<Integer>>)res;

            if (list != null) {
                for (final ArrayList<Integer> row : list) {
                    final Integer pressure = row.get(0);
                    final Integer impact = row.get(1);
                    final Integer driver = row.get(2);
                    if ((pressure == null) || (impact == null)) {
                        continue;
                    }
                    final WkFgPanOne.PressureImpact pressureImpact = new WkFgPanOne.PressureImpact(pressure, impact);
                    Set<Integer> impactDriverList = pressureImpactDriverMap.get(pressureImpact);
                    Set<Integer> impactList = pressureImpactMap.get(pressure);

                    if (impactList == null) {
                        impactList = new HashSet<>();
                        pressureImpactMap.put(pressure, impactList);
                    }

                    if (impactDriverList == null) {
                        impactDriverList = new HashSet<>();
                        pressureImpactDriverMap.put(pressureImpact, impactList);
                    }

                    impactList.add(impact);
                    impactDriverList.add(driver);
                }
            }
        } catch (ConnectionException e) {
            LOG.error("Cannot retrieve Pressure Impacts proposal", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setModel() {
        if (cidsBean != null) {
            final List<CidsBean> pressures = CidsBeanSupport.getBeanCollectionFromProperty(
                    cidsBean,
                    "pressure_impact_driver");
            tabPressure.setModel(new WkFgPanOne.PressureTableModel(readOnly, pressures));
        } else {
            tabPressure.setModel(new WkFgPanOne.PressureTableModel(readOnly));
        }

        final DefaultBindableScrollableComboboxCellEditor cbImpact = new DefaultBindableScrollableComboboxCellEditor(
                IMPACT_CODE_MC,
                true);
        final DefaultBindableScrollableComboboxCellEditor cbdriver = new DefaultBindableScrollableComboboxCellEditor(
                DRIVER_MC,
                true);
        final DefaultBindableScrollableComboboxCellEditor cbSubstance = new DefaultBindableScrollableComboboxCellEditor(
                SUBSTANCE_CODE_MC,
                true,
                new Comparator<CidsBean>() {

                    @Override
                    public int compare(final CidsBean o1, final CidsBean o2) {
                        final Integer value1 = (Integer)o1.getProperty("value");
                        final Integer value2 = (Integer)o2.getProperty("value");
                        final String name1 = (String)o1.getProperty("name");
                        final String name2 = (String)o2.getProperty("name");

                        if ((value1 == null) && (value2 != null)) {
                            return 1;
                        } else if ((value1 != null) && (value2 == null)) {
                            return -1;
                        } else {
                            return name1.compareTo(name2);
                        }
                    }
                });

        cbImpact.getComboBox().setRenderer(new DefaultListCellRenderer() {

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

                    if (value instanceof DefaultBindableReferenceCombo.NullableItem) {
                        ((JLabel)result).setText(" ");
                    }

                    if ((result instanceof JLabel) && (value instanceof CidsBean)) {
                        final CidsBean bean = (CidsBean)value;

                        if (!isImpactProposed(bean)) {
                            ((JLabel)result).setForeground(Color.GRAY);
                        }
                    }

                    return result;
                }
            });

        cbdriver.getComboBox().setRenderer(new DefaultListCellRenderer() {

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

                    if (value instanceof DefaultBindableReferenceCombo.NullableItem) {
                        ((JLabel)result).setText(" ");
                    }

                    if ((result instanceof JLabel) && (value instanceof CidsBean)) {
                        final CidsBean bean = (CidsBean)value;

                        if (!isDriverProposed(bean)) {
                            ((JLabel)result).setForeground(Color.GRAY);
                        }
                    }

                    return result;
                }
            });

        tabPressure.getColumn(2).setCellEditor(cbImpact);
        tabPressure.getColumn(3).setCellEditor(cbdriver);
        tabPressure.getColumn(4).setCellEditor(cbSubstance);
        tabPressure.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    setVerticalAlignment(TOP);
                    final Component c = super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column);
                    if (c instanceof JLabel) {
                        ((JLabel)c).setText("<html>" + ((JLabel)c).getText() + "</html>");
                        ((JLabel)c).setToolTipText(String.valueOf(value));
                    }
                    return c;
                }
            });
        tabPressure.setDefaultRenderer(CidsBean.class, new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    setVerticalAlignment(TOP);
                    final Component c = super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column);
                    if (c instanceof JLabel) {
                        ((JLabel)c).setText("<html>" + ((JLabel)c).getText() + "</html>");
                        ((JLabel)c).setToolTipText(String.valueOf(value));
                    }
                    return c;
                }
            });

        tabPressure.setRowHeight(55);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   impact  pressure DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isImpactProposed(final CidsBean impact) {
        final int selectedRow = tabPressure.getSelectedRow();
        final CidsBean bean = ((WkFgPanOne.PressureTableModel)tabPressure.getModel()).getCidsBean(selectedRow);

        if (bean != null) {
            final Integer pressureId = (Integer)bean.getProperty("pressure.id");
            final Integer impactId = (Integer)impact.getProperty("id");

            if ((pressureId == null) || pressureImpactMap.isEmpty() || (impactId == null)) {
                return true;
            }

            final Set<Integer> proposedImpacts = pressureImpactMap.get(pressureId);

            if (proposedImpacts != null) {
                return proposedImpacts.contains(impactId);
            }
        } else {
            return true;
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   driver  pressure DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isDriverProposed(final CidsBean driver) {
        final int selectedRow = tabPressure.getSelectedRow();
        final CidsBean bean = ((WkFgPanOne.PressureTableModel)tabPressure.getModel()).getCidsBean(selectedRow);

        if (bean != null) {
            final Integer pressureId = (Integer)bean.getProperty("pressure.id");
            final Integer impactId = (Integer)bean.getProperty("impact.id");
            final Integer driverId = (Integer)driver.getProperty("id");

            if ((pressureId == null) || pressureImpactDriverMap.isEmpty() || (impactId == null) || (driverId == null)) {
                return true;
            }

            final WkFgPanOne.PressureImpact pressureImpact = new WkFgPanOne.PressureImpact(pressureId, impactId);

            final Set<Integer> proposedDriver = pressureImpactDriverMap.get(pressureImpact);

            if (proposedDriver != null) {
                return proposedDriver.contains(driverId);
            }
        } else {
            return true;
        }
        return false;
    }
}
