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

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.types.treenode.DefaultMetaTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.RequestsFullSizeComponent;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.tree.TreeNode;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.SimSimulationsabschnittEditor.SimulationResultChangedEvent;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.Calc;
import de.cismet.cids.custom.wrrl_db_mv.util.ReadOnlyFgskBand;
import de.cismet.cids.custom.wrrl_db_mv.util.ReadOnlyFgskBandMember;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.*;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.commons.concurrency.CismetConcurrency;

import de.cismet.tools.CalculationCache;
import de.cismet.tools.Calculator;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.TitleComponentProvider;
import de.cismet.tools.gui.WaitingDialogThread;
import de.cismet.tools.gui.jbands.BandModelEvent;
import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
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

    private static Logger LOG = Logger.getLogger(SimulationEditor.class);
    private static final MetaClass MC_WK_FG = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");
    private static final MetaClass MC_FGSK = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "fgsk_kartierabschnitt");
    private static final CalculationCache<List, MetaObject[]> fgskCache = new CalculationCache<List, MetaObject[]>(
            new FgskCalculator());
    private static final CalculationCache<List, MetaObject[]> wkfgCache = new CalculationCache<List, MetaObject[]>(
            new WkfgCalculator());

    static {
        fgskCache.setTimeToCacheResults(30000);
        wkfgCache.setTimeToCacheResults(30000);
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
    private boolean readOnly = false;
    private boolean isNew = false;
    private CidsBean wkFg;
    private MetaObject[] fgsks;
    private boolean cancel = false;
    private boolean namePrompt = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgZiel;
    private javax.swing.ButtonGroup bgrpDetails;
    private javax.swing.JButton butCancel;
    private javax.swing.JButton butOK;
    private javax.swing.JDialog diaName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBemerkungen;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblMarker1;
    private javax.swing.JLabel lblMarker2;
    private javax.swing.JLabel lblMarker3;
    private javax.swing.JLabel lblMarker4;
    private javax.swing.JLabel lblMarker5;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNeu;
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
    private javax.swing.JPanel panEmpty;
    private javax.swing.JPanel panFgsk;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private javax.swing.JPanel panHeader;
    private javax.swing.JPanel panHeaderInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panLoading;
    private javax.swing.JPanel panMorphometer;
    private javax.swing.JPanel panNew;
    private javax.swing.JPanel panTitle;
    private javax.swing.JPanel panVermessung;
    private javax.swing.JRadioButton rbOekPot;
    private javax.swing.JRadioButton rbZust;
    private javax.swing.JSlider sldZoom;
    private javax.swing.JTextArea taBemerkungen;
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
        this.readOnly = readOnly;
        initComponents();

        panHeaderInfo.setVisible(false);
        jPanel1.setVisible(false);

        if (!readOnly) {
            new CidsBeanDropTarget(panNew);
        }
        simulationsEditor = new SimSimulationsabschnittEditor(readOnly);
        simulationsEditor.addSimulationResultChangedListener(this);

        switchToForm("empty");
        lblHeading.setText("FGSK Abschnitt");
        panFgsk.add(simulationsEditor, BorderLayout.CENTER);

        sldZoom.setPaintTrack(false);
    }

    //~ Methods ----------------------------------------------------------------

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
            fgskBands[internIndex] = new ReadOnlyFgskBand(gwk, simulationsEditor);
            jband[internIndex] = new JBand(true);
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
                    ((CardLayout)panInfoContent.getLayout()).show(panInfoContent, id);
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

            if (isNew) {
                panBand.removeAll();
                panBand.add(panNew, BorderLayout.CENTER);
                namePrompt = true;
                try {
                    cidsBean.setProperty("name", "Variante");
                    fillDialog();
                } catch (Exception e) {
                    LOG.error("Error while setting the name", e);
                }
                final DefaultMetaTreeNode dmtn = (DefaultMetaTreeNode)ComponentRegistry.getRegistry().getCatalogueTree()
                            .getSelectedNode();

                if (dmtn != null) {
                    final TreeNode nodeTmp = dmtn.getParent();
                    if ((nodeTmp != null) && (nodeTmp instanceof ObjectTreeNode)) {
                        final ObjectTreeNode node = (ObjectTreeNode)nodeTmp;
                        final CidsBean tmpBean = node.getMetaObject().getBean();
                        if (tmpBean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_fg")) {
                            wkFg = tmpBean;
                            panBand.removeAll();
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
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void fillDialog() {
        String name = (String)cidsBean.getProperty("name");
        String beschreibung = (String)cidsBean.getProperty("beschreibung");

        if (name == null) {
            name = "";
        }
        if (beschreibung == null) {
            beschreibung = "";
        }
        txtName.setText(name);
        taBemerkungen.setText(beschreibung);
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
        panBand.removeAll();
        panBand.setLayout(new BorderLayout());
        panBand.add(panLoading, BorderLayout.CENTER);
        panBand.doLayout();
        panBand.invalidate();
        panBand.validate();
        panBand.repaint();

        final SwingWorker<MetaObject[], Void> waitingDialog = new SwingWorker<MetaObject[], Void>() {

                @Override
                protected MetaObject[] doInBackground() throws Exception {
                    final List wkfgIn = new ArrayList(1);
                    wkfgIn.add(cidsBean.getProperty("wk_key"));
                    final MetaObject[] mosWkFg = wkfgCache.calcValue(wkfgIn);

                    if ((mosWkFg != null) && (mosWkFg.length == 1)) {
                        wkFg = mosWkFg[0].getBean();
                        final List in = new ArrayList(1);
                        in.add(cidsBean.getProperty("wk_key"));
                        return fgskCache.calcValue(in);
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

                                    for (final MetaObject fgsk : fgsks) {
                                        Double bis = (Double)fgsk.getBean().getProperty(
                                                "linie.bis.wert");
                                        Double von = (Double)fgsk.getBean().getProperty(
                                                "linie.von.wert");
                                        if (von.doubleValue() > bis.doubleValue()) {
                                            final Double tmp = von;
                                            von = bis;
                                            bis = tmp;
                                        }
                                        if (fgsk.getBean().getProperty("linie.von.route.id").equals(rid)
                                                    && ((bis - 1) >= from)
                                                    && ((von + 1) <= till)) {
                                            fgskList.add(fgsk.getBean());
                                            massnahmenMap.put(
                                                fgsk.getBean(),
                                                getMassnahmenForFgsk(fgsk.getBean()));
                                        }
                                    }
                                    fgskBands[index].setCidsBeans(fgskList, massnahmenMap);
                                    sbm[index].fireBandModelChanged();
                                }

                                ++index;
                            }

                            setGewTyp();

                            refreshMorphometer();
                            if (fgsks != null) {
                                if (fgsks.length > 100) {
                                    int zoomValue = 0;

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
                        }
                    } catch (Exception e) {
                        LOG.error("Error while retrieving fgsk objects.", e);
                    }
                    panHeaderInfo.setVisible(true);
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
                final Double p = simulationsEditor.calc(fgsk.getBean(), getMassnahmenForFgsk(fgsk.getBean()), false);
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
     * DOCUMENT ME!
     *
     * @param   fgsk  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private List<CidsBean> getMassnahmenForFgsk(final CidsBean fgsk) {
        final List<CidsBean> massnahmen = new ArrayList<CidsBean>();
        final Integer fgskId = (Integer)fgsk.getProperty("id");
        final List<CidsBean> angMassn = cidsBean.getBeanCollectionProperty("angewendete_simulationsmassnahmen");

        for (final CidsBean massn : angMassn) {
            if (massn.getProperty("fgsk_ka.id").equals(fgskId)) {
                massnahmen.add((CidsBean)massn.getProperty("massnahme"));
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
        panLoading = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        diaName = new javax.swing.JDialog();
        panAllgemein = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblBemerkungen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taBemerkungen = new javax.swing.JTextArea();
        butOK = new javax.swing.JButton();
        butCancel = new javax.swing.JButton();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panFgsk = new javax.swing.JPanel();
        panEmpty = new javax.swing.JPanel();
        panVermessung = new javax.swing.JPanel();
        panHeader = new javax.swing.JPanel();
        panHeaderInfo = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        sldZoom = new javax.swing.JSlider();
        jLabel6 = new javax.swing.JLabel();
        lblSubTitle1 = new javax.swing.JLabel();
        rbOekPot = new javax.swing.JRadioButton();
        rbZust = new javax.swing.JRadioButton();
        panBand = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        panFooter.setMinimumSize(new java.awt.Dimension(1050, 48));
        panFooter.setOpaque(false);
        panFooter.setPreferredSize(new java.awt.Dimension(1050, 48));
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

        lblNeu.setText("Bitte den gewünschten Wasserkörper in dieses Fenster ziehen");
        panNew.add(lblNeu, new java.awt.GridBagConstraints());

        panTitle.setOpaque(false);
        panTitle.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
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
        lblMarker1.setText("0");
        jPanel1.add(lblMarker1);
        lblMarker1.setBounds(2, 30, 10, 17);

        lblMarker2.setForeground(java.awt.Color.white);
        lblMarker2.setText("25");
        jPanel1.add(lblMarker2);
        lblMarker2.setBounds(158, 30, 20, 17);

        lblMarker3.setForeground(java.awt.Color.white);
        lblMarker3.setText("50");
        jPanel1.add(lblMarker3);
        lblMarker3.setBounds(315, 30, 20, 17);

        lblMarker4.setForeground(java.awt.Color.white);
        lblMarker4.setText("75");
        jPanel1.add(lblMarker4);
        lblMarker4.setBounds(471, 30, 20, 17);

        lblMarker5.setForeground(java.awt.Color.white);
        lblMarker5.setText("100");
        jPanel1.add(lblMarker5);
        lblMarker5.setBounds(615, 30, 25, 17);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
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
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        panTitle.add(lblTitleName, gridBagConstraints);

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

        jLabel1.setText("Kartierabschnitte werden geladen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panLoading.add(jLabel1, gridBagConstraints);

        diaName.setTitle("Allgemeine Informationen");
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

        butOK.setText("OK");
        butOK.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    butOKActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panAllgemein.add(butOK, gridBagConstraints);

        butCancel.setText("Abbrechen");
        butCancel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    butCancelActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panAllgemein.add(butCancel, gridBagConstraints);

        diaName.getContentPane().add(panAllgemein, new java.awt.GridBagConstraints());

        setMinimumSize(new java.awt.Dimension(1050, 750));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1050, 750));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(640, 420));
        panInfo.setPreferredSize(new java.awt.Dimension(640, 420));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Informationen");
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.CardLayout());

        panFgsk.setOpaque(false);
        panFgsk.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panFgsk, "fgsk");

        panEmpty.setOpaque(false);
        panEmpty.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panEmpty, "empty");

        panVermessung.setOpaque(false);
        panVermessung.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panVermessung, "vermessung");

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo, gridBagConstraints);

        panHeader.setOpaque(false);
        panHeader.setLayout(new java.awt.GridBagLayout());

        panHeaderInfo.setMinimumSize(new java.awt.Dimension(531, 52));
        panHeaderInfo.setOpaque(false);
        panHeaderInfo.setPreferredSize(new java.awt.Dimension(531, 52));
        panHeaderInfo.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        jLabel5.setText("Zoom:");
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
        jLabel6.setText("Gewässer:");
        jLabel6.setMinimumSize(new java.awt.Dimension(91, 22));
        panHeaderInfo.add(jLabel6);
        jLabel6.setBounds(12, 0, 92, 22);

        lblSubTitle1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        panHeaderInfo.add(lblSubTitle1);
        lblSubTitle1.setBounds(110, 0, 220, 20);

        bgZiel.add(rbOekPot);
        rbOekPot.setText("gutes ökologisches Potenzial");
        rbOekPot.setContentAreaFilled(false);
        rbOekPot.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    rbOekPotActionPerformed(evt);
                }
            });
        panHeaderInfo.add(rbOekPot);
        rbOekPot.setBounds(770, 0, 240, 24);

        bgZiel.add(rbZust);
        rbZust.setText("guter Zustand");
        rbZust.setContentAreaFilled(false);
        rbZust.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    rbZustActionPerformed(evt);
                }
            });
        panHeaderInfo.add(rbZust);
        rbZust.setBounds(770, 20, 230, 24);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        panHeader.add(panHeaderInfo, gridBagConstraints);

        panBand.setOpaque(false);
        panBand.setLayout(new java.awt.GridLayout(1, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panHeader.add(panBand, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
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
                    jPanel3Layout.createSequentialGroup().addGap(0, 5, Short.MAX_VALUE).addComponent(
                        jLabel4,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        1040,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 5, Short.MAX_VALUE))));
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
            diaName.setSize(590, 226);
            StaticSwingTools.showDialog(diaName);
        }
    }                                                                            //GEN-LAST:event_lblTitleNameMouseClicked

    /**
     * DOCUMENT ME!
     */
    private void setGewTyp() {
        if (wkFg.getProperty("evk.id").equals(1)) {
            rbZust.setSelected(true);
        } else {
            rbOekPot.setSelected(true);
        }
    }

    @Override
    public void dispose() {
        simulationsEditor.dispose();

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
            2,
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

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class RestriktionBandModelListener implements BandModelListener {

        //~ Instance fields ----------------------------------------------------

        private JBand band;

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
            final BandMember bm;

            bm = band.getSelectedBandMember();
            band.setRefreshAvoided(true);
            simulationsEditor.dispose();

            if (bm != null) {
                bgrpDetails.clearSelection();
                switchToForm("empty");
                lblHeading.setText("");

                if (bm instanceof ReadOnlyFgskBandMember) {
                    switchToForm("fgsk");
                    final CidsBean bean = ((ReadOnlyFgskBandMember)bm).getCidsBean();
                    lblHeading.setText("FGSK-Abschnitt: " + bean.toString());

                    simulationsEditor.setSimulation(cidsBean);
                    simulationsEditor.setMassnahmen(getMassnahmenForFgsk(bean));
                    simulationsEditor.setCidsBean(bean);
                }
            } else {
                switchToForm("empty");
                lblHeading.setText("");
            }

            band.setRefreshAvoided(false);
            band.bandModelChanged(null);
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
            if (!isNew) {
                return;
            }
            if (beans.size() > 0) {
                final CidsBean tmpBean = beans.get(0);

                if (tmpBean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_fg")) {
                    wkFg = tmpBean;
                    panBand.removeAll();
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
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class FgskCalculator implements Calculator<List, MetaObject[]> {

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
                        + String.valueOf(input.get(0)) + "';";

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
}
