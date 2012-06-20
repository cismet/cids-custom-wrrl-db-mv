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
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.search.CidsServerSearch;

import com.vividsolutions.jts.geom.Geometry;

import java.awt.Component;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.server.search.MaxWBNumberSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.StaluSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.*;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;

import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * Massnahmen koennen sich auf Fliessgewaesser und Seegewaesser beziehen. Massnahmen, die sich auf Seegewaesser beziehen
 * haben keine Stationierung und als Geometrie die Geometrie des Sees. Ausser wenn sie schon zuvor eine Station hatten,
 * dann haben sie diese auch weiterhin und sie bekommen nicht die Geometrie des Sees.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnahmenEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MassnahmenEditor.class);
    private static final MetaClass DE_MEASURE_TYPE_CODE_MC;
    private static final MetaClass DE_MEASURE_TYPE_CODE_AFTER2015_MC;
    private static final String[] WB_PROPERTIES = { "wk_fg", "wk_sg", "wk_kg", "wk_gw" }; // NOI18N
    private static final MappingComponent MAPPING_COMPONENT = CismapBroker.getInstance().getMappingComponent();

    static {
        DE_MEASURE_TYPE_CODE_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "wfd.de_measure_type_code");           // NOI18N
        DE_MEASURE_TYPE_CODE_AFTER2015_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "wfd.de_measure_type_code_after2015"); // NOI18N
    }

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private ArrayList<CidsBean> beansToDelete = new ArrayList<CidsBean>();
    private RouteWBDropBehavior dropBehaviorListener;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blbSpace;
    private javax.swing.JButton btnAddDe_meas;
    private javax.swing.JButton btnAddMeas15;
    private javax.swing.JButton btnAddMeas21;
    private javax.swing.JButton btnMeas15Abort;
    private javax.swing.JButton btnMeas15Ok;
    private javax.swing.JButton btnMeas21Abort;
    private javax.swing.JButton btnMeas21Ok;
    private javax.swing.JButton btnMeasAbort;
    private javax.swing.JButton btnMeasOk;
    private javax.swing.JButton btnRemDeMeas;
    private javax.swing.JButton btnRemMeas15;
    private javax.swing.JButton btnRemMeas21;
    private javax.swing.JCheckBox cbFin;
    private javax.swing.JComboBox cbGeom;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassn_typ;
    private javax.swing.JComboBox cbMeas15Cataloge;
    private javax.swing.JComboBox cbMeas21Cataloge;
    private javax.swing.JComboBox cbMeasCataloge;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPressur_cd;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPrioritaet;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbReal;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbRevital;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbStalu;
    private javax.swing.JCheckBox cbStarted;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbSuppl_cd;
    private javax.swing.JDialog dlgMeas;
    private javax.swing.JDialog dlgMeas15;
    private javax.swing.JDialog dlgMeas21;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblBeschrDerMa;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGeom;
    private javax.swing.JLabel lblGwk;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblHeading3;
    private javax.swing.JLabel lblHeading4;
    private javax.swing.JLabel lblKosten;
    private javax.swing.JLabel lblMassn_id;
    private javax.swing.JLabel lblMassn_typ;
    private javax.swing.JLabel lblMeas15Cataloge;
    private javax.swing.JLabel lblMeas21Cataloge;
    private javax.swing.JLabel lblMeasCataloge;
    private javax.swing.JLabel lblMs_cd_bw;
    private javax.swing.JLabel lblPressur_cd;
    private javax.swing.JLabel lblPrioritaet;
    private javax.swing.JLabel lblRevital;
    private javax.swing.JLabel lblStalu;
    private javax.swing.JLabel lblSubs_typ;
    private javax.swing.JLabel lblSuppl_cd;
    private javax.swing.JLabel lblValGwk;
    private javax.swing.JLabel lblValLfdnr;
    private javax.swing.JLabel lblValMs_cd_bw;
    private javax.swing.JLabel lblValWk_k;
    private javax.swing.JLabel lblValWk_name;
    private javax.swing.JLabel lblWk_k;
    private javax.swing.JLabel lblWk_name;
    private javax.swing.JLabel lblZiele;
    private javax.swing.JLabel lbllfdnr;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JList lstMeas15;
    private javax.swing.JList lstMeas21;
    private javax.swing.JList lstdeMeas;
    private de.cismet.tools.gui.RoundedPanel panDeMeas;
    private javax.swing.JPanel panDe_meas;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.RoundedPanel panGeo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo3;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo4;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent2;
    private javax.swing.JPanel panInfoContent3;
    private javax.swing.JPanel panInfoContent4;
    private de.cismet.tools.gui.RoundedPanel panMeas15;
    private de.cismet.tools.gui.RoundedPanel panMeas21;
    private javax.swing.JPanel panMenButtonsMeas;
    private javax.swing.JPanel panMenButtonsMeas15;
    private javax.swing.JPanel panMenButtonsMeas21;
    private javax.swing.JPanel panmeas15;
    private javax.swing.JPanel panmeas21;
    private javax.swing.JScrollPane scpMeas15;
    private javax.swing.JScrollPane scpMeas21;
    private javax.swing.JScrollPane scpdeMeas;
    private javax.swing.JTextField txtKosten;
    private javax.swing.JTextField txtMassn_id;
    private javax.swing.JTextField txtZiele;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public MassnahmenEditor() {
        initComponents();
        RendererTools.makeReadOnly(cbFin);
        RendererTools.makeReadOnly(cbStarted);
        deActivateGUI(false);
        dropBehaviorListener = new RouteWBDropBehavior(this);
        linearReferencedLineEditor.setLineField("linie");                 // NOI18N
        linearReferencedLineEditor.setDropBehavior(dropBehaviorListener); // NOI18N
        linearReferencedLineEditor.setOtherLinesEnabled(false);
        try {
            new CidsBeanDropTarget(this);
        } catch (final Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
            }
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            deActivateGUI(true);
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
            dropBehaviorListener.setWkFg((CidsBean)cidsBean.getProperty("wk_fg"));
            linearReferencedLineEditor.setCidsBean(cidsBean);
            zoomToFeatures();
        } else {
            deActivateGUI(false);
            dropBehaviorListener.setWkFg(null);
        }
        bindReadOnlyFields();
        showOrHideGeometryEditors();
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     */
    private void bindReadOnlyFields() {
        if (cidsBean == null) {
            lblValWk_k.setText("");
            lblValWk_name.setText("");
            lblValGwk.setText("");
            lblValMs_cd_bw.setText("");
            lblFoot.setText("");
        } else {
            final String wk_k = getWk_k();
            lblValWk_k.setText(wk_k);
            lblValWk_name.setText(getWk_name());
            lblValGwk.setText(getGwk());

            if (!wk_k.equals(CidsBeanSupport.FIELD_NOT_SET)) {
                lblValMs_cd_bw.setText("DE_MV_" + wk_k); // NOI18N
            } else {
                lblValMs_cd_bw.setText("");
            }

            // refresh footer
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
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getWk_k() {
        if (cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_fg.wk_k")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_sg.wk_k")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[2]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_kg.name")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[3]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_gw.name")); // NOI18N
        } else {
            return CidsBeanSupport.FIELD_NOT_SET;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getWk_name() {
        if (cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_fg.wk_n"));    // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_sg.ls_name")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[2]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_kg.name"));    // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[3]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_gw.name"));    // NOI18N
        } else {
            return CidsBeanSupport.FIELD_NOT_SET;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getGwk() {
        if (cidsBean.getProperty("linie") != null) {                           // NOI18N
            return String.valueOf(cidsBean.getProperty(
                        "linie."
                                + LinearReferencingConstants.PROP_STATIONLINIE_FROM
                                + "."
                                + LinearReferencingConstants.PROP_STATION_ROUTE
                                + "."
                                + LinearReferencingConstants.PROP_ROUTE_GWK)); // NOI18N
        } else {
            return "";
        }
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
        dlgMeas = new javax.swing.JDialog();
        lblMeasCataloge = new javax.swing.JLabel();
        cbMeasCataloge = new ScrollableComboBox(DE_MEASURE_TYPE_CODE_MC, true, true, new CustomElementComparator());
        panMenButtonsMeas = new javax.swing.JPanel();
        btnMeasAbort = new javax.swing.JButton();
        btnMeasOk = new javax.swing.JButton();
        dlgMeas15 = new javax.swing.JDialog();
        lblMeas15Cataloge = new javax.swing.JLabel();
        cbMeas15Cataloge = new ScrollableComboBox(
                DE_MEASURE_TYPE_CODE_AFTER2015_MC,
                true,
                true,
                new CustomElementComparator(1));
        panMenButtonsMeas15 = new javax.swing.JPanel();
        btnMeas15Abort = new javax.swing.JButton();
        btnMeas15Ok = new javax.swing.JButton();
        dlgMeas21 = new javax.swing.JDialog();
        lblMeas21Cataloge = new javax.swing.JLabel();
        cbMeas21Cataloge = new ScrollableComboBox(
                DE_MEASURE_TYPE_CODE_AFTER2015_MC,
                true,
                true,
                new CustomElementComparator(1));
        panMenButtonsMeas21 = new javax.swing.JPanel();
        btnMeas21Abort = new javax.swing.JButton();
        btnMeas21Ok = new javax.swing.JButton();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        blbSpace = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblZiele = new javax.swing.JLabel();
        lblMassn_typ = new javax.swing.JLabel();
        lblRevital = new javax.swing.JLabel();
        lblPrioritaet = new javax.swing.JLabel();
        lblKosten = new javax.swing.JLabel();
        lblSubs_typ = new javax.swing.JLabel();
        txtKosten = new javax.swing.JTextField();
        cbMassn_typ = new ScrollableComboBox();
        cbRevital = new ScrollableComboBox();
        cbPrioritaet = new ScrollableComboBox();
        cbFin = new javax.swing.JCheckBox();
        txtZiele = new javax.swing.JTextField();
        lbllfdnr = new javax.swing.JLabel();
        lblValLfdnr = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblBeschrDerMa = new javax.swing.JLabel();
        lblWk_k = new javax.swing.JLabel();
        lblValWk_k = new javax.swing.JLabel();
        lblWk_name = new javax.swing.JLabel();
        lblValWk_name = new javax.swing.JLabel();
        lblGwk = new javax.swing.JLabel();
        lblValGwk = new javax.swing.JLabel();
        lblMassn_id = new javax.swing.JLabel();
        lblStalu = new javax.swing.JLabel();
        txtMassn_id = new javax.swing.JTextField();
        cbStalu = new ScrollableComboBox();
        cbStarted = new javax.swing.JCheckBox();
        cbReal = new ScrollableComboBox();
        jPanel3 = new javax.swing.JPanel();
        panDeMeas = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        panDe_meas = new javax.swing.JPanel();
        btnAddDe_meas = new javax.swing.JButton();
        btnRemDeMeas = new javax.swing.JButton();
        scpdeMeas = new javax.swing.JScrollPane();
        lstdeMeas = new javax.swing.JList();
        panMeas15 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo3 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading3 = new javax.swing.JLabel();
        panInfoContent3 = new javax.swing.JPanel();
        panmeas15 = new javax.swing.JPanel();
        btnAddMeas15 = new javax.swing.JButton();
        btnRemMeas15 = new javax.swing.JButton();
        scpMeas15 = new javax.swing.JScrollPane();
        lstMeas15 = new javax.swing.JList();
        panMeas21 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo4 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading4 = new javax.swing.JLabel();
        panInfoContent4 = new javax.swing.JPanel();
        panmeas21 = new javax.swing.JPanel();
        btnAddMeas21 = new javax.swing.JButton();
        btnRemMeas21 = new javax.swing.JButton();
        scpMeas21 = new javax.swing.JScrollPane();
        lstMeas21 = new javax.swing.JList();
        lblSuppl_cd = new javax.swing.JLabel();
        cbSuppl_cd = new ScrollableComboBox();
        lblPressur_cd = new javax.swing.JLabel();
        cbPressur_cd = new ScrollableComboBox(new CustomElementComparator(1));
        lblMs_cd_bw = new javax.swing.JLabel();
        lblValMs_cd_bw = new javax.swing.JLabel();
        panGeo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        linearReferencedLineEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor();
        jPanel1 = new javax.swing.JPanel();
        cbGeom = new DefaultCismapGeometryComboBoxEditor();
        lblGeom = new javax.swing.JLabel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        dlgMeas.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblMeasCataloge.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.lblMeasCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas.getContentPane().add(lblMeasCataloge, gridBagConstraints);

        cbMeasCataloge.setMinimumSize(new java.awt.Dimension(700, 18));
        cbMeasCataloge.setPreferredSize(new java.awt.Dimension(700, 18));
        cbMeasCataloge.setRenderer(new MeasureTypeCodeRenderer());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas.getContentPane().add(cbMeasCataloge, gridBagConstraints);

        panMenButtonsMeas.setLayout(new java.awt.GridBagLayout());

        btnMeasAbort.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeasAbort.text")); // NOI18N
        btnMeasAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMeasAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas.add(btnMeasAbort, gridBagConstraints);

        btnMeasOk.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeasOk.text")); // NOI18N
        btnMeasOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMeasOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMeasOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMeasOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMeasOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas.add(btnMeasOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas.getContentPane().add(panMenButtonsMeas, gridBagConstraints);

        dlgMeas15.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblMeas15Cataloge.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.lblMeas15Cataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas15.getContentPane().add(lblMeas15Cataloge, gridBagConstraints);

        cbMeas15Cataloge.setMinimumSize(new java.awt.Dimension(700, 18));
        cbMeas15Cataloge.setPreferredSize(new java.awt.Dimension(700, 18));
        cbMeas15Cataloge.setRenderer(new WfdTypeCodeRenderer());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas15.getContentPane().add(cbMeas15Cataloge, gridBagConstraints);

        panMenButtonsMeas15.setLayout(new java.awt.GridBagLayout());

        btnMeas15Abort.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeas15Abort.text")); // NOI18N
        btnMeas15Abort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMeas15AbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas15.add(btnMeas15Abort, gridBagConstraints);

        btnMeas15Ok.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeas15Ok.text")); // NOI18N
        btnMeas15Ok.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMeas15Ok.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMeas15Ok.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMeas15Ok.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMeas15OkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas15.add(btnMeas15Ok, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas15.getContentPane().add(panMenButtonsMeas15, gridBagConstraints);

        dlgMeas21.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblMeas21Cataloge.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.lblMeas21Cataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas21.getContentPane().add(lblMeas21Cataloge, gridBagConstraints);

        cbMeas21Cataloge.setMinimumSize(new java.awt.Dimension(700, 18));
        cbMeas21Cataloge.setPreferredSize(new java.awt.Dimension(700, 18));
        cbMeas21Cataloge.setRenderer(new WfdTypeCodeRenderer());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas21.getContentPane().add(cbMeas21Cataloge, gridBagConstraints);

        panMenButtonsMeas21.setLayout(new java.awt.GridBagLayout());

        btnMeas21Abort.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeas21Abort.text")); // NOI18N
        btnMeas21Abort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMeas21AbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas21.add(btnMeas21Abort, gridBagConstraints);

        btnMeas21Ok.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeas21Ok.text")); // NOI18N
        btnMeas21Ok.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMeas21Ok.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMeas21Ok.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMeas21Ok.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMeas21OkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas21.add(btnMeas21Ok, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas21.getContentPane().add(panMenButtonsMeas21, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(840, 770));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1140, 770));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMaximumSize(new java.awt.Dimension(1150, 790));
        panInfo.setMinimumSize(new java.awt.Dimension(880, 770));
        panInfo.setPreferredSize(new java.awt.Dimension(1080, 770));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Maßnahmen BVP");
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(blbSpace, gridBagConstraints);

        jPanel2.setMinimumSize(new java.awt.Dimension(430, 540));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(580, 540));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblZiele.setText("Entwicklungsziele (BVP)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblZiele, gridBagConstraints);

        lblMassn_typ.setText("Umfang (BVP)");
        lblMassn_typ.setToolTipText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.lblMassn_typ.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblMassn_typ, gridBagConstraints);

        lblRevital.setText("Art der Maßnahme (BVP)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblRevital, gridBagConstraints);

        lblPrioritaet.setText("Priorität (BVP)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblPrioritaet, gridBagConstraints);

        lblKosten.setText("geschätzte Kosten");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblKosten, gridBagConstraints);

        lblSubs_typ.setText("Realisierung bis");
        lblSubs_typ.setPreferredSize(new java.awt.Dimension(165, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblSubs_typ, gridBagConstraints);

        txtKosten.setMinimumSize(new java.awt.Dimension(200, 25));
        txtKosten.setPreferredSize(new java.awt.Dimension(200, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kosten}"),
                txtKosten,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(txtKosten, gridBagConstraints);

        cbMassn_typ.setMinimumSize(new java.awt.Dimension(200, 25));
        cbMassn_typ.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_typ}"),
                cbMassn_typ,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbMassn_typ, gridBagConstraints);

        cbRevital.setMinimumSize(new java.awt.Dimension(200, 25));
        cbRevital.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.revital}"),
                cbRevital,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbRevital, gridBagConstraints);

        cbPrioritaet.setMinimumSize(new java.awt.Dimension(200, 25));
        cbPrioritaet.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.prioritaet}"),
                cbPrioritaet,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbPrioritaet, gridBagConstraints);

        cbFin.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.cbFin.text")); // NOI18N
        cbFin.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_fin}"),
                cbFin,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        cbFin.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbFinActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbFin, gridBagConstraints);

        txtZiele.setMinimumSize(new java.awt.Dimension(200, 25));
        txtZiele.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ziele}"),
                txtZiele,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(txtZiele, gridBagConstraints);

        lbllfdnr.setText("laufende Nummer im WK");
        lbllfdnr.setToolTipText("laufende Nummer im Wasserkörper");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        jPanel2.add(lbllfdnr, gridBagConstraints);

        lblValLfdnr.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValLfdnr.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_wk_lfdnr}"),
                lblValLfdnr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValLfdnr, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(380, 100));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(380, 100));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massnahme}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        lblBeschrDerMa.setText("Beschreibung der Maßnahme");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanel2.add(lblBeschrDerMa, gridBagConstraints);

        lblWk_k.setText("Wasserkörper-Kürzel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblWk_k, gridBagConstraints);

        lblValWk_k.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValWk_k.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_fg.wk_k}"),
                lblValWk_k,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValWk_k, gridBagConstraints);

        lblWk_name.setText("Wasserkörper-Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblWk_name, gridBagConstraints);

        lblValWk_name.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValWk_name.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValWk_name, gridBagConstraints);

        lblGwk.setText("Gewässerroute");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblGwk, gridBagConstraints);

        lblValGwk.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValGwk.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValGwk, gridBagConstraints);

        lblMassn_id.setText("Maßnahmen-Nummer");
        lblMassn_id.setToolTipText("laufende Nummer im Wasserkörper");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblMassn_id, gridBagConstraints);

        lblStalu.setText("Zuständiges StALU");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(lblStalu, gridBagConstraints);

        txtMassn_id.setMinimumSize(new java.awt.Dimension(200, 25));
        txtMassn_id.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_id}"),
                txtMassn_id,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(txtMassn_id, gridBagConstraints);

        cbStalu.setMinimumSize(new java.awt.Dimension(200, 25));
        cbStalu.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stalu}"),
                cbStalu,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbStalu, gridBagConstraints);

        cbStarted.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.cbStarted.text")); // NOI18N
        cbStarted.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_started}"),
                cbStarted,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        cbStarted.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbStartedActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbStarted, gridBagConstraints);

        cbReal.setMinimumSize(new java.awt.Dimension(200, 25));
        cbReal.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.realisierung}"),
                cbReal,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbReal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 10, 20);
        panInfoContent.add(jPanel2, gridBagConstraints);

        jPanel3.setMinimumSize(new java.awt.Dimension(430, 540));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(580, 540));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        panDeMeas.setMinimumSize(new java.awt.Dimension(480, 135));
        panDeMeas.setPreferredSize(new java.awt.Dimension(480, 135));

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText("Maßnahmen bis 2015");
        panHeadInfo2.add(lblHeading2);

        panDeMeas.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        panDe_meas.setOpaque(false);
        panDe_meas.setLayout(new java.awt.GridBagLayout());

        btnAddDe_meas.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddDe_meas.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddDe_measActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panDe_meas.add(btnAddDe_meas, gridBagConstraints);

        btnRemDeMeas.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemDeMeas.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemDeMeasActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panDe_meas.add(btnRemDeMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panInfoContent2.add(panDe_meas, gridBagConstraints);

        scpdeMeas.setMinimumSize(new java.awt.Dimension(400, 90));
        scpdeMeas.setPreferredSize(new java.awt.Dimension(400, 90));

        lstdeMeas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.de_meas_cd}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstdeMeas);
        bindingGroup.addBinding(jListBinding);

        scpdeMeas.setViewportView(lstdeMeas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent2.add(scpdeMeas, gridBagConstraints);

        panDeMeas.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        jPanel3.add(panDeMeas, gridBagConstraints);

        panMeas15.setMinimumSize(new java.awt.Dimension(480, 135));
        panMeas15.setPreferredSize(new java.awt.Dimension(480, 135));

        panHeadInfo3.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo3.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setLayout(new java.awt.FlowLayout());

        lblHeading3.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading3.setText("Maßnahmen nach 2015");
        panHeadInfo3.add(lblHeading3);

        panMeas15.add(panHeadInfo3, java.awt.BorderLayout.NORTH);

        panInfoContent3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent3.setOpaque(false);
        panInfoContent3.setLayout(new java.awt.GridBagLayout());

        panmeas15.setOpaque(false);
        panmeas15.setLayout(new java.awt.GridBagLayout());

        btnAddMeas15.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddMeas15.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddMeas15ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panmeas15.add(btnAddMeas15, gridBagConstraints);

        btnRemMeas15.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemMeas15.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemMeas15ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panmeas15.add(btnRemMeas15, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panInfoContent3.add(panmeas15, gridBagConstraints);

        scpMeas15.setMinimumSize(new java.awt.Dimension(400, 90));
        scpMeas15.setPreferredSize(new java.awt.Dimension(400, 90));

        lstMeas15.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.meas_2015}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                eLProperty,
                lstMeas15);
        bindingGroup.addBinding(jListBinding);

        scpMeas15.setViewportView(lstMeas15);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent3.add(scpMeas15, gridBagConstraints);

        panMeas15.add(panInfoContent3, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        jPanel3.add(panMeas15, gridBagConstraints);

        panMeas21.setMinimumSize(new java.awt.Dimension(480, 135));
        panMeas21.setPreferredSize(new java.awt.Dimension(480, 135));

        panHeadInfo4.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo4.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setLayout(new java.awt.FlowLayout());

        lblHeading4.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading4.setText("Maßnahmen nach 2021");
        panHeadInfo4.add(lblHeading4);

        panMeas21.add(panHeadInfo4, java.awt.BorderLayout.NORTH);

        panInfoContent4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent4.setOpaque(false);
        panInfoContent4.setLayout(new java.awt.GridBagLayout());

        panmeas21.setOpaque(false);
        panmeas21.setLayout(new java.awt.GridBagLayout());

        btnAddMeas21.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddMeas21.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddMeas21ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panmeas21.add(btnAddMeas21, gridBagConstraints);

        btnRemMeas21.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemMeas21.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemMeas21ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panmeas21.add(btnRemMeas21, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panInfoContent4.add(panmeas21, gridBagConstraints);

        scpMeas21.setMinimumSize(new java.awt.Dimension(400, 90));
        scpMeas21.setPreferredSize(new java.awt.Dimension(400, 90));

        lstMeas21.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.meas_2021}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                eLProperty,
                lstMeas21);
        bindingGroup.addBinding(jListBinding);

        scpMeas21.setViewportView(lstMeas21);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent4.add(scpMeas21, gridBagConstraints);

        panMeas21.add(panInfoContent4, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(panMeas21, gridBagConstraints);

        lblSuppl_cd.setText("EU-Maßnahmentyp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel3.add(lblSuppl_cd, gridBagConstraints);

        cbSuppl_cd.setMinimumSize(new java.awt.Dimension(300, 25));
        cbSuppl_cd.setPreferredSize(new java.awt.Dimension(300, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suppl_cd}"),
                cbSuppl_cd,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 0);
        jPanel3.add(cbSuppl_cd, gridBagConstraints);

        lblPressur_cd.setText("EU-Belastungstyp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel3.add(lblPressur_cd, gridBagConstraints);

        cbPressur_cd.setMinimumSize(new java.awt.Dimension(300, 25));
        cbPressur_cd.setPreferredSize(new java.awt.Dimension(300, 25));
        cbPressur_cd.setRenderer(new WfdTypeCodeRenderer());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.pressur_cd}"),
                cbPressur_cd,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 0);
        jPanel3.add(cbPressur_cd, gridBagConstraints);

        lblMs_cd_bw.setText("EU-Wasserkörpercode");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel3.add(lblMs_cd_bw, gridBagConstraints);

        lblValMs_cd_bw.setMinimumSize(new java.awt.Dimension(300, 25));
        lblValMs_cd_bw.setPreferredSize(new java.awt.Dimension(300, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 0);
        jPanel3.add(lblValMs_cd_bw, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 10, 20);
        panInfoContent.add(jPanel3, gridBagConstraints);

        panGeo.setMinimumSize(new java.awt.Dimension(640, 100));
        panGeo.setPreferredSize(new java.awt.Dimension(640, 100));

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText("Geometrie");
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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 10, 20);
        panInfoContent.add(panGeo, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        cbGeom.setMinimumSize(new java.awt.Dimension(300, 20));
        cbGeom.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.additional_geom}"),
                cbGeom,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setConverter(((DefaultCismapGeometryComboBoxEditor)cbGeom).getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel1.add(cbGeom, gridBagConstraints);

        lblGeom.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "WkSgPanOne.lblGeom.text")); // NOI18N
        lblGeom.setMaximumSize(new java.awt.Dimension(350, 20));
        lblGeom.setMinimumSize(new java.awt.Dimension(250, 20));
        lblGeom.setPreferredSize(new java.awt.Dimension(180, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel1.add(lblGeom, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 20);
        panInfoContent.add(jPanel1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panInfo, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbFinActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbFinActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbFinActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddDe_measActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddDe_measActionPerformed
        UIUtil.findOptimalPositionOnScreen(dlgMeas);
        dlgMeas.setSize(750, 150);
        dlgMeas.setVisible(true);
    }                                                                                 //GEN-LAST:event_btnAddDe_measActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemDeMeasActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemDeMeasActionPerformed
        final Object selection = lstdeMeas.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Soll die Massnahmenart '"
                            + selection.toString()
                            + "' wirklich gelöscht werden?",
                    "Massnahmenart entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("de_meas_cd");      // NOI18N
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                //GEN-LAST:event_btnRemDeMeasActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddMeas15ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddMeas15ActionPerformed
        UIUtil.findOptimalPositionOnScreen(dlgMeas15);
        dlgMeas15.setSize(750, 150);
        dlgMeas15.setVisible(true);
    }                                                                                //GEN-LAST:event_btnAddMeas15ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemMeas15ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemMeas15ActionPerformed
        final Object selection = lstMeas15.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Soll die Massnahmenart '"
                            + selection.toString()
                            + "' wirklich gelöscht werden?",
                    "Massnahmenart entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("meas_2015");       // NOI18N
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                //GEN-LAST:event_btnRemMeas15ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddMeas21ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddMeas21ActionPerformed
        UIUtil.findOptimalPositionOnScreen(dlgMeas21);
        dlgMeas21.setSize(750, 150);
        dlgMeas21.setVisible(true);
    }                                                                                //GEN-LAST:event_btnAddMeas21ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemMeas21ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemMeas21ActionPerformed
        final Object selection = lstMeas21.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Soll die Massnahmenart '"
                            + selection.toString()
                            + "' wirklich gelöscht werden?",
                    "Massnahmenart entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("meas_2021");       // NOI18N
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                //GEN-LAST:event_btnRemMeas21ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMeasAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMeasAbortActionPerformed
        dlgMeas.setVisible(false);
    }                                                                                //GEN-LAST:event_btnMeasAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMeasOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMeasOkActionPerformed
        final Object selection = cbMeasCataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final Thread t = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            final CidsBean selectedBean = (CidsBean)selection;
                            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(
                                    cidsBean,
                                    "de_meas_cd"); // NOI18N
                            if (colToAdd != null) {
                                if (!colToAdd.contains(selectedBean)) {
                                    colToAdd.add(selectedBean);

                                    if ((colToAdd.size() == 1) && (cbSuppl_cd.getSelectedIndex() == -1)) {
                                        // set the value of supple_cd
                                        setRemommendedEuMeasureType(selectedBean);
                                        setRemommendedEuPressureType(selectedBean);
                                    }
                                }
                            }
                        }
                    });

            t.start();
        }

        dlgMeas.setVisible(false);
    } //GEN-LAST:event_btnMeasOkActionPerformed

    /**
     * set the field supple_cd with the label 'EU-Massnahmentyp' on the recommended value, which is derived from the
     * first element of the field de_meas_cd.
     *
     * @param  selectedBean  DOCUMENT ME!
     */
    private void setRemommendedEuMeasureType(final CidsBean selectedBean) {
        String measureType = String.valueOf(selectedBean.getProperty("measure_type"));

        if (!measureType.equals("null")) {
            final StringTokenizer st = new StringTokenizer(measureType, ", ");
            if (st.hasMoreTokens()) {
                measureType = st.nextToken();
                measureType = "(" + measureType + ")";
                final ComboBoxModel model = cbSuppl_cd.getModel();

                for (int i = 0; i < model.getSize(); ++i) {
                    final Object o = model.getElementAt(i);
                    if (o instanceof CidsBean) {
                        final Object type = ((CidsBean)o).getProperty("type");

                        if ((type instanceof String) && type.toString().equals(measureType)) {
                            model.setSelectedItem((CidsBean)o);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * set the field supple_cd with the label 'EU-Belastungstyp' on the recommended value, which is derived from the
     * first element of the field de_meas_cd.
     *
     * @param  selectedBean  DOCUMENT ME!
     */
    private void setRemommendedEuPressureType(final CidsBean selectedBean) {
        final String measureType = String.valueOf(selectedBean.getProperty("p_value"));

        if (!measureType.equals("null")) {
            final ComboBoxModel model = cbPressur_cd.getModel();

            for (int i = 0; i < model.getSize(); ++i) {
                final Object o = model.getElementAt(i);
                if (o instanceof CidsBean) {
                    final Object type = ((CidsBean)o).getProperty("value");

                    if ((type instanceof String) && type.toString().equals(measureType)) {
                        model.setSelectedItem((CidsBean)o);
                        break;
                    }
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMeas15AbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMeas15AbortActionPerformed
        dlgMeas15.setVisible(false);
    }                                                                                  //GEN-LAST:event_btnMeas15AbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMeas15OkActionPerformed(final java.awt.event.ActionEvent evt) {                                     //GEN-FIRST:event_btnMeas15OkActionPerformed
        final Object selection = cbMeas15Cataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean)selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "meas_2015"); // NOI18N
            if (colToAdd != null) {
                if (!colToAdd.contains(selectedBean)) {
                    colToAdd.add(selectedBean);
                }
            }
        }
        dlgMeas15.setVisible(false);
    }                                                                                                                   //GEN-LAST:event_btnMeas15OkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMeas21AbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMeas21AbortActionPerformed
        dlgMeas21.setVisible(false);
    }                                                                                  //GEN-LAST:event_btnMeas21AbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMeas21OkActionPerformed(final java.awt.event.ActionEvent evt) {                                     //GEN-FIRST:event_btnMeas21OkActionPerformed
        final Object selection = cbMeas21Cataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean)selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "meas_2021"); // NOI18N
            if (colToAdd != null) {
                if (!colToAdd.contains(selectedBean)) {
                    colToAdd.add(selectedBean);
                }
            }
        }
        dlgMeas21.setVisible(false);
    }                                                                                                                   //GEN-LAST:event_btnMeas21OkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbStartedActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbStartedActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbStartedActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    private void deActivateGUI(final boolean enable) {
        txtKosten.setEnabled(enable);
        cbReal.setEnabled(enable);
        txtZiele.setEnabled(enable);
        jTextArea1.setEnabled(enable);
        cbPressur_cd.setEnabled(enable);
        cbPrioritaet.setEnabled(enable);
        cbRevital.setEnabled(enable);
        cbSuppl_cd.setEnabled(enable);
        cbMassn_typ.setEnabled(enable);
    }

    @Override
    public void dispose() {
        ((DefaultCismapGeometryComboBoxEditor)cbGeom).dispose();
        linearReferencedLineEditor.dispose();
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
        linearReferencedLineEditor.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        if (cidsBean != null) {
            cidsBean.getMetaObject().setAllClasses();
            if (dropBehaviorListener.isRouteChanged() && !linearReferencedLineEditor.hasChangedSinceDrop()) {
                final int ans = JOptionPane.showConfirmDialog(
                        this,
                        "Sie haben die Stationen nicht geändert, nachdem Sie eine "
                                + "neue Route ausgewählt haben. Möchten Sie die Stationen ändern?",
                        "Keine Änderung der Stationen",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (ans == JOptionPane.YES_OPTION) {
                    return false;
                }
            }

            try {
                linearReferencedLineEditor.hasChangedSinceDrop();
                cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());   // NOI18N
                cidsBean.setProperty("av_time", new java.sql.Timestamp(System.currentTimeMillis())); // NOI18N

                for (final CidsBean tmp : beansToDelete) {
                    tmp.persist();
                }
            } catch (final Exception ex) {
                LOG.error("Error in prepareForSave.", ex); // NOI18N
            }

            try {
                final CidsBean stalu = (CidsBean)cidsBean.getProperty("stalu");
                Geometry geom = (Geometry)cidsBean.getProperty("linie.geom.geo_field");

                if (geom == null) {
                    geom = (Geometry)cidsBean.getProperty("additional_geom.geo_field");
                }

                if ((stalu == null) && (geom != null)) {
                    final String staluName = determineStalu(geom);

                    if (staluName != null) {
                        for (int i = 0; i < cbStalu.getModel().getSize(); ++i) {
                            final CidsBean bean = (CidsBean)cbStalu.getModel().getElementAt(i);
                            if (bean.getProperty("value").equals(staluName)) {
                                cidsBean.setProperty("stalu", bean);
                                break;
                            }
                        }
                    }
                }
            } catch (final Exception ex) {
                LOG.error("Error in prepareForSave.", ex); // NOI18N
            }
        }

        boolean save = true;
        save &= linearReferencedLineEditor.prepareForSave();
        return save;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   geom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String determineStalu(final Geometry geom) {
        try {
            final CidsServerSearch search = new StaluSearch(geom.toText());
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

            if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                final Object o = resArray.get(0).get(0);

                if (o instanceof String) {
                    return o.toString();
                }
            } else {
                LOG.error("Server error in getWk_k(). Cids server search return null. " // NOI18N
                            + "See the server logs for further information");     // NOI18N
            }
        } catch (ConnectionException e) {
            LOG.error("Exception during a cids server search.", e);               // NOI18N
        }

        return null;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        if (cidsBean != null) {
            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_fg")) {        // NOI18N
                    bindToWb(WB_PROPERTIES[0], bean);
                    dropBehaviorListener.setWkFg(bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_sg")) { // NOI18N
                    bindToWb(WB_PROPERTIES[1], bean);
                }
                // Massnahmen beziehen sich ausschliesslich auf Fliessgewaesser und Seegewaesser
                else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_kg")) { // NOI18N
                    // bindToWb(WB_PROPERTIES[2], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_gw")) { // NOI18N
                    // bindToWb(WB_PROPERTIES[3], bean);
                }
            }
            bindReadOnlyFields();
        }
    }

    /**
     * binds the given water body to the current CidsBean object.
     *
     * @param  propertyName   the name of the water body property. (The allowed values are stored in the array
     *                        WB_PROPERTIES)
     * @param  propertyEntry  the water body object
     */
    private void bindToWb(final String propertyName, final CidsBean propertyEntry) {
        try {
            cidsBean.setProperty(propertyName, propertyEntry);

            for (final String propName : WB_PROPERTIES) {
                if (!propName.equals(propertyName)) {
                    cidsBean.setProperty(propName, null);
                }
            }

            if (!propertyName.equals(WB_PROPERTIES[1]) || (cidsBean.getProperty("linie") == null)) {
                copyGeometries(String.valueOf(propertyEntry.getProperty("id"))); // NOI18N
            } else {
                setWBValues(String.valueOf(propertyEntry.getProperty("id")));
            }
            showOrHideGeometryEditors();
            bindReadOnlyFields();
        } catch (final Exception ex) {
            LOG.error("Error while binding a water body", ex);                   // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkId  DOCUMENT ME!
     */
    private void copyGeometries(final String wkId) {
        // delete old geometries
        try {
            CidsBeanSupport.deleteStationlineIfExists(cidsBean, "linie", beansToDelete); // NOI18N
            cidsBean.setProperty("linie", null);                                         // NOI18N
        } catch (final Exception e) {
            LOG.error("Cannot delete cids bean.", e);                                    // NOI18N
        }

        // delete additional_geom if exists
        try {
            CidsBeanSupport.deletePropertyIfExists(cidsBean, "additional_geom", beansToDelete); // NOI18N
            cidsBean.setProperty("additional_geom", null);                                      // NOI18N
        } catch (final Exception e) {
            LOG.error("Cannot delete cids bean.", e);                                           // NOI18N
        }

        setWBValues(wkId);

        // copy new geometries
        if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[1]) != null)) {
            final CidsBean wk_sg = (CidsBean)cidsBean.getProperty(WB_PROPERTIES[1]);

            try {
                final CidsBean geom = CidsBeanSupport.cloneCidsBean((CidsBean)wk_sg.getProperty("geom")); // NOI18N
                cidsBean.setProperty("additional_geom", geom);                                            // NOI18N
            } catch (final Exception e) {
                LOG.error("Cannot copy the new geometry.", e);                                            // NOI18N
            }
        } else if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[0]) != null)) {
            // wk_fg
            final CidsBean wk_fg = (CidsBean)cidsBean.getProperty(WB_PROPERTIES[0]);
            final List<CidsBean> teile = CidsBeanSupport.getBeanCollectionFromProperty(wk_fg, "teile"); // NOI18N

            if ((teile != null) && (teile.size() > 0)) {
                final CidsBean teil = teile.get(0);

                try {
                    final CidsBean lineBean = (CidsBean)teil.getProperty("linie");
                    final CidsBean clonedLineBean = CidsBeanSupport.cloneStationline(lineBean); // NOI18N
                    cidsBean.setProperty("linie", clonedLineBean);
                } catch (final Exception e) {
                    LOG.error("Cannot copy the new geometry.", e);                              // NOI18N
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkId  DOCUMENT ME!
     */
    private void setWBValues(final String wkId) {
        try {
            int lfdnr = 1;
            String wkTable;
            String massReferencedField;

            if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[1]) != null)) {
                wkTable = "wk_sg";             // NOI18N
                massReferencedField = "wk_sg"; // NOI18N
            } else {
                wkTable = "wk_fg";             // NOI18N
                massReferencedField = "wk_fg"; // NOI18N
            }

            final CidsServerSearch search = new MaxWBNumberSearch(wkTable, wkId, massReferencedField);
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;
            if ((resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                final Object o = resArray.get(0).get(0);
                if (o instanceof java.math.BigDecimal) {
                    lfdnr = ((java.math.BigDecimal)o).intValue();
                    ++lfdnr;
                }
            }

            cidsBean.setProperty("massn_wk_lfdnr", BigDecimal.valueOf(lfdnr));                  // NOI18N
            cidsBean.setProperty("massn_id", getWk_k() + "_M_" + convertNumberToString(lfdnr)); // NOI18N
        } catch (final Exception e) {
            LOG.error(e, e);
        }
    }
    /**
     * laut Herr Rahmlow in der EMail vom 28.3.2012 muss die Nummer zwei Stellen haben.
     *
     * @param   number  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String convertNumberToString(final int number) {
        if (number > 9) {
            return "" + number;
        } else {
            return "0" + number;
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void showOrHideGeometryEditors() {
        if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[1]) != null)
                    && (cidsBean.getProperty("linie") == null)) {
            panGeo.setVisible(false);
            cbGeom.setVisible(true);
            lblGeom.setVisible(true);
        } else {
            panGeo.setVisible(true);
            cbGeom.setVisible(true);
            lblGeom.setVisible(true);
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class WfdTypeCodeRenderer extends DefaultListCellRenderer {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getListCellRendererComponent(final JList list,
                final Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            final Component result = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if ((result instanceof JLabel) && (value instanceof CidsBean)) {
                final CidsBean bean = (CidsBean)value;

                final String text = bean.getProperty("value") + " - " + bean.getProperty("name");
                ((JLabel)result).setText(text);
            }

            return result;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomElementComparator implements Comparator<CidsBean> {

        //~ Instance fields ----------------------------------------------------

        private int integerIndex = 0;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomElementComparator object.
         */
        public CustomElementComparator() {
        }

        /**
         * Creates a new CustomElementComparator object.
         *
         * @param  integerIndex  DOCUMENT ME!
         */
        public CustomElementComparator(final int integerIndex) {
            this.integerIndex = integerIndex;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final CidsBean o1, final CidsBean o2) {
            if ((o1 != null) && (o2 != null)) {
                final String strValue1 = (String)o1.getProperty("value");
                final String strValue2 = (String)o2.getProperty("value");

                if ((strValue1 != null) && (strValue2 != null)) {
                    try {
                        final Integer value1 = Integer.parseInt(strValue1.substring(integerIndex));
                        final Integer value2 = Integer.parseInt(strValue2.substring(integerIndex));

                        return value1.intValue() - value2.intValue();
                    } catch (NumberFormatException e) {
                        // nothing to do, because not every 'value'-property contains a integer
                    }

                    return strValue1.compareTo(strValue2);
                } else {
                    if ((strValue1 == null) && (strValue2 == null)) {
                        return 0;
                    } else {
                        return ((strValue1 == null) ? -1 : 1);
                    }
                }
            } else {
                if ((o1 == null) && (o2 == null)) {
                    return 0;
                } else {
                    return ((o1 == null) ? -1 : 1);
                }
            }
        }
    }
}
