/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import de.cismet.cids.custom.util.LinearReferencingConstants;
import de.cismet.cids.custom.util.LinearReferencingHelper;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class StationArrayEditor extends JPanel implements DisposableCidsBeanStore, LinearReferencingConstants {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private String arrayField;
    private Collection<StationEditor> stationEditors = new ArrayList<StationEditor>();
    private HashMap<JButton, StationEditor> stationenMap = new HashMap<JButton, StationEditor>();
    private Collection<StationArrayEditorListener> listeners = new ArrayList<StationArrayEditorListener>();
    private CidsBeanDropTarget cidsBeanDropTarget;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dropPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private de.cismet.tools.gui.SemiRoundedPanel semiRoundedPanel1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkTeileEditor.
     */
    public StationArrayEditor() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  arrayField  DOCUMENT ME!
     */
    public final void setFields(final String arrayField) {
        setArrayField(arrayField);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  arrayField  DOCUMENT ME!
     */
    private void setArrayField(final String arrayField) {
        this.arrayField = arrayField;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean addStationEditorListener(final StationArrayEditorListener listener) {
        return listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean removeListener(final StationArrayEditorListener listener) {
        return listeners.remove(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  source  DOCUMENT ME!
     */
    private void fireStationAdded(final StationEditor source) {
        for (final StationArrayEditorListener listener : listeners) {
            listener.editorAdded(source);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  source  DOCUMENT ME!
     */
    private void fireStationRemoved(final StationEditor source) {
        for (final StationArrayEditorListener listener : listeners) {
            listener.editorRemoved(source);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<CidsBean> getCidsBeans() {
        if (cidsBean != null) {
            return (Collection<CidsBean>)cidsBean.getProperty(arrayField);
        } else {
            return new ArrayList<CidsBean>();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stationEditor  DOCUMENT ME!
     */
    private void addEditor(final StationEditor stationEditor) {
        stationEditors.add(stationEditor);

        final JPanel panItem = new JPanel(new BorderLayout());
        panItem.setOpaque(false);
        final JButton btnRemove = new JButton();

        btnRemove.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    final JButton button = (JButton)evt.getSource();
                    final StationEditor stationEditor = stationenMap.get(button);
                    removeEditor(stationEditor);
                }
            });

        final JPanel panEast = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panEast.add(btnRemove);
        panEast.setOpaque(false);

        panItem.add(stationEditor, BorderLayout.CENTER);
        panItem.add(panEast, BorderLayout.EAST);
        panItem.add(new JSeparator(), BorderLayout.SOUTH);

        stationenMap.put(btnRemove, stationEditor);
        jPanel1.add(panItem);
        ((java.awt.GridLayout)jPanel1.getLayout()).setRows(jPanel1.getComponentCount());

        revalidate();
        fireStationAdded(stationEditor);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stationEditor  DOCUMENT ME!
     */
    private void removeEditor(final StationEditor stationEditor) {
        final Collection<CidsBean> cidsBeans = getCidsBeans();

        cidsBeans.remove(stationEditor.getCidsBean());

        jPanel1.remove(stationEditor.getParent());
        stationEditor.dispose();

        if (cidsBeans.size() > 0) {
            ((java.awt.GridLayout)jPanel1.getLayout()).setRows(cidsBeans.size());
        } else {
            ((java.awt.GridLayout)jPanel1.getLayout()).setRows(1);
        }

        stationEditors.remove(stationEditor);

        revalidate();
        fireStationRemoved(stationEditor);
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void dispose() {
        for (final StationEditor stationEditor : stationEditors) {
            stationEditor.dispose();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        roundedPanel1 = new de.cismet.tools.gui.RoundedPanel();
        semiRoundedPanel1 = new de.cismet.tools.gui.SemiRoundedPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        dropPanel = new DropPanel();
        jLabel3 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(200, 100));
        setOpaque(false);
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        semiRoundedPanel1.setBackground(new java.awt.Color(51, 51, 51));
        semiRoundedPanel1.setMinimumSize(new java.awt.Dimension(55, 24));
        semiRoundedPanel1.setPreferredSize(new java.awt.Dimension(0, 24));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(org.openide.util.NbBundle.getMessage(
                StationArrayEditor.class,
                "StationArrayEditor.jLabel1.text")); // NOI18N
        semiRoundedPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        roundedPanel1.add(semiRoundedPanel1, java.awt.BorderLayout.NORTH);

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 48));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));
        roundedPanel1.add(jPanel1, java.awt.BorderLayout.CENTER);

        dropPanel.setOpaque(false);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(org.openide.util.NbBundle.getMessage(
                StationArrayEditor.class,
                "StationArrayEditor.jLabel3.text")); // NOI18N
        dropPanel.add(jLabel3);

        roundedPanel1.add(dropPanel, java.awt.BorderLayout.SOUTH);

        add(roundedPanel1);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;

        final Collection<CidsBean> cidsBeans = getCidsBeans();

        if (cidsBeanDropTarget == null) {
            cidsBeanDropTarget = new CidsBeanDropTarget(dropPanel);
        }

//        if (stationenMap != null) {
//            stationenMap.clear();
//            jPanel1.removeAll();
//        }

        if (cidsBeans.size() > 0) {
            ((java.awt.GridLayout)jPanel1.getLayout()).setRows(cidsBeans.size());
        } else {
            ((java.awt.GridLayout)jPanel1.getLayout()).setRows(1);
        }

        for (final CidsBean stationBean : cidsBeans) {
            final StationEditor stationEditor = new StationEditor();
            stationEditor.setCidsBean(stationBean);
            addEditor(stationEditor);
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class DropPanel extends JPanel implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            for (final CidsBean routeBean : beans) {
                if (routeBean.getMetaObject().getMetaClass().getName().equals(CN_ROUTE)) {
                    final StationEditor editor = new StationEditor();
                    final CidsBean stationBean = LinearReferencingHelper.createStationBeanFromRouteBean(routeBean);
                    editor.setCidsBean(stationBean);
                    addEditor(editor);
                    getCidsBeans().add(editor.getCidsBean());
                } else {
                    return;
                }
            }
        }
    }
}
