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
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class BioMstMessungenRenderer extends JPanel implements CidsBeanRenderer, FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BioMstMessungenRenderer.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel4;
    private javax.swing.JCheckBox jcGesDiatomeen;
    private javax.swing.JCheckBox jcGesMacPhyto;
    private javax.swing.JCheckBox jcGesPerlodes;
    private javax.swing.JCheckBox jcGesPerlodesAd;
    private javax.swing.JCheckBox jcGesPhylib;
    private javax.swing.JCheckBox jcGesPhyto;
    private javax.swing.JCheckBox jcGesSI;
    private javax.swing.JLabel lblDiatTrophie;
    private javax.swing.JLabel lblDiatTrophieVal;
    private javax.swing.JLabel lblDiatomeen;
    private javax.swing.JLabel lblDiatomeenVal;
    private javax.swing.JLabel lblFiBS;
    private javax.swing.JLabel lblFiBSVal;
    private javax.swing.JLabel lblFisch;
    private javax.swing.JLabel lblFischeBem;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGkFische;
    private javax.swing.JLabel lblGkMacPhyt;
    private javax.swing.JLabel lblGkMzb;
    private javax.swing.JLabel lblGkPhyt;
    private javax.swing.JLabel lblMZB;
    private javax.swing.JLabel lblMZBBem;
    private javax.swing.JLabel lblMacPhytBem;
    private javax.swing.JLabel lblMacPhyten;
    private javax.swing.JLabel lblMacPhytenVal;
    private javax.swing.JLabel lblMacPhyto;
    private javax.swing.JLabel lblPerlodes;
    private javax.swing.JLabel lblPerlodesAd;
    private javax.swing.JLabel lblPerlodesVal;
    private javax.swing.JLabel lblPerlodesValAd;
    private javax.swing.JLabel lblPhylib;
    private javax.swing.JLabel lblPhylibVal;
    private javax.swing.JLabel lblPhytBem;
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
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new LawaEditor object.
     */
    public BioMstMessungenRenderer() {
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
            bindingGroup.bind();
        } else {
            clearForm();
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
        lblPerlodesValAd.setText("");
        lblDiatTrophieVal.setText("");
        lblFischeBem.setText("");
        lblMacPhytBem.setText("");
        lblMZBBem.setText("");
        lblPhytBem.setText("");
        lblGkFische.setText("");
        lblGkMacPhyt.setText("");
        lblGkMzb.setText("");
        lblGkPhyt.setText("");
        jcGesDiatomeen.setSelected(false);
        jcGesMacPhyto.setSelected(false);
        jcGesPerlodes.setSelected(false);
        jcGesPerlodesAd.setSelected(false);
        jcGesPhylib.setSelected(false);
        jcGesPhyto.setSelected(false);
        jcGesSI.setSelected(false);
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
        lblMacPhyto = new javax.swing.JLabel();
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
        lblPhytoPl = new javax.swing.JLabel();
        lblFiBS = new javax.swing.JLabel();
        lblFiBSVal = new javax.swing.JLabel();
        lblPhyto = new javax.swing.JLabel();
        lblPhytoVal = new javax.swing.JLabel();
        lblGkMzb = new javax.swing.JLabel();
        lblGkFische = new javax.swing.JLabel();
        lblGkMacPhyt = new javax.swing.JLabel();
        lblGkPhyt = new javax.swing.JLabel();
        lblMZBBem = new javax.swing.JLabel();
        lblFischeBem = new javax.swing.JLabel();
        lblMacPhytBem = new javax.swing.JLabel();
        lblPhytBem = new javax.swing.JLabel();
        jcGesPerlodes = new javax.swing.JCheckBox();
        jcGesPerlodesAd = new javax.swing.JCheckBox();
        jcGesSI = new javax.swing.JCheckBox();
        lblPerlodesAd = new javax.swing.JLabel();
        lblPerlodesValAd = new javax.swing.JLabel();
        jcGesPhylib = new javax.swing.JCheckBox();
        jcGesPhyto = new javax.swing.JCheckBox();
        jcGesMacPhyto = new javax.swing.JCheckBox();
        jcGesDiatomeen = new javax.swing.JCheckBox();
        lblDiatTrophie = new javax.swing.JLabel();
        lblDiatTrophieVal = new javax.swing.JLabel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblMZB.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblMZB, gridBagConstraints);

        lblSTITVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblSTITVal.setPreferredSize(new java.awt.Dimension(150, 20));

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTITVal, gridBagConstraints);

        lblMacPhyto.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblMacPhyto.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblMacPhyto, gridBagConstraints);

        lblSTIT.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblSTIT.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTIT, gridBagConstraints);

        lblSTIM.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblSTIM.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTIM, gridBagConstraints);

        lblPerlodes.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblPerlodes.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPerlodes, gridBagConstraints);

        lblSi.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblSi.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel4.add(lblSi, gridBagConstraints);

        lblSTIMVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblSTIMVal.setPreferredSize(new java.awt.Dimension(150, 20));

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTIMVal, gridBagConstraints);

        lblPerlodesVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblPerlodesVal.setPreferredSize(new java.awt.Dimension(150, 20));

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPerlodesVal, gridBagConstraints);

        lblSIVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblSIVal.setPreferredSize(new java.awt.Dimension(150, 20));

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
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSIVal, gridBagConstraints);

        lblSTIMP.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblSTIMP.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTIMP, gridBagConstraints);

        lblPhylib.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblPhylib.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhylib, gridBagConstraints);

        lblPhytob.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblPhytob.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel4.add(lblPhytob, gridBagConstraints);

        lblMacPhyten.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblMacPhyten.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel4.add(lblMacPhyten, gridBagConstraints);

        lblSTIMPVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblSTIMPVal.setPreferredSize(new java.awt.Dimension(150, 20));

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSTIMPVal, gridBagConstraints);

        lblPhylibVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblPhylibVal.setPreferredSize(new java.awt.Dimension(150, 20));

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhylibVal, gridBagConstraints);

        lblPhytobVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblPhytobVal.setPreferredSize(new java.awt.Dimension(150, 20));

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhytobVal, gridBagConstraints);

        lblMacPhytenVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblMacPhytenVal.setPreferredSize(new java.awt.Dimension(150, 20));

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblMacPhytenVal, gridBagConstraints);

        lblDiatomeenVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblDiatomeenVal.setPreferredSize(new java.awt.Dimension(150, 20));

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblDiatomeenVal, gridBagConstraints);

        lblDiatomeen.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblDiatomeen.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel4.add(lblDiatomeen, gridBagConstraints);

        lblFisch.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblFisch.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(lblFisch, gridBagConstraints);

        lblPhytoPl.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblPhytoPl.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(lblPhytoPl, gridBagConstraints);

        lblFiBS.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblFiBS.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblFiBS, gridBagConstraints);

        lblFiBSVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblFiBSVal.setPreferredSize(new java.awt.Dimension(150, 20));

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
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblFiBSVal, gridBagConstraints);

        lblPhyto.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenEditor.lblPhyto.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhyto, gridBagConstraints);

        lblPhytoVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblPhytoVal.setPreferredSize(new java.awt.Dimension(150, 20));

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
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhytoVal, gridBagConstraints);

        lblGkMzb.setMinimumSize(new java.awt.Dimension(150, 17));
        lblGkMzb.setPreferredSize(new java.awt.Dimension(150, 17));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_mzb_gesamt.name}"),
                lblGkMzb,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblGkMzb, gridBagConstraints);

        lblGkFische.setMinimumSize(new java.awt.Dimension(150, 17));
        lblGkFische.setPreferredSize(new java.awt.Dimension(150, 17));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_fische_gesamt.name}"),
                lblGkFische,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(lblGkFische, gridBagConstraints);

        lblGkMacPhyt.setMinimumSize(new java.awt.Dimension(150, 17));
        lblGkMacPhyt.setPreferredSize(new java.awt.Dimension(150, 17));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_mp_gesamt.name}"),
                lblGkMacPhyt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblGkMacPhyt, gridBagConstraints);

        lblGkPhyt.setMinimumSize(new java.awt.Dimension(150, 17));
        lblGkPhyt.setPreferredSize(new java.awt.Dimension(150, 17));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_phytoplankton_gesamt.name}"),
                lblGkPhyt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(lblGkPhyt, gridBagConstraints);

        lblMZBBem.setMinimumSize(new java.awt.Dimension(200, 17));
        lblMZBBem.setPreferredSize(new java.awt.Dimension(200, 17));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_mzb}"),
                lblMZBBem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_mzb}"),
                lblMZBBem,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblMZBBem, gridBagConstraints);

        lblFischeBem.setMinimumSize(new java.awt.Dimension(200, 17));
        lblFischeBem.setPreferredSize(new java.awt.Dimension(200, 17));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_fische}"),
                lblFischeBem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_fische}"),
                lblFischeBem,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(lblFischeBem, gridBagConstraints);

        lblMacPhytBem.setMinimumSize(new java.awt.Dimension(200, 17));
        lblMacPhytBem.setPreferredSize(new java.awt.Dimension(200, 17));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_mp}"),
                lblMacPhytBem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_mp}"),
                lblMacPhytBem,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblMacPhytBem, gridBagConstraints);

        lblPhytBem.setMinimumSize(new java.awt.Dimension(200, 17));
        lblPhytBem.setPreferredSize(new java.awt.Dimension(200, 17));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_phypl}"),
                lblPhytBem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_phypl}"),
                lblPhytBem,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel4.add(lblPhytBem, gridBagConstraints);

        jcGesPerlodes.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstMessungenRenderer.jcGesSTIT.text")); // NOI18N
        jcGesPerlodes.setContentAreaFilled(false);
        jcGesPerlodes.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.perlodes_gesichert}"),
                jcGesPerlodes,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jcGesPerlodes, gridBagConstraints);

        jcGesPerlodesAd.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstMessungenEditor.jcGesSTIT.text")); // NOI18N
        jcGesPerlodesAd.setContentAreaFilled(false);
        jcGesPerlodesAd.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.perlodes_ad_gesichert}"),
                jcGesPerlodesAd,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jcGesPerlodesAd, gridBagConstraints);

        jcGesSI.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstMessungenEditor.jcGesSTIT.text")); // NOI18N
        jcGesSI.setContentAreaFilled(false);
        jcGesSI.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.perlodes_sl_gesichert}"),
                jcGesSI,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jcGesSI, gridBagConstraints);

        lblPerlodesAd.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenRenderer.lblPerlodesAd.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel4.add(lblPerlodesAd, gridBagConstraints);

        lblPerlodesValAd.setMinimumSize(new java.awt.Dimension(150, 20));
        lblPerlodesValAd.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.perlodes_ad.name}"),
                lblPerlodesValAd,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPerlodesValAd, gridBagConstraints);

        jcGesPhylib.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstMessungenEditor.jcGesSTIT.text")); // NOI18N
        jcGesPhylib.setContentAreaFilled(false);
        jcGesPhylib.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phylib_gesichert}"),
                jcGesPhylib,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jcGesPhylib, gridBagConstraints);

        jcGesPhyto.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstMessungenEditor.jcGesSTIT.text")); // NOI18N
        jcGesPhyto.setContentAreaFilled(false);
        jcGesPhyto.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phylib_phytobenthos_gesichert}"),
                jcGesPhyto,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jcGesPhyto, gridBagConstraints);

        jcGesMacPhyto.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstMessungenEditor.jcGesSTIT.text")); // NOI18N
        jcGesMacPhyto.setContentAreaFilled(false);
        jcGesMacPhyto.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phylib_makrophyten_gesichert}"),
                jcGesMacPhyto,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jcGesMacPhyto, gridBagConstraints);

        jcGesDiatomeen.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstMessungenEditor.jcGesSTIT.text")); // NOI18N
        jcGesDiatomeen.setContentAreaFilled(false);
        jcGesDiatomeen.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phylib_diatomeen_gesichert}"),
                jcGesDiatomeen,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jcGesDiatomeen, gridBagConstraints);

        lblDiatTrophie.setText(org.openide.util.NbBundle.getMessage(
                BioMstMessungenRenderer.class,
                "BioMstStammdatenRenderer.lblDiatTrophie.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        jPanel4.add(lblDiatTrophie, gridBagConstraints);

        lblDiatTrophieVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblDiatTrophieVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phylib_diat_trophie.name}"),
                lblDiatTrophieVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblDiatTrophieVal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
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
    public JComponent getFooterComponent() {
        return panFooter;
    }
}
