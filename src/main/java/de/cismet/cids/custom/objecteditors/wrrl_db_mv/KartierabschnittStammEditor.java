/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;
import de.cismet.cids.editors.converters.SqlTimestampToUtilDateConverter;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class KartierabschnittStammEditor extends javax.swing.JPanel implements DisposableCidsBeanStore,
    EditorSaveListener,
    LinearReferencedLineEditorListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            KartierabschnittStammEditor.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");

    //~ Instance fields --------------------------------------------------------

    private SqlTimestampToUtilDateConverter dateConverter = new SqlTimestampToUtilDateConverter();
    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbFliessgewaesser;
    private javax.swing.JCheckBox cbHistorisch;
    private javax.swing.JCheckBox cbVorkatierung;
    private javax.swing.JDialog geomDialog;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBearbeiter;
    private javax.swing.JLabel lblDatum;
    private javax.swing.JLabel lblFotoNr;
    private javax.swing.JLabel lblGewaesserabschnitt;
    private javax.swing.JLabel lblGewaesserkennzahl;
    private javax.swing.JLabel lblGewaessername;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHistorisch;
    private javax.swing.JLabel lblSpacing;
    private javax.swing.JLabel lblVorkatierung;
    private javax.swing.JLabel lblWk;
    private javax.swing.JLabel lblWkName;
    private javax.swing.JLabel lblWkType;
    private javax.swing.JLabel lblfliessrichtung;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JSeparator sepMiddle;
    private de.cismet.cids.editors.DefaultBindableTimestampChooser timErfassungsdatum;
    private javax.swing.JTextField txtBearbeiter;
    private javax.swing.JTextField txtFotoNr;
    private javax.swing.JTextField txtGewaesserabschnitt;
    private javax.swing.JTextField txtGewaesserkennzahl;
    private javax.swing.JTextField txtGewaessername;
    private javax.swing.JTextField txtWk;
    private javax.swing.JTextField txtWkName;
    private javax.swing.JTextField txtWkType;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public KartierabschnittStammEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgPanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public KartierabschnittStammEditor(final boolean readOnly) {
        linearReferencedLineEditor = (readOnly) ? new LinearReferencedLineRenderer() : new LinearReferencedLineEditor();
        linearReferencedLineEditor.setOtherLinesEnabled(false);
//        linearReferencedLineEditor.setOtherLinesQueryAddition(
//            "fgsk_kartierabschnitt",
//            "fgsk_kartierabschnitt.linie = ");
        linearReferencedLineEditor.setLineField("linie");
        initComponents();
        linearReferencedLineEditor.addListener(this);
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

        geomDialog = new JDialog(StaticSwingTools.getParentFrame(this));
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        lblGewaessername = new javax.swing.JLabel();
        lblGewaesserkennzahl = new javax.swing.JLabel();
        lblGewaesserabschnitt = new javax.swing.JLabel();
        lblWk = new javax.swing.JLabel();
        lblWkName = new javax.swing.JLabel();
        txtGewaessername = new javax.swing.JTextField();
        txtGewaesserkennzahl = new javax.swing.JTextField();
        lblSpacing = new javax.swing.JLabel();
        sepMiddle = new javax.swing.JSeparator();
        txtGewaesserabschnitt = new javax.swing.JTextField();
        txtWk = new javax.swing.JTextField();
        txtWkName = new javax.swing.JTextField();
        lblDatum = new javax.swing.JLabel();
        lblBearbeiter = new javax.swing.JLabel();
        txtBearbeiter = new javax.swing.JTextField();
        lblfliessrichtung = new javax.swing.JLabel();
        cbFliessgewaesser = new ScrollableComboBox();
        timErfassungsdatum = new de.cismet.cids.editors.DefaultBindableTimestampChooser();
        lblWkType = new javax.swing.JLabel();
        txtWkType = new javax.swing.JTextField();
        lblFotoNr = new javax.swing.JLabel();
        txtFotoNr = new javax.swing.JTextField();
        lblVorkatierung = new javax.swing.JLabel();
        linearReferencedLineEditor = linearReferencedLineEditor;
        jPanel2 = new javax.swing.JPanel();
        cbVorkatierung = new javax.swing.JCheckBox();
        cbHistorisch = new javax.swing.JCheckBox();
        lblHistorisch = new javax.swing.JLabel();

        geomDialog.setTitle(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.geomDialog.title")); // NOI18N
        geomDialog.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosing(final java.awt.event.WindowEvent evt) {
                    geomDialogWindowClosing(evt);
                }
            });

        jPanel1.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(jPanel1);

        geomDialog.getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        setMinimumSize(new java.awt.Dimension(1100, 275));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1100, 275));
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblGewaessername.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblgewaessername.text")); // NOI18N
        lblGewaessername.setMaximumSize(new java.awt.Dimension(120, 17));
        lblGewaessername.setMinimumSize(new java.awt.Dimension(130, 17));
        lblGewaessername.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblGewaessername, gridBagConstraints);

        lblGewaesserkennzahl.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblgewaesserkennzahl.text")); // NOI18N
        lblGewaesserkennzahl.setMinimumSize(new java.awt.Dimension(130, 17));
        lblGewaesserkennzahl.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblGewaesserkennzahl, gridBagConstraints);

        lblGewaesserabschnitt.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblgewaesserabschnitt.text")); // NOI18N
        lblGewaesserabschnitt.setMinimumSize(new java.awt.Dimension(130, 17));
        lblGewaesserabschnitt.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblGewaesserabschnitt, gridBagConstraints);

        lblWk.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblWk.text")); // NOI18N
        lblWk.setMaximumSize(new java.awt.Dimension(120, 17));
        lblWk.setMinimumSize(new java.awt.Dimension(130, 17));
        lblWk.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblWk, gridBagConstraints);

        lblWkName.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblWkName.text")); // NOI18N
        lblWkName.setMinimumSize(new java.awt.Dimension(130, 17));
        lblWkName.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblWkName, gridBagConstraints);

        txtGewaessername.setEditable(false);
        txtGewaessername.setMinimumSize(new java.awt.Dimension(170, 20));
        txtGewaessername.setPreferredSize(new java.awt.Dimension(170, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.linie.von.route.routenname}"),
                txtGewaessername,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent.add(txtGewaessername, gridBagConstraints);

        txtGewaesserkennzahl.setEditable(false);
        txtGewaesserkennzahl.setMinimumSize(new java.awt.Dimension(170, 20));
        txtGewaesserkennzahl.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.linie.von.route.gwk}"),
                txtGewaesserkennzahl,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtGewaesserkennzahl, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(lblSpacing, gridBagConstraints);

        sepMiddle.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 5, 25);
        panInfoContent.add(sepMiddle, gridBagConstraints);

        txtGewaesserabschnitt.setMinimumSize(new java.awt.Dimension(170, 20));
        txtGewaesserabschnitt.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gewaesser_abschnitt}"),
                txtGewaesserabschnitt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtGewaesserabschnitt, gridBagConstraints);

        txtWk.setEditable(false);
        txtWk.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWk.setPreferredSize(new java.awt.Dimension(170, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtWk, gridBagConstraints);

        txtWkName.setEditable(false);
        txtWkName.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWkName.setPreferredSize(new java.awt.Dimension(170, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtWkName, gridBagConstraints);

        lblDatum.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblDatum.text")); // NOI18N
        lblDatum.setMaximumSize(new java.awt.Dimension(120, 17));
        lblDatum.setMinimumSize(new java.awt.Dimension(130, 17));
        lblDatum.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblDatum, gridBagConstraints);

        lblBearbeiter.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblBearbeiter.text")); // NOI18N
        lblBearbeiter.setMinimumSize(new java.awt.Dimension(130, 17));
        lblBearbeiter.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblBearbeiter, gridBagConstraints);

        txtBearbeiter.setMinimumSize(new java.awt.Dimension(170, 20));
        txtBearbeiter.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bearbeiter}"),
                txtBearbeiter,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtBearbeiter, gridBagConstraints);

        lblfliessrichtung.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblfliessrichtung.text")); // NOI18N
        lblfliessrichtung.setMinimumSize(new java.awt.Dimension(130, 17));
        lblfliessrichtung.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblfliessrichtung, gridBagConstraints);

        cbFliessgewaesser.setMinimumSize(new java.awt.Dimension(170, 20));
        cbFliessgewaesser.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fliessrichtung_id}"),
                cbFliessgewaesser,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(cbFliessgewaesser, gridBagConstraints);

        timErfassungsdatum.setMinimumSize(new java.awt.Dimension(170, 20));
        timErfassungsdatum.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.erfassungsdatum}"),
                timErfassungsdatum,
                org.jdesktop.beansbinding.BeanProperty.create("timestamp"));
        binding.setConverter(dateConverter);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(timErfassungsdatum, gridBagConstraints);

        lblWkType.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblWkType.text")); // NOI18N
        lblWkType.setMinimumSize(new java.awt.Dimension(130, 17));
        lblWkType.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblWkType, gridBagConstraints);

        txtWkType.setEditable(false);
        txtWkType.setMinimumSize(new java.awt.Dimension(170, 20));
        txtWkType.setPreferredSize(new java.awt.Dimension(170, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(txtWkType, gridBagConstraints);

        lblFotoNr.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblFotoNr.text")); // NOI18N
        lblFotoNr.setMinimumSize(new java.awt.Dimension(130, 17));
        lblFotoNr.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblFotoNr, gridBagConstraints);

        txtFotoNr.setMinimumSize(new java.awt.Dimension(170, 20));
        txtFotoNr.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.foto_nr}"),
                txtFotoNr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent.add(txtFotoNr, gridBagConstraints);

        lblVorkatierung.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblVorkatierung.text")); // NOI18N
        lblVorkatierung.setMinimumSize(new java.awt.Dimension(130, 17));
        lblVorkatierung.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblVorkatierung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        panInfoContent.add(linearReferencedLineEditor, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        cbVorkatierung.setContentAreaFilled(false);
        cbVorkatierung.setMinimumSize(new java.awt.Dimension(100, 20));
        cbVorkatierung.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.vorkatierung}"),
                cbVorkatierung,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(cbVorkatierung, gridBagConstraints);

        cbHistorisch.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.historisch}"),
                cbHistorisch,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        jPanel2.add(cbHistorisch, gridBagConstraints);

        lblHistorisch.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittStammEditor.class,
                "KartierabschnittStammEditor.lblHistorisch.text")); // NOI18N
        lblHistorisch.setMinimumSize(new java.awt.Dimension(100, 17));
        lblHistorisch.setPreferredSize(new java.awt.Dimension(100, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel2.add(lblHistorisch, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(jPanel2, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

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
    private void geomDialogWindowClosing(final java.awt.event.WindowEvent evt) { //GEN-FIRST:event_geomDialogWindowClosing
        final boolean visible = linearReferencedLineEditor.changeOtherLinesPanelVisibility();
        otherLinesPanelVisibilityChange(visible);
    }                                                                            //GEN-LAST:event_geomDialogWindowClosing

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        if (cidsBean != null) {
            this.cidsBean = cidsBean;
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                this.cidsBean);
            bindingGroup.bind();
            linearReferencedLineEditor.setCidsBean(cidsBean);
            txtWk.setText("");
            txtWkName.setText("");
            txtWkType.setText("");
        } else {
            txtGewaessername.setText("");
            txtGewaesserkennzahl.setText("");
            txtWk.setText("");
            txtWkName.setText("");
            txtWkType.setText("");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkk  DOCUMENT ME!
     */
    public void refreshLabels(final String wkk) {
        String wkName = CidsBeanSupport.FIELD_NOT_SET;
        String wkType = CidsBeanSupport.FIELD_NOT_SET;
        try {
            if (wkk != null) {
                final String query = "select " + MC.getID() + ", " + MC.getPrimaryKey() + " from "
                            + MC.getTableName() + " where wk_k = '" + wkk + "'";
                final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
                if (metaObjects.length == 1) {
                    wkName = (String)metaObjects[0].getBean().getProperty("wk_n");
                    final CidsBean lawaType = (CidsBean)metaObjects[0].getBean().getProperty("lawa_type");

                    if (lawaType != null) {
                        wkType = String.valueOf(lawaType.getProperty("code")) + "-"
                                    + String.valueOf(lawaType.getProperty("description"));
                    } else {
                        wkType = "nicht ermittelbar";
                    }
                } else {
                    wkName = "nicht ermittelbar";
                    wkType = "nicht ermittelbar";
                }
            }
        } catch (final Exception e) {
            LOG.error("Error while determining the water body", e);
        }

        final String wkNameF = wkName;
        final String wkTypeF = wkType;

        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    txtWk.setText(wkk);
                    txtWkName.setText(wkNameF);
                    txtWkType.setText(wkTypeF);
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkk  DOCUMENT ME!
     */
    public void setWkk(final String wkk) {
        new Thread(new Runnable() {

                @Override
                public void run() {
                    refreshLabels(wkk);
                }
            }).start();
    }

    @Override
    public void dispose() {
        linearReferencedLineEditor.dispose();
        bindingGroup.unbind();
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        linearReferencedLineEditor.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        final Boolean vorkatierung = (Boolean)cidsBean.getProperty("vorkatierung");

        if ((vorkatierung == null) || !vorkatierung.booleanValue()) {
            if ((txtBearbeiter.getText() == null)
                        || (timErfassungsdatum.getTimestamp().getTime() == 0)
                        || (cidsBean.getProperty("linie") == null)) {
                JOptionPane.showMessageDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Mindestens die Stationierung des Abschnitts, "
                            + "Datum und Bearbeiter müssen angegeben werden.",
                    "Unvollständig",
                    JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        return linearReferencedLineEditor.prepareForSave();
    }

    @Override
    public void linearReferencedLineCreated() {
        // nothing to do
    }

    @Override
    public void otherLinesPanelVisibilityChange(final boolean visible) {
        if (visible) {
            panInfoContent.remove(linearReferencedLineEditor);
            final GridBagConstraints constraints = new java.awt.GridBagConstraints(
                    5,
                    4,
                    2,
                    3,
                    0,
                    1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.NONE,
                    new Insets(10, 10, 10, 10),
                    0,
                    0);
            jPanel1.add(linearReferencedLineEditor, constraints);
            geomDialog.setModal(true);
            StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), geomDialog, true);
        } else {
            geomDialog.setVisible(false);
            jPanel1.removeAll();
            final GridBagConstraints constraints = new java.awt.GridBagConstraints(
                    5,
                    4,
                    2,
                    3,
                    0,
                    1,
                    GridBagConstraints.CENTER,
                    GridBagConstraints.NONE,
                    new Insets(5, 10, 10, 10),
                    0,
                    0);
            panInfoContent.add(linearReferencedLineEditor, constraints);
        }
    }
}
