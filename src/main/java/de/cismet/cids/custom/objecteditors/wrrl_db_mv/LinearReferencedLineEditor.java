package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import de.cismet.cids.custom.util.StationToMapRegistry;
import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaClassStore;
import com.vividsolutions.jts.geom.Geometry;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author jruiz
 */
public class LinearReferencedLineEditor extends DefaultCustomObjectEditor implements MetaClassStore {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LinearReferencedLineEditor.class);

    private static final String ID = "id";    // NOI18N
    private static final String ROUTE_BEAN = "route";    // NOI18N
    private static final String ROUTE_GEOM_BEAN = "geom";    // NOI18N
    private static final String LINEAR_VALUE = "wert";    // NOI18N
    private static final String GEOM_FIELD = "geo_field";    // NOI18N
    private static final boolean FROM = true;
    private static final boolean TO = false;

    private LinearReferencedLineFeature feature;
    private LinearReferencedPointFeature fromFeature;
    private LinearReferencedPointFeature toFeature;
    private boolean isFeatureInited = false;
    private MetaClass metaClass;
    private boolean isFromSpinnerChangeLocked = false;
    private boolean isFromFeatureChangeLocked = false;
    private boolean isToSpinnerChangeLocked = false;
    private boolean isToFeatureChangeLocked = false;
    private PropertyChangeListener fromCidsBeanListener;
    private PropertyChangeListener toCidsBeanListener;

    private String fromStationField;
    private String toStationField;
    private String realGeomField;
    private String metaClassName;

    /** Creates new form LinearReferencedLineEditor */
    public LinearReferencedLineEditor() {
        initComponents();

        initCidsBeanListener(FROM);
        initCidsBeanListener(TO);
        initSpinnerListener();
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

    public void setLineColor(final Color paint) {
        feature.setLinePaint(paint);
        feature.updateGeometry();
        panLine.setBackground(paint);
    }

    public LinearReferencedLineFeature getFeature() {
        return feature;
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;

        if (!isFeatureInited) {
            // Feature erzeugen
            initFeature();
        }
        getFromStationBean().addPropertyChangeListener(fromCidsBeanListener);
        getToStationBean().addPropertyChangeListener(toCidsBeanListener);

        cidsBeanChanged(FROM);
        cidsBeanChanged(TO);

        ((SpinnerNumberModel) spinFrom.getModel()).setMaximum(getRouteGeometry().getLength());
        ((SpinnerNumberModel) spinTo.getModel()).setMaximum(getRouteGeometry().getLength());

        labGwk.setText("Route: " + Long.toString(StationEditor.getRouteGwk(getFromStationBean())));
        panLine.setBackground(LinearReferencedLineFeature.DEFAULT_COLOR);
    }

    @Override
    public void dispose() {
        if (cidsBean != null) {
            StationToMapRegistry.getInstance().removeLinearReferencedLineFeature(metaClassName, getId());
            StationToMapRegistry.getInstance().removeStationFeature(getFromStationId());
            StationToMapRegistry.getInstance().removeStationFeature(getToStationId());
            StationToMapRegistry.getInstance().removeRouteFeature(getRouteId());
            getFromStationBean().removePropertyChangeListener(fromCidsBeanListener);
            getToStationBean().removePropertyChangeListener(toCidsBeanListener);
        }
    }

    private void initFeature() {
        final CidsBean fromBean = getFromStationBean();
        final CidsBean toBean = getToStationBean();

        fromFeature = StationToMapRegistry.getInstance().addStationFeature(
            getFromStationId(),
            StationEditor.getLinearValue(fromBean),
            StationEditor.getRouteGeometry(fromBean)
        );

        toFeature = StationToMapRegistry.getInstance().addStationFeature(
            getToStationId(),
            StationEditor.getLinearValue(toBean),
            StationEditor.getRouteGeometry(toBean)
        );

        feature = StationToMapRegistry.getInstance().addLinearReferencedLineFeature(
            metaClassName,
            getId(),
            fromFeature,
            toFeature
        );

        StationToMapRegistry.getInstance().addRouteFeature(
            getRouteId(),
            getRouteGeometry()
        );

        initFeatureListener(FROM);
        initFeatureListener(TO);

        isFeatureInited = true;
    }

    private void initSpinnerListener() {
        ((JSpinner.DefaultEditor) spinFrom.getEditor()).getTextField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                spinnerChanged(FROM);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                spinnerChanged(FROM);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                spinnerChanged(FROM);
            }
        });

        ((JSpinner.DefaultEditor) spinTo.getEditor()).getTextField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                spinnerChanged(TO);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                spinnerChanged(TO);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                spinnerChanged(TO);
            }
        });
    }

    private void initFeatureListener(final boolean isFrom) {
        final LinearReferencedPointFeature linearRefFeature = (isFrom) ? fromFeature : toFeature;
        linearRefFeature.addListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (pce.getPropertyName().equals(LinearReferencedPointFeature.PROPERTY_FEATURE_COORDINATE)) {
                    featureChanged(isFrom);
                }
            }
        });
    }

    private void featureChanged(final boolean isFrom) {
        try {
            lockFeatureChange(true, isFrom);

            final LinearReferencedPointFeature linearRefFeature = (isFrom) ? fromFeature : toFeature;
            final JSpinner spinner = (isFrom) ? spinFrom: spinTo;

            final double value = Math.round(linearRefFeature.getCurrentPosition() * 100) / 100d;
            if (!isSpinnerChangeLocked(isFrom)) {
                spinner.setValue((double) Math.round(value));
            }
            try {
                final CidsBean stationBean = (isFrom) ? getFromStationBean() : getToStationBean();
                final Geometry geometry = (isFrom) ? fromFeature.getGeometry() : toFeature.getGeometry();

                // jeweilige Station anpassen
                StationEditor.setLinearValue(value, stationBean);
                StationEditor.setPointGeometry(geometry, stationBean);

                // Real-Geometrien anpassen
                setRealGeometry(feature.getGeometry());
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while setting bean", ex);
                }
            }
        } finally {
            lockFeatureChange(false, isFrom);
        }
    }

    private void spinnerChanged(final boolean isFrom) {
        try {
            lockSpinnerChange(true, isFrom);

            if (cidsBean == null) {
                cidsBean = metaClass.getEmptyInstance().getBean();
            }

            final Double oldValue = (isFrom) ? StationEditor.getLinearValue(getFromStationBean()) : StationEditor.getLinearValue(getToStationBean());

            Double value;
            try {
                if (isFrom) {
                    AbstractFormatter formatter = ((JSpinner.DefaultEditor) spinFrom.getEditor()).getTextField().getFormatter();
                    value = (Double) formatter.stringToValue(((JSpinner.DefaultEditor) spinFrom.getEditor()).getTextField().getText());
                } else {
                    AbstractFormatter formatter = ((JSpinner.DefaultEditor) spinTo.getEditor()).getTextField().getFormatter();
                    value = (Double) formatter.stringToValue(((JSpinner.DefaultEditor) spinTo.getEditor()).getTextField().getText());
                }
            } catch (ParseException ex) {
                value = oldValue;
            }

            if (((oldValue == null) && (value != null)) || ((oldValue != null) && !oldValue.equals(value))) {
                LinearReferencedPointFeature fromOrToFeature = (isFrom) ? fromFeature : toFeature;
                if (fromOrToFeature != null) {
                    if (!isFeatureChangeLocked(isFrom)) {
                        fromOrToFeature.moveToPosition(value);
                    }
                }
            }
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error during setting CidsBean", ex); // NOI18N
            }
        } finally {
            lockSpinnerChange(false, isFrom);
        }
    }

    private boolean isFeatureChangeLocked(final boolean isFrom) {
        return (isFrom) ? isFromFeatureChangeLocked : isToFeatureChangeLocked;
    }

    private boolean isSpinnerChangeLocked(final boolean isFrom) {
        return (isFrom) ? isFromSpinnerChangeLocked : isToSpinnerChangeLocked;
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

    private void initCidsBeanListener(final boolean isFrom) {
        final PropertyChangeListener cidsBeanListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (pce.getPropertyName().equals(LINEAR_VALUE)) {
                    cidsBeanChanged(isFrom);
                }
            }
        };

        if (isFrom) {
            fromCidsBeanListener = cidsBeanListener;
        } else {
            toCidsBeanListener = cidsBeanListener;
        }
    }

    private void cidsBeanChanged(final boolean isFrom) {
        final LinearReferencedPointFeature feature = (isFrom) ? fromFeature : toFeature;
        final JSpinner spinValue = (isFrom) ? spinFrom : spinTo;
        final CidsBean stationBean = (isFrom) ? getFromStationBean() : getToStationBean();

        if (stationBean != null)  {
            double position = (Double) stationBean.getProperty(LINEAR_VALUE);

            if (!isSpinnerChangeLocked(isFrom)) {
                spinValue.setValue((double) Math.round(position));
            }

            if (feature != null) {
                if (!isFeatureChangeLocked(isFrom)) {
                    feature.moveToPosition(position);
                }
                try {
                    StationEditor.setPointGeometry(feature.getGeometry(), stationBean);
                } catch (Exception ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("error while setting property", ex);
                    }
                }
            }
        }
    }

    private int getId() {
        return (Integer) cidsBean.getProperty(ID);
    }

    private int getFromStationId() {
        return (Integer) getFromStationBean().getProperty(ID);
    }

    private int getToStationId() {
        return (Integer) getToStationBean().getProperty(ID);
    }

    private int getRouteId() {
        return (Integer) getRouteBean().getProperty(ID);
    }

    private Geometry getRouteGeometry() {
        return (Geometry) getRouteGeomBean().getProperty(GEOM_FIELD);
    }

    private CidsBean getFromStationBean() {
        return (CidsBean) cidsBean.getProperty(fromStationField);
    }

    private CidsBean getToStationBean() {
        return (CidsBean) cidsBean.getProperty(toStationField);
    }

    private CidsBean getRouteBean() {
        return (CidsBean) getFromStationBean().getProperty(ROUTE_BEAN);
    }

    private CidsBean getRouteGeomBean() {
        return (CidsBean) getRouteBean().getProperty(ROUTE_GEOM_BEAN);
    }

    private CidsBean getRealGeomBean() {
        return (CidsBean) cidsBean.getProperty(realGeomField);
    }

    private void setRealGeometry(final Geometry line) throws Exception {
        getRealGeomBean().setProperty(GEOM_FIELD, line);
    }

    @Override
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    @Override
    public MetaClass getMetaClass() {
        return metaClass;
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

        spinFrom = new javax.swing.JSpinner();
        spinTo = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        panLine = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labGwk = new javax.swing.JLabel();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        spinFrom.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spinFrom.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(spinFrom, gridBagConstraints);

        spinTo.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        spinTo.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(spinTo, gridBagConstraints);

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
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labGwk;
    private javax.swing.JPanel panLine;
    private javax.swing.JSpinner spinFrom;
    private javax.swing.JSpinner spinTo;
    // End of variables declaration//GEN-END:variables

}
