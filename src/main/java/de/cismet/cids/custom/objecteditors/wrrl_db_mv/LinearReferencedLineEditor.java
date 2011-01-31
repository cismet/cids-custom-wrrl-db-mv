/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

import java.awt.CardLayout;
import java.awt.Color;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JPanel;
import javax.swing.JSpinner;
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
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
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
public class LinearReferencedLineEditor extends JPanel implements DisposableCidsBeanStore,
    LinearReferencingConstants,
    CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            LinearReferencedLineEditor.class);

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
    private LinearReferencedLineEditorDropBehavior behavior;

    private String fromStationField;
    private String toStationField;
    private String realGeomField;
    private String metaClassName;
    private CidsBean cidsBean;

    private boolean changedSinceDrop = false;

    private Collection<LinearReferencedLineEditorListener> listeners =
        new ArrayList<LinearReferencedLineEditorListener>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel labGwk;
    private javax.swing.JPanel panAdd;
    private javax.swing.JPanel panEdit;
    private javax.swing.JPanel panError;
    private javax.swing.JPanel panLine;
    private javax.swing.JPanel panSpinner;
    private javax.swing.JSpinner spinFrom;
    private javax.swing.JSpinner spinTo;
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
                LOG.debug("error while creating CidsBeanDropTarget");
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
    }

    /**
     * Creates a new LinearReferencedLineEditor object.
     *
     * @param  metaClassName     DOCUMENT ME!
     * @param  fromStationField  DOCUMENT ME!
     * @param  toStationField    DOCUMENT ME!
     * @param  realGeomField     DOCUMENT ME!
     */
    public LinearReferencedLineEditor(final String metaClassName,
            final String fromStationField,
            final String toStationField,
            final String realGeomField) {
        this();
        setMetaClassName(metaClassName);
        setFromStationField(fromStationField);
        setToStationField(toStationField);
        setRealGeomField(realGeomField);
    }

    //~ Methods ----------------------------------------------------------------

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
     */
    public void fireLineAdded() {
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
     * @param  metaClassName  DOCUMENT ME!
     */
    public final void setMetaClassName(final String metaClassName) {
        this.metaClassName = metaClassName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fromStationField  DOCUMENT ME!
     */
    public final void setFromStationField(final String fromStationField) {
        this.fromStationField = fromStationField;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  toStationField  DOCUMENT ME!
     */
    public final void setToStationField(final String toStationField) {
        this.toStationField = toStationField;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  realGeomField  DOCUMENT ME!
     */
    public final void setRealGeomField(final String realGeomField) {
        this.realGeomField = realGeomField;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public LinearReferencedLineFeature getFeature() {
        return feature;
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    // >> BEAN

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;

        if (checkProperties() && (getStationBean(FROM) != null) && (getStationBean(TO) != null)) {
            setStationBean(getStationBean(FROM), FROM);
            setStationBean(getStationBean(TO), TO);
            setLineBean(cidsBean);

            stationBeanChanged(FROM);
            stationBeanChanged(TO);

            ((SpinnerNumberModel)spinFrom.getModel()).setMaximum(getRouteGeometry().getLength());
            ((SpinnerNumberModel)spinTo.getModel()).setMaximum(getRouteGeometry().getLength());
            labGwk.setText("Route: " + Long.toString(StationEditor.getRouteGwk(getStationBean(FROM))));

            showCard(Card.edit);
        } else {
            setStationBean(null, FROM);
            setStationBean(null, TO);
            setLineBean(null);

            showCard(Card.add);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stationBean  DOCUMENT ME!
     * @param  isFrom       DOCUMENT ME!
     */
    private void setStationBean(final CidsBean stationBean, final boolean isFrom) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setStationBean " + stationBean, new CurrentStackTrace());
        }

        final StationToMapRegistryListener stationToMapRegistryListener = (isFrom) ? fromStationToMapRegistryListener
                                                                                   : toStationToMapRegistryListener;
        final PropertyChangeListener stationBeanListener = (isFrom) ? fromStationBeanListener : toStationBeanListener;
        final LinearReferencedPointFeature oldStationFeature = (isFrom) ? fromFeature : toFeature;
        final LinearReferencedPointFeatureListener stationFeatureListener = (isFrom) ? fromFeatureListener
                                                                                     : toFeatureListener;

        // aufräumen
        if (oldStationFeature != null) {
            final CidsBean oldStationBean = StationToMapRegistry.getInstance().getCidsBean(oldStationFeature);
            if (oldStationBean != null) {
                StationToMapRegistry.getInstance().removeStationFeature(oldStationBean);
                oldStationFeature.removeListener(stationFeatureListener);
                oldStationBean.removePropertyChangeListener(stationBeanListener);
                StationToMapRegistry.getInstance().removeListener(oldStationBean, stationToMapRegistryListener);
            }
        }

        LinearReferencedPointFeature stationFeature;
        if (stationBean != null) {
            // bean listener
            stationBean.addPropertyChangeListener(stationBeanListener);
            StationToMapRegistry.getInstance().addListener(stationBean, stationToMapRegistryListener);

            // feature erzeugen
            stationFeature = StationToMapRegistry.getInstance().addStationFeature(stationBean);

            // feature listener
            stationFeature.addListener(stationFeatureListener);

            // bean setzen
            if (cidsBean != null) {
                try {
                    final String stationField = (isFrom) ? fromStationField : toStationField;
                    cidsBean.setProperty(stationField, stationBean);
                } catch (Exception ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("error while setting cidsbean for merging stations", ex);
                    }
                }
            }
        } else {
            // bean ist null, feature also auch
            stationFeature = null;
        }

        // feature setzen
        if (isFrom) {
            fromFeature = stationFeature;
        } else {
            toFeature = stationFeature;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    private void setLineBean(final CidsBean cidsBean) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("setLineBean " + cidsBean, new CurrentStackTrace());
        }
        final LinearReferencedLineFeature oldFeature = this.feature;

        if (oldFeature != null) {
            final CidsBean oldBean = StationToMapRegistry.getInstance().getCidsBean(oldFeature);
            StationToMapRegistry.getInstance().removeLinearReferencedLineFeature(oldBean);
        }

        if (cidsBean != null) {
            // feature erzeugen
            feature = StationToMapRegistry.getInstance()
                        .addLinearReferencedLineFeature(
                                cidsBean,
                                getStationFeature(FROM),
                                getStationFeature(TO));

            // farbe setzen
            final Color color = (Color)feature.getLinePaint();
            panLine.setBackground(color);

            fireLineAdded();
        } else {
            // bean ist null, also feature auch
            feature = null;
        }
    }

    /**
     * BEAN.<<
     *
     * @return  DOCUMENT ME!
     */
    private boolean checkProperties() {
        if (cidsBean == null) {
            return false;
        }
        boolean fromStationFound = false;
        boolean toStationFound = false;
        boolean realGeomFound = false;
        for (final String propertyName : cidsBean.getPropertyNames()) {
            if (propertyName.equals(fromStationField)) {
                fromStationFound = true;
            }
            if (propertyName.equals(toStationField)) {
                toStationFound = true;
            }
            if (propertyName.equals(realGeomField)) {
                realGeomFound = true;
            }
        }

        if (fromStationFound && toStationFound && realGeomFound) {
            return true;
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error, property not found! | fromStationField found = '" + fromStationField + "' "
                            + fromStationFound + ", toStationField found = '" + toStationField + "' " + toStationFound
                            + ", realGeomField found = '" + realGeomField + "' " + realGeomFound);
            }
            return false;
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
     * @param  cidsBean   DOCUMENT ME!
     * @param  routeBean  DOCUMENT ME!
     */
    private void fillFromRoute(final CidsBean cidsBean, final CidsBean routeBean) {
        final MetaClass stationMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_STATION);
        final MetaClass geomMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_GEOM);

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

            cidsBean.setProperty(fromStationField, fromBean);
            cidsBean.setProperty(toStationField, toBean);
            cidsBean.setProperty(realGeomField, geomBean);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while filling bean", ex);
            }
        }
    }

    @Override
    public void dispose() {
        setCidsBean(null);
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

        if (isFrom) {
            fromStationToMapRegistryListener = listener;
        } else {
            toStationToMapRegistryListener = listener;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void initSpinnerListener(final boolean isFrom) {
        final JSpinner spinner = (isFrom) ? spinFrom : spinTo;
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
                    final CidsBean withBean = StationToMapRegistry.getInstance().getCidsBean(withPoint);

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

        if (isFrom) {
            fromFeatureListener = featureListener;
        } else {
            toFeatureListener = featureListener;
        }
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
                final LinearReferencedPointFeature linearRefFeature = (isFrom) ? fromFeature : toFeature;

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

            try {
                Double value;
                if (isFrom) {
                    final AbstractFormatter formatter = ((JSpinner.DefaultEditor)spinFrom.getEditor()).getTextField()
                                .getFormatter();
                    value = (Double)formatter.stringToValue(((JSpinner.DefaultEditor)spinFrom.getEditor())
                                    .getTextField().getText());
                } else {
                    final AbstractFormatter formatter = ((JSpinner.DefaultEditor)spinTo.getEditor()).getTextField()
                                .getFormatter();
                    value = (Double)formatter.stringToValue(((JSpinner.DefaultEditor)spinTo.getEditor()).getTextField()
                                    .getText());
                }
                changeStationValue(value, isFrom);
            } catch (ParseException ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error parsing spinner", ex);
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

            changedSinceDrop = true;

            final CidsBean stationBean = getStationBean(isFrom);

            if (stationBean != null) {
                final double value = (Double)stationBean.getProperty(PROP_STATION_VALUE);

                changeSpinner(value, isFrom);
                changeFeature(value, isFrom);

                updateRealGeoms(isFrom);
            }
        } finally {
            lockBeanChange(false, isFrom);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void updateSplitButton(final boolean isFrom) {
        if (cidsBean != null) {
            final CidsBean stationBean = getStationBean(isFrom);
            if (stationBean != null) {
                final JButton stationButton = (isFrom) ? jButton1 : jButton2;
                stationButton.setVisible(StationToMapRegistry.getInstance().getCounter(stationBean) > 1);
            }
        }
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
            final JSpinner stationSpinner = (isFrom) ? spinFrom : spinTo;
            stationSpinner.setValue(value);
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
            final LinearReferencedPointFeature stationFeature = (isFrom) ? fromFeature : toFeature;
            if (stationFeature != null) {
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
            final LinearReferencedPointFeature stationFeature = (isFrom) ? fromFeature : toFeature;
            final CidsBean stationBean = getStationBean(isFrom);

            // realgeom der Station anpassen
            if (stationFeature != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("change station geom");
                }
                final Geometry geometry = stationFeature.getGeometry();
                StationEditor.setPointGeometry(geometry, stationBean);
            }
            // realgeom der Linie anpassen
            if (feature != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("change line geom");
                }
                setRealGeometry(feature.getGeometry());
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
        final double oldValue = StationEditor.getLinearValue(stationBean);

        if (oldValue != value) {
            try {
                if (!isBeanChangeLocked(isFrom)) {
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

        if (isFrom) {
            fromStationBeanListener = stationBeanListener;
        } else {
            toStationBeanListener = stationBeanListener;
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
     * @return  DOCUMENT ME!
     */
    private Geometry getRouteGeometry() {
        return (Geometry)getRouteGeomBean().getProperty(PROP_GEOM_GEOFIELD);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   isFrom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getStationBean(final boolean isFrom) {
        final String stationField = (isFrom) ? fromStationField : toStationField;
        return (CidsBean)cidsBean.getProperty(stationField);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getRouteBean() {
        return (CidsBean)getStationBean(FROM).getProperty(PROP_STATION_ROUTE);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getRouteGeomBean() {
        return (CidsBean)getRouteBean().getProperty(PROP_ROUTE_GEOM);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getRealGeomBean() {
        return (CidsBean)cidsBean.getProperty(realGeomField);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   line  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private void setRealGeometry(final Geometry line) throws Exception {
        getRealGeomBean().setProperty(PROP_GEOM_GEOFIELD, line);
    }

    @Override
    public void setEnabled(final boolean bln) {
        super.setEnabled(bln);
        jLabel3.setVisible(bln);
        spinFrom.setEnabled(bln);
        spinTo.setEnabled(bln);
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
        panSpinner = new javax.swing.JPanel();
        spinFrom = new javax.swing.JSpinner();
        spinTo = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        panAdd = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panError = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(panLine, gridBagConstraints);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel5.text"));                                             // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        jLabel6.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel6.text"));                                             // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jLabel6, gridBagConstraints);

        labGwk.setForeground(new java.awt.Color(128, 128, 128));
        labGwk.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.labGwk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 0, 25);
        jPanel1.add(labGwk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 35, 5, 35);
        panEdit.add(jPanel1, gridBagConstraints);

        panSpinner.setOpaque(false);
        panSpinner.setLayout(new java.awt.GridBagLayout());

        spinFrom.setModel(new javax.swing.SpinnerNumberModel(
                Double.valueOf(0.0d),
                Double.valueOf(0.0d),
                null,
                Double.valueOf(1.0d)));
        spinFrom.setMinimumSize(new java.awt.Dimension(100, 28));
        spinFrom.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        panSpinner.add(spinFrom, gridBagConstraints);

        spinTo.setModel(new javax.swing.SpinnerNumberModel(
                Double.valueOf(0.0d),
                Double.valueOf(0.0d),
                null,
                Double.valueOf(1.0d)));
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

        jButton1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/split-from.png"))); // NOI18N
        jButton1.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jButton1.text"));                                               // NOI18N
        jButton1.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jButton1.toolTipText"));                                        // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panSpinner.add(jButton1, gridBagConstraints);

        jButton2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/split-to.png"))); // NOI18N
        jButton2.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jButton2.text"));                                             // NOI18N
        jButton2.setToolTipText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jButton2.toolTipText"));                                      // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panSpinner.add(jButton2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panEdit.add(panSpinner, gridBagConstraints);

        add(panEdit, "edit");

        panAdd.setOpaque(false);
        panAdd.setLayout(new java.awt.GridBagLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel3.text")); // NOI18N
        panAdd.add(jLabel3, new java.awt.GridBagConstraints());

        add(panAdd, "add");

        panError.setOpaque(false);
        panError.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineEditor.class,
                "LinearReferencedLineEditor.jLabel4.text")); // NOI18N
        panError.add(jLabel4, new java.awt.GridBagConstraints());

        add(panError, "error");
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jButton1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
        splitStation(FROM);
    }                                                                            //GEN-LAST:event_jButton1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jButton2ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton2ActionPerformed
        splitStation(TO);
    }                                                                            //GEN-LAST:event_jButton2ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  isFrom  DOCUMENT ME!
     */
    private void splitStation(final boolean isFrom) {
        final double oldPosition = getStationFeature(isFrom).getCurrentPosition();

        final CidsBean stationBean = StationEditor.createFromRoute(StationEditor.getRouteBean(getStationBean(isFrom)));
        setStationBean(stationBean, isFrom);
        stationBeanChanged(isFrom);

        // neue station auf selbe position setzen wie die alte
        final LinearReferencedPointFeature stationFeature = (isFrom) ? fromFeature : toFeature;
        stationFeature.moveToPosition(oldPosition);

        getFeature().setPointFeature(getStationFeature(isFrom), isFrom);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  routeBean  DOCUMENT ME!
     */
    private void addFromRoute(final CidsBean routeBean) {
        if (cidsBean == null) {
            cidsBean = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, metaClassName).getEmptyInstance()
                        .getBean();
        }
        fillFromRoute(cidsBean, routeBean);
        setCidsBean(cidsBean);

        // Geometrie für BoundingBox erzeufen
        final BoundingBox boundingBox = CismapBroker.getInstance().getMappingComponent().getCurrentBoundingBox();
        final Collection<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY1()));
        coordinates.add(new Coordinate(boundingBox.getX2(), boundingBox.getY1()));
        coordinates.add(new Coordinate(boundingBox.getX2(), boundingBox.getY2()));
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY2()));
        coordinates.add(new Coordinate(boundingBox.getX1(), boundingBox.getY1()));
        final GeometryFactory gf = new GeometryFactory();
        final LineString boundingBoxGeom = gf.createLineString(coordinates.toArray(new Coordinate[0]));

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

    /**
     * DOCUMENT ME!
     *
     * @param  behavior  DOCUMENT ME!
     */
    public void setDropBehavior(final LinearReferencedLineEditorDropBehavior behavior) {
        this.behavior = behavior;
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> cidsBeans) {
        if (isEnabled()) {
            for (final CidsBean routeBean : cidsBeans) {
                if (routeBean.getMetaObject().getMetaClass().getName().equals(MC_ROUTE)) {
                    if ((behavior == null) || behavior.checkForAdding(routeBean)) {
                        addFromRoute(routeBean);
                        changedSinceDrop = false;
                    }
                    return;
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("no route found in dropped objects");
            }
        }
    }
}
