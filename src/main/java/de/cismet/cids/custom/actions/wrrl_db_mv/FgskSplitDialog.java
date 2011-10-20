/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 jruiz
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * FgskSplitDialog.java
 *
 * Created on 19.10.2011, 18:52:26
 */
package de.cismet.cids.custom.actions.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.method.MethodManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.log4j.Logger;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JSpinner;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.linearreferencing.LinearReferencingConstants;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedLineFeature;
import de.cismet.cismap.commons.gui.piccolo.eventlistener.LinearReferencedPointFeature;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class FgskSplitDialog extends javax.swing.JDialog {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger LOG = Logger.getLogger(LinearReferencingConstants.class);

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
    private final CidsBean fgskBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JLabel lblFromIcon;
    private javax.swing.JLabel lblLeftCaption;
    private javax.swing.JLabel lblLeftCaption1;
    private javax.swing.JLabel lblLeftCaption2;
    private javax.swing.JLabel lblLeftDescription;
    private javax.swing.JLabel lblLeftDescription1;
    private javax.swing.JLabel lblLeftDescription2;
    private javax.swing.JLabel lblRightCaption;
    private javax.swing.JLabel lblToIcon;
    private javax.swing.JPanel panDesc;
    private javax.swing.JPanel panLine;
    private javax.swing.JPanel panSettings;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new FgskSplitDialog object.
     *
     * @param  fgskBean          DOCUMENT ME!
     * @param  mappingComponent  DOCUMENT ME!
     */
    public FgskSplitDialog(final CidsBean fgskBean, final MappingComponent mappingComponent) {
        super(StaticSwingTools.getParentFrame(mappingComponent), true);

        this.fgskBean = fgskBean;

        initComponents();
        getRootPane().setDefaultButton(cmdOk);
        this.mappingComponent = mappingComponent;
        // cmdOk.setEnabled(false);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int getStationMinimum() {
        return
            ((Double)fgskBean.getProperty(
                    "linie."
                            + LinearReferencingConstants.PROP_STATIONLINIE_FROM
                            + "."
                            + LinearReferencingConstants.PROP_STATION_VALUE)).intValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int getStationMaximum() {
        return
            ((Double)fgskBean.getProperty(
                    "linie."
                            + LinearReferencingConstants.PROP_STATIONLINIE_TO
                            + "."
                            + LinearReferencingConstants.PROP_STATION_VALUE)).intValue();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        panDesc = new javax.swing.JPanel();
        lblLeftCaption = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblLeftDescription = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lblLeftCaption1 = new javax.swing.JLabel();
        lblLeftDescription1 = new javax.swing.JLabel();
        lblLeftCaption2 = new javax.swing.JLabel();
        lblLeftDescription2 = new javax.swing.JLabel();
        cmdOk = new javax.swing.JButton();
        cmdCancel = new javax.swing.JButton();
        panSettings = new javax.swing.JPanel();
        lblRightCaption = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jSlider1 = new javax.swing.JSlider();
        lblFromIcon = new javax.swing.JLabel();
        panLine = new javax.swing.JPanel();
        lblToIcon = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panDesc.setBackground(new java.awt.Color(216, 228, 248));

        lblLeftCaption.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblLeftCaption.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.lblLeftCaption.text")); // NOI18N

        lblLeftDescription.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.lblLeftDescription.text")); // NOI18N

        lblLeftCaption1.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblLeftCaption1.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.lblLeftCaption1.text")); // NOI18N

        lblLeftDescription1.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.lblLeftDescription1.text")); // NOI18N

        lblLeftCaption2.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblLeftCaption2.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.lblLeftCaption2.text")); // NOI18N

        lblLeftDescription2.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.lblLeftDescription2.text")); // NOI18N

        final javax.swing.GroupLayout panDescLayout = new javax.swing.GroupLayout(panDesc);
        panDesc.setLayout(panDescLayout);
        panDescLayout.setHorizontalGroup(
            panDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panDescLayout.createSequentialGroup().addContainerGap().addGroup(
                    panDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                        panDescLayout.createSequentialGroup().addComponent(
                            lblLeftDescription1,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                            jSeparator2,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            247,
                            Short.MAX_VALUE).addContainerGap()).addGroup(
                        panDescLayout.createSequentialGroup().addComponent(lblLeftCaption).addGap(354, 354, 354))
                                .addGroup(
                                    panDescLayout.createSequentialGroup().addComponent(lblLeftCaption1).addContainerGap(
                                        71,
                                        Short.MAX_VALUE)).addGroup(
                        panDescLayout.createSequentialGroup().addComponent(
                            lblLeftDescription,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE)).addGroup(
                        panDescLayout.createSequentialGroup().addGroup(
                            panDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                                lblLeftCaption2).addComponent(
                                lblLeftDescription2,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                            panDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                panDescLayout.createSequentialGroup().addPreferredGap(
                                    javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                    253,
                                    Short.MAX_VALUE).addComponent(jLabel5).addContainerGap()).addGroup(
                                panDescLayout.createSequentialGroup().addComponent(
                                    jSeparator3,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    241,
                                    javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE)))))));
        panDescLayout.setVerticalGroup(
            panDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panDescLayout.createSequentialGroup().addContainerGap().addComponent(lblLeftCaption).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    panDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                        jSeparator2,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        2,
                        javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(
                        lblLeftDescription1,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addComponent(lblLeftCaption1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    lblLeftDescription,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(
                    panDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                        panDescLayout.createSequentialGroup().addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                            108,
                            Short.MAX_VALUE).addComponent(jLabel5).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                            jSeparator3,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            6,
                            javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(
                        panDescLayout.createSequentialGroup().addGap(18, 18, 18).addComponent(lblLeftCaption2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(
                            lblLeftDescription2,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()))));

        cmdOk.setMnemonic('O');
        cmdOk.setText(org.openide.util.NbBundle.getMessage(FgskSplitDialog.class, "FgskSplitDialog.cmdOk.text")); // NOI18N
        cmdOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdOkActionPerformed(evt);
                }
            });

        cmdCancel.setMnemonic('A');
        cmdCancel.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.cmdCancel.text")); // NOI18N
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdCancelActionPerformed(evt);
                }
            });

        lblRightCaption.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblRightCaption.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.lblRightCaption.text")); // NOI18N

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(String.valueOf(getStationMinimum()));
        jLabel1.setMaximumSize(new java.awt.Dimension(80, 16));
        jLabel1.setMinimumSize(new java.awt.Dimension(80, 16));
        jLabel1.setPreferredSize(new java.awt.Dimension(80, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(String.valueOf(getStationMaximum()));
        jLabel2.setMaximumSize(new java.awt.Dimension(80, 16));
        jLabel2.setMinimumSize(new java.awt.Dimension(80, 16));
        jLabel2.setPreferredSize(new java.awt.Dimension(80, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        jPanel1.add(jLabel2, gridBagConstraints);

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
        jSpinner1.setEditor(new javax.swing.JSpinner.NumberEditor(jSpinner1, ""));
        jSpinner1.setMinimumSize(new java.awt.Dimension(80, 26));
        jSpinner1.setPreferredSize(new java.awt.Dimension(80, 26));
        jSpinner1.setValue((int)getStationMinimum() + Math.abs((getStationMaximum() - getStationMinimum()) / 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        jPanel1.add(jSpinner1, gridBagConstraints);
        ((javax.swing.SpinnerNumberModel)jSpinner1.getModel()).setMinimum(getStationMinimum());
        ((javax.swing.SpinnerNumberModel)jSpinner1.getModel()).setMaximum(getStationMaximum());

        ((JSpinner.DefaultEditor)jSpinner1.getEditor()).getTextField()
                .getDocument()
                .addDocumentListener(new DocumentListener() {

                        @Override
                        public void insertUpdate(final DocumentEvent de) {
                            spinnerChanged();
                        }

                        @Override
                        public void removeUpdate(final DocumentEvent de) {
                            spinnerChanged();
                        }

                        @Override
                        public void changedUpdate(final DocumentEvent de) {
                            spinnerChanged();
                        }
                    });

        jSlider1.setMaximum(getStationMaximum());
        jSlider1.setMinimum(getStationMinimum());
        jSlider1.setPaintTrack(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jSlider1, gridBagConstraints);

        lblFromIcon.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        lblFromIcon.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.lblFromIcon.text"));                                                    // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(lblFromIcon, gridBagConstraints);

        panLine.setBackground(new java.awt.Color(255, 91, 0));
        panLine.setMinimumSize(new java.awt.Dimension(10, 3));
        panLine.setPreferredSize(new java.awt.Dimension(100, 3));
        panLine.setLayout(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(panLine, gridBagConstraints);

        lblToIcon.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/station.png"))); // NOI18N
        lblToIcon.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.lblToIcon.text"));                                                      // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(lblToIcon, gridBagConstraints);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        final javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        final javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel4, gridBagConstraints);

        final javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel5, gridBagConstraints);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.jRadioButton1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jRadioButton1, gridBagConstraints);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.jRadioButton2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jRadioButton2, gridBagConstraints);

        jLabel3.setText(org.openide.util.NbBundle.getMessage(FgskSplitDialog.class, "FgskSplitDialog.jLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        final javax.swing.GroupLayout panSettingsLayout = new javax.swing.GroupLayout(panSettings);
        panSettings.setLayout(panSettingsLayout);
        panSettingsLayout.setHorizontalGroup(
            panSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panSettingsLayout.createSequentialGroup().addContainerGap().addComponent(lblRightCaption)
                            .addContainerGap(310, Short.MAX_VALUE)).addComponent(
                jSeparator4,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                410,
                Short.MAX_VALUE).addComponent(
                jPanel1,
                javax.swing.GroupLayout.Alignment.TRAILING,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                410,
                Short.MAX_VALUE).addComponent(
                jSeparator1,
                javax.swing.GroupLayout.Alignment.TRAILING,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                410,
                Short.MAX_VALUE));
        panSettingsLayout.setVerticalGroup(
            panSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panSettingsLayout.createSequentialGroup().addContainerGap().addComponent(lblRightCaption)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    jSeparator1,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    10,
                    javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                    jPanel1,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    252,
                    Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(
                    jSeparator4,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE)));

        jProgressBar1.setVisible(false);
        jProgressBar1.setString(org.openide.util.NbBundle.getMessage(
                FgskSplitDialog.class,
                "FgskSplitDialog.jProgressBar1.string")); // NOI18N
        jProgressBar1.setStringPainted(true);

        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(
                        layout.createSequentialGroup().addContainerGap().addComponent(
                            jProgressBar1,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            416,
                            Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(
                                        cmdCancel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        110,
                                        javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(
                            cmdOk,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            107,
                            javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(
                        javax.swing.GroupLayout.Alignment.LEADING,
                        layout.createSequentialGroup().addComponent(
                            panDesc,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            241,
                            javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(
                            panSettings,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                layout.createSequentialGroup().addGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(
                        panSettings,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addComponent(
                        panDesc,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(cmdOk)
                                    .addComponent(cmdCancel)).addComponent(
                        jProgressBar1,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));

        pack();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     */
    private void spinnerChanged() {
        final AbstractFormatter formatter = ((JSpinner.DefaultEditor)jSpinner1.getEditor()).getTextField()
                    .getFormatter();
        final String text = ((JSpinner.DefaultEditor)jSpinner1.getEditor()).getTextField().getText();
        if (!text.isEmpty()) {
            try {
                // LOG.fatal("text = " + text);
                final int value = (Integer)formatter.stringToValue(text);
                // LOG.fatal("value = " + value);
                jSlider1.setValue(value);
            } catch (ParseException ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error parsing spinner", ex);
                }
                jSlider1.setValue((Integer)jSpinner1.getValue());
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdOkActionPerformed
//        final CreateLinearReferencedMarksListener marksListener = (CreateLinearReferencedMarksListener)
//                mappingComponent.getInputListener(MappingComponent.LINEAR_REFERENCING);
//
        cmdOk.setEnabled(false);
        cmdCancel.setEnabled(false);
        jProgressBar1.setVisible(true);

        final SwingWorker<Collection<Node>, Void> sw = new SwingWorker<Collection<Node>, Void>() {

                @Override
                protected Collection<Node> doInBackground() throws Exception {
                    final Collection<Node> r = new ArrayList<Node>();
                    jProgressBar1.setMaximum(2);

                    CidsBean newFgsk = null;
                    try {
                        newFgsk = splitFgskBean(
                                fgskBean,
                                jRadioButton2.isSelected(),
                                (Integer)jSpinner1.getValue());

                        jProgressBar1.setValue(1);
                        final CidsBean newAfterPersist = newFgsk.persist();
                        final CidsBean newAfterRetrieve = SessionManager.getProxy()
                                    .getMetaObject(
                                            newAfterPersist.getMetaObject().getId(),
                                            MC_FGSK.getId(),
                                            WRRLUtil.DOMAIN_NAME)
                                    .getBean();
                        r.add(new MetaObjectNode(newAfterRetrieve));
                        jProgressBar1.setValue(2);
                        final CidsBean oldAfterPersist = fgskBean.persist();
                        final CidsBean oldAfterRetrieve = SessionManager.getProxy()
                                    .getMetaObject(
                                            oldAfterPersist.getMetaObject().getId(),
                                            MC_FGSK.getId(),
                                            WRRLUtil.DOMAIN_NAME)
                                    .getBean();
                        r.add(new MetaObjectNode(oldAfterRetrieve));
                    } catch (Exception ex) {
                        if (newFgsk != null) {
                            newFgsk.delete();
                        }
                        throw ex;
                    }
                    return r;
                }

                @Override
                protected void done() {
                    try {
                        final Collection<Node> r = get();
                        if (r != null) {
                            MethodManager.getManager().showSearchResults(r.toArray(new Node[r.size()]), false);
                        }
                    } catch (Exception ex) {
                        LOG.error("error while splitting fgsk", ex);
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
     * @param   oldBean      DOCUMENT ME!
     * @param   fromToValue  DOCUMENT ME!
     * @param   value        DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    private CidsBean splitFgskBean(final CidsBean oldBean, final boolean fromToValue, final double value)
            throws Exception {
        final Geometry routeGeom = (Geometry)oldBean.getProperty("linie."
                        + LinearReferencingConstants.PROP_STATIONLINIE_FROM + "."
                        + LinearReferencingConstants.PROP_STATION_ROUTE + "."
                        + LinearReferencingConstants.PROP_ROUTE_GEOM + "."
                        + LinearReferencingConstants.PROP_GEOM_GEOFIELD);

        final LinearReferencedPointFeature fromPointFeature = new LinearReferencedPointFeature(
                getStationMinimum(),
                routeGeom);
        final LinearReferencedPointFeature splitPointFeature = new LinearReferencedPointFeature(value, routeGeom);
        final LinearReferencedPointFeature toPointFeature = new LinearReferencedPointFeature(
                getStationMaximum(),
                routeGeom);

        final LinearReferencedLineFeature fromLineFeature = new LinearReferencedLineFeature(
                fromPointFeature,
                splitPointFeature);
        final LinearReferencedLineFeature toLineFeature = new LinearReferencedLineFeature(
                splitPointFeature,
                toPointFeature);

        final CidsBean newPointGeomBean = MC_GEOM.getEmptyInstance().getBean();
        final CidsBean newPointBean = MC_STATION.getEmptyInstance().getBean();
        final CidsBean newStationLinieBean = MC_STATIONLINIE.getEmptyInstance().getBean();
        final CidsBean newlineGeomBean = MC_GEOM.getEmptyInstance().getBean();
        final CidsBean newfgskBean = MC_FGSK.getEmptyInstance().getBean();

        newStationLinieBean.setProperty(LinearReferencingConstants.PROP_STATIONLINIE_GEOM, newlineGeomBean);
        newfgskBean.setProperty("erfassungsdatum", new java.sql.Timestamp(System.currentTimeMillis()));
        newfgskBean.setProperty("linie", newStationLinieBean);
        newPointBean.setProperty(LinearReferencingConstants.PROP_STATION_GEOM, newPointGeomBean);
        newPointBean.setProperty(
            LinearReferencingConstants.PROP_STATION_ROUTE,
            (CidsBean)oldBean.getProperty(
                "linie."
                        + LinearReferencingConstants.PROP_STATIONLINIE_FROM
                        + "."
                        + LinearReferencingConstants.PROP_STATION_ROUTE));

        if (fromToValue) {
            final CidsBean splitPointBean = (CidsBean)oldBean.getProperty("linie."
                            + LinearReferencingConstants.PROP_STATIONLINIE_FROM);

            splitPointBean.setProperty(
                LinearReferencingConstants.PROP_STATION_VALUE,
                splitPointFeature.getCurrentPosition());
            splitPointBean.setProperty(LinearReferencingConstants.PROP_STATION_GEOM + "."
                        + LinearReferencingConstants.PROP_GEOM_GEOFIELD,
                splitPointFeature.getGeometry());

            newPointBean.setProperty(
                LinearReferencingConstants.PROP_STATION_VALUE,
                fromPointFeature.getCurrentPosition());
            newPointBean.setProperty(LinearReferencingConstants.PROP_STATION_GEOM + "."
                        + LinearReferencingConstants.PROP_GEOM_GEOFIELD,
                fromPointFeature.getGeometry());

            oldBean.setProperty("linie." + LinearReferencingConstants.PROP_STATIONLINIE_GEOM + "."
                        + LinearReferencingConstants.PROP_GEOM_GEOFIELD,
                toLineFeature.getGeometry());

            newfgskBean.setProperty("linie." + LinearReferencingConstants.PROP_STATIONLINIE_FROM, newPointBean);
            newfgskBean.setProperty("linie." + LinearReferencingConstants.PROP_STATIONLINIE_TO, splitPointBean);
            newfgskBean.setProperty("linie." + LinearReferencingConstants.PROP_STATIONLINIE_GEOM + "."
                        + LinearReferencingConstants.PROP_GEOM_GEOFIELD,
                fromLineFeature.getGeometry());
        } else {
            final CidsBean splitPointBean = (CidsBean)oldBean.getProperty("linie."
                            + LinearReferencingConstants.PROP_STATIONLINIE_TO);

            splitPointBean.setProperty(
                LinearReferencingConstants.PROP_STATION_VALUE,
                splitPointFeature.getCurrentPosition());
            splitPointBean.setProperty(LinearReferencingConstants.PROP_STATION_GEOM + "."
                        + LinearReferencingConstants.PROP_GEOM_GEOFIELD,
                splitPointFeature.getGeometry());

            newPointBean.setProperty(
                LinearReferencingConstants.PROP_STATION_VALUE,
                toPointFeature.getCurrentPosition());
            newPointBean.setProperty(LinearReferencingConstants.PROP_STATION_GEOM + "."
                        + LinearReferencingConstants.PROP_GEOM_GEOFIELD,
                toPointFeature.getGeometry());

            oldBean.setProperty("linie." + LinearReferencingConstants.PROP_STATIONLINIE_GEOM + "."
                        + LinearReferencingConstants.PROP_GEOM_GEOFIELD,
                fromLineFeature.getGeometry());

            newfgskBean.setProperty("linie." + LinearReferencingConstants.PROP_STATIONLINIE_FROM, splitPointBean);
            newfgskBean.setProperty("linie." + LinearReferencingConstants.PROP_STATIONLINIE_TO, newPointBean);
            newfgskBean.setProperty("linie." + LinearReferencingConstants.PROP_STATIONLINIE_GEOM + "."
                        + LinearReferencingConstants.PROP_GEOM_GEOFIELD,
                toLineFeature.getGeometry());
        }
        return newfgskBean;
    }
    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdCancelActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdCancelActionPerformed
        dispose();
    }                                                                             //GEN-LAST:event_cmdCancelActionPerformed
}
