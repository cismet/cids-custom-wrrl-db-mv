/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import de.cismet.cids.server.search.MetaObjectNodeServerSearch;

import de.cismet.cids.tools.search.clientstuff.CidsWindowSearch;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = CidsWindowSearch.class)
public class MassnahmenHistory extends javax.swing.JPanel implements CidsWindowSearch {

    //~ Instance fields --------------------------------------------------------

    private MassnahmenHistoryListModel model = new MassnahmenHistoryListModel();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.CidsBeanList cidsBeanList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbClear;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenHistory.
     */
    public MassnahmenHistory() {
        initComponents();
        setName("Massnahmen Historie");
        cidsBeanList1.setModel(model);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        cidsBeanList1 = new de.cismet.cids.editors.CidsBeanList();
        toolbar = new javax.swing.JToolBar();
        jbClear = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setViewportView(cidsBeanList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);

        toolbar.setRollover(true);

        jbClear.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/wrrl_db_mv/util/gup/Filesystem-trash-empty-icon.png"))); // NOI18N
        jbClear.setToolTipText(org.openide.util.NbBundle.getMessage(
                MassnahmenHistory.class,
                "MassnahmenHistory.jbClear.toolTipText"));                                                              // NOI18N
        jbClear.setBorderPainted(false);
        jbClear.setFocusPainted(false);
        jbClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbClear.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbClearActionPerformed(evt);
                }
            });
        toolbar.add(jbClear);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        add(toolbar, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbClearActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbClearActionPerformed
        model.clean();
    }                                                                           //GEN-LAST:event_jbClearActionPerformed

    @Override
    public JComponent getSearchWindowComponent() {
        return this;
    }

    @Override
    public MetaObjectNodeServerSearch getServerSearch() {
        return null;
    }

    @Override
    public ImageIcon getIcon() {
        return new javax.swing.ImageIcon(getClass().getResource(
                    "/de/cismet/cids/custom/icons/wrrl-db-mv/raisePoly.png"));
    }
}
