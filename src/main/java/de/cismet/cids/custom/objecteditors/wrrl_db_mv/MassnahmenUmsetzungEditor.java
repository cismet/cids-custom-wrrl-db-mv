/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;

import org.openide.util.NbBundle;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.MapUtil;
import de.cismet.cids.custom.util.MassnahmenUmsetzungCache;
import de.cismet.cids.custom.util.MeasureTypeCodeRenderer;
import de.cismet.cids.custom.util.RouteWBDropBehavior;
import de.cismet.cids.custom.util.ScrollableComboBox;
import de.cismet.cids.custom.util.UIUtil;
import de.cismet.cids.custom.util.linearreferencing.LinearReferencingConstants;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnahmenUmsetzungEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            MassnahmenUmsetzungEditor.class);
    public static final String[] WB_PROPERTIES = { "wk_fg", "wk_sg", "wk_kg", "wk_gw" }; // NOI18N
    private static final MetaClass MTC_MC = ClassCacheMultiple.getMetaClass(
            CidsBeanSupport.DOMAIN_NAME,
            "wfd.de_measure_type_code");

    //~ Instance fields --------------------------------------------------------

    private boolean isStandalone = true;
    private CidsBean cidsBean;
    private ArrayList<CidsBean> beansToDelete = new ArrayList<CidsBean>();
    private ArrayList<CidsBean> beansToSave = new ArrayList<CidsBean>();
    private JList referencedList;
    private RouteWBDropBehavior dropBehaviorListener;
    private Thread actionRetrievalThread = null;
    private MassnahmenUmsetzungCache cache;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnArtAbort;
    private javax.swing.JButton btnArtOk;
    private javax.swing.JComboBox cbArtKatalog;
    private javax.swing.JComboBox cbGeom;
    private javax.swing.JDialog dlgArtKatalog;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblArtKatalog;
    private javax.swing.JLabel lblBeschrDerMa;
    private javax.swing.JLabel lblGeom;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblMassnahme_nr;
    private javax.swing.JLabel lblMeasure_type_code;
    private javax.swing.JLabel lblValMassnahme_nr;
    private javax.swing.JLabel lblValWk_k;
    private javax.swing.JLabel lblWk_k;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private de.cismet.tools.gui.RoundedPanel panGeo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panMenButtonsArt;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WirkungPan wirkungPan1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenUmsetzungEditor object.
     */
    public MassnahmenUmsetzungEditor() {
        this(true);
    }

    /**
     * Creates new form MassnahmenUmsetzungEditor.
     *
     * @param  isStandalone  DOCUMENT ME!
     */
    public MassnahmenUmsetzungEditor(final boolean isStandalone) {
        this.isStandalone = isStandalone;

        if (!isStandalone) {
            initComponents();
            jTextField1.setEditable(false);
            dropBehaviorListener = new RouteWBDropBehavior(this);
            linearReferencedLineEditor.setLineField("linie");                 // NOI18N
            linearReferencedLineEditor.setDropBehavior(dropBehaviorListener);
            linearReferencedLineEditor.setOtherLinesEnabled(false);
            deActivateGUIElements(false);
            try {
                new CidsBeanDropTarget(this);
            } catch (final Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
                }
            }
        } else {
            final JLabel hintLabel = new JLabel();
            hintLabel.setText(NbBundle.getMessage(
                    MassnahmenUmsetzungEditor.class,
                    "MassnahmenUmsetzungEditor.hintLabel.text"));
            setLayout(new GridBagLayout());
            final GridBagConstraints constraints = new GridBagConstraints(
                    1,
                    1,
                    1,
                    1,
                    0,
                    0,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.NONE,
                    new Insets(5, 5, 5, 5),
                    0,
                    0);
            add(hintLabel, constraints);
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  arg  DOCUMENT ME!
     */
    public static void main(final String[] arg) {
        final JFrame f = new JFrame("Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final MassnahmenUmsetzungEditor s = new MassnahmenUmsetzungEditor();
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(s, BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
    }

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        MapUtil.zoomToFeatureCollection(linearReferencedLineEditor.getZoomFeatures());
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

        dlgArtKatalog = new javax.swing.JDialog();
        lblArtKatalog = new javax.swing.JLabel();
        final DefaultBindableReferenceCombo cb1 = new ScrollableComboBox(
                MTC_MC,
                true,
                true,
                new CustomElementComparator());
        cbArtKatalog = cb1;
        panMenButtonsArt = new javax.swing.JPanel();
        btnArtAbort = new javax.swing.JButton();
        btnArtOk = new javax.swing.JButton();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblBeschrDerMa = new javax.swing.JLabel();
        lblWk_k = new javax.swing.JLabel();
        lblValWk_k = new javax.swing.JLabel();
        lblMassnahme_nr = new javax.swing.JLabel();
        lblMeasure_type_code = new javax.swing.JLabel();
        wirkungPan1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WirkungPan();
        lblValMassnahme_nr = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        panGeo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        linearReferencedLineEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor();
        jPanel1 = new javax.swing.JPanel();
        cbGeom = new DefaultCismapGeometryComboBoxEditor();
        lblGeom = new javax.swing.JLabel();

        dlgArtKatalog.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblArtKatalog.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblArtKatalog.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgArtKatalog.getContentPane().add(lblArtKatalog, gridBagConstraints);

        cbArtKatalog.setMinimumSize(new java.awt.Dimension(750, 18));
        cbArtKatalog.setPreferredSize(new java.awt.Dimension(750, 18));
        cbArtKatalog.setRenderer(new MeasureTypeCodeRenderer());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgArtKatalog.getContentPane().add(cbArtKatalog, gridBagConstraints);

        panMenButtonsArt.setLayout(new java.awt.GridBagLayout());

        btnArtAbort.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.btnArtAbort.text")); // NOI18N
        btnArtAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnArtAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsArt.add(btnArtAbort, gridBagConstraints);

        btnArtOk.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.btnArtOk.text")); // NOI18N
        btnArtOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnArtOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnArtOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnArtOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnArtOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsArt.add(btnArtOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgArtKatalog.getContentPane().add(panMenButtonsArt, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(440, 675));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(440, 675));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(470, 370));
        panInfo.setPreferredSize(new java.awt.Dimension(470, 370));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(450, 480));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(450, 480));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(422, 75));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(422, 75));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mass_beschreibung}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        lblBeschrDerMa.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblBeschrDerMa.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanel2.add(lblBeschrDerMa, gridBagConstraints);

        lblWk_k.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblWk_k.text")); // NOI18N
        lblWk_k.setMinimumSize(new java.awt.Dimension(182, 20));
        lblWk_k.setPreferredSize(new java.awt.Dimension(182, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblWk_k, gridBagConstraints);

        lblValWk_k.setMinimumSize(new java.awt.Dimension(250, 20));
        lblValWk_k.setPreferredSize(new java.awt.Dimension(250, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(lblValWk_k, gridBagConstraints);

        lblMassnahme_nr.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblMassnahme_nr.text")); // NOI18N
        lblMassnahme_nr.setMinimumSize(new java.awt.Dimension(182, 20));
        lblMassnahme_nr.setPreferredSize(new java.awt.Dimension(182, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblMassnahme_nr, gridBagConstraints);

        lblMeasure_type_code.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblMeasure_type_code.text"));        // NOI18N
        lblMeasure_type_code.setToolTipText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblMeasure_type_code.toolTipText")); // NOI18N
        lblMeasure_type_code.setMinimumSize(new java.awt.Dimension(182, 20));
        lblMeasure_type_code.setPreferredSize(new java.awt.Dimension(182, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblMeasure_type_code, gridBagConstraints);

        wirkungPan1.setMinimumSize(new java.awt.Dimension(380, 115));
        wirkungPan1.setPreferredSize(new java.awt.Dimension(380, 115));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        jPanel2.add(wirkungPan1, gridBagConstraints);

        lblValMassnahme_nr.setMinimumSize(new java.awt.Dimension(250, 20));
        lblValMassnahme_nr.setPreferredSize(new java.awt.Dimension(250, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(lblValMassnahme_nr, gridBagConstraints);

        jTextField1.setEditable(false);
        jTextField1.setMinimumSize(new java.awt.Dimension(200, 20));
        jTextField1.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.measure_type_code.name}"),
                jTextField1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        jPanel2.add(jTextField1, gridBagConstraints);

        jButton1.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.jbModMass.text")); // NOI18N
        jButton1.setMinimumSize(new java.awt.Dimension(65, 20));
        jButton1.setPreferredSize(new java.awt.Dimension(65, 20));
        jButton1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        jPanel2.add(jButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 5, 20);
        panInfoContent.add(jPanel2, gridBagConstraints);

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panGeo.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        panInfoContent1.add(linearReferencedLineEditor, gridBagConstraints);

        panGeo.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 10, 20);
        panInfoContent.add(panGeo, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        cbGeom.setMinimumSize(new java.awt.Dimension(300, 20));
        cbGeom.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.additional_geom}"),
                cbGeom,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setConverter(((DefaultCismapGeometryComboBoxEditor)cbGeom).getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(cbGeom, gridBagConstraints);

        lblGeom.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblGeom.text")); // NOI18N
        lblGeom.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(lblGeom, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        panInfoContent.add(jPanel1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panInfo, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnArtAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnArtAbortActionPerformed
        dlgArtKatalog.setVisible(false);
    }                                                                               //GEN-LAST:event_btnArtAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnArtOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnArtOkActionPerformed
        try {
            final Object selection = cbArtKatalog.getSelectedItem();
            if (selection instanceof CidsBean) {
                cidsBean.setProperty("measure_type_code", selection);
            }
        } catch (Exception e) {
            LOG.error("Error while changing property measure_type_code.", e);
        }
        dlgArtKatalog.setVisible(false);
    }                                                                            //GEN-LAST:event_btnArtOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jButton1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
        if (cbGeom.isEnabled()) {
            UIUtil.findOptimalPositionOnScreen(dlgArtKatalog);
            dlgArtKatalog.setSize(800, 150);
            dlgArtKatalog.setVisible(true);
        }
    }                                                                            //GEN-LAST:event_jButton1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        CidsBean action = null;
        CidsBean wb = null;
        if (isStandalone) {
            return;
        }
        dispose();
        this.cidsBean = cidsBean;

        if (dropBehaviorListener.isRouteChanged() && !linearReferencedLineEditor.hasChangedSinceDrop()) {
            final int ans = JOptionPane.showConfirmDialog(
                    this,
                    "Sie haben die Stationen nicht geändert, nachdem Sie eine "
                            + "neue Route ausgewählt haben. Möchten Sie die Stationen ändern?",
                    "Keine Änderung der Stationen",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (ans == JOptionPane.YES_OPTION) {
                return;
            }
        }

        if (cidsBean != null) {
            if (cache != null) {
                action = cache.getAction(cidsBean);
                wb = cache.getWB(cidsBean);
            }
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).setCidsMetaObject(cidsBean.getMetaObject());
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).initForNewBinding();
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);

            if ((wb == null) && (cidsBean != null) && (MassnahmenUmsetzungCache.getWk_kId(cidsBean) != null)) {
                final CidsBean wkObject = MassnahmenUmsetzungCache.bindWkkField(lblValWk_k, cidsBean);
                dropBehaviorListener.setWkFg(wkObject);
            } else if (wb != null) {
                dropBehaviorListener.setWkFg(wb);
                lblValWk_k.setText(String.valueOf(wb.getProperty(MassnahmenUmsetzungCache.getWk_kProperty(cidsBean))));
            } else if ((cidsBean != null) && (MassnahmenUmsetzungCache.getWk_kId(cidsBean) == null)) {
                lblValWk_k.setText(CidsBeanSupport.FIELD_NOT_SET);
            } else {
            }

            bindingGroup.bind();
            deActivateGUIElements(true);
            zoomToFeatures();
        } else {
            dropBehaviorListener.setWkFg(null);
            deActivateGUIElements(false);
            lblValWk_k.setText("");
        }

        linearReferencedLineEditor.setCidsBean(cidsBean);
        wirkungPan1.setCidsBean(cidsBean);

        waitForRunningThreads();
        if ((action == null) && (cidsBean != null) && (cidsBean.getProperty("massnahme") != null)) {
            lblValMassnahme_nr.setText("");

            actionRetrievalThread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            MassnahmenUmsetzungCache.bindActionField(lblValMassnahme_nr, cidsBean);
                        }
                    });
            actionRetrievalThread.start();
        } else if (action != null) {
            lblValMassnahme_nr.setText(String.valueOf(action.getProperty("massn_id")));
        } else if ((cidsBean != null) && (cidsBean.getProperty("massnahme") == null)) {
            lblValMassnahme_nr.setText(CidsBeanSupport.FIELD_NOT_SET);
        } else {
            lblValMassnahme_nr.setText("");
        }
    }

    /**
     * interrupt the last retrieval threads and wait until the threads are stopped.
     */
    private void waitForRunningThreads() {
        if ((actionRetrievalThread != null) && actionRetrievalThread.isAlive()) {
            actionRetrievalThread.interrupt();
        }

        while ((actionRetrievalThread != null) && actionRetrievalThread.isAlive()) {
            try {
                Thread.sleep(50);
            } catch (final InterruptedException e) {
                // nothing to do
            }
        }
    }

    @Override
    public void dispose() {
        if (!isStandalone) {
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).dispose();
            linearReferencedLineEditor.dispose();
            bindingGroup.unbind();
        }
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void setTitle(final String title) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param  referencedList  DOCUMENT ME!
     */
    public void setList(final JList referencedList) {
        this.referencedList = referencedList;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    private void deActivateGUIElements(final boolean enable) {
        jTextArea1.setEnabled(enable);
//        cbMeasure_type_code.setEnabled(enable);
        cbGeom.setEnabled(enable);
        linearReferencedLineEditor.setEnabled(enable);
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        if (isStandalone) {
            return;
        }
        if (cidsBean != null) {
            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_fg")) {             // NOI18N
                    bindToWb(WB_PROPERTIES[0], bean);
                    dropBehaviorListener.setWkFg(bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_sg")) {      // NOI18N
                    bindToWb(WB_PROPERTIES[1], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_kg")) {      // NOI18N
                    bindToWb(WB_PROPERTIES[2], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_gw")) {      // NOI18N
                    bindToWb(WB_PROPERTIES[3], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Massnahmen")) { // NOI18N
                    final int conf = JOptionPane.showConfirmDialog(
                            this,
                            "Soll die Maßnahme "
                                    + bean.toString()
                                    + " wirklich als Template für die ausgewählte Umsetzung dienen?",
                            "Maßnahme als Template nutzen",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (conf == JOptionPane.YES_OPTION) {
                        copyActionToImplementation(bean);
                        if (referencedList != null) {
                            referencedList.repaint();
                        }
                    }
                }
            }
//            bindReadOnlyFields(lblValWk_k, lblValMassnahme_nr, cidsBean, true);
            final CidsBean action = MassnahmenUmsetzungCache.bindActionField(lblValMassnahme_nr, cidsBean);
            final CidsBean wb = MassnahmenUmsetzungCache.bindWkkField(lblValWk_k, cidsBean);

            if (getCache() != null) {
                getCache().addAction(cidsBean, action);
                getCache().addWB(cidsBean, wb);
            }
        }
    }

    /**
     * binds the given water body to the current CidsBean object.
     *
     * @param  propertyName   the name of the water body property. (The allowed values are stored in the array
     *                        WB_PROPERTIES)
     * @param  propertyEntry  the water body object
     */
    private void bindToWb(final String propertyName, final CidsBean propertyEntry) {
        try {
            cidsBean.setProperty(propertyName, propertyEntry.getProperty("id"));

            for (final String propName : WB_PROPERTIES) {
                if (!propName.equals(propertyName)) {
                    cidsBean.setProperty(propName, null);
                }
            }
            showOrHideGeometryEditors();
        } catch (final Exception ex) {
            LOG.error("Error while binding a water body", ex); // NOI18N
        }
    }

    /**
     * copies all relevant properties from the given action to the current CidsBean object.
     *
     * @param  act  DOCUMENT ME!
     */
    public void copyActionToImplementation(final CidsBean act) {
        try {
            final CidsBean additionalGeom = (CidsBean)act.getProperty("additional_geom"); // NOI18N

            CidsBeanSupport.deletePropertyIfExists(cidsBean, "additional_geom", beansToDelete); // NOI18N

            cidsBean.setProperty("massnahme", act.getProperty("id"));                               // NOI18N
            cidsBean.setProperty("additional_geom", CidsBeanSupport.cloneCidsBean(additionalGeom)); // NOI18N

            if (act.getProperty("wk_fg") != null) {
                cidsBean.setProperty("wk_fg", ((CidsBean)act.getProperty("wk_fg")).getProperty("id")); // NOI18N
            } else {
                cidsBean.setProperty("wk_fg", null);                                                   // NOI18N
            }
            if (act.getProperty("wk_sg") != null) {
                cidsBean.setProperty("wk_sg", ((CidsBean)act.getProperty("wk_sg")).getProperty("id")); // NOI18N
            } else {
                cidsBean.setProperty("wk_sg", null);                                                   // NOI18N
            }
            if (act.getProperty("wk_kg") != null) {
                cidsBean.setProperty("wk_kg", ((CidsBean)act.getProperty("wk_kg")).getProperty("id")); // NOI18N
            } else {
                cidsBean.setProperty("wk_kg", null);                                                   // NOI18N
            }
            if (act.getProperty("wk_gw") != null) {
                cidsBean.setProperty("wk_gw", ((CidsBean)act.getProperty("wk_gw")).getProperty("id")); // NOI18N
            } else {
                cidsBean.setProperty("wk_gw", null);                                                   // NOI18N
            }

            final List<CidsBean> meas = CidsBeanSupport.getBeanCollectionFromProperty(act, "de_meas_cd"); // NOI18N

            if ((meas != null) && (meas.size() > 0)) {
                cidsBean.setProperty("measure_type_code", meas.get(0)); // NOI18N
            }

            final CidsBean linBean = (CidsBean)act.getProperty("linie");
            if (linBean != null) {                                                                                      // NOI18N
                final CidsBean statFrom = (CidsBean)linBean.getProperty(
                        LinearReferencingConstants.PROP_STATIONLINIE_FROM);                                             // NOI18N
                final CidsBean statTo = (CidsBean)linBean.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_TO); // NOI18N
                if (LOG.isDebugEnabled()) {
                    LOG.debug("statFrom " + statFrom.getProperty(LinearReferencingConstants.PROP_STATION_VALUE));       // NOI18N
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("statTo " + statTo.getProperty(LinearReferencingConstants.PROP_STATION_VALUE));           // NOI18N
                }

                CidsBeanSupport.deleteStationlineIfExists(cidsBean, "linie", beansToDelete); // NOI18N

                cidsBean.setProperty("linie", CidsBeanSupport.cloneStationline(linBean));
                linearReferencedLineEditor.setCidsBean(cidsBean);
            }

            if (act != null) {
                final Object fin = act.getProperty("massn_fin");
                if ((fin == null) || ((fin instanceof Boolean) && !((Boolean)fin).booleanValue())) {
                    try {
                        act.setProperty("massn_fin", Boolean.TRUE);
                        if (!beansToSave.contains(act)) {
                            beansToSave.add(act);
                        }
                    } catch (final Exception e) {
                        LOG.error("Error while set action to fin.", e); // NOI18N
                    }
                }
            }
        } catch (final Exception e) {
            LOG.error("Error during the creation of a new bean of type massnahmen", e); // NOI18N
        }
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        if (!isStandalone) {
            linearReferencedLineEditor.editorClosed(event);
        }
    }

    @Override
    public boolean prepareForSave() {
        if (isStandalone) {
            return true;
        }
        if (dropBehaviorListener.isRouteChanged() && !linearReferencedLineEditor.hasChangedSinceDrop()) {
            final int ans = JOptionPane.showConfirmDialog(
                    this,
                    "Sie haben die Stationen nicht geändert, nachdem Sie eine "
                            + "neue Route ausgewählt haben. Möchten Sie die Stationen ändern?",
                    "Keine Änderung der Stationen",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (ans == JOptionPane.YES_OPTION) {
                return false;
            }
        }

        for (final CidsBean bean : beansToDelete) {
            try {
                bean.persist();
            } catch (final Exception e) {
                LOG.error("Error while deleting bean", e); // NOI18N
            }
        }

        for (final CidsBean bean : beansToSave) {
            try {
                bean.persist();
            } catch (final Exception e) {
                LOG.error("Error while deleting bean", e); // NOI18N
            }
        }

        boolean save = true;
        save &= wirkungPan1.prepareForSave();
        save &= linearReferencedLineEditor.prepareForSave();
        return save;
    }

    /**
     * DOCUMENT ME!
     */
    private void showOrHideGeometryEditors() {
        if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[1]) != null)) {
            panGeo.setVisible(false);
        } else {
            panGeo.setVisible(true);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the cache
     */
    public MassnahmenUmsetzungCache getCache() {
        return cache;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cache  the cache to set
     */
    public void setCache(final MassnahmenUmsetzungCache cache) {
        this.cache = cache;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomElementComparator implements Comparator<CidsBean> {

        //~ Instance fields ----------------------------------------------------

        private int integerIndex = 0;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomElementComparator object.
         */
        public CustomElementComparator() {
        }

        /**
         * Creates a new CustomElementComparator object.
         *
         * @param  integerIndex  DOCUMENT ME!
         */
        public CustomElementComparator(final int integerIndex) {
            this.integerIndex = integerIndex;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final CidsBean o1, final CidsBean o2) {
            if ((o1 != null) && (o2 != null)) {
                final String strValue1 = (String)o1.getProperty("value");
                final String strValue2 = (String)o2.getProperty("value");

                if ((strValue1 != null) && (strValue2 != null)) {
                    try {
                        final Integer value1 = Integer.parseInt(strValue1.substring(integerIndex));
                        final Integer value2 = Integer.parseInt(strValue2.substring(integerIndex));

                        return value1.intValue() - value2.intValue();
                    } catch (NumberFormatException e) {
                        // nothing to do, because not every 'value'-property contains a integer
                    }

                    return strValue1.compareTo(strValue2);
                } else {
                    if ((strValue1 == null) && (strValue2 == null)) {
                        return 0;
                    } else {
                        return ((strValue1 == null) ? -1 : 1);
                    }
                }
            } else {
                if ((o1 == null) && (o2 == null)) {
                    return 0;
                } else {
                    return ((o1 == null) ? -1 : 1);
                }
            }
        }
    }
}
