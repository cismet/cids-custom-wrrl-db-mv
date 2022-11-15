/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * WkFgPanOne.java
 *
 * Created on 04.08.2010, 13:44:05
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.tools.CacheException;
import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.awt.Color;
import java.awt.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class WkGwPanSeven extends javax.swing.JPanel implements DisposableCidsBeanStore, ListSelectionListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkGwPanSeven.class);

    //~ Instance fields --------------------------------------------------------

    private final MstTableModel model = new MstTableModel();
    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jtMstTab1;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblSpace;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkGwMstGueteStammdatenEditor wkGwMstStammdatenEditor1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkGwPanSeven() {
        this(false);
    }

    /**
     * Creates a new WkFgPanSix object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    WkGwPanSeven(final boolean readOnly) {
        initComponents();
        jtMstTab1.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    final Component c = super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column); // To change body of generated methods, choose Tools | Templates.

                    if ((c instanceof JLabel)) {
                        if ((value instanceof String) && (((String)value).length() > 10)) {
                            ((JLabel)c).setToolTipText(String.valueOf(value));
                        } else {
                            ((JLabel)c).setToolTipText(null);
                        }

                        if (!isSelected && (column > 3)) {
                            if ((value != null) && String.valueOf(value).equals("2")) {
                                ((JLabel)c).setBackground(Color.GREEN);
                            } else if ((value != null) && String.valueOf(value).equals("3")) {
                                ((JLabel)c).setBackground(Color.RED);
                            } else {
                                ((JLabel)c).setBackground(Color.WHITE);
                            }
                        } else if (!isSelected) {
                            ((JLabel)c).setBackground(Color.WHITE);
                        }
                    }

                    return c;
                }
            });

        wkGwMstStammdatenEditor1.setCidsBean(getMst(null));
        jScrollPane1.getViewport().setOpaque(false);
        jtMstTab1.getSelectionModel().addListSelectionListener(this);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panInfoContent = new javax.swing.JPanel();
        lblSpace = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtMstTab1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        wkGwMstStammdatenEditor1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkGwMstGueteStammdatenEditor(
                true,
                true);

        setMinimumSize(new java.awt.Dimension(910, 550));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(910, 550));
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Chemisches Monitoring");
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        panInfoContent.setMaximumSize(new java.awt.Dimension(777, 400));
        panInfoContent.setMinimumSize(new java.awt.Dimension(777, 400));
        panInfoContent.setOpaque(false);
        panInfoContent.setPreferredSize(new java.awt.Dimension(777, 400));
        panInfoContent.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(lblSpace, gridBagConstraints);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(800, 100));
        jScrollPane3.setOpaque(false);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(800, 100));

        jtMstTab1.setModel(model);
        jScrollPane3.setViewportView(jtMstTab1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(25, 10, 15, 10);
        panInfoContent.add(jScrollPane3, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Messwerte"));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        wkGwMstStammdatenEditor1.setMinimumSize(new java.awt.Dimension(1400, 650));
        wkGwMstStammdatenEditor1.setPreferredSize(new java.awt.Dimension(1400, 650));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(wkGwMstStammdatenEditor1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        panInfoContent.add(jPanel1, gridBagConstraints);

        jScrollPane1.setViewportView(panInfoContent);

        panInfo.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getTheCurrentlyWorstMeasure() {
        final List<CidsBean> beans = model.getData();
        int latestYear = 0;
        int worstValue = 0;
        CidsBean measure = null;

        for (final CidsBean cbean : beans) {
            final Integer year = (Integer)cbean.getProperty("messjahr");

            if (year != null) {
                if (latestYear == 0) {
                    latestYear = year;
                    final CidsBean tmp = (CidsBean)cbean.getProperty("gk_pc_mst");
                    if ((tmp != null)) {
                        try {
                            final int valTmp = Integer.parseInt((String)tmp.getProperty("value"));

                            if (valTmp < 6) {
                                measure = cbean;
                                worstValue = valTmp;
                            }
                        } catch (final NumberFormatException e) {
                            LOG.error("Field value does not contain a number", e);
                        }
                    }
                } else {
                    if (latestYear == year) {
                        try {
                            final CidsBean qualityTmp = (CidsBean)cbean.getProperty("gk_pc_mst");
                            if (qualityTmp != null) {
                                final int valTmp = Integer.parseInt((String)qualityTmp.getProperty("value"));

                                if ((valTmp > worstValue) && (valTmp < 6)) {
                                    worstValue = valTmp;
                                    measure = cbean;
                                }
                            }
                        } catch (final NumberFormatException e) {
                            LOG.error("Field value does not contain a number", e);
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return measure;
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        if (cidsBean != null) {
            this.cidsBean = cidsBean;
            model.fireTableDataChanged();

            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        model.refreshData(cidsBean);
                    }
                }).start();
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (jtMstTab1.getSelectedRow() > -1) {
                final CidsBean sbean = model.getData().get(jtMstTab1.getSelectedRow());
                wkGwMstStammdatenEditor1.setCidsBean(getMst(sbean));
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static synchronized List<CidsBean> getChemMst(final CidsBean cidsBean) {
        final List<CidsBean> data = new ArrayList<CidsBean>();

        try {
            final MetaClass MC = ClassCacheMultiple.getMetaClass(
                    WRRLUtil.DOMAIN_NAME,
                    "wk_gw_mst_chemie_gk_zeitraum");
            String query = "select " + MC.getID() + ", m." + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
            query += " m";                                                                                      // NOI18N
            query += " WHERE m.wk_k = '" + cidsBean.getProperty("name") + "'";                                  // NOI18N
            query += " order by messstelle";                                                                    // NOI18N

            final MetaObject[] metaObjects = MetaObjectCache.getInstance()
                        .getMetaObjectsByQuery(query, MC, false, WkFgEditor.CONNECTION_CONTEXT);

            for (final MetaObject mo : metaObjects) {
                data.add(mo.getBean());
            }
        } catch (final CacheException e) {
            LOG.error("Error while trying to receive measurements.", e); // NOI18N
        }

        return data;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static synchronized CidsBean getMst(final CidsBean cidsBean) {
        if (cidsBean == null) {
            return null;
        }
        try {
            final MetaClass MC = ClassCacheMultiple.getMetaClass(
                    WRRLUtil.DOMAIN_NAME,
                    "wk_gw_mst_stammdaten");
            String query = "select " + MC.getID() + ", m." + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
            query += " m WHERE m.messstelle = '" + cidsBean.getProperty("messstelle") + "'";                    // NOI18N

            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            if ((metaObjects != null) && (metaObjects.length == 1)) {
                return metaObjects[0].getBean();
            } else {
                return null;
            }
        } catch (final ConnectionException e) {
            LOG.error("Error while trying to receive measurements.", e); // NOI18N
            return null;
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class MstTableModel extends AbstractTableModel {

        //~ Instance fields ----------------------------------------------------

        private String[][] header = {
                { "WK", "wk_k" },                                          // NOI18N
                { "MST", "messstelle" },                                   // NOI18N
                { "Status", "status" },                                    // NOI18N
                { "GK Nitrat", "gk_nitrat" },                              // NOI18N
                { "GK Arsen", "gk_arsen" },                                // NOI18N
                { "GK Cadmium", "gk_cadmium" },                            // NOI18N
                { "GK Blei", "gk_blei" },                                  // NOI18N
                { "GK Quecksilber", "gk_quecksilber" },                    // NOI18N
                { "GK Ammonium", "gk_ammonium" },                          // NOI18N
                { "GK Chlorid", "gk_chlorid" },                            // NOI18N
                { "GK Nitrit", "gk_nitrit" },                              // NOI18N
                { "GK OPO4-P", "gk_orthophosphat_p" },                     // NOI18N
                { "GK Sulfat", "gk_sulfat" },                              // NOI18N
                { "GK Tri-Tetrachlorethen", "gk_sum_tri_tetrachlorethen" } // NOI18N
            };
        private List<CidsBean> data = new Vector<CidsBean>();
        private boolean isInitialised = false;

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            if (!isInitialised) {
                return 1;
            } else {
                return data.size();
            }
        }

        @Override
        public int getColumnCount() {
            return header.length;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            if (columnIndex < header.length) {
                return header[columnIndex][0];
            } else {
                return "";
            }
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            if (!isInitialised) {
                return "lade ...";
            } else if ((rowIndex < data.size()) && (columnIndex < header.length)) {
                final Object value = data.get(rowIndex).getProperty(header[columnIndex][1]);
                if (value != null) {
                    if (value instanceof CidsBean) {
                        return String.valueOf(((CidsBean)value).getProperty("name")); // NOI18N
                    } else {
                        return String.valueOf(value);
                    }
                } else {
                    return "-";                                                       // NOI18N
                }
            } else {
                return "";                                                            // NOI18N
            }
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            // nothing to do, because it is not allowed to modify columns
        }

        /**
         * DOCUMENT ME!
         *
         * @param  cidsBean  DOCUMENT ME!
         */
        public void refreshData(final CidsBean cidsBean) {
            data.clear();
            data.addAll(getChemMst(cidsBean));
            isInitialised = true;
            fireTableDataChanged();
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public List<CidsBean> getData() {
            return data;
        }

        /**
         * DOCUMENT ME!
         */
        public void clearModel() {
            data.clear();
        }
    }
}
