package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.tree.MetaCatalogueTree;
import de.cismet.cids.custom.util.StationToMapRegistry;
import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaClassStore;
import Sirius.server.middleware.types.MetaObjectNode;
import com.vividsolutions.jts.geom.Geometry;
import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;
import de.cismet.cismap.navigatorplugin.CidsFeature;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
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
    private static final String STATION_REAL_GEOM_BEAN = "real_point";    // NOI18N
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
    private static int NEW_WKTEIL = 0;
    private static int NEW_STATION = 0;


    /** Creates new form WkTeilEditor */
    public WkTeilEditor() {
        initComponents();

        initCidsBeanListener(FROM);
        initCidsBeanListener(TO);
        initSpinnerListener();
    }

    public static WkTeilEditor createFromRoute(CidsBean routeBean) {
        MetaClass wkTeilMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "WK_TEIL");
        MetaClass stationMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "STATION");
        MetaClass geomMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "GEOM");

        CidsBean wkTeilBean = wkTeilMC.getEmptyInstance().getBean();
        CidsBean fromBean = stationMC.getEmptyInstance().getBean();
        CidsBean toBean = stationMC.getEmptyInstance().getBean();
        CidsBean geomBean = geomMC.getEmptyInstance().getBean();
        CidsBean fromGeomBean = geomMC.getEmptyInstance().getBean();
        CidsBean toGeomBean = geomMC.getEmptyInstance().getBean();

        try {
            fromBean.setProperty(ID, --NEW_STATION);
            fromBean.setProperty(ROUTE_BEAN, routeBean);
            fromBean.setProperty(LINEAR_VALUE, 0d);
            fromBean.setProperty(STATION_REAL_GEOM_BEAN, fromGeomBean);

            toBean.setProperty(ID, --NEW_STATION);
            toBean.setProperty(ROUTE_BEAN, routeBean);
            toBean.setProperty(LINEAR_VALUE, 0d);
            toBean.setProperty(LINEAR_VALUE, ((Geometry) ((CidsBean) routeBean.getProperty(ROUTE_GEOM_BEAN)).getProperty(GEOM_FIELD)).getLength());
            toBean.setProperty(STATION_REAL_GEOM_BEAN, toGeomBean);

            wkTeilBean.setProperty(ID, --NEW_WKTEIL);
            wkTeilBean.setProperty(FROM_STATION_BEAN, fromBean);
            wkTeilBean.setProperty(TO_STATION_BEAN, toBean);
            wkTeilBean.setProperty(REAL_GEOM_BEAN, geomBean);
        } catch (Exception ex) {
                LOG.debug("Error while creating wkteil bean", ex);
        }

        WkTeilEditor editor = new WkTeilEditor();
        editor.setCidsBean(wkTeilBean);
        return editor;
    }

    public void setLineColor(Color paint) {
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
    public void setCidsBean(CidsBean cidsBean) {
        this.cidsBean = cidsBean;

        if (!isFeatureInited) {
            // cidsFeature rausschmeissen
            MetaCatalogueTree metaCatalogueTree = ComponentRegistry.getRegistry().getCatalogueTree();
            Collection<Feature> features = new ArrayList<Feature>();
            features.add(new CidsFeature((MetaObjectNode) metaCatalogueTree.getSelectedNode().getNode()));
            CismapBroker.getInstance().getMappingComponent().getFeatureCollection().removeFeatures(features);

            // Feature erzeugen
            initFeature(cidsBean);
        }
        getFromStationBean(cidsBean).addPropertyChangeListener(fromCidsBeanListener);
        getToStationBean(cidsBean).addPropertyChangeListener(toCidsBeanListener);

        cidsBeanChanged(FROM);
        cidsBeanChanged(TO);

        ((SpinnerNumberModel) spinFrom.getModel()).setMaximum(getRouteGeometry(cidsBean).getLength());
        ((SpinnerNumberModel) spinTo.getModel()).setMaximum(getRouteGeometry(cidsBean).getLength());

        labGwk.setText("Route: " + Long.toString(StationEditor.getRouteGwk(getFromStationBean(cidsBean))));
        panLine.setBackground(LinearReferencedLineFeature.DEFAULT_COLOR);
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

        initFeatureListener(FROM);
        initFeatureListener(TO);

        isFeatureInited = true;
    }

    private void initSpinnerListener() {
        ((JSpinner.DefaultEditor) spinFrom.getEditor()).getTextField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                spinnerChanged(IFROM);
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

    private void featureChanged(boolean isFrom) {
        try {
            lockFeatureChange(true, isFrom);

            final LinearReferencedPointFeature linearRefFeature = (isFrom) ? fromFeature : toFeature;
            final JSpinner spinner = (isFrom) ? spinFrom: spinTo;

            final double value = Math.round(linearRefFeature.getCurrentPosition() * 100) / 100d;
            if (!isSpinnerChangeLocked(isFrom)) {
                spinner.setValue((double) Math.round(value));
            }
            try {
                final CidsBean stationBean = (isFrom) ? getFromStationBean(cidsBean) : getToStationBean(cidsBean);
                final Geometry geometry = (isFrom) ? fromFeature.getGeometry() : toFeature.getGeometry();

                // jeweilige Station anpassen
                StationEditor.setLinearValue(value, stationBean);
                StationEditor.setPointGeometry(geometry, stationBean);

                // "real"-Geometry anpassen
                setRealGeometry(feature.getGeometry(), cidsBean);
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while setting bean", ex);
                }
            }
        } finally {
            lockFeatureChange(false, isFrom);
        }
    }

    private void spinnerChanged(boolean isFrom) {
        try {
            lockSpinnerChange(true, isFrom);

            if (cidsBean == null) {
                cidsBean = metaClass.getEmptyInstance().getBean();
            }

            final Double oldValue = (isFrom) ? StationEditor.getLinearValue(getFromStationBean(cidsBean)) : StationEditor.getLinearValue(getToStationBean(cidsBean));

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

    private boolean isFeatureChangeLocked(boolean isFrom) {
        return (isFrom) ? isFromFeatureChangeLocked : isToFeatureChangeLocked;
    }

    private boolean isSpinnerChangeLocked(boolean isFrom) {
        return (isFrom) ? isFromSpinnerChangeLocked : isToSpinnerChangeLocked;
    }

    private void lockFeatureChange(boolean lock, boolean isFrom) {
        if (isFrom) {
            isFromFeatureChangeLocked = lock;
        } else {
            isToFeatureChangeLocked = lock;
        }
    }

    private void lockSpinnerChange(boolean lock, boolean isFrom) {
        if (isFrom) {
            isFromSpinnerChangeLocked = lock;
        } else {
            isToSpinnerChangeLocked = lock;
        }
    }

    private void initCidsBeanListener(final boolean isFrom) {
        PropertyChangeListener cidsBeanListener = new PropertyChangeListener() {

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

    private void cidsBeanChanged(boolean isFrom) {
        LinearReferencedPointFeature feature = (isFrom) ? fromFeature : toFeature;
        JSpinner spinValue = (isFrom) ? spinFrom : spinTo;
        CidsBean stationBean = (isFrom) ? getFromStationBean(cidsBean) : getToStationBean(cidsBean);

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

    public static CidsBean getRouteBean(CidsBean cidsBean) {
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
    private javax.swing.JLabel labGwk;
    private javax.swing.JPanel panLine;
    private javax.swing.JSpinner spinFrom;
    private javax.swing.JSpinner spinTo;
    // End of variables declaration//GEN-END:variables

}
