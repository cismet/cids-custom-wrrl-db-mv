/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.awt.EventQueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.cids.custom.wrrl_db_mv.util.YesNoConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ChemieMstMessungenEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            ChemieMstMessungenEditor.class);

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.ChemieMstMessungenPanOne chemieMstMessungenPanOne1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblBemerkMst;
    private javax.swing.JLabel lblEqsHmMst;
    private javax.swing.JLabel lblEqsHmMstVal;
    private javax.swing.JLabel lblEqsIndPolMst;
    private javax.swing.JLabel lblEqsOthplMst;
    private javax.swing.JLabel lblEqsOthplVal;
    private javax.swing.JLabel lblEqsPesticMst;
    private javax.swing.JLabel lblEqsPesticMstVal;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblIndPolMstVal;
    private javax.swing.JLabel lblUEcoBemerkMst;
    private javax.swing.JLabel lblUEcoMst;
    private javax.swing.JLabel lblUEcoMstVal;
    private javax.swing.JLabel lblUNonComp;
    private javax.swing.JLabel lblUNonCompBemerkMst;
    private javax.swing.JLabel lblUNonCompVal;
    private javax.swing.JLabel lblYesNoMst;
    private javax.swing.JPanel panFooter;
    private javax.swing.JLabel txtEqsHmMst;
    private javax.swing.JLabel txtEqsOthplBemerkungMst;
    private javax.swing.JLabel txtEqsPesticBemerkMst;
    private javax.swing.JLabel txtIndpolBemerkMst;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public ChemieMstMessungenEditor() {
        this(false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public ChemieMstMessungenEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
        clearForm();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        setCidsBean(cidsBean, null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     * @param  parent    DOCUMENT ME!
     */
    public void setCidsBean(final CidsBean cidsBean, final CidsBean parent) {
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

            if ((parent != null) && !readOnly) {
                EventQueue.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            cidsBean.addPropertyChangeListener(new SubObjectPropertyChangedListener(parent));
                        }
                    });
            }
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

        chemieMstMessungenPanOne1.setCidsBean(cidsBean);
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     */
    private void clearForm() {
        lblEqsHmMstVal.setText("");
        lblEqsOthplVal.setText("");
        lblEqsPesticMstVal.setText("");
        lblFoot.setText("");
        lblIndPolMstVal.setText("");
        lblUNonCompBemerkMst.setText("");
        lblUEcoBemerkMst.setText("");
        lblUNonCompVal.setText("");
        lblUEcoMstVal.setText("");
        txtEqsHmMst.setText("");
        txtEqsOthplBemerkungMst.setText("");
        txtEqsPesticBemerkMst.setText("");
        txtIndpolBemerkMst.setText("");
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    private void setEnable(final boolean enable) {
        chemieMstMessungenPanOne1.setEnable(enable);
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
        chemieMstMessungenPanOne1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.ChemieMstMessungenPanOne();
        lblEqsHmMst = new javax.swing.JLabel();
        lblEqsHmMstVal = new javax.swing.JLabel();
        txtEqsHmMst = new javax.swing.JLabel();
        lblBemerkMst = new javax.swing.JLabel();
        lblYesNoMst = new javax.swing.JLabel();
        lblEqsPesticMst = new javax.swing.JLabel();
        lblEqsIndPolMst = new javax.swing.JLabel();
        lblEqsOthplMst = new javax.swing.JLabel();
        lblUNonComp = new javax.swing.JLabel();
        lblEqsPesticMstVal = new javax.swing.JLabel();
        lblIndPolMstVal = new javax.swing.JLabel();
        lblEqsOthplVal = new javax.swing.JLabel();
        lblUNonCompVal = new javax.swing.JLabel();
        lblUNonCompBemerkMst = new javax.swing.JLabel();
        txtEqsOthplBemerkungMst = new javax.swing.JLabel();
        txtIndpolBemerkMst = new javax.swing.JLabel();
        txtEqsPesticBemerkMst = new javax.swing.JLabel();
        lblUEcoMst = new javax.swing.JLabel();
        lblUEcoMstVal = new javax.swing.JLabel();
        lblUEcoBemerkMst = new javax.swing.JLabel();

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(35, 0, 5, 5);
        jPanel4.add(chemieMstMessungenPanOne1, gridBagConstraints);

        lblEqsHmMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
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

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_schwermetalle}"),
                lblEqsHmMstVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
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
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtEqsHmMst, gridBagConstraints);

        lblBemerkMst.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBemerkMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
                "WkFgPanThirteen.lblBemerkMst.text")); // NOI18N
        lblBemerkMst.setMinimumSize(new java.awt.Dimension(330, 20));
        lblBemerkMst.setPreferredSize(new java.awt.Dimension(330, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblBemerkMst, gridBagConstraints);

        lblYesNoMst.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblYesNoMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
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
                ChemieMstMessungenEditor.class,
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
                ChemieMstMessungenEditor.class,
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
                ChemieMstMessungenEditor.class,
                "WkFgPanThirteen.lblEqsOthplMst.text")); // NOI18N
        lblEqsOthplMst.setMinimumSize(new java.awt.Dimension(230, 20));
        lblEqsOthplMst.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblEqsOthplMst, gridBagConstraints);

        lblUNonComp.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
                "WkFgPanThirteen.lblNonCompMst.text")); // NOI18N
        lblUNonComp.setMinimumSize(new java.awt.Dimension(230, 20));
        lblUNonComp.setPreferredSize(new java.awt.Dimension(230, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblUNonComp, gridBagConstraints);

        lblEqsPesticMstVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblEqsPesticMstVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_psm}"),
                lblEqsPesticMstVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
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
        binding.setSourceNullValue("");
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
        binding.setSourceNullValue("");
        binding.setConverter(YesNoConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblEqsOthplVal, gridBagConstraints);

        lblUNonCompVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblUNonCompVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_andere_nat}"),
                lblUNonCompVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setConverter(YesNoConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblUNonCompVal, gridBagConstraints);

        lblUNonCompBemerkMst.setMinimumSize(new java.awt.Dimension(330, 20));
        lblUNonCompBemerkMst.setPreferredSize(new java.awt.Dimension(330, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.u_andere_nat_welche}"),
                lblUNonCompBemerkMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
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
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
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
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
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
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtEqsPesticBemerkMst, gridBagConstraints);

        lblUEcoMst.setText(org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenEditor.class,
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
        binding.setSourceNullValue("");
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
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblUEcoBemerkMst, gridBagConstraints);

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
    public void editorClosed(final EditorClosedEvent event) {
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

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class SubObjectPropertyChangedListener implements PropertyChangeListener {

        //~ Instance fields ----------------------------------------------------

        private final CidsBean parent;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SubObjectPropertyChangedListener object.
         *
         * @param  parent  DOCUMENT ME!
         */
        public SubObjectPropertyChangedListener(final CidsBean parent) {
            this.parent = parent;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (parent != null) {
                parent.setArtificialChangeFlag(true);
            }
        }
    }
}
