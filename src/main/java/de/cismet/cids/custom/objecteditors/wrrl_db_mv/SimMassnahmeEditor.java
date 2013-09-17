/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;

import org.apache.log4j.Logger;

import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.VerticalTextIcon;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class SimMassnahmeEditor extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(SimMassnahmeEditor.class);
    private static final String[] columnNames = {
            "Typ",
            "Wirkungsklasse",
            "Kostenklasse",
            "Wirkung FGSK",
            "Wirkung Biologie",
            "Fische",
            "Makrozoobenthos",
            "Makrophyten",
            "Laufkrümmung",
            "Anzahl Längsbänke",
            "Anzahl Laufstrukturen",
            "Krümmungserosion",
            "Anzahl Querbänke",
            "Strömungsdiversität",
            "Tiefenvarianz",
            "Substratdiversität",
            "Sohlverbau",
            "Anzahl Sohlstrukturen",
            "Sohltiefe",
            "Breitenerosion",
            "Breitenvarianz",
            "Profiltyp",
            "Uferverbau",
            "Anzahl Uferstrukturen",
            "Uferbewuchs",
            "Gewässerrandstreifen",
            "Flächennutzung",
            "so. Umfeldstrukturen"
        };

    private static final Map<String, String> tooltips = new HashMap<String, String>();
    private static final MetaClass LAWA_TYPE = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "la_lawa_nr");
    private static final MetaClass WIRKUNG = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "sim_wirkungsklasse");
    private static final MetaClass KOSTEN = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "sim_kostenklasse");

    //~ Instance fields --------------------------------------------------------

    private String title;
    private CidsBean cidsBean;
    private JComboBox lawaCombo = new ScrollableComboBox(LAWA_TYPE);
    private JComboBox wirkungCombo = new ScrollableComboBox(WIRKUNG);
    private JComboBox kostenCombo = new ScrollableComboBox(KOSTEN);
    private CustomTableModel model;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbRestriktion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JButton jbAdd;
    private javax.swing.JButton jbRemove;
    private javax.swing.JScrollPane jsTable;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblBerechnung;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHilfe;
    private javax.swing.JLabel lblKurzbez;
    private javax.swing.JLabel lblMNT;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOrt;
    private javax.swing.JLabel lblPreis;
    private de.cismet.tools.gui.RoundedPanel panAuswirkungen;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private de.cismet.tools.gui.RoundedPanel panKosten;
    private javax.swing.JTextArea taHilfe;
    private javax.swing.JTable tabWirkung;
    private javax.swing.JTextField txtKurzbez;
    private javax.swing.JTextField txtMnt;
    private javax.swing.JTextField txtName;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form SimMassnahmenEditor.
     */
    public SimMassnahmeEditor() {
        initComponents();
        RendererTools.makeReadOnly(taHilfe);
        jSplitPane1.setOpaque(false);
        jSplitPane2.setOpaque(false);
        jSplitPane3.setOpaque(false);
        jSplitPane1.setBorder(null);
        jSplitPane2.setBorder(null);
        jSplitPane3.setBorder(null);

        for (int i = 0; i < columnNames.length; ++i) {
            if (i == 3) {
                tooltips.put(columnNames[i], "Summe Wirkung FGSK");
            } else if (i == 4) {
                tooltips.put(columnNames[i], "Summe Wirkung Biologie");
            } else if (i == 9) {
                tooltips.put(columnNames[i], "Anzahl der Längsbänke mit vorherrschenden Substraten");
            } else if (i == 10) {
                tooltips.put(columnNames[i], "Anzahl besonderer Laufstrukturen");
            } else if (i == 12) {
                tooltips.put(columnNames[i], "Anzahl der Querbänke mit den vorherrschenden Substraten");
            } else if (i == 18) {
                tooltips.put(columnNames[i], "Anzahl besonderer Sohlstrukturen");
            } else if (i == 19) {
                tooltips.put(columnNames[i], "Sohltiefe / obere Profilbreite");
            } else if (i == 24) {
                tooltips.put(columnNames[i], "Anzahl besonderer Uferstrukturen");
            } else if (i == 27) {
                tooltips.put(columnNames[i], "sonstige Umfeldstrukturen");
            } else {
                tooltips.put(columnNames[i], columnNames[i]);
            }
        }

        tabWirkung.getTableHeader()
                .setDefaultRenderer(new CustomTableHeaderCellRenderer(
                        tabWirkung.getTableHeader().getDefaultRenderer()));
        tabWirkung.getTableHeader().setPreferredSize(new Dimension(25, 150));
        lawaCombo.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(final JList<?> list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    String text = "";

                    if (value != null) {
                        text = "Typ " + ((CidsBean)value).getProperty("code");
                    }
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });

