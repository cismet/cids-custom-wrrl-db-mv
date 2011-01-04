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

import org.jdesktop.beansbinding.Converter;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JLabel;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class RohrleitungEditor extends javax.swing.JPanel implements CidsBeanRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RohrleitungEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassnahmen;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbSchachtabsturz;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbSediment;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cdOekoDurch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblMassnahmen;
    private javax.swing.JLabel lblMassnahmenId;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panContent;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JScrollPane scpBemerkung;
    private javax.swing.JSpinner spDurchmesser;
    private javax.swing.JSpinner spHoeheAuslauf;
    private javax.swing.JSpinner spLaenge;
    private javax.swing.JTextArea taBemerkung;
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
        LOG.fatal(System.identityHashCode(this));
        initComponents();
        initLinearReferencedLineEditor();

        new CidsBeanDropTarget(jLabel2);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void initLinearReferencedLineEditor() {
        linearReferencedLineEditor.setMetaClassName("Rohrleitung");
        linearReferencedLineEditor.setFromStationField("station_von");
        linearReferencedLineEditor.setToStationField("station_bis");
        linearReferencedLineEditor.setRealGeomField("real_geom");
        linearReferencedLineEditor.addLinearReferencedLineEditorListener(new LinearReferencedLineEditorListener() {

                @Override
                public void linearReferencedLineCreated() {
                    zoomToFeatures();
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        final MappingComponent mappingComponent = CismapBroker.getInstance().getMappingComponent();
        if (!mappingComponent.isFixedMapExtent()) {
            final Collection<Feature> fc = new ArrayList<Feature>();
            for (final Feature f
                        : CismapBroker.getInstance().getMappingComponent().getFeatureCollection().getAllFeatures()) {
                if (f.equals(linearReferencedLineEditor.getFeature())) {
                    fc.add(f);
                }
            }
            CismapBroker.getInstance()
                    .getMappingComponent()
                    .zoomToAFeatureCollection(fc, true, mappingComponent.isFixedMapScale());
        }
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

        panContent = new javax.swing.JPanel();
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
        linearReferencedLineEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new MassnIdLabel();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        panContent.setOpaque(false);
        panContent.setLayout(new java.awt.GridBagLayout());

        txtDescGwName.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescGwName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtDescGwName, gridBagConstraints);

        txtDescSQAId.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescSQAId.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtDescSQAId, gridBagConstraints);

        txtDescDurchmesser.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescDurchmesser.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtDescDurchmesser, gridBagConstraints);

        txtDescAbsturz.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescAbsturz.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtDescAbsturz, gridBagConstraints);

        txtDescAbsturzAuslauf.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescAbsturzAuslauf.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtDescAbsturzAuslauf, gridBagConstraints);

        txtDescBemerkung.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescBemerkung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtDescBemerkung, gridBagConstraints);

        txtDescLaenge.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescLaenge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtDescLaenge, gridBagConstraints);

        txtDescOekoDurch.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescOekoDurch.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtDescOekoDurch, gridBagConstraints);

        txtDescSediment.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.txtDescSediment.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtDescSediment, gridBagConstraints);

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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtStatusQuoAnalyse, gridBagConstraints);

        scpBemerkung.setMaximumSize(new java.awt.Dimension(200, 75));
        scpBemerkung.setMinimumSize(new java.awt.Dimension(200, 75));
        scpBemerkung.setPreferredSize(new java.awt.Dimension(500, 75));

        taBemerkung.setColumns(20);
        taBemerkung.setFont(new java.awt.Font("Tahoma", 0, 11));
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
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(scpBemerkung, gridBagConstraints);

        spHoeheAuslauf.setMaximumSize(new java.awt.Dimension(75, 20));
        spHoeheAuslauf.setMinimumSize(new java.awt.Dimension(75, 20));
        spHoeheAuslauf.setPreferredSize(new java.awt.Dimension(75, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laenge_m}"),
                spHoeheAuslauf,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(spHoeheAuslauf, gridBagConstraints);

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
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(spLaenge, gridBagConstraints);

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
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(spDurchmesser, gridBagConstraints);

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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbSchachtabsturz, gridBagConstraints);

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
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbSediment, gridBagConstraints);

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
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cdOekoDurch, gridBagConstraints);

        lblMassnahmen.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.lblMassnahmen.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblMassnahmen, gridBagConstraints);

        lblMassnahmenId.setText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.lblMassnahmenId.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblMassnahmenId, gridBagConstraints);

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
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbMassnahmen, gridBagConstraints);

        panInfo1.setMinimumSize(new java.awt.Dimension(640, 120));
        panInfo1.setPreferredSize(new java.awt.Dimension(640, 120));

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
        panInfoContent1.add(linearReferencedLineEditor, gridBagConstraints);

        panInfo1.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panContent.add(panInfo1, gridBagConstraints);

        jLabel1.setMinimumSize(new java.awt.Dimension(500, 20));
        jLabel1.setPreferredSize(new java.awt.Dimension(500, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.station_bis.route.routenname}"),
                jLabel1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(jLabel1, gridBagConstraints);

        jLabel2.setToolTipText(org.openide.util.NbBundle.getMessage(
                RohrleitungEditor.class,
                "RohrleitungEditor.jLabel2.toolTipText")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_ref}"),
                jLabel2,
                org.jdesktop.beansbinding.BeanProperty.create("text"),
                "massnahmenbinding");
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        binding.setConverter(new MassnRefConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panContent, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        if (cidsBean != null) {
            this.cidsBean = cidsBean;
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                this.cidsBean);
            linearReferencedLineEditor.setCidsBean(cidsBean);
            bindingGroup.bind();
        }
    }

    @Override
    public void dispose() {
        linearReferencedLineEditor.dispose();
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return cidsBean.toString();
    }

    @Override
    public void setTitle(final String title) {
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
                    LOG.fatal("bean instanceof " + toAdd.getClass().getCanonicalName());
                    cidsBean.setProperty("massn_ref", toAdd);
                } catch (Exception ex) {
                    LOG.fatal("error while setting massn_id", ex);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class MassnRefConverter extends Converter<CidsBean, String> {

        //~ Methods ------------------------------------------------------------

        @Override
        public String convertForward(final CidsBean bean) {
            try {
                return Integer.toString((Integer)bean.getProperty("id"));
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error while converting massn_ref to string", ex);
                }
                return null;
            }
        }

        @Override
        public CidsBean convertReverse(final String t) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
