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
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeatureListener;
import de.cismet.tools.CurrentStackTrace;
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

/**
 *
 * @author jruiz
 */
public class LinearReferencedLineEditor extends JPanel implements DisposableCidsBeanStore, LinearReferencingConstants {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LinearReferencedLineEditor.class);

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

    private String fromStationField;
    private String toStationField;
    private String realGeomField;
    private String metaClassName;
    private CidsBean cidsBean;

    private Collection<LinearReferencedLineEditorListener> listeners = new ArrayList<LinearReferencedLineEditorListener>();

    private enum Card {edit, add, error};

    /** Creates new form LinearReferencedLineEditor */
    public LinearReferencedLineEditor() {
        initComponents();

        try {
            new CidsBeanDropTarget(panAdd);
        } catch (Exception ex) {
            LOG.debug("error while creating CidsBeanDropTarget");
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

    public LinearReferencedLineEditor(final String metaClassName, final String fromStationField, final String toStationField, final String realGeomField) {
        this();
        setMetaClassName(metaClassName);
        setFromStationField(fromStationField);
        setToStationField(toStationField);
        setRealGeomField(realGeomField);
    }

    public void fireLineAdded() {
        for(LinearReferencedLineEditorListener listener : listeners) {
            listener.linearReferencedLineCreated();
        }
    }

    public boolean addLinearReferencedLineEditorListener(LinearReferencedLineEditorListener listener) {
        return listeners.add(listener);
    }

    public final void setMetaClassName(final String metaClassName) {
        this.metaClassName = metaClassName;
    }

    public final void setFromStationField(final String fromStationField) {
        this.fromStationField = fromStationField;
    }

    public final void setToStationField(final String toStationField) {
        this.toStationField = toStationField;
    }

    public final void setRealGeomField(final String realGeomField) {
        this.realGeomField = realGeomField;
    }

    public LinearReferencedLineFeature getFeature() {
        return feature;
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    // >> BEAN

    @Override
    public void setCidsBean(CidsBean cidsBean) {
        this.cidsBean = cidsBean;
        
        if (checkProperties() && getStationBean(FROM) != null && getStationBean(TO) != null) {
            setStationBean(getStationBean(FROM), FROM);
            setStationBean(getStationBean(TO), TO);
            setLineBean(cidsBean);

            stationBeanChanged(FROM);
            stationBeanChanged(TO);

            ((SpinnerNumberModel) spinFrom.getModel()).setMaximum(getRouteGeometry().getLength());
            ((SpinnerNumberModel) spinTo.getModel()).setMaximum(getRouteGeometry().getLength());
            labGwk.setText("Route: " + Long.toString(StationEditor.getRouteGwk(getStationBean(FROM))));

            showCard(Card.edit);
        } else {
            setStationBean(null, FROM);
            setStationBean(null, TO);
            setLineBean(null);
            
            showCard(Card.add);
        }
    }

    private void setStationBean(CidsBean stationBean, boolean isFrom) {
        LOG.debug("setStationBean " + stationBean, new CurrentStackTrace());

        StationToMapRegistryListener stationToMapRegistryListener = (isFrom) ? fromStationToMapRegistryListener : toStationToMapRegistryListener;
        PropertyChangeListener stationBeanListener = (isFrom) ? fromStationBeanListener : toStationBeanListener;
        LinearReferencedPointFeature oldStationFeature = (isFrom) ? fromFeature : toFeature;
        LinearReferencedPointFeatureListener stationFeatureListener = (isFrom) ? fromFeatureListener : toFeatureListener;

        // aufräumen
        if (oldStationFeature != null)  {
            CidsBean oldStationBean = StationToMapRegistry.getInstance().getCidsBean(oldStationFeature);
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
                    String stationField = (isFrom) ? fromStationField : toStationField;
                    cidsBean.setProperty(stationField, stationBean);
                } catch (Exception ex) {
                    LOG.debug("error while setting cidsbean for merging stations", ex);
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

    private void setLineBean(CidsBean cidsBean) {
        LOG.debug("setLineBean " + cidsBean, new CurrentStackTrace());
        LinearReferencedLineFeature oldFeature = this.feature;

        if (oldFeature != null) {
            CidsBean oldBean = StationToMapRegistry.getInstance().getCidsBean(oldFeature);
            StationToMapRegistry.getInstance().removeLinearReferencedLineFeature(oldBean);
        }

        if (cidsBean != null) {
            // feature erzeugen
            feature = StationToMapRegistry.getInstance().addLinearReferencedLineFeature(
                cidsBean,
                getStationFeature(FROM),
                getStationFeature(TO)
            );

            // farbe setzen
            Color color = (Color) feature.getLinePaint();
            panLine.setBackground(color);

            fireLineAdded();
        } else {
            // bean ist null, also feature auch
            feature = null;
        }
    }

    // BEAN <<

    private boolean checkProperties() {
        if (cidsBean == null) {
            return false;
        }
        boolean fromStationFound = false;
        boolean toStationFound = false;
        boolean realGeomFound = false;
        for (String propertyName : cidsBean.getPropertyNames()) {
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
            LOG.debug("Error, property not found! | fromStationField found = '" + fromStationField + "' " + fromStationFound + ", toStationField found = '" + toStationField + "' " + toStationFound + ", realGeomField found = '" + realGeomField + "' " + realGeomFound);
            return false;
        }
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

    private void fillFromRoute(CidsBean cidsBean, CidsBean routeBean) {
        MetaClass stationMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_STATION);
        MetaClass geomMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_GEOM);

        CidsBean fromBean = stationMC.getEmptyInstance().getBean();
        CidsBean toBean = stationMC.getEmptyInstance().getBean();
        CidsBean geomBean = geomMC.getEmptyInstance().getBean();
        CidsBean fromGeomBean = geomMC.getEmptyInstance().getBean();
        CidsBean toGeomBean = geomMC.getEmptyInstance().getBean();

        try {
            fromBean.setProperty(PROP_STATION_ROUTE, routeBean);
            fromBean.setProperty(PROP_STATION_VALUE, 0d);
            fromBean.setProperty(PROP_STATION_GEOM, fromGeomBean);

            toBean.setProperty(PROP_STATION_ROUTE, routeBean);
            toBean.setProperty(PROP_STATION_VALUE, ((Geometry) ((CidsBean) routeBean.getProperty(PROP_ROUTE_GEOM)).getProperty(PROP_GEOM_GEOFIELD)).getLength());
            toBean.setProperty(PROP_STATION_GEOM, toGeomBean);

            cidsBean.setProperty(fromStationField, fromBean);
            cidsBean.setProperty(toStationField, toBean);
            cidsBean.setProperty(realGeomField, geomBean);
        } catch (Exception ex) {
            LOG.debug("Error while filling bean", ex);
        }
    }


    @Override
    public void dispose() {
        setCidsBean(null);
    }

    private void initStationToMapRegistryListener(final boolean isFrom) {
        StationToMapRegistryListener listener = new StationToMapRegistryListener() {
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

    private void initSpinnerListener(final boolean isFrom) {
        JSpinner spinner = (isFrom) ? spinFrom : spinTo;
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                spinnerChanged(isFrom);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                spinnerChanged(isFrom);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                spinnerChanged(isFrom);
            }
        });
    }

    private void initFeatureListener(final boolean isFrom) {        
        LinearReferencedPointFeatureListener featureListener = new LinearReferencedPointFeatureListener() {

            @Override
            public void featureMoved(LinearReferencedPointFeature pointFeature) {
                featureChanged(isFrom);
            }

            //@Override
            // kommt noch
            public void featureSplitted(LinearReferencedPointFeature mergePoint, LinearReferencedPointFeature withPoint) {

            }

            @Override
            public void featureMerged(LinearReferencedPointFeature mergePoint, LinearReferencedPointFeature withPoint) {
                CidsBean withBean = StationToMapRegistry.getInstance().getCidsBean(withPoint);

                if ((fromFeature.equals(mergePoint) && !toFeature.equals(withPoint)) || (toFeature.equals(mergePoint) && !fromFeature.equals(withPoint))) {
                    boolean isFrom = fromFeature.equals(mergePoint);

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

    /*
     * cidsbean ändern
     */
    private void featureChanged(final boolean isFrom) {
        LOG.debug("feature changed", new CurrentStackTrace());
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

    /*
     * cidsbean ändern
     */
    private void spinnerChanged(final boolean isFrom) {
        LOG.debug("spinner changed", new CurrentStackTrace());
        try {
            lockSpinnerChange(true, isFrom);
            
            try {
                Double value;
                if (isFrom) {
                    AbstractFormatter formatter = ((JSpinner.DefaultEditor) spinFrom.getEditor()).getTextField().getFormatter();
                    value = (Double) formatter.stringToValue(((JSpinner.DefaultEditor) spinFrom.getEditor()).getTextField().getText());
                } else {
                    AbstractFormatter formatter = ((JSpinner.DefaultEditor) spinTo.getEditor()).getTextField().getFormatter();
                    value = (Double) formatter.stringToValue(((JSpinner.DefaultEditor) spinTo.getEditor()).getTextField().getText());
                }
                changeStationValue(value, isFrom);
            } catch (ParseException ex) {
                LOG.debug("error parsing spinner", ex);
            }
        } finally {
            lockSpinnerChange(false, isFrom);
        }
    }

    /*
     * spinner ändern
     * feature ändern
     * realgeoms anpassen
     */
    private void stationBeanChanged(final boolean isFrom) {
        LOG.debug("cidsbean changed", new CurrentStackTrace());

        try {
            lockBeanChange(true, isFrom);

            final CidsBean stationBean = getStationBean(isFrom);

            if (stationBean != null)  {
                double value = (Double) stationBean.getProperty(PROP_STATION_VALUE);

                changeSpinner(value, isFrom);
                changeFeature(value, isFrom);

                updateRealGeoms(isFrom);
            }
        } finally {
            lockBeanChange(false, isFrom);
        }
    }

    private void updateSplitButton(boolean isFrom) {
        if (cidsBean != null) {
            final CidsBean stationBean = getStationBean(isFrom);
            if (stationBean != null) {
                final JButton stationButton = (isFrom) ? jButton1 : jButton2;
                stationButton.setVisible(StationToMapRegistry.getInstance().getCounter(stationBean) > 1);
            }
        }
    }

    private void changeSpinner(double value, boolean isFrom) {
        LOG.debug("change spinner");

        if (!isSpinnerChangeLocked(isFrom)) {
            final JSpinner stationSpinner = (isFrom) ? spinFrom : spinTo;
            stationSpinner.setValue(value);
        }
    }

    private void changeFeature(double value, boolean isFrom) {
        if (!isFeatureChangeLocked(isFrom)) {
            final LinearReferencedPointFeature stationFeature = (isFrom) ? fromFeature : toFeature;
            if (stationFeature != null) {
                stationFeature.moveToPosition(value);
            } else {
                LOG.debug("there are no feature to move");
            }
        }
    }

    private void updateRealGeoms(boolean isFrom) {
        LOG.debug("update real geom");

        try {
            final LinearReferencedPointFeature stationFeature = (isFrom) ? fromFeature : toFeature;
            final CidsBean stationBean = getStationBean(isFrom);

            // realgeom der Station anpassen
            if (stationFeature != null) {
             LOG.debug("change station geom");
                final Geometry geometry = stationFeature.getGeometry();
                StationEditor.setPointGeometry(geometry, stationBean);
            }
            // realgeom der Linie anpassen
            if (feature != null) {
                LOG.debug("change line geom");
                setRealGeometry(feature.getGeometry());
            }
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while setting real geoms", ex);
            }
        }
    }
    
    private void changeStationValue(double value, boolean isFrom) {
        LOG.debug("change bean value to " + value);
        final CidsBean stationBean = getStationBean(isFrom);
        final double oldValue = StationEditor.getLinearValue(stationBean);

        if (oldValue != value) {
            try {
                if (!isBeanChangeLocked(isFrom)) {
                    StationEditor.setLinearValue(value, stationBean);
                }
            } catch (Exception ex) {
                LOG.debug("error changing bean", ex);
            }
        } else {
            LOG.debug("no changes needed, old value was " + oldValue);
        }
    }

    private boolean isFeatureChangeLocked(final boolean isFrom) {
        return (isFrom) ? isFromFeatureChangeLocked : isToFeatureChangeLocked;
    }

    private boolean isSpinnerChangeLocked(final boolean isFrom) {
        return (isFrom) ? isFromSpinnerChangeLocked : isToSpinnerChangeLocked;
    }

    private boolean isBeanChangeLocked(final boolean isFrom) {
        return (isFrom) ? isFromBeanChangeLocked : isToBeanChangeLocked;
    }

    private void lockFeatureChange(final boolean lock, final boolean isFrom) {
        if (isFrom) {
            isFromFeatureChangeLocked = lock;
        } else {
            isToFeatureChangeLocked = lock;
        }
    }

    private void lockSpinnerChange(final boolean lock, final boolean isFrom) {
        if (isFrom) {
            isFromSpinnerChangeLocked = lock;
        } else {
            isToSpinnerChangeLocked = lock;
        }
    }

    private void lockBeanChange(final boolean lock, final boolean isFrom) {
        if (isFrom) {
            isFromBeanChangeLocked = lock;
        } else {
            isToBeanChangeLocked = lock;
        }
    }

    private void initCidsBeanListener(final boolean isFrom) {
        final PropertyChangeListener stationBeanListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
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

    private LinearReferencedPointFeature getStationFeature(boolean isFrom) {
        return (isFrom) ? fromFeature : toFeature;
    }

    private Geometry getRouteGeometry() {
        return (Geometry) getRouteGeomBean().getProperty(PROP_GEOM_GEOFIELD);
    }

    private CidsBean getStationBean(boolean isFrom) {
        String stationField = (isFrom) ? fromStationField : toStationField;
        return (CidsBean) cidsBean.getProperty(stationField);
    }

    private CidsBean getRouteBean() {
        return (CidsBean) getStationBean(FROM).getProperty(PROP_STATION_ROUTE);
    }

    private CidsBean getRouteGeomBean() {
        return (CidsBean) getRouteBean().getProperty(PROP_ROUTE_GEOM);
    }

    private CidsBean getRealGeomBean() {
        return (CidsBean) cidsBean.getProperty(realGeomField);
    }

    private void setRealGeometry(final Geometry line) throws Exception {
        getRealGeomBean().setProperty(PROP_GEOM_GEOFIELD, line);
    }

    @Override
    public void setEnabled(boolean bln) {
        super.setEnabled(bln);
        jLabel3.setVisible(bln);
        spinFrom.setEnabled(bln);
        spinTo.setEnabled(bln);
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
        panAdd = new AddPanel();
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

        jLabel1.setText(org.openide.util.NbBundle.getMessage(LinearReferencedLineEditor.class, "LinearReferencedLineEditor.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(LinearReferencedLineEditor.class, "LinearReferencedLineEditor.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(LinearReferencedLineEditor.class, "LinearReferencedLineEditor.jLabel5.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        jLabel6.setText(org.openide.util.NbBundle.getMessage(LinearReferencedLineEditor.class, "LinearReferencedLineEditor.jLabel6.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jLabel6, gridBagConstraints);

        labGwk.setForeground(new java.awt.Color(128, 128, 128));
        labGwk.setText(org.openide.util.NbBundle.getMessage(LinearReferencedLineEditor.class, "LinearReferencedLineEditor.labGwk.text")); // NOI18N
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

        spinFrom.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spinFrom.setMinimumSize(new java.awt.Dimension(100, 28));
        spinFrom.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        panSpinner.add(spinFrom, gridBagConstraints);

        spinTo.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/split-from.png"))); // NOI18N
        jButton1.setText(org.openide.util.NbBundle.getMessage(LinearReferencedLineEditor.class, "LinearReferencedLineEditor.jButton1.text")); // NOI18N
        jButton1.setToolTipText(org.openide.util.NbBundle.getMessage(LinearReferencedLineEditor.class, "LinearReferencedLineEditor.jButton1.toolTipText")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        panSpinner.add(jButton1, gridBagConstraints);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/split-to.png"))); // NOI18N
        jButton2.setText(org.openide.util.NbBundle.getMessage(LinearReferencedLineEditor.class, "LinearReferencedLineEditor.jButton2.text")); // NOI18N
        jButton2.setToolTipText(org.openide.util.NbBundle.getMessage(LinearReferencedLineEditor.class, "LinearReferencedLineEditor.jButton2.toolTipText")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
        jLabel3.setText(org.openide.util.NbBundle.getMessage(LinearReferencedLineEditor.class, "LinearReferencedLineEditor.jLabel3.text")); // NOI18N
        panAdd.add(jLabel3, new java.awt.GridBagConstraints());

        add(panAdd, "add");

        panError.setOpaque(false);
        panError.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText(org.openide.util.NbBundle.getMessage(LinearReferencedLineEditor.class, "LinearReferencedLineEditor.jLabel4.text")); // NOI18N
        panError.add(jLabel4, new java.awt.GridBagConstraints());

        add(panError, "error");
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        splitStation(FROM);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        splitStation(TO);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void splitStation(boolean isFrom) {
        double oldPosition = getStationFeature(isFrom).getCurrentPosition();

        CidsBean stationBean = StationEditor.createFromRoute(StationEditor.getRouteBean(getStationBean(isFrom)));       
        setStationBean(stationBean, isFrom);
        stationBeanChanged(isFrom);
        
        // neue station auf selbe position setzen wie die alte
        LinearReferencedPointFeature stationFeature = (isFrom) ? fromFeature : toFeature;
        stationFeature.moveToPosition(oldPosition);

        getFeature().setPointFeature(getStationFeature(isFrom), isFrom);
    }

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

    class AddPanel extends JPanel implements CidsBeanDropListener {

        @Override
        public void beansDropped(ArrayList<CidsBean> beans) {
            if (isEnabled()) {
                CidsBean routeBean = null;
                for (CidsBean bean : beans) {
                    if (bean.getMetaObject().getMetaClass().getName().equals(MC_ROUTE)) {
                        routeBean = bean;
                        break;
                    }
                }
                if (routeBean != null) {
                    if (cidsBean == null) {
                        cidsBean = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, metaClassName).getEmptyInstance().getBean();
                    }
                    fillFromRoute(cidsBean, routeBean);
                    setCidsBean(cidsBean);
                } else {
                    LOG.debug("no route found in dropped objects");
                }
            }
        }
    }
}
