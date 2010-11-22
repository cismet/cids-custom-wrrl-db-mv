package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import de.cismet.cids.custom.util.StationToMapRegistry;
import Sirius.server.middleware.types.MetaClass;
import com.vividsolutions.jts.geom.Geometry;
import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.LinearReferencingConstants;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;
import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;
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

    private LinearReferencedPointFeature feature;
    private PropertyChangeListener cidsBeanListener;
    private boolean isSpinnerChangeLocked = false;
    private boolean isFeatureChangeLocked = false;
    private ImageIcon ico;
    private CidsBean cidsBean;
    private Collection<StationEditorListener> listeners = new ArrayList<StationEditorListener>();

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
    }

    public boolean addStationEditorListener(StationEditorListener listener) {
        return listeners.add(listener);
    }

    public void setImageIcon(ImageIcon ico) {
        this.ico = ico;
        if (feature != null) {
            feature.setIconImage(ico);
        }
        jLabel5.setIcon(ico);
    }

    private void fireStationCreated() {
        for (StationEditorListener listener : listeners) {
            listener.stationCreated();
        }
    }    

    public LinearReferencedPointFeature getFeature() {
        return feature;
    }

    private void initCidsBeanListener() {
        cidsBeanListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (pce.getPropertyName().equals(PROP_STATION_VALUE)) {
                    double position = (Double) cidsBean.getProperty(PROP_STATION_VALUE);

                    if (!isSpinnerChangeLocked) {
                        spinValue.setValue((double) Math.round(position));
                    }

                    if (feature != null) {
                        if (!isFeatureChangeLocked) {
                            feature.moveToPosition(position);
                        }
                        try {
                            StationEditor.setPointGeometry(feature.getGeometry(), cidsBean);
                        } catch (Exception ex) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("error while setting the " + PROP_STATION_GEOM + "property", ex);
                            }
                        }
                    }
                }
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
        try {
            // endlosschleife verhindern (bean => spinner => bean => ...)
            isSpinnerChangeLocked = true;

            final Double oldValue = (Double) cidsBean.getProperty(PROP_STATION_VALUE);

            Double value;
            try {
                AbstractFormatter formatter = ((JSpinner.DefaultEditor) spinValue.getEditor()).getTextField().getFormatter();
                value = (Double) formatter.stringToValue(((JSpinner.DefaultEditor) spinValue.getEditor()).getTextField().getText());
            } catch (ParseException ex) {
                value = oldValue;
            }

            if (((oldValue == null) && (value != null)) || ((oldValue != null) && !oldValue.equals(value))) {
                cidsBean.setProperty(PROP_STATION_VALUE, value);
            }
        } catch(Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error during setting CidsBean", ex);
            }
        } finally {
            isSpinnerChangeLocked = false;
        }
    }

    @Override
    public void setCidsBean(CidsBean cidsBean) {
        //listener von der alten Bean entfernen
        if (this.cidsBean != null) {
            this.cidsBean.removePropertyChangeListener(cidsBeanListener);
        }

        if (cidsBean == null) {
            cidsBean = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_STATION).getEmptyInstance().getBean();
        }

        this.cidsBean = cidsBean;

        init();
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    private void init() {
        try {
            if (getRouteBean(cidsBean) == null) {
                showCard(Card.add);
            } else {
                labGwk.setText("Route: " + Long.toString(getRouteGwk(cidsBean)));

                cidsBean.addPropertyChangeListener(cidsBeanListener);
                cidsBean.setProperty(PROP_STATION_VALUE, cidsBean.getProperty(PROP_STATION_VALUE));

                // neues Feature erzeugen
                feature = null;
                initFeature(cidsBean);

                ((SpinnerNumberModel) spinValue.getModel()).setMaximum(feature.getLineGeometry().getLength());

                showCard(Card.edit);
            }
        } catch (Exception ex) {
            LOG.fatal("error while init StationEditor", ex);
            showCard(Card.error);
        }
    }

    private void initFeature(final CidsBean cidsBean) {
        StationToMapRegistry.getInstance().addRouteFeature(
            getId(getRouteBean(cidsBean)),
            getRouteGeometry(cidsBean)
        );

        feature = StationToMapRegistry.getInstance().addStationFeature(
            getId(cidsBean),
            getLinearValue(cidsBean),
            getRouteGeometry(cidsBean)
        );
        if (ico != null) {
            feature.setIconImage(ico);
        }

        feature.addListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (pce.getPropertyName().equals(LinearReferencedPointFeature.PROPERTY_FEATURE_COORDINATE)) {
                    // endlosschleife verhindern (feature => bean => feature => ...)
                    try {
                        isFeatureChangeLocked = true;
                        double currentPosition = feature.getCurrentPosition();
                        setLinearValue(currentPosition, cidsBean);
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("", ex);
                        }
                    } finally {
                        isFeatureChangeLocked = false;
                    }
                }
            }
        });

        fireStationCreated();
    }

    private void fillFromRoute(CidsBean routeBean) {
        MetaClass geomMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_GEOM);
        CidsBean geomBean = geomMC.getEmptyInstance().getBean();

        try {
            cidsBean.setProperty(PROP_ID, StationToMapRegistry.getNewId(MC_STATION));
            cidsBean.setProperty(PROP_STATION_ROUTE, routeBean);
            cidsBean.setProperty(PROP_STATION_VALUE, 0d);
            cidsBean.setProperty(PROP_STATION_GEOM, geomBean);
        } catch (Exception ex) {
            LOG.debug("Error while filling bean", ex);
        }
    }

    @Override
    public void dispose() {
        if (cidsBean != null) {
            try {
                StationToMapRegistry.getInstance().removeStationFeature(getId(cidsBean));
            } catch (Exception ex) {
                LOG.debug("exception while removing stationFeature from map", ex);
            }
            try {
                StationToMapRegistry.getInstance().removeRouteFeature(getId(getRouteBean(cidsBean)));
            } catch (Exception ex) {
                LOG.debug("exception while removing stationFeature from map", ex);
            }
            try {
                cidsBean.removePropertyChangeListener(cidsBeanListener);
            } catch (Exception ex) {
                LOG.debug("exception while removing Changelistener", ex);
            }
        }
    }

    private static int getId(CidsBean cidsBean) {
        return (Integer) cidsBean.getProperty(PROP_ID);
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        panEdit.add(labGwk, gridBagConstraints);

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel labGwk;
    private javax.swing.JPanel panAdd;
    private javax.swing.JPanel panEdit;
    private javax.swing.JPanel panError;
    private javax.swing.JSpinner spinValue;
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
                fillFromRoute(routeBean);
                init();
            } else {
                LOG.debug("no route found in dropped objects");
            }
        }
    }

}
