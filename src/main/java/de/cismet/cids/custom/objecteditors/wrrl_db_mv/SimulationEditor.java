/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * WkFgEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.Navigator;
import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.plugin.PluginDescriptor;
import Sirius.navigator.plugin.PluginRegistry;
import Sirius.navigator.plugin.PluginUIDescriptor;
import Sirius.navigator.types.treenode.DefaultMetaTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.LayoutedContainer;
import Sirius.navigator.ui.MutableConstraints;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;

import org.apache.log4j.Logger;

import org.openide.util.NbBundle;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.InputStream;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.tree.TreeNode;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.SimSimulationsabschnittEditor.SimulationResultChangedEvent;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.Calc;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.FgskSimulationHelper;
import de.cismet.cids.custom.wrrl_db_mv.fgsksimulation.FgskSimCalc;
import de.cismet.cids.custom.wrrl_db_mv.server.search.AllObjectsSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.ComparableCidsBean;
import de.cismet.cids.custom.wrrl_db_mv.util.ReadOnlyFgskBand;
import de.cismet.cids.custom.wrrl_db_mv.util.ReadOnlyFgskBandMember;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.WrrlMapOptionsPanel;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.*;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.AbstractCidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.CismapPlugin;

import de.cismet.commons.concurrency.CismetConcurrency;

