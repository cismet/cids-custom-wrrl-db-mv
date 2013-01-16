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
import Sirius.navigator.types.treenode.DefaultMetaTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.tree.MetaCatalogueTree;

import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.*;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.server.search.MassnahmenGaebSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.MassnahmenSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.ExpressionEvaluator;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.GaebExport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.gaeb.types.GaebContainer;
import de.cismet.cids.gaeb.types.GaebLvItem;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

import de.cismet.cids.server.search.AbstractCidsServerSearch;
import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.BrowserLauncher;
import de.cismet.tools.CalculationCache;
import de.cismet.tools.Calculator;
import de.cismet.tools.CismetThreadPool;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.TitleComponentProvider;
import de.cismet.tools.gui.WaitDialog;

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

    // The massnahmen objects are stored in ArrayLists due to performance reasons.
    // The fields have the following values
    public static final int PLANUNGSABSCHNITT_VON = 0;
    public static final int PLANUNGSABSCHNITT_BIS = 1;
    public static final int GUP_NAME = 2;
    public static final int PLANUNGSABSCHNITT_NAME = 3;
    public static final int MASSNAHMENART_NAME = 4;
    public static final int MASSNAHMENBEZUG = 5;
    public static final int MASSNAHME_VON = 6;
    public static final int MASSNAHME_BIS = 7;
    public static final int RANDSTREIFENBREITE = 8;
    public static final int BOESCHUNGSNEIGUNG = 9;
    public static final int BOESCHUNGSLAENGE = 10;
    public static final int DEICHKRONENBREITE = 11;
    public static final int SOHLBREITE = 12;
    public static final int VORLANDBREITE = 13;
    public static final int CBMPROM = 14;
    public static final int STUECK = 15;
    public static final int MASSNAHMENART_ID = 16;
    public static final int LEISTUNGSTEXT = 17;
    public static final int AUFMASS_REGEL = 18;
    public static final int EINHEIT = 19;

    //~ Instance fields --------------------------------------------------------

    boolean isNew = false;

    private List<String> gups;
    private List<String> planungsabschnitte;
    private List<CidsBean> massnToAdd = new ArrayList<CidsBean>();
    private ExpressionEvaluator eval = new ExpressionEvaluator();

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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jsGupList;
    private javax.swing.JScrollPane jsMassnahmenabschnittList;
    private javax.swing.JLabel lblBemerkungen;
    private javax.swing.JLabel lblBezeichnung;
    private javax.swing.JLabel lblGups;
    private javax.swing.JLabel lblMassnahmen;
    private javax.swing.JLabel lblMassnahmenKum;
    private javax.swing.JLabel lblMassnahmenabschnitte;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList liGup;
    private javax.swing.JList liPlan;
    private javax.swing.JPanel panTitle;
    private javax.swing.JTable tabMassn;
    private javax.swing.JTable tabMassnKum;
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
                new DropTarget(liPlan, (DropTargetListener)liPlan);
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
        jScrollPane3 = new TabScrollPane();
        tabMassnKum = new MassnahmenTable();
        lblMassnahmenKum = new javax.swing.JLabel();

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
        jButton1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
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
        gridBagConstraints.gridy = 10;
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
        gridBagConstraints.gridy = 9;
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

        jScrollPane3.setOpaque(false);

        tabMassnKum.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    tabMassnKumMouseClicked(evt);
                }
            });
        jScrollPane3.setViewportView(tabMassnKum);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        add(jScrollPane3, gridBagConstraints);

        lblMassnahmenKum.setText(org.openide.util.NbBundle.getMessage(
                GupLosEditor.class,
                "GupLosEditor.lblMassnahmenKum.text")); // NOI18N
        lblMassnahmenKum.setMaximumSize(new java.awt.Dimension(230, 17));
        lblMassnahmenKum.setMinimumSize(new java.awt.Dimension(230, 17));
        lblMassnahmenKum.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        add(lblMassnahmenKum, gridBagConstraints);

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

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tabMassnKumMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_tabMassnKumMouseClicked
        // TODO add your handling code here:
    } //GEN-LAST:event_tabMassnKumMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jButton1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
        try {
            final WaitDialog wd = new WaitDialog(
                    StaticSwingTools.getParentFrame(GupLosEditor.this),
                    true,
                    "Erstelle Export-Datei",
                    null);

            CismetThreadPool.execute(new SwingWorker<Boolean, Object>() {

                    private File file;

                    @Override
                    protected Boolean doInBackground() throws Exception {
                        try {
                            final CidsServerSearch search = new MassnahmenGaebSearch(
                                    cidsBean.getProperty("id").toString());
//                            final ArrayList<ArrayList> massnBeans = (ArrayList<ArrayList>)SessionManager
//                                        .getConnection().customServerSearch(SessionManager.getSession().getUser(), search);
                            final GaebExport ex = new GaebExport();
                            final GaebContainer c = new GaebContainer();

                            final List<GaebLvItem> l = new ArrayList<GaebLvItem>();
                            final ArrayList<ArrayList> massnBeans = createKumMassnList();
                            int i = 0;

                            if (massnBeans != null) {
                                for (final ArrayList al : massnBeans) {
                                    final GaebLvItem item = new GaebLvItem();
                                    item.setId(((Integer)al.get(5)).toString());
                                    item.setAmount(new BigDecimal((Double)al.get(3)));
                                    item.setMeasure(String.valueOf(al.get(4)));
                                    item.setDescription(String.valueOf(al.get(6)));
                                    item.setDescriptionShort(String.valueOf(al.get(0)));
                                    item.setOz(String.valueOf(++i));
                                    l.add(item);

//                                    final GaebLvItem item = new GaebLvItem();
//                                    item.setId(((Integer)al.get(0)).toString());
//                                    item.setAmount(new BigDecimal((Double)al.get(1)));
//                                    item.setMeasure(String.valueOf(al.get(2)));
//                                    item.setDescription(String.valueOf(al.get(3)));
//                                    item.setDescriptionShort(String.valueOf(al.get(4)));
//                                    l.add(item);
                                }
                            }
                            c.setItemList(l);
                            c.setProjectName(String.valueOf(cidsBean.getProperty("bezeichnung")));
                            c.setDescription(String.valueOf(cidsBean.getProperty("bemerkung")));
                            c.setNebenangebot(true);
                            c.setBieterKommentar(true);
                            final String result = ex.startExport(c);
                            file = new File(
                                    CismapBroker.getInstance().getCismapFolderPath(),
                                    cidsBean.toString()
                                            + ".x81");
                            int n = 1;
                            while (file.exists()) {
                                file = new File(
                                        CismapBroker.getInstance().getCismapFolderPath(),
                                        cidsBean.toString()
                                                + "_"
                                                + (n++)
                                                + ".x81");
                            }

                            final FileWriter fw = new FileWriter(
                                    file);
                            final BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(result);
                            bw.close();

                            return true;
                        } finally {
                            while (!wd.isVisible()) {
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    // nothing to do
                                }
                            }
                            wd.setVisible(false);
                        }
                    }

                    @Override
                    protected void done() {
                        boolean error = false;
                        try {
                            error = !get();
                        } catch (InterruptedException ex) {
                            // unterbrochen, nichts tun
                        } catch (ExecutionException ex) {
                            error = true;
                            LOG.error("error while generating gaeb export", ex);
                        }

                        if (error) {
                            JOptionPane.showMessageDialog(
                                GupLosEditor.this,
                                "Beim Generieren des Gaeb Exports ist ein Fehler aufgetreten.",
                                "Fehler!",
                                JOptionPane.ERROR_MESSAGE);
                        } else {
                            try {
                                BrowserLauncher.openURL("file:///" + file.getAbsolutePath());
                            } catch (Exception e) {
                                LOG.error("Cannot open generated gaeb file in browser", e);
                            }
                        }
                    }
                });

            StaticSwingTools.showDialog(wd);
        } catch (Exception e) {
            LOG.error("Error while creating gaeb file.", e);
        }
    } //GEN-LAST:event_jButton1ActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        jButton1.setEnabled(false);

        if (cidsBean != null) {
            treePath = ComponentRegistry.getRegistry().getCatalogueTree().getSelectionPath();

            if (treePath != null) {
                treePath = treePath.getParentPath();
            }

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
            fillKumTable();
            tabMassnKum.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                    @Override
                    public void valueChanged(final ListSelectionEvent e) {
                        final int row = tabMassnKum.getSelectedRow();
                        final Object filter = ((MassnKumTableModel)tabMassnKum.getModel()).getValueAt(row, 0);
                        ((MassnTableModel)tabMassn.getModel()).setFilter((String)filter);
                    }
                });
            initialised = true;
            jButton1.setEnabled(true);
        } catch (Exception e) {
            LOG.error("Error while trying to receive the underlying data.", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void fillKumTable() {
        tabMassnKum.setModel(new MassnKumTableModel(createKumMassnList()));
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private ArrayList<ArrayList> createKumMassnList() {
        final ArrayList<ArrayList> beans = ((MassnTableModel)tabMassn.getModel()).getBeans();
        final ArrayList<ArrayList> groups = new ArrayList<ArrayList>();

        for (final ArrayList bean : beans) {
            ArrayList group = null;
            for (final ArrayList tmp : groups) {
                if ((tmp.get(0) == bean.get(MASSNAHMENART_NAME))
                            || ((tmp.get(0) != null) && tmp.get(0).equals(bean.get(MASSNAHMENART_NAME)))) {
                    group = tmp;
                    break;
                }
            }

            if (group == null) {
                Integer id = (Integer)bean.get(MASSNAHMENART_ID);
                if (id == null) {
                    id = 1;
                }
                group = new ArrayList();
                group.add(bean.get(MASSNAHMENART_NAME)); // Massn.Typ
                group.add(0);                            // Teilstücke
                group.add(0.0);                          // laenge
                group.add(0.0);                          // Aufmass
                group.add(bean.get(EINHEIT));            // Masseinheit
                group.add(id);                           // ID
                group.add(bean.get(LEISTUNGSTEXT));      // Beschreibung
                group.add(bean.get(AUFMASS_REGEL));      // Aufmassregel
                group.add(bean.get(EINHEIT));            // Einheit
                groups.add(group);
            }

            group.set(1, ((Integer)group.get(1)) + 1);
            group.set(2, (Double)group.get(2) + calculateLength(bean));

            String expression = (String)bean.get(AUFMASS_REGEL);

            if (expression != null) {
                expression = replaceVariablesFromExpression(expression, bean);

                if (!eval.isValidExpression(expression)) {
                    JOptionPane.showMessageDialog(
                        GupLosEditor.this,
                        "Ungültige Formel zur Berechnung des Aufmaßes der Maßnahme: "
                                + bean.get(MASSNAHMENART_NAME)
                                + " Formel: "
                                + bean.get(AUFMASS_REGEL)
                                + ". Die angezeigten Werte sind nicht korrekt",
                        "Fehler!",
                        JOptionPane.ERROR_MESSAGE);
                    break;
                }

                final Double a = eval.eval(expression);

                if (a != null) {
                    group.set(3, (Double)group.get(3) + a);
                } else {
                    JOptionPane.showMessageDialog(
                        GupLosEditor.this,
                        "Ungültige Formel zur Berechnung des Aufmaßes der Maßnahme: "
                                + bean.get(MASSNAHMENART_NAME)
                                + " Formel: "
                                + expression
                                + ". Die angezeigten Werte sind nicht korrekt",
                        "Fehler!",
                        JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        }

        return groups;
    }

    /**
     * Replaces all variables of the given expression with the values from the valueList (for filling, see the static
     * index constants).
     *
     * @param   ex         the expression
     * @param   valueList  the list with the values or null, if default values should be used
     *
     * @return  the new expression
     */
    public static String replaceVariablesFromExpression(final String ex, ArrayList valueList) {
        if (valueList == null) {
            valueList = new ArrayList();
            for (int i = 0; i < 16; ++i) {
                valueList.add(2);
            }
            valueList.set(MASSNAHME_VON, 1);
        }

        String expression = ex.toLowerCase();

        expression = expression.replace((CharSequence)"böschungslänge",
                (CharSequence)replaceNull(valueList.get(BOESCHUNGSLAENGE), 0.0).toString());
        expression = expression.replace((CharSequence)"böschungsneigung",
                (CharSequence)replaceNull(valueList.get(BOESCHUNGSNEIGUNG), 0.0).toString());
        expression = expression.replace((CharSequence)"deichkronenbreite",
                (CharSequence)replaceNull(valueList.get(DEICHKRONENBREITE), 0.0).toString());
        expression = expression.replace((CharSequence)"randstreifenbreite",
                (CharSequence)replaceNull(valueList.get(RANDSTREIFENBREITE), 0.0).toString());
        expression = expression.replace((CharSequence)"sohlbreite",
                (CharSequence)replaceNull(valueList.get(SOHLBREITE), 0.0).toString());
        expression = expression.replace((CharSequence)"vorlandbreite",
                (CharSequence)replaceNull(valueList.get(VORLANDBREITE), 0.0).toString());
        expression = expression.replace((CharSequence)"cbmprom",
                (CharSequence)replaceNull(valueList.get(CBMPROM), 0.0).toString());
        expression = expression.replace((CharSequence)"stück",
                (CharSequence)replaceNull(valueList.get(STUECK), 0.0).toString());
        expression = expression.replace((CharSequence)"von",
                (CharSequence)replaceNull(valueList.get(MASSNAHME_VON), 0.0).toString());
        expression = expression.replace((CharSequence)"bis",
                (CharSequence)replaceNull(valueList.get(MASSNAHME_BIS), 0.0).toString());

        return expression;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   o            DOCUMENT ME!
     * @param   replacement  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static Object replaceNull(final Object o, final Object replacement) {
        return ((o == null) ? replacement : o);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private double calculateLength(final ArrayList bean) {
        final double von = (Double)bean.get(MASSNAHME_VON);
        final double bis = (Double)bean.get(MASSNAHME_BIS);

        return bis - von;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  the name of the planungsabschnitt object that is assigned to the given bean
     */
    private static String toName(final ArrayList bean) {
        return String.valueOf(bean.get(PLANUNGSABSCHNITT_NAME)) + " " + String.valueOf(bean.get(PLANUNGSABSCHNITT_VON))
                    + " - " + String.valueOf(bean.get(PLANUNGSABSCHNITT_BIS));
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
                final int deps = massnToAdd.size();
                final WaitDialog wd = new WaitDialog(
                        StaticSwingTools.getParentFrame(GupLosEditor.this),
                        true,
                        "Speichere Abhängigkeit 1 von "
                                + deps,
                        null);

                new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                int n = 0;
                                synchronized (wd.getTreeLock()) {
                                    wd.setMax(deps);
                                }
                                while (massnToAdd.size() > 0) {
                                    final CidsBean bean = massnToAdd.get(0);
                                    try {
                                        // the bean can be a object of the type gup_unterhaltungsmassnahmen
                                        // or gup_planungsabschnitt. Both have a property with the name los
                                        bean.setProperty("los", event.getSavedBean());
                                        bean.persist();
                                        massnToAdd.remove(0);
                                        synchronized (wd.getTreeLock()) {
                                            wd.setProgress(++n);
                                            wd.setText("Speichere Abhängigkeit " + (n + 1) + " von " + deps);
                                        }
                                    } catch (Exception e) {
                                        LOG.error("Error while saving a los object.", e);
                                    }
                                }
                            } catch (Exception e) {
                                LOG.error("Error while saving a los object.", e);
                            } finally {
                                waitUntilVisible();
                                wd.setVisible(false);
                                wd.dispose();
                            }
                        }

                        private void waitUntilVisible() {
                            while (!wd.isVisible()) {
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    // nothing to do
                                }
                            }
                        }
                    }).start();

                StaticSwingTools.showDialog(wd);
            }

            EventQueue.invokeLater(new Thread(new Runnable() {

                        @Override
                        public void run() {
                            UIUtil.refreshTree(treePath);
                        }
                    }));
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
                final String planName = toName((CidsBean)bean.getProperty("planungsabschnitt"));

                if (!planungsabschnitte.contains(planName)) {
                    ((CidsBeanModel)liPlan.getModel()).add(planName);
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
    private class PlanungsabschnittList extends JList implements DropTargetListener {

        //~ Instance fields ----------------------------------------------------

        DataFlavor fromNavigatorNode = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class="
                        + DefaultMetaTreeNode.class.getName(),
                "a DefaultMetaTreeNode");                                                                 // NOI18N
        DataFlavor fromNavigatorCollection = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class="
                        + java.util.Collection.class.getName(),
                "a java.util.Collection of Sirius.navigator.types.treenode.DefaultMetaTreeNode objects"); // NOI18N

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param  beans  DOCUMENT ME!
         */
        public void beansDropped(final ArrayList<ObjectTreeNode> beans) {
            if (readOnly || !initialised) {
                // ignore the drop action
                return;
            }

            for (final ObjectTreeNode node : beans) {
                final CidsBean bean = node.getMetaObject().getBean();

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
                    final ObjectTreeNode losNode = (ObjectTreeNode)node.getParent().getParent();
                    // Wenn der Planungsabschnitt im Baum unter einem Los haengt, dann sollen nur die Massnahmen
                    // hinzugefuegt werden, die auch dem Los zugeordnet sind
                    Integer losId = null;

                    if ((losNode != null)
                                && losNode.getMetaObject().getBean().getClass().getName().equals(
                                    "de.cismet.cids.dynamics.Gup_los")) {
                        losId = (Integer)losNode.getMetaObject().getBean().getProperty("id");
                    }

                    if (massnBeans != null) {
                        for (final CidsBean tmp : massnBeans) {
                            if ((losId == null)
                                        || ((tmp.getProperty("los.id") != null)
                                            && tmp.getProperty("los.id").equals(losId))) {
                                if (((MassnTableModel)tabMassn.getModel()).add(tmp, name, gup, von, bis)) {
                                    massnToAdd.add(tmp);
                                }
                            }
                        }
                    }
                    massnToAdd.add(bean);
                    cidsBean.setArtificialChangeFlag(true);
                }
            }

            ((MassnTableModel)tabMassn.getModel()).fireTableChanged();
            fillKumTable();
        }

        @Override
        public void dragEnter(final DropTargetDragEvent dtde) {
        }

        @Override
        public void dragOver(final DropTargetDragEvent dtde) {
        }

        @Override
        public void dropActionChanged(final DropTargetDragEvent dtde) {
        }

        @Override
        public void dragExit(final DropTargetEvent dte) {
        }

        @Override
        public void drop(final DropTargetDropEvent dtde) {
            try {
                final ArrayList<ObjectTreeNode> beans = new ArrayList<ObjectTreeNode>();
                if (dtde.getTransferable().isDataFlavorSupported(fromNavigatorNode)
                            && dtde.getTransferable().isDataFlavorSupported(fromNavigatorCollection)) {
                    try {
                        final Object object = dtde.getTransferable().getTransferData(fromNavigatorCollection);
                        if (object instanceof Collection) {
                            final Collection c = (Collection)object;
                            for (final Object o : c) {
                                if (o instanceof ObjectTreeNode) {
                                    beans.add((ObjectTreeNode)o);
                                }
                            }
                        }
                    } catch (Throwable t) {
                        LOG.fatal("Drop Problems occurred", t); // NOI18N
                    }
                } else {
                    LOG.fatal("Wrong transferable");            // NOI18N
                }
                // ruft baensDropped mit den Knoten auf
                beansDropped(beans);
            } catch (Throwable ups) {
                LOG.error("Problem during the DnD Opertaion", ups); // NOI18N
            }
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
            fillKumTable();
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
            fillKumTable();
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

        public static final int UNUSED_ATTRIBUTE_FIELDS = 4;

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
                "Böschungsneigung",
                "Böschungslänge",
                "Deichkronenbreite",
                "Sohlbreite",
                "Vorlandbreite",
                "m³/m",
                "Stück"
            };

        //~ Instance fields ----------------------------------------------------

        private ArrayList<ArrayList> beans;
        private List<TableModelListener> listener = new ArrayList<TableModelListener>();
        private String filter = "";

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
            int count = 0;

            for (final ArrayList bean : beans) {
                final String massn = String.valueOf(bean.get(4));
                if (massn.equals(filter)) {
                    ++count;
                }
            }

            return count;
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
            final ArrayList bean = getRow(rowIndex);

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
         * DOCUMENT ME!
         *
         * @param   index  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private ArrayList getRow(final int index) {
            int count = 0;

            for (final ArrayList tmp : beans) {
                final String massn = String.valueOf(tmp.get(4));
                if (massn.equals(filter)) {
                    if (count == index) {
                        return tmp;
                    }
                    ++count;
                }
            }

            return null;
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

            for (int i = FIRST_ATTRIB; i < (bean.size() - UNUSED_ATTRIBUTE_FIELDS); ++i) {
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
         *
         * @param  filter  DOCUMENT ME!
         */
        public void setFilter(final String filter) {
            this.filter = filter;
            fireTableChanged();
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
            String aufmassRegel = (String)massnBean.getProperty("massnahme.aufmass_regel");
            String einheit = (String)massnBean.getProperty("massnahme.einheit");
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
            newBean.add(massnBean.getProperty("boeschungsneigung"));
            newBean.add(massnBean.getProperty("boeschungslaenge"));
            newBean.add(massnBean.getProperty("deichkronenbreite"));
            newBean.add(massnBean.getProperty("sohlbreite"));
            newBean.add(massnBean.getProperty("vorlandbreite"));
            newBean.add(massnBean.getProperty("cbmprom"));
            newBean.add(massnBean.getProperty("stueck"));
            newBean.add(massnBean.getProperty("massnahme.id"));
            newBean.add(massnBean.getProperty("massnahme.leistungstext"));
            if ((aufmassRegel == null) || aufmassRegel.equals("")) {
                aufmassRegel = (String)massnBean.getProperty("massnahme.gewerk.aufmass_regel");
            }

            if ((einheit == null) || einheit.equals("")) {
                einheit = (String)massnBean.getProperty("massnahme.gewerk.einheit");
            }
            newBean.add(aufmassRegel);
            newBean.add(einheit);

            if (!beans.contains(newBean)) {
                beans.add(newBean);
                return true;
            }

            return false;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public ArrayList<ArrayList> getBeans() {
            return beans;
        }
    }

    /**
     * Handles cidsBeans of the type gup_unterhaltungsmassnahme and ArrayLists, that containes information about
     * massnahmen.
     *
     * @version  $Revision$, $Date$
     */
    private static class MassnKumTableModel implements TableModel {

        //~ Static fields/initializers -----------------------------------------

        private static final String[] columns = {
                "Maßn.Typ",
                "Teilstücke",
                "Länge",
                "Aufmaß"
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
        public MassnKumTableModel(final ArrayList<ArrayList> beans) {
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
                case 3: {
                    return String.valueOf(bean.get(columnIndex));
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
