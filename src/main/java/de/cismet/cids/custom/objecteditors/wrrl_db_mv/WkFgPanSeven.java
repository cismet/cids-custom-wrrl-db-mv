/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 stefan
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * WkFgPanOne.java
 *
 * Created on 04.08.2010, 13:44:05
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import org.apache.log4j.Logger;

import java.awt.Component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import de.cismet.cids.custom.wrrl_db_mv.server.search.WkFgMeldeInfosSearch;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.server.search.CidsServerSearch;

import static javax.swing.SwingConstants.TOP;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class WkFgPanSeven extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    private Logger LOG = Logger.getLogger(WkFgPanSeven.class);
    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblSpace;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadQuality;
    private de.cismet.tools.gui.RoundedPanel panQuality;
    private javax.swing.JPanel panQualityContent;
    private org.jdesktop.swingx.JXTable tabPressure;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WkFgPanSeven object.
     */
    public WkFgPanSeven() {
        this(true);
    }

    /**
     * Creates new form WkFgPanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkFgPanSeven(final boolean readOnly) {
        initComponents();

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
                        ((JLabel)c).setToolTipText("<html>" + wrapText(String.valueOf(value), 50) + "</html>");
                    }
                    return c;
                }
            });
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   text  DOCUMENT ME!
     * @param   size  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String wrapText(final String text, final int size) {
        final StringBuilder buffer = new StringBuilder(text.length());
        int lineLength = 0;

        for (int i = 0; i < text.length(); ++i) {
            if ((text.charAt(i) == '\n')) {
                lineLength = 0;
                buffer.append("<br>");
            } else if ((lineLength >= size) && (text.charAt(i) == ' ')) {
                lineLength = 1;
                buffer.append("<br>");
                buffer.append(text.charAt(i));
            } else {
                ++lineLength;
                buffer.append(text.charAt(i));
            }
        }

        return buffer.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panQuality = new de.cismet.tools.gui.RoundedPanel();
        panHeadQuality = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panQualityContent = new javax.swing.JPanel();
        lblSpace = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabPressure = new org.jdesktop.swingx.JXTable();

        setMinimumSize(new java.awt.Dimension(1150, 690));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1150, 690));
        setLayout(new java.awt.BorderLayout());

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

        add(panQuality, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
//        bindingGroup.unbind();
        if (cidsBean != null) {
            this.cidsBean = cidsBean;
//            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
//                bindingGroup,
//                this.cidsBean);
//            bindingGroup.bind();

            final boolean showPanMelinf = SessionManager.getSession()
                        .getUser()
                        .getUserGroup()
                        .getName()
                        .equalsIgnoreCase("administratoren")
                        || SessionManager.getSession()
                        .getUser()
                        .getUserGroup()
                        .getName()
                        .toLowerCase()
                        .startsWith("stalu");

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
                                tabPressure.setModel(new CustomTableModel(infos));

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
                                LOG.error("Error while retrieving anhörungs infos", e);
                            }
                        }
                    };

                t.start();
            } else {
                tabPressure.setModel(new CustomTableModel(new ArrayList<ArrayList>()));
            }
        } else {
            tabPressure.setModel(new CustomTableModel(new ArrayList<ArrayList>()));
        }
    }

    @Override
    public void dispose() {
//        bindingGroup.unbind();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class CustomTableModel implements TableModel {

        //~ Static fields/initializers -----------------------------------------

        private static final String[] COLUMN_NAMES = {
                "Katalog-Nummer",
                "Name des Einwenders",
                "Nr. der Stellungnahme",
                "Nr. der Einzelforderung",
                "Bezug",
                "Einzelforderung",
                "erwiderte Einzelforderung",
                "Bemerkung"
            };

        //~ Instance fields ----------------------------------------------------

        private final List<TableModelListener> listener = new ArrayList<>();
        private final List<ArrayList> data;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new PressureTableModel object.
         *
         * @param  data  readOnly DOCUMENT ME!
         */
        public CustomTableModel(final List<ArrayList> data) {
            this.data = data;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            return COLUMN_NAMES[columnIndex];
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
            return data.get(rowIndex).get(columnIndex);
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        }

        @Override
        public void addTableModelListener(final TableModelListener l) {
            listener.add(l);
        }

        @Override
        public void removeTableModelListener(final TableModelListener l) {
            listener.remove(l);
        }

        /**
         * DOCUMENT ME!
         */
        public void fireTableChanged() {
            final TableModelEvent e = new TableModelEvent(this);
            for (final TableModelListener tmp : listener) {
                tmp.tableChanged(e);
            }
        }
    }
}
