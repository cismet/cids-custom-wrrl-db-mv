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
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JSpinner;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author jruiz
 */
public class WkTeilEditor extends DefaultCustomObjectEditor implements MetaClassStore {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkTeilEditor.class);

    private static final String ID = "id";    // NOI18N
    private static final String ROUTE_BEAN = "route";    // NOI18N
    private static final String ROUTE_GEOM_BEAN = "geom";    // NOI18N
    private static final String FROM_STATION_BEAN = "von";    // NOI18N
    private static final String TO_STATION_BEAN = "bis";    // NOI18N
    private static final String LINEAR_VALUE = "wert";    // NOI18N
    private static final String GEOM_FIELD = "geo_field";    // NOI18N
    private static final String REAL_GEOM_BEAN = "real_geom";    // NOI18N

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


    /** Creates new form WkTeilEditor */
    public WkTeilEditor() {
        initComponents();

        initCidsBeanListener(true);
        initCidsBeanListener(false);
        initSpinnerListener();

    }

    public void setLineColor(Color paint) {
        feature.setLinePaint(paint);
        feature.updateGeometry();
        jPanel2.setBackground(paint);
    }

    public LinearReferencedLineFeature getFeature() {
        return feature;
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(CidsBean cidsBean) {
        this.cidsBean = cidsBean;

        // Feature erzeugen
        if (!isFeatureInited) {
            initFeature(cidsBean);
        }
        getFromStationBean(cidsBean).addPropertyChangeListener(fromCidsBeanListener);
        getToStationBean(cidsBean).addPropertyChangeListener(toCidsBeanListener);
        try {
            getFromStationBean(cidsBean).setProperty(LINEAR_VALUE, getFromStationBean(cidsBean).getProperty(LINEAR_VALUE));
            getToStationBean(cidsBean).setProperty(LINEAR_VALUE, getToStationBean(cidsBean).getProperty(LINEAR_VALUE));
        } catch (Exception ex) {
        }

        labGwk.setText("Route: " + Long.toString(StationEditor.getRouteGwk(getFromStationBean(cidsBean))));
        jPanel2.setBackground(LinearReferencedLineFeature.DEFAULT_COLOR);
    }

    @Override
    public void dispose() {
        if (cidsBean != null) {
            StationToMapRegistry.getInstance().removeWkTeilFeature(getId(cidsBean));
            StationToMapRegistry.getInstance().removeStationFeature(getId(getFromStationBean(cidsBean)));
            StationToMapRegistry.getInstance().removeStationFeature(getId(getToStationBean(cidsBean)));
            StationToMapRegistry.getInstance().removeRouteFeature(getId(getRouteBean(cidsBean)));
            getFromStationBean(cidsBean).removePropertyChangeListener(fromCidsBeanListener);
            getToStationBean(cidsBean).removePropertyChangeListener(toCidsBeanListener);
        }
    }

    private void initFeature(final CidsBean cidsBean) {
        CidsBean fromBean = getFromStationBean(cidsBean);
        CidsBean toBean = getToStationBean(cidsBean);

        fromFeature = StationToMapRegistry.getInstance().addStationFeature(
            getId(fromBean),
            StationEditor.getLinearValue(fromBean),
            StationEditor.getRouteGeometry(fromBean)
        );

        toFeature = StationToMapRegistry.getInstance().addStationFeature(
            getId(toBean),
            StationEditor.getLinearValue(toBean),
            StationEditor.getRouteGeometry(toBean)
        );

        feature = StationToMapRegistry.getInstance().addWkTeilFeature(
            getId(cidsBean),
            fromFeature,
            toFeature
        );

        StationToMapRegistry.getInstance().addRouteFeature(
            getId(getRouteBean(cidsBean)),
            getRouteGeometry(cidsBean)
        );

        listenToFeature(fromFeature, spinFrom, true);
        listenToFeature(toFeature, spinTo, false);

        isFeatureInited = true;
    }

    private void initSpinnerListener() {
        ((JSpinner.DefaultEditor) spinFrom.getEditor()).getTextField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                spinnerChanged(true);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                spinnerChanged(true);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                spinnerChanged(true);
            }
        });

        ((JSpinner.DefaultEditor) spinTo.getEditor()).getTextField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                spinnerChanged(false);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                spinnerChanged(false);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                spinnerChanged(false);
            }
        });
    }

    private void listenToFeature(final LinearReferencedPointFeature linearRefFeature, final JSpinner spinner, final boolean isFrom) {
        linearRefFeature.addListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (pce.getPropertyName().equals(LinearReferencedPointFeature.PROPERTY_FEATURE_COORDINATE)) {
                    if (isFrom) {
                        isFromFeatureChangeLocked = true;
                    } else {
                        isToFeatureChangeLocked = true;
                    }
                    if ((isFrom && !isFromSpinnerChangeLocked) || (!isFrom && !isToSpinnerChangeLocked)) {
                        try {
                            double currentPosition = Math.round(linearRefFeature.getCurrentPosition() * 100) / 100d;
                            spinner.setValue((double) Math.round(currentPosition));
                        } catch (Exception ex) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("", ex);
                            }
                        }
                    }
                    if (isFrom) {
                        isFromFeatureChangeLocked = false;
                    } else {
                        isToFeatureChangeLocked = false;
                    }
                }
            }
        });
    }

    private void spinnerChanged(boolean isFrom) {
        // endlosschleife verhindern (bean => spinner => bean => ...)
        if (isFrom) {
            isFromSpinnerChangeLocked = true;
        } else {
            isToSpinnerChangeLocked = true;
        }

        try {
            Double value = null;
            if (isFrom) {
                AbstractFormatter formatter = ((JSpinner.DefaultEditor) spinFrom.getEditor()).getTextField().getFormatter();
                value = (Double) formatter.stringToValue(((JSpinner.DefaultEditor) spinFrom.getEditor()).getTextField().getText());
            } else {
                AbstractFormatter formatter = ((JSpinner.DefaultEditor) spinTo.getEditor()).getTextField().getFormatter();
                value = (Double) formatter.stringToValue(((JSpinner.DefaultEditor) spinTo.getEditor()).getTextField().getText());
            }

            if (cidsBean == null) {
                cidsBean = metaClass.getEmptyInstance().getBean();
            }

            CidsBean stationBean = null;
            
            if (isFrom) {
                stationBean = getFromStationBean(cidsBean);
            } else {
                stationBean = getToStationBean(cidsBean);
            }

            final Double oldValue = (Double) stationBean.getProperty(LINEAR_VALUE);

            if (((oldValue == null) && (value != null)) || ((oldValue != null) && !oldValue.equals(value))) {
                // Bean Properties setzen
                stationBean.setProperty(LINEAR_VALUE, value);
                if (isFrom) {
                    if (fromFeature != null) {
                        StationEditor.setPointGeometry(fromFeature.getGeometry(), stationBean);
                        fromFeature.moveToPosition(value);
                    }
                } else {
                    if (toFeature != null) {
                        StationEditor.setPointGeometry(toFeature.getGeometry(), stationBean);
                        toFeature.moveToPosition(value);
                    }
                }
                setRealGeometry(feature.getGeometry(), cidsBean);
            }
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error during setting CidsBean", ex); // NOI18N
            }
        }

        if (isFrom) {
            isFromSpinnerChangeLocked = false;
        } else {
            isToSpinnerChangeLocked = false;
        }
    }

    private void initCidsBeanListener(final boolean isFrom) {
        PropertyChangeListener cidsBeanListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (pce.getPropertyName().equals(LINEAR_VALUE)) {

                    LinearReferencedPointFeature feature = null;
                    JSpinner spinValue = null;
                    CidsBean stationBean = null;
                    if (isFrom) {
                        feature = fromFeature;
                        spinValue = spinFrom;
                        stationBean = getFromStationBean(cidsBean);
                    } else {
                        feature = toFeature;
                        spinValue = spinTo;
                        stationBean = getToStationBean(cidsBean);
                    }


                    if (stationBean != null)  {
                        double position = (Double) stationBean.getProperty(LINEAR_VALUE);

                        if ((isFrom && !isFromSpinnerChangeLocked) || (!isFrom && !isToSpinnerChangeLocked)) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("spinValue.setValue: " + isFrom);
                            }
                            spinValue.setValue((double) Math.round(position));
                        }

                        if (feature != null) {
                            if ((isFrom && !isFromFeatureChangeLocked) || (!isFrom && !isToFeatureChangeLocked)) {
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
            }
        };

        if (isFrom) {
            fromCidsBeanListener = cidsBeanListener;
        } else {
            toCidsBeanListener = cidsBeanListener;
        }
    }

    private static int getId(CidsBean cidsBean) {
        return (Integer) cidsBean.getProperty(ID);
    }

    private static Geometry getRouteGeometry(CidsBean cidsBean) {
        return (Geometry) getRouteGeomBean(cidsBean).getProperty(GEOM_FIELD);
    }

    private static CidsBean getFromStationBean(CidsBean cidsBean) {
        return (CidsBean) cidsBean.getProperty(FROM_STATION_BEAN);
    }

    private static CidsBean getToStationBean(CidsBean cidsBean) {
        return (CidsBean) cidsBean.getProperty(TO_STATION_BEAN);
    }

    private static CidsBean getRouteBean(CidsBean cidsBean) {
        return (CidsBean) getFromStationBean(cidsBean).getProperty(ROUTE_BEAN);
    }

    private static CidsBean getRouteGeomBean(CidsBean cidsBean) {
        return (CidsBean) getRouteBean(cidsBean).getProperty(ROUTE_GEOM_BEAN);
    }

    private static CidsBean getRealGeomBean(CidsBean cidsBean) {
        return (CidsBean) cidsBean.getProperty(REAL_GEOM_BEAN);
    }

    private static void setRealGeometry(Geometry line, CidsBean cidsBean) throws Exception {
        getRealGeomBean(cidsBean).setProperty(GEOM_FIELD, line);
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
        jPanel2 = new javax.swing.JPanel();
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

        jPanel2.setBackground(new java.awt.Color(255, 91, 0));
        jPanel2.setMinimumSize(new java.awt.Dimension(10, 4));
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 4));
        jPanel2.setLayout(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(WkTeilEditor.class, "WkTeilEditor.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(WkTeilEditor.class, "WkTeilEditor.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(WkTeilEditor.class, "WkTeilEditor.jLabel5.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        jLabel6.setText(org.openide.util.NbBundle.getMessage(WkTeilEditor.class, "WkTeilEditor.jLabel6.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jLabel6, gridBagConstraints);

        labGwk.setForeground(new java.awt.Color(128, 128, 128));
        labGwk.setText(org.openide.util.NbBundle.getMessage(WkTeilEditor.class, "WkTeilEditor.labGwk.text")); // NOI18N
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labGwk;
    private javax.swing.JSpinner spinFrom;
    private javax.swing.JSpinner spinTo;
    // End of variables declaration//GEN-END:variables

}
