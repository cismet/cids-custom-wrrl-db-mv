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

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.Calc;
import de.cismet.cids.custom.wrrl_db_mv.util.ReadOnlyFgskBand;
import de.cismet.cids.custom.wrrl_db_mv.util.ReadOnlyFgskBandMember;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.*;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

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
    EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger LOG = Logger.getLogger(SimulationEditor.class);
//    private static final MetaClass MC_WK_FG = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");
//    private static final MetaClass MC_FGSK = ClassCacheMultiple.getMetaClass(
//            WRRLUtil.DOMAIN_NAME,
//            "fgsk_kartierabschnitt");

    //~ Instance fields --------------------------------------------------------

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgZiel;
    private javax.swing.ButtonGroup bgrpDetails;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblMarker1;
    private javax.swing.JLabel lblMarker2;
    private javax.swing.JLabel lblMarker3;
    private javax.swing.JLabel lblMarker4;
    private javax.swing.JLabel lblMarker5;
    private javax.swing.JLabel lblNeu;
    private javax.swing.JLabel lblSubTitle1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panBand;
    private javax.swing.JPanel panClass1;
    private javax.swing.JPanel panClass2;
    private javax.swing.JPanel panClass3;
    private javax.swing.JPanel panClass4;
    private javax.swing.JPanel panClass5;
    private javax.swing.JPanel panEmpty;
    private javax.swing.JPanel panFgsk;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private javax.swing.JPanel panHeader;
    private javax.swing.JPanel panHeaderInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panMorphometer;
    private javax.swing.JPanel panNew;
    private javax.swing.JPanel panTitle;
    private javax.swing.JPanel panVermessung;
    private javax.swing.JRadioButton rbOekPot;
    private javax.swing.JRadioButton rbZust;
    private javax.swing.JSlider sldZoom;
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

        if (!readOnly) {
            new CidsBeanDropTarget(panNew);
        }
        simulationsEditor = new SimSimulationsabschnittEditor(readOnly);

        switchToForm("empty");
        lblHeading.setText("FGSK Abschnitt");
        panFgsk.add(simulationsEditor, BorderLayout.CENTER);

        sldZoom.setPaintTrack(false);