//        tabWirkung.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
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

        jPanel1 = new javax.swing.JPanel();
        lblKurzbez = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtKurzbez = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtMnt = new javax.swing.JTextField();
        lblOrt = new javax.swing.JLabel();
        lblBemerkung = new javax.swing.JLabel();
        cbRestriktion = new ScrollableComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblMNT = new javax.swing.JLabel();
        jSplitPane3 = new javax.swing.JSplitPane();
        panKosten = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        lblPreis = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        lblBerechnung = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        lblHilfe = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        taHilfe = new javax.swing.JTextArea();
        panAuswirkungen = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jsTable = new javax.swing.JScrollPane();
        tabWirkung = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jbAdd = new javax.swing.JButton();
        jbRemove = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblKurzbez.setText(org.openide.util.NbBundle.getMessage(
                SimMassnahmeEditor.class,
                "SimMassnahmeEditor.lblKurzbez.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        jPanel1.add(lblKurzbez, gridBagConstraints);

        lblName.setText(org.openide.util.NbBundle.getMessage(
                SimMassnahmeEditor.class,
                "SimMassnahmeEditor.lblName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanel1.add(lblName, gridBagConstraints);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.key}"),
                txtKurzbez,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        jPanel1.add(txtKurzbez, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanel1.add(txtName, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mnt}"),
                txtMnt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanel1.add(txtMnt, gridBagConstraints);

        lblOrt.setText(org.openide.util.NbBundle.getMessage(
                SimMassnahmeEditor.class,
                "SimMassnahmeEditor.lblOrt.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 5, 5);
        jPanel1.add(lblOrt, gridBagConstraints);

        lblBemerkung.setText(org.openide.util.NbBundle.getMessage(
                SimMassnahmeEditor.class,
                "SimMassnahmeEditor.lblBemerkung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanel1.add(lblBemerkung, gridBagConstraints);

        cbRestriktion.setMinimumSize(new java.awt.Dimension(120, 20));
        cbRestriktion.setPreferredSize(new java.awt.Dimension(80, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ort}"),
                cbRestriktion,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        jPanel1.add(cbRestriktion, gridBagConstraints);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(3);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        lblMNT.setText(org.openide.util.NbBundle.getMessage(
                SimMassnahmeEditor.class,
                "SimMassnahmeEditor.lblMNT.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        jPanel1.add(lblMNT, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 15);
        add(jPanel1, gridBagConstraints);

        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        panKosten.setMinimumSize(new java.awt.Dimension(880, 200));
        panKosten.setPreferredSize(new java.awt.Dimension(880, 300));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                SimMassnahmeEditor.class,
                "SimMassnahmeEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panKosten.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jSplitPane1.setDividerLocation(600);

        jSplitPane2.setDividerLocation(297);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblPreis.setText(org.openide.util.NbBundle.getMessage(
                SimMassnahmeEditor.class,
                "SimMassnahmeEditor.lblPreis.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblPreis, gridBagConstraints);

        jTextArea2.setColumns(10);
        jTextArea2.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kosten}"),
                jTextArea2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(jTextArea2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel2.add(jScrollPane2, gridBagConstraints);

        jSplitPane2.setLeftComponent(jPanel2);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblBerechnung.setText(org.openide.util.NbBundle.getMessage(
                SimMassnahmeEditor.class,
                "SimMassnahmeEditor.lblBerechnung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblBerechnung, gridBagConstraints);

        jTextArea3.setColumns(10);
        jTextArea3.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kostenformel}"),
                jTextArea3,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane3.setViewportView(jTextArea3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel3.add(jScrollPane3, gridBagConstraints);

        jSplitPane2.setRightComponent(jPanel3);

        jSplitPane1.setLeftComponent(jSplitPane2);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        lblHilfe.setText(org.openide.util.NbBundle.getMessage(
                SimMassnahmeEditor.class,
                "SimMassnahmeEditor.lblHilfe.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblHilfe, gridBagConstraints);

        taHilfe.setColumns(10);
        taHilfe.setEditable(false);
        taHilfe.setRows(5);
        taHilfe.setText(org.openide.util.NbBundle.getMessage(
                SimMassnahmeEditor.class,
                "SimMassnahmeEditor.taHilfe.text")); // NOI18N
        jScrollPane4.setViewportView(taHilfe);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jScrollPane4, gridBagConstraints);

        jSplitPane1.setRightComponent(jPanel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(jSplitPane1, gridBagConstraints);

        panKosten.add(panInfoContent, java.awt.BorderLayout.CENTER);

        jSplitPane3.setLeftComponent(panKosten);

        panAuswirkungen.setMinimumSize(new java.awt.Dimension(880, 200));
        panAuswirkungen.setPreferredSize(new java.awt.Dimension(880, 300));

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(209, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(209, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                SimMassnahmeEditor.class,
                "SimMassnahmeEditor.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panAuswirkungen.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jsTable.setViewportView(tabWirkung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 10, 15);
        jPanel8.add(jsTable, gridBagConstraints);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jbAdd.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_16.png"))); // NOI18N
        jbAdd.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbAddActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 15, 10);
        jPanel5.add(jbAdd, gridBagConstraints);

        jbRemove.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_16.png"))); // NOI18N
        jbRemove.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbRemoveActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 15, 10);
        jPanel5.add(jbRemove, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel8.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panInfoContent1.add(jPanel8, gridBagConstraints);

        panAuswirkungen.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        jSplitPane3.setRightComponent(panAuswirkungen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(jSplitPane3, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbAddActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbAddActionPerformed
        try {
            final CidsBean bean = CidsBeanSupport.createNewCidsBeanFromTableName("sim_massnahmen_wirkung");
            model.addBean(bean);
        } catch (Exception e) {
            LOG.error("Error while creating a new bean of type sim_massnahmen_wirkung", e);
        }
    }                                                                         //GEN-LAST:event_jbAddActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbRemoveActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbRemoveActionPerformed
        final int[] selectedRows = tabWirkung.getSelectedRows();
        Arrays.sort(selectedRows);

        for (int i = selectedRows.length - 1; i >= 0; --i) {
            model.removeRow(selectedRows[i]);
        }
    } //GEN-LAST:event_jbRemoveActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        this.cidsBean = cidsBean;
        bindingGroup.unbind();

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            model = new CustomTableModel(cidsBean.getBeanCollectionProperty("wirkungen"));
            tabWirkung.setModel(model);

            tabWirkung.getColumnModel().getColumn(0).setCellEditor(new ComboBoxCellEditor(lawaCombo));
            tabWirkung.getColumnModel().getColumn(1).setCellEditor(new ComboBoxCellEditor(wirkungCombo));
            tabWirkung.getColumnModel().getColumn(2).setCellEditor(new ComboBoxCellEditor(kostenCombo));

            tabWirkung.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(final JTable table,
                            final Object value,
                            final boolean isSelected,
                            final boolean hasFocus,
                            final int row,
                            final int column) {
                        String text = "";

                        if (value != null) {
                            text = "Typ " + ((CidsBean)value).getProperty("code");
                        }

                        return super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
                    }
                });
            setTableSize();
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setTableSize() {
        final TableColumnModel columnModel = tabWirkung.getColumnModel();
        final FontMetrics fmetrics = tabWirkung.getFontMetrics(tabWirkung.getFont());
        final TableModel model = tabWirkung.getModel();
        final int columnCount = model.getColumnCount();
        final int totalSize = 0;

        for (int i = 2; i < columnCount; ++i) {
            final int size = 25;
            columnModel.getColumn(i).setMinWidth(size);
            columnModel.getColumn(i).setWidth(size);
            columnModel.getColumn(i).setPreferredWidth(size);
        }

        tabWirkung.setMinimumSize(new Dimension(totalSize + 20, 50));
    }

    @Override
    public void dispose() {
    }

    @Override
    public String getTitle() {
        if (cidsBean != null) {
            return
                (((cidsBean.toString() == null) || cidsBean.toString().equals("") || cidsBean.toString().equals("null"))
                    ? "unbenannt" : cidsBean.toString());
        } else {
            return "unbenannt";
        }
    }

    @Override
    public void setTitle(final String title) {
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "kif",
            "sim_massnahme",
            241,
            1280,
            1024);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomTableModel implements TableModel {

        //~ Instance fields ----------------------------------------------------

        List<CidsBean> beans;
        List<TableModelListener> listener = new ArrayList<TableModelListener>();

        private final String[] columnProperties = {
                "gewaessertyp",
                "wirkungsklasse",
                "kostenklasse",
                "summe_wirkung_fgsk",
                "summe_wirkung_biologie",
                "fische",
                "makrozoobenthos",
                "makrophyten",
                "laufkruemmung",
                "anzahl_laengsbaenken_mvs",
                "anzahl_besonderer_laufstrukturen",
                "kruemmungserosion",
                "anzahl_querbaenke_mvs",
                "stroemungsdiversitaet",
                "tiefenvarianz",
                "substratdiversitaet",
                "sohlverbau",
                "anzahl_besonderer_sohlstrukturen",
                "sohltiefe_obere_profilbreite",
                "breitenerosion",
                "breitenvarianz",
                "profiltyp",
                "uferverbau",
                "anzahl_besonderer_uferstrukturen",
                "uferbewuchs",
                "gewaesserrandstreifen",
                "flaechennutzung",
                "sonstige_umfeldstrukturen"
            };

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomTableModel object.
         *
         * @param  beans  DOCUMENT ME!
         */
        public CustomTableModel(final List<CidsBean> beans) {
            this.beans = beans;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            return beans.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            if (columnIndex <= 2) {
                return CidsBean.class;
            } else {
                return String.class;
            }
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return true;
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            if (columnIndex == 0) {
                return beans.get(rowIndex).getProperty("gewaessertyp");
            } else {
                return beans.get(rowIndex).getProperty(columnProperties[columnIndex]);
            }
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            if (columnIndex <= 2) {
                try {
                    beans.get(rowIndex).setProperty(columnProperties[columnIndex], aValue);
                } catch (Exception e) {
                    LOG.error("Error while set value.", e);
                }
            } else {
                try {
                    beans.get(rowIndex).setProperty(columnProperties[columnIndex], Integer.parseInt((String)aValue));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(
                        SimMassnahmeEditor.this,
                        "Es sind nur ganze Zahlen zulässig.",
                        "Ungültige Eingabe",
                        JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    LOG.error("Error while set value.", e);
                }
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param  bean  DOCUMENT ME!
         */
        public void addBean(final CidsBean bean) {
            beans.add(bean);
            fireTableChangedEvent();
        }

        /**
         * DOCUMENT ME!
         *
         * @param  bean  DOCUMENT ME!
         */
        public void removeBean(final CidsBean bean) {
            try {
                beans.remove(bean);
                bean.delete();
            } catch (final Exception e) {
                LOG.error("Error while deleting bean of type sim_massnahmen_wirkung", e);
            }

            fireTableChangedEvent();
        }

        /**
         * DOCUMENT ME!
         *
         * @param  row  DOCUMENT ME!
         */
        public void removeRow(final int row) {
            removeBean(beans.get(row));
        }

        @Override
        public void addTableModelListener(final TableModelListener l) {
            listener.add(l);
        }

        @Override
        public void removeTableModelListener(final TableModelListener l) {
            listener.remove(l);
        }

        /**
         * DOCUMENT ME!
         */
        public void fireTableChangedEvent() {
            final TableModelEvent e = new TableModelEvent(this);

            for (final TableModelListener l : listener) {
                l.tableChanged(e);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomTableHeaderCellRenderer extends JLabel implements TableCellRenderer {

        //~ Instance fields ----------------------------------------------------

        TableCellRenderer renderer = new DefaultTableCellRenderer();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomTableHeaderCellRenderer object.
         *
         * @param  renderer  DOCUMENT ME!
         */
        public CustomTableHeaderCellRenderer(final TableCellRenderer renderer) {
            this.renderer = renderer;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getTableCellRendererComponent(final JTable table,
                final Object value,
                final boolean isSelected,
                final boolean hasFocus,
                final int row,
                final int column) {
            final VerticalTextIcon icon = new VerticalTextIcon(String.valueOf(value), false);
            final Component c = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setText(null);
            setIcon(icon);
            setHorizontalAlignment(JLabel.CENTER);
            setToolTipText(tooltips.get((String)value));
            setBorder(MetalBorders.getTextBorder());

            return this;
        }
    }
}
