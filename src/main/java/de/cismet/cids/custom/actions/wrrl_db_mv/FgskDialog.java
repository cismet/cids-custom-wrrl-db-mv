/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.actions.wrrl_db_mv;

import Sirius.navigator.method.MethodManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import com.vividsolutions.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingConstants;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.PFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.CreateLinearReferencedMarksListener;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;

import de.cismet.cismap.navigatorplugin.CidsFeature;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
public class FgskDialog extends javax.swing.JDialog {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(FgskDialog.class);
    private static final MetaClass MC_FGSK = ClassCacheMultiple.getMetaClass(
            CidsBeanSupport.DOMAIN_NAME,
            "fgsk_kartierabschnitt");
    private static final MetaClass MC_STATION = ClassCacheMultiple.getMetaClass(
            CidsBeanSupport.DOMAIN_NAME,
            LinearReferencingConstants.CN_STATION);
    private static final MetaClass MC_STATIONLINIE = ClassCacheMultiple.getMetaClass(
            CidsBeanSupport.DOMAIN_NAME,
            LinearReferencingConstants.CN_STATIONLINE);
    private static final MetaClass MC_GEOM = ClassCacheMultiple.getMetaClass(
            CidsBeanSupport.DOMAIN_NAME,
            LinearReferencingConstants.CN_GEOM);

    //~ Instance fields --------------------------------------------------------

