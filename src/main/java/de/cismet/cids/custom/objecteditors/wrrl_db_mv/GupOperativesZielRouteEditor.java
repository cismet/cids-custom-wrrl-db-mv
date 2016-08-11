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

import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.*;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.RoundedPanel;
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
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupOperativesZielRouteEditor extends JPanel implements CidsBeanRenderer,
    FooterComponentProvider,
    EditorSaveListener,
    TitleComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    public static final String COLLECTION_PROPERTY = "ziele";

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupOperativesZielRouteEditor.class);
    private static final String GUP_OPERATIVES_ZIEL = "gup_operatives_ziel_abschnitt";
    public static final int GUP_UFER_LINKS = 1;
    public static final int GUP_UFER_RECHTS = 2;
    public static final int GUP_UMFELD_RECHTS = 3;
    public static final int GUP_UMFELD_LINKS = 4;
    public static final int GUP_SOHLE = 5;
    private static PflegezieleValidator searchValidator;
    private static CidsBean lastActiveMassnBean;

    //~ Instance fields --------------------------------------------------------

    private List<CidsBean> rechtesUferList = new ArrayList<CidsBean>();
    private List<CidsBean> sohleList = new ArrayList<CidsBean>();
    private List<CidsBean> linkesUferList = new ArrayList<CidsBean>();
    private List<CidsBean> rechtesUmfeldList = new ArrayList<CidsBean>();
    private List<CidsBean> linkesUmfeldList = new ArrayList<CidsBean>();
    private final OperativesZielRWBand uferLinksBand = new OperativesZielRWBand("Ufer links", GUP_OPERATIVES_ZIEL);
    private final OperativesZielRWBand uferRechtsBand = new OperativesZielRWBand("Ufer rechts", GUP_OPERATIVES_ZIEL);
    private final OperativesZielRWBand umfeldLinksBand = new OperativesZielRWBand("Umfeld links", GUP_OPERATIVES_ZIEL);
    private final OperativesZielRWBand umfeldRechtsBand = new OperativesZielRWBand(
            "Umfeld rechts",
            GUP_OPERATIVES_ZIEL);
    private final OperativesZielRWBand sohleBand = new OperativesZielRWBand("Sohle", GUP_OPERATIVES_ZIEL);

    private final ColoredReadOnlyBand nutzungLinksBand = new ColoredReadOnlyBand(
            "Umlandnutzung links",
            "art",
            "art.name");
    private final ColoredReadOnlyBand nutzungRechtsBand = new ColoredReadOnlyBand(
            "Umlandnutzung rechts",
            "art",
            "art.name");
    private final ColoredReadOnlySnappingBand schutzgebietLinksBand = new ColoredReadOnlySnappingBand(
            "Schutzgebiet links",
            "art",
            "art.name");
    private final ColoredReadOnlySnappingBand schutzgebietRechtsBand = new ColoredReadOnlySnappingBand(
            "Schutzgebiet rechts",
            "art",
            "art.name");
    private final ColoredReadOnlySnappingBand schutzgebietSohleBand = new ColoredReadOnlySnappingBand(
            "Schutzgebiet Sohle",
            "art",
            "art.name");
    private final VermeidungsgruppeRBand verbreitungsraumLinksBand = new VermeidungsgruppeRBand(
            "Verbreitungsraum links");
    private final VermeidungsgruppeRBand verbreitungsraumRechtsBand = new VermeidungsgruppeRBand(
            "Verbreitungsraum rechts");
    private final VermeidungsgruppeRBand verbreitungsraumUmfeldLinksBand = new VermeidungsgruppeRBand(
            "Verbreitungsraum Umfeld links");
    private final VermeidungsgruppeRBand verbreitungsraumUmfeldRechtsBand = new VermeidungsgruppeRBand(
            "Verbreitungsraum Umfeld rechts");
    private final VermeidungsgruppeRBand verbreitungsraumSohleBand = new VermeidungsgruppeRBand(
            "Verbreitungsraum Sohle");
    private final ColoredReadOnlyBand entwicklungszielBand = new ColoredReadOnlyBand(
            "Entwicklungsziel",
            "name_bezeichnung",
            "name_bezeichnung.name");
    private final ReadOnlyTextBand unterhaltungshinweisLinks = new ReadOnlyTextBand(
            "Unterhaltungshinweise links",
            "art.name",
            "art.name");
    private final ReadOnlyTextBand unterhaltungshinweisRechts = new ReadOnlyTextBand(
            "Unterhaltungshinweise rechts",
            "art.name",
            "art.name");
    private final ReadOnlyTextBand unterhaltungshinweisSohle = new ReadOnlyTextBand(
            "Unterhaltungshinweise Sohle",
            "art.name",
            "art.name");
    private final ReadOnlyTextBand umlandnutzerLinks = new ReadOnlyTextBand(
            "Umlandnutzer links",
            null,
            null);
    private final ReadOnlyTextBand umlandnutzerRechts = new ReadOnlyTextBand(
            "Umlandnutzer rechts",
            null,
            null);
    private final ColoredReadOnlyBand hydrologieBand = new ColoredReadOnlyBand(
            "Hydrologie",
            null,
            null);
    private final ColoredReadOnlyBand massnahmeRechtesUferBand = new ColoredReadOnlyBand(
            "Maßnahme rechts",
            "massnahme",
            "massnahme.massnahmen_id");
    private final ColoredReadOnlyBand massnahmeSohleBand = new ColoredReadOnlyBand(
            "Maßnahme Sohle",
            "massnahme",
            "massnahme.massnahmen_id");
    private final ColoredReadOnlyBand massnahmeLinkesUferBand = new ColoredReadOnlyBand(
            "Maßnahme links",
            "massnahme",
            "massnahme.massnahmen_id");
    private final ColoredReadOnlyBand massnahmeRechtesUmfeldBand = new ColoredReadOnlyBand(
            "Maßnahme Umfeld rechts",
            "massnahme",
            "massnahme.massnahmen_id");
    private final ColoredReadOnlyBand massnahmeLinkesUmfeldBand = new ColoredReadOnlyBand(
            "Maßnahme Umfeld links",
            "massnahme",
            "massnahme.massnahmen_id");

    private final UnterhaltungserfordernisBand unterhaltungserfordernisBand = new UnterhaltungserfordernisBand();
    private WKBand wkband;
    private final QuerbauwerksBand querbauwerksband = new QuerbauwerksBand();
    private final EmptyAbsoluteHeightedBand massnahmenFiller = new EmptyAbsoluteHeightedBand(5);
    private final EmptyAbsoluteHeightedBand unterhaltungshinweisFiller = new EmptyAbsoluteHeightedBand(5);
    private final EmptyAbsoluteHeightedBand schutzgebieteFiller = new EmptyAbsoluteHeightedBand(5);
    private final EmptyAbsoluteHeightedBand hydrologieFiller = new EmptyAbsoluteHeightedBand(5);
    private final EmptyAbsoluteHeightedBand verbreitungsraumFiller = new EmptyAbsoluteHeightedBand(5);
    private final EmptyAbsoluteHeightedBand wkBandFiller = new EmptyAbsoluteHeightedBand(5);
    private final VermessungBandElementEditor vermessungsEditor = new VermessungBandElementEditor();
    private VermessungsbandHelper vermessungsband;
    private final JBand jband;
    private final BandModelListener modelListener = new GupOperativesZielBandModelListener();
    private final SimpleBandModel sbm = new SimpleBandModel();
    private CidsBean cidsBean;
    private final GupEntwicklungszielEditor entwicklungszielEditor = new GupEntwicklungszielEditor(true);
    private final GupPoiEditor unterhaltungshinweisEditor = new GupPoiEditor(true);
    private final UmlandnutzerEditor umlandnutzerEditor = new UmlandnutzerEditor(true);
    private final GupUnterhaltungserfordernisEditor unterhaltungserfordernisEditor =
        new GupUnterhaltungserfordernisEditor(
            true);
    private final GupUnterhaltungsmassnahmeEditor massnahmeEditor = new GupUnterhaltungsmassnahmeEditor(true);
    private final GupUmlandnutzungEditor umlandnutzungEditor = new GupUmlandnutzungEditor(true);
    private final SchutzgebietEditor schutzgebietEditor = new SchutzgebietEditor(true);
    private final GeschuetzteArtAbschnittEditor verbreitungsraumEditor = new GeschuetzteArtAbschnittEditor(true, true);
    private final GupHydrologEditor hydroEditor = new GupHydrologEditor(true);
    private GupOperativesZielAbschnittEditor operativesZielEditor;
    private boolean readOnly = false;
    private final StationLineBackup stationBackup = new StationLineBackup("linie");
    private boolean isNew = false;
    private PflegezieleValidator validator;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrpDetails;
    private javax.swing.JToggleButton butStationierung;
    private javax.swing.JCheckBox chkEntwicklungsziel;
    private javax.swing.JCheckBox chkHydrologie;
    private javax.swing.JCheckBox chkMassnahmen;
    private javax.swing.JCheckBox chkNaturschutz;
    private javax.swing.JCheckBox chkOperativeZiele;
    private javax.swing.JCheckBox chkQuerbauwerke;
    private javax.swing.JCheckBox chkSonstigeMassnahmen;
    private javax.swing.JCheckBox chkUmlandnutzer;
    private javax.swing.JCheckBox chkUmlandnutzung;
    private javax.swing.JCheckBox chkUnterhaltungserfordernis;
    private javax.swing.JCheckBox chkUnterhaltungshinweise;
    private javax.swing.JCheckBox chkVerbreitungsraum;
    private javax.swing.JCheckBox chkWasserkoerper;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JButton jbApply;
    private javax.swing.JButton jbApply1;
    private javax.swing.JLabel lblFiller;
    private javax.swing.JLabel lblFiller2;
    private javax.swing.JLabel lblFiller3;
    private javax.swing.JLabel lblFiller4;
    private javax.swing.JLabel lblFiller5;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSubTitle;
    private javax.swing.JLabel lblTitle;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panApply;
    private javax.swing.JPanel panApplyBand;
    private javax.swing.JPanel panBand;
    private javax.swing.JPanel panBandControl;
    private javax.swing.JPanel panEmpty;
    private javax.swing.JPanel panEntwicklungsziel;
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
    private javax.swing.JPanel panSchutzgebiet;
    private javax.swing.JPanel panTitle;
    private javax.swing.JPanel panUmlandnutzer;
    private javax.swing.JPanel panUmlandnutzung;
    private javax.swing.JPanel panUnterhaltungserfordernis;
    private javax.swing.JPanel panUnterhaltungshinweis;
    private javax.swing.JPanel panVerbreitungsraum;
    private javax.swing.JPanel panVermessung;
    private javax.swing.JPanel panVermessung1;
    private javax.swing.JPanel panZiele;
    private javax.swing.JSlider sldZoom;
    private javax.swing.JScrollPane spBand;
    private javax.swing.JToggleButton togApplyStats;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GupGewaesserabschnittEditor object.
     */
    public GupOperativesZielRouteEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupOperativesZielRouteEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        jband = new JBand(readOnly);
        initComponents();

        spBand.getViewport().setOpaque(false);
        uferLinksBand.setReadOnly(readOnly);
        uferRechtsBand.setReadOnly(readOnly);
        umfeldLinksBand.setReadOnly(readOnly);
        umfeldRechtsBand.setReadOnly(readOnly);
        sohleBand.setReadOnly(readOnly);
        uferLinksBand.setType(GUP_UFER_LINKS);
        uferRechtsBand.setType(GUP_UFER_RECHTS);
        umfeldLinksBand.setType(GUP_UMFELD_LINKS);
        umfeldRechtsBand.setType(GUP_UMFELD_RECHTS);
        sohleBand.setType(GUP_SOHLE);
        operativesZielEditor = new GupOperativesZielAbschnittEditor(readOnly);
        massnahmeEditor.hideValidation(true);

        massnahmeLinkesUferBand.setEnabled(false);
        massnahmeRechtesUferBand.setEnabled(false);
        massnahmeSohleBand.setEnabled(false);
        massnahmeLinkesUmfeldBand.setEnabled(false);
        massnahmeRechtesUmfeldBand.setEnabled(false);
        unterhaltungserfordernisBand.setEnabled(false);
        entwicklungszielBand.setEnabled(false);
        unterhaltungshinweisLinks.setEnabled(false);
        unterhaltungshinweisRechts.setEnabled(false);
        unterhaltungshinweisSohle.setEnabled(false);
        umlandnutzerLinks.setEnabled(false);
        umlandnutzerRechts.setEnabled(false);
        hydrologieBand.setEnabled(false);
        hydrologieBand.setUseBorder(true);
        querbauwerksband.setEnabled(false);

        nutzungLinksBand.setEnabled(false);
        nutzungRechtsBand.setEnabled(false);
        schutzgebietRechtsBand.setEnabled(false);
        schutzgebietSohleBand.setEnabled(false);
        schutzgebietLinksBand.setEnabled(false);
        verbreitungsraumRechtsBand.setEnabled(false);
        verbreitungsraumLinksBand.setEnabled(false);
        verbreitungsraumUmfeldRechtsBand.setEnabled(false);
        verbreitungsraumUmfeldLinksBand.setEnabled(false);
        verbreitungsraumSohleBand.setEnabled(false);
        unterhaltungshinweisFiller.setEnabled(false);
        schutzgebieteFiller.setEnabled(false);
        hydrologieFiller.setEnabled(false);
        verbreitungsraumFiller.setEnabled(false);
        wkBandFiller.setEnabled(false);

