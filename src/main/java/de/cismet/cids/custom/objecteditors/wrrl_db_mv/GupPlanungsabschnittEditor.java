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
import Sirius.navigator.tools.MetaObjectCache;
import Sirius.navigator.types.treenode.DefaultMetaTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.Geometry;


import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.server.search.NaturschutzgebietSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.QuerbautenSearchByStations;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkSearchByStations;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.*;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.FeatureRegistry;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;
import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.features.DefaultStyledFeature;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.PFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.CreateLinearReferencedMarksListener;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.CidsFeature;

import de.cismet.tools.CalculationCache;
import de.cismet.tools.Calculator;
import de.cismet.tools.CismetThreadPool;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.RoundedPanel;
import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.TitleComponentProvider;
import de.cismet.tools.gui.jbands.BandModelEvent;
import de.cismet.tools.gui.jbands.EmptyAbsoluteHeightedBand;
import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandModelListener;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class GupPlanungsabschnittEditor extends JPanel implements CidsBeanRenderer,
    FooterComponentProvider,
    TitleComponentProvider,
    EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupPlanungsabschnittEditor.class);
    private static final String GUP_MASSNAHME = "gup_unterhaltungsmassnahme";
    private static final String VERMESSUNG = "vermessung_band_element";
    public static final int GUP_MASSNAHME_UFER_LINKS = 2;
    public static final int GUP_MASSNAHME_UFER_RECHTS = 1;
    public static final int GUP_MASSNAHME_UMFELD_RECHTS = 4;
    public static final int GUP_MASSNAHME_UMFELD_LINKS = 3;
    public static final int GUP_MASSNAHME_SOHLE = 5;
    private static CalculationCache<List, MetaObject[]> schutzgebietCache = new CalculationCache<List, MetaObject[]>(
            new SchutzgebietCalculator());
    private static CalculationCache<List, MetaObject[]> umlandCache = new CalculationCache<List, MetaObject[]>(
            new UmlandnutzungCalculator());
    private static CalculationCache<List, MetaObject[]> entwicklungszielCache =
        new CalculationCache<List, MetaObject[]>(new EntwicklungszielCalculator());
    private static CalculationCache<List, ArrayList<ArrayList>> querbauwerkCache =
        new CalculationCache<List, ArrayList<ArrayList>>(new QuerbauwerkeCalculator());
    private static CalculationCache<List, MetaObject[]> unterhaltungserfordernisCache =
        new CalculationCache<List, MetaObject[]>(new UnterhaltungserfordernisCalculator());
    private static CalculationCache<List, MetaObject[]> verbreitungsraumCache =
        new CalculationCache<List, MetaObject[]>(new VerbreitungsraumCalculator());
    private static CalculationCache<List, MetaObject[]> operativeZieleCache = new CalculationCache<List, MetaObject[]>(
            new OperativesZielCalculator());

    static {
        // Inhalte der Comboboxen des Massnahmeneditors schon laden, um Wartezeiten beim Oeffnen des Editors zu
        // verhindern
        CismetThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    final MetaClass year = ClassCacheMultiple.getMetaClass(
                            WRRLUtil.DOMAIN_NAME,
                            "GUP_JAHR");
                    final MetaClass interval = ClassCacheMultiple.getMetaClass(
                            WRRLUtil.DOMAIN_NAME,
                            "gup_massnahmenintervall");

                    try {
                        DefaultBindableReferenceCombo.getModelByMetaClass(year, true);
                        DefaultBindableReferenceCombo.getModelByMetaClass(interval, true);
                    } catch (Exception e) {
                        // nothing to do
                    }
                }
            });
        // Inhalte der Comboboxen des Massnahmeneditors schon laden, um Wartezeiten beim Oeffnen des Editors zu
        // verhindern
        CismetThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    final MetaClass time = ClassCacheMultiple.getMetaClass(
                            WRRLUtil.DOMAIN_NAME,
                            "gup_unterhaltungsmassnahme_ausfuehrungszeitpunkt");
                    final MetaClass material = ClassCacheMultiple.getMetaClass(
                            WRRLUtil.DOMAIN_NAME,
                            "gup_material_verbleib");
                    try {
                        DefaultBindableReferenceCombo.getModelByMetaClass(time, true);
                        DefaultBindableReferenceCombo.getModelByMetaClass(material, true);
                    } catch (Exception e) {
                        // nothing to do
                    }
                }
            });
        // Inhalte der Comboboxen des Massnahmeneditors schon laden, um Wartezeiten beim Oeffnen des Editors zu
        // verhindern
        CismetThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    final MetaClass geraet = ClassCacheMultiple.getMetaClass(
                            WRRLUtil.DOMAIN_NAME,
                            "gup_geraet");
                    final MetaClass einsatzvariante = ClassCacheMultiple.getMetaClass(
                            WRRLUtil.DOMAIN_NAME,
                            "gup_einsatzvariante");
                    try {
                        DefaultBindableReferenceCombo.getModelByMetaClass(geraet, true);
                        DefaultBindableReferenceCombo.getModelByMetaClass(einsatzvariante, true);
                    } catch (Exception e) {
                        // nothing to do
                    }
                }
            });
    }

    //~ Instance fields --------------------------------------------------------

    private List<CidsBean> rechtesUferList = new ArrayList<CidsBean>();
    private List<CidsBean> sohleList = new ArrayList<CidsBean>();
    private List<CidsBean> linkesUferList = new ArrayList<CidsBean>();
    private List<CidsBean> rechtesUmfeldList = new ArrayList<CidsBean>();
    private List<CidsBean> linkesUmfeldList = new ArrayList<CidsBean>();

    private PureNewFeature routeFeature;
    private JBand vBand = new JBand();
    private SimpleBandModel vBandModel = new SimpleBandModel();
    private VermessungsBand vermessungsBand = new VermessungsBand("Vermessung", VERMESSUNG);
    private MassnahmenBand rechtesUferBand = new MassnahmenBand("Ufer rechts", GUP_MASSNAHME);
    private MassnahmenBand sohleBand = new MassnahmenBand("Sohle", GUP_MASSNAHME);
    private MassnahmenBand linkesUferBand = new MassnahmenBand("Ufer links", GUP_MASSNAHME);
    private MassnahmenBand rechtesUmfeldBand = new MassnahmenBand("Umfeld rechts", GUP_MASSNAHME);
    private MassnahmenBand linkesUmfeldBand = new MassnahmenBand("Umfeld links", GUP_MASSNAHME);
    private UmlandnutzungBand nutzungLinksBand = new UmlandnutzungBand("links");
    private UmlandnutzungBand nutzungRechtsBand = new UmlandnutzungBand("rechts");
    private ColoredReadOnlyBand schutzgebietLinksBand = new ColoredReadOnlyBand(
            "Schutzgebiet links",
            "art",
            "art.name");
    private ColoredReadOnlyBand schutzgebietRechtsBand = new ColoredReadOnlyBand(
            "Schutzgebiet rechts",
            "art",
            "art.name");
    private ColoredReadOnlyBand verbreitungsraumLinksBand = new ColoredReadOnlyBand(
            "Verbreitungsgebiet links",
            "vermeidungsgruppe",
            "vermeidungsgruppe.name");
    private ColoredReadOnlyBand verbreitungsraumRechtsBand = new ColoredReadOnlyBand(
            "Verbreitungsgebiet rechts",
            "vermeidungsgruppe",
            "vermeidungsgruppe.name");
    private ColoredReadOnlyBand verbreitungsraumSohleBand = new ColoredReadOnlyBand(
            "Verbreitungsgebiet Sohle",
            "vermeidungsgruppe",
            "vermeidungsgruppe.name");
    private ColoredReadOnlyBand operativeZieleLinksBand = new ColoredReadOnlyBand(
            "Op. Ziele links",
            "operatives_ziel",
            "operatives_ziel.name");
    private ColoredReadOnlyBand operativeZieleRechtsBand = new ColoredReadOnlyBand(
            "Op. Ziele rechts",
            "operatives_ziel",
            "operatives_ziel.name");
    private ColoredReadOnlyBand operativeZieleSohleBand = new ColoredReadOnlyBand(
            "Op. Ziele Sohle",
            "operatives_ziel",
            "operatives_ziel.name");
    private ColoredReadOnlyBand operativeZieleUmfeldLinksBand = new ColoredReadOnlyBand(
            "Op. Ziele Umfeld links",
            "operatives_ziel",
            "operatives_ziel.name");
    private ColoredReadOnlyBand operativeZieleUmfeldRechtsBand = new ColoredReadOnlyBand(
            "Op. Ziele Umfeld rechts",
            "operatives_ziel",
            "operatives_ziel.name");
    private AnwohnerBand anwohnerLinksBand = new AnwohnerBand(0.5f, "Ansprechpartner");
    private AnwohnerBand anwohnerRechtsBand = new AnwohnerBand(0.5f, "Ansprechpartner");
    private EntwicklungszielBand entwicklungszielBand = new EntwicklungszielBand();
    private UnterhaltungserfordernisBand unterhaltungserfordernisBand = new UnterhaltungserfordernisBand();
    private WKBand wkband;
    private WKBand vwkband;
    private QuerbauwerksBand querbauwerksband = new QuerbauwerksBand();
    private final JBand jband;
    private final BandModelListener modelListener = new GupGewaesserabschnittBandModelListener();
    private final SimpleBandModel sbm = new SimpleBandModel();
    private String gwk = null;
    private CidsBean cidsBean;
    private VermessungBandElementEditor vermessungsEditor = new VermessungBandElementEditor();
    private GupAbschnittsinfoEditor abschnittsinfoEditor = new GupAbschnittsinfoEditor();
    private GupEntwicklungszielEditor entwicklungszielEditor = new GupEntwicklungszielEditor(true);
    private GupUnterhaltungserfordernisEditor unterhaltungserfordernisEditor = new GupUnterhaltungserfordernisEditor(
            true);
    private GupUnterhaltungsmassnahmeEditor massnahmeEditor;
    private GupUmlandnutzungEditor umlandnutzungEditor = new GupUmlandnutzungEditor(true);
    private SchutzgebietEditor schutzgebietEditor = new SchutzgebietEditor(true);
    private GupOperativesZielAbschnittEditor operativesZielEditor = new GupOperativesZielAbschnittEditor(true);
    private VermeidungsgruppenAbschnittEditor verbreitungsraumEditor = new VermeidungsgruppenAbschnittEditor(true);
    private GupGewaesserabschnittAllgemein allgemeinEditor;
    private GupGewaesserWrrl wrrlEditor = new GupGewaesserWrrl();
    private GupHydrologieEditor hydroEditor = new GupHydrologieEditor();
    private boolean readOnly = false;
    private CidsBean route;
    private UnterhaltungsmaßnahmeValidator validator = new UnterhaltungsmaßnahmeValidator();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrpDetails;
    private javax.swing.JCheckBox chkEntwicklungsziel;
    private javax.swing.JCheckBox chkMassnahmen;
    private javax.swing.JCheckBox chkNaturschutz;
    private javax.swing.JCheckBox chkOperativeZiele;
    private javax.swing.JCheckBox chkQuerbauwerke;
    private javax.swing.JCheckBox chkSonstigeMassnahmen;
    private javax.swing.JCheckBox chkUmlandnutzung;
    private javax.swing.JCheckBox chkUnterhaltungserfordernis;
    private javax.swing.JCheckBox chkVerbreitungsraum;
    private javax.swing.JCheckBox chkWasserkoerper;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton jbApply;
    private javax.swing.JButton jbApply1;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblSubTitle;
    private javax.swing.JLabel lblTitle;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panAbschnittsinfo;
    private javax.swing.JPanel panAllgemein;
    private javax.swing.JPanel panApply;
    private javax.swing.JPanel panApplyBand;
    private javax.swing.JPanel panBand;
    private javax.swing.JPanel panBandControl;
    private javax.swing.JPanel panEmpty;
    private javax.swing.JPanel panEntwicklungsziel;
    private javax.swing.JPanel panFiller1;
    private javax.swing.JPanel panFiller2;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private javax.swing.JPanel panHeader;
    private javax.swing.JPanel panHeaderInfo;
    private javax.swing.JPanel panHydro;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panMassnahme;
    private javax.swing.JPanel panMassnahmeSonstige;
    private javax.swing.JPanel panNew;
    private javax.swing.JPanel panOperativeZiele;
    private javax.swing.JPanel panSchutzgebiet;
    private javax.swing.JPanel panTitle;
    private javax.swing.JPanel panUmlandnutzung;
    private javax.swing.JPanel panUnterhaltungserfordernis;
    private javax.swing.JPanel panVerbreitungsraum;
    private javax.swing.JPanel panVermessung;
    private javax.swing.JPanel panWRRL;
    private javax.swing.JSlider sldZoom;
    private javax.swing.JScrollPane spBand;
    private javax.swing.JToggleButton togAllgemeinInfo;
    private javax.swing.JToggleButton togApplyStats;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GupGewaesserabschnittEditor object.
     */
    public GupPlanungsabschnittEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupPlanungsabschnittEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        jband = new JBand(readOnly);
        initComponents();

