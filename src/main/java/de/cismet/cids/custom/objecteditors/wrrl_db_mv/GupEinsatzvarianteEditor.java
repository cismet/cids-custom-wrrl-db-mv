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
 * Created on 04.04.2012, 11:49:19
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaObject;

import java.util.Comparator;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultBindableCheckboxField;
import de.cismet.cids.editors.DefaultBindableColorChooser;
import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import static de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupMassnahmenartEditor.SELECT_COLOR;
import static de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupMassnahmenartEditor.UNSELECT_COLOR;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupEinsatzvarianteEditor extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupEinsatzvarianteEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private final VermeidungsgruppenDecider vdecider = new VermeidungsgruppenDecider(false);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableCheckboxField ccVermeidungsgruppen;
    private de.cismet.cids.editors.DefaultBindableColorChooser dccColor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel jpGeschuetzteArten;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblFaktor;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblName;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo2;
    private javax.swing.JPanel panInfoContent2;
    private javax.swing.JPanel panSpacingBottom2;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private de.cismet.tools.gui.SemiRoundedPanel semiRoundedPanel1;
    private javax.swing.JTextField txtFaktor;
    private javax.swing.JTextField txtName;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupEinsatzvarianteEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupEinsatzvarianteEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
        jTabbedPane1.setUI(new TabbedPaneUITransparent());
        ccVermeidungsgruppen.setBackgroundSelected(SELECT_COLOR);
        ccVermeidungsgruppen.setBackgroundUnselected(UNSELECT_COLOR);

        if (readOnly) {
            RendererTools.makeReadOnly(txtName);
            RendererTools.makeReadOnly(txtFaktor);
            RendererTools.makeReadOnly(dccColor);
            RendererTools.makeReadOnly(ccVermeidungsgruppen);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        roundedPanel1 = new de.cismet.tools.gui.RoundedPanel();
        semiRoundedPanel1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblColor = new javax.swing.JLabel();
        dccColor = new de.cismet.cids.editors.DefaultBindableColorChooser();
        lblFaktor = new javax.swing.JLabel();
        txtFaktor = new javax.swing.JTextField();
        jpGeschuetzteArten = new javax.swing.JPanel();
        panInfo2 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        ccVermeidungsgruppen = new DefaultBindableCheckboxField(new CustomComparator());
        panSpacingBottom2 = new javax.swing.JPanel();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        semiRoundedPanel1.setBackground(new java.awt.Color(51, 51, 51));
        semiRoundedPanel1.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                GupEinsatzvarianteEditor.class,
                "GupEinsatzvarianteEditor.lblHeading.text")); // NOI18N
        semiRoundedPanel1.add(lblHeading);

        roundedPanel1.add(semiRoundedPanel1, java.awt.BorderLayout.NORTH);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblName.setText(org.openide.util.NbBundle.getMessage(
                GupEinsatzvarianteEditor.class,
                "GupEinsatzvarianteEditor.lblName.text")); // NOI18N
        lblName.setMaximumSize(new java.awt.Dimension(170, 17));
        lblName.setMinimumSize(new java.awt.Dimension(170, 17));
        lblName.setPreferredSize(new java.awt.Dimension(215, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 15, 5, 5);
        jPanel1.add(lblName, gridBagConstraints);

        txtName.setMinimumSize(new java.awt.Dimension(400, 25));
        txtName.setPreferredSize(new java.awt.Dimension(450, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 5, 5);
        jPanel1.add(txtName, gridBagConstraints);

        lblColor.setText(org.openide.util.NbBundle.getMessage(
                GupEinsatzvarianteEditor.class,
                "GupEinsatzvarianteEditor.lblColor.text")); // NOI18N
        lblColor.setMaximumSize(new java.awt.Dimension(170, 17));
        lblColor.setMinimumSize(new java.awt.Dimension(170, 17));
        lblColor.setPreferredSize(new java.awt.Dimension(215, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblColor, gridBagConstraints);

        dccColor.setMinimumSize(new java.awt.Dimension(250, 20));
        dccColor.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.color}"),
                dccColor,
                org.jdesktop.beansbinding.BeanProperty.create("color"));
        binding.setConverter(((DefaultBindableColorChooser)dccColor).getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanel1.add(dccColor, gridBagConstraints);

        lblFaktor.setText(org.openide.util.NbBundle.getMessage(
                GupEinsatzvarianteEditor.class,
                "GupEinsatzvarianteEditor.lblFaktor.text")); // NOI18N
        lblFaktor.setMaximumSize(new java.awt.Dimension(170, 17));
        lblFaktor.setMinimumSize(new java.awt.Dimension(170, 17));
        lblFaktor.setPreferredSize(new java.awt.Dimension(215, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel1.add(lblFaktor, gridBagConstraints);

        txtFaktor.setMinimumSize(new java.awt.Dimension(400, 25));
        txtFaktor.setPreferredSize(new java.awt.Dimension(450, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.factor}"),
                txtFaktor,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtFaktor, gridBagConstraints);

        roundedPanel1.add(jPanel1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel2.add(roundedPanel1, gridBagConstraints);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(
                GupEinsatzvarianteEditor.class,
                "GupEinsatzvarianteEditor.jPanel2.TabConstraints.tabTitle"),
            jPanel2); // NOI18N

        jpGeschuetzteArten.setOpaque(false);
        jpGeschuetzteArten.setLayout(new java.awt.GridBagLayout());

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText(org.openide.util.NbBundle.getMessage(
                GupEinsatzvarianteEditor.class,
                "GupEinsatzvarianteEditor.lblHeading2.text")); // NOI18N
        panHeadInfo2.add(lblHeading2);

        panInfo2.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        ccVermeidungsgruppen.setMinimumSize(new java.awt.Dimension(550, 320));
        ccVermeidungsgruppen.setOpaque(false);
        ccVermeidungsgruppen.setPreferredSize(new java.awt.Dimension(550, 320));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.vermeidungsgruppen}"),
                ccVermeidungsgruppen,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        jPanel4.add(ccVermeidungsgruppen, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent2.add(jPanel4, gridBagConstraints);

        panSpacingBottom2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panInfoContent2.add(panSpacingBottom2, gridBagConstraints);

        panInfo2.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jpGeschuetzteArten.add(panInfo2, gridBagConstraints);

        jTabbedPane1.addTab("Regeln für geschützte Arten", jpGeschuetzteArten);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jTabbedPane1, gridBagConstraints);

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
            ccVermeidungsgruppen.refreshCheckboxState(vdecider, true, false);
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Einsatzvariante: " + ((cidsBean.getProperty("name") != null) ? cidsBean.toString() : "");
    }

    @Override
    public void setTitle(final String title) {
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
    }

    @Override
    public boolean prepareForSave() {
        return true;
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
            "gup_entwicklungsziel_name",
            9,
            1280,
            1024);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomComparator implements Comparator<MetaObject> {

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final MetaObject o1, final MetaObject o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
