/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * GupMassnahmeSohle.java
 *
 * Created on 28.06.2012, 11:49:19
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.util.ArrayList;
import java.util.List;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class SchutzgebietEditor extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            SchutzgebietEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private List<CidsBean> others;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbName;
    private javax.swing.JLabel lblArt;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public SchutzgebietEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public SchutzgebietEditor(final boolean readOnly) {
        linearReferencedLineEditor = (readOnly) ? new LinearReferencedLineRenderer() : new LinearReferencedLineEditor();
        linearReferencedLineEditor.setLineField("linie");
        initComponents();

        if (!readOnly) {
            linearReferencedLineEditor.setOtherLinesEnabled(true);
            linearReferencedLineEditor.setOtherLinesQueryAddition(
                "schutzgebiet",
                "schutzgebiet.linie = ");
            linearReferencedLineEditor.setShowOtherInDialog(true);
        } else {
            RendererTools.makeReadOnly(cbName);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        lblArt = new javax.swing.JLabel();
        cbName = new ScrollableComboBox();
        linearReferencedLineEditor = linearReferencedLineEditor;

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(994, 400));
        setLayout(new java.awt.GridBagLayout());

        lblArt.setText(org.openide.util.NbBundle.getMessage(
                SchutzgebietEditor.class,
                "SchutzgebietEditor.lblArt.text")); // NOI18N
        lblArt.setMaximumSize(new java.awt.Dimension(170, 17));
        lblArt.setMinimumSize(new java.awt.Dimension(170, 17));
        lblArt.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 15, 5, 5);
        add(lblArt, gridBagConstraints);

        cbName.setMaximumSize(new java.awt.Dimension(420, 20));
        cbName.setMinimumSize(new java.awt.Dimension(420, 20));
        cbName.setPreferredSize(new java.awt.Dimension(520, 20));

        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.art}"),
                cbName,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 5, 5);
        add(cbName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 10, 10);
        add(linearReferencedLineEditor, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            if (this.others != null) {
                final List<CidsBean> lineBeans = new ArrayList<CidsBean>();
                final Object id = cidsBean.getProperty("linie.id");

                for (final CidsBean b : this.others) {
                    final CidsBean tmp = (CidsBean)b.getProperty("linie");

                    if ((tmp != null) && (!tmp.getProperty("id").equals(id))) {
                        lineBeans.add(tmp);
                    }
                }
                linearReferencedLineEditor.setOtherLines(lineBeans);
            }

            linearReferencedLineEditor.setCidsBean(cidsBean);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  others  DOCUMENT ME!
     */
    public void setOthers(final List<CidsBean> others) {
        this.others = others;
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
        linearReferencedLineEditor.dispose();
    }

    @Override
    public String getTitle() {
        return "Schutzgebiet";
    }

    @Override
    public void setTitle(final String title) {
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        linearReferencedLineEditor.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        return linearReferencedLineEditor.prepareForSave();
    }
    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "x",
            "schutzgebiet",
            1,
            1280,
            1024);
    }
}