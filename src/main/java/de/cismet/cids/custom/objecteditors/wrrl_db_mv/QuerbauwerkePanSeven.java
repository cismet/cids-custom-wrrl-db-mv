/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaObject;

import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkePanSeven extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuerbauwerkePanOne.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private MetaObject moWk;
    private ConnectionContext cc = ConnectionContext.create(AbstractConnectionContext.Category.EDITOR, "Querbauwerke");

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbHb;
    private javax.swing.JCheckBox cbHze;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbRang;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblBFna;
    private javax.swing.JLabel lblBGroppe;
    private javax.swing.JLabel lblBIndex;
    private javax.swing.JLabel lblBIndex1;
    private javax.swing.JLabel lblBLachs;
    private javax.swing.JLabel lblBLritze;
    private javax.swing.JLabel lblBMai;
    private javax.swing.JLabel lblBMf;
    private javax.swing.JLabel lblBMna;
    private javax.swing.JLabel lblBNa;
    private javax.swing.JLabel lblBQuappe;
    private javax.swing.JLabel lblBRapfen;
    private javax.swing.JLabel lblBRh;
    private javax.swing.JLabel lblBSchnaep;
    private javax.swing.JLabel lblBStint;
    private javax.swing.JLabel lblBStintB;
    private javax.swing.JLabel lblBStoer;
    private javax.swing.JLabel lblBUnio;
    private javax.swing.JLabel lblBWels;
    private javax.swing.JLabel lblBZaehrte;
    private javax.swing.JLabel lblBZope;
    private javax.swing.JLabel lblBaal;
    private javax.swing.JLabel lblBaal1;
    private javax.swing.JLabel lblBaal2;
    private javax.swing.JLabel lblBbf;
    private javax.swing.JLabel lblBbf1;
    private javax.swing.JLabel lblBereich;
    private javax.swing.JLabel lblBereichU;
    private javax.swing.JLabel lblEzgFl;
    private javax.swing.JLabel lblFgskGutO;
    private javax.swing.JLabel lblFipsGutO;
    private javax.swing.JLabel lblHb;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblHeading3;
    private javax.swing.JLabel lblQbw;
    private javax.swing.JLabel lblQbwIdO;
    private javax.swing.JLabel lblQbwIdU;
    private javax.swing.JLabel lblUp;
    private javax.swing.JLabel lblUpAggId;
    private javax.swing.JLabel lblVor;
    private javax.swing.JLabel lblWkNwbO;
    private javax.swing.JLabel lblZuord;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo3;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo3;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent2;
    private javax.swing.JPanel panInfoContent3;
    private javax.swing.JTextField txtAnzahlUnterhalb;
    private javax.swing.JTextField txtBachforelle;
    private javax.swing.JTextField txtBachmuschel;
    private javax.swing.JTextField txtBachneunauge;
    private javax.swing.JTextField txtBinnenStint;
    private javax.swing.JTextField txtEzgFl;
    private javax.swing.JTextField txtFgskGutO;
    private javax.swing.JTextField txtFipsGutO;
    private javax.swing.JTextField txtFlussaal;
    private javax.swing.JTextField txtFlussneunauge;
    private javax.swing.JTextField txtGroppe;
    private javax.swing.JTextField txtHasel;
    private javax.swing.JTextField txtHinweisEinstufung;
    private javax.swing.JTextField txtIdFaa;
    private javax.swing.JTextField txtLachs;
    private javax.swing.JTextField txtMaifisch;
    private javax.swing.JTextField txtMeerforelle;
    private javax.swing.JTextField txtMeerneunauge;
    private javax.swing.JTextField txtOstseeschnaepel;
    private javax.swing.JTextField txtQbwIdO;
    private javax.swing.JTextField txtQbwIdU;
    private javax.swing.JTextField txtQuappe;
    private javax.swing.JTextField txtRapfen;
    private javax.swing.JTextField txtRithraleArten;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtStoer;
    private javax.swing.JTextField txtSumme;
    private javax.swing.JTextField txtUnzerschnitten;
    private javax.swing.JTextField txtUnzerschnittenU;
    private javax.swing.JTextField txtUpAggId;
    private javax.swing.JTextField txtWanderstint;
    private javax.swing.JTextField txtWels;
    private javax.swing.JTextField txtWkNwbO;
    private javax.swing.JTextField txtZaehrte;
    private javax.swing.JTextField txtZope;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form QuerbauwerkePanOne.
     */
    public QuerbauwerkePanSeven() {
        this(false);
    }

    /**
     * Creates a new QuerbauwerkePanSeven object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public QuerbauwerkePanSeven(final boolean readOnly) {
        initComponents();
        txtRithraleArten.setVisible(false);
        lblBRh.setVisible(false);
        txtIdFaa.setVisible(false);
        lblZuord.setVisible(false);
        setReadOnly();
        lblUp.setVisible(false);
        txtHinweisEinstufung.setVisible(false);

        boolean fieldsEditable = false;

        try {
            fieldsEditable = SessionManager.getProxy()
                        .hasConfigAttr(SessionManager.getSession().getUser(), "qbw.fisch.editable", cc);
        } catch (Exception e) {
            LOG.error("Cannot check configuration attribute qbw.fisch.editable", e);
        }

        if (!readOnly && fieldsEditable) {
            RendererTools.makeWritable(txtAnzahlUnterhalb);
            RendererTools.makeWritable(cbHb);
        }

        try {
            final boolean showInternal = SessionManager.getProxy()
                        .hasConfigAttr(SessionManager.getSession().getUser(), "qbw.show.internal", cc);
            panInfo3.setVisible(showInternal);
        } catch (Exception e) {
            LOG.error("Cannot check configuration attribute qbw.show.internal", e);
            panInfo3.setVisible(false);
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
        lblHb = new javax.swing.JLabel();
        cbHb = new DefaultBindableReferenceCombo(true);
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtHinweisEinstufung = new javax.swing.JTextField();
        lblBRh = new javax.swing.JLabel();
        lblBUnio = new javax.swing.JLabel();
        lblZuord = new javax.swing.JLabel();
        lblUp = new javax.swing.JLabel();
        lblBereich = new javax.swing.JLabel();
        lblQbw = new javax.swing.JLabel();
        txtUnzerschnitten = new javax.swing.JTextField();
        txtBachmuschel = new javax.swing.JTextField();
        txtAnzahlUnterhalb = new javax.swing.JTextField();
        txtIdFaa = new javax.swing.JTextField();
        lblVor = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtStatus = new javax.swing.JTextField();
        lblBIndex = new javax.swing.JLabel();
        txtSumme = new javax.swing.JTextField();
        txtRithraleArten = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        cbHze = new javax.swing.JCheckBox();
        lblBIndex1 = new javax.swing.JLabel();
        cbRang = new DefaultBindableReferenceCombo(true);
        lblBereichU = new javax.swing.JLabel();
        txtUnzerschnittenU = new javax.swing.JTextField();
        panInfo3 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo3 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading3 = new javax.swing.JLabel();
        panInfoContent3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblFgskGutO = new javax.swing.JLabel();
        lblFipsGutO = new javax.swing.JLabel();
        txtFipsGutO = new javax.swing.JTextField();
        lblWkNwbO = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        txtWkNwbO = new javax.swing.JTextField();
        lblEzgFl = new javax.swing.JLabel();
        txtEzgFl = new javax.swing.JTextField();
        txtFgskGutO = new javax.swing.JTextField();
        lblBbf1 = new javax.swing.JLabel();
        lblUpAggId = new javax.swing.JLabel();
        lblQbwIdU = new javax.swing.JLabel();
        lblQbwIdO = new javax.swing.JLabel();
        txtUpAggId = new javax.swing.JTextField();
        txtQbwIdU = new javax.swing.JTextField();
        txtQbwIdO = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        panInfo2 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txtMeerneunauge = new javax.swing.JTextField();
        lblBFna = new javax.swing.JLabel();
        lblBStoer = new javax.swing.JLabel();
        lblBMf = new javax.swing.JLabel();
        lblBMna = new javax.swing.JLabel();
        lblBSchnaep = new javax.swing.JLabel();
        lblBMai = new javax.swing.JLabel();
        txtOstseeschnaepel = new javax.swing.JTextField();
        txtStoer = new javax.swing.JTextField();
        txtMaifisch = new javax.swing.JTextField();
        txtMeerforelle = new javax.swing.JTextField();
        lblBLachs = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtLachs = new javax.swing.JTextField();
        lblBaal = new javax.swing.JLabel();
        txtFlussaal = new javax.swing.JTextField();
        txtFlussneunauge = new javax.swing.JTextField();
        lblBStint = new javax.swing.JLabel();
        lblBbf = new javax.swing.JLabel();
        lblBNa = new javax.swing.JLabel();
        lblBLritze = new javax.swing.JLabel();
        lblBGroppe = new javax.swing.JLabel();
        lblBQuappe = new javax.swing.JLabel();
        lblBRapfen = new javax.swing.JLabel();
        lblBStintB = new javax.swing.JLabel();
        txtWanderstint = new javax.swing.JTextField();
        txtBachforelle = new javax.swing.JTextField();
        txtBachneunauge = new javax.swing.JTextField();
        txtHasel = new javax.swing.JTextField();
        txtGroppe = new javax.swing.JTextField();
        txtQuappe = new javax.swing.JTextField();
        txtRapfen = new javax.swing.JTextField();
        txtBinnenStint = new javax.swing.JTextField();
        lblBWels = new javax.swing.JLabel();
        txtWels = new javax.swing.JTextField();
        lblBZaehrte = new javax.swing.JLabel();
        txtZaehrte = new javax.swing.JTextField();
        lblBZope = new javax.swing.JLabel();
        txtZope = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        lblBaal1 = new javax.swing.JLabel();
        lblBaal2 = new javax.swing.JLabel();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        panInfo.setPreferredSize(new java.awt.Dimension(978, 59));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
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

        lblHb.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanTwo.lblHb.text")); // NOI18N
        lblHb.setMaximumSize(new java.awt.Dimension(150, 17));
        lblHb.setMinimumSize(new java.awt.Dimension(150, 17));
        lblHb.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblHb, gridBagConstraints);

        cbHb.setMinimumSize(new java.awt.Dimension(250, 25));
        cbHb.setPreferredSize(new java.awt.Dimension(250, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hb}"),
                cbHb,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(cbHb, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        add(panInfo, gridBagConstraints);

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo1.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        txtHinweisEinstufung.setMinimumSize(new java.awt.Dimension(250, 25));
        txtHinweisEinstufung.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.up_bem}"),
                txtHinweisEinstufung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtHinweisEinstufung, gridBagConstraints);

        lblBRh.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanTwo.lblBRh.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblBRh, gridBagConstraints);

        lblBUnio.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanTwo.lblBUnio.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblBUnio, gridBagConstraints);

        lblZuord.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblZuord.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblZuord, gridBagConstraints);

        lblUp.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblUp.text")); // NOI18N
        lblUp.setMaximumSize(new java.awt.Dimension(262, 17));
        lblUp.setMinimumSize(new java.awt.Dimension(262, 17));
        lblUp.setPreferredSize(new java.awt.Dimension(262, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblUp, gridBagConstraints);

        lblBereich.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBereich.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblBereich, gridBagConstraints);

        lblQbw.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblQbw.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblQbw, gridBagConstraints);

        txtUnzerschnitten.setMinimumSize(new java.awt.Dimension(250, 25));
        txtUnzerschnitten.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.unz_oberh}"),
                txtUnzerschnitten,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtUnzerschnitten, gridBagConstraints);

        txtBachmuschel.setMinimumSize(new java.awt.Dimension(250, 25));
        txtBachmuschel.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_unio}"),
                txtBachmuschel,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtBachmuschel, gridBagConstraints);

        txtAnzahlUnterhalb.setMinimumSize(new java.awt.Dimension(250, 25));
        txtAnzahlUnterhalb.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.qbw_anz3a}"),
                txtAnzahlUnterhalb,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtAnzahlUnterhalb, gridBagConstraints);

        txtIdFaa.setMinimumSize(new java.awt.Dimension(250, 25));
        txtIdFaa.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zuord_faa}"),
                txtIdFaa,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtIdFaa, gridBagConstraints);

        lblVor.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblVor.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel3.add(lblVor, gridBagConstraints);

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

        txtStatus.setMinimumSize(new java.awt.Dimension(250, 25));
        txtStatus.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.vorrang.name}"),
                txtStatus,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel3.add(txtStatus, gridBagConstraints);

        lblBIndex.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanTwo.lblBIndex.text")); // NOI18N
        lblBIndex.setMinimumSize(new java.awt.Dimension(150, 17));
        lblBIndex.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblBIndex, gridBagConstraints);

        txtSumme.setMinimumSize(new java.awt.Dimension(250, 25));
        txtSumme.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_index1}"),
                txtSumme,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtSumme, gridBagConstraints);

        txtRithraleArten.setMinimumSize(new java.awt.Dimension(250, 25));
        txtRithraleArten.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_rhithral}"),
                txtRithraleArten,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtRithraleArten, gridBagConstraints);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 15, 5);
        jPanel3.add(jSeparator2, gridBagConstraints);

        cbHze.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.cbHze.text",
                new Object[] {})); // NOI18N
        cbHze.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hze_plus}"),
                cbHze,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbHze, gridBagConstraints);

        lblBIndex1.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBIndex1.text")); // NOI18N
        lblBIndex1.setMinimumSize(new java.awt.Dimension(150, 17));
        lblBIndex1.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel3.add(lblBIndex1, gridBagConstraints);

        cbRang.setMinimumSize(new java.awt.Dimension(250, 25));
        cbRang.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.rang}"),
                cbRang,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel3.add(cbRang, gridBagConstraints);

        lblBereichU.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBereichU.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblBereichU, gridBagConstraints);

        txtUnzerschnittenU.setMinimumSize(new java.awt.Dimension(250, 25));
        txtUnzerschnittenU.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.unz_unterh}"),
                txtUnzerschnittenU,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtUnzerschnittenU, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(jPanel3, gridBagConstraints);

        panInfo1.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(panInfo1, gridBagConstraints);

        panHeadInfo3.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo3.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setLayout(new java.awt.FlowLayout());

        lblHeading3.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading3.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblHeading3.text")); // NOI18N
        panHeadInfo3.add(lblHeading3);

        panInfo3.add(panHeadInfo3, java.awt.BorderLayout.NORTH);

        panInfoContent3.setOpaque(false);
        panInfoContent3.setLayout(new java.awt.GridBagLayout());

        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.GridBagLayout());

        lblFgskGutO.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblFgskGutO.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(lblFgskGutO, gridBagConstraints);

        lblFipsGutO.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblFipsGutO.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(lblFipsGutO, gridBagConstraints);

        txtFipsGutO.setMinimumSize(new java.awt.Dimension(250, 25));
        txtFipsGutO.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fips_gut_o}"),
                txtFipsGutO,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(txtFipsGutO, gridBagConstraints);

        lblWkNwbO.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblWkNwbO.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(lblWkNwbO, gridBagConstraints);

        jPanel10.setOpaque(false);

        final javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                0,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel9.add(jPanel10, gridBagConstraints);

        txtWkNwbO.setMinimumSize(new java.awt.Dimension(250, 25));
        txtWkNwbO.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_nwb_o}"),
                txtWkNwbO,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(txtWkNwbO, gridBagConstraints);

        lblEzgFl.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblEzgFl.text")); // NOI18N
        lblEzgFl.setMinimumSize(new java.awt.Dimension(150, 17));
        lblEzgFl.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel9.add(lblEzgFl, gridBagConstraints);

        txtEzgFl.setMinimumSize(new java.awt.Dimension(250, 25));
        txtEzgFl.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ezg_fl_qkm}"),
                txtEzgFl,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel9.add(txtEzgFl, gridBagConstraints);

        txtFgskGutO.setMinimumSize(new java.awt.Dimension(250, 25));
        txtFgskGutO.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fgsk_gut_o}"),
                txtFgskGutO,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(txtFgskGutO, gridBagConstraints);

        lblBbf1.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBbf1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel9.add(lblBbf1, gridBagConstraints);

        lblUpAggId.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblUpAggId.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(lblUpAggId, gridBagConstraints);

        lblQbwIdU.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblQbwIdU.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(lblQbwIdU, gridBagConstraints);

        lblQbwIdO.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblQbwIdO.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(lblQbwIdO, gridBagConstraints);

        txtUpAggId.setMinimumSize(new java.awt.Dimension(250, 25));
        txtUpAggId.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.up_agg_id}"),
                txtUpAggId,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(txtUpAggId, gridBagConstraints);

        txtQbwIdU.setMinimumSize(new java.awt.Dimension(250, 25));
        txtQbwIdU.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.qbw_id_u}"),
                txtQbwIdU,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(txtQbwIdU, gridBagConstraints);

        txtQbwIdO.setMinimumSize(new java.awt.Dimension(250, 25));
        txtQbwIdO.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.qbw_id_o}"),
                txtQbwIdO,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(txtQbwIdO, gridBagConstraints);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 15, 5);
        jPanel9.add(jSeparator3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent3.add(jPanel9, gridBagConstraints);

        panInfo3.add(panInfoContent3, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(panInfo3, gridBagConstraints);

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblHeading2.text")); // NOI18N
        panHeadInfo2.add(lblHeading2);

        panInfo2.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridBagLayout());

        txtMeerneunauge.setMinimumSize(new java.awt.Dimension(250, 25));
        txtMeerneunauge.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_mna}"),
                txtMeerneunauge,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtMeerneunauge, gridBagConstraints);

        lblBFna.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBFna.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBFna, gridBagConstraints);

        lblBStoer.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBStoer.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBStoer, gridBagConstraints);

        lblBMf.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBMf.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBMf, gridBagConstraints);

        lblBMna.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBMna.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBMna, gridBagConstraints);

        lblBSchnaep.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBSchnaep.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBSchnaep, gridBagConstraints);

        lblBMai.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBMai.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBMai, gridBagConstraints);

        txtOstseeschnaepel.setMinimumSize(new java.awt.Dimension(250, 25));
        txtOstseeschnaepel.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_schnaep}"),
                txtOstseeschnaepel,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtOstseeschnaepel, gridBagConstraints);

        txtStoer.setMinimumSize(new java.awt.Dimension(250, 25));
        txtStoer.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_stoer}"),
                txtStoer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtStoer, gridBagConstraints);

        txtMaifisch.setMinimumSize(new java.awt.Dimension(250, 25));
        txtMaifisch.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_mai}"),
                txtMaifisch,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtMaifisch, gridBagConstraints);

        txtMeerforelle.setMinimumSize(new java.awt.Dimension(250, 25));
        txtMeerforelle.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_mf}"),
                txtMeerforelle,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtMeerforelle, gridBagConstraints);

        lblBLachs.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBLachs.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBLachs, gridBagConstraints);

        jPanel8.setOpaque(false);

        final javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel7.add(jPanel8, gridBagConstraints);

        txtLachs.setMinimumSize(new java.awt.Dimension(250, 25));
        txtLachs.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_lachs}"),
                txtLachs,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtLachs, gridBagConstraints);

        lblBaal.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBaal.text")); // NOI18N
        lblBaal.setMinimumSize(new java.awt.Dimension(150, 17));
        lblBaal.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel7.add(lblBaal, gridBagConstraints);

        txtFlussaal.setMinimumSize(new java.awt.Dimension(250, 25));
        txtFlussaal.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_aal}"),
                txtFlussaal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel7.add(txtFlussaal, gridBagConstraints);

        txtFlussneunauge.setMinimumSize(new java.awt.Dimension(250, 25));
        txtFlussneunauge.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_fna}"),
                txtFlussneunauge,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtFlussneunauge, gridBagConstraints);

        lblBStint.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBStint.text")); // NOI18N
        lblBStint.setMinimumSize(new java.awt.Dimension(150, 17));
        lblBStint.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBStint, gridBagConstraints);

        lblBbf.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBbf.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel7.add(lblBbf, gridBagConstraints);

        lblBNa.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBNa.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBNa, gridBagConstraints);

        lblBLritze.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBLritze.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBLritze, gridBagConstraints);

        lblBGroppe.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBGroppe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBGroppe, gridBagConstraints);

        lblBQuappe.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBQuappe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBQuappe, gridBagConstraints);

        lblBRapfen.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBRapfen.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBRapfen, gridBagConstraints);

        lblBStintB.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBStintB.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBStintB, gridBagConstraints);

        txtWanderstint.setMinimumSize(new java.awt.Dimension(250, 25));
        txtWanderstint.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_stint_w}"),
                txtWanderstint,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtWanderstint, gridBagConstraints);

        txtBachforelle.setMinimumSize(new java.awt.Dimension(250, 25));
        txtBachforelle.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_bf}"),
                txtBachforelle,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel7.add(txtBachforelle, gridBagConstraints);

        txtBachneunauge.setMinimumSize(new java.awt.Dimension(250, 25));
        txtBachneunauge.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_bna}"),
                txtBachneunauge,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtBachneunauge, gridBagConstraints);

        txtHasel.setMinimumSize(new java.awt.Dimension(250, 25));
        txtHasel.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_hasel}"),
                txtHasel,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtHasel, gridBagConstraints);

        txtGroppe.setMinimumSize(new java.awt.Dimension(250, 25));
        txtGroppe.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_groppe}"),
                txtGroppe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtGroppe, gridBagConstraints);

        txtQuappe.setMinimumSize(new java.awt.Dimension(250, 25));
        txtQuappe.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_quappe}"),
                txtQuappe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtQuappe, gridBagConstraints);

        txtRapfen.setMinimumSize(new java.awt.Dimension(250, 25));
        txtRapfen.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_rapfen}"),
                txtRapfen,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtRapfen, gridBagConstraints);

        txtBinnenStint.setMinimumSize(new java.awt.Dimension(250, 25));
        txtBinnenStint.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_stint_b}"),
                txtBinnenStint,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtBinnenStint, gridBagConstraints);

        lblBWels.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBWels.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBWels, gridBagConstraints);

        txtWels.setMinimumSize(new java.awt.Dimension(250, 25));
        txtWels.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_wels}"),
                txtWels,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtWels, gridBagConstraints);

        lblBZaehrte.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBZaehrte.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBZaehrte, gridBagConstraints);

        txtZaehrte.setMinimumSize(new java.awt.Dimension(250, 25));
        txtZaehrte.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_zaehrte}"),
                txtZaehrte,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtZaehrte, gridBagConstraints);

        lblBZope.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBZope.text")); // NOI18N
        lblBZope.setPreferredSize(new java.awt.Dimension(262, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblBZope, gridBagConstraints);

        txtZope.setMinimumSize(new java.awt.Dimension(250, 25));
        txtZope.setPreferredSize(new java.awt.Dimension(250, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b_zope}"),
                txtZope,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtZope, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 15, 5);
        jPanel7.add(jSeparator1, gridBagConstraints);

        lblBaal1.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBaal1.text")); // NOI18N
        lblBaal1.setMinimumSize(new java.awt.Dimension(200, 17));
        lblBaal1.setPreferredSize(new java.awt.Dimension(200, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel7.add(lblBaal1, gridBagConstraints);

        lblBaal2.setText(org.openide.util.NbBundle.getMessage(
                QuerbauwerkePanSeven.class,
                "QuerbauwerkePanSeven.lblBaal2.text")); // NOI18N
        lblBaal2.setMinimumSize(new java.awt.Dimension(200, 17));
        lblBaal2.setPreferredSize(new java.awt.Dimension(200, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        jPanel7.add(lblBaal2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent2.add(jPanel7, gridBagConstraints);

        panInfo2.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(panInfo2, gridBagConstraints);

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
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    /**
     * DOCUMENT ME!
     */
    private void setReadOnly() {
        RendererTools.makeReadOnly(txtZope);
        RendererTools.makeReadOnly(txtBachmuschel);
        RendererTools.makeReadOnly(txtStatus);
        RendererTools.makeReadOnly(txtSumme);
        RendererTools.makeReadOnly(txtRithraleArten);
        RendererTools.makeReadOnly(txtAnzahlUnterhalb);
        RendererTools.makeReadOnly(cbHb);
        RendererTools.makeReadOnly(txtHinweisEinstufung);
        RendererTools.makeReadOnly(txtStoer);
        RendererTools.makeReadOnly(txtMaifisch);
        RendererTools.makeReadOnly(txtLachs);
        RendererTools.makeReadOnly(txtFlussaal);
        RendererTools.makeReadOnly(txtFlussneunauge);
        RendererTools.makeReadOnly(txtWanderstint);
        RendererTools.makeReadOnly(txtBachforelle);
        RendererTools.makeReadOnly(txtIdFaa);
        RendererTools.makeReadOnly(txtMeerforelle);
        RendererTools.makeReadOnly(txtBachneunauge);
        RendererTools.makeReadOnly(txtMeerneunauge);
        RendererTools.makeReadOnly(txtHasel);
        RendererTools.makeReadOnly(txtGroppe);
        RendererTools.makeReadOnly(txtQuappe);
        RendererTools.makeReadOnly(txtUnzerschnitten);
        RendererTools.makeReadOnly(txtUnzerschnittenU);
        RendererTools.makeReadOnly(txtOstseeschnaepel);
        RendererTools.makeReadOnly(txtRapfen);
        RendererTools.makeReadOnly(txtBinnenStint);
        RendererTools.makeReadOnly(txtWels);
        RendererTools.makeReadOnly(txtZaehrte);
        RendererTools.makeReadOnly(cbHze);
        RendererTools.makeReadOnly(cbRang);
        RendererTools.makeReadOnly(txtEzgFl);
        RendererTools.makeReadOnly(txtFipsGutO);
        RendererTools.makeReadOnly(txtFgskGutO);
        RendererTools.makeReadOnly(txtWkNwbO);
        RendererTools.makeReadOnly(txtUpAggId);
        RendererTools.makeReadOnly(txtQbwIdO);
        RendererTools.makeReadOnly(txtQbwIdU);
    }
}
