/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * GupMassnahmeSohle.java
 *
 * Created on 08.09.2016
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;

import org.apache.log4j.Logger;

import java.awt.event.KeyEvent;

import java.util.Collection;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.MeasureTypeCodeRenderer;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class SimFlaechenerwerbspreisEditor extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger LOG = Logger.getLogger(SimFlaechenerwerbspreisEditor.class);
    private static final MetaClass FGSK_FLAECHENNUTZUNG_MC;

    static {
        FGSK_FLAECHENNUTZUNG_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "fgsk_flaechennutzung");
    }

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDe_meas;
    private javax.swing.JButton btnMeasAbort;
    private javax.swing.JButton btnMeasOk;
    private javax.swing.JButton btnRemDeMeas;
    private javax.swing.JComboBox cbFlN;
    private javax.swing.JDialog dlgMeas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblArten;
    private javax.swing.JLabel lblMeasCataloge;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPreis;
    private javax.swing.JList lstNutzung;
    private javax.swing.JPanel panFlN;
    private javax.swing.JPanel panMenButtonsMeas;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPreis;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public SimFlaechenerwerbspreisEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public SimFlaechenerwerbspreisEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();

        if (readOnly) {
            RendererTools.makeReadOnly(txtName);
            RendererTools.makeReadOnly(txtPreis);
            panFlN.setVisible(false);
        }
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        dlgMeas = new JDialog(StaticSwingTools.getParentFrame(this));
        lblMeasCataloge = new javax.swing.JLabel();
        cbFlN = new ScrollableComboBox(FGSK_FLAECHENNUTZUNG_MC, true, false);
        panMenButtonsMeas = new javax.swing.JPanel();
        btnMeasAbort = new javax.swing.JButton();
        btnMeasOk = new javax.swing.JButton();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblPreis = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstNutzung = new javax.swing.JList();
        lblArten = new javax.swing.JLabel();
        txtPreis = new javax.swing.JTextField();
        panFlN = new javax.swing.JPanel();
        btnAddDe_meas = new javax.swing.JButton();
        btnRemDeMeas = new javax.swing.JButton();

        dlgMeas.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblMeasCataloge.setText(org.openide.util.NbBundle.getMessage(
                SimFlaechenerwerbspreisEditor.class,
                "SimFlaechenerwerbspreisEditor.lblMeasCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas.getContentPane().add(lblMeasCataloge, gridBagConstraints);

        cbFlN.setMinimumSize(new java.awt.Dimension(400, 18));
        cbFlN.setPreferredSize(new java.awt.Dimension(400, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas.getContentPane().add(cbFlN, gridBagConstraints);

        panMenButtonsMeas.setLayout(new java.awt.GridBagLayout());

        btnMeasAbort.setText(org.openide.util.NbBundle.getMessage(
                SimFlaechenerwerbspreisEditor.class,
                "SimFlaechenerwerbspreisEditor.btnMeasAbort.text")); // NOI18N
        btnMeasAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMeasAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas.add(btnMeasAbort, gridBagConstraints);

        btnMeasOk.setText(org.openide.util.NbBundle.getMessage(
                SimFlaechenerwerbspreisEditor.class,
                "SimFlaechenerwerbspreisEditor.btnMeasOk.text")); // NOI18N
        btnMeasOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMeasOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMeasOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMeasOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMeasOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas.add(btnMeasOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas.getContentPane().add(panMenButtonsMeas, gridBagConstraints);

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(994, 400));
        setLayout(new java.awt.GridBagLayout());

        lblName.setText(org.openide.util.NbBundle.getMessage(
                SimFlaechenerwerbspreisEditor.class,
                "SimFlaechenerwerbspreisEditor.lblName.text")); // NOI18N
        lblName.setMaximumSize(new java.awt.Dimension(170, 17));
        lblName.setMinimumSize(new java.awt.Dimension(170, 17));
        lblName.setPreferredSize(new java.awt.Dimension(215, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 15, 5, 5);
        add(lblName, gridBagConstraints);

        txtName.setMinimumSize(new java.awt.Dimension(400, 25));
        txtName.setPreferredSize(new java.awt.Dimension(450, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 5, 5);
        add(txtName, gridBagConstraints);

        lblPreis.setText(org.openide.util.NbBundle.getMessage(
                SimFlaechenerwerbspreisEditor.class,
                "SimFlaechenerwerbspreisEditor.lblPreis.text")); // NOI18N
        lblPreis.setMaximumSize(new java.awt.Dimension(170, 17));
        lblPreis.setMinimumSize(new java.awt.Dimension(170, 17));
        lblPreis.setPreferredSize(new java.awt.Dimension(215, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        add(lblPreis, gridBagConstraints);

        lstNutzung.setModel(new javax.swing.AbstractListModel() {

                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

                @Override
                public int getSize() {
                    return strings.length;
                }
                @Override
                public Object getElementAt(final int i) {
                    return strings[i];
                }
            });

        final org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.fgsk_flaechennutzung}");
        final org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstNutzung);
        bindingGroup.addBinding(jListBinding);

        lstNutzung.addKeyListener(new java.awt.event.KeyAdapter() {

                @Override
                public void keyTyped(final java.awt.event.KeyEvent evt) {
                    lstNutzungKeyTyped(evt);
                }
                @Override
                public void keyPressed(final java.awt.event.KeyEvent evt) {
                    lstNutzungKeyPressed(evt);
                }
            });
        jScrollPane1.setViewportView(lstNutzung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        add(jScrollPane1, gridBagConstraints);

        lblArten.setText(org.openide.util.NbBundle.getMessage(
                SimFlaechenerwerbspreisEditor.class,
                "SimFlaechenerwerbspreisEditor.lblArten.text")); // NOI18N
        lblArten.setMaximumSize(new java.awt.Dimension(170, 17));
        lblArten.setMinimumSize(new java.awt.Dimension(170, 17));
        lblArten.setPreferredSize(new java.awt.Dimension(215, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        add(lblArten, gridBagConstraints);

        txtPreis.setMinimumSize(new java.awt.Dimension(400, 25));
        txtPreis.setPreferredSize(new java.awt.Dimension(450, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.preis}"),
                txtPreis,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(txtPreis, gridBagConstraints);

        panFlN.setOpaque(false);
        panFlN.setLayout(new java.awt.GridBagLayout());

        btnAddDe_meas.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddDe_meas.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddDe_measActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panFlN.add(btnAddDe_meas, gridBagConstraints);

        btnRemDeMeas.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemDeMeas.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemDeMeasActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panFlN.add(btnRemDeMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        add(panFlN, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstNutzungKeyTyped(final java.awt.event.KeyEvent evt) { //GEN-FIRST:event_lstNutzungKeyTyped
    }                                                                    //GEN-LAST:event_lstNutzungKeyTyped

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstNutzungKeyPressed(final java.awt.event.KeyEvent evt) { //GEN-FIRST:event_lstNutzungKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_DELETE) {
            evt.consume();
            if (!readOnly) {
                final List<CidsBean> artenBeans = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "arten");
                final Object[] o = lstNutzung.getSelectedValues();

                for (final Object tmp : o) {
                    artenBeans.remove((CidsBean)tmp);
                }
            }
        }
    } //GEN-LAST:event_lstNutzungKeyPressed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddDe_measActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddDe_measActionPerformed
        dlgMeas.setSize(450, 150);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgMeas, true);
    }                                                                                 //GEN-LAST:event_btnAddDe_measActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemDeMeasActionPerformed(final java.awt.event.ActionEvent evt) {      //GEN-FIRST:event_btnRemDeMeasActionPerformed
        final Object selection = lstNutzung.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll die Flächennutzung '"
                            + selection.toString()
                            + "' wirklich entfernt werden?",
                    "Flächennutzung entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("fgsk_flaechennutzung"); // NOI18N
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                     //GEN-LAST:event_btnRemDeMeasActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMeasAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMeasAbortActionPerformed
        dlgMeas.setVisible(false);
    }                                                                                //GEN-LAST:event_btnMeasAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMeasOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMeasOkActionPerformed
        final Object selection = cbFlN.getSelectedItem();
        if (selection instanceof CidsBean) {
            final Thread t = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            final CidsBean selectedBean = (CidsBean)selection;
                            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(
                                    cidsBean,
                                    "fgsk_flaechennutzung"); // NOI18N
                            if (colToAdd != null) {
                                if (!colToAdd.contains(selectedBean)) {
                                    colToAdd.add(selectedBean);
                                }
                            }
                        }
                    });

            t.start();
        }

        dlgMeas.setVisible(false);
    } //GEN-LAST:event_btnMeasOkActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Flächenerwerbspreis: " + ((cidsBean.getProperty("name") != null) ? cidsBean.toString() : "");
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
    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "x",
            "vermeidungsgruppe",
            1,
            1280,
            1024);
    }
}