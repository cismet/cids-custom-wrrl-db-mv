/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;

import java.awt.Color;
import java.awt.EventQueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import de.cismet.cids.editors.DefaultCustomObjectEditor;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkePanTwo extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuerbauwerkePanOne.class);

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public enum Kategorie {

        //~ Enum constants -----------------------------------------------------

        Fliessgewaesser, Standgewaesser
    }

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private MetaObject moWk;
    private boolean readOnly = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbArt;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbDetailtyp;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbIntervall;
    private javax.swing.JCheckBox cbPruefungErfolgt;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbRegulierbarkeit;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbSubstrat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAnlagenameKey;
    private javax.swing.JLabel lblBauwerkKey;
    private javax.swing.JLabel lblBemerkAltKey;
    private javax.swing.JLabel lblBreiteKey;
    private javax.swing.JLabel lblDetailTyp;
    private javax.swing.JLabel lblDurchmesserKey;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHoeheKey;
    private javax.swing.JLabel lblIntervallKey;
    private javax.swing.JLabel lblLaengeKey;
    private javax.swing.JLabel lblMaterialKey;
    private javax.swing.JLabel lblRegulierbarkeit;
    private javax.swing.JLabel lblSohlbefestigungLaengeKey;
    private javax.swing.JLabel lblSohlbefestigungOhMaterialKey;
    private javax.swing.JLabel lblSohlbefestigungUhMaterialKey;
    private javax.swing.JLabel lblStauhoeheKey;
    private javax.swing.JLabel lblSubstratKey;
    private javax.swing.JLabel lblUeberdeckungKey;
    private javax.swing.JLabel lblWGwkAchsKey;
    private javax.swing.JLabel lblWaKoerperKey;
    private javax.swing.JLabel lblWaKoerperKey1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JTextArea tareaBemerkung;
    private javax.swing.JTextArea tareaBeschreibung;
    private javax.swing.JTextField txtAnderesBauwerk;
    private javax.swing.JTextField txtBreite;
    private javax.swing.JTextField txtDurchmesser;
    private javax.swing.JTextField txtHoehe;
    private javax.swing.JTextField txtLaenge;
    private javax.swing.JTextField txtMatOberhalb;
    private javax.swing.JTextField txtMatUnterhalb;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSohllaenge;
    private javax.swing.JTextField txtStauhoehe;
    private javax.swing.JTextField txtUeberdeckung;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form QuerbauwerkePanOne.
     */
    public QuerbauwerkePanTwo() {
        this(false);
    }

    /**
     * Creates new form QuerbauwerkePanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public QuerbauwerkePanTwo(final boolean readOnly) {
        initComponents();

        this.readOnly = readOnly;

        if (readOnly) {
            RendererTools.makeReadOnly(tareaBeschreibung);
            RendererTools.makeReadOnly(tareaBemerkung);
            RendererTools.makeReadOnly(cbPruefungErfolgt);
            RendererTools.makeReadOnly(txtMatOberhalb);
            RendererTools.makeReadOnly(txtName);
            RendererTools.makeReadOnly(txtAnderesBauwerk);
            RendererTools.makeReadOnly(txtMatUnterhalb);
            RendererTools.makeReadOnly(txtMaterial);
            RendererTools.makeReadOnly(txtHoehe);
            RendererTools.makeReadOnly(txtLaenge);
            RendererTools.makeReadOnly(txtBreite);
            RendererTools.makeReadOnly(txtDurchmesser);
            RendererTools.makeReadOnly(txtUeberdeckung);
            RendererTools.makeReadOnly(txtStauhoehe);
            RendererTools.makeReadOnly(txtSohllaenge);
            RendererTools.makeReadOnly(cbArt);
            RendererTools.makeReadOnly(cbIntervall);
            RendererTools.makeReadOnly(cbSubstrat);
            RendererTools.makeReadOnly(cbDetailtyp);
            RendererTools.makeReadOnly(cbRegulierbarkeit);
            jLabel10.setForeground(Color.BLUE);
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

        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtHoehe = new javax.swing.JTextField();
        cbArt = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblBauwerkKey = new javax.swing.JLabel();
        lblAnlagenameKey = new javax.swing.JLabel();
        lblLaengeKey = new javax.swing.JLabel();
        lblHoeheKey = new javax.swing.JLabel();
        lblBreiteKey = new javax.swing.JLabel();
        lblDurchmesserKey = new javax.swing.JLabel();
        lblMaterialKey = new javax.swing.JLabel();
        lblUeberdeckungKey = new javax.swing.JLabel();
        lblStauhoeheKey = new javax.swing.JLabel();
        txtBreite = new javax.swing.JTextField();
        txtDurchmesser = new javax.swing.JTextField();
        txtUeberdeckung = new javax.swing.JTextField();
        txtStauhoehe = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtMaterial = new javax.swing.JTextField();
        txtLaenge = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtAnderesBauwerk = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblWaKoerperKey1 = new javax.swing.JLabel();
        lblWGwkAchsKey = new javax.swing.JLabel();
        lblWaKoerperKey = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cbPruefungErfolgt = new javax.swing.JCheckBox();
        lblDetailTyp = new javax.swing.JLabel();
        cbDetailtyp = new DefaultBindableReferenceCombo(new DefaultBindableReferenceCombo.CategorisedOption("=>"),
                new DefaultBindableReferenceCombo.SortingColumnOption("sort"));
        lblRegulierbarkeit = new javax.swing.JLabel();
        cbRegulierbarkeit = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tareaBemerkung = new javax.swing.JTextArea();
        lblBemerkAltKey = new javax.swing.JLabel();
        lblSohlbefestigungLaengeKey = new javax.swing.JLabel();
        txtSohllaenge = new javax.swing.JTextField();
        lblSohlbefestigungUhMaterialKey = new javax.swing.JLabel();
        txtMatUnterhalb = new javax.swing.JTextField();
        lblSohlbefestigungOhMaterialKey = new javax.swing.JLabel();
        txtMatOberhalb = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblIntervallKey = new javax.swing.JLabel();
        cbIntervall = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cbSubstrat = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblSubstratKey = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tareaBeschreibung = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(jPanel2, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        txtHoehe.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtHoehe.setMinimumSize(new java.awt.Dimension(250, 25));
        txtHoehe.setPreferredSize(new java.awt.Dimension(250, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hoehe}"),
                txtHoehe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtHoehe, gridBagConstraints);

        cbArt.setMinimumSize(new java.awt.Dimension(250, 25));
        cbArt.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bauwerk}"),
                cbArt,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbArt, gridBagConstraints);

        lblBauwerkKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblBauwerkKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblBauwerkKey, gridBagConstraints);

        lblAnlagenameKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblAnlagenameKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblAnlagenameKey, gridBagConstraints);

        lblLaengeKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblLaengeKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblLaengeKey, gridBagConstraints);

        lblHoeheKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblHoeheKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblHoeheKey, gridBagConstraints);

        lblBreiteKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblBreiteKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblBreiteKey, gridBagConstraints);

        lblDurchmesserKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblDurchmesserKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblDurchmesserKey, gridBagConstraints);

        lblMaterialKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblMaterialKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblMaterialKey, gridBagConstraints);

        lblUeberdeckungKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblUeberdeckungKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblUeberdeckungKey, gridBagConstraints);

        lblStauhoeheKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblStauhoeheKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblStauhoeheKey, gridBagConstraints);

        txtBreite.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtBreite.setMinimumSize(new java.awt.Dimension(250, 25));
        txtBreite.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.breite}"),
                txtBreite,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtBreite, gridBagConstraints);

        txtDurchmesser.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtDurchmesser.setMinimumSize(new java.awt.Dimension(250, 25));
        txtDurchmesser.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.durchmesser}"),
                txtDurchmesser,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtDurchmesser, gridBagConstraints);

        txtUeberdeckung.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtUeberdeckung.setMinimumSize(new java.awt.Dimension(250, 25));
        txtUeberdeckung.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ueberdeckung}"),
                txtUeberdeckung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtUeberdeckung, gridBagConstraints);

        txtStauhoehe.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtStauhoehe.setMinimumSize(new java.awt.Dimension(250, 25));
        txtStauhoehe.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stauhoehe}"),
                txtStauhoehe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtStauhoehe, gridBagConstraints);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel3.add(jLabel1, gridBagConstraints);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel3.add(jLabel2, gridBagConstraints);

        jLabel3.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.jLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel3.add(jLabel3, gridBagConstraints);

        jLabel4.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.jLabel4.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel3.add(jLabel4, gridBagConstraints);

        jLabel5.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.jLabel5.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel3.add(jLabel5, gridBagConstraints);

        jLabel7.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.jLabel7.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel3.add(jLabel7, gridBagConstraints);

        txtName.setMinimumSize(new java.awt.Dimension(250, 25));
        txtName.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.anlagename}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtName, gridBagConstraints);

        txtMaterial.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtMaterial.setMinimumSize(new java.awt.Dimension(250, 25));
        txtMaterial.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.material}"),
                txtMaterial,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtMaterial, gridBagConstraints);

        txtLaenge.setMinimumSize(new java.awt.Dimension(250, 25));
        txtLaenge.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laenge}"),
                txtLaenge,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtLaenge, gridBagConstraints);

        jLabel8.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.jLabel8.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel8, gridBagConstraints);

        jPanel6.setOpaque(false);

        final javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jPanel6, gridBagConstraints);

        txtAnderesBauwerk.setMinimumSize(new java.awt.Dimension(250, 25));
        txtAnderesBauwerk.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bauw_ander}"),
                txtAnderesBauwerk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtAnderesBauwerk, gridBagConstraints);

        jLabel10.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.jLabel10.text")); // NOI18N
        jLabel10.setMinimumSize(new java.awt.Dimension(250, 25));
        jLabel10.setPreferredSize(new java.awt.Dimension(250, 25));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    jLabel10MouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel10, gridBagConstraints);

        jLabel11.setMinimumSize(new java.awt.Dimension(250, 25));
        jLabel11.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stat09.route.gwk}"),
                jLabel11,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel11, gridBagConstraints);

        jLabel12.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.jLabel12.text")); // NOI18N
        jLabel12.setMinimumSize(new java.awt.Dimension(250, 25));
        jLabel12.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel12, gridBagConstraints);

        lblWaKoerperKey1.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblWaKoerperKey1.text")); // NOI18N
        lblWaKoerperKey1.setMinimumSize(new java.awt.Dimension(150, 17));
        lblWaKoerperKey1.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblWaKoerperKey1, gridBagConstraints);

        lblWGwkAchsKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblWGwkAchsKey.text")); // NOI18N
        lblWGwkAchsKey.setMinimumSize(new java.awt.Dimension(150, 17));
        lblWGwkAchsKey.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblWGwkAchsKey, gridBagConstraints);

        lblWaKoerperKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblWaKoerperKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblWaKoerperKey, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        cbPruefungErfolgt.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.cbPruefungErfolgt.text",
                new Object[] {})); // NOI18N
        cbPruefungErfolgt.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.check_sohlgleiten}"),
                cbPruefungErfolgt,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(cbPruefungErfolgt, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jPanel1, gridBagConstraints);

        lblDetailTyp.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblDetailTyp.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblDetailTyp, gridBagConstraints);

        cbDetailtyp.setMinimumSize(new java.awt.Dimension(250, 25));
        cbDetailtyp.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.detailtyp}"),
                cbDetailtyp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbDetailtyp.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbDetailtypItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbDetailtyp, gridBagConstraints);

        lblRegulierbarkeit.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblRegulierbarkeit.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblRegulierbarkeit, gridBagConstraints);

        cbRegulierbarkeit.setMinimumSize(new java.awt.Dimension(250, 25));
        cbRegulierbarkeit.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.regulierbarkeit}"),
                cbRegulierbarkeit,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbRegulierbarkeit, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jPanel3, gridBagConstraints);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setMinimumSize(new java.awt.Dimension(250, 142));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(250, 142));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkungen}"),
                tareaBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(tareaBemerkung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jScrollPane2, gridBagConstraints);

        lblBemerkAltKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblBemerkAltKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblBemerkAltKey, gridBagConstraints);

        lblSohlbefestigungLaengeKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblSohlbefestigungLaengeKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSohlbefestigungLaengeKey, gridBagConstraints);

        txtSohllaenge.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtSohllaenge.setMinimumSize(new java.awt.Dimension(250, 25));
        txtSohllaenge.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sohlbefestigung_laenge}"),
                txtSohllaenge,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSohllaenge, gridBagConstraints);

        lblSohlbefestigungUhMaterialKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblSohlbefestigungUhMaterialKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSohlbefestigungUhMaterialKey, gridBagConstraints);

        txtMatUnterhalb.setMinimumSize(new java.awt.Dimension(250, 25));
        txtMatUnterhalb.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sohlbefestigung_uh_material}"),
                txtMatUnterhalb,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtMatUnterhalb, gridBagConstraints);

        lblSohlbefestigungOhMaterialKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblSohlbefestigungOhMaterialKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSohlbefestigungOhMaterialKey, gridBagConstraints);

        txtMatOberhalb.setMinimumSize(new java.awt.Dimension(250, 25));
        txtMatOberhalb.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sohlbefestigung_oh_material}"),
                txtMatOberhalb,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtMatOberhalb, gridBagConstraints);

        jPanel5.setOpaque(false);

        final javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                517,
                Short.MAX_VALUE));
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                66,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jPanel5, gridBagConstraints);

        jLabel6.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.jLabel6.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel4.add(jLabel6, gridBagConstraints);

        lblIntervallKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblIntervallKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblIntervallKey, gridBagConstraints);

        cbIntervall.setMinimumSize(new java.awt.Dimension(250, 25));
        cbIntervall.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.intervall}"),
                cbIntervall,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbIntervall, gridBagConstraints);

        cbSubstrat.setMinimumSize(new java.awt.Dimension(250, 25));
        cbSubstrat.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.substrat}"),
                cbSubstrat,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbSubstrat, gridBagConstraints);

        lblSubstratKey.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.lblSubstratKey.text")); // NOI18N
        lblSubstratKey.setMinimumSize(new java.awt.Dimension(150, 34));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblSubstratKey, gridBagConstraints);

        jLabel9.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanTwo.class,
                "QuerbauwerkePanTwo.jLabel9.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel9, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(250, 142));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(250, 142));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.beschreibung}"),
                tareaBeschreibung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(tareaBeschreibung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jPanel4, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 15, 5);
        panInfoContent.add(jSeparator1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  moWk  DOCUMENT ME!
     */
    public void setMoWk(final MetaObject moWk) {
        this.moWk = moWk;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jLabel10MouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_jLabel10MouseClicked
        if ((evt.getClickCount() == 1) && readOnly && (moWk != null)) {
            ComponentRegistry.getRegistry()
                    .getDescriptionPane()
                    .gotoMetaObjectNode(new MetaObjectNode(moWk.getBean()), false);
        }
    }                                                                        //GEN-LAST:event_jLabel10MouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbDetailtypItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbDetailtypItemStateChanged
        if (!readOnly) {
            final Object selectedItem = cbDetailtyp.getSelectedItem();

            if (selectedItem instanceof CidsBean) {
                final Object reg = ((CidsBean)selectedItem).getProperty("regulierbar");

                if (reg instanceof Boolean) {
                    cbRegulierbarkeit.setEnabled(((Boolean)reg).booleanValue());
                } else {
                    cbRegulierbarkeit.setEnabled(false);
                }
            } else {
                cbRegulierbarkeit.setEnabled(false);
            }
        }
    } //GEN-LAST:event_cbDetailtypItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  kategorie  DOCUMENT ME!
     */
    public void setKategorie(final Kategorie kategorie) {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if (kategorie != null) {
                        switch (kategorie) {
                            case Fliessgewaesser: {
                                jLabel12.setText("Fließgewässer");
                                jLabel12.setVisible(true);
                                lblWaKoerperKey1.setVisible(true);
                                break;
                            }
                            case Standgewaesser: {
                                jLabel12.setText("Standgewässer");
                                jLabel12.setVisible(true);
                                lblWaKoerperKey1.setVisible(true);
                                break;
                            }
                        }
                    } else {
                        jLabel12.setVisible(false);
                        lblWaKoerperKey1.setVisible(false);
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  waKoerper  DOCUMENT ME!
     */
    public void setWaKoerper(final String waKoerper) {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    lblWaKoerperKey.setVisible(waKoerper != null);
                    jLabel10.setVisible(waKoerper != null);
                    jLabel10.setText(waKoerper);
                }
            });
    }

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
            try {
                cidsBean.addPropertyChangeListener(new PropertyChangeListener() {

                        @Override
                        public void propertyChange(final PropertyChangeEvent pce) {
                            if (pce.getPropertyName().equals("bauwerk")) {
                                final String o = (String)cidsBean.getProperty("bauwerk.value");
                                txtAnderesBauwerk.setEnabled((o != null) && o.equals("8"));
                            }
                        }
                    });
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error while autosetting properties", ex);
                }
            }
            bindingGroup.bind();
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }
}
