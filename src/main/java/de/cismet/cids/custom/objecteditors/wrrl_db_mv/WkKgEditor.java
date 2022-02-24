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
 * WkKgEditor.java
 *
 * Created on 04.10.2010, 10:29:53
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.wrrl_db_mv.util.WrrlEditorTester;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class WkKgEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkKgEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    private final PropertyChangeListener propertyChange = new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent pce) {
                if (pce.getPropertyName().equals("ty_cd_cw")) {
                    try {
                        cidsBean.setProperty(
                            "ty_na_cw",
                            (String)((CidsBean)cidsBean.getProperty("ty_cd_cw")).getProperty("name"));
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("autosetting ty_na_cw failed", ex);
                        }
                    }
                } else if (pce.getPropertyName().equals("nitrat")
                            || pce.getPropertyName().equals("eqs_hm")
                            || pce.getPropertyName().equals("eqs_pestic")
                            || pce.getPropertyName().equals("eqs_indpol")
                            || pce.getPropertyName().equals("eqs_othpl")
                            || pce.getPropertyName().equals("eqs_onatpl")) {
                    try {
                        int worstCaseValue = 0;
                        final String[] props = {
                                "nitrat",
                                "eqs_hm",
                                "eqs_pestic",
                                "eqs_indpol",
                                "eqs_othpl",
                                "eqs_onatpl"
                            };
                        int worstCasePropIndex = 0;
                        for (int propIndex = 0; propIndex < props.length; propIndex++) {
                            final String prop = props[propIndex];
                            final String baseValue = (String)((CidsBean)cidsBean.getProperty(prop)).getProperty(
                                    "value");
                            int propValue = 0;
                            try {
                                propValue = Integer.valueOf(baseValue);
                            } catch (Exception ex) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("baseValInt cast", ex);
                                }
                            }

                            if (propValue >= worstCaseValue) {
                                worstCasePropIndex = propIndex;
                                worstCaseValue = propValue;
                            }
                        }
                        cidsBean.setProperty(
                            "chem_stat",
                            (CidsBean)cidsBean.getProperty(props[worstCasePropIndex]));
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("autosetting chem_stat failed", ex);
                        }
                    }
                } else if (pce.getPropertyName().equals("risk_ecpo")
                            || pce.getPropertyName().equals("risk_ecst")) {
                    try {
                        int worstCaseValue = 0;
                        final String[] props = { "risk_ecpo", "risk_ecst" };
                        int worstCasePropIndex = 0;
                        for (int propIndex = 0; propIndex < props.length; propIndex++) {
                            final String prop = props[propIndex];
                            final String baseValue = (String)((CidsBean)cidsBean.getProperty(prop)).getProperty(
                                    "value");
                            int propValue = 0;
                            try {
                                propValue = Integer.valueOf(baseValue);
                            } catch (Exception ex) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("baseValInt cast", ex);
                                }
                            }

                            if (propValue >= worstCaseValue) {
                                worstCasePropIndex = propIndex;
                                worstCaseValue = propValue;
                            }
                        }
                        cidsBean.setProperty(
                            "risk_total",
                            (CidsBean)cidsBean.getProperty(props[worstCasePropIndex]));
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("autosetting risk_total failed", ex);
                        }
                    }
                }
            }
        };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblSpacingBottom;
    private javax.swing.JLabel lblSpacingBottom1;
    private javax.swing.JLabel lblSpacingBottom2;
    private javax.swing.JLabel lblSpacingBottom3;
    private javax.swing.JPanel panAllgemeines;
    private javax.swing.JPanel panFooter;
    private javax.swing.JPanel panQualitaetsinformationen;
    private javax.swing.JPanel panRisikoabschätzung;
    private javax.swing.JPanel panWeitereInformationen;
    private javax.swing.JTabbedPane tpMain;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanEight wkKgPanEight1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanFive wkKgPanFive1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanFour wkKgPanFour1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanNine wkKgPanNine1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanOne wkKgPanOne1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanSeven wkKgPanSeven1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanSix wkKgPanSix1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanThree wkKgPanThree1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanTwo wkKgPanTwo1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkKgEditor.
     */
    public WkKgEditor() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        initComponents();
        panRisikoabschätzung.setVisible(false);
        panWeitereInformationen.setVisible(false);
        panQualitaetsinformationen.setVisible(false);
        tpMain.remove(panRisikoabschätzung);
        tpMain.remove(panWeitereInformationen);
        tpMain.remove(panQualitaetsinformationen);

        tpMain.setUI(new TabbedPaneUITransparent());
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

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        tpMain = new javax.swing.JTabbedPane();
        panAllgemeines = new javax.swing.JPanel();
        wkKgPanOne1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanOne();
        lblSpacingBottom = new javax.swing.JLabel();
        panRisikoabschätzung = new javax.swing.JPanel();
        wkKgPanEight1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanEight();
        lblSpacingBottom2 = new javax.swing.JLabel();
        wkKgPanTwo1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanTwo();
        panQualitaetsinformationen = new javax.swing.JPanel();
        lblSpacingBottom1 = new javax.swing.JLabel();
        wkKgPanFive1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanFive();
        wkKgPanThree1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanThree();
        wkKgPanFour1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanFour();
        wkKgPanSix1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanSix();
        wkKgPanSeven1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanSeven();
        panWeitereInformationen = new javax.swing.JPanel();
        wkKgPanNine1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgPanNine();
        lblSpacingBottom3 = new javax.swing.JLabel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setLayout(new java.awt.BorderLayout());

        panAllgemeines.setOpaque(false);
        panAllgemeines.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panAllgemeines.add(wkKgPanOne1, gridBagConstraints);

        lblSpacingBottom.setText(org.openide.util.NbBundle.getMessage(
                WkKgEditor.class,
                "WkKgEditor.lblSpacingBottom.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panAllgemeines.add(lblSpacingBottom, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                WkKgEditor.class,
                "WkKgEditor.panAllgemeines.TabConstraints.tabTitle"),
            panAllgemeines); // NOI18N

        panRisikoabschätzung.setOpaque(false);
        panRisikoabschätzung.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panRisikoabschätzung.add(wkKgPanEight1, gridBagConstraints);

        lblSpacingBottom2.setText(org.openide.util.NbBundle.getMessage(
                WkKgEditor.class,
                "WkKgEditor.lblSpacingBottom2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panRisikoabschätzung.add(lblSpacingBottom2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panRisikoabschätzung.add(wkKgPanTwo1, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                WkKgEditor.class,
                "WkKgEditor.panRisikoabschätzung.TabConstraints.tabTitle"),
            panRisikoabschätzung); // NOI18N

        panQualitaetsinformationen.setOpaque(false);
        panQualitaetsinformationen.setLayout(new java.awt.GridBagLayout());

        lblSpacingBottom1.setText(org.openide.util.NbBundle.getMessage(
                WkKgEditor.class,
                "WkKgEditor.lblSpacingBottom1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panQualitaetsinformationen.add(lblSpacingBottom1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        panQualitaetsinformationen.add(wkKgPanFive1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panQualitaetsinformationen.add(wkKgPanThree1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        panQualitaetsinformationen.add(wkKgPanFour1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        panQualitaetsinformationen.add(wkKgPanSix1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        panQualitaetsinformationen.add(wkKgPanSeven1, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                WkKgEditor.class,
                "WkKgEditor.panQualitaetsinformationen.TabConstraints.tabTitle"),
            panQualitaetsinformationen); // NOI18N

        panWeitereInformationen.setOpaque(false);
        panWeitereInformationen.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panWeitereInformationen.add(wkKgPanNine1, gridBagConstraints);

        lblSpacingBottom3.setText(org.openide.util.NbBundle.getMessage(
                WkKgEditor.class,
                "WkKgEditor.lblSpacingBottom3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panWeitereInformationen.add(lblSpacingBottom3, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                WkKgEditor.class,
                "WkKgEditor.panWeitereInformationen.TabConstraints.tabTitle"),
            panWeitereInformationen); // NOI18N

        add(tpMain, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        if (cidsBean != null) {
            wkKgPanOne1.setCidsBean(cidsBean);
            wkKgPanTwo1.setCidsBean(cidsBean);
            wkKgPanThree1.setCidsBean(cidsBean);
            wkKgPanFour1.setCidsBean(cidsBean);
            wkKgPanFive1.setCidsBean(cidsBean);
            wkKgPanSix1.setCidsBean(cidsBean);
            wkKgPanSeven1.setCidsBean(cidsBean);
            wkKgPanEight1.setCidsBean(cidsBean);
            wkKgPanNine1.setCidsBean(cidsBean);

            // *.setCidsBean(cidsBean);
            bindingGroup.bind();

            cidsBean.addPropertyChangeListener(propertyChange);

//            cidsBean.addPropertyChangeListener(new PropertyChangeListener() {
//
//                    @Override
//                    public void propertyChange(final PropertyChangeEvent pce) {
//                        if (pce.getPropertyName().equals("name")) {
//                            try {
//                                cidsBean.setProperty("ms_cd_cw", "DEMV_" + (String)pce.getNewValue());
//                            } catch (Exception ex) {
//                                if (LOG.isDebugEnabled()) {
//                                    LOG.debug("autosetting ms_cd_cw failed", ex);
//                                }
//                            }
//                        }
//                    }
//                });

//            cidsBean.addPropertyChangeListener(new PropertyChangeListener() {
//
//                    @Override
//                    public void propertyChange(final PropertyChangeEvent pce) {
//                        if (pce.getPropertyName().equals("ms_cd_cw")) {
//                            try {
//                                cidsBean.setProperty("eu_cd_cw", "DEMV_" + (String)pce.getNewValue());
//                            } catch (Exception ex) {
//                                if (LOG.isDebugEnabled()) {
//                                    LOG.debug("autosetting eu_cd_cw failed", ex);
//                                }
//                            }
//                        }
//                    }
//                });

//            cidsBean.addPropertyChangeListener(new PropertyChangeListener() {
//
//                    @Override
//                    public void propertyChange(final PropertyChangeEvent pce) {
//                        if (pce.getPropertyName().equals("ms_cd_cw")) {
//                            try {
//                                cidsBean.setProperty("eu_cd_cw", "DEMV_" + (String)pce.getNewValue());
//                            } catch (Exception ex) {
//                                if (LOG.isDebugEnabled()) {
//                                    LOG.debug("autosetting eu_cd_cw failed", ex);
//                                }
//                            }
//                        }
//                    }
//                });
        }
    }

    @Override
    public void dispose() {
        if (cidsBean != null) {
            cidsBean.removePropertyChangeListener(propertyChange);
        }
        wkKgPanOne1.dispose();
        wkKgPanTwo1.dispose();
        wkKgPanThree1.dispose();
        wkKgPanFour1.dispose();
        wkKgPanFive1.dispose();
        wkKgPanSix1.dispose();
        wkKgPanSeven1.dispose();
        wkKgPanEight1.dispose();
        wkKgPanNine1.dispose();
        bindingGroup.unbind();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        new WrrlEditorTester("wk_kg", WkKgEditor.class, WRRLUtil.DOMAIN_NAME).run();
    }

    @Override
    public String getTitle() {
        return "Wasserkörper " + String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        // TODO ?
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }
}
