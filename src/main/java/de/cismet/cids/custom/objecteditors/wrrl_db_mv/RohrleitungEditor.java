/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * RohrleitungEditor.java
 *
 * Created on 18.10.2010, 16:24:32
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaObjectNode;

import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.MapUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.WrrlEditorTester;
import de.cismet.cids.custom.wrrl_db_mv.util.YesNoConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class RohrleitungEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RohrleitungEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassnahmen;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbSchachtabsturz;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbSediment;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cdOekoDurch;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblMassnahmen;
    private javax.swing.JLabel lblMassnahmenId;
    private javax.swing.JLabel lblMassnahmenId1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panAl;
    private javax.swing.JPanel panContent;
    private javax.swing.JPanel panFisch;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private javax.swing.JPanel panInfoContent1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanSeven querbauwerkePanSeven1;
    private javax.swing.JScrollPane scpBemerkung;
    private javax.swing.JSpinner spDurchmesser;
    private javax.swing.JSpinner spHoeheAuslauf;
    private javax.swing.JSpinner spLaenge;
    private javax.swing.JTextArea taBemerkung;
    private javax.swing.JTabbedPane tpMain;
    private javax.swing.JLabel txtDescAbsturz;
    private javax.swing.JLabel txtDescAbsturzAuslauf;
    private javax.swing.JLabel txtDescBemerkung;
    private javax.swing.JLabel txtDescDurchmesser;
    private javax.swing.JLabel txtDescGwName;
    private javax.swing.JLabel txtDescLaenge;
    private javax.swing.JLabel txtDescOekoDurch;
    private javax.swing.JLabel txtDescSQAId;
    private javax.swing.JLabel txtDescSediment;
    private javax.swing.JTextField txtStatusQuoAnalyse;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form RohrleitungEditor.
     */
    public RohrleitungEditor() {
        this(false);
    }

    /**
     * Creates a new RohrleitungEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public RohrleitungEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
        tpMain.setUI(new TabbedPaneUITransparent());
        Boolean hasPerm = false;

        try {
            hasPerm = SessionManager.getProxy()
                        .hasConfigAttr(SessionManager.getSession().getUser(),
                                "qb_all_infos",
                                ConnectionContext.create(
                                    AbstractConnectionContext.Category.EDITOR,
                                    "has qb permission"));
        } catch (Exception e) {
            LOG.error("Cannot check permission", e);
        }

        txtDescSQAId.setVisible(hasPerm);
        txtStatusQuoAnalyse.setVisible(hasPerm);

        if (readOnly) {
            RendererTools.makeReadOnly(txtStatusQuoAnalyse);
            RendererTools.makeReadOnly(cbMassnahmen);
            RendererTools.makeReadOnly(cbSchachtabsturz);
            RendererTools.makeReadOnly(cbSediment);
            RendererTools.makeReadOnly(jCheckBox1);
            RendererTools.makeReadOnly(cdOekoDurch);
            RendererTools.makeReadOnly(taBemerkung);
            RendererTools.makeReadOnly(spDurchmesser);
            RendererTools.makeReadOnly(spHoeheAuslauf);
            RendererTools.makeReadOnly(spLaenge);
            jLabel7.setForeground(Color.BLUE);
        } else {
            new CidsBeanDropTarget(jLabel2);
            new CidsBeanDropTarget(jLabel7);
        }

        initLinearReferencedLineEditor();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void initLinearReferencedLineEditor() {
        linearReferencedLineEditor.setLineField("linie");
        linearReferencedLineEditor.setOtherLinesQueryAddition(
            "rohrleitung",
            "rohrleitung.linie = ");
        linearReferencedLineEditor.setDrawingFeaturesEnabled(!readOnly);
    }

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        MapUtil.zoomToFeatureCollection(linearReferencedLineEditor.getZoomFeatures());
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
        panContent = new javax.swing.JPanel();
        tpMain = new javax.swing.JTabbedPane();
        panAl = new javax.swing.JPanel();
        txtDescGwName = new javax.swing.JLabel();
        txtDescSQAId = new javax.swing.JLabel();
        txtDescDurchmesser = new javax.swing.JLabel();
        txtDescAbsturz = new javax.swing.JLabel();
        txtDescAbsturzAuslauf = new javax.swing.JLabel();
        txtDescBemerkung = new javax.swing.JLabel();
        txtDescLaenge = new javax.swing.JLabel();
        txtDescOekoDurch = new javax.swing.JLabel();
        txtDescSediment = new javax.swing.JLabel();
        txtStatusQuoAnalyse = new javax.swing.JTextField();
        scpBemerkung = new javax.swing.JScrollPane();
        taBemerkung = new javax.swing.JTextArea();
        spHoeheAuslauf = new javax.swing.JSpinner();
        spLaenge = new javax.swing.JSpinner();
        spDurchmesser = new javax.swing.JSpinner();
        cbSchachtabsturz = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cbSediment = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cdOekoDurch = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblMassnahmen = new javax.swing.JLabel();
        lblMassnahmenId = new javax.swing.JLabel();
        cbMassnahmen = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        linearReferencedLineEditor = (readOnly
                ? new LinearReferencedLineRenderer(true)
                : new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor());
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new MassnIdLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        lblMassnahmenId1 = new javax.swing.JLabel();
        jLabel7 = new WkFgLabel();
        panFisch = new javax.swing.JPanel();
        querbauwerkePanSeven1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanSeven();

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

        panContent.setOpaque(false);
        panContent.setLayout(new java.awt.BorderLayout());

        panAl.setOpaque(false);
        panAl.setLayout(new java.awt.GridBagLayout());

        txtDescGwName.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescGwName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(txtDescGwName, gridBagConstraints);

        txtDescSQAId.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescSQAId.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(txtDescSQAId, gridBagConstraints);

        txtDescDurchmesser.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescDurchmesser.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(txtDescDurchmesser, gridBagConstraints);

        txtDescAbsturz.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescAbsturz.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(txtDescAbsturz, gridBagConstraints);

        txtDescAbsturzAuslauf.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescAbsturzAuslauf.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(txtDescAbsturzAuslauf, gridBagConstraints);

        txtDescBemerkung.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescBemerkung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(txtDescBemerkung, gridBagConstraints);

        txtDescLaenge.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescLaenge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(txtDescLaenge, gridBagConstraints);

        txtDescOekoDurch.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescOekoDurch.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(txtDescOekoDurch, gridBagConstraints);

        txtDescSediment.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescSediment.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(txtDescSediment, gridBagConstraints);

        txtStatusQuoAnalyse.setMinimumSize(new java.awt.Dimension(500, 20));
        txtStatusQuoAnalyse.setPreferredSize(new java.awt.Dimension(500, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sqa_id}"),
                txtStatusQuoAnalyse,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(txtStatusQuoAnalyse, gridBagConstraints);

        scpBemerkung.setMaximumSize(new java.awt.Dimension(200, 75));
        scpBemerkung.setMinimumSize(new java.awt.Dimension(200, 75));
        scpBemerkung.setPreferredSize(new java.awt.Dimension(500, 75));

        taBemerkung.setColumns(20);
        taBemerkung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        taBemerkung.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung}"),
                taBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        scpBemerkung.setViewportView(taBemerkung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(scpBemerkung, gridBagConstraints);

        spHoeheAuslauf.setEnabled(false);
        spHoeheAuslauf.setMaximumSize(new java.awt.Dimension(75, 20));
        spHoeheAuslauf.setMinimumSize(new java.awt.Dimension(75, 20));
        spHoeheAuslauf.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(spHoeheAuslauf, gridBagConstraints);

        spLaenge.setMaximumSize(new java.awt.Dimension(75, 20));
        spLaenge.setMinimumSize(new java.awt.Dimension(75, 20));
        spLaenge.setPreferredSize(new java.awt.Dimension(75, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.durchmess}"),
                spLaenge,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(spLaenge, gridBagConstraints);

        spDurchmesser.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        spDurchmesser.setMaximumSize(new java.awt.Dimension(75, 20));
        spDurchmesser.setMinimumSize(new java.awt.Dimension(75, 20));
        spDurchmesser.setPreferredSize(new java.awt.Dimension(75, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.abst_r_end}"),
                spDurchmesser,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(spDurchmesser, gridBagConstraints);

        cbSchachtabsturz.setMaximumSize(new java.awt.Dimension(200, 20));
        cbSchachtabsturz.setMinimumSize(new java.awt.Dimension(200, 20));
        cbSchachtabsturz.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.absturz}"),
                cbSchachtabsturz,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(cbSchachtabsturz, gridBagConstraints);

        cbSediment.setMaximumSize(new java.awt.Dimension(200, 20));
        cbSediment.setMinimumSize(new java.awt.Dimension(200, 20));
        cbSediment.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sediment}"),
                cbSediment,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(cbSediment, gridBagConstraints);

        cdOekoDurch.setMaximumSize(new java.awt.Dimension(200, 20));
        cdOekoDurch.setMinimumSize(new java.awt.Dimension(200, 20));
        cdOekoDurch.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.dgk}"),
                cdOekoDurch,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(cdOekoDurch, gridBagConstraints);

        lblMassnahmen.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.lblMassnahmen.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(lblMassnahmen, gridBagConstraints);

        lblMassnahmenId.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.lblMassnahmenId.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(lblMassnahmenId, gridBagConstraints);

        cbMassnahmen.setMaximumSize(new java.awt.Dimension(200, 20));
        cbMassnahmen.setMinimumSize(new java.awt.Dimension(200, 20));
        cbMassnahmen.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn}"),
                cbMassnahmen,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(cbMassnahmen, gridBagConstraints);

        panInfo1.setMinimumSize(new java.awt.Dimension(640, 140));
        panInfo1.setPreferredSize(new java.awt.Dimension(640, 140));

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo1.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(linearReferencedLineEditor, gridBagConstraints);

        panInfo1.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panAl.add(panInfo1, gridBagConstraints);

        jLabel1.setMinimumSize(new java.awt.Dimension(500, 20));
        jLabel1.setPreferredSize(new java.awt.Dimension(500, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.linie.von.route.routenname}"),
                jLabel1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(jLabel1, gridBagConstraints);

        jLabel2.setToolTipText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.jLabel2.toolTipText")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_ref.massn_id}"),
                jLabel2,
                org.jdesktop.beansbinding.BeanProperty.create("text"),
                "massnahmenbinding");
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("keine Maßnahme zugewiesen");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(jLabel2, gridBagConstraints);

        jLabel3.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.jLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(jLabel3, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_ref.massn_fin}"),
                jLabel4,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("-");
        binding.setConverter(new YesNoConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(jLabel4, gridBagConstraints);

        jLabel5.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.jLabel5.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(jLabel5, gridBagConstraints);

        jCheckBox1.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.jCheckBox1.text")); // NOI18N
        jCheckBox1.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zurueckgebaut}"),
                jCheckBox1,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jCheckBox1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(jCheckBox1, gridBagConstraints);
        jCheckBox1.setBackground(null);

        jLabel6.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.jLabel6.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panAl.add(jLabel6, gridBagConstraints);

        lblMassnahmenId1.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.lblMassnahmenId1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(lblMassnahmenId1, gridBagConstraints);

        jLabel7.setToolTipText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.jLabel7.toolTipText")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_fg.wk_k}"),
                jLabel7,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("kein Wasserkörper zugewiesen");
        bindingGroup.addBinding(binding);

        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    jLabel7MouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAl.add(jLabel7, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.panAl.TabConstraints.tabTitle",
                new Object[] {}),
            panAl); // NOI18N

        panFisch.setOpaque(false);
        panFisch.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panFisch.add(querbauwerkePanSeven1, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.panFisch.TabConstraints.tabTitle",
                new Object[] {}),
            panFisch); // NOI18N

        panContent.add(tpMain, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panContent, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jCheckBox1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_jCheckBox1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jLabel7MouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jLabel7MouseClicked
        if ((evt.getClickCount() == 1) && readOnly && (cidsBean.getProperty("wk_fg") instanceof CidsBean)) {
            ComponentRegistry.getRegistry()
                    .getDescriptionPane()
                    .gotoMetaObjectNode(new MetaObjectNode((CidsBean)cidsBean.getProperty("wk_fg")), false);
        }
    }                                                                       //GEN-LAST:event_jLabel7MouseClicked

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;
        bindingGroup.unbind();

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                this.cidsBean);
            bindingGroup.bind();
            UIUtil.setLastModifier(cidsBean, lblFoot);
            querbauwerkePanSeven1.setCidsBean(cidsBean);
            linearReferencedLineEditor.setCidsBean(cidsBean);
            zoomToFeatures();
        }

        setLaenge();
    }

    /**
     * DOCUMENT ME!
     */
    private void setLaenge() {
        Double from = (Double)cidsBean.getProperty("linie.von.wert");
        Double to = (Double)cidsBean.getProperty("linie.bis.wert");

        if ((from != null) && (to != null)) {
            if (from > to) {
                final Double tmp = from;
                from = to;
                to = tmp;
            }
            spHoeheAuslauf.setValue((to - from));
        }
    }

    @Override
    public void dispose() {
        linearReferencedLineEditor.dispose();
        querbauwerkePanSeven1.dispose();
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        if (cidsBean == null) {
            return "neue Rohrleitung";
        } else {
            return "Rohrleitung " + cidsBean.toString();
        }
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
        boolean save = true;

        if (cidsBean != null) {
            try {
                cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());
                cidsBean.setProperty("av_time", new java.sql.Timestamp(System.currentTimeMillis()));
            } catch (Exception ex) {
                LOG.error(ex, ex);
            }
        }

        save &= linearReferencedLineEditor.prepareForSave();
        return save;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        new WrrlEditorTester("Rohrleitung", RohrleitungEditor.class, WRRLUtil.DOMAIN_NAME).run();
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
    class MassnIdLabel extends JLabel implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            CidsBean toAdd = null;
            for (final CidsBean bean : beans) {
                if (bean.getMetaObject().getMetaClass().getName().equals("MASSNAHMEN")) {
                    toAdd = bean;
                    break;
                }
            }

            if (toAdd != null) {
                try {
//                    LOG.fatal("dropped: " + toAdd.getMOString());
                    cidsBean.setProperty("massn_ref", toAdd);
                } catch (Exception ex) {
                    LOG.fatal("error while setting massn_ref", ex);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class WkFgLabel extends JLabel implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            CidsBean toAdd = null;
            for (final CidsBean bean : beans) {
                if (bean.getMetaObject().getMetaClass().getName().equalsIgnoreCase("WK_FG")) {
                    toAdd = bean;
                    break;
                }
            }

            if (toAdd != null) {
                try {
//                    LOG.fatal("dropped: " + toAdd.getMOString());
                    cidsBean.setProperty("wk_fg", toAdd);
                } catch (Exception ex) {
                    LOG.fatal("error while setting wk_fg", ex);
                }
            }
        }
    }
}
