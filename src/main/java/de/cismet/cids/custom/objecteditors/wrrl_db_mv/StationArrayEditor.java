/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.LinearReferencingConstants;
import de.cismet.cids.custom.util.StationToMapRegistry;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class StationArrayEditor extends javax.swing.JPanel implements CidsBeanDropListener, LinearReferencingConstants {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StationArrayEditor.class);

    //~ Instance fields --------------------------------------------------------

    private Collection<CidsBean> cidsBeans;
    private Collection<StationEditor> stationEditors = new ArrayList<StationEditor>();
    private HashMap<JButton, StationEditor> stationenMap = new HashMap<JButton, StationEditor>();
    private Collection<StationArrayEditorListener> listeners = new ArrayList<StationArrayEditorListener>();
    private CidsBeanDropTarget cidsBeanDropTarget;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private de.cismet.tools.gui.SemiRoundedPanel semiRoundedPanel1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkTeileEditor.
     */
    public StationArrayEditor() {
        initComponents();

        try {
            new CidsBeanDropTarget(this);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while creating CidsBeanDropTarget");
            }
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        for (final CidsBean bean : beans) {
            if (bean.getMetaObject().getMetaClass().getName().equals(MC_ROUTE)) {
                final StationEditor editor = new StationEditor();
                editor.setCidsBean(createStationFromRoute(bean));
                addEditor(editor);
                cidsBeans.add(editor.getCidsBean());
            } else {
                return;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   routeBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private static CidsBean createStationFromRoute(final CidsBean routeBean) {
        final MetaClass stationMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "STATION");
        final MetaClass geomMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "GEOM");

        final CidsBean stationBean = stationMC.getEmptyInstance().getBean();
        final CidsBean geomBean = geomMC.getEmptyInstance().getBean();

        try {
            stationBean.setProperty(PROP_STATION_ROUTE, routeBean);
            stationBean.setProperty(PROP_STATION_VALUE, 0d);
            stationBean.setProperty(PROP_STATION_GEOM, geomBean);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while creating wkteil bean", ex);
            }
        }

        return stationBean;
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
        return cidsBeans;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBeans  DOCUMENT ME!
     */
    public void setCidsBeans(final Collection<CidsBean> cidsBeans) {
        this.cidsBeans = cidsBeans;

        if (cidsBeanDropTarget == null) {
            cidsBeanDropTarget = new CidsBeanDropTarget(this);
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

    /**
     * DOCUMENT ME!
     *
     * @param  stationEditor  DOCUMENT ME!
     */
    private void addEditor(final StationEditor stationEditor) {
        stationEditors.add(stationEditor);

        final JPanel panItem = new JPanel(new GridBagLayout());
        panItem.setOpaque(false);
        GridBagConstraints gridBagConstraints;
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

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        panItem.add(stationEditor, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets.left = 5;
        gridBagConstraints.insets.right = 5;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        panItem.add(btnRemove, gridBagConstraints);

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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

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

        jPanel2.setOpaque(false);

        jLabel2.setText(" "); // NOI18N
        jPanel2.add(jLabel2);

        roundedPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        add(roundedPanel1);
    } // </editor-fold>//GEN-END:initComponents
}
