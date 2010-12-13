package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import de.cismet.cids.custom.util.StationToMapRegistry;
import Sirius.server.middleware.types.MetaClass;
import com.vividsolutions.jts.geom.Geometry;
import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.LinearReferencingConstants;
import de.cismet.cids.custom.util.StationToMapRegistryListener;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;
import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeatureListener;
import de.cismet.tools.CurrentStackTrace;
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

/**
 *
 * @author jruiz
 */
public class StationEditor extends JPanel implements DisposableCidsBeanStore, LinearReferencingConstants {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StationEditor.class);

    //private LinearReferencedPointFeature feature;
    private PropertyChangeListener cidsBeanListener;
    private boolean isSpinnerChangeLocked = false;
    private boolean isFeatureChangeLocked = false;
    private boolean isBeanChangeLocked = false;
    private ImageIcon ico;
    private CidsBean cidsBean;
    private Collection<StationEditorListener> listeners = new ArrayList<StationEditorListener>();
    private LinearReferencedPointFeatureListener featureListener;
    private StationToMapRegistryListener stationToMapRegistryListener;

    private enum Card {edit, add, error};

    public StationEditor() {
        initComponents();
        
        try {
            new CidsBeanDropTarget(panAdd);
        } catch (Exception ex) {
            LOG.debug("error while creating CidsBeanDropTarget");
        }

        initCidsBeanListener();
        initSpinnerListener();
        initFeatureListener();
        initStationToMapRegistryListener();
    }

    public boolean addStationEditorListener(StationEditorListener listener) {
        return listeners.add(listener);
    }

    public void setImageIcon(ImageIcon ico) {
        this.ico = ico;
        if (getFeature() != null) {
            getFeature().setIconImage(ico);
        }
        jLabel5.setIcon(ico);
    }

    private void fireStationCreated() {
        for (StationEditorListener listener : listeners) {
            listener.stationCreated();
        }
    }    

    public LinearReferencedPointFeature getFeature() {
        return (LinearReferencedPointFeature) StationToMapRegistry.getInstance().getFeature(cidsBean);
    }

    private void initStationToMapRegistryListener() {
        stationToMapRegistryListener = new StationToMapRegistryListener() {
            @Override
            public void FeatureCountChanged() {
                updateSplitButton();
            }
        };
    }

    private void initCidsBeanListener() {
        cidsBeanListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (pce.getPropertyName().equals(PROP_STATION_VALUE)) {
                    cidsBeanChanged((Double) pce.getNewValue());
                }
            }
        };
    }

    private void initFeatureListener() {
        featureListener = new LinearReferencedPointFeatureListener() {

            @Override
            public void featureMoved(LinearReferencedPointFeature pointFeature) {
                featureChanged(pointFeature.getCurrentPosition());
            }

            @Override
            public void featureMerged(LinearReferencedPointFeature mergePoint, LinearReferencedPointFeature withPoint) {
                CidsBean withBean = StationToMapRegistry.getInstance().getCidsBean(withPoint);
                setCidsBean(withBean);
                
                updateSplitButton();
            }
        };
    }

    private void showCard(Card card) {
        switch (card) {
            case edit:
                ((CardLayout) getLayout()).show(this, "edit");
                break;
            case add:
                ((CardLayout) getLayout()).show(this, "add");
                break;
            case error:
                ((CardLayout) getLayout()).show(this, "error");
                break;
        }
    }

    private void initSpinnerListener() {
        ((JSpinner.DefaultEditor) spinValue.getEditor()).getTextField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                spinnerChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                spinnerChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                spinnerChanged();
            }
        });
    }

    private void spinnerChanged() {
        LOG.debug("spinner changed", new CurrentStackTrace());

        try {
            lockSpinnerChange(true);

            try {
                AbstractFormatter formatter = ((JSpinner.DefaultEditor) spinValue.getEditor()).getTextField().getFormatter();
                Double value = (Double) formatter.stringToValue(((JSpinner.DefaultEditor) spinValue.getEditor()).getTextField().getText());
                changeValue(value);
            } catch (ParseException ex) {
                LOG.debug("error parsing spinner", ex);
            }
        } finally {
            lockSpinnerChange(false);
        }
    }

    private void featureChanged(double value) {
        try {
            lockFeatureChange(true);
            changeValue(value);
        } finally {
            lockFeatureChange(false);
        }
    }

    private void cidsBeanChanged(double value) {
        LOG.debug("cidsbean changed", new CurrentStackTrace());

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

    private void changeValue(double value) {
        LOG.debug("change bean value to " + value);
        final CidsBean stationBean = cidsBean;
        final double oldValue = StationEditor.getLinearValue(stationBean);

        if (oldValue != value) {
            try {
                if (!isBeanChangeLocked()) {
                    StationEditor.setLinearValue(value, stationBean);
                }
            } catch (Exception ex) {
                LOG.debug("error changing bean", ex);
            }
        } else {
            LOG.debug("no changes needed, old value was " + oldValue);
        }
    }
    
    private boolean isSpinnerChangeLocked() {
        return isSpinnerChangeLocked;
    }

    private boolean isFeatureChangeLocked() {
        return isFeatureChangeLocked;
    }

    private boolean isBeanChangeLocked() {
        return isBeanChangeLocked;
    }

    private void lockSpinnerChange(final boolean lock) {
        isSpinnerChangeLocked = lock;
    }

    private void lockFeatureChange(final boolean lock) {
        isFeatureChangeLocked = lock;
    }

    private void lockBeanChange(final boolean lock) {
        isBeanChangeLocked = lock;
    }

    @Override
    public void setCidsBean(CidsBean cidsBean) {
        CidsBean oldBean = this.cidsBean;
        if (oldBean != null) {

            LinearReferencedPointFeature oldFeature = StationToMapRegistry.getInstance().removeStationFeature(oldBean);
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

            LinearReferencedPointFeature feature = StationToMapRegistry.getInstance().addStationFeature(cidsBean);
            feature.addListener(featureListener);

            if (ico != null) {
                feature.setIconImage(ico);
            }

            cidsBeanChanged(getLinearValue(cidsBean));
            fireStationCreated();
            
            ((SpinnerNumberModel) spinValue.getModel()).setMaximum(feature.getLineGeometry().getLength());
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

    private void updateSplitButton() {
        if (cidsBean != null) {
            splitButton.setVisible(StationToMapRegistry.getInstance().getCounter(cidsBean) > 1);
        }
    }

    private void split() {
        double oldPosition = getFeature().getCurrentPosition();

        CidsBean stationBean = StationEditor.createFromRoute(StationEditor.getRouteBean(cidsBean));
        setCidsBean(stationBean);

        // neue station auf selbe position setzen wie die alte
        getFeature().moveToPosition(oldPosition);
    }

    public static CidsBean createFromRoute(CidsBean routeBean) {
        CidsBean cidsBean = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_STATION).getEmptyInstance().getBean();
        MetaClass geomMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_GEOM);
        CidsBean geomBean = geomMC.getEmptyInstance().getBean();

        try {
            cidsBean.setProperty(PROP_STATION_ROUTE, routeBean);
            cidsBean.setProperty(PROP_STATION_VALUE, 0d);
            cidsBean.setProperty(PROP_STATION_GEOM, geomBean);
        } catch (Exception ex) {
            LOG.debug("Error while filling bean", ex);
        }
        return cidsBean;
    }

    @Override
    public void dispose() {
        setCidsBean(null);
    }

    public static double getLinearValue(CidsBean cidsBean) {
        return (Double) cidsBean.getProperty(PROP_STATION_VALUE);
    }

    public static void setLinearValue(Double value, CidsBean cidsBean) throws Exception {
        cidsBean.setProperty(PROP_STATION_VALUE, value);
    }

    public static Geometry getPointGeometry(CidsBean cidsBean) {
        return (Geometry) getPointGeomBean(cidsBean).getProperty(PROP_GEOM_GEOFIELD);
    }

    public static void setPointGeometry(Geometry point, CidsBean cidsBean) throws Exception {
        getPointGeomBean(cidsBean).setProperty(PROP_GEOM_GEOFIELD, point);
    }

    public static Geometry getRouteGeometry(CidsBean cidsBean) {
        return (Geometry) getRouteGeomBean(cidsBean).getProperty(PROP_GEOM_GEOFIELD);
    }

    public static void setRouteGeometry(Geometry geometry, CidsBean cidsBean) throws Exception {
        getRouteGeomBean(cidsBean).setProperty(PROP_GEOM_GEOFIELD, geometry);
    }

    public static Long getRouteGwk(CidsBean cidsBean) {
        return (Long) getRouteBean(cidsBean).getProperty(PROP_ROUTE_GWK);
    }

    private static CidsBean getPointGeomBean(CidsBean cidsBean) {
        return (CidsBean) cidsBean.getProperty(PROP_STATION_GEOM);
    }

    public static CidsBean getRouteBean(CidsBean cidsBean) {
        return (CidsBean) cidsBean.getProperty(PROP_STATION_ROUTE);
    }

    private static CidsBean getRouteGeomBean(CidsBean cidsBean) {
        return (CidsBean) getRouteBean(cidsBean).getProperty(PROP_ROUTE_GEOM);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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

        spinValue.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spinValue.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panEdit.add(spinValue, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
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

        splitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/split-to.png"))); // NOI18N
        splitButton.setText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.splitButton.text")); // NOI18N
        splitButton.setToolTipText(org.openide.util.NbBundle.getMessage(StationEditor.class, "StationEditor.splitButton.toolTipText")); // NOI18N
        splitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
    }// </editor-fold>//GEN-END:initComponents

    private void splitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_splitButtonActionPerformed
        split();
}//GEN-LAST:event_splitButtonActionPerformed


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

    class AddPanel extends JPanel implements CidsBeanDropListener {

        @Override
        public void beansDropped(ArrayList<CidsBean> beans) {
            CidsBean routeBean = null;
            for (CidsBean bean : beans) {
                if (bean.getMetaObject().getMetaClass().getName().equals(MC_ROUTE)) {
                    routeBean = bean;
                    break;
                }
            }
            if (routeBean != null) {
                setCidsBean(createFromRoute(routeBean));
            } else {
                LOG.debug("no route found in dropped objects");
            }
        }
    }

}
