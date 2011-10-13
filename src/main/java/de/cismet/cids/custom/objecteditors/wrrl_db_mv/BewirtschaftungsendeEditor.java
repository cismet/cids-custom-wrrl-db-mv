/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;

import de.cismet.cids.custom.wrrl_db_mv.util.BewirtschaftungsendeHelper;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.MapUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.WrrlEditorTester;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureGroup;
import de.cismet.cismap.commons.features.FeatureGroups;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.CidsFeature;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class BewirtschaftungsendeEditor extends JPanel implements CidsBeanRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            BewirtschaftungsendeEditor.class);

    //~ Instance fields --------------------------------------------------------

    MappingComponent MAPPING_COMPONENT = CismapBroker.getInstance().getMappingComponent();

    private final BewirtschaftungsendeHelper helper = new BewirtschaftungsendeHelper();
    private CidsBean cidsBean;
    private final PropertyChangeListener helperListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if ((evt.getSource() == helper) && evt.getPropertyName().equals(BewirtschaftungsendeHelper.PROP_WK)) {
                    final CidsBean wkBean = (CidsBean)evt.getNewValue();
                    if (wkBean != null) {
                        lblWk.setText((String)wkBean.getProperty("wk_k"));
                    } else {
                        lblWk.setText("-");
                    }
                }
            }
        };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblBemerkungKey;
    private javax.swing.JPanel lblSpacingBottom;
    private javax.swing.JLabel lblStatKey;
    private javax.swing.JLabel lblWk;
    private javax.swing.JScrollPane scpBemerkung;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.StationEditor stationEditor1;
    private javax.swing.JTextArea txtBemerkungValue;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form BewirtschaftungsendeEditor.
     */
    public BewirtschaftungsendeEditor() {
        initComponents();
        stationEditor1.addListener(new LinearReferencedPointEditorListener() {

                @Override
                public void pointCreated() {
                    try {
                        cidsBean.setProperty("stat", stationEditor1.getCidsBean());
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("error while assigning new station-cidsbean to the cidsbean", ex);
                        }
                    }
                }
            });
        helper.addPropertyChangeListener(helperListener);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        MapUtil.zoomToFeatureCollection(stationEditor1.getZoomFeatures());
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        helper.setCidsBean(cidsBean);
        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        if (cidsBean != null) {
            bindingGroup.bind();

            // cidsFeature rausschmeissen
            final CidsFeature cidsFeature = new CidsFeature(cidsBean.getMetaObject());
            final Collection<Feature> features = new ArrayList<Feature>();
            features.addAll(FeatureGroups.expandAll((FeatureGroup)cidsFeature));
            MAPPING_COMPONENT.getFeatureCollection().removeFeatures(features);

            zoomToFeatures();
        }
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

        jPanel2 = new javax.swing.JPanel();
        lblBemerkungKey = new javax.swing.JLabel();
        stationEditor1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.StationEditor();
        scpBemerkung = new javax.swing.JScrollPane();
        txtBemerkungValue = new javax.swing.JTextArea();
        lblStatKey = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblWk = new javax.swing.JLabel();
        lblSpacingBottom = new javax.swing.JPanel();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblBemerkungKey.setText("Bemerkung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblBemerkungKey, gridBagConstraints);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stat}"),
                stationEditor1,
                org.jdesktop.beansbinding.BeanProperty.create("cidsBean"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(stationEditor1, gridBagConstraints);

        txtBemerkungValue.setColumns(30);
        txtBemerkungValue.setLineWrap(true);
        txtBemerkungValue.setRows(10);
        txtBemerkungValue.setWrapStyleWord(true);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung}"),
                txtBemerkungValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        scpBemerkung.setViewportView(txtBemerkungValue);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(scpBemerkung, gridBagConstraints);

        lblStatKey.setText("Station");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblStatKey, gridBagConstraints);

        jLabel1.setText("Wasserkörper");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblWk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jPanel2, gridBagConstraints);

        lblSpacingBottom.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(lblSpacingBottom, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public void dispose() {
        helper.dispose();
        helper.removeListener(helperListener);
        stationEditor1.dispose();
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        new WrrlEditorTester("bewirtschaftungsende", BewirtschaftungsendeEditor.class, CidsBeanSupport.DOMAIN_NAME)
                .run();
    }
}