//        RendererTools.makeReadOnly(rbOekPot);
//        RendererTools.makeReadOnly(rbZust);
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
            fgskBands[internIndex] = new ReadOnlyFgskBand(gwk);
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
        this.cidsBean = cidsBean;
        this.wkFg = null;

        switchToForm("empty");
        lblHeading.setText("");

        if (cidsBean != null) {
            isNew = cidsBean.getProperty("wk_key") == null;

            if (isNew) {
                panBand.removeAll();
                panBand.add(panNew, BorderLayout.CENTER);
            } else {
                setNamesAndBands();
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setNamesAndBands() {
        final WaitingDialogThread<MetaObject[]> waitingDialog = new WaitingDialogThread<MetaObject[]>(StaticSwingTools
                        .getParentFrame(this),
                true,
                "Lade Abschnitte",
                null,
                100) {

                @Override
                protected MetaObject[] doInBackground() throws Exception {
                    final MetaClass MC_WK_FG = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");
                    final MetaClass MC_FGSK = ClassCacheMultiple.getMetaClass(
                            WRRLUtil.DOMAIN_NAME,
                            "fgsk_kartierabschnitt");
                    final String queryWkFg = "SELECT " + MC_WK_FG.getID() + ",  " + MC_WK_FG.getPrimaryKey() + " FROM "
                                + MC_WK_FG.getTableName() + " WHERE wk_k = '"
                                + cidsBean.getProperty("wk_key") + "';";

                    final MetaObject[] mosWkFg = SessionManager.getProxy().getMetaObjectByQuery(queryWkFg, 0);
                    if ((mosWkFg != null) && (mosWkFg.length == 1)) {
                        wkFg = mosWkFg[0].getBean();

                        final String queryfgsk = "SELECT " + MC_FGSK.getID() + ",  " + MC_FGSK.getPrimaryKey()
                                    + " FROM "
                                    + MC_FGSK.getTableName() + " WHERE wkk = '"
                                    + cidsBean.getProperty("wk_key") + "';";

                        return SessionManager.getProxy().getMetaObjectByQuery(queryfgsk, 0);
                    }

                    return null;
                }

                @Override
                protected void done() {
                    try {
                        fgsks = get();

                        if (wkFg != null) {
                            addBands();
                            lblSubTitle1.setText(String.valueOf(wkFg.getProperty("wk_k")));
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
                                jband[index].setMinValue(from);
                                jband[index].setMaxValue(till);
                                final String rname = String.valueOf(route.getProperty("routenname"));
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

                                    for (final MetaObject fgsk : fgsks) {
                                        if (fgsk.getBean().getProperty("linie.von.route.id").equals(rid)
                                                    && ((Double)fgsk.getBean().getProperty("linie.von.wert") >= from)
                                                    && ((Double)fgsk.getBean().getProperty("linie.bis.wert") <= till)) {
                                            fgskList.add(fgsk.getBean());
                                        }
                                    }
                                    fgskBands[index].setCidsBeans(fgskList);
                                    sbm[index].fireBandModelChanged();
                                }

                                ++index;
                            }

                            if (wkFg.getProperty("evk.id").equals(1)) {
                                rbZust.setSelected(true);
                            } else {
                                rbOekPot.setSelected(true);
                            }

                            refreshMorphometer();
                        }
                    } catch (Exception e) {
                        LOG.error("Error while retrieving fgsk objects.", e);
                    }
                }
            };

        if (EventQueue.isDispatchThread()) {
            waitingDialog.start();
        } else {
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        waitingDialog.start();
                    }
                });
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshMorphometer() {
        final double[] classInMeter = new double[5];
        final int HEIGHT = 28;
        final int maxWidth = panMorphometer.getWidth() - 2;
        final double totalLength = getWBLength();

        for (final MetaObject fgsk : fgsks) {
            try {
                final double length = Calc.getStationLength(fgsk.getBean());
                final Double p = simulationsEditor.calc(fgsk.getBean(), getMassnahmenForFgsk(fgsk.getBean()), false);
                final int cl = getGueteklasse(fgsk.getBean(), p);

                if (cl > 0) {
                    classInMeter[cl - 1] += length;
                }
            } catch (final Exception e) {
                LOG.error("Error while calculating class", e);
            }
        }

        panClass1.setSize((int)(classInMeter[0] * maxWidth / totalLength), HEIGHT);
        panClass2.setBounds((int)(panClass1.getBounds().getX() + panClass1.getBounds().getWidth()),
            1,
            (int)(classInMeter[1] * maxWidth / totalLength),
            HEIGHT);
//        panClass2.setSize((int)(classInMeter[1] * maxWidth / totalLength), HEIGHT);
        panClass3.setBounds((int)(panClass2.getBounds().getX() + panClass2.getBounds().getWidth()),
            1,
            (int)(classInMeter[2] * maxWidth / totalLength),
            HEIGHT);
//        panClass3.setSize((int)(classInMeter[2] * maxWidth / totalLength), HEIGHT);
        panClass4.setBounds((int)(panClass3.getBounds().getX() + panClass3.getBounds().getWidth()),
            1,
            (int)(classInMeter[3] * maxWidth / totalLength),
            HEIGHT);
//        panClass4.setSize((int)(classInMeter[3] * maxWidth / totalLength), HEIGHT);
        panClass5.setBounds((int)(panClass4.getBounds().getX() + panClass4.getBounds().getWidth()),
            1,
            (int)(classInMeter[4] * maxWidth / totalLength),
            HEIGHT);
//        panClass5.setSize((int)(classInMeter[4] * maxWidth / totalLength), HEIGHT);
        panClass1.setBackground(SimSimulationsabschnittEditor.getColor(1));
        panClass2.setBackground(SimSimulationsabschnittEditor.getColor(2));
        panClass3.setBackground(SimSimulationsabschnittEditor.getColor(3));
        panClass4.setBackground(SimSimulationsabschnittEditor.getColor(4));
        panClass5.setBackground(SimSimulationsabschnittEditor.getColor(5));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     * @param   p     DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int getGueteklasse(final CidsBean bean, final Double p) {
        int gueteklasse = 0;

        final CidsBean exception = (CidsBean)bean.getProperty(Calc.PROP_EXCEPTION);

        if ((exception != null) && !Integer.valueOf(0).equals(exception.getProperty(Calc.PROP_VALUE))) {
            gueteklasse = 5;
        } else {
            if (p != null) {
                gueteklasse = SimSimulationsabschnittEditor.convertPointsToClass(p);
            }
        }

        return gueteklasse;
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
        lblMarker1 = new javax.swing.JLabel();
        lblMarker2 = new javax.swing.JLabel();
        lblMarker3 = new javax.swing.JLabel();
        lblMarker4 = new javax.swing.JLabel();
        lblMarker5 = new javax.swing.JLabel();
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

        jPanel1.add(panMorphometer);
        panMorphometer.setBounds(5, 0, 625, 30);

        lblMarker1.setText("0");
        jPanel1.add(lblMarker1);
        lblMarker1.setBounds(2, 30, 10, 17);

        lblMarker2.setText("25");
        jPanel1.add(lblMarker2);
        lblMarker2.setBounds(158, 30, 20, 17);

        lblMarker3.setText("50");
        jPanel1.add(lblMarker3);
        lblMarker3.setBounds(315, 30, 20, 17);

        lblMarker4.setText("75");
        jPanel1.add(lblMarker4);
        lblMarker4.setBounds(471, 30, 20, 17);

        lblMarker5.setText("100");
        jPanel1.add(lblMarker5);
        lblMarker5.setBounds(615, 30, 25, 17);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        panTitle.add(jPanel1, gridBagConstraints);

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
        lblSubTitle1.setBounds(110, 0, 220, 0);

        bgZiel.add(rbOekPot);
        rbOekPot.setText("gutes Ökologisches Potenzial");
        rbOekPot.setContentAreaFilled(false);
        panHeaderInfo.add(rbOekPot);
        rbOekPot.setBounds(770, 0, 240, 24);

        bgZiel.add(rbZust);
        rbZust.setText("guter Zustand");
        rbZust.setContentAreaFilled(false);
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

    @Override
    public void dispose() {
        simulationsEditor.dispose();

        for (int index = 0; index < sbm.length; ++index) {
            if (sbm[index] != null) {
                sbm[index].removeBandModelListener(modelListener[index]);
            }
        }
    }

    @Override
    public String getTitle() {
        return "WK: " + String.valueOf(cidsBean.getProperty("wk_k"));
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
        return true;
    }

    @Override
    public JComponent getTitleComponent() {
        lblTitle.setText(getTitle());
        return panTitle;
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
//                    panBand.add(jband, BorderLayout.CENTER);
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
}
