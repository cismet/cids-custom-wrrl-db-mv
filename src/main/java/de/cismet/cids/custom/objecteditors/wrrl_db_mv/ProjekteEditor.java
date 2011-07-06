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

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.MassnahmenUmsetzungCache;
import de.cismet.cids.custom.util.ScrollableComboBox;
import de.cismet.cids.custom.util.TimestampConverter;
import de.cismet.cids.custom.util.UIUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ProjekteEditor extends JPanel implements CidsBeanRenderer, EditorSaveListener, FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ProjekteEditor.class);
    private static final MetaClass INDIKATOR_MC;

    static {
        INDIKATOR_MC = ClassCacheMultiple.getMetaClass(
                CidsBeanSupport.DOMAIN_NAME,
                "indikator"); // NOI18N
    }

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private ArrayList<CidsBean> beansToDelete = new ArrayList<CidsBean>();
    private MassnahmenUmsetzungCache cache;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddIndikator;
    private javax.swing.JButton btnAddMaIndikator;
    private javax.swing.JButton btnAddUmsetzung;
    private javax.swing.JButton btnIndikatorAbort;
    private javax.swing.JButton btnIndikatorOk;
    private javax.swing.JButton btnIndikatorUmsAbort;
    private javax.swing.JButton btnIndikatorUmsOk;
    private javax.swing.JButton btnRemIndikator;
    private javax.swing.JButton btnRemMaIndikator;
    private javax.swing.JButton btnRemUmsetzung;
    private javax.swing.JComboBox cbGeom;
    private javax.swing.JComboBox cbIndikatorCataloge;
    private javax.swing.JComboBox cbIndikatorUmsCataloge;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcValM_beginn;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcValM_ende;
    private javax.swing.JDialog dlgIndikator;
    private javax.swing.JDialog dlgIndikatorUms;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JCheckBox jcLand;
    private javax.swing.JList jlIndikator;
    private javax.swing.JList jlIndikator1;
    private javax.swing.JList jlUmsetzung;
    private javax.swing.JLabel lblFoerdersumme;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGeometrie;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblHeading3;
    private javax.swing.JLabel lblHeading4;
    private javax.swing.JLabel lblIndikatorCataloge;
    private javax.swing.JLabel lblIndikatorUmsCataloge;
    private javax.swing.JLabel lblKost_forderf;
    private javax.swing.JLabel lblKurzBez;
    private javax.swing.JLabel lblM_beginn;
    private javax.swing.JLabel lblM_ende;
    private javax.swing.JLabel lblProjekt_bez;
    private javax.swing.JLabel lblProjekt_code;
    private javax.swing.JLabel lblProjekt_nr;
    private javax.swing.JLabel lblfoerdersatz;
    private javax.swing.JLabel lblkost_gesamt;
    private javax.swing.JLabel lbltraeger;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.MassnahmenUmsetzungEditor massnahmenUmsetzungEditor;
    private javax.swing.JPanel panContrImpactSrc;
    private de.cismet.tools.gui.RoundedPanel panDeMeas;
    private de.cismet.tools.gui.RoundedPanel panDeMeas1;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo3;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo4;
    private javax.swing.JPanel panIndikator;
    private javax.swing.JPanel panIndikator1;
    private javax.swing.JPanel panInfoContent2;
    private javax.swing.JPanel panInfoContent3;
    private javax.swing.JPanel panInfoContent4;
    private de.cismet.tools.gui.RoundedPanel panMaInd;
    private javax.swing.JPanel panMenButtonsIndikator;
    private javax.swing.JPanel panMenButtonsIndikatorUms;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.ProjekteIndikatorenEditor projekteIndikatorenEditor1;
    private javax.swing.JTextField txtValFoerdersatz;
    private javax.swing.JTextField txtValKost_forderf;
    private javax.swing.JTextField txtValKost_gesamt;
    private javax.swing.JTextField txtValKurzBez;
    private javax.swing.JTextField txtValProjekt_bez;
    private javax.swing.JTextField txtValProjekt_code;
    private javax.swing.JTextField txtValProjekt_nr;
    private javax.swing.JTextField txtValTraeger;
    private javax.swing.JTextField txtValfoerdersumme;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public ProjekteEditor() {
        initComponents();
        massnahmenUmsetzungEditor.setList(jlUmsetzung);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        massnahmenUmsetzungEditor.setCidsBean(null);
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
            Object avUser = cidsBean.getProperty("av_user"); // NOI18N
            Object avTime = cidsBean.getProperty("av_time"); // NOI18N
            if (avUser == null) {
                avUser = "(unbekannt)";
            }
            if (avTime instanceof Timestamp) {
                avTime = TimestampConverter.getInstance().convertForward((Timestamp)avTime);
            } else {
                avTime = "(unbekannt)";
            }

            lblFoot.setText("Zuletzt bearbeitet von " + avUser + " am " + avTime);

//            //zoom to feature
//            MappingComponent mappingComponent = CismapBroker.getInstance().getMappingComponent();
//            if (!mappingComponent.isFixedMapExtent()) {
//                mappingComponent.zoomToFeatureCollection(mappingComponent.isFixedMapScale());
//            }
        } else {
            lblFoot.setText("");
        }

        cache = new MassnahmenUmsetzungCache(cidsBean);
        massnahmenUmsetzungEditor.setCache(cache);
        cache.refresh();
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
        dlgIndikator = new javax.swing.JDialog();
        lblIndikatorCataloge = new javax.swing.JLabel();
        cbIndikatorCataloge = new ScrollableComboBox(INDIKATOR_MC, true, false);
        panMenButtonsIndikator = new javax.swing.JPanel();
        btnIndikatorAbort = new javax.swing.JButton();
        btnIndikatorOk = new javax.swing.JButton();
        dlgIndikatorUms = new javax.swing.JDialog();
        lblIndikatorUmsCataloge = new javax.swing.JLabel();
        cbIndikatorUmsCataloge = new ScrollableComboBox(INDIKATOR_MC, true, false);
        panMenButtonsIndikatorUms = new javax.swing.JPanel();
        btnIndikatorUmsAbort = new javax.swing.JButton();
        btnIndikatorUmsOk = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblProjekt_nr = new javax.swing.JLabel();
        txtValProjekt_nr = new javax.swing.JTextField();
        lbltraeger = new javax.swing.JLabel();
        lblkost_gesamt = new javax.swing.JLabel();
        lblfoerdersatz = new javax.swing.JLabel();
        txtValTraeger = new javax.swing.JTextField();
        txtValFoerdersatz = new javax.swing.JTextField();
        txtValKost_gesamt = new javax.swing.JTextField();
        lblProjekt_code = new javax.swing.JLabel();
        lblM_beginn = new javax.swing.JLabel();
        lblM_ende = new javax.swing.JLabel();
        lblKost_forderf = new javax.swing.JLabel();
        lblFoerdersumme = new javax.swing.JLabel();
        txtValProjekt_code = new javax.swing.JTextField();
        txtValKost_forderf = new javax.swing.JTextField();
        txtValfoerdersumme = new javax.swing.JTextField();
        lblProjekt_bez = new javax.swing.JLabel();
        txtValProjekt_bez = new javax.swing.JTextField();
        dcValM_beginn = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcValM_ende = new de.cismet.cids.editors.DefaultBindableDateChooser();
        cbGeom = new DefaultCismapGeometryComboBoxEditor();
        lblGeometrie = new javax.swing.JLabel();
        txtValKurzBez = new javax.swing.JTextField();
        lblKurzBez = new javax.swing.JLabel();
        jcLand = new javax.swing.JCheckBox();
        panDeMeas = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlUmsetzung = new ImplementationList();
        panContrImpactSrc = new javax.swing.JPanel();
        btnRemUmsetzung = new javax.swing.JButton();
        btnAddUmsetzung = new javax.swing.JButton();
        massnahmenUmsetzungEditor = new MassnahmenUmsetzungEditor(false);
        panDeMeas1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo3 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading3 = new javax.swing.JLabel();
        panInfoContent3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlIndikator = new javax.swing.JList();
        panIndikator = new javax.swing.JPanel();
        btnRemIndikator = new javax.swing.JButton();
        btnAddIndikator = new javax.swing.JButton();
        projekteIndikatorenEditor1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.ProjekteIndikatorenEditor();
        panMaInd = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo4 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading4 = new javax.swing.JLabel();
        panInfoContent4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlIndikator1 = new javax.swing.JList();
        panIndikator1 = new javax.swing.JPanel();
        btnRemMaIndikator = new javax.swing.JButton();
        btnAddMaIndikator = new javax.swing.JButton();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        dlgIndikator.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblIndikatorCataloge.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.lblIndikatorCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgIndikator.getContentPane().add(lblIndikatorCataloge, gridBagConstraints);

        cbIndikatorCataloge.setMinimumSize(new java.awt.Dimension(500, 18));
        cbIndikatorCataloge.setPreferredSize(new java.awt.Dimension(500, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgIndikator.getContentPane().add(cbIndikatorCataloge, gridBagConstraints);

        panMenButtonsIndikator.setLayout(new java.awt.GridBagLayout());

        btnIndikatorAbort.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.btnIndikatorAbort.text")); // NOI18N
        btnIndikatorAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnIndikatorAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsIndikator.add(btnIndikatorAbort, gridBagConstraints);

        btnIndikatorOk.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.btnIndikatorOk.text")); // NOI18N
        btnIndikatorOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnIndikatorOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnIndikatorOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnIndikatorOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnIndikatorOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsIndikator.add(btnIndikatorOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgIndikator.getContentPane().add(panMenButtonsIndikator, gridBagConstraints);

        dlgIndikatorUms.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblIndikatorUmsCataloge.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.lblIndikatorUmsCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgIndikatorUms.getContentPane().add(lblIndikatorUmsCataloge, gridBagConstraints);

        cbIndikatorUmsCataloge.setMinimumSize(new java.awt.Dimension(500, 18));
        cbIndikatorUmsCataloge.setPreferredSize(new java.awt.Dimension(500, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgIndikatorUms.getContentPane().add(cbIndikatorUmsCataloge, gridBagConstraints);

        panMenButtonsIndikatorUms.setLayout(new java.awt.GridBagLayout());

        btnIndikatorUmsAbort.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.btnIndikatorUmsAbort.text")); // NOI18N
        btnIndikatorUmsAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnIndikatorUmsAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsIndikatorUms.add(btnIndikatorUmsAbort, gridBagConstraints);

        btnIndikatorUmsOk.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.btnIndikatorUmsOk.text")); // NOI18N
        btnIndikatorUmsOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnIndikatorUmsOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnIndikatorUmsOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnIndikatorUmsOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnIndikatorUmsOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsIndikatorUms.add(btnIndikatorUmsOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgIndikatorUms.getContentPane().add(panMenButtonsIndikatorUms, gridBagConstraints);

        setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblProjekt_nr.setText("Projektnummer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        jPanel3.add(lblProjekt_nr, gridBagConstraints);

        txtValProjekt_nr.setEnabled(false);
        txtValProjekt_nr.setMinimumSize(new java.awt.Dimension(215, 20));
        txtValProjekt_nr.setPreferredSize(new java.awt.Dimension(215, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.id}"),
                txtValProjekt_nr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(txtValProjekt_nr, gridBagConstraints);

        lbltraeger.setText("Antragsteller");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        jPanel3.add(lbltraeger, gridBagConstraints);

        lblkost_gesamt.setText("Gesamtkosten");
        lblkost_gesamt.setMinimumSize(new java.awt.Dimension(115, 17));
        lblkost_gesamt.setPreferredSize(new java.awt.Dimension(115, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblkost_gesamt, gridBagConstraints);

        lblfoerdersatz.setText("Fördersatz");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblfoerdersatz, gridBagConstraints);

        txtValTraeger.setMinimumSize(new java.awt.Dimension(150, 20));
        txtValTraeger.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.traeger}"),
                txtValTraeger,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(txtValTraeger, gridBagConstraints);

        txtValFoerdersatz.setMinimumSize(new java.awt.Dimension(100, 20));
        txtValFoerdersatz.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.foerdersatz}"),
                txtValFoerdersatz,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanel3.add(txtValFoerdersatz, gridBagConstraints);

        txtValKost_gesamt.setMinimumSize(new java.awt.Dimension(100, 20));
        txtValKost_gesamt.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kost_gesamt}"),
                txtValKost_gesamt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(txtValKost_gesamt, gridBagConstraints);

        lblProjekt_code.setText("Förigef Schlüssel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblProjekt_code, gridBagConstraints);

        lblM_beginn.setText("Projektbeginn");
        lblM_beginn.setMinimumSize(new java.awt.Dimension(90, 17));
        lblM_beginn.setPreferredSize(new java.awt.Dimension(90, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblM_beginn, gridBagConstraints);

        lblM_ende.setText("Projektende");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblM_ende, gridBagConstraints);

        lblKost_forderf.setText("förderfähige Kosten");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblKost_forderf, gridBagConstraints);

        lblFoerdersumme.setText("Fördersumme");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel3.add(lblFoerdersumme, gridBagConstraints);

        txtValProjekt_code.setMinimumSize(new java.awt.Dimension(150, 20));
        txtValProjekt_code.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.projekt_code}"),
                txtValProjekt_code,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(txtValProjekt_code, gridBagConstraints);

        txtValKost_forderf.setMinimumSize(new java.awt.Dimension(100, 20));
        txtValKost_forderf.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kost_forderf}"),
                txtValKost_forderf,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        jPanel3.add(txtValKost_forderf, gridBagConstraints);

        txtValfoerdersumme.setMinimumSize(new java.awt.Dimension(100, 20));
        txtValfoerdersumme.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.foerdersumme}"),
                txtValfoerdersumme,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel3.add(txtValfoerdersumme, gridBagConstraints);

        lblProjekt_bez.setText("Bez. des Projektes");
        lblProjekt_bez.setToolTipText("Bezeichnung des Projektes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel3.add(lblProjekt_bez, gridBagConstraints);

        txtValProjekt_bez.setMinimumSize(new java.awt.Dimension(150, 20));
        txtValProjekt_bez.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.projekt_bez}"),
                txtValProjekt_bez,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtValProjekt_bez, gridBagConstraints);

        dcValM_beginn.setMinimumSize(new java.awt.Dimension(150, 20));
        dcValM_beginn.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.m_beginn}"),
                dcValM_beginn,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(dcValM_beginn.getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(dcValM_beginn, gridBagConstraints);

        dcValM_ende.setMinimumSize(new java.awt.Dimension(150, 20));
        dcValM_ende.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.m_ende}"),
                dcValM_ende,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(dcValM_ende.getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(dcValM_ende, gridBagConstraints);

        cbGeom.setMinimumSize(new java.awt.Dimension(200, 20));
        cbGeom.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.geom}"),
                cbGeom,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setConverter(((DefaultCismapGeometryComboBoxEditor)cbGeom).getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbGeom, gridBagConstraints);

        lblGeometrie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblGeometrie.setText("Geometrie");
        lblGeometrie.setToolTipText("Bezeichnung des Projektes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblGeometrie, gridBagConstraints);

        txtValKurzBez.setMinimumSize(new java.awt.Dimension(150, 20));
        txtValKurzBez.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.projekt_kurz_bez}"),
                txtValKurzBez,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(txtValKurzBez, gridBagConstraints);

        lblKurzBez.setText("Kurzbezeichnung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        jPanel3.add(lblKurzBez, gridBagConstraints);

        jcLand.setText(org.openide.util.NbBundle.getMessage(ProjekteEditor.class, "ProjekteEditor.jcLand.text")); // NOI18N
        jcLand.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.landesweit}"),
                jcLand,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        jcLand.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jcLandActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jcLand, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jPanel3, gridBagConstraints);

        panDeMeas.setMinimumSize(new java.awt.Dimension(290, 660));
        panDeMeas.setPreferredSize(new java.awt.Dimension(290, 660));

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText("Umsetzungen");
        panHeadInfo2.add(lblHeading2);

        panDeMeas.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(240, 550));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(240, 550));

        jlUmsetzung.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.umsetzung}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        jlUmsetzung);
        bindingGroup.addBinding(jListBinding);

        jlUmsetzung.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    jlUmsetzungValueChanged(evt);
                }
            });
        jScrollPane1.setViewportView(jlUmsetzung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 25, 10, 5);
        panInfoContent2.add(jScrollPane1, gridBagConstraints);

        panContrImpactSrc.setOpaque(false);
        panContrImpactSrc.setLayout(new java.awt.GridBagLayout());

        btnRemUmsetzung.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_24.png"))); // NOI18N
        btnRemUmsetzung.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemUmsetzungActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        panContrImpactSrc.add(btnRemUmsetzung, gridBagConstraints);

        btnAddUmsetzung.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_24.png"))); // NOI18N
        btnAddUmsetzung.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddUmsetzungActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panContrImpactSrc.add(btnAddUmsetzung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 5, 30);
        panInfoContent2.add(panContrImpactSrc, gridBagConstraints);

        panDeMeas.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 12);
        add(panDeMeas, gridBagConstraints);

        massnahmenUmsetzungEditor.setMinimumSize(new java.awt.Dimension(460, 660));
        massnahmenUmsetzungEditor.setPreferredSize(new java.awt.Dimension(460, 660));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        add(massnahmenUmsetzungEditor, gridBagConstraints);

        panDeMeas1.setMinimumSize(new java.awt.Dimension(350, 225));
        panDeMeas1.setPreferredSize(new java.awt.Dimension(350, 225));

        panHeadInfo3.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo3.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setLayout(new java.awt.FlowLayout());

        lblHeading3.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading3.setText("Projektbezogene Indikatoren");
        panHeadInfo3.add(lblHeading3);

        panDeMeas1.add(panHeadInfo3, java.awt.BorderLayout.NORTH);

        panInfoContent3.setOpaque(false);
        panInfoContent3.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setMinimumSize(new java.awt.Dimension(290, 120));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(290, 120));

        jlIndikator.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.indikator}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                eLProperty,
                jlIndikator);
        bindingGroup.addBinding(jListBinding);

        jlIndikator.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    jlIndikatorValueChanged(evt);
                }
            });
        jScrollPane2.setViewportView(jlIndikator);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 30, 0, 5);
        panInfoContent3.add(jScrollPane2, gridBagConstraints);

        panIndikator.setOpaque(false);
        panIndikator.setLayout(new java.awt.GridBagLayout());

        btnRemIndikator.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_24.png"))); // NOI18N
        btnRemIndikator.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemIndikatorActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        panIndikator.add(btnRemIndikator, gridBagConstraints);

        btnAddIndikator.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_24.png"))); // NOI18N
        btnAddIndikator.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddIndikatorActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panIndikator.add(btnAddIndikator, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 5, 30);
        panInfoContent3.add(panIndikator, gridBagConstraints);

        panDeMeas1.add(panInfoContent3, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 12, 5, 0);
        add(panDeMeas1, gridBagConstraints);

        projekteIndikatorenEditor1.setMinimumSize(new java.awt.Dimension(350, 180));
        projekteIndikatorenEditor1.setPreferredSize(new java.awt.Dimension(350, 180));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 0);
        add(projekteIndikatorenEditor1, gridBagConstraints);

        panMaInd.setMinimumSize(new java.awt.Dimension(350, 225));
        panMaInd.setPreferredSize(new java.awt.Dimension(350, 225));

        panHeadInfo4.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo4.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setLayout(new java.awt.FlowLayout());

        lblHeading4.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading4.setText("Umsetzungsbezogene Indikatoren");
        panHeadInfo4.add(lblHeading4);

        panMaInd.add(panHeadInfo4, java.awt.BorderLayout.NORTH);

        panInfoContent4.setOpaque(false);
        panInfoContent4.setLayout(new java.awt.GridBagLayout());

        jScrollPane3.setMinimumSize(new java.awt.Dimension(290, 120));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(290, 120));

        jlIndikator1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlIndikator1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    jlIndikator1ValueChanged(evt);
                }
            });
        jScrollPane3.setViewportView(jlIndikator1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 30, 0, 5);
        panInfoContent4.add(jScrollPane3, gridBagConstraints);

        panIndikator1.setOpaque(false);
        panIndikator1.setLayout(new java.awt.GridBagLayout());

        btnRemMaIndikator.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_24.png"))); // NOI18N
        btnRemMaIndikator.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemMaIndikatorActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        panIndikator1.add(btnRemMaIndikator, gridBagConstraints);

        btnAddMaIndikator.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_24.png"))); // NOI18N
        btnAddMaIndikator.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddMaIndikatorActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panIndikator1.add(btnAddMaIndikator, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 30, 5, 30);
        panInfoContent4.add(panIndikator1, gridBagConstraints);

        panMaInd.add(panInfoContent4, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
        add(panMaInd, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemUmsetzungActionPerformed(final java.awt.event.ActionEvent evt) {                         //GEN-FIRST:event_btnRemUmsetzungActionPerformed
        final Object selection = jlUmsetzung.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Soll die Umsetzung wirklich gelöscht werden?",
                    "Umsetzung entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("umsetzung");
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                        CidsBeanSupport.deleteStationlineIfExists(beanToDelete, "linie", beansToDelete);
                        CidsBeanSupport.deletePropertyIfExists(beanToDelete, "additional_geom", beansToDelete); // NOI18N
                        beanToDelete.delete();
                        massnahmenUmsetzungEditor.setCidsBean(null);
                        jlIndikator1.clearSelection();
                        jlIndikator1.removeAll();
                    }
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                                           //GEN-LAST:event_btnRemUmsetzungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jlUmsetzungValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_jlUmsetzungValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object selObj = jlUmsetzung.getSelectedValue();
            if (selObj instanceof CidsBean) {
                massnahmenUmsetzungEditor.setCidsBean((CidsBean)selObj);
                final List<CidsBean> indikatorList = CidsBeanSupport.getBeanCollectionFromProperty((CidsBean)selObj,
                        "indikator");                                                      // NOI18N

                // checks if the currently shown indicator is one of the list jlIndikator1, which will be cleaned
                final CidsBean currentIndicatior = projekteIndikatorenEditor1.getCidsBean();
                if ((currentIndicatior != null) && (jlIndikator1.getSelectedValue() == currentIndicatior)) {
                    projekteIndikatorenEditor1.setCidsBean(null);
                }

                jlIndikator1.removeAll();
                if (indikatorList != null) {
                    jlIndikator1.setListData(indikatorList.toArray());
                }
            }
        }
    } //GEN-LAST:event_jlUmsetzungValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jlIndikatorValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_jlIndikatorValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object selObj = jlIndikator.getSelectedValue();
            if (selObj instanceof CidsBean) {
                jlIndikator1.clearSelection();
                jlIndikator.setSelectedValue(selObj, false);
                projekteIndikatorenEditor1.setCidsBean((CidsBean)selObj);
            } else {
                projekteIndikatorenEditor1.setCidsBean(null);
            }
        }
    }                                                                                      //GEN-LAST:event_jlIndikatorValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddUmsetzungActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddUmsetzungActionPerformed
        final Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName(
                                    "MASSNAHMEN_UMSETZUNG");                                   // NOI18N
                            final Collection<CidsBean> umsetzungCollection = CidsBeanSupport
                                        .getBeanCollectionFromProperty(cidsBean, "umsetzung"); // NOI18N
                            umsetzungCollection.add(newBean);
                            jlUmsetzung.setSelectedValue(newBean, true);
                        } catch (final Exception ex) {
                            LOG.error(ex, ex);
                        }
                    }
                });

        t.start();
    } //GEN-LAST:event_btnAddUmsetzungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemIndikatorActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemIndikatorActionPerformed
        final Object selection = jlIndikator.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Soll der ausgewählte Indikator wirklich gelöscht werden?",
                    "Indikator entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("indikator");          // NOI18N
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                        beanToDelete.delete();
                    }
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                   //GEN-LAST:event_btnRemIndikatorActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddIndikatorActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddIndikatorActionPerformed
        UIUtil.findOptimalPositionOnScreen(dlgIndikator);
        dlgIndikator.setSize(550, 150);
        dlgIndikator.setVisible(true);
    }                                                                                   //GEN-LAST:event_btnAddIndikatorActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jlIndikator1ValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_jlIndikator1ValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object selObj = jlIndikator1.getSelectedValue();
            if (selObj instanceof CidsBean) {
                jlIndikator.clearSelection();
                jlIndikator1.setSelectedValue(selObj, false);
                projekteIndikatorenEditor1.setCidsBean((CidsBean)selObj);
            } else {
                projekteIndikatorenEditor1.setCidsBean(null);
            }
        }
    }                                                                                       //GEN-LAST:event_jlIndikator1ValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemMaIndikatorActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemMaIndikatorActionPerformed
        final Object selection = jlIndikator1.getSelectedValue();
        final CidsBean umsetzung = (CidsBean)jlUmsetzung.getSelectedValue();

        if ((selection != null) && (umsetzung != null)) {
            final int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Soll der ausgewählte Indikator wirklich gelöscht werden?",
                    "Indikator entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = umsetzung.getProperty("indikator"); // NOI18N
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                        beanToDelete.delete();
                        jlIndikator1.setListData(((Collection)beanColl).toArray());
                        jlIndikator1.clearSelection();
                    }
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                           //GEN-LAST:event_btnRemMaIndikatorActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddMaIndikatorActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddMaIndikatorActionPerformed
        final CidsBean umsetzung = (CidsBean)jlUmsetzung.getSelectedValue();

        if (umsetzung != null) {
            UIUtil.findOptimalPositionOnScreen(dlgIndikatorUms);
            dlgIndikatorUms.setSize(550, 150);
            dlgIndikatorUms.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Sie haben noch keine Umsetzung ausgewählt, zu der der Indikator hinzugefügt werden soll.",
                "Umsetzung auswählen",
                JOptionPane.INFORMATION_MESSAGE);
        }
    } //GEN-LAST:event_btnAddMaIndikatorActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnIndikatorAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnIndikatorAbortActionPerformed
        dlgIndikator.setVisible(false);
    }                                                                                     //GEN-LAST:event_btnIndikatorAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnIndikatorOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnIndikatorOkActionPerformed
        final Object selection = cbIndikatorCataloge.getSelectedItem();

        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean)selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "indikator"); // NOI18N

            if (colToAdd != null) {
                final Thread t = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName(
                                            "projekte_indikatoren");                           // NOI18N
                                    newBean.setProperty("indikator_schluessel", selectedBean); // NOI18N
                                    colToAdd.add(newBean);
                                    jlIndikator.setSelectedValue(newBean, true);
                                } catch (final Exception ex) {
                                    LOG.error(ex, ex);
                                }
                            }
                        });
                t.start();
            } else {
                LOG.error("The property indikator was not found.");
            }
        }

        dlgIndikator.setVisible(false);
    } //GEN-LAST:event_btnIndikatorOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnIndikatorUmsAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnIndikatorUmsAbortActionPerformed
        dlgIndikatorUms.setVisible(false);
    }                                                                                        //GEN-LAST:event_btnIndikatorUmsAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnIndikatorUmsOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnIndikatorUmsOkActionPerformed
        final Object selection = cbIndikatorUmsCataloge.getSelectedItem();
        final CidsBean umsetzung = (CidsBean)jlUmsetzung.getSelectedValue();

        if ((umsetzung != null) && (selection instanceof CidsBean)) {
            final CidsBean selectedBean = (CidsBean)selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(umsetzung, "indikator"); // NOI18N

            if (colToAdd != null) {
                final Thread t = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName(
                                            "projekte_indikatoren");                           // NOI18N
                                    newBean.setProperty("indikator_schluessel", selectedBean); // NOI18N
                                    colToAdd.add(newBean);
                                    jlIndikator1.setListData(colToAdd.toArray());
                                    jlIndikator1.setSelectedValue(newBean, true);
                                } catch (final Exception ex) {
                                    LOG.error(ex, ex);
                                }
                            }
                        });
                t.start();
            } else {
                LOG.error("The property indikator was not found.");
            }
        } else if (umsetzung == null) {
            LOG.error("No implementation selected.");
        }

        dlgIndikatorUms.setVisible(false);
    } //GEN-LAST:event_btnIndikatorUmsOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jcLandActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jcLandActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_jcLandActionPerformed

    @Override
    public void dispose() {
        massnahmenUmsetzungEditor.dispose();
        ((DefaultCismapGeometryComboBoxEditor)cbGeom).dispose();
        projekteIndikatorenEditor1.dispose();
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
        if (cidsBean != null) {
            try {
                cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());   // NOI18N
                cidsBean.setProperty("av_time", new java.sql.Timestamp(System.currentTimeMillis())); // NOI18N
            } catch (final Exception ex) {
                LOG.error(ex, ex);
            }

            for (final CidsBean bean : beansToDelete) {
                try {
                    bean.persist();
                } catch (final Exception e) {
                    LOG.error("Error while deleting a bean.", e); // NOI18N
                }
            }
        }

        return massnahmenUmsetzungEditor.prepareForSave();
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  act  DOCUMENT ME!
     */
    private void addImplementationFromAction(final CidsBean act) {
        try {
            final CidsBean newBean = ClassCacheMultiple.getMetaClass(
                        CidsBeanSupport.DOMAIN_NAME,
                        "massnahmen_umsetzung")
                        .getEmptyInstance()
                        .getBean(); // NOI18N

            final List<CidsBean> impls = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "umsetzung"); // NOI18N
            if (impls != null) {
                impls.add(newBean);
                final ArrayList<CidsBean> beanList = new ArrayList<CidsBean>();
                beanList.add(act);

                jlUmsetzung.setSelectedValue(newBean, true);
                massnahmenUmsetzungEditor.beansDropped(beanList);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The property umsetzung returns null"); // NOI18N
                }
            }
        } catch (final Exception e) {
            LOG.error("Error during the creation of a new bean of type massnahmen", e); // NOI18N
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * a JList that implements the CidsBeanDropListener to catch objects of the type massnahme.
     *
     * @version  $Revision$, $Date$
     */
    private class ImplementationList extends JList implements CidsBeanDropListener {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ImplementationList object.
         */
        public ImplementationList() {
            try {
                new CidsBeanDropTarget(this);
            } catch (final Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error while creating CidsBeanDropTarget", ex); // NOI18N
                }
            }
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            if (cidsBean != null) {
                for (final CidsBean bean : beans) {
                    if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Massnahmen")) { // NOI18N
                        addImplementationFromAction(bean);
                    }
                }
            }
        }
    }
}
