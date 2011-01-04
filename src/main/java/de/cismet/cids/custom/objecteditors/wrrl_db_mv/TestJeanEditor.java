/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class TestJeanEditor extends DefaultCustomObjectEditor {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TestJeanEditor.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.StationArrayEditor stationArrayEditor;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.StationEditor stationEditor;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkTeilEditor wkTeilEditor;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkTeileEditor wkTeileEditor;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form TestJeanEditor.
     */
    public TestJeanEditor() {
        initComponents();

        stationEditor.addStationEditorListener(new StationEditorListener() {

                @Override
                public void stationCreated() {
                    zoomToFeatures();
                    try {
                        cidsBean.setProperty("station", stationEditor.getCidsBean());
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("error while setting new cidsbean for station", ex);
                        }
                    }
                }
            });

        wkTeilEditor.getWrappedEditor()
                .addLinearReferencedLineEditorListener(new LinearReferencedLineEditorListener() {

                        @Override
                        public void linearReferencedLineCreated() {
                            zoomToFeatures();
                            try {
                                cidsBean.setProperty("wk_teil", wkTeilEditor.getCidsBean());
                            } catch (Exception ex) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("error while setting new cidsbean for wk_teil", ex);
                                }
                            }
                        }
                    });

        wkTeileEditor.addLinearReferencedLineEditorListener(new LinearReferencedLineArrayEditorListener() {

                @Override
                public void editorAdded(final LinearReferencedLineEditor source) {
                    zoomToFeatures();
                }

                @Override
                public void editorRemoved(final LinearReferencedLineEditor source) {
                    zoomToFeatures();
                }
            });

        stationArrayEditor.addStationEditorListener(new StationArrayEditorListener() {

                @Override
                public void editorAdded(final StationEditor source) {
                    zoomToFeatures();
                }

                @Override
                public void editorRemoved(final StationEditor source) {
                    zoomToFeatures();
                }
            });

        // stationEditor1.setImageIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/ende.png")));
        // // NOI18N
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        final MappingComponent mappingComponent = CismapBroker.getInstance().getMappingComponent();
        if (!mappingComponent.isFixedMapExtent()) {
            CismapBroker.getInstance()
                    .getMappingComponent()
                    .zoomToFeatureCollection(mappingComponent.isFixedMapScale());
        }
    }

    @Override
    public void dispose() {
        try {
            stationEditor.dispose();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while dispose", ex);
            }
        }
        try {
            stationArrayEditor.dispose();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while dispose", ex);
            }
        }
        try {
            wkTeilEditor.dispose();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while dispose", ex);
            }
        }
        try {
            wkTeileEditor.dispose();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while dispose", ex);
            }
        }
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

        wkTeilEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkTeilEditor();
        stationEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.StationEditor();
        wkTeileEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkTeileEditor();
        stationArrayEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.StationArrayEditor();

        setLayout(new java.awt.GridBagLayout());

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_teil}"),
                wkTeilEditor,
                org.jdesktop.beansbinding.BeanProperty.create("cidsBean"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(wkTeilEditor, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.station}"),
                stationEditor,
                org.jdesktop.beansbinding.BeanProperty.create("cidsBean"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(stationEditor, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_teile}"),
                wkTeileEditor,
                org.jdesktop.beansbinding.BeanProperty.create("cidsBeans"));
        bindingGroup.addBinding(binding);

        wkTeileEditor.setLayout(new java.awt.GridLayout(1, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(wkTeileEditor, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stationen}"),
                stationArrayEditor,
                org.jdesktop.beansbinding.BeanProperty.create("cidsBeans"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(stationArrayEditor, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents
}
