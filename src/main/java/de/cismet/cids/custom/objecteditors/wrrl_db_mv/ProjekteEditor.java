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
import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.awt.EventQueue;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.CurrencyConverter;
import de.cismet.cids.custom.wrrl_db_mv.util.MassnahmenUmsetzungCache;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.StringIntValueComparator;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.wrrl_db_mv.util.TimestampConverter;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;

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
import de.cismet.tools.gui.StaticSwingTools;

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
    private static final MetaClass MASSNAHMEN_TYP_MC;
    private static final MetaClass MASSNAHMEN_GROUP_MC;
    private static final MetaClass MASSNAHMEN_MC;

    static {
        INDIKATOR_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "indikator");                 // NOI18N
        MASSNAHMEN_TYP_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "wfd.de_measure_type_code");  // NOI18N
        MASSNAHMEN_GROUP_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "wfd.de_measure_type_group"); // NOI18N
        MASSNAHMEN_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "massnahmen");                // NOI18N
    }

    private static final String MASSN_FIN_PROPERTY = "massn_fin";

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;
    private final MeasureTypeModel measureModel = new MeasureTypeModel();
    private final MeasureTypeModelAll measureModelAll = new MeasureTypeModelAll();
    private CidsBean cidsBean;
    private ArrayList<CidsBean> beansToDelete = new ArrayList<CidsBean>();
    private MassnahmenUmsetzungCache cache;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.AggregierterMassnahmenTypEditor
        aggregierterMassnahmenTypEditor1;
    private javax.swing.JButton btnAddIndikator;
    private javax.swing.JButton btnAddUmsetzung;
    private javax.swing.JButton btnAddUmsetzung1;
    private javax.swing.JButton btnIndikatorAbort;
    private javax.swing.JButton btnIndikatorOk;
    private javax.swing.JButton btnMassnahmenTypAbort;
    private javax.swing.JButton btnMassnahmenTypAllAbort;
    private javax.swing.JButton btnMassnahmenTypAllOk;
    private javax.swing.JButton btnMassnahmenTypOk;
    private javax.swing.JButton btnRemIndikator;
    private javax.swing.JButton btnRemUmsetzung;
    private javax.swing.JButton btnRemUmsetzung1;
    private javax.swing.JComboBox cbGeom;
    private javax.swing.JComboBox cbIndikatorCataloge;
    private javax.swing.JComboBox cbMassnahmenTypCataloge;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbStatus;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcValM_beginn;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcValM_ende;
    private javax.swing.JDialog dlgIndikator;
    private javax.swing.JDialog dlgMassnahmenTyp;
    private javax.swing.JDialog dlgMassnahmenTypAll;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JCheckBox jcLand;
    private javax.swing.JList jlIndikator;
    private javax.swing.JList jlMassnahme;
    private javax.swing.JList jlMassnahmenAllCataloge;
    private javax.swing.JList jlMassnahmenCataloge;
    private javax.swing.JList jlUmsetzung;
    private javax.swing.JLabel lblFoerdersumme;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGeometrie;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblHeading3;
    private javax.swing.JLabel lblHeading4;
    private javax.swing.JLabel lblHeading5;
    private javax.swing.JLabel lblHeading6;
    private javax.swing.JLabel lblIndikatorCataloge;
    private javax.swing.JLabel lblKost_forderf;
    private javax.swing.JLabel lblKurzBez;
    private javax.swing.JLabel lblM_beginn;
    private javax.swing.JLabel lblM_ende;
    private javax.swing.JLabel lblMassnahmenGruppeCataloge;
    private javax.swing.JLabel lblMassnahmenTypAllCataloge;
    private javax.swing.JLabel lblMassnahmenTypCataloge;
    private javax.swing.JLabel lblProjekt_bez;
    private javax.swing.JLabel lblProjekt_code;
    private javax.swing.JLabel lblProjekt_nr;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblfoerdersatz;
    private javax.swing.JLabel lblkost_gesamt;
    private javax.swing.JLabel lbltraeger;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.MassnahmenUmsetzungEditor massnahmenUmsetzungEditor;
    private javax.swing.JPanel panAllgemein;
    private javax.swing.JPanel panContrImpactSrc;
    private javax.swing.JPanel panContrImpactSrc1;
    private de.cismet.tools.gui.RoundedPanel panDeMeas;
    private de.cismet.tools.gui.RoundedPanel panDeMeas1;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo3;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo4;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo5;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo6;
    private javax.swing.JPanel panIndikator;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent2;
    private javax.swing.JPanel panInfoContent3;
    private javax.swing.JPanel panInfoContent4;
    private javax.swing.JPanel panInfoContent5;
    private javax.swing.JPanel panInfoContent6;
    private de.cismet.tools.gui.RoundedPanel panKosten;
    private de.cismet.tools.gui.RoundedPanel panKosten1;
    private de.cismet.tools.gui.RoundedPanel panKosten2;
    private de.cismet.tools.gui.RoundedPanel panKosten3;
    private de.cismet.tools.gui.RoundedPanel panMeasTypes;
    private javax.swing.JPanel panMenButtonsIndikator;
    private javax.swing.JPanel panMenButtonsMassnahmenTyp;
    private javax.swing.JPanel panMenButtonsMassnahmenTyp1;
    private javax.swing.JPanel panProjekt;
    private de.cismet.tools.gui.RoundedPanel panSubProjekt;
    private javax.swing.JPanel panUmsetzungen;
    private de.cismet.tools.gui.RoundedPanel panZus;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.ProjekteIndikatorenEditor projekteIndikatorenEditor1;
    private javax.swing.JFormattedTextField txtValFoerdersatz;
    private javax.swing.JFormattedTextField txtValKost_forderf;
    private javax.swing.JFormattedTextField txtValKost_gesamt;
    private javax.swing.JTextField txtValKurzBez;
    private javax.swing.JTextArea txtValProjekt_bez;
    private javax.swing.JTextField txtValProjekt_code;
    private javax.swing.JTextField txtValProjekt_nr;
    private javax.swing.JTextField txtValTraeger;
    private javax.swing.JFormattedTextField txtValfoerdersumme;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public ProjekteEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public ProjekteEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
        jTabbedPane1.setUI(new TabbedPaneUITransparent());
        deActivateGUI(!readOnly);
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

    /**
     * DOCUMENT ME!
     *
     * @param  enabled  DOCUMENT ME!
     */
    private void deActivateGUI(final boolean enabled) {
        txtValFoerdersatz.setEnabled(enabled);
        txtValKost_forderf.setEnabled(enabled);
        txtValKost_gesamt.setEnabled(enabled);
        txtValKurzBez.setEnabled(enabled);
        txtValProjekt_bez.setEnabled(enabled);
        txtValProjekt_code.setEnabled(enabled);
//        txtValProjekt_nr.setEnabled(enabled);
        txtValTraeger.setEnabled(enabled);
        txtValfoerdersumme.setEnabled(enabled);

        panIndikator.setVisible(enabled);
        panContrImpactSrc.setVisible(enabled);
        panContrImpactSrc1.setVisible(enabled);

        if (cbGeom != null) {
            cbGeom.setVisible(enabled);
        }
        lblGeometrie.setVisible(enabled);
        dcValM_beginn.setEditable(enabled);
        dcValM_ende.setEditable(enabled);
        cbStatus.setEnabled(enabled);
//        cbStatus.setEditable(enabled);
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
        dlgIndikator = new JDialog(StaticSwingTools.getParentFrame(this));
        lblIndikatorCataloge = new javax.swing.JLabel();
        cbIndikatorCataloge = new ScrollableComboBox(INDIKATOR_MC, false, false, new IndikatorComparator());
        panMenButtonsIndikator = new javax.swing.JPanel();
        btnIndikatorAbort = new javax.swing.JButton();
        btnIndikatorOk = new javax.swing.JButton();
        dlgMassnahmenTyp = new JDialog(StaticSwingTools.getParentFrame(this));
        lblMassnahmenGruppeCataloge = new javax.swing.JLabel();
        cbMassnahmenTypCataloge = new ScrollableComboBox(MASSNAHMEN_GROUP_MC, true, false);
        panMenButtonsMassnahmenTyp = new javax.swing.JPanel();
        btnMassnahmenTypAbort = new javax.swing.JButton();
        btnMassnahmenTypOk = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jlMassnahmenCataloge = new javax.swing.JList();
        lblMassnahmenTypCataloge = new javax.swing.JLabel();
        dlgMassnahmenTypAll = new JDialog(StaticSwingTools.getParentFrame(this));
        panMenButtonsMassnahmenTyp1 = new javax.swing.JPanel();
        btnMassnahmenTypAllAbort = new javax.swing.JButton();
        btnMassnahmenTypAllOk = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jlMassnahmenAllCataloge = new javax.swing.JList();
        lblMassnahmenTypAllCataloge = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panProjekt = new javax.swing.JPanel();
        panSubProjekt = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panAllgemein = new javax.swing.JPanel();
        lblProjekt_nr = new javax.swing.JLabel();
        txtValProjekt_nr = new javax.swing.JTextField();
        lbltraeger = new javax.swing.JLabel();
        txtValTraeger = new javax.swing.JTextField();
        lblProjekt_code = new javax.swing.JLabel();
        txtValProjekt_code = new javax.swing.JTextField();
        lblProjekt_bez = new javax.swing.JLabel();
        txtValKurzBez = new javax.swing.JTextField();
        lblKurzBez = new javax.swing.JLabel();
        jcLand = new javax.swing.JCheckBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtValProjekt_bez = new javax.swing.JTextArea();
        panDeMeas1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo3 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading3 = new javax.swing.JLabel();
        panInfoContent3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlIndikator = new javax.swing.JList();
        panIndikator = new javax.swing.JPanel();
        btnRemIndikator = new javax.swing.JButton();
        btnAddIndikator = new javax.swing.JButton();
        projekteIndikatorenEditor1 = new ProjekteIndikatorenEditor(readOnly);
        panKosten1 = new de.cismet.tools.gui.RoundedPanel();
        panKosten = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo5 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading5 = new javax.swing.JLabel();
        panInfoContent5 = new javax.swing.JPanel();
        lblkost_gesamt = new javax.swing.JLabel();
        lblKost_forderf = new javax.swing.JLabel();
        lblfoerdersatz = new javax.swing.JLabel();
        lblFoerdersumme = new javax.swing.JLabel();
        txtValKost_gesamt = new javax.swing.JFormattedTextField();
        txtValKost_forderf = new javax.swing.JFormattedTextField();
        txtValFoerdersatz = new javax.swing.JFormattedTextField();
        txtValfoerdersumme = new javax.swing.JFormattedTextField();
        panZus = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo6 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading6 = new javax.swing.JLabel();
        panInfoContent6 = new javax.swing.JPanel();
        dcValM_beginn = new de.cismet.cids.editors.DefaultBindableDateChooser();
        lblM_beginn = new javax.swing.JLabel();
        lblM_ende = new javax.swing.JLabel();
        dcValM_ende = new de.cismet.cids.editors.DefaultBindableDateChooser();
        lblGeometrie = new javax.swing.JLabel();
        if (!readOnly) {
            cbGeom = new DefaultCismapGeometryComboBoxEditor();
        }
        lblStatus = new javax.swing.JLabel();
        cbStatus = new ScrollableComboBox();
        jPanel3 = new javax.swing.JPanel();
        panUmsetzungen = new javax.swing.JPanel();
        panDeMeas = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlUmsetzung = new ImplementationList();
        panContrImpactSrc = new javax.swing.JPanel();
        btnRemUmsetzung = new javax.swing.JButton();
        btnAddUmsetzung = new javax.swing.JButton();
        massnahmenUmsetzungEditor = new MassnahmenUmsetzungEditor(false, readOnly);
        panKosten2 = new de.cismet.tools.gui.RoundedPanel();
        panMeasTypes = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo4 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading4 = new javax.swing.JLabel();
        panInfoContent4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlMassnahme = new ImplementationList();
        panContrImpactSrc1 = new javax.swing.JPanel();
        btnRemUmsetzung1 = new javax.swing.JButton();
        btnAddUmsetzung1 = new javax.swing.JButton();
        aggregierterMassnahmenTypEditor1 = new AggregierterMassnahmenTypEditor(false, readOnly);
        panKosten3 = new de.cismet.tools.gui.RoundedPanel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        dlgMassnahmenTyp.setTitle(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.dlgMassnahmenTyp.title")); // NOI18N
        dlgMassnahmenTyp.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblMassnahmenGruppeCataloge.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.lblMassnahmenGruppeCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMassnahmenTyp.getContentPane().add(lblMassnahmenGruppeCataloge, gridBagConstraints);

        cbMassnahmenTypCataloge.setMinimumSize(new java.awt.Dimension(750, 18));
        cbMassnahmenTypCataloge.setPreferredSize(new java.awt.Dimension(750, 18));
        cbMassnahmenTypCataloge.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbMassnahmenTypCatalogeItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMassnahmenTyp.getContentPane().add(cbMassnahmenTypCataloge, gridBagConstraints);

        panMenButtonsMassnahmenTyp.setLayout(new java.awt.GridBagLayout());

        btnMassnahmenTypAbort.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.btnMassnahmenTypAbort.text")); // NOI18N
        btnMassnahmenTypAbort.setMinimumSize(new java.awt.Dimension(105, 23));
        btnMassnahmenTypAbort.setPreferredSize(new java.awt.Dimension(105, 23));
        btnMassnahmenTypAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMassnahmenTypAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        panMenButtonsMassnahmenTyp.add(btnMassnahmenTypAbort, gridBagConstraints);

        btnMassnahmenTypOk.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.btnMassnahmentypOk.text")); // NOI18N
        btnMassnahmenTypOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMassnahmenTypOk.setMinimumSize(new java.awt.Dimension(105, 23));
        btnMassnahmenTypOk.setPreferredSize(new java.awt.Dimension(105, 23));
        btnMassnahmenTypOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMassnahmenTypOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        panMenButtonsMassnahmenTyp.add(btnMassnahmenTypOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        dlgMassnahmenTyp.getContentPane().add(panMenButtonsMassnahmenTyp, gridBagConstraints);

        jScrollPane5.setMinimumSize(new java.awt.Dimension(750, 200));
        jScrollPane5.setPreferredSize(new java.awt.Dimension(750, 200));

        jlMassnahmenCataloge.setModel(measureModel);
        jScrollPane5.setViewportView(jlMassnahmenCataloge);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        dlgMassnahmenTyp.getContentPane().add(jScrollPane5, gridBagConstraints);

        lblMassnahmenTypCataloge.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.lblMassnahmenTypCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        dlgMassnahmenTyp.getContentPane().add(lblMassnahmenTypCataloge, gridBagConstraints);

        dlgMassnahmenTypAll.setTitle(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.dlgMassnahmenTyp.title")); // NOI18N
        dlgMassnahmenTypAll.getContentPane().setLayout(new java.awt.GridBagLayout());

        panMenButtonsMassnahmenTyp1.setLayout(new java.awt.GridBagLayout());

        btnMassnahmenTypAllAbort.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.btnMassnahmenTypAbort.text")); // NOI18N
        btnMassnahmenTypAllAbort.setMinimumSize(new java.awt.Dimension(105, 23));
        btnMassnahmenTypAllAbort.setPreferredSize(new java.awt.Dimension(105, 23));
        btnMassnahmenTypAllAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMassnahmenTypAllAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        panMenButtonsMassnahmenTyp1.add(btnMassnahmenTypAllAbort, gridBagConstraints);

        btnMassnahmenTypAllOk.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.btnMassnahmentypOk.text")); // NOI18N
        btnMassnahmenTypAllOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMassnahmenTypAllOk.setMinimumSize(new java.awt.Dimension(105, 23));
        btnMassnahmenTypAllOk.setPreferredSize(new java.awt.Dimension(105, 23));
        btnMassnahmenTypAllOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMassnahmenTypAllOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        panMenButtonsMassnahmenTyp1.add(btnMassnahmenTypAllOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        dlgMassnahmenTypAll.getContentPane().add(panMenButtonsMassnahmenTyp1, gridBagConstraints);

        jScrollPane6.setMinimumSize(new java.awt.Dimension(750, 200));
        jScrollPane6.setPreferredSize(new java.awt.Dimension(750, 200));

        jlMassnahmenAllCataloge.setModel(measureModelAll);
        jScrollPane6.setViewportView(jlMassnahmenAllCataloge);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        dlgMassnahmenTypAll.getContentPane().add(jScrollPane6, gridBagConstraints);

        lblMassnahmenTypAllCataloge.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.lblMassnahmenTypCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        dlgMassnahmenTypAll.getContentPane().add(lblMassnahmenTypAllCataloge, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(924, 770));
        setPreferredSize(new java.awt.Dimension(924, 770));
        setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);

        panProjekt.setMinimumSize(new java.awt.Dimension(900, 741));
        panProjekt.setOpaque(false);
        panProjekt.setPreferredSize(new java.awt.Dimension(900, 741));
        panProjekt.setLayout(new java.awt.GridBagLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(ProjekteEditor.class, "WkFgPanOne.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panSubProjekt.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        panAllgemein.setOpaque(false);
        panAllgemein.setLayout(new java.awt.GridBagLayout());

        lblProjekt_nr.setText("Projektnummer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        panAllgemein.add(lblProjekt_nr, gridBagConstraints);

        txtValProjekt_nr.setEditable(false);
        txtValProjekt_nr.setDisabledTextColor(new java.awt.Color(26, 26, 26));
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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        panAllgemein.add(txtValProjekt_nr, gridBagConstraints);

        lbltraeger.setText("Antragsteller");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        panAllgemein.add(lbltraeger, gridBagConstraints);

        txtValTraeger.setDisabledTextColor(new java.awt.Color(26, 26, 26));
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
        panAllgemein.add(txtValTraeger, gridBagConstraints);

        lblProjekt_code.setText("Förigef Schlüssel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        panAllgemein.add(lblProjekt_code, gridBagConstraints);

        txtValProjekt_code.setDisabledTextColor(new java.awt.Color(26, 26, 26));
        txtValProjekt_code.setMinimumSize(new java.awt.Dimension(140, 20));
        txtValProjekt_code.setPreferredSize(new java.awt.Dimension(140, 20));

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        panAllgemein.add(txtValProjekt_code, gridBagConstraints);

        lblProjekt_bez.setText("Bez. des Projektes");
        lblProjekt_bez.setToolTipText("Bezeichnung des Projektes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        panAllgemein.add(lblProjekt_bez, gridBagConstraints);

        txtValKurzBez.setDisabledTextColor(new java.awt.Color(26, 26, 26));
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
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        panAllgemein.add(txtValKurzBez, gridBagConstraints);

        lblKurzBez.setText("Kurzbezeichnung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        panAllgemein.add(lblKurzBez, gridBagConstraints);

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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(jcLand, gridBagConstraints);

        jScrollPane4.setMinimumSize(new java.awt.Dimension(150, 60));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(150, 60));

        txtValProjekt_bez.setColumns(20);
        txtValProjekt_bez.setRows(2);
        txtValProjekt_bez.setDisabledTextColor(new java.awt.Color(26, 26, 26));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.projekt_bez}"),
                txtValProjekt_bez,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        jScrollPane4.setViewportView(txtValProjekt_bez);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(jScrollPane4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 10, 10);
        panInfoContent.add(panAllgemein, gridBagConstraints);

        panSubProjekt.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panProjekt.add(panSubProjekt, gridBagConstraints);

        panDeMeas1.setMinimumSize(new java.awt.Dimension(700, 270));
        panDeMeas1.setPreferredSize(new java.awt.Dimension(700, 270));

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

        jScrollPane2.setMinimumSize(new java.awt.Dimension(290, 100));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(490, 100));

        jlIndikator.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.indikator}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent3.add(jScrollPane2, gridBagConstraints);

        panIndikator.setOpaque(false);
        panIndikator.setLayout(new java.awt.GridBagLayout());

        btnRemIndikator.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_16.png"))); // NOI18N
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
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_16.png"))); // NOI18N
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
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 5, 30);
        panInfoContent3.add(panIndikator, gridBagConstraints);

        projekteIndikatorenEditor1.setMinimumSize(new java.awt.Dimension(450, 200));
        projekteIndikatorenEditor1.setPreferredSize(new java.awt.Dimension(450, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 15);
        panInfoContent3.add(projekteIndikatorenEditor1, gridBagConstraints);

        panKosten1.setBackground(new java.awt.Color(51, 51, 51));
        panKosten1.setAlpha(255);
        panKosten1.setCurve(6);
        panKosten1.setMinimumSize(new java.awt.Dimension(4, 200));
        panKosten1.setPreferredSize(new java.awt.Dimension(4, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 10);
        panInfoContent3.add(panKosten1, gridBagConstraints);

        panDeMeas1.add(panInfoContent3, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 5, 0);
        panProjekt.add(panDeMeas1, gridBagConstraints);

        panKosten.setMinimumSize(new java.awt.Dimension(260, 200));
        panKosten.setPreferredSize(new java.awt.Dimension(400, 200));

        panHeadInfo5.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo5.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo5.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo5.setLayout(new java.awt.FlowLayout());

        lblHeading5.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading5.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.lblHeading5.text")); // NOI18N
        panHeadInfo5.add(lblHeading5);

        panKosten.add(panHeadInfo5, java.awt.BorderLayout.NORTH);

        panInfoContent5.setOpaque(false);
        panInfoContent5.setLayout(new java.awt.GridBagLayout());

        lblkost_gesamt.setText("Gesamtkosten");
        lblkost_gesamt.setMinimumSize(new java.awt.Dimension(115, 17));
        lblkost_gesamt.setPreferredSize(new java.awt.Dimension(115, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 10);
        panInfoContent5.add(lblkost_gesamt, gridBagConstraints);

        lblKost_forderf.setText("förderfähige Kosten");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent5.add(lblKost_forderf, gridBagConstraints);

        lblfoerdersatz.setText("Fördersatz");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent5.add(lblfoerdersatz, gridBagConstraints);

        lblFoerdersumme.setText("Fördersumme");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent5.add(lblFoerdersumme, gridBagConstraints);

        txtValKost_gesamt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        txtValKost_gesamt.setDisabledTextColor(new java.awt.Color(26, 26, 26));
        txtValKost_gesamt.setMinimumSize(new java.awt.Dimension(100, 20));
        txtValKost_gesamt.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kost_gesamt}"),
                txtValKost_gesamt,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(BigDecimal.ZERO);
        binding.setConverter(CurrencyConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent5.add(txtValKost_gesamt, gridBagConstraints);

        txtValKost_forderf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtValKost_forderf.setDisabledTextColor(new java.awt.Color(26, 26, 26));
        txtValKost_forderf.setMinimumSize(new java.awt.Dimension(100, 20));
        txtValKost_forderf.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kost_forderf}"),
                txtValKost_forderf,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(BigDecimal.ZERO);
        binding.setConverter(CurrencyConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent5.add(txtValKost_forderf, gridBagConstraints);

        txtValFoerdersatz.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtValFoerdersatz.setDisabledTextColor(new java.awt.Color(26, 26, 26));
        txtValFoerdersatz.setMinimumSize(new java.awt.Dimension(100, 20));
        txtValFoerdersatz.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.foerdersatz}"),
                txtValFoerdersatz,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(BigDecimal.ZERO);
        binding.setConverter(CurrencyConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent5.add(txtValFoerdersatz, gridBagConstraints);

        txtValfoerdersumme.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtValfoerdersumme.setDisabledTextColor(new java.awt.Color(26, 26, 26));
        txtValfoerdersumme.setMinimumSize(new java.awt.Dimension(100, 20));
        txtValfoerdersumme.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.foerdersumme}"),
                txtValfoerdersumme,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(BigDecimal.ZERO);
        binding.setConverter(CurrencyConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent5.add(txtValfoerdersumme, gridBagConstraints);

        panKosten.add(panInfoContent5, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 0);
        panProjekt.add(panKosten, gridBagConstraints);

        panZus.setMinimumSize(new java.awt.Dimension(260, 200));
        panZus.setPreferredSize(new java.awt.Dimension(400, 200));

        panHeadInfo6.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo6.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo6.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo6.setLayout(new java.awt.FlowLayout());

        lblHeading6.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading6.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.lblHeading6.text")); // NOI18N
        panHeadInfo6.add(lblHeading6);

        panZus.add(panHeadInfo6, java.awt.BorderLayout.NORTH);

        panInfoContent6.setOpaque(false);
        panInfoContent6.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent6.add(dcValM_beginn, gridBagConstraints);

        lblM_beginn.setText("Projektbeginn");
        lblM_beginn.setMinimumSize(new java.awt.Dimension(90, 17));
        lblM_beginn.setPreferredSize(new java.awt.Dimension(90, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 10);
        panInfoContent6.add(lblM_beginn, gridBagConstraints);

        lblM_ende.setText("Projektende");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent6.add(lblM_ende, gridBagConstraints);

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent6.add(dcValM_ende, gridBagConstraints);

        lblGeometrie.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblGeometrie.setText("Geometrie");
        lblGeometrie.setToolTipText("Bezeichnung des Projektes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent6.add(lblGeometrie, gridBagConstraints);

        if (!readOnly) {
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
        }
        if (!readOnly) {
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 5;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
            panInfoContent6.add(cbGeom, gridBagConstraints);
        }

        lblStatus.setText(org.openide.util.NbBundle.getMessage(ProjekteEditor.class, "ProjekteEditor.lblStatus.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent6.add(lblStatus, gridBagConstraints);

        cbStatus.setMinimumSize(new java.awt.Dimension(150, 20));
        cbStatus.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.status}"),
                cbStatus,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent6.add(cbStatus, gridBagConstraints);

        panZus.add(panInfoContent6, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 5, 0);
        panProjekt.add(panZus, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        panProjekt.add(jPanel3, gridBagConstraints);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.panProjekt.tabTitle"),
            panProjekt); // NOI18N

        panUmsetzungen.setOpaque(false);
        panUmsetzungen.setLayout(new java.awt.GridBagLayout());

        panDeMeas.setMinimumSize(new java.awt.Dimension(390, 345));
        panDeMeas.setPreferredSize(new java.awt.Dimension(390, 345));

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

        jScrollPane1.setMinimumSize(new java.awt.Dimension(290, 100));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(490, 100));

        jlUmsetzung.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.umsetzung}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent2.add(jScrollPane1, gridBagConstraints);

        panContrImpactSrc.setOpaque(false);
        panContrImpactSrc.setLayout(new java.awt.GridBagLayout());

        btnRemUmsetzung.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_16.png"))); // NOI18N
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
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_16.png"))); // NOI18N
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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 5, 30);
        panInfoContent2.add(panContrImpactSrc, gridBagConstraints);

        massnahmenUmsetzungEditor.setMinimumSize(new java.awt.Dimension(560, 360));
        massnahmenUmsetzungEditor.setPreferredSize(new java.awt.Dimension(560, 360));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 15);
        panInfoContent2.add(massnahmenUmsetzungEditor, gridBagConstraints);

        panKosten2.setBackground(new java.awt.Color(51, 51, 51));
        panKosten2.setAlpha(255);
        panKosten2.setCurve(6);
        panKosten2.setMinimumSize(new java.awt.Dimension(4, 200));
        panKosten2.setPreferredSize(new java.awt.Dimension(4, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 10);
        panInfoContent2.add(panKosten2, gridBagConstraints);

        panDeMeas.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panUmsetzungen.add(panDeMeas, gridBagConstraints);

        panMeasTypes.setMinimumSize(new java.awt.Dimension(390, 345));
        panMeasTypes.setPreferredSize(new java.awt.Dimension(390, 345));

        panHeadInfo4.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo4.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setLayout(new java.awt.FlowLayout());

        lblHeading4.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading4.setText(org.openide.util.NbBundle.getMessage(
                ProjekteEditor.class,
                "ProjekteEditor.lblHeading4.text")); // NOI18N
        panHeadInfo4.add(lblHeading4);

        panMeasTypes.add(panHeadInfo4, java.awt.BorderLayout.NORTH);

        panInfoContent4.setOpaque(false);
        panInfoContent4.setLayout(new java.awt.GridBagLayout());

        jScrollPane3.setMinimumSize(new java.awt.Dimension(290, 100));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(490, 100));

        jlMassnahme.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.aggregierte_massnahmen_typen}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                eLProperty,
                jlMassnahme);
        bindingGroup.addBinding(jListBinding);

        jlMassnahme.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    jlMassnahmeValueChanged(evt);
                }
            });
        jScrollPane3.setViewportView(jlMassnahme);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent4.add(jScrollPane3, gridBagConstraints);

        panContrImpactSrc1.setOpaque(false);
        panContrImpactSrc1.setLayout(new java.awt.GridBagLayout());

        btnRemUmsetzung1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_16.png"))); // NOI18N
        btnRemUmsetzung1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemUmsetzung1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        panContrImpactSrc1.add(btnRemUmsetzung1, gridBagConstraints);

        btnAddUmsetzung1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_16.png"))); // NOI18N
        btnAddUmsetzung1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddUmsetzung1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panContrImpactSrc1.add(btnAddUmsetzung1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 5, 30);
        panInfoContent4.add(panContrImpactSrc1, gridBagConstraints);

        aggregierterMassnahmenTypEditor1.setMinimumSize(new java.awt.Dimension(560, 360));
        aggregierterMassnahmenTypEditor1.setPreferredSize(new java.awt.Dimension(560, 360));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 15);
        panInfoContent4.add(aggregierterMassnahmenTypEditor1, gridBagConstraints);

        panKosten3.setBackground(new java.awt.Color(51, 51, 51));
        panKosten3.setAlpha(255);
        panKosten3.setCurve(6);
        panKosten3.setMinimumSize(new java.awt.Dimension(4, 200));
        panKosten3.setPreferredSize(new java.awt.Dimension(4, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 10);
        panInfoContent4.add(panKosten3, gridBagConstraints);

        panMeasTypes.add(panInfoContent4, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panUmsetzungen.add(panMeasTypes, gridBagConstraints);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(ProjekteEditor.class, "Projekte.panUmsetzungen"),
            panUmsetzungen); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        add(jTabbedPane1, gridBagConstraints);

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
                    StaticSwingTools.getParentFrame(this),
                    "Soll die Umsetzung wirklich gelöscht werden?",
                    "Umsetzung entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("umsetzung");
                    if (beanColl instanceof Collection) {
                        CidsBeanSupport.deleteStationlineIfExists(beanToDelete, "linie", beansToDelete);
                        CidsBeanSupport.deletePropertyIfExists(beanToDelete, "additional_geom", beansToDelete); // NOI18N
                        beanToDelete.delete();
                        beansToDelete.add(beanToDelete);
                        massnahmenUmsetzungEditor.setCidsBean(null);
                        ((Collection)beanColl).remove(beanToDelete);
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
            }
        }
    }                                                                                      //GEN-LAST:event_jlUmsetzungValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jlIndikatorValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_jlIndikatorValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object selObj = jlIndikator.getSelectedValue();
            if (selObj instanceof CidsBean) {
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
                            EventQueue.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        dlgMassnahmenTyp.setSize(790, 400);
                                        StaticSwingTools.showDialog(
                                            StaticSwingTools.getParentFrame(ProjekteEditor.this),
                                            dlgMassnahmenTyp,
                                            true);
                                    }
                                });
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
                    StaticSwingTools.getParentFrame(this),
                    "Soll der ausgewählte Indikator wirklich gelöscht werden?",
                    "Indikator entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("indikator");          // NOI18N
                    if (beanColl instanceof Collection) {
                        beanToDelete.delete();
                        ((Collection)beanColl).remove(beanToDelete);
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
        dlgIndikator.setSize(550, 150);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgIndikator, true);
    }                                                                                   //GEN-LAST:event_btnAddIndikatorActionPerformed

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
    private void jcLandActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jcLandActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_jcLandActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jlMassnahmeValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_jlMassnahmeValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object selObj = jlMassnahme.getSelectedValue();
            if (selObj instanceof CidsBean) {
                aggregierterMassnahmenTypEditor1.setCidsBean((CidsBean)selObj);
            }
        }
    }                                                                                      //GEN-LAST:event_jlMassnahmeValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemUmsetzung1ActionPerformed(final java.awt.event.ActionEvent evt) {          //GEN-FIRST:event_btnRemUmsetzung1ActionPerformed
        final Object selection = jlMassnahme.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll der ausgewählte Maßnahmentyp wirklich gelöscht werden?",
                    "Maßnahmentyp entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("aggregierte_massnahmen_typen"); // NOI18N
                    if (beanColl instanceof Collection) {
                        beanToDelete.delete();
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                    measureModel.fireContentsChanged();
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                             //GEN-LAST:event_btnRemUmsetzung1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddUmsetzung1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddUmsetzung1ActionPerformed
        dlgMassnahmenTypAll.setSize(790, 350);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgMassnahmenTypAll, true);
    }                                                                                    //GEN-LAST:event_btnAddUmsetzung1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMassnahmenTypAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMassnahmenTypAbortActionPerformed
        dlgMassnahmenTyp.setVisible(false);
    }                                                                                         //GEN-LAST:event_btnMassnahmenTypAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMassnahmenTypOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMassnahmenTypOkActionPerformed
        final Object[] selectedObjects = jlMassnahmenCataloge.getSelectedValues();

        if (selectedObjects != null) {
            final Thread t = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            for (final Object selection : selectedObjects) {
                                addMeasureTypeCode((CidsBean)selection);
                            }
                            measureModel.fireContentsChanged();
                        }
                    });
            t.start();
        }

        jlMassnahmenCataloge.setSelectedIndex(0);
        dlgMassnahmenTyp.setVisible(false);
    } //GEN-LAST:event_btnMassnahmenTypOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbMassnahmenTypCatalogeItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbMassnahmenTypCatalogeItemStateChanged
        if (evt.getItem() != null) {
            measureModel.setMeasureGroup((CidsBean)evt.getItem());
        }
    }                                                                                          //GEN-LAST:event_cbMassnahmenTypCatalogeItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMassnahmenTypAllAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMassnahmenTypAllAbortActionPerformed
        dlgMassnahmenTypAll.setVisible(false);
    }                                                                                            //GEN-LAST:event_btnMassnahmenTypAllAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMassnahmenTypAllOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMassnahmenTypAllOkActionPerformed
        final Object[] selectedObjects = jlMassnahmenAllCataloge.getSelectedValues();

        if (selectedObjects != null) {
            final Thread t = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            for (final Object selection : selectedObjects) {
                                addMeasureTypeCode((CidsBean)selection);
                            }
                            measureModel.fireContentsChanged();
                        }
                    });
            t.start();
        }

        jlMassnahmenAllCataloge.setSelectedIndex(0);
        dlgMassnahmenTypAll.setVisible(false);
    } //GEN-LAST:event_btnMassnahmenTypAllOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  measureTypeCode  DOCUMENT ME!
     */
    private void addMeasureTypeCode(final CidsBean measureTypeCode) {
        if ((measureTypeCode instanceof CidsBean) && !isMeasureTypeCodeAlreadyContained((CidsBean)measureTypeCode)) {
            final CidsBean selectedBean = (CidsBean)measureTypeCode;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(
                    cidsBean,
                    "aggregierte_massnahmen_typen"); // NOI18N

            if (colToAdd != null) {
                try {
                    final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName(
                            "aggregierter_massnahmen_typ");         // NOI18N
                    newBean.setProperty("massnahme", selectedBean); // NOI18N
                    newBean.setProperty("bezeichnung", selectedBean.getProperty("name"));
                    colToAdd.add(newBean);
                    jlMassnahme.setSelectedValue(newBean, true);
                } catch (final Exception ex) {
                    LOG.error(ex, ex);
                }
            } else {
                LOG.error("The property aggregierte_massnahmen_typen was not found.");
            }
        }
    }

    /**
     * checks, if the given bean is already contained in the measure type code list.
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isMeasureTypeCodeAlreadyContained(final CidsBean bean) {
        final List<CidsBean> beans = CidsBeanSupport.getBeanCollectionFromProperty(
                cidsBean,
                "aggregierte_massnahmen_typen");
        if (beans != null) {
            // todo: Suche ist langsam. contained-Methode liefert aber immer false
            for (final CidsBean tmp : beans) {
                final Object measureCodeId = tmp.getProperty("massnahme.id");
                if ((measureCodeId != null) && measureCodeId.equals(bean.getProperty("id"))) {
                    return true;
                }
            }

            return false;
        } else {
            LOG.error("null");
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
                "WRRL_DB_MV",
                "Administratoren",
                "admin",
                "x",
                "projekte",
                420,
                1280,
                1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        massnahmenUmsetzungEditor.dispose();
        if (cbGeom != null) {
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).dispose();
        }
        projekteIndikatorenEditor1.dispose();
        bindingGroup.unbind();
        aggregierterMassnahmenTypEditor1.dispose();
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
            cidsBean.getMetaObject().setAllClasses();
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

        final CidsBean status = (CidsBean)cidsBean.getProperty("status");
        if ((status != null) && status.getProperty("name").equals("abgeschlossen")) {
            final List<CidsBean> implementations = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "umsetzung");

            if (implementations != null) {
                for (final CidsBean tmp : implementations) {
                    final Object idObject = tmp.getProperty("massnahme");
                    if ((idObject != null) && !idObject.equals("")) {
                        final String query = "select " + MASSNAHMEN_MC.getID() + ", " + MASSNAHMEN_MC.getPrimaryKey()
                                    + " from "
                                    + MASSNAHMEN_MC.getTableName() + " where id = " + idObject.toString(); // NOI18N
                        final MetaObject[] metaObjects = MetaObjectCache.getInstance().getMetaObjectByQuery(query);

                        for (final MetaObject massn : metaObjects) {
                            final CidsBean massnBean = massn.getBean();
                            try {
                                final Object fin = massnBean.getProperty(MASSN_FIN_PROPERTY);
                                if ((fin == null) || ((fin instanceof Boolean) && !((Boolean)fin).booleanValue())) {
                                    if (LOG.isDebugEnabled()) {
                                        LOG.debug("set action massn_fin to true");
                                    }
                                    massnBean.setProperty(MASSN_FIN_PROPERTY, true);
                                    massnBean.persist();
                                }
                            } catch (Exception e) {
                                LOG.error("Error while setting massn_fin to true.", e);
                            }
                        }
                    }
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
                        WRRLUtil.DOMAIN_NAME,
                        "massnahmen_umsetzung").getEmptyInstance().getBean(); // NOI18N

            final List<CidsBean> impls = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "umsetzung"); // NOI18N
            if (impls != null) {
                impls.add(newBean);
                final ArrayList<CidsBean> beanList = new ArrayList<CidsBean>();
                beanList.add(act);

                jlUmsetzung.setSelectedValue(newBean, true);
                massnahmenUmsetzungEditor.beansDropped(beanList);
                final List<CidsBean> measureTypes = CidsBeanSupport.getBeanCollectionFromProperty(act, "de_meas_cd");
                if (measureTypes != null) {
                    for (final CidsBean tmp : measureTypes) {
                        addMeasureTypeCode(tmp);
                        addWkToMeasureTypeCode(tmp, newBean);
                    }
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("The property umsetzung returns null"); // NOI18N
                }
            }
        } catch (final Exception e) {
            LOG.error("Error during the creation of a new bean of type massnahmen", e); // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  measureType  DOCUMENT ME!
     * @param  impl         DOCUMENT ME!
     */
    private void addWkToMeasureTypeCode(final CidsBean measureType, final CidsBean impl) {
        CidsBean projMeasureType = null;
        final List<CidsBean> measureList = CidsBeanSupport.getBeanCollectionFromProperty(
                cidsBean,
                "aggregierte_massnahmen_typen");
        final Object id = measureType.getProperty("id");

        for (final CidsBean tmp : measureList) {
            if (tmp.getProperty("massnahme.id").equals(id)) {
                projMeasureType = tmp;
                break;
            }
        }

        if (projMeasureType != null) {
            final CidsBean wb = cache.getWB(impl);
            final CidsBean w = WirkungPan.addWB(projMeasureType, wb);
            final CidsBean selectedMeasureType = aggregierterMassnahmenTypEditor1.getCidsBean();

            if (selectedMeasureType.getProperty("id").equals(projMeasureType.getProperty("id"))) {
                aggregierterMassnahmenTypEditor1.getWirkungPan().addWirkungToListModel(w);
            }
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

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class MeasureTypeModel implements ListModel {

        //~ Instance fields ----------------------------------------------------

        private CidsBean measureGroup;
        private List<CidsBean> types;
        private List<ListDataListener> listener = new ArrayList<ListDataListener>();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new MeasureTypeModel object.
         */
        public MeasureTypeModel() {
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param  bean  DOCUMENT ME!
         */
        public void setMeasureGroup(final CidsBean bean) {
            measureGroup = bean;
            fireContentsChanged();
        }

        @Override
        public int getSize() {
            if ((measureGroup != null) && (types != null)) {
                return types.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getElementAt(final int index) {
            if ((measureGroup != null) && (types != null)) {
                return types.get(index);
            } else {
                return null;
            }
        }

        /**
         * DOCUMENT ME!
         */
        public void fireContentsChanged() {
            final ListDataEvent e = new ListDataEvent(
                    this,
                    ListDataEvent.CONTENTS_CHANGED,
                    0,
                    ((types != null) ? types.size() : 0));
            types = new ArrayList();

            if (measureGroup != null) {
                final List<CidsBean> allTypes = CidsBeanSupport.getBeanCollectionFromProperty(measureGroup, "types");

                for (final CidsBean b : allTypes) {
                    if (!isMeasureTypeCodeAlreadyContained(b)) {
                        types.add(b);
                    }
                }
            }
            Collections.sort(types, new StringIntValueComparator());

            // fire event
            for (final ListDataListener l : listener) {
                l.contentsChanged(e);
            }
        }

        @Override
        public void addListDataListener(final ListDataListener l) {
            listener.add(l);
        }

        @Override
        public void removeListDataListener(final ListDataListener l) {
            listener.remove(l);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class MeasureTypeModelAll implements ListModel {

        //~ Instance fields ----------------------------------------------------

        private List<CidsBean> types;
        private List<ListDataListener> listener = new ArrayList<ListDataListener>();
        private boolean initialized = false;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new MeasureTypeModel object.
         */
        public MeasureTypeModelAll() {
            final Thread t = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            final String query = "select " + MASSNAHMEN_TYP_MC.getID() + ","
                                        + MASSNAHMEN_TYP_MC.getPrimaryKey() + " from "
                                        + MASSNAHMEN_TYP_MC.getTableName(); // NOI18N
                            final MetaObject[] metaObjects = MetaObjectCache.getInstance().getMetaObjectByQuery(query);

                            types = new ArrayList<CidsBean>();

                            for (final MetaObject tmp : metaObjects) {
                                types.add(tmp.getBean());
                            }

                            Collections.sort(types, new StringIntValueComparator());
                            initialized = true;

                            fireContentsChanged();
                        }
                    });

            t.start();
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int getSize() {
            if (initialized) {
                return types.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getElementAt(final int index) {
            if (initialized) {
                return types.get(index);
            } else {
                return null;
            }
        }

        /**
         * DOCUMENT ME!
         */
        public void fireContentsChanged() {
            final ListDataEvent e = new ListDataEvent(
                    this,
                    ListDataEvent.CONTENTS_CHANGED,
                    0,
                    ((initialized) ? types.size() : 0));

            // fire event
            for (final ListDataListener l : listener) {
                l.contentsChanged(e);
            }
        }

        @Override
        public void addListDataListener(final ListDataListener l) {
            listener.add(l);
        }

        @Override
        public void removeListDataListener(final ListDataListener l) {
            listener.remove(l);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class IndikatorComparator implements Comparator<CidsBean> {

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final CidsBean o1, final CidsBean o2) {
            if ((o1 == null) && (o2 == null)) {
                LOG.error("null1");
                return 0;
            } else if (o1 == null) {
                LOG.error("null2");
                return -1;
            } else if (o2 == null) {
                LOG.error("null3");
                return 1;
            }

            final Integer indikator1 = (Integer)o1.getProperty("indikator_nr");
            final Integer indikator2 = (Integer)o2.getProperty("indikator_nr");

            if ((indikator1 == null) && (indikator2 == null)) {
                return 0;
            } else if (indikator1 == null) {
                return -1;
            } else if (indikator2 == null) {
                return 1;
            } else {
                return indikator1.compareTo(indikator2);
            }
        }
    }
}
