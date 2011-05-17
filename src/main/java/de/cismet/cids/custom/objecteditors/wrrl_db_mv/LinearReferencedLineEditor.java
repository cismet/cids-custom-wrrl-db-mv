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
import javax.swing.JLabel;
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
import de.cismet.cids.custom.util.MapUtil;
import de.cismet.cids.custom.util.StationToMapRegistryListener;
import de.cismet.cids.custom.util.WrrlEditorTester;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.Crs;
import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.PureNewFeature;
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
    private LinearReferencedPointFeature fromPointFeature;
    private LinearReferencedPointFeature toPointFeature;
    private boolean isFromSpinnerChangeLocked = false;
    private boolean isFromFeatureChangeLocked = false;
    private boolean isFromBeanChangeLocked = false;
    private boolean isToSpinnerChangeLocked = false;
    private boolean isToFeatureChangeLocked = false;
    private boolean isToBeanChangeLocked = false;
    private PropertyChangeListener fromPointBeanListener;
    private PropertyChangeListener toPointBeanListener;
    private LinearReferencedPointFeatureListener fromFeatureListener;
    private LinearReferencedPointFeatureListener toFeatureListener;
    private StationToMapRegistryListener fromPointToMapRegistryListener;
    private StationToMapRegistryListener toPointToMapRegistryListener;
    private LinearReferencedLineEditorDropBehavior dropBehavior;
    private CrsChangeListener crsChangeListener;
    private Feature fromBadGeomFeature;
    private Feature toBadGeomFeature;
    private String metaClassName;
    private String lineField;
    private CidsBean cidsBean;
    private XBoundingBox boundingbox;
    private boolean isAutoZoomActivated = true;
    private boolean inited = false;
    private boolean changedSinceDrop = false;
    private boolean isEditable;

    private Collection<LinearReferencedLineEditorListener> listeners =
        new ArrayList<LinearReferencedLineEditorListener>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnFromBadGeom;
    private javax.swing.JButton btnFromBadGeomCorrect;
    private javax.swing.JButton btnFromPointSplit;
    private javax.swing.JToggleButton btnToBadGeom;
    private javax.swing.JButton btnToBadGeomCorrect;
    private javax.swing.JButton btnToPointSplit;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblFrom;
    private javax.swing.JLabel lblFromIcon;
    private javax.swing.JLabel lblFromValue;
    private javax.swing.JLabel lblGwk;
    private javax.swing.JLabel lblTo;
    private javax.swing.JLabel lblToIcon;
    private javax.swing.JLabel lblToValue;
    private javax.swing.JPanel panAdd;
    private javax.swing.JPanel panEdit;
    private javax.swing.JPanel panError;
    private javax.swing.JPanel panFromBadGeomSpacer;
    private javax.swing.JPanel panGwk;
    private javax.swing.JPanel panLine;
    private javax.swing.JPanel panLinePoints;
    private javax.swing.JPanel panToBadGeomSpacer;
    private javax.swing.JPanel panValue;
    private javax.swing.JSpinner spnFrom;
    private javax.swing.JSpinner spnTo;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new LinearReferencedLineEditor object.
     */
    public LinearReferencedLineEditor() {
        this(true);
    }

    /**
     * Creates new form LinearReferencedLineEditor.
     *
     * @param  isEditable  DOCUMENT ME!
     */
    protected LinearReferencedLineEditor(final boolean isEditable) {
        initComponents();

        setEditable(isEditable);
        if (isEditable) {
            try {
                new CidsBeanDropTarget(this);
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error while creating CidsBeanDropTarget", ex);
                }
            }
            initFeatureListener(FROM);
            initFeatureListener(TO);
            initSpinnerListener(FROM);
            initSpinnerListener(TO);
            initMapRegistryListener(FROM);
            initMapRegistryListener(TO);
        }
        initCidsBeanListener(FROM);
        initCidsBeanListener(TO);

        if (isEditable) {
            initCrsChangeListener();

            CismapBroker.getInstance().addCrsChangeListener(getCrsChangeListener());
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  isEditable  DOCUMENT ME!
     */
    private void setEditable(final boolean isEditable) {
        this.isEditable = isEditable;
        spnFrom.setVisible(isEditable);
        spnTo.setVisible(isEditable);
        lblFromValue.setVisible(!isEditable);
        lblToValue.setVisible(!isEditable);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isEditable() {
        return isEditable;
    }

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
    public boolean addListener(final LinearReferencedLineEditorListener listener) {
        return listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean removeListener(final LinearReferencedLineEditorListener listener) {
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
        if (isEditable) {
            final LinearReferencedLineFeature oldFeature = getFeature();
            if (oldFeature != null) {
                final CidsBean oldBean = MAP_REGISTRY.getCidsBean(oldFeature);
                MAP_REGISTRY.removeLinearReferencedLineFeature(oldBean);
            }
            setFeature(null);
        }

        cleanupPoint(FROM);
        cleanupPoint(TO);

        showCard(Card.add);

        setInited(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        new WrrlEditorTester("station_linie", LinearReferencedLineEditor.class, CidsBeanSupport.DOMAIN_NAME).run();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void cleanupPoint(final boolean isFrom) {
        final CidsBean pointBean = getPointBean(isFrom);

        if (pointBean != null) {
            pointBean.removePropertyChangeListener(getPointBeanListener(isFrom));
        }

        if (isEditable()) {
            final LinearReferencedPointFeature pointFeature = getPointFeature(isFrom);
            if (pointFeature != null) {
                pointFeature.removeListener(getPointFeatureListener(isFrom));
                if (pointBean != null) {
                    MAP_REGISTRY.removeStationFeature(pointBean);
                }
                MAP_REGISTRY.removeListener(pointBean, getMapRegistryListener(isFrom));

                // bean ist null, feature also auch
                setPointFeature(null, isFrom);

                final Feature badGeomFeature = getBadGeomFeature(isFrom);
                if (badGeomFeature != null) {
                    MAPPING_COMPONENT.getFeatureCollection().removeFeature(badGeomFeature);
                    setBadGeomFeature(null, isFrom);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void initLine() {
        // wird das aktuelle crs unterstützt ?
        if (!isCrsSupported(CismapBroker.getInstance().getSrs())) {
            showCrsNotSupported();
            // noch nicht initialisiert ?
        } else if (!isInited()) {
            final CidsBean lineBean = getLineBean();
            if (lineBean != null) {
                initPoint(FROM);
                initPoint(TO);

                if (isEditable()) {
                    // feature erzeugen
                    final LinearReferencedLineFeature lineFeature = MAP_REGISTRY.addLinearReferencedLineFeature(
                            lineBean,
                            getPointFeature(FROM),
                            getPointFeature(TO));

                    // farbe setzen
                    final Color color = (Color)lineFeature.getLinePaint();
                    panLine.setBackground(color);

                    setFeature(lineFeature);
                }
                fireLineAdded();

                pointBeanChanged(FROM);
                pointBeanChanged(TO);

                ((SpinnerNumberModel)getPointSpinner(FROM).getModel()).setMaximum(Math.ceil(
                        getRouteGeometry().getLength()));
                ((SpinnerNumberModel)getPointSpinner(TO).getModel()).setMaximum(Math.ceil(
                        getRouteGeometry().getLength()));
                lblGwk.setText("Route: "
                            + Long.toString(LinearReferencingHelper.getRouteGwkFromStationBean(getPointBean(FROM))));

                showCard(Card.edit);

                setInited(true);
            } else {
                if (isEditable()) {
                    showCard(Card.add);
                } else {
                    setErrorMsg("keine Stationierung zugewiesen.");
                    showCard(Card.error);
                }
            }
        }
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        // aufräumen falls vorher cidsbean schon gesetzt war
        cleanup();

        this.cidsBean = cidsBean;

        // neu initialisieren
        initLine();
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
    private JButton getPointSplitButton(final boolean isFrom) {
        return (isFrom) ? btnFromPointSplit : btnToPointSplit;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getPointField(final boolean isFrom) {
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
        return (isFrom) ? btnFromBadGeomCorrect : btnToBadGeomCorrect;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JToggleButton getBadGeomButton(final boolean isFrom) {
        return (isFrom) ? btnFromBadGeom : btnToBadGeom;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private StationToMapRegistryListener getMapRegistryListener(final boolean isFrom) {
        return (isFrom) ? fromPointToMapRegistryListener : toPointToMapRegistryListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     * @param  isFrom    DOCUMENT ME!
     */
    private void setMapRegistryListener(final StationToMapRegistryListener listener, final boolean isFrom) {
        if (isFrom) {
            fromPointToMapRegistryListener = listener;
        } else {
            toPointToMapRegistryListener = listener;
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
    private PropertyChangeListener getPointBeanListener(final boolean isFrom) {
        return (isFrom) ? fromPointBeanListener : toPointBeanListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  listener  DOCUMENT ME!
     * @param  isFrom    DOCUMENT ME!
     */
    private void setPointBeanListener(final PropertyChangeListener listener, final boolean isFrom) {
        if (isFrom) {
            fromPointBeanListener = listener;
        } else {
            toPointBeanListener = listener;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private LinearReferencedPointFeature getPointFeature(final boolean isFrom) {
        return (isFrom) ? fromPointFeature : toPointFeature;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pointFeature  DOCUMENT ME!
     * @param  isFrom        DOCUMENT ME!
     */
    private void setPointFeature(final LinearReferencedPointFeature pointFeature,
            final boolean isFrom) {
        if (isFrom) {
            fromPointFeature = pointFeature;
        } else {
            toPointFeature = pointFeature;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private LinearReferencedPointFeatureListener getPointFeatureListener(final boolean isFrom) {
        return (isFrom) ? fromFeatureListener : toFeatureListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  featureListener  DOCUMENT ME!
     * @param  isFrom           DOCUMENT ME!
     */
    private void setPointFeatureListener(final LinearReferencedPointFeatureListener featureListener,
            final boolean isFrom) {
        if (isFrom) {
            fromFeatureListener = featureListener;
        } else {
            toFeatureListener = featureListener;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JSpinner getPointSpinner(final boolean isFrom) {
        return (isFrom) ? spnFrom : spnTo;
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
    private void initPoint(final boolean isFrom) {
        final CidsBean pointBean = getPointBean(isFrom);
        if (pointBean != null) {
            pointBean.addPropertyChangeListener(getPointBeanListener(isFrom));

            if (isEditable()) {
                final double distance = LinearReferencingHelper.distanceOfStationGeomToRouteGeomFromStationBean(
                        pointBean);

                if (distance > 1) {
                    setBadGeomFeature(StationEditor.createBadGeomFeature(
                            LinearReferencingHelper.getPointGeometryFromStationBean(pointBean)),
                        isFrom);
                } else {
                    setBadGeomFeature(null, isFrom);
                }

                MAP_REGISTRY.addListener(pointBean, getMapRegistryListener(isFrom));

                // feature erzeugen
                final LinearReferencedPointFeature pointFeature = MAP_REGISTRY.addStationFeature(
                        pointBean);

                // feature listener
                pointFeature.addListener(getPointFeatureListener(isFrom));

                // feature setzen
                setPointFeature(pointFeature, isFrom);
            }
        }
        updateBadGeomButton(isFrom);
        updateSplitButton(isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pointBean  DOCUMENT ME!
     * @param  isFrom     DOCUMENT ME!
     */
    private void setPointBean(final CidsBean pointBean, final boolean isFrom) {
        cleanupPoint(isFrom);

        // bean setzen
        if (getCidsBean() != null) {
            try {
                getLineBean().setProperty(getPointField(isFrom), pointBean);
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error while setting cidsbean for point", ex);
                }
            }
        }

        initPoint(isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void switchBadGeomVisibility(final boolean isFrom) {
        if (isEditable()) {
            final Feature badGeomFeature = getBadGeomFeature(isFrom);
            final Feature pointFeature = getPointFeature(isFrom);

            final boolean selected = getBadGeomButton(isFrom).isSelected();

            if (selected) {
                boundingbox = (XBoundingBox)MAPPING_COMPONENT.getCurrentBoundingBox();

                MAPPING_COMPONENT.getFeatureCollection().addFeature(badGeomFeature);
                MAPPING_COMPONENT.getFeatureCollection().select(pointFeature);

                zoomToBadFeature(isFrom);
            } else {
                MAPPING_COMPONENT.getFeatureCollection().removeFeature(badGeomFeature);
                MAPPING_COMPONENT.gotoBoundingBoxWithoutHistory(boundingbox);
            }

            getBadGeomCorrectButton(isFrom).setVisible(selected);
        }
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
     * @return  DOCUMENT ME!
     */
    public Collection<Feature> getZoomFeatures() {
        final Collection<Feature> zoomFeatures = new ArrayList<Feature>();
        addZoomFeaturesToCollection(zoomFeatures);
        return zoomFeatures;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  collection  DOCUMENT ME!
     */
    public void addZoomFeaturesToCollection(final Collection<Feature> collection) {
        final Feature fromPointFeature = getPointFeature(FROM);
        final Feature toPointFeature = getPointFeature(TO);
        if ((fromPointFeature != null) && (toPointFeature != null)) {
            final Feature boundedFromFeature = new PureNewFeature(fromPointFeature.getGeometry().buffer(500));
            final Feature boundedToFeature = new PureNewFeature(toPointFeature.getGeometry().buffer(500));
            collection.add(boundedFromFeature);
            collection.add(boundedToFeature);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void correctBadGeom(final boolean isFrom) {
        if (isEditable()) {
            final LinearReferencedPointFeature feature = getPointFeature(isFrom);
            final Feature badFeature = getBadGeomFeature(isFrom);
            feature.moveTo(badFeature.getGeometry().getCoordinate());
            zoomToBadFeature(isFrom);
        }
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
        try {
            final CidsBean linieBean = LinearReferencingHelper.createLineBeanFromRouteBean(routeBean);
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
    private void initMapRegistryListener(final boolean isFrom) {
        final StationToMapRegistryListener mapRegistryListener = new StationToMapRegistryListener() {

                @Override
                public void FeatureCountChanged() {
                    updateSplitButton(isFrom);
                }
            };

        setMapRegistryListener(mapRegistryListener, isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initSpinnerListener(final boolean isFrom) {
        final JSpinner spinner = getPointSpinner(isFrom);
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
                    MAPPING_COMPONENT.getFeatureCollection().select(getPointFeature(isFrom));
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
                    final CidsBean withBean = MAP_REGISTRY.getCidsBean(withPoint);

                    final LinearReferencedPointFeature fromFeature = getPointFeature(FROM);
                    final LinearReferencedPointFeature toFeature = getPointFeature(TO);
                    if ((fromFeature.equals(mergePoint) && !toFeature.equals(withPoint))
                                || (toFeature.equals(mergePoint) && !fromFeature.equals(withPoint))) {
                        final boolean isFrom = fromFeature.equals(mergePoint);

                        // neue bean übernehmen
                        setPointBean(withBean, isFrom);
                        pointBeanChanged(isFrom);

                        updateSplitButton(isFrom);
                    }
                }
            };

        setPointFeatureListener(featureListener, isFrom);
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
                        initLine();
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
                final LinearReferencedPointFeature linearRefFeature = getPointFeature(isFrom);

                final double value = Math.round(linearRefFeature.getCurrentPosition() * 100) / 100d;
                setPointValue(value, isFrom);
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

            final AbstractFormatter formatter = ((JSpinner.DefaultEditor)getPointSpinner(isFrom).getEditor())
                        .getTextField().getFormatter();
            final String text = ((JSpinner.DefaultEditor)getPointSpinner(isFrom).getEditor()).getTextField().getText();
            if (!text.isEmpty()) {
                try {
                    final double value = (Double)formatter.stringToValue(text);
                    setPointValue(value, isFrom);
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
    private void pointBeanChanged(final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("cidsbean changed", new CurrentStackTrace());
        }

        try {
            lockBeanChange(true, isFrom);

            setChangedSinceDrop(true);

            final CidsBean pointBean = getPointBean(isFrom);

            if (pointBean != null) {
                final double value = (Double)pointBean.getProperty(PROP_STATION_VALUE);

                setPointValueToSpinner(value, isFrom);
                setPointValueToLabel(value, isFrom);
                setPointValueToFeature(value, isFrom);

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
     */
    public void updateRealGeoms() {
        updateRealGeoms(FROM);
        updateRealGeoms(TO);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JLabel getValueLabel(final boolean isFrom) {
        return (isFrom) ? lblFromValue : lblToValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void setPointValueToLabel(final double value, final boolean isFrom) {
        final JLabel valueLabel = getValueLabel(isFrom);
        valueLabel.setText(Long.toString(Math.round(value)));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public double getPointValue(final boolean isFrom) {
        final CidsBean pointBean = getPointBean(isFrom);
        if (pointBean != null) {
            return LinearReferencingHelper.getLinearValueFromStationBean(pointBean);
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
        final JButton pointSplitButton = getPointSplitButton(isFrom);
        if (isEditable()) {
            if (getCidsBean() != null) {
                final CidsBean pointBean = getPointBean(isFrom);
                if (pointBean != null) {
                    final boolean visible = MAP_REGISTRY.getCounter(pointBean) > 1;
                    pointSplitButton.setVisible(visible);
                    return;
                }
            }
        }
        pointSplitButton.setVisible(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void updateBadGeomButton(final boolean isFrom) {
        final boolean visible = isEditable() && (getBadGeomFeature(isFrom) != null);
        getBadGeomButton(isFrom).setVisible(visible);
        getBadGeomCorrectButton(isFrom).setVisible(visible && getBadGeomButton(isFrom).isSelected());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void setPointValueToSpinner(final double value, final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("change spinner");
        }

        if (!isSpinnerChangeLocked(isFrom)) {
            final JSpinner pointSpinner = getPointSpinner(isFrom);
            pointSpinner.setValue(Math.round(value));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value   DOCUMENT ME!
     * @param  isFrom  DOCUMENT ME!
     */
    private void setPointValueToFeature(final double value, final boolean isFrom) {
        if (!isFeatureChangeLocked(isFrom)) {
            final LinearReferencedPointFeature pointFeature = getPointFeature(isFrom);
            if (pointFeature != null) {
                pointFeature.setInfoFormat(new DecimalFormat("###"));
                pointFeature.moveToPosition(value);
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
            final LinearReferencedPointFeature pointFeature = getPointFeature(isFrom);
            final CidsBean pointBean = getPointBean(isFrom);

            // realgeom der Station anpassen
            if (pointFeature != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("change station geom");
                }

                final Geometry pointGeom = LinearReferencedPointFeature.getPointOnLine(LinearReferencingHelper
                                .getLinearValueFromStationBean(pointBean),
                        LinearReferencingHelper.getRouteGeometryFromStationBean(pointBean));
                pointGeom.setSRID(CismapBroker.getInstance().getDefaultCrsAlias());
                LinearReferencingHelper.setPointGeometryToStationBean(pointGeom, pointBean);
            }
            // realgeom der Linie anpassen
            if (getFeature() != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("change line geom");
                }
                final Geometry lineGeom = LinearReferencedLineFeature.createSubline(getPointValue(FROM),
                        getPointValue(TO),
                        LinearReferencingHelper.getRouteGeometryFromStationBean(getPointBean(isFrom)));
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
    private void setPointValue(final double value, final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("change bean value to " + value);
        }
        final CidsBean pointBean = getPointBean(isFrom);
        final double oldValue = LinearReferencingHelper.getLinearValueFromStationBean(pointBean);

        if (oldValue != value) {
            try {
                if (!isFeatureChangeLocked(isFrom)) {
                    MAPPING_COMPONENT.getFeatureCollection().select(getPointFeature(isFrom));
                }
                if (!isBeanChangeLocked(isFrom)) {
                    LinearReferencingHelper.setLinearValueToStationBean((double)Math.round(value), pointBean);
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
        final PropertyChangeListener pointBeanListener = new PropertyChangeListener() {

                @Override
                public void propertyChange(final PropertyChangeEvent pce) {
                    if (pce.getPropertyName().equals(PROP_STATION_VALUE)) {
                        pointBeanChanged(isFrom);
                    }
                }
            };

        setPointBeanListener(pointBeanListener, isFrom);
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
    private CidsBean getPointBean(final boolean isFrom) {
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
        final CidsBean pointBean = getPointBean(FROM);
        if (pointBean == null) {
            return null;
        }
        return (CidsBean)pointBean.getProperty(PROP_STATION_ROUTE);
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
        getPointSpinner(FROM).setEnabled(bln);
        getPointSpinner(TO).setEnabled(bln);
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
        panGwk = new javax.swing.JPanel();
        panValue = new javax.swing.JPanel();
        spnFrom = new javax.swing.JSpinner();
        spnTo = new javax.swing.JSpinner();
        lblToValue = new javax.swing.JLabel();
        lblFromValue = new javax.swing.JLabel();
        panFromBadGeomSpacer = new javax.swing.JPanel();
        btnFromPointSplit = new javax.swing.JButton();
        btnFromBadGeom = new javax.swing.JToggleButton();
        btnFromBadGeomCorrect = new javax.swing.JButton();
        panToBadGeomSpacer = new javax.swing.JPanel();
        btnToBadGeomCorrect = new javax.swing.JButton();
        btnToBadGeom = new javax.swing.JToggleButton();
        btnToPointSplit = new javax.swing.JButton();
        lblGwk = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panLinePoints = new javax.swing.JPanel();
        lblFromIcon = new javax.swing.JLabel();
        lblToIcon = new javax.swing.JLabel();
        panLine = new javax.swing.JPanel();
        lblFrom = new javax.swing.JLabel();
        lblTo = new javax.swing.JLabel();
        panAdd = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panError = new javax.swing.JPanel();
        lblError = new javax.swing.JLabel();

        setOpaque(false);
        setLayout(new java.awt.CardLayout());

        panEdit.setOpaque(false);
        panEdit.setLayout(new java.awt.GridBagLayout());

        panGwk.setOpaque(false);
        panGwk.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        panEdit.add(panGwk, gridBagConstraints);

        panValue.setOpaque(false);
        panValue.setLayout(new java.awt.GridBagLayout());

        spnFrom.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 0.0d, 1.0d));
        spnFrom.setEditor(new javax.swing.JSpinner.NumberEditor(spnFrom, "###"));
        spnFrom.setMinimumSize(new java.awt.Dimension(100, 28));
        spnFrom.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panValue.add(spnFrom, gridBagConstraints);

        spnTo.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 0.0d, 1.0d));
        spnTo.setEditor(new javax.swing.JSpinner.NumberEditor(spnTo, "###"));
        spnTo.setMinimumSize(new java.awt.Dimension(100, 28));
        spnTo.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        panValue.add(spnTo, gridBagConstraints);

        lblToValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblToValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblToValue.setMaximumSize(new java.awt.Dimension(100, 28));
        lblToValue.setMinimumSize(new java.awt.Dimension(100, 28));
        lblToValue.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        panValue.add(lblToValue, gridBagConstraints);

        lblFromValue.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblFromValue.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblFromValue.setMaximumSize(new java.awt.Dimension(100, 28));
        lblFromValue.setMinimumSize(new java.awt.Dimension(100, 28));
        lblFromValue.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        panValue.add(lblFromValue, gridBagConstraints);

        panFromBadGeomSpacer.setMaximumSize(new java.awt.Dimension(86, 30));
        panFromBadGeomSpacer.setMinimumSize(new java.awt.Dimension(86, 30));
        panFromBadGeomSpacer.setOpaque(false);
        panFromBadGeomSpacer.setPreferredSize(new java.awt.Dimension(86, 30));
        panFromBadGeomSpacer.setLayout(new java.awt.GridBagLayout());

        btnFromPointSplit.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/sql-join-left.png"))); // NOI18N
        btnFromPointSplit.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromPointSplit.text"));                                         // NOI18N
        btnFromPointSplit.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromPointSplit.toolTipText"));                                  // NOI18N
        btnFromPointSplit.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnFromPointSplitActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panFromBadGeomSpacer.add(btnFromPointSplit, gridBagConstraints);

        btnFromBadGeom.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/exclamation.png"))); // NOI18N
        btnFromBadGeom.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromBadGeom.text"));                                          // NOI18N
        btnFromBadGeom.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromBadGeom.toolTipText"));                                   // NOI18N
        btnFromBadGeom.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnFromBadGeomActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        panFromBadGeomSpacer.add(btnFromBadGeom, gridBagConstraints);

        btnFromBadGeomCorrect.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/node-delete.png"))); // NOI18N
        btnFromBadGeomCorrect.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromBadGeomCorrect.text_1"));                                 // NOI18N
        btnFromBadGeomCorrect.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnFromBadGeomCorrect.toolTipText"));                            // NOI18N
        btnFromBadGeomCorrect.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnFromBadGeomCorrectActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        panFromBadGeomSpacer.add(btnFromBadGeomCorrect, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panValue.add(panFromBadGeomSpacer, gridBagConstraints);

        panToBadGeomSpacer.setMaximumSize(new java.awt.Dimension(86, 30));
        panToBadGeomSpacer.setMinimumSize(new java.awt.Dimension(86, 30));
        panToBadGeomSpacer.setOpaque(false);
        panToBadGeomSpacer.setPreferredSize(new java.awt.Dimension(86, 30));
        panToBadGeomSpacer.setLayout(new java.awt.GridBagLayout());

        btnToBadGeomCorrect.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/node-delete-child.png"))); // NOI18N
        btnToBadGeomCorrect.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToBadGeomCorrect.text_1"));                                         // NOI18N
        btnToBadGeomCorrect.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToBadGeomCorrect.toolTipText"));                                    // NOI18N
        btnToBadGeomCorrect.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnToBadGeomCorrectActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panToBadGeomSpacer.add(btnToBadGeomCorrect, gridBagConstraints);

        btnToBadGeom.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/exclamation.png"))); // NOI18N
        btnToBadGeom.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToBadGeom.text"));                                            // NOI18N
        btnToBadGeom.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToBadGeom.toolTipText"));                                     // NOI18N
        btnToBadGeom.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnToBadGeomActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panToBadGeomSpacer.add(btnToBadGeom, gridBagConstraints);

        btnToPointSplit.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/sql-join-right.png"))); // NOI18N
        btnToPointSplit.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToPointSplit.text"));                                            // NOI18N
        btnToPointSplit.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.btnToPointSplit.toolTipText"));                                     // NOI18N
        btnToPointSplit.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnToPointSplitActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        panToBadGeomSpacer.add(btnToPointSplit, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        panValue.add(panToBadGeomSpacer, gridBagConstraints);

        lblGwk.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGwk.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblGwk.text_1")); // NOI18N
        lblGwk.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panValue.add(lblGwk, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        panValue.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panEdit.add(panValue, gridBagConstraints);

        panLinePoints.setOpaque(false);
        panLinePoints.setLayout(new java.awt.GridBagLayout());

        lblFromIcon.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        lblFromIcon.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblFromIcon.text_1"));                                       // NOI18N
        lblFromIcon.setMaximumSize(new java.awt.Dimension(13, 28));
        lblFromIcon.setMinimumSize(new java.awt.Dimension(13, 28));
        lblFromIcon.setPreferredSize(new java.awt.Dimension(13, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        panLinePoints.add(lblFromIcon, gridBagConstraints);

        lblToIcon.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        lblToIcon.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblToIcon.text_1"));                                         // NOI18N
        lblToIcon.setMaximumSize(new java.awt.Dimension(13, 28));
        lblToIcon.setMinimumSize(new java.awt.Dimension(13, 28));
        lblToIcon.setPreferredSize(new java.awt.Dimension(13, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panLinePoints.add(lblToIcon, gridBagConstraints);

        panLine.setBackground(new java.awt.Color(255, 91, 0));
        panLine.setMinimumSize(new java.awt.Dimension(10, 4));
        panLine.setPreferredSize(new java.awt.Dimension(100, 4));
        panLine.setLayout(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        panLinePoints.add(panLine, gridBagConstraints);

        lblFrom.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFrom.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblFrom.text_1")); // NOI18N
        lblFrom.setMaximumSize(new java.awt.Dimension(28, 28));
        lblFrom.setMinimumSize(new java.awt.Dimension(28, 28));
        lblFrom.setPreferredSize(new java.awt.Dimension(28, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 5);
        panLinePoints.add(lblFrom, gridBagConstraints);

        lblTo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTo.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.lblTo.text_1")); // NOI18N
        lblTo.setMaximumSize(new java.awt.Dimension(28, 28));
        lblTo.setMinimumSize(new java.awt.Dimension(28, 28));
        lblTo.setPreferredSize(new java.awt.Dimension(28, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 3);
        panLinePoints.add(lblTo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 15);
        panEdit.add(panLinePoints, gridBagConstraints);

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
    private void btnFromPointSplitActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnFromPointSplitActionPerformed
        splitPoint(FROM);
    }                                                                                     //GEN-LAST:event_btnFromPointSplitActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnToPointSplitActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnToPointSplitActionPerformed
        splitPoint(TO);
    }                                                                                   //GEN-LAST:event_btnToPointSplitActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnToBadGeomCorrectActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnToBadGeomCorrectActionPerformed
        correctBadGeom(TO);
    }                                                                                       //GEN-LAST:event_btnToBadGeomCorrectActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnToBadGeomActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnToBadGeomActionPerformed
        switchBadGeomVisibility(TO);
    }                                                                                //GEN-LAST:event_btnToBadGeomActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnFromBadGeomCorrectActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnFromBadGeomCorrectActionPerformed
        correctBadGeom(FROM);
    }                                                                                         //GEN-LAST:event_btnFromBadGeomCorrectActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnFromBadGeomActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnFromBadGeomActionPerformed
        switchBadGeomVisibility(FROM);
    }                                                                                  //GEN-LAST:event_btnFromBadGeomActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void splitPoint(final boolean isFrom) {
        if (isEditable()) {
            final double oldPosition = getPointFeature(isFrom).getCurrentPosition();

            final CidsBean pointBean = LinearReferencingHelper.createStationBeanFromRouteBean(LinearReferencingHelper
                            .getRouteBeanFromStationBean(getPointBean(isFrom)));
            setPointBean(pointBean, isFrom);
            pointBeanChanged(isFrom);

            // neue station auf selbe position setzen wie die alte
            final LinearReferencedPointFeature pointFeature = getPointFeature(isFrom);
            pointFeature.moveToPosition(oldPosition);

            getFeature().setPointFeature(getPointFeature(isFrom), isFrom);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  routeBean  DOCUMENT ME!
     */
    private void addFromRoute(final CidsBean routeBean) {
        CidsBean lineBean = getCidsBean();
        if (lineBean == null) {
            lineBean = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, getMetaClassName())
                        .getEmptyInstance()
                        .getBean();
        }
        fillFromRoute(routeBean, lineBean, getLineField());
        setCidsBean(lineBean);

        // Geometrie für BoundingBox erzeufen
        final XBoundingBox boundingBox = (XBoundingBox)MAPPING_COMPONENT.getCurrentBoundingBox();
        final Collection<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY1()));
        coordinates.add(new Coordinate(boundingBox.getX2(), boundingBox.getY1()));
        coordinates.add(new Coordinate(boundingBox.getX2(), boundingBox.getY2()));
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY2()));
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY1()));
        final GeometryFactory gf = new GeometryFactory();
        final LineString boundingBoxGeom = gf.createLineString(coordinates.toArray(new Coordinate[0]));

        final LinearReferencedLineFeature lineFeature = getFeature();

        // ermitteln welche Punkte sich innerhalb der Boundingbox befinden
        final Coordinate fromCoord = lineFeature.getPointFeature(FROM).getGeometry().getCoordinate();
        final Coordinate toCoord = lineFeature.getPointFeature(TO).getGeometry().getCoordinate();
        final boolean testFrom = (fromCoord.x > boundingBox.getX1())
                    && (fromCoord.x < boundingBox.getX2())
                    && (fromCoord.y > boundingBox.getY1())
                    && (fromCoord.y < boundingBox.getY2());
        final boolean testTo = (toCoord.x > boundingBox.getX1())
                    && (toCoord.x < boundingBox.getX2())
                    && (toCoord.y > boundingBox.getY1())
                    && (toCoord.y < boundingBox.getY2());

        // Startwerte festlegen
        final LineString featureGeom = (LineString)lineFeature.getGeometry();
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
        setPointValue(minPosition, FROM);
        setPointValue(maxPosition, TO);
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> cidsBeans) {
        if (isEnabled() && isEditable()) {
            for (final CidsBean routeBean : cidsBeans) {
                if (routeBean.getMetaObject().getMetaClass().getName().equals(CN_ROUTE)) {
                    if ((getDropBehavior() == null) || getDropBehavior().checkForAdding(routeBean)) {
                        addFromRoute(routeBean);
                        setChangedSinceDrop(false);
                    }
                    if (isAutoZoomActivated) {
                        MapUtil.zoomToFeatureCollection(getZoomFeatures());
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
        final int pointId = getPointBean(isFrom).getMetaObject().getId();
        final String query = "SELECT "
                    + "   " + mcLine.getId() + ", "
                    + "   " + mcLine.getPrimaryKey() + " "
                    + "FROM "
                    + "   " + mcLine.getTableName() + " "
                    + "WHERE "
                    + "   " + mcLine.getPrimaryKey() + " != " + ownLineId + " AND "
                    + "   " + getPointField(targetIsFrom) + " = " + pointId + " "
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
                final CidsBean targetFromBean = (CidsBean)targetBean.getProperty(getPointField(FROM));
                final CidsBean targetToBean = (CidsBean)targetBean.getProperty(getPointField(TO));

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

                final LinearReferencedPointFeature targetPointFeature = (targetIsFrom) ? targetFromFeature
                                                                                       : targetToFeature;

                // gesnappte Station auf den selben StationierungsWert wie das Original setzen
                targetPointFeature.moveToPosition(getPointFeature(isFrom).getCurrentPosition());

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

                if (isEditable()) {
                    updateSnappedRealGeoms(FROM);
                    updateSnappedRealGeoms(TO);
                }

                setCidsBean(oldCidsBean);
            }
        }
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }
}
