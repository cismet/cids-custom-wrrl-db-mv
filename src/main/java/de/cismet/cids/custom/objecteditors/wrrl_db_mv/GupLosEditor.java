/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * GupLosEditor.java
 *
 * Created on 28.09.2012, 11:49:19
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.tree.MetaCatalogueTree;

import Sirius.server.search.CidsServerSearch;

import java.awt.Component;
import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.tree.TreePath;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.server.search.MassnahmenSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.CalculationCache;
import de.cismet.tools.Calculator;
import de.cismet.tools.CismetThreadPool;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.TitleComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupLosEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    TitleComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupLosEditor.class);
    private static CalculationCache<List, ArrayList<ArrayList>> massnCache =
        new CalculationCache<List, ArrayList<ArrayList>>(
            new GupLosEditor.MassnCalculator());

    //~ Instance fields --------------------------------------------------------

    private List<String> gups;
    private List<String> planungsabschnitte;
    private List<CidsBean> massnToAdd = new ArrayList<CidsBean>();

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private boolean initialised = false;
    private TreePath treePath;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jsGupList;
    private javax.swing.JScrollPane jsMassnahmenabschnittList;
    private javax.swing.JLabel lblBemerkungen;
    private javax.swing.JLabel lblBezeichnung;
    private javax.swing.JLabel lblGups;
    private javax.swing.JLabel lblMassnahmen;
    private javax.swing.JLabel lblMassnahmenabschnitte;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList liGup;
    private javax.swing.JList liPlan;
    private javax.swing.JPanel panTitle;
    private javax.swing.JTable tabMassn;
    private javax.swing.JTextArea teBemerkungHinweise;
    private javax.swing.JTextField txtBezeichnung;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form gup_los.
     */
    public GupLosEditor() {
        this(false);
    }

    /**
     * Creates new form gup_los.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupLosEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();

        if (readOnly) {
            setReadOnly();
        } else {
            try {
                new CidsBeanDropTarget(tabMassn);
                new CidsBeanDropTarget(liPlan);
                new CidsBeanDropTarget(jScrollPane2);
            } catch (final Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
                }
            }
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

        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblMassnahmenabschnitte = new javax.swing.JLabel();
        jsMassnahmenabschnittList = new javax.swing.JScrollPane();
        liPlan = new PlanungsabschnittList();
        jsGupList = new javax.swing.JScrollPane();
        liGup = new javax.swing.JList();
        lblGups = new javax.swing.JLabel();
        jScrollPane2 = new TabScrollPane();
        tabMassn = new MassnahmenTable();
        lblMassnahmen = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblBemerkungen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        teBemerkungHinweise = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        lblBezeichnung = new javax.swing.JLabel();
        txtBezeichnung = new javax.swing.JTextField();

        panTitle.setMinimumSize(new java.awt.Dimension(1050, 48));
        panTitle.setOpaque(false);
        panTitle.setPreferredSize(new java.awt.Dimension(1050, 44));
        panTitle.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24));                                                     // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText(org.openide.util.NbBundle.getMessage(GupLosEditor.class, "GupLosEditor.lblTitle.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTitle.add(lblTitle, gridBagConstraints);

        jButton1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/wrrl_db_mv/util/gup/export.png")));                // NOI18N
        jButton1.setText(org.openide.util.NbBundle.getMessage(GupLosEditor.class, "GupLosEditor.jButton1.text")); // NOI18N
        jButton1.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 7);
        panTitle.add(jButton1, gridBagConstraints);

        jButton2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/wrrl_db_mv/util/gup/print.png")));                 // NOI18N
        jButton2.setText(org.openide.util.NbBundle.getMessage(GupLosEditor.class, "GupLosEditor.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        panTitle.add(jButton2, gridBagConstraints);

        jLabel6.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/wrrl_db_mv/util/gup/gaeb.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panTitle.add(jLabel6, gridBagConstraints);

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1294, 500));
        setLayout(new java.awt.GridBagLayout());

        lblMassnahmenabschnitte.setText(org.openide.util.NbBundle.getMessage(
                GupLosEditor.class,
                "GupLosEditor.lblMassnahmenabschnitte.text")); // NOI18N
        lblMassnahmenabschnitte.setMaximumSize(new java.awt.Dimension(230, 17));
        lblMassnahmenabschnitte.setMinimumSize(new java.awt.Dimension(230, 17));
        lblMassnahmenabschnitte.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        add(lblMassnahmenabschnitte, gridBagConstraints);

        jsMassnahmenabschnittList.setPreferredSize(new java.awt.Dimension(300, 154));

        liPlan.setCellRenderer(new PlanungsabschnittListCellRenderer());
        jsMassnahmenabschnittList.setViewportView(liPlan);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 15, 15);
        add(jsMassnahmenabschnittList, gridBagConstraints);

        jsGupList.setPreferredSize(new java.awt.Dimension(300, 154));
        jsGupList.setViewportView(liGup);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 15, 15);
        add(jsGupList, gridBagConstraints);

        lblGups.setText(org.openide.util.NbBundle.getMessage(GupLosEditor.class, "GupLosEditor.lblGups.text")); // NOI18N
        lblGups.setMaximumSize(new java.awt.Dimension(230, 17));
        lblGups.setMinimumSize(new java.awt.Dimension(230, 17));
        lblGups.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        add(lblGups, gridBagConstraints);

        jScrollPane2.setOpaque(false);

        tabMassn.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    tabMassnMouseClicked(evt);
                }
            });
        jScrollPane2.setViewportView(tabMassn);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        add(jScrollPane2, gridBagConstraints);

        lblMassnahmen.setText(org.openide.util.NbBundle.getMessage(
                GupLosEditor.class,
                "GupLosEditor.lblMassnahmen.text")); // NOI18N
        lblMassnahmen.setMaximumSize(new java.awt.Dimension(230, 17));
        lblMassnahmen.setMinimumSize(new java.awt.Dimension(230, 17));
        lblMassnahmen.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        add(lblMassnahmen, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblBemerkungen.setText(org.openide.util.NbBundle.getMessage(
                GupLosEditor.class,
                "GupLosEditor.lblBemerkungen.text")); // NOI18N
        lblBemerkungen.setMaximumSize(new java.awt.Dimension(230, 17));
        lblBemerkungen.setMinimumSize(new java.awt.Dimension(230, 17));
        lblBemerkungen.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        jPanel2.add(lblBemerkungen, gridBagConstraints);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(300, 50));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(300, 50));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 50));

        teBemerkungHinweise.setColumns(20);
        teBemerkungHinweise.setRows(2);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung}"),
                teBemerkungHinweise,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(teBemerkungHinweise);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanel2, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblBezeichnung.setText(org.openide.util.NbBundle.getMessage(
                GupLosEditor.class,
                "GupLosEditor.lblBezeichnung.text")); // NOI18N
        lblBezeichnung.setMaximumSize(new java.awt.Dimension(150, 17));
        lblBezeichnung.setMinimumSize(new java.awt.Dimension(150, 17));
        lblBezeichnung.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        jPanel3.add(lblBezeichnung, gridBagConstraints);

        txtBezeichnung.setMaximumSize(new java.awt.Dimension(180, 20));
        txtBezeichnung.setMinimumSize(new java.awt.Dimension(180, 20));
        txtBezeichnung.setPreferredSize(new java.awt.Dimension(180, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bezeichnung}"),
                txtBezeichnung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
        jPanel3.add(txtBezeichnung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanel3, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tabMassnMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_tabMassnMouseClicked
    }                                                                        //GEN-LAST:event_tabMassnMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jButton2ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton2ActionPerformed
//        try {
//            java.awt.Desktop.getDesktop()
//                    .browse(java.net.URI.create("http://localhost/~thorsten/cids/web/gup/GWUTollense-Los1-5.pdf"));
//        } catch (Exception ex) {
//            log.error("Problem beim Oeffnen des LV", ex);
//        }
    } //GEN-LAST:event_jButton2ActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            treePath = ComponentRegistry.getRegistry().getCatalogueTree().getSelectionPath();
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            CismetThreadPool.execute(new Runnable() {

                    @Override
                    public void run() {
                        loadData();
                    }
                });
        }
    }

    /**
     * loads the contained objects from the server.
     */
    private void loadData() {
        try {
            final List in = new ArrayList(1);
            in.add(cidsBean.getProperty("id").toString());
            final ArrayList<ArrayList> massnBeans = massnCache.calcValue(in);
            gups = new ArrayList<String>();
            planungsabschnitte = new ArrayList<String>();

            for (final ArrayList bean : massnBeans) {
                final String plan = toName(bean);

                if (!planungsabschnitte.contains(plan)) {
                    final String gup = String.valueOf(bean.get(2));

                    planungsabschnitte.add(plan);
                    if (!gups.contains(gup)) {
                        gups.add(gup);
                    }
                }
            }

            liGup.setModel(new CidsBeanModel(gups));
            liPlan.setModel(new CidsBeanModel(planungsabschnitte));
            tabMassn.setModel(new MassnTableModel(massnBeans));
            initialised = true;
        } catch (Exception e) {
            LOG.error("Error while trying to receive the underlying data.", e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  the name of the planungsabschnitt object that is assigned to the given bean
     */
    private static String toName(final ArrayList bean) {
        return String.valueOf(bean.get(3)) + " " + String.valueOf(bean.get(0)) + " - " + String.valueOf(bean.get(1));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  the name of the planungsabschnitt object that is assigned to the given bean
     */
    private static String toName(final CidsBean bean) {
        final String routenname = String.valueOf(bean.getProperty("linie.von.route.routenname"));
        final String von = String.valueOf(bean.getProperty("linie.von.wert"));
        final String bis = String.valueOf(bean.getProperty("linie.bis.wert"));

        return routenname + " " + von + " - " + bis;
    }

    /**
     * DOCUMENT ME!
     */
    public void setReadOnly() {
        RendererTools.makeReadOnly(txtBezeichnung);
        RendererTools.makeReadOnly(teBemerkungHinweise);
    }

    @Override
    public void dispose() {
        initialised = false;
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Los";
    }

    @Override
    public void setTitle(final String title) {
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        if (event.getStatus() == EditorSaveStatus.SAVE_SUCCESS) {
            // saves the references between the gup_unterhaltungsmassnahme objects and the los object
            if (massnToAdd.size() > 0) {
                new Thread(new Runnable() {

                        @Override
                        public void run() {
                            int n = 0;
                            final int deps = massnToAdd.size();
                            final ProgressMonitor pm = new ProgressMonitor(
                                    StaticSwingTools.getParentFrame(GupLosEditor.this),
                                    "Speichere Abhängigkeiten zu "
                                            + deps
                                            + " Maßnahmen",
                                    "Speichere Abhängigkeit 1 von "
                                            + deps,
                                    0,
                                    deps
                                            - 1);
                            pm.setMillisToDecideToPopup(100);
                            pm.setMillisToPopup(200);

                            while (massnToAdd.size() > 0) {
                                final CidsBean bean = massnToAdd.get(0);
                                try {
                                    bean.setProperty("los", event.getSavedBean());
                                    bean.persist();
                                    massnToAdd.remove(0);
                                    pm.setProgress(++n);
                                    pm.setNote("Speichere Abhängigkeit " + (n + 1) + " von " + deps);
                                } catch (Exception e) {
                                    LOG.error("Error while saving a los object.", e);
                                }
                            }
                            pm.close();
                            EventQueue.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        // the new referenced objects should be shown on the navigator tree
                                        refreshTree();
                                    }
                                });
                        }
                    }).start();
            }
        }
    }

    /**
     * refreshs the navigator tree.
     */
    private void refreshTree() {
        if (treePath != null) {
            try {
                final MetaCatalogueTree metaCatalogueTree = ComponentRegistry.getRegistry().getCatalogueTree();
                metaCatalogueTree.refreshTreePath(treePath);
            } catch (Exception e) {
                LOG.error("Error when refreshing Tree", e); // NOI18N
            }
        }
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }

    /**
     * add a new gup_unterhaltungsmassnahme object to the los.
     *
     * @param  bean  the object of the type gup_unterhaltungsmassnahme that should be added
     */
    private void addMassnahme(final CidsBean bean) {
        if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Gup_unterhaltungsmassnahme")) {
            if (((MassnTableModel)tabMassn.getModel()).add(bean)) {
                massnToAdd.add(bean);
                cidsBean.setArtificialChangeFlag(true);

                if (!planungsabschnitte.contains(toName((CidsBean)bean.getProperty("planungsabschnitt")))) {
                    ((CidsBeanModel)liPlan.getModel()).add(toName(bean));
                    final Object gupName = bean.getProperty("planungsabschnitt.gup.name");

                    if (!gups.contains(String.valueOf(gupName))) {
                        ((CidsBeanModel)liGup.getModel()).add(String.valueOf(gupName));
                    }
                }
            }
        }
    }

    /**
     * only for test purposes.
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "x",
            "gup_los",
            1,
            1280,
            1024);
    }

    @Override
    public JComponent getTitleComponent() {
        return panTitle;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * This list shows the planungsabschnitte objects.
     *
     * @version  $Revision$, $Date$
     */
    private class PlanungsabschnittList extends JList implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            if (readOnly || !initialised) {
                // ignore the drop action
                return;
            }

            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Gup_planungsabschnitt")) {
                    final String beanName = toName(bean);
                    final String von = String.valueOf(bean.getProperty("linie.von.wert"));
                    final String bis = String.valueOf(bean.getProperty("linie.bis.wert"));
                    final String gup = String.valueOf(bean.getProperty("gup.name"));
                    final String name = String.valueOf(bean.getProperty("name"));

                    if (!planungsabschnitte.contains(beanName)) {
                        ((CidsBeanModel)liPlan.getModel()).add(beanName);

                        if (!gups.contains(gup)) {
                            ((CidsBeanModel)liGup.getModel()).add(gup);
                        }
                    }
                    final List<CidsBean> massnBeans = CidsBeanSupport.getBeanCollectionFromProperty(bean, "massnahmen");

                    if (massnBeans != null) {
                        for (final CidsBean tmp : massnBeans) {
                            if (((MassnTableModel)tabMassn.getModel()).add(tmp, name, gup, von, bis)) {
                                massnToAdd.add(tmp);
                                cidsBean.setArtificialChangeFlag(true);
                            }
                        }
                    }
                }
            }

            ((MassnTableModel)tabMassn.getModel()).fireTableChanged();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class MassnahmenTable extends JTable implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            if (readOnly || !initialised) {
                // ignore the drop action
                return;
            }

            for (final CidsBean bean : beans) {
                addMassnahme(bean);
            }
            ((MassnTableModel)tabMassn.getModel()).fireTableChanged();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class TabScrollPane extends JScrollPane implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            if (readOnly || !initialised) {
                // ignore the drop action
                return;
            }

            for (final CidsBean bean : beans) {
                addMassnahme(bean);
            }
            ((MassnTableModel)tabMassn.getModel()).fireTableChanged();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class PlanungsabschnittListCellRenderer extends DefaultListCellRenderer {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getListCellRendererComponent(final JList list,
                final Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            final Component result = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if ((result instanceof JLabel) && (value instanceof CidsBean)) {
                final CidsBean bean = (CidsBean)value;
                final String routenname = String.valueOf(bean.getProperty("linie.von.route.routenname"));
                final String von = String.valueOf(bean.getProperty("linie.von.wert"));
                final String bis = String.valueOf(bean.getProperty("linie.bis.wert"));

                final String text = routenname + " " + von + " - " + bis;
                ((JLabel)result).setText(text);
            }

            return result;
        }
    }

    /**
     * A List Model for String values.
     *
     * @version  $Revision$, $Date$
     */
    private class CidsBeanModel implements ListModel {

        //~ Instance fields ----------------------------------------------------

        private List<String> titles;
        private List<ListDataListener> listener = new ArrayList<ListDataListener>();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CidsBeanModel object.
         *
         * @param  beans  DOCUMENT ME!
         */
        public CidsBeanModel(final List<String> beans) {
            this.titles = beans;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int getSize() {
            return titles.size();
        }

        @Override
        public Object getElementAt(final int index) {
            return titles.get(index);
        }

        @Override
        public void addListDataListener(final ListDataListener l) {
            listener.add(l);
        }

        @Override
        public void removeListDataListener(final ListDataListener l) {
            listener.remove(l);
        }

        /**
         * DOCUMENT ME!
         */
        private void fireIntervalAdded() {
            final ListDataEvent e = new ListDataEvent(
                    this,
                    ListDataEvent.INTERVAL_ADDED,
                    titles.size()
                            - 1,
                    titles.size()
                            - 1);

            for (final ListDataListener tmp : listener) {
                tmp.intervalAdded(e);
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param  title  bean DOCUMENT ME!
         */
        public void add(final String title) {
            titles.add(title);

            fireIntervalAdded();
        }
    }

    /**
     * Handles cidsBeans of the type gup_unterhaltungsmassnahme and ArrayLists, that containes information about
     * massnahmen.
     *
     * @version  $Revision$, $Date$
     */
    private static class MassnTableModel implements TableModel {

        //~ Static fields/initializers -----------------------------------------

        private static final String[] columns = {
                "GUP",
                "Planungsabschnitt",
                "Maßn.Typ",
                "Kompartiment",
                "von",
                "bis",
                "Attr.1",
                "Wert Attr.1",
                "Attr.2",
                "Wert Attr.2",
                "Attr.3",
                "Wert Attr.3"
            };
        private static final String[] additionalAttribs = {
                "Randstreifenbreite",
                "Böschungsbreite",
                "Böschungslänge",
                "Deichkronenbreite",
                "Sohlbreite",
                "Vorlandbreite"
            };

        //~ Instance fields ----------------------------------------------------

        private ArrayList<ArrayList> beans;
        private List<TableModelListener> listener = new ArrayList<TableModelListener>();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new MassnTableModel object.
         *
         * @param  beans  DOCUMENT ME!
         */
        public MassnTableModel(final ArrayList<ArrayList> beans) {
            this.beans = beans;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            return beans.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            return columns[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            final ArrayList bean = beans.get(rowIndex);

            switch (columnIndex) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5: {
                    return String.valueOf(bean.get(columnIndex + 2));
                }
                case 6: {
                    return getAttribute(bean, 1, false);
                }
                case 7: {
                    return getAttribute(bean, 1, true);
                }
                case 8: {
                    return getAttribute(bean, 2, false);
                }
                case 9: {
                    return getAttribute(bean, 2, true);
                }
                case 10: {
                    return getAttribute(bean, 3, false);
                }
                case 11: {
                    return getAttribute(bean, 3, true);
                }
            }

            return "";
        }

        /**
         * Returns the optional attributes.
         *
         * @param   bean    the object, the attribute should be determined from
         * @param   number  The number of the attribute
         * @param   value   true, if the value of the attribute should be determined. Otherwise the name of the
         *                  attribute is determined.
         *
         * @return  the numberth optional attribute of the given object
         */
        private String getAttribute(final ArrayList bean, final int number, final boolean value) {
            final int FIRST_ATTRIB = 8;
            int currentNumber = 0;

            for (int i = FIRST_ATTRIB; i < bean.size(); ++i) {
                final Object tmpVal = bean.get(i);
                if (tmpVal != null) {
                    if (++currentNumber == number) {
                        if (value) {
                            return String.valueOf(tmpVal);
                        } else {
                            return additionalAttribs[i - FIRST_ATTRIB];
                        }
                    }
                }
            }

            return "";
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void addTableModelListener(final TableModelListener l) {
            listener.add(l);
        }

        @Override
        public void removeTableModelListener(final TableModelListener l) {
            listener.remove(l);
        }

        /**
         * DOCUMENT ME!
         */
        public void fireTableChanged() {
            final TableModelEvent e = new TableModelEvent(this);
            for (final TableModelListener tmp : listener) {
                tmp.tableChanged(e);
            }
        }

        /**
         * Adds the given bean of the type gup_unterhaltungsmassnahme.
         *
         * @param   massnBean  a cids bean of the type gup_unterhaltungsmassnahme
         *
         * @return  true, iff the bean was added. The bean is only added, if it is not already contained
         */
        private boolean add(final CidsBean massnBean) {
            final String von = String.valueOf(massnBean.getProperty("planungsabschnitt.linie.von.wert"));
            final String bis = String.valueOf(massnBean.getProperty("planungsabschnitt.linie.bis.wert"));
            final String gup = String.valueOf(massnBean.getProperty("planungsabschnitt.gup.name"));
            final String name = String.valueOf(massnBean.getProperty("planungsabschnitt.name"));

            return add(massnBean, name, gup, von, bis);
        }

        /**
         * DOCUMENT ME!
         *
         * @param   massnBean  DOCUMENT ME!
         * @param   name       the name of the corresponding planungsabschnitt
         * @param   gup        the name of the corresponding gup
         * @param   von        the first station value of the corresponding planungsabschnitt
         * @param   bis        the second station value of the corresponding planungsabschnitt
         *
         * @return  true, iff the object was added. The object is only added, if it is not already contained
         */
        private boolean add(final CidsBean massnBean,
                final String name,
                final String gup,
                final String von,
                final String bis) {
            final ArrayList newBean = new ArrayList();

            newBean.add(von);
            newBean.add(bis);
            newBean.add(gup);
            newBean.add(name);
            newBean.add(massnBean.getProperty("massnahme.name"));
            newBean.add(massnBean.getProperty("wo.ort"));
            newBean.add(massnBean.getProperty("linie.von.wert"));
            newBean.add(massnBean.getProperty("linie.bis.wert"));
            newBean.add(massnBean.getProperty("randstreifenbreite"));
            newBean.add(massnBean.getProperty("boeschungsbreite"));
            newBean.add(massnBean.getProperty("boeschungslaenge"));
            newBean.add(massnBean.getProperty("deichkronenbreite"));
            newBean.add(massnBean.getProperty("sohlbreite"));
            newBean.add(massnBean.getProperty("vorlandbreite"));

            if (!beans.contains(newBean)) {
                beans.add(newBean);
                return true;
            }

            return false;
        }
    }

    /**
     * Loads the contained gup_unterhaltungsmassnahme objects.
     *
     * @version  $Revision$, $Date$
     */
    private static class MassnCalculator implements Calculator<List, ArrayList<ArrayList>> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  containes the los id
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public ArrayList<ArrayList> calculate(final List input) throws Exception {
            final CidsServerSearch search = new MassnahmenSearch((String)input.get(0));
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);

            return (ArrayList<ArrayList>)res;
        }
    }
}
