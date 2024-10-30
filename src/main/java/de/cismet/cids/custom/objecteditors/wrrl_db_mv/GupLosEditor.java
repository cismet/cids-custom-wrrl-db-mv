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

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.reports.LosReport;
import de.cismet.cids.custom.wrrl_db_mv.server.search.MassnahmenSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.ExpressionEvaluator;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.GaebDownload;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.CalculationCache;
import de.cismet.tools.Calculator;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.TitleComponentProvider;
import de.cismet.tools.gui.WaitingDialogThread;
import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

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
    private static final int UNUSED_ATTRIBUTE_FIELDS = 10;

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
    public static final int STUNDEN = 16;
    public static final int SCHNITTTIEFE = 17;
    public static final int TEILLAENGE = 18;
    public static final int QM = 19;
    public static final int CBM = 20;
    public static final int ARBEITSBREITE = 21;
    public static final int MASSNAHMENART_ID = 22;
    public static final int LEISTUNGSTEXT = 23;
    public static final int AUFMASS_REGEL = 24;
    public static final int EINHEIT = 25;
    public static final int UM_ID = 26;
    public static final int PL_ID = 27;
    public static final int GUP_ID = 28;
    public static final int GWK = 29;
    public static final int MASSNAHMEN_ID = 30;
    public static final int BEAN = 31;
    public static final int GROUP_MASSN_TYP = 0;
    public static final int GROUP_TEILSTUECKE = 1;
    public static final int GROUP_LAENGE = 2;
    public static final int GROUP_AUFMASS = 3;
    public static final int GROUP_MASSEINHEIT = 4;
    public static final int GROUP_ID = 5;
    public static final int GROUP_LEISTUNGSTEXT = 6;
    public static final int GROUP_AUFMASSREGEL = 7;
    public static final int GROUP_EINHEIT = 8;
    public static final int GROUP_MASSN_ID = 9;

    public static final String[] ADDITIONAL_ATTRIBUTES = {
            "Randstreifenbreite",
            "Böschungsneigung",
            "Böschungslänge",
            "Deichkronenbreite",
            "Sohlbreite",
            "Vorlandbreite",
            "m³/m",
            "Stück",
            "Stunden",
            "Schnitttiefe",
            "teillaenge",
            "m²",
            "m³",
            "arbeitsbreite"
        };
    public static final String[] ADDITIONAL_ATTRIBUTE_MEASURES = {
            "m",
            "1/",
            "m",
            "m",
            "m",
            "m",
            "m³",
            "",
            "h",
            "m",
            "m",
            "m²",
            "m³",
            "m"
        };
    private static ExpressionEvaluator eval = new ExpressionEvaluator();

    //~ Enums ------------------------------------------------------------------

    /**
     * To choose the type of the additional attribute.
     *
     * @version  $Revision$, $Date$
     */
    public enum FieldKind {

        //~ Enum constants -----------------------------------------------------

        name, value, measure
    }

    //~ Instance fields --------------------------------------------------------

    boolean isNew = false;

    private List<String> gups;
    private List<String> planungsabschnitte;

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private boolean initialised = false;
//    private TreePath treePath;

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
        liPlan.addKeyListener(new java.awt.event.KeyAdapter() {

                @Override
                public void keyPressed(final java.awt.event.KeyEvent evt) {
                    liPlanKeyPressed(evt);
                }
            });
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

        liGup.addKeyListener(new java.awt.event.KeyAdapter() {

                @Override
                public void keyPressed(final java.awt.event.KeyEvent evt) {
                    liGupKeyPressed(evt);
                }
                @Override
                public void keyTyped(final java.awt.event.KeyEvent evt) {
                    liGupKeyTyped(evt);
                }
            });
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
        tabMassn.addKeyListener(new java.awt.event.KeyAdapter() {

                @Override
                public void keyPressed(final java.awt.event.KeyEvent evt) {
                    tabMassnKeyPressed(evt);
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
        try {
            if (DownloadManagerDialog.getInstance().showAskingForUserTitleDialog(this)) {
                final String jobname = DownloadManagerDialog.getInstance().getJobName();
                final String extension = ".xls";
                final String filename = cidsBean.toString();

                DownloadManager.instance()
                        .add(new LosReport(
                                createKumMassnList(false),
                                ((MassnTableModel)tabMassn.getModel()).getBeans(),
                                jobname,
                                filename,
                                filename,
                                extension));
            }
        } catch (Exception e) {
            LOG.error("Error while creating report.", e);
        }
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
            if (DownloadManagerDialog.getInstance().showAskingForUserTitleDialog(this)) {
                final String jobname = DownloadManagerDialog.getInstance().getJobName();
                final String extension = ".x81";
                final String filename = cidsBean.toString();

                DownloadManager.instance()
                        .add(new GaebDownload(
                                createKumMassnList(false),
                                cidsBean,
                                jobname,
                                "Leistungsverzeichnis - "
                                + filename,
                                filename,
                                extension));
            }
        } catch (Exception e) {
            LOG.error("Error while creating gaeb file.", e);
        }
    } //GEN-LAST:event_jButton1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void liPlanKeyPressed(final java.awt.event.KeyEvent evt) { //GEN-FIRST:event_liPlanKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_DELETE) {
            evt.consume();

            if (!readOnly && (liPlan.getModel() instanceof CidsBeanModel)) {
                final CidsBeanModel model = (CidsBeanModel)liPlan.getModel();
                final int[] rows = liPlan.getSelectedIndices();
                final MassnTableModel massnModel = (MassnTableModel)tabMassn.getModel();
                final List<Integer> beanIds = new ArrayList<Integer>();

                for (final int row : rows) {
                    final Integer bean = (Integer)model.getElementIdAt(row);
                    beanIds.add(bean);
                }

                for (final Integer id : beanIds) {
                    massnModel.removePlanungsabschnitt(id);
                    model.removeElement(id);
                }
            }
        }
    } //GEN-LAST:event_liPlanKeyPressed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void liGupKeyTyped(final java.awt.event.KeyEvent evt) { //GEN-FIRST:event_liGupKeyTyped
        // TODO add your handling code here:
    } //GEN-LAST:event_liGupKeyTyped

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void liGupKeyPressed(final java.awt.event.KeyEvent evt) { //GEN-FIRST:event_liGupKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_DELETE) {
            evt.consume();

            if (!readOnly && (liGup.getModel() instanceof CidsBeanModel)) {
                final CidsBeanModel model = (CidsBeanModel)liGup.getModel();
                final int[] rows = liGup.getSelectedIndices();
                final MassnTableModel massnModel = (MassnTableModel)tabMassn.getModel();
                final List<Integer> beanIds = new ArrayList<Integer>();

                for (final int row : rows) {
                    final Integer bean = (Integer)model.getElementIdAt(row);
                    beanIds.add(bean);
                }

                for (final Integer id : beanIds) {
                    massnModel.removeGup(id);
                    model.removeElement(id);
                }
            }
        }
    } //GEN-LAST:event_liGupKeyPressed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tabMassnKeyPressed(final java.awt.event.KeyEvent evt) { //GEN-FIRST:event_tabMassnKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_DELETE) {
            evt.consume();

            if (!readOnly && (tabMassn.getModel() instanceof MassnTableModel)) {
                final int[] rows = tabMassn.getSelectedRows();

                final MassnTableModel model = (MassnTableModel)tabMassn.getModel();
                model.removeRows(rows);
            }
        }
    } //GEN-LAST:event_tabMassnKeyPressed

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
//            treePath = ComponentRegistry.getRegistry().getCatalogueTree().getSelectionPath();
//
//            if (treePath != null) {
//                treePath = treePath.getParentPath();
//            }

            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        loadData();
                    }
                });
        }
    }

    /**
     * This method creates an incomplete list. this list should not be used in the MassnTableModel
     *
     * @param   massnBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static ArrayList convertToList(final CidsBean massnBean) {
        final String von = "";   // String.valueOf(massnBean.getProperty("planungsabschnitt.linie.von.wert"));
        final String bis = "";   // String.valueOf(massnBean.getProperty("planungsabschnitt.linie.bis.wert"));
        final String gup = "";   // String.valueOf(massnBean.getProperty("planungsabschnitt.gup.name"));
        final String name = "";  // String.valueOf(massnBean.getProperty("planungsabschnitt.name"));
        final Integer umId = (Integer)massnBean.getProperty("id");
        final Integer plId = 0;  // (Integer)massnBean.getProperty("planungsabschnitt.id");
        final Integer gupId = 0; // (Integer)massnBean.getProperty("planungsabschnitt.gup.id");
        final String aufmassRegel = getAufmassRegel(massnBean);
        final String einheit = getEinheit(massnBean);
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
        newBean.add(massnBean.getProperty("boeschungsbreite")); // Boeschungsneigung
        newBean.add(massnBean.getProperty("boeschungslaenge"));
        newBean.add(massnBean.getProperty("deichkronenbreite"));
        newBean.add(massnBean.getProperty("sohlbreite"));
        newBean.add(massnBean.getProperty("vorlandbreite"));
        newBean.add(massnBean.getProperty("cbmprom"));
        newBean.add(massnBean.getProperty("stueck"));
        newBean.add(massnBean.getProperty("stunden"));
        newBean.add(massnBean.getProperty("schnitttiefe"));
        newBean.add(massnBean.getProperty("teillaenge"));
        newBean.add(massnBean.getProperty("m_zwei"));
        newBean.add(massnBean.getProperty("m_drei"));
        newBean.add(massnBean.getProperty("arbeitsbreite"));
        newBean.add(massnBean.getProperty("massnahme.id"));
        newBean.add(massnBean.getProperty("massnahme.leistungstext"));
        newBean.add(aufmassRegel);
        newBean.add(einheit);
        newBean.add(umId);                                      // unterhaltungsmassnahme id
        newBean.add(plId);                                      // unterhaltungsmassnahme id
        newBean.add(gupId);                                     // unterhaltungsmassnahme id
        newBean.add(massnBean);

        return newBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String getAufmassRegel(final CidsBean bean) {
        String aufmassRegel = (String)bean.getProperty("massnahme.aufmass_regel");

        if ((aufmassRegel == null) || aufmassRegel.equals("")) {
            aufmassRegel = (String)bean.getProperty("massnahme.gewerk.aufmass_regel");
        }

        return aufmassRegel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String getEinheit(final CidsBean bean) {
        String einheit = (String)bean.getProperty("massnahme.einheit");

        if ((einheit == null) || einheit.equals("")) {
            einheit = (String)bean.getProperty("massnahme.gewerk.einheit");
        }

        return einheit;
    }

    /**
     * loads the contained objects from the server.
     */
    private void loadData() {
        final WaitingDialogThread<ArrayList<ArrayList>> t = new WaitingDialogThread<ArrayList<ArrayList>>(
                StaticSwingTools.getParentFrame(
                    GupLosEditor.this),
                true,
                "Lade betroffene Maßnahmen",
                null,
                200) {

                @Override
                protected ArrayList<ArrayList> doInBackground() throws Exception {
                    final List<CidsBean> beans = cidsBean.getBeanCollectionProperty("massnahmen");

                    if ((beans != null) && (beans.size() > 0)) {
                        final List in = new ArrayList(1);
                        in.add(cidsBean.getProperty("id").toString());
                        return (ArrayList<ArrayList>)massnCache.calcValue(in);
                    } else {
                        return new ArrayList<ArrayList>();
                    }
                }

                @Override
                protected void done() {
                    try {
                        final ArrayList<ArrayList> massnBeans = get();
                        gups = new ArrayList<String>();
                        planungsabschnitte = new ArrayList<String>();
                        final List<Integer> planungsabschnitteId = new ArrayList<Integer>();
                        final List<Integer> gupId = new ArrayList<Integer>();
                        final List<CidsBean> beans = cidsBean.getBeanCollectionProperty("massnahmen");

                        for (final ArrayList bean : massnBeans) {
                            final String plan = toName(bean);
                            final Integer id = (Integer)bean.get(UM_ID);

                            for (final CidsBean tmp : beans) {
                                if (tmp.getProperty("id").equals(id) && (bean.size() == BEAN)) {
                                    bean.add(tmp);
                                    break;
                                }
                            }

                            if (!planungsabschnitte.contains(plan)) {
                                final String gup = String.valueOf(bean.get(GUP_NAME));

                                planungsabschnitte.add(plan);
                                planungsabschnitteId.add((Integer)bean.get(PL_ID));
                                if (!gups.contains(gup)) {
                                    gups.add(gup);
                                    gupId.add((Integer)bean.get(GUP_ID));
                                }
                            }
                        }

                        liGup.setModel(new CidsBeanModel(gups, gupId));
                        liPlan.setModel(new CidsBeanModel(planungsabschnitte, planungsabschnitteId));
                        tabMassn.setModel(new MassnTableModel(massnBeans));
                        fillKumTable();
                        tabMassnKum.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                                @Override
                                public void valueChanged(final ListSelectionEvent e) {
                                    final int[] row = tabMassnKum.getSelectedRows();
                                    final String[] filters = new String[row.length];
                                    int index = 0;

                                    for (final int tmp : row) {
                                        final Object filter = ((MassnKumTableModel)tabMassnKum.getModel()).getValueAt(
                                                tmp,
                                                0);
                                        filters[index++] = (String)filter;
                                    }

                                    ((MassnTableModel)tabMassn.getModel()).setFilter(filters);
                                }
                            });
                        initialised = true;
                        jButton1.setEnabled(true);
                    } catch (Exception e) {
                        LOG.error("Error while trying to receive the underlying data.", e);
                    }
                }
            };

        t.start();
    }

    /**
     * DOCUMENT ME!
     */
    private void fillKumTable() {
        tabMassnKum.setModel(new MassnKumTableModel(createKumMassnList(true)));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   showError  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private ArrayList<ArrayList> createKumMassnList(final boolean showError) {
        final ArrayList<ArrayList> beans = ((MassnTableModel)tabMassn.getModel()).getBeans();
        final ArrayList<ArrayList> groups = new ArrayList<ArrayList>();
        boolean error = false;

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
                group.add(bean.get(MASSNAHMEN_ID));      // Massn-ID
                groups.add(group);
            }

            group.set(1, ((Integer)group.get(1)) + 1);
            group.set(2, (Double)group.get(2) + calculateLength(bean));

            final String expression = (String)bean.get(AUFMASS_REGEL);

            try {
                final Double a = calcMenge(bean, expression);

                if (a != null) {
                    group.set(3, (Double)group.get(3) + a);
                } else {
                    if (!error && showError) {
                        error = true;
                        if (bean.get(MASSNAHMENART_ID) == null) {
                            JOptionPane.showMessageDialog(
                                GupLosEditor.this,
                                "Maßnahme wurde nicht gesetzt. Die angezeigten Werte sind nicht korrekt",
                                "Fehler!",
                                JOptionPane.ERROR_MESSAGE);
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
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    GupLosEditor.this,
                    e.getMessage(),
                    "Fehler!",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        Collections.sort(groups, new Comparator<ArrayList>() {

                @Override
                public int compare(final ArrayList o1, final ArrayList o2) {
                    if (((o1 != null) && (o1.get(0) != null)) && ((o2 != null) && (o2.get(0) != null))) {
                        return ((String)o1.get(0)).compareTo((String)o2.get(0));
                    } else if (((o1 == null) || (o1.get(0) == null)) && ((o2 == null) || (o2.get(0) == null))) {
                        return 0;
                    } else if ((o1 == null) || (o1.get(0) == null)) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });

        return groups;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean        DOCUMENT ME!
     * @param   expression  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static Double calcMenge(final ArrayList bean, String expression) throws Exception {
        if (expression != null) {
            expression = replaceVariablesFromExpression(expression, bean);

            if (!eval.isValidExpression(expression)) {
                final String text = "Ungültige Formel zur Berechnung des Aufmaßes der Maßnahme: "
                            + bean.get(MASSNAHMENART_NAME)
                            + " Formel: "
                            + bean.get(AUFMASS_REGEL)
                            + ". Die angezeigten Werte sind nicht korrekt";
                throw new Exception(text);
            }

            return eval.eval(expression);
        }

        return null;
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
            for (int i = 0; i < 22; ++i) {
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
        expression = expression.replace((CharSequence)"stunden",
                (CharSequence)replaceNull(valueList.get(STUNDEN), 0.0).toString());
        expression = expression.replace((CharSequence)"schnitttiefe",
                (CharSequence)replaceNull(valueList.get(SCHNITTTIEFE), 0.0).toString());
        expression = expression.replace((CharSequence)"teillänge",
                (CharSequence)replaceNull(valueList.get(TEILLAENGE), 0.0).toString());
        expression = expression.replace((CharSequence)"qm",
                (CharSequence)replaceNull(valueList.get(QM), 0.0).toString());
        expression = expression.replace((CharSequence)"cbm",
                (CharSequence)replaceNull(valueList.get(CBM), 0.0).toString());
        expression = expression.replace((CharSequence)"arbeitsbreite",
                (CharSequence)replaceNull(valueList.get(ARBEITSBREITE), 0.0).toString());
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

    /**
     * DOCUMENT ME!
     */
    private void refreshPlanungsabschnitte() {
        final CidsBeanModel model = (CidsBeanModel)liPlan.getModel();
        final MassnTableModel massnModel = (MassnTableModel)tabMassn.getModel();
        final List<Integer> obsoleteObjects = new ArrayList<Integer>();

        for (int i = 0; i < model.getSize(); ++i) {
            obsoleteObjects.add(model.getElementIdAt(i));
        }

        for (final ArrayList bean : massnModel.getBeans()) {
            final Integer id = (Integer)bean.get(PL_ID);
            obsoleteObjects.remove(id);
        }

        for (final Integer id : obsoleteObjects) {
            model.removeElement(id);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshGup() {
        final CidsBeanModel model = (CidsBeanModel)liGup.getModel();
        final MassnTableModel massnModel = (MassnTableModel)tabMassn.getModel();
        final List<Integer> obsoleteObjects = new ArrayList<Integer>();

        for (int i = 0; i < model.getSize(); ++i) {
            obsoleteObjects.add(model.getElementIdAt(i));
        }

        for (final ArrayList bean : massnModel.getBeans()) {
            final Integer id = (Integer)bean.get(GUP_ID);
            obsoleteObjects.remove(id);
        }

        for (final Integer id : obsoleteObjects) {
            model.removeElement(id);
        }
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
//        EventQueue.invokeLater(new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        UIUtil.refreshTree(treePath);
//                    }
//                }));
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
                cidsBean.getBeanCollectionProperty("massnahmen").add(bean);
                final String planName = toName((CidsBean)bean.getProperty("planungsabschnitt"));

                if (!planungsabschnitte.contains(planName)) {
                    final Integer planId = (Integer)bean.getProperty("planungsabschnitt.id");
                    ((CidsBeanModel)liPlan.getModel()).add(planName, planId);
                    final Object gupName = bean.getProperty("planungsabschnitt.gup.name");

                    if (!gups.contains(String.valueOf(gupName))) {
                        final Integer gupId = (Integer)bean.getProperty("planungsabschnitt.gup.id");
                        ((CidsBeanModel)liGup.getModel()).add(String.valueOf(gupName), gupId);
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

    /**
     * Returns the optional attributes.
     *
     * @param   bean    the object, the attribute should be determined from
     * @param   number  The number of the attribute
     * @param   value   true, if the value of the attribute should be determined. Otherwise the name of the attribute is
     *                  determined.
     *
     * @return  the numberth optional attribute of the given object
     */
    public static Object getAttribute(final ArrayList bean, final int number, final FieldKind value) {
        final int FIRST_ATTRIB = 8;
        int currentNumber = 0;

        for (int i = FIRST_ATTRIB; i < (bean.size() - UNUSED_ATTRIBUTE_FIELDS); ++i) {
            final Object tmpVal = bean.get(i);
            if (tmpVal != null) {
                if (++currentNumber == number) {
                    switch (value) {
                        case value: {
                            if (tmpVal instanceof Integer) {
                                final Integer intVal = (Integer)tmpVal;

                                return intVal.doubleValue();
                            } else {
                                return tmpVal;
                            }
                        }
                        case name: {
                            return ADDITIONAL_ATTRIBUTES[i - FIRST_ATTRIB];
                        }
                        case measure: {
                            return ADDITIONAL_ATTRIBUTE_MEASURES[i - FIRST_ATTRIB];
                        }
                    }
                }
            }
        }

        return null;
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
                    final WaitingDialogThread<Void> t = new WaitingDialogThread<Void>(StaticSwingTools.getParentFrame(
                                GupLosEditor.this),
                            true,
                            "Füge Planungsabschnitt hinzu",
                            null,
                            200) {

                            @Override
                            public Void doInBackground() throws Exception {
                                final String beanName = toName(bean);
                                final String von = String.valueOf(bean.getProperty("linie.von.wert"));
                                final String bis = String.valueOf(bean.getProperty("linie.bis.wert"));
                                final Long route = (Long)bean.getProperty("linie.von.route.gwk");
                                final String gup = String.valueOf(bean.getProperty("gup.name"));
                                final Integer gupId = (Integer)bean.getProperty("gup.id");
                                final Integer plId = (Integer)bean.getProperty("id");
                                final String name = String.valueOf(bean.getProperty("name"));

                                if (!planungsabschnitte.contains(beanName)) {
                                    ((CidsBeanModel)liPlan.getModel()).add(beanName, plId);

                                    if (!gups.contains(gup)) {
                                        ((CidsBeanModel)liGup.getModel()).add(gup, gupId);
                                    }
                                }
                                final List<CidsBean> massnBeans = CidsBeanSupport.getBeanCollectionFromProperty(
                                        bean,
                                        "massnahmen");
                                final ObjectTreeNode losNode = (ObjectTreeNode)node.getParent().getParent();
                                // Wenn der Planungsabschnitt im Baum unter einem Los haengt, dann sollen nur die
                                // Massnahmen hinzugefuegt werden, die auch dem Los zugeordnet sind
                                Integer losId = null;
                                List<CidsBean> massnCollection = null;

                                if ((losNode != null)
                                            && losNode.getMetaObject().getBean().getClass().getName().equals(
                                                "de.cismet.cids.dynamics.Gup_los")) {
                                    final CidsBean losBean = losNode.getMetaObject().getBean();
                                    losId = (Integer)losBean.getProperty("id");
                                    massnCollection = CidsBeanSupport.getBeanCollectionFromProperty(
                                            losBean,
                                            "massnahmen");
                                }

                                if (massnBeans != null) {
                                    for (final CidsBean tmp : massnBeans) {
                                        if ((losId == null)
                                                    || ((massnCollection != null)
                                                        && massnCollection.contains(tmp))) {
                                            if (((MassnTableModel)tabMassn.getModel()).add(
                                                            tmp,
                                                            name,
                                                            gup,
                                                            von,
                                                            bis,
                                                            plId,
                                                            gupId,
                                                            (Integer)tmp.getProperty("id"),
                                                            route)) {
                                                cidsBean.getBeanCollectionProperty("massnahmen").add(tmp);
                                            }
                                        }
                                    }
                                }

                                return null;
                            }
                        };

                    t.start();
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
            try {
                fillKumTable();
            } catch (Exception e) {
                LOG.error("Error", e);
            }
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
        private List<Integer> ids;
        private List<ListDataListener> listener = new ArrayList<ListDataListener>();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CidsBeanModel object.
         *
         * @param  beans  DOCUMENT ME!
         * @param  ids    DOCUMENT ME!
         */
        public CidsBeanModel(final List<String> beans, final List<Integer> ids) {
            this.titles = beans;
            this.ids = ids;
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

        /**
         * DOCUMENT ME!
         *
         * @param   index  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public Integer getElementIdAt(final int index) {
            return ids.get(index);
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
        private void fireContentsChanged() {
            final ListDataEvent e = new ListDataEvent(
                    this,
                    ListDataEvent.CONTENTS_CHANGED,
                    0,
                    0);

            for (final ListDataListener tmp : listener) {
                tmp.contentsChanged(e);
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param  title  bean DOCUMENT ME!
         * @param  id     DOCUMENT ME!
         */
        public void add(final String title, final Integer id) {
            titles.add(title);
            ids.add(id);

            fireContentsChanged();
        }

        /**
         * DOCUMENT ME!
         *
         * @param  id  DOCUMENT ME!
         */
        public void removeElement(final Integer id) {
            final int index = ids.indexOf(id);
            if (index >= 0) {
                ids.remove(index);
                titles.remove(index);
            }

            fireContentsChanged();
        }
    }

    /**
     * Handles cidsBeans of the type gup_unterhaltungsmassnahme and ArrayLists, that containes information about
     * massnahmen.
     *
     * @version  $Revision$, $Date$
     */
    private class MassnTableModel implements TableModel {

        //~ Instance fields ----------------------------------------------------

        private final String[] columns = {
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

        private ArrayList<ArrayList> beans;
        private List<TableModelListener> listener = new ArrayList<TableModelListener>();
        private String[] filter = {};

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
                if (isContainedInFilter(massn)) {
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
                    return getAttribute(bean, 1, FieldKind.name);
                }
                case 7: {
                    return getAttribute(bean, 1, FieldKind.value);
                }
                case 8: {
                    return getAttribute(bean, 2, FieldKind.name);
                }
                case 9: {
                    return getAttribute(bean, 2, FieldKind.value);
                }
                case 10: {
                    return getAttribute(bean, 3, FieldKind.name);
                }
                case 11: {
                    return getAttribute(bean, 3, FieldKind.value);
                }
            }

            return "";
        }

        /**
         * DOCUMENT ME!
         *
         * @param   cond  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private boolean isContainedInFilter(final String cond) {
            for (final String tmp : filter) {
                if (tmp.equals(cond)) {
                    return true;
                }
            }

            return false;
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
                if (isContainedInFilter(massn)) {
                    if (count == index) {
                        return tmp;
                    }
                    ++count;
                }
            }

            return null;
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
        public void setFilter(final String[] filter) {
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
            final Long route = (Long)massnBean.getProperty("planungsabschnitt.linie.von.route.gwk");
            final String gup = String.valueOf(massnBean.getProperty("planungsabschnitt.gup.name"));
            final String name = String.valueOf(massnBean.getProperty("planungsabschnitt.name"));
            final Integer umId = (Integer)massnBean.getProperty("id");
            final Integer plId = (Integer)massnBean.getProperty("planungsabschnitt.id");
            final Integer gupId = (Integer)massnBean.getProperty("planungsabschnitt.gup.id");

            return add(massnBean, name, gup, von, bis, plId, gupId, umId, route);
        }

        /**
         * DOCUMENT ME!
         *
         * @param   massnBean  DOCUMENT ME!
         * @param   name       the name of the corresponding planungsabschnitt
         * @param   gup        the name of the corresponding gup
         * @param   von        the first station value of the corresponding planungsabschnitt
         * @param   bis        the second station value of the corresponding planungsabschnitt
         * @param   plId       DOCUMENT ME!
         * @param   gupId      DOCUMENT ME!
         * @param   umId       DOCUMENT ME!
         * @param   route      DOCUMENT ME!
         *
         * @return  true, iff the object was added. The object is only added, if it is not already contained
         */
        private boolean add(final CidsBean massnBean,
                final String name,
                final String gup,
                final String von,
                final String bis,
                final Integer plId,
                final Integer gupId,
                final Integer umId,
                final Long route) {
            final String aufmassRegel = getAufmassRegel(massnBean);
            final String einheit = getEinheit(massnBean);
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
            newBean.add(massnBean.getProperty("boeschungsbreite"));        // Boeschungsneigung
            newBean.add(massnBean.getProperty("boeschungslaenge"));
            newBean.add(massnBean.getProperty("deichkronenbreite"));
            newBean.add(massnBean.getProperty("sohlbreite"));
            newBean.add(massnBean.getProperty("vorlandbreite"));
            newBean.add(massnBean.getProperty("cbmprom"));
            newBean.add(massnBean.getProperty("stueck"));
            newBean.add(massnBean.getProperty("stunden"));
            newBean.add(massnBean.getProperty("schnitttiefe"));
            newBean.add(massnBean.getProperty("teillaenge"));
            newBean.add(massnBean.getProperty("m_zwei"));
            newBean.add(massnBean.getProperty("m_drei"));
            newBean.add(massnBean.getProperty("arbeitsbreite"));
            newBean.add(massnBean.getProperty("massnahme.id"));
            newBean.add(massnBean.getProperty("massnahme.leistungstext"));
            newBean.add(aufmassRegel);
            newBean.add(einheit);
            newBean.add(umId);                                             // unterhaltungsmassnahme id
            newBean.add(plId);                                             // planungseinheit id
            newBean.add(gupId);                                            // gup id
            newBean.add(route);                                            // gwk
            newBean.add(massnBean.getProperty("massnahme.massnahmen_id")); // massnahmenid
            newBean.add(massnBean);

            if (!containsBean(beans, newBean)) {
                beans.add(newBean);

                return true;
            }

            return false;
        }

        /**
         * DOCUMENT ME!
         *
         * @param   beans  DOCUMENT ME!
         * @param   bean   DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private boolean containsBean(final ArrayList<ArrayList> beans, final ArrayList bean) {
            if (bean != null) {
                for (final ArrayList tmp : beans) {
                    if (tmp.get(UM_ID).equals(bean.get(UM_ID))) {
                        return true;
                    }
                }
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

        /**
         * DOCUMENT ME!
         *
         * @param  rows  DOCUMENT ME!
         */
        public void removeRows(final int[] rows) {
            final int selectedRow = tabMassnKum.getSelectedRow();

            for (final int tmp : rows) {
                cidsBean.getBeanCollectionProperty("massnahmen").remove((CidsBean)getRow(tmp).get(BEAN));
            }

            // remove the rows from the data array
            Arrays.sort(rows);
            for (int index = rows.length - 1; index >= 0; --index) {
                // beans.remove(index) will not work, because the filter is not considered, if you use the row number
                // as index
                beans.remove(getRow(index));
            }

            cidsBean.setArtificialChangeFlag(true);
            fireTableChanged();
            fillKumTable();
            refreshPlanungsabschnitte();
            refreshGup();

            if ((selectedRow != -1) && (selectedRow < tabMassnKum.getModel().getRowCount())) {
                tabMassnKum.setRowSelectionInterval(selectedRow, selectedRow);
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param  planId  DOCUMENT ME!
         */
        public void removePlanungsabschnitt(final Integer planId) {
            final List<ArrayList> tmpToRemove = new ArrayList<ArrayList>();

            for (final ArrayList bean : beans) {
                if ((bean.get(PL_ID) != null) && bean.get(PL_ID).equals(planId)) {
                    final CidsBean beanToRemove = null;
                    final CidsBean cbean = (CidsBean)bean.get(BEAN);
                    cidsBean.getBeanCollectionProperty("massnahmen").remove(cbean);

                    tmpToRemove.add(bean);
                }
            }

            beans.removeAll(tmpToRemove);

            cidsBean.setArtificialChangeFlag(true);
            fireTableChanged();
            fillKumTable();
            refreshGup();
        }

        /**
         * DOCUMENT ME!
         *
         * @param  gupId  planId DOCUMENT ME!
         */
        public void removeGup(final Integer gupId) {
            final List<ArrayList> tmpToRemove = new ArrayList<ArrayList>();

            for (final ArrayList bean : beans) {
                if ((bean.get(GUP_ID) != null) && bean.get(GUP_ID).equals(gupId)) {
                    final CidsBean cbean = (CidsBean)bean.get(BEAN);

                    cidsBean.getBeanCollectionProperty("massnahmen").remove(cbean);

                    tmpToRemove.add(bean);
                }
            }

            beans.removeAll(tmpToRemove);

            cidsBean.setArtificialChangeFlag(true);
            fireTableChanged();
            fillKumTable();
            refreshPlanungsabschnitte();
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
