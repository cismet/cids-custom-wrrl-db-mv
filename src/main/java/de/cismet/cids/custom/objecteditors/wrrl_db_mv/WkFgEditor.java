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

import Sirius.server.middleware.types.MetaObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.reports.WkFgReport;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkkUniqueSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.MapUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureGroup;
import de.cismet.cismap.commons.features.FeatureGroups;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.CidsFeature;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.TitleComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class WkFgEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    TitleComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkFgEditor.class);
    public static final String PROP_WKFG_WKTEILE = "teile";
    public static final String MC_WKTEIL = "wk_teil";             // NOI18N
    public static final String PROP_WKTEIL_STATIONLINE = "linie"; // NOI18N
    private static final MappingComponent MAPPING_COMPONENT = CismapBroker.getInstance().getMappingComponent();
    public static final ConnectionContext CONNECTION_CONTEXT = ConnectionContext.create(
            AbstractConnectionContext.Category.EDITOR,
            "WkFgEditor");

    // private final DefaultComboBoxModel qualityStatusCodeModel;

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean showPanMelinf;
    private boolean readOnly;
//    private org.xhtmlrenderer.simple.XHTMLPanel xHTMLPanel1 = new FSXHtmlPanel();
    private org.jdesktop.beansbinding.Binding binding;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAusnahme;
    private javax.swing.JButton btnRemAusnahme;
    private javax.swing.JButton btnReport;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.ExcemptionEditor excemptionEditor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList lstAusnahmen;
    private javax.swing.JPanel panAllgemeines;
    private javax.swing.JPanel panAusnahmen;
    private javax.swing.JPanel panBioQk;
    private javax.swing.JPanel panChemZust;
    private javax.swing.JPanel panContrAusnahmen;
    private javax.swing.JPanel panFooter;
    private javax.swing.JPanel panGeo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private javax.swing.JPanel panHydroQk;
    private javax.swing.JPanel panLawa;
    private javax.swing.JPanel panMelInf;
    private javax.swing.JPanel panOekoZd;
    private javax.swing.JPanel panPhysChemQk;
    private javax.swing.JLabel panSpace;
    private javax.swing.JPanel panTitle;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private javax.swing.JScrollPane scpAusnahmen;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineArrayEditor teileEditor;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineArrayRenderer teileRenderer;
    private javax.swing.JTabbedPane tpMain;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanEleven wkFgPanEleven1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanFourteen wkFgPanFourteen1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanOne wkFgPanOne;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanSeven wkFgPanSeven1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanSix wkFgPanSix;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanTen wkFgPanTen;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanThirteen wkFgPanThirteen1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanTwelve wkFgPanTwelve1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WkFgEditor object.
     */
    public WkFgEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkFgEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        showPanMelinf =
            SessionManager.getSession().getUser().getUserGroup().getName().equalsIgnoreCase("administratoren")
                    || SessionManager.getSession()
                    .getUser()
                    .getUserGroup()
                    .getName()
                    .toLowerCase()
                    .startsWith("stalu");
        initComponents();

        tpMain.setUI(new TabbedPaneUITransparent());

        if (!readOnly) {
            teileEditor.setOtherLinesEnabled(true);
            teileEditor.setFields(
                MC_WKTEIL,
                PROP_WKFG_WKTEILE,
                PROP_WKTEIL_STATIONLINE);

            teileEditor.setOtherLinesQueryAddition(
                "wk_fg, wk_fg_teile, wk_teil",
                "wk_fg.id = wk_fg_teile.wk_fg_reference AND wk_fg_teile.teil = wk_teil.id AND wk_teil.linie = ");
        } else {
            teileRenderer.setFields(
                MC_WKTEIL,
                PROP_WKFG_WKTEILE,
                PROP_WKTEIL_STATIONLINE);
            panContrAusnahmen.setVisible(false);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        if (!readOnly) {
            MapUtil.zoomToFeatureCollection(teileEditor.getZoomFeatures());
        }
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
            "wk_fg",
            476,
            1280,
            1024);
        // new WrrlEditorTester("wk_fg", WkFgEditor.class, WRRLUtil.DOMAIN_NAME).run();
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        // cidsFeature rausschmeissen
        if (!readOnly) {
            final CidsFeature cidsFeature = new CidsFeature(cidsBean.getMetaObject());
            final Collection<Feature> features = new ArrayList<Feature>();
            features.addAll(FeatureGroups.expandAll((FeatureGroup)cidsFeature));
            MAPPING_COMPONENT.getFeatureCollection().removeFeatures(features);
        }

        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            wkFgPanOne.setCidsBean(cidsBean);
            wkFgPanTen.setCidsBean(cidsBean);
            wkFgPanEleven1.setCidsBean(cidsBean);
            wkFgPanSeven1.setCidsBean(cidsBean);
            wkFgPanTwelve1.setCidsBean(cidsBean);
            if (wkFgPanFourteen1 != null) {
                wkFgPanFourteen1.setCidsBean(cidsBean);
            }
            wkFgPanSix.setCidsBean(cidsBean);
            wkFgPanThirteen1.setCidsBean(cidsBean);
            bindingGroup.bind();
            lstAusnahmen.setSelectedIndex((lstAusnahmen.getModel().getSize() == 0) ? -1 : 0);
            UIUtil.setLastModifier(cidsBean, lblFoot);
            zoomToFeatures();
        } else {
            lblFoot.setText("");
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
        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        btnReport = new javax.swing.JButton();
        tpMain = new javax.swing.JTabbedPane();
        panAllgemeines = new javax.swing.JPanel();
        wkFgPanOne = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanOne(readOnly);
        panOekoZd = new javax.swing.JPanel();
        wkFgPanTwelve1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanTwelve(readOnly);
        panBioQk = new javax.swing.JPanel();
        wkFgPanTen = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanTen(readOnly);
        panPhysChemQk = new javax.swing.JPanel();
        wkFgPanSix = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanSix(readOnly);
        panHydroQk = new javax.swing.JPanel();
        wkFgPanEleven1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanEleven(readOnly);
        panChemZust = new javax.swing.JPanel();
        wkFgPanThirteen1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanThirteen(readOnly);
        if (!readOnly) {
            panLawa = new javax.swing.JPanel();
        }
        if (!readOnly) {
            wkFgPanFourteen1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanFourteen();
        }
        if (showPanMelinf) {
            panMelInf = new javax.swing.JPanel();
        }
        panSpace = new javax.swing.JLabel();
        wkFgPanSeven1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanSeven(readOnly);
        panAusnahmen = new javax.swing.JPanel();
        excemptionEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.ExcemptionEditor(readOnly);
        roundedPanel1 = new de.cismet.tools.gui.RoundedPanel();
        scpAusnahmen = new javax.swing.JScrollPane();
        lstAusnahmen = new javax.swing.JList();
        panContrAusnahmen = new javax.swing.JPanel();
        btnAddAusnahme = new javax.swing.JButton();
        btnRemAusnahme = new javax.swing.JButton();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panGeo = new javax.swing.JPanel();
        if (!readOnly) {
            teileEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineArrayEditor();
        }
        if (readOnly) {
            teileRenderer = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineArrayRenderer();
        }
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        panTitle.setOpaque(false);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));

        btnReport.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wrrl_db_mv/printer.png")));         // NOI18N
        btnReport.setText(org.openide.util.NbBundle.getMessage(
                WkFgEditor.class,
                "FotodokumentationRenderer.btnReport.text"));                                                     // NOI18N
        btnReport.setBorder(null);
        btnReport.setBorderPainted(false);
        btnReport.setContentAreaFilled(false);
        btnReport.setFocusPainted(false);
        btnReport.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wrrl_db_mv/printer_pressed.png"))); // NOI18N
        btnReport.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnReportActionPerformed(evt);
                }
            });

        final javax.swing.GroupLayout panTitleLayout = new javax.swing.GroupLayout(panTitle);
        panTitle.setLayout(panTitleLayout);
        panTitleLayout.setHorizontalGroup(
            panTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panTitleLayout.createSequentialGroup().addContainerGap().addComponent(lblTitle).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                    232,
                    Short.MAX_VALUE).addComponent(btnReport)));
        panTitleLayout.setVerticalGroup(
            panTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lblTitle)
                        .addComponent(btnReport));

        setLayout(new java.awt.BorderLayout());

        panAllgemeines.setOpaque(false);
        panAllgemeines.setLayout(new java.awt.GridBagLayout());

        wkFgPanOne.setMaximumSize(new java.awt.Dimension(1150, 550));
        wkFgPanOne.setMinimumSize(new java.awt.Dimension(1150, 550));
        wkFgPanOne.setPreferredSize(new java.awt.Dimension(1150, 550));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panAllgemeines.add(wkFgPanOne, gridBagConstraints);

        tpMain.addTab("Allgemeines", panAllgemeines);

        panOekoZd.setOpaque(false);
        panOekoZd.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panOekoZd.add(wkFgPanTwelve1, gridBagConstraints);

        tpMain.addTab("Ökologischer Zustand", panOekoZd);

        panBioQk.setOpaque(false);
        panBioQk.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panBioQk.add(wkFgPanTen, gridBagConstraints);

        tpMain.addTab("Biologische QK", panBioQk);

        panPhysChemQk.setMinimumSize(new java.awt.Dimension(910, 930));
        panPhysChemQk.setOpaque(false);
        panPhysChemQk.setPreferredSize(new java.awt.Dimension(910, 930));
        panPhysChemQk.setLayout(new java.awt.GridBagLayout());

        wkFgPanSix.setMinimumSize(new java.awt.Dimension(875, 100));
        wkFgPanSix.setPreferredSize(new java.awt.Dimension(875, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panPhysChemQk.add(wkFgPanSix, gridBagConstraints);

        tpMain.addTab("Physikalisch-chemische QK", panPhysChemQk);

        panHydroQk.setMinimumSize(new java.awt.Dimension(910, 650));
        panHydroQk.setOpaque(false);
        panHydroQk.setPreferredSize(new java.awt.Dimension(910, 650));
        panHydroQk.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panHydroQk.add(wkFgPanEleven1, gridBagConstraints);

        tpMain.addTab("Hydromorphologische QK", panHydroQk);

        panChemZust.setMinimumSize(new java.awt.Dimension(910, 650));
        panChemZust.setOpaque(false);
        panChemZust.setPreferredSize(new java.awt.Dimension(910, 650));
        panChemZust.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panChemZust.add(wkFgPanThirteen1, gridBagConstraints);

        tpMain.addTab("Chemischer Zustand", panChemZust);

        if (!readOnly) {
            panLawa.setMinimumSize(new java.awt.Dimension(910, 650));
            panLawa.setOpaque(false);
            panLawa.setPreferredSize(new java.awt.Dimension(910, 650));
            panLawa.setLayout(new java.awt.GridBagLayout());
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
            panLawa.add(wkFgPanFourteen1, gridBagConstraints);

            tpMain.addTab("LAWA-Typen", panLawa);
        }

        if (showPanMelinf) {
            panMelInf.setOpaque(false);
            panMelInf.setLayout(new java.awt.GridBagLayout());
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.weighty = 1.0;
            panMelInf.add(panSpace, gridBagConstraints);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
            panMelInf.add(wkFgPanSeven1, gridBagConstraints);

            tpMain.addTab("Anhörung", panMelInf);
        }

        panAusnahmen.setOpaque(false);
        panAusnahmen.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 0);
        panAusnahmen.add(excemptionEditor, gridBagConstraints);

        roundedPanel1.setMinimumSize(new java.awt.Dimension(450, 292));
        roundedPanel1.setPreferredSize(new java.awt.Dimension(500, 292));
        roundedPanel1.setLayout(new java.awt.GridBagLayout());

        scpAusnahmen.setMinimumSize(new java.awt.Dimension(350, 175));
        scpAusnahmen.setPreferredSize(new java.awt.Dimension(350, 175));

        lstAusnahmen.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        final org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.ausnahmen}");
        final org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstAusnahmen);
        bindingGroup.addBinding(jListBinding);

        lstAusnahmen.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    lstAusnahmenValueChanged(evt);
                }
            });
        scpAusnahmen.setViewportView(lstAusnahmen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(25, 15, 15, 15);
        roundedPanel1.add(scpAusnahmen, gridBagConstraints);

        panContrAusnahmen.setOpaque(false);
        panContrAusnahmen.setLayout(new java.awt.GridBagLayout());

        btnAddAusnahme.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddAusnahme.setText(org.openide.util.NbBundle.getMessage(
                WkFgEditor.class,
                "WkFgPanOne.btnAddImpactSrc.text"));                                                           // NOI18N
        btnAddAusnahme.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddAusnahmeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrAusnahmen.add(btnAddAusnahme, gridBagConstraints);

        btnRemAusnahme.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemAusnahme.setText(org.openide.util.NbBundle.getMessage(
                WkFgEditor.class,
                "WkFgPanOne.btnRemImpactSrc.text"));                                                              // NOI18N
        btnRemAusnahme.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemAusnahmeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrAusnahmen.add(btnRemAusnahme, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        roundedPanel1.add(panContrAusnahmen, gridBagConstraints);

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ausnahmen");
        panHeadInfo.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        roundedPanel1.add(panHeadInfo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 10);
        panAusnahmen.add(roundedPanel1, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        panAusnahmen.add(jPanel1, gridBagConstraints);

        tpMain.addTab("Ausnahmen", panAusnahmen);

        panGeo.setOpaque(false);
        panGeo.setLayout(new java.awt.GridBagLayout());

        if (!readOnly) {
            final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                    org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                    this,
                    org.jdesktop.beansbinding.ELProperty.create("${cidsBean}"),
                    teileEditor,
                    org.jdesktop.beansbinding.BeanProperty.create("cidsBean"));
            bindingGroup.addBinding(binding);
        }
        if (!readOnly) {
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
            panGeo.add(teileEditor, gridBagConstraints);
        }

        if (readOnly) {
            binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                    org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                    this,
                    org.jdesktop.beansbinding.ELProperty.create("${cidsBean}"),
                    teileRenderer,
                    org.jdesktop.beansbinding.BeanProperty.create("cidsBean"));
            bindingGroup.addBinding(binding);
        }
        if (readOnly) {
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
            panGeo.add(teileRenderer, gridBagConstraints);
        }

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panGeo.add(jPanel2, gridBagConstraints);

        jPanel3.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.6;
        panGeo.add(jPanel3, gridBagConstraints);

        jPanel4.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.6;
        panGeo.add(jPanel4, gridBagConstraints);

        tpMain.addTab("Geometrie", panGeo);

        add(tpMain, java.awt.BorderLayout.PAGE_START);
        tpMain.getAccessibleContext().setAccessibleName("Qualitaetsinformationen 1");

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddAusnahmeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddAusnahmeActionPerformed
        try {
            final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName("EXCEMPTION");
            final Collection<CidsBean> excemptionCollection = CidsBeanSupport.getBeanCollectionFromProperty(
                    cidsBean,
                    "ausnahmen");
            excemptionCollection.add(newBean);
        } catch (Exception ex) {
            LOG.error(ex, ex);
        }
    }                                                                                  //GEN-LAST:event_btnAddAusnahmeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstAusnahmenValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_lstAusnahmenValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object selObj = lstAusnahmen.getSelectedValue();
            if (selObj instanceof CidsBean) {
                excemptionEditor.setCidsBean((CidsBean)selObj);
            }
        }
    }                                                                                       //GEN-LAST:event_lstAusnahmenValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemAusnahmeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemAusnahmeActionPerformed
        final Object selection = lstAusnahmen.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll die Ausnahme wirklich gelöscht werden?",
                    "Ausnahme entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("ausnahmen");
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                    excemptionEditor.setCidsBean(null);
                } catch (Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                  //GEN-LAST:event_btnRemAusnahmeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnReportActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnReportActionPerformed
        WkFgReport.showReport(cidsBean);
    }                                                                             //GEN-LAST:event_btnReportActionPerformed

    @Override
    public void dispose() {
        wkFgPanOne.dispose();
        wkFgPanSix.dispose();
        wkFgPanTen.dispose();
        wkFgPanEleven1.dispose();
        wkFgPanTwelve1.dispose();
        wkFgPanThirteen1.dispose();
        wkFgPanSeven1.dispose();
        excemptionEditor.dispose();
        if (teileEditor != null) {
            teileEditor.dispose();
        }
        if (teileRenderer != null) {
            teileRenderer.dispose();
        }
        if (wkFgPanFourteen1 != null) {
            wkFgPanFourteen1.dispose();
        }
        bindingGroup.unbind();
    }

    @Override
    public JComponent getTitleComponent() {
        if (readOnly) {
            return panTitle;
        } else {
            final JPanel panel = new JPanel();

            panel.setOpaque(false);
            return panel;
        }
    }

    @Override
    public String getTitle() {
        return "Wasserkörper "
                    + String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        if (teileEditor != null) {
            teileEditor.editorClosed(event);
        }
        if (teileRenderer != null) {
            teileRenderer.editorClosed(event);
        }
    }

    @Override
    public boolean prepareForSave() {
        if (cidsBean != null) {
            cidsBean.getMetaObject().setAllClasses();
            try {
                cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());
                cidsBean.setProperty("av_time", new java.sql.Timestamp(System.currentTimeMillis()));
            } catch (Exception ex) {
                LOG.error(ex, ex);
            }
        }

        boolean save = true;

        if (!isWkkUnique()) {
            JOptionPane.showMessageDialog(
                StaticSwingTools.getParentFrame(this),
                org.openide.util.NbBundle.getMessage(
                    WkFgEditor.class,
                    "WkFgEditor.prepareForSave().wkkNotUnique.message"),
                org.openide.util.NbBundle.getMessage(
                    WkFgEditor.class,
                    "WkFgEditor.prepareForSave().wkkNotUnique.title"),
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        final List<CidsBean> teile = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "teile");

        if (teile != null) {
            for (final CidsBean teil : teile) {
                final CidsBean line = (CidsBean)teil.getProperty("linie");

                if (line != null) {
                    CidsBean station = (CidsBean)line.getProperty("von");

                    if ((station != null) && (station.getMetaObject().getStatus() == MetaObject.MODIFIED)) {
                        line.getMetaObject().getAttribute("von").setChanged(true);
                    }

                    station = (CidsBean)line.getProperty("bis");

                    if ((station != null) && (station.getMetaObject().getStatus() == MetaObject.MODIFIED)) {
                        line.getMetaObject().getAttribute("bis").setChanged(true);
                    }
                }
            }
        }

        final CidsBean benInv = (CidsBean)cidsBean.getProperty("ben_inv");
        final CidsBean macPhyto = (CidsBean)cidsBean.getProperty("mac_phyto");
        final CidsBean phyto = (CidsBean)cidsBean.getProperty("phyto");
        final CidsBean fish = (CidsBean)cidsBean.getProperty("fish");

        if ((benInv != null) && hasValueBetween1AndFive((String)benInv.getProperty("value"))) {
            if ((cidsBean.getProperty("ben_inv_gk_mst") == null) || cidsBean.getProperty("ben_inv_gk_mst").equals("")) {
                JOptionPane.showMessageDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Es wurde zwar eine Güteklasse für Makrozoobenthos gesetzt, aber keine Messstelle.",
                    "Messstelle fehlt",
                    JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }
        if ((macPhyto != null) && hasValueBetween1AndFive((String)macPhyto.getProperty("value"))) {
            if ((cidsBean.getProperty("mac_phyto_gk_mst") == null)
                        || cidsBean.getProperty("mac_phyto_gk_mst").equals("")) {
                JOptionPane.showMessageDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Es wurde zwar eine Güteklasse für Makrophyten gesetzt, aber keine Messstelle.",
                    "Messstelle fehlt",
                    JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }
        if ((phyto != null) && hasValueBetween1AndFive((String)phyto.getProperty("value"))) {
            if ((cidsBean.getProperty("phyto_gk_mst") == null) || cidsBean.getProperty("phyto_gk_mst").equals("")) {
                JOptionPane.showMessageDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Es wurde zwar eine Güteklasse für Phytoplankton gesetzt, aber keine Messstelle",
                    "Messstelle fehlt",
                    JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }
        if ((fish != null) && hasValueBetween1AndFive((String)fish.getProperty("value"))) {
            if ((cidsBean.getProperty("fish_gk_mst") == null) || cidsBean.getProperty("fish_gk_mst").equals("")) {
                JOptionPane.showMessageDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Es wurde zwar eine Güteklasse für Fische gesetzt, aber keine Messstelle",
                    "Messstelle fehlt",
                    JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }

        save &= teileEditor.prepareForSave();
        return save;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   val  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean hasValueBetween1AndFive(final String val) {
        return val.equals("1")
                    || val.equals("2")
                    || val.equals("3")
                    || val.equals("4")
                    || val.equals("5");
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isWkkUnique() {
        try {
            final CidsServerSearch search = new WkkUniqueSearch(String.valueOf(cidsBean.getProperty("wk_k")),
                    String.valueOf(cidsBean.getProperty("id")));
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

            return ((resArray == null) || resArray.isEmpty());
        } catch (ConnectionException e) {
            LOG.error("Error during Server Search", e);
        }

        return true;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }
}
