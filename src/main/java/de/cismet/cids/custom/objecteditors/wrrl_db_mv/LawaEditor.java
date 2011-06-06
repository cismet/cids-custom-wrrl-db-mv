/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * WkFgEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.search.CidsServerSearch;

import com.vividsolutions.jts.geom.Geometry;

import java.awt.Color;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.cismet.cids.custom.featurerenderer.wrrl_db_mv.LawaFeatureRenderer;
import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.LawaTypeNeighbourSearch;
import de.cismet.cids.custom.util.MapUtil;
import de.cismet.cids.custom.util.WkkSearch;
import de.cismet.cids.custom.util.linearreferencing.LinearReferencingConstants;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureGroup;
import de.cismet.cismap.commons.features.FeatureGroups;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.CidsFeature;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class LawaEditor extends JPanel implements CidsBeanRenderer,
    PropertyChangeListener,
    EditorSaveListener,
    FooterComponentProvider,
    CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LawaEditor.class);
    private static final String ROUTE_FEATURE_CLASS_NAME =
        "de.cismet.cids.custom.util.StationToMapRegistry$RouteFeature"; // NOI18N
    private static final int NO_NEIGHBOUR_FOUND = -1;
    private static final MappingComponent MAPPING_COMPONENT = CismapBroker.getInstance().getMappingComponent();

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;
    /** this variable contains the realGeom object, the current LawaEditor object is registered on as listener. */
    private CidsBean realGeom;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blbSpace;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbLawa_nr;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblLawa_nr;
    private javax.swing.JLabel lblTypOberhalb;
    private javax.swing.JLabel lblTypUnterhalb;
    private javax.swing.JLabel lblValLawa_nr;
    private javax.swing.JLabel lblValTypOberhalb;
    private javax.swing.JLabel lblValTypUnterhalb;
    private javax.swing.JLabel lblValWk_k;
    private javax.swing.JLabel lblWk_k;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panLineOberhalb;
    private javax.swing.JPanel panLineUnterhalb;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public LawaEditor() {
        this(false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public LawaEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        linearReferencedLineEditor = (readOnly) ? new LinearReferencedLineRenderer() : new LinearReferencedLineEditor();
        linearReferencedLineEditor.setLineField("linie");

        initComponents();
        if (!readOnly) {
            lblValLawa_nr.setVisible(false);
            panLineOberhalb.setVisible(false);
            panLineUnterhalb.setVisible(false);
        } else {
            cbLawa_nr.setVisible(false);
        }

        try {
            new CidsBeanDropTarget(this);
        } catch (final Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
            }
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        // cidsFeature rausschmeissen
        if (!readOnly) {
            final CidsFeature cidsFeature = new CidsFeature(cidsBean.getMetaObject());
            final Collection<Feature> features = new ArrayList<Feature>();
            features.addAll(FeatureGroups.expandAll((FeatureGroup)cidsFeature));
            MAPPING_COMPONENT.getFeatureCollection().removeFeatures(features);
        }

        bindingGroup.unbind();
        removeListener();

        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            linearReferencedLineEditor.setCidsBean(cidsBean);
            final Object lawa_nr = cidsBean.getProperty("lawa_nr");

            if (lawa_nr != null) {
                lblValLawa_nr.setText(lawa_nr.toString());
            } else {
                lblValLawa_nr.setText("");
            }

            cidsBean.addPropertyChangeListener(this);

            if (cidsBean.getProperty("linie.geom") != null) {
                realGeom = (CidsBean)cidsBean.getProperty("linie.geom");
                realGeom.addPropertyChangeListener(this);
            }

            lblFoot.setText("");
            if (!readOnly) {
                zoomToFeatures();
            }
        } else {
            lblValLawa_nr.setText("");
            lblFoot.setText("");
        }

        setNeighbours();
    }

    /**
     * DOCUMENT ME!
     */
    private void setNeighbours() {
        if (cidsBean != null) {
            final int predecessorType = getNeighbourType(String.valueOf(cidsBean.getProperty("id")), true);
            final int sucessorType = getNeighbourType(String.valueOf(cidsBean.getProperty("id")), false);

            if (predecessorType != NO_NEIGHBOUR_FOUND) {
                lblValTypUnterhalb.setText(String.valueOf(predecessorType));
                final Color color = LawaFeatureRenderer.getPaintForLawaType(predecessorType);
                panLineUnterhalb.setBackground(color);
            } else {
                lblValTypUnterhalb.setText(CidsBeanSupport.FIELD_NOT_SET);
            }

            if (sucessorType != NO_NEIGHBOUR_FOUND) {
                lblValTypOberhalb.setText(String.valueOf(sucessorType));
                final Color color = LawaFeatureRenderer.getPaintForLawaType(sucessorType);
                panLineOberhalb.setBackground(color);
            } else {
                lblValTypOberhalb.setText(CidsBeanSupport.FIELD_NOT_SET);
            }

            panLineUnterhalb.setVisible((predecessorType != NO_NEIGHBOUR_FOUND));
            panLineOberhalb.setVisible((sucessorType != NO_NEIGHBOUR_FOUND));
        } else {
            lblValTypUnterhalb.setText(CidsBeanSupport.FIELD_NOT_SET);
            lblValTypOberhalb.setText(CidsBeanSupport.FIELD_NOT_SET);
            panLineUnterhalb.setVisible(false);
            panLineOberhalb.setVisible(false);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   lawaId       DOCUMENT ME!
     * @param   predecessor  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int getNeighbourType(final String lawaId, final boolean predecessor) {
        int result = NO_NEIGHBOUR_FOUND;

        try {
            final CidsServerSearch search = new LawaTypeNeighbourSearch(lawaId, predecessor);
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

            if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                final Object o = resArray.get(0).get(0);

                if (o instanceof Integer) {
                    result = ((Integer)o).intValue();
                }
            } else {
                LOG.error("Server error in getNeighbourType(). Cids server search return null. " // NOI18N
                            + "See the server logs for further information");              // NOI18N
            }
        } catch (ConnectionException e) {
            LOG.error("Exception during a cids server search.", e);                        // NOI18N
        }

        return result;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   realGeom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getWk_k(final Geometry realGeom) {
        String result = null;
        if (realGeom == null) {
            return null;
        }
        try {
            final String geom = realGeom.toText(); // linearReferencedLineEditor.getLineFeature().getGeometry().toText();
            final CidsBean route = (CidsBean)cidsBean.getProperty("linie."
                            + LinearReferencingConstants.PROP_STATIONLINIE_FROM
                            + LinearReferencingConstants.PROP_STATION_ROUTE);

            if (route == null) {
                LOG.error("Route not found");
                return null;
            }

            final Object routeId = route.getProperty("id");

            if (routeId == null) {
                LOG.error("Route id not found");
                return null;
            }

            final CidsServerSearch search = new WkkSearch(geom, routeId.toString());
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

            if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                final Object o = resArray.get(0).get(0);

                if (o instanceof String) {
                    result = o.toString();
                }
            } else {
                LOG.error("Server error in getWk_k(). Cids server search return null. " // NOI18N
                            + "See the server logs for further information");     // NOI18N
            }
        } catch (ConnectionException e) {
            LOG.error("Exception during a cids server search.", e);               // NOI18N
        }

        return result;
    }

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        MapUtil.zoomToFeatureCollection(linearReferencedLineEditor.getZoomFeatures());
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        lblWk_k = new javax.swing.JLabel();
        blbSpace = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblLawa_nr = new javax.swing.JLabel();
        cbLawa_nr = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblValWk_k = new javax.swing.JLabel();
        lblValLawa_nr = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        linearReferencedLineEditor = linearReferencedLineEditor;
        lblTypUnterhalb = new javax.swing.JLabel();
        lblTypOberhalb = new javax.swing.JLabel();
        lblValTypUnterhalb = new javax.swing.JLabel();
        lblValTypOberhalb = new javax.swing.JLabel();
        panLineUnterhalb = new javax.swing.JPanel();
        panLineOberhalb = new javax.swing.JPanel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(640, 100));
        panInfo.setPreferredSize(new java.awt.Dimension(640, 100));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("LAWA-Detailtyp");
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblWk_k.setText("Wasserkörper-Kürzel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblWk_k, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(blbSpace, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(jPanel1, gridBagConstraints);

        lblLawa_nr.setText("LAWA-Typ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblLawa_nr, gridBagConstraints);

        cbLawa_nr.setMinimumSize(new java.awt.Dimension(415, 20));
        cbLawa_nr.setPreferredSize(new java.awt.Dimension(415, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.lawa_nr}"),
                cbLawa_nr,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(cbLawa_nr, gridBagConstraints);

        lblValWk_k.setMaximumSize(new java.awt.Dimension(415, 20));
        lblValWk_k.setPreferredSize(new java.awt.Dimension(415, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_k}"),
                lblValWk_k,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblValWk_k, gridBagConstraints);

        lblValLawa_nr.setMinimumSize(new java.awt.Dimension(415, 20));
        lblValLawa_nr.setPreferredSize(new java.awt.Dimension(415, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(lblValLawa_nr, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo, gridBagConstraints);

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);

        panInfo1.setMinimumSize(new java.awt.Dimension(640, 140));
        panInfo1.setPreferredSize(new java.awt.Dimension(640, 140));

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText("Geometrie");
        panHeadInfo1.add(lblHeading1);

        panInfo1.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        panInfoContent1.add(linearReferencedLineEditor, gridBagConstraints);

        lblTypUnterhalb.setText("Typ unterhalb");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(30, 10, 5, 10);
        panInfoContent1.add(lblTypUnterhalb, gridBagConstraints);

        lblTypOberhalb.setText("Typ oberhalb");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(30, 10, 5, 10);
        panInfoContent1.add(lblTypOberhalb, gridBagConstraints);

        lblValTypUnterhalb.setText("<nicht gesetzt>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent1.add(lblValTypUnterhalb, gridBagConstraints);

        lblValTypOberhalb.setText("<nicht gesetzt>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent1.add(lblValTypOberhalb, gridBagConstraints);

        panLineUnterhalb.setBackground(new java.awt.Color(255, 91, 0));
        panLineUnterhalb.setMinimumSize(new java.awt.Dimension(10, 4));
        panLineUnterhalb.setPreferredSize(new java.awt.Dimension(100, 4));
        panLineUnterhalb.setLayout(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        panInfoContent1.add(panLineUnterhalb, gridBagConstraints);

        panLineOberhalb.setBackground(new java.awt.Color(255, 91, 0));
        panLineOberhalb.setMinimumSize(new java.awt.Dimension(10, 4));
        panLineOberhalb.setPreferredSize(new java.awt.Dimension(100, 4));
        panLineOberhalb.setLayout(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        panInfoContent1.add(panLineOberhalb, gridBagConstraints);

        panInfo1.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public void dispose() {
        linearReferencedLineEditor.dispose();
        removeListener();
        bindingGroup.unbind();
    }

    /**
     * DOCUMENT ME!
     */
    private void removeListener() {
        if (realGeom != null) {
            realGeom.removePropertyChangeListener(this);
        }
        if (cidsBean != null) {
            cidsBean.removePropertyChangeListener(this);
        }
    }

    @Override
    public String getTitle() {
        return cidsBean.getProperty("wk_k") + " " + String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        linearReferencedLineEditor.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        boolean save = true;
        save &= linearReferencedLineEditor.prepareForSave();
        return save;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }
    /**
     * When the route or a station of the LinearReferencedLineEditor will be changed, this method is invoked and can
     * change the wk_k property if required.
     *
     * @param  evt  DOCUMENT ME!
     */
    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (!readOnly) {
            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("propertyChange " + evt.getPropertyName());
                        }

                        if (evt.getPropertyName().equals("geom")) {
                            // the geom CidsBean from the cidsBean object was changed
                            if (evt.getNewValue() != null) {
                                ((CidsBean)evt.getNewValue()).addPropertyChangeListener(LawaEditor.this);
                            } else {
                                if (realGeom != null) {
                                    realGeom.removePropertyChangeListener(LawaEditor.this);
                                }
                            }
                        } else if (evt.getPropertyName().equals("geo_field") && (evt.getNewValue() != null)) {
                            // the geo_field of the geom CidsBean from the cidsBean object was changed
                            final String wkk = getWk_k((Geometry)evt.getNewValue());

                            if (LOG.isDebugEnabled()) {
                                LOG.debug("change wk_k to " + wkk);
                            }

                            if (wkk != null) {
                                lblValWk_k.setText(wkk);
                                try {
                                    final Object o = cidsBean.getProperty("wk_k");

                                    if (o instanceof String) {
                                        // the synchronisation avoids that more than one message dialog
                                        // is showing, if the wk-fg has changed. (The problem is that one change fires
                                        // more than one property change event)
                                        synchronized (LOG) {
                                            if (!wkk.equals((String)o)) {
                                                JOptionPane.showMessageDialog(
                                                    LawaEditor.this,
                                                    "Durch die Änderung der Stationierung hat "
                                                            + "sich der Wasserkörper geändert.",
                                                    "Änderung am Wasserkörper",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                                cidsBean.setProperty("wk_k", wkk);
                                            }
                                        }
                                    } else {
                                        cidsBean.setProperty("wk_k", wkk);
                                    }

                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("wk_k updated");
                                    }
                                } catch (final Exception e) {
                                    LOG.error("Cannot assign the new wk_k to the cids bean", e);
                                }
                            }
                        }
                    }
                }).start();
        }
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        if ((cidsBean != null) && !readOnly) {
            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_fg")) { // NOI18N
                    try {
                        cidsBean.setProperty("wk_k", bean.getProperty("wk_k"));
                    } catch (final Exception e) {
                        LOG.error("Error while setting a new wk_k", e);
                    }
                }
            }
        }
    }
}
