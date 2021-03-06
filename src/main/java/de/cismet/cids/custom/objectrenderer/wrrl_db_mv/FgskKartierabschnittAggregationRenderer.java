/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittEditor;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanAggregationRenderer;

import de.cismet.tools.gui.TitleComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public class FgskKartierabschnittAggregationRenderer extends javax.swing.JPanel implements CidsBeanAggregationRenderer,
    TitleComponentProvider {

    //~ Instance fields --------------------------------------------------------

    private final transient FgskKartierabschnittAggregationTitleComponent titleComponent;

    private transient Collection<CidsBean> cidsBeans;
//    private FgskKartierabschnittRenderer kartierabschnittRenderer;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.FgskKartierabschnittRenderer fgskKartierabschnittRenderer1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList jlKartierListe;
    private javax.swing.JLabel lblLab;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form FgskKartierabschnittAggregationRenderer.
     */
    public FgskKartierabschnittAggregationRenderer() {
        initComponents();
        titleComponent = new FgskKartierabschnittAggregationTitleComponent();
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
        jlKartierListe = new javax.swing.JList();
        jSeparator1 = new javax.swing.JSeparator();
        lblLab = new javax.swing.JLabel();
        fgskKartierabschnittRenderer1 =
            new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.FgskKartierabschnittRenderer();

        setMinimumSize(new java.awt.Dimension(1100, 870));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1100, 870));
        setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMaximumSize(new java.awt.Dimension(280, 100));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(280, 100));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(280, 100));

        jlKartierListe.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    jlKartierListeValueChanged(evt);
                }
            });
        jScrollPane1.setViewportView(jlKartierListe);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(jSeparator1, gridBagConstraints);

        lblLab.setFont(new java.awt.Font("DejaVu Sans", 1, 13));
        lblLab.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittAggregationRenderer.class,
                "FgskKartierabschnittAggregationRenderer.lblLab.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblLab, gridBagConstraints);

        fgskKartierabschnittRenderer1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        add(fgskKartierabschnittRenderer1, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jlKartierListeValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_jlKartierListeValueChanged
        if (!evt.getValueIsAdjusting()) {
            if (jlKartierListe.getSelectedValue() instanceof CidsBean) {
                fgskKartierabschnittRenderer1.dispose();
                fgskKartierabschnittRenderer1.setCidsBean((CidsBean)jlKartierListe.getSelectedValue());
//                if (kartierabschnittRenderer == null) {
//                    remove(lblNoSelection);
//                } else {
//                    kartierabschnittRenderer.dispose();
//                    remove(kartierabschnittRenderer);
//                }
//                kartierabschnittRenderer = new FgskKartierabschnittRenderer();
//                final GridBagConstraints constraint = new GridBagConstraints(
//                        0,
//                        3,
//                        1,
//                        1,
//                        1.0,
//                        1.0,
//                        GridBagConstraints.NORTHWEST,
//                        GridBagConstraints.NONE,
//                        new Insets(10, 10, 10, 10),
//                        0,
//                        0);
//                add(kartierabschnittRenderer, constraint);
//                kartierabschnittRenderer.setOpaque(false);
//                kartierabschnittRenderer.setCidsBean((CidsBean)jlKartierListe.getSelectedValue());
            }
        }
    }                                                                                         //GEN-LAST:event_jlKartierListeValueChanged

    @Override
    public Collection<CidsBean> getCidsBeans() {
        return cidsBeans;
    }

    @Override
    public void setCidsBeans(final Collection<CidsBean> beans) {
        this.cidsBeans = beans;
        this.titleComponent.setCidsBeans(cidsBeans);

        if ((beans != null) && (beans.size() > 0)) {
            jlKartierListe.setModel(new CustomModel(beans));
        }
    }

    @Override
    public void dispose() {
        // not needed
    }

    @Override
    public String getTitle() {
        // not needed, because of TitleComponentProvider
        return null;
    }

    @Override
    public void setTitle(final String title) {
        // not needed, because of TitleComponentProvider
    }

    @Override
    public JComponent getTitleComponent() {
        return titleComponent;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomModel implements ListModel {

        //~ Instance fields ----------------------------------------------------

        private final List<CidsBean> beans = new ArrayList<CidsBean>();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomModel object.
         *
         * @param  beans  DOCUMENT ME!
         */
        public CustomModel(final Collection<CidsBean> beans) {
            this.beans.addAll(beans);
            Collections.sort(this.beans, new BeanComparator());
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int getSize() {
            return this.beans.size();
        }

        @Override
        public Object getElementAt(final int index) {
            return beans.get(index);
        }

        @Override
        public void addListDataListener(final ListDataListener l) {
        }

        @Override
        public void removeListDataListener(final ListDataListener l) {
        }

        //~ Inner Classes ------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @version  $Revision$, $Date$
         */
        private class BeanComparator implements Comparator<CidsBean> {

            //~ Methods --------------------------------------------------------

            @Override
            public int compare(final CidsBean o1, final CidsBean o2) {
                final Long gwk1 = (Long)o1.getProperty("linie.von.route.gwk");
                final Long gwk2 = (Long)o2.getProperty("linie.von.route.gwk");

                if ((gwk1 != null) && (gwk2 != null) && !gwk1.equals(gwk2)) {
                    return gwk1.compareTo(gwk2);
                } else if (gwk1 == null) {
                    return -1;
                } else if (gwk2 == null) {
                    return 1;
                } else {
                    final Double von1 = (Double)o1.getProperty("linie.von.wert");
                    final Double von2 = (Double)o2.getProperty("linie.von.wert");

                    if ((von1 != null) && (von2 != null)) {
                        return von1.compareTo(von2);
                    } else if (von1 == null) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        }
    }
}
