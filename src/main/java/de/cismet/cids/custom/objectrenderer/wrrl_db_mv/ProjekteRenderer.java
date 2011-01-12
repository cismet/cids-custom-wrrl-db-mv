/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * WkFgEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.*;
import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.DateConverter;
import de.cismet.cids.custom.util.TimestampConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ProjekteRenderer extends JPanel implements CidsBeanRenderer, FooterComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ProjekteRenderer.class);
    private static final DateConverter DATE_CONVERTER = new DateConverter();

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private ArrayList<CidsBean> beansToDelete = new ArrayList<CidsBean>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList jlIndikator;
    private javax.swing.JList jlIndikator1;
    private javax.swing.JList jlUmsetzung;
    private javax.swing.JLabel lblFoerdersumme;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblHeading3;
    private javax.swing.JLabel lblHeading4;
    private javax.swing.JLabel lblKost_forderf;
    private javax.swing.JLabel lblKurzBez;
    private javax.swing.JLabel lblM_beginn;
    private javax.swing.JLabel lblM_ende;
    private javax.swing.JLabel lblProjekt_bez;
    private javax.swing.JLabel lblProjekt_code;
    private javax.swing.JLabel lblProjekt_nr;
    private javax.swing.JLabel lblValFoerdersatz;
    private javax.swing.JLabel lblValFoerdersumme;
    private javax.swing.JLabel lblValKost_forderf;
    private javax.swing.JLabel lblValKost_gesamt;
    private javax.swing.JLabel lblValM_beginn;
    private javax.swing.JLabel lblValM_ende;
    private javax.swing.JLabel lblValProjekt_bez;
    private javax.swing.JLabel lblValProjekt_code;
    private javax.swing.JLabel lblValProjekt_kurz_bez;
    private javax.swing.JLabel lblValProjekt_nr;
    private javax.swing.JLabel lblValTraeger;
    private javax.swing.JLabel lblfoerdersatz;
    private javax.swing.JLabel lblkost_gesamt;
    private javax.swing.JLabel lbltraeger;
    private de.cismet.cids.custom.objectrenderer.wrrl_db_mv.MassnahmenUmsetzungRenderer massnahmenUmsetzungRenderer1;
    private de.cismet.tools.gui.RoundedPanel panDeMeas;
    private de.cismet.tools.gui.RoundedPanel panDeMeas1;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo3;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo4;
    private javax.swing.JPanel panInfoContent2;
    private javax.swing.JPanel panInfoContent3;
    private javax.swing.JPanel panInfoContent4;
    private de.cismet.tools.gui.RoundedPanel panMaInd;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.ProjekteIndikatorenEditor projekteIndikatorenEditor1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public ProjekteRenderer() {
        initComponents();
        massnahmenUmsetzungRenderer1.setList(jlUmsetzung);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
            Object avUser = cidsBean.getProperty("av_user"); // NOI18N
            Object avTime = cidsBean.getProperty("av_time"); // NOI18N
            if (avUser == null) {
                avUser = "(unbekannt)";
            }
            if (avTime instanceof Timestamp) {
                avTime = TimestampConverter.getInstance().convertForward((Timestamp)avTime);
            } else {
                avTime = "(unbekannt)";
            }

            lblFoot.setText("Zuletzt bearbeitet von " + avUser + " am " + avTime);
        } else {
            lblFoot.setText("");
        }
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
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

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblProjekt_nr = new javax.swing.JLabel();
        lbltraeger = new javax.swing.JLabel();
        lblkost_gesamt = new javax.swing.JLabel();
        lblfoerdersatz = new javax.swing.JLabel();
        lblProjekt_code = new javax.swing.JLabel();
        lblM_beginn = new javax.swing.JLabel();
        lblM_ende = new javax.swing.JLabel();
        lblKost_forderf = new javax.swing.JLabel();
        lblFoerdersumme = new javax.swing.JLabel();
        lblProjekt_bez = new javax.swing.JLabel();
        lblKurzBez = new javax.swing.JLabel();
        lblValProjekt_nr = new javax.swing.JLabel();
        lblValProjekt_code = new javax.swing.JLabel();
        lblValTraeger = new javax.swing.JLabel();
        lblValProjekt_bez = new javax.swing.JLabel();
        lblValProjekt_kurz_bez = new javax.swing.JLabel();
        lblValKost_gesamt = new javax.swing.JLabel();
        lblValKost_forderf = new javax.swing.JLabel();
        lblValFoerdersatz = new javax.swing.JLabel();
        lblValFoerdersumme = new javax.swing.JLabel();
        lblValM_beginn = new javax.swing.JLabel();
        lblValM_ende = new javax.swing.JLabel();
        panDeMeas = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlUmsetzung = new javax.swing.JList();
        panDeMeas1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo3 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading3 = new javax.swing.JLabel();
        panInfoContent3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlIndikator = new javax.swing.JList();
        projekteIndikatorenEditor1 = new ProjekteIndikatorenEditor(true);
        panMaInd = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo4 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading4 = new javax.swing.JLabel();
        panInfoContent4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlIndikator1 = new javax.swing.JList();
        massnahmenUmsetzungRenderer1 =
            new de.cismet.cids.custom.objectrenderer.wrrl_db_mv.MassnahmenUmsetzungRenderer();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblProjekt_nr.setText("Projektnummer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        jPanel3.add(lblProjekt_nr, gridBagConstraints);

        lbltraeger.setText("Antragsteller");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        jPanel3.add(lbltraeger, gridBagConstraints);

        lblkost_gesamt.setText("Gesamtkosten");
        lblkost_gesamt.setMinimumSize(new java.awt.Dimension(115, 17));
        lblkost_gesamt.setPreferredSize(new java.awt.Dimension(115, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblkost_gesamt, gridBagConstraints);

        lblfoerdersatz.setText("Fördersatz");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblfoerdersatz, gridBagConstraints);

        lblProjekt_code.setText("Förigef Schlüssel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblProjekt_code, gridBagConstraints);

        lblM_beginn.setText("Projektbeginn");
        lblM_beginn.setMinimumSize(new java.awt.Dimension(90, 17));
        lblM_beginn.setPreferredSize(new java.awt.Dimension(90, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblM_beginn, gridBagConstraints);

        lblM_ende.setText("Projektende");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblM_ende, gridBagConstraints);

        lblKost_forderf.setText("förderfähige Kosten");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblKost_forderf, gridBagConstraints);

        lblFoerdersumme.setText("Fördersumme");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel3.add(lblFoerdersumme, gridBagConstraints);

        lblProjekt_bez.setText("Bez. des Projektes");
        lblProjekt_bez.setToolTipText("Bezeichnung des Projektes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        jPanel3.add(lblProjekt_bez, gridBagConstraints);

        lblKurzBez.setText("Kurzbezeichnung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        jPanel3.add(lblKurzBez, gridBagConstraints);

        lblValProjekt_nr.setMinimumSize(new java.awt.Dimension(215, 20));
        lblValProjekt_nr.setPreferredSize(new java.awt.Dimension(215, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.id}"),
                lblValProjekt_nr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblValProjekt_nr, gridBagConstraints);

        lblValProjekt_code.setMinimumSize(new java.awt.Dimension(150, 20));
        lblValProjekt_code.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.projekt_code}"),
                lblValProjekt_code,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblValProjekt_code, gridBagConstraints);

        lblValTraeger.setMinimumSize(new java.awt.Dimension(150, 20));
        lblValTraeger.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.traeger}"),
                lblValTraeger,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblValTraeger, gridBagConstraints);

        lblValProjekt_bez.setMaximumSize(new java.awt.Dimension(150, 20));
        lblValProjekt_bez.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.projekt_bez}"),
                lblValProjekt_bez,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblValProjekt_bez, gridBagConstraints);

        lblValProjekt_kurz_bez.setMaximumSize(new java.awt.Dimension(150, 20));
        lblValProjekt_kurz_bez.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.projekt_kurz_bez}"),
                lblValProjekt_kurz_bez,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblValProjekt_kurz_bez, gridBagConstraints);

        lblValKost_gesamt.setMinimumSize(new java.awt.Dimension(110, 20));
        lblValKost_gesamt.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kost_gesamt}"),
                lblValKost_gesamt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(lblValKost_gesamt, gridBagConstraints);

        lblValKost_forderf.setMinimumSize(new java.awt.Dimension(110, 20));
        lblValKost_forderf.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kost_forderf}"),
                lblValKost_forderf,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        jPanel3.add(lblValKost_forderf, gridBagConstraints);

        lblValFoerdersatz.setMaximumSize(new java.awt.Dimension(110, 20));
        lblValFoerdersatz.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.foerdersatz}"),
                lblValFoerdersatz,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanel3.add(lblValFoerdersatz, gridBagConstraints);

        lblValFoerdersumme.setMinimumSize(new java.awt.Dimension(110, 20));
        lblValFoerdersumme.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.foerdersumme}"),
                lblValFoerdersumme,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel3.add(lblValFoerdersumme, gridBagConstraints);

        lblValM_beginn.setMaximumSize(new java.awt.Dimension(150, 20));
        lblValM_beginn.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.m_beginn}"),
                lblValM_beginn,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setConverter(DATE_CONVERTER);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblValM_beginn, gridBagConstraints);

        lblValM_ende.setMaximumSize(new java.awt.Dimension(150, 20));
        lblValM_ende.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.m_ende}"),
                lblValM_ende,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setConverter(DATE_CONVERTER);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblValM_ende, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jPanel3, gridBagConstraints);

        panDeMeas.setMinimumSize(new java.awt.Dimension(290, 660));
        panDeMeas.setPreferredSize(new java.awt.Dimension(290, 660));

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText("Umsetzungen");
        panHeadInfo2.add(lblHeading2);

        panDeMeas.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(240, 585));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(240, 585));

        jlUmsetzung.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.umsetzung}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        jlUmsetzung);
        bindingGroup.addBinding(jListBinding);

        jlUmsetzung.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    jlUmsetzungValueChanged(evt);
                }
            });
        jScrollPane1.setViewportView(jlUmsetzung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 25, 10, 5);
        panInfoContent2.add(jScrollPane1, gridBagConstraints);

        panDeMeas.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 12);
        add(panDeMeas, gridBagConstraints);

        panDeMeas1.setMinimumSize(new java.awt.Dimension(350, 225));
        panDeMeas1.setPreferredSize(new java.awt.Dimension(350, 225));

        panHeadInfo3.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo3.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setLayout(new java.awt.FlowLayout());

        lblHeading3.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading3.setText("Projektbezogene Indikatoren");
        panHeadInfo3.add(lblHeading3);

        panDeMeas1.add(panHeadInfo3, java.awt.BorderLayout.NORTH);

        panInfoContent3.setOpaque(false);
        panInfoContent3.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setMinimumSize(new java.awt.Dimension(290, 165));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(290, 165));

        jlIndikator.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.indikator}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                eLProperty,
                jlIndikator);
        bindingGroup.addBinding(jListBinding);

        jlIndikator.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    jlIndikatorValueChanged(evt);
                }
            });
        jScrollPane2.setViewportView(jlIndikator);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 30, 0, 5);
        panInfoContent3.add(jScrollPane2, gridBagConstraints);

        panDeMeas1.add(panInfoContent3, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 12, 5, 0);
        add(panDeMeas1, gridBagConstraints);

        projekteIndikatorenEditor1.setMinimumSize(new java.awt.Dimension(350, 180));
        projekteIndikatorenEditor1.setPreferredSize(new java.awt.Dimension(350, 180));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 0);
        add(projekteIndikatorenEditor1, gridBagConstraints);

        panMaInd.setMinimumSize(new java.awt.Dimension(350, 225));
        panMaInd.setPreferredSize(new java.awt.Dimension(350, 225));

        panHeadInfo4.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo4.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setLayout(new java.awt.FlowLayout());

        lblHeading4.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading4.setText("Umsetzungsbezogene Indikatoren");
        panHeadInfo4.add(lblHeading4);

        panMaInd.add(panHeadInfo4, java.awt.BorderLayout.NORTH);

        panInfoContent4.setOpaque(false);
        panInfoContent4.setLayout(new java.awt.GridBagLayout());

        jScrollPane3.setMinimumSize(new java.awt.Dimension(290, 165));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(290, 165));

        jlIndikator1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlIndikator1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    jlIndikator1ValueChanged(evt);
                }
            });
        jScrollPane3.setViewportView(jlIndikator1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 30, 0, 5);
        panInfoContent4.add(jScrollPane3, gridBagConstraints);

        panMaInd.add(panInfoContent4, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
        add(panMaInd, gridBagConstraints);

        massnahmenUmsetzungRenderer1.setMinimumSize(new java.awt.Dimension(470, 660));
        massnahmenUmsetzungRenderer1.setPreferredSize(new java.awt.Dimension(470, 660));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        add(massnahmenUmsetzungRenderer1, gridBagConstraints);

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
    private void jlUmsetzungValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_jlUmsetzungValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object selObj = jlUmsetzung.getSelectedValue();
            if (selObj instanceof CidsBean) {
                final Object o = ((CidsBean)selObj).getProperty("massnahme");              // NOI18N
                if ((o instanceof CidsBean) || (o == null)) {
                    massnahmenUmsetzungRenderer1.setCidsBean((CidsBean)selObj);
                    final List<CidsBean> indikatorList = CidsBeanSupport.getBeanCollectionFromProperty((CidsBean)
                            selObj,
                            "indikator");                                                  // NOI18N

                    // checks if the currently shown indicator is one of the list jlIndikator1, which will be cleaned
                    final CidsBean currentIndicatior = projekteIndikatorenEditor1.getCidsBean();
                    if ((currentIndicatior != null) && (jlIndikator1.getSelectedValue() == currentIndicatior)) {
                        projekteIndikatorenEditor1.setCidsBean(null);
                    }

                    jlIndikator1.removeAll();
                    if (indikatorList != null) {
                        jlIndikator1.setListData(indikatorList.toArray());
                    }
                }
            }
        }
    } //GEN-LAST:event_jlUmsetzungValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jlIndikatorValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_jlIndikatorValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object selObj = jlIndikator.getSelectedValue();
            if (selObj instanceof CidsBean) {
                jlIndikator1.clearSelection();
                jlIndikator.setSelectedValue(selObj, false);
                projekteIndikatorenEditor1.setCidsBean((CidsBean)selObj);
            } else {
                projekteIndikatorenEditor1.setCidsBean(null);
            }
        }
    }                                                                                      //GEN-LAST:event_jlIndikatorValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jlIndikator1ValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_jlIndikator1ValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object selObj = jlIndikator1.getSelectedValue();
            if (selObj instanceof CidsBean) {
                jlIndikator.clearSelection();
                jlIndikator1.setSelectedValue(selObj, false);
                projekteIndikatorenEditor1.setCidsBean((CidsBean)selObj);
            } else {
                projekteIndikatorenEditor1.setCidsBean(null);
            }
        }
    }                                                                                       //GEN-LAST:event_jlIndikator1ValueChanged

    @Override
    public void dispose() {
        massnahmenUmsetzungRenderer1.dispose();
        projekteIndikatorenEditor1.dispose();
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }
}