//        sbm.addBand(wkband);
//        sbm.addBand(wkBandFiller);                     // filler
        sbm.addBand(entwicklungszielBand);
        sbm.addBand(unterhaltungserfordernisBand);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5)); // filler
        sbm.addBand(unterhaltungshinweisRechts);
        sbm.addBand(unterhaltungshinweisSohle);
        sbm.addBand(unterhaltungshinweisLinks);
        sbm.addBand(unterhaltungshinweisFiller);       // filler
        sbm.addBand(verbreitungsraumUmfeldRechtsBand);
        sbm.addBand(verbreitungsraumRechtsBand);
        sbm.addBand(verbreitungsraumSohleBand);
        sbm.addBand(verbreitungsraumLinksBand);
        sbm.addBand(verbreitungsraumUmfeldLinksBand);
        sbm.addBand(verbreitungsraumFiller);           // filler
        sbm.addBand(schutzgebietRechtsBand);
        sbm.addBand(schutzgebietSohleBand);
        sbm.addBand(schutzgebietLinksBand);
        sbm.addBand(schutzgebieteFiller);              // filler
        sbm.addBand(massnahmeRechtesUmfeldBand);
        sbm.addBand(massnahmeRechtesUferBand);
        sbm.addBand(massnahmeSohleBand);
        sbm.addBand(massnahmeLinkesUferBand);
        sbm.addBand(massnahmeLinkesUmfeldBand);
        sbm.addBand(massnahmenFiller);                 // filler
        sbm.addBand(umlandnutzerRechts);
        sbm.addBand(nutzungRechtsBand);
        sbm.addBand(umfeldRechtsBand);
        sbm.addBand(uferRechtsBand);
        sbm.addBand(sohleBand);
        sbm.addBand(uferLinksBand);
        sbm.addBand(umfeldLinksBand);
