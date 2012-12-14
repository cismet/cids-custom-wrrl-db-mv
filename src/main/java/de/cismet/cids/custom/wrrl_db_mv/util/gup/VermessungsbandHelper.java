/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import com.vividsolutions.jts.geom.Geometry;

import java.awt.BorderLayout;
import java.awt.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.FeatureRegistry;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.PFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.CreateLinearReferencedMarksListener;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.CidsFeature;

import de.cismet.tools.gui.StaticSwingTools;
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
public class VermessungsbandHelper {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            VermessungsbandHelper.class);
    private static final String VERMESSUNG = "vermessung_band_element";

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private final JToggleButton togApplyStats;
    private WKBand vwkband;
    private final JBand jband;
    private PureNewFeature routeFeature;
    private final BandModelListener modelListener;
    private final javax.swing.JPanel panBand;
    private final javax.swing.JPanel panApplyBand;
    private final javax.swing.JPanel panApply;
    private VermessungsBand vermessungsBand = new VermessungsBand("Vermessung", VERMESSUNG);
    private JBand vBand = new JBand();
    private SimpleBandModel vBandModel = new SimpleBandModel();
    private CidsBean route;
    private List<CidsBean> createdBeans = new ArrayList<CidsBean>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new VermessungsbandHelper object.
     *
     * @param  jband          DOCUMENT ME!
     * @param  modelListener  DOCUMENT ME!
     * @param  panBand        DOCUMENT ME!
     * @param  panApplyBand   DOCUMENT ME!
     * @param  panApply       DOCUMENT ME!
     * @param  togApplyStats  DOCUMENT ME!
     */
    public VermessungsbandHelper(final JBand jband,
            final BandModelListener modelListener,
            final javax.swing.JPanel panBand,
            final javax.swing.JPanel panApplyBand,
            final javax.swing.JPanel panApply,
            final JToggleButton togApplyStats) {
        this.jband = jband;
        this.modelListener = modelListener;
        this.panBand = panBand;
        this.panApplyBand = panApplyBand;
        this.panApply = panApply;
        this.togApplyStats = togApplyStats;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  vwkband  DOCUMENT ME!
     */
    public void setVwkBand(final WKBand vwkband) {
        this.vwkband = vwkband;

        vBand.setModel(vBandModel);
        vBandModel.addBand(vwkband);
        vBandModel.addBand(new EmptyAbsoluteHeightedBand(5));
        vBandModel.addBand(vermessungsBand);
        vBandModel.addBandModelListener(modelListener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wk  DOCUMENT ME!
     */
    public void setWk(final ArrayList<ArrayList> wk) {
        vwkband.setWK(wk);
        vBandModel.fireBandModelChanged();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;

        togApplyStats.setEnabled(cidsBean.getProperty("linie") != null);
        showRoute();
    }

    /**
     * DOCUMENT ME!
     */
    public void showRoute() {
        final CidsBean station = (CidsBean)cidsBean.getProperty("linie.von");

        if ((station != null)) {
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
    public void showVermessungsband() {
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
            modelListener.bandModelSelectionChanged(null);
        } catch (Exception e) {
            LOG.error("Error while applying stations.", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void hideVermessungsband() {
        panBand.removeAll();
        panBand.add(jband, BorderLayout.CENTER);
//        updateUI();
        modelListener.bandModelSelectionChanged(null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  comp               DOCUMENT ME!
     * @param  bands              DOCUMENT ME!
     * @param  cidsBeanTableName  DOCUMENT ME!
     */
    public void applyStats(final Component comp, final LineBand[] bands, final String cidsBeanTableName) {
        try {
            final int res = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(comp),
                    "Wenn Sie die Abschnitte übernehmen, dann werden die bereits vorhandenen Abschnitte gelöscht. Wollen Sie fortrfahren?",
                    "Stationen übernehmen",
                    JOptionPane.OK_CANCEL_OPTION);

            if (res == JOptionPane.OK_OPTION) {
                for (final LineBand tmp : bands) {
                    final HashMap<CidsBean, CidsBean> stations = new HashMap<CidsBean, CidsBean>();
                    tmp.removeAllMember();
                    for (int i = 0; i < vermessungsBand.getNumberOfMembers(); ++i) {
                        final CidsBean bean = ((VermessungsbandMember)vermessungsBand.getMember(i)).getCidsBean();
                        final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName(cidsBeanTableName);
                        final CidsBean von = getStationCopy((CidsBean)bean.getProperty("linie.von"), stations);
                        final CidsBean bis = getStationCopy((CidsBean)bean.getProperty("linie.bis"), stations);

                        tmp.addMember(newBean, von, bis);
                    }
                }
            }
            togApplyStats.setSelected(false);
            hideVermessungsband();
        } catch (Exception e) {
            LOG.error("Error while creating the initial bands.", e);
        }
    }

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
            try {
                bean = bean.persist();
                createdBeans.add(bean);
                final CidsBean geom = (CidsBean)bean.getProperty(LinearReferencingConstants.PROP_STATION_GEOM);

                if (geom != null) {
                    createdBeans.add(geom);
                }
            } catch (Exception e) {
                LOG.error("Cannot persist station.", e);
            }

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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<CidsBean> getAllMembers() {
        final List<CidsBean> others = new ArrayList<CidsBean>();
        for (int i = 0; i < vermessungsBand.getNumberOfMembers(); ++i) {
            others.add(((VermessungsbandMember)vermessungsBand.getMember(i)).getCidsBean());
        }

        return others;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public BandMember getSelectedMember() {
        return vBand.getSelectedBandMember();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  zoom  DOCUMENT ME!
     */
    public void setZoomFactor(final double zoom) {
        vBand.setZoomFactor(zoom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  avoided  DOCUMENT ME!
     */
    public void setRefreshAvoided(final boolean avoided) {
        vBand.setRefreshAvoided(avoided);
    }

    /**
     * DOCUMENT ME!
     */
    public void bandModelChanged() {
        vBand.bandModelChanged(null);
    }

    /**
     * DOCUMENT ME!
     */
    public void dispose() {
        if (route != null) {
            FeatureRegistry.getInstance().removeRouteFeature(route);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  event  DOCUMENT ME!
     */
    public void editorClosed(final EditorClosedEvent event) {
        if ((event.getStatus() == EditorSaveListener.EditorSaveStatus.CANCELED) && (createdBeans != null)) {
            for (final CidsBean c : createdBeans) {
                try {
                    c.delete();
                    c.persist();
                } catch (Exception e) {
                    LOG.error("Error while deleting unused bean.", e);
                }
            }
        }
    }
}
