/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * WkFgPanOne.java
 *
 * Created on 04.08.2010, 13:44:05
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.IntegerValueComparator;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.YesNoDecider;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultBindableRadioButtonField;
import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class FgskKartierabschnittUferstruktur extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            FgskKartierabschnittUferstruktur.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "fgsk_z_uferverbau");
    private static final int NONE_VAL = 8;
    private static final int NONE_VAL_STATE = 4;
    private static IntegerValueComparator comparator = new IntegerValueComparator();

    //~ Instance fields --------------------------------------------------------

    private YesNoDecider decider;
    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbTypischL;
    private javax.swing.JCheckBox cbTypischR;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbUferbewuchsL;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbUferbewuchsR;
    private de.cismet.tools.gui.RoundedPanel glassPanel;
    private javax.swing.JPanel jpUferbewuchs;
    private javax.swing.JPanel jpUferverbau;
    private javax.swing.JPanel jpZustand;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.KartierabschnittBesUferbelastungen
        kartierabschnittBesUferbelastungen1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.KartierabschnittBesUferstrukturen
        kartierabschnittBesUferstrukturen1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.KartierabschnittUebersicht kartierabschnittUebersicht1;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblSpacing;
    private javax.swing.JLabel lblSpacing1;
    private javax.swing.JLabel lblSpacing2;
    private javax.swing.JLabel lblTypL;
    private javax.swing.JLabel lblTypR;
    private javax.swing.JLabel lblUferbewuchsL;
    private javax.swing.JLabel lblUferbewuchsR;
    private javax.swing.JLabel lblUferverbauL;
    private javax.swing.JLabel lblUferverbauR;
    private javax.swing.JLabel lblZustandL;
    private javax.swing.JLabel lblZustandR;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo3;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent2;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdUferverbauL;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdUferverbauR;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdZustandL;
    private de.cismet.cids.editors.DefaultBindableRadioButtonField rdZustandR;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public FgskKartierabschnittUferstruktur() {
        initComponents();
        setOpaque(false);
        rdUferverbauL.setEnableLabels(false);
        rdZustandL.setEnableLabels(false);
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

        glassPanel = new de.cismet.tools.gui.RoundedPanel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        kartierabschnittUebersicht1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.KartierabschnittUebersicht();
        jpUferbewuchs = new javax.swing.JPanel();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        lblUferbewuchsL = new javax.swing.JLabel();
        lblSpacing = new javax.swing.JLabel();
        cbUferbewuchsL = new ScrollableComboBox(comparator);
        lblUferbewuchsR = new javax.swing.JLabel();
        cbUferbewuchsR = new ScrollableComboBox(comparator);
        lblTypR = new javax.swing.JLabel();
        lblTypL = new javax.swing.JLabel();
        cbTypischL = new javax.swing.JCheckBox();
        cbTypischR = new javax.swing.JCheckBox();
        jpUferverbau = new javax.swing.JPanel();
        panInfo2 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        lblUferverbauL = new javax.swing.JLabel();
        lblSpacing1 = new javax.swing.JLabel();
        lblUferverbauR = new javax.swing.JLabel();
        rdUferverbauL = new de.cismet.cids.editors.DefaultBindableRadioButtonField();
        rdUferverbauR = new de.cismet.cids.editors.DefaultBindableRadioButtonField();
        kartierabschnittBesUferstrukturen1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.KartierabschnittBesUferstrukturen();
        kartierabschnittBesUferbelastungen1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.KartierabschnittBesUferbelastungen();
        jpZustand = new javax.swing.JPanel();
        panInfo3 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        lblZustandL = new javax.swing.JLabel();
        lblSpacing2 = new javax.swing.JLabel();
        lblZustandR = new javax.swing.JLabel();
        rdZustandL = new de.cismet.cids.editors.DefaultBindableRadioButtonField();
        rdZustandR = new de.cismet.cids.editors.DefaultBindableRadioButtonField();

        setMaximumSize(new java.awt.Dimension(1100, 650));
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1100, 650));
        setLayout(new java.awt.GridBagLayout());

        glassPanel.setAlpha(0);
        glassPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(glassPanel, gridBagConstraints);

        panInfo.setLayout(new java.awt.GridBagLayout());

        kartierabschnittUebersicht1.setMinimumSize(new java.awt.Dimension(1100, 100));
        kartierabschnittUebersicht1.setPreferredSize(new java.awt.Dimension(1100, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panInfo.add(kartierabschnittUebersicht1, gridBagConstraints);

        jpUferbewuchs.setMinimumSize(new java.awt.Dimension(1100, 100));
        jpUferbewuchs.setOpaque(false);
        jpUferbewuchs.setPreferredSize(new java.awt.Dimension(1100, 100));
        jpUferbewuchs.setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittUferstruktur.class,
                "FgskKartierabschnittUferstruktur.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo1.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblUferbewuchsL.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittUferstruktur.class,
                "FgskKartierabschnittUferstruktur.lblUferbewuchsL.text")); // NOI18N
        lblUferbewuchsL.setMaximumSize(new java.awt.Dimension(120, 17));
        lblUferbewuchsL.setMinimumSize(new java.awt.Dimension(130, 17));
        lblUferbewuchsL.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblUferbewuchsL, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(lblSpacing, gridBagConstraints);

        cbUferbewuchsL.setMinimumSize(new java.awt.Dimension(170, 20));
        cbUferbewuchsL.setPreferredSize(new java.awt.Dimension(170, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ufervegetation_links_id}"),
                cbUferbewuchsL,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent.add(cbUferbewuchsL, gridBagConstraints);

        lblUferbewuchsR.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittUferstruktur.class,
                "FgskKartierabschnittUferstruktur.lblUferbewuchsR.text")); // NOI18N
        lblUferbewuchsR.setMaximumSize(new java.awt.Dimension(120, 17));
        lblUferbewuchsR.setMinimumSize(new java.awt.Dimension(130, 17));
        lblUferbewuchsR.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblUferbewuchsR, gridBagConstraints);

        cbUferbewuchsR.setMinimumSize(new java.awt.Dimension(170, 20));
        cbUferbewuchsR.setPreferredSize(new java.awt.Dimension(170, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ufervegetation_rechts_id}"),
                cbUferbewuchsR,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(cbUferbewuchsR, gridBagConstraints);

        lblTypR.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittUferstruktur.class,
                "FgskKartierabschnittUferstruktur.lblTypR.text")); // NOI18N
        lblTypR.setMaximumSize(new java.awt.Dimension(120, 17));
        lblTypR.setMinimumSize(new java.awt.Dimension(120, 17));
        lblTypR.setPreferredSize(new java.awt.Dimension(120, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblTypR, gridBagConstraints);

        lblTypL.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittUferstruktur.class,
                "FgskKartierabschnittUferstruktur.lblTypL.text")); // NOI18N
        lblTypL.setMaximumSize(new java.awt.Dimension(120, 17));
        lblTypL.setMinimumSize(new java.awt.Dimension(120, 17));
        lblTypL.setPreferredSize(new java.awt.Dimension(120, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblTypL, gridBagConstraints);

        cbTypischL.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ufervegetation_links_typical}"),
                cbTypischL,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent.add(cbTypischL, gridBagConstraints);

        cbTypischR.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ufervegetation_rechts_typical}"),
                cbTypischR,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(cbTypischR, gridBagConstraints);

        panInfo1.add(panInfoContent, java.awt.BorderLayout.CENTER);

        jpUferbewuchs.add(panInfo1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panInfo.add(jpUferbewuchs, gridBagConstraints);

        jpUferverbau.setMinimumSize(new java.awt.Dimension(540, 250));
        jpUferverbau.setOpaque(false);
        jpUferverbau.setPreferredSize(new java.awt.Dimension(540, 250));
        jpUferverbau.setLayout(new java.awt.BorderLayout());

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittUferstruktur.class,
                "FgskKartierabschnittUferstruktur.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo2.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        lblUferverbauL.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittUferstruktur.class,
                "FgskKartierabschnittUferstruktur.lblUferverbauL.text")); // NOI18N
        lblUferverbauL.setMaximumSize(new java.awt.Dimension(40, 17));
        lblUferverbauL.setMinimumSize(new java.awt.Dimension(40, 17));
        lblUferverbauL.setPreferredSize(new java.awt.Dimension(40, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent1.add(lblUferverbauL, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent1.add(lblSpacing1, gridBagConstraints);

        lblUferverbauR.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittUferstruktur.class,
                "FgskKartierabschnittUferstruktur.lblUferverbauR.text")); // NOI18N
        lblUferverbauR.setMaximumSize(new java.awt.Dimension(120, 17));
        lblUferverbauR.setMinimumSize(new java.awt.Dimension(130, 17));
        lblUferverbauR.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent1.add(lblUferverbauR, gridBagConstraints);

        rdUferverbauL.setMinimumSize(new java.awt.Dimension(30, 140));
        rdUferverbauL.setOpaque(false);
        rdUferverbauL.setPreferredSize(new java.awt.Dimension(30, 140));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferverbau_links_id}"),
                rdUferverbauL,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        rdUferverbauL.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(final java.beans.PropertyChangeEvent evt) {
                    rdUferverbauLPropertyChange(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent1.add(rdUferverbauL, gridBagConstraints);

        rdUferverbauR.setMinimumSize(new java.awt.Dimension(300, 140));
        rdUferverbauR.setOpaque(false);
        rdUferverbauR.setPreferredSize(new java.awt.Dimension(300, 140));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferverbau_rechts_id}"),
                rdUferverbauR,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        rdUferverbauR.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(final java.beans.PropertyChangeEvent evt) {
                    rdUferverbauRPropertyChange(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent1.add(rdUferverbauR, gridBagConstraints);

        panInfo2.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        jpUferverbau.add(panInfo2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panInfo.add(jpUferverbau, gridBagConstraints);

        kartierabschnittBesUferstrukturen1.setMinimumSize(new java.awt.Dimension(540, 150));
        kartierabschnittBesUferstrukturen1.setPreferredSize(new java.awt.Dimension(540, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panInfo.add(kartierabschnittBesUferstrukturen1, gridBagConstraints);

        kartierabschnittBesUferbelastungen1.setMinimumSize(new java.awt.Dimension(540, 150));
        kartierabschnittBesUferbelastungen1.setPreferredSize(new java.awt.Dimension(540, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
        panInfo.add(kartierabschnittBesUferbelastungen1, gridBagConstraints);

        jpZustand.setMinimumSize(new java.awt.Dimension(540, 250));
        jpZustand.setOpaque(false);
        jpZustand.setPreferredSize(new java.awt.Dimension(540, 250));
        jpZustand.setLayout(new java.awt.BorderLayout());

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittUferstruktur.class,
                "FgskKartierabschnittUferstruktur.lblHeading2.text")); // NOI18N
        panHeadInfo2.add(lblHeading2);

        panInfo3.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        lblZustandL.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittUferstruktur.class,
                "FgskKartierabschnittUferstruktur.lblZustandL.text")); // NOI18N
        lblZustandL.setMaximumSize(new java.awt.Dimension(40, 17));
        lblZustandL.setMinimumSize(new java.awt.Dimension(40, 17));
        lblZustandL.setPreferredSize(new java.awt.Dimension(40, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent2.add(lblZustandL, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent2.add(lblSpacing2, gridBagConstraints);

        lblZustandR.setText(org.openide.util.NbBundle.getMessage(
                FgskKartierabschnittUferstruktur.class,
                "FgskKartierabschnittUferstruktur.lblZustandR.text")); // NOI18N
        lblZustandR.setMaximumSize(new java.awt.Dimension(120, 17));
        lblZustandR.setMinimumSize(new java.awt.Dimension(130, 17));
        lblZustandR.setPreferredSize(new java.awt.Dimension(130, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent2.add(lblZustandR, gridBagConstraints);

        rdZustandL.setMinimumSize(new java.awt.Dimension(30, 100));
        rdZustandL.setOpaque(false);
        rdZustandL.setPreferredSize(new java.awt.Dimension(30, 100));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.z_uferverbau_links_id}"),
                rdZustandL,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent2.add(rdZustandL, gridBagConstraints);

        rdZustandR.setMinimumSize(new java.awt.Dimension(300, 100));
        rdZustandR.setOpaque(false);
        rdZustandR.setPreferredSize(new java.awt.Dimension(300, 100));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.z_uferverbau_rechts_id}"),
                rdZustandR,
                org.jdesktop.beansbinding.BeanProperty.create("selectedElements"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent2.add(rdZustandR, gridBagConstraints);

        panInfo3.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        jpZustand.add(panInfo3, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 0);
        panInfo.add(jpZustand, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(panInfo, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rdUferverbauLPropertyChange(final java.beans.PropertyChangeEvent evt) { //GEN-FIRST:event_rdUferverbauLPropertyChange
        if ((evt.getPropertyName().equals("selectedElements"))) {
            final CidsBean newValue = (CidsBean)evt.getNewValue();
            refreshState(newValue, rdZustandL, "z_uferverbau_links_id");
        }
    }                                                                                    //GEN-LAST:event_rdUferverbauLPropertyChange

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void rdUferverbauRPropertyChange(final java.beans.PropertyChangeEvent evt) { //GEN-FIRST:event_rdUferverbauRPropertyChange
        if ((evt.getPropertyName().equals("selectedElements"))) {
            final CidsBean newValue = (CidsBean)evt.getNewValue();
            refreshState(newValue, rdZustandR, "z_uferverbau_rechts_id");
        }
    }                                                                                    //GEN-LAST:event_rdUferverbauRPropertyChange

    /**
     * DOCUMENT ME!
     *
     * @param  selectedElement  DOCUMENT ME!
     * @param  rbField          DOCUMENT ME!
     * @param  fieldname        DOCUMENT ME!
     */
    private void refreshState(final CidsBean selectedElement,
            final DefaultBindableRadioButtonField rbField,
            final String fieldname) {
        new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        final boolean enabled = isStateEnabled(selectedElement);

                        if (decider == null) {
                            decider = new YesNoDecider(enabled);
                            decider.addPositiveException(getZUferverbauByValue(NONE_VAL_STATE).getMetaObject());
                        } else {
                            decider.setEnable(enabled);
                        }

                        if (!enabled) {
                            final CidsBean none = getZUferverbauByValue(NONE_VAL_STATE);
                            cidsBean.setProperty(fieldname, none);
                            rbField.setSelectedElements(none);
                        } else {
                            final CidsBean selected = (CidsBean)rbField.getSelectedElements();
                            if (selected != null) {
                                final Integer val = (Integer)selected.getProperty("value");
                                if (val == NONE_VAL_STATE) {
                                    cidsBean.setProperty(fieldname, getZUferverbauByValue(1));
                                    rbField.setSelectedElements(getZUferverbauByValue(1));
                                }
                            }
                        }

                        rbField.refreshCheckboxState(decider, false);
                    } catch (final Exception e) {
                        LOG.error("Error while refreshing the state.", e);
                    }
                }
            }).start();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isStateEnabled(final CidsBean bean) {
        if (bean != null) {
            final Integer value = (Integer)bean.getProperty("value");

            if ((value != null) && (value == NONE_VAL)) {
                return false;
            }
        }

        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   value  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ConnectionException  DOCUMENT ME!
     */
    private CidsBean getZUferverbauByValue(final int value) throws ConnectionException {
        final String query = "select " + MC.getID() + ", " + MC.getPrimaryKey() + " from "
                    + MC.getTableName() + " where value = " + value;
        final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

        if (metaObjects.length == 1) {
            return metaObjects[0].getBean();
        } else {
            return null;
        }
    }

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
        bindingGroup.unbind();
        if (cidsBean != null) {
            this.cidsBean = cidsBean;
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                this.cidsBean);
            bindingGroup.bind();
            kartierabschnittUebersicht1.setCidsBean(cidsBean);
            kartierabschnittBesUferbelastungen1.setCidsBean(cidsBean);
            kartierabschnittBesUferstrukturen1.setCidsBean(cidsBean);
            refreshState((CidsBean)cidsBean.getProperty("uferverbau_links_id"), rdZustandL, "z_uferverbau_links_id");
            refreshState((CidsBean)cidsBean.getProperty("uferverbau_rechts_id"), rdZustandR, "z_uferverbau_rechts_id");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public void setReadOnly(final boolean readOnly) {
        if (readOnly) {
            glassPanel.addMouseListener(new MouseAdapter() {
                });
        } else {
            for (final MouseListener ml : glassPanel.getMouseListeners()) {
                glassPanel.removeMouseListener(ml);
            }
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
        kartierabschnittUebersicht1.dispose();
        kartierabschnittBesUferbelastungen1.dispose();
        kartierabschnittBesUferstrukturen1.dispose();
        rdUferverbauL.dispose();
        rdUferverbauR.dispose();
        rdZustandL.dispose();
        rdZustandR.dispose();
    }
}