import de.cismet.tools.CalculationCache;
import de.cismet.tools.Calculator;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.TitleComponentProvider;
import de.cismet.tools.gui.jbands.BandModelEvent;
import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandMemberSelectable;
import de.cismet.tools.gui.jbands.interfaces.BandModelListener;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class SimulationEditor extends JPanel implements CidsBeanRenderer,
    FooterComponentProvider,
    TitleComponentProvider,
    EditorSaveListener,
    SimSimulationsabschnittEditor.SimulationResultChangedListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final String WK_FG_CLASS_NAME = "de.cismet.cids.dynamics.Wk_fg";
    private static final Logger LOG = Logger.getLogger(SimulationEditor.class);
    private static final MetaClass MC_WK_FG = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");
    private static final MetaClass MC_MASSNAHMEN = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "massnahmen");
    private static final MetaClass MC_FGSK = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "fgsk_kartierabschnitt");
    private static final MetaClass MC_SIM_FLAECEHNERWERBSPREIS = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "sim_flaechenerwerbspreis");
    private static final CalculationCache<List, MetaObject[]> FGSK_CACHE = new CalculationCache<List, MetaObject[]>(
            new FgskCalculator());
    private static final CalculationCache<List, MetaObject[]> WK_FG_CACHE = new CalculationCache<List, MetaObject[]>(
            new WkfgCalculator());
    private static final CalculationCache<List, MetaObject[]> MASSNAHMEN_CACHE =
        new CalculationCache<List, MetaObject[]>(
            new MassnBvpCalculator());
    public static final CalculationCache<String, List<MetaObject>> FL_COSTS_CACHE =
        new CalculationCache<String, List<MetaObject>>(
            new FlCostsCalculator());
    private static final String HOME = System.getProperty("user.home");    // NOI18N
    private static final String FS = System.getProperty("file.separator"); // NOI18N
    private static final String CISMAP_BACKUP_LAYOUT = HOME + FS + ".cismap" + FS + "backup.layout";
    private static final String NAVIGATOR_BACKUP_LAYOUT = Navigator.NAVIGATOR_HOME + "navigatorBackup.layout";
    private static boolean layoutRestored = true;

    static {
        FGSK_CACHE.setTimeToCacheResults(30000);
        WK_FG_CACHE.setTimeToCacheResults(30000);
        FL_COSTS_CACHE.setTimeToCacheResults(3000000L);

        final Thread t = new Thread("load costs") {

                @Override
                public void run() {
                    try {
                        SimulationEditor.FL_COSTS_CACHE.calcValue("1");
                    } catch (Exception e) {
                        LOG.error("error while loading costs", e);
                    }
                }
            };

        t.start();
    }

    //~ Instance fields --------------------------------------------------------

    private boolean warningAlreadyShown = false;

    private ReadOnlyFgskBand[] fgskBands;
    private WKBand wkband;
    private JBand[] jband;
    private BandModelListener[] modelListener;
    private SimpleBandModel[] sbm;
    private CidsBean cidsBean;
    private SimSimulationsabschnittEditor simulationsEditor;
    private boolean initialReadOnly = false;
    private boolean readOnly = false;
    private boolean isNew = false;
    private CidsBean wkFg;
    private MetaObject[] fgsks;
    private boolean cancel = false;
    private boolean namePrompt = false;
    private boolean selectionChangedHandlingInProgress = false;
    private boolean wkMassnListChangedByUser = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgZiel;
    private javax.swing.ButtonGroup bgrpDetails;
    private javax.swing.JButton butCancel;
    private javax.swing.JButton butOK;
    private javax.swing.JCheckBox cbKostenFix;
    private javax.swing.JDialog diaName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton jbRem;
    private javax.swing.JList jlMultiSimList;
    private javax.swing.JList jlWKMassnList;
    private javax.swing.JLabel labInfo;
    private javax.swing.JLabel labMassnProp;
    private javax.swing.JLabel labMultiSim;
    private javax.swing.JLabel lblBemerkungen;
    private javax.swing.JLabel lblBemerkungen1;
    private javax.swing.JLabel lblCosts4;
    private javax.swing.JLabel lblFlCosts;
    private javax.swing.JLabel lblFlKosten;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblKostenGes4;
    private javax.swing.JLabel lblMaCosts;
    private javax.swing.JLabel lblMaKosten;
    private javax.swing.JLabel lblMarker1;
    private javax.swing.JLabel lblMarker2;
    private javax.swing.JLabel lblMarker3;
    private javax.swing.JLabel lblMarker4;
    private javax.swing.JLabel lblMarker5;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNeCosts;
    private javax.swing.JLabel lblNeKosten;
    private javax.swing.JLabel lblNeu;
    private javax.swing.JLabel lblSoCosts;
    private javax.swing.JLabel lblSoKosten;
    private javax.swing.JLabel lblSubTitle1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleName;
    private javax.swing.JPanel panAllgemein;
    private javax.swing.JPanel panBand;
    private javax.swing.JPanel panClass1;
    private javax.swing.JPanel panClass2;
    private javax.swing.JPanel panClass3;
    private javax.swing.JPanel panClass4;
    private javax.swing.JPanel panClass5;
    private javax.swing.JPanel panClass6;
    private javax.swing.JPanel panEditorPanel;
    private javax.swing.JPanel panEmpty;
    private javax.swing.JPanel panFgsk;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private javax.swing.JPanel panHeader;
    private javax.swing.JPanel panHeaderInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panLoading;
    private javax.swing.JPanel panMain;
    private javax.swing.JPanel panMorphometer;
    private javax.swing.JPanel panMultiSim;
    private javax.swing.JPanel panNew;
    private javax.swing.JPanel panSim;
    private javax.swing.JPanel panSimCard;
    private javax.swing.JPanel panTitle;
    private javax.swing.JPanel panTotal1;
    private javax.swing.JPanel panVermessung;
    private de.cismet.tools.gui.RoundedPanel panWkInfo;
    private javax.swing.JRadioButton rbOekPot;
    private javax.swing.JRadioButton rbZust;
    private javax.swing.JSlider sldZoom;
    private javax.swing.JTextArea taBemerkungen;
    private javax.swing.JToggleButton togZoomToParts;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GupGewaesserabschnittEditor object.
     */
    public SimulationEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public SimulationEditor(final boolean readOnly) {
        this.initialReadOnly = readOnly;
        this.readOnly = readOnly;
        initComponents();

        panHeaderInfo.setVisible(false);
        jPanel1.setVisible(false);

        if (!readOnly) {
            new CidsBeanDropTarget(panNew);
            new CidsBeanDropTarget(jlMultiSimList);
        }
        simulationsEditor = new SimSimulationsabschnittEditor(readOnly);
        simulationsEditor.hideProposalButton();
        simulationsEditor.addSimulationResultChangedListener(this);

        switchToForm("empty");
        lblHeading.setText("FGSK Abschnitt");
        panSim.add(simulationsEditor, BorderLayout.CENTER);
        jlMultiSimList.setCellRenderer(new MultiMassnahmenConainerCellRenderer());

        sldZoom.setPaintTrack(false);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  readOnly  DOCUMENT ME!
     */
    private void setReadOnly(final boolean readOnly) {
        if (this.readOnly != readOnly) {
            this.readOnly = readOnly;
            simulationsEditor.setReadOnly(readOnly);

            if (readOnly) {
                RendererTools.makeReadOnly(txtName);
                RendererTools.makeReadOnly(taBemerkungen);
                RendererTools.makeReadOnly(cbKostenFix);
            } else {
                RendererTools.makeWritable(txtName);
                RendererTools.makeWritable(taBemerkungen);
                RendererTools.makeWritable(cbKostenFix);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void addBands() {
        final List<CidsBean> teile = wkFg.getBeanCollectionProperty("teile");
        int teileLength = 0;

        for (final CidsBean tmpTeil : teile) {
            if (hasFgsk(
                            (Integer)tmpTeil.getProperty("linie.von.route.id"),
                            (Double)tmpTeil.getProperty("linie.von.wert"),
                            (Double)tmpTeil.getProperty("linie.bis.wert"))) {
                ++teileLength;
            }
        }
        jband = new JBand[teileLength];
        sbm = new SimpleBandModel[teileLength];
        fgskBands = new ReadOnlyFgskBand[teileLength];
        modelListener = new RestriktionBandModelListener[teileLength];
        panBand.setLayout(new GridLayout(teileLength, 1));
        int internIndex = 0;

        for (int index = 0; index < teile.size(); ++index) {
            if (
                !hasFgsk(
                            (Integer)teile.get(index).getProperty("linie.von.route.id"),
                            (Double)teile.get(index).getProperty("linie.von.wert"),
                            (Double)teile.get(index).getProperty("linie.bis.wert"))) {
                continue;
            }
            sbm[internIndex] = new SimpleBandModel();
            final String gwk = String.valueOf(teile.get(index).getProperty("linie.von.route.gwk"));
            fgskBands[internIndex] = new ReadOnlyFgskBand(gwk);
            jband[internIndex] = new JBand(true);
            jband[internIndex].setSelectionMode(JBand.SelectionMode.MULTIPLE_INTERVAL_SELECTION);
            modelListener[internIndex] = new RestriktionBandModelListener(jband[internIndex]);

            sbm[internIndex].addBand(fgskBands[internIndex]);
            jband[internIndex].setModel(sbm[internIndex]);
            panBand.add(jband[internIndex], BorderLayout.CENTER);
            jband[internIndex].setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            sbm[internIndex].addBandModelListener(modelListener[internIndex]);
            ++internIndex;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id  DOCUMENT ME!
     */
    private void switchToForm(final String id) {
        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    final StringTokenizer st = new StringTokenizer(id, ">");

                    if (st.hasMoreTokens()) {
                        ((CardLayout)panEditorPanel.getLayout()).show(panEditorPanel, st.nextToken());
                    }

                    String secondToken = null;

                    if (st.hasMoreTokens()) {
                        secondToken = st.nextToken();
                        ((CardLayout)panSimCard.getLayout()).show(panSimCard, secondToken);
                    }

                    labMassnProp.setVisible((secondToken != null) && secondToken.equals("sim") && !readOnly);
                }
            };
        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            EventQueue.invokeLater(r);
        }
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
//        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        this.wkFg = null;

        switchToForm("empty");
        lblHeading.setText("");

        if (cidsBean != null) {
//            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
//                bindingGroup,
//                cidsBean);
//            bindingGroup.bind();
            isNew = cidsBean.getProperty("wk_key") == null;
            warningAlreadyShown = false;

            if (initialReadOnly
                        || ((cidsBean.getProperty("read_only") != null)
                            && ((Boolean)cidsBean.getProperty("read_only")))) {
                setReadOnly(true);
            } else {
                setReadOnly(false);
            }

            if (isNew) {
                ((CardLayout)panMain.getLayout()).show(panMain, "band");
                panBand.add(panNew, BorderLayout.CENTER);
                namePrompt = true;
                try {
                    cidsBean.setProperty("name", "Variante");
                    fillDialog();
                } catch (Exception e) {
                    LOG.error("Error while setting the name", e);
                }
                DefaultMetaTreeNode dmtn = (DefaultMetaTreeNode)ComponentRegistry.getRegistry().getCatalogueTree()
                            .getSelectedNode();

                if (dmtn == null) {
                    dmtn = (DefaultMetaTreeNode)ComponentRegistry.getRegistry().getSearchResultsTree()
                                .getSelectedNode();
                }

                if (dmtn != null) {
                    final TreeNode nodeTmp = dmtn.getParent();
                    if ((nodeTmp != null) && (nodeTmp instanceof ObjectTreeNode)) {
                        final ObjectTreeNode node = (ObjectTreeNode)nodeTmp;
                        final CidsBean tmpBean = node.getMetaObject().getBean();
                        if (tmpBean.getClass().getName().equals(WK_FG_CLASS_NAME)) {
                            wkFg = tmpBean;
                            ((CardLayout)panMain.getLayout()).show(panMain, "band");
                            try {
                                cidsBean.setProperty("wk_key", wkFg.getProperty("wk_k"));
                                fillDialog();
                                setNamesAndBands();
                                isNew = false;
                            } catch (Exception e) {
                                LOG.error("error while setting the wk_key property", e);
                            }
                        }
                    }
                }
            } else {
                lblTitle.setText(getTitle());
                setName();
                fillDialog();
                setNamesAndBands();
                refreshWkFgMassnList();
            }
        }

        if (!initialReadOnly && WrrlMapOptionsPanel.isChangeMapWindowActive()) {
            changeLayout();
        }
    }

    /**
     * Saves the current layout and move the map window to the left corner.
     */
    private void changeLayout() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    // Check, if the layout is already in simulation mode
                    if (layoutRestored) {
                        try {
                            final CismapPlugin cismap = (CismapPlugin)PluginRegistry.getRegistry().getPlugin("cismap");
                            final LayoutedContainer container = (LayoutedContainer)ComponentRegistry.getRegistry()
                                        .getGUIContainer();

                            if (LOG.isDebugEnabled()) {
                                LOG.debug("cismap backup file: " + CISMAP_BACKUP_LAYOUT);
                            }
                            cismap.saveLayout(CISMAP_BACKUP_LAYOUT);

                            if (LOG.isDebugEnabled()) {
                                LOG.debug("navigator backup file: " + NAVIGATOR_BACKUP_LAYOUT);
                            }
                            container.saveLayout(
                                NAVIGATOR_BACKUP_LAYOUT,
                                ComponentRegistry.getRegistry().getMainWindow());

                            layoutRestored = false;

                            final InputStream is = this.getClass()
                                        .getClassLoader()
                                        .getResourceAsStream(
                                            "de/cismet/cids/custom/objecteditors/wrrl_db_mv/fullscreenmap.layout");

                            cismap.loadLayout(is, false);

                            final PluginDescriptor cismapDescriptor = PluginRegistry.getRegistry()
                                        .getPluginDescriptor("cismap");
                            final PluginUIDescriptor uiDescriptor = cismapDescriptor.getUIDescriptor("cismap");

                            container.remove("cismap");
                            uiDescriptor.setPosition(MutableConstraints.P2);
                            container.add(uiDescriptor);

                            ComponentRegistry.getRegistry().showComponent(ComponentRegistry.ATTRIBUTE_EDITOR);
                        } catch (Exception e) {
                            LOG.error("Cannot cahnge the layout.", e);
                        }
                    }
                }
            });
    }

    /**
     * Restores the layout.
     */
    private void restoreLayout() {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (new File(CISMAP_BACKUP_LAYOUT).exists()) {
                            final CismapPlugin cismap = (CismapPlugin)PluginRegistry.getRegistry().getPlugin("cismap");
                            cismap.loadLayout(CISMAP_BACKUP_LAYOUT, false);
                        }

                        if (new File(NAVIGATOR_BACKUP_LAYOUT).exists()) {
                            final LayoutedContainer container = (LayoutedContainer)ComponentRegistry.getRegistry()
                                        .getGUIContainer();
                            container.loadLayout(
                                NAVIGATOR_BACKUP_LAYOUT,
                                false,
                                ComponentRegistry.getRegistry().getMainWindow());
                        }
                        layoutRestored = true;
                    } catch (Exception e) {
                        LOG.error("Cannot cahnge the layout.", e);
                    }
                }
            });
    }

    /**
     * Refreshs the massnahmen list of the water body.
     */
    private void refreshWkFgMassnList() {
        final TreeSet<ComparableCidsBean> massnSet = new TreeSet<ComparableCidsBean>();

        final List<CidsBean> massnAnwendungen = cidsBean.getBeanCollectionProperty(
                FgskSimulationHelper.SIMULATIONSMASSNAHMEN_PROPERTY);

        if (massnAnwendungen != null) {
            for (final CidsBean massnAnwendung : massnAnwendungen) {
                final CidsBean massnGruppe = FgskSimulationHelper.getSimMassnahmeGruppeById((Integer)
                        massnAnwendung.getProperty(FgskSimulationHelper.MASSNAHME_PROPERTY));
                final CidsBean einzelMassn = FgskSimulationHelper.getSimMassnahmeById((Integer)
                        massnAnwendung.getProperty(FgskSimulationHelper.EINZEL_MASSNAHME_PROPERTY));

                if (massnGruppe != null) {
                    final List<CidsBean> massnList = massnGruppe.getBeanCollectionProperty("massnahmen");

                    if (massnList != null) {
                        for (final CidsBean massn : massnList) {
                            massnSet.add(new ComparableCidsBean(massn));
                        }
                    }
                } else if (einzelMassn != null) {
                    massnSet.add(new ComparableCidsBean(einzelMassn));
                }
            }
        }

        final DefaultListModel<CidsBean> listModel = new DefaultListModel<CidsBean>();

        for (final ComparableCidsBean massn : massnSet) {
            listModel.addElement(massn.getBean());
        }
        jlWKMassnList.setModel(listModel);

        jlWKMassnList.setCellRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(final JList list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    String name = String.valueOf(value);

                    if ((value != null)
                                && value.getClass().getName().equals(FgskSimulationHelper.SIM_MASSNAHME_CLASS_NAME)) {
                        final CidsBean bean = (CidsBean)value;
                        name = bean.getProperty("key") + " - " + bean.toString();
                    }
                    return super.getListCellRendererComponent(list, name, index, isSelected, cellHasFocus);
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    private void fillDialog() {
        String name = (String)cidsBean.getProperty("name");
        String beschreibung = (String)cidsBean.getProperty("beschreibung");
        Boolean fixCosts = (Boolean)cidsBean.getProperty("kosten_fix");

        if (name == null) {
            name = "";
        }
        if (beschreibung == null) {
            beschreibung = "";
        }

        if (fixCosts == null) {
            fixCosts = false;
        }

        txtName.setText(name);
        taBemerkungen.setText(beschreibung);
        cbKostenFix.setSelected(fixCosts);
    }

    /**
     * DOCUMENT ME!
     */
    private void setName() {
        final String name = (String)cidsBean.getProperty("name");

        if (name != null) {
            lblTitleName.setText("Name: " + cidsBean.getProperty("name"));
            lblTitleName.setToolTipText("Beschreibung: " + cidsBean.getProperty("beschreibung"));
        } else {
            lblTitleName.setText("Name: ");
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setNamesAndBands() {
        final boolean checkForMassnahmen = isNew;
        ((CardLayout)panMain.getLayout()).show(panMain, "loading");

        final SwingWorker<MetaObject[], Void> waitingDialog = new SwingWorker<MetaObject[], Void>() {

                @Override
                protected MetaObject[] doInBackground() throws Exception {
                    final List wkfgIn = new ArrayList(1);
                    wkfgIn.add(cidsBean.getProperty("wk_key"));
                    final MetaObject[] mosWkFg = WK_FG_CACHE.calcValue(wkfgIn);

                    if ((mosWkFg != null) && (mosWkFg.length == 1)) {
                        wkFg = mosWkFg[0].getBean();
                        final List in = new ArrayList(1);
                        in.add(cidsBean.getProperty("wk_key"));
                        return FGSK_CACHE.calcValue(in);
                    }

                    return null;
                }

                @Override
                protected void done() {
                    try {
                        fgsks = get();
                        panBand.removeAll();

                        if (wkFg != null) {
                            addBands();
                            lblSubTitle1.setText(String.valueOf(wkFg.getProperty("wk_k")));
                            lblTitle.setText(getTitle());
                            final List<CidsBean> teile = wkFg.getBeanCollectionProperty("teile");
                            int index = 0;
                            boolean firstBand = true;

                            for (final CidsBean teil : teile) {
                                if (
                                    !hasFgsk(
                                                (Integer)teil.getProperty("linie.von.route.id"),
                                                (Double)teil.getProperty("linie.von.wert"),
                                                (Double)teil.getProperty("linie.bis.wert"))) {
                                    continue;
                                }

                                final CidsBean route = LinearReferencingHelper.getRouteBeanFromStationBean((CidsBean)
                                        teil.getProperty(
                                            "linie.von"));
                                final double from = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)
                                        teil.getProperty(
                                            "linie.von"));
                                final double till = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)
                                        teil.getProperty(
                                            "linie.bis"));
                                sbm[index].setMin(from);
                                sbm[index].setMax(till);
                                fgskBands[index].setMin(from);
                                fgskBands[index].setMax(till);
                                jband[index].setMinValue(from);
                                jband[index].setMaxValue(till);
                                final Integer rid = (Integer)route.getProperty("id");

                                if (firstBand) {
                                    wkband = new WKBand(from, till);
                                    wkband.fillAndInsertBand(
                                        sbm[index],
                                        String.valueOf(route.getProperty("gwk")),
                                        jband[index],
                                        null);
                                    firstBand = false;
                                }

                                if (fgsks != null) {
                                    final List<CidsBean> fgskList = new ArrayList<CidsBean>();
                                    final Map<CidsBean, List<CidsBean>> massnahmenMap =
                                        new HashMap<CidsBean, List<CidsBean>>();
                                    final Map<CidsBean, Boolean> massnahmenCompleteMap =
                                        new HashMap<CidsBean, Boolean>();

                                    for (final MetaObject fgsk : fgsks) {
                                        Double bis = (Double)fgsk.getBean().getProperty(
                                                "linie.bis.wert");
                                        Double von = (Double)fgsk.getBean().getProperty(
                                                "linie.von.wert");
                                        if (von > bis) {
                                            final Double tmp = von;
                                            von = bis;
                                            bis = tmp;
                                        }
                                        if (fgsk.getBean().getProperty("linie.von.route.id").equals(rid)
                                                    && ((bis - 1) >= from)
                                                    && ((von + 1) <= till)) {
                                            final CidsBean fgskBean = fgsk.getBean();
                                            fgskList.add(fgskBean);
                                            massnahmenMap.put(fgskBean, getMassnahmenForFgsk(fgskBean));
                                            massnahmenCompleteMap.put(
                                                fgskBean,
                                                getMassnahmenFragmentForFgsk(fgskBean).isEmpty());
                                        }
                                    }
                                    fgskBands[index].setCidsBeans(fgskList, massnahmenMap, massnahmenCompleteMap);
                                    sbm[index].fireBandModelChanged();
                                }

                                ++index;
                            }

                            setGewTyp();

                            if (fgsks != null) {
                                if (fgsks.length > 100) {
                                    int zoomValue;

                                    if (fgsks.length < 150) {
                                        zoomValue = 30;
                                    } else {
                                        zoomValue = 60;
                                    }

                                    sldZoom.setValue(zoomValue);

                                    final double zoom = sldZoom.getValue() / 10d;
                                    for (final JBand tmpBand : jband) {
                                        tmpBand.setZoomFactor(zoom);
                                    }
                                }
                            }

                            if (checkForMassnahmen) {
                                checkForMassnahmenBvp();
                            } else {
                                refreshMorphometer();
                                ((CardLayout)panMain.getLayout()).show(panMain, "band");
                                panHeaderInfo.setVisible(true);
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("Error while retrieving fgsk objects.", e);
                    }
                }
            };

        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    CismetConcurrency.getInstance("Fgsk_sim").getDefaultExecutor().submit(waitingDialog);
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    private void checkForMassnahmenBvp() {
        final SwingWorker<MetaObject[], Void> waitingDialog = new SwingWorker<MetaObject[], Void>() {

                @Override
                protected MetaObject[] doInBackground() throws Exception {
                    final List in = new ArrayList(1);
                    in.add(cidsBean.getProperty("wk_key"));
                    return MASSNAHMEN_CACHE.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        final MetaObject[] mos = get();

                        if ((mos != null) && (mos.length > 0)) {
                            final int userAnswer = JOptionPane.showConfirmDialog(
                                    SimulationEditor.this,
                                    NbBundle.getMessage(
                                        SimulationEditor.class,
                                        "SimulationEditor.checkForMassnahmenBvp().message"),
                                    NbBundle.getMessage(
                                        SimulationEditor.class,
                                        "SimulationEditor.checkForMassnahmenBvp().title"),
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            if (userAnswer == JOptionPane.YES_OPTION) {
                                for (final MetaObject mo : mos) {
                                    final CidsBean maBean = mo.getBean();
                                    final List<CidsBean> MassnBeans = FgskSimulationHelper.getSimMassnBeanFromMassnBvp(
                                            maBean);

                                    if ((MassnBeans != null) && !MassnBeans.isEmpty()) {
                                        final CidsBean stationLine = (CidsBean)maBean.getProperty("linie");
                                        final double from = Math.min((Double)stationLine.getProperty("von.wert"),
                                                (Double)stationLine.getProperty("bis.wert"));
                                        final double to = Math.max((Double)stationLine.getProperty("von.wert"),
                                                (Double)stationLine.getProperty("bis.wert"));
                                        final Long gwk = (Long)stationLine.getProperty("von.route.gwk");

                                        final List<CidsBean> fgskList = FgskSimulationHelper.getAllCorrespondingFgsk(
                                                from,
                                                to,
                                                gwk,
                                                fgsks);

                                        for (final CidsBean fgsk : fgskList) {
                                            for (final CidsBean massn : MassnBeans) {
                                                final double percentage = FgskSimulationHelper
                                                            .determineFgskIntersectionPercentage(fgsk, from, to, gwk);
                                                final boolean complete = percentage == 100.0;
                                                addMassnahme(fgsk, massn, complete);

                                                if (!complete) {
                                                    final ReadOnlyFgskBandMember member = getBandMemberForFgsk(fgsk);

                                                    if (member != null) {
                                                        member.setComplete(false);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("Error while retrieving massnahmen BVP objects", e);
                    }
                    refreshMorphometer();
                    refreshWkFgMassnList();
                    ((CardLayout)panMain.getLayout()).show(panMain, "band");
                    panHeaderInfo.setVisible(true);
                }
            };

        CismetConcurrency.getInstance("Fgsk_sim").getDefaultExecutor().submit(waitingDialog);
    }

    /**
     * The band member, that represents the given fgsk baen.
     *
     * @param   fgsk  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private ReadOnlyFgskBandMember getBandMemberForFgsk(final CidsBean fgsk) {
        for (final ReadOnlyFgskBand band : fgskBands) {
            for (int i = 0; i < band.getNumberOfMembers(); ++i) {
                final ReadOnlyFgskBandMember member = (ReadOnlyFgskBandMember)band.getMember(i);

                if (member.getCidsBean().equals(fgsk)) {
                    return member;
                }
            }
        }

        return null;
    }

    /**
     * Adds the given massnahmen object for the given fgsk to the simulation. If the massnahmen object is already
     * assigned to the fgsk object, nothing will happen.
     *
     * @param  fgsk   DOCUMENT ME!
     * @param  massn  DOCUMENT ME!
     */
    private void addMassnahme(final CidsBean fgsk, final CidsBean massn) {
        addMassnahme(fgsk, massn, null);
    }

    /**
     * Adds the given massnahmen object for the given fgsk to the simulation. If the massnahmen object is already
     * assigned to the fgsk object, nothing will happen.
     *
     * @param  fgsk      DOCUMENT ME!
     * @param  massn     DOCUMENT ME!
     * @param  complete  DOCUMENT ME!
     */
    private void addMassnahme(final CidsBean fgsk, final CidsBean massn, final Boolean complete) {
        if (FgskSimulationHelper.isMassnGroupContained(massn, cidsBean, fgsk)) {
            return;
        }
        try {
            final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName("sim_massnahmen_anwendungen");
            newBean.setProperty(FgskSimulationHelper.FGSK_KA_PROPERTY, fgsk.getProperty("id"));

            if (massn.getClass().getName().equals(FgskSimulationHelper.SIM_MASSNAHMEN_GRUPPE_CLASS_NAME)) {
                newBean.setProperty(FgskSimulationHelper.MASSNAHME_PROPERTY, massn.getProperty("id"));
            } else if (massn.getClass().getName().equals(FgskSimulationHelper.SIM_MASSNAHME_CLASS_NAME)) {
                newBean.setProperty(FgskSimulationHelper.EINZEL_MASSNAHME_PROPERTY, massn.getProperty("id"));
            }

            if ((complete != null) && complete) {
                newBean.setProperty("complete", true);
            } else {
                newBean.setProperty("complete", false);
            }

            cidsBean.getBeanCollectionProperty(FgskSimulationHelper.SIMULATIONSMASSNAHMEN_PROPERTY).add(newBean);
            final SimulationResultChangedEvent e = new SimulationResultChangedEvent(
                    this,
                    fgsk,
                    getMassnahmenForFgsk(fgsk));

            simulationResultChanged(e);
        } catch (Exception e) {
            LOG.error("error adding new object of type sim_massnahmen_anwendung", e);
        }
    }

    /**
     * Removes the given massnahmen object from the given simulation. If the massnahmen object is only assigned within a
     * group to the fgsk object, nothing will happen.
     *
     * @param  fgsk   DOCUMENT ME!
     * @param  massn  DOCUMENT ME!
     */
    private void removeMassnahme(final CidsBean fgsk, final CidsBean massn) {
        try {
            final List<CidsBean> simMassnList = cidsBean.getBeanCollectionProperty(
                    FgskSimulationHelper.SIMULATIONSMASSNAHMEN_PROPERTY);
            CidsBean massnToRemove = null;
            final Integer targetId = (Integer)massn.getProperty("id");
            final Integer fgskId = (Integer)fgsk.getProperty("id");

            for (final CidsBean simMassn : simMassnList) {
                if (simMassn.getProperty(FgskSimulationHelper.FGSK_KA_PROPERTY).equals(fgskId)) {
                    final Integer massnId = (Integer)simMassn.getProperty(
                            FgskSimulationHelper.EINZEL_MASSNAHME_PROPERTY);

                    if (massnId != null) {
                        if (massnId.equals(targetId)) {
                            massnToRemove = simMassn;
                            break;
                        }
                    }
                }
            }
            if (massnToRemove != null) {
                simMassnList.remove(massnToRemove);
                final SimulationResultChangedEvent e = new SimulationResultChangedEvent(
                        this,
                        fgsk,
                        getMassnahmenForFgsk(fgsk));

                simulationResultChanged(e);
            }
        } catch (Exception e) {
            LOG.error("error adding new object of type sim_massnahmen_anwendung", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshMorphometer() {
        jPanel1.setVisible(true);
        final double[] classInMeter = new double[6];
        final int HEIGHT = 28;
        final int maxWidth = panMorphometer.getWidth() - 2;
        final int[] lengthClass = new int[6];
        final double totalLength = getWBLength();
        double lengthFgsk = 0.0;

        for (final MetaObject fgsk : fgsks) {
            double length = 0;
            int cl = 0;
            try {
                length = Calc.getStationLength(fgsk.getBean());
                final Double p = SimSimulationsabschnittEditor.calc(fgsk.getBean(),
                        getMassnahmenForFgsk(fgsk.getBean()),
                        false,
                        null);
                cl = SimSimulationsabschnittEditor.getGueteklasse(fgsk.getBean(), p);
            } catch (final Exception e) {
                LOG.error("Error while calculating class", e);
                lengthFgsk += length;
                classInMeter[5] += length;
                continue;
            }
            lengthFgsk += length;

            if (cl > 0) {
                classInMeter[cl - 1] += length;
            } else {
                // Sonderfall: sonstige
                classInMeter[5] += length;
            }
        }

        if ((lengthFgsk > totalLength) && !warningAlreadyShown && !readOnly) {
            JOptionPane.showMessageDialog(
                this,
                "Die einzelnen Kartierabschnitte sind größer als der gesamte Wasserkörper.",
                "Fehler",
                JOptionPane.WARNING_MESSAGE);
            warningAlreadyShown = true;
        }

        lengthClass[0] = (int)(classInMeter[0] * maxWidth / totalLength);
        lengthClass[1] = (int)(classInMeter[1] * maxWidth / totalLength);
        lengthClass[2] = (int)(classInMeter[2] * maxWidth / totalLength);
        lengthClass[3] = (int)(classInMeter[3] * maxWidth / totalLength);
        lengthClass[4] = (int)(classInMeter[4] * maxWidth / totalLength);
        lengthClass[5] = (int)(classInMeter[5] * maxWidth / totalLength);
        int total = 0;

        for (final int i : lengthClass) {
            total += i;
        }
        int lostPrecionTotal = maxWidth - (int)(total * maxWidth / totalLength);

        int i = 0;
        while (lostPrecionTotal != 0) {
            if (lengthClass[i] > 0) {
                final int change = lostPrecionTotal / Math.abs(lostPrecionTotal);
                lengthClass[i] += change;
                lostPrecionTotal += change;
            }
            ++i;
            if (i == lengthClass.length) {
                break;
            }
        }

        panClass1.setSize(lengthClass[0], HEIGHT);
        panClass2.setBounds((int)(panClass1.getBounds().getX() + panClass1.getBounds().getWidth()),
            1,
            lengthClass[1],
            HEIGHT);
        panClass3.setBounds((int)(panClass2.getBounds().getX() + panClass2.getBounds().getWidth()),
            1,
            lengthClass[2],
            HEIGHT);
        panClass4.setBounds((int)(panClass3.getBounds().getX() + panClass3.getBounds().getWidth()),
            1,
            lengthClass[3],
            HEIGHT);
        panClass5.setBounds((int)(panClass4.getBounds().getX() + panClass4.getBounds().getWidth()),
            1,
            lengthClass[4],
            HEIGHT);
        panClass6.setBounds((int)(panClass5.getBounds().getX() + panClass5.getBounds().getWidth()),
            1,
            lengthClass[5],
            HEIGHT);
        panClass1.setBackground(SimSimulationsabschnittEditor.getColor(1));
        panClass2.setBackground(SimSimulationsabschnittEditor.getColor(2));
        panClass3.setBackground(SimSimulationsabschnittEditor.getColor(3));
        panClass4.setBackground(SimSimulationsabschnittEditor.getColor(4));
        panClass5.setBackground(SimSimulationsabschnittEditor.getColor(5));
        panClass6.setBackground(SimSimulationsabschnittEditor.getColor(0));

        refreshKosten();
    }

    /**
     * calculates the costs from the assigned massnahmen_gruppen and massnahmen objects.
     */
    private void refreshKosten() {
        final List<CidsBean> angMassn = cidsBean.getBeanCollectionProperty(
                FgskSimulationHelper.SIMULATIONSMASSNAHMEN_PROPERTY);
        double costs = 0.0;
        double flaechenerwerb = 0.0;
        double customCosts = 0.0;

        for (final CidsBean massn : angMassn) {
            try {
                final CidsBean fgsk = getFgskById((Integer)massn.getProperty(FgskSimulationHelper.FGSK_KA_PROPERTY));
                final CidsBean mass = FgskSimulationHelper.getSimMassnahmeGruppeById((Integer)massn.getProperty(
                            FgskSimulationHelper.MASSNAHME_PROPERTY));
                final CidsBean einzelMass = FgskSimulationHelper.getSimMassnahmeById((Integer)massn.getProperty(
                            FgskSimulationHelper.EINZEL_MASSNAHME_PROPERTY));

                if (fgsk != null) {
                    if ((mass != null)) {
                        for (final CidsBean m : mass.getBeanCollectionProperty("massnahmen")) {
                            final String ort = (String)m.getProperty("ort.name");

                            if (ort.equalsIgnoreCase("Flächenbedarf")) {
                                flaechenerwerb += FgskSimCalc.getInstance()
                                            .calcCosts(fgsk, m, FL_COSTS_CACHE.calcValue("1"));
                            } else {
                                costs += FgskSimCalc.getInstance().calcCosts(fgsk, m, FL_COSTS_CACHE.calcValue("1"));
                            }
                        }
                    } else if (einzelMass != null) {
                        final String ort = (String)einzelMass.getProperty("ort.name");

                        if (ort.equalsIgnoreCase("Flächenbedarf")) {
                            flaechenerwerb += FgskSimCalc.getInstance()
                                        .calcCosts(fgsk, einzelMass, FL_COSTS_CACHE.calcValue("1"));
                        } else {
                            costs += FgskSimCalc.getInstance()
                                        .calcCosts(fgsk, einzelMass, FL_COSTS_CACHE.calcValue("1"));
                        }
                    } else if (massn.getProperty(FgskSimulationHelper.CUSTOM_COSTS_PROPERTY) != null) {
                        customCosts += (Double)massn.getProperty(FgskSimulationHelper.CUSTOM_COSTS_PROPERTY);
                    }
                }
            } catch (Exception e) {
                LOG.error("Cannot calculate the costs", e);
            }
        }

        final double p = (costs + flaechenerwerb + customCosts) * 0.15;
        final DecimalFormat format = new DecimalFormat();
        lblMaKosten.setText(format.format(costs) + " €");
        lblFlKosten.setText(format.format(flaechenerwerb) + " €");
        lblSoKosten.setText(format.format(customCosts) + " €");
        lblNeKosten.setText(format.format(p) + " €");
        lblKostenGes4.setText(format.format(costs + flaechenerwerb + p + customCosts) + " €");
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getFgskById(final int id) {
        for (final MetaObject mo : fgsks) {
            if (mo.getID() == id) {
                return mo.getBean();
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private double getWBLength() {
        double length = 0;

        if (wkFg != null) {
            final List<CidsBean> parts = wkFg.getBeanCollectionProperty("teile");

            for (final CidsBean p : parts) {
                length += getWbStationLength(p);
            }
        }
        return length;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  kaBean DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static double getWbStationLength(final CidsBean bean) {
        try {
            final Double toValue = (Double)bean.getProperty("linie.bis.wert");
            final Double fromValue = (Double)bean.getProperty("linie.von.wert");

            return Math.abs(toValue - fromValue);
        } catch (final Exception e) {
            final String message = "illegal station settings in WK-FG-Teil"; // NOI18N
            LOG.error(message, e);
            return 0.0;
        }
    }

    /**
     * Determines all massnahmengruppen and massnahmen objects of the given fgsk object.
     *
     * @param   fgsk  DOCUMENT ME!
     *
     * @return  a list with all massnahmengruppen and massnahmen objects of the given fgsk object.
     */
    private List<CidsBean> getMassnahmenForFgsk(final CidsBean fgsk) {
        final List<CidsBean> massnahmen = new ArrayList<CidsBean>();
        final Integer fgskId = (Integer)fgsk.getProperty("id");
        final List<CidsBean> angMassn = cidsBean.getBeanCollectionProperty(
                FgskSimulationHelper.SIMULATIONSMASSNAHMEN_PROPERTY);

        for (final CidsBean massn : angMassn) {
            if (massn.getProperty(FgskSimulationHelper.FGSK_KA_PROPERTY).equals(fgskId)) {
                if (massn.getProperty(FgskSimulationHelper.MASSNAHME_PROPERTY) != null) {
                    final Integer massnId = (Integer)massn.getProperty(FgskSimulationHelper.MASSNAHME_PROPERTY);
                    final CidsBean simMassBean = FgskSimulationHelper.getSimMassnahmeGruppeById(massnId);
                    massnahmen.add(simMassBean);
                } else if (massn.getProperty(FgskSimulationHelper.EINZEL_MASSNAHME_PROPERTY) != null) {
                    final Integer massnId = (Integer)massn.getProperty(FgskSimulationHelper.EINZEL_MASSNAHME_PROPERTY);
                    final CidsBean simMassBean = FgskSimulationHelper.getSimMassnahmeById(massnId);
                    massnahmen.add(simMassBean);
                }
            }
        }

        return massnahmen;
    }

    /**
     * Determines the custom costs of the given fgsk object.
     *
     * @param   fgsk  DOCUMENT ME!
     *
     * @return  the custom costs of the given fgsk object
     */
    private Double getCustomCostsForFgsk(final CidsBean fgsk) {
        final CidsBean costsBean = FgskSimulationHelper.getCustomCostsBeanForFgsk(cidsBean, fgsk);

        if (costsBean != null) {
            return (Double)costsBean.getProperty(FgskSimulationHelper.CUSTOM_COSTS_PROPERTY);
        }

        return null;
    }

    /**
     * Determines all massnahmengruppen and massnahmen objects of the given fgsk object.
     *
     * @param   fgsk  DOCUMENT ME!
     *
     * @return  a list with all massnahmengruppen and massnahmen objects of the given fgsk object.
     */
    private List<CidsBean> getMassnahmenFragmentForFgsk(final CidsBean fgsk) {
        final List<CidsBean> massnahmen = new ArrayList<CidsBean>();
        final Integer fgskId = (Integer)fgsk.getProperty("id");
        final List<CidsBean> angMassn = cidsBean.getBeanCollectionProperty(
                FgskSimulationHelper.SIMULATIONSMASSNAHMEN_PROPERTY);

        for (final CidsBean massn : angMassn) {
            if (massn.getProperty(FgskSimulationHelper.FGSK_KA_PROPERTY).equals(fgskId)) {
                if ((massn.getProperty("complete") != null) && !((Boolean)massn.getProperty("complete"))) {
                    if (massn.getProperty(FgskSimulationHelper.MASSNAHME_PROPERTY) != null) {
                        final Integer massnId = (Integer)massn.getProperty(FgskSimulationHelper.MASSNAHME_PROPERTY);
                        final CidsBean simMassBean = FgskSimulationHelper.getSimMassnahmeGruppeById(massnId);
                        massnahmen.add(simMassBean);
                    } else if (massn.getProperty(FgskSimulationHelper.EINZEL_MASSNAHME_PROPERTY) != null) {
                        final Integer massnId = (Integer)massn.getProperty(
                                FgskSimulationHelper.EINZEL_MASSNAHME_PROPERTY);
                        final CidsBean simMassBean = FgskSimulationHelper.getSimMassnahmeById(massnId);
                        massnahmen.add(simMassBean);
                    }
                }
            }
        }

        return massnahmen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   rid   DOCUMENT ME!
     * @param   from  DOCUMENT ME!
     * @param   till  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean hasFgsk(final Integer rid, final Double from, final Double till) {
        for (final MetaObject fgsk : fgsks) {
            if (fgsk.getBean().getProperty("linie.von.route.id").equals(rid)
                        && ((Double)fgsk.getBean().getProperty("linie.von.wert") >= from)
                        && ((Double)fgsk.getBean().getProperty("linie.bis.wert") <= till)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
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
        bgrpDetails = new javax.swing.ButtonGroup();
        panNew = new CidsBeanDropPanel();
        lblNeu = new javax.swing.JLabel();
        bgZiel = new javax.swing.ButtonGroup();
        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panMorphometer = new javax.swing.JPanel();
        panClass1 = new javax.swing.JPanel();
        panClass2 = new javax.swing.JPanel();
        panClass3 = new javax.swing.JPanel();
        panClass4 = new javax.swing.JPanel();
        panClass5 = new javax.swing.JPanel();
        panClass6 = new javax.swing.JPanel();
        lblMarker1 = new javax.swing.JLabel();
        lblMarker2 = new javax.swing.JLabel();
        lblMarker3 = new javax.swing.JLabel();
        lblMarker4 = new javax.swing.JLabel();
        lblMarker5 = new javax.swing.JLabel();
        lblTitleName = new javax.swing.JLabel();
        labInfo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblMaCosts = new javax.swing.JLabel();
        lblMaKosten = new javax.swing.JLabel();
        lblFlCosts = new javax.swing.JLabel();
        lblFlKosten = new javax.swing.JLabel();
        lblSoCosts = new javax.swing.JLabel();
        lblSoKosten = new javax.swing.JLabel();
        lblNeCosts = new javax.swing.JLabel();
        lblNeKosten = new javax.swing.JLabel();
        lblCosts4 = new javax.swing.JLabel();
        lblKostenGes4 = new javax.swing.JLabel();
        diaName = new javax.swing.JDialog();
        panAllgemein = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblBemerkungen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taBemerkungen = new javax.swing.JTextArea();
        butOK = new javax.swing.JButton();
        butCancel = new javax.swing.JButton();
        lblBemerkungen1 = new javax.swing.JLabel();
        cbKostenFix = new javax.swing.JCheckBox();
        jMenu1 = new javax.swing.JMenu();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        labMassnProp = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panEditorPanel = new javax.swing.JPanel();
        panFgsk = new javax.swing.JPanel();
        panSimCard = new javax.swing.JPanel();
        panSim = new javax.swing.JPanel();
        panMultiSim = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlMultiSimList = new MultiFgskCidsBeanDropList();
        labMultiSim = new javax.swing.JLabel();
        jbRem = new javax.swing.JButton();
        panEmpty = new javax.swing.JPanel();
        panVermessung = new javax.swing.JPanel();
        panWkInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        panTotal1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jlWKMassnList = new javax.swing.JList();
        panHeader = new javax.swing.JPanel();
        panHeaderInfo = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        sldZoom = new javax.swing.JSlider();
        jLabel6 = new javax.swing.JLabel();
        lblSubTitle1 = new javax.swing.JLabel();
        rbOekPot = new javax.swing.JRadioButton();
        rbZust = new javax.swing.JRadioButton();
        togZoomToParts = new javax.swing.JToggleButton();
        panMain = new javax.swing.JPanel();
        panLoading = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        panBand = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        panFooter.setMinimumSize(new java.awt.Dimension(1050, 16));
        panFooter.setOpaque(false);
        panFooter.setPreferredSize(new java.awt.Dimension(1050, 16));
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        panNew.setOpaque(false);
        panNew.setLayout(new java.awt.GridBagLayout());

        lblNeu.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblNeu.text",
                new Object[] {})); // NOI18N
        panNew.add(lblNeu, new java.awt.GridBagConstraints());

        panTitle.setOpaque(false);
        panTitle.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        panTitle.add(lblTitle, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(640, 30));
        jPanel1.setLayout(null);

        panMorphometer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panMorphometer.setOpaque(false);
        panMorphometer.setPreferredSize(new java.awt.Dimension(200, 30));
        panMorphometer.setLayout(null);

        panClass1.setMinimumSize(new java.awt.Dimension(0, 28));
        panClass1.setPreferredSize(new java.awt.Dimension(0, 28));

        final javax.swing.GroupLayout panClass1Layout = new javax.swing.GroupLayout(panClass1);
        panClass1.setLayout(panClass1Layout);
        panClass1Layout.setHorizontalGroup(
            panClass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panClass1Layout.setVerticalGroup(
            panClass1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                28,
                Short.MAX_VALUE));

        panMorphometer.add(panClass1);
        panClass1.setBounds(1, 1, 0, 28);

        panClass2.setMinimumSize(new java.awt.Dimension(0, 26));
        panClass2.setPreferredSize(new java.awt.Dimension(0, 26));

        final javax.swing.GroupLayout panClass2Layout = new javax.swing.GroupLayout(panClass2);
        panClass2.setLayout(panClass2Layout);
        panClass2Layout.setHorizontalGroup(
            panClass2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panClass2Layout.setVerticalGroup(
            panClass2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                26,
                Short.MAX_VALUE));

        panMorphometer.add(panClass2);
        panClass2.setBounds(12, 1, 0, 26);

        panClass3.setMinimumSize(new java.awt.Dimension(0, 26));
        panClass3.setPreferredSize(new java.awt.Dimension(0, 26));

        final javax.swing.GroupLayout panClass3Layout = new javax.swing.GroupLayout(panClass3);
        panClass3.setLayout(panClass3Layout);
        panClass3Layout.setHorizontalGroup(
            panClass3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panClass3Layout.setVerticalGroup(
            panClass3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                26,
                Short.MAX_VALUE));

        panMorphometer.add(panClass3);
        panClass3.setBounds(12, 1, 0, 26);

        panClass4.setMinimumSize(new java.awt.Dimension(0, 26));
        panClass4.setPreferredSize(new java.awt.Dimension(0, 26));

        final javax.swing.GroupLayout panClass4Layout = new javax.swing.GroupLayout(panClass4);
        panClass4.setLayout(panClass4Layout);
        panClass4Layout.setHorizontalGroup(
            panClass4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panClass4Layout.setVerticalGroup(
            panClass4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                26,
                Short.MAX_VALUE));

        panMorphometer.add(panClass4);
        panClass4.setBounds(12, 1, 0, 26);

        panClass5.setMinimumSize(new java.awt.Dimension(0, 26));
        panClass5.setPreferredSize(new java.awt.Dimension(0, 26));

        final javax.swing.GroupLayout panClass5Layout = new javax.swing.GroupLayout(panClass5);
        panClass5.setLayout(panClass5Layout);
        panClass5Layout.setHorizontalGroup(
            panClass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panClass5Layout.setVerticalGroup(
            panClass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                26,
                Short.MAX_VALUE));

        panMorphometer.add(panClass5);
        panClass5.setBounds(12, 1, 0, 26);

        panClass6.setMinimumSize(new java.awt.Dimension(0, 26));

        final javax.swing.GroupLayout panClass6Layout = new javax.swing.GroupLayout(panClass6);
        panClass6.setLayout(panClass6Layout);
        panClass6Layout.setHorizontalGroup(
            panClass6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        panClass6Layout.setVerticalGroup(
            panClass6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                26,
                Short.MAX_VALUE));

        panMorphometer.add(panClass6);
        panClass6.setBounds(12, 1, 0, 26);

        jPanel1.add(panMorphometer);
        panMorphometer.setBounds(5, 0, 625, 30);

        lblMarker1.setForeground(java.awt.Color.white);
        lblMarker1.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblMarker1.text",
                new Object[] {})); // NOI18N
        jPanel1.add(lblMarker1);
        lblMarker1.setBounds(2, 30, 10, 17);

        lblMarker2.setForeground(java.awt.Color.white);
        lblMarker2.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblMarker2.text",
                new Object[] {})); // NOI18N
        jPanel1.add(lblMarker2);
        lblMarker2.setBounds(158, 30, 20, 17);

        lblMarker3.setForeground(java.awt.Color.white);
        lblMarker3.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblMarker3.text",
                new Object[] {})); // NOI18N
        jPanel1.add(lblMarker3);
        lblMarker3.setBounds(315, 30, 20, 17);

        lblMarker4.setForeground(java.awt.Color.white);
        lblMarker4.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblMarker4.text",
                new Object[] {})); // NOI18N
        jPanel1.add(lblMarker4);
        lblMarker4.setBounds(471, 30, 20, 17);

        lblMarker5.setForeground(java.awt.Color.white);
        lblMarker5.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblMarker5.text",
                new Object[] {})); // NOI18N
        jPanel1.add(lblMarker5);
        lblMarker5.setBounds(615, 30, 25, 17);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        panTitle.add(jPanel1, gridBagConstraints);

        lblTitleName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitleName.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleName.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblTitleNameMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        panTitle.add(lblTitleName, gridBagConstraints);

        labInfo.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/icon-info-sign-white.png"))); // NOI18N
        labInfo.setToolTipText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.labInfo.toolTipText",
                new Object[] {}));                                                                                    // NOI18N
        labInfo.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        labInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labInfo.setRequestFocusEnabled(false);
        labInfo.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    labInfoMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panTitle.add(labInfo, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(640, 30));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblMaCosts.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaCosts.setForeground(new java.awt.Color(255, 255, 255));
        lblMaCosts.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblMaCosts.text",
                new Object[] {}));                              // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(lblMaCosts, gridBagConstraints);

        lblMaKosten.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaKosten.setForeground(new java.awt.Color(255, 255, 255));
        lblMaKosten.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblMaKosten.text",
                new Object[] {}));                               // NOI18N
        lblMaKosten.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblMaKostenMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel2.add(lblMaKosten, gridBagConstraints);

        lblFlCosts.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFlCosts.setForeground(new java.awt.Color(255, 255, 255));
        lblFlCosts.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblFlCosts.text",
                new Object[] {}));                              // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(lblFlCosts, gridBagConstraints);

        lblFlKosten.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFlKosten.setForeground(new java.awt.Color(255, 255, 255));
        lblFlKosten.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblFlKosten.text",
                new Object[] {}));                               // NOI18N
        lblFlKosten.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblFlKostenMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel2.add(lblFlKosten, gridBagConstraints);

        lblSoCosts.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSoCosts.setForeground(new java.awt.Color(255, 255, 255));
        lblSoCosts.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblSoCosts.text",
                new Object[] {}));                              // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(lblSoCosts, gridBagConstraints);

        lblSoKosten.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSoKosten.setForeground(new java.awt.Color(255, 255, 255));
        lblSoKosten.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblSoKosten.text",
                new Object[] {}));                               // NOI18N
        lblSoKosten.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblSoKostenMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel2.add(lblSoKosten, gridBagConstraints);

        lblNeCosts.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNeCosts.setForeground(new java.awt.Color(255, 255, 255));
        lblNeCosts.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblNeCosts.text",
                new Object[] {}));                              // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(lblNeCosts, gridBagConstraints);

        lblNeKosten.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNeKosten.setForeground(new java.awt.Color(255, 255, 255));
        lblNeKosten.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblNeKosten.text",
                new Object[] {}));                               // NOI18N
        lblNeKosten.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblNeKostenMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel2.add(lblNeKosten, gridBagConstraints);

        lblCosts4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCosts4.setForeground(new java.awt.Color(255, 255, 255));
        lblCosts4.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblCosts4.text",
                new Object[] {}));                             // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel2.add(lblCosts4, gridBagConstraints);

        lblKostenGes4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblKostenGes4.setForeground(new java.awt.Color(255, 255, 255));
        lblKostenGes4.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblKostenGes4.text",
                new Object[] {}));                                 // NOI18N
        lblKostenGes4.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblKostenGes4MouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel2.add(lblKostenGes4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        panTitle.add(jPanel2, gridBagConstraints);

        diaName.setTitle(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.diaName.title",
                new Object[] {})); // NOI18N
        diaName.setMinimumSize(new java.awt.Dimension(590, 206));
        diaName.setModal(true);
        diaName.setResizable(false);
        diaName.getContentPane().setLayout(new java.awt.GridBagLayout());

        panAllgemein.setOpaque(false);
        panAllgemein.setLayout(new java.awt.GridBagLayout());

        lblName.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "GupGewaesserabschnittAllgemein.lblGewaessername.text")); // NOI18N
        lblName.setMaximumSize(new java.awt.Dimension(170, 17));
        lblName.setMinimumSize(new java.awt.Dimension(170, 17));
        lblName.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 15, 5, 5);
        panAllgemein.add(lblName, gridBagConstraints);

        txtName.setMaximumSize(new java.awt.Dimension(280, 20));
        txtName.setMinimumSize(new java.awt.Dimension(280, 20));
        txtName.setPreferredSize(new java.awt.Dimension(380, 20));
        txtName.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtNameActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 5, 15);
        panAllgemein.add(txtName, gridBagConstraints);

        lblBemerkungen.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "GupGewaesserabschnittAllgemein.lblGwk.text")); // NOI18N
        lblBemerkungen.setMaximumSize(new java.awt.Dimension(170, 17));
        lblBemerkungen.setMinimumSize(new java.awt.Dimension(170, 17));
        lblBemerkungen.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panAllgemein.add(lblBemerkungen, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(262, 87));

        taBemerkungen.setColumns(20);
        taBemerkungen.setRows(5);
        jScrollPane1.setViewportView(taBemerkungen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panAllgemein.add(jScrollPane1, gridBagConstraints);

        butOK.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.butOK.text",
                new Object[] {})); // NOI18N
        butOK.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    butOKActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panAllgemein.add(butOK, gridBagConstraints);

        butCancel.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.butCancel.text",
                new Object[] {})); // NOI18N
        butCancel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    butCancelActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panAllgemein.add(butCancel, gridBagConstraints);

        lblBemerkungen1.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblBemerkungen1.text")); // NOI18N
        lblBemerkungen1.setMaximumSize(new java.awt.Dimension(170, 17));
        lblBemerkungen1.setMinimumSize(new java.awt.Dimension(170, 17));
        lblBemerkungen1.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panAllgemein.add(lblBemerkungen1, gridBagConstraints);

        cbKostenFix.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.cbKostenFix.text",
                new Object[] {})); // NOI18N
        cbKostenFix.setContentAreaFilled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        panAllgemein.add(cbKostenFix, gridBagConstraints);

        diaName.getContentPane().add(panAllgemein, new java.awt.GridBagConstraints());

        jMenu1.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.jMenu1.text",
                new Object[] {})); // NOI18N

        setMinimumSize(new java.awt.Dimension(1150, 750));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1150, 750));
        addKeyListener(new java.awt.event.KeyAdapter() {

                @Override
                public void keyPressed(final java.awt.event.KeyEvent evt) {
                    formKeyPressed(evt);
                }
            });
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(830, 436));
        panInfo.setPreferredSize(new java.awt.Dimension(830, 436));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 50));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 40));
        panHeadInfo.setLayout(new java.awt.GridBagLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblHeading.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        panHeadInfo.add(lblHeading, gridBagConstraints);

        labMassnProp.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/icon-searchfolder_w.png"))); // NOI18N
        labMassnProp.setToolTipText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.labMassnProp.toolTipText",
                new Object[] {}));                                                                                   // NOI18N
        labMassnProp.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        labMassnProp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labMassnProp.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    labMassnPropMouseClicked(evt);
                }
            });
        panHeadInfo.add(labMassnProp, new java.awt.GridBagConstraints());

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        panEditorPanel.setLayout(new java.awt.CardLayout());

        panFgsk.setOpaque(false);
        panFgsk.setLayout(new java.awt.GridBagLayout());

        panSimCard.setLayout(new java.awt.CardLayout());

        panSim.setLayout(new java.awt.BorderLayout());
        panSimCard.add(panSim, "sim");

        panMultiSim.setLayout(new java.awt.GridBagLayout());

        jScrollPane3.setPreferredSize(new java.awt.Dimension(280, 130));

        jScrollPane3.setViewportView(jlMultiSimList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 15, 10);
        panMultiSim.add(jScrollPane3, gridBagConstraints);

        labMultiSim.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labMultiSim.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.labMultiSim.text",
                new Object[] {})); // NOI18N
        labMultiSim.setPreferredSize(new java.awt.Dimension(480, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 15);
        panMultiSim.add(labMultiSim, gridBagConstraints);

        jbRem.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_16.png"))); // NOI18N
        jbRem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbRemActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 15);
        panMultiSim.add(jbRem, gridBagConstraints);

        panSimCard.add(panMultiSim, "multiSim");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panFgsk.add(panSimCard, gridBagConstraints);

        panEditorPanel.add(panFgsk, "fgsk");

        panEmpty.setOpaque(false);
        panEmpty.setLayout(new java.awt.BorderLayout());
        panEditorPanel.add(panEmpty, "empty");

        panVermessung.setOpaque(false);
        panVermessung.setLayout(new java.awt.BorderLayout());
        panEditorPanel.add(panVermessung, "vermessung");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(panEditorPanel, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo, gridBagConstraints);

        panWkInfo.setMinimumSize(new java.awt.Dimension(640, 436));
        panWkInfo.setPreferredSize(new java.awt.Dimension(270, 436));

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 40));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 40));
        panHeadInfo1.setLayout(new java.awt.GridBagLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.lblHeading1.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        panHeadInfo1.add(lblHeading1, gridBagConstraints);

        panWkInfo.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        panTotal1.setPreferredSize(new java.awt.Dimension(315, 200));
        panTotal1.setLayout(new java.awt.GridBagLayout());

        jlWKMassnList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    jlWKMassnListValueChanged(evt);
                }
            });
        jScrollPane5.setViewportView(jlWKMassnList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 5, 15);
        panTotal1.add(jScrollPane5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panInfoContent1.add(panTotal1, gridBagConstraints);

        panWkInfo.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panWkInfo, gridBagConstraints);

        panHeader.setOpaque(false);
        panHeader.setLayout(new java.awt.GridBagLayout());

        panHeaderInfo.setMinimumSize(new java.awt.Dimension(531, 52));
        panHeaderInfo.setOpaque(false);
        panHeaderInfo.setPreferredSize(new java.awt.Dimension(531, 52));
        panHeaderInfo.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.jLabel5.text",
                new Object[] {}));                                // NOI18N
        jLabel5.setMaximumSize(new java.awt.Dimension(92, 22));
        jLabel5.setMinimumSize(new java.awt.Dimension(92, 22));
        jLabel5.setPreferredSize(new java.awt.Dimension(92, 22));
        panHeaderInfo.add(jLabel5);
        jLabel5.setBounds(330, 0, 60, 20);

        sldZoom.setMaximum(200);
        sldZoom.setValue(0);
        sldZoom.addChangeListener(new javax.swing.event.ChangeListener() {

                @Override
                public void stateChanged(final javax.swing.event.ChangeEvent evt) {
                    sldZoomStateChanged(evt);
                }
            });
        panHeaderInfo.add(sldZoom);
        sldZoom.setBounds(390, 4, 350, 16);

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel6.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.jLabel6.text",
                new Object[] {}));                                  // NOI18N
        jLabel6.setMinimumSize(new java.awt.Dimension(91, 22));
        panHeaderInfo.add(jLabel6);
        jLabel6.setBounds(12, 0, 92, 22);

        lblSubTitle1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        panHeaderInfo.add(lblSubTitle1);
        lblSubTitle1.setBounds(110, 0, 220, 20);

        bgZiel.add(rbOekPot);
        rbOekPot.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.rbOekPot.text",
                new Object[] {})); // NOI18N
        rbOekPot.setContentAreaFilled(false);
        rbOekPot.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    rbOekPotActionPerformed(evt);
                }
            });
        panHeaderInfo.add(rbOekPot);
        rbOekPot.setBounds(810, 0, 240, 24);

        bgZiel.add(rbZust);
        rbZust.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.rbZust.text",
                new Object[] {})); // NOI18N
        rbZust.setContentAreaFilled(false);
        rbZust.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    rbZustActionPerformed(evt);
                }
            });
        panHeaderInfo.add(rbZust);
        rbZust.setBounds(810, 20, 230, 24);

        togZoomToParts.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/icon-zoom-in.png"))); // NOI18N
        togZoomToParts.setToolTipText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.togZoomToParts.toolTipText",
                new Object[] {}));                                                                            // NOI18N
        panHeaderInfo.add(togZoomToParts);
        togZoomToParts.setBounds(760, 4, 30, 28);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        panHeader.add(panHeaderInfo, gridBagConstraints);

        panMain.setOpaque(false);
        panMain.setLayout(new java.awt.CardLayout());

        panLoading.setMinimumSize(new java.awt.Dimension(1050, 188));
        panLoading.setOpaque(false);
        panLoading.setPreferredSize(new java.awt.Dimension(1050, 188));
        panLoading.setLayout(new java.awt.GridBagLayout());

        jProgressBar1.setIndeterminate(true);
        jProgressBar1.setPreferredSize(new java.awt.Dimension(250, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panLoading.add(jProgressBar1, gridBagConstraints);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(
                SimulationEditor.class,
                "SimulationEditor.jLabel1.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panLoading.add(jLabel1, gridBagConstraints);

        panMain.add(panLoading, "loading");

        panBand.setOpaque(false);
        panBand.setLayout(new java.awt.BorderLayout());
        panMain.add(panBand, "band");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panHeader.add(panMain, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panHeader, gridBagConstraints);

        jPanel3.setMinimumSize(new java.awt.Dimension(1050, 1));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(1050, 1));

        jLabel4.setMaximumSize(new java.awt.Dimension(1050, 1));
        jLabel4.setMinimumSize(new java.awt.Dimension(1050, 1));
        jLabel4.setPreferredSize(new java.awt.Dimension(1050, 1));

        final javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                1050,
                Short.MAX_VALUE).addGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                    jPanel3Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(
                        jLabel4,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        1040,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 10, Short.MAX_VALUE))));
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1, Short.MAX_VALUE)
                        .addGroup(
                            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                jPanel3Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(
                                    jLabel4,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE))));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(jPanel3, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void sldZoomStateChanged(final javax.swing.event.ChangeEvent evt) { //GEN-FIRST:event_sldZoomStateChanged
        final double zoom = sldZoom.getValue() / 10d;
        for (final JBand tmpBand : jband) {
            tmpBand.setZoomFactor(zoom);
        }
    }                                                                           //GEN-LAST:event_sldZoomStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rbOekPotActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_rbOekPotActionPerformed
        setGewTyp();
    }                                                                            //GEN-LAST:event_rbOekPotActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rbZustActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_rbZustActionPerformed
        setGewTyp();
    }                                                                          //GEN-LAST:event_rbZustActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtNameActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtNameActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void butCancelActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_butCancelActionPerformed
        cancel = true;
        namePrompt = false;
        diaName.setVisible(false);
        fillDialog();
    }                                                                             //GEN-LAST:event_butCancelActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void butOKActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_butOKActionPerformed
        cancel = false;
        namePrompt = false;
        diaName.setVisible(false);
        try {
            cidsBean.setProperty("name", txtName.getText());
            cidsBean.setProperty("beschreibung", taBemerkungen.getText());
            cidsBean.setProperty("kosten_fix", cbKostenFix.isSelected());
        } catch (Exception e) {
            LOG.error("Cannot set name", e);
        }
        setName();
    }                                                                         //GEN-LAST:event_butOKActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblTitleNameMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblTitleNameMouseClicked
        if (evt.getClickCount() == 2) {
//            FgskSimulationHelper.createAllSimulations();
            diaName.setSize(590, 226);
            diaName.pack();
            StaticSwingTools.showDialog(diaName);
        }
    }                                                                            //GEN-LAST:event_lblTitleNameMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jlWKMassnListValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_jlWKMassnListValueChanged
        if (!evt.getValueIsAdjusting()) {
            final int[] selectedIndices = jlWKMassnList.getSelectedIndices();

            wkMassnListChangedByUser = true;
            try {
                if ((selectedIndices != null) && (selectedIndices.length > 0)) {
                    final List<CidsBean> beans = new ArrayList<CidsBean>();
                    final ListModel<CidsBean> model = jlWKMassnList.getModel();

                    for (final int index : selectedIndices) {
                        beans.add(model.getElementAt(index));
                    }

                    for (int bandIndex = 0; bandIndex < fgskBands.length; ++bandIndex) {
                        final ReadOnlyFgskBand fgskBand = fgskBands[bandIndex];
                        final List<BandMemberSelectable> validMembers = new ArrayList<BandMemberSelectable>();

                        for (int i = 0; i < fgskBand.getNumberOfMembers(); ++i) {
                            final BandMember member = fgskBand.getMember(i);
                            final ReadOnlyFgskBandMember fgskMember = (ReadOnlyFgskBandMember)member;

                            if (containsAllMassnahmen(fgskMember.getMassnahmen(), beans)) {
                                validMembers.add(fgskMember);
                            }
                        }

                        jband[bandIndex].setSelectedMember(validMembers);
                    }
                }
            } finally {
                wkMassnListChangedByUser = false;
            }
        }
    } //GEN-LAST:event_jlWKMassnListValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void labMassnPropMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_labMassnPropMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            simulationsEditor.generateMassnahmenvorschlag();
        }
    }                                                                            //GEN-LAST:event_labMassnPropMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void formKeyPressed(final java.awt.event.KeyEvent evt) { //GEN-FIRST:event_formKeyPressed
        System.out.println("drin");
    }                                                                //GEN-LAST:event_formKeyPressed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void labInfoMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_labInfoMouseClicked
        diaName.setSize(590, 226);
        diaName.pack();
        StaticSwingTools.showDialog(diaName);
    }                                                                       //GEN-LAST:event_labInfoMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbRemActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbRemActionPerformed
        if (readOnly) {
            return;
        }

        final int[] selectedIndices = jlMultiSimList.getSelectedIndices();

        if (selectedIndices != null) {
            for (final int index : selectedIndices) {
                final MultiMassnahmenContainer container = (MultiMassnahmenContainer)jlMultiSimList
                            .getModel().getElementAt(index);
                final CidsBean massn = container.massnBean;

                for (final CidsBean fgsk : getAllSelectedFgsk()) {
                    try {
                        if (FgskSimulationHelper.isMassnGroupContained(massn, cidsBean, fgsk)) {
                            removeMassnahme(fgsk, massn);
                        }
                    } catch (Exception e) {
                        LOG.error("error adding new object of type sim_massnahmen_anwendung", e);
                    }
                }
            }
        }

        refreshMultiFgskListModel(getAllSelectedFgsk());
        refreshWkFgMassnList();
    } //GEN-LAST:event_jbRemActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblMaKostenMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblMaKostenMouseClicked
        // TODO add your handling code here:
    } //GEN-LAST:event_lblMaKostenMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblFlKostenMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblFlKostenMouseClicked
        // TODO add your handling code here:
    } //GEN-LAST:event_lblFlKostenMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblNeKostenMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblNeKostenMouseClicked
        // TODO add your handling code here:
    } //GEN-LAST:event_lblNeKostenMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblKostenGes4MouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblKostenGes4MouseClicked
        // TODO add your handling code here:
    } //GEN-LAST:event_lblKostenGes4MouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblSoKostenMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblSoKostenMouseClicked
        // TODO add your handling code here:
    } //GEN-LAST:event_lblSoKostenMouseClicked

    /**
     * Checks, if all elements of the containedList are contained in the reference list. The containedList should
     * contains massnahmen objects and the referenceList can contain massnahmengruppen and massnahmen.
     *
     * @param   referenceList  DOCUMENT ME!
     * @param   containedList  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean containsAllMassnahmen(final List<CidsBean> referenceList, final List<CidsBean> containedList) {
        final List<CidsBean> massnReferenceList = FgskSimulationHelper.getMassnahmenBeans(referenceList);

        for (final CidsBean bean : containedList) {
            if (!massnReferenceList.contains(bean)) {
                return false;
            }
        }

        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private List<CidsBean> getAllSelectedFgsk() {
        final List<CidsBean> selectedFgsk = new ArrayList<CidsBean>();

        for (final JBand tmp : jband) {
            for (final BandMemberSelectable bm : tmp.getSelectedBandMemberList()) {
                if (bm instanceof ReadOnlyFgskBandMember) {
                    selectedFgsk.add(((ReadOnlyFgskBandMember)bm).getCidsBean());
                }
            }
        }

        return selectedFgsk;
    }

    /**
     * DOCUMENT ME!
     */
    private void setGewTyp() {
        if (wkFg.getProperty("b9ausw").equals("natürlich")) {
            rbZust.setSelected(true);
        } else {
            rbOekPot.setSelected(true);
        }
    }

    @Override
    public void dispose() {
        simulationsEditor.dispose();

        if (!initialReadOnly && WrrlMapOptionsPanel.isChangeMapWindowActive()) {
            restoreLayout();
        }

        if (fgskBands != null) {
            for (final JBand band : jband) {
                band.dispose();
            }
        }

        if (sbm != null) {
            for (int index = 0; index < sbm.length; ++index) {
                if (sbm[index] != null) {
                    sbm[index].removeBandModelListener(modelListener[index]);
                }
            }
        }
    }

    @Override
    public String getTitle() {
        if ((cidsBean != null) && (cidsBean.getProperty("wk_key") != null)) {
            return "WK: " + String.valueOf(cidsBean.getProperty("wk_key"));
        } else {
            return "";
        }
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        DevelopmentTools.initSessionManagerFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "kif");
        DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "kif",
            "simulation",
            9,
            1280,
            1024);
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
    }

    @Override
    public boolean prepareForSave() {
        diaName.setSize(590, 156);
        if (namePrompt) {
            StaticSwingTools.showDialog(diaName);
        } else {
            cancel = false;
        }
        if (simulationsEditor != null) {
            simulationsEditor.prepareForSave();
        }

        try {
            cidsBean.setProperty("kosten", Double.parseDouble(lblKostenGes4.getText()));
        } catch (Exception e) {
            LOG.error("Cannot set the property kosten", e);
        }

        return !cancel;
    }

    @Override
    public JComponent getTitleComponent() {
        lblTitle.setText(getTitle());
        return panTitle;
    }

    @Override
    public void simulationResultChanged(final SimulationResultChangedEvent e) {
        refreshMorphometer();
        for (final ReadOnlyFgskBand band : fgskBands) {
            band.refreshMembers(e.getChangedFgsk(), e.getMassnahmenList());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  selectedFgsk  DOCUMENT ME!
     */
    private void refreshMultiFgskListModel(final List<CidsBean> selectedFgsk) {
        final Map<CidsBean, MultiMassnahmenContainer> totalMassnMap = new HashMap<CidsBean, MultiMassnahmenContainer>();

        for (final CidsBean bean : selectedFgsk) {
            final List<CidsBean> massnList = FgskSimulationHelper.getMassnahmenBeans(getMassnahmenForFgsk(
                        bean));
            final TreeSet<ComparableCidsBean> beansOfCurrentBandMember = new TreeSet<ComparableCidsBean>();

            for (final CidsBean massn : massnList) {
                final ComparableCidsBean massnWrapper = new ComparableCidsBean(massn);

                if (beansOfCurrentBandMember.contains(massnWrapper)) {
                    // If a fgsk part contains the same massnahme twice, it should only be
                    // considered once in the massnahmen list.
                    continue;
                } else {
                    beansOfCurrentBandMember.add(massnWrapper);
                }

                MultiMassnahmenContainer container = totalMassnMap.get(massn);

                if (container == null) {
                    container = new MultiMassnahmenContainer(massn, selectedFgsk.size());
                    totalMassnMap.put(massn, container);
                } else {
                    container.increaseCounter();
                }
            }
        }

        final List<MultiMassnahmenContainer> allContainer = new ArrayList<MultiMassnahmenContainer>(
                totalMassnMap.values());
        Collections.sort(allContainer);

        final DefaultListModel<MultiMassnahmenContainer> listModel = new DefaultListModel<MultiMassnahmenContainer>();
        for (final MultiMassnahmenContainer container : allContainer) {
            listModel.addElement(container);
        }
        jlMultiSimList.setModel(listModel);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class RestriktionBandModelListener implements BandModelListener {

        //~ Instance fields ----------------------------------------------------

        private final JBand band;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new RestriktionBandModelListener object.
         *
         * @param  band  DOCUMENT ME!
         */
        public RestriktionBandModelListener(final JBand band) {
            this.band = band;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void bandModelChanged(final BandModelEvent e) {
        }

        @Override
        public void bandModelSelectionChanged(final BandModelEvent e) {
            final List<BandMemberSelectable> selectedMembers = new ArrayList<BandMemberSelectable>();
            if (!selectionChangedHandlingInProgress) {
                selectionChangedHandlingInProgress = true;
            } else {
                return;
            }

            if (e.isSelectionLost() && !wkMassnListChangedByUser) {
                for (final JBand tmp : jband) {
                    if (!tmp.equals(band)) {
                        tmp.setSelectedMember(new ArrayList<BandMemberSelectable>());
                    }
                }

                selectedMembers.addAll(band.getSelectedBandMemberList());
            } else {
                for (final JBand tmp : jband) {
                    selectedMembers.addAll(tmp.getSelectedBandMemberList());
                }
            }

            band.setRefreshAvoided(true);
            simulationsEditor.dispose();

            if (selectedMembers.isEmpty()) {
                switchToForm("empty");
                lblHeading.setText("");
            } else if (selectedMembers.size() == 1) {
                bgrpDetails.clearSelection();
                switchToForm("empty");
                lblHeading.setText("");

                if (selectedMembers.get(0).getBandMember() instanceof ReadOnlyFgskBandMember) {
                    switchToForm("fgsk>sim");
                    final CidsBean bean = ((ReadOnlyFgskBandMember)selectedMembers.get(0).getBandMember())
                                .getCidsBean();
                    lblHeading.setText("FGSK-Abschnitt: " + bean.toString());

                    simulationsEditor.setSimulation(cidsBean);
                    simulationsEditor.setCustomCosts(getCustomCostsForFgsk(bean));
                    simulationsEditor.setMassnahmen(getMassnahmenForFgsk(bean));
                    simulationsEditor.setCidsBean(bean);
                }
            } else {
                switchToForm("fgsk>multiSim");
                final List<CidsBean> selectedFgsk = new ArrayList<CidsBean>();

                for (final BandMemberSelectable tmp : selectedMembers) {
                    final BandMember bm = tmp.getBandMember();

                    if (bm instanceof ReadOnlyFgskBandMember) {
                        selectedFgsk.add(((ReadOnlyFgskBandMember)bm).getCidsBean());
                    }
                }

                refreshMultiFgskListModel(selectedFgsk);
            }

            band.setRefreshAvoided(false);
            band.bandModelChanged(null);
            selectionChangedHandlingInProgress = false;

            if (!wkMassnListChangedByUser) {
                jlWKMassnList.getSelectionModel().clearSelection();
            }

            if (togZoomToParts.isSelected() && !CismapBroker.getInstance().getMappingComponent().isFixedMapExtent()) {
                // zoom to the selected features
                final List<Geometry> geomList = new ArrayList<Geometry>();

                for (final BandMemberSelectable tmp : selectedMembers) {
                    final BandMember bm = tmp.getBandMember();

                    if (bm instanceof ReadOnlyFgskBandMember) {
                        final Geometry tmpGeom = (Geometry)((ReadOnlyFgskBandMember)bm).getCidsBean()
                                    .getProperty("linie.geom.geo_field");

                        if (tmpGeom != null) {
                            geomList.add(tmpGeom.getEnvelope());
                        }
                    }
                }

                if (geomList.size() > 0) {
                    Geometry geom;
                    final GeometryFactory factory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING),
                            geomList.get(0).getSRID());
                    geom = factory.buildGeometry(geomList);

                    if (geom instanceof GeometryCollection) {
                        geom = ((GeometryCollection)geom).union();
                    }

                    if (geom != null) {
                        CismapBroker.getInstance()
                                .getMappingComponent()
                                .gotoBoundingBox(new XBoundingBox(geom),
                                    true,
                                    !CismapBroker.getInstance().getMappingComponent().isFixedMapScale(),
                                    500);
                    }
                }
            }
        }

        @Override
        public void bandModelValuesChanged(final BandModelEvent e) {
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CidsBeanDropPanel extends JPanel implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            if (!isNew || readOnly) {
                return;
            }
            if (beans.size() > 0) {
                final CidsBean tmpBean = beans.get(0);

                if (tmpBean.getClass().getName().equals(WK_FG_CLASS_NAME)) {
                    wkFg = tmpBean;
                    ((CardLayout)panMain.getLayout()).show(panMain, "band");
                    try {
                        cidsBean.setProperty("wk_key", wkFg.getProperty("wk_k"));
                        setNamesAndBands();
                        isNew = false;
                    } catch (Exception e) {
                        LOG.error("error while setting the wk_key property", e);
                    }
                }
            }
        }
    }

    /**
     * This list can handle massnahmen_gruppe objects and massnahmen objects.
     *
     * @version  $Revision$, $Date$
     */
    private class MultiFgskCidsBeanDropList extends JList implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            if (readOnly) {
                return;
            }
            for (final CidsBean massn : beans) {
                if (!(massn.getClass().getName().equals(FgskSimulationHelper.SIM_MASSNAHMEN_GRUPPE_CLASS_NAME)
                                || massn.getClass().getName().equals(FgskSimulationHelper.SIM_MASSNAHME_CLASS_NAME))) {
                    // not supported bean type
                    continue;
                }
                final String hinweis = (String)massn.getProperty("hinweis");

                if ((hinweis != null) && !hinweis.equals("")) {
                    JOptionPane.showMessageDialog(
                        SimulationEditor.this,
                        hinweis,
                        "Hinweis",
                        JOptionPane.INFORMATION_MESSAGE);
                }

                for (final CidsBean fgsk : getAllSelectedFgsk()) {
                    try {
                        addMassnahme(fgsk, massn);
                    } catch (Exception e) {
                        LOG.error("error adding new object of type sim_massnahmen_anwendung", e);
                    }
                }
            }

            refreshMultiFgskListModel(getAllSelectedFgsk());
            refreshWkFgMassnList();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class FgskCalculator implements Calculator<List, MetaObject[]> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  den wkk des Wasserkörpers
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "SELECT " + MC_FGSK.getID() + ",  " + MC_FGSK.getPrimaryKey()
                        + " FROM "
                        + MC_FGSK.getTableName() + " WHERE wkk = '"
                        + String.valueOf(input.get(0)) + "' and (historisch is null or not historisch);";

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for fgsk: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class FlCostsCalculator implements Calculator<String, List<MetaObject>> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   id  input den wkk des Wasserkörpers
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public List<MetaObject> calculate(final String id) throws Exception {
            final AbstractCidsServerSearch objectSeartch = new AllObjectsSearch(MC_SIM_FLAECEHNERWERBSPREIS.getID(),
                    MC_SIM_FLAECEHNERWERBSPREIS.getTableName());

            return (List<MetaObject>)SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), objectSeartch);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class WkfgCalculator implements Calculator<List, MetaObject[]> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  den wkk des Wasserkörpers
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "SELECT " + MC_WK_FG.getID() + ",  " + MC_WK_FG.getPrimaryKey() + " FROM "
                        + MC_WK_FG.getTableName() + " WHERE wk_k = '"
                        + String.valueOf(input.get(0)) + "';";

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for wk-fg: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class MassnBvpCalculator implements Calculator<List, MetaObject[]> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  den wkk des Wasserkörpers
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            String query = "SELECT " + MC_MASSNAHMEN.getID() + ",  m." + MC_MASSNAHMEN.getPrimaryKey() + " FROM "
                        + MC_MASSNAHMEN.getTableName() + " m, " + MC_WK_FG.getTableName() + " f ";

            if (input.size() > 1) {
                query += ", massnahmen_realisierung mr ";
            }

            query += " WHERE m.wk_fg = f.id and f.wk_k = '"
                        + String.valueOf(input.get(0)) + "' and not massn_fin";

            if (input.size() > 1) {
                query += " and m.realisierung = mr.id and m.realisierung <= " + input
                            .get(1).toString();
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for massnahmen: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class MultiMassnahmenContainer implements Comparable<MultiMassnahmenContainer> {

        //~ Instance fields ----------------------------------------------------

        private int count = 1;
        private final CidsBean massnBean;
        private final int totalCount;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new MultiMassnahmenConainer object.
         *
         * @param  massnBean   DOCUMENT ME!
         * @param  totalCount  DOCUMENT ME!
         */
        public MultiMassnahmenContainer(final CidsBean massnBean, final int totalCount) {
            this.massnBean = massnBean;
            this.totalCount = totalCount;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         */
        public void increaseCounter() {
            ++count;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public boolean isUsedByAll() {
            return count == totalCount;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getCount() {
            return count;
        }

        @Override
        public String toString() {
            return massnBean.getProperty("key") + " - " + massnBean.toString();
        }

        @Override
        public int compareTo(final MultiMassnahmenContainer o) {
            if (count != o.count) {
                return o.count - count;
            } else {
                return toString().compareTo(o.toString());
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class MultiMassnahmenConainerCellRenderer extends DefaultListCellRenderer {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getListCellRendererComponent(final JList<?> list,
                final Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            final Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if ((value instanceof MultiMassnahmenContainer) && (c instanceof JLabel)) {
                final MultiMassnahmenContainer container = (MultiMassnahmenContainer)value;
                final JLabel lab = (JLabel)c;

                if (!container.isUsedByAll()) {
                    lab.setForeground(Color.LIGHT_GRAY);
                    lab.setText(value + " (" + container.getCount() + " Vorkommen)");
                }
            }

            return c;
        }
    }
}
