/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * ChemieMstStammdatenEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.awt.EventQueue;

import java.util.GregorianCalendar;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CoordinateConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class BioMstFibsEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    DocumentListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final ConnectionContext CC = ConnectionContext.create(
            AbstractConnectionContext.Category.EDITOR,
            "BioMstFibsEditor");
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BioMstFibsEditor.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "bio_mst_messungen");

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;
    private int measureNumber = 0;
    private boolean noDocumentUpdate = false;
    private volatile boolean urlExists = true;
    private boolean noEditAllowed = true;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.BioMstFibsMessungenEditor bioMstFibsMessungenEditor1;
    private javax.swing.JButton btnBack1;
    private javax.swing.JButton btnForward;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFischTyp;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGew;
    private javax.swing.JLabel lblGewVal;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHoReVal;
    private javax.swing.JLabel lblHore;
    private javax.swing.JLabel lblLage;
    private javax.swing.JLabel lblLageVal;
    private javax.swing.JLabel lblLawa;
    private javax.swing.JLabel lblLawaVal;
    private javax.swing.JLabel lblMst;
    private javax.swing.JLabel lblMstCodeVal;
    private javax.swing.JLabel lblNwb;
    private javax.swing.JLabel lblNwbVal;
    private javax.swing.JLabel lblSatluVal;
    private javax.swing.JLabel lblStalu;
    private javax.swing.JLabel lblWk;
    private javax.swing.JLabel lblWkVal;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JList<String> listFisch;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.RoundedPanel panGeo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panScr;
    private javax.swing.JPanel panStamm;
    private javax.swing.JTextField txtJahr;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public BioMstFibsEditor() {
        this(false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public BioMstFibsEditor(final boolean readOnly) {
        this(readOnly, false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     * @param  embedded  DOCUMENT ME!
     */
    public BioMstFibsEditor(final boolean readOnly, final boolean embedded) {
        this.readOnly = readOnly;
        try {
            noEditAllowed = !SessionManager.getSession()
                        .getUser()
                        .getUserGroup()
                        .getName()
                        .equalsIgnoreCase("Administratoren");
        } catch (Exception e) {
            LOG.error("Cannot check user group", e);
        }
        initComponents();
        lblFischTyp.setVisible(false);
        jScrollPane1.setVisible(false);
        listFisch.setVisible(false);

        linearReferencedLineEditor.setDrawingFeaturesEnabled(!readOnly);
        linearReferencedLineEditor.setLineField("linie"); // NOI18N
        linearReferencedLineEditor.setOtherLinesEnabled(false);

        if (embedded) {
            panStamm.setVisible(false);
        }

        txtJahr.getDocument().addDocumentListener(this);

//        if (readOnly) {
//            lblWkGwVal.setForeground(Color.BLUE);
//            lblWkGwVal.addMouseListener(new MouseAdapter() {
//
//                    @Override
//                    public void mouseClicked(final MouseEvent e) {
//                        final CidsBean bean = (CidsBean)cidsBean.getProperty("wk_gw");
//
//                        if ((bean != null) && readOnly) {
//                            ComponentRegistry.getRegistry()
//                                    .getDescriptionPane()
//                                    .gotoMetaObject(bean.getMetaObject(), "");
//                        }
//                    }
//                });
//        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();

        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            urlExists = true;
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean,
                CC);
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        txtJahr.setText(String.valueOf(new GregorianCalendar().get(GregorianCalendar.YEAR)));
                    }
                });
            refreshMeasures();
            bindingGroup.bind();
            linearReferencedLineEditor.setCidsBean(cidsBean);
        } else {
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        bioMstFibsMessungenEditor1.setCidsBean(null);
                    }
                });
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

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panStamm = new javax.swing.JPanel();
        lblMst = new javax.swing.JLabel();
        lblGew = new javax.swing.JLabel();
        lblNwb = new javax.swing.JLabel();
        lblHore = new javax.swing.JLabel();
        lblMstCodeVal = new javax.swing.JLabel();
        lblGewVal = new javax.swing.JLabel();
        lblNwbVal = new javax.swing.JLabel();
        lblHoReVal = new javax.swing.JLabel();
        lblWk = new javax.swing.JLabel();
        lblWkVal = new javax.swing.JLabel();
        lblFischTyp = new javax.swing.JLabel();
        lblLawa = new javax.swing.JLabel();
        lblLawaVal = new javax.swing.JLabel();
        lblLage = new javax.swing.JLabel();
        lblLageVal = new javax.swing.JLabel();
        lblStalu = new javax.swing.JLabel();
        lblSatluVal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listFisch = new javax.swing.JList<>();
        panScr = new javax.swing.JPanel();
        txtJahr = new javax.swing.JTextField();
        btnBack1 = new javax.swing.JButton();
        btnForward = new javax.swing.JButton();
        bioMstFibsMessungenEditor1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.BioMstFibsMessungenEditor();
        panGeo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        linearReferencedLineEditor = ((readOnly || noEditAllowed)
                ? new LinearReferencedLineRenderer(true)
                : new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor());
        jPanel1 = new javax.swing.JPanel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1400, 600));
        setPreferredSize(new java.awt.Dimension(1400, 600));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(1400, 1200));
        panInfo.setPreferredSize(new java.awt.Dimension(1400, 1200));

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

        lblMst.setText(org.openide.util.NbBundle.getMessage(
                BioMstFibsEditor.class,
                "BioMstFibsEditor.lblMstKennz.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblMst, gridBagConstraints);

        lblGew.setText(org.openide.util.NbBundle.getMessage(
                BioMstFibsEditor.class,
                "BioMstFibsEditor.lblMstName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblGew, gridBagConstraints);

        lblNwb.setText(org.openide.util.NbBundle.getMessage(BioMstFibsEditor.class, "BioMstFibsEditor.lblReHo.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblNwb, gridBagConstraints);

        lblHore.setText(org.openide.util.NbBundle.getMessage(BioMstFibsEditor.class, "BioMstFibsEditor.lblBauj.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblHore, gridBagConstraints);

        lblMstCodeVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblMstCodeVal.setPreferredSize(new java.awt.Dimension(200, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bio_mst.messstelle}"),
                lblMstCodeVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblMstCodeVal, gridBagConstraints);

        lblGewVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblGewVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_fg.wk_n}"),
                lblGewVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblGewVal, gridBagConstraints);

        lblNwbVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblNwbVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_fg.b9ausw}"),
                lblNwbVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblNwbVal, gridBagConstraints);

        lblHoReVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblHoReVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bio_mst.geom}"),
                lblHoReVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("/");
        binding.setSourceUnreadableValue("<error>");
        binding.setConverter(new CoordinateConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblHoReVal, gridBagConstraints);

        lblWk.setText(org.openide.util.NbBundle.getMessage(BioMstFibsEditor.class, "BioMstFibsEditor.lbStatus.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblWk, gridBagConstraints);

        lblWkVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblWkVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_fg.wk_k}"),
                lblWkVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblWkVal, gridBagConstraints);

        lblFischTyp.setText(org.openide.util.NbBundle.getMessage(
                BioMstFibsEditor.class,
                "BioMstFibsEditor.lblFilterOb.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblFischTyp, gridBagConstraints);

        lblLawa.setText(org.openide.util.NbBundle.getMessage(
                BioMstFibsEditor.class,
                "BioMstFibsEditor.lbSteckbrief.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblLawa, gridBagConstraints);

        lblLawaVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblLawaVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create(
                    "${cidsBean.bio_mst.lawa_typ.value} - ${cidsBean.bio_mst.lawa_typ.name}"),
                lblLawaVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblLawaVal, gridBagConstraints);

        lblLage.setText(org.openide.util.NbBundle.getMessage(BioMstFibsEditor.class, "BioMstFibsEditor.lblWkGw.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblLage, gridBagConstraints);

        lblLageVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblLageVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bio_mst.lage}"),
                lblLageVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblLageVal, gridBagConstraints);

        lblStalu.setText(org.openide.util.NbBundle.getMessage(
                BioMstFibsEditor.class,
                "BioMstFibsEditor.lblReHo1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panStamm.add(lblStalu, gridBagConstraints);

        lblSatluVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblSatluVal.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_fg.stalu.kurz}"),
                lblSatluVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblSatluVal, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(200, 80));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 80));

        listFisch.setModel(new javax.swing.AbstractListModel<String>() {

                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

                @Override
                public int getSize() {
                    return strings.length;
                }
                @Override
                public String getElementAt(final int i) {
                    return strings[i];
                }
            });
        jScrollPane1.setViewportView(listFisch);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        panInfoContent.add(panStamm, gridBagConstraints);

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

        bioMstFibsMessungenEditor1.setMinimumSize(new java.awt.Dimension(1000, 130));
        bioMstFibsMessungenEditor1.setPreferredSize(new java.awt.Dimension(1000, 130));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panInfoContent.add(bioMstFibsMessungenEditor1, gridBagConstraints);

        panGeo.setMinimumSize(new java.awt.Dimension(640, 100));
        panGeo.setPreferredSize(new java.awt.Dimension(640, 100));

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                BioMstFibsEditor.class,
                "DghEditor.lblHeading1.text",
                new Object[] {})); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panGeo.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        panInfoContent1.add(linearReferencedLineEditor, gridBagConstraints);

        panGeo.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(panGeo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(jPanel1, gridBagConstraints);

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
                    synchronized (BioMstFibsEditor.this) {
                        CidsBean measure = null;
                        int measureYear = newYear;

                        do {
                            txtJahr.setText(String.valueOf(measureYear));
                            measure = getDataForYear(measureYear, measureNumber);
                            showNewMeasure(measure);
                            --measureYear;
                        } while ((measure == null) && (measureYear > 1990));
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
                        synchronized (BioMstFibsEditor.this) {
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
    private void showNewMeasure(final CidsBean measure) {
        if (!readOnly) {
            bioMstFibsMessungenEditor1.setCidsBean(measure, cidsBean);
        } else {
            bioMstFibsMessungenEditor1.setCidsBean(measure);
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
        linearReferencedLineEditor.dispose();
    }

    @Override
    public String getTitle() {
        return "Abschnitt für Fischbewertung " + String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        linearReferencedLineEditor.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        boolean save = true;
        save &= linearReferencedLineEditor.prepareForSave();
        return save;
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
            query += " WHERE m.messstelle = s.id AND s.id = " + cidsBean.getProperty("bio_mst.id");             // NOI18N
            query += " AND messjahr = " + year + " order by id asc";                                            // NOI18N

            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0, CC);

            if ((metaObjects != null) && (number >= 0) && (number < metaObjects.length)) {
                final CidsBean retVal = metaObjects[number].getBean();
                final int index = -1;

                return retVal;
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

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class YearAndMeasure {

        //~ Instance fields ----------------------------------------------------

        private CidsBean[] measure;
        private int year;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new YearAndMeasure object.
         *
         * @param  measure  DOCUMENT ME!
         * @param  year     DOCUMENT ME!
         */
        public YearAndMeasure(final CidsBean[] measure, final int year) {
            this.measure = measure;
            this.year = year;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  the measure
         */
        public CidsBean[] getMeasure() {
            return measure;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  measure  the measure to set
         */
        public void setMeasure(final CidsBean[] measure) {
            this.measure = measure;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the year
         */
        public int getYear() {
            return year;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  year  the year to set
         */
        public void setYear(final int year) {
            this.year = year;
        }
    }
}
