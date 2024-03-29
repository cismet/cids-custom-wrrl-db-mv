/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * BioMstStammdatenEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CoordinateConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;
import de.cismet.cids.editors.FieldStateDecider;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class BioMstStammdatenEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    DocumentListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BioMstStammdatenEditor.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "bio_mst_messungen");

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;
    private int measureNumber = 0;
    private boolean noDocumentUpdate = false;
    private List<CidsBean> beansToSave = new ArrayList<CidsBean>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.BioMstMessungenEditor bioMstMessungenEditor1;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.BioMstMessungenRenderer bioMstMessungenRenderer1;
    private javax.swing.JButton btnBack1;
    private javax.swing.JButton btnForward;
    private de.cismet.cids.editors.DefaultBindableCheckboxField defaultBindableCheckboxField1;
    private javax.swing.JLabel lblArt;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGew;
    private javax.swing.JLabel lblGewVal;
    private javax.swing.JLabel lblHR;
    private javax.swing.JLabel lblHRVal;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblLage;
    private javax.swing.JLabel lblLageVal;
    private javax.swing.JLabel lblLawa;
    private javax.swing.JLabel lblLawaVal;
    private javax.swing.JLabel lblMst;
    private javax.swing.JLabel lblMstCodeVal;
    private javax.swing.JLabel lblSti;
    private javax.swing.JLabel lblStiVal;
    private javax.swing.JLabel lblWk;
    private javax.swing.JLabel lblWkVal;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panScr;
    private javax.swing.JPanel panStamm;
    private javax.swing.JTextField txtJahr;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public BioMstStammdatenEditor() {
        this(false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public BioMstStammdatenEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
        txtJahr.getDocument().addDocumentListener(this);
        defaultBindableCheckboxField1.setReadOnly(readOnly);

        if (readOnly) {
            lblWkVal.setForeground(Color.BLUE);
            lblWkVal.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        final CidsBean bean = (CidsBean)cidsBean.getProperty("wk_fg");

                        if ((bean != null) && readOnly) {
                            ComponentRegistry.getRegistry()
                                    .getDescriptionPane()
                                    .gotoMetaObjectNode(new MetaObjectNode(bean), false);
                        }
                    }
                });
        }
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
            txtJahr.setText(String.valueOf(new GregorianCalendar().get(GregorianCalendar.YEAR)));
            refreshMeasures();
            bindingGroup.bind();
