
/*
 * WkFgEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.server.middleware.types.MetaClass;
import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.ScrollableComboBox;
import de.cismet.cids.custom.util.StationToMapRegistry;
import de.cismet.cids.custom.util.TimestampConverter;
import de.cismet.cids.custom.util.UIUtil;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorSaveListener;
import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;
import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;
import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;
import de.cismet.tools.gui.FooterComponentProvider;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author therter
 */
public class MassnahmenEditor extends JPanel implements CidsBeanRenderer, EditorSaveListener, FooterComponentProvider, CidsBeanDropListener {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MassnahmenEditor.class);
    private CidsBean cidsBean;
    private static final MetaClass DE_MEASURE_TYPE_CODE_MC;
    private static final MetaClass DE_MEASURE_TYPE_CODE_AFTER2015_MC;
    private static final String[] WB_PROPERTIES = {"wk_fg", "wk_sg", "wk_kg", "wk_gw"};
    private ArrayList<CidsBean> beansToDelete = new ArrayList<CidsBean>();

    static {
        DE_MEASURE_TYPE_CODE_MC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wfd.de_measure_type_code");
        DE_MEASURE_TYPE_CODE_AFTER2015_MC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wfd.de_measure_type_code_after2015");
    }

    /** Creates new form WkFgEditor */
    public MassnahmenEditor() {
        initComponents();
        deActivateGUI(false);
        linearReferencedLineEditor.setMetaClassName("MASSNAHMEN");
        linearReferencedLineEditor.setFromStationField("stat_von");
        linearReferencedLineEditor.setToStationField("stat_bis");
        linearReferencedLineEditor.setRealGeomField("real_geom");
        linearReferencedLineEditor.addLinearReferencedLineEditorListener(new LinearReferencedLineEditorListener() {
            @Override
            public void linearReferencedLineCreated() {
                zoomToFeature();
            }
        });
        try {
            new CidsBeanDropTarget(this);
        } catch (Exception ex) {
            log.debug("error while creating CidsBeanDropTarget", ex);
        }
    }


    @Override
    public void setCidsBean(CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        if (cidsBean != null) {
            deActivateGUI(true);
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(bindingGroup, cidsBean);
            bindingGroup.bind();
            linearReferencedLineEditor.setCidsBean(cidsBean);
            Object avUser = cidsBean.getProperty("av_user");
            Object avTime = cidsBean.getProperty("av_time");
            if (avUser == null) {
                avUser = "(unbekannt)";
            }
            if (avTime instanceof Timestamp) {
                avTime = TimestampConverter.getInstance().convertForward((Timestamp) avTime);
            } else {
                avTime = "(unbekannt)";
            }
            lblFoot.setText("Zuletzt bearbeitet von " + avUser + " am " + avTime);
            
            //zoom to feature
            MappingComponent mappingComponent = CismapBroker.getInstance().getMappingComponent();
            if (!mappingComponent.isFixedMapExtent()) {
                CismapBroker.getInstance().getMappingComponent().zoomToFeatureCollection(mappingComponent.isFixedMapScale());
            }
        } else {
            lblFoot.setText("");
            deActivateGUI(false);
        }
        bindReadOnlyFields();
        showOrHideGeometryEditors();
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    private void bindReadOnlyFields() {
        if (cidsBean == null) {
            lblValWk_k.setText("");
            lblValWk_name.setText("");
            lblValGwk.setText("");
            lblValMs_cd_bw.setText("");
        } else {
            String wk_k = getWk_k();
            lblValWk_k.setText(wk_k);
            lblValWk_name.setText(getWk_name());
            lblValGwk.setText(getGwk());
            
            if ( !wk_k.equals(CidsBeanSupport.FIELD_NOT_SET) ) {
                lblValMs_cd_bw.setText("DE_MV_" + wk_k);
            } else {
                lblValMs_cd_bw.setText("");
            }
        }
    }


    private String getWk_k() {
        if (cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            return String.valueOf( cidsBean.getProperty("wk_fg.wk_k") );
        } else if (cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            return String.valueOf( cidsBean.getProperty("wk_sg.wk_k") );
        } else if (cidsBean.getProperty(WB_PROPERTIES[2]) != null) {
            return String.valueOf( cidsBean.getProperty("wk_kg.name") );
        } else if (cidsBean.getProperty(WB_PROPERTIES[3]) != null) {
            return String.valueOf( cidsBean.getProperty("wk_gw.name") );
        } else {
            return CidsBeanSupport.FIELD_NOT_SET;
        }
    }

    private String getWk_name() {
        if (cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            return String.valueOf( cidsBean.getProperty("wk_fg.wk_n") );
        } else if (cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            return String.valueOf( cidsBean.getProperty("wk_sg.ls_name") );
        } else if (cidsBean.getProperty(WB_PROPERTIES[2]) != null) {
            return String.valueOf( cidsBean.getProperty("wk_kg.name") );
        } else if (cidsBean.getProperty(WB_PROPERTIES[3]) != null) {
            return String.valueOf( cidsBean.getProperty("wk_gw.name") );
        } else {
            return CidsBeanSupport.FIELD_NOT_SET;
        }
    }

    private String getGwk() {
        if (cidsBean.getProperty("stat_von") != null) {
            return String.valueOf( cidsBean.getProperty("stat_von.route.gwk") );
        } else {
            return "";
        }
    }

    private String getStalu() {
        if (cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            Object stalu = cidsBean.getProperty("wk_fg.stalu");
            if (stalu == null) {
                return CidsBeanSupport.FIELD_NOT_SET;
            } else {
                return stalu.toString();
            }
        } else {
            return "";
        }
    }

    private void zoomToFeature() {
        MappingComponent mappingComponent = CismapBroker.getInstance().getMappingComponent();
        if (!mappingComponent.isFixedMapExtent()) {
            Object o = cidsBean.getProperty("wk_fg");

            if ( o != null ) {
                Collection<Feature> collection = new ArrayList<Feature>();
                FeatureCollection fCol = mappingComponent.getFeatureCollection();

                for (Feature feature : fCol.getAllFeatures()) {
                    if ( !(feature instanceof StationToMapRegistry.RouteFeature) ) {
                        collection.add(feature);
                    }
                }

                CismapBroker.getInstance().getMappingComponent().zoomToAFeatureCollection(collection, true, mappingComponent.isFixedMapScale());
            } else {
                CismapBroker.getInstance().getMappingComponent().zoomToFeatureCollection(mappingComponent.isFixedMapScale());
            }
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
        cbMeasCataloge = new ScrollableComboBox(DE_MEASURE_TYPE_CODE_MC,true,true);
        panMenButtonsMeas = new javax.swing.JPanel();
        btnMeasAbort = new javax.swing.JButton();
        btnMeasOk = new javax.swing.JButton();
        dlgMeas15 = new javax.swing.JDialog();
        lblMeas15Cataloge = new javax.swing.JLabel();
        cbMeas15Cataloge = new ScrollableComboBox(DE_MEASURE_TYPE_CODE_AFTER2015_MC,true,true);
        panMenButtonsMeas15 = new javax.swing.JPanel();
        btnMeas15Abort = new javax.swing.JButton();
        btnMeas15Ok = new javax.swing.JButton();
        dlgMeas21 = new javax.swing.JDialog();
        lblMeas21Cataloge = new javax.swing.JLabel();
        cbMeas21Cataloge = new ScrollableComboBox(DE_MEASURE_TYPE_CODE_AFTER2015_MC,true,true);
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
        jCheckBox1 = new javax.swing.JCheckBox();
        txtZiele = new javax.swing.JTextField();
        lbllfdnr = new javax.swing.JLabel();
        lblValLfdnr = new javax.swing.JLabel();
        txtReal = new javax.swing.JTextField();
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
        txtStalu = new javax.swing.JTextField();
        txtMassn_id = new javax.swing.JTextField();
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
        cbPressur_cd = new ScrollableComboBox();
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

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        dlgMeas.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblMeasCataloge.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.lblMeasCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas.getContentPane().add(lblMeasCataloge, gridBagConstraints);

        cbMeasCataloge.setMinimumSize(new java.awt.Dimension(350, 18));
        cbMeasCataloge.setPreferredSize(new java.awt.Dimension(350, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas.getContentPane().add(cbMeasCataloge, gridBagConstraints);

        panMenButtonsMeas.setLayout(new java.awt.GridBagLayout());

        btnMeasAbort.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.btnMeasAbort.text")); // NOI18N
        btnMeasAbort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMeasAbortActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas.add(btnMeasAbort, gridBagConstraints);

        btnMeasOk.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.btnMeasOk.text")); // NOI18N
        btnMeasOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMeasOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMeasOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMeasOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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

        lblMeas15Cataloge.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.lblMeas15Cataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas15.getContentPane().add(lblMeas15Cataloge, gridBagConstraints);

        cbMeas15Cataloge.setMinimumSize(new java.awt.Dimension(350, 18));
        cbMeas15Cataloge.setPreferredSize(new java.awt.Dimension(350, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas15.getContentPane().add(cbMeas15Cataloge, gridBagConstraints);

        panMenButtonsMeas15.setLayout(new java.awt.GridBagLayout());

        btnMeas15Abort.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.btnMeas15Abort.text")); // NOI18N
        btnMeas15Abort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMeas15AbortActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas15.add(btnMeas15Abort, gridBagConstraints);

        btnMeas15Ok.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.btnMeas15Ok.text")); // NOI18N
        btnMeas15Ok.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMeas15Ok.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMeas15Ok.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMeas15Ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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

        lblMeas21Cataloge.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.lblMeas21Cataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas21.getContentPane().add(lblMeas21Cataloge, gridBagConstraints);

        cbMeas21Cataloge.setMinimumSize(new java.awt.Dimension(350, 18));
        cbMeas21Cataloge.setPreferredSize(new java.awt.Dimension(350, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas21.getContentPane().add(cbMeas21Cataloge, gridBagConstraints);

        panMenButtonsMeas21.setLayout(new java.awt.GridBagLayout());

        btnMeas21Abort.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.btnMeas21Abort.text")); // NOI18N
        btnMeas21Abort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMeas21AbortActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas21.add(btnMeas21Abort, gridBagConstraints);

        btnMeas21Ok.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.btnMeas21Ok.text")); // NOI18N
        btnMeas21Ok.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMeas21Ok.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMeas21Ok.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMeas21Ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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

        setMinimumSize(new java.awt.Dimension(940, 770));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(940, 770));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMaximumSize(new java.awt.Dimension(950, 790));
        panInfo.setMinimumSize(new java.awt.Dimension(880, 770));
        panInfo.setPreferredSize(new java.awt.Dimension(880, 770));

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

        jPanel2.setMinimumSize(new java.awt.Dimension(380, 540));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(480, 540));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblZiele.setText("Entwicklungsziele (BVP)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblZiele, gridBagConstraints);

        lblMassn_typ.setText("Umfang (BVP)");
        lblMassn_typ.setToolTipText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.lblMassn_typ.toolTipText")); // NOI18N
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
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblSubs_typ, gridBagConstraints);

        txtKosten.setMinimumSize(new java.awt.Dimension(200, 25));
        txtKosten.setPreferredSize(new java.awt.Dimension(200, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kosten}"), txtKosten, org.jdesktop.beansbinding.BeanProperty.create("text"));
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_typ}"), cbMassn_typ, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.revital}"), cbRevital, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.prioritaet}"), cbPrioritaet, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbPrioritaet, gridBagConstraints);

        jCheckBox1.setText("Maßnahme bereits umgesetzt");
        jCheckBox1.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_fin}"), jCheckBox1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
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
        jPanel2.add(jCheckBox1, gridBagConstraints);

        txtZiele.setMinimumSize(new java.awt.Dimension(200, 25));
        txtZiele.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ziele}"), txtZiele, org.jdesktop.beansbinding.BeanProperty.create("text"));
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_wk_lfdnr}"), lblValLfdnr, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValLfdnr, gridBagConstraints);

        txtReal.setMinimumSize(new java.awt.Dimension(200, 25));
        txtReal.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.real}"), txtReal, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(txtReal, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(380, 100));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(380, 100));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massnahme}"), jTextArea1, org.jdesktop.beansbinding.BeanProperty.create("text"));
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_fg.wk_k}"), lblValWk_k, org.jdesktop.beansbinding.BeanProperty.create("text"));
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

        lblStalu.setText("Zuständiges STALU");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(lblStalu, gridBagConstraints);

        txtStalu.setMinimumSize(new java.awt.Dimension(200, 25));
        txtStalu.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stalu}"), txtStalu, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(txtStalu, gridBagConstraints);

        txtMassn_id.setMinimumSize(new java.awt.Dimension(200, 25));
        txtMassn_id.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_id}"), txtMassn_id, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(txtMassn_id, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 10, 20);
        panInfoContent.add(jPanel2, gridBagConstraints);

        jPanel3.setMinimumSize(new java.awt.Dimension(380, 540));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(480, 540));
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

        btnAddDe_meas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddDe_meas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDe_measActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panDe_meas.add(btnAddDe_meas, gridBagConstraints);

        btnRemDeMeas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemDeMeas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.de_meas_cd}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, lstdeMeas);
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

        btnAddMeas15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddMeas15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMeas15ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panmeas15.add(btnAddMeas15, gridBagConstraints);

        btnRemMeas15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemMeas15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, lstMeas15);
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

        btnAddMeas21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddMeas21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMeas21ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panmeas21.add(btnAddMeas21, gridBagConstraints);

        btnRemMeas21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemMeas21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, lstMeas21);
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.suppl_cd}"), cbSuppl_cd, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.pressur_cd}"), cbPressur_cd, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
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

        panGeo.setMinimumSize(new java.awt.Dimension(640, 120));
        panGeo.setPreferredSize(new java.awt.Dimension(640, 120));

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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.additional_geom}"), cbGeom, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
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
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 20);
        panInfoContent.add(jPanel1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panInfo, gridBagConstraints);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void btnAddDe_measActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDe_measActionPerformed
        UIUtil.findOptimalPositionOnScreen(dlgMeas);
        dlgMeas.setSize(400, 150);
        dlgMeas.setVisible(true);
}//GEN-LAST:event_btnAddDe_measActionPerformed

    private void btnRemDeMeasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemDeMeasActionPerformed
        final Object selection = lstdeMeas.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(this, "Soll die Massnahmenart '" + selection.toString() + "' wirklich gelöscht werden?", "Massnahmenart entfernen", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean) selection;
                    final Object beanColl = cidsBean.getProperty("de_meas_cd");
                    if (beanColl instanceof Collection) {
                        ((Collection) beanColl).remove(beanToDelete);
                    }
                } catch (Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
}//GEN-LAST:event_btnRemDeMeasActionPerformed

    private void btnAddMeas15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMeas15ActionPerformed
        UIUtil.findOptimalPositionOnScreen(dlgMeas15);
        dlgMeas15.setSize(400, 150);
        dlgMeas15.setVisible(true);
    }//GEN-LAST:event_btnAddMeas15ActionPerformed

    private void btnRemMeas15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemMeas15ActionPerformed
        final Object selection = lstMeas15.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(this, "Soll die Massnahmenart '" + selection.toString() + "' wirklich gelöscht werden?", "Massnahmenart entfernen", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean) selection;
                    final Object beanColl = cidsBean.getProperty("meas_2015");
                    if (beanColl instanceof Collection) {
                        ((Collection) beanColl).remove(beanToDelete);
                    }
                } catch (Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }//GEN-LAST:event_btnRemMeas15ActionPerformed

    private void btnAddMeas21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMeas21ActionPerformed
        UIUtil.findOptimalPositionOnScreen(dlgMeas21);
        dlgMeas21.setSize(400, 150);
        dlgMeas21.setVisible(true);
    }//GEN-LAST:event_btnAddMeas21ActionPerformed

    private void btnRemMeas21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemMeas21ActionPerformed
        final Object selection = lstMeas21.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(this, "Soll die Massnahmenart '" + selection.toString() + "' wirklich gelöscht werden?", "Massnahmenart entfernen", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean) selection;
                    final Object beanColl = cidsBean.getProperty("meas_2021");
                    if (beanColl instanceof Collection) {
                        ((Collection) beanColl).remove(beanToDelete);
                    }
                } catch (Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }//GEN-LAST:event_btnRemMeas21ActionPerformed

    private void btnMeasAbortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMeasAbortActionPerformed
        dlgMeas.setVisible(false);
}//GEN-LAST:event_btnMeasAbortActionPerformed

    private void btnMeasOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMeasOkActionPerformed
        final Object selection = cbMeasCataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean) selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "de_meas_cd");
            if (colToAdd != null) {
                if (!colToAdd.contains(selectedBean)) {
                    colToAdd.add(selectedBean);
                }
            }
        }
        dlgMeas.setVisible(false);
}//GEN-LAST:event_btnMeasOkActionPerformed

    private void btnMeas15AbortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMeas15AbortActionPerformed
        dlgMeas15.setVisible(false);
    }//GEN-LAST:event_btnMeas15AbortActionPerformed

    private void btnMeas15OkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMeas15OkActionPerformed
        final Object selection = cbMeas15Cataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean) selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "meas_2015");
            if (colToAdd != null) {
                if (!colToAdd.contains(selectedBean)) {
                    colToAdd.add(selectedBean);
                }
            }
        }
        dlgMeas15.setVisible(false);
    }//GEN-LAST:event_btnMeas15OkActionPerformed

    private void btnMeas21AbortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMeas21AbortActionPerformed
        dlgMeas21.setVisible(false);
    }//GEN-LAST:event_btnMeas21AbortActionPerformed

    private void btnMeas21OkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMeas21OkActionPerformed
        final Object selection = cbMeas21Cataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean) selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "meas_2021");
            if (colToAdd != null) {
                if (!colToAdd.contains(selectedBean)) {
                    colToAdd.add(selectedBean);
                }
            }
        }
        dlgMeas21.setVisible(false);
    }//GEN-LAST:event_btnMeas21OkActionPerformed

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
    private javax.swing.JComboBox cbGeom;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassn_typ;
    private javax.swing.JComboBox cbMeas15Cataloge;
    private javax.swing.JComboBox cbMeas21Cataloge;
    private javax.swing.JComboBox cbMeasCataloge;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPressur_cd;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPrioritaet;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbRevital;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbSuppl_cd;
    private javax.swing.JDialog dlgMeas;
    private javax.swing.JDialog dlgMeas15;
    private javax.swing.JDialog dlgMeas21;
    private javax.swing.JCheckBox jCheckBox1;
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
    private javax.swing.JTextField txtReal;
    private javax.swing.JTextField txtStalu;
    private javax.swing.JTextField txtZiele;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables


    private void deActivateGUI(boolean enable) {
        txtKosten.setEnabled(enable);
        txtReal.setEnabled(enable);
        txtZiele.setEnabled(enable);
        jTextArea1.setEnabled(enable);
        cbPressur_cd.setEnabled(enable);
        cbPrioritaet.setEnabled(enable);
        cbRevital.setEnabled(enable);
        cbSuppl_cd.setEnabled(enable);
        cbMassn_typ.setEnabled(enable);
        jCheckBox1.setEnabled(enable);
    }


    @Override
    public void dispose() {
        ((DefaultCismapGeometryComboBoxEditor) cbGeom).dispose();
        linearReferencedLineEditor.dispose();
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(String title) {
        //NOP
    }

    @Override
    public void editorClosed(EditorSaveStatus status) {
        //TODO ?
    }

    @Override
    public boolean prepareForSave() {
        if (cidsBean != null) {
            try {
                cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());
                cidsBean.setProperty("av_time", new java.sql.Timestamp(System.currentTimeMillis()));

                for (CidsBean tmp : beansToDelete) {
                    tmp.persist();
                }
            } catch (Exception ex) {
                log.error(ex, ex);
            }
        }
        return true;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    @Override
    public void beansDropped(ArrayList<CidsBean> beans) {
        if (cidsBean != null) {
            for (CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_fg")) {
                    bindToWb(WB_PROPERTIES[0], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_sg")) {
                    bindToWb(WB_PROPERTIES[1], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_kg")) {
                //    bindToWb(WB_PROPERTIES[2], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_gw")) {
                //    bindToWb(WB_PROPERTIES[3], bean);
                }
            }
            bindReadOnlyFields();
        }
    }

    /**
     * binds the given water body to the current CidsBean object
     * @param propertyName the name of the water body property. (The allowed values are stored in the array WB_PROPERTIES)
     * @param propertyEntry the water body object
     */
    private void bindToWb(String propertyName, CidsBean propertyEntry) {
        try {
            cidsBean.setProperty(propertyName, propertyEntry);

            for (String propName : WB_PROPERTIES) {
                if (!propName.equals(propertyName)) {
                    cidsBean.setProperty(propName, null);
                }
            }

            copyGeometries();
            showOrHideGeometryEditors();
        } catch (Exception ex) {
            log.error("Error while binding a water body", ex);
        }
    }


    private void copyGeometries() {
        //delete old geometries
        //delete stat_von if exists
        try {
            CidsBeanSupport.deleteStationIfExists(cidsBean, "stat_von", beansToDelete);
            cidsBean.setProperty("stat_von", null);
        } catch (Exception e) {
            log.error("Cannot delete cids bean.", e);
        }

        //stat_bis if exists
        try {
            CidsBeanSupport.deleteStationIfExists(cidsBean, "stat_bis", beansToDelete);
            cidsBean.setProperty("stat_bis", null);
        } catch (Exception e) {
            log.error("Cannot delete cids bean.", e);
        }

        //delete real_geom if exists
        try {
            CidsBeanSupport.deletePropertyIfExists(cidsBean, "real_geom", beansToDelete);
            cidsBean.setProperty("real_geom", null);
        } catch (Exception e) {
            log.error("Cannot delete cids bean.", e);
        }

        //delete additional_geom if exists
        try {
            CidsBeanSupport.deletePropertyIfExists(cidsBean, "additional_geom", beansToDelete);
            cidsBean.setProperty("additional_geom", null);
        } catch (Exception e) {
            log.error("Cannot delete cids bean.", e);
        }

        //todo: fldnr bestimmen
        int lfdnr = 1;

        try {
            cidsBean.setProperty("massn_wk_lfdnr", BigDecimal.valueOf(lfdnr) );
            cidsBean.setProperty("massn_id", getWk_k() + "M_" + lfdnr);
        } catch (Exception e) {
            log.error(e,e);
        }

        // copy new geometries
        if (cidsBean != null && cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            CidsBean wk_sg = (CidsBean)cidsBean.getProperty(WB_PROPERTIES[1]);

            try {
                CidsBean geom = CidsBeanSupport.cloneCidsBean((CidsBean)wk_sg.getProperty("geom"));
                cidsBean.setProperty("additional_geom", geom);
            } catch (Exception e) {
                log.error("Cannot copy the new geometry.", e);
            }
        } else if (cidsBean != null && cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            //wk_fg
            CidsBean wk_fg = (CidsBean)cidsBean.getProperty(WB_PROPERTIES[0]);
            List<CidsBean> teile = CidsBeanSupport.getBeanCollectionFromProperty(wk_fg, "teile");

            if (teile != null && teile.size() > 0) {
                CidsBean teil = teile.get(0);

                try {
                    CidsBean geom = CidsBeanSupport.cloneCidsBean((CidsBean)teil.getProperty("real_geom"));
                    CidsBean stat_von = CidsBeanSupport.cloneStation((CidsBean)teil.getProperty("von"));
                    CidsBean stat_bis = CidsBeanSupport.cloneStation((CidsBean)teil.getProperty("bis"));
                    cidsBean.setProperty("real_geom", geom);
                    cidsBean.setProperty("stat_von", stat_von);
                    cidsBean.setProperty("stat_bis", stat_bis);

                } catch (Exception e) {
                    log.error("Cannot copy the new geometry.", e);
                }
            }
        }
    }

    private void showOrHideGeometryEditors() {
        if ( cidsBean != null && cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            panGeo.setVisible(false);
            cbGeom.setVisible(false);
            lblGeom.setVisible(false);
        } else {
            panGeo.setVisible(true);
            cbGeom.setVisible(true);
            lblGeom.setVisible(true);
        }
    }
}
