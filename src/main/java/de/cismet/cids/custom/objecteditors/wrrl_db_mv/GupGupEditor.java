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
 * Created on 19.10.2011, 11:49:19
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.types.treenode.DefaultMetaTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.newuser.permission.Policy;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.tree.TreePath;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.PlanungsabschnittSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.DocumentDropList;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.WaitDialog;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupGupEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupGupEditor.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_planungsabschnitt");

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private List<CidsBean> planToAdd = new ArrayList<CidsBean>();
    private TreePath treePath;
    private int y = 0;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butNewPlan;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGenehmigungsbehoerde;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassnahme;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcVon;
    private javax.swing.JButton jbAdd;
    private javax.swing.JButton jbDownload;
    private javax.swing.JList jlObjectList;
    private javax.swing.JButton jpDelete;
    private javax.swing.JScrollPane jsObjectList;
    private javax.swing.JLabel lblBis;
    private javax.swing.JLabel lblErlaeuterungsberichte;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGenehmigungsbehoerde;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblVon;
    private javax.swing.JLabel lblWorkflowStatus;
    private javax.swing.JLabel lblZeitraum;
    private javax.swing.JLabel lblZustaendigkeit;
    private javax.swing.JPanel panFooter;
    private javax.swing.JPanel panGewaesserInner;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JScrollPane scrollGewaesser;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtZustaendigkeit;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupGupEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupGupEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
        scrollGewaesser.getViewport().setOpaque(false);
        butNewPlan.setVisible(!readOnly);

        jbAdd.setEnabled(!readOnly);
        jpDelete.setEnabled(!readOnly);

        if (readOnly) {
            RendererTools.makeReadOnly(cbGenehmigungsbehoerde);
            RendererTools.makeReadOnly(cbMassnahme);
            RendererTools.makeReadOnly(dcVon);
            RendererTools.makeReadOnly(txtName);
            RendererTools.makeReadOnly(dcBis);
            RendererTools.makeReadOnly(txtZustaendigkeit);
        } else {
            try {
                new CidsBeanDropTarget(panGewaesserInner);
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

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        lblZeitraum = new javax.swing.JLabel();
        lblZustaendigkeit = new javax.swing.JLabel();
        txtZustaendigkeit = new javax.swing.JTextField();
        lblVon = new javax.swing.JLabel();
        lblBis = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblErlaeuterungsberichte = new javax.swing.JLabel();
        lblWorkflowStatus = new javax.swing.JLabel();
        cbMassnahme = new ScrollableComboBox();
        jsObjectList = new javax.swing.JScrollPane();
        jlObjectList = new DocumentDropList(readOnly, "dokumente");
        jbDownload = new javax.swing.JButton();
        lblGenehmigungsbehoerde = new javax.swing.JLabel();
        cbGenehmigungsbehoerde = new ScrollableComboBox();
        jpDelete = new javax.swing.JButton();
        dcVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        jbAdd = new javax.swing.JButton();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        scrollGewaesser = new javax.swing.JScrollPane();
        panGewaesserInner = new PlanungsabschnittPanel();
        butNewPlan = new javax.swing.JButton();

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
        setPreferredSize(new java.awt.Dimension(994, 700));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(557, 209));
        panInfo.setPreferredSize(new java.awt.Dimension(1130, 248));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblZeitraum.setFont(new java.awt.Font("Ubuntu", 1, 15));                                                        // NOI18N
        lblZeitraum.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblZeitraum.text")); // NOI18N
        lblZeitraum.setMaximumSize(new java.awt.Dimension(170, 17));
        lblZeitraum.setMinimumSize(new java.awt.Dimension(170, 17));
        lblZeitraum.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblZeitraum, gridBagConstraints);

        lblZustaendigkeit.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblZustaendigkeit.setText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.lblZustaendigkeit.text"));               // NOI18N
        lblZustaendigkeit.setMaximumSize(new java.awt.Dimension(280, 17));
        lblZustaendigkeit.setMinimumSize(new java.awt.Dimension(280, 17));
        lblZustaendigkeit.setPreferredSize(new java.awt.Dimension(280, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblZustaendigkeit, gridBagConstraints);

        txtZustaendigkeit.setMaximumSize(new java.awt.Dimension(280, 25));
        txtZustaendigkeit.setMinimumSize(new java.awt.Dimension(280, 25));
        txtZustaendigkeit.setPreferredSize(new java.awt.Dimension(380, 22));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zustaendigkeit}"),
                txtZustaendigkeit,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panInfoContent.add(txtZustaendigkeit, gridBagConstraints);

        lblVon.setFont(new java.awt.Font("Ubuntu", 1, 15));                                                   // NOI18N
        lblVon.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblVon.text")); // NOI18N
        lblVon.setMaximumSize(new java.awt.Dimension(30, 17));
        lblVon.setMinimumSize(new java.awt.Dimension(30, 17));
        lblVon.setPreferredSize(new java.awt.Dimension(30, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblVon, gridBagConstraints);

        lblBis.setFont(new java.awt.Font("Ubuntu", 1, 15));                                                   // NOI18N
        lblBis.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblBis.text")); // NOI18N
        lblBis.setMaximumSize(new java.awt.Dimension(30, 17));
        lblBis.setMinimumSize(new java.awt.Dimension(30, 17));
        lblBis.setPreferredSize(new java.awt.Dimension(30, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblBis, gridBagConstraints);

        lblName.setFont(new java.awt.Font("Ubuntu", 1, 15));                                                    // NOI18N
        lblName.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblName.text")); // NOI18N
        lblName.setMaximumSize(new java.awt.Dimension(170, 17));
        lblName.setMinimumSize(new java.awt.Dimension(170, 17));
        lblName.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 5, 5);
        panInfoContent.add(lblName, gridBagConstraints);

        txtName.setMaximumSize(new java.awt.Dimension(280, 25));
        txtName.setMinimumSize(new java.awt.Dimension(280, 25));
        txtName.setPreferredSize(new java.awt.Dimension(380, 24));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 15);
        panInfoContent.add(txtName, gridBagConstraints);

        lblErlaeuterungsberichte.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblErlaeuterungsberichte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErlaeuterungsberichte.setText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.lblErlaeuterungsberichte.text"));               // NOI18N
        lblErlaeuterungsberichte.setMaximumSize(new java.awt.Dimension(310, 17));
        lblErlaeuterungsberichte.setMinimumSize(new java.awt.Dimension(310, 17));
        lblErlaeuterungsberichte.setPreferredSize(new java.awt.Dimension(310, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 5, 15);
        panInfoContent.add(lblErlaeuterungsberichte, gridBagConstraints);

        lblWorkflowStatus.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblWorkflowStatus.setText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.lblWorkflowStatus.text"));               // NOI18N
        lblWorkflowStatus.setMaximumSize(new java.awt.Dimension(230, 17));
        lblWorkflowStatus.setMinimumSize(new java.awt.Dimension(230, 17));
        lblWorkflowStatus.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblWorkflowStatus, gridBagConstraints);

        cbMassnahme.setMaximumSize(new java.awt.Dimension(290, 25));
        cbMassnahme.setMinimumSize(new java.awt.Dimension(290, 25));
        cbMassnahme.setPreferredSize(new java.awt.Dimension(290, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.status}"),
                cbMassnahme,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panInfoContent.add(cbMassnahme, gridBagConstraints);

        final org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.dokumente}");
        final org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        jlObjectList);
        bindingGroup.addBinding(jListBinding);

        jsObjectList.setViewportView(jlObjectList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 15);
        panInfoContent.add(jsObjectList, gridBagConstraints);

        jbDownload.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.jbDownload.text")); // NOI18N
        jbDownload.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbDownloadActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 15);
        panInfoContent.add(jbDownload, gridBagConstraints);

        lblGenehmigungsbehoerde.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblGenehmigungsbehoerde.setText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.lblGenehmigungsbehoerde.text"));               // NOI18N
        lblGenehmigungsbehoerde.setMaximumSize(new java.awt.Dimension(230, 17));
        lblGenehmigungsbehoerde.setMinimumSize(new java.awt.Dimension(230, 17));
        lblGenehmigungsbehoerde.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblGenehmigungsbehoerde, gridBagConstraints);

        cbGenehmigungsbehoerde.setMaximumSize(new java.awt.Dimension(290, 25));
        cbGenehmigungsbehoerde.setMinimumSize(new java.awt.Dimension(290, 25));
        cbGenehmigungsbehoerde.setPreferredSize(new java.awt.Dimension(290, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.genehmigungsbehoerde}"),
                cbGenehmigungsbehoerde,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panInfoContent.add(cbGenehmigungsbehoerde, gridBagConstraints);

        jpDelete.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.jpDelete.text")); // NOI18N
        jpDelete.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jpDeleteActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 15);
        panInfoContent.add(jpDelete, gridBagConstraints);

        dcVon.setMaximumSize(new java.awt.Dimension(280, 25));
        dcVon.setMinimumSize(new java.awt.Dimension(130, 25));
        dcVon.setPreferredSize(new java.awt.Dimension(280, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.start_datum}"),
                dcVon,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(dcVon.getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panInfoContent.add(dcVon, gridBagConstraints);

        dcBis.setMaximumSize(new java.awt.Dimension(280, 25));
        dcBis.setMinimumSize(new java.awt.Dimension(130, 25));
        dcBis.setPreferredSize(new java.awt.Dimension(280, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.end_datum}"),
                dcBis,
                org.jdesktop.beansbinding.BeanProperty.create("date"),
                "");
        binding.setConverter(dcBis.getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panInfoContent.add(dcBis, gridBagConstraints);

        jbAdd.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.jbAdd.text")); // NOI18N
        jbAdd.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbAddActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 15);
        panInfoContent.add(jbAdd, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 15);
        add(panInfo, gridBagConstraints);

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo1.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        scrollGewaesser.setBorder(null);
        scrollGewaesser.setOpaque(false);

        panGewaesserInner.setBorder(null);
        panGewaesserInner.setMinimumSize(new java.awt.Dimension(100, 100));
        panGewaesserInner.setOpaque(false);
        panGewaesserInner.setLayout(new java.awt.GridBagLayout());
        scrollGewaesser.setViewportView(panGewaesserInner);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(scrollGewaesser, gridBagConstraints);

        panInfo1.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 15, 15);
        add(panInfo1, gridBagConstraints);

        butNewPlan.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.butNewPlan.text")); // NOI18N
        butNewPlan.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    butNewPlanActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        add(butNewPlan, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbDownloadActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbDownloadActionPerformed
        ((DocumentDropList)jlObjectList).downloadSelectedDocs();
    }                                                                              //GEN-LAST:event_jbDownloadActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jpDeleteActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jpDeleteActionPerformed
        if (readOnly) {
            return;
        }
        final int[] selection = jlObjectList.getSelectedIndices();
        int count = 0;

        for (final int index : selection) {
            ((DocumentDropList)jlObjectList).removeObject(index - (count++));
        }
    } //GEN-LAST:event_jpDeleteActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbAddActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbAddActionPerformed
        if (!readOnly) {
            final JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.showDialog(this, "Hinzufügen");
            final File[] files = chooser.getSelectedFiles();

            ((DocumentDropList)jlObjectList).addFiles(Arrays.asList(files));
        }
    } //GEN-LAST:event_jbAddActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void butNewPlanActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_butNewPlanActionPerformed
        GupPlanungsabschnittEditor.setLastGup(cidsBean);

        final MetaClass MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "gup_planungsabschnitt");

        try {
            final CidsBean cb = CidsBean.createNewCidsBeanFromTableName(MC.getDomain(), MC.getTableName());

            final MetaObjectNode metaObjectNode = new MetaObjectNode(
                    -1,
                    SessionManager.getSession().getUser().getDomain(),
                    cb.getMetaObject(),
                    null,
                    null,
                    true,
                    Policy.createWIKIPolicy(),
                    -1,
                    null,
                    false);
            final DefaultMetaTreeNode metaTreeNode = new ObjectTreeNode(metaObjectNode);
            ComponentRegistry.getRegistry().showComponent(ComponentRegistry.ATTRIBUTE_EDITOR);
            ComponentRegistry.getRegistry().getAttributeEditor().setTreeNode(metaTreeNode);
        } catch (Exception e) {
            LOG.error("Error while creating a new object", e);
        }
    } //GEN-LAST:event_butNewPlanActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            treePath = ComponentRegistry.getRegistry().getCatalogueTree().getSelectionPath();
            if (treePath != null) {
                treePath = treePath.getParentPath();
            }
            ((DocumentDropList)jlObjectList).setCidsBean(cidsBean);
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
//            gupGewaesserPreviewTollense.setBeanName("Tollense");
//            gupGewaesserPreviewLindebach.setBeanName("Lindebach");
//            gupGewaesserPreviewTollense.setCidsBean(cidsBean);
//            gupGewaesserPreviewLindebach.setCidsBean(cidsBean);
//            lblFoot.setText("Zuletzt bearbeitet von admin am 05.04.2012");
            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        setCidsBeans();
                    }
                }).start();
