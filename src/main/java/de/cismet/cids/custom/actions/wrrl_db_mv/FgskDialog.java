/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.actions.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.method.MethodManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;
import Sirius.server.newuser.User;

import com.vividsolutions.jts.geom.Geometry;

import java.awt.Color;
import java.awt.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.server.search.FgskIdByIntersectionSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WKKSearchBySingleStation;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

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
            WRRLUtil.DOMAIN_NAME,
            "fgsk_kartierabschnitt");
    private static final MetaClass MC_STATION = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            LinearReferencingConstants.CN_STATION);
    private static final MetaClass MC_STATIONLINIE = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            LinearReferencingConstants.CN_STATIONLINE);
    private static final MetaClass MC_GEOM = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            LinearReferencingConstants.CN_GEOM);

    //~ Instance fields --------------------------------------------------------

    private MappingComponent mappingComponent = null;
    private String interactionModeWhenFinished = ""; // NOI18N
    private final Double[] positions;
    private CidsBean routeBean = null;
    private final ArrayList<KartierAbschnitt> kartierAbschnitte = new ArrayList<KartierAbschnitt>();
    private boolean allValid;

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

        // positionen speichern
        final CreateLinearReferencedMarksListener marksListener = (CreateLinearReferencedMarksListener)
            mappingComponent.getInputListener(MappingComponent.LINEAR_REFERENCING);
        final PFeature selectedPFeature = marksListener.getSelectedLinePFeature();
        positions = marksListener.getMarkPositionsOfSelectedFeature();

        // route bestimmen
        if (selectedPFeature != null) {
            final Feature feature = selectedPFeature.getFeature();
            if ((feature != null) && (feature instanceof CidsFeature)) {
                final CidsFeature cidsFeature = (CidsFeature)feature;
                if (cidsFeature.getMetaClass().getName().equals(LinearReferencingConstants.CN_ROUTE)) {
                    routeBean = cidsFeature.getMetaObject().getBean();
                }
            }
        }

        // tabelle füllen
        if (routeBean != null) {
            Double fromPosition = null;
            for (final double position : positions) {
                final Double toPosition = position;
                if (fromPosition != null) {
                    kartierAbschnitte.add(new KartierAbschnitt(fromPosition.intValue(), toPosition.intValue()));
                }
                fromPosition = toPosition;
            }
        }

        allValid = true;
        for (int index = 0; index < kartierAbschnitte.size(); index++) {
            final KartierAbschnitt kartierAbschnitt = kartierAbschnitte.get(index);
            ((DefaultTableModel)jTable1.getModel()).addRow(
                new Object[] {
                    index,
                    kartierAbschnitt.getVon(),
                    kartierAbschnitt.getBis()
                });
            if (!kartierAbschnitt.isValid()) {
                allValid = false;
            }
        }

        cmdOk.setEnabled(positions.length > 1);
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
                    64,
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
        jTable1.setDefaultRenderer(Integer.class, new MyTableCellRenderer());
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(20);
        jScrollPane1.setViewportView(jTable1);

        final org.jdesktop.layout.GroupLayout panSettingsLayout = new org.jdesktop.layout.GroupLayout(panSettings);
        panSettings.setLayout(panSettingsLayout);
        panSettingsLayout.setHorizontalGroup(
            panSettingsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                panSettingsLayout.createSequentialGroup().addContainerGap().add(lblRightCaption).addContainerGap(
                    214,
                    Short.MAX_VALUE)).add(
                jSeparator4,
                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                288,
                Short.MAX_VALUE).add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                panSettingsLayout.createSequentialGroup().addContainerGap().add(
                    jSeparator1,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    276,
                    Short.MAX_VALUE)).add(
                panSettingsLayout.createSequentialGroup().addContainerGap().add(
                    jScrollPane1,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    276,
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
                    222,
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
                            294,
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
        if (!allValid) {
            final int returnOption = JOptionPane.showOptionDialog(
                    StaticSwingTools.getParentFrame(this),
                    "<html>Die Regeln zur Mindest- oder Höchstlänge<br/>von Kartierabschnitten werden verletzt.",
                    "Vorsicht",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new String[] { "Abbrechen", "Trotzdem Fortfahren" },
                    "Abbrechen");
            if (returnOption == 0) {
                cmdCancelActionPerformed(null);
                return;
            }
        }

        final List<Integer> histFgskAbschn = new ArrayList<Integer>();

        try {
            final CidsServerSearch search = new FgskIdByIntersectionSearch(routeBean.getMetaObject().getId(),
                    positions[0],
                    positions[positions.length - 1]);
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

            if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                for (final ArrayList l : resArray) {
                    histFgskAbschn.add((Integer)l.get(0));
                }
            }

            if (histFgskAbschn.size() > 0) {
                final int returnOption = JOptionPane.showOptionDialog(
                        StaticSwingTools.getParentFrame(this),
                        "<html>An der angegebenen Stelle existieren bereits Kartierabschnitt.",
                        "Vorsicht",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new String[] { "Abbrechen", "Fortfahren" },
                        "Abbrechen");
                if (returnOption == 0) {
                    cmdCancelActionPerformed(null);
                    return;
                }
            }
        } catch (ConnectionException e) {
        }

        cmdOk.setEnabled(false);
        cmdCancel.setEnabled(false);
        jProgressBar1.setVisible(true);

        final SwingWorker<Collection<Node>, Void> sw = new SwingWorker<Collection<Node>, Void>() {

                @Override
                protected Collection<Node> doInBackground() throws Exception {
                    final Collection<Node> r = new ArrayList<Node>();

                    jProgressBar1.setMaximum((positions.length * 2) - 1);
//                    jProgressBar1.setMaximum((positions.length * 2) - 1 + histFgskAbschn.size());
                    int numOfPersisted = 0;
                    final Geometry routeGeom = (Geometry)
                        ((CidsBean)routeBean.getProperty(LinearReferencingConstants.PROP_ROUTE_GEOM)).getProperty(
                            LinearReferencingConstants.PROP_GEOM_GEOFIELD);

                    // Abschnitte als historisch markieren
// final User u = SessionManager.getSession().getUser();
// final Double minPosition = min(positions);
// final Double maxPosition = max(positions);
// for (final Integer id : histFgskAbschn) {
// try {
// final MetaObject mo = SessionManager.getProxy()
// .getMetaObject(u, id, MC_FGSK.getId(), MC_FGSK.getDomain());
// final CidsBean histBean = mo.getBean();
// Double from = (Double)histBean.getProperty("linie.von.wert");
// Double till = (Double)histBean.getProperty("linie.bis.wert");
//
// if ((from != null) && (till != null)) {
// if (from > till) {
// final Double tmp = from;
// from = till;
// till = tmp;
// }
// if ((from < minPosition) && (till > maxPosition)) {
// // new fgsk object are contained in this old fgsk object
// // divide station line
// CidsBean newPart = CidsBeanSupport.cloneCidsBean(histBean, false);
// newPart.setProperty(
// "linie",
// CidsBeanSupport.cloneStationline((CidsBean)histBean.getProperty("linie")));
// changeStationLine((CidsBean)newPart.getProperty("linie"), from, minPosition);
// newPart.persist();
//
// // divide station line
// newPart = CidsBeanSupport.cloneCidsBean(histBean, false);
// newPart.setProperty(
// "linie",
// CidsBeanSupport.cloneStationline((CidsBean)histBean.getProperty("linie")));
// changeStationLine((CidsBean)newPart.getProperty("linie"), maxPosition, till);
// newPart.persist();
//
// final CidsBean statLine = (CidsBean)histBean.getProperty("linie");
// changeStationLine((CidsBean)histBean.getProperty("linie"),
// minPosition,
// maxPosition);
// statLine.persist();
// } else if (from < minPosition) {
// // divide station line
// final CidsBean newPart = CidsBeanSupport.cloneCidsBean(histBean, false);
// newPart.setProperty(
// "linie",
// CidsBeanSupport.cloneStationline((CidsBean)histBean.getProperty("linie")));
// changeStationLine((CidsBean)newPart.getProperty("linie"), from, minPosition);
// newPart.persist();
//
// // historic part
// final CidsBean statLine = (CidsBean)histBean.getProperty("linie");
// changeStationLine((CidsBean)histBean.getProperty("linie"), minPosition, till);
// statLine.persist();
// } else if (till > maxPosition) {
// // divide station line
// final CidsBean newPart = CidsBeanSupport.cloneCidsBean(histBean, false);
// newPart.setProperty(
// "linie",
// CidsBeanSupport.cloneStationline((CidsBean)histBean.getProperty("linie")));
// changeStationLine((CidsBean)newPart.getProperty("linie"), maxPosition, till);
// newPart.persist();
//
// // historic part
// final CidsBean statLine = (CidsBean)histBean.getProperty("linie");
// changeStationLine(statLine, from, maxPosition);
// statLine.persist();
// }
// }
// histBean.setProperty("historisch", Boolean.TRUE);
// histBean.persist();
// jProgressBar1.setValue(numOfPersisted++);
// } catch (Exception e) {
// LOG.error("Cannot adjust old object", e);
// }
// }

                    // stationen erzeugen
                    final Collection<CidsBean> stationenBeans = new ArrayList<CidsBean>();
                    for (final Double position : positions) {
                        try {
                            final Geometry pointGeom = LinearReferencedPointFeature.getPointOnLine(position, routeGeom);
                            // punkt geom bean erzeugen
                            final CidsBean pointGeomBean = MC_GEOM.getEmptyInstance().getBean();
                            pointGeomBean.setProperty(LinearReferencingConstants.PROP_GEOM_GEOFIELD, pointGeom);
                            // punkt erzeugen
                            final CidsBean toPointBean = MC_STATION.getEmptyInstance().getBean();
                            toPointBean.setProperty(LinearReferencingConstants.PROP_STATION_GEOM, pointGeomBean);
                            toPointBean.setProperty(
                                LinearReferencingConstants.PROP_STATION_VALUE,
                                ((Integer)position.intValue()).doubleValue());
                            toPointBean.setProperty(LinearReferencingConstants.PROP_STATION_ROUTE, routeBean);
                            stationenBeans.add(toPointBean.persist());
                            jProgressBar1.setValue(numOfPersisted++);
                        } catch (Exception ex) {
                            LOG.error("error while creating point bean", ex);
                        }
                    }

                    // station_linien erzeugen
                    CidsBean fromPointBean = null;
                    for (final CidsBean stationenBean : stationenBeans) {
                        final CidsBean toPointBean = stationenBean;

                        if (fromPointBean != null) {
                            final int fromValue =
                                ((Double)fromPointBean.getProperty(LinearReferencingConstants.PROP_STATION_VALUE))
                                        .intValue();
                            final int toValue =
                                ((Double)toPointBean.getProperty(LinearReferencingConstants.PROP_STATION_VALUE))
                                        .intValue();
                            final Geometry lineGeom = LinearReferencedLineFeature.createSubline(
                                    fromValue,
                                    toValue,
                                    routeGeom);

                            // linien geom bean erzeugen
                            final CidsBean lineGeomBean = MC_GEOM.getEmptyInstance().getBean();
                            lineGeomBean.setProperty(LinearReferencingConstants.PROP_GEOM_GEOFIELD, lineGeom);

                            // linie bean erzeugen
                            final CidsBean lineBean = MC_STATIONLINIE.getEmptyInstance().getBean();
                            lineBean.setProperty(LinearReferencingConstants.PROP_STATIONLINIE_FROM, fromPointBean);
                            lineBean.setProperty(LinearReferencingConstants.PROP_STATIONLINIE_TO, toPointBean);
                            lineBean.setProperty(LinearReferencingConstants.PROP_STATIONLINIE_GEOM, lineGeomBean);

                            // fgsk bean erzeugen
                            final CidsBean fgskBean = MC_FGSK.getEmptyInstance().getBean();
                            fgskBean.setProperty("linie", lineBean);
                            fgskBean.setProperty("erfassungsdatum", new java.sql.Timestamp(System.currentTimeMillis()));
                            fgskBean.setProperty("av_time", new java.sql.Timestamp(System.currentTimeMillis()));
                            fgskBean.setProperty("av_user", SessionManager.getSession().getUser().toString());
                            fgskBean.setProperty("gwk", routeBean.getProperty("gwk"));

                            try {
                                if ((lineBean != null) && (routeBean != null)) {
                                    final Double vonWert = (Double)lineBean.getProperty("von.wert");
                                    final Double bisWert = (Double)lineBean.getProperty("bis.wert");
                                    if ((vonWert != null) && (bisWert != null)) {
                                        final double wert = (bisWert + vonWert) / 2;
                                        final CidsServerSearch search = new WKKSearchBySingleStation(String.valueOf(
                                                    routeBean.getProperty("id")),
                                                String.valueOf(wert));
                                        final Collection res = SessionManager.getProxy()
                                                    .customServerSearch(SessionManager.getSession().getUser(), search);
                                        final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

                                        if ((resArray != null) && (resArray.size() > 0)
                                                    && (resArray.get(0).size() > 0)) {
                                            final Object o = resArray.get(0).get(0);

                                            if (o instanceof String) {
                                                fgskBean.setProperty("wkk", o.toString());
                                            }
                                        } else {
                                            LOG.error(
                                                "Server error in getWk_k(). Cids server search return null. " // NOI18N
                                                        + "See the server logs for further information");     // NOI18N
                                        }
                                    }
                                }
                            } catch (final Exception e) {
                                LOG.error("Error while determining the water body", e);
                            }

                            r.add(new MetaObjectNode(fgskBean.persist()));
                            jProgressBar1.setValue(numOfPersisted++);
                        }
                        fromPointBean = toPointBean;
                    }
                    return r;
                }

                @Override
                protected void done() {
                    try {
                        final Collection<Node> r = get();
                        MethodManager.getManager().showSearchResults(null, r.toArray(new Node[r.size()]), false);
                    } catch (Exception ex) {
                        LOG.error("error while creating beans", ex);
                    }
                    cmdOk.setEnabled(true);
                    cmdCancel.setEnabled(true);
                    jProgressBar1.setVisible(false);
                    dispose();
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
     * Determines the min value.
     *
     * @param   pos  a possible values
     *
     * @return  the min value
     */
    private Double min(final Double[] pos) {
        Double res = Double.MAX_VALUE;

        for (final double d : pos) {
            if (d < res) {
                res = d;
            }
        }

        return res;
    }

    /**
     * Determines the max value.
     *
     * @param   pos  a possible values
     *
     * @return  the max value
     */
    private Double max(final Double[] pos) {
        Double res = Double.MIN_VALUE;

        for (final double d : pos) {
            if (d > res) {
                res = d;
            }
        }

        return res;
    }

    /**
     * Change the length of a station line object.
     *
     * @param  stationLine  the station line object to change
     * @param  fromVal      the new from value
     * @param  tillVal      the new till value
     */
    private void changeStationLine(final CidsBean stationLine, final Double fromVal, final Double tillVal) {
        try {
            Double from = (Double)stationLine.getProperty("von.wert");
            Double till = (Double)stationLine.getProperty("bis.wert");
            CidsBean fromStation = (CidsBean)stationLine.getProperty("von");
            CidsBean tillStation = (CidsBean)stationLine.getProperty("bis");
            final boolean rightOrder = from <= till;
            final Geometry routeGeom = (Geometry)
                ((CidsBean)routeBean.getProperty(LinearReferencingConstants.PROP_ROUTE_GEOM)).getProperty(
                    LinearReferencingConstants.PROP_GEOM_GEOFIELD);

            if (!rightOrder) {
                final Double tmp = from;
                from = till;
                till = tmp;
                final CidsBean tmpStat = fromStation;
                fromStation = tillStation;
                tillStation = tmpStat;
            }

            if (!fromVal.equals(from)) {
                final CidsBean newFromStat = CidsBeanSupport.cloneStation(fromStation);
                final Geometry pointGeom = LinearReferencedPointFeature.getPointOnLine(fromVal, routeGeom);
                newFromStat.setProperty("wert", fromVal);
                newFromStat.setProperty("real_point.geo_field", pointGeom);
                final Geometry lineGeom = LinearReferencedLineFeature.createSubline(
                        fromVal,
                        tillVal,
                        routeGeom);
                stationLine.setProperty("geom.geo_field", lineGeom);

                if (rightOrder) {
                    stationLine.setProperty("von", newFromStat);
                } else {
                    stationLine.setProperty("bis", newFromStat);
                }
            }

            if (!tillVal.equals(till)) {
                final CidsBean newTillStat = CidsBeanSupport.cloneStation(tillStation);
                final Geometry pointGeom = LinearReferencedPointFeature.getPointOnLine(tillVal, routeGeom);
                newTillStat.setProperty("wert", tillVal);
                newTillStat.setProperty("real_point.geo_field", pointGeom);
                final Geometry lineGeom = LinearReferencedLineFeature.createSubline(
                        fromVal,
                        tillVal,
                        routeGeom);
                stationLine.setProperty("geom.geo_field", lineGeom);

                if (rightOrder) {
                    stationLine.setProperty("bis", newTillStat);
                } else {
                    stationLine.setProperty("von", newTillStat);
                }
            }
        } catch (Exception e) {
            LOG.error("Error while dividing station line", e);
        }
    }

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

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class KartierAbschnitt {

        //~ Instance fields ----------------------------------------------------

        private final int von;
        private final int bis;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new KartierAbschnitt object.
         *
         * @param  von  DOCUMENT ME!
         * @param  bis  DOCUMENT ME!
         */
        public KartierAbschnitt(final int von, final int bis) {
            this.von = von;
            this.bis = bis;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getBis() {
            return bis;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getVon() {
            return von;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public boolean isValid() {
            return !isTooShort() && !isTooLong();
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public boolean isTooShort() {
            return getDistance() < 50;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public boolean isTooLong() {
            return getDistance() > 400;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getDistance() {
            return (int)Math.abs(von - bis);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class MyTableCellRenderer extends JLabel implements TableCellRenderer {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getTableCellRendererComponent(final JTable table,
                final Object value,
                final boolean isSelected,
                final boolean hasFocus,
                final int row,
                final int column) {
            final KartierAbschnitt kartierAbschnitt = kartierAbschnitte.get(row);
            String tooltip = null;
            final int distance = kartierAbschnitt.getDistance();
            if (kartierAbschnitt.isTooShort()) {
                tooltip = "<html>Mit " + distance
                            + " Metern ist dieser Kartierabschnitt zu kurz,<br/>er sollte mindestens 50 Meter lang sein.";
            } else if (kartierAbschnitt.isTooLong()) {
                tooltip = "<html>Mit " + distance
                            + " Metern ist dieser Kartierabschnitt zu lang,<br/>er sollte höchstens 400 Meter lang sein.";
            }
            setToolTipText(tooltip);
            if (!kartierAbschnitt.isValid()) {
                setOpaque(true);
                setBackground(Color.RED);
                setForeground(Color.WHITE);
            } else {
                setBackground(null);
                setForeground(null);
            }
            setText(value.toString());
            return this;
        }
    }
}
