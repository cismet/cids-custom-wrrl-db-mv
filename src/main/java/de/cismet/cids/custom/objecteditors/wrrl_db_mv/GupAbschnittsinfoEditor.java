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

import Sirius.server.search.CidsServerSearch;

import com.vividsolutions.jts.geom.Geometry;

import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Collection;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkkSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;

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
public class GupAbschnittsinfoEditor extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbFische1;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbFischotter1;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbStandsicherheitLinks;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbStandsicherheitRechts;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbWirbellose1;
    private de.cismet.cids.editors.DefaultBindableCheckboxField ccAusbauzustand;
    private de.cismet.cids.editors.DefaultBindableCheckboxField ccUmlandLinks;
    private de.cismet.cids.editors.DefaultBindableCheckboxField ccUmlandLinks1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel jpDetails;
    private javax.swing.JPanel jpUmlandnutzung;
    private javax.swing.JLabel lblAusbauzustand;
    private javax.swing.JLabel lblBeschreibung;
    private javax.swing.JLabel lblFische1;
    private javax.swing.JLabel lblFischotter1;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblStandsicherheitLinks;
    private javax.swing.JLabel lblStandsicherheitRechts;
    private javax.swing.JLabel lblUmlandLinks;
    private javax.swing.JLabel lblUmlandRechts;
    private javax.swing.JLabel lblUnterPfl;
    private javax.swing.JLabel lblWirbellose1;
    private javax.swing.JLabel lblWkk;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panDurchgaengigkeit;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo3;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent2;
    private javax.swing.JPanel panStandsicherheit;
    private javax.swing.JScrollPane spBeschreibung;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtUnterPfl;
    private javax.swing.JTextField txtWkk;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupAbschnittsinfoEditor() {
        linearReferencedLineEditor = (readOnly) ? new LinearReferencedLineRenderer() : new LinearReferencedLineEditor();
        linearReferencedLineEditor.setLineField("linie");
        linearReferencedLineEditor.setOtherLinesEnabled(false);
        linearReferencedLineEditor.setDrawingFeaturesEnabled(false);
        initComponents();
        jTabbedPane1.setUI(new TabbedPaneUITransparent());
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        lblWkk = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblBeschreibung = new javax.swing.JLabel();
        txtWkk = new javax.swing.JTextField();
        spBeschreibung = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        linearReferencedLineEditor = linearReferencedLineEditor;
        txtName = new javax.swing.JTextField();
        jpUmlandnutzung = new javax.swing.JPanel();
        panInfo2 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        ccUmlandLinks = new de.cismet.cids.editors.DefaultBindableCheckboxField();
        ccUmlandLinks1 = new de.cismet.cids.editors.DefaultBindableCheckboxField();
        lblUmlandLinks = new javax.swing.JLabel();
        lblUmlandRechts = new javax.swing.JLabel();
        jpDetails = new javax.swing.JPanel();
        panInfo3 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        panDurchgaengigkeit = new javax.swing.JPanel();
        lblFische1 = new javax.swing.JLabel();
        cbFische1 = new ScrollableComboBox();
        lblFischotter1 = new javax.swing.JLabel();
        cbFischotter1 = new ScrollableComboBox();
        lblWirbellose1 = new javax.swing.JLabel();
        cbWirbellose1 = new ScrollableComboBox();
        lblAusbauzustand = new javax.swing.JLabel();
        panStandsicherheit = new javax.swing.JPanel();
        lblStandsicherheitLinks = new javax.swing.JLabel();
        cbStandsicherheitLinks = new ScrollableComboBox();
        lblStandsicherheitRechts = new javax.swing.JLabel();
        cbStandsicherheitRechts = new ScrollableComboBox();
        lblUnterPfl = new javax.swing.JLabel();
        txtUnterPfl = new javax.swing.JTextField();
        ccAusbauzustand = new de.cismet.cids.editors.DefaultBindableCheckboxField();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(994, 800));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        panInfo1.setLayout(new java.awt.GridBagLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo1.add(panHeadInfo, new java.awt.GridBagConstraints());

        panInfoContent.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());
        panInfo1.add(panInfoContent, new java.awt.GridBagConstraints());

        lblWkk.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblWkk.text")); // NOI18N
        lblWkk.setMaximumSize(new java.awt.Dimension(170, 17));
        lblWkk.setMinimumSize(new java.awt.Dimension(170, 17));
        lblWkk.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 10, 5, 5);
        panInfo1.add(lblWkk, gridBagConstraints);

        lblName.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblName.text")); // NOI18N
        lblName.setMaximumSize(new java.awt.Dimension(170, 17));
        lblName.setMinimumSize(new java.awt.Dimension(170, 17));
        lblName.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfo1.add(lblName, gridBagConstraints);

        lblBeschreibung.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblBeschreibung.text")); // NOI18N
        lblBeschreibung.setMaximumSize(new java.awt.Dimension(170, 17));
        lblBeschreibung.setMinimumSize(new java.awt.Dimension(170, 17));
        lblBeschreibung.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfo1.add(lblBeschreibung, gridBagConstraints);

        txtWkk.setEnabled(false);
        txtWkk.setMaximumSize(new java.awt.Dimension(280, 20));
        txtWkk.setMinimumSize(new java.awt.Dimension(280, 20));
        txtWkk.setPreferredSize(new java.awt.Dimension(280, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 5, 5);
        panInfo1.add(txtWkk, gridBagConstraints);

        spBeschreibung.setMaximumSize(new java.awt.Dimension(280, 90));
        spBeschreibung.setMinimumSize(new java.awt.Dimension(280, 90));
        spBeschreibung.setPreferredSize(new java.awt.Dimension(280, 90));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.beschreibung}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        spBeschreibung.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfo1.add(spBeschreibung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        panInfo1.add(linearReferencedLineEditor, gridBagConstraints);

        txtName.setMaximumSize(new java.awt.Dimension(280, 20));
        txtName.setMinimumSize(new java.awt.Dimension(280, 20));
        txtName.setPreferredSize(new java.awt.Dimension(280, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfo1.add(txtName, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel1.add(panInfo1, gridBagConstraints);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupMassnahmeUferEditor.jPanel1.tabTitle"),
            jPanel1); // NOI18N

        jpUmlandnutzung.setOpaque(false);
        jpUmlandnutzung.setLayout(new java.awt.GridBagLayout());

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo2.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        ccUmlandLinks.setMinimumSize(new java.awt.Dimension(200, 250));
        ccUmlandLinks.setOpaque(false);
        ccUmlandLinks.setPreferredSize(new java.awt.Dimension(200, 250));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.umlandnutzung_links}"),
                ccUmlandLinks,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(ccUmlandLinks, gridBagConstraints);

        ccUmlandLinks1.setMinimumSize(new java.awt.Dimension(200, 250));
        ccUmlandLinks1.setOpaque(false);
        ccUmlandLinks1.setPreferredSize(new java.awt.Dimension(200, 250));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.umlandnutzung_rechts}"),
                ccUmlandLinks1,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(ccUmlandLinks1, gridBagConstraints);

        lblUmlandLinks.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblUmlandLinks.text")); // NOI18N
        lblUmlandLinks.setMaximumSize(new java.awt.Dimension(150, 17));
        lblUmlandLinks.setMinimumSize(new java.awt.Dimension(150, 17));
        lblUmlandLinks.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblUmlandLinks, gridBagConstraints);

        lblUmlandRechts.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblUmlandRechts.text")); // NOI18N
        lblUmlandRechts.setMaximumSize(new java.awt.Dimension(150, 17));
        lblUmlandRechts.setMinimumSize(new java.awt.Dimension(150, 17));
        lblUmlandRechts.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(lblUmlandRechts, gridBagConstraints);

        panInfo2.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jpUmlandnutzung.add(panInfo2, gridBagConstraints);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.jpUmlandnutzung.tabTitle"),
            jpUmlandnutzung); // NOI18N

        jpDetails.setOpaque(false);
        jpDetails.setLayout(new java.awt.GridBagLayout());

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblHeading2.text")); // NOI18N
        panHeadInfo2.add(lblHeading2);

        panInfo3.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        panDurchgaengigkeit.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    GupAbschnittsinfoEditor.class,
                    "GupAbschnittsinfoEditor.panDurchgaengigkeit.border.title"))); // NOI18N
        panDurchgaengigkeit.setMaximumSize(new java.awt.Dimension(555, 57));
        panDurchgaengigkeit.setMinimumSize(new java.awt.Dimension(555, 57));
        panDurchgaengigkeit.setOpaque(false);
        panDurchgaengigkeit.setPreferredSize(new java.awt.Dimension(555, 57));
        panDurchgaengigkeit.setLayout(new java.awt.GridBagLayout());

        lblFische1.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblFische1.text")); // NOI18N
        lblFische1.setMaximumSize(new java.awt.Dimension(70, 17));
        lblFische1.setMinimumSize(new java.awt.Dimension(70, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDurchgaengigkeit.add(lblFische1, gridBagConstraints);

        cbFische1.setMaximumSize(new java.awt.Dimension(100, 20));
        cbFische1.setMinimumSize(new java.awt.Dimension(100, 20));
        cbFische1.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDurchgaengigkeit.add(cbFische1, gridBagConstraints);

        lblFischotter1.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblFischotter1.text")); // NOI18N
        lblFischotter1.setMaximumSize(new java.awt.Dimension(70, 17));
        lblFischotter1.setMinimumSize(new java.awt.Dimension(70, 17));
        lblFischotter1.setPreferredSize(new java.awt.Dimension(70, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDurchgaengigkeit.add(lblFischotter1, gridBagConstraints);

        cbFischotter1.setMaximumSize(new java.awt.Dimension(100, 20));
        cbFischotter1.setMinimumSize(new java.awt.Dimension(100, 20));
        cbFischotter1.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDurchgaengigkeit.add(cbFischotter1, gridBagConstraints);

        lblWirbellose1.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblWirbellose1.text")); // NOI18N
        lblWirbellose1.setMaximumSize(new java.awt.Dimension(70, 17));
        lblWirbellose1.setMinimumSize(new java.awt.Dimension(70, 17));
        lblWirbellose1.setPreferredSize(new java.awt.Dimension(70, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDurchgaengigkeit.add(lblWirbellose1, gridBagConstraints);

        cbWirbellose1.setMaximumSize(new java.awt.Dimension(100, 20));
        cbWirbellose1.setMinimumSize(new java.awt.Dimension(100, 20));
        cbWirbellose1.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDurchgaengigkeit.add(cbWirbellose1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 7, 10, 2);
        panInfoContent2.add(panDurchgaengigkeit, gridBagConstraints);

        lblAusbauzustand.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblAusbauzustand.text")); // NOI18N
        lblAusbauzustand.setMaximumSize(new java.awt.Dimension(170, 17));
        lblAusbauzustand.setMinimumSize(new java.awt.Dimension(170, 17));
        lblAusbauzustand.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent2.add(lblAusbauzustand, gridBagConstraints);

        panStandsicherheit.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    GupAbschnittsinfoEditor.class,
                    "GupAbschnittsinfoEditor.panStandsicherheit.border.title"))); // NOI18N
        panStandsicherheit.setMaximumSize(new java.awt.Dimension(555, 57));
        panStandsicherheit.setMinimumSize(new java.awt.Dimension(555, 57));
        panStandsicherheit.setOpaque(false);
        panStandsicherheit.setPreferredSize(new java.awt.Dimension(555, 57));
        panStandsicherheit.setLayout(new java.awt.GridBagLayout());

        lblStandsicherheitLinks.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblStandsicherheitLinks.text")); // NOI18N
        lblStandsicherheitLinks.setMaximumSize(new java.awt.Dimension(50, 17));
        lblStandsicherheitLinks.setMinimumSize(new java.awt.Dimension(50, 17));
        lblStandsicherheitLinks.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStandsicherheit.add(lblStandsicherheitLinks, gridBagConstraints);

        cbStandsicherheitLinks.setMaximumSize(new java.awt.Dimension(170, 20));
        cbStandsicherheitLinks.setMinimumSize(new java.awt.Dimension(170, 20));
        cbStandsicherheitLinks.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStandsicherheit.add(cbStandsicherheitLinks, gridBagConstraints);

        lblStandsicherheitRechts.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblStandsicherheitRechts.text")); // NOI18N
        lblStandsicherheitRechts.setMaximumSize(new java.awt.Dimension(50, 17));
        lblStandsicherheitRechts.setMinimumSize(new java.awt.Dimension(50, 17));
        lblStandsicherheitRechts.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStandsicherheit.add(lblStandsicherheitRechts, gridBagConstraints);

        cbStandsicherheitRechts.setMaximumSize(new java.awt.Dimension(170, 20));
        cbStandsicherheitRechts.setMinimumSize(new java.awt.Dimension(170, 20));
        cbStandsicherheitRechts.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStandsicherheit.add(cbStandsicherheitRechts, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 7, 10, 2);
        panInfoContent2.add(panStandsicherheit, gridBagConstraints);

        lblUnterPfl.setText(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.lblUnterPfl.text")); // NOI18N
        lblUnterPfl.setMaximumSize(new java.awt.Dimension(170, 17));
        lblUnterPfl.setMinimumSize(new java.awt.Dimension(170, 17));
        lblUnterPfl.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 5);
        panInfoContent2.add(lblUnterPfl, gridBagConstraints);

        txtUnterPfl.setMaximumSize(new java.awt.Dimension(280, 20));
        txtUnterPfl.setMinimumSize(new java.awt.Dimension(280, 20));
        txtUnterPfl.setPreferredSize(new java.awt.Dimension(280, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        panInfoContent2.add(txtUnterPfl, gridBagConstraints);

        ccAusbauzustand.setMinimumSize(new java.awt.Dimension(150, 100));
        ccAusbauzustand.setOpaque(false);
        ccAusbauzustand.setPreferredSize(new java.awt.Dimension(150, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent2.add(ccAusbauzustand, gridBagConstraints);

        panInfo3.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jpDetails.add(panInfo3, gridBagConstraints);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(
                GupAbschnittsinfoEditor.class,
                "GupAbschnittsinfoEditor.jpDetails.tabTitle"),
            jpDetails); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        txtWkk.setText("");

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
            linearReferencedLineEditor.setCidsBean(cidsBean);
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
        linearReferencedLineEditor.dispose();
    }

    @Override
    public String getTitle() {
        return "Ufer-Maßnahme";
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
}