//            refreshGewaesser();
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setCidsBeans() {
        try {
//            String query = "select " + MC.getID() + ", " + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
//            query += " WHERE gup = " + cidsBean.getProperty("id");                                            // NOI18N
//
//            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
            final CidsServerSearch search = new PlanungsabschnittSearch(cidsBean.getProperty("id").toString());
            final ArrayList<ArrayList> list = (ArrayList<ArrayList>)SessionManager.getProxy()
                        .customServerSearch(search);

            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        if ((list != null) && (list.size() > 0)) {
                            for (final ArrayList mo : list) {
                                addPa((Integer)mo.get(0), (String)mo.get(1));
                            }
                        }
                    }
                });
        } catch (final ConnectionException e) {
            LOG.error("Error while trying to receive measurements.", e); // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id    mo DOCUMENT ME!
     * @param  name  DOCUMENT ME!
     */
    private void addPa(final int id, final String name) {
        final GupGewaesserPreview comp = new GupGewaesserPreview();
        final GridBagConstraints constraint = new GridBagConstraints(
                0,
                y++,
                1,
                1,
                0,
                0,
                GridBagConstraints.NORTHWEST,
                GridBagConstraints.NONE,
                new Insets(20, 10, 10, 10),
                0,
                0);
        panGewaesserInner.add(comp, constraint);
        comp.setBeanName(name);
        comp.loadCidsBean(id);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mo  DOCUMENT ME!
     */
    private void addPa(final MetaObject mo) {
        final GupGewaesserPreview comp = new GupGewaesserPreview();
        final GridBagConstraints constraint = new GridBagConstraints(
                0,
                y++,
                1,
                1,
                0,
                0,
                GridBagConstraints.NORTHWEST,
                GridBagConstraints.NONE,
                new Insets(20, 10, 10, 10),
                0,
                0);
        panGewaesserInner.add(comp, constraint);
        comp.setBeanName(String.valueOf(mo.getBean().getProperty("name")));
        comp.setCidsBean(mo.getBean());
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
            "sb",
            "gup_gup",
            1,
            1280,
            1024);
    }

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
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        ((DocumentDropList)jlObjectList).editorClosed(event);

        if (event.getStatus() == EditorSaveStatus.SAVE_SUCCESS) {
            // saves the references between the gup_planungsabschnitt objects and the gup object
            if (planToAdd.size() > 0) {
                final int deps = planToAdd.size();
                final WaitDialog wd = new WaitDialog(
                        StaticSwingTools.getParentFrame(this),
                        true,
                        "Speichere Abhängigkeit 1 von "
                                + deps,
                        null);

                new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                int n = 0;
                                synchronized (wd.getTreeLock()) {
                                    wd.setMax(deps);
                                }
                                while (planToAdd.size() > 0) {
                                    final CidsBean bean = planToAdd.get(0);
                                    try {
                                        bean.setProperty("gup", event.getSavedBean());
                                        bean.persist();
                                        planToAdd.remove(0);
                                        synchronized (wd.getTreeLock()) {
                                            wd.setProgress(++n);
                                            wd.setText("Speichere Abhängigkeit " + (n + 1) + " von " + deps);
                                        }
                                    } catch (Exception e) {
                                        LOG.error("Error while saving a gup object.", e);
                                    }
                                }
                            } catch (Exception e) {
                                LOG.error("Error while saving a gup object.", e);
                            } finally {
                                waitUntilVisible();
                                wd.setVisible(false);
                                wd.dispose();
                            }
                        }

                        private void waitUntilVisible() {
                            while (!wd.isVisible()) {
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    // nothing to do
                                }
                            }
                        }
                    }).start();

                StaticSwingTools.showDialog(wd);

                EventQueue.invokeLater(new Thread(new Runnable() {

                            @Override
                            public void run() {
                                if (treePath != null) {
                                    UIUtil.refreshTree(treePath);
                                }
                            }
                        }));
            }
        }
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
     * This list shows the planungsabschnitte objects.
     *
     * @version  $Revision$, $Date$
     */
    private class PlanungsabschnittPanel extends JPanel implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param  beans  DOCUMENT ME!
         */
        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            if (readOnly) {
                // ignore the drop action
                return;
            }

            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Gup_planungsabschnitt")) {
                    planToAdd.add(bean);
                    cidsBean.setArtificialChangeFlag(true);
                    addPa(bean.getMetaObject());
                }
            }
        }
    }
}