    private MappingComponent mappingComponent = null;
    private final Collection<CidsBean> fgskBeans = new ArrayList<CidsBean>();
    private String interactionModeWhenFinished = ""; // NOI18N

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdOk;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblLeftCaption;
    private javax.swing.JLabel lblLeftCaption1;
    private javax.swing.JLabel lblLeftDescription;
    private javax.swing.JLabel lblLeftDescription1;
    private javax.swing.JLabel lblRightCaption;
    private javax.swing.JPanel panDesc;
    private javax.swing.JPanel panSettings;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form FgskDialog.
     *
     * @param  modal             DOCUMENT ME!
     * @param  mappingComponent  DOCUMENT ME!
     */
    public FgskDialog(final boolean modal, final MappingComponent mappingComponent) {
        super(StaticSwingTools.getParentFrame(mappingComponent), modal);

        initComponents();
        getRootPane().setDefaultButton(cmdOk);
        this.mappingComponent = mappingComponent;
        cmdOk.setEnabled(false);

        final SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    final CreateLinearReferencedMarksListener marksListener = (CreateLinearReferencedMarksListener)
                        mappingComponent.getInputListener(MappingComponent.LINEAR_REFERENCING);
                    final PFeature selectedPFeature = marksListener.getSelectedLinePFeature();

                    CidsBean routeBean = null;
                    if (selectedPFeature != null) {
                        final Feature feature = selectedPFeature.getFeature();
                        if ((feature != null) && (feature instanceof CidsFeature)) {
                            final CidsFeature cidsFeature = (CidsFeature)feature;
                            if (cidsFeature.getMetaClass().getName().equals(LinearReferencingConstants.CN_ROUTE)) {
                                routeBean = cidsFeature.getMetaObject().getBean();
                            }
                        }
                    }

                    fgskBeans.clear();
                    if (routeBean != null) {
                        final Geometry pointGeom = (Geometry)
                            ((CidsBean)routeBean.getProperty(LinearReferencingConstants.PROP_ROUTE_GEOM)).getProperty(
                                LinearReferencingConstants.PROP_GEOM_GEOFIELD);
                        final Double[] positions = marksListener.getMarkPositionsOfSelectedFeature();

                        LinearReferencedPointFeature pointBeforeFeature = null;
                        CidsBean fromPointBean = null;
                        int rowIndex = 0;

                        for (final Double position : positions) {
                            try {
                                // punkt geom bean erzeugen
                                final CidsBean pointGeomBean = MC_GEOM.getEmptyInstance().getBean();
                                pointGeomBean.setProperty(LinearReferencingConstants.PROP_GEOM_GEOFIELD, pointGeom);

                                // punkt erzeugen
                                final CidsBean toPointBean = MC_STATION.getEmptyInstance().getBean();
                                toPointBean.setProperty(
                                    LinearReferencingConstants.PROP_STATION_GEOM,
                                    pointGeomBean);
                                toPointBean.setProperty(LinearReferencingConstants.PROP_STATION_VALUE, position);
                                toPointBean.setProperty(LinearReferencingConstants.PROP_STATION_ROUTE, routeBean);

                                final LinearReferencedPointFeature pointFeature = new LinearReferencedPointFeature(
                                        position,
                                        pointGeom);
                                // FeatureRegistry.addFeatureToMap(pointFeature);
                                if (fromPointBean != null) {
                                    final LinearReferencedLineFeature lineFeature = new LinearReferencedLineFeature(
                                            pointBeforeFeature,
                                            pointFeature);

                                    // linien geom bean erzeugen
                                    final CidsBean lineGeomBean = MC_GEOM.getEmptyInstance().getBean();
                                    lineGeomBean.setProperty(
                                        LinearReferencingConstants.PROP_GEOM_GEOFIELD,
                                        lineFeature.getGeometry());
//                                    LOG.fatal(LinearReferencingConstants.PROP_GEOM_GEOFIELD + " "
//                                                + lineFeature.getGeometry().toText());

                                    // linie bean erzeugen
                                    final CidsBean lineBean = MC_STATIONLINIE.getEmptyInstance().getBean();
                                    lineBean.setProperty(
                                        LinearReferencingConstants.PROP_STATIONLINIE_FROM,
                                        fromPointBean);
                                    lineBean.setProperty(
                                        LinearReferencingConstants.PROP_STATIONLINIE_TO,
                                        toPointBean);
                                    lineBean.setProperty(
                                        LinearReferencingConstants.PROP_STATIONLINIE_GEOM,
                                        lineGeomBean);

                                    // fgsk bean erzeugen
                                    final CidsBean fgskBean = MC_FGSK.getEmptyInstance().getBean();
                                    fgskBean.setProperty("linie", lineBean);
                                    fgskBean.setProperty("bearbeiter", "TEST_FGSK");

                                    ((DefaultTableModel)jTable1.getModel()).addRow(
                                        new Object[] {
                                            ++rowIndex,
                                            ((Double)fromPointBean.getProperty(
                                                    LinearReferencingConstants.PROP_STATION_VALUE)).intValue(),
                                            ((Double)toPointBean.getProperty(
                                                    LinearReferencingConstants.PROP_STATION_VALUE)).intValue()
                                        });

                                    fgskBeans.add(fgskBean);
                                }

                                fromPointBean = toPointBean;
                                pointBeforeFeature = pointFeature;
                            } catch (Exception ex) {
                                LOG.error("error creating fgsk", ex);
                            }
                        }
                    }
                    return null;
                }