//            defaultBindableCheckboxField1.refreshCheckboxState(new FieldStateDecider() {
//
//                    @Override
//                    public boolean isCheckboxForClassActive(final MetaObject mo) {
//                        return true;
//                    }
//                }, false);
//            defaultBindableCheckboxField1.
        } else {
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        if (!readOnly) {
                            bioMstMessungenEditor1.setCidsBean(null);
                        } else {
                            bioMstMessungenRenderer1.setCidsBean(null);
                        }
                    }
                });
        }

        lblFoot.setText("");
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
        panStamm = new javax.swing.JPanel();
        lblGew = new javax.swing.JLabel();
        lblLage = new javax.swing.JLabel();
        lblMstCodeVal = new javax.swing.JLabel();
        lblGewVal = new javax.swing.JLabel();
        lblLageVal = new javax.swing.JLabel();
        lblWk = new javax.swing.JLabel();
        lblHR = new javax.swing.JLabel();
        lblLawa = new javax.swing.JLabel();
        lblWkVal = new javax.swing.JLabel();
        lblHRVal = new javax.swing.JLabel();
        lblLawaVal = new javax.swing.JLabel();
        lblSti = new javax.swing.JLabel();
        lblStiVal = new javax.swing.JLabel();
        lblMst = new javax.swing.JLabel();
        defaultBindableCheckboxField1 = new de.cismet.cids.editors.DefaultBindableCheckboxField();
        lblArt = new javax.swing.JLabel();
        if (!readOnly) {
            bioMstMessungenEditor1 = new BioMstMessungenEditor(readOnly);
        }
        panScr = new javax.swing.JPanel();
        txtJahr = new javax.swing.JTextField();
        btnBack1 = new javax.swing.JButton();
        btnForward = new javax.swing.JButton();
        if (readOnly) {
            bioMstMessungenRenderer1 = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.BioMstMessungenRenderer();
        }

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1000, 650));
        setPreferredSize(new java.awt.Dimension(1000, 650));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(1000, 650));
        panInfo.setPreferredSize(new java.awt.Dimension(1000, 650));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Messstation");
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        panStamm.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Stammdaten"));
        panStamm.setOpaque(false);
        panStamm.setLayout(new java.awt.GridBagLayout());

        lblGew.setText(org.openide.util.NbBundle.getMessage(
                BioMstStammdatenEditor.class,
                "BioMstStammdatenEditor.lblGew.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblGew, gridBagConstraints);

        lblLage.setText(org.openide.util.NbBundle.getMessage(
                BioMstStammdatenEditor.class,
                "BioMstStammdatenEditor.lblLage.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblLage, gridBagConstraints);

        lblMstCodeVal.setMinimumSize(new java.awt.Dimension(170, 20));
        lblMstCodeVal.setPreferredSize(new java.awt.Dimension(170, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.messstelle}"),
                lblMstCodeVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblMstCodeVal, gridBagConstraints);

        lblGewVal.setMinimumSize(new java.awt.Dimension(170, 20));
        lblGewVal.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewaesser}"),
                lblGewVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblGewVal, gridBagConstraints);

        lblLageVal.setMinimumSize(new java.awt.Dimension(170, 20));
        lblLageVal.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.lage}"),
                lblLageVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblLageVal, gridBagConstraints);

        lblWk.setText(org.openide.util.NbBundle.getMessage(
                BioMstStammdatenEditor.class,
                "BioMstStammdatenEditor.lblWk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblWk, gridBagConstraints);

        lblHR.setText(org.openide.util.NbBundle.getMessage(
                BioMstStammdatenEditor.class,
                "BioMstStammdatenEditor.lblHR.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblHR, gridBagConstraints);

        lblLawa.setText(org.openide.util.NbBundle.getMessage(
                BioMstStammdatenEditor.class,
                "BioMstStammdatenEditor.lblLawa.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblLawa, gridBagConstraints);

        lblWkVal.setMinimumSize(new java.awt.Dimension(170, 20));
        lblWkVal.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_fg.wk_k}"),
                lblWkVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblWkVal, gridBagConstraints);

        lblHRVal.setMinimumSize(new java.awt.Dimension(170, 20));
        lblHRVal.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.geom}"),
                lblHRVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("/");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new CoordinateConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblHRVal, gridBagConstraints);

        lblLawaVal.setMinimumSize(new java.awt.Dimension(170, 20));
        lblLawaVal.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.lawa_typ.value}-${cidsBean.lawa_typ.name}"),
                lblLawaVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblLawaVal, gridBagConstraints);

        lblSti.setText(org.openide.util.NbBundle.getMessage(
                BioMstStammdatenEditor.class,
                "BioMstStammdatenEditor.lblSti.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblSti, gridBagConstraints);

        lblStiVal.setMinimumSize(new java.awt.Dimension(170, 20));
        lblStiVal.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sti_typ}"),
                lblStiVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblStiVal, gridBagConstraints);

        lblMst.setText(org.openide.util.NbBundle.getMessage(
                BioMstStammdatenEditor.class,
                "BioMstStammdatenEditor.lblMst.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblMst, gridBagConstraints);

        defaultBindableCheckboxField1.setOpaque(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.art}"),
                defaultBindableCheckboxField1,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        panStamm.add(defaultBindableCheckboxField1, gridBagConstraints);

        lblArt.setText(org.openide.util.NbBundle.getMessage(
                BioMstStammdatenEditor.class,
                "BioMstStammdatenEditor.lblArt.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        panStamm.add(lblArt, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 10);
        panInfoContent.add(panStamm, gridBagConstraints);

        if (!readOnly) {
        }
        if (!readOnly) {
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
            gridBagConstraints.weighty = 1.0;
            panInfoContent.add(bioMstMessungenEditor1, gridBagConstraints);
        }

        panScr.setOpaque(false);
        panScr.setLayout(new java.awt.GridBagLayout());

        txtJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        txtJahr.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(txtJahr, gridBagConstraints);

        btnBack1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left.png")));          // NOI18N
        btnBack1.setBorder(null);
        btnBack1.setBorderPainted(false);
        btnBack1.setContentAreaFilled(false);
        btnBack1.setFocusPainted(false);
        btnBack1.setMaximumSize(new java.awt.Dimension(30, 30));
        btnBack1.setMinimumSize(new java.awt.Dimension(30, 30));
        btnBack1.setPreferredSize(new java.awt.Dimension(30, 30));
        btnBack1.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left-pressed.png")));  // NOI18N
        btnBack1.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left-selected.png"))); // NOI18N
        btnBack1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnBack1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(btnBack1, gridBagConstraints);

        btnForward.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right.png")));          // NOI18N
        btnForward.setBorder(null);
        btnForward.setBorderPainted(false);
        btnForward.setContentAreaFilled(false);
        btnForward.setFocusPainted(false);
        btnForward.setMaximumSize(new java.awt.Dimension(30, 30));
        btnForward.setMinimumSize(new java.awt.Dimension(30, 30));
        btnForward.setPreferredSize(new java.awt.Dimension(30, 30));
        btnForward.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right-pressed.png")));  // NOI18N
        btnForward.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right-selected.png"))); // NOI18N
        btnForward.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnForwardActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(btnForward, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        panInfoContent.add(panScr, gridBagConstraints);

        if (readOnly) {
        }
        if (readOnly) {
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
            gridBagConstraints.weighty = 1.0;
            panInfoContent.add(bioMstMessungenRenderer1, gridBagConstraints);
        }

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnBack1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnBack1ActionPerformed
        int year = getCurrentlyEnteredYear();

        if (--measureNumber < 0) {
            measureNumber = 0;
            --year;
        }

        noDocumentUpdate = true;
        txtJahr.setText(String.valueOf(year));
        noDocumentUpdate = false;

        final int newYear = year;
        new Thread(new Runnable() {

                @Override
                public void run() {
                    synchronized (BioMstStammdatenEditor.this) {
                        CidsBean measure = null;
                        int measureYear = newYear;

                        do {
                            txtJahr.setText(String.valueOf(measureYear));
                            measure = getDataForYear(measureYear, measureNumber);
                            showNewMeasure(measure);
                            --measureYear;
                        } while ((measure == null) && (measureYear > 2006));
                    }
                }
            }).start();
    } //GEN-LAST:event_btnBack1ActionPerformed
    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnForwardActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnForwardActionPerformed
        int year = getCurrentlyEnteredYear();

        noDocumentUpdate = true;
        txtJahr.setText(String.valueOf(year));
        ++measureNumber;
        final CidsBean measure = getDataForYear(year, measureNumber);

        if (measure == null) {
            measureNumber = 0;
            ++year;
            txtJahr.setText(String.valueOf(year));

            final int newYear = year;
            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        synchronized (BioMstStammdatenEditor.this) {
                            CidsBean measure = null;
                            int measureYear = newYear;
                            final int currentYear = (new GregorianCalendar()).get(GregorianCalendar.YEAR);

                            do {
                                txtJahr.setText(String.valueOf(measureYear));
                                measure = getDataForYear(measureYear, measureNumber);
                                showNewMeasure(measure);
                                ++measureYear;
                            } while ((measure == null) && (measureYear <= currentYear));
                        }
                    }
                }).start();
        } else {
            showNewMeasure(measure);
        }

        noDocumentUpdate = false;
    } //GEN-LAST:event_btnForwardActionPerformed

    /**
     * shows the measure of the currently entered year.
     */
    private void refreshMeasures() {
        final int year = getCurrentlyEnteredYear();
        measureNumber = 0;

        final CidsBean measure = getDataForYear(year, measureNumber);
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    showNewMeasure(measure);
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  measure  DOCUMENT ME!
     */
    private synchronized void showNewMeasure(final CidsBean measure) {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if (!readOnly) {
                        saveLastMeasure();
                        bioMstMessungenEditor1.setCidsBean(measure, cidsBean);
                    } else {
                        bioMstMessungenRenderer1.setCidsBean(measure);
                    }
                }
            });
    }

    /**
     * adds the last processed bean to the beansToSave list, if it is not in, yet.
     */
    private void saveLastMeasure() {
        if (!readOnly) {
            final CidsBean lastMeasure = bioMstMessungenEditor1.getCidsBean();

            if ((lastMeasure != null) && !beansToSave.contains(lastMeasure)) {
                beansToSave.add(lastMeasure);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int getCurrentlyEnteredYear() {
        try {
            return Integer.parseInt(txtJahr.getText());
        } catch (final NumberFormatException e) {
            return new GregorianCalendar().get(GregorianCalendar.YEAR);
        }
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
    public void editorClosed(final EditorClosedEvent event) {
    }

    @Override
    public boolean prepareForSave() {
        saveLastMeasure();
        for (final CidsBean tmp : beansToSave) {
            try {
                tmp.persist();
            } catch (final Exception e) {
                LOG.error("Exception ehile saving the last measure.", e);
            }
        }

        beansToSave.clear();
        return true;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   year    DOCUMENT ME!
     * @param   number  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getDataForYear(final int year, final int number) {
        try {
            String query = "select " + MC.getID() + ", m." + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
            query += " m, bio_mst_stammdaten s";                                                                // NOI18N
            query += " WHERE m.messstelle = s.id AND s.id = " + cidsBean.getProperty("id");                     // NOI18N
            query += " AND messjahr = " + year + " order by id asc";                                            // NOI18N

            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            if ((metaObjects != null) && (number >= 0) && (number < metaObjects.length)) {
                final CidsBean retVal = metaObjects[number].getBean();
                int index = -1;

                // if the bean is already in the beansToSave list, the bean from the list should be used
                if ((index = beansToSave.indexOf(retVal)) != -1) {
                    return beansToSave.get(index);
                } else {
                    beansToSave.add(retVal);
                    return retVal;
                }
            } else {
                return null;
            }
        } catch (final ConnectionException e) {
            LOG.error("Error while trying to receive measurements.", e); // NOI18N
            return null;
        }
    }

    @Override
    public void insertUpdate(final DocumentEvent e) {
        if (!noDocumentUpdate && (e.getDocument().getLength() == 4)) {
            refreshMeasures();
        }
    }

    @Override
    public void removeUpdate(final DocumentEvent e) {
        if (!noDocumentUpdate && (e.getDocument().getLength() == 4)) {
            refreshMeasures();
        }
    }

    @Override
    public void changedUpdate(final DocumentEvent e) {
        // nothing to do
    }
}
