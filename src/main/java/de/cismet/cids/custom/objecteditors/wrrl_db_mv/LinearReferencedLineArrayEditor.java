/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;

import com.vividsolutions.jts.geom.Geometry;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.LinearReferencingConstants;

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
public class LinearReferencedLineArrayEditor extends javax.swing.JPanel implements CidsBeanDropListener,
    LinearReferencingConstants {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkTeileEditor.class);

    //~ Instance fields --------------------------------------------------------

    private Collection<CidsBean> cidsBeans;
    private Collection<LinearReferencedLineEditor> editors = new ArrayList<LinearReferencedLineEditor>();
    private HashMap<JButton, LinearReferencedLineEditor> editorButtonMap =
        new HashMap<JButton, LinearReferencedLineEditor>();
    private Collection<LinearReferencedLineArrayEditorListener> listeners =
        new ArrayList<LinearReferencedLineArrayEditorListener>();

    private String fromStationField;
    private String toStationField;
    private String realGeomField;
    private String metaClassName;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTitle;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private de.cismet.tools.gui.SemiRoundedPanel semiRoundedPanel1;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LinearReferencedLineArrayEditor.
     */
    public LinearReferencedLineArrayEditor() {
        initComponents();

        try {
            new CidsBeanDropTarget(this);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while creating CidsBeanDropTarget");
            }
        }
    }

    /**
     * Creates a new LinearReferencedLineArrayEditor object.
     *
     * @param  metaClassName     DOCUMENT ME!
     * @param  fromStationField  DOCUMENT ME!
     * @param  toStationField    DOCUMENT ME!
     * @param  realGeomField     DOCUMENT ME!
     */
    public LinearReferencedLineArrayEditor(final String metaClassName,
            final String fromStationField,
            final String toStationField,
            final String realGeomField) {
        this();
        setMetaClassName(metaClassName);
        setFromStationField(fromStationField);
        setToStationField(toStationField);
        setRealGeomField(realGeomField);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  metaClassName  DOCUMENT ME!
     */
    public final void setMetaClassName(final String metaClassName) {
        this.metaClassName = metaClassName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  fromStationField  DOCUMENT ME!
     */
    public final void setFromStationField(final String fromStationField) {
        this.fromStationField = fromStationField;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  toStationField  DOCUMENT ME!
     */
    public final void setToStationField(final String toStationField) {
        this.toStationField = toStationField;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  realGeomField  DOCUMENT ME!
     */
    public final void setRealGeomField(final String realGeomField) {
        this.realGeomField = realGeomField;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  title  DOCUMENT ME!
     */
    public void setTitle(final String title) {
        lblTitle.setText(title);
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        for (final CidsBean bean : beans) {
            if (bean.getMetaObject().getMetaClass().getName().equals(MC_ROUTE)) {
                final LinearReferencedLineEditor editor = createEditor();
                editor.setMetaClassName(metaClassName);
                editor.setCidsBean(createBeanFromRoute(bean));
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
     * @return  DOCUMENT ME!
     */
    protected LinearReferencedLineEditor createEditor() {
        final LinearReferencedLineEditor editor = new LinearReferencedLineEditor();
        editor.setMetaClassName(metaClassName);
        editor.setFromStationField(fromStationField);
        editor.setToStationField(toStationField);
        editor.setRealGeomField(realGeomField);
        return editor;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean addLinearReferencedLineArrayEditorListener(final LinearReferencedLineArrayEditorListener listener) {
        return listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean removeLinearReferencedLineArrayEditorListener(
            final LinearReferencedLineArrayEditorListener listener) {
        return listeners.remove(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  source  DOCUMENT ME!
     */
    private void fireEditorAdded(final LinearReferencedLineEditor source) {
        for (final LinearReferencedLineArrayEditorListener listener : listeners) {
            listener.editorAdded(source);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  source  DOCUMENT ME!
     */
    private void fireEditorRemoved(final LinearReferencedLineEditor source) {
        for (final LinearReferencedLineArrayEditorListener listener : listeners) {
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

        if (cidsBeans.size() > 0) {
            ((java.awt.GridLayout)jPanel1.getLayout()).setRows(cidsBeans.size());
        } else {
            ((java.awt.GridLayout)jPanel1.getLayout()).setRows(1);
        }

        for (final CidsBean cidsBean : cidsBeans) {
            final LinearReferencedLineEditor editor = createEditor();
            editor.setCidsBean(cidsBean);
            addEditor(editor);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  editor  DOCUMENT ME!
     */
    private void addEditor(final LinearReferencedLineEditor editor) {
        editors.add(editor);

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
                    final LinearReferencedLineEditor editor = editorButtonMap.get(button);
                    removeEditor(editor);
                }
            });

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        panItem.add(editor, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets.left = 5;
        gridBagConstraints.insets.right = 5;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        panItem.add(btnRemove, gridBagConstraints);

        editorButtonMap.put(btnRemove, editor);
        jPanel1.add(panItem);
        ((java.awt.GridLayout)jPanel1.getLayout()).setRows(jPanel1.getComponentCount());

        revalidate();
        fireEditorAdded(editor);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   routeBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected CidsBean createBeanFromRoute(final CidsBean routeBean) {
        final MetaClass segmentMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, metaClassName);
        final MetaClass stationMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_STATION);
        final MetaClass geomMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_GEOM);

        final CidsBean newBean = segmentMC.getEmptyInstance().getBean();
        final CidsBean fromBean = stationMC.getEmptyInstance().getBean();
        final CidsBean toBean = stationMC.getEmptyInstance().getBean();
        final CidsBean geomBean = geomMC.getEmptyInstance().getBean();
        final CidsBean fromGeomBean = geomMC.getEmptyInstance().getBean();
        final CidsBean toGeomBean = geomMC.getEmptyInstance().getBean();

        try {
            fromBean.setProperty(PROP_STATION_ROUTE, routeBean);
            fromBean.setProperty(PROP_STATION_VALUE, 0d);
            fromBean.setProperty(PROP_STATION_GEOM, fromGeomBean);

            toBean.setProperty(PROP_STATION_ROUTE, routeBean);
            toBean.setProperty(
                PROP_STATION_VALUE,
                ((Geometry)((CidsBean)routeBean.getProperty(PROP_ROUTE_GEOM)).getProperty(PROP_GEOM_GEOFIELD))
                            .getLength());
            toBean.setProperty(PROP_STATION_GEOM, toGeomBean);

            newBean.setProperty(fromStationField, fromBean);
            newBean.setProperty(toStationField, toBean);
            newBean.setProperty(realGeomField, geomBean);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while creating new bean", ex);
            }
        }

        return newBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  editor  DOCUMENT ME!
     */
    private void removeEditor(final LinearReferencedLineEditor editor) {
        cidsBeans.remove(editor.getCidsBean());

        jPanel1.remove(editor.getParent());
        editor.dispose();

        if (cidsBeans.size() > 0) {
            ((java.awt.GridLayout)jPanel1.getLayout()).setRows(cidsBeans.size());
        } else {
            ((java.awt.GridLayout)jPanel1.getLayout()).setRows(1);
        }

        editors.remove(editor);

        revalidate();
        fireEditorRemoved(editor);
    }

    /**
     * DOCUMENT ME!
     */
    public void dispose() {
        for (final LinearReferencedLineEditor editor : editors) {
            editor.dispose();
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
        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(200, 100));
        setOpaque(false);
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        semiRoundedPanel1.setBackground(new java.awt.Color(51, 51, 51));
        semiRoundedPanel1.setMinimumSize(new java.awt.Dimension(55, 24));
        semiRoundedPanel1.setPreferredSize(new java.awt.Dimension(0, 24));

        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineArrayEditor.class,
                "LinearReferencedLineArrayEditor.lblTitle.text")); // NOI18N
        semiRoundedPanel1.add(lblTitle, java.awt.BorderLayout.CENTER);

        roundedPanel1.add(semiRoundedPanel1, java.awt.BorderLayout.NORTH);

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 48));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));
        roundedPanel1.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setMinimumSize(new java.awt.Dimension(10, 24));
        jPanel2.setOpaque(false);

        jLabel2.setText(" "); // NOI18N
        jPanel2.add(jLabel2);

        roundedPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        add(roundedPanel1);
    } // </editor-fold>//GEN-END:initComponents
}