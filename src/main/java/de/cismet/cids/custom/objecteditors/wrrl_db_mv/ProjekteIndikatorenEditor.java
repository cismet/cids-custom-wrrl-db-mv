/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * WkFgEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.math.BigDecimal;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ProjekteIndikatorenEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(
            ProjekteIndikatorenEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblEinheit;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblIndikator;
    private javax.swing.JLabel lblIndikator_nr;
    private javax.swing.JLabel lblValEinheit;
    private javax.swing.JLabel lblValIndikator;
    private javax.swing.JLabel lblValIndikator_nr;
    private javax.swing.JLabel lblValWert_char;
    private javax.swing.JLabel lblValWert_num;
    private javax.swing.JLabel lblWert_char;
    private javax.swing.JLabel lblWert_num;
    private javax.swing.JPanel panFooter;
    private javax.swing.JTextField txtValWert_char;
    private javax.swing.JTextField txtValWert_num;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ProjekteIndikatorenEditor object.
     */
    public ProjekteIndikatorenEditor() {
        this(false);
    }

    /**
     * Creates a new ProjekteIndikatorenEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public ProjekteIndikatorenEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();

        if (!readOnly) {
            lblValWert_char.setVisible(false);
            lblValWert_num.setVisible(false);
            txtValWert_char.setEditable(false);
            txtValWert_num.setEditable(false);
            try {
                new CidsBeanDropTarget(this);
            } catch (Exception ex) {
                if (log.isDebugEnabled()) {
                    log.debug("error while creating CidsBeanDropTarget", ex);
                }
            }
        } else {
            txtValWert_char.setVisible(false);
            txtValWert_num.setVisible(false);
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            final String wertChar = (String)cidsBean.getProperty("wert_char");
            final BigDecimal wertNum = (BigDecimal)cidsBean.getProperty("wert_num");

            if (wertChar != null) {
                lblValWert_char.setText(wertChar);
            } else {
                lblValWert_char.setText("");
            }

            if (wertNum != null) {
                lblValWert_num.setText(wertNum.toString());
            } else {
                lblValWert_num.setText("");
            }

            if (!readOnly) {
                txtValWert_char.setEditable(true);
                txtValWert_num.setEditable(true);
            }
        } else {
            lblFoot.setText("");
            lblValWert_char.setText(CidsBeanSupport.FIELD_NOT_SET);
            lblValWert_num.setText(CidsBeanSupport.FIELD_NOT_SET);
            lblValEinheit.setText(CidsBeanSupport.FIELD_NOT_SET);
            lblValIndikator.setText(CidsBeanSupport.FIELD_NOT_SET);
            lblValIndikator_nr.setText(CidsBeanSupport.FIELD_NOT_SET);
            txtValWert_char.setText("");
            txtValWert_num.setText("");
            txtValWert_char.setEditable(false);
            txtValWert_num.setEditable(false);
        }
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        lblIndikator = new javax.swing.JLabel();
        lblEinheit = new javax.swing.JLabel();
        lblWert_num = new javax.swing.JLabel();
        lblWert_char = new javax.swing.JLabel();
        lblIndikator_nr = new javax.swing.JLabel();
        lblValIndikator_nr = new javax.swing.JLabel();
        lblValIndikator = new javax.swing.JLabel();
        lblValEinheit = new javax.swing.JLabel();
        txtValWert_num = new javax.swing.JTextField();
        txtValWert_char = new javax.swing.JTextField();
        lblValWert_num = new javax.swing.JLabel();
        lblValWert_char = new javax.swing.JLabel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setMaximumSize(new java.awt.Dimension(700, 700));
        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        lblIndikator.setText("Indikator");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        add(lblIndikator, gridBagConstraints);

        lblEinheit.setText("Einheit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        add(lblEinheit, gridBagConstraints);

        lblWert_num.setText("Numerischer Wert");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        add(lblWert_num, gridBagConstraints);

        lblWert_char.setText("Textueller Wert");
        lblWert_char.setPreferredSize(new java.awt.Dimension(165, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        add(lblWert_char, gridBagConstraints);

        lblIndikator_nr.setText("Nummer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        add(lblIndikator_nr, gridBagConstraints);

        lblValIndikator_nr.setMinimumSize(new java.awt.Dimension(200, 20));
        lblValIndikator_nr.setPreferredSize(new java.awt.Dimension(200, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.indikator_schluessel.indikator_nr}"),
                lblValIndikator_nr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        add(lblValIndikator_nr, gridBagConstraints);

        lblValIndikator.setMinimumSize(new java.awt.Dimension(200, 20));
        lblValIndikator.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.indikator_schluessel.indikator}"),
                lblValIndikator,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.indikator_schluessel.indikator}"),
                lblValIndikator,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        add(lblValIndikator, gridBagConstraints);

        lblValEinheit.setMinimumSize(new java.awt.Dimension(200, 20));
        lblValEinheit.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.indikator_schluessel.einheit}"),
                lblValEinheit,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        add(lblValEinheit, gridBagConstraints);

        txtValWert_num.setMinimumSize(new java.awt.Dimension(200, 20));
        txtValWert_num.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wert_num}"),
                txtValWert_num,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtValWert_num.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtValWert_numActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        add(txtValWert_num, gridBagConstraints);

        txtValWert_char.setMinimumSize(new java.awt.Dimension(200, 20));
        txtValWert_char.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wert_char}"),
                txtValWert_char,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtValWert_char.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtValWert_charActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        add(txtValWert_char, gridBagConstraints);

        lblValWert_num.setMinimumSize(new java.awt.Dimension(200, 20));
        lblValWert_num.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        add(lblValWert_num, gridBagConstraints);

        lblValWert_char.setMinimumSize(new java.awt.Dimension(200, 20));
        lblValWert_char.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        add(lblValWert_char, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtValWert_numActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtValWert_numActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtValWert_numActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtValWert_charActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtValWert_charActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_txtValWert_charActionPerformed

    @Override
    public void dispose() {
        bindingGroup.unbind();
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

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        if ((cidsBean != null) && !readOnly) {
            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Indikator")) {
                    try {
                        cidsBean.setProperty("indikator_schluessel", bean);
                    } catch (Exception e) {
                        log.error(e, e);
                    }
                }
            }
        }
    }
}
