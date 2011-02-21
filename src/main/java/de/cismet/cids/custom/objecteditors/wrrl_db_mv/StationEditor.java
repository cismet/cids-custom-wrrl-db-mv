/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.log4j.Logger;

import java.awt.CardLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
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
import de.cismet.cids.custom.util.StationToMapRegistry;
import de.cismet.cids.custom.util.StationToMapRegistryListener;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.features.DefaultStyledFeature;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.FeatureAnnotationSymbol;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeatureListener;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class StationEditor extends JPanel implements DisposableCidsBeanStore, LinearReferencingConstants {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(StationEditor.class);
    private static final StationToMapRegistry STATION_TO_MAP_REGISTRY = StationToMapRegistry.getInstance();
    private static final MappingComponent MAPPING_COMPONENT = CismapBroker.getInstance().getMappingComponent();

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

    private boolean inited;

    // private LinearReferencedPointFeature feature;
    private PropertyChangeListener cidsBeanListener;
    private boolean isSpinnerChangeLocked = false;
    private boolean isFeatureChangeLocked = false;
    private boolean isBeanChangeLocked = false;
    private ImageIcon ico;
    private CidsBean cidsBean;
    private Collection<StationEditorListener> listeners = new ArrayList<StationEditorListener>();
    private LinearReferencedPointFeatureListener featureListener;
    private StationToMapRegistryListener stationToMapRegistryListener;
    private LinearReferencedLineEditorDropBehavior dropBehavior;
    private Feature badGeomFeature;
    private BoundingBox boundingbox;

    private boolean changedSinceDrop = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton badGeomButton;
    private javax.swing.JButton badGeomCorrectButton;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labGwk;
    private javax.swing.JPanel panAdd;
    private javax.swing.JPanel panEdit;
    private javax.swing.JPanel panError;
    private javax.swing.JSpinner spinValue;
    private javax.swing.JButton splitButton;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new StationEditor object.
     */
    public StationEditor() {
        initComponents();

        try {
            new CidsBeanDropTarget(panAdd);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while creating CidsBeanDropTarget");
            }
        }

        initCidsBeanListener();
        initSpinnerListener();
        initFeatureListener();
        initStationToMapRegistryListener();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean addStationEditorListener(final StationEditorListener listener) {
        return listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean removeStationEditorListener(final StationEditorListener listener) {
        return listeners.remove(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  ico  DOCUMENT ME!
     */
    public void setImageIcon(final ImageIcon ico) {
        this.ico = ico;
        if (getFeature() != null) {
            getFeature().setIconImage(ico);
        }
        jLabel5.setIcon(ico);
    }

    /**
     * DOCUMENT ME!
     */
    private void fireStationCreated() {
        for (final StationEditorListener listener : listeners) {
            listener.stationCreated();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LinearReferencedPointFeature getFeature() {
        return (LinearReferencedPointFeature)STATION_TO_MAP_REGISTRY.getFeature(getCidsBean());
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
     * @return  DOCUMENT ME!
     */
    private JButton getBadGeomCorrectButton() {
        return badGeomCorrectButton;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Feature getBadGeomFeature() {
        return badGeomFeature;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  badGeomFeature  DOCUMENT ME!
     */
    private void setBadGeomFeature(final Feature badGeomFeature) {
        this.badGeomFeature = badGeomFeature;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JToggleButton getBadGeomButton() {
        return badGeomButton;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JSpinner getValueSpinner() {
        return spinValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private StationToMapRegistryListener getStationToMapRegistryListener() {
        return stationToMapRegistryListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stationToMapRegistryListener  DOCUMENT ME!
     */
    private void setStationToMapRegistryListener(final StationToMapRegistryListener stationToMapRegistryListener) {
        this.stationToMapRegistryListener = stationToMapRegistryListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private PropertyChangeListener getCidsBeanListener() {
        return cidsBeanListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBeanListener  DOCUMENT ME!
     */
    private void setCidsBeanListener(final PropertyChangeListener cidsBeanListener) {
        this.cidsBeanListener = cidsBeanListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private LinearReferencedPointFeatureListener getFeatureListener() {
        return featureListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  featureListener  DOCUMENT ME!
     */
    private void setFeatureListener(final LinearReferencedPointFeatureListener featureListener) {
        this.featureListener = featureListener;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private JButton getSplitButton() {
        return splitButton;
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
     */
    private void initStationToMapRegistryListener() {
        setStationToMapRegistryListener(new StationToMapRegistryListener() {

                @Override
                public void FeatureCountChanged() {
                    updateSplitButton();
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    private void initCidsBeanListener() {
        setCidsBeanListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(final PropertyChangeEvent pce) {
                    if (pce.getPropertyName().equals(PROP_STATION_VALUE)) {
                        cidsBeanChanged((Double)pce.getNewValue());
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    private void initFeatureListener() {
        setFeatureListener(new LinearReferencedPointFeatureListener() {

                @Override
                public void featureMoved(final LinearReferencedPointFeature pointFeature) {
                    featureChanged(pointFeature.getCurrentPosition());
                }

                @Override
                public void featureMerged(final LinearReferencedPointFeature mergePoint,
                        final LinearReferencedPointFeature withPoint) {
                    final CidsBean withBean = STATION_TO_MAP_REGISTRY.getCidsBean(withPoint);
                    setCidsBean(withBean);

                    updateSplitButton();
                }
            });
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
     */
    private void initSpinnerListener() {
        ((JSpinner.DefaultEditor)getValueSpinner().getEditor()).getTextField()
                .getDocument()
                .addDocumentListener(new DocumentListener() {

                        @Override
                        public void insertUpdate(final DocumentEvent de) {
                            spinnerChanged();
                        }

                        @Override
                        public void removeUpdate(final DocumentEvent de) {
                            spinnerChanged();
                        }

                        @Override
                        public void changedUpdate(final DocumentEvent de) {
                            spinnerChanged();
                        }
                    });
        ((JSpinner.DefaultEditor)getValueSpinner().getEditor()).getTextField().addFocusListener(new FocusAdapter() {

                @Override
                public void focusGained(final FocusEvent fe) {
                    MAPPING_COMPONENT.getFeatureCollection().select(getFeature());
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    private void spinnerChanged() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("spinner changed", new CurrentStackTrace());
        }

        try {
            lockSpinnerChange(true);

            try {
                final JFormattedTextField textField = ((JSpinner.DefaultEditor)getValueSpinner().getEditor())
                            .getTextField();
                final AbstractFormatter formatter = textField.getFormatter();
                final Double value = (Double)formatter.stringToValue(textField.getText());
                changeValue(value);
            } catch (ParseException ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error parsing spinner", ex);
                }
            }
        } finally {
            lockSpinnerChange(false);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value  DOCUMENT ME!
     */
    private void featureChanged(final double value) {
        try {
            lockFeatureChange(true);
            changeValue(value);
        } finally {
            lockFeatureChange(false);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value  DOCUMENT ME!
     */
    private void cidsBeanChanged(final double value) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("cidsbean changed", new CurrentStackTrace());
        }

        try {
            lockBeanChange(true);

            setChangedSinceDrop(true);

            if (!isSpinnerChangeLocked()) {
                getValueSpinner().setValue(value);
            }

            if (getFeature() != null) {
                if (!isFeatureChangeLocked()) {
                    getFeature().moveToPosition(value);
                }

                // realgeoms nur nach manueller eingabe updaten
                if (isInited()) {
                    updateRealGeom();
                }
            }
        } finally {
            lockBeanChange(false);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void updateRealGeom() {
        try {
            StationEditor.setPointGeometry(getFeature().getGeometry(), getCidsBean());
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while setting the " + PROP_STATION_GEOM + "property", ex);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value  DOCUMENT ME!
     */
    private void changeValue(final double value) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("change bean value to " + value);
        }
        final CidsBean stationBean = getCidsBean();
        final double oldValue = StationEditor.getLinearValue(stationBean);

        if (oldValue != value) {
            try {
                if (!isFeatureChangeLocked()) {
                    MAPPING_COMPONENT.getFeatureCollection().select(getFeature());
                }
                if (!isBeanChangeLocked()) {
                    StationEditor.setLinearValue(value, stationBean);
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
     * @return  DOCUMENT ME!
     */
    private boolean isSpinnerChangeLocked() {
        return isSpinnerChangeLocked;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isFeatureChangeLocked() {
        return isFeatureChangeLocked;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isBeanChangeLocked() {
        return isBeanChangeLocked;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lock  DOCUMENT ME!
     */
    private void lockSpinnerChange(final boolean lock) {
        isSpinnerChangeLocked = lock;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lock  DOCUMENT ME!
     */
    private void lockFeatureChange(final boolean lock) {
        isFeatureChangeLocked = lock;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lock  DOCUMENT ME!
     */
    private void lockBeanChange(final boolean lock) {
        isBeanChangeLocked = lock;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   geom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Feature createBadGeomFeature(final Geometry geom) {
        final DefaultStyledFeature dsf = new DefaultStyledFeature();
        dsf.setGeometry(geom);
        dsf.setCanBeSelected(false);
        dsf.setPointAnnotationSymbol(FeatureAnnotationSymbol.newCenteredFeatureAnnotationSymbol(
                new javax.swing.ImageIcon(
                    StationEditor.class.getResource(
                        "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/exclamation-octagon.png")).getImage(),
                null));
        return dsf;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static double distanceToOwnLine(final CidsBean cidsBean) {
        final Geometry routeGeometry = StationEditor.getRouteGeometry(cidsBean);
        final Geometry pointGeometry = StationEditor.getPointGeometry(cidsBean);
        if (pointGeometry != null) {
            final double distance = pointGeometry.distance(routeGeometry);
            return distance;
        }
        return 0d;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        final CidsBean oldBean = getCidsBean();
        if (oldBean != null) {
            final LinearReferencedPointFeature oldFeature = STATION_TO_MAP_REGISTRY.removeStationFeature(oldBean);
            if (oldFeature != null) {
                oldFeature.removeListener(getFeatureListener());
            }

            // listener von der alten Bean entfernen
            oldBean.removePropertyChangeListener(getCidsBeanListener());
            STATION_TO_MAP_REGISTRY.removeListener(oldBean, getStationToMapRegistryListener());
        }

        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            final double distance = StationEditor.distanceToOwnLine(cidsBean);

            if (distance > 1) {
                setBadGeomFeature(createBadGeomFeature(StationEditor.getPointGeometry(cidsBean)));
            } else {
                setBadGeomFeature(null);
            }
            updateBadGeomButton();

            STATION_TO_MAP_REGISTRY.addListener(cidsBean, getStationToMapRegistryListener());
            cidsBean.addPropertyChangeListener(getCidsBeanListener());

            final LinearReferencedPointFeature feature = STATION_TO_MAP_REGISTRY.addStationFeature(cidsBean);
            feature.addListener(getFeatureListener());

            if (ico != null) {
                feature.setIconImage(ico);
            }

            cidsBeanChanged(getLinearValue(cidsBean));
            fireStationCreated();

            ((SpinnerNumberModel)getValueSpinner().getModel()).setMaximum(feature.getLineGeometry().getLength());
            labGwk.setText("Route: " + Long.toString(getRouteGwk(cidsBean)));

            showCard(Card.edit);

            setInited(true);
        } else {
            if (getBadGeomFeature() != null) {
                MAPPING_COMPONENT.getFeatureCollection().removeFeature(getBadGeomFeature());
                setBadGeomFeature(null);
            }
            showCard(Card.add);

            setInited(false);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void updateBadGeomButton() {
        final boolean visible = getBadGeomFeature() != null;
        getBadGeomButton().setVisible(visible);
        getBadGeomCorrectButton().setVisible(visible && getBadGeomButton().isSelected());
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     */
    private void updateSplitButton() {
        if (getCidsBean() != null) {
            getSplitButton().setVisible(STATION_TO_MAP_REGISTRY.getCounter(getCidsBean()) > 1);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void split() {
        final double oldPosition = getFeature().getCurrentPosition();

        final CidsBean stationBean = StationEditor.createFromRoute(StationEditor.getRouteBean(getCidsBean()));
        setCidsBean(stationBean);

        // neue station auf selbe position setzen wie die alte
        getFeature().moveToPosition(oldPosition);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   routeBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean createFromRoute(final CidsBean routeBean) {
        final CidsBean cidsBean = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_STATION)
                    .getEmptyInstance()
                    .getBean();
        final MetaClass geomMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_GEOM);
        final CidsBean geomBean = geomMC.getEmptyInstance().getBean();

        try {
            cidsBean.setProperty(PROP_STATION_ROUTE, routeBean);
            cidsBean.setProperty(PROP_STATION_VALUE, 0d);
            cidsBean.setProperty(PROP_STATION_GEOM, geomBean);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while filling bean", ex);
            }
        }
        return cidsBean;
    }

    @Override
    public void dispose() {
        setCidsBean(null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static double getLinearValue(final CidsBean cidsBean) {
        return (Double)cidsBean.getProperty(PROP_STATION_VALUE);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   value     DOCUMENT ME!
     * @param   cidsBean  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void setLinearValue(final Double value, final CidsBean cidsBean) throws Exception {
        cidsBean.setProperty(PROP_STATION_VALUE, value);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Geometry getPointGeometry(final CidsBean cidsBean) {
        return (Geometry)getPointGeomBean(cidsBean).getProperty(PROP_GEOM_GEOFIELD);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   point     DOCUMENT ME!
     * @param   cidsBean  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void setPointGeometry(final Geometry point, final CidsBean cidsBean) throws Exception {
        getPointGeomBean(cidsBean).setProperty(PROP_GEOM_GEOFIELD, point);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Geometry getRouteGeometry(final CidsBean cidsBean) {
        return (Geometry)getRouteGeomBean(cidsBean).getProperty(PROP_GEOM_GEOFIELD);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   geometry  DOCUMENT ME!
     * @param   cidsBean  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void setRouteGeometry(final Geometry geometry, final CidsBean cidsBean) throws Exception {
        getRouteGeomBean(cidsBean).setProperty(PROP_GEOM_GEOFIELD, geometry);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Long getRouteGwk(final CidsBean cidsBean) {
        return (Long)getRouteBean(cidsBean).getProperty(PROP_ROUTE_GWK);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static CidsBean getPointGeomBean(final CidsBean cidsBean) {
        return (CidsBean)cidsBean.getProperty(PROP_STATION_GEOM);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean getRouteBean(final CidsBean cidsBean) {
        return (CidsBean)cidsBean.getProperty(PROP_STATION_ROUTE);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static CidsBean getRouteGeomBean(final CidsBean cidsBean) {
        return (CidsBean)getRouteBean(cidsBean).getProperty(PROP_ROUTE_GEOM);
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
        spinValue = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        labGwk = new javax.swing.JLabel();
        splitButton = new javax.swing.JButton();
        badGeomButton = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        badGeomCorrectButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        panAdd = new AddPanel();
        jLabel3 = new javax.swing.JLabel();
        panError = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setEnabled(false);
        setOpaque(false);
        setLayout(new java.awt.CardLayout());

        panEdit.setOpaque(false);
        panEdit.setLayout(new java.awt.GridBagLayout());

        spinValue.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spinValue.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panEdit.add(spinValue, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.jLabel5.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panEdit.add(jLabel5, gridBagConstraints);

        labGwk.setForeground(new java.awt.Color(128, 128, 128));
        labGwk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labGwk.setText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.labGwk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panEdit.add(labGwk, gridBagConstraints);

        splitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/sql-join-left.png"))); // NOI18N
        splitButton.setText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.splitButton.text")); // NOI18N
        splitButton.setToolTipText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.splitButton.toolTipText")); // NOI18N
        splitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                splitButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panEdit.add(splitButton, gridBagConstraints);

        badGeomButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/exclamation.png"))); // NOI18N
        badGeomButton.setText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.badGeomButton.text")); // NOI18N
        badGeomButton.setToolTipText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.badGeomButton.toolTipText")); // NOI18N
        badGeomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                badGeomButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        panEdit.add(badGeomButton, gridBagConstraints);

        jPanel2.setMaximumSize(new java.awt.Dimension(32, 0));
        jPanel2.setMinimumSize(new java.awt.Dimension(32, 0));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(32, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panEdit.add(jPanel2, gridBagConstraints);

        badGeomCorrectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/node-delete.png"))); // NOI18N
        badGeomCorrectButton.setText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.badGeomCorrectButton.text")); // NOI18N
        badGeomCorrectButton.setToolTipText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.badGeomCorrectButton.toolTipText")); // NOI18N
        badGeomCorrectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                badGeomCorrectButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        panEdit.add(badGeomCorrectButton, gridBagConstraints);

        jPanel3.setMaximumSize(new java.awt.Dimension(32, 0));
        jPanel3.setMinimumSize(new java.awt.Dimension(32, 0));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(32, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        panEdit.add(jPanel3, gridBagConstraints);

        add(panEdit, "edit");

        panAdd.setOpaque(false);
        panAdd.setLayout(new java.awt.GridBagLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.jLabel3.text")); // NOI18N
        panAdd.add(jLabel3, new java.awt.GridBagConstraints());

        add(panAdd, "add");

        panError.setOpaque(false);
        panError.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.jLabel4.text")); // NOI18N
        panError.add(jLabel4, new java.awt.GridBagConstraints());

        add(panError, "error");
    }// </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void splitButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_splitButtonActionPerformed
        split();
    }//GEN-LAST:event_splitButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void badGeomButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_badGeomButtonActionPerformed
        badGeomButtonPressed();
    }//GEN-LAST:event_badGeomButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void badGeomCorrectButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_badGeomCorrectButtonActionPerformed
        badGeomCorrectButtonPressed();
    }//GEN-LAST:event_badGeomCorrectButtonActionPerformed

    /**
     * DOCUMENT ME!
     */
    private void badGeomButtonPressed() {
        final Feature badGeomFeature = getBadGeomFeature();
        final Feature stationFeature = getFeature();

        final boolean selected = getBadGeomButton().isSelected();

        if (selected) {
            boundingbox = MAPPING_COMPONENT.getCurrentBoundingBox();

            MAPPING_COMPONENT.getFeatureCollection().addFeature(badGeomFeature);
            MAPPING_COMPONENT.getFeatureCollection().select(stationFeature);

            zoomToBadFeature();
        } else {
            MAPPING_COMPONENT.getFeatureCollection().removeFeature(badGeomFeature);
            MAPPING_COMPONENT.gotoBoundingBoxWithoutHistory(boundingbox);
        }

        getBadGeomCorrectButton().setVisible(selected);
    }

    /**
     * DOCUMENT ME!
     */
    private void badGeomCorrectButtonPressed() {
        final LinearReferencedPointFeature feature = getFeature();
        final Feature badFeature = getBadGeomFeature();
        feature.moveTo(badFeature.getGeometry().getCoordinate());
        zoomToBadFeature();
    }

    /**
     * DOCUMENT ME!
     */
    private void zoomToBadFeature() {
        final Feature badGeomFeature = getBadGeomFeature();
        final Collection<Feature> aFeatureCollection = new ArrayList<Feature>();
        aFeatureCollection.add(badGeomFeature);
        aFeatureCollection.add(getFeature());
        // TODO boundingbox
        MAPPING_COMPONENT.zoomToAFeatureCollection(aFeatureCollection, false, MAPPING_COMPONENT.isFixedMapScale());
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class AddPanel extends JPanel implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            CidsBean routeBean = null;
            for (final CidsBean bean : beans) {
                if (bean.getMetaObject().getMetaClass().getName().equals(MC_ROUTE)) {
                    if ((getDropBehavior() == null) || getDropBehavior().checkForAdding(routeBean)) {
                        routeBean = bean;
                        setChangedSinceDrop(false);
                    }
                    break;
                }
            }
            if (routeBean != null) {
                setCidsBean(createFromRoute(routeBean));
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("no route found in dropped objects");
                }
            }
        }
    }
}
