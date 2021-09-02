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
 * QuerbauwerkeEditor.java
 *
 * Created on 26.10.2010, 13:09:43
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.method.MethodManager;

import Sirius.server.localserver.attribute.ClassAttribute;
import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import org.openide.util.NbBundle;

import java.awt.EventQueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.MapUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.WrrlEditorTester;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.BeanInitializer;
import de.cismet.cids.editors.BeanInitializerProvider;
import de.cismet.cids.editors.DefaultBeanInitializer;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.CismetThreadPool;

import de.cismet.tools.gui.FXWebViewPanel;
import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkeEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    BeanInitializerProvider,
    PropertyChangeListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuerbauwerkeEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private final org.jdesktop.beansbinding.BindingGroup bindingGroup;
    private final PropertyChangeListener bauwerkPropertyChangeListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent pce) {
                if (pce.getPropertyName().equals("bauwerk")) {
                    int bauwerk_value = 0;
                    try {
                        bauwerk_value = Integer.parseInt((String)((CidsBean)cidsBean.getProperty("bauwerk"))
                                        .getProperty("value"));
                    } catch (Exception ex) {
                    }
                    querbauwerkePanFour.setWehrVisible(bauwerk_value == 1);
                    querbauwerkePanFour.setStarrVisible(bauwerk_value == 3);
                }
            }
        };

    private ConnectionContext cc = ConnectionContext.create(AbstractConnectionContext.Category.EDITOR, "Querbauwerke");

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butLoadObjects;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JPanel panAllgemeines;
    private javax.swing.JPanel panBeschreibung;
    private javax.swing.JPanel panFische;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panSearchTool;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanFive querbauwerkePanFive;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanFour querbauwerkePanFour;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanOne querbauwerkePanOne;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanSeven querbauwerkePanSeven1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanSix querbauwerkePanSix;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanThree querbauwerkePanThree;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanTwo querbauwerkePanTwo;
    private javax.swing.JTabbedPane tpMain;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form QuerbauwerkeEditor.
     */
    public QuerbauwerkeEditor() {
        this(false);
    }

    /**
     * Creates new form QuerbauwerkeEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public QuerbauwerkeEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        initComponents();
        Boolean hasPerm = false;

        try {
            hasPerm = SessionManager.getProxy()
                        .hasConfigAttr(SessionManager.getSession().getUser(),
                                "qb_all_infos",
                                ConnectionContext.create(
                                    AbstractConnectionContext.Category.EDITOR,
                                    "has qb permission"));
        } catch (Exception e) {
            LOG.error("Cannot check permission", e);
        }

        if (!hasPerm) {
            tpMain.setEnabledAt(2, false);
//            tpMain.removeTabAt(2);
//            jPanel4.setVisible(hasPerm);
        }

        try {
            final boolean showSearch = SessionManager.getProxy()
                        .hasConfigAttr(SessionManager.getSession().getUser(), "qbw.search", cc);

            if (!showSearch) {
                tpMain.remove(tpMain.getTabCount() - 1);
            }
        } catch (Exception e) {
            LOG.error("Cannot check configuration attribute qbw.search", e);
            tpMain.remove(tpMain.getTabCount() - 1);
        }

        if (!readOnly) {
            querbauwerkePanSix.getStat09Editor().addListener(new LinearReferencedPointEditorListener() {

                    @Override
                    public void pointCreated() {
                        try {
                            cidsBean.setProperty("stat09", querbauwerkePanSix.getStat09Editor().getCidsBean());
                        } catch (Exception ex) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("error while assigning stat09 cidsbean to cidsbean", ex);
                            }
                        }
                    }
                });
            querbauwerkePanSix.getStat09BisEditor().addListener(new LinearReferencedPointEditorListener() {

                    @Override
                    public void pointCreated() {
                        try {
                            cidsBean.setProperty("stat09_bis", querbauwerkePanSix.getStat09BisEditor().getCidsBean());
                        } catch (Exception ex) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("error while assigning stat09_bis cidsbean to cidsbean", ex);
                            }
                        }
                    }
                });
        }

        final FXWebViewPanel browserPanel = new FXWebViewPanel();
        browserPanel.setOpaque(false);
        jPanel1.add(browserPanel, java.awt.BorderLayout.CENTER);

        final SwingWorker<String, Void> sw = new SwingWorker<String, Void>() {

                @Override
                protected String doInBackground() throws Exception {
                    return SessionManager.getProxy()
                                .getConfigAttr(SessionManager.getSession().getUser(), "qbw.text", cc);
                }

                @Override
                protected void done() {
                    try {
                        final String r = get();

                        if (r != null) {
                            browserPanel.loadContent(r);
                        }
                    } catch (Exception ex) {
                        LOG.error("error while reatuing qbw.text configuration attribute", ex);
                    }
                }
            };
        sw.execute();

        tpMain.setUI(new TabbedPaneUITransparent());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        final Collection<Feature> zoomFeatures = new ArrayList<Feature>();
        querbauwerkePanSix.getStat09Editor().addZoomFeaturesToCollection(zoomFeatures);
        querbauwerkePanSix.getStat09BisEditor().addZoomFeaturesToCollection(zoomFeatures);
        MapUtil.zoomToFeatureCollection(zoomFeatures);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        tpMain = new javax.swing.JTabbedPane();
        panBeschreibung = new javax.swing.JPanel();
        querbauwerkePanTwo = new QuerbauwerkePanTwo(readOnly);
        jPanel3 = new javax.swing.JPanel();
        querbauwerkePanThree = new QuerbauwerkePanThree(readOnly);
        querbauwerkePanFive = new QuerbauwerkePanFive(readOnly);
        if (!readOnly) {
            querbauwerkePanSix = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanSix();
        }
        panAllgemeines = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        querbauwerkePanOne = new QuerbauwerkePanOne(readOnly);
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        querbauwerkePanFour = new QuerbauwerkePanFour(readOnly);
        panFische = new javax.swing.JPanel();
        querbauwerkePanSeven1 = new QuerbauwerkePanSeven(readOnly);
        panSearchTool = new javax.swing.JPanel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        butLoadObjects = new javax.swing.JButton();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        panBeschreibung.setOpaque(false);
        panBeschreibung.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panBeschreibung.add(querbauwerkePanTwo, gridBagConstraints);

        jPanel3.setOpaque(false);

        final javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panBeschreibung.add(jPanel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        panBeschreibung.add(querbauwerkePanThree, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        panBeschreibung.add(querbauwerkePanFive, gridBagConstraints);

        if (!readOnly) {
        }
        if (!readOnly) {
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
            panBeschreibung.add(querbauwerkePanSix, gridBagConstraints);
        }

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                QuerbauwerkeEditor.class,
                "QuerbauwerkeEditor.panBeschreibung.TabConstraints.tabTitle"),
            panBeschreibung); // NOI18N

        panAllgemeines.setOpaque(false);
        panAllgemeines.setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                1316,
                Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                464,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panAllgemeines.add(jPanel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panAllgemeines.add(querbauwerkePanOne, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                QuerbauwerkeEditor.class,
                "QuerbauwerkeEditor.panAllgemeines.TabConstraints.tabTitle"),
            panAllgemeines); // NOI18N

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel5.setOpaque(false);

        final javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                1316,
                Short.MAX_VALUE));
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                888,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jPanel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel4.add(querbauwerkePanFour, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                QuerbauwerkeEditor.class,
                "QuerbauwerkeEditor.jPanel4.TabConstraints.tabTitle"),
            jPanel4); // NOI18N

        panFische.setOpaque(false);
        panFische.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panFische.add(querbauwerkePanSeven1, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                QuerbauwerkeEditor.class,
                "QuerbauwerkeEditor.panFische.TabConstraints.tabTitle"),
            panFische); // NOI18N

        panSearchTool.setOpaque(false);
        panSearchTool.setLayout(new java.awt.GridBagLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkeEditor.class,
                "QuerbauwerkeEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        panInfoContent.add(jPanel1, gridBagConstraints);

        butLoadObjects.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkeEditor.class,
                "QuerbauwerkeEditor.butLoadObjects.text",
                new Object[] {})); // NOI18N
        butLoadObjects.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    butLoadObjectsActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panInfoContent.add(butLoadObjects, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panSearchTool.add(panInfo, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                QuerbauwerkeEditor.class,
                "QuerbauwerkeEditor.panSearchTool.TabConstraints.tabTitle",
                new Object[] {}),
            panSearchTool); // NOI18N

        add(tpMain, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void butLoadObjectsActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_butLoadObjectsActionPerformed
        loadRelatedQbw(cidsBean);
    }                                                                                  //GEN-LAST:event_butLoadObjectsActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        if (this.cidsBean != null) {
            this.cidsBean.removePropertyChangeListener(this);
        }
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            cidsBean.addPropertyChangeListener(this);
            try {
                cidsBean.addPropertyChangeListener(bauwerkPropertyChangeListener);
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error while autosetting properties", ex);
                }
            }

            if ((tpMain.getTabCount() == 5) && (cidsBean.getProperty("up_agg_id") == null)) {
                tpMain.remove(tpMain.getTabCount() - 1);
            }
//            else {
//                EventQueue.invokeLater(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            tpMain.setSelectedIndex(4);
//                            tpMain.setSelectedIndex(0);
//                            updateUI();
//                            repaint();
//                        }
//                    });
//            }

            refreshReadOnlyFields();

            querbauwerkePanOne.setCidsBean(cidsBean);
            querbauwerkePanTwo.setCidsBean(cidsBean);
            querbauwerkePanThree.setCidsBean(cidsBean);
            querbauwerkePanFour.setCidsBean(cidsBean);
            querbauwerkePanFive.setCidsBean(cidsBean);
            if (querbauwerkePanSix != null) {
                querbauwerkePanSix.setCidsBean(cidsBean);
            }
            querbauwerkePanSeven1.setCidsBean(cidsBean);

            bindingGroup.bind();
            UIUtil.setLastModifier(cidsBean, lblFoot);
            tpMain.setEnabledAt(3, QuerbauwerkeEditor.showFishPanel(cidsBean));

            if (!readOnly) {
                zoomToFeatures();
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshReadOnlyFields() {
        updateQbwId();
        // aus Performancegründen nicht in wertChanged
        CismetThreadPool.execute(new Thread(new Runnable() {

                    @Override
                    public void run() {
                        updateWaKoerper();
                    }
                }));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static boolean showFishPanel(final CidsBean cidsBean) {
        return cidsBean.getProperty("b_aal") != null;
    }

    /**
     * DOCUMENT ME!
     */
    private synchronized void updateWaKoerper() {
        final MetaClass mcWkFg = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");
        final MetaClass mcWkFgTeile = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg_teile");
        final MetaClass mcWkTeil = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_teil");
        final MetaClass mcLine = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                LinearReferencingConstants.CN_STATIONLINE);
        final MetaClass mcStation = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                LinearReferencingConstants.CN_STATION);
        final MetaClass mcRoute = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                LinearReferencingConstants.CN_ROUTE);

        final MetaClass mcWkSg = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_sg");
        final MetaClass mcQuerbauwerke = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "querbauwerke");
        final MetaClass mcGeom = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "geom");

        String queryWkFg = "";
        String queryWkSg = "";
        final int id = (Integer)cidsBean.getProperty("id");
        final CidsBean stat09 = (CidsBean)cidsBean.getProperty("stat09");
        if (stat09 != null) {
            final double wert = (Double)stat09.getProperty(LinearReferencingConstants.PROP_STATION_VALUE);
            final CidsBean route = (CidsBean)stat09.getProperty(LinearReferencingConstants.PROP_STATION_ROUTE);
            final long gwk = (Long)route.getProperty(LinearReferencingConstants.PROP_ROUTE_GWK);

            queryWkFg = "SELECT "
                        + "   " + mcWkFg.getID() + ", "
                        + "   wk_fg." + mcWkFg.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + mcWkFg.getTableName() + " AS wk_fg, "
                        + "   " + mcWkFgTeile.getTableName() + " AS wk_fg_teile, "
                        + "   " + mcWkTeil.getTableName() + " AS wk_teil, "
                        + "   " + mcLine.getTableName() + " AS linie, "
                        + "   " + mcStation.getTableName() + " AS von, "
                        + "   " + mcStation.getTableName() + " AS bis, "
                        + "   " + mcRoute.getTableName() + " AS route "
                        + "WHERE "
                        + "   wk_fg.teile = wk_fg_teile.wk_fg_reference AND "
                        + "   wk_fg_teile.teil = wk_teil.id AND "
                        + "   wk_teil.linie = linie." + LinearReferencingConstants.PROP_ID + " AND "
                        + "   linie." + LinearReferencingConstants.PROP_STATIONLINIE_FROM + " = von."
                        + LinearReferencingConstants.PROP_ID + " AND "
                        + "   linie." + LinearReferencingConstants.PROP_STATIONLINIE_TO + " = bis."
                        + LinearReferencingConstants.PROP_ID + " AND "
                        + "   von.route = route." + LinearReferencingConstants.PROP_ID + " AND "
                        + "   route." + LinearReferencingConstants.PROP_ROUTE_GWK + " = " + Long.toString(gwk)
                        + " AND ( "
                        + "      (von." + LinearReferencingConstants.PROP_STATION_VALUE + " <= " + Double.toString(wert)
                        + " AND bis." + LinearReferencingConstants.PROP_STATION_VALUE + " >= "
                        + Double.toString(wert) + ") OR "
                        + "      (bis." + LinearReferencingConstants.PROP_STATION_VALUE + " <= " + Double.toString(wert)
                        + " AND von." + LinearReferencingConstants.PROP_STATION_VALUE + " >= "
                        + Double.toString(wert) + ") "
                        + "   ) "
                        + ";";
        }
        try {
            final MetaObject[] mosWkFg;
            final MetaObject[] mosWkSg;
            if (LOG.isDebugEnabled()) {
                LOG.debug("queryWkFg  => " + queryWkFg);
            }
            mosWkFg = SessionManager.getProxy().getMetaObjectByQuery(queryWkFg, 0);

            final MetaObject moWkFg;
            if ((mosWkFg != null) && (mosWkFg.length > 0)) {
                moWkFg = mosWkFg[0];
                final String wkK = (String)moWkFg.getAttributeByFieldName("wk_k").getValue();
                querbauwerkePanTwo.setWaKoerper(wkK);
                querbauwerkePanTwo.setMoWk(moWkFg);
                querbauwerkePanTwo.setKategorie(QuerbauwerkePanTwo.Kategorie.Fliessgewaesser);
            } else {
                if (stat09 != null) {
                    queryWkSg = "SELECT "
                                + "   " + mcWkSg.getID() + ", "
                                + "   wk_sg." + mcWkSg.getPrimaryKey() + " "
                                + "FROM "
                                + "   " + mcWkSg.getTableName() + " AS wk_sg, "
                                + "   " + mcGeom.getTableName() + " AS geom_sg, "
                                + "   ( "
                                + "      SELECT "
                                + "         querbauwerke.id AS id, "
                                + "         station_von.wert AS wert, "
                                + "         route.gwk AS gwk, "
                                + "         st_linesubstring( "
                                + "            geom_route.geo_field, "
                                + "            (case when station_von.wert < station_bis.wert then station_von.wert else station_bis.wert end ) / length2d(geom_route.geo_field), "
                                + "            (case when station_von.wert < station_bis.wert then station_bis.wert else station_von.wert end ) / length2d(geom_route.geo_field) "
                                + "         ) AS geom "
                                + "      FROM "
                                + "         " + mcQuerbauwerke.getTableName() + " AS querbauwerke, "
                                + "         " + mcStation.getTableName() + " AS station_von, "
                                + "         " + mcStation.getTableName() + " AS station_bis, "
                                + "         " + mcRoute.getTableName() + " AS route, "
                                + "         " + mcGeom.getTableName() + " AS geom_route "
                                + "      WHERE "
                                + "         querbauwerke.stat09 = station_von.id AND "
                                + "         querbauwerke.stat09_bis = station_bis.id AND "
                                + "         station_von.route = route.id AND "
                                + "         route.geom = geom_route.id "
                                + "   ) AS qbw, "
                                + "   ( "
                                + "      SELECT "
                                + "         querbauwerke.id AS id, "
                                + "         ST_Extent(geom_route.geo_field) AS geom "
                                + "      FROM "
                                + "         " + mcQuerbauwerke.getTableName() + " AS querbauwerke, "
                                + "         " + mcStation.getTableName() + " AS station_von, "
                                + "         " + mcRoute.getTableName() + " AS route, "
                                + "         " + mcGeom.getTableName() + " AS geom_route "
                                + "      WHERE "
                                + "         querbauwerke.stat09 = station_von.id AND "
                                + "         station_von.route = route.id AND "
                                + "         route.geom = geom_route.id "
                                + "      GROUP BY querbauwerke.id "
                                + "   ) AS qbw_ext "
                                + "WHERE "
                                + "   qbw.id = " + id + " AND "
                                + "   wk_sg.geom = geom_sg.id AND "
                                + "   qbw.id = qbw_ext.id AND "
                                + "   geom_sg.geo_field && qbw_ext.geom AND "
                                + "   ST_Intersects( "
                                + "      geom_sg.geo_field, "
                                + "      qbw.geom "
                                + "   ) "
                                + ";";

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("queryWkSg  => " + queryWkSg);
                    }
                    mosWkSg = SessionManager.getProxy().getMetaObjectByQuery(queryWkSg, 0);

                    if ((mosWkSg != null) && (mosWkSg.length > 0)) {
                        final MetaObject moWkSg = mosWkSg[0];

                        final String wkK = (String)moWkSg.getAttributeByFieldName("wk_k").getValue();
                        querbauwerkePanTwo.setWaKoerper(wkK);
                        querbauwerkePanTwo.setMoWk(moWkSg);
                        querbauwerkePanTwo.setKategorie(QuerbauwerkePanTwo.Kategorie.Standgewaesser);
                    } else {
                        querbauwerkePanTwo.setWaKoerper(null);
                        querbauwerkePanTwo.setMoWk(null);
                        querbauwerkePanTwo.setKategorie(null);
                    }
                }
            }
        } catch (ConnectionException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while fetching metaobject", ex);
            }
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while autosetting wa_koerper", ex);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void updateQbwId() {
        try {
            final CidsBean stat09 = (CidsBean)cidsBean.getProperty("stat09");

            if (stat09 != null) {
                final String wert =
                    new DecimalFormat("#.#").format((Double)stat09.getProperty(
                            LinearReferencingConstants.PROP_STATION_VALUE));
                final CidsBean route = (CidsBean)stat09.getProperty(LinearReferencingConstants.PROP_STATION_ROUTE);
                final String gwk = String.valueOf(route.getProperty(LinearReferencingConstants.PROP_ROUTE_GWK));
                querbauwerkePanOne.setQbwLage(gwk + "@" + wert);
            } else {
                querbauwerkePanOne.setQbwLage("");
            }
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.error("error while auto-setting qbw_id", ex);
            }
        }
    }

    @Override
    public void dispose() {
        querbauwerkePanOne.dispose();
        querbauwerkePanTwo.dispose();
        querbauwerkePanThree.dispose();
        querbauwerkePanFour.dispose();
        querbauwerkePanFive.dispose();

        if (querbauwerkePanSix != null) {
            querbauwerkePanSix.dispose();
        }
        querbauwerkePanSeven1.dispose();
        if (this.cidsBean != null) {
            this.cidsBean.removePropertyChangeListener(this);
        }
    }

    @Override
    public String getTitle() {
        return String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        // NOP
    }

    @Override
    public boolean prepareForSave() {
        if (cidsBean != null) {
            try {
                cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());
                cidsBean.setProperty("av_time", new java.sql.Timestamp(System.currentTimeMillis()));

                final String zweck = (String)cidsBean.getProperty("zweck.value");

                if ((zweck != null) && zweck.equals("9")) {
                    if (cidsBean.getProperty("baujahr") == null) {
                        JOptionPane.showMessageDialog(
                            QuerbauwerkeEditor.this,
                            NbBundle.getMessage(
                                QuerbauwerkeEditor.class,
                                "QuerbauwerkeEditor.prepareForSave.baujahr.message"),
                            NbBundle.getMessage(
                                QuerbauwerkeEditor.class,
                                "QuerbauwerkeEditor.prepareForSave.baujahr.title"),
                            JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    if (cidsBean.getProperty("opt_jahr") == null) {
                        JOptionPane.showMessageDialog(
                            QuerbauwerkeEditor.this,
                            NbBundle.getMessage(
                                QuerbauwerkeEditor.class,
                                "QuerbauwerkeEditor.prepareForSave.opt_jahr.message"),
                            NbBundle.getMessage(
                                QuerbauwerkeEditor.class,
                                "QuerbauwerkeEditor.prepareForSave.opt_jahr.title"),
                            JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    if (cidsBean.getProperty("faa_typ") == null) {
                        JOptionPane.showMessageDialog(
                            QuerbauwerkeEditor.this,
                            NbBundle.getMessage(
                                QuerbauwerkeEditor.class,
                                "QuerbauwerkeEditor.prepareForSave.faa_typ.message"),
                            NbBundle.getMessage(
                                QuerbauwerkeEditor.class,
                                "QuerbauwerkeEditor.prepareForSave.faa_typ.title"),
                            JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }

                return CidsBeanSupport.checkOptionalAttribute(cidsBean, this, null);
            } catch (Exception ex) {
                LOG.error(ex, ex);
            }
        }

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
        new WrrlEditorTester("Querbauwerke", QuerbauwerkeEditor.class, WRRLUtil.DOMAIN_NAME).run();
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    @Override
    public BeanInitializer getBeanInitializer() {
        return new DefaultBeanInitializer(cidsBean) {

                private final String[] IGNORED_SIMPLE_TYPES = {
                        "av_user",
                        "av_date",
                        "anlagename",
                        "massn_id",
                        "zurueckgebaut",
                        "foto",
                        "starr_ander",
                        "wehr_andere",
                        "info_quel",
                        "sqa_id",
                        "bemerk_alt",
                        "hb",
                        "b_index1",
                        "b_unio",
                        "b_rhithral",
                        "vorrang",
                        "qbw_anz3a",
                        "up_bem",
                        "endkontr",
                        "unz_oberh",
                        "b_aal",
                        "b_stoer",
                        "b_fna",
                        "b_lachs",
                        "b_mai",
                        "b_mf",
                        "b_mna",
                        "b_schnaep",
                        "b_stint_w",
                        "b_bf",
                        "b_bna",
                        "b_elritze",
                        "b_groppe",
                        "b_quappe",
                        "b_rapfen",
                        "b_stint_b",
                        "b_wels",
                        "b_zaehrte",
                        "b_zope",
                        "zuord_faa"
                    };

                {
                    Arrays.sort(IGNORED_SIMPLE_TYPES);
                }

                @Override
                protected void processSimpleProperty(final CidsBean beanToInit,
                        final String propertyName,
                        final Object simpleValueToProcess) throws Exception {
                    if (Arrays.binarySearch(IGNORED_SIMPLE_TYPES, propertyName) >= 0) {
                        return;
                    }
                    super.processSimpleProperty(beanToInit, propertyName, simpleValueToProcess);
                }

                @Override
                protected void processArrayProperty(final CidsBean beanToInit,
                        final String propertyName,
                        final Collection<CidsBean> arrayValueToProcess) throws Exception {
                    final List<CidsBean> beans = CidsBeanSupport.getBeanCollectionFromProperty(
                            beanToInit,
                            propertyName);
                    beans.clear();

                    for (final CidsBean tmp : arrayValueToProcess) {
                        beans.add(tmp);
                    }
                }

                @Override
                protected void processComplexProperty(final CidsBean beanToInit,
                        final String propertyName,
                        final CidsBean complexValueToProcess) throws Exception {
                    if (propertyName.equals("stat09") || propertyName.equals("stat09_bis")
                                || propertyName.equals("massn_ref")
                                || propertyName.equals("massn1") || propertyName.equals("massn2")
                                || propertyName.equals("massn3") || propertyName.equalsIgnoreCase("foto_richtung")
                                || propertyName.equals("starr") || propertyName.equalsIgnoreCase("wehr_1")
                                || propertyName.equalsIgnoreCase("wehr_2") || propertyName.equalsIgnoreCase("oeko_dgk")
                                || propertyName.equalsIgnoreCase("dgk_warum")) {
                        return;
                    }

                    // flat copy
                    beanToInit.setProperty(propertyName, complexValueToProcess);
                }
            };
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("stat09")) {
            refreshReadOnlyFields();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  qbwBean  DOCUMENT ME!
     */
    private void loadRelatedQbw(final CidsBean qbwBean) {
        final String up_agg_id = (String)qbwBean.getProperty("up_agg_id");

        if (up_agg_id != null) {
            try {
                final MetaClass MC = ClassCacheMultiple.getMetaClass(
                        WRRLUtil.DOMAIN_NAME,
                        "querbauwerke");
                String query = "select " + MC.getID() + ", q." + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
                query += " q WHERE q.qbw_id_u = '" + up_agg_id + "'";                                               // NOI18N

                final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
                final List<Node> qbwNodeList = new ArrayList<Node>();

                if ((metaObjects != null)) {
                    for (final MetaObject qbwMetaObject : metaObjects) {
                        qbwNodeList.add(new MetaObjectNode(qbwMetaObject.getBean()));
                    }

                    MethodManager.getManager()
                            .showSearchResults(null, qbwNodeList.toArray(new Node[qbwNodeList.size()]), false);
                    MethodManager.getManager().showSearchResults();
                }
            } catch (final ConnectionException e) {
                LOG.error("Error while trying to receive qbws.", e); // NOI18N
            }
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class DropPanel extends JPanel implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> al) {
            if (al.size() == 1) {
                final CidsBean qbwBean = al.get(0);
                loadRelatedQbw(qbwBean);
            }
        }
    }
}