                @Override
                protected void done() {
                    cmdOk.setEnabled(!fgskBeans.isEmpty());
                }
            };
        sw.execute();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        panDesc = new javax.swing.JPanel();
        lblLeftCaption = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblLeftDescription = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lblLeftDescription1 = new javax.swing.JLabel();
        lblLeftCaption1 = new javax.swing.JLabel();
        cmdOk = new javax.swing.JButton();
        cmdCancel = new javax.swing.JButton();
        panSettings = new javax.swing.JPanel();
        lblRightCaption = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(FgskDialog.class, "FgskDialog.title")); // NOI18N

        panDesc.setBackground(new java.awt.Color(216, 228, 248));

        lblLeftCaption.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblLeftCaption.setText(org.openide.util.NbBundle.getMessage(
                FgskDialog.class,
                "FgskDialog.lblLeftCaption.text")); // NOI18N

        lblLeftDescription.setText(org.openide.util.NbBundle.getMessage(
                FgskDialog.class,
                "FgskDialog.lblLeftDescription.text")); // NOI18N

        lblLeftDescription1.setText(org.openide.util.NbBundle.getMessage(
                FgskDialog.class,
                "FgskDialog.lblLeftDescription1.text")); // NOI18N

        lblLeftCaption1.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblLeftCaption1.setText(org.openide.util.NbBundle.getMessage(
                FgskDialog.class,
                "FgskDialog.lblLeftCaption1.text")); // NOI18N

        final org.jdesktop.layout.GroupLayout panDescLayout = new org.jdesktop.layout.GroupLayout(panDesc);
        panDesc.setLayout(panDescLayout);
        panDescLayout.setHorizontalGroup(
            panDescLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                jSeparator3,
                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                438,
                Short.MAX_VALUE).add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                panDescLayout.createSequentialGroup().addContainerGap(426, Short.MAX_VALUE).add(jLabel5)
                            .addContainerGap()).add(
                panDescLayout.createSequentialGroup().addContainerGap().add(
                    panDescLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                        panDescLayout.createSequentialGroup().add(
                            jSeparator2,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                            414,
                            Short.MAX_VALUE).addContainerGap()).add(
                        panDescLayout.createSequentialGroup().add(lblLeftCaption).add(354, 354, 354)))).add(
                panDescLayout.createSequentialGroup().addContainerGap().add(
                    lblLeftDescription,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap(219, Short.MAX_VALUE)).add(
                panDescLayout.createSequentialGroup().addContainerGap().add(lblLeftCaption1).addContainerGap(
                    356,
                    Short.MAX_VALUE)).add(
                panDescLayout.createSequentialGroup().addContainerGap().add(
                    lblLeftDescription1,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap(215, Short.MAX_VALUE)));
        panDescLayout.setVerticalGroup(
            panDescLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                panDescLayout.createSequentialGroup().addContainerGap().add(lblLeftCaption).addPreferredGap(
                    org.jdesktop.layout.LayoutStyle.RELATED).add(
                    jSeparator2,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    2,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    org.jdesktop.layout.LayoutStyle.RELATED).add(
                    lblLeftDescription,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(18, 18, 18).add(lblLeftCaption1)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED).add(
                    lblLeftDescription1,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    org.jdesktop.layout.LayoutStyle.RELATED,
                    60,
                    Short.MAX_VALUE).add(jLabel5).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(
                    jSeparator3,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));

        cmdOk.setMnemonic('O');
        cmdOk.setText(org.openide.util.NbBundle.getMessage(FgskDialog.class, "FgskDialog.cmdOk.text")); // NOI18N
        cmdOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdOkActionPerformed(evt);
                }
            });

        cmdCancel.setMnemonic('A');
        cmdCancel.setText(org.openide.util.NbBundle.getMessage(FgskDialog.class, "FgskDialog.cmdCancel.text")); // NOI18N
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdCancelActionPerformed(evt);
                }
            });

        lblRightCaption.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblRightCaption.setText(org.openide.util.NbBundle.getMessage(
                FgskDialog.class,
                "FgskDialog.lblRightCaption.text")); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] { "#", "von", "bis" }) {

                Class[] types = new Class[] {
                        java.lang.Integer.class,
                        java.lang.Integer.class,
                        java.lang.Integer.class
                    };
                boolean[] canEdit = new boolean[] { false, false, false };

                @Override
                public Class getColumnClass(final int columnIndex) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                    return canEdit[columnIndex];
                }
            });
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(20);
        jScrollPane1.setViewportView(jTable1);

        final org.jdesktop.layout.GroupLayout panSettingsLayout = new org.jdesktop.layout.GroupLayout(panSettings);
        panSettings.setLayout(panSettingsLayout);
        panSettingsLayout.setHorizontalGroup(
            panSettingsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                panSettingsLayout.createSequentialGroup().addContainerGap().add(lblRightCaption).addContainerGap(
                    197,
                    Short.MAX_VALUE)).add(
                jSeparator4,
                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                271,
                Short.MAX_VALUE).add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                panSettingsLayout.createSequentialGroup().addContainerGap().add(
                    jSeparator1,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    259,
                    Short.MAX_VALUE)).add(
                panSettingsLayout.createSequentialGroup().addContainerGap().add(
                    jScrollPane1,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    259,
                    Short.MAX_VALUE)));
        panSettingsLayout.setVerticalGroup(
            panSettingsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                panSettingsLayout.createSequentialGroup().addContainerGap().add(lblRightCaption).addPreferredGap(
                    org.jdesktop.layout.LayoutStyle.RELATED).add(
                    jSeparator1,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    10,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    org.jdesktop.layout.LayoutStyle.RELATED).add(
                    jScrollPane1,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    218,
                    Short.MAX_VALUE).addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED).add(
                    jSeparator4,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));

        jProgressBar1.setVisible(false);
        jProgressBar1.setString(org.openide.util.NbBundle.getMessage(
                FgskDialog.class,
                "FgskDialog.jProgressBar1.string")); // NOI18N
        jProgressBar1.setStringPainted(true);

        final org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                layout.createSequentialGroup().add(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING).add(
                        layout.createSequentialGroup().addContainerGap().add(
                            jProgressBar1,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                            277,
                            Short.MAX_VALUE).addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED).add(
                            cmdCancel,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                            110,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                            org.jdesktop.layout.LayoutStyle.RELATED).add(
                            cmdOk,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                            107,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).add(
                        org.jdesktop.layout.GroupLayout.LEADING,
                        layout.createSequentialGroup().add(
                            panDesc,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                            241,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                            org.jdesktop.layout.LayoutStyle.UNRELATED).add(
                            panSettings,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                layout.createSequentialGroup().add(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING).add(
                        panSettings,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).add(
                        panDesc,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING).add(
                        layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(cmdOk).add(
                            cmdCancel)).add(
                        jProgressBar1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addContainerGap()));

        pack();
    } // </editor-fold>//GEN-END:initComponents
    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdOkActionPerformed
        final CreateLinearReferencedMarksListener marksListener = (CreateLinearReferencedMarksListener)
            mappingComponent.getInputListener(MappingComponent.LINEAR_REFERENCING);

        cmdOk.setEnabled(false);
        cmdCancel.setEnabled(false);
        jProgressBar1.setVisible(true);

        final SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

                private final Collection<Node> r = new ArrayList<Node>();

                @Override
                protected Void doInBackground() throws Exception {
                    jProgressBar1.setMaximum(marksListener.getMarkPositionsOfSelectedFeature().length);
                    int numOfPersisted = 0;
                    for (final CidsBean fgskBean : fgskBeans) {
                        jProgressBar1.setValue(numOfPersisted++);

                        try {
                            // FeatureRegistry.addFeatureToMap(lineFeature);
                            fgskBean.persist();

                            // node erzeugen
                            r.add(new MetaObjectNode(fgskBean));
                        } catch (Exception ex) {
                            LOG.error("error persisting fgsk", ex);
                        }
                    }
                    return null;
                }

                @Override
                protected void done() {
                    cmdOk.setEnabled(true);
                    cmdCancel.setEnabled(true);
                    jProgressBar1.setVisible(false);
                    dispose();
                    MethodManager.getManager().showSearchResults(r.toArray(new Node[r.size()]), false);
                }
            };
        sw.execute();
    } //GEN-LAST:event_cmdOkActionPerformed
    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdCancelActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdCancelActionPerformed
        mappingComponent.setInteractionMode(interactionModeWhenFinished);
        dispose();
    }                                                                             //GEN-LAST:event_cmdCancelActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getInteractionWhenFinished() {
        return interactionModeWhenFinished;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  interactionModeWhenFinished  DOCUMENT ME!
     */
    public void setInteractionModeWhenFinished(final String interactionModeWhenFinished) {
        this.interactionModeWhenFinished = interactionModeWhenFinished;
    }
}
