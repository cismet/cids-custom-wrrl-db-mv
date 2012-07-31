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
 * Created on 19.10.2011, 11:49:19
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.newuser.User;

import org.apache.log4j.Logger;

import java.awt.Component;
import java.awt.event.ItemEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.MassnahmenComboBox;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupUnterhaltungsmassnahmeEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    PropertyChangeListener {

    //~ Static fields/initializers ---------------------------------------------

    public static final int KOMPARTIMENT_SOHLE = 1;
    public static final int KOMPARTIMENT_UFER = 2;
    public static final int KOMPARTIMENT_UMFELD = 3;
    private static final int INTERVAL_TWO_TIMES = 4;
    private static final Logger LOG = Logger.getLogger(GupUnterhaltungsmassnahmeEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private List<CidsBean> massnahmen = null;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbAusfuehrung;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbAusfuehrungSec;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbIntervall;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbJahr;
    private javax.swing.JComboBox cbMassnahme;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbVerbleib;
    private javax.swing.JPanel flowPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblAusfuehrung;
    private javax.swing.JLabel lblAusfuehrungSec;
    private javax.swing.JLabel lblBearbeiter;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblBoeschungslaenge;
    private javax.swing.JLabel lblBoeschungsneigung;
    private javax.swing.JLabel lblDeichkronenbreite;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblIntervall;
    private javax.swing.JLabel lblJahr;
    private javax.swing.JLabel lblMassnahme;
    private javax.swing.JLabel lblRandstreifenbreite;
    private javax.swing.JLabel lblSohlbreite;
    private javax.swing.JLabel lblVerbleib;
    private javax.swing.JLabel lblVorlandbreite;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panBoeschungslaenge;
    private javax.swing.JPanel panBoeschungsneigung;
    private javax.swing.JPanel panDeichkronenbreite;
    private javax.swing.JPanel panRandstreifen;
    private javax.swing.JPanel panSohlbreite;
    private javax.swing.JPanel panVorlandbreite;
    private javax.swing.JScrollPane spBemerkung;
    private javax.swing.JTextField txtBearbeiter;
    private javax.swing.JTextField txtBoeschungslaenge;
    private javax.swing.JTextField txtBoeschungsneigung;
    private javax.swing.JTextField txtDeichkronenbreite;
    private javax.swing.JTextField txtRandstreifenbreite;
    private javax.swing.JTextField txtSohlbreite;
    private javax.swing.JTextField txtVorlandbreite;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupUnterhaltungsmassnahmeEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupUnterhaltungsmassnahmeEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        linearReferencedLineEditor = (readOnly) ? new LinearReferencedLineRenderer() : new LinearReferencedLineEditor();
        linearReferencedLineEditor.setLineField("linie");
        linearReferencedLineEditor.setOtherLinesEnabled(true);
        linearReferencedLineEditor.setShowOtherInDialog(true);
//        linearReferencedLineEditor.setOtherLinesQueryAddition("gup_massnahme_sonstige gms", "gms.linie = ");
        linearReferencedLineEditor.setDrawingFeaturesEnabled(true);
        initComponents();
        RendererTools.makeReadOnly(txtBearbeiter);

        if (readOnly) {
            RendererTools.makeReadOnly(cbMassnahme);
            RendererTools.makeReadOnly(cbIntervall);
            RendererTools.makeReadOnly(cbVerbleib);
            RendererTools.makeReadOnly(cbJahr);
            RendererTools.makeReadOnly(txtBoeschungslaenge);
            RendererTools.makeReadOnly(txtBoeschungsneigung);
            RendererTools.makeReadOnly(txtDeichkronenbreite);
            RendererTools.makeReadOnly(txtRandstreifenbreite);
            RendererTools.makeReadOnly(txtSohlbreite);
            RendererTools.makeReadOnly(txtVorlandbreite);
            RendererTools.makeReadOnly(jTextArea1);
            RendererTools.makeReadOnly(cbAusfuehrung);
            RendererTools.makeReadOnly(cbAusfuehrungSec);
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

        lblBearbeiter = new javax.swing.JLabel();
        lblMassnahme = new javax.swing.JLabel();
        lblJahr = new javax.swing.JLabel();
        lblIntervall = new javax.swing.JLabel();
        lblBemerkung = new javax.swing.JLabel();
        lblVerbleib = new javax.swing.JLabel();
        cbIntervall = new ScrollableComboBox();
        cbVerbleib = new ScrollableComboBox();
        txtBearbeiter = new javax.swing.JTextField();
        spBemerkung = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        linearReferencedLineEditor = linearReferencedLineEditor;
        jPanel1 = new javax.swing.JPanel();
        lblInfo = new javax.swing.JLabel();
        flowPanel = new javax.swing.JPanel();
        panBoeschungslaenge = new javax.swing.JPanel();
        lblBoeschungslaenge = new javax.swing.JLabel();
        txtBoeschungslaenge = new javax.swing.JTextField();
        panRandstreifen = new javax.swing.JPanel();
        lblRandstreifenbreite = new javax.swing.JLabel();
        txtRandstreifenbreite = new javax.swing.JTextField();
        panBoeschungsneigung = new javax.swing.JPanel();
        lblBoeschungsneigung = new javax.swing.JLabel();
        txtBoeschungsneigung = new javax.swing.JTextField();
        panSohlbreite = new javax.swing.JPanel();
        lblSohlbreite = new javax.swing.JLabel();
        txtSohlbreite = new javax.swing.JTextField();
        panDeichkronenbreite = new javax.swing.JPanel();
        lblDeichkronenbreite = new javax.swing.JLabel();
        txtDeichkronenbreite = new javax.swing.JTextField();
        panVorlandbreite = new javax.swing.JPanel();
        lblVorlandbreite = new javax.swing.JLabel();
        txtVorlandbreite = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        cbMassnahme = new MassnahmenComboBox();
        lblAusfuehrung = new javax.swing.JLabel();
        cbAusfuehrung = new ScrollableComboBox();
        cbJahr = new ScrollableComboBox();
        lblAusfuehrungSec = new javax.swing.JLabel();
        cbAusfuehrungSec = new ScrollableComboBox();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(994, 800));
        setLayout(new java.awt.GridBagLayout());

        lblBearbeiter.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblBearbeiter.text")); // NOI18N
        lblBearbeiter.setMaximumSize(new java.awt.Dimension(120, 17));
        lblBearbeiter.setMinimumSize(new java.awt.Dimension(120, 17));
        lblBearbeiter.setPreferredSize(new java.awt.Dimension(120, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblBearbeiter, gridBagConstraints);

        lblMassnahme.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblMassnahme.text")); // NOI18N
        lblMassnahme.setMaximumSize(new java.awt.Dimension(150, 17));
        lblMassnahme.setMinimumSize(new java.awt.Dimension(150, 17));
        lblMassnahme.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 5);
        add(lblMassnahme, gridBagConstraints);

        lblJahr.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblJahr.text")); // NOI18N
        lblJahr.setMaximumSize(new java.awt.Dimension(150, 17));
        lblJahr.setMinimumSize(new java.awt.Dimension(150, 17));
        lblJahr.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblJahr, gridBagConstraints);

        lblIntervall.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblIntervall.text")); // NOI18N
        lblIntervall.setMaximumSize(new java.awt.Dimension(80, 17));
        lblIntervall.setMinimumSize(new java.awt.Dimension(80, 17));
        lblIntervall.setPreferredSize(new java.awt.Dimension(80, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblIntervall, gridBagConstraints);

        lblBemerkung.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblBemerkung.text")); // NOI18N
        lblBemerkung.setMaximumSize(new java.awt.Dimension(150, 17));
        lblBemerkung.setMinimumSize(new java.awt.Dimension(150, 17));
        lblBemerkung.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblBemerkung, gridBagConstraints);

        lblVerbleib.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblVerbleib.text")); // NOI18N
        lblVerbleib.setMaximumSize(new java.awt.Dimension(150, 17));
        lblVerbleib.setMinimumSize(new java.awt.Dimension(150, 17));
        lblVerbleib.setPreferredSize(new java.awt.Dimension(150, 17));
        lblVerbleib.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblVerbleib, gridBagConstraints);

        cbIntervall.setMinimumSize(new java.awt.Dimension(170, 20));
        cbIntervall.setPreferredSize(new java.awt.Dimension(175, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.intervall}"),
                cbIntervall,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbIntervall.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbIntervallItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbIntervall, gridBagConstraints);

        cbVerbleib.setMaximumSize(new java.awt.Dimension(290, 20));
        cbVerbleib.setMinimumSize(new java.awt.Dimension(290, 20));
        cbVerbleib.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.verbleib}"),
                cbVerbleib,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbVerbleib.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbVerbleibItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbVerbleib, gridBagConstraints);

        txtBearbeiter.setMaximumSize(new java.awt.Dimension(200, 20));
        txtBearbeiter.setMinimumSize(new java.awt.Dimension(200, 20));
        txtBearbeiter.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bearbeiter}"),
                txtBearbeiter,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(txtBearbeiter, gridBagConstraints);

        spBemerkung.setMaximumSize(new java.awt.Dimension(280, 90));
        spBemerkung.setMinimumSize(new java.awt.Dimension(280, 90));
        spBemerkung.setPreferredSize(new java.awt.Dimension(300, 90));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(2);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hinweise}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        spBemerkung.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(spBemerkung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(linearReferencedLineEditor, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblInfo.text")); // NOI18N
        lblInfo.setMaximumSize(new java.awt.Dimension(260, 17));
        lblInfo.setMinimumSize(new java.awt.Dimension(260, 17));
        lblInfo.setPreferredSize(new java.awt.Dimension(260, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanel1.add(lblInfo, gridBagConstraints);

        flowPanel.setMinimumSize(new java.awt.Dimension(300, 170));
        flowPanel.setOpaque(false);
        flowPanel.setPreferredSize(new java.awt.Dimension(300, 210));

        panBoeschungslaenge.setOpaque(false);
        panBoeschungslaenge.setLayout(new java.awt.GridBagLayout());

        lblBoeschungslaenge.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblBoeschungslaenge.text")); // NOI18N
        lblBoeschungslaenge.setMaximumSize(new java.awt.Dimension(150, 17));
        lblBoeschungslaenge.setMinimumSize(new java.awt.Dimension(150, 17));
        lblBoeschungslaenge.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panBoeschungslaenge.add(lblBoeschungslaenge, gridBagConstraints);

        txtBoeschungslaenge.setMaximumSize(new java.awt.Dimension(100, 20));
        txtBoeschungslaenge.setMinimumSize(new java.awt.Dimension(60, 20));
        txtBoeschungslaenge.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.boeschungslaenge}"),
                txtBoeschungslaenge,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panBoeschungslaenge.add(txtBoeschungslaenge, gridBagConstraints);

        flowPanel.add(panBoeschungslaenge);

        panRandstreifen.setOpaque(false);
        panRandstreifen.setLayout(new java.awt.GridBagLayout());

        lblRandstreifenbreite.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblRandstreifenbreite.text")); // NOI18N
        lblRandstreifenbreite.setMaximumSize(new java.awt.Dimension(150, 17));
        lblRandstreifenbreite.setMinimumSize(new java.awt.Dimension(150, 17));
        lblRandstreifenbreite.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panRandstreifen.add(lblRandstreifenbreite, gridBagConstraints);

        txtRandstreifenbreite.setMaximumSize(new java.awt.Dimension(100, 20));
        txtRandstreifenbreite.setMinimumSize(new java.awt.Dimension(60, 20));
        txtRandstreifenbreite.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.randstreifenbreite}"),
                txtRandstreifenbreite,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panRandstreifen.add(txtRandstreifenbreite, gridBagConstraints);

        flowPanel.add(panRandstreifen);

        panBoeschungsneigung.setOpaque(false);
        panBoeschungsneigung.setLayout(new java.awt.GridBagLayout());

        lblBoeschungsneigung.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblBoeschungsneigung.text")); // NOI18N
        lblBoeschungsneigung.setMaximumSize(new java.awt.Dimension(150, 17));
        lblBoeschungsneigung.setMinimumSize(new java.awt.Dimension(150, 17));
        lblBoeschungsneigung.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panBoeschungsneigung.add(lblBoeschungsneigung, gridBagConstraints);

        txtBoeschungsneigung.setMaximumSize(new java.awt.Dimension(100, 20));
        txtBoeschungsneigung.setMinimumSize(new java.awt.Dimension(60, 20));
        txtBoeschungsneigung.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.boeschungsbreite}"),
                txtBoeschungsneigung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panBoeschungsneigung.add(txtBoeschungsneigung, gridBagConstraints);

        flowPanel.add(panBoeschungsneigung);

        panSohlbreite.setOpaque(false);
        panSohlbreite.setLayout(new java.awt.GridBagLayout());

        lblSohlbreite.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblSohlbreite.text")); // NOI18N
        lblSohlbreite.setMaximumSize(new java.awt.Dimension(150, 17));
        lblSohlbreite.setMinimumSize(new java.awt.Dimension(150, 17));
        lblSohlbreite.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panSohlbreite.add(lblSohlbreite, gridBagConstraints);

        txtSohlbreite.setMaximumSize(new java.awt.Dimension(100, 20));
        txtSohlbreite.setMinimumSize(new java.awt.Dimension(60, 20));
        txtSohlbreite.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sohlbreite}"),
                txtSohlbreite,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panSohlbreite.add(txtSohlbreite, gridBagConstraints);

        flowPanel.add(panSohlbreite);

        panDeichkronenbreite.setOpaque(false);
        panDeichkronenbreite.setLayout(new java.awt.GridBagLayout());

        lblDeichkronenbreite.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblDeichkronenbreite.text")); // NOI18N
        lblDeichkronenbreite.setMaximumSize(new java.awt.Dimension(150, 17));
        lblDeichkronenbreite.setMinimumSize(new java.awt.Dimension(150, 17));
        lblDeichkronenbreite.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panDeichkronenbreite.add(lblDeichkronenbreite, gridBagConstraints);

        txtDeichkronenbreite.setMaximumSize(new java.awt.Dimension(100, 20));
        txtDeichkronenbreite.setMinimumSize(new java.awt.Dimension(60, 20));
        txtDeichkronenbreite.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.deichkronenbreite}"),
                txtDeichkronenbreite,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panDeichkronenbreite.add(txtDeichkronenbreite, gridBagConstraints);

        flowPanel.add(panDeichkronenbreite);

        panVorlandbreite.setOpaque(false);
        panVorlandbreite.setLayout(new java.awt.GridBagLayout());

        lblVorlandbreite.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblVorlandbreite.text")); // NOI18N
        lblVorlandbreite.setMaximumSize(new java.awt.Dimension(150, 17));
        lblVorlandbreite.setMinimumSize(new java.awt.Dimension(150, 17));
        lblVorlandbreite.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panVorlandbreite.add(lblVorlandbreite, gridBagConstraints);

        txtVorlandbreite.setMaximumSize(new java.awt.Dimension(100, 20));
        txtVorlandbreite.setMinimumSize(new java.awt.Dimension(60, 20));
        txtVorlandbreite.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.vorlandbreite}"),
                txtVorlandbreite,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panVorlandbreite.add(txtVorlandbreite, gridBagConstraints);

        flowPanel.add(panVorlandbreite);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel1.add(flowPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        add(jSeparator1, gridBagConstraints);

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        add(jPanel2, gridBagConstraints);

        cbMassnahme.setMaximumSize(new java.awt.Dimension(290, 20));
        cbMassnahme.setMinimumSize(new java.awt.Dimension(290, 20));
        cbMassnahme.setPreferredSize(new java.awt.Dimension(290, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massnahme}"),
                cbMassnahme,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbMassnahme.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbMassnahmeItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        add(cbMassnahme, gridBagConstraints);

        lblAusfuehrung.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblAusfuehrung.text")); // NOI18N
        lblAusfuehrung.setMaximumSize(new java.awt.Dimension(150, 17));
        lblAusfuehrung.setMinimumSize(new java.awt.Dimension(150, 17));
        lblAusfuehrung.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblAusfuehrung, gridBagConstraints);

        cbAusfuehrung.setMinimumSize(new java.awt.Dimension(100, 20));
        cbAusfuehrung.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ausfuehrungszeitpunkt}"),
                cbAusfuehrung,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbAusfuehrung.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbAusfuehrungItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbAusfuehrung, gridBagConstraints);

        cbJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        cbJahr.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.jahr}"),
                cbJahr,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbJahr.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbJahrItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbJahr, gridBagConstraints);

        lblAusfuehrungSec.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblAusfuehrungSec.text")); // NOI18N
        lblAusfuehrungSec.setMaximumSize(new java.awt.Dimension(175, 17));
        lblAusfuehrungSec.setMinimumSize(new java.awt.Dimension(175, 17));
        lblAusfuehrungSec.setPreferredSize(new java.awt.Dimension(175, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblAusfuehrungSec, gridBagConstraints);

        cbAusfuehrungSec.setMinimumSize(new java.awt.Dimension(85, 20));
        cbAusfuehrungSec.setPreferredSize(new java.awt.Dimension(85, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.second_ausfuehrungszeitpunkt}"),
                cbAusfuehrungSec,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbAusfuehrungSec.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbAusfuehrungSecItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbAusfuehrungSec, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbMassnahmeItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbMassnahmeItemStateChanged
        // Wenn die entsprechende Combobox gleichzeitig auch den Fokus hat, dann handelt es sich um eine
        // Aenderung des Nutzers. Hat die Combobox nicht den Focus, dann aenderte sich das Item aus
        // der Combobox bzw. dem Model heraus
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                changeBearbeiter();
            }

            if (evt.getStateChange() == ItemEvent.SELECTED) {
                deActivateAdditionalAttributes((CidsBean)evt.getItem());
            }
        }
    } //GEN-LAST:event_cbMassnahmeItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbIntervallItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbIntervallItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                final CidsBean bean = (CidsBean)evt.getItem();
                cbAusfuehrungSec.setEnabled((bean != null) && bean.getProperty("id").equals(INTERVAL_TWO_TIMES));
                changeBearbeiter();
            }
        }
    }                                                                              //GEN-LAST:event_cbIntervallItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbAusfuehrungItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbAusfuehrungItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                changeBearbeiter();
            }
        }
    }                                                                                //GEN-LAST:event_cbAusfuehrungItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbVerbleibItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbVerbleibItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                changeBearbeiter();
            }
        }
    }                                                                             //GEN-LAST:event_cbVerbleibItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbJahrItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbJahrItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                changeBearbeiter();
            }
        }
    }                                                                         //GEN-LAST:event_cbJahrItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbAusfuehrungSecItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbAusfuehrungSecItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                changeBearbeiter();
            }
        }
    }                                                                                   //GEN-LAST:event_cbAusfuehrungSecItemStateChanged

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
            final List<CidsBean> linieBeans = new ArrayList<CidsBean>();

            if (getMassnahmen() != null) {
                for (final CidsBean b : getMassnahmen()) {
                    final CidsBean tmp = (CidsBean)b.getProperty("linie");

                    if ((tmp != null) && (!tmp.getProperty("id").equals(cidsBean.getProperty("linie.id")))) {
                        linieBeans.add(tmp);
                    }
                }
            }
            linearReferencedLineEditor.setOtherLines(linieBeans);
            linearReferencedLineEditor.setCidsBean(cidsBean);
            final CidsBean bean = (CidsBean)cidsBean.getProperty("intervall");
            cbAusfuehrungSec.setEnabled((bean != null) && bean.getProperty("id").equals(INTERVAL_TWO_TIMES));
            final CidsBean line = (CidsBean)cidsBean.getProperty("linie");

            if (line != null) {
                line.addPropertyChangeListener(this);
                final CidsBean von = (CidsBean)line.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_FROM);
                final CidsBean bis = (CidsBean)line.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_TO);

                if (von != null) {
                    von.addPropertyChangeListener(this);
                }
                if (bis != null) {
                    bis.addPropertyChangeListener(this);
                }
            }
            cidsBean.addPropertyChangeListener(this);
            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final Object item = cidsBean.getProperty("massnahme");
                        if (item != null) {
                            cbMassnahme.setSelectedItem(item);
                        }
                    }
                }).start();

            deActivateAdditionalAttributes((CidsBean)cidsBean.getProperty("massnahme"));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  massnahme  DOCUMENT ME!
     */
    private void deActivateAdditionalAttributes(final CidsBean massnahme) {
        if (massnahme != null) {
            final Object bl = massnahme.getProperty("boeschungslaenge");
            final Object bn = massnahme.getProperty("boeschungsneigung");
            final Object db = massnahme.getProperty("deichkronenbreite");
            final Object rs = massnahme.getProperty("randstreifenbreite");
            final Object sb = massnahme.getProperty("sohlbreite");
            final Object vb = massnahme.getProperty("vorlandbreite");

            panBoeschungslaenge.setVisible((bl != null) && ((Boolean)bl).booleanValue());
            panBoeschungsneigung.setVisible((bn != null) && ((Boolean)bn).booleanValue());
            panDeichkronenbreite.setVisible((db != null) && ((Boolean)db).booleanValue());
            panRandstreifen.setVisible((rs != null) && ((Boolean)rs).booleanValue());
            panSohlbreite.setVisible((sb != null) && ((Boolean)sb).booleanValue());
            panVorlandbreite.setVisible((vb != null) && ((Boolean)vb).booleanValue());
        } else {
            panBoeschungslaenge.setVisible(false);
            panBoeschungsneigung.setVisible(false);
            panDeichkronenbreite.setVisible(false);
            panRandstreifen.setVisible(false);
            panSohlbreite.setVisible(false);
            panVorlandbreite.setVisible(false);
        }
    }

