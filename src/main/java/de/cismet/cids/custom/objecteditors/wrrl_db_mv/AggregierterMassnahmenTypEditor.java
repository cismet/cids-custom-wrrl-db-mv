/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import org.openide.util.NbBundle;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;

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
public class AggregierterMassnahmenTypEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            AggregierterMassnahmenTypEditor.class);
    public static final String[] WB_PROPERTIES = { "wk_fg", "wk_sg", "wk_kg", "wk_gw" }; // NOI18N

    //~ Instance fields --------------------------------------------------------

    private boolean isStandalone = true;
    private boolean readOnly = false;
    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblAnzahl;
    private javax.swing.JLabel lblBezDerMa;
    private javax.swing.JLabel lblEw;
    private javax.swing.JLabel lblFlaeche;
    private javax.swing.JLabel lblKosten;
    private javax.swing.JLabel lblLaenge;
    private javax.swing.JLabel lblPicklist;
    private javax.swing.JLabel lblValPick;
    private javax.swing.JTextField txtAnzahl;
    private javax.swing.JTextField txtEw;
    private javax.swing.JTextField txtFlaeche;
    private javax.swing.JTextField txtKosten;
    private javax.swing.JTextField txtLaenge;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WirkungPan wirkungPan1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenUmsetzungEditor object.
     */
    public AggregierterMassnahmenTypEditor() {
        this(true, false);
    }

    /**
     * Creates new form MassnahmenUmsetzungEditor.
     *
     * @param  isStandalone  DOCUMENT ME!
     * @param  readOnly      DOCUMENT ME!
     */
    public AggregierterMassnahmenTypEditor(final boolean isStandalone, final boolean readOnly) {
        this.isStandalone = isStandalone;
        this.readOnly = readOnly;

        if (!isStandalone) {
            initComponents();
            deActivateGUIElements(false);
        } else {
            final JLabel hintLabel = new JLabel();
            hintLabel.setText(NbBundle.getMessage(
                    AggregierterMassnahmenTypEditor.class,
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
        final AggregierterMassnahmenTypEditor s = new AggregierterMassnahmenTypEditor();
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(s, BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
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

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblBezDerMa = new javax.swing.JLabel();
        lblPicklist = new javax.swing.JLabel();
        lblLaenge = new javax.swing.JLabel();
        lblAnzahl = new javax.swing.JLabel();
        lblValPick = new javax.swing.JLabel();
        lblFlaeche = new javax.swing.JLabel();
        txtLaenge = new javax.swing.JTextField();
        txtAnzahl = new javax.swing.JTextField();
        txtFlaeche = new javax.swing.JTextField();
        lblKosten = new javax.swing.JLabel();
        txtKosten = new javax.swing.JTextField();
        lblEw = new javax.swing.JLabel();
        txtEw = new javax.swing.JTextField();
        wirkungPan1 = new WirkungPan(readOnly);

        setMinimumSize(new java.awt.Dimension(770, 675));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(770, 675));
        setLayout(new java.awt.GridBagLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(450, 160));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(450, 160));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(220, 60));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(220, 60));

        jTextArea1.setColumns(25);
        jTextArea1.setRows(3);
        jTextArea1.setTabSize(6);
        jTextArea1.setDisabledTextColor(new java.awt.Color(26, 26, 26));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bezeichnung}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        lblBezDerMa.setText(org.openide.util.NbBundle.getMessage(
                AggregierterMassnahmenTypEditor.class,
                "AggregierterMassnahmenTypEditor.lblBezDerMa.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanel2.add(lblBezDerMa, gridBagConstraints);

        lblPicklist.setText(org.openide.util.NbBundle.getMessage(
                AggregierterMassnahmenTypEditor.class,
                "AggregierterMassnahmenTypEditor.lblPicklist.text")); // NOI18N
        lblPicklist.setMinimumSize(new java.awt.Dimension(150, 20));
        lblPicklist.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblPicklist, gridBagConstraints);

        lblLaenge.setText(org.openide.util.NbBundle.getMessage(
                AggregierterMassnahmenTypEditor.class,
                "AggregierterMassnahmenTypEditor.lblLaenge.text")); // NOI18N
        lblLaenge.setMinimumSize(new java.awt.Dimension(150, 20));
        lblLaenge.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblLaenge, gridBagConstraints);

        lblAnzahl.setText(org.openide.util.NbBundle.getMessage(
                AggregierterMassnahmenTypEditor.class,
                "AggregierterMassnahmenTypEditor.lblAnzahl.text")); // NOI18N
        lblAnzahl.setMinimumSize(new java.awt.Dimension(150, 20));
        lblAnzahl.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        jPanel2.add(lblAnzahl, gridBagConstraints);

        lblValPick.setMinimumSize(new java.awt.Dimension(150, 20));
        lblValPick.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massnahme.value}"),
                lblValPick,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(lblValPick, gridBagConstraints);

        lblFlaeche.setText(org.openide.util.NbBundle.getMessage(
                AggregierterMassnahmenTypEditor.class,
                "AggregierterMassnahmenTypEditor.lblFlaeche.text")); // NOI18N
        lblFlaeche.setMinimumSize(new java.awt.Dimension(150, 20));
        lblFlaeche.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        jPanel2.add(lblFlaeche, gridBagConstraints);

        txtLaenge.setDisabledTextColor(new java.awt.Color(26, 26, 26));
        txtLaenge.setMinimumSize(new java.awt.Dimension(150, 20));
        txtLaenge.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.laenge}"),
                txtLaenge,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(txtLaenge, gridBagConstraints);

        txtAnzahl.setDisabledTextColor(new java.awt.Color(26, 26, 26));
        txtAnzahl.setMinimumSize(new java.awt.Dimension(150, 20));
        txtAnzahl.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.anzahl}"),
                txtAnzahl,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(txtAnzahl, gridBagConstraints);

        txtFlaeche.setDisabledTextColor(new java.awt.Color(26, 26, 26));
        txtFlaeche.setMinimumSize(new java.awt.Dimension(150, 20));
        txtFlaeche.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.flaeche}"),
                txtFlaeche,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(txtFlaeche, gridBagConstraints);

        lblKosten.setText(org.openide.util.NbBundle.getMessage(
                AggregierterMassnahmenTypEditor.class,
                "AggregierterMassnahmenTypEditor.lblKosten.text")); // NOI18N
        lblKosten.setMinimumSize(new java.awt.Dimension(150, 20));
        lblKosten.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblKosten, gridBagConstraints);

        txtKosten.setDisabledTextColor(new java.awt.Color(26, 26, 26));
        txtKosten.setMinimumSize(new java.awt.Dimension(150, 20));
        txtKosten.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kosten}"),
                txtKosten,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(txtKosten, gridBagConstraints);

        lblEw.setText(org.openide.util.NbBundle.getMessage(
                AggregierterMassnahmenTypEditor.class,
                "AggregierterMassnahmenTypEditor.lblEw.text")); // NOI18N
        lblEw.setMinimumSize(new java.awt.Dimension(150, 20));
        lblEw.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        jPanel2.add(lblEw, gridBagConstraints);

        txtEw.setDisabledTextColor(new java.awt.Color(26, 26, 26));
        txtEw.setMinimumSize(new java.awt.Dimension(150, 20));
        txtEw.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.betroffene}"),
                txtEw,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(txtEw, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 5, 20);
        add(jPanel2, gridBagConstraints);

        wirkungPan1.setMinimumSize(new java.awt.Dimension(380, 115));
        wirkungPan1.setPreferredSize(new java.awt.Dimension(380, 115));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 10, 20);
        add(wirkungPan1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

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
        if (isStandalone) {
            return;
        }
        dispose();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);

            bindingGroup.bind();
            deActivateGUIElements(true);
        } else {
            deActivateGUIElements(false);
        }

        wirkungPan1.setCidsBean(cidsBean);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public WirkungPan getWirkungPan() {
        return wirkungPan1;
    }

    @Override
    public void dispose() {
        if (!isStandalone) {
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
     * @param  enable  DOCUMENT ME!
     */
    private void deActivateGUIElements(boolean enable) {
        if (readOnly) {
            enable = false;
        }
        jTextArea1.setEnabled(enable);
        txtAnzahl.setEnabled(enable);
        txtEw.setEnabled(enable);
        txtFlaeche.setEnabled(enable);
        txtKosten.setEnabled(enable);
        txtLaenge.setEnabled(enable);

        txtEw.setEditable(true);
        txtFlaeche.setEditable(true);
        txtKosten.setEditable(true);
        txtLaenge.setEditable(true);

        if (cidsBean != null) {
            final CidsBean bean = (CidsBean)cidsBean.getProperty("massnahme");

            if (bean != null) {
                final boolean laenge = (Boolean)bean.getProperty("laenge");
                final boolean flaeche = (Boolean)bean.getProperty("flaeche");
                final boolean kosten = (Boolean)bean.getProperty("kosten");
                final boolean betroffene = (Boolean)bean.getProperty("einwohner");

                txtLaenge.setEditable(laenge);
                txtFlaeche.setEditable(flaeche);
                txtKosten.setEditable(kosten);
                txtEw.setEditable(betroffene);
            }
        }
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }
}