//        sbm.addBand(rechtesUmfeldBand);
//        sbm.addBand(rechtesUferBand);
//        sbm.addBand(sohleBand);
//        sbm.addBand(linkesUferBand);
//        sbm.addBand(linkesUmfeldBand);
        sbm.addBand(nutzungLinksBand);
        sbm.addBand(umlandnutzerLinks);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5)); // filler
        sbm.addBand(hydrologieBand);
        sbm.addBand(hydrologieFiller);                 // filler
        sbm.addBand(querbauwerksband);
        sbm.addBand(new EmptyAbsoluteHeightedBand(5)); // filler

        jband.setModel(sbm);

        panBand.add(jband, BorderLayout.CENTER);
        jband.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        switchToForm("empty");
        lblHeading.setText("Allgemeine Informationen");
        panZiele.add(operativesZielEditor, BorderLayout.CENTER);
        panVermessung.add(vermessungsEditor, BorderLayout.CENTER);
        panEntwicklungsziel.add(entwicklungszielEditor, BorderLayout.CENTER);
        panUnterhaltungshinweis.add(unterhaltungshinweisEditor, BorderLayout.CENTER);
        panUmlandnutzer.add(umlandnutzerEditor, BorderLayout.CENTER);
        panUnterhaltungserfordernis.add(unterhaltungserfordernisEditor, BorderLayout.CENTER);
        panMassnahme.add(massnahmeEditor);
        panUmlandnutzung.add(umlandnutzungEditor, BorderLayout.CENTER);
        panSchutzgebiet.add(schutzgebietEditor, BorderLayout.CENTER);
        panVerbreitungsraum.add(verbreitungsraumEditor, BorderLayout.CENTER);
        panHydro.add(hydroEditor, BorderLayout.CENTER);

        sbm.addBandModelListener(modelListener);

        sldZoom.setPaintTrack(false);

        if (!readOnly) {
            vermessungsband = new VermessungsbandHelper(
                    jband,
                    modelListener,
                    panBand,
                    panApplyBand,
                    panApply,
                    togApplyStats);
        } else {
            togApplyStats.setVisible(false);
        }
    }

    //~ Methods ----------------------------------------------------------------

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
//        bindingGroup.bind();
        this.cidsBean = cidsBean;

        switchToForm("empty");
        lblHeading.setText("");

        if (cidsBean != null) {
            lblTitle.setText(getTitle());
            if (!readOnly) {
                vermessungsband.setCidsBean(cidsBean);
            }
            isNew = cidsBean.getProperty("linie") == null;

            if (cidsBean.getProperty("linie") == null) {
                panBand.removeAll();
                panBand.add(panNew, BorderLayout.CENTER);
                linearReferencedLineEditor.setLineField("linie");
                linearReferencedLineEditor.setCidsBean(cidsBean);
            } else {
                setNamesAndBands();
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setNamesAndBands() {
        validator = new PflegezieleValidator();
        searchValidator = validator;
        final CidsBean route = LinearReferencingHelper.getRouteBeanFromStationBean((CidsBean)cidsBean.getProperty(
                    "linie.von"));
        final double from = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)cidsBean.getProperty(
                    "linie.von"));
        final double till = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)cidsBean.getProperty(
                    "linie.bis"));
        operativesZielEditor.setValidator(validator);
        uferLinksBand.setValidator(validator);
        uferRechtsBand.setValidator(validator);
        umfeldLinksBand.setValidator(validator);
        umfeldRechtsBand.setValidator(validator);
        sohleBand.setValidator(validator);
        sbm.setMin(from);
        sbm.setMax(till);
        wkband = new WKBand(sbm.getMin(), sbm.getMax());
        if (!readOnly) {
            vermessungsband.setVwkBand(new WKBand(sbm.getMin(), sbm.getMax()));
        }
        jband.setMinValue(from);
        jband.setMaxValue(till);
        uferLinksBand.setRoute(route);
        uferRechtsBand.setRoute(route);
        sohleBand.setRoute(route);
        umfeldRechtsBand.setRoute(route);
        umfeldLinksBand.setRoute(route);

        final List<CidsBean> all = cidsBean.getBeanCollectionProperty(COLLECTION_PROPERTY);
        rechtesUferList = new ArrayList<CidsBean>();
        sohleList = new ArrayList<CidsBean>();
        linkesUferList = new ArrayList<CidsBean>();
        rechtesUmfeldList = new ArrayList<CidsBean>();
        linkesUmfeldList = new ArrayList<CidsBean>();

        for (final CidsBean tmp : all) {
            final Integer kind = (Integer)tmp.getProperty("wo.id");

            switch (kind) {
                case GupPlanungsabschnittEditor.GUP_UFER_LINKS: {
                    linkesUferList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_UFER_RECHTS: {
                    rechtesUferList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_UMFELD_LINKS: {
                    linkesUmfeldList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_UMFELD_RECHTS: {
                    rechtesUmfeldList.add(tmp);
                    break;
                }
                case GupPlanungsabschnittEditor.GUP_SOHLE: {
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
                GupPlanungsabschnittEditor.GUP_UFER_RECHTS,
                cidsBean,
                COLLECTION_PROPERTY));
        ((ObservableList<CidsBean>)linkesUferList).addObservableListListener(new MassnBezugListListener(
                GupPlanungsabschnittEditor.GUP_UFER_LINKS,
                cidsBean,
                COLLECTION_PROPERTY));
        ((ObservableList<CidsBean>)rechtesUmfeldList).addObservableListListener(new MassnBezugListListener(
                GupPlanungsabschnittEditor.GUP_UMFELD_RECHTS,
                cidsBean,
                COLLECTION_PROPERTY));
        ((ObservableList<CidsBean>)linkesUmfeldList).addObservableListListener(new MassnBezugListListener(
                GupPlanungsabschnittEditor.GUP_UMFELD_LINKS,
                cidsBean,
                COLLECTION_PROPERTY));
        ((ObservableList<CidsBean>)sohleList).addObservableListListener(new MassnBezugListListener(
                GupPlanungsabschnittEditor.GUP_SOHLE,
                cidsBean,
                COLLECTION_PROPERTY));

        uferRechtsBand.setCidsBeans(rechtesUferList);
        sohleBand.setCidsBeans(sohleList);
        uferLinksBand.setCidsBeans(linkesUferList);
        umfeldRechtsBand.setCidsBeans(rechtesUmfeldList);
        umfeldLinksBand.setCidsBeans(linkesUmfeldList);

//        uferLinksBand.setCidsBeans(cidsBean.getBeanCollectionProperty("ufer_links"));
//        uferRechtsBand.setCidsBeans(cidsBean.getBeanCollectionProperty("ufer_rechts"));
//        umfeldLinksBand.setCidsBeans(cidsBean.getBeanCollectionProperty("umfeld_links"));
//        umfeldRechtsBand.setCidsBeans(cidsBean.getBeanCollectionProperty("umfeld_rechts"));
//        sohleBand.setCidsBeans(cidsBean.getBeanCollectionProperty("sohle"));

        final String rname = String.valueOf(route.getProperty("routenname"));

        lblSubTitle.setText(rname + " [" + (int)sbm.getMin() + "," + (int)sbm.getMax() + "]");

        wkband.fillAndInsertBand(sbm, String.valueOf(route.getProperty("gwk")), jband, vermessungsband);
        loadExternalData(route);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  route  DOCUMENT ME!
     */
    private void loadExternalData(final CidsBean route) {
        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<ArrayList<ArrayList>, Void>() {

                @Override
                protected ArrayList<ArrayList> doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    return GupHelper.querbauwerkCache.calcValue(in);
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

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<Collection<CidsBean>, Void>() {

                @Override
                protected Collection<CidsBean> doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    final MetaObject[] metaObjects = GupHelper.schutzgebietCache.calcValue(in);

                    final List<CidsBean> beanList = new ArrayList<CidsBean>();

                    for (final MetaObject mo : metaObjects) {
                        beanList.add(mo.getBean());
                    }

                    return beanList;
                }

                @Override
                protected void done() {
                    try {
                        final Collection<CidsBean> result = get();
                        final List<CidsBean> beansLeft = new ArrayList<CidsBean>();
                        final List<CidsBean> beansRight = new ArrayList<CidsBean>();
                        final List<CidsBean> beansSohle = new ArrayList<CidsBean>();
//                        validator.setSchutzgebiete(result);

                        for (final CidsBean tmp : result) {
                            final CidsBean side = (CidsBean)tmp.getProperty("wo");

                            if (side != null) {
                                if ((Integer)side.getProperty("id") == GUP_UFER_RECHTS) {
                                    beansRight.add(tmp);
                                } else if ((Integer)side.getProperty("id") == GUP_UFER_LINKS) {
                                    beansLeft.add(tmp);
                                } else if ((Integer)side.getProperty("id") == GUP_SOHLE) {
                                    beansSohle.add(tmp);
                                }
                            }
                        }

                        schutzgebietLinksBand.setCidsBeans(beansLeft);
                        schutzgebietRechtsBand.setCidsBeans(beansRight);
                        schutzgebietSohleBand.setCidsBeans(beansSohle);
                        chkNaturschutz.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Schutzgebiete", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<Collection<CidsBean>, Void>() {

                @Override
                protected Collection<CidsBean> doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    final MetaObject[] metaObjects = GupHelper.unterhaltungsmassnahmeCache.calcValue(in);

                    final List<CidsBean> beanList = new ArrayList<CidsBean>();

                    for (final MetaObject mo : metaObjects) {
                        beanList.add(mo.getBean());
                    }

                    return beanList;
                }

                @Override
                protected void done() {
                    try {
                        final Collection<CidsBean> result = get();
                        final List<CidsBean> beansLeft = new ArrayList<CidsBean>();
                        final List<CidsBean> beansRight = new ArrayList<CidsBean>();
                        final List<CidsBean> beansSohle = new ArrayList<CidsBean>();
                        final List<CidsBean> beansUmfeldRight = new ArrayList<CidsBean>();
                        final List<CidsBean> beansUmfeldLeft = new ArrayList<CidsBean>();

                        for (final CidsBean tmp : result) {
                            final CidsBean side = (CidsBean)tmp.getProperty("wo");

                            if (side != null) {
                                if ((Integer)side.getProperty("id") == GUP_UFER_RECHTS) {
                                    beansRight.add(tmp);
                                } else if ((Integer)side.getProperty("id") == GUP_UFER_LINKS) {
                                    beansLeft.add(tmp);
                                } else if ((Integer)side.getProperty("id") == GUP_SOHLE) {
                                    beansSohle.add(tmp);
                                } else if ((Integer)side.getProperty("id") == GUP_UMFELD_LINKS) {
                                    beansUmfeldLeft.add(tmp);
                                } else if ((Integer)side.getProperty("id") == GUP_UMFELD_RECHTS) {
                                    beansUmfeldRight.add(tmp);
                                }
                            }
                        }

                        massnahmeLinkesUferBand.setCidsBeans(beansLeft);
                        massnahmeRechtesUferBand.setCidsBeans(beansRight);
                        massnahmeSohleBand.setCidsBeans(beansSohle);
                        massnahmeLinkesUmfeldBand.setCidsBeans(beansUmfeldLeft);
                        massnahmeRechtesUmfeldBand.setCidsBeans(beansUmfeldRight);
                        chkMassnahmen.setEnabled(true);
                        chkSonstigeMassnahmen.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Maßnahmen", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(
            new javax.swing.SwingWorker<VermeidungsgruppeReadOnlyBandMember[], Void>() {

                private final MetaClass VERMEIDUNGSGRUPPE = ClassCacheMultiple.getMetaClass(
                        WRRLUtil.DOMAIN_NAME,
                        "VERMEIDUNGSGRUPPE");

                private final String query = "select " + VERMEIDUNGSGRUPPE.getID() + ", v."
                            + VERMEIDUNGSGRUPPE.getPrimaryKey()
                            + " from "
                            + VERMEIDUNGSGRUPPE.getTableName()
                            + " v join VERMEIDUNGSGRUPPE_GESCHUETZTE_ART vga on v.arten = vga.vermeidungsgruppe_reference "
                            + "join geschuetzte_art ga on vga.art = ga.id where ga.id = ";

                @Override
                protected VermeidungsgruppeReadOnlyBandMember[] doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    final List<CidsBean> beanList = new ArrayList<CidsBean>();
                    final MetaObject[] metaObjects = GupHelper.verbreitungsraumCache.calcValue(in);

                    for (final MetaObject tmp : metaObjects) {
                        final MetaObject[] vermeidungsgruppen = MetaObjectCache.getInstance()
                                        .getMetaObjectsByQuery(
                                            query
                                            + tmp.getBean().getProperty("art.id"),
                                            WRRLUtil.DOMAIN_NAME);

                        if (vermeidungsgruppen != null) {
                            for (final MetaObject vermeidungsgruppe : vermeidungsgruppen) {
                                final CidsBean newBean = CidsBean.createNewCidsBeanFromTableName(
                                        WRRLUtil.DOMAIN_NAME,
                                        "gup_vermeidungsgruppe_art");
                                newBean.setProperty("art", tmp.getBean());
                                newBean.setProperty("vermeidungsgruppe", vermeidungsgruppe.getBean());
                                beanList.add(newBean);
                            }
                        }
                    }

                    final List<VermeidungsgruppeReadOnlyBandMember> res =
                        new ArrayList<VermeidungsgruppeReadOnlyBandMember>();

                    if (beanList != null) {
                        for (final CidsBean tmp : beanList) {
                            final VermeidungsgruppeReadOnlyBandMember bandMember =
                                new VermeidungsgruppeReadOnlyBandMember();
                            bandMember.setCidsBean(
                                (CidsBean)tmp.getProperty("art"),
                                (CidsBean)tmp.getProperty("vermeidungsgruppe"));
                            res.add(bandMember);
                        }
                    }

                    return res.toArray(new VermeidungsgruppeReadOnlyBandMember[res.size()]);
                }

                @Override
                protected void done() {
                    try {
                        final VermeidungsgruppeReadOnlyBandMember[] result = get();
                        final List<VermeidungsgruppeReadOnlyBandMember> beansLeft =
                            new ArrayList<VermeidungsgruppeReadOnlyBandMember>();
                        final List<VermeidungsgruppeReadOnlyBandMember> beansRight =
                            new ArrayList<VermeidungsgruppeReadOnlyBandMember>();
                        final List<VermeidungsgruppeReadOnlyBandMember> beansMiddle =
                            new ArrayList<VermeidungsgruppeReadOnlyBandMember>();
                        final List<VermeidungsgruppeReadOnlyBandMember> beansUmfeldLeft =
                            new ArrayList<VermeidungsgruppeReadOnlyBandMember>();
                        final List<VermeidungsgruppeReadOnlyBandMember> beansUmfeldRight =
                            new ArrayList<VermeidungsgruppeReadOnlyBandMember>();

                        final List<VermeidungsgruppeMitGeom> verbreitungsraeume =
                            new ArrayList<VermeidungsgruppeMitGeom>();
                        for (final VermeidungsgruppeReadOnlyBandMember tmp : result) {
                            verbreitungsraeume.add(
                                new VermeidungsgruppeMitGeom(tmp.getVermeidungsgruppe(), tmp.getArt()));
                        }
//                        validator.setVerbreitungsraum(
//                            verbreitungsraeume.toArray(new VermeidungsgruppeMitGeom[verbreitungsraeume.size()]));

                        for (final VermeidungsgruppeReadOnlyBandMember tmp : result) {
                            final CidsBean side = (CidsBean)tmp.getCidsBean().getProperty("wo");

                            if (side != null) {
                                if ((Integer)side.getProperty("id") == GUP_UFER_RECHTS) {
                                    beansRight.add(tmp);
                                } else if ((Integer)side.getProperty("id") == GUP_UMFELD_LINKS) {
                                    beansUmfeldLeft.add(tmp);
                                } else if ((Integer)side.getProperty("id") == GUP_UMFELD_RECHTS) {
                                    beansUmfeldRight.add(tmp);
                                } else if ((Integer)side.getProperty("id") == GUP_UFER_LINKS) {
                                    beansLeft.add(tmp);
                                } else if ((Integer)side.getProperty("id") == GUP_SOHLE) {
                                    beansMiddle.add(tmp);
                                }
                            }
                        }

                        verbreitungsraumLinksBand.addMember(beansLeft);
                        verbreitungsraumRechtsBand.addMember(beansRight);
                        verbreitungsraumUmfeldLinksBand.addMember(beansUmfeldLeft);
                        verbreitungsraumUmfeldRechtsBand.addMember(beansUmfeldRight);
                        verbreitungsraumSohleBand.addMember(beansMiddle);
                        chkVerbreitungsraum.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Verbreitungsräume", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<Collection<CidsBean>, Void>() {

                @Override
                protected Collection<CidsBean> doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    final MetaObject[] metaObjects = GupHelper.umlandCache.calcValue(in);

                    final List<CidsBean> beanList = new ArrayList<CidsBean>();

                    for (final MetaObject mo : metaObjects) {
                        beanList.add(mo.getBean());
                    }

                    return beanList;
                }

                @Override
                protected void done() {
                    try {
                        final Collection<CidsBean> result = get();
                        final List<CidsBean> beansLeft = new ArrayList<CidsBean>();
                        final List<CidsBean> beansRight = new ArrayList<CidsBean>();

                        for (final CidsBean tmp : result) {
                            final CidsBean side = (CidsBean)tmp.getProperty("wo");

                            if (side != null) {
                                if ((Integer)side.getProperty("id") == GUP_UFER_RECHTS) {
                                    beansRight.add(tmp);
                                } else if ((Integer)side.getProperty("id") == GUP_UFER_LINKS) {
                                    beansLeft.add(tmp);
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

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<Collection<CidsBean>, Void>() {

                @Override
                protected Collection<CidsBean> doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(sbm.getMin());
                    in.add(sbm.getMax());
                    in.add(route.getProperty("gwk"));
                    final MetaObject[] metaObjects = GupHelper.entwicklungszielCache.calcValue(in);

                    final List<CidsBean> beanList = new ArrayList<CidsBean>();

                    for (final MetaObject mo : metaObjects) {
                        beanList.add(mo.getBean());
                    }

                    return beanList;
                }

                @Override
                protected void done() {
                    try {
                        final Collection<CidsBean> beans = get();
                        validator.setEntwicklungsziele(beans);

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
                    return GupHelper.unterhaltungshinweiseCache.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        final MetaObject[] result = get();

                        final List<CidsBean> beansLeft = new ArrayList<CidsBean>();
                        final List<CidsBean> beansRight = new ArrayList<CidsBean>();
                        final List<CidsBean> beansMiddle = new ArrayList<CidsBean>();

                        for (final MetaObject tmp : result) {
                            final CidsBean side = (CidsBean)tmp.getBean().getProperty("wo");

                            if (side != null) {
                                if ((Integer)side.getProperty("id") == GUP_UFER_RECHTS) {
                                    beansRight.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == GUP_UFER_LINKS) {
                                    beansLeft.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == GUP_SOHLE) {
                                    beansMiddle.add(tmp.getBean());
                                }
                            }
                        }

                        unterhaltungshinweisLinks.setCidsBeans(beansLeft);
                        unterhaltungshinweisRechts.setCidsBeans(beansRight);
                        unterhaltungshinweisSohle.setCidsBeans(beansMiddle);
                        chkUnterhaltungshinweise.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Unterhaltungshinweise", e);
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
                    return GupHelper.umlandnutzerCache.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        final MetaObject[] result = get();

                        final List<CidsBean> beansLeft = new ArrayList<CidsBean>();
                        final List<CidsBean> beansRight = new ArrayList<CidsBean>();

                        for (final MetaObject tmp : result) {
                            final CidsBean side = (CidsBean)tmp.getBean().getProperty("wo");

                            if (side != null) {
                                if ((Integer)side.getProperty("id") == GUP_UFER_RECHTS) {
                                    beansRight.add(tmp.getBean());
                                } else if ((Integer)side.getProperty("id") == GUP_UFER_LINKS) {
                                    beansLeft.add(tmp.getBean());
                                }
                            }
                        }

                        umlandnutzerLinks.setCidsBeans(beansLeft);
                        umlandnutzerRechts.setCidsBeans(beansRight);
                        chkUmlandnutzer.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Umlandnutzer", e);
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
                    return GupHelper.unterhaltungserfordernisCache.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        final MetaObject[] result = get();
                        final List<CidsBean> beans = new ArrayList<CidsBean>();

                        for (final MetaObject tmp : result) {
                            beans.add(tmp.getBean());
                        }

                        validator.setSituationstypen(beans);
                        unterhaltungserfordernisBand.setCidsBeans(beans);
                        chkUnterhaltungserfordernis.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Situationstypen", e);
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
                    return GupHelper.hydrologieCache.calcValue(in);
                }

                @Override
                protected void done() {
                    try {
                        final MetaObject[] result = get();
                        final List<CidsBean> beans = new ArrayList<CidsBean>();

                        for (final MetaObject tmp : result) {
                            beans.add(tmp.getBean());
                        }

                        hydrologieBand.setCidsBeans(beans);
                        chkHydrologie.setEnabled(true);
                        ((SimpleBandModel)jband.getModel()).fireBandModelChanged();
                    } catch (Exception e) {
                        LOG.error("Problem beim Suchen der Hydrologiedaten", e);
                    }
                }
            });
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
        panNew = new javax.swing.JPanel();
        linearReferencedLineEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor();
        jbApply = new javax.swing.JButton();
        panApply = new javax.swing.JPanel();
        jbApply1 = new javax.swing.JButton();
        panApplyBand = new javax.swing.JPanel();
        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        togApplyStats = new javax.swing.JToggleButton();
        butStationierung = new javax.swing.JToggleButton();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panZiele = new javax.swing.JPanel();
        panEmpty = new javax.swing.JPanel();
        panVermessung = new javax.swing.JPanel();
        panUnterhaltungserfordernis = new javax.swing.JPanel();
        panMassnahme = new javax.swing.JPanel();
        panHydro = new javax.swing.JPanel();
        panEntwicklungsziel = new javax.swing.JPanel();
        panVermessung1 = new javax.swing.JPanel();
        panUmlandnutzung = new javax.swing.JPanel();
        panVerbreitungsraum = new javax.swing.JPanel();
        panMassnahmeSonstige = new javax.swing.JPanel();
        panSchutzgebiet = new javax.swing.JPanel();
        panUnterhaltungshinweis = new javax.swing.JPanel();
        panUmlandnutzer = new javax.swing.JPanel();
        panHeader = new javax.swing.JPanel();
        panHeaderInfo = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblSubTitle = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        sldZoom = new javax.swing.JSlider();
        panBand = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
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
        chkUnterhaltungshinweise = new javax.swing.JCheckBox();
        chkUmlandnutzer = new javax.swing.JCheckBox();
        chkHydrologie = new javax.swing.JCheckBox();
        lblFiller = new javax.swing.JLabel();
        lblFiller2 = new javax.swing.JLabel();
        lblFiller3 = new javax.swing.JLabel();
        lblFiller4 = new javax.swing.JLabel();
        lblFiller5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        panFooter.setMinimumSize(new java.awt.Dimension(1050, 12));
        panFooter.setOpaque(false);
        panFooter.setPreferredSize(new java.awt.Dimension(1050, 12));
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        panNew.add(linearReferencedLineEditor, gridBagConstraints);

        jbApply.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupGewaesserabschnitt")); // NOI18N
        jbApply.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbApplyActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panNew.add(jbApply, gridBagConstraints);

        panApply.setOpaque(false);
        panApply.setLayout(new java.awt.GridBagLayout());

        jbApply1.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
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

        panTitle.setMinimumSize(new java.awt.Dimension(1050, 36));
        panTitle.setOpaque(false);
        panTitle.setPreferredSize(new java.awt.Dimension(1050, 36));
        panTitle.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTitle.add(lblTitle, gridBagConstraints);

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panTitle.add(lblStatus, gridBagConstraints);

        jPanel1.setMaximumSize(new java.awt.Dimension(1, 1));
        jPanel1.setMinimumSize(new java.awt.Dimension(1, 1));
        jPanel1.setOpaque(false);

        final javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 1, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panTitle.add(jPanel1, gridBagConstraints);

        togApplyStats.setText("Vermessen");
        togApplyStats.setPreferredSize(new java.awt.Dimension(115, 25));
        togApplyStats.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    togApplyStatsActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 7);
        panTitle.add(togApplyStats, gridBagConstraints);

        butStationierung.setText("Stationierung");
        butStationierung.setPreferredSize(new java.awt.Dimension(115, 25));
        butStationierung.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    butStationierungActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 7);
        panTitle.add(butStationierung, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1050, 700));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1050, 700));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(640, 340));
        panInfo.setPreferredSize(new java.awt.Dimension(640, 340));

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

        panZiele.setOpaque(false);
        panZiele.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panZiele, "ziele");

        panEmpty.setOpaque(false);
        panEmpty.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panEmpty, "empty");

        panVermessung.setOpaque(false);
        panVermessung.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panVermessung, "vermessung");

        panUnterhaltungserfordernis.setOpaque(false);
        panUnterhaltungserfordernis.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panUnterhaltungserfordernis, "unterhaltungserfordernis");

        panMassnahme.setOpaque(false);
        panMassnahme.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panMassnahme, "massnahme");

        panHydro.setOpaque(false);
        panHydro.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panHydro, "hydro");

        panEntwicklungsziel.setOpaque(false);
        panEntwicklungsziel.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panEntwicklungsziel, "entwicklungsziel");

        panVermessung1.setOpaque(false);
        panVermessung1.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panVermessung1, "vermessung");

        panUmlandnutzung.setOpaque(false);
        panUmlandnutzung.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panUmlandnutzung, "umlandnutzung");

        panVerbreitungsraum.setOpaque(false);
        panVerbreitungsraum.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panVerbreitungsraum, "verbreitungsraum");

        panMassnahmeSonstige.setOpaque(false);
        panMassnahmeSonstige.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panMassnahmeSonstige, "massnahmesonstige");

        panSchutzgebiet.setOpaque(false);
        panSchutzgebiet.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panSchutzgebiet, "schutzgebiet");

        panUnterhaltungshinweis.setOpaque(false);
        panUnterhaltungshinweis.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panUnterhaltungshinweis, "unterhaltungshinweis");

        panUmlandnutzer.setOpaque(false);
        panUmlandnutzer.setLayout(new java.awt.BorderLayout());
        panInfoContent.add(panUmlandnutzer, "umlandnutzer");

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

        panHeaderInfo.setMinimumSize(new java.awt.Dimension(531, 102));
        panHeaderInfo.setOpaque(false);
        panHeaderInfo.setPreferredSize(new java.awt.Dimension(531, 102));
        panHeaderInfo.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel3.setText("Gewässer:");
        jLabel3.setMinimumSize(new java.awt.Dimension(91, 22));
        panHeaderInfo.add(jLabel3);
        jLabel3.setBounds(12, 40, 92, 22);

        lblSubTitle.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        lblSubTitle.setText("Warnow KM 0 - 4711");
        panHeaderInfo.add(lblSubTitle);
        lblSubTitle.setBounds(110, 40, 350, 22);

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
        sldZoom.setBounds(110, 72, 350, 16);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        panHeader.add(panHeaderInfo, gridBagConstraints);

        panBand.setOpaque(false);
        panBand.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panHeader.add(panBand, gridBagConstraints);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        panHeader.add(jPanel5, gridBagConstraints);

        spBand.setBorder(null);
        spBand.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spBand.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        spBand.setViewportBorder(null);
        spBand.setMinimumSize(new java.awt.Dimension(500, 100));
        spBand.setOpaque(false);
        spBand.setPreferredSize(new java.awt.Dimension(500, 100));

        panBandControl.setPreferredSize(new java.awt.Dimension(490, 95));
        panBandControl.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 2));

        chkMassnahmen.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkMassnahmen.text",
                new Object[] {})); // NOI18N
        chkMassnahmen.setContentAreaFilled(false);
        chkMassnahmen.setEnabled(false);
        chkMassnahmen.setPreferredSize(new java.awt.Dimension(180, 18));
        chkMassnahmen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkMassnahmenActionPerformed(evt);
                }
            });
        panBandControl.add(chkMassnahmen);

        chkSonstigeMassnahmen.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkSonstigeMassnahmen.text",
                new Object[] {})); // NOI18N
        chkSonstigeMassnahmen.setContentAreaFilled(false);
        chkSonstigeMassnahmen.setEnabled(false);
        chkSonstigeMassnahmen.setPreferredSize(new java.awt.Dimension(180, 18));
        chkSonstigeMassnahmen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkSonstigeMassnahmenActionPerformed(evt);
                }
            });
        panBandControl.add(chkSonstigeMassnahmen);

        chkWasserkoerper.setSelected(true);
        chkWasserkoerper.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkWasserkoerper.text",
                new Object[] {})); // NOI18N
        chkWasserkoerper.setContentAreaFilled(false);
        chkWasserkoerper.setPreferredSize(new java.awt.Dimension(180, 18));
        chkWasserkoerper.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkWasserkoerperActionPerformed(evt);
                }
            });
        panBandControl.add(chkWasserkoerper);

        chkUmlandnutzung.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkUmlandnutzung.text",
                new Object[] {})); // NOI18N
        chkUmlandnutzung.setContentAreaFilled(false);
        chkUmlandnutzung.setEnabled(false);
        chkUmlandnutzung.setPreferredSize(new java.awt.Dimension(180, 18));
        chkUmlandnutzung.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkUmlandnutzungActionPerformed(evt);
                }
            });
        panBandControl.add(chkUmlandnutzung);

        chkQuerbauwerke.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkQuerbauwerke.text",
                new Object[] {})); // NOI18N
        chkQuerbauwerke.setContentAreaFilled(false);
        chkQuerbauwerke.setEnabled(false);
        chkQuerbauwerke.setPreferredSize(new java.awt.Dimension(180, 18));
        chkQuerbauwerke.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkQuerbauwerkeActionPerformed(evt);
                }
            });
        panBandControl.add(chkQuerbauwerke);

        chkNaturschutz.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkNaturschutz.text",
                new Object[] {})); // NOI18N
        chkNaturschutz.setContentAreaFilled(false);
        chkNaturschutz.setEnabled(false);
        chkNaturschutz.setPreferredSize(new java.awt.Dimension(180, 18));
        chkNaturschutz.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkNaturschutzActionPerformed(evt);
                }
            });
        panBandControl.add(chkNaturschutz);

        chkUnterhaltungserfordernis.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkUnterhaltungserfordernis.text",
                new Object[] {})); // NOI18N
        chkUnterhaltungserfordernis.setContentAreaFilled(false);
        chkUnterhaltungserfordernis.setEnabled(false);
        chkUnterhaltungserfordernis.setPreferredSize(new java.awt.Dimension(180, 18));
        chkUnterhaltungserfordernis.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkUnterhaltungserfordernisActionPerformed(evt);
                }
            });
        panBandControl.add(chkUnterhaltungserfordernis);

        chkEntwicklungsziel.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkEntwicklungsziel.text",
                new Object[] {})); // NOI18N
        chkEntwicklungsziel.setContentAreaFilled(false);
        chkEntwicklungsziel.setEnabled(false);
        chkEntwicklungsziel.setPreferredSize(new java.awt.Dimension(180, 18));
        chkEntwicklungsziel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkEntwicklungszielActionPerformed(evt);
                }
            });
        panBandControl.add(chkEntwicklungsziel);

        chkVerbreitungsraum.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkVerbreitungsraum.text",
                new Object[] {})); // NOI18N
        chkVerbreitungsraum.setContentAreaFilled(false);
        chkVerbreitungsraum.setEnabled(false);
        chkVerbreitungsraum.setPreferredSize(new java.awt.Dimension(180, 18));
        chkVerbreitungsraum.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkVerbreitungsraumActionPerformed(evt);
                }
            });
        panBandControl.add(chkVerbreitungsraum);

        chkOperativeZiele.setSelected(true);
        chkOperativeZiele.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkOperativeZiele.text",
                new Object[] {})); // NOI18N
        chkOperativeZiele.setContentAreaFilled(false);
        chkOperativeZiele.setPreferredSize(new java.awt.Dimension(180, 18));
        chkOperativeZiele.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkOperativeZieleActionPerformed(evt);
                }
            });
        panBandControl.add(chkOperativeZiele);

        chkUnterhaltungshinweise.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkUnterhaltungshinweise.text",
                new Object[] {})); // NOI18N
        chkUnterhaltungshinweise.setContentAreaFilled(false);
        chkUnterhaltungshinweise.setEnabled(false);
        chkUnterhaltungshinweise.setPreferredSize(new java.awt.Dimension(180, 18));
        chkUnterhaltungshinweise.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkUnterhaltungshinweiseActionPerformed(evt);
                }
            });
        panBandControl.add(chkUnterhaltungshinweise);

        chkUmlandnutzer.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkUmlandnutzer.text",
                new Object[] {})); // NOI18N
        chkUmlandnutzer.setContentAreaFilled(false);
        chkUmlandnutzer.setEnabled(false);
        chkUmlandnutzer.setPreferredSize(new java.awt.Dimension(180, 18));
        chkUmlandnutzer.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkUmlandnutzerActionPerformed(evt);
                }
            });
        panBandControl.add(chkUmlandnutzer);

        chkHydrologie.setText(org.openide.util.NbBundle.getMessage(
                GupOperativesZielRouteEditor.class,
                "GupPlanungsabschnittEditor.chkHydrologie.text",
                new Object[] {})); // NOI18N
        chkHydrologie.setContentAreaFilled(false);
        chkHydrologie.setEnabled(false);
        chkHydrologie.setPreferredSize(new java.awt.Dimension(180, 18));
        chkHydrologie.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    chkHydrologieActionPerformed(evt);
                }
            });
        panBandControl.add(chkHydrologie);

        lblFiller.setPreferredSize(new java.awt.Dimension(180, 18));
        panBandControl.add(lblFiller);

        lblFiller2.setPreferredSize(new java.awt.Dimension(180, 18));
        panBandControl.add(lblFiller2);

        lblFiller3.setPreferredSize(new java.awt.Dimension(180, 18));
        panBandControl.add(lblFiller3);

        lblFiller4.setPreferredSize(new java.awt.Dimension(180, 18));
        panBandControl.add(lblFiller4);

        lblFiller5.setPreferredSize(new java.awt.Dimension(180, 18));
        panBandControl.add(lblFiller5);

        spBand.setViewportView(panBandControl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
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
    private void sldZoomStateChanged(final javax.swing.event.ChangeEvent evt) { //GEN-FIRST:event_sldZoomStateChanged
        final double zoom = sldZoom.getValue() / 10d;
        jband.setZoomFactor(zoom);
        if (vermessungsband != null) {
            vermessungsband.setZoomFactor(zoom);
        }
    }                                                                           //GEN-LAST:event_sldZoomStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbApplyActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbApplyActionPerformed
        if (isNew) {
            panBand.removeAll();
            panBand.add(jband, BorderLayout.CENTER);
            setNamesAndBands();
            linearReferencedLineEditor.dispose();
            if (!readOnly) {
                vermessungsband.showRoute();
                togApplyStats.setEnabled(true);
            }

            isNew = false;
        } else {
            final int resp = JOptionPane.showConfirmDialog(
                    this,
                    "Ziele, die nicht mehr innerhalb des Routenabschnitts liegen, werden entfernt.",
                    "Achtung",
                    JOptionPane.OK_CANCEL_OPTION);

            if (resp == JOptionPane.OK_OPTION) {
                final Integer routeId = (Integer)LinearReferencingHelper.getRouteBeanFromStationBean((CidsBean)
                            cidsBean.getProperty(
                                "linie.von")).getProperty("id");
                final double from = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)
                        cidsBean.getProperty(
                            "linie.von"));
                final double till = LinearReferencingHelper.getLinearValueFromStationBean((CidsBean)
                        cidsBean.getProperty(
                            "linie.bis"));
                final List<CidsBean> all = cidsBean.getBeanCollectionProperty(COLLECTION_PROPERTY);

                stationBackup.cutSubobjects(all, from, till, routeId);

                panBand.removeAll();
                panBand.add(jband, BorderLayout.CENTER);
                repaint();
                sbm.removeBand(wkband);
                vermessungsband.reset();
                butStationierung.setSelected(!butStationierung.isSelected());
                setNamesAndBands();
                linearReferencedLineEditor.dispose();
                if (!readOnly) {
                    vermessungsband.showRoute();
                    togApplyStats.setEnabled(true);
                }
            }
        }
    } //GEN-LAST:event_jbApplyActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void togApplyStatsActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_togApplyStatsActionPerformed
        if (togApplyStats.isSelected()) {
            vermessungsband.showVermessungsband();

            if (butStationierung.isSelected()) {
                butStationierung.setSelected(false);
                stationBackup.restoreStationValues(cidsBean);
            }
        } else {
            vermessungsband.hideVermessungsband();
        }
        updateUI();
        repaint();
        lblTitle.setText(getTitle());
    } //GEN-LAST:event_togApplyStatsActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbApply1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbApply1ActionPerformed
        final OperativesZielRWBand[] bands = new OperativesZielRWBand[5];
        bands[0] = uferLinksBand;
        bands[1] = uferRechtsBand;
        bands[2] = sohleBand;
        bands[3] = umfeldLinksBand;
        bands[4] = umfeldRechtsBand;
        vermessungsband.applyStats(this, bands, GUP_OPERATIVES_ZIEL);
    }                                                                            //GEN-LAST:event_jbApply1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void butStationierungActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_butStationierungActionPerformed
        if (butStationierung.isSelected()) {
            panBand.removeAll();
            panBand.add(panNew, BorderLayout.CENTER);
            if (togApplyStats.isSelected()) {
                togApplyStats.setSelected(false);
            }

            // save old values to restore them if the user cancel the restation process
            stationBackup.save(cidsBean);

            linearReferencedLineEditor.setLineField("linie");
            linearReferencedLineEditor.setOtherLinesEnabled(false);
            linearReferencedLineEditor.setCidsBean(cidsBean);
            repaint();
        } else {
            stationBackup.restoreStationValues(cidsBean);
            panBand.removeAll();
            panBand.add(jband, BorderLayout.CENTER);
            repaint();
        }
        lblTitle.setText(getTitle());
    } //GEN-LAST:event_butStationierungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkMassnahmenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkMassnahmenActionPerformed
        massnahmeLinkesUferBand.setEnabled(chkMassnahmen.isSelected());
        massnahmeRechtesUferBand.setEnabled(chkMassnahmen.isSelected());
        massnahmeSohleBand.setEnabled(chkMassnahmen.isSelected());
        massnahmenFiller.setEnabled(chkMassnahmen.isSelected() || chkSonstigeMassnahmen.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                 //GEN-LAST:event_chkMassnahmenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkSonstigeMassnahmenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkSonstigeMassnahmenActionPerformed
        massnahmeLinkesUmfeldBand.setEnabled(chkSonstigeMassnahmen.isSelected());
        massnahmeRechtesUmfeldBand.setEnabled(chkSonstigeMassnahmen.isSelected());
        massnahmenFiller.setEnabled(chkMassnahmen.isSelected() || chkSonstigeMassnahmen.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                         //GEN-LAST:event_chkSonstigeMassnahmenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkWasserkoerperActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkWasserkoerperActionPerformed
        wkband.setEnabled(chkWasserkoerper.isSelected());
        wkBandFiller.setEnabled(chkWasserkoerper.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                    //GEN-LAST:event_chkWasserkoerperActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkUmlandnutzungActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkUmlandnutzungActionPerformed
        nutzungLinksBand.setEnabled(chkUmlandnutzung.isSelected());
        nutzungRechtsBand.setEnabled(chkUmlandnutzung.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                    //GEN-LAST:event_chkUmlandnutzungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkQuerbauwerkeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkQuerbauwerkeActionPerformed
        querbauwerksband.setEnabled(chkQuerbauwerke.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                   //GEN-LAST:event_chkQuerbauwerkeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkNaturschutzActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkNaturschutzActionPerformed
        schutzgebietLinksBand.setEnabled(chkNaturschutz.isSelected());
        schutzgebietRechtsBand.setEnabled(chkNaturschutz.isSelected());
        schutzgebietSohleBand.setEnabled(chkNaturschutz.isSelected());
        schutzgebieteFiller.setEnabled(chkNaturschutz.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                  //GEN-LAST:event_chkNaturschutzActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkUnterhaltungserfordernisActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkUnterhaltungserfordernisActionPerformed
        unterhaltungserfordernisBand.setEnabled((chkUnterhaltungserfordernis.isSelected()));
        sbm.fireBandModelValuesChanged();
    }                                                                                               //GEN-LAST:event_chkUnterhaltungserfordernisActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkEntwicklungszielActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkEntwicklungszielActionPerformed
        entwicklungszielBand.setEnabled(chkEntwicklungsziel.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                       //GEN-LAST:event_chkEntwicklungszielActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkVerbreitungsraumActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkVerbreitungsraumActionPerformed
        verbreitungsraumLinksBand.setEnabled(chkVerbreitungsraum.isSelected());
        verbreitungsraumUmfeldLinksBand.setEnabled(chkVerbreitungsraum.isSelected());
        verbreitungsraumUmfeldRechtsBand.setEnabled(chkVerbreitungsraum.isSelected());
        verbreitungsraumRechtsBand.setEnabled(chkVerbreitungsraum.isSelected());
        verbreitungsraumSohleBand.setEnabled(chkVerbreitungsraum.isSelected());
        verbreitungsraumFiller.setEnabled(chkVerbreitungsraum.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                       //GEN-LAST:event_chkVerbreitungsraumActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkOperativeZieleActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkOperativeZieleActionPerformed
        uferLinksBand.setEnabled(chkOperativeZiele.isSelected());
        uferRechtsBand.setEnabled(chkOperativeZiele.isSelected());
        umfeldLinksBand.setEnabled(chkOperativeZiele.isSelected());
        umfeldRechtsBand.setEnabled(chkOperativeZiele.isSelected());
        sohleBand.setEnabled(chkOperativeZiele.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                     //GEN-LAST:event_chkOperativeZieleActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkUnterhaltungshinweiseActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkUnterhaltungshinweiseActionPerformed
        unterhaltungshinweisLinks.setEnabled(chkUnterhaltungshinweise.isSelected());
        unterhaltungshinweisRechts.setEnabled(chkUnterhaltungshinweise.isSelected());
        unterhaltungshinweisSohle.setEnabled(chkUnterhaltungshinweise.isSelected());
        unterhaltungshinweisFiller.setEnabled(chkUnterhaltungshinweise.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                            //GEN-LAST:event_chkUnterhaltungshinweiseActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkUmlandnutzerActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkUmlandnutzerActionPerformed
        umlandnutzerLinks.setEnabled(chkUmlandnutzer.isSelected());
        umlandnutzerRechts.setEnabled(chkUmlandnutzer.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                   //GEN-LAST:event_chkUmlandnutzerActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void chkHydrologieActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_chkHydrologieActionPerformed
        hydrologieBand.setEnabled(chkHydrologie.isSelected());
        hydrologieFiller.setEnabled(chkHydrologie.isSelected());
        sbm.fireBandModelValuesChanged();
    }                                                                                 //GEN-LAST:event_chkHydrologieActionPerformed

    @Override
    public void dispose() {
        if (!readOnly) {
            vermessungsband.dispose();
        }
        disposeEditors();
        linearReferencedLineEditor.dispose();
        sbm.removeBandModelListener(modelListener);
        jband.dispose();
    }

    /**
     * DOCUMENT ME!
     */
    private void disposeEditors() {
        vermessungsEditor.dispose();
        operativesZielEditor.dispose();
        massnahmeEditor.dispose();
        umlandnutzungEditor.dispose();
        schutzgebietEditor.dispose();
        verbreitungsraumEditor.dispose();
        hydroEditor.dispose();
        entwicklungszielEditor.dispose();
        unterhaltungserfordernisEditor.dispose();
        vermessungsEditor.dispose();
        unterhaltungshinweisEditor.dispose();
        umlandnutzerEditor.dispose();
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
        vermessungsband.editorClosed(event);
        massnahmeEditor.editorClosed(event);
        umlandnutzungEditor.editorClosed(event);
        schutzgebietEditor.editorClosed(event);
        verbreitungsraumEditor.editorClosed(event);
        operativesZielEditor.editorClosed(event);
        hydroEditor.editorClosed(event);
        entwicklungszielEditor.editorClosed(event);
        unterhaltungserfordernisEditor.editorClosed(event);
        vermessungsEditor.editorClosed(event);
        unterhaltungshinweisEditor.editorClosed(event);
        umlandnutzerEditor.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        return linearReferencedLineEditor.prepareForSave();
    }

    @Override
    public JComponent getTitleComponent() {
        return panTitle;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static PflegezieleValidator getSearchValidator() {
        return searchValidator;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean getLastActivePflegezielBean() {
        return lastActiveMassnBean;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class GupOperativesZielBandModelListener implements BandModelListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void bandModelChanged(final BandModelEvent e) {
        }

        @Override
        public void bandModelSelectionChanged(final BandModelEvent e) {
            BandMember bm;
            disposeEditors();

            if (togApplyStats.isSelected()) {
                bm = vermessungsband.getSelectedMember();
                vermessungsband.setRefreshAvoided(true);
            } else {
                bm = jband.getSelectedBandMember();
                jband.setRefreshAvoided(true);
            }

            if (bm != null) {
                bgrpDetails.clearSelection();
                switchToForm("empty");
                lblHeading.setText("");

                if (bm instanceof OperativesZielRWBandMember) {
                    switchToForm("ziele");
                    lblHeading.setText("Pflegeziel");
                    final int type = ((OperativesZielRWBand)((OperativesZielRWBandMember)bm).getParentBand()).getType();
                    int kompartiment = 0;
                    List<CidsBean> otherBeans = null;

                    if (type == GUP_SOHLE) {
                        kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_SOHLE;
                        otherBeans = sohleList;
                    } else if (type == GUP_UFER_LINKS) {
                        kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UFER;
                        otherBeans = linkesUferList;
                    } else if (type == GUP_UFER_RECHTS) {
                        kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UFER;
                        otherBeans = rechtesUferList;
                    } else if (type == GUP_UMFELD_LINKS) {
                        kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UMFELD;
                        otherBeans = linkesUmfeldList;
                    } else if (type == GUP_UMFELD_RECHTS) {
                        kompartiment = GupOperativesZielAbschnittEditor.OPERATIVES_ZIEL_UMFELD;
                        otherBeans = rechtesUmfeldList;
                    }

                    if (!readOnly) {
                        lastActiveMassnBean = ((OperativesZielRWBandMember)bm).getCidsBean();
                    }
                    operativesZielEditor.unbind();
                    operativesZielEditor.setKompartiment(kompartiment);
                    operativesZielEditor.setOthers(otherBeans);
                    operativesZielEditor.setCidsBean(((OperativesZielRWBandMember)bm).getCidsBean());
                } else if (bm instanceof VermessungsbandMember) {
                    switchToForm("vermessung");
                    lblHeading.setText("Vermessungselement");
                    final List<CidsBean> others = vermessungsband.getAllMembers();
                    vermessungsEditor.setOthers(others);
                    vermessungsEditor.setCidsBean(((VermessungsbandMember)bm).getCidsBean());
                } else if (bm instanceof VermeidungsgruppeReadOnlyBandMember) {
                    switchToForm("verbreitungsraum");
                    lblHeading.setText("Verbreitungsraum");
                    verbreitungsraumEditor.setVerbreitungsraum(((VermeidungsgruppeReadOnlyBandMember)bm)
                                .getVermeidungsgruppe());
                    verbreitungsraumEditor.setCidsBean(((VermeidungsgruppeReadOnlyBandMember)bm).getCidsBean());
                } else if (bm instanceof ColoredReadOnlyBandMember) {
                    final String colorProp = ((ColoredReadOnlyBandMember)bm).getColorProperty();
                    if (((ColoredReadOnlyBandMember)bm).getCidsBean().getClass().getName().endsWith(
                                    "Gup_umlandnutzung")) {
                        switchToForm("umlandnutzung");
                        lblHeading.setText("Umlandnutzung");
                        umlandnutzungEditor.setCidsBean(((ColoredReadOnlyBandMember)bm).getCidsBean());
                    } else if (((ColoredReadOnlyBandMember)bm).getCidsBean().getClass().getName().endsWith(
                                    "hydrolog")) {
                        switchToForm("hydro");
                        lblHeading.setText("Hydrologie");
                        hydroEditor.setCidsBean(((ColoredReadOnlyBandMember)bm).getCidsBean());
                    } else if ((colorProp != null) && colorProp.equals("name_bezeichnung")) {
                        switchToForm("entwicklungsziel");
                        lblHeading.setText("Entwicklungsziel");
                        entwicklungszielEditor.setCidsBean(((ColoredReadOnlyBandMember)bm).getCidsBean());
                    } else if ((colorProp != null) && colorProp.equals("massnahme")) {
                        switchToForm("massnahme");
                        lblHeading.setText("Unterhaltungsmaßnahme");
                        massnahmeEditor.setCidsBean(((ColoredReadOnlyBandMember)bm).getCidsBean());
                    } else {
                        switchToForm("schutzgebiet");
                        lblHeading.setText("Schutzgebiet");
                        schutzgebietEditor.setCidsBean(((ColoredReadOnlyBandMember)bm).getCidsBean());
                    }
                } else if (bm instanceof UnterhaltungserfordernisBandMember) {
                    switchToForm("unterhaltungserfordernis");
                    lblHeading.setText("Situationstyp");
                    unterhaltungserfordernisEditor.setCidsBean(((UnterhaltungserfordernisBandMember)bm).getCidsBean());
                } else if (bm instanceof ReadOnlyTextBandMember) {
                    final String textProperty = ((ReadOnlyTextBandMember)bm).getTextProperty();
                    if ((textProperty != null) && textProperty.equals("art.name")) {
                        switchToForm("unterhaltungshinweis");
                        lblHeading.setText("Unterhaltungshinweis");
                        unterhaltungshinweisEditor.setCidsBean(((ReadOnlyTextBandMember)bm).getCidsBean());
                    } else {
                        switchToForm("umlandnutzer");
                        lblHeading.setText("Umlandnutzer");
                        umlandnutzerEditor.setCidsBean(((ReadOnlyTextBandMember)bm).getCidsBean());
                    }
                }
            } else {
                switchToForm("empty");
                lblHeading.setText("");
            }

            if (togApplyStats.isSelected()) {
                vermessungsband.setRefreshAvoided(false);
                vermessungsband.bandModelChanged();
            } else {
                jband.setRefreshAvoided(false);
                jband.bandModelChanged(null);
            }
        }

        @Override
        public void bandModelValuesChanged(final BandModelEvent e) {
        }
    }
}