//        chkOperativeZiele.setVisible(false);

        if (readOnly) {
            togApplyStats.setVisible(false);
        }

        spBand.getViewport().setOpaque(false);
        massnahmeEditor = new GupUnterhaltungsmassnahmeEditor(readOnly);
        allgemeinEditor = new GupGewaesserabschnittAllgemein(readOnly);
        rechtesUferBand.setReadOnly(readOnly);
        linkesUferBand.setReadOnly(readOnly);
        rechtesUmfeldBand.setReadOnly(readOnly);
        linkesUmfeldBand.setReadOnly(readOnly);
        sohleBand.setReadOnly(readOnly);
        unterhaltungserfordernisBand.setEnabled(false);
        entwicklungszielBand.setEnabled(false);
        querbauwerksband.setEnabled(false);
        anwohnerLinksBand.setEnabled(false);
        anwohnerRechtsBand.setEnabled(false);

        rechtesUmfeldBand.setEnabled(false);
        linkesUmfeldBand.setEnabled(false);
        nutzungLinksBand.setEnabled(false);
        nutzungRechtsBand.setEnabled(false);
        schutzgebietRechtsBand.setEnabled(false);
        schutzgebietLinksBand.setEnabled(false);
        verbreitungsraumRechtsBand.setEnabled(false);
        verbreitungsraumLinksBand.setEnabled(false);
        verbreitungsraumSohleBand.setEnabled(false);
        operativeZieleRechtsBand.setEnabled(false);
        operativeZieleLinksBand.setEnabled(false);
        operativeZieleSohleBand.setEnabled(false);
        operativeZieleUmfeldLinksBand.setEnabled(false);
        operativeZieleUmfeldRechtsBand.setEnabled(false);

        sohleBand.setMeasureType(GUP_MASSNAHME_SOHLE);
        rechtesUferBand.setMeasureType(GUP_MASSNAHME_UFER_RECHTS);
        linkesUferBand.setMeasureType(GUP_MASSNAHME_UFER_LINKS);
        rechtesUmfeldBand.setMeasureType(GUP_MASSNAHME_UMFELD_RECHTS);
        linkesUmfeldBand.setMeasureType(GUP_MASSNAHME_UMFELD_LINKS);
        wkband = new WKBand(sbm.getMin(), sbm.getMax());
        vwkband = new WKBand(sbm.getMin(), sbm.getMax());

        sbm.addBand(wkband);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
        sbm.addBand(entwicklungszielBand);
        sbm.addBand(unterhaltungserfordernisBand);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
        sbm.addBand(verbreitungsraumRechtsBand);
        sbm.addBand(verbreitungsraumSohleBand);
        sbm.addBand(verbreitungsraumLinksBand);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
        sbm.addBand(operativeZieleUmfeldRechtsBand);
        sbm.addBand(operativeZieleRechtsBand);
        sbm.addBand(operativeZieleSohleBand);
        sbm.addBand(operativeZieleLinksBand);
        sbm.addBand(operativeZieleUmfeldLinksBand);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
        sbm.addBand(schutzgebietRechtsBand);
        sbm.addBand(nutzungRechtsBand);
        sbm.addBand(anwohnerRechtsBand);
        sbm.addBand(rechtesUmfeldBand);
        sbm.addBand(rechtesUferBand);
        sbm.addBand(sohleBand);
        sbm.addBand(linkesUferBand);
        sbm.addBand(linkesUmfeldBand);
        sbm.addBand(anwohnerLinksBand);
        sbm.addBand(nutzungLinksBand);
        sbm.addBand(schutzgebietLinksBand);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
        sbm.addBand(new EmptyAbsoluteHeightedBand(5));
        sbm.addBand(querbauwerksband);
        sbm.addBand(new EmptyAbsoluteHeightedBand(12));

        jband.setModel(sbm);

        panBand.add(jband, BorderLayout.CENTER);
        jband.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        switchToForm("allgemein");
        lblHeading.setText("Allgemeine Informationen");
        panAbschnittsinfo.add(abschnittsinfoEditor, BorderLayout.CENTER);
        panEntwicklungsziel.add(entwicklungszielEditor, BorderLayout.CENTER);
        panUnterhaltungserfordernis.add(unterhaltungserfordernisEditor, BorderLayout.CENTER);
        panVermessung.add(vermessungsEditor, BorderLayout.CENTER);
        panMassnahme.add(massnahmeEditor);
        panUmlandnutzung.add(umlandnutzungEditor, BorderLayout.CENTER);
        panSchutzgebiet.add(schutzgebietEditor, BorderLayout.CENTER);
        panOperativeZiele.add(operativesZielEditor, BorderLayout.CENTER);
        panVerbreitungsraum.add(verbreitungsraumEditor, BorderLayout.CENTER);
        panAllgemein.add(allgemeinEditor, BorderLayout.CENTER);
        panWRRL.add(wrrlEditor, BorderLayout.CENTER);
        panHydro.add(hydroEditor, BorderLayout.CENTER);

        sbm.addBandModelListener(modelListener);

        if (!readOnly) {
            vBand.setModel(vBandModel);
            vBandModel.addBand(vwkband);
            vBandModel.addBand(new EmptyAbsoluteHeightedBand(5));
            vBandModel.addBand(vermessungsBand);
            vBandModel.addBandModelListener(modelListener);
        }

        sldZoom.setPaintTrack(false);

        CismetThreadPool.execute(new Runnable() {

                private final MetaClass MASSNAHMENART_MC = ClassCacheMultiple.getMetaClass(
                        WRRLUtil.DOMAIN_NAME,
                        "gup_massnahmenart");

                @Override
                public void run() {
                    try {
                        final String query = "select " + MASSNAHMENART_MC.getID() + ","
                                    + MASSNAHMENART_MC.getPrimaryKey()
                                    + " from " + MASSNAHMENART_MC.getTableName();
                        final MetaObject[] mo = MetaObjectCache.getInstance().getMetaObjectsByQuery(query);

                        EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    massnahmeEditor.setMassnahmnenObjects(mo);
                                }
                            });
                    } catch (Exception e) {
                        LOG.error("Error while retrieving massnahmen objects.", e);
                    }
                }
            });
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  c  DOCUMENT ME!
     */
    private void normalizeDimensions(final JComponent c) {
        c.setMinimumSize(new Dimension(1, 1));
        c.setPreferredSize(new Dimension(1, 1));
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

        switchToForm("empty");
        lblHeading.setText("");

        if (cidsBean != null) {
            if (cidsBean.getProperty("linie") == null) {
                panBand.removeAll();
                final DefaultMetaTreeNode dmtn = (DefaultMetaTreeNode)ComponentRegistry.getRegistry().getCatalogueTree()
                            .getSelectedNode();
                final ObjectTreeNode node = (ObjectTreeNode)dmtn.getParent();

                try {
                    cidsBean.setProperty("gup", node.getMetaObject().getBean());
                } catch (Exception e) {
                    LOG.error("Error while setting the gup id.", e);
                }
                panBand.add(panNew, BorderLayout.CENTER);
                linearReferencedLineEditor.setLineField("linie");
                linearReferencedLineEditor.setCidsBean(cidsBean);
                togApplyStats.setEnabled(false);
            } else {
                showRoute();
                setNamesAndBands();
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void showRoute() {
        final CidsBean station = (CidsBean)cidsBean.getProperty("linie.von");
        if ((station != null) && !readOnly) {
            final Geometry routeGeometry = LinearReferencingHelper.getRouteGeometryFromStationBean(station);
            route = LinearReferencingHelper.getRouteBeanFromStationBean(station);
            routeFeature = FeatureRegistry.getInstance().addRouteFeature(route, routeGeometry);
            final MappingComponent map = CismapBroker.getInstance().getMappingComponent();
            if (!map.isFixedMapExtent()) {
                map.zoomToAFeatureCollection(map.getFeatureCollection().getAllFeatures(),
                    true,
                    map.isFixedMapScale());
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setNamesAndBands() {
        final CidsBean route = LinearReferencingHelper.getRouteBeanFromStationBean((CidsBean)cidsBean.getProperty(
                    "linie.von"));
        final double from = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)cidsBean.getProperty(
                    "linie.von"));
        final double till = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)cidsBean.getProperty(
                    "linie.bis"));
        sbm.setMin(from);
        sbm.setMax(till);
        wkband.setMinMax(from, till);
        vwkband.setMinMax(from, till);
        jband.setMinValue(from);
        jband.setMaxValue(till);
        rechtesUferBand.setRoute(route);
        sohleBand.setRoute(route);
        linkesUferBand.setRoute(route);
        rechtesUmfeldBand.setRoute(route);
        linkesUmfeldBand.setRoute(route);

        final List<CidsBean> all = cidsBean.getBeanCollectionProperty("massnahmen");
        rechtesUferList = new ArrayList<CidsBean>();
        sohleList = new ArrayList<CidsBean>();
        linkesUferList = new ArrayList<CidsBean>();
        rechtesUmfeldList = new ArrayList<CidsBean>();
        linkesUmfeldList = new ArrayList<CidsBean>();

        for (final CidsBean tmp : all) {
            final Integer kind = (Integer)tmp.getProperty("wo.id");

            switch (kind) {
                case GUP_MASSNAHME_UFER_LINKS: {
                    linkesUferList.add(tmp);
                    break;
                }
                case GUP_MASSNAHME_UFER_RECHTS: {
                    rechtesUferList.add(tmp);
                    break;
                }
                case GUP_MASSNAHME_UMFELD_LINKS: {
                    linkesUmfeldList.add(tmp);
                    break;
                }
                case GUP_MASSNAHME_UMFELD_RECHTS: {
                    rechtesUmfeldList.add(tmp);
                    break;
                }
                case GUP_MASSNAHME_SOHLE: {
                    sohleList.add(tmp);
                    break;
                }
            }
        }

        // Massnahmen extrahieren
        rechtesUferList = ObservableCollections.observableList(rechtesUferList);
        linkesUferList = ObservableCollections.observableList(linkesUferList);
        sohleList = ObservableCollections.observableList(sohleList);
        rechtesUmfeldList = ObservableCollections.observableList(rechtesUmfeldList);
        linkesUmfeldList = ObservableCollections.observableList(linkesUmfeldList);

        ((ObservableList<CidsBean>)rechtesUferList).addObservableListListener(new MassnBezugListListener(
                GUP_MASSNAHME_UFER_RECHTS,
                cidsBean,
                "massnahmen"));
        ((ObservableList<CidsBean>)linkesUferList).addObservableListListener(new MassnBezugListListener(
                GUP_MASSNAHME_UFER_LINKS,
                cidsBean,
                "massnahmen"));
        ((ObservableList<CidsBean>)rechtesUmfeldList).addObservableListListener(new MassnBezugListListener(
                GUP_MASSNAHME_UMFELD_RECHTS,
                cidsBean,
                "massnahmen"));
        ((ObservableList<CidsBean>)linkesUmfeldList).addObservableListListener(new MassnBezugListListener(
                GUP_MASSNAHME_UMFELD_LINKS,
                cidsBean,
                "massnahmen"));
        ((ObservableList<CidsBean>)sohleList).addObservableListListener(new MassnBezugListListener(
                GUP_MASSNAHME_SOHLE,
                cidsBean,
                "massnahmen"));

        rechtesUferBand.setCidsBeans(rechtesUferList);
        sohleBand.setCidsBeans(sohleList);
        linkesUferBand.setCidsBeans(linkesUferList);
        rechtesUmfeldBand.setCidsBeans(rechtesUmfeldList);
        linkesUmfeldBand.setCidsBeans(linkesUmfeldList);

        allgemeinEditor.setCidsBean(cidsBean);
        wrrlEditor.setCidsBean(cidsBean);

        final String rname = String.valueOf(route.getProperty("routenname"));

        lblSubTitle.setText(rname + " [" + (int)sbm.getMin() + "," + (int)sbm.getMax() + "]");

        loadExternalData(route);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  route  DOCUMENT ME!
     */
    private void loadExternalData(final CidsBean route) {
        validator = new UnterhaltungsmaßnahmeValidator();
        massnahmeEditor.setValidator(validator);

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<ArrayList<ArrayList>, Void>() {

                @Override
                protected ArrayList<ArrayList> doInBackground() throws Exception {
                    final CidsServerSearch searchWK = new WkSearchByStations(
                            sbm.getMin(),
                            sbm.getMax(),
                            String.valueOf(route.getProperty("gwk")));

                    final Collection resWK = SessionManager.getProxy()
                                .customServerSearch(SessionManager.getSession().getUser(), searchWK);
                    return (ArrayList<ArrayList>)resWK;
                }

                @Override
                protected void done() {
                    try {
                        final ArrayList<ArrayList> res = get();
                        wkband.setWK(res);
                        if (!readOnly) {
                            vwkband.setWK(res);
                            vBandModel.fireBandModelChanged();
                        }
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                        updateUI();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Wasserkörper", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<ArrayList<ArrayList>, Void>() {

                @Override
                protected ArrayList<ArrayList> doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    return querbauwerkCache.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        chkQuerbauwerke.setEnabled(true);
                        querbauwerksband.addQuerbauwerkeFromQueryResult(get());
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Querbauwerke", e);
                    }
                }
            });

//        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<ArrayList<ArrayList>, Void>() {
//
//                @Override
//                protected ArrayList<ArrayList> doInBackground() throws Exception {
//                    final List in = new ArrayList(3);
//                    in.add(sbm.getMin());
//                    in.add(sbm.getMax());
//                    in.add(route.getProperty("gwk"));
//                    return naturschutzCache.calcValue(in);
//                }
//
//                @Override
//                protected void done() {
//                    try {
//                        chkNaturschutz.setEnabled(true);
//                        naturchutzBand.addMembersFromQueryResult(get());
//                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
//                    } catch (Exception e) {
//                        LOG.error("Problem beim Suchen der Naturschutzgebiete", e);
//                    }
//                }
//            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<MetaObject[], Void>() {

                @Override
                protected MetaObject[] doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    return schutzgebietCache.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        final MetaObject[] result = get();
                        final List<CidsBean> beansLeft = new ArrayList<CidsBean>();
                        final List<CidsBean> beansRight = new ArrayList<CidsBean>();
                        validator.setSchutzgebiete(result);

                        for (final MetaObject tmp : result) {
                            final CidsBean side = (CidsBean)tmp.getBean().getProperty("wo");

                            if (side != null) {
                                if ((Integer)side.getProperty("id") == GUP_MASSNAHME_UFER_RECHTS) {
                                    beansRight.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == GUP_MASSNAHME_UFER_LINKS) {
                                    beansLeft.add(tmp.getBean());
                                }
                            }
                        }

                        schutzgebietLinksBand.setCidsBeans(beansLeft);
                        schutzgebietRechtsBand.setCidsBeans(beansRight);
                        chkNaturschutz.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Schutzgebiete", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<MetaObject[], Void>() {

                @Override
                protected MetaObject[] doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    return verbreitungsraumCache.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        final MetaObject[] result = get();
                        final List<CidsBean> beansLeft = new ArrayList<CidsBean>();
                        final List<CidsBean> beansRight = new ArrayList<CidsBean>();
                        final List<CidsBean> beansMiddle = new ArrayList<CidsBean>();
                        validator.setVerbreitungsraum(result);

                        for (final MetaObject tmp : result) {
                            final CidsBean side = (CidsBean)tmp.getBean().getProperty("wo");

                            if (side != null) {
                                if ((Integer)side.getProperty("id") == GUP_MASSNAHME_UFER_RECHTS) {
                                    beansRight.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == GUP_MASSNAHME_UFER_LINKS) {
                                    beansLeft.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == GUP_MASSNAHME_SOHLE) {
                                    beansMiddle.add(tmp.getBean());
                                }
                            }
                        }

                        verbreitungsraumLinksBand.setCidsBeans(beansLeft);
                        verbreitungsraumRechtsBand.setCidsBeans(beansRight);
                        verbreitungsraumSohleBand.setCidsBeans(beansMiddle);
                        chkVerbreitungsraum.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Verbreitungsräume", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<MetaObject[], Void>() {

                @Override
                protected MetaObject[] doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    return operativeZieleCache.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        final MetaObject[] result = get();
                        final List<CidsBean> beansLeft = new ArrayList<CidsBean>();
                        final List<CidsBean> beansRight = new ArrayList<CidsBean>();
                        final List<CidsBean> beansMiddle = new ArrayList<CidsBean>();
                        final List<CidsBean> beansUmLeft = new ArrayList<CidsBean>();
                        final List<CidsBean> beansUmRight = new ArrayList<CidsBean>();
                        validator.setOperativeZiele(result);

                        for (final MetaObject tmp : result) {
                            final CidsBean side = (CidsBean)tmp.getBean().getProperty("wo");

                            if (side != null) {
                                if ((Integer)side.getProperty("id") == GUP_MASSNAHME_UFER_RECHTS) {
                                    beansRight.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == GUP_MASSNAHME_UFER_LINKS) {
                                    beansLeft.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == GUP_MASSNAHME_SOHLE) {
                                    beansMiddle.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == GUP_MASSNAHME_UMFELD_LINKS) {
                                    beansUmLeft.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == GUP_MASSNAHME_UMFELD_RECHTS) {
                                    beansUmRight.add(tmp.getBean());
                                }
                            }
                        }

                        operativeZieleLinksBand.setCidsBeans(beansLeft);
                        operativeZieleRechtsBand.setCidsBeans(beansRight);
                        operativeZieleSohleBand.setCidsBeans(beansMiddle);
                        operativeZieleUmfeldLinksBand.setCidsBeans(beansUmLeft);
                        operativeZieleUmfeldRechtsBand.setCidsBeans(beansUmRight);
                        chkOperativeZiele.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Operativen Ziele", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<MetaObject[], Void>() {

                @Override
                protected MetaObject[] doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    return umlandCache.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        final MetaObject[] result = get();
                        final List<CidsBean> beansLeft = new ArrayList<CidsBean>();
                        final List<CidsBean> beansRight = new ArrayList<CidsBean>();

                        for (final MetaObject tmp : result) {
                            final CidsBean side = (CidsBean)tmp.getBean().getProperty("seite");

                            if (side != null) {
                                if ((Integer)side.getProperty("id") == 1) {
                                    beansRight.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == 2) {
                                    beansLeft.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == 3) {
                                    beansRight.add(tmp.getBean());
                                    beansLeft.add(tmp.getBean());
                                }
                            }
                        }

                        nutzungLinksBand.setCidsBeans(beansLeft);
                        nutzungRechtsBand.setCidsBeans(beansRight);
                        chkUmlandnutzung.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Umlandnutzung", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<MetaObject[], Void>() {

                @Override
                protected MetaObject[] doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    return entwicklungszielCache.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        final MetaObject[] result = get();
                        final List<CidsBean> beans = new ArrayList<CidsBean>();

                        for (final MetaObject tmp : result) {
                            beans.add(tmp.getBean());
                        }

                        entwicklungszielBand.setCidsBeans(beans);
                        chkEntwicklungsziel.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Entwicklungsziele", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<MetaObject[], Void>() {

                @Override
                protected MetaObject[] doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    return unterhaltungserfordernisCache.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        final MetaObject[] result = get();
                        final List<CidsBean> beans = new ArrayList<CidsBean>();

                        for (final MetaObject tmp : result) {
                            beans.add(tmp.getBean());
                        }

                        unterhaltungserfordernisBand.setCidsBeans(beans);
                        chkUnterhaltungserfordernis.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Unterhaltungserfordernisse", e);
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  metaObjects  DOCUMENT ME!
     * @param  min          DOCUMENT ME!
     * @param  max          DOCUMENT ME!
     */
    private static void adjustBorders(final MetaObject[] metaObjects, final double min, final double max) {
        if (metaObjects != null) {
            for (final MetaObject tmp : metaObjects) {
                final double von = (Double)tmp.getBean().getProperty("linie.von.wert");
                final double bis = (Double)tmp.getBean().getProperty("linie.bis.wert");

                try {
                    if (von < min) {
                        tmp.getBean().setProperty("linie.von.wert", min);
                    }
                    if (bis > max) {
                        tmp.getBean().setProperty("linie.bis.wert", max);
                    }
                } catch (Exception e) {
                    LOG.error("Cannot adjust the station value", e);
                }
            }
        }
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public JComponent getTitleComponent() {
        return panTitle;
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
        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        togAllgemeinInfo = new javax.swing.JToggleButton();
        togApplyStats = new javax.swing.JToggleButton();
        panNew = new javax.swing.JPanel();
        linearReferencedLineEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor();
        jbApply = new javax.swing.JButton();
        panApply = new javax.swing.JPanel();
        jbApply1 = new javax.swing.JButton();
        panApplyBand = new javax.swing.JPanel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panEntwicklungsziel = new javax.swing.JPanel();
        panUnterhaltungserfordernis = new javax.swing.JPanel();
        panMassnahmeSonstige = new javax.swing.JPanel();
        panAbschnittsinfo = new javax.swing.JPanel();
        panEmpty = new javax.swing.JPanel();
        panWRRL = new javax.swing.JPanel();
        panHydro = new javax.swing.JPanel();
        panAllgemein = new javax.swing.JPanel();
        panUmlandnutzung = new javax.swing.JPanel();
        panMassnahme = new javax.swing.JPanel();
        panVermessung = new javax.swing.JPanel();
        panSchutzgebiet = new javax.swing.JPanel();
        panVerbreitungsraum = new javax.swing.JPanel();
        panOperativeZiele = new javax.swing.JPanel();
        panHeader = new javax.swing.JPanel();
        panHeaderInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblSubTitle = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        sldZoom = new javax.swing.JSlider();
        panBand = new javax.swing.JPanel();
        spBand = new javax.swing.JScrollPane();
        panBandControl = new RoundedPanel();
        chkMassnahmen = new javax.swing.JCheckBox();
        chkSonstigeMassnahmen = new javax.swing.JCheckBox();
        chkWasserkoerper = new javax.swing.JCheckBox();
        chkUmlandnutzung = new javax.swing.JCheckBox();
        chkQuerbauwerke = new javax.swing.JCheckBox();
        chkNaturschutz = new javax.swing.JCheckBox();
        chkUnterhaltungserfordernis = new javax.swing.JCheckBox();
        chkEntwicklungsziel = new javax.swing.JCheckBox();
        chkVerbreitungsraum = new javax.swing.JCheckBox();
        chkOperativeZiele = new javax.swing.JCheckBox();
        panFiller1 = new javax.swing.JPanel();
        panFiller2 = new javax.swing.JPanel();
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

        panTitle.setMinimumSize(new java.awt.Dimension(1050, 36));
        panTitle.setOpaque(false);
        panTitle.setPreferredSize(new java.awt.Dimension(1050, 36));
        panTitle.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("GUP Gewässerabschnitt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTitle.add(lblTitle, gridBagConstraints);

        bgrpDetails.add(togAllgemeinInfo);
        togAllgemeinInfo.setText("Allgemeine Infos");
        togAllgemeinInfo.setPreferredSize(new java.awt.Dimension(117, 44));
        togAllgemeinInfo.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    togAllgemeinInfoActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 7);
        panTitle.add(togAllgemeinInfo, gridBagConstraints);

        togApplyStats.setText("Vermessen");
        togApplyStats.setPreferredSize(new java.awt.Dimension(117, 44));
        togApplyStats.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    togApplyStatsActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 7);
        panTitle.add(togApplyStats, gridBagConstraints);

        panNew.setOpaque(false);
        panNew.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        panNew.add(linearReferencedLineEditor, gridBagConstraints);

        jbApply.setText(org.openide.util.NbBundle.getMessage(
                GupPlanungsabschnittEditor.class,
                "GupGewaesserabschnitt")); // NOI18N
        jbApply.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbApplyActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panNew.add(jbApply, gridBagConstraints);

        panApply.setOpaque(false);
        panApply.setLayout(new java.awt.GridBagLayout());

        jbApply1.setText(org.openide.util.NbBundle.getMessage(
                GupPlanungsabschnittEditor.class,
                "GupGewaesserabschnitt")); // NOI18N
        jbApply1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbApply1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 5, 5);
        panApply.add(jbApply1, gridBagConstraints);

        panApplyBand.setOpaque(false);
        panApplyBand.setPreferredSize(new java.awt.Dimension(300, 100));
        panApplyBand.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panApply.add(panApplyBand, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1050, 700));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1050, 700));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(640, 310));
        panInfo.setPreferredSize(new java.awt.Dimension(640, 310));

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

        panEntwicklungsziel.setOpaque(false);
        panEntwicklungsziel.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panEntwicklungsziel, "entwicklungsziel");

        panUnterhaltungserfordernis.setOpaque(false);
        panUnterhaltungserfordernis.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panUnterhaltungserfordernis, "unterhaltungserfordernis");

        panMassnahmeSonstige.setOpaque(false);
        panMassnahmeSonstige.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panMassnahmeSonstige, "massnahmesonstige");

        panAbschnittsinfo.setOpaque(false);
        panAbschnittsinfo.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panAbschnittsinfo, "abschnittsinfo");

        panEmpty.setOpaque(false);
        panEmpty.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panEmpty, "empty");

        panWRRL.setOpaque(false);
        panWRRL.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panWRRL, "wrrl");

        panHydro.setOpaque(false);
        panHydro.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panHydro, "hydro");

        panAllgemein.setOpaque(false);
        panAllgemein.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panAllgemein, "allgemein");

        panUmlandnutzung.setOpaque(false);
        panUmlandnutzung.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panUmlandnutzung, "umlandnutzung");

        panMassnahme.setOpaque(false);
        panMassnahme.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panMassnahme, "massnahme");

        panVermessung.setOpaque(false);
        panVermessung.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panVermessung, "vermessung");

        panSchutzgebiet.setOpaque(false);
        panSchutzgebiet.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panSchutzgebiet, "schutzgebiet");

        panVerbreitungsraum.setOpaque(false);
        panVerbreitungsraum.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panVerbreitungsraum, "verbreitungsraum");

        panOperativeZiele.setOpaque(false);
        panOperativeZiele.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panOperativeZiele, "operativeZiele");

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

        panHeaderInfo.setMinimumSize(new java.awt.Dimension(431, 102));
        panHeaderInfo.setOpaque(false);
        panHeaderInfo.setPreferredSize(new java.awt.Dimension(431, 102));
        panHeaderInfo.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setText("GUP:");
        jLabel1.setMinimumSize(new java.awt.Dimension(91, 22));
        panHeaderInfo.add(jLabel1);
        jLabel1.setBounds(12, 12, 45, 22);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel2.setText("Demo-GUP");
        panHeaderInfo.add(jLabel2);
        jLabel2.setBounds(110, 12, 250, 22);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel3.setText("Gewässer:");
        jLabel3.setMinimumSize(new java.awt.Dimension(91, 22));
        panHeaderInfo.add(jLabel3);
        jLabel3.setBounds(12, 40, 92, 22);

        lblSubTitle.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        panHeaderInfo.add(lblSubTitle);
        lblSubTitle.setBounds(110, 40, 250, 0);

        jLabel5.setFont(new java.awt.Font("Lucida Sans", 0, 18)); // NOI18N
        jLabel5.setText("Zoom:");
        jLabel5.setMaximumSize(new java.awt.Dimension(92, 22));
        jLabel5.setMinimumSize(new java.awt.Dimension(92, 22));
        jLabel5.setPreferredSize(new java.awt.Dimension(92, 22));
        panHeaderInfo.add(jLabel5);
        jLabel5.setBounds(12, 68, 80, 20);

        sldZoom.setMaximum(200);
        sldZoom.setValue(0);
        sldZoom.addChangeListener(new javax.swing.event.ChangeListener() {

                @Override
                public void stateChanged(final javax.swing.event.ChangeEvent evt) {
                    sldZoomStateChanged(evt);
                }
            });
        panHeaderInfo.add(sldZoom);
        sldZoom.setBounds(110, 72, 250, 16);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        panHeader.add(panHeaderInfo, gridBagConstraints);

        panBand.setOpaque(false);
        panBand.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panHeader.add(panBand, gridBagConstraints);

        spBand.setBorder(null);
        spBand.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spBand.setViewportBorder(null);
        spBand.setMinimumSize(new java.awt.Dimension(500, 100));
        spBand.setOpaque(false);
        spBand.setPreferredSize(new java.awt.Dimension(500, 100));

        panBandControl.setPreferredSize(new java.awt.Dimension(490, 95));
        panBandControl.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 2));

        chkMassnahmen.setSelected(true);
        chkMassnahmen.setText("Maßnahmen (links, Sohle, rechts)");
        chkMassnahmen.setContentAreaFilled(false);
        chkMassnahmen.setPreferredSize(new java.awt.Dimension(240, 22));
        chkMassnahmen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkMassnahmenActionPerformed(evt);
                }
            });
        panBandControl.add(chkMassnahmen);

        chkSonstigeMassnahmen.setText("Umfeld Maßnahmen");
        chkSonstigeMassnahmen.setContentAreaFilled(false);
        chkSonstigeMassnahmen.setPreferredSize(new java.awt.Dimension(160, 22));
        chkSonstigeMassnahmen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkSonstigeMassnahmenActionPerformed(evt);
                }
            });
        panBandControl.add(chkSonstigeMassnahmen);

        chkWasserkoerper.setSelected(true);
        chkWasserkoerper.setText("Wasserkörper");
        chkWasserkoerper.setContentAreaFilled(false);
        chkWasserkoerper.setPreferredSize(new java.awt.Dimension(160, 22));
        chkWasserkoerper.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkWasserkoerperActionPerformed(evt);
                }
            });
        panBandControl.add(chkWasserkoerper);

        chkUmlandnutzung.setText("Umlandnutzung");
        chkUmlandnutzung.setContentAreaFilled(false);
        chkUmlandnutzung.setEnabled(false);
        chkUmlandnutzung.setPreferredSize(new java.awt.Dimension(240, 22));
        chkUmlandnutzung.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkUmlandnutzungActionPerformed(evt);
                }
            });
        panBandControl.add(chkUmlandnutzung);

        chkQuerbauwerke.setText("Querbauwerke");
        chkQuerbauwerke.setContentAreaFilled(false);
        chkQuerbauwerke.setEnabled(false);
        chkQuerbauwerke.setPreferredSize(new java.awt.Dimension(160, 22));
        chkQuerbauwerke.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkQuerbauwerkeActionPerformed(evt);
                }
            });
        panBandControl.add(chkQuerbauwerke);

        chkNaturschutz.setText("Schutzgebiete");
        chkNaturschutz.setContentAreaFilled(false);
        chkNaturschutz.setEnabled(false);
        chkNaturschutz.setPreferredSize(new java.awt.Dimension(160, 22));
        chkNaturschutz.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkNaturschutzActionPerformed(evt);
                }
            });
        panBandControl.add(chkNaturschutz);

        chkUnterhaltungserfordernis.setText("Unterhaltungserfordernis");
        chkUnterhaltungserfordernis.setContentAreaFilled(false);
        chkUnterhaltungserfordernis.setEnabled(false);
        chkUnterhaltungserfordernis.setPreferredSize(new java.awt.Dimension(240, 22));
        chkUnterhaltungserfordernis.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkUnterhaltungserfordernisActionPerformed(evt);
                }
            });
        panBandControl.add(chkUnterhaltungserfordernis);

        chkEntwicklungsziel.setText("Entwicklungsziel");
        chkEntwicklungsziel.setContentAreaFilled(false);
        chkEntwicklungsziel.setEnabled(false);
        chkEntwicklungsziel.setPreferredSize(new java.awt.Dimension(160, 22));
        chkEntwicklungsziel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkEntwicklungszielActionPerformed(evt);
                }
            });
        panBandControl.add(chkEntwicklungsziel);

        chkVerbreitungsraum.setText("Verbreitungsraum");
        chkVerbreitungsraum.setContentAreaFilled(false);
        chkVerbreitungsraum.setEnabled(false);
        chkVerbreitungsraum.setPreferredSize(new java.awt.Dimension(160, 22));
        chkVerbreitungsraum.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkVerbreitungsraumActionPerformed(evt);
                }
            });
        panBandControl.add(chkVerbreitungsraum);

        chkOperativeZiele.setText("Operative Ziele");
        chkOperativeZiele.setContentAreaFilled(false);
        chkOperativeZiele.setEnabled(false);
        chkOperativeZiele.setPreferredSize(new java.awt.Dimension(240, 22));
        chkOperativeZiele.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkOperativeZieleActionPerformed(evt);
                }
            });
        panBandControl.add(chkOperativeZiele);

        panFiller1.setOpaque(false);
        panFiller1.setPreferredSize(new java.awt.Dimension(160, 22));

        final javax.swing.GroupLayout panFiller1Layout = new javax.swing.GroupLayout(panFiller1);
        panFiller1.setLayout(panFiller1Layout);
        panFiller1Layout.setHorizontalGroup(
            panFiller1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                160,
                Short.MAX_VALUE));
        panFiller1Layout.setVerticalGroup(
            panFiller1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                22,
                Short.MAX_VALUE));

        panBandControl.add(panFiller1);

        panFiller2.setOpaque(false);
        panFiller2.setPreferredSize(new java.awt.Dimension(160, 22));

        final javax.swing.GroupLayout panFiller2Layout = new javax.swing.GroupLayout(panFiller2);
        panFiller2.setLayout(panFiller2Layout);
        panFiller2Layout.setHorizontalGroup(
            panFiller2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                160,
                Short.MAX_VALUE));
        panFiller2Layout.setVerticalGroup(
            panFiller2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                22,
                Short.MAX_VALUE));

        panBandControl.add(panFiller2);

        spBand.setViewportView(panBandControl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 4);
        panHeader.add(spBand, gridBagConstraints);

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
    private void sldZoomStateChanged(final javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldZoomStateChanged
        final double zoom = sldZoom.getValue() / 10d;
        jband.setZoomFactor(zoom);
        vBand.setZoomFactor(zoom);
    }//GEN-LAST:event_sldZoomStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void togAllgemeinInfoActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togAllgemeinInfoActionPerformed
        switchToForm("allgemein");
        lblHeading.setText("Allgemeine Informationen");
        zoomToAbschnitt();
    }//GEN-LAST:event_togAllgemeinInfoActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkQuerbauwerkeActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkQuerbauwerkeActionPerformed
        querbauwerksband.setEnabled(chkQuerbauwerke.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkQuerbauwerkeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkMassnahmenActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMassnahmenActionPerformed
        linkesUferBand.setEnabled(chkMassnahmen.isSelected());
        rechtesUferBand.setEnabled(chkMassnahmen.isSelected());
        sohleBand.setEnabled(chkMassnahmen.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkMassnahmenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkSonstigeMassnahmenActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSonstigeMassnahmenActionPerformed
        rechtesUmfeldBand.setEnabled(chkSonstigeMassnahmen.isSelected());
        linkesUmfeldBand.setEnabled(chkSonstigeMassnahmen.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkSonstigeMassnahmenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkWasserkoerperActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkWasserkoerperActionPerformed
        wkband.setEnabled(chkWasserkoerper.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkWasserkoerperActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkUmlandnutzungActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkUmlandnutzungActionPerformed
        nutzungLinksBand.setEnabled(chkUmlandnutzung.isSelected());
        nutzungRechtsBand.setEnabled(chkUmlandnutzung.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkUmlandnutzungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkNaturschutzActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkNaturschutzActionPerformed
        schutzgebietLinksBand.setEnabled(chkNaturschutz.isSelected());
        schutzgebietRechtsBand.setEnabled(chkNaturschutz.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkNaturschutzActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkUnterhaltungserfordernisActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkUnterhaltungserfordernisActionPerformed
        unterhaltungserfordernisBand.setEnabled((chkUnterhaltungserfordernis.isSelected()));
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkUnterhaltungserfordernisActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkEntwicklungszielActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEntwicklungszielActionPerformed
        entwicklungszielBand.setEnabled(chkEntwicklungsziel.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkEntwicklungszielActionPerformed

    /**
     * DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     */
    private void zoomToAbschnitt() {
        final Geometry g = (Geometry)(cidsBean.getProperty("linie.geom.geo_field"));
        final MappingComponent mc = CismapBroker.getInstance().getMappingComponent();
        final XBoundingBox xbb = new XBoundingBox(g);

        mc.gotoBoundingBoxWithHistory(xbb);
        final DefaultStyledFeature dsf = new DefaultStyledFeature();
        dsf.setGeometry(g);
        dsf.setCanBeSelected(false);
        dsf.setLinePaint(Color.YELLOW);
        dsf.setLineWidth(6);
        final PFeature highlighter = new PFeature(dsf, mc);
        mc.getHighlightingLayer().addChild(highlighter);
        highlighter.animateToTransparency(0.1f, 2000);
        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    Thread.currentThread().sleep(2500);
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        mc.getHighlightingLayer().removeChild(highlighter);
                    } catch (Exception e) {
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbApplyActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbApplyActionPerformed
        panBand.removeAll();
        panBand.add(jband, BorderLayout.CENTER);
        setNamesAndBands();
        linearReferencedLineEditor.dispose();
        showRoute();
        togApplyStats.setEnabled(true);
    }//GEN-LAST:event_jbApplyActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void togApplyStatsActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_togApplyStatsActionPerformed
        if (togApplyStats.isSelected()) {
            showVermessungsband();
        } else {
            hideVermessungsband();
        }
    }//GEN-LAST:event_togApplyStatsActionPerformed

    /**
     * DOCUMENT ME!
     */
    private void showVermessungsband() {
        try {
            final MappingComponent mappingComponent = CismapBroker.getInstance().getMappingComponent();

            // positionen speichern
            final CreateLinearReferencedMarksListener marksListener = (CreateLinearReferencedMarksListener)
                mappingComponent.getInputListener(MappingComponent.LINEAR_REFERENCING);
            final PFeature selectedPFeature = marksListener.getSelectedLinePFeature();
            final Double[] positions = marksListener.getMarkPositionsOfSelectedFeature();
            CidsBean routeBean = null;

            // route bestimmen
            if (selectedPFeature != null) {
                final Feature feature = selectedPFeature.getFeature();
                if ((feature != null) && (feature instanceof CidsFeature)) {
                    final CidsFeature cidsFeature = (CidsFeature)feature;
                    if (cidsFeature.getMetaClass().getName().equals(LinearReferencingConstants.CN_ROUTE)) {
                        routeBean = cidsFeature.getMetaObject().getBean();
                    }
                } else if ((feature != null) && (feature == routeFeature)) {
                    routeBean = (CidsBean)cidsBean.getProperty("linie.von.route");
                }
            }

            // Member hinzufuegen
            if (routeBean != null) {
                final double from = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)
                        cidsBean.getProperty(
                            "linie.von"));
                final double till = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)
                        cidsBean.getProperty(
                            "linie.bis"));
                vermessungsBand.setRoute(routeBean);
                vermessungsBand.removeAllMember();
                Double fromPosition = null;
                CidsBean fromStation = null;
                CidsBean toStation = null;
                for (final double position : positions) {
                    Double toPosition = Math.floor(position);
                    if (toPosition < from) {
                        toPosition = from;
                    } else if (position > till) {
                        toPosition = till;
                    }
                    toStation = LinearReferencingHelper.createStationBeanFromRouteBean(routeBean, (double)toPosition);
                    if ((fromPosition != null) && (toPosition > fromPosition)) {
                        final CidsBean memberBean = CidsBeanSupport.createNewCidsBeanFromTableName(VERMESSUNG);
                        vermessungsBand.addMember(memberBean, fromStation, toStation);
                    }
                    fromPosition = toPosition;
                    fromStation = toStation;
                }
            }

            final double from = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)cidsBean.getProperty(
                        "linie.von"));
            final double till = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)cidsBean.getProperty(
                        "linie.bis"));
            vBand.setMinValue(from);
            vBand.setMaxValue(till);
            panBand.removeAll();
            panApplyBand.removeAll();
            panApplyBand.add(vBand, BorderLayout.CENTER);
            panBand.add(panApply, BorderLayout.CENTER);
            updateUI();
            repaint();
            modelListener.bandModelSelectionChanged(null);
        } catch (Exception e) {
            LOG.error("Error while applying stations.", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void hideVermessungsband() {
        panBand.removeAll();
        panBand.add(jband, BorderLayout.CENTER);
        updateUI();
        modelListener.bandModelSelectionChanged(null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbApply1ActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbApply1ActionPerformed
        try {
            JOptionPane.showConfirmDialog(
                StaticSwingTools.getParentFrame(this),
                "Wenn Sie die Abschnitte übernehmen, dann werden die bereits vorhandenen Abschnitte gelöscht. Wollen Sie fortrfahren?",
                "Stationen übernehmen",
                JOptionPane.OK_CANCEL_OPTION);
            final MassnahmenBand[] bands = new MassnahmenBand[3];
            bands[0] = rechtesUferBand;
            bands[1] = linkesUferBand;
            bands[2] = sohleBand;

            for (final MassnahmenBand tmp : bands) {
                final HashMap<CidsBean, CidsBean> stations = new HashMap<CidsBean, CidsBean>();
                tmp.removeAllMember();
                for (int i = 0; i < vermessungsBand.getNumberOfMembers(); ++i) {
                    final CidsBean bean = ((VermessungsbandMember)vermessungsBand.getMember(i)).getCidsBean();
                    final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName(GUP_MASSNAHME);
                    final CidsBean von = getStationCopy((CidsBean)bean.getProperty("linie.von"), stations);
                    final CidsBean bis = getStationCopy((CidsBean)bean.getProperty("linie.bis"), stations);

                    tmp.addMember(newBean, von, bis);
                }
            }

            togApplyStats.setSelected(false);
            hideVermessungsband();
        } catch (Exception e) {
            LOG.error("Error while creating the initial bands.", e);
        }
    }//GEN-LAST:event_jbApply1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkVerbreitungsraumActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVerbreitungsraumActionPerformed
        verbreitungsraumLinksBand.setEnabled(chkVerbreitungsraum.isSelected());
        verbreitungsraumRechtsBand.setEnabled(chkVerbreitungsraum.isSelected());
        verbreitungsraumSohleBand.setEnabled(chkVerbreitungsraum.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkVerbreitungsraumActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkOperativeZieleActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOperativeZieleActionPerformed
        operativeZieleLinksBand.setEnabled(chkOperativeZiele.isSelected());
        operativeZieleRechtsBand.setEnabled(chkOperativeZiele.isSelected());
        operativeZieleSohleBand.setEnabled(chkOperativeZiele.isSelected());
        operativeZieleUmfeldLinksBand.setEnabled(chkOperativeZiele.isSelected());
        operativeZieleUmfeldRechtsBand.setEnabled(chkOperativeZiele.isSelected());
        sbm.fireBandModelValuesChanged();
    }//GEN-LAST:event_chkOperativeZieleActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param   s         DOCUMENT ME!
     * @param   stations  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getStationCopy(final CidsBean s, final HashMap<CidsBean, CidsBean> stations) {
        CidsBean bean = stations.get(s);

        if (bean == null) {
            bean = LinearReferencingHelper.createStationBeanFromRouteBean((CidsBean)s.getProperty("route"),
                    (Double)s.getProperty("wert"));

            // add the station geometry
            final Geometry geom = LinearReferencedPointFeature.getPointOnLine(LinearReferencingHelper
                            .getLinearValueFromStationBean(bean),
                    LinearReferencingHelper.getRouteGeometryFromStationBean(bean));
            geom.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
            try {
                LinearReferencingHelper.setPointGeometryToStationBean(geom, bean);
            } catch (Exception ex) {
                LOG.error("Cannot create geometry for station", ex);
            }

            stations.put(s, bean);
        }

        return bean;
    }

    @Override
    public void dispose() {
        disposeEditors();
        sbm.removeBandModelListener(modelListener);
        if (route != null) {
            FeatureRegistry.getInstance().removeRouteFeature(route);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void disposeEditors() {
        abschnittsinfoEditor.dispose();
        massnahmeEditor.dispose();
        umlandnutzungEditor.dispose();
        schutzgebietEditor.dispose();
        verbreitungsraumEditor.dispose();
        operativesZielEditor.dispose();
        allgemeinEditor.dispose();
        wrrlEditor.dispose();
        hydroEditor.dispose();
        entwicklungszielEditor.dispose();
        unterhaltungserfordernisEditor.dispose();
        vermessungsEditor.dispose();
    }

    @Override
    public String getTitle() {
        return cidsBean.toString();
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
        DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "x",
            "gup_gewaesserabschnitt",
            1,
            1280,
            1024);
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        linearReferencedLineEditor.editorClosed(event);
        allgemeinEditor.editorClosed(event);
        abschnittsinfoEditor.editorClosed(event);
        massnahmeEditor.editorClosed(event);
        umlandnutzungEditor.editorClosed(event);
        schutzgebietEditor.editorClosed(event);
        verbreitungsraumEditor.editorClosed(event);
        operativesZielEditor.editorClosed(event);
        wrrlEditor.editorClosed(event);
        hydroEditor.editorClosed(event);
        entwicklungszielEditor.editorClosed(event);
        unterhaltungserfordernisEditor.editorClosed(event);
        vermessungsEditor.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        try {
            final CidsBean statLine = (CidsBean)cidsBean.getProperty("linie");
            if ((cidsBean.getProperty("name") == null) && (statLine != null)) {
                final CidsBean statVon = (CidsBean)statLine.getProperty("von");
                if (statVon != null) {
                    final CidsBean route = (CidsBean)statVon.getProperty("route");
                    if (route != null) {
                        final String gewName = route.getProperty("routenname").toString();
                        cidsBean.setProperty("name", gewName);
                    }
                }
            }
        } catch (final Exception e) {
            LOG.error("Error while determining the water body", e);
        }

        return allgemeinEditor.prepareForSave() && linearReferencedLineEditor.prepareForSave();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class GupGewaesserabschnittBandModelListener implements BandModelListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void bandModelChanged(final BandModelEvent e) {
        }

        @Override
        public void bandModelSelectionChanged(final BandModelEvent e) {
            BandMember bm;
            togAllgemeinInfo.setSelected(false);

            if (togApplyStats.isSelected()) {
                bm = vBand.getSelectedBandMember();
                vBand.setRefreshAvoided(true);
            } else {
                bm = jband.getSelectedBandMember();
                jband.setRefreshAvoided(true);
            }

            disposeEditors();
            if (bm != null) {
                bgrpDetails.clearSelection();
                switchToForm("empty");
                lblHeading.setText("");

                if (bm instanceof MassnahmenBandMember) {
                    final CidsBean bean = ((MassnahmenBandMember)bm).getCidsBean();
                    final MassnahmenBand mb = (MassnahmenBand)((MassnahmenBandMember)bm).getParentBand();

                    if (mb.getMeasureType() == GUP_MASSNAHME_SOHLE) {
                        switchToForm("massnahme");
                        lblHeading.setText("Maßnahmen Sohle");
                        massnahmeEditor.setMassnahmen(sohleList);
                        massnahmeEditor.setKompartiment(GupUnterhaltungsmassnahmeEditor.KOMPARTIMENT_SOHLE);
                        massnahmeEditor.setCidsBean(bean);
                    } else if (mb.getMeasureType() == GUP_MASSNAHME_UMFELD_RECHTS) {
                        switchToForm("massnahme");
                        lblHeading.setText("Umfeld rechts");
                        massnahmeEditor.setKompartiment(GupUnterhaltungsmassnahmeEditor.KOMPARTIMENT_UMFELD);
                        massnahmeEditor.setMassnahmen(rechtesUmfeldList);
                        massnahmeEditor.setCidsBean(bean);
                    } else if (mb.getMeasureType() == GUP_MASSNAHME_UMFELD_LINKS) {
                        switchToForm("massnahme");
                        lblHeading.setText("Umfeld links");
                        massnahmeEditor.setKompartiment(GupUnterhaltungsmassnahmeEditor.KOMPARTIMENT_UMFELD);
                        massnahmeEditor.setMassnahmen(linkesUmfeldList);
                        massnahmeEditor.setCidsBean(bean);
                    } else if (mb.getMeasureType() == GUP_MASSNAHME_UFER_RECHTS) {
                        switchToForm("massnahme");
                        lblHeading.setText("Maßnahmen Ufer");

                        massnahmeEditor.setKompartiment(GupUnterhaltungsmassnahmeEditor.KOMPARTIMENT_UFER);
                        massnahmeEditor.setMassnahmen(rechtesUferList);
                        massnahmeEditor.setCidsBean(bean);
                    } else if (mb.getMeasureType() == GUP_MASSNAHME_UFER_LINKS) {
                        switchToForm("massnahme");
                        lblHeading.setText("Maßnahmen Ufer");

                        final List<CidsBean> massnBeans;

                        massnahmeEditor.setKompartiment(GupUnterhaltungsmassnahmeEditor.KOMPARTIMENT_UFER);
                        massnahmeEditor.setMassnahmen(linkesUferList);
                        massnahmeEditor.setCidsBean(bean);
                    }
                } else if (bm instanceof UmlandnutzungBandMember) {
                    switchToForm("umlandnutzung");
                    lblHeading.setText("Umlandnutzung");
                    umlandnutzungEditor.setCidsBean(((UmlandnutzungBandMember)bm).getCidsBean());
                } else if (bm instanceof ColoredReadOnlyBandMember) {
                    if (((ColoredReadOnlyBandMember)bm).getColorProperty().equals("vermeidungsgruppe")) {
                        switchToForm("verbreitungsraum");
                        lblHeading.setText("Verbreitungsraum");
                        verbreitungsraumEditor.setCidsBean(((ColoredReadOnlyBandMember)bm).getCidsBean());
                    } else if (((ColoredReadOnlyBandMember)bm).getColorProperty().equals("operatives_ziel")) {
                        switchToForm("operativeZiele");
                        lblHeading.setText("Operatives Ziel");
                        operativesZielEditor.setCidsBean(((ColoredReadOnlyBandMember)bm).getCidsBean());
                    } else {
                        switchToForm("schutzgebiet");
                        lblHeading.setText("Schutzgebiet");
                        schutzgebietEditor.setCidsBean(((ColoredReadOnlyBandMember)bm).getCidsBean());
                    }
                } else if (bm instanceof EntwicklungszielBandMember) {
                    switchToForm("entwicklungsziel");
                    lblHeading.setText("Entwicklungsziel");
                    entwicklungszielEditor.setCidsBean(((EntwicklungszielBandMember)bm).getCidsBean());
                } else if (bm instanceof UnterhaltungserfordernisBandMember) {
                    switchToForm("unterhaltungserfordernis");
                    lblHeading.setText("Unterhaltungserfordernis");
                    unterhaltungserfordernisEditor.setCidsBean(((UnterhaltungserfordernisBandMember)bm).getCidsBean());
                } else if (bm instanceof VermessungsbandMember) {
                    switchToForm("vermessung");
                    lblHeading.setText("Vermessungselement");
                    final List<CidsBean> others = new ArrayList<CidsBean>();
                    for (int i = 0; i < vermessungsBand.getNumberOfMembers(); ++i) {
                        others.add(((VermessungsbandMember)vermessungsBand.getMember(i)).getCidsBean());
                    }
                    vermessungsEditor.setOthers(others);
                    vermessungsEditor.setCidsBean(((VermessungsbandMember)bm).getCidsBean());
                }
            } else {
                switchToForm("empty");
                lblHeading.setText("");
            }

            if (togApplyStats.isSelected()) {
                vBand.setRefreshAvoided(false);
                vBand.bandModelChanged(null);
            } else {
                jband.setRefreshAvoided(false);
                jband.bandModelChanged(null);
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
    private static class NaturschutzCalculator implements Calculator<List, ArrayList<ArrayList>> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public ArrayList<ArrayList> calculate(final List input) throws Exception {
            final CidsServerSearch searchNSG = new NaturschutzgebietSearch((Double)input.get(0),
                    (Double)input.get(1),
                    String.valueOf(input.get(2)));
            final Collection resNSG = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), searchNSG);
            return (ArrayList<ArrayList>)resNSG;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class UmlandnutzungCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass UMLANDNUTZUNG = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GUP_UMLANDNUTZUNG");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + UMLANDNUTZUNG.getID() + ", u." + UMLANDNUTZUNG.getPrimaryKey()
                        + " from "
                        + UMLANDNUTZUNG.getTableName()
                        + " u, station_linie sl, station von, station bis, route r "
                        + "WHERE u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Umlandnutzung: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            // Passe die Groessen der Umfeldnutzung an die Groesse des GUP an. Da keine Verknuepfung zwischen
            // der Umfeldnutzung und dem Gewaesser besteht, werde diese Aenderungen auch nicht gespeichert
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class SchutzgebietCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass SCHUTZGEBIET = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "SCHUTZGEBIET");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + SCHUTZGEBIET.getID() + ", u." + SCHUTZGEBIET.getPrimaryKey()
                        + " from "
                        + SCHUTZGEBIET.getTableName()
                        + " u, station_linie sl, station von, station bis, route r "
                        + "WHERE u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Schutzgebiete: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            // Passe die Groessen der SChutzgebiete an die Groesse des GUP an. Da keine Verknuepfung zwischen
            // den Schutzgebieten und dem Gewaesser besteht, werden diese Aenderungen auch nicht gespeichert
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class OperativesZielCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass OPERATIVES_ZIEL = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GUP_OPERATIVES_ZIEL_ABSCHNITT");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + OPERATIVES_ZIEL.getID() + ", u." + OPERATIVES_ZIEL.getPrimaryKey()
                        + " from "
                        + OPERATIVES_ZIEL.getTableName()
                        + " u, station_linie sl, station von, station bis, route r "
                        + "WHERE u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for operatives Ziel: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            // Passe die Groessen der operativen Ziele an die Groesse des GUP an. Da keine Verknuepfung zwischen
            // den operativen Zielen und dem Gewaesser besteht, werden diese Aenderungen auch nicht gespeichert
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class VerbreitungsraumCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass VERMEIDUNGSGRUPPE_ABSCHNITT = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "VERMEIDUNGSGRUPPE_ABSCHNITT");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + VERMEIDUNGSGRUPPE_ABSCHNITT.getID() + ", u."
                        + VERMEIDUNGSGRUPPE_ABSCHNITT.getPrimaryKey()
                        + " from "
                        + VERMEIDUNGSGRUPPE_ABSCHNITT.getTableName()
                        + " u, station_linie sl, station von, station bis, route r "
                        + "WHERE u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N

            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Verbreitungsraum: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            // Passe die Groessen der Verbreitungsraeume an die Groesse des GUP an. Da keine Verknuepfung zwischen
            // den Verbreitungsraeumen und dem Gewaesser besteht, werden diese Aenderungen auch nicht gespeichert
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class EntwicklungszielCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass ENTWICKLUNGSZIEL = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GUP_ENTWICKLUNGSZIEL");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + ENTWICKLUNGSZIEL.getID() + ", u."
                        + ENTWICKLUNGSZIEL.getPrimaryKey()
                        + " from "
                        + ENTWICKLUNGSZIEL.getTableName()
                        + " u, station_linie sl, station von, station bis, route r "
                        + "WHERE u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N
            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Entwicklungsziel: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class UnterhaltungserfordernisCalculator implements Calculator<List, MetaObject[]> {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass UNTERHALTUNGSERFORDERNIS = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "GUP_UNTERHALTUNGSERFORDERNIS");

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public MetaObject[] calculate(final List input) throws Exception {
            final String query = "select " + UNTERHALTUNGSERFORDERNIS.getID() + ", u."
                        + UNTERHALTUNGSERFORDERNIS.getPrimaryKey()
                        + " from "
                        + UNTERHALTUNGSERFORDERNIS.getTableName()
                        + " u, station_linie sl, station von, station bis, route r "
                        + "WHERE u.linie = sl.id and sl.von = von.id and sl.bis = bis.id and von.route = r.id "
                        + "      and r.gwk = " + String.valueOf(input.get(2))
                        + " and (( von.wert > " + (Double)input.get(0) + " and von.wert < " + (Double)input.get(1)
                        + ") OR "
                        + "             (bis.wert > " + (Double)input.get(0) + " AND bis.wert < " + (Double)input.get(1)
                        + " ) OR (von.wert <= " + (Double)input.get(0) + " and bis.wert >= " + (Double)input.get(1)
                        + "))"; // NOI18N
            if (LOG.isDebugEnabled()) {
                LOG.debug("Request for Unterhaltungserfordernisse: " + query);
            }
            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            adjustBorders(metaObjects, (Double)input.get(0), (Double)input.get(1));

            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class QuerbauwerkeCalculator implements Calculator<List, ArrayList<ArrayList>> {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   input  enthalt den Stationierungswert des Starts, des Endes und den GWT der Route in dieser
         *                 Reihenfolge
         *
         * @return  DOCUMENT ME!
         *
         * @throws  Exception  DOCUMENT ME!
         */
        @Override
        public ArrayList<ArrayList> calculate(final List input) throws Exception {
            final CidsServerSearch searchQB = new QuerbautenSearchByStations((Double)input.get(0),
                    (Double)input.get(1),
                    String.valueOf(input.get(2)));
            final Collection resQB = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), searchQB);
            return (ArrayList<ArrayList>)resQB;
        }
    }
}
