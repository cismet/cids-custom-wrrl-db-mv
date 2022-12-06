/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

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
public class WkGwMstChemieMessungenEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            WkGwMstChemieMessungenEditor.class);

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JPanel panFooter;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkGwMstMessungenPanOne wkGwMstMessungenPanOne1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public WkGwMstChemieMessungenEditor() {
        this(false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkGwMstChemieMessungenEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();

        clearForm();
        jPanel4.addMouseListener(new MouseAdapter() {

                boolean isHandCursor = false;

                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isMouseOver(e)) {
                        try {
                            BrowserLauncher.openURL(
                                "https://www.gesetze-im-internet.de/grwv_2010/anlage_2.html");
                        } catch (Exception ex) {
                            LOG.warn(ex, ex);
                        }
                    }
                }

                @Override
                public void mouseMoved(final MouseEvent e) {
                    if (!isHandCursor && isMouseOver(e)) {
                        jPanel4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        isHandCursor = true;
                    } else if (isHandCursor) {
                        jPanel4.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        isHandCursor = false;
                    }
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                    if (isHandCursor) {
                        jPanel4.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        isHandCursor = false;
                    }
                }

                private boolean isMouseOver(final MouseEvent e) {
                    return ((e.getPoint().x > 10) && (e.getPoint().x < 85) && (e.getPoint().y < 18));
                }
            });
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBeans  DOCUMENT ME!
     * @param  parent     DOCUMENT ME!
     */
    public void setCidsBeans(final CidsBean[] cidsBeans, final CidsBean parent) {
        if (!EventQueue.isDispatchThread()) {
            LOG.error("setCidsBeans() invocation not in edt", new Exception());
        }
        clearForm();

        setEnable(!readOnly);
        lblFoot.setText("");

        wkGwMstMessungenPanOne1.setCidsBeans(cidsBeans);
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
        if (!EventQueue.isDispatchThread()) {
            EventQueue.invokeLater(new Thread("Enable editor") {

                    @Override
                    public void run() {
                        wkGwMstMessungenPanOne1.setEnable(enable);
                    }
                });
        } else {
            wkGwMstMessungenPanOne1.setEnable(enable);
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

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        wkGwMstMessungenPanOne1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkGwMstMessungenPanOne();

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
                    WkGwMstChemieMessungenEditor.class,
                    "WkGwMstChemieMessungenEditor.jPanel4.border.title",
                    new Object[] {}),
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Dialog", 0, 12),
                new java.awt.Color(28, 72, 227))); // NOI18N
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
        jPanel4.add(wkGwMstMessungenPanOne1, gridBagConstraints);

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
}
