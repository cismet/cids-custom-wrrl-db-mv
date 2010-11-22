package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;
import com.vividsolutions.jts.geom.Geometry;
import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.LinearReferencingConstants;
import de.cismet.cids.custom.util.StationToMapRegistry;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author jruiz
 */
public class LinearReferencedLineArrayEditor extends javax.swing.JPanel implements CidsBeanDropListener, LinearReferencingConstants {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkTeileEditor.class);
    private static final Color[] COLORS = new Color[] {
        new Color(41, 86, 178), new Color(101, 156, 239), new Color(125, 189, 0), new Color(220, 246, 0), new Color(255, 91, 0)
    };

    private Collection<CidsBean> cidsBeans;
    private Collection<LinearReferencedLineEditor> editors = new ArrayList<LinearReferencedLineEditor>();
    private HashMap<JButton, LinearReferencedLineEditor> editorButtonMap = new HashMap<JButton, LinearReferencedLineEditor>();
    private int colorIndex = 0;
    private Collection<LinearReferencedLineArrayEditorListener> listeners = new ArrayList<LinearReferencedLineArrayEditorListener>();

    private String fromStationField;
    private String toStationField;
    private String realGeomField;
    private String metaClassName;

    /** Creates new form LinearReferencedLineArrayEditor */
    public LinearReferencedLineArrayEditor () {
        initComponents();

        try {
            new CidsBeanDropTarget(this);
        } catch (Exception ex) {
            LOG.debug("error while creating CidsBeanDropTarget");
        }
    }

    public LinearReferencedLineArrayEditor (final String metaClassName, final String fromStationField, final String toStationField, final String realGeomField) {
        this();
        setMetaClassName(metaClassName);
        setFromStationField(fromStationField);
        setToStationField(toStationField);
        setRealGeomField(realGeomField);
    }

    public final void setMetaClassName(final String metaClassName) {
        this.metaClassName = metaClassName;
    }

    public final void setFromStationField(final String fromStationField) {
        this.fromStationField = fromStationField;
    }

    public final void setToStationField(final String toStationField) {
        this.toStationField = toStationField;
    }

    public final void setRealGeomField(final String realGeomField) {
        this.realGeomField = realGeomField;
    }

    public void setTitle(String title) {
        lblTitle.setText(title);
    }

    @Override
    public void beansDropped(ArrayList<CidsBean> beans) {
        for (CidsBean bean : beans) {
            if (bean.getMetaObject().getMetaClass().getName().equals(MC_ROUTE)) {
                LinearReferencedLineEditor editor = createEditor();
                editor.setMetaClassName(metaClassName);
                editor.setCidsBean(createBeanFromRoute(bean));
                addEditor(editor);
                cidsBeans.add(editor.getCidsBean());
            } else {
                return;
            }
        }
    }

    protected LinearReferencedLineEditor createEditor() {
        LinearReferencedLineEditor editor = new LinearReferencedLineEditor();
        editor.setMetaClassName(metaClassName);
        editor.setFromStationField(fromStationField);
        editor.setToStationField(toStationField);
        editor.setRealGeomField(realGeomField);
        return editor;
    }

    public boolean addLinearReferencedLineArrayEditorListener(LinearReferencedLineArrayEditorListener listener) {
        return listeners.add(listener);
    }

    public boolean removeLinearReferencedLineArrayEditorListener(LinearReferencedLineArrayEditorListener listener) {
        return listeners.remove(listener);
    }

    private void fireEditorAdded(LinearReferencedLineEditor source) {
        for (LinearReferencedLineArrayEditorListener listener : listeners) {
            listener.editorAdded(source);
        }
    }

    private void fireEditorRemoved(LinearReferencedLineEditor source) {
        for (LinearReferencedLineArrayEditorListener listener : listeners) {
            listener.editorRemoved(source);
        }
    }

    public Collection<CidsBean> getCidsBeans() {
        return cidsBeans;
    }

    private Color getNextColor() {
        colorIndex = (colorIndex + 1) % COLORS.length;
        return COLORS[colorIndex];
    }

    public void setCidsBeans(Collection<CidsBean> cidsBeans) {
        this.cidsBeans = cidsBeans;

        if (cidsBeans.size() > 0) {
            ((java.awt.GridLayout) jPanel1.getLayout()).setRows(cidsBeans.size());
        } else {
            ((java.awt.GridLayout) jPanel1.getLayout()).setRows(1);
        }
        
        for (CidsBean cidsBean : cidsBeans) {
            LinearReferencedLineEditor editor = createEditor();
            editor.setCidsBean(cidsBean);
            addEditor(editor);
        }
    }

    private void addEditor(LinearReferencedLineEditor editor) {
        editors.add(editor);

        Color color = getNextColor();
        editor.setLineColor(color);

        JPanel panItem = new JPanel(new GridBagLayout());
        panItem.setOpaque(false);
        GridBagConstraints gridBagConstraints;
        JButton btnRemove = new JButton();

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton button = (JButton) evt.getSource();
                LinearReferencedLineEditor editor = editorButtonMap.get(button);
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
        ((java.awt.GridLayout) jPanel1.getLayout()).setRows(jPanel1.getComponentCount());
        
        revalidate();
        fireEditorAdded(editor);
    }

    protected CidsBean createBeanFromRoute(CidsBean routeBean) {
        MetaClass segmentMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, metaClassName);
        MetaClass stationMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_STATION);
        MetaClass geomMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, MC_GEOM);

        CidsBean newBean = segmentMC.getEmptyInstance().getBean();
        CidsBean fromBean = stationMC.getEmptyInstance().getBean();
        CidsBean toBean = stationMC.getEmptyInstance().getBean();
        CidsBean geomBean = geomMC.getEmptyInstance().getBean();
        CidsBean fromGeomBean = geomMC.getEmptyInstance().getBean();
        CidsBean toGeomBean = geomMC.getEmptyInstance().getBean();

        try {
            fromBean.setProperty(PROP_ID, StationToMapRegistry.getNewId(MC_STATION));
            fromBean.setProperty(PROP_STATION_ROUTE, routeBean);
            fromBean.setProperty(PROP_STATION_VALUE, 0d);
            fromBean.setProperty(PROP_STATION_GEOM, fromGeomBean);

            toBean.setProperty(PROP_ID, StationToMapRegistry.getNewId(MC_STATION));
            toBean.setProperty(PROP_STATION_ROUTE, routeBean);
            toBean.setProperty(PROP_STATION_VALUE, ((Geometry) ((CidsBean) routeBean.getProperty(PROP_ROUTE_GEOM)).getProperty(PROP_GEOM_GEOFIELD)).getLength());
            toBean.setProperty(PROP_STATION_GEOM, toGeomBean);

            newBean.setProperty(PROP_ID, StationToMapRegistry.getNewId(metaClassName));
            newBean.setProperty(fromStationField, fromBean);
            newBean.setProperty(toStationField, toBean);
            newBean.setProperty(realGeomField, geomBean);
        } catch (Exception ex) {
                LOG.debug("Error while creating new bean", ex);
        }

        return newBean;
    }

    private void removeEditor(LinearReferencedLineEditor editor) {
        cidsBeans.remove(editor.getCidsBean());
        
        jPanel1.remove(editor.getParent());
        editor.dispose();

        if (cidsBeans.size() > 0) {
            ((java.awt.GridLayout) jPanel1.getLayout()).setRows(cidsBeans.size());
        } else {
            ((java.awt.GridLayout) jPanel1.getLayout()).setRows(1);
        }

        editors.remove(editor);

        revalidate();
        fireEditorRemoved(editor);
    }

    public void dispose() {
        for (LinearReferencedLineEditor editor : editors) {
            editor.dispose();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
        lblTitle.setText(org.openide.util.NbBundle.getMessage(LinearReferencedLineArrayEditor.class, "LinearReferencedLineArrayEditor.lblTitle.text")); // NOI18N
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
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTitle;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private de.cismet.tools.gui.SemiRoundedPanel semiRoundedPanel1;
    // End of variables declaration//GEN-END:variables

}
