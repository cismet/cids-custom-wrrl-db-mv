/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.MapUtil;
import de.cismet.cids.custom.util.linearreferencing.LinearReferencingConstants;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.features.Feature;

import de.cismet.tools.CurrentStackTrace;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class LinearReferencedLineArrayEditor extends JPanel implements DisposableCidsBeanStore,
    EditorSaveListener,
    LinearReferencingConstants {

    //~ Instance fields --------------------------------------------------------

    HashMap<CidsBean, LinearReferencedLineEditor> editorMap = new HashMap<CidsBean, LinearReferencedLineEditor>();

    // private Collection<LinearReferencedLineEditor> editors = new ArrayList<LinearReferencedLineEditor>();
    private HashMap<JButton, LinearReferencedLineEditor> editorButtonMap =
        new HashMap<JButton, LinearReferencedLineEditor>();
    private Collection<LinearReferencedLineArrayEditorListener> listeners =
        new ArrayList<LinearReferencedLineArrayEditorListener>();

    private CidsBean cidsBean;
    private String arrayField;
    private String metaClassName;
    private String lineField;
    private String otherLinesFromQueryPart;
    private String otherLinesWhereQueryPart;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panDrop;
    private javax.swing.JPanel panLines;
    private de.cismet.tools.gui.RoundedPanel panRounded;
    private de.cismet.tools.gui.SemiRoundedPanel panSemiRounded;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form LinearReferencedLineArrayEditor.
     */
    public LinearReferencedLineArrayEditor() {
        initComponents();

        try {
            new CidsBeanDropTarget(panDrop);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while creating CidsBeanDropTarget");
            }
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  otherLinesFromQueryPart   DOCUMENT ME!
     * @param  otherLinesWhereQueryPart  DOCUMENT ME!
     */
    public void setOtherLinesQueryAddition(final String otherLinesFromQueryPart,
            final String otherLinesWhereQueryPart) {
        this.otherLinesFromQueryPart = otherLinesFromQueryPart;
        this.otherLinesWhereQueryPart = otherLinesWhereQueryPart;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getMetaClassName() {
        return metaClassName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  metaClassName  DOCUMENT ME!
     */
    private void setMetaClassName(final String metaClassName) {
        this.metaClassName = metaClassName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  metaClassName  DOCUMENT ME!
     * @param  arrayField     DOCUMENT ME!
     * @param  lineField      fromStationField DOCUMENT ME!
     */
    public final void setFields(final String metaClassName,
            final String arrayField,
            final String lineField) {
        setMetaClassName(metaClassName);
        setArrayField(arrayField);
        setLineField(lineField);
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
     * @return  DOCUMENT ME!
     */
    private String getLineField() {
        return lineField;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  lineField  DOCUMENT ME!
     */
    private void setLineField(final String lineField) {
        this.lineField = lineField;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  title  DOCUMENT ME!
     */
    public void setTitle(final String title) {
        lblTitle.setText(title);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected LinearReferencedLineEditor createEditor() {
        final LinearReferencedLineEditor editor = new LinearReferencedLineEditor();
        editor.setFields(getMetaClassName(), getLineField());
        return editor;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean addListener(final LinearReferencedLineArrayEditorListener listener) {
        return listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean removeListener(final LinearReferencedLineArrayEditorListener listener) {
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
    private Collection<CidsBean> getCidsBeans() {
        if (getCidsBean() == null) {
            return new ArrayList<CidsBean>();
        }
        return (Collection<CidsBean>)getCidsBean().getProperty(arrayField);
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;

        for (final CidsBean childBean : getCidsBeans()) {
            final LinearReferencedLineEditor editor = createEditor();
            editor.setOtherLinesQueryAddition(otherLinesFromQueryPart, otherLinesWhereQueryPart);
            editor.setCidsBean(childBean);
            addEditor(editor);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  editor  DOCUMENT ME!
     */
    private void addEditor(final LinearReferencedLineEditor editor) {
        editorMap.put(editor.getCidsBean(), editor);

        final JPanel panItem = new JPanel(new BorderLayout(5, 5));
        panItem.setOpaque(false);
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

        final JPanel panEast = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panEast.add(btnRemove);
        panEast.setOpaque(false);

        final JPanel panEditor = new JPanel(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        panEditor.setOpaque(false);
        panEditor.add(editor, gridBagConstraints);

        panItem.add(panEditor, BorderLayout.CENTER);
        panItem.add(panEast, BorderLayout.EAST);
        panItem.add(new JSeparator(), BorderLayout.SOUTH);

        editorButtonMap.put(btnRemove, editor);
        panLines.add(panItem);

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
        final MetaClass parentMC = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, metaClassName);
        final CidsBean newBean = parentMC.getEmptyInstance().getBean();

        LinearReferencedLineEditor.fillFromRoute(routeBean, newBean, getLineField());
        return newBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  editor  DOCUMENT ME!
     */
    private void removeEditor(final LinearReferencedLineEditor editor) {
        final Collection<CidsBean> cidsBeans = getCidsBeans();
        cidsBeans.remove(editor.getCidsBean());

        panLines.remove(editor.getParent().getParent());
        editor.dispose();
        editorMap.remove(editor.getCidsBean());

        revalidate();
        fireEditorRemoved(editor);
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void dispose() {
        for (final LinearReferencedLineEditor editor : editorMap.values()) {
            editor.dispose();
        }
        // TODO weg damit, gehört in editorclosed rein
        CIDSBEAN_CACHE.clear();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panRounded = new de.cismet.tools.gui.RoundedPanel();
        panSemiRounded = new de.cismet.tools.gui.SemiRoundedPanel();
        lblTitle = new javax.swing.JLabel();
        panLines = new javax.swing.JPanel();
        panDrop = new DropPanel();
        jLabel3 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(200, 100));
        setOpaque(false);
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        panRounded.setLayout(new java.awt.GridBagLayout());

        panSemiRounded.setBackground(new java.awt.Color(51, 51, 51));
        panSemiRounded.setMinimumSize(new java.awt.Dimension(55, 24));
        panSemiRounded.setPreferredSize(new java.awt.Dimension(0, 24));

        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineArrayEditor.class,
                "LinearReferencedLineArrayEditor.lblTitle.text_1")); // NOI18N
        panSemiRounded.add(lblTitle, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panRounded.add(panSemiRounded, gridBagConstraints);

        panLines.setMinimumSize(new java.awt.Dimension(100, 48));
        panLines.setOpaque(false);
        panLines.setLayout(new javax.swing.BoxLayout(panLines, javax.swing.BoxLayout.Y_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panRounded.add(panLines, gridBagConstraints);

        panDrop.setMinimumSize(new java.awt.Dimension(10, 24));
        panDrop.setOpaque(false);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(org.openide.util.NbBundle.getMessage(
                LinearReferencedLineArrayEditor.class,
                "LinearReferencedLineArrayEditor.jLabel3.text")); // NOI18N
        panDrop.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panRounded.add(panDrop, gridBagConstraints);

        add(panRounded);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        final CidsBean savedBean = event.getSavedBean();
        if (savedBean != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("SavedBean: " + event.getSavedBean().getMOString(), new CurrentStackTrace());
            }
            for (final CidsBean savedChildBean : (Collection<CidsBean>)savedBean.getProperty(arrayField)) {
                final LinearReferencedLineEditor editor = editorMap.get(savedChildBean);
                if (editor != null) {
                    editor.editorClosed(new EditorClosedEvent(event.getStatus(), savedChildBean));
                }
            }
        }
    }

    @Override
    public boolean prepareForSave() {
        boolean save = true;
        for (final LinearReferencedLineEditor editor : editorMap.values()) {
            save &= editor.prepareForSave();
        }
        return save;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<Feature> getZoomFeatures() {
        final Collection<Feature> zoomFeatures = new ArrayList<Feature>();
        addZoomFeaturesToCollection(zoomFeatures);
        return zoomFeatures;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  collection  DOCUMENT ME!
     */
    public void addZoomFeaturesToCollection(final Collection<Feature> collection) {
        for (final LinearReferencedLineEditor editor : editorMap.values()) {
            editor.addZoomFeaturesToCollection(collection);
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
            for (final CidsBean bean : beans) {
                if (bean.getMetaObject().getMetaClass().getName().equals(CN_ROUTE)) {
                    final LinearReferencedLineEditor editor = createEditor();
                    editor.setFields(getMetaClassName(), getLineField());
                    final CidsBean lineBean = createBeanFromRoute(bean);
                    editor.setOtherLinesQueryAddition(otherLinesFromQueryPart, otherLinesWhereQueryPart);
                    editor.setCidsBean(lineBean);
                    editor.updateRealGeoms();
                    addEditor(editor);
                    getCidsBeans().add(editor.getCidsBean());
                    MapUtil.zoomToFeatureCollection(editor.getZoomFeatures());
                } else {
                    return;
                }
            }
        }
    }
}
