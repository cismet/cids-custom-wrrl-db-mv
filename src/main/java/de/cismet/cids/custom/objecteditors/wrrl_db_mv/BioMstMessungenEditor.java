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

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.cids.custom.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class BioMstMessungenEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BioMstMessungenEditor.class);

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbFisch;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGkMacPhyto;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGkMzb;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPhyto;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblDiatomeen;
    private javax.swing.JLabel lblDiatomeenVal;
    private javax.swing.JLabel lblFiBS;
    private javax.swing.JLabel lblFiBSVal;
    private javax.swing.JLabel lblFisch;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblMZB;
    private javax.swing.JLabel lblMacPhyten;
    private javax.swing.JLabel lblMacPhytenVal;
    private javax.swing.JLabel lblMacPhyto;
    private javax.swing.JLabel lblPerlodes;
    private javax.swing.JLabel lblPerlodesVal;
    private javax.swing.JLabel lblPhylib;
    private javax.swing.JLabel lblPhylibVal;
    private javax.swing.JLabel lblPhyto;
    private javax.swing.JLabel lblPhytoPl;
    private javax.swing.JLabel lblPhytoVal;
    private javax.swing.JLabel lblPhytob;
    private javax.swing.JLabel lblPhytobVal;
    private javax.swing.JLabel lblSIVal;
    private javax.swing.JLabel lblSTIM;
    private javax.swing.JLabel lblSTIMP;
    private javax.swing.JLabel lblSTIMPVal;
    private javax.swing.JLabel lblSTIMVal;
    private javax.swing.JLabel lblSTIT;
    private javax.swing.JLabel lblSTITVal;
    private javax.swing.JLabel lblSi;
    private javax.swing.JPanel panFooter;
    private javax.swing.JTextField txtFischBem;
    private javax.swing.JTextField txtMacPhytoBem;
    private javax.swing.JTextField txtMzbBem;
    private javax.swing.JTextField txtPhytoBem;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public BioMstMessungenEditor() {
        this(false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public BioMstMessungenEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();

        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            if (!readOnly) {
                setEnable(true);
            }
            bindingGroup.bind();
        } else {
            if (!readOnly) {
                setEnable(false);
            }
            clearForm();
        }

        if (readOnly) {
            setEnable(false);
        }
        lblFoot.setText("");
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     */
    private void clearForm() {
        lblDiatomeenVal.setText("");
        lblFiBSVal.setText("");
        lblMacPhytenVal.setText("");
        lblPerlodesVal.setText("");
        lblPhylibVal.setText("");
        lblPhytoVal.setText("");
        lblPhytobVal.setText("");
        lblSIVal.setText("");
        lblSTIMPVal.setText("");
        lblSTIMVal.setText("");
        lblSTITVal.setText("");
        txtFischBem.setText("");
        txtMacPhytoBem.setText("");
        txtMzbBem.setText("");
        txtPhytoBem.setText("");
        cbFisch.setSelectedIndex(-1);
        cbGkMacPhyto.setSelectedIndex(-1);
        cbGkMzb.setSelectedIndex(-1);
        cbPhyto.setSelectedIndex(-1);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    private void setEnable(final boolean enable) {
        cbFisch.setEnabled(enable);
        cbGkMacPhyto.setEnabled(enable);
        cbGkMzb.setEnabled(enable);
        cbPhyto.setEnabled(enable);
        txtFischBem.setEnabled(enable);
        txtMacPhytoBem.setEnabled(enable);
        txtMzbBem.setEnabled(enable);
        txtPhytoBem.setEnabled(enable);
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
        jPanel4 = new javax.swing.JPanel();
        lblMZB = new javax.swing.JLabel();
        lblSTITVal = new javax.swing.JLabel();
        cbGkMzb = new ScrollableComboBox();
        txtMzbBem = new javax.swing.JTextField();
        lblMacPhyto = new javax.swing.JLabel();
        cbGkMacPhyto = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        txtMacPhytoBem = new javax.swing.JTextField();
        lblSTIT = new javax.swing.JLabel();
        lblSTIM = new javax.swing.JLabel();
        lblPerlodes = new javax.swing.JLabel();
        lblSi = new javax.swing.JLabel();
        lblSTIMVal = new javax.swing.JLabel();
        lblPerlodesVal = new javax.swing.JLabel();
        lblSIVal = new javax.swing.JLabel();
        lblSTIMP = new javax.swing.JLabel();
        lblPhylib = new javax.swing.JLabel();
        lblPhytob = new javax.swing.JLabel();
        lblMacPhyten = new javax.swing.JLabel();
        lblSTIMPVal = new javax.swing.JLabel();
        lblPhylibVal = new javax.swing.JLabel();
        lblPhytobVal = new javax.swing.JLabel();
        lblMacPhytenVal = new javax.swing.JLabel();
        lblDiatomeenVal = new javax.swing.JLabel();
        lblDiatomeen = new javax.swing.JLabel();
        lblFisch = new javax.swing.JLabel();
        cbFisch = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        txtFischBem = new javax.swing.JTextField();
        lblPhytoPl = new javax.swing.JLabel();
        cbPhyto = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        txtPhytoBem = new javax.swing.JTextField();
        lblFiBS = new javax.swing.JLabel();
        lblFiBSVal = new javax.swing.JLabel();
        lblPhyto = new javax.swing.JLabel();
        lblPhytoVal = new javax.swing.JLabel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Messdaten"));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        lblMZB.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblMZB.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblMZB, gridBagConstraints);

        lblSTITVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblSTITVal.setPreferredSize(new java.awt.Dimension(200, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_sti_t.name}"),
                lblSTITVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTITVal, gridBagConstraints);

        cbGkMzb.setMaximumSize(new java.awt.Dimension(200, 20));
        cbGkMzb.setMinimumSize(new java.awt.Dimension(150, 20));
        cbGkMzb.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_mzb_gesamt}"),
                cbGkMzb,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbGkMzb, gridBagConstraints);

        txtMzbBem.setToolTipText("Bemerkung");
        txtMzbBem.setMinimumSize(new java.awt.Dimension(200, 20));
        txtMzbBem.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_mzb}"),
                txtMzbBem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtMzbBem, gridBagConstraints);

        lblMacPhyto.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblMacPhyto.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblMacPhyto, gridBagConstraints);

        cbGkMacPhyto.setMaximumSize(new java.awt.Dimension(200, 20));
        cbGkMacPhyto.setMinimumSize(new java.awt.Dimension(150, 20));
        cbGkMacPhyto.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_mp_gesamt}"),
                cbGkMacPhyto,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbGkMacPhyto, gridBagConstraints);

        txtMacPhytoBem.setToolTipText("Bemerkung");
        txtMacPhytoBem.setMinimumSize(new java.awt.Dimension(200, 20));
        txtMacPhytoBem.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_mp}"),
                txtMacPhytoBem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtMacPhytoBem, gridBagConstraints);

        lblSTIT.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblSTIT.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTIT, gridBagConstraints);

        lblSTIM.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblSTIM.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTIM, gridBagConstraints);

        lblPerlodes.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblPerlodes.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPerlodes, gridBagConstraints);

        lblSi.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblSi.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSi, gridBagConstraints);

        lblSTIMVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblSTIMVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_sti_m.name}"),
                lblSTIMVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTIMVal, gridBagConstraints);

        lblPerlodesVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblPerlodesVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.perlodes.name}"),
                lblPerlodesVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPerlodesVal, gridBagConstraints);

        lblSIVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblSIVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.perlodes_sl.name}"),
                lblSIVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSIVal, gridBagConstraints);

        lblSTIMP.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblSTIMP.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTIMP, gridBagConstraints);

        lblPhylib.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblPhylib.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhylib, gridBagConstraints);

        lblPhytob.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblPhytob.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhytob, gridBagConstraints);

        lblMacPhyten.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblMacPhyten.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblMacPhyten, gridBagConstraints);

        lblSTIMPVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblSTIMPVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sti_mp.name}"),
                lblSTIMPVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTIMPVal, gridBagConstraints);

        lblPhylibVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblPhylibVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phylib.name}"),
                lblPhylibVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhylibVal, gridBagConstraints);

        lblPhytobVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblPhytobVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phylib_phytobenthos.name}"),
                lblPhytobVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhytobVal, gridBagConstraints);

        lblMacPhytenVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblMacPhytenVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phylib_makrophyten.name}"),
                lblMacPhytenVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblMacPhytenVal, gridBagConstraints);

        lblDiatomeenVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblDiatomeenVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phylib_diatomeen.name}"),
                lblDiatomeenVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblDiatomeenVal, gridBagConstraints);

        lblDiatomeen.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblDiatomeen.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblDiatomeen, gridBagConstraints);

        lblFisch.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblFisch.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(lblFisch, gridBagConstraints);

        cbFisch.setMaximumSize(new java.awt.Dimension(200, 20));
        cbFisch.setMinimumSize(new java.awt.Dimension(150, 20));
        cbFisch.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_fische_gesamt}"),
                cbFisch,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(cbFisch, gridBagConstraints);

        txtFischBem.setToolTipText("Bemerkung");
        txtFischBem.setMinimumSize(new java.awt.Dimension(200, 20));
        txtFischBem.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_fische}"),
                txtFischBem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(txtFischBem, gridBagConstraints);

        lblPhytoPl.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblPhytoPl.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(lblPhytoPl, gridBagConstraints);

        cbPhyto.setMaximumSize(new java.awt.Dimension(200, 20));
        cbPhyto.setMinimumSize(new java.awt.Dimension(150, 20));
        cbPhyto.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_phytoplankton_gesamt}"),
                cbPhyto,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(cbPhyto, gridBagConstraints);

        txtPhytoBem.setToolTipText("Bemerkung");
        txtPhytoBem.setMinimumSize(new java.awt.Dimension(200, 20));
        txtPhytoBem.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_phypl}"),
                txtPhytoBem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(txtPhytoBem, gridBagConstraints);

        lblFiBS.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblFiBS.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblFiBS, gridBagConstraints);

        lblFiBSVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblFiBSVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fibs.name}"),
                lblFiBSVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblFiBSVal, gridBagConstraints);

        lblPhyto.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenEditor.class,
                "BioMstStammdatenEditor.lblPhyto.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhyto, gridBagConstraints);

        lblPhytoVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblPhytoVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phytoplankton.name}"),
                lblPhytoVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhytoVal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 10);
        add(jPanel4, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     */
    @Override
    public void dispose() {
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

    @Override
    public void editorClosed(final EditorSaveStatus status) {
        // TODO ?
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }
}
