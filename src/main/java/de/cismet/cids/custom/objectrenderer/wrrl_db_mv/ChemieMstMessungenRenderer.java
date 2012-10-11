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

import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.ChemieMstMessungenEditor;
import de.cismet.cids.custom.wrrl_db_mv.util.YesNoConverter;

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
public class ChemieMstMessungenRenderer extends JPanel implements CidsBeanRenderer, FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            ChemieMstMessungenRenderer.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbl90PerzentilAmm;
    private javax.swing.JLabel lbl90PerzentilChlor;
    private javax.swing.JLabel lbl90PerzentilGesN;
    private javax.swing.JLabel lbl90PerzentilNit;
    private javax.swing.JLabel lbl90PerzentilOrth;
    private javax.swing.JLabel lbl90PerzentilPhos;
    private javax.swing.JLabel lblBemerkMst;
    private javax.swing.JLabel lblEqsHmMst;
    private javax.swing.JLabel lblEqsHmMstVal;
    private javax.swing.JLabel lblEqsIndPolMst;
    private javax.swing.JLabel lblEqsOthplMst;
    private javax.swing.JLabel lblEqsOthplVal;
    private javax.swing.JLabel lblEqsPesticMst;
    private javax.swing.JLabel lblEqsPesticMstVal;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGkLawaAmm;
    private javax.swing.JLabel lblGkLawaChlor;
    private javax.swing.JLabel lblGkLawaGesN;
    private javax.swing.JLabel lblGkLawaNit;
    private javax.swing.JLabel lblGkLawaOrth;
    private javax.swing.JLabel lblGkLawaPhos;
    private javax.swing.JLabel lblGkPhysChem;
    private javax.swing.JLabel lblIndPolMstVal;
    private javax.swing.JLabel lblLawa;
    private javax.swing.JLabel lblLawa1;
    private javax.swing.JLabel lblLawa2;
    private javax.swing.JLabel lblLawa3;
    private javax.swing.JLabel lblLawa4;
    private javax.swing.JLabel lblLawa5;
    private javax.swing.JLabel lblMittelAmm;
    private javax.swing.JLabel lblMittelChlor;
    private javax.swing.JLabel lblMittelGesN;
    private javax.swing.JLabel lblMittelNit;
    private javax.swing.JLabel lblMittelO2;
    private javax.swing.JLabel lblMittelOrth;
    private javax.swing.JLabel lblMittelPhos;
    private javax.swing.JLabel lblOWertAmm;
    private javax.swing.JLabel lblOWertChlor;
    private javax.swing.JLabel lblOWertO2;
    private javax.swing.JLabel lblOWertOrth;
    private javax.swing.JLabel lblOWertPhos;
    private javax.swing.JLabel lblPhyChem;
    private javax.swing.JLabel lblPhysChemBem;
    private javax.swing.JLabel lblRakon;
    private javax.swing.JLabel lblRakon1;
    private javax.swing.JLabel lblRakon3;
    private javax.swing.JLabel lblRakon4;
    private javax.swing.JLabel lblRakon5;
    private javax.swing.JLabel lblUEcoBemerkMst;
    private javax.swing.JLabel lblUEcoMst;
    private javax.swing.JLabel lblUEcoMstVal;
    private javax.swing.JLabel lblUNonCompBemerkMst;
    private javax.swing.JLabel lblUNonCompMst;
    private javax.swing.JLabel lblUNonCompMstVal;
    private javax.swing.JLabel lblYesNoMst;
    private javax.swing.JPanel panAmm;
    private javax.swing.JPanel panChlor;
    private javax.swing.JPanel panFooter;
    private javax.swing.JPanel panGesN;
    private javax.swing.JPanel panNit;
    private javax.swing.JPanel panO2;
    private javax.swing.JPanel panOrtho;
    private javax.swing.JPanel panPhos;
    private javax.swing.JLabel txtEqsHmMst;
    private javax.swing.JLabel txtEqsOthplBemerkungMst;
    private javax.swing.JLabel txtEqsPesticBemerkMst;
    private javax.swing.JLabel txtIndpolBemerkMst;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new LawaEditor object.
     */
    public ChemieMstMessungenRenderer() {
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
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        setColors();
                    }
                });
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
        lbl90PerzentilAmm.setText("");
        lbl90PerzentilChlor.setText("");
        lbl90PerzentilGesN.setText("");
        lbl90PerzentilNit.setText("");
        lbl90PerzentilOrth.setText("");
        lbl90PerzentilPhos.setText("");
        lblEqsHmMstVal.setText("");
        lblEqsOthplVal.setText("");
        lblEqsPesticMstVal.setText("");
        lblFoot.setText("");
        lblGkLawaAmm.setText("");
        lblGkLawaChlor.setText("");
        lblGkLawaGesN.setText("");
        lblGkLawaNit.setText("");
        lblGkLawaOrth.setText("");
        lblGkLawaPhos.setText("");
        lblIndPolMstVal.setText("");
        lblMittelAmm.setText("");
        lblMittelChlor.setText("");
        lblMittelGesN.setText("");
        lblMittelNit.setText("");
        lblMittelO2.setText("");
        lblMittelOrth.setText("");
        lblMittelPhos.setText("");
        lblOWertAmm.setText("");
        lblOWertChlor.setText("");
        lblOWertO2.setText("");
        lblOWertOrth.setText("");
        lblOWertPhos.setText("");
        lblUNonCompBemerkMst.setText("");
        lblUEcoBemerkMst.setText("");
        lblUNonCompMstVal.setText("");
        lblUEcoMstVal.setText("");
        txtEqsHmMst.setText("");
        txtEqsOthplBemerkungMst.setText("");
        txtEqsPesticBemerkMst.setText("");
        txtIndpolBemerkMst.setText("");
        lblGkPhysChem.setText("");
        lblPhysChemBem.setText("");
        lblMittelOrth.setOpaque(false);
        lblMittelAmm.setOpaque(false);
        lblMittelChlor.setOpaque(false);
        lblMittelGesN.setOpaque(false);
        lblMittelNit.setOpaque(false);
        lblMittelO2.setOpaque(false);
        lblMittelPhos.setOpaque(false);
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
        lblPhyChem = new javax.swing.JLabel();
        panOrtho = new javax.swing.JPanel();
        lblLawa = new javax.swing.JLabel();
        lblRakon = new javax.swing.JLabel();
        lbl90PerzentilOrth = new javax.swing.JLabel();
        lblGkLawaOrth = new javax.swing.JLabel();
        lblOWertOrth = new javax.swing.JLabel();
        lblMittelOrth = new javax.swing.JLabel();
        panAmm = new javax.swing.JPanel();
        lblLawa1 = new javax.swing.JLabel();
        lblRakon1 = new javax.swing.JLabel();
        lbl90PerzentilAmm = new javax.swing.JLabel();
        lblGkLawaAmm = new javax.swing.JLabel();
        lblOWertAmm = new javax.swing.JLabel();
        lblMittelAmm = new javax.swing.JLabel();
        panGesN = new javax.swing.JPanel();
        lblLawa2 = new javax.swing.JLabel();
        lbl90PerzentilGesN = new javax.swing.JLabel();
        lblGkLawaGesN = new javax.swing.JLabel();
        lblMittelGesN = new javax.swing.JLabel();
        panPhos = new javax.swing.JPanel();
        lblLawa3 = new javax.swing.JLabel();
        lblRakon3 = new javax.swing.JLabel();
        lbl90PerzentilPhos = new javax.swing.JLabel();
        lblGkLawaPhos = new javax.swing.JLabel();
        lblOWertPhos = new javax.swing.JLabel();
        lblMittelPhos = new javax.swing.JLabel();
        panNit = new javax.swing.JPanel();
        lblLawa4 = new javax.swing.JLabel();
        lbl90PerzentilNit = new javax.swing.JLabel();
        lblGkLawaNit = new javax.swing.JLabel();
        lblMittelNit = new javax.swing.JLabel();
        panChlor = new javax.swing.JPanel();
        lblLawa5 = new javax.swing.JLabel();
        lblRakon5 = new javax.swing.JLabel();
        lbl90PerzentilChlor = new javax.swing.JLabel();
        lblGkLawaChlor = new javax.swing.JLabel();
        lblOWertChlor = new javax.swing.JLabel();
        lblMittelChlor = new javax.swing.JLabel();
        panO2 = new javax.swing.JPanel();
        lblRakon4 = new javax.swing.JLabel();
        lblOWertO2 = new javax.swing.JLabel();
        lblMittelO2 = new javax.swing.JLabel();
        lblEqsHmMst = new javax.swing.JLabel();
        lblEqsHmMstVal = new javax.swing.JLabel();
        txtEqsHmMst = new javax.swing.JLabel();
        lblBemerkMst = new javax.swing.JLabel();
        lblYesNoMst = new javax.swing.JLabel();
        lblEqsPesticMst = new javax.swing.JLabel();
        lblEqsIndPolMst = new javax.swing.JLabel();
        lblEqsOthplMst = new javax.swing.JLabel();
        lblUNonCompMst = new javax.swing.JLabel();
        lblEqsPesticMstVal = new javax.swing.JLabel();
        lblIndPolMstVal = new javax.swing.JLabel();
        lblEqsOthplVal = new javax.swing.JLabel();
        lblUNonCompMstVal = new javax.swing.JLabel();
        lblUNonCompBemerkMst = new javax.swing.JLabel();
        txtEqsOthplBemerkungMst = new javax.swing.JLabel();
        txtIndpolBemerkMst = new javax.swing.JLabel();
        txtEqsPesticBemerkMst = new javax.swing.JLabel();
        lblUEcoMst = new javax.swing.JLabel();
        lblUEcoMstVal = new javax.swing.JLabel();
        lblUEcoBemerkMst = new javax.swing.JLabel();
        lblGkPhysChem = new javax.swing.JLabel();
        lblPhysChemBem = new javax.swing.JLabel();

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

        lblPhyChem.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "ChemieMstMessungenEditor.lblPhysChem.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhyChem, gridBagConstraints);

        panOrtho.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Orthophosphat"));
        panOrtho.setOpaque(false);
        panOrtho.setLayout(new java.awt.GridBagLayout());

        lblLawa.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lblLawa, gridBagConstraints);

        lblRakon.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblRakon.text")); // NOI18N
        lblRakon.setMinimumSize(new java.awt.Dimension(45, 20));
        lblRakon.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lblRakon, gridBagConstraints);

        lbl90PerzentilOrth.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilOrth.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilOrth.setPreferredSize(new java.awt.Dimension(110, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.opo4_90_perzentil}"),
                lbl90PerzentilOrth,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lbl90PerzentilOrth, gridBagConstraints);

        lblGkLawaOrth.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaOrth.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaOrth.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.opo4_gk_lawa.name}"),
                lblGkLawaOrth,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lblGkLawaOrth, gridBagConstraints);

        lblOWertOrth.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblOWert.toolTipText")); // NOI18N
        lblOWertOrth.setMinimumSize(new java.awt.Dimension(110, 20));
        lblOWertOrth.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.opo4_owert_rakon}"),
                lblOWertOrth,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lblOWertOrth, gridBagConstraints);

        lblMittelOrth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMittelOrth.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelOrth.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelOrth.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.opo4_mittelwert}"),
                lblMittelOrth,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lblMittelOrth, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(panOrtho, gridBagConstraints);

        panAmm.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Ammonium-N"));
        panAmm.setOpaque(false);
        panAmm.setLayout(new java.awt.GridBagLayout());

        lblLawa1.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa1.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa1.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lblLawa1, gridBagConstraints);

        lblRakon1.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblRakon.text")); // NOI18N
        lblRakon1.setMinimumSize(new java.awt.Dimension(45, 20));
        lblRakon1.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lblRakon1, gridBagConstraints);

        lbl90PerzentilAmm.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilAmm.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilAmm.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.nh4_90_perzentil}"),
                lbl90PerzentilAmm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lbl90PerzentilAmm, gridBagConstraints);

        lblGkLawaAmm.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaAmm.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaAmm.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.nh4_gk_lawa.name}"),
                lblGkLawaAmm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lblGkLawaAmm, gridBagConstraints);

        lblOWertAmm.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblOWert.toolTipText")); // NOI18N
        lblOWertAmm.setMinimumSize(new java.awt.Dimension(110, 20));
        lblOWertAmm.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.nh4_owert_rakon}"),
                lblOWertAmm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lblOWertAmm, gridBagConstraints);

        lblMittelAmm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMittelAmm.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelAmm.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelAmm.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.nh4_mittelwert}"),
                lblMittelAmm,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lblMittelAmm, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(panAmm, gridBagConstraints);

        panGesN.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Gesamt-N"));
        panGesN.setOpaque(false);
        panGesN.setLayout(new java.awt.GridBagLayout());

        lblLawa2.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa2.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa2.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panGesN.add(lblLawa2, gridBagConstraints);

        lbl90PerzentilGesN.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilGesN.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilGesN.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ges_n_90_perzentil}"),
                lbl90PerzentilGesN,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panGesN.add(lbl90PerzentilGesN, gridBagConstraints);

        lblGkLawaGesN.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaGesN.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaGesN.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ges_n_gk_lawa.name}"),
                lblGkLawaGesN,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panGesN.add(lblGkLawaGesN, gridBagConstraints);

        lblMittelGesN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMittelGesN.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelGesN.setMinimumSize(new java.awt.Dimension(120, 20));
        lblMittelGesN.setPreferredSize(new java.awt.Dimension(120, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ges_n_mittelwert}"),
                lblMittelGesN,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panGesN.add(lblMittelGesN, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(panGesN, gridBagConstraints);

        panPhos.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Gesamtphosphat"));
        panPhos.setOpaque(false);
        panPhos.setLayout(new java.awt.GridBagLayout());

        lblLawa3.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa3.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa3.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lblLawa3, gridBagConstraints);

        lblRakon3.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblRakon.text")); // NOI18N
        lblRakon3.setMinimumSize(new java.awt.Dimension(45, 20));
        lblRakon3.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lblRakon3, gridBagConstraints);

        lbl90PerzentilPhos.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilPhos.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilPhos.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ges_p_90_perzentil}"),
                lbl90PerzentilPhos,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lbl90PerzentilPhos, gridBagConstraints);

        lblGkLawaPhos.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaPhos.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaPhos.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ges_p_gk_lawa.name}"),
                lblGkLawaPhos,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lblGkLawaPhos, gridBagConstraints);

        lblOWertPhos.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblOWert.toolTipText")); // NOI18N
        lblOWertPhos.setMinimumSize(new java.awt.Dimension(110, 20));
        lblOWertPhos.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ges_p_owert_rakon}"),
                lblOWertPhos,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lblOWertPhos, gridBagConstraints);

        lblMittelPhos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMittelPhos.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelPhos.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelPhos.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ges_p_mittelwert}"),
                lblMittelPhos,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lblMittelPhos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(panPhos, gridBagConstraints);

        panNit.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Nitrat-N"));
        panNit.setOpaque(false);
        panNit.setLayout(new java.awt.GridBagLayout());

        lblLawa4.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa4.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa4.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panNit.add(lblLawa4, gridBagConstraints);

        lbl90PerzentilNit.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilNit.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilNit.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.no3_n_90_perzentil}"),
                lbl90PerzentilNit,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panNit.add(lbl90PerzentilNit, gridBagConstraints);

        lblGkLawaNit.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaNit.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaNit.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.no3_n_gk_lawa.name}"),
                lblGkLawaNit,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panNit.add(lblGkLawaNit, gridBagConstraints);

        lblMittelNit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMittelNit.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelNit.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelNit.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.no3_n_mittelwert}"),
                lblMittelNit,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panNit.add(lblMittelNit, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(panNit, gridBagConstraints);

        panChlor.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Chlorid"));
        panChlor.setOpaque(false);
        panChlor.setLayout(new java.awt.GridBagLayout());

        lblLawa5.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa5.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa5.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lblLawa5, gridBagConstraints);

        lblRakon5.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblRakon.text")); // NOI18N
        lblRakon5.setMinimumSize(new java.awt.Dimension(45, 20));
        lblRakon5.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lblRakon5, gridBagConstraints);

        lbl90PerzentilChlor.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilChlor.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilChlor.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.cl_90_perzentil}"),
                lbl90PerzentilChlor,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lbl90PerzentilChlor, gridBagConstraints);

        lblGkLawaChlor.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaChlor.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaChlor.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.cl_gk_lawa.name}"),
                lblGkLawaChlor,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lblGkLawaChlor, gridBagConstraints);

        lblOWertChlor.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblOWert.toolTipText")); // NOI18N
        lblOWertChlor.setMinimumSize(new java.awt.Dimension(110, 20));
        lblOWertChlor.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.cl_owert_rakon}"),
                lblOWertChlor,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lblOWertChlor, gridBagConstraints);

        lblMittelChlor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMittelChlor.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelChlor.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelChlor.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.cl_mittelwert}"),
                lblMittelChlor,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lblMittelChlor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(panChlor, gridBagConstraints);

        panO2.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Sauerstoff"));
        panO2.setOpaque(false);
        panO2.setLayout(new java.awt.GridBagLayout());

        lblRakon4.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblRakon.text")); // NOI18N
        lblRakon4.setMinimumSize(new java.awt.Dimension(45, 20));
        lblRakon4.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panO2.add(lblRakon4, gridBagConstraints);

        lblOWertO2.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblOWert.toolTipText")); // NOI18N
        lblOWertO2.setMinimumSize(new java.awt.Dimension(110, 20));
        lblOWertO2.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.o2_owert_rakon}"),
                lblOWertO2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panO2.add(lblOWertO2, gridBagConstraints);

        lblMittelO2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMittelO2.setToolTipText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelO2.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelO2.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.o2_mittelwert}"),
                lblMittelO2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panO2.add(lblMittelO2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(panO2, gridBagConstraints);

        lblEqsHmMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanThirteen.lblEqsHmMst.text")); // NOI18N
        lblEqsHmMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblEqsHmMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblEqsHmMst, gridBagConstraints);

        lblEqsHmMstVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblEqsHmMstVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_schwermetalle}"),
                lblEqsHmMstVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setConverter(YesNoConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblEqsHmMstVal, gridBagConstraints);

        txtEqsHmMst.setMinimumSize(new java.awt.Dimension(330, 20));
        txtEqsHmMst.setPreferredSize(new java.awt.Dimension(330, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_schwermetalle_welche}"),
                txtEqsHmMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtEqsHmMst, gridBagConstraints);

        lblBemerkMst.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBemerkMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanThirteen.lblBemerkMst.text")); // NOI18N
        lblBemerkMst.setMinimumSize(new java.awt.Dimension(330, 20));
        lblBemerkMst.setPreferredSize(new java.awt.Dimension(330, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblBemerkMst, gridBagConstraints);

        lblYesNoMst.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblYesNoMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanThirteen.lblYesNoMst.text")); // NOI18N
        lblYesNoMst.setMinimumSize(new java.awt.Dimension(200, 20));
        lblYesNoMst.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblYesNoMst, gridBagConstraints);

        lblEqsPesticMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanThirteen.lblEqsPesticMst.text")); // NOI18N
        lblEqsPesticMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblEqsPesticMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblEqsPesticMst, gridBagConstraints);

        lblEqsIndPolMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanThirteen.lblEqsIndPolMst.text")); // NOI18N
        lblEqsIndPolMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblEqsIndPolMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblEqsIndPolMst, gridBagConstraints);

        lblEqsOthplMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanThirteen.lblEqsOthplMst.text")); // NOI18N
        lblEqsOthplMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblEqsOthplMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblEqsOthplMst, gridBagConstraints);

        lblUNonCompMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanThirteen.lblNonCompMst.text")); // NOI18N
        lblUNonCompMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblUNonCompMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblUNonCompMst, gridBagConstraints);

        lblEqsPesticMstVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblEqsPesticMstVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_psm}"),
                lblEqsPesticMstVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setConverter(YesNoConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblEqsPesticMstVal, gridBagConstraints);

        lblIndPolMstVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblIndPolMstVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_ind_stoffe}"),
                lblIndPolMstVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setConverter(YesNoConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblIndPolMstVal, gridBagConstraints);

        lblEqsOthplVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblEqsOthplVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_andere_stoffe}"),
                lblEqsOthplVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setConverter(YesNoConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblEqsOthplVal, gridBagConstraints);

        lblUNonCompMstVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblUNonCompMstVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_andere_nat}"),
                lblUNonCompMstVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setConverter(YesNoConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblUNonCompMstVal, gridBagConstraints);

        lblUNonCompBemerkMst.setMinimumSize(new java.awt.Dimension(330, 20));
        lblUNonCompBemerkMst.setPreferredSize(new java.awt.Dimension(330, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_andere_nat_welche}"),
                lblUNonCompBemerkMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblUNonCompBemerkMst, gridBagConstraints);

        txtEqsOthplBemerkungMst.setMinimumSize(new java.awt.Dimension(330, 20));
        txtEqsOthplBemerkungMst.setPreferredSize(new java.awt.Dimension(330, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_andere_stoffe_welche}"),
                txtEqsOthplBemerkungMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtEqsOthplBemerkungMst, gridBagConstraints);

        txtIndpolBemerkMst.setMinimumSize(new java.awt.Dimension(330, 20));
        txtIndpolBemerkMst.setPreferredSize(new java.awt.Dimension(330, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_ind_stoffe_welche}"),
                txtIndpolBemerkMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtIndpolBemerkMst, gridBagConstraints);

        txtEqsPesticBemerkMst.setMinimumSize(new java.awt.Dimension(330, 20));
        txtEqsPesticBemerkMst.setPreferredSize(new java.awt.Dimension(330, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_psm_welche}"),
                txtEqsPesticBemerkMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtEqsPesticBemerkMst, gridBagConstraints);

        lblUEcoMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenRenderer.class,
                "WkFgPanThirteen.lblEcoMst.text")); // NOI18N
        lblUEcoMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblUEcoMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblUEcoMst, gridBagConstraints);

        lblUEcoMstVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblUEcoMstVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_eco_stoffe}"),
                lblUEcoMstVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setConverter(YesNoConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblUEcoMstVal, gridBagConstraints);

        lblUEcoBemerkMst.setMinimumSize(new java.awt.Dimension(330, 20));
        lblUEcoBemerkMst.setPreferredSize(new java.awt.Dimension(330, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_eco_stoffe_welche}"),
                lblUEcoBemerkMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblUEcoBemerkMst, gridBagConstraints);

        lblGkPhysChem.setMinimumSize(new java.awt.Dimension(150, 20));
        lblGkPhysChem.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_mst.name}"),
                lblGkPhysChem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblGkPhysChem, gridBagConstraints);

        lblPhysChemBem.setMinimumSize(new java.awt.Dimension(200, 20));
        lblPhysChemBem.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_pc}"),
                lblPhysChemBem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblPhysChemBem, gridBagConstraints);

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
    private void setColors() {
        ChemieMstMessungenEditor.setColorOfField(lblMittelOrth, lblOWertOrth, lbl90PerzentilOrth, false);
        ChemieMstMessungenEditor.setColorOfField(lblMittelAmm, lblOWertAmm, lbl90PerzentilAmm, false);
        ChemieMstMessungenEditor.setColorOfField(lblMittelChlor, lblOWertChlor, lbl90PerzentilChlor, false);
        ChemieMstMessungenEditor.setColorOfField(lblMittelGesN, null, lbl90PerzentilGesN, false);
        ChemieMstMessungenEditor.setColorOfField(lblMittelNit, null, lbl90PerzentilNit, false);
        ChemieMstMessungenEditor.setColorOfField(lblMittelO2, lblOWertO2, null, true);
        ChemieMstMessungenEditor.setColorOfField(lblMittelPhos, lblOWertPhos, lbl90PerzentilPhos, false);
    }

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
