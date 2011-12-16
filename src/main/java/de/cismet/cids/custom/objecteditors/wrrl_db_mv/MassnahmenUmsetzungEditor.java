/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import org.openide.util.NbBundle;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.MapUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.MassnahmenUmsetzungCache;
import de.cismet.cids.custom.wrrl_db_mv.util.RouteWBDropBehavior;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingConstants;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnahmenUmsetzungEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            MassnahmenUmsetzungEditor.class);
    public static final String[] WB_PROPERTIES = { "wk_fg", "wk_sg", "wk_kg", "wk_gw" }; // NOI18N
    private static final String MASSN_STARTED_PROPERTY = "massn_started";

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;
    private boolean isStandaloneEditor = true;
    private CidsBean cidsBean;
    private ArrayList<CidsBean> beansToDelete = new ArrayList<CidsBean>();
    private ArrayList<CidsBean> beansToSave = new ArrayList<CidsBean>();
    private JList referencedList;
    private RouteWBDropBehavior dropBehaviorListener;
    private Thread actionRetrievalThread = null;
    private MassnahmenUmsetzungCache cache;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbGeom;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblBeschrDerMa;
    private javax.swing.JLabel lblGeom;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblMassnahme_nr;
    private javax.swing.JLabel lblValMassnahme_nr;
    private javax.swing.JLabel lblValWk_k;
    private javax.swing.JLabel lblWk_k;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private de.cismet.tools.gui.RoundedPanel panGeo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private javax.swing.JPanel panInfoContent1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenUmsetzungEditor object.
     */
    public MassnahmenUmsetzungEditor() {
        this(true, false);
    }

    /**
     * Creates new form MassnahmenUmsetzungEditor.
     *
     * @param  isStandaloneEditor  DOCUMENT ME!
     * @param  readOnly            DOCUMENT ME!
     */
    public MassnahmenUmsetzungEditor(final boolean isStandaloneEditor, final boolean readOnly) {
        this.isStandaloneEditor = isStandaloneEditor;
        this.readOnly = readOnly;

        if (!isStandaloneEditor) {
            // is embedded in an other editor/renderer
            initComponents();
            dropBehaviorListener = new RouteWBDropBehavior(this);
            linearReferencedLineEditor.setLineField("linie");                 // NOI18N
            linearReferencedLineEditor.setDropBehavior(dropBehaviorListener);
            linearReferencedLineEditor.setOtherLinesEnabled(false);
            deActivateGUIElements(false);
            jPanel1.setVisible(!readOnly);
            try {
                new CidsBeanDropTarget(this);
            } catch (final Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
                }
            }
        } else {
            // is a standalone editor/renderer
            final JLabel hintLabel = new JLabel();
            hintLabel.setText(NbBundle.getMessage(
                    MassnahmenUmsetzungEditor.class,
                    "MassnahmenUmsetzungEditor.hintLabel.text"));
            setLayout(new GridBagLayout());
            final GridBagConstraints constraints = new GridBagConstraints(
                    1,
                    1,
                    1,
                    1,
                    0,
                    0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.NONE,
                    new Insets(5, 5, 5, 5),
                    0,
                    0);
            add(hintLabel, constraints);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  arg  DOCUMENT ME!
     */
    public static void main(final String[] arg) {
        final JFrame f = new JFrame("Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final MassnahmenUmsetzungEditor s = new MassnahmenUmsetzungEditor();
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(s, BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
    }

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        MapUtil.zoomToFeatureCollection(linearReferencedLineEditor.getZoomFeatures());
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

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblBeschrDerMa = new javax.swing.JLabel();
        lblWk_k = new javax.swing.JLabel();
        lblValWk_k = new javax.swing.JLabel();
        lblMassnahme_nr = new javax.swing.JLabel();
        lblValMassnahme_nr = new javax.swing.JLabel();
        panGeo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        linearReferencedLineEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor();
        jPanel1 = new javax.swing.JPanel();
        cbGeom = new DefaultCismapGeometryComboBoxEditor();
        lblGeom = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(440, 675));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(440, 675));
        setLayout(new java.awt.GridBagLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(550, 480));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(550, 480));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(422, 75));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(422, 75));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(3);
        jTextArea1.setDisabledTextColor(new java.awt.Color(26, 26, 26));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mass_beschreibung}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        lblBeschrDerMa.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblBeschrDerMa.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanel2.add(lblBeschrDerMa, gridBagConstraints);

        lblWk_k.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblWk_k.text")); // NOI18N
        lblWk_k.setMinimumSize(new java.awt.Dimension(182, 20));
        lblWk_k.setPreferredSize(new java.awt.Dimension(182, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblWk_k, gridBagConstraints);

        lblValWk_k.setMinimumSize(new java.awt.Dimension(250, 20));
        lblValWk_k.setPreferredSize(new java.awt.Dimension(250, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(lblValWk_k, gridBagConstraints);

        lblMassnahme_nr.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblMassnahme_nr.text")); // NOI18N
        lblMassnahme_nr.setMinimumSize(new java.awt.Dimension(182, 20));
        lblMassnahme_nr.setPreferredSize(new java.awt.Dimension(182, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblMassnahme_nr, gridBagConstraints);

        lblValMassnahme_nr.setMinimumSize(new java.awt.Dimension(250, 20));
        lblValMassnahme_nr.setPreferredSize(new java.awt.Dimension(250, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(lblValMassnahme_nr, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 5, 20);
        add(jPanel2, gridBagConstraints);

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panGeo.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        panInfoContent1.add(linearReferencedLineEditor, gridBagConstraints);

        panGeo.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 10, 20);
        add(panGeo, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        if (!readOnly) {
            cbGeom.setMinimumSize(new java.awt.Dimension(300, 20));
            cbGeom.setPreferredSize(new java.awt.Dimension(300, 20));

            binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                    org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                    this,
                    org.jdesktop.beansbinding.ELProperty.create("${cidsBean.additional_geom}"),
                    cbGeom,
                    org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
            binding.setConverter(((DefaultCismapGeometryComboBoxEditor)cbGeom).getConverter());
            bindingGroup.addBinding(binding);
        }
        if (!readOnly) {
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
            jPanel1.add(cbGeom, gridBagConstraints);
        }

        lblGeom.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblGeom.text")); // NOI18N
        lblGeom.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(lblGeom, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        add(jPanel1, gridBagConstraints);

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
        CidsBean action = null;
        CidsBean wb = null;
        if (isStandaloneEditor) {
            return;
        }

        if (dropBehaviorListener.isRouteChanged() && !linearReferencedLineEditor.hasChangedSinceDrop()) {
            final int ans = JOptionPane.showConfirmDialog(
                    this,
                    "Sie haben die Stationen nicht geändert, nachdem Sie eine "
                            + "neue Route ausgewählt haben. Möchten Sie die Stationen ändern?",
                    "Keine Änderung der Stationen",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (ans == JOptionPane.YES_OPTION) {
                return;
            }
        }

        dispose();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            if (cache != null) {
                action = cache.getAction(cidsBean);
                wb = cache.getWB(cidsBean);
            }
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).setCidsMetaObject(cidsBean.getMetaObject());
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).initForNewBinding();
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);

            if ((wb == null) && (cidsBean != null) && (MassnahmenUmsetzungCache.getWk_kId(cidsBean) != null)) {
                final CidsBean wkObject = MassnahmenUmsetzungCache.bindWkkField(lblValWk_k, cidsBean);
                dropBehaviorListener.setWkFg(wkObject);
            } else if (wb != null) {
                dropBehaviorListener.setWkFg(wb);
                lblValWk_k.setText(String.valueOf(wb.getProperty(MassnahmenUmsetzungCache.getWk_kProperty(cidsBean))));
            } else if ((cidsBean != null) && (MassnahmenUmsetzungCache.getWk_kId(cidsBean) == null)) {
                lblValWk_k.setText(CidsBeanSupport.FIELD_NOT_SET);
            } else {
            }

            bindingGroup.bind();
            deActivateGUIElements(true);
            zoomToFeatures();
        } else {
            dropBehaviorListener.setWkFg(null);
            deActivateGUIElements(false);
            lblValWk_k.setText("");
        }

        linearReferencedLineEditor.setCidsBean(cidsBean);

        waitForRunningThreads();
        if ((action == null) && (cidsBean != null) && (cidsBean.getProperty("massnahme") != null)) {
            lblValMassnahme_nr.setText("");

            actionRetrievalThread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            MassnahmenUmsetzungCache.bindActionField(lblValMassnahme_nr, cidsBean);
                        }
                    });
            actionRetrievalThread.start();
        } else if (action != null) {
            lblValMassnahme_nr.setText(String.valueOf(action.getProperty("massn_id")));
        } else if ((cidsBean != null) && (cidsBean.getProperty("massnahme") == null)) {
            lblValMassnahme_nr.setText(CidsBeanSupport.FIELD_NOT_SET);
        } else {
            lblValMassnahme_nr.setText("");
        }
    }

    /**
     * interrupt the last retrieval threads and wait until the threads are stopped.
     */
    private void waitForRunningThreads() {
        if ((actionRetrievalThread != null) && actionRetrievalThread.isAlive()) {
            actionRetrievalThread.interrupt();
        }

        while ((actionRetrievalThread != null) && actionRetrievalThread.isAlive()) {
            try {
                Thread.sleep(50);
            } catch (final InterruptedException e) {
                // nothing to do
            }
        }
    }

    @Override
    public void dispose() {
        if (!isStandaloneEditor) {
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).dispose();
            linearReferencedLineEditor.dispose();
            bindingGroup.unbind();
        }
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void setTitle(final String title) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param  referencedList  DOCUMENT ME!
     */
    public void setList(final JList referencedList) {
        this.referencedList = referencedList;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    private void deActivateGUIElements(boolean enable) {
        if (readOnly) {
            enable = false;
        }
        jTextArea1.setEnabled(enable);
        cbGeom.setEnabled(enable);
        linearReferencedLineEditor.setEnabled(enable);
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        if (isStandaloneEditor || readOnly) {
            return;
        }
        if (cidsBean != null) {
            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_fg")) {             // NOI18N
                    bindToWb(WB_PROPERTIES[0], bean);
                    dropBehaviorListener.setWkFg(bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_sg")) {      // NOI18N
                    bindToWb(WB_PROPERTIES[1], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_kg")) {      // NOI18N
                    bindToWb(WB_PROPERTIES[2], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_gw")) {      // NOI18N
                    bindToWb(WB_PROPERTIES[3], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Massnahmen")) { // NOI18N
                    final int conf = JOptionPane.showConfirmDialog(
                            this,
                            "Soll die Maßnahme "
                                    + bean.toString()
                                    + " wirklich als Template für die ausgewählte Umsetzung dienen?",
                            "Maßnahme als Template nutzen",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (conf == JOptionPane.YES_OPTION) {
                        copyActionToImplementation(bean);
                        if (referencedList != null) {
                            referencedList.repaint();
                        }
                    }
                }
            }
//            bindReadOnlyFields(lblValWk_k, lblValMassnahme_nr, cidsBean, true);
            final CidsBean action = MassnahmenUmsetzungCache.bindActionField(lblValMassnahme_nr, cidsBean);
            final CidsBean wb = MassnahmenUmsetzungCache.bindWkkField(lblValWk_k, cidsBean);

            if (getCache() != null) {
                getCache().addAction(cidsBean, action);
                getCache().addWB(cidsBean, wb);
            }
        }
    }

    /**
     * binds the given water body to the current CidsBean object.
     *
     * @param  propertyName   the name of the water body property. (The allowed values are stored in the array
     *                        WB_PROPERTIES)
     * @param  propertyEntry  the water body object
     */
    private void bindToWb(final String propertyName, final CidsBean propertyEntry) {
        try {
            cidsBean.setProperty(propertyName, propertyEntry.getProperty("id"));

            for (final String propName : WB_PROPERTIES) {
                if (!propName.equals(propertyName)) {
                    cidsBean.setProperty(propName, null);
                }
            }
            showOrHideGeometryEditors();
        } catch (final Exception ex) {
            LOG.error("Error while binding a water body", ex); // NOI18N
        }
    }

    /**
     * copies all relevant properties from the given action to the current CidsBean object.
     *
     * @param  act  DOCUMENT ME!
     */
    public void copyActionToImplementation(final CidsBean act) {
        try {
            final CidsBean additionalGeom = (CidsBean)act.getProperty("additional_geom"); // NOI18N

            CidsBeanSupport.deletePropertyIfExists(cidsBean, "additional_geom", beansToDelete); // NOI18N

            cidsBean.setProperty("massnahme", act.getProperty("id"));                               // NOI18N
            cidsBean.setProperty("additional_geom", CidsBeanSupport.cloneCidsBean(additionalGeom)); // NOI18N

            if (act.getProperty("wk_fg") != null) {
                cidsBean.setProperty("wk_fg", ((CidsBean)act.getProperty("wk_fg")).getProperty("id")); // NOI18N
            } else {
                cidsBean.setProperty("wk_fg", null);                                                   // NOI18N
            }
            if (act.getProperty("wk_sg") != null) {
                cidsBean.setProperty("wk_sg", ((CidsBean)act.getProperty("wk_sg")).getProperty("id")); // NOI18N
            } else {
                cidsBean.setProperty("wk_sg", null);                                                   // NOI18N
            }
            if (act.getProperty("wk_kg") != null) {
                cidsBean.setProperty("wk_kg", ((CidsBean)act.getProperty("wk_kg")).getProperty("id")); // NOI18N
            } else {
                cidsBean.setProperty("wk_kg", null);                                                   // NOI18N
            }
            if (act.getProperty("wk_gw") != null) {
                cidsBean.setProperty("wk_gw", ((CidsBean)act.getProperty("wk_gw")).getProperty("id")); // NOI18N
            } else {
                cidsBean.setProperty("wk_gw", null);                                                   // NOI18N
            }

            final List<CidsBean> meas = CidsBeanSupport.getBeanCollectionFromProperty(act, "de_meas_cd"); // NOI18N

            if ((meas != null) && (meas.size() > 0)) {
                cidsBean.setProperty("measure_type_code", meas.get(0)); // NOI18N
            }

            final CidsBean linBean = (CidsBean)act.getProperty("linie");
            if (linBean != null) {                                                                                      // NOI18N
                final CidsBean statFrom = (CidsBean)linBean.getProperty(
                        LinearReferencingConstants.PROP_STATIONLINIE_FROM);                                             // NOI18N
                final CidsBean statTo = (CidsBean)linBean.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_TO); // NOI18N
                if (LOG.isDebugEnabled()) {
                    LOG.debug("statFrom " + statFrom.getProperty(LinearReferencingConstants.PROP_STATION_VALUE));       // NOI18N
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("statTo " + statTo.getProperty(LinearReferencingConstants.PROP_STATION_VALUE));           // NOI18N
                }

                CidsBeanSupport.deleteStationlineIfExists(cidsBean, "linie", beansToDelete); // NOI18N

                cidsBean.setProperty("linie", CidsBeanSupport.cloneStationline(linBean));
                linearReferencedLineEditor.setCidsBean(cidsBean);
            }

            if (act != null) {
                final Object started = act.getProperty(MASSN_STARTED_PROPERTY);
                if ((started == null) || ((started instanceof Boolean) && !((Boolean)started).booleanValue())) {
                    try {
                        act.setProperty(MASSN_STARTED_PROPERTY, Boolean.TRUE);
                        if (!beansToSave.contains(act)) {
                            beansToSave.add(act);
                        }
                    } catch (final Exception e) {
                        LOG.error("Error while set action to fin.", e); // NOI18N
                    }
                }
            }
        } catch (final Exception e) {
            LOG.error("Error during the creation of a new bean of type massnahmen", e); // NOI18N
        }
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        if (!isStandaloneEditor) {
            linearReferencedLineEditor.editorClosed(event);
        }
    }

    @Override
    public boolean prepareForSave() {
        if (isStandaloneEditor) {
            return true;
        }
        if (dropBehaviorListener.isRouteChanged() && !linearReferencedLineEditor.hasChangedSinceDrop()) {
            final int ans = JOptionPane.showConfirmDialog(
                    this,
                    "Sie haben die Stationen nicht geändert, nachdem Sie eine "
                            + "neue Route ausgewählt haben. Möchten Sie die Stationen ändern?",
                    "Keine Änderung der Stationen",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (ans == JOptionPane.YES_OPTION) {
                return false;
            }
        }

        for (final CidsBean bean : beansToDelete) {
            try {
                bean.persist();
            } catch (final Exception e) {
                LOG.error("Error while deleting bean", e); // NOI18N
            }
        }

        for (final CidsBean bean : beansToSave) {
            try {
                bean.persist();
            } catch (final Exception e) {
                LOG.error("Error while deleting bean", e); // NOI18N
            }
        }

        boolean save = true;
        save &= linearReferencedLineEditor.prepareForSave();
        return save;
    }

    /**
     * DOCUMENT ME!
     */
    private void showOrHideGeometryEditors() {
        if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[1]) != null)) {
            panGeo.setVisible(false);
        } else {
            panGeo.setVisible(true);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the cache
     */
    public MassnahmenUmsetzungCache getCache() {
        return cache;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cache  the cache to set
     */
    public void setCache(final MassnahmenUmsetzungCache cache) {
        this.cache = cache;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomElementComparator implements Comparator<CidsBean> {

        //~ Instance fields ----------------------------------------------------

        private int integerIndex = 0;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomElementComparator object.
         */
        public CustomElementComparator() {
        }

        /**
         * Creates a new CustomElementComparator object.
         *
         * @param  integerIndex  DOCUMENT ME!
         */
        public CustomElementComparator(final int integerIndex) {
            this.integerIndex = integerIndex;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final CidsBean o1, final CidsBean o2) {
            if ((o1 != null) && (o2 != null)) {
                final String strValue1 = (String)o1.getProperty("value");
                final String strValue2 = (String)o2.getProperty("value");

                if ((strValue1 != null) && (strValue2 != null)) {
                    try {
                        final Integer value1 = Integer.parseInt(strValue1.substring(integerIndex));
                        final Integer value2 = Integer.parseInt(strValue2.substring(integerIndex));

                        return value1.intValue() - value2.intValue();
                    } catch (NumberFormatException e) {
                        // nothing to do, because not every 'value'-property contains a integer
                    }

                    return strValue1.compareTo(strValue2);
                } else {
                    if ((strValue1 == null) && (strValue2 == null)) {
                        return 0;
                    } else {
                        return ((strValue1 == null) ? -1 : 1);
                    }
                }
            } else {
                if ((o1 == null) && (o2 == null)) {
                    return 0;
                } else {
                    return ((o1 == null) ? -1 : 1);
                }
            }
        }
    }
}
