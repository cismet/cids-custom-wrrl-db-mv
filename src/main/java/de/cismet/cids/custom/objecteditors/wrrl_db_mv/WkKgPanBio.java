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
import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.WaitingDialogThread;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class WkKgPanBio extends javax.swing.JPanel implements DisposableCidsBeanStore,
    ListSelectionListener,
    DocumentListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkKgPanBio.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "wk_kg_bio_mst_messungen");

    //~ Instance fields --------------------------------------------------------

    private final MstTableModel model = new MstTableModel();
    private CidsBean cidsBean;
    private int measureNumber = 0;
    private boolean noDocumentUpdate = false;
    private boolean readOnly = false;
    private final List<CidsBean> beansToSave = new ArrayList<CidsBean>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack1;
    private javax.swing.JButton btnForward;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jtMstTab1;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblSpace;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panScr;
    private javax.swing.JTextField txtJahr;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgBioDetailPanel wkKgBioDetailPanel1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkKgPanBio() {
        this(false);
    }

    /**
     * Creates a new WkFgPanSix object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    WkKgPanBio(final boolean readOnly) {
        this.readOnly = readOnly;
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

                        if (!isSelected && (column > 1)) {
                            if ((value != null) && String.valueOf(value).equalsIgnoreCase("ja")) {
                                ((JLabel)c).setBackground(Color.GREEN);
                            } else if ((value != null) && String.valueOf(value).equalsIgnoreCase("nein")) {
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

        wkKgBioDetailPanel1.setCidsBean(getMst(null));
        jScrollPane1.getViewport().setOpaque(false);
        jtMstTab1.getSelectionModel().addListSelectionListener(this);
        txtJahr.getDocument().addDocumentListener(this);
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
        panScr = new javax.swing.JPanel();
        txtJahr = new javax.swing.JTextField();
        btnBack1 = new javax.swing.JButton();
        btnForward = new javax.swing.JButton();
        wkKgBioDetailPanel1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgBioDetailPanel();

        setMinimumSize(new java.awt.Dimension(910, 580));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(910, 580));
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

        jScrollPane3.setMinimumSize(new java.awt.Dimension(800, 250));
        jScrollPane3.setOpaque(false);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(800, 250));

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
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 15, 10);
        panInfoContent.add(jScrollPane3, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Messwerte"));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        panScr.setOpaque(false);
        panScr.setLayout(new java.awt.GridBagLayout());

        txtJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        txtJahr.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(txtJahr, gridBagConstraints);

        btnBack1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left.png")));          // NOI18N
        btnBack1.setBorder(null);
        btnBack1.setBorderPainted(false);
        btnBack1.setContentAreaFilled(false);
        btnBack1.setFocusPainted(false);
        btnBack1.setMaximumSize(new java.awt.Dimension(30, 30));
        btnBack1.setMinimumSize(new java.awt.Dimension(30, 30));
        btnBack1.setPreferredSize(new java.awt.Dimension(30, 30));
        btnBack1.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left-pressed.png")));  // NOI18N
        btnBack1.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left-selected.png"))); // NOI18N
        btnBack1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnBack1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(btnBack1, gridBagConstraints);

        btnForward.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right.png")));          // NOI18N
        btnForward.setBorder(null);
        btnForward.setBorderPainted(false);
        btnForward.setContentAreaFilled(false);
        btnForward.setFocusPainted(false);
        btnForward.setMaximumSize(new java.awt.Dimension(30, 30));
        btnForward.setMinimumSize(new java.awt.Dimension(30, 30));
        btnForward.setPreferredSize(new java.awt.Dimension(30, 30));
        btnForward.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right-pressed.png")));  // NOI18N
        btnForward.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right-selected.png"))); // NOI18N
        btnForward.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnForwardActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(btnForward, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        jPanel1.add(panScr, gridBagConstraints);

        wkKgBioDetailPanel1.setMinimumSize(new java.awt.Dimension(1400, 300));
        wkKgBioDetailPanel1.setPreferredSize(new java.awt.Dimension(1400, 300));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(wkKgBioDetailPanel1, gridBagConstraints);

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
     * @param  evt  DOCUMENT ME!
     */
    private void btnBack1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnBack1ActionPerformed
        int year = getCurrentlyEnteredYear();

        if (--measureNumber < 0) {
            measureNumber = 0;
            --year;
        }

        noDocumentUpdate = true;
        txtJahr.setText(String.valueOf(year));
        noDocumentUpdate = false;

        final int newYear = year;
        final WaitingDialogThread<YearAndMeasure> wdt = new WaitingDialogThread<YearAndMeasure>(StaticSwingTools
                        .getParentFrame(this),
                true,
                "Lade Messwerte",
                null,
                100) {

                @Override
                protected YearAndMeasure doInBackground() throws Exception {
                    CidsBean measure = null;
                    int measureYear = newYear;

                    do {
                        measure = getDataForYear(measureYear, measureNumber);
                        --measureYear;
                    } while ((measure == null) && (measureYear > 1990));

                    return new YearAndMeasure(measure, ++measureYear);
                }

                @Override
                protected void done() {
                    try {
                        final YearAndMeasure measure = get();

                        noDocumentUpdate = true;
                        txtJahr.setText(String.valueOf(measure.getYear()));
                        noDocumentUpdate = false;
                        showNewMeasure(measure.getMeasure());
                    } catch (Exception e) {
                        LOG.error("Erro while searching measure values", e);
                    }
                }
            };

        wdt.start();
    } //GEN-LAST:event_btnBack1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnForwardActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnForwardActionPerformed
        final int year = getCurrentlyEnteredYear();

        // noDocumentUpdate = true;
        // txtJahr.setText(String.valueOf(year));
        ++measureNumber;

        final WaitingDialogThread<YearAndMeasure> wdt = new WaitingDialogThread<YearAndMeasure>(StaticSwingTools
                        .getParentFrame(this),
                true,
                "Lade Messwerte",
                null,
                100) {

                @Override
                protected YearAndMeasure doInBackground() throws Exception {
                    CidsBean measure = getDataForYear(year, measureNumber);

                    if (measure == null) {
                        measureNumber = 0;
                        int measureYear = year + 1;
                        // noDocumentUpdate = true;
                        // txtJahr.setText(String.valueOf(measureYear));
                        // noDocumentUpdate = false;

                        measure = null;
                        final int currentYear = (new GregorianCalendar()).get(GregorianCalendar.YEAR);

                        do {
                            // txtJahr.setText(String.valueOf(measureYear));
                            measure = getDataForYear(measureYear, measureNumber);
                            // showNewMeasure(measure);
                            ++measureYear;
                        } while ((measure == null) && (measureYear <= currentYear));

                        return new YearAndMeasure(measure, --measureYear);
                    } else {
                        return new YearAndMeasure(measure, year);
                    }
                }

                @Override
                protected void done() {
                    try {
                        final YearAndMeasure measure = get();

                        noDocumentUpdate = true;
                        txtJahr.setText(String.valueOf(measure.getYear()));
                        noDocumentUpdate = false;
                        showNewMeasure(measure.getMeasure());
                    } catch (Exception e) {
                        LOG.error("Erro while searching measure values", e);
                    }
                }
            };

        wdt.start();
    } //GEN-LAST:event_btnForwardActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int getCurrentlyEnteredYear() {
        try {
            return Integer.parseInt(txtJahr.getText());
        } catch (final NumberFormatException e) {
            txtJahr.setText(String.valueOf(new GregorianCalendar().get(GregorianCalendar.YEAR)));
            return new GregorianCalendar().get(GregorianCalendar.YEAR);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   year    DOCUMENT ME!
     * @param   number  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getDataForYear(final int year, final int number) {
        int row = jtMstTab1.getSelectedRow();

        if (row != -1) {
            row = jtMstTab1.convertRowIndexToModel(row);

            final CidsBean mstStamm = ((MstTableModel)jtMstTab1.getModel()).getData().get(row);

            final List<CidsBean> beansFromYear = new ArrayList<>();
            final List<CidsBean> messungen = (List<CidsBean>)mstStamm.getProperty("messungen");

            for (final CidsBean bean : messungen) {
                if ((bean.getProperty("jahr") != null) && bean.getProperty("jahr").equals(year)) {
                    beansFromYear.add(bean);
                }
            }

            if ((beansFromYear != null) && (number >= 0) && (number < beansFromYear.size())) {
                final CidsBean retVal = beansFromYear.get(number);
                int index = -1;

                // if the bean is already in the beansToSave list, the bean from the list should be used
                if ((index = beansToSave.indexOf(retVal)) != -1) {
                    return beansToSave.get(index);
                } else {
                    beansToSave.add(retVal);
                    return retVal;
                }
            } else {
                return null;
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  measure  DOCUMENT ME!
     */
    private void showNewMeasure(final CidsBean measure) {
        saveLastMeasure();
        wkKgBioDetailPanel1.setCidsBean(measure, cidsBean);
    }

    /**
     * adds the last processed bean to the beansToSave list, if it is not in, yet.
     */
    private void saveLastMeasure() {
        if (!readOnly) {
            final CidsBean lastMeasure = wkKgBioDetailPanel1.getCidsBean();

            if ((lastMeasure != null) && !beansToSave.contains(lastMeasure)) {
                beansToSave.add(lastMeasure);
            }
        }
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
            refreshMeasures();
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
                refreshMeasures();
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
    public static synchronized List<CidsBean> getBioMst(final CidsBean cidsBean) {
        final List<CidsBean> data = new ArrayList<CidsBean>();

        try {
            final MetaClass MC = ClassCacheMultiple.getMetaClass(
                    WRRLUtil.DOMAIN_NAME,
                    "wk_kg_mst_stammdaten");
            String query = "select " + MC.getID() + ", m." + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
            query += " m";                                                                                      // NOI18N
            query += " WHERE m.wk_kg = " + cidsBean.getProperty("id");                                          // NOI18N
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
                    "wk_kg_mst_stammdaten");
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

    /**
     * DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     */
    private void refreshMeasures() {
        final int year = getCurrentlyEnteredYear();
        measureNumber = 0;

        final CidsBean measure = getDataForYear(year, measureNumber);
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    showNewMeasure(measure);
                }
            });
    }

    @Override
    public void insertUpdate(final DocumentEvent e) {
        if (!noDocumentUpdate && (e.getDocument().getLength() == 4)) {
            refreshMeasures();
        }
    }

    @Override
    public void removeUpdate(final DocumentEvent e) {
        if (!noDocumentUpdate && (e.getDocument().getLength() == 4)) {
            refreshMeasures();
        }
    }

    @Override
    public void changedUpdate(final DocumentEvent e) {
        // nothing to do
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class MstTableModel extends AbstractTableModel {

        //~ Instance fields ----------------------------------------------------

        private final String[][] HEADER = {
                { "WK", "wk_k" },                               // NOI18N
                { "MST", "messstelle" },                        // NOI18N
                { "Phytoplankton", "ppt_class" },               // NOI18N
                { "Großalgen und Angiospermen", "phyb_class" }, // NOI18N
                { "Benthische wirbellose Fauna", "mzb_class" }  // NOI18N
            };
        private List<CidsBean> data = new ArrayList<CidsBean>();
        private boolean isInitialised = false;
        private CidsBean wkBean = null;

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
            return HEADER.length;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            if (columnIndex < HEADER.length) {
                return HEADER[columnIndex][0];
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
            } else if ((rowIndex < data.size()) && (columnIndex < HEADER.length)) {
                if (columnIndex == 0) {
                    return cidsBean.getProperty("wk_k");
                } else if ((columnIndex == 2) || (columnIndex == 3) || (columnIndex == 4)) {
                    final List<CidsBean> messungenBean = (List<CidsBean>)data.get(rowIndex).getProperty("messungen");
                    boolean found = false;

                    for (final CidsBean mBean : messungenBean) {
                        if (mBean.getProperty(HEADER[columnIndex][1]) != null) {
                            found = true;
                            break;
                        }
                    }

                    return (found ? "Ja" : "Nein");
                } else {
                    final Object value = data.get(rowIndex).getProperty(HEADER[columnIndex][1]);

                    if (value != null) {
                        if (value instanceof CidsBean) {
                            return String.valueOf(((CidsBean)value).getProperty("name")); // NOI18N
                        } else {
                            return String.valueOf(value);
                        }
                    } else {
                        return "-";                                                       // NOI18N
                    }
                }
            } else {
                return "";                                                                // NOI18N
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
            this.wkBean = cidsBean;
            data.clear();
            data.addAll(getBioMst(cidsBean));
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

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class YearAndMeasure {

        //~ Instance fields ----------------------------------------------------

        private CidsBean measure;
        private int year;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new YearAndMeasure object.
         *
         * @param  measure  DOCUMENT ME!
         * @param  year     DOCUMENT ME!
         */
        public YearAndMeasure(final CidsBean measure, final int year) {
            this.measure = measure;
            this.year = year;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  the measure
         */
        public CidsBean getMeasure() {
            return measure;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  measure  the measure to set
         */
        public void setMeasure(final CidsBean measure) {
            this.measure = measure;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  the year
         */
        public int getYear() {
            return year;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  year  the year to set
         */
        public void setYear(final int year) {
            this.year = year;
        }
    }
}
