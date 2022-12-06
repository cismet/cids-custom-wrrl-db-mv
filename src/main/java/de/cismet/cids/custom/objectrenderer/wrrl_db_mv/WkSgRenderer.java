/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;

import org.jdesktop.beansbinding.Converter;

import java.awt.Component;

import java.sql.Timestamp;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkFgPanSeven;
import de.cismet.cids.custom.reports.WkSgReport;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkFgMeldeInfosSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.wrrl_db_mv.util.TimestampConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FXWebViewPanel;
import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.TitleComponentProvider;

import static javax.swing.SwingConstants.TOP;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkSgRenderer extends JPanel implements CidsBeanRenderer, FooterComponentProvider, TitleComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(WkSgRenderer.class);
    private static final MetaClass AUSNAHME_MC;

    static {
        AUSNAHME_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "EXCEMPTION");
    }
    // private final DefaultComboBoxModel qualityStatusCodeModel;

    //~ Instance fields --------------------------------------------------------

    private boolean showPanMelinf;
    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReport;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.ExcemptionRenderer excemptionEditor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeadingAusnahme;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList lstAusnahmen;
    private javax.swing.JPanel panAllgemeines;
    private javax.swing.JPanel panAusnahmen;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadQuality;
    private javax.swing.JPanel panMelInf;
    private javax.swing.JPanel panMeld;
    private javax.swing.JPanel panQualitaet;
    private de.cismet.tools.gui.RoundedPanel panQuality;
    private javax.swing.JPanel panQualityContent;
    private javax.swing.JPanel panRisiken;
    private javax.swing.JLabel panSpace;
    private javax.swing.JLabel panSpace1;
    private javax.swing.JPanel panTitle;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private javax.swing.JScrollPane scpAusnahmen;
    private org.jdesktop.swingx.JXTable tabPressure;
    private javax.swing.JTabbedPane tpMain;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanEight wkSgPanEight;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanFive wkSgPanFive;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanFour wkSgPanFour;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanNine wkSgPanNine;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanOne wkSgPanOne;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanSeven wkSgPanSeven;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanSix wkSgPanSix;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanThree wkSgPanThree;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanTwo wkSgPanTwo;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public WkSgRenderer() {
        showPanMelinf =
            SessionManager.getSession().getUser().getUserGroup().getName().equalsIgnoreCase("administratoren")
                    || SessionManager.getSession()
                    .getUser()
                    .getUserGroup()
                    .getName()
                    .toLowerCase()
                    .startsWith("stalu");
        initComponents();
        btnReport.setVisible(false);
        panRisiken.setVisible(false);
        panQualitaet.setVisible(false);
        panMeld.setVisible(false);
        panAusnahmen.setVisible(false);
        tpMain.remove(panRisiken);
        tpMain.remove(panQualitaet);
        tpMain.remove(panMeld);
        tpMain.remove(panAusnahmen);
        tpMain.setUI(new TabbedPaneUITransparent());
        jScrollPane1.setVisible(true);
        tabPressure.setRowHeight(75); // 55
        tabPressure.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    setVerticalAlignment(TOP);
                    final Component c = super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column);
                    if (c instanceof JLabel) {
                        ((JLabel)c).setText("<html>" + ((JLabel)c).getText() + "</html>");
                        ((JLabel)c).setToolTipText(
                            "<html>"
                                    + WkFgPanSeven.wrapText(String.valueOf(value), 50)
                                    + "</html>");
                    }
                    return c;
                }
            });
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
            wkSgPanOne.setCidsBean(cidsBean);
            wkSgPanTwo.setCidsBean(cidsBean);
            wkSgPanThree.setCidsBean(cidsBean);
            wkSgPanFour.setCidsBean(cidsBean);
            wkSgPanFive.setCidsBean(cidsBean);
            wkSgPanSix.setCidsBean(cidsBean);
            wkSgPanSeven.setCidsBean(cidsBean);
            wkSgPanEight.setCidsBean(cidsBean);
            wkSgPanNine.setCidsBean(cidsBean);
            bindingGroup.bind();
            lstAusnahmen.setSelectedIndex((lstAusnahmen.getModel().getSize() == 0) ? -1 : 0);
            Object avUser = cidsBean.getProperty("av_user");
            Object avTime = cidsBean.getProperty("av_date");

            if (avUser == null) {
                avUser = "(unbekannt)";
            }
            if (avTime instanceof Timestamp) {
                avTime = TimestampConverter.getInstance().convertForward((Timestamp)avTime);
            } else {
                avTime = "(unbekannt)";
            }

            lblFoot.setText("Zuletzt bearbeitet von " + avUser + " am " + avTime);

            if (showPanMelinf) {
                final Thread t = new Thread("retrieveAnhData") {

                        @Override
                        public void run() {
                            try {
                                final CidsServerSearch anhoerungInfo = new WkFgMeldeInfosSearch((String)
                                        cidsBean.getProperty(
                                            "wk_k"));
                                final ArrayList<ArrayList> infos = (ArrayList<ArrayList>)SessionManager
                                            .getProxy()
                                            .customServerSearch(SessionManager.getSession().getUser(), anhoerungInfo);
                                int currentRow = 0;
                                tabPressure.setModel(new WkFgPanSeven.CustomTableModel(infos));

                                for (final ArrayList row : infos) {
                                    int maxLength = 0;
                                    for (final Object col : row) {
                                        if (String.valueOf(col).length() > maxLength) {
                                            maxLength = String.valueOf(col).length();
                                        }
                                    }

                                    if (maxLength > 44) {
                                        tabPressure.setRowHeight(currentRow, maxLength / 22 * 20);
                                    }

                                    currentRow++;
                                }
                            } catch (Exception e) {
                                log.error("Error while retrieving anhörungs infos", e);
                            }
                        }
                    };

                t.start();
            } else {
                tabPressure.setModel(new WkFgPanSeven.CustomTableModel(new ArrayList<ArrayList>()));
            }
        } else {
            lblFoot.setText("");
            tabPressure.setModel(new WkFgPanSeven.CustomTableModel(new ArrayList<ArrayList>()));
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
        wkSgPanOne = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanOne();
        panRisiken = new javax.swing.JPanel();
        wkSgPanSeven = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanSeven();
        wkSgPanSix = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanSix();
        panQualitaet = new javax.swing.JPanel();
        wkSgPanTwo = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanTwo();
        wkSgPanThree = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanThree();
        wkSgPanFour = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanFour();
        wkSgPanFive = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanFive();
        panSpace = new javax.swing.JLabel();
        wkSgPanEight = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanEight();
        panMeld = new javax.swing.JPanel();
        wkSgPanNine = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.WkSgPanNine();
        panAusnahmen = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        excemptionEditor = new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.ExcemptionRenderer();
        roundedPanel1 = new de.cismet.tools.gui.RoundedPanel();
        scpAusnahmen = new javax.swing.JScrollPane();
        lstAusnahmen = new javax.swing.JList();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeadingAusnahme = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        if (showPanMelinf) {
            panMelInf = new javax.swing.JPanel();
        }
        panQuality = new de.cismet.tools.gui.RoundedPanel();
        panHeadQuality = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panQualityContent = new javax.swing.JPanel();
        lblSpace = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabPressure = new org.jdesktop.swingx.JXTable();
        panSpace1 = new javax.swing.JLabel();

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

        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean}"),
                lblTitle,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new CidsbeanToStringConverter());
        bindingGroup.addBinding(binding);

        btnReport.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wrrl_db_mv/printer.png")));         // NOI18N
        btnReport.setText(org.openide.util.NbBundle.getMessage(
                WkSgRenderer.class,
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
                    446,
                    Short.MAX_VALUE).addComponent(btnReport)));
        panTitleLayout.setVerticalGroup(
            panTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lblTitle)
                        .addComponent(btnReport));

        setLayout(new java.awt.BorderLayout());

        panAllgemeines.setOpaque(false);
        panAllgemeines.setLayout(new java.awt.GridBagLayout());

        wkSgPanOne.setMinimumSize(new java.awt.Dimension(1015, 550));
        wkSgPanOne.setPreferredSize(new java.awt.Dimension(1015, 550));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panAllgemeines.add(wkSgPanOne, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                WkSgRenderer.class,
                "WkSgRenderer.panAllgemeines.TabConstraints.tabTitle",
                new Object[] {}),
            panAllgemeines); // NOI18N

        panRisiken.setOpaque(false);
        panRisiken.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 10, 0);
        panRisiken.add(wkSgPanSeven, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panRisiken.add(wkSgPanSix, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                WkSgRenderer.class,
                "WkSgRenderer.panRisiken.TabConstraints.tabTitle",
                new Object[] {}),
            panRisiken); // NOI18N

        panQualitaet.setOpaque(false);
        panQualitaet.setLayout(new java.awt.GridBagLayout());

        wkSgPanTwo.setMinimumSize(new java.awt.Dimension(520, 250));
        wkSgPanTwo.setPreferredSize(new java.awt.Dimension(520, 250));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        panQualitaet.add(wkSgPanTwo, gridBagConstraints);

        wkSgPanThree.setMinimumSize(new java.awt.Dimension(450, 320));
        wkSgPanThree.setPreferredSize(new java.awt.Dimension(450, 320));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        panQualitaet.add(wkSgPanThree, gridBagConstraints);

        wkSgPanFour.setMinimumSize(new java.awt.Dimension(450, 300));
        wkSgPanFour.setPreferredSize(new java.awt.Dimension(450, 300));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        panQualitaet.add(wkSgPanFour, gridBagConstraints);

        wkSgPanFive.setMinimumSize(new java.awt.Dimension(450, 250));
        wkSgPanFive.setPreferredSize(new java.awt.Dimension(450, 250));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        panQualitaet.add(wkSgPanFive, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1.0;
        panQualitaet.add(panSpace, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 10, 0);
        panQualitaet.add(wkSgPanEight, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                WkSgRenderer.class,
                "WkSgRenderer.panQualitaet.TabConstraints.tabTitle",
                new Object[] {}),
            panQualitaet); // NOI18N

        panMeld.setOpaque(false);
        panMeld.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panMeld.add(wkSgPanNine, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                WkSgRenderer.class,
                "WkSgRenderer.panMeld.TabConstraints.tabTitle",
                new Object[] {}),
            panMeld); // NOI18N

        panAusnahmen.setOpaque(false);
        panAusnahmen.setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 0);
        jPanel1.add(excemptionEditor, gridBagConstraints);

        roundedPanel1.setMinimumSize(new java.awt.Dimension(500, 229));
        roundedPanel1.setPreferredSize(new java.awt.Dimension(500, 229));
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
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        roundedPanel1.add(scpAusnahmen, gridBagConstraints);

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeadingAusnahme.setForeground(new java.awt.Color(255, 255, 255));
        lblHeadingAusnahme.setText(org.openide.util.NbBundle.getMessage(
                WkSgRenderer.class,
                "WkFgPanOne.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeadingAusnahme);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        roundedPanel1.add(panHeadInfo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 10);
        jPanel1.add(roundedPanel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel1, gridBagConstraints);

        panAusnahmen.add(jPanel1, java.awt.BorderLayout.CENTER);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                WkSgRenderer.class,
                "WkSgRenderer.panAusnahmen.TabConstraints.tabTitle",
                new Object[] {}),
            panAusnahmen); // NOI18N

        if (showPanMelinf) {
            panMelInf.setOpaque(false);
            panMelInf.setLayout(new java.awt.GridBagLayout());

            panHeadQuality.setBackground(new java.awt.Color(51, 51, 51));
            panHeadQuality.setMinimumSize(new java.awt.Dimension(109, 24));
            panHeadQuality.setPreferredSize(new java.awt.Dimension(109, 24));
            panHeadQuality.setLayout(new java.awt.FlowLayout());

            lblHeading.setForeground(new java.awt.Color(255, 255, 255));
            lblHeading.setText(org.openide.util.NbBundle.getMessage(
                    WkSgRenderer.class,
                    "WkSgRenderer.lblHeading.text",
                    new Object[] {})); // NOI18N
            panHeadQuality.add(lblHeading);

            panQuality.add(panHeadQuality, java.awt.BorderLayout.NORTH);

            panQualityContent.setMinimumSize(new java.awt.Dimension(1100, 260));
            panQualityContent.setOpaque(false);
            panQualityContent.setPreferredSize(new java.awt.Dimension(1100, 260));
            panQualityContent.setLayout(new java.awt.GridBagLayout());
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 8;
            panQualityContent.add(lblSpace, gridBagConstraints);

            jScrollPane1.setViewportView(tabPressure);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
            panQualityContent.add(jScrollPane1, gridBagConstraints);

            panQuality.add(panQualityContent, java.awt.BorderLayout.CENTER);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
            panMelInf.add(panQuality, gridBagConstraints);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.weighty = 1.0;
            panMelInf.add(panSpace1, gridBagConstraints);

            tpMain.addTab(org.openide.util.NbBundle.getMessage(
                    WkSgRenderer.class,
                    "WkSgRenderer.panMelInf.TabConstraints.tabTitle",
                    new Object[] {}),
                panMelInf); // NOI18N
        }

        add(tpMain, java.awt.BorderLayout.PAGE_START);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

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
    private void btnReportActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnReportActionPerformed
        WkSgReport.showReport(cidsBean);
    }                                                                             //GEN-LAST:event_btnReportActionPerformed

    @Override
    public void dispose() {
        wkSgPanOne.dispose();
        wkSgPanTwo.dispose();
        wkSgPanThree.dispose();
        wkSgPanFour.dispose();
        wkSgPanFive.dispose();
        excemptionEditor.dispose();
        wkSgPanSix.dispose();
        wkSgPanSeven.dispose();
        wkSgPanEight.dispose();
        wkSgPanNine.dispose();
        bindingGroup.unbind();
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
    public JComponent getFooterComponent() {
        return panFooter;
    }

    @Override
    public JComponent getTitleComponent() {
        return panTitle;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class CidsbeanToStringConverter extends Converter<CidsBean, String> {

        //~ Methods ------------------------------------------------------------

        @Override
        public String convertForward(final CidsBean s) {
            return getTitle();
        }

        @Override
        public CidsBean convertReverse(final String t) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
