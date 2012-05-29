/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * GupPegelEditor.java
 *
 * Created on 26.10.2011, 11:16:19
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupHydrologieEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    MouseListener,
    MouseMotionListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupHydrologieEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private HydroTableModel hydroModel = new HydroTableModel();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.tools.gui.RoundedPanel glassPanel;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXTable jxPegel;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupHydrologieEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupHydrologieEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
//        tabPegel.setModel(new HydroTableModel());
        setReadOnly(readOnly);

        jxPegel.setModel(hydroModel);
        jxPegel.getColumn(11).setCellRenderer(new GanglinieRenderer());
        jxPegel.getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    final ImageIcon icon = new ImageIcon(
                            getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/pegel.png"));
                    final JLabel lab = (JLabel)super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column);
                    lab.setIcon(icon);
                    return lab;
                }
            });
        jxPegel.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        jxPegel.getColumnModel().getColumn(0).setPreferredWidth(140);
        jxPegel.getColumnModel().getColumn(1).setPreferredWidth(140);
        jxPegel.getColumnModel().getColumn(2).setPreferredWidth(80);
        jxPegel.getColumnModel().getColumn(3).setPreferredWidth(55);
        jxPegel.getColumnModel().getColumn(4).setPreferredWidth(55);
        jxPegel.getColumnModel().getColumn(5).setPreferredWidth(55);
        jxPegel.getColumnModel().getColumn(6).setPreferredWidth(55);
        jxPegel.getColumnModel().getColumn(7).setPreferredWidth(55);
        jxPegel.getColumnModel().getColumn(8).setPreferredWidth(55);
        jxPegel.getColumnModel().getColumn(9).setPreferredWidth(55);
        jxPegel.getColumnModel().getColumn(10).setPreferredWidth(55);
        jxPegel.getColumnModel().getColumn(11).setPreferredWidth(200);

        final Highlighter alternateRowHighlighter = HighlighterFactory.createAlternateStriping(
                new Color(208, 208, 208),
                new Color(144, 144, 144));
        jxPegel.setHighlighters(alternateRowHighlighter, new LinkHighlighter());
        jxPegel.addMouseListener(this);
        jxPegel.addMouseMotionListener(this);
        jxPegel.setOpaque(false);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jxPegel = new org.jdesktop.swingx.JXTable();
        glassPanel = new de.cismet.tools.gui.RoundedPanel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(994, 800));
        setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setViewportView(jxPegel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 15, 20);
        add(jScrollPane2, gridBagConstraints);

        glassPanel.setAlpha(0);
        glassPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(glassPanel, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
//        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
//            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
//                bindingGroup,
//                cidsBean);
//            bindingGroup.bind();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public void setReadOnly(final boolean readOnly) {
        if (readOnly) {
            glassPanel.addMouseListener(new MouseAdapter() {
                });
        } else {
            for (final MouseListener ml : glassPanel.getMouseListeners()) {
                glassPanel.removeMouseListener(ml);
            }
        }
    }

    @Override
    public void dispose() {
//        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Hydrologische Kenngrößen";
    }

    @Override
    public void setTitle(final String title) {
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        final int col = jxPegel.columnAtPoint(e.getPoint());
        if (col == 11) {
            try {
                java.awt.Desktop.getDesktop()
                        .browse(java.net.URI.create("http://localhost/~thorsten/cids/web/gup/GWUTollense-Los1-5.pdf"));
            } catch (final Exception ex) {
                LOG.error("Error while starting download.", ex);
            }
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        final int col = jxPegel.columnAtPoint(e.getPoint());
        final int row = jxPegel.rowAtPoint(e.getPoint());

        if ((col == 11) && (row != -1)) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class HydroTableModel extends AbstractTableModel {

        //~ Instance fields ----------------------------------------------------

        private String[] header = {
                "Bezeichnung Pegel",
                "Station",
                "Einz-Geb.",
                "NW",
                "MW",
                "HWx",
                "HHW",
                "NQ",
                "MQ",
                "HQx",
                "HHQ",
                "Ganglinie"
            }; // NOI18N
        private String[][] data = {
                {
                    "Pegel A",
                    "15700 m",
                    "180 km²",
                    "0.45",
                    "0.51",
                    "1.20",
                    "1.82",
                    "0.12",
                    "2.25",
                    "9.20",
                    "14.40",
                    "Ganglinie Pegel A"
                },
                {
                    "Pegel B",
                    "38500 m",
                    "180 km²",
                    "0.20",
                    "0.45",
                    "0.83",
                    "1.14",
                    "0.00",
                    "0.58",
                    "3.94",
                    "11.10",
                    "Ganglinie Pegel B"
                },
                {
                    "Pegel C",
                    "48500 m",
                    "120 km²",
                    "0.30",
                    "0.55",
                    "0.95",
                    "1.24",
                    "0.10",
                    "0.55",
                    "2.94",
                    "9.10",
                    "Ganglinie Pegel C"
                },
                {
                    "Pegel D",
                    "53800 m",
                    "150 km²",
                    "0.28",
                    "0.37",
                    "0.77",
                    "1.04",
                    "0.00",
                    "0.57",
                    "3.83",
                    "10.07",
                    "Ganglinie Pegel D"
                },
                {
                    "Pegel E",
                    "53800 m",
                    "150 km²",
                    "0.28",
                    "0.37",
                    "0.77",
                    "1.04",
                    "0.00",
                    "0.57",
                    "3.83",
                    "10.07",
                    "Ganglinie Pegel E"
                },
                {
                    "Pegel F",
                    "57500 m",
                    "50 km²",
                    "0.38",
                    "0.37",
                    "0.87",
                    "1.24",
                    "0.10",
                    "0.75",
                    "2.64",
                    "5.86",
                    "Ganglinie Pegel F"
                },
                {
                    "Pegel G",
                    "57500 m",
                    "50 km²",
                    "0.38",
                    "0.37",
                    "0.87",
                    "1.24",
                    "0.10",
                    "0.75",
                    "2.64",
                    "5.86",
                    "Ganglinie Pegel G"
                },
                {
                    "Pegel H",
                    "53800 m",
                    "150 km²",
                    "0.28",
                    "0.37",
                    "0.77",
                    "1.04",
                    "0.00",
                    "0.57",
                    "3.83",
                    "10.07",
                    "Ganglinie Pegel H"
                },
                {
                    "Pegel I",
                    "48500 m",
                    "120 km²",
                    "0.30",
                    "0.55",
                    "0.95",
                    "1.24",
                    "0.10",
                    "0.55",
                    "2.94",
                    "9.10",
                    "Ganglinie Pegel I"
                },
                {
                    "Pegel J",
                    "38500 m",
                    "180 km²",
                    "0.20",
                    "0.45",
                    "0.83",
                    "1.14",
                    "0.00",
                    "0.58",
                    "3.94",
                    "11.10",
                    "Ganglinie Pegel J"
                },
                {
                    "Pegel K",
                    "57500 m",
                    "50 km²",
                    "0.38",
                    "0.37",
                    "0.87",
                    "1.24",
                    "0.10",
                    "0.75",
                    "2.64",
                    "5.86",
                    "Ganglinie Pegel K"
                },
                {
                    "Pegel L",
                    "57500 m",
                    "50 km²",
                    "0.38",
                    "0.37",
                    "0.87",
                    "1.24",
                    "0.10",
                    "0.75",
                    "2.64",
                    "5.86",
                    "Ganglinie Pegel L"
                },
                {
                    "Pegel M",
                    "57500 m",
                    "50 km²",
                    "0.38",
                    "0.37",
                    "0.87",
                    "1.24",
                    "0.10",
                    "0.75",
                    "2.64",
                    "5.86",
                    "Ganglinie Pegel M"
                },
                {
                    "Pegel N",
                    "53800 m",
                    "150 km²",
                    "0.28",
                    "0.37",
                    "0.77",
                    "1.04",
                    "0.00",
                    "0.57",
                    "3.83",
                    "10.07",
                    "Ganglinie Pegel N"
                },
                {
                    "Pegel O",
                    "48500 m",
                    "120 km²",
                    "0.30",
                    "0.55",
                    "0.95",
                    "1.24",
                    "0.10",
                    "0.55",
                    "2.94",
                    "9.10",
                    "Ganglinie Pegel O"
                },
                {
                    "Pegel P",
                    "48500 m",
                    "120 km²",
                    "0.30",
                    "0.55",
                    "0.95",
                    "1.24",
                    "0.10",
                    "0.55",
                    "2.94",
                    "9.10",
                    "Ganglinie Pegel P"
                }
            };

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return header.length;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            if (columnIndex < header.length) {
                return header[columnIndex];
            } else {
                return "";
            }
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return Object.class;
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            if ((rowIndex < data.length) && (columnIndex < header.length)) {
                return data[rowIndex][columnIndex];
            } else {
                return ""; // NOI18N
            }
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            // nothing to do, because it is not allowed to modify columns
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class LinkHighlighter implements Highlighter {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component highlight(final Component renderer, final ComponentAdapter adapter) {
            if (renderer instanceof GanglinieRenderer) {
                final GanglinieRenderer tmp = (GanglinieRenderer)renderer;
                tmp.setForeground(Color.BLUE);
            }
            return renderer;
        }

        @Override
        public void addChangeListener(final ChangeListener l) {
        }

        @Override
        public void removeChangeListener(final ChangeListener l) {
        }

        @Override
        public ChangeListener[] getChangeListeners() {
            return new ChangeListener[0];
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class GanglinieRenderer extends DefaultTableCellRenderer {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getTableCellRendererComponent(final JTable table,
                final Object value,
                final boolean isSelected,
                final boolean hasFocus,
                final int row,
                final int column) {
            final ImageIcon icon = new ImageIcon(
                    getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/ganglinie.png"));
            final JLabel lab = (JLabel)super.getTableCellRendererComponent(
                    table,
                    value,
                    isSelected,
                    hasFocus,
                    row,
                    column);
            lab.setIcon(icon);
            return lab;
        }
    }
}
