/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * CcsRestrictioninfoEditor_.java
 *
 * Created on 30.11.2011, 11:58:00
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class CcsRestrictioninfoEditor_ extends javax.swing.JPanel implements CidsBeanRenderer {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor
        defaultCismapGeometryComboBoxEditor1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form CcsRestrictioninfoEditor_.
     */
    public CcsRestrictioninfoEditor_() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        defaultCismapGeometryComboBoxEditor1 =
            new de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.restrictiongeom}"),
                defaultCismapGeometryComboBoxEditor1,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        binding.setConverter(defaultCismapGeometryComboBoxEditor1.getConverter());
        bindingGroup.addBinding(binding);

        defaultCismapGeometryComboBoxEditor1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    defaultCismapGeometryComboBoxEditor1ActionPerformed(evt);
                }
            });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                jTextField1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(
                CcsRestrictioninfoEditor_.class,
                "CcsRestrictioninfoEditor_.jLabel1.text")); // NOI18N

        final org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                layout.createSequentialGroup().addContainerGap().add(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false).add(
                        layout.createSequentialGroup().add(jLabel1).addPreferredGap(
                            org.jdesktop.layout.LayoutStyle.RELATED).add(jTextField1)).add(
                        defaultCismapGeometryComboBoxEditor1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addContainerGap(415, Short.MAX_VALUE)));
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                layout.createSequentialGroup().addContainerGap().add(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(jLabel1).add(
                        jTextField1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                    org.jdesktop.layout.LayoutStyle.UNRELATED).add(
                    defaultCismapGeometryComboBoxEditor1,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap(190, Short.MAX_VALUE)));

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void defaultCismapGeometryComboBoxEditor1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_defaultCismapGeometryComboBoxEditor1ActionPerformed
// TODO add your handling code here:
    } //GEN-LAST:event_defaultCismapGeometryComboBoxEditor1ActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;
        final MetaClass mc = ClassCacheMultiple.getMetaClass("WRRL_DB_MV", "GEOM");
        defaultCismapGeometryComboBoxEditor1.setMetaClass(mc);
        bindingGroup.unbind();
        bindingGroup.bind();
    }

    @Override
    public void dispose() {
    }

    @Override
    public String getTitle() {
        return "RestrictionInfo";
    }

    @Override
    public void setTitle(final String title) {
    }
}
