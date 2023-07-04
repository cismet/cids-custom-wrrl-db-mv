/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.BrowserLauncher;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkKgBioDetailPanel extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkKgBioDetailPanel.class);
    private static final String[][] STOFF_HEADER = {
            { "Stoffname", "substance_code.name_de" }, // NOI18N
            { "Messwert", "messwert" },                // NOI18N
            { "Einheit", "einheit" },                  // NOI18N
            { "Grenzwert", "grenzwert" },              // NOI18N
            { "UQN-Art", "uqn_type.value" },           // NOI18N
            { "Jahr", "jahr_messung" }                 // NOI18N
        };

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JPanel panFooter;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgMstBioMessungenPanOne wkKgMstBioMessungenPanOne1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public WkKgBioDetailPanel() {
        this(false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkKgBioDetailPanel(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();

        clearForm();
//        jPanel4.addMouseListener(new MouseAdapter() {
//
//                boolean isHandCursor = false;
//
//                @Override
//                public void mouseClicked(final MouseEvent e) {
//                    if (isMouseOver(e)) {
//                        try {
//                            BrowserLauncher.openURL(
//                                "https://www.gesetze-im-internet.de/ogewv_2016/anlage_7.html");
//                        } catch (Exception ex) {
//                            LOG.warn(ex, ex);
//                        }
//                    }
//                }
//
//                @Override
//                public void mouseMoved(final MouseEvent e) {
//                    if (!isHandCursor && isMouseOver(e)) {
//                        jPanel4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//                        isHandCursor = true;
//                    } else if (isHandCursor) {
//                        jPanel4.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//                        isHandCursor = false;
//                    }
//                }
//
//                @Override
//                public void mouseExited(final MouseEvent e) {
//                    if (isHandCursor) {
//                        jPanel4.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//                        isHandCursor = false;
//                    }
//                }
//
//                private boolean isMouseOver(final MouseEvent e) {
//                    return ((e.getPoint().x > 10) && (e.getPoint().x < 85) && (e.getPoint().y < 18));
//                }
//            });
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        setCidsBean(cidsBean, null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     * @param  parent    DOCUMENT ME!
     */
    public void setCidsBean(final CidsBean cidsBean, final CidsBean parent) {
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            if (!readOnly) {
                setEnable(true);
            }

            if ((parent != null) && !readOnly) {
                EventQueue.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            cidsBean.addPropertyChangeListener(new SubObjectPropertyChangedListener(parent));
                        }
                    });
            }
        } else {
            if (!readOnly) {
                setEnable(false);
            }
            clearForm();
        }

        if (readOnly) {
            setEnable(false);
        }
        lblFoot.setText("");

        wkKgMstBioMessungenPanOne1.setCidsBean(cidsBean);
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     */
    private void clearForm() {
        lblFoot.setText("");
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    private void setEnable(final boolean enable) {
        EventQueue.invokeLater(new Thread("Enable editor") {

                @Override
                public void run() {
                    wkKgMstBioMessungenPanOne1.setEnable(enable);
                }
            });
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
        jPanel4 = new javax.swing.JPanel();
        wkKgMstBioMessungenPanOne1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkKgMstBioMessungenPanOne();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(910, 1250));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(910, 1250));
        setLayout(new java.awt.GridBagLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                org.openide.util.NbBundle.getMessage(
                    WkKgBioDetailPanel.class,
                    "WkKgBioDetailPanel.jPanel4.border.title_1",
                    new Object[] {}))); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel4.add(wkKgMstBioMessungenPanOne1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 10);
        add(jPanel4, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  mit              DOCUMENT ME!
     * @param  oD               DOCUMENT ME!
     * @param  reverseColors    DOCUMENT ME!
     * @param  foregroundColor  DOCUMENT ME!
     */
    public static void setColorOfField(final JTextField mit,
            final Number oD,
            final boolean reverseColors,
            final Color foregroundColor) {
        mit.setDisabledTextColor(new Color(139, 142, 143));
        mit.setBackground(new Color(245, 246, 247));

        if ((mit.getText() == null) || mit.getText().equals("") || mit.getText().equals("<nicht gesetzt>")) {
            mit.setBackground(new Color(245, 246, 247));
            return;
        }

        if (oD == null) {
            mit.setBackground(new Color(245, 246, 247));
            return;
        }

        try {
            final double mitD = Double.parseDouble(mit.getText().replace(",", "."));

            if (reverseColors) {
                mit.setBackground(calcColorReverse(mitD, oD.doubleValue()));
            } else {
                mit.setBackground(calcColor(mitD, oD.doubleValue()));
            }

            if (mit.getBackground().equals(Color.RED)) {
                mit.setDisabledTextColor(new Color(255, 255, 255));
//                mit.setForeground(new Color(0, 0, 255));
            }
//            mit.setOpaque(true);
//            mit.repaint();
        } catch (NumberFormatException e) {
            mit.setOpaque(false);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   mittel  DOCUMENT ME!
     * @param   o       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Color calcColor(final double mittel, final double o) {
        if (mittel <= o) {
            return Color.GREEN;
        } else {
            return Color.RED;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   mittel  DOCUMENT ME!
     * @param   o       DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Color calcColorReverse(final double mittel, final double o) {
        if (mittel >= o) {
            return Color.GREEN;
        } else {
            return Color.RED;
        }
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void dispose() {
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
        // TODO ?
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class SubObjectPropertyChangedListener implements PropertyChangeListener {

        //~ Instance fields ----------------------------------------------------

        private final CidsBean parent;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new SubObjectPropertyChangedListener object.
         *
         * @param  parent  DOCUMENT ME!
         */
        public SubObjectPropertyChangedListener(final CidsBean parent) {
            this.parent = parent;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (parent != null) {
                parent.setArtificialChangeFlag(true);
            }
        }
    }
}
