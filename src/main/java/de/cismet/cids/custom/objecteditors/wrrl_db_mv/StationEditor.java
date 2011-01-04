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

import java.awt.CardLayout;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.LinearReferencingConstants;
import de.cismet.cids.custom.util.StationToMapRegistry;
import de.cismet.cids.custom.util.StationToMapRegistryListener;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeatureListener;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class StationEditor extends JPanel implements DisposableCidsBeanStore, LinearReferencingConstants {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StationEditor.class);

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
        return (LinearReferencedPointFeature)StationToMapRegistry.getInstance().getFeature(cidsBean);
    }

    /**
     * DOCUMENT ME!
     */
    private void initStationToMapRegistryListener() {
        stationToMapRegistryListener = new StationToMapRegistryListener() {

                @Override
                public void FeatureCountChanged() {
                    updateSplitButton();
                }
            };
    }

    /**
     * DOCUMENT ME!
     */
    private void initCidsBeanListener() {
        cidsBeanListener = new PropertyChangeListener() {

                @Override
                public void propertyChange(final PropertyChangeEvent pce) {
                    if (pce.getPropertyName().equals(PROP_STATION_VALUE)) {
                        cidsBeanChanged((Double)pce.getNewValue());
                    }
                }
            };
    }

    /**
     * DOCUMENT ME!
     */
    private void initFeatureListener() {
        featureListener = new LinearReferencedPointFeatureListener() {

                @Override
                public void featureMoved(final LinearReferencedPointFeature pointFeature) {
                    featureChanged(pointFeature.getCurrentPosition());
                }

                @Override
                public void featureMerged(final LinearReferencedPointFeature mergePoint,
                        final LinearReferencedPointFeature withPoint) {
                    final CidsBean withBean = StationToMapRegistry.getInstance().getCidsBean(withPoint);
                    setCidsBean(withBean);

                    updateSplitButton();
                }
            };
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
        ((JSpinner.DefaultEditor)spinValue.getEditor()).getTextField()
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
                final AbstractFormatter formatter = ((JSpinner.DefaultEditor)spinValue.getEditor()).getTextField()
                            .getFormatter();
                final Double value = (Double)formatter.stringToValue(((JSpinner.DefaultEditor)spinValue.getEditor())
                                .getTextField().getText());
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

            if (!isSpinnerChangeLocked()) {
                spinValue.setValue(value);
            }

            if (getFeature() != null) {
                if (!isFeatureChangeLocked()) {
                    getFeature().moveToPosition(value);
                }
                try {
                    StationEditor.setPointGeometry(getFeature().getGeometry(), cidsBean);
                } catch (Exception ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("error while setting the " + PROP_STATION_GEOM + "property", ex);
                    }
                }
            }
        } finally {
            lockBeanChange(false);
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
        final CidsBean stationBean = cidsBean;
        final double oldValue = StationEditor.getLinearValue(stationBean);

        if (oldValue != value) {
            try {
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

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        final CidsBean oldBean = this.cidsBean;
        if (oldBean != null) {
            final LinearReferencedPointFeature oldFeature = StationToMapRegistry.getInstance()
                        .removeStationFeature(oldBean);
            if (oldFeature != null) {
                oldFeature.removeListener(featureListener);
            }

            // listener von der alten Bean entfernen
            oldBean.removePropertyChangeListener(cidsBeanListener);
            StationToMapRegistry.getInstance().removeListener(oldBean, stationToMapRegistryListener);
        }

        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            StationToMapRegistry.getInstance().addListener(cidsBean, stationToMapRegistryListener);
            cidsBean.addPropertyChangeListener(cidsBeanListener);

            final LinearReferencedPointFeature feature = StationToMapRegistry.getInstance().addStationFeature(cidsBean);
            feature.addListener(featureListener);

            if (ico != null) {
                feature.setIconImage(ico);
            }

            cidsBeanChanged(getLinearValue(cidsBean));
            fireStationCreated();

            ((SpinnerNumberModel)spinValue.getModel()).setMaximum(feature.getLineGeometry().getLength());
            labGwk.setText("Route: " + Long.toString(getRouteGwk(cidsBean)));

            showCard(Card.edit);
        } else {
            showCard(Card.add);
        }
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     */
    private void updateSplitButton() {
        if (cidsBean != null) {
            splitButton.setVisible(StationToMapRegistry.getInstance().getCounter(cidsBean) > 1);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void split() {
        final double oldPosition = getFeature().getCurrentPosition();

        final CidsBean stationBean = StationEditor.createFromRoute(StationEditor.getRouteBean(cidsBean));
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
        panAdd = new AddPanel();
        jLabel3 = new javax.swing.JLabel();
        panError = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setEnabled(false);
        setOpaque(false);
        setLayout(new java.awt.CardLayout());

        panEdit.setOpaque(false);
        panEdit.setLayout(new java.awt.GridBagLayout());

        spinValue.setModel(new javax.swing.SpinnerNumberModel(
                Double.valueOf(0.0d),
                Double.valueOf(0.0d),
                null,
                Double.valueOf(1.0d)));
        spinValue.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panEdit.add(spinValue, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png")));          // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.jLabel5.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panEdit.add(jLabel5, gridBagConstraints);

        labGwk.setForeground(new java.awt.Color(128, 128, 128));
        labGwk.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labGwk.setText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.labGwk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        panEdit.add(labGwk, gridBagConstraints);

        splitButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/split-to.png"))); // NOI18N
        splitButton.setText(org.openide.util.NbBundle.getMessage(
                StationEditor.class,
                "StationEditor.splitButton.text"));                                                       // NOI18N
        splitButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                StationEditor.class,
                "StationEditor.splitButton.toolTipText"));                                                // NOI18N
        splitButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    splitButtonActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panEdit.add(splitButton, gridBagConstraints);

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
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void splitButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_splitButtonActionPerformed
        split();
    }                                                                               //GEN-LAST:event_splitButtonActionPerformed

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
                    routeBean = bean;
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