//    /**
//     * DOCUMENT ME!
//     */
//    public void refresh() {
//        linearReferencedLineEditor.refreshOtherLines();
//    }

    @Override
    public void dispose() {
        if (cidsBean != null) {
            cidsBean.removePropertyChangeListener(this);
            final CidsBean line = (CidsBean)cidsBean.getProperty("linie");

            if (line != null) {
                line.removePropertyChangeListener(this);
                final CidsBean von = (CidsBean)line.getProperty("von");
                final CidsBean bis = (CidsBean)line.getProperty("bis");

                if (von != null) {
                    von.removePropertyChangeListener(this);
                }
                if (bis != null) {
                    bis.removePropertyChangeListener(this);
                }
            }
        }
        bindingGroup.unbind();
        linearReferencedLineEditor.dispose();
    }

    @Override
    public String getTitle() {
        return "Maßnahme";
    }

    @Override
    public void setTitle(final String title) {
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        linearReferencedLineEditor.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        return linearReferencedLineEditor.prepareForSave();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the massnahmen
     */
    public List<CidsBean> getMassnahmen() {
        return massnahmen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  massnahmen  the massnahmen to set
     */
    public void setMassnahmen(final List<CidsBean> massnahmen) {
        this.massnahmen = massnahmen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  kompartiment  the kompartiment to set
     */
    public void setKompartiment(final int kompartiment) {
        ((MassnahmenComboBox)cbMassnahme).setKompartiment(kompartiment);
        cbMassnahme.setSelectedItem(null);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        // Die Comboboxen loesen auch PropertyChangeEvents aus, ohne dass der Nutzer etwas tut.
        // Diese muessen also gesondert behandelt werden
        if (!readOnly && !evt.getPropertyName().equals("bearbeiter")
                    && !evt.getPropertyName().equals("intervall")
                    && !evt.getPropertyName().equals("ausfuehrungszeitpunkt")
                    && !evt.getPropertyName().equals("verbleib") && !evt.getPropertyName().equals("massnahme")) {
            changeBearbeiter();

            if (evt.getPropertyName().equals("von") || evt.getPropertyName().equals("bis")) {
                ((CidsBean)evt.getOldValue()).removePropertyChangeListener(this);
                ((CidsBean)evt.getNewValue()).addPropertyChangeListener(this);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void changeBearbeiter() {
        if (cidsBean != null) {
            cidsBean.getMetaObject().setAllClasses();
            try {
                final User user = SessionManager.getSession().getUser();
                cidsBean.setProperty("bearbeiter", user.getName() + "@" + user.getDomain());
            } catch (Exception ex) {
                LOG.error(ex, ex);
            }
        }
    }
}
