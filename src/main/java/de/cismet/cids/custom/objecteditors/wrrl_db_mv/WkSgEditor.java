/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;

import java.awt.Component;

import java.sql.Timestamp;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkFgMeldeInfosSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.wrrl_db_mv.util.TimestampConverter;
import de.cismet.cids.custom.wrrl_db_mv.util.WrrlEditorTester;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;

import static javax.swing.SwingConstants.TOP;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkSgEditor extends JPanel implements CidsBeanRenderer, EditorSaveListener, FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(WkSgEditor.class);
    private static final MetaClass AUSNAHME_MC;

    static {
        AUSNAHME_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "EXCEMPTION");
    }
    // private final DefaultComboBoxModel qualityStatusCodeModel;

    //~ Instance fields --------------------------------------------------------

    private boolean showPanMelinf;
    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JPanel panAllgemeines;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadQuality;
    private javax.swing.JPanel panMelInf;
    private de.cismet.tools.gui.RoundedPanel panQuality;
    private javax.swing.JPanel panQualityContent;
    private javax.swing.JLabel panSpace1;
    private org.jdesktop.swingx.JXTable tabPressure;
    private javax.swing.JTabbedPane tpMain;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanOne wkSgPanOne;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public WkSgEditor() {
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
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            wkSgPanOne.setCidsBean(cidsBean);
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

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        tpMain = new javax.swing.JTabbedPane();
        panAllgemeines = new javax.swing.JPanel();
        wkSgPanOne = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkSgPanOne();
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

        setMinimumSize(new java.awt.Dimension(1200, 736));
        setPreferredSize(new java.awt.Dimension(1200, 736));
        setLayout(new java.awt.BorderLayout());

        panAllgemeines.setOpaque(false);
        panAllgemeines.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panAllgemeines.add(wkSgPanOne, gridBagConstraints);

        tpMain.addTab("Allgemeines", panAllgemeines);

        if (showPanMelinf) {
            panMelInf.setOpaque(false);
            panMelInf.setLayout(new java.awt.GridBagLayout());

            panHeadQuality.setBackground(new java.awt.Color(51, 51, 51));
            panHeadQuality.setMinimumSize(new java.awt.Dimension(109, 24));
            panHeadQuality.setPreferredSize(new java.awt.Dimension(109, 24));
            panHeadQuality.setLayout(new java.awt.FlowLayout());

            lblHeading.setForeground(new java.awt.Color(255, 255, 255));
            lblHeading.setText("Anhörung");
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

            tpMain.addTab("Anhörung", panMelInf);
        }

        add(tpMain, java.awt.BorderLayout.PAGE_START);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public void dispose() {
        wkSgPanOne.dispose();
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
        // TODO ?
    }

    @Override
    public boolean prepareForSave() {
        if (cidsBean != null) {
            try {
                cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());
                cidsBean.setProperty("av_date", new java.sql.Timestamp(System.currentTimeMillis()));
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

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        new WrrlEditorTester("WK_SG", WkSgEditor.class, WRRLUtil.DOMAIN_NAME).run();
    }
}
