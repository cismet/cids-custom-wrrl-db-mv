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
 * Created on 10.10.2012, 11:49:19
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import org.apache.log4j.Logger;

import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultBindableColorChooser;
import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class VermeidungsgruppeEditor extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger LOG = Logger.getLogger(VermeidungsgruppeEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbAusfuehrungszeitpunkt;
    private javax.swing.JCheckBox cbSohle;
    private javax.swing.JCheckBox cbUfer;
    private javax.swing.JCheckBox cbUmfeld;
    private javax.swing.JCheckBox cbWarning;
    private de.cismet.cids.editors.DefaultBindableColorChooser dccColor;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblArten;
    private javax.swing.JLabel lblBereiche;
    private javax.swing.JLabel lblColor;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblWarning;
    private javax.swing.JLabel lblWarning1;
    private javax.swing.JTextField txtName;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public VermeidungsgruppeEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public VermeidungsgruppeEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();

        if (readOnly) {
            RendererTools.makeReadOnly(txtName);
            RendererTools.makeReadOnly(dccColor);
            RendererTools.makeReadOnly(cbAusfuehrungszeitpunkt);
            RendererTools.makeReadOnly(cbWarning);
            RendererTools.makeReadOnly(cbUmfeld);
            RendererTools.makeReadOnly(cbSohle);
            RendererTools.makeReadOnly(cbUfer);
        } else {
            try {
                new CidsBeanDropTarget(jList1);
            } catch (final Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
                }
            }
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

        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblColor = new javax.swing.JLabel();
        dccColor = new de.cismet.cids.editors.DefaultBindableColorChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new ArtenList();
        lblArten = new javax.swing.JLabel();
        lblWarning = new javax.swing.JLabel();
        cbWarning = new javax.swing.JCheckBox();
        lblBereiche = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cbUfer = new javax.swing.JCheckBox();
        cbUmfeld = new javax.swing.JCheckBox();
        cbSohle = new javax.swing.JCheckBox();
        lblWarning1 = new javax.swing.JLabel();
        cbAusfuehrungszeitpunkt = new javax.swing.JCheckBox();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        lblName.setText(org.openide.util.NbBundle.getMessage(
                VermeidungsgruppeEditor.class,
                "VermeidungsgruppeEditor.lblName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 15, 5, 5);
        add(lblName, gridBagConstraints);

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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 5, 5);
        add(txtName, gridBagConstraints);

        lblColor.setText(org.openide.util.NbBundle.getMessage(
                VermeidungsgruppeEditor.class,
                "VermeidungsgruppeEditor.lblColor.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        add(lblColor, gridBagConstraints);

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
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        add(dccColor, gridBagConstraints);

        jList1.setModel(new javax.swing.AbstractListModel() {

                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

                @Override
                public int getSize() {
                    return strings.length;
                }
                @Override
                public Object getElementAt(final int i) {
                    return strings[i];
                }
            });

        final org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.arten}");
        final org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        jList1);
        bindingGroup.addBinding(jListBinding);

        jList1.addKeyListener(new java.awt.event.KeyAdapter() {

                @Override
                public void keyPressed(final java.awt.event.KeyEvent evt) {
                    jList1KeyPressed(evt);
                }
                @Override
                public void keyTyped(final java.awt.event.KeyEvent evt) {
                    jList1KeyTyped(evt);
                }
            });
        jScrollPane1.setViewportView(jList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 9, 4);
        add(jScrollPane1, gridBagConstraints);

        lblArten.setText(org.openide.util.NbBundle.getMessage(
                VermeidungsgruppeEditor.class,
                "VermeidungsgruppeEditor.lblArten.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        add(lblArten, gridBagConstraints);

        lblWarning.setText(org.openide.util.NbBundle.getMessage(
                VermeidungsgruppeEditor.class,
                "VermeidungsgruppeEditor.lblWarning.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        add(lblWarning, gridBagConstraints);

        cbWarning.setText(org.openide.util.NbBundle.getMessage(
                VermeidungsgruppeEditor.class,
                "VermeidungsgruppeEditor.cbWarning.text")); // NOI18N
        cbWarning.setContentAreaFilled(false);
        cbWarning.setMaximumSize(new java.awt.Dimension(230, 22));
        cbWarning.setMinimumSize(new java.awt.Dimension(230, 22));
        cbWarning.setPreferredSize(new java.awt.Dimension(230, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.warnung}"),
                cbWarning,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbWarning, gridBagConstraints);

        lblBereiche.setText(org.openide.util.NbBundle.getMessage(
                VermeidungsgruppeEditor.class,
                "VermeidungsgruppeEditor.lblBereiche.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        add(lblBereiche, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        cbUfer.setText(org.openide.util.NbBundle.getMessage(
                VermeidungsgruppeEditor.class,
                "VermeidungsgruppeEditor.cbUfer.text")); // NOI18N
        cbUfer.setContentAreaFilled(false);
        cbUfer.setPreferredSize(new java.awt.Dimension(120, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ufer}"),
                cbUfer,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(cbUfer, gridBagConstraints);

        cbUmfeld.setText(org.openide.util.NbBundle.getMessage(
                VermeidungsgruppeEditor.class,
                "VermeidungsgruppeEditor.cbUmfeld.text")); // NOI18N
        cbUmfeld.setContentAreaFilled(false);
        cbUmfeld.setPreferredSize(new java.awt.Dimension(120, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.umfeld}"),
                cbUmfeld,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(cbUmfeld, gridBagConstraints);

        cbSohle.setText(org.openide.util.NbBundle.getMessage(
                VermeidungsgruppeEditor.class,
                "VermeidungsgruppeEditor.cbSohle.text")); // NOI18N
        cbSohle.setContentAreaFilled(false);
        cbSohle.setPreferredSize(new java.awt.Dimension(120, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sohle}"),
                cbSohle,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(cbSohle, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jPanel1, gridBagConstraints);

        lblWarning1.setText(org.openide.util.NbBundle.getMessage(
                VermeidungsgruppeEditor.class,
                "VermeidungsgruppeEditor.lblWarning1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        add(lblWarning1, gridBagConstraints);

        cbAusfuehrungszeitpunkt.setText(org.openide.util.NbBundle.getMessage(
                VermeidungsgruppeEditor.class,
                "VermeidungsgruppeEditor.cbAusfuehrungszeitpunkt.text")); // NOI18N
        cbAusfuehrungszeitpunkt.setContentAreaFilled(false);
        cbAusfuehrungszeitpunkt.setMaximumSize(new java.awt.Dimension(230, 22));
        cbAusfuehrungszeitpunkt.setMinimumSize(new java.awt.Dimension(230, 22));
        cbAusfuehrungszeitpunkt.setPreferredSize(new java.awt.Dimension(230, 22));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ausfuehrungszeitpunkt}"),
                cbAusfuehrungszeitpunkt,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbAusfuehrungszeitpunkt, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jList1KeyTyped(final java.awt.event.KeyEvent evt) { //GEN-FIRST:event_jList1KeyTyped
    }                                                                //GEN-LAST:event_jList1KeyTyped

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jList1KeyPressed(final java.awt.event.KeyEvent evt) { //GEN-FIRST:event_jList1KeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_DELETE) {
            evt.consume();
            if (!readOnly) {
                final List<CidsBean> artenBeans = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "arten");
                final Object[] o = jList1.getSelectedValues();

                for (final Object tmp : o) {
                    artenBeans.remove((CidsBean)tmp);
                }
            }
        }
    } //GEN-LAST:event_jList1KeyPressed

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
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Vermeidungsgruppe: " + ((cidsBean.getProperty("name") != null) ? cidsBean.toString() : "");
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
            "vermeidungsgruppe",
            1,
            1280,
            1024);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class ArtenList extends JList implements CidsBeanDropListener {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ArtenList object.
         */
        public ArtenList() {
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            if (readOnly) {
                // ignore the drop action
                return;
            }

            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Geschuetzte_art")) {
                    final List<CidsBean> artenBeans = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "arten");
                    artenBeans.add(bean);
                }
            }
        }
    }
}
