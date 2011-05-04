/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.DecimalFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.LinearReferencedLineEditorDropBehavior;
import de.cismet.cids.custom.util.LinearReferencingConstants;
import de.cismet.cids.custom.util.LinearReferencingHelper;
import de.cismet.cids.custom.util.StationToMapRegistryListener;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.Crs;
import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeatureListener;
import de.cismet.cismap.commons.interaction.CismapBroker;
import de.cismet.cismap.commons.interaction.CrsChangeListener;
import de.cismet.cismap.commons.interaction.events.CrsChangedEvent;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class LinearReferencedLineEditor extends JPanel implements DisposableCidsBeanStore,
    LinearReferencingConstants,
    CidsBeanDropListener,
    EditorSaveListener {

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private enum Card {

        //~ Enum constants -----------------------------------------------------

        edit, add, error
    }

    //~ Instance fields --------------------------------------------------------

    private LinearReferencedLineFeature feature;
    private LinearReferencedPointFeature fromFeature;
    private LinearReferencedPointFeature toFeature;
    private boolean isFromSpinnerChangeLocked = false;
    private boolean isFromFeatureChangeLocked = false;
    private boolean isFromBeanChangeLocked = false;
    private boolean isToSpinnerChangeLocked = false;
    private boolean isToFeatureChangeLocked = false;
    private boolean isToBeanChangeLocked = false;
    private PropertyChangeListener fromStationBeanListener;
    private PropertyChangeListener toStationBeanListener;
    private LinearReferencedPointFeatureListener fromFeatureListener;
    private LinearReferencedPointFeatureListener toFeatureListener;
    private StationToMapRegistryListener fromStationToMapRegistryListener;
    private StationToMapRegistryListener toStationToMapRegistryListener;
    private LinearReferencedLineEditorDropBehavior dropBehavior;
    private CrsChangeListener crsChangeListener;
    private Feature fromBadGeomFeature;
    private Feature toBadGeomFeature;
    private String metaClassName;
    private String lineField;
    private CidsBean cidsBean;
    private BoundingBox boundingbox;

    private boolean inited = false;

    private boolean changedSinceDrop = false;

    private Collection<LinearReferencedLineEditorListener> listeners =
        new ArrayList<LinearReferencedLineEditorListener>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton fromBadGeomButton;
    private javax.swing.JButton fromBadGeomCorrectButton;
    private javax.swing.JButton fromStationSplitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel labGwk;
    private javax.swing.JLabel lblError;
    private javax.swing.JPanel panAdd;
    private javax.swing.JPanel panEdit;
    private javax.swing.JPanel panError;
    private javax.swing.JPanel panLine;
    private javax.swing.JPanel panSpinner;
    private javax.swing.JSpinner spinFrom;
    private javax.swing.JSpinner spinTo;
    private javax.swing.JToggleButton toBadGeomButton;
    private javax.swing.JButton toBadGeomCorrectButton;
    private javax.swing.JButton toStationSplitButton;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LinearReferencedLineEditor.
     */
    public LinearReferencedLineEditor() {
        initComponents();

        try {
            new CidsBeanDropTarget(this);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while creating CidsBeanDropTarget", ex);
            }
        }

        initCidsBeanListener(FROM);
        initCidsBeanListener(TO);
        initFeatureListener(FROM);
        initFeatureListener(TO);
        initSpinnerListener(FROM);
        initSpinnerListener(TO);
        initStationToMapRegistryListener(FROM);
        initStationToMapRegistryListener(TO);

        initCrsChangeListener();

        CismapBroker.getInstance().addCrsChangeListener(getCrsChangeListener());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  metaClassName  DOCUMENT ME!
     * @param  lineField      DOCUMENT ME!
     */
    public final void setFields(final String metaClassName,
            final String lineField) {
        setMetaClassName(metaClassName);
        setLineField(lineField);
    }

    /**
     * DOCUMENT ME!
     */
    private void fireLineAdded() {
        for (final LinearReferencedLineEditorListener listener : listeners) {
            listener.linearReferencedLineCreated();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean addLinearReferencedLineEditorListener(final LinearReferencedLineEditorListener listener) {
        return listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean removeLinearReferencedLineEditorListener(final LinearReferencedLineEditorListener listener) {
        return listeners.remove(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LinearReferencedLineFeature getFeature() {
        return feature;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  feature  DOCUMENT ME!
     */
    private void setFeature(final LinearReferencedLineFeature feature) {
        this.feature = feature;
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * >> BEAN.
     */
    private void cleanup() {
        if (isInited()) {
            final LinearReferencedLineFeature oldFeature = getFeature();
            if (oldFeature != null) {
                final CidsBean oldBean = STATION_TO_MAP_REGISTRY.getCidsBean(oldFeature);
                STATION_TO_MAP_REGISTRY.removeLinearReferencedLineFeature(oldBean);
            }
            setFeature(null);

            cleanupStationBean(FROM);
            cleanupStationBean(TO);

            showCard(Card.add);

            setInited(false);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void cleanupStationBean(final boolean isFrom) {
        final StationToMapRegistryListener stationToMapRegistryListener = getStationToMapRegistryListener(isFrom);
        final PropertyChangeListener stationBeanListener = getStationBeanListener(isFrom);
        final LinearReferencedPointFeatureListener stationFeatureListener = getStationFeatureListener(isFrom);

        // aufräumen
        final LinearReferencedPointFeature stationFeature = getStationFeature(isFrom);
        if (stationFeature != null) {
            final CidsBean stationBean = STATION_TO_MAP_REGISTRY.getCidsBean(stationFeature);
            if (stationBean != null) {
                STATION_TO_MAP_REGISTRY.removeStationFeature(stationBean);
                stationFeature.removeListener(stationFeatureListener);
                stationBean.removePropertyChangeListener(stationBeanListener);
                STATION_TO_MAP_REGISTRY.removeListener(stationBean, stationToMapRegistryListener);
            }
            // bean ist null, feature also auch
            setLinearReferencedPointFeature(null, isFrom);

            if (getBadGeomFeature(isFrom) != null) {
                MAPPING_COMPONENT.getFeatureCollection().removeFeature(getBadGeomFeature(isFrom));
                setBadGeomFeature(null, isFrom);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void init() {
        // wird das aktuelle crs unterstützt ?
        if (!isCrsSupported(CismapBroker.getInstance().getSrs())) {
            showCrsNotSupported();
            // noch nicht initialisiert ?
        } else if (!isInited()) {
            final CidsBean lineBean = getLineBean();
            if (lineBean != null) {
                initStation(FROM);
                initStation(TO);

                // feature erzeugen
                final LinearReferencedLineFeature feature = STATION_TO_MAP_REGISTRY.addLinearReferencedLineFeature(
                        lineBean,
                        getStationFeature(FROM),
                        getStationFeature(TO));

                // farbe setzen
                final Color color = (Color)feature.getLinePaint();
                panLine.setBackground(color);

                setFeature(feature);
                fireLineAdded();

                stationBeanChanged(FROM);
                stationBeanChanged(TO);

                ((SpinnerNumberModel)getStationSpinner(FROM).getModel()).setMaximum(Math.ceil(
                        getRouteGeometry().getLength()));
                ((SpinnerNumberModel)getStationSpinner(TO).getModel()).setMaximum(Math.ceil(
                        getRouteGeometry().getLength()));
                labGwk.setText("Route: "
                            + Long.toString(LinearReferencingHelper.getRouteGwkFromStationBean(getStationBean(FROM))));

                showCard(Card.edit);

                setInited(true);
            } else {
                showCard(Card.add);
            }
        }
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        // aufräumen falls vorher cidsbean schon gesetzt war
        cleanup();

        // neue cidsbean setzen
        this.cidsBean = cidsBean;

        // neu initialisieren
        init();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean hasChangedSinceDrop() {
        return changedSinceDrop;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  changedSinceDrop  DOCUMENT ME!
     */
    private void setChangedSinceDrop(final boolean changedSinceDrop) {
        this.changedSinceDrop = changedSinceDrop;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isInited() {
        return inited;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  inited  DOCUMENT ME!
     */
    private void setInited(final boolean inited) {
        this.inited = inited;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JButton getStationSplitButton(final boolean isFrom) {
        return (isFrom) ? fromStationSplitButton : toStationSplitButton;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getStationField(final boolean isFrom) {
        return (isFrom) ? PROP_STATIONLINIE_FROM : PROP_STATIONLINIE_TO;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lineField  DOCUMENT ME!
     */
    private void setLineField(final String lineField) {
        this.lineField = lineField;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getLineField() {
        return lineField;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean hasBadGeomFeature() {
        return (getBadGeomFeature(FROM) != null) && (getBadGeomFeature(TO) != null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Feature getBadGeomFeature(final boolean isFrom) {
        return (isFrom) ? fromBadGeomFeature : toBadGeomFeature;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  badGeomFeature  DOCUMENT ME!
     * @param  isFrom          DOCUMENT ME!
     */
    private void setBadGeomFeature(final Feature badGeomFeature, final boolean isFrom) {
        if (isFrom) {
            fromBadGeomFeature = badGeomFeature;
        } else {
            toBadGeomFeature = badGeomFeature;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JButton getBadGeomCorrectButton(final boolean isFrom) {
        return (isFrom) ? fromBadGeomCorrectButton : toBadGeomCorrectButton;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JToggleButton getBadGeomButton(final boolean isFrom) {
        return (isFrom) ? fromBadGeomButton : toBadGeomButton;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private StationToMapRegistryListener getStationToMapRegistryListener(final boolean isFrom) {
        return (isFrom) ? fromStationToMapRegistryListener : toStationToMapRegistryListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     * @param  isFrom    DOCUMENT ME!
     */
    private void setStationToMapRegistryListener(final StationToMapRegistryListener listener, final boolean isFrom) {
        if (isFrom) {
            fromStationToMapRegistryListener = listener;
        } else {
            toStationToMapRegistryListener = listener;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CrsChangeListener getCrsChangeListener() {
        return crsChangeListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  crsChangeListener  DOCUMENT ME!
     */
    private void setCrsChangeListener(final CrsChangeListener crsChangeListener) {
        this.crsChangeListener = crsChangeListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private PropertyChangeListener getStationBeanListener(final boolean isFrom) {
        return (isFrom) ? fromStationBeanListener : toStationBeanListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     * @param  isFrom    DOCUMENT ME!
     */
    private void setStationBeanListener(final PropertyChangeListener listener, final boolean isFrom) {
        if (isFrom) {
            fromStationBeanListener = listener;
        } else {
            toStationBeanListener = listener;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private LinearReferencedPointFeature getStationFeature(final boolean isFrom) {
        return (isFrom) ? fromFeature : toFeature;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stationFeature  DOCUMENT ME!
     * @param  isFrom          DOCUMENT ME!
     */
    private void setLinearReferencedPointFeature(final LinearReferencedPointFeature stationFeature,
            final boolean isFrom) {
        if (isFrom) {
            fromFeature = stationFeature;
        } else {
            toFeature = stationFeature;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private LinearReferencedPointFeatureListener getStationFeatureListener(final boolean isFrom) {
        return (isFrom) ? fromFeatureListener : toFeatureListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     * @param  isFrom    DOCUMENT ME!
     */
    private void setStationFeatureListener(final LinearReferencedPointFeatureListener listener, final boolean isFrom) {
        if (isFrom) {
            fromFeatureListener = listener;
        } else {
            toFeatureListener = listener;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JSpinner getStationSpinner(final boolean isFrom) {
        return (isFrom) ? spinFrom : spinTo;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getMetaClassName() {
        return metaClassName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  metaClassName  DOCUMENT ME!
     */
    private void setMetaClassName(final String metaClassName) {
        this.metaClassName = metaClassName;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LinearReferencedLineEditorDropBehavior getDropBehavior() {
        return dropBehavior;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  dropBehavior  DOCUMENT ME!
     */
    public void setDropBehavior(final LinearReferencedLineEditorDropBehavior dropBehavior) {
        this.dropBehavior = dropBehavior;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initStation(final boolean isFrom) {
        final StationToMapRegistryListener stationToMapRegistryListener = getStationToMapRegistryListener(isFrom);
        final PropertyChangeListener stationBeanListener = getStationBeanListener(isFrom);
        final LinearReferencedPointFeatureListener stationFeatureListener = getStationFeatureListener(isFrom);

        final CidsBean stationBean = getStationBean(isFrom);
        if (stationBean != null) {
            final double distance = LinearReferencingHelper.distanceOfStationGeomToRouteGeomFromStationBean(
                    stationBean);

            if (distance > 1) {
                setBadGeomFeature(StationEditor.createBadGeomFeature(
                        LinearReferencingHelper.getPointGeometryFromStationBean(stationBean)),
                    isFrom);
            } else {
                setBadGeomFeature(null, isFrom);
            }
            updateBadGeomButton(isFrom);

            // bean listener
            stationBean.addPropertyChangeListener(stationBeanListener);
            STATION_TO_MAP_REGISTRY.addListener(stationBean, stationToMapRegistryListener);

            // feature erzeugen
            final LinearReferencedPointFeature stationFeature = STATION_TO_MAP_REGISTRY.addStationFeature(stationBean);

            // feature listener
            stationFeature.addListener(stationFeatureListener);

            // feature setzen
            setLinearReferencedPointFeature(stationFeature, isFrom);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stationBean  DOCUMENT ME!
     * @param  isFrom       DOCUMENT ME!
     */
    private void setStationBean(final CidsBean stationBean, final boolean isFrom) {
        cleanupStationBean(isFrom);

        // bean setzen
        if (getCidsBean() != null) {
            try {
                getLineBean().setProperty(getStationField(isFrom), stationBean);
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error while setting cidsbean for station", ex);
                }
            }
        }

        initStation(isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void badGeomButtonPressed(final boolean isFrom) {
        final Feature badGeomFeature = getBadGeomFeature(isFrom);
        final Feature stationFeature = getStationFeature(isFrom);

        final boolean selected = getBadGeomButton(isFrom).isSelected();

        if (selected) {
            boundingbox = MAPPING_COMPONENT.getCurrentBoundingBox();

            MAPPING_COMPONENT.getFeatureCollection().addFeature(badGeomFeature);
            MAPPING_COMPONENT.getFeatureCollection().select(stationFeature);

            zoomToBadFeature(isFrom);
        } else {
            MAPPING_COMPONENT.getFeatureCollection().removeFeature(badGeomFeature);
            MAPPING_COMPONENT.gotoBoundingBoxWithoutHistory(boundingbox);
        }

        getBadGeomCorrectButton(isFrom).setVisible(selected);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void zoomToBadFeature(final boolean isFrom) {
        final Feature badGeomFeature = getBadGeomFeature(isFrom);
        final Collection<Feature> aFeatureCollection = new ArrayList<Feature>();
        aFeatureCollection.add(badGeomFeature);
        aFeatureCollection.add(getFeature());
        // TODO boundingbox
        MAPPING_COMPONENT.zoomToAFeatureCollection(aFeatureCollection, false, MAPPING_COMPONENT.isFixedMapScale());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void badGeomCorrectButtonPressed(final boolean isFrom) {
        final LinearReferencedPointFeature feature = getStationFeature(isFrom);
        final Feature badFeature = getBadGeomFeature(isFrom);
        feature.moveTo(badFeature.getGeometry().getCoordinate());
        zoomToBadFeature(isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  card  DOCUMENT ME!
     */
    private void showCard(final Card card) {
        switch (card) {
            case edit: {
                ((CardLayout)getLayout()).show(this, "edit");
                break;
            }
            case add: {
                ((CardLayout)getLayout()).show(this, "add");
                break;
            }
            case error: {
                ((CardLayout)getLayout()).show(this, "error");
                break;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  routeBean  DOCUMENT ME!
     * @param  cidsBean   DOCUMENT ME!
     * @param  lineField  DOCUMENT ME!
     */
    public static void fillFromRoute(final CidsBean routeBean, final CidsBean cidsBean, final String lineField) {
        final MetaClass linieMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, CN_STATIONLINE);
        final MetaClass stationMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, CN_STATION);
        final MetaClass geomMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, CN_GEOM);

        final CidsBean linieBean = linieMC.getEmptyInstance().getBean();
        final CidsBean fromBean = stationMC.getEmptyInstance().getBean();
        final CidsBean toBean = stationMC.getEmptyInstance().getBean();
        final CidsBean geomBean = geomMC.getEmptyInstance().getBean();
        final CidsBean fromGeomBean = geomMC.getEmptyInstance().getBean();
        final CidsBean toGeomBean = geomMC.getEmptyInstance().getBean();

        try {
            fromBean.setProperty(PROP_STATION_ROUTE, routeBean);
            fromBean.setProperty(PROP_STATION_VALUE, 0d);
            fromBean.setProperty(PROP_STATION_GEOM, fromGeomBean);

            toBean.setProperty(PROP_STATION_ROUTE, routeBean);
            toBean.setProperty(
                PROP_STATION_VALUE,
                ((Geometry)((CidsBean)routeBean.getProperty(PROP_ROUTE_GEOM)).getProperty(PROP_GEOM_GEOFIELD))
                            .getLength());
            toBean.setProperty(PROP_STATION_GEOM, toGeomBean);

            linieBean.setProperty(PROP_STATIONLINIE_FROM, fromBean);
            linieBean.setProperty(PROP_STATIONLINIE_TO, toBean);
            linieBean.setProperty(PROP_STATIONLINIE_GEOM, geomBean);

            cidsBean.setProperty(lineField, linieBean);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while filling bean", ex);
            }
        }
    }

    @Override
    public void dispose() {
        cleanup();

        CismapBroker.getInstance().removeCrsChangeListener(getCrsChangeListener());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initStationToMapRegistryListener(final boolean isFrom) {
        final StationToMapRegistryListener listener = new StationToMapRegistryListener() {

                @Override
                public void FeatureCountChanged() {
                    updateSplitButton(isFrom);
                }
            };

        setStationToMapRegistryListener(listener, isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initSpinnerListener(final boolean isFrom) {
        final JSpinner spinner = getStationSpinner(isFrom);
        ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField()
                .getDocument()
                .addDocumentListener(new DocumentListener() {

                        @Override
                        public void insertUpdate(final DocumentEvent de) {
                            spinnerChanged(isFrom);
                        }

                        @Override
                        public void removeUpdate(final DocumentEvent de) {
                            spinnerChanged(isFrom);
                        }

                        @Override
                        public void changedUpdate(final DocumentEvent de) {
                            spinnerChanged(isFrom);
                        }
                    });
        ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().addFocusListener(new FocusAdapter() {

                @Override
                public void focusGained(final FocusEvent fe) {
                    MAPPING_COMPONENT.getFeatureCollection().select(getStationFeature(isFrom));
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initFeatureListener(final boolean isFrom) {
        final LinearReferencedPointFeatureListener featureListener = new LinearReferencedPointFeatureListener() {

                @Override
                public void featureMoved(final LinearReferencedPointFeature pointFeature) {
                    featureChanged(isFrom);
                }

                @Override
                public void featureMerged(final LinearReferencedPointFeature mergePoint,
                        final LinearReferencedPointFeature withPoint) {
                    final CidsBean withBean = STATION_TO_MAP_REGISTRY.getCidsBean(withPoint);

                    final LinearReferencedPointFeature fromFeature = getStationFeature(FROM);
                    final LinearReferencedPointFeature toFeature = getStationFeature(TO);
                    if ((fromFeature.equals(mergePoint) && !toFeature.equals(withPoint))
                                || (toFeature.equals(mergePoint) && !fromFeature.equals(withPoint))) {
                        final boolean isFrom = fromFeature.equals(mergePoint);

                        // neue bean übernehmen
                        setStationBean(withBean, isFrom);
                        stationBeanChanged(isFrom);

                        updateSplitButton(isFrom);
                    }
                }
            };

        setStationFeatureListener(featureListener, isFrom);
    }

    /**
     * DOCUMENT ME!
     */
    private void initCrsChangeListener() {
        setCrsChangeListener(new CrsChangeListener() {

                @Override
                public void crsChanged(final CrsChangedEvent event) {
                    if (!isCrsSupported(event.getCurrentCrs())) {
                        showCrsNotSupported();
                    } else {
                        init();
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    private void showCrsNotSupported() {
        cleanup();
        setErrorMsg("Das aktuelle CRS wird vom Stationierungseditor nicht unterstützt.");
        showCard(Card.error);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  msg  DOCUMENT ME!
     */
    private void setErrorMsg(final String msg) {
        lblError.setText(msg);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   crs  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isCrsSupported(final Crs crs) {
        return CrsTransformer.extractSridFromCrs(crs.getCode()) == 35833;
    }

    /**
     * cidsbean ändern.
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void featureChanged(final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("feature changed", new CurrentStackTrace());
        }
        try {
            lockFeatureChange(true, isFrom);

            if (!isBeanChangeLocked(isFrom)) {
                final LinearReferencedPointFeature linearRefFeature = getStationFeature(isFrom);

                final double value = Math.round(linearRefFeature.getCurrentPosition() * 100) / 100d;
                changeStationValue(value, isFrom);
            }
        } finally {
            lockFeatureChange(false, isFrom);
        }
    }
    /**
     * cidsbean ändern.
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void spinnerChanged(final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("spinner changed", new CurrentStackTrace());
        }
        try {
            lockSpinnerChange(true, isFrom);

            final AbstractFormatter formatter = ((JSpinner.DefaultEditor)getStationSpinner(isFrom).getEditor())
                        .getTextField().getFormatter();
            final String text = ((JSpinner.DefaultEditor)getStationSpinner(isFrom).getEditor()).getTextField()
                        .getText();
            if (!text.isEmpty()) {
                try {
                    final double value = (Double)formatter.stringToValue(text);
                    changeStationValue(value, isFrom);
                } catch (ParseException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("error parsing spinner", ex);
                    }
                }
            }
        } finally {
            lockSpinnerChange(false, isFrom);
        }
    }
    /**
     * spinner ändern feature ändern realgeoms anpassen.
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void stationBeanChanged(final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("cidsbean changed", new CurrentStackTrace());
        }

        try {
            lockBeanChange(true, isFrom);

            setChangedSinceDrop(true);

            final CidsBean stationBean = getStationBean(isFrom);

            if (stationBean != null) {
                final double value = (Double)stationBean.getProperty(PROP_STATION_VALUE);

                changeSpinner(value, isFrom);
                changeFeature(value, isFrom);

                // realgeoms nur nach manueller eingabe updaten
                if (isInited()) {
                    updateRealGeoms(isFrom);
                }
            }
        } finally {
            lockBeanChange(false, isFrom);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public double getStationValue(final boolean isFrom) {
        final CidsBean stationBean = getStationBean(isFrom);
        if (stationBean != null) {
            return LinearReferencingHelper.getLinearValueFromStationBean(stationBean);
        } else {
            return 0d;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void updateSplitButton(final boolean isFrom) {
        if (getCidsBean() != null) {
            final CidsBean stationBean = getStationBean(isFrom);
            if (stationBean != null) {
                final JButton stationButton = getStationSplitButton(isFrom);
                stationButton.setVisible(STATION_TO_MAP_REGISTRY.getCounter(stationBean) > 1);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void updateBadGeomButton(final boolean isFrom) {
        final boolean visible = getBadGeomFeature(isFrom) != null;
        getBadGeomButton(isFrom).setVisible(visible);
        getBadGeomCorrectButton(isFrom).setVisible(visible && getBadGeomButton(isFrom).isSelected());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void changeSpinner(final double value, final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("change spinner");
        }

        if (!isSpinnerChangeLocked(isFrom)) {
            final JSpinner stationSpinner = getStationSpinner(isFrom);
            stationSpinner.setValue(Math.round(value));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void changeFeature(final double value, final boolean isFrom) {
        if (!isFeatureChangeLocked(isFrom)) {
            final LinearReferencedPointFeature stationFeature = getStationFeature(isFrom);
            if (stationFeature != null) {
                stationFeature.setInfoFormat(new DecimalFormat("###"));
                stationFeature.moveToPosition(value);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("there are no feature to move");
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void updateRealGeoms(final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("update real geom");
        }

        try {
            final LinearReferencedPointFeature stationFeature = getStationFeature(isFrom);
            final CidsBean stationBean = getStationBean(isFrom);

            // realgeom der Station anpassen
            if (stationFeature != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("change station geom");
                }

                final Geometry pointGeom = LinearReferencedPointFeature.getPointOnLine(LinearReferencingHelper
                                .getLinearValueFromStationBean(stationBean),
                        LinearReferencingHelper.getRouteGeometryFromStationBean(stationBean));
                pointGeom.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
                LinearReferencingHelper.setPointGeometryToStationBean(pointGeom, stationBean);
            }
            // realgeom der Linie anpassen
            if (getFeature() != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("change line geom");
                }
                final Geometry lineGeom = LinearReferencedLineFeature.createSubline(getStationValue(FROM),
                        getStationValue(TO),
                        LinearReferencingHelper.getRouteGeometryFromStationBean(getStationBean(isFrom)));
                lineGeom.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
                setGeometry(lineGeom);
            }
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while setting real geoms", ex);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void changeStationValue(final double value, final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("change bean value to " + value);
        }
        final CidsBean stationBean = getStationBean(isFrom);
        final double oldValue = LinearReferencingHelper.getLinearValueFromStationBean(stationBean);

        if (oldValue != value) {
            try {
                if (!isFeatureChangeLocked(isFrom)) {
                    MAPPING_COMPONENT.getFeatureCollection().select(getStationFeature(isFrom));
                }
                if (!isBeanChangeLocked(isFrom)) {
                    LinearReferencingHelper.setLinearValueToStationBean((double)Math.round(value), stationBean);
                }
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error changing bean", ex);
                }
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("no changes needed, old value was " + oldValue);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isFeatureChangeLocked(final boolean isFrom) {
        return (isFrom) ? isFromFeatureChangeLocked : isToFeatureChangeLocked;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isSpinnerChangeLocked(final boolean isFrom) {
        return (isFrom) ? isFromSpinnerChangeLocked : isToSpinnerChangeLocked;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isBeanChangeLocked(final boolean isFrom) {
        return (isFrom) ? isFromBeanChangeLocked : isToBeanChangeLocked;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lock    DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void lockFeatureChange(final boolean lock, final boolean isFrom) {
        if (isFrom) {
            isFromFeatureChangeLocked = lock;
        } else {
            isToFeatureChangeLocked = lock;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lock    DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void lockSpinnerChange(final boolean lock, final boolean isFrom) {
        if (isFrom) {
            isFromSpinnerChangeLocked = lock;
        } else {
            isToSpinnerChangeLocked = lock;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lock    DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void lockBeanChange(final boolean lock, final boolean isFrom) {
        if (isFrom) {
            isFromBeanChangeLocked = lock;
        } else {
            isToBeanChangeLocked = lock;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initCidsBeanListener(final boolean isFrom) {
        final PropertyChangeListener stationBeanListener = new PropertyChangeListener() {

                @Override
                public void propertyChange(final PropertyChangeEvent pce) {
                    if (pce.getPropertyName().equals(PROP_STATION_VALUE)) {
                        stationBeanChanged(isFrom);
                    }
                }
            };

        setStationBeanListener(stationBeanListener, isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Geometry getRouteGeometry() {
        final CidsBean routeGeomBean = getRouteGeomBean();
        if (routeGeomBean == null) {
            return null;
        }
        return (Geometry)routeGeomBean.getProperty(PROP_GEOM_GEOFIELD);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getStationBean(final boolean isFrom) {
        return LinearReferencingHelper.getStationBeanFromLineBean(getLineBean(), isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getLineBean() {
        return getLineBean(getCidsBean(), getLineField());
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean   DOCUMENT ME!
     * @param   lineField  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static CidsBean getLineBean(final CidsBean cidsBean, final String lineField) {
        if (cidsBean == null) {
            return null;
        }
        return (CidsBean)cidsBean.getProperty(lineField);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getRouteBean() {
        final CidsBean stationBean = getStationBean(FROM);
        if (stationBean == null) {
            return null;
        }
        return (CidsBean)stationBean.getProperty(PROP_STATION_ROUTE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getRouteGeomBean() {
        final CidsBean routeBean = getRouteBean();
        if (routeBean == null) {
            return null;
        }
        return (CidsBean)routeBean.getProperty(PROP_ROUTE_GEOM);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   line  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void setGeometry(final Geometry line) throws Exception {
        LinearReferencingHelper.setGeometryToLineBean(line, getLineBean());
    }

    @Override
    public void setEnabled(final boolean bln) {
        super.setEnabled(bln);
        jLabel3.setVisible(bln);
        getStationSpinner(FROM).setEnabled(bln);
        getStationSpinner(TO).setEnabled(bln);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panEdit = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panLine = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labGwk = new javax.swing.JLabel();
        fromBadGeomButton = new javax.swing.JToggleButton();
        toBadGeomButton = new javax.swing.JToggleButton();
        fromBadGeomCorrectButton = new javax.swing.JButton();
        toBadGeomCorrectButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        panSpinner = new javax.swing.JPanel();
        spinFrom = new javax.swing.JSpinner();
        spinTo = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        fromStationSplitButton = new javax.swing.JButton();
        toStationSplitButton = new javax.swing.JButton();
        panAdd = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panError = new javax.swing.JPanel();
        lblError = new javax.swing.JLabel();

        setOpaque(false);
        setLayout(new java.awt.CardLayout());

        panEdit.setOpaque(false);
        panEdit.setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        panLine.setBackground(new java.awt.Color(255, 91, 0));
        panLine.setMinimumSize(new java.awt.Dimension(10, 4));
        panLine.setPreferredSize(new java.awt.Dimension(100, 4));
        panLine.setLayout(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(panLine, gridBagConstraints);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel1.text_1")); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(28, 28));
        jLabel1.setMinimumSize(new java.awt.Dimension(28, 28));
        jLabel1.setPreferredSize(new java.awt.Dimension(28, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel2.text_1")); // NOI18N
        jLabel2.setMaximumSize(new java.awt.Dimension(28, 28));
        jLabel2.setMinimumSize(new java.awt.Dimension(28, 28));
        jLabel2.setPreferredSize(new java.awt.Dimension(28, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel5.text_1"));                                           // NOI18N
        jLabel5.setMaximumSize(new java.awt.Dimension(13, 28));
        jLabel5.setMinimumSize(new java.awt.Dimension(13, 28));
        jLabel5.setPreferredSize(new java.awt.Dimension(13, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        jLabel6.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel6.text_1"));                                           // NOI18N
        jLabel6.setMaximumSize(new java.awt.Dimension(13, 28));
        jLabel6.setMinimumSize(new java.awt.Dimension(13, 28));
        jLabel6.setPreferredSize(new java.awt.Dimension(13, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jLabel6, gridBagConstraints);

        labGwk.setForeground(new java.awt.Color(128, 128, 128));
        labGwk.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labGwk.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.labGwk.text_1")); // NOI18N
        labGwk.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(labGwk, gridBagConstraints);

        fromBadGeomButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/exclamation.png"))); // NOI18N
        fromBadGeomButton.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.fromBadGeomButton.text"));                                       // NOI18N
        fromBadGeomButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.fromBadGeomButton.toolTipText"));                                // NOI18N
        fromBadGeomButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    fromBadGeomButtonActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(fromBadGeomButton, gridBagConstraints);

        toBadGeomButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/exclamation.png"))); // NOI18N
        toBadGeomButton.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.toBadGeomButton.text"));                                         // NOI18N
        toBadGeomButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.toBadGeomButton.toolTipText"));                                  // NOI18N
        toBadGeomButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    toBadGeomButtonActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(toBadGeomButton, gridBagConstraints);

        fromBadGeomCorrectButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/node-delete.png"))); // NOI18N
        fromBadGeomCorrectButton.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.fromBadGeomCorrectButton.text_1"));                              // NOI18N
        fromBadGeomCorrectButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.fromBadGeomCorrectButton.toolTipText"));                         // NOI18N
        fromBadGeomCorrectButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    fromBadGeomCorrectButtonActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(fromBadGeomCorrectButton, gridBagConstraints);

        toBadGeomCorrectButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/node-delete-child.png"))); // NOI18N
        toBadGeomCorrectButton.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.toBadGeomCorrectButton.text_1"));                                      // NOI18N
        toBadGeomCorrectButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.toBadGeomCorrectButton.toolTipText"));                                 // NOI18N
        toBadGeomCorrectButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    toBadGeomCorrectButtonActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(toBadGeomCorrectButton, gridBagConstraints);

        jPanel2.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel5.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 30);
        jPanel1.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panEdit.add(jPanel1, gridBagConstraints);

        panSpinner.setOpaque(false);
        panSpinner.setLayout(new java.awt.GridBagLayout());

        spinFrom.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 0.0d, 1.0d));
        spinFrom.setEditor(new javax.swing.JSpinner.NumberEditor(spinFrom, "###"));
        spinFrom.setMinimumSize(new java.awt.Dimension(100, 28));
        spinFrom.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        panSpinner.add(spinFrom, gridBagConstraints);

        spinTo.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 0.0d, 1.0d));
        spinTo.setEditor(new javax.swing.JSpinner.NumberEditor(spinTo, "###"));
        spinTo.setMinimumSize(new java.awt.Dimension(100, 28));
        spinTo.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        panSpinner.add(spinTo, gridBagConstraints);

        jPanel4.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panSpinner.add(jPanel4, gridBagConstraints);

        fromStationSplitButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/sql-join-left.png"))); // NOI18N
        fromStationSplitButton.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.fromStationSplitButton.text"));                                    // NOI18N
        fromStationSplitButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.fromStationSplitButton.toolTipText"));                             // NOI18N
        fromStationSplitButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    fromStationSplitButtonActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panSpinner.add(fromStationSplitButton, gridBagConstraints);

        toStationSplitButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/sql-join-right.png"))); // NOI18N
        toStationSplitButton.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.toStationSplitButton.text"));                                       // NOI18N
        toStationSplitButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.toStationSplitButton.toolTipText"));                                // NOI18N
        toStationSplitButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    toStationSplitButtonActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panSpinner.add(toStationSplitButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panEdit.add(panSpinner, gridBagConstraints);

        add(panEdit, "edit");

        panAdd.setOpaque(false);
        panAdd.setLayout(new java.awt.GridBagLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel3.text_1")); // NOI18N
        panAdd.add(jLabel3, new java.awt.GridBagConstraints());

        add(panAdd, "add");

        panError.setOpaque(false);
        panError.setLayout(new java.awt.GridBagLayout());

        lblError.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblError.text_1")); // NOI18N
        panError.add(lblError, new java.awt.GridBagConstraints());

        add(panError, "error");
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void fromStationSplitButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_fromStationSplitButtonActionPerformed
        splitStation(FROM);
    }                                                                                          //GEN-LAST:event_fromStationSplitButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void toStationSplitButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_toStationSplitButtonActionPerformed
        splitStation(TO);
    }                                                                                        //GEN-LAST:event_toStationSplitButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void toBadGeomCorrectButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_toBadGeomCorrectButtonActionPerformed
        badGeomCorrectButtonPressed(TO);
    }                                                                                          //GEN-LAST:event_toBadGeomCorrectButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void toBadGeomButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_toBadGeomButtonActionPerformed
        badGeomButtonPressed(TO);
    }                                                                                   //GEN-LAST:event_toBadGeomButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void fromBadGeomCorrectButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_fromBadGeomCorrectButtonActionPerformed
        badGeomCorrectButtonPressed(FROM);
    }                                                                                            //GEN-LAST:event_fromBadGeomCorrectButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void fromBadGeomButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_fromBadGeomButtonActionPerformed
        badGeomButtonPressed(FROM);
    }                                                                                     //GEN-LAST:event_fromBadGeomButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  evt DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  evt DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  evt DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  evt DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  evt DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  evt DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void splitStation(final boolean isFrom) {
        final double oldPosition = getStationFeature(isFrom).getCurrentPosition();

        final CidsBean stationBean = LinearReferencingHelper.createStationBeanFromRouteBean(LinearReferencingHelper
                        .getRouteBeanFromStationBean(getStationBean(isFrom)));
        setStationBean(stationBean, isFrom);
        stationBeanChanged(isFrom);

        // neue station auf selbe position setzen wie die alte
        final LinearReferencedPointFeature stationFeature = getStationFeature(isFrom);
        stationFeature.moveToPosition(oldPosition);

        getFeature().setPointFeature(getStationFeature(isFrom), isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  routeBean  DOCUMENT ME!
     */
    private void addFromRoute(final CidsBean routeBean) {
        CidsBean cidsBean = getCidsBean();
        if (cidsBean == null) {
            cidsBean = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, getMetaClassName())
                        .getEmptyInstance()
                        .getBean();
        }
        fillFromRoute(routeBean, cidsBean, getLineField());
        setCidsBean(cidsBean);

        // Geometrie für BoundingBox erzeufen
        final BoundingBox boundingBox = MAPPING_COMPONENT.getCurrentBoundingBox();
        final Collection<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY1()));
        coordinates.add(new Coordinate(boundingBox.getX2(), boundingBox.getY1()));
        coordinates.add(new Coordinate(boundingBox.getX2(), boundingBox.getY2()));
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY2()));
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY1()));
        final GeometryFactory gf = new GeometryFactory();
        final LineString boundingBoxGeom = gf.createLineString(coordinates.toArray(new Coordinate[0]));

        final LinearReferencedLineFeature feature = getFeature();

        // ermitteln welche Punkte sich innerhalb der Boundingbox befinden
        final Coordinate fromCoord = feature.getStationFeature(FROM).getGeometry().getCoordinate();
        final Coordinate toCoord = feature.getStationFeature(TO).getGeometry().getCoordinate();
        final boolean testFrom = (fromCoord.x > boundingBox.getX1())
                    && (fromCoord.x < boundingBox.getX2())
                    && (fromCoord.y > boundingBox.getY1())
                    && (fromCoord.y < boundingBox.getY2());
        final boolean testTo = (toCoord.x > boundingBox.getX1())
                    && (toCoord.x < boundingBox.getX2())
                    && (toCoord.y > boundingBox.getY1())
                    && (toCoord.y < boundingBox.getY2());

        // Startwerte festlegen
        final LineString featureGeom = (LineString)feature.getGeometry();
        double minPosition = (testFrom) ? 0 : featureGeom.getLength();
        double maxPosition = (testTo) ? featureGeom.getLength() : 0;

        // Coordinaten durchlaufen und anhand der Position auf der Linie sortieren
        final Geometry intersectionGeom = featureGeom.intersection(boundingBoxGeom);
        for (final Coordinate coord : intersectionGeom.getCoordinates()) {
            final double position = LinearReferencedPointFeature.getPositionOnLine(
                    coord,
                    featureGeom);
            if (position > maxPosition) {
                maxPosition = position;
            }
            if (position < minPosition) {
                minPosition = position;
            }
        }

        // sollte max größer min sein, dann umdrehen
        if (minPosition > maxPosition) {
            final double tmp = minPosition;
            minPosition = maxPosition;
            maxPosition = tmp;
        }

        // ermittelte from und to Position setzen
        changeStationValue(minPosition, FROM);
        changeStationValue(maxPosition, TO);
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> cidsBeans) {
        if (isEnabled()) {
            for (final CidsBean routeBean : cidsBeans) {
                if (routeBean.getMetaObject().getMetaClass().getName().equals(CN_ROUTE)) {
                    if ((getDropBehavior() == null) || getDropBehavior().checkForAdding(routeBean)) {
                        addFromRoute(routeBean);
                        setChangedSinceDrop(false);
                    }
                    return;
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("no route found in dropped objects");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom        DOCUMENT ME!
     * @param  targetIsFrom  DOCUMENT ME!
     */
    private void updateSnappedRealGeoms(final boolean isFrom, final boolean targetIsFrom) {
        final MetaClass mcLine = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, CN_STATIONLINE);

        final int ownLineId = getLineBean().getMetaObject().getId();
        final int stationId = getStationBean(isFrom).getMetaObject().getId();
        final String query = "SELECT "
                    + "   " + mcLine.getId() + ", "
                    + "   " + mcLine.getPrimaryKey() + " "
                    + "FROM "
                    + "   " + mcLine.getTableName() + " "
                    + "WHERE "
                    + "   " + mcLine.getPrimaryKey() + " != " + ownLineId + " AND "
                    + "   " + getStationField(targetIsFrom) + " = " + stationId + " "
                    + ";";
        LOG.fatal("query: " + query);

        try {
            // wk_teile mit gleicher station an einem Ende holen
            final MetaObject[] mos = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            for (final MetaObject mo : mos) {
                // bean des wk_teils
                final CidsBean targetBean = mo.getBean();

                LOG.fatal("target: " + targetBean.getMOString());

                // beans der stationen
                final CidsBean targetFromBean = (CidsBean)targetBean.getProperty(getStationField(FROM));
                final CidsBean targetToBean = (CidsBean)targetBean.getProperty(getStationField(TO));

                // features erzeugen um damit die Geometrie neu zu berechnen
                final LinearReferencedPointFeature targetFromFeature = new LinearReferencedPointFeature(
                        LinearReferencingHelper.getLinearValueFromStationBean(targetFromBean),
                        LinearReferencingHelper.getRouteGeometryFromStationBean(targetFromBean));
                final LinearReferencedPointFeature targetToFeature = new LinearReferencedPointFeature(
                        LinearReferencingHelper.getLinearValueFromStationBean(targetToBean),
                        LinearReferencingHelper.getRouteGeometryFromStationBean(targetToBean));
                final LinearReferencedLineFeature targetFeature = new LinearReferencedLineFeature(
                        targetFromFeature,
                        targetToFeature);

                final LinearReferencedPointFeature targetStationFeature = (targetIsFrom) ? targetFromFeature
                                                                                         : targetToFeature;

                // gesnappte Station auf den selben StationierungsWert wie das Original setzen
                targetStationFeature.moveToPosition(getStationFeature(isFrom).getCurrentPosition());

                // von feature neu berechnete geometrie im wk_teil setzen
                LinearReferencingHelper.setGeometryToLineBean(targetFeature.getGeometry(), targetBean);

                // wk_teil speichern
                targetBean.persist();
            }
        } catch (Exception ex) {
            LOG.fatal("", ex);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void updateSnappedRealGeoms(final boolean isFrom) {
        updateSnappedRealGeoms(isFrom, FROM);
        updateSnappedRealGeoms(isFrom, TO);
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        if (event.getStatus() != EditorSaveStatus.SAVE_SUCCESS) {
            final CidsBean savedBean = event.getSavedBean();
            if (savedBean != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("editor closed: " + event.getSavedBean().getMOString(), new CurrentStackTrace());
                }
                final CidsBean oldCidsBean = getCidsBean();
                setCidsBean(event.getSavedBean());

                updateSnappedRealGeoms(FROM);
                updateSnappedRealGeoms(TO);

                setCidsBean(oldCidsBean);
            }
        }
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }
}
