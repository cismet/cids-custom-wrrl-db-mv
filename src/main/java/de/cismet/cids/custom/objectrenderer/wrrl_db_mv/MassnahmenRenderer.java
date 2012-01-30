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

import java.sql.Timestamp;

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.TimestampConverter;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingConstants;

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
public class MassnahmenRenderer extends JPanel implements CidsBeanRenderer, FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MassnahmenRenderer.class);
    private static final String[] WB_PROPERTIES = { "wk_fg", "wk_sg", "wk_kg", "wk_gw" }; // NOI18N

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blbSpace;
    private javax.swing.JCheckBox cbFin;
    private javax.swing.JCheckBox cbStarted;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblBeschrDerMa;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGwk;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblHeading3;
    private javax.swing.JLabel lblHeading4;
    private javax.swing.JLabel lblKosten;
    private javax.swing.JLabel lblMassn_id;
    private javax.swing.JLabel lblMassn_typ;
    private javax.swing.JLabel lblMs_cd_bw;
    private javax.swing.JLabel lblPressur_cd;
    private javax.swing.JLabel lblPrioritaet;
    private javax.swing.JLabel lblRevital;
    private javax.swing.JLabel lblStalu;
    private javax.swing.JLabel lblSubs_typ;
    private javax.swing.JLabel lblSuppl_cd;
    private javax.swing.JTextField lblValGwk;
    private javax.swing.JLabel lblValKosten;
    private javax.swing.JLabel lblValLfdnr;
    private javax.swing.JTextField lblValMassn_id;
    private javax.swing.JLabel lblValMassn_typ;
    private javax.swing.JLabel lblValMs_cd_bw;
    private javax.swing.JLabel lblValPressure_cd;
    private javax.swing.JLabel lblValPrioritaet;
    private javax.swing.JLabel lblValReal;
    private javax.swing.JLabel lblValRevital;
    private javax.swing.JLabel lblValStalu;
    private javax.swing.JLabel lblValSuppl_cd;
    private javax.swing.JLabel lblValWk_k;
    private javax.swing.JLabel lblValWk_name;
    private javax.swing.JLabel lblValZiele;
    private javax.swing.JLabel lblWk_k;
    private javax.swing.JLabel lblWk_name;
    private javax.swing.JLabel lblZiele;
    private javax.swing.JLabel lbllfdnr;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JList lstMeas15;
    private javax.swing.JList lstMeas21;
    private javax.swing.JList lstdeMeas;
    private de.cismet.tools.gui.RoundedPanel panDeMeas;
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
    private javax.swing.JScrollPane scpMeas15;
    private javax.swing.JScrollPane scpMeas21;
    private javax.swing.JScrollPane scpdeMeas;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public MassnahmenRenderer() {
        initComponents();
        jTextArea1.setEditable(false);
        lblValGwk.setBorder(null);
        lblValGwk.setOpaque(false);
        lblValGwk.setEditable(false);
        lblValMassn_id.setBorder(null);
        lblValMassn_id.setOpaque(false);
        lblValMassn_id.setEditable(false);
        linearReferencedLineEditor.setLineField("linie"); // NOI18N
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
            linearReferencedLineEditor.setCidsBean(cidsBean);
            bindingGroup.bind();
        }

        showOrHideGeometryEditors();
        bindReadOnlyFields();
    }

    /**
     * DOCUMENT ME!
     */
    private void showOrHideGeometryEditors() {
        if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[1]) != null)) {
            panGeo.setVisible(false);
        } else {
            panGeo.setVisible(true);
        }
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
     * @return  the abbreviation of the currently assigned water body
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
     * @return  the name of the currently assigned water body
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
     * @return  the currently assigned gwk
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
        blbSpace = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblZiele = new javax.swing.JLabel();
        lblMassn_typ = new javax.swing.JLabel();
        lblRevital = new javax.swing.JLabel();
        lblPrioritaet = new javax.swing.JLabel();
        lblKosten = new javax.swing.JLabel();
        lblSubs_typ = new javax.swing.JLabel();
        cbFin = new javax.swing.JCheckBox();
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
        lblMassn_id = new javax.swing.JLabel();
        lblStalu = new javax.swing.JLabel();
        lblValZiele = new javax.swing.JLabel();
        lblValKosten = new javax.swing.JLabel();
        lblValReal = new javax.swing.JLabel();
        lblValStalu = new javax.swing.JLabel();
        lblValMassn_typ = new javax.swing.JLabel();
        lblValRevital = new javax.swing.JLabel();
        lblValPrioritaet = new javax.swing.JLabel();
        lblValGwk = new javax.swing.JTextField();
        lblValMassn_id = new javax.swing.JTextField();
        cbStarted = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        panDeMeas = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        scpdeMeas = new javax.swing.JScrollPane();
        lstdeMeas = new javax.swing.JList();
        panMeas15 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo3 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading3 = new javax.swing.JLabel();
        panInfoContent3 = new javax.swing.JPanel();
        scpMeas15 = new javax.swing.JScrollPane();
        lstMeas15 = new javax.swing.JList();
        panMeas21 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo4 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading4 = new javax.swing.JLabel();
        panInfoContent4 = new javax.swing.JPanel();
        scpMeas21 = new javax.swing.JScrollPane();
        lstMeas21 = new javax.swing.JList();
        lblSuppl_cd = new javax.swing.JLabel();
        lblPressur_cd = new javax.swing.JLabel();
        lblMs_cd_bw = new javax.swing.JLabel();
        lblValMs_cd_bw = new javax.swing.JLabel();
        lblValPressure_cd = new javax.swing.JLabel();
        lblValSuppl_cd = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panGeo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        linearReferencedLineEditor = new LinearReferencedLineRenderer();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

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
                MassnahmenRenderer.class,
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

        cbFin.setText(org.openide.util.NbBundle.getMessage(MassnahmenRenderer.class, "MassnahmenRenderer.cbfin.text")); // NOI18N
        cbFin.setContentAreaFilled(false);
        cbFin.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
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
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbFin, gridBagConstraints);

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

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

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

        lblValZiele.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValZiele.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ziele}"),
                lblValZiele,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValZiele, gridBagConstraints);

        lblValKosten.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValKosten.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kosten}"),
                lblValKosten,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValKosten, gridBagConstraints);

        lblValReal.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValReal.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.realisierung.name}"),
                lblValReal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValReal, gridBagConstraints);

        lblValStalu.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValStalu.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stalu.value}"),
                lblValStalu,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(lblValStalu, gridBagConstraints);

        lblValMassn_typ.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValMassn_typ.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create(
                    "${cidsBean.massn_typ.code}-${cidsBean.massn_typ.description}"),
                lblValMassn_typ,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValMassn_typ, gridBagConstraints);

        lblValRevital.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValRevital.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.revital.code}-${cidsBean.revital.description}"),
                lblValRevital,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValRevital, gridBagConstraints);

        lblValPrioritaet.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValPrioritaet.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.prioritaet.description}"),
                lblValPrioritaet,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValPrioritaet, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValGwk, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_id}"),
                lblValMassn_id,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValMassn_id, gridBagConstraints);

        cbStarted.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenRenderer.class,
                "MassnahmenRenderer.cbStarted.text")); // NOI18N
        cbStarted.setContentAreaFilled(false);
        cbStarted.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
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
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbStarted, gridBagConstraints);

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

        lblPressur_cd.setText("EU-Belastungstyp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel3.add(lblPressur_cd, gridBagConstraints);

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

        lblValPressure_cd.setMinimumSize(new java.awt.Dimension(300, 25));
        lblValPressure_cd.setPreferredSize(new java.awt.Dimension(300, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.pressur_cd.name}"),
                lblValPressure_cd,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 0);
        jPanel3.add(lblValPressure_cd, gridBagConstraints);

        lblValSuppl_cd.setMinimumSize(new java.awt.Dimension(300, 25));
        lblValSuppl_cd.setPreferredSize(new java.awt.Dimension(300, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suppl_cd.name}"),
                lblValSuppl_cd,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 0);
        jPanel3.add(lblValSuppl_cd, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 10, 20);
        panInfoContent.add(jPanel3, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 20);
        panInfoContent.add(jPanel1, gridBagConstraints);

        panGeo.setMinimumSize(new java.awt.Dimension(640, 140));
        panGeo.setPreferredSize(new java.awt.Dimension(640, 140));

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
    private void cbStartedActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbStartedActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbStartedActionPerformed

    @Override
    public void dispose() {
        bindingGroup.unbind();
        linearReferencedLineEditor.dispose();
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
