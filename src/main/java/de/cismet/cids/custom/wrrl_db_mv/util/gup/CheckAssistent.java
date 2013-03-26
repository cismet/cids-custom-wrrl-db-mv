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
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import Sirius.server.middleware.types.MetaClass;

import org.apache.log4j.Logger;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupGupEditor;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupPlanungsabschnittEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanStore;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.MetaObjectNodeServerSearch;

import de.cismet.cids.tools.search.clientstuff.CidsWindowSearch;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = CidsWindowSearch.class)
public class CheckAssistent extends javax.swing.JPanel implements CidsWindowSearch,
    CidsBeanDropListener,
    CidsBeanStore,
    ListSelectionListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final String TABLE_HEADER_ALL = "Alle Maßnahmen";
    private static final String TABLE_HEADER_UFER_LINKS = "Ufer links";
    private static final String TABLE_HEADER_UFER_RECHTS = "Ufer rechts";
    private static final String TABLE_HEADER_SOHLE = "Sohle";
    private static final String TABLE_HEADER_UMFELD_LINKS = "Umfeld links";
    private static final String TABLE_HEADER_UMFELD_RECHTS = "Umfeld rechts";
    private static final String TABLE_HEADER_VALIDE = "Valide Maßnahmen";
    private static final String TABLE_HEADER_INVALIDE = "Invalide Maßnahmen";
    private static final String TABLE_HEADER_ABGELEHNT = "Abgelehnte Maßnahmen";
    private static final String TABLE_HEADER_AUFLAGEN = "Maßnahmen mit Auflagen";
    private static final String TABLE_HEADER_ANGENOMMEN = "Angenommene Maßnahmen";
    private static final String TABLE_HEADER_UNGEPRUEFT = "Ungeprüfte Maßnahmen";
    private static final String TABLE_HEADER_GEMISCHT = "Ungefiltert";
    private static Logger LOG = Logger.getLogger(CheckAssistent.class);
    private static CheckAssistent instance;
    private static final String DOC_ICON = "/de/cismet/cids/custom/wrrl_db_mv/util/gup/document.png";

    //~ Enums ------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static enum Decision {

        //~ Enum constants -----------------------------------------------------

        accepted, onCondition, declined, notChecked
    }

    //~ Instance fields --------------------------------------------------------

    List<CheckAssistentListener> listener = new ArrayList<CheckAssistentListener>();

    private CidsBean planungsabschnitt;
    private UnterhaltungsmassnahmeValidator validator;
    private ExaminationManager examinationManager = new ExaminationManager();
    private boolean ignoreSetSelection = false;
    private boolean readOnly = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXHyperlink hlAbgelehnt;
    private org.jdesktop.swingx.JXHyperlink hlAngenommen;
    private org.jdesktop.swingx.JXHyperlink hlAuflagen;
    private org.jdesktop.swingx.JXHyperlink hlGesMassn;
    private org.jdesktop.swingx.JXHyperlink hlInvalide;
    private org.jdesktop.swingx.JXHyperlink hlSohle;
    private org.jdesktop.swingx.JXHyperlink hlUferLinks;
    private org.jdesktop.swingx.JXHyperlink hlUferRechts;
    private org.jdesktop.swingx.JXHyperlink hlUmfeldLinks;
    private org.jdesktop.swingx.JXHyperlink hlUmfeldRechts;
    private org.jdesktop.swingx.JXHyperlink hlUngeprueft;
    private org.jdesktop.swingx.JXHyperlink hlValide;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel jpEmpty;
    private javax.swing.JPanel jpEntscheidung;
    private javax.swing.JPanel jpMain;
    private javax.swing.JPanel jpPruefung;
    private javax.swing.JPanel jpStat;
    private javax.swing.JPanel jpStatistik;
    private javax.swing.JPanel jpTable;
    private javax.swing.JPanel jpVorpruefung;
    private javax.swing.JScrollPane jsMain;
    private javax.swing.JLabel lblAbgelehntLab;
    private javax.swing.JLabel lblAngenommenLab;
    private javax.swing.JLabel lblAuflagen;
    private javax.swing.JLabel lblAuflagenLab;
    private javax.swing.JLabel lblClosed;
    private javax.swing.JLabel lblStat;
    private javax.swing.JLabel lblStat2;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusPrefix;
    private javax.swing.JLabel lblTableHeader;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUngeprueftLab;
    private javax.swing.JLabel lblVorpruefung;
    private javax.swing.JPanel panTitle;
    private javax.swing.JToggleButton tbAngenommen;
    private javax.swing.JTextArea textAuflagen;
    private javax.swing.JToggleButton tgAbgelehnt;
    private javax.swing.JLabel txtHint;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenHistory.
     */
    public CheckAssistent() {
        initComponents();
        hlGesMassn.setClickedColor(hlGesMassn.getUnclickedColor());
        hlAbgelehnt.setClickedColor(hlGesMassn.getUnclickedColor());
        hlAngenommen.setClickedColor(hlGesMassn.getUnclickedColor());
        hlAuflagen.setClickedColor(hlGesMassn.getUnclickedColor());
        hlInvalide.setClickedColor(hlGesMassn.getUnclickedColor());
        hlSohle.setClickedColor(hlGesMassn.getUnclickedColor());
        hlUferLinks.setClickedColor(hlGesMassn.getUnclickedColor());
        hlUferRechts.setClickedColor(hlGesMassn.getUnclickedColor());
        hlUmfeldLinks.setClickedColor(hlGesMassn.getUnclickedColor());
        hlUmfeldRechts.setClickedColor(hlGesMassn.getUnclickedColor());
        hlUngeprueft.setClickedColor(hlGesMassn.getUnclickedColor());
        hlValide.setClickedColor(hlGesMassn.getUnclickedColor());
        final Highlighter alternateRowHighlighter = HighlighterFactory.createAlternateStriping(
                new Color(255, 255, 255),
                new Color(235, 235, 235));
        ((JXTable)jTable1).setHighlighters(alternateRowHighlighter);
        ((JXTable)jTable1).setUpdateSelectionOnSort(true);
//        final TableRowSorter sorter = new TableRowSorter();
//        sorter.setMaxSortKeys(3);
//        jTable1.setRowSorter(sorter);
        jTable1.setDefaultRenderer(JCheckBox.class, new CheckBoxRenderer());
        jTable1.setDefaultRenderer(StationCell.class, new StationCellRenderer());
        setName("Prüfassistent");
        lblClosed.setText("Es ist kein Planungsabschnitt geöffnet");

        switchToForm("closed");
        instance = this;
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

        jpEmpty = new javax.swing.JPanel();
        lblClosed = new javax.swing.JLabel();
        jpMain = new javax.swing.JPanel();
        jsMain = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblStatusPrefix = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jpStat = new javax.swing.JPanel();
        lblStat = new javax.swing.JLabel();
        lblVorpruefung = new javax.swing.JLabel();
        lblStat2 = new javax.swing.JLabel();
        jpStatistik = new javax.swing.JPanel();
        hlGesMassn = new org.jdesktop.swingx.JXHyperlink();
        jPanel1 = new javax.swing.JPanel();
        hlUferLinks = new org.jdesktop.swingx.JXHyperlink();
        hlUferRechts = new org.jdesktop.swingx.JXHyperlink();
        hlSohle = new org.jdesktop.swingx.JXHyperlink();
        hlUmfeldLinks = new org.jdesktop.swingx.JXHyperlink();
        hlUmfeldRechts = new org.jdesktop.swingx.JXHyperlink();
        jpVorpruefung = new javax.swing.JPanel();
        hlValide = new org.jdesktop.swingx.JXHyperlink();
        hlInvalide = new org.jdesktop.swingx.JXHyperlink();
        jpPruefung = new javax.swing.JPanel();
        lblAbgelehntLab = new javax.swing.JLabel();
        lblAuflagenLab = new javax.swing.JLabel();
        lblAngenommenLab = new javax.swing.JLabel();
        lblUngeprueftLab = new javax.swing.JLabel();
        hlAbgelehnt = new org.jdesktop.swingx.JXHyperlink();
        hlAuflagen = new org.jdesktop.swingx.JXHyperlink();
        hlAngenommen = new org.jdesktop.swingx.JXHyperlink();
        hlUngeprueft = new org.jdesktop.swingx.JXHyperlink();
        jSeparator2 = new javax.swing.JSeparator();
        jpTable = new javax.swing.JPanel();
        lblTableHeader = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new org.jdesktop.swingx.JXTable();
        jSeparator3 = new javax.swing.JSeparator();
        jpEntscheidung = new javax.swing.JPanel();
        tbAngenommen = new javax.swing.JToggleButton();
        tgAbgelehnt = new javax.swing.JToggleButton();
        lblAuflagen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAuflagen = new javax.swing.JTextArea();
        jSeparator4 = new javax.swing.JSeparator();
        txtHint = new javax.swing.JLabel();

        setBorder(null);
        addComponentListener(new java.awt.event.ComponentAdapter() {

                @Override
                public void componentResized(final java.awt.event.ComponentEvent evt) {
                    formComponentResized(evt);
                }
            });
        setLayout(new java.awt.CardLayout());

        jpEmpty.setBorder(null);
        jpEmpty.setLayout(new java.awt.GridBagLayout());

        lblClosed.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.lblClosed.text")); // NOI18N
        jpEmpty.add(lblClosed, new java.awt.GridBagConstraints());

        add(jpEmpty, "closed");

        jpMain.setBorder(null);
        jpMain.setLayout(new java.awt.GridBagLayout());

        jsMain.setBorder(null);
        jsMain.setOpaque(false);

        jPanel2.setBorder(null);
        jPanel2.setMinimumSize(new java.awt.Dimension(400, 500));
        jPanel2.setPreferredSize(new java.awt.Dimension(450, 573));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        panTitle.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panTitle.add(lblTitle, gridBagConstraints);

        lblStatusPrefix.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblStatusPrefix.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        panTitle.add(lblStatusPrefix, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        panTitle.add(lblStatus, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        jPanel2.add(panTitle, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel2.add(jSeparator1, gridBagConstraints);

        jpStat.setLayout(new java.awt.GridBagLayout());

        lblStat.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.lblStat.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jpStat.add(lblStat, gridBagConstraints);

        lblVorpruefung.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblVorpruefung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jpStat.add(lblVorpruefung, gridBagConstraints);

        lblStat2.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.lblStat2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jpStat.add(lblStat2, gridBagConstraints);

        jpStatistik.setLayout(new java.awt.GridBagLayout());

        hlGesMassn.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.hlGesMassn.text")); // NOI18N
        hlGesMassn.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlGesMassnActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jpStatistik.add(hlGesMassn, gridBagConstraints);

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 22));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        hlUferLinks.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.hlUferLinks.text")); // NOI18N
        hlUferLinks.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlUferLinksActionPerformed(evt);
                }
            });
        jPanel1.add(hlUferLinks);

        hlUferRechts.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.hlUferRechts.text"));        // NOI18N
        hlUferRechts.setToolTipText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.hlUferRechts.toolTipText")); // NOI18N
        hlUferRechts.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlUferRechtsActionPerformed(evt);
                }
            });
        jPanel1.add(hlUferRechts);

        hlSohle.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.hlSohle.text")); // NOI18N
        hlSohle.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlSohleActionPerformed(evt);
                }
            });
        jPanel1.add(hlSohle);

        hlUmfeldLinks.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.hlUmfeldLinks.text")); // NOI18N
        hlUmfeldLinks.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlUmfeldLinksActionPerformed(evt);
                }
            });
        jPanel1.add(hlUmfeldLinks);

        hlUmfeldRechts.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.hlUmfeldRechts.text")); // NOI18N
        hlUmfeldRechts.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlUmfeldRechtsActionPerformed(evt);
                }
            });
        jPanel1.add(hlUmfeldRechts);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jpStatistik.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 0);
        jpStat.add(jpStatistik, gridBagConstraints);

        jpVorpruefung.setLayout(new java.awt.GridBagLayout());

        hlValide.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.hlValide.text")); // NOI18N
        hlValide.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlValideActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jpVorpruefung.add(hlValide, gridBagConstraints);

        hlInvalide.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.hlInvalide.text")); // NOI18N
        hlInvalide.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlInvalideActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jpVorpruefung.add(hlInvalide, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 5, 0);
        jpStat.add(jpVorpruefung, gridBagConstraints);

        jpPruefung.setLayout(new java.awt.GridBagLayout());

        lblAbgelehntLab.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAbgelehntLab.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jpPruefung.add(lblAbgelehntLab, gridBagConstraints);

        lblAuflagenLab.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAuflagenLab.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jpPruefung.add(lblAuflagenLab, gridBagConstraints);

        lblAngenommenLab.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAngenommenLab.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jpPruefung.add(lblAngenommenLab, gridBagConstraints);

        lblUngeprueftLab.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblUngeprueftLab.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jpPruefung.add(lblUngeprueftLab, gridBagConstraints);

        hlAbgelehnt.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.hlAbgelehnt.text")); // NOI18N
        hlAbgelehnt.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlAbgelehntActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jpPruefung.add(hlAbgelehnt, gridBagConstraints);

        hlAuflagen.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.hlAuflagen.text")); // NOI18N
        hlAuflagen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlAuflagenActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jpPruefung.add(hlAuflagen, gridBagConstraints);

        hlAngenommen.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.hlAngenommen.text")); // NOI18N
        hlAngenommen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlAngenommenActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jpPruefung.add(hlAngenommen, gridBagConstraints);

        hlUngeprueft.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.hlUngeprueft.text")); // NOI18N
        hlUngeprueft.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    hlUngeprueftActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jpPruefung.add(hlUngeprueft, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        jpStat.add(jpPruefung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(jpStat, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel2.add(jSeparator2, gridBagConstraints);

        jpTable.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jpTable.add(lblTableHeader, gridBagConstraints);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {}));
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {

                @Override
                public void focusLost(final java.awt.event.FocusEvent evt) {
                    jTable1FocusLost(evt);
                }
            });
        jScrollPane2.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jpTable.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jpTable, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel2.add(jSeparator3, gridBagConstraints);

        jpEntscheidung.setLayout(new java.awt.GridBagLayout());

        tbAngenommen.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.tbAngenommen.text")); // NOI18N
        tbAngenommen.setMaximumSize(new java.awt.Dimension(88, 30));
        tbAngenommen.setMinimumSize(new java.awt.Dimension(88, 30));
        tbAngenommen.setPreferredSize(new java.awt.Dimension(88, 30));
        tbAngenommen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    tbAngenommenActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jpEntscheidung.add(tbAngenommen, gridBagConstraints);

        tgAbgelehnt.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.tgAbgelehnt.text")); // NOI18N
        tgAbgelehnt.setMaximumSize(new java.awt.Dimension(88, 30));
        tgAbgelehnt.setMinimumSize(new java.awt.Dimension(88, 30));
        tgAbgelehnt.setPreferredSize(new java.awt.Dimension(88, 30));
        tgAbgelehnt.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    tgAbgelehntActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 0, 0);
        jpEntscheidung.add(tgAbgelehnt, gridBagConstraints);

        lblAuflagen.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAuflagen.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 0);
        jpEntscheidung.add(lblAuflagen, gridBagConstraints);

        textAuflagen.setColumns(20);
        textAuflagen.setRows(5);
        textAuflagen.addFocusListener(new java.awt.event.FocusAdapter() {

                @Override
                public void focusLost(final java.awt.event.FocusEvent evt) {
                    textAuflagenFocusLost(evt);
                }
            });
        jScrollPane1.setViewportView(textAuflagen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jpEntscheidung.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.2;
        jPanel2.add(jpEntscheidung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jPanel2.add(jSeparator4, gridBagConstraints);

        txtHint.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.txtHint.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(txtHint, gridBagConstraints);

        jsMain.setViewportView(jPanel2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jpMain.add(jsMain, gridBagConstraints);

        add(jpMain, "open");
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void formComponentResized(final java.awt.event.ComponentEvent evt) { //GEN-FIRST:event_formComponentResized
    }                                                                            //GEN-LAST:event_formComponentResized

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tbAngenommenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tbAngenommenActionPerformed
        examinationManager.setAccept(tbAngenommen.isSelected());
    }                                                                                //GEN-LAST:event_tbAngenommenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlGesMassnActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlGesMassnActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(null);
        lblTableHeader.setText(TABLE_HEADER_ALL);
    }                                                                              //GEN-LAST:event_hlGesMassnActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlUferLinksActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlUferLinksActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(new KompartimentFilter(
                GupPlanungsabschnittEditor.GUP_UFER_LINKS));
        lblTableHeader.setText(TABLE_HEADER_UFER_LINKS);
    }                                                                               //GEN-LAST:event_hlUferLinksActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlUferRechtsActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlUferRechtsActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(new KompartimentFilter(
                GupPlanungsabschnittEditor.GUP_UFER_RECHTS));
        lblTableHeader.setText(TABLE_HEADER_UFER_RECHTS);
    }                                                                                //GEN-LAST:event_hlUferRechtsActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlSohleActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlSohleActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(new KompartimentFilter(GupPlanungsabschnittEditor.GUP_SOHLE));
        lblTableHeader.setText(TABLE_HEADER_SOHLE);
    }                                                                           //GEN-LAST:event_hlSohleActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlUmfeldLinksActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlUmfeldLinksActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(new KompartimentFilter(
                GupPlanungsabschnittEditor.GUP_UMFELD_LINKS));
        lblTableHeader.setText(TABLE_HEADER_UMFELD_LINKS);
    }                                                                                 //GEN-LAST:event_hlUmfeldLinksActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlUmfeldRechtsActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlUmfeldRechtsActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(new KompartimentFilter(
                GupPlanungsabschnittEditor.GUP_UMFELD_RECHTS));
        lblTableHeader.setText(TABLE_HEADER_UMFELD_RECHTS);
    }                                                                                  //GEN-LAST:event_hlUmfeldRechtsActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlValideActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlValideActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(new ValidatorFilter(
                UnterhaltungsmassnahmeValidator.ValidationResult.ok));
        lblTableHeader.setText(TABLE_HEADER_VALIDE);
    }                                                                            //GEN-LAST:event_hlValideActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlInvalideActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlInvalideActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(new ValidatorFilter(
                UnterhaltungsmassnahmeValidator.ValidationResult.error));
        lblTableHeader.setText(TABLE_HEADER_INVALIDE);
    }                                                                              //GEN-LAST:event_hlInvalideActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlAbgelehntActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlAbgelehntActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(new TableFilter() {

                @Override
                public boolean check(final CidsBean bean) {
                    final Boolean changed = (Boolean)bean.getProperty("geaendert_nach_pruefung");
                    final Boolean abg = (Boolean)bean.getProperty("abgelehnt");

                    if ((changed != null) && changed.booleanValue()) {
                        return false;
                    }

                    return (abg != null) && abg.booleanValue();
                }
            });
        lblTableHeader.setText(TABLE_HEADER_ABGELEHNT);
    } //GEN-LAST:event_hlAbgelehntActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlAuflagenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlAuflagenActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(new TableFilter() {

                @Override
                public boolean check(final CidsBean bean) {
                    final Boolean changed = (Boolean)bean.getProperty("geaendert_nach_pruefung");
                    final Boolean ang = (Boolean)bean.getProperty("angenommen");
                    final String auflagen = (String)bean.getProperty("auflagen");

                    if ((changed != null) && changed.booleanValue()) {
                        return false;
                    }

                    return (ang != null) && ang.booleanValue() && (auflagen != null) && !auflagen.equals("");
                }
            });
        lblTableHeader.setText(TABLE_HEADER_AUFLAGEN);
    } //GEN-LAST:event_hlAuflagenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlAngenommenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlAngenommenActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(new TableFilter() {

                @Override
                public boolean check(final CidsBean bean) {
                    final Boolean changed = (Boolean)bean.getProperty("geaendert_nach_pruefung");
                    final Boolean ang = (Boolean)bean.getProperty("angenommen");
                    final String auflagen = (String)bean.getProperty("auflagen");

                    if ((changed != null) && changed.booleanValue()) {
                        return false;
                    }

                    return (ang != null) && ang.booleanValue() && ((auflagen == null) || auflagen.equals(""));
                }
            });
        lblTableHeader.setText(TABLE_HEADER_ANGENOMMEN);
    } //GEN-LAST:event_hlAngenommenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlUngeprueftActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlUngeprueftActionPerformed
        ((CustomTableModel)jTable1.getModel()).setFilter(new TableFilter() {

                @Override
                public boolean check(final CidsBean bean) {
                    final Boolean changed = (Boolean)bean.getProperty("geaendert_nach_pruefung");
                    final Boolean ang = (Boolean)bean.getProperty("angenommen");
                    final Boolean abg = (Boolean)bean.getProperty("abgelehnt");

                    if ((changed != null) && changed.booleanValue()) {
                        return true;
                    }

                    return ((ang == null) || !ang.booleanValue()) && ((abg == null) || !abg.booleanValue());
                }
            });
        lblTableHeader.setText(TABLE_HEADER_UNGEPRUEFT);
    } //GEN-LAST:event_hlUngeprueftActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tgAbgelehntActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tgAbgelehntActionPerformed
        examinationManager.setDecline(tgAbgelehnt.isSelected());
    }                                                                               //GEN-LAST:event_tgAbgelehntActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jTable1FocusLost(final java.awt.event.FocusEvent evt) { //GEN-FIRST:event_jTable1FocusLost
    }                                                                    //GEN-LAST:event_jTable1FocusLost

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void textAuflagenFocusLost(final java.awt.event.FocusEvent evt) { //GEN-FIRST:event_textAuflagenFocusLost
        examinationManager.setCondition(textAuflagen.getText());
    }                                                                         //GEN-LAST:event_textAuflagenFocusLost

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CheckAssistent getInstance() {
        return instance;
    }

    /**
     * DOCUMENT ME!
     *
     * @return    DOCUMENT ME!
     *
     * @Override  DOCUMENT ME!
     */
    @Override
    public JComponent getSearchWindowComponent() {
        return this;
    }
    /**
     * DOCUMENT ME!
     *
     * @return    DOCUMENT ME!
     *
     * @Override  DOCUMENT ME!
     */
    @Override
    public MetaObjectNodeServerSearch getServerSearch() {
        return null;
    }
    /**
     * DOCUMENT ME!
     *
     * @return    DOCUMENT ME!
     *
     * @Override  DOCUMENT ME!
     */
    @Override
    public ImageIcon getIcon() {
        final MetaClass los = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "gup_los");
        return new javax.swing.ImageIcon(los.getIconData());
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CidsBean getCidsBean() {
        return planungsabschnitt;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        planungsabschnitt = cidsBean;

        if (cidsBean != null) {
            switchToForm("open");
            readOnly = isReadOnly();

            refreshTitle();
            refreshStats(true);

            textAuflagen.setEnabled(readOnly);
            tgAbgelehnt.setEnabled(readOnly);
            tbAngenommen.setEnabled(readOnly);
            lblTableHeader.setText(TABLE_HEADER_ALL);
        }
    }

    /**
     * It is only for user with the action attribute pruefer in the status pruefung allowed to make changes.
     *
     * @return  DOCUMENT ME!
     */
    private boolean isReadOnly() {
        final Boolean isClosed = (Boolean)planungsabschnitt.getProperty("geschlossen");

        if ((isClosed != null) && isClosed.booleanValue()) {
            return true;
        }

        Integer statId = (Integer)planungsabschnitt.getProperty("gup.status.id");

        if (statId == null) {
            statId = GupGupEditor.ID_PLANUNG;
        }

        if (GupGupEditor.hasActionPruefer() && (statId == GupGupEditor.ID_PRUEFUNG)) {
            return false;
        }

        return true;
    }

    /**
     * Refreshes the title label.
     */
    private void refreshTitle() {
        String name = (String)planungsabschnitt.getProperty("name");
        final Double von = (Double)planungsabschnitt.getProperty("linie.von.wert");
        final Double bis = (Double)planungsabschnitt.getProperty("linie.bis.wert");
        String stat = null;
        String status = (String)planungsabschnitt.getProperty("gup.status.name");

        if (name == null) {
            name = "unbenannt";
        }

        if ((von != null) && (bis != null)) {
            stat = "[" + von + ", " + bis + "]";
        } else {
            stat = "[]";
        }

        lblTitle.setText(name + stat);

        if (status == null) {
            status = "Planung/Abstimmung";
        }

        lblStatus.setText(status);
    }

    /**
     * DOCUMENT ME!a.
     *
     * @param  id  DOCUMENT ME!
     */
    private void switchToForm(final String id) {
        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    ((CardLayout)getLayout()).show(CheckAssistent.this, id);
                }
            };
        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            EventQueue.invokeLater(r);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  firstInvocation  DOCUMENT ME!
     */
    private void refreshStats(final boolean firstInvocation) {
        // todo:  Beim Aufruf dieser Methode muss sichergestellt sein, dass schon der aktuelle Validator erstellt wurde
        new Thread(new javax.swing.SwingWorker<UnterhaltungsmassnahmeValidator, Void>() {

                @Override
                protected UnterhaltungsmassnahmeValidator doInBackground() throws Exception {
                    final UnterhaltungsmassnahmeValidator validator = GupPlanungsabschnittEditor.getSearchValidator();

                    while (!validator.isReady()) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            // nothing to do
                        }
                    }

                    return validator;
                }

                @Override
                protected void done() {
                    try {
                        validator = get();
                        final List<CidsBean> massnBeans = planungsabschnitt.getBeanCollectionProperty("massnahmen");
                        int uferLinks = 0;
                        int uferRechts = 0;
                        int sohle = 0;
                        int umfeldLinks = 0;
                        int umfeldRechts = 0;
                        int valide = 0;
                        int invalide = 0;
                        int abgelehnt = 0;
                        int auflagen = 0;
                        int angenommen = 0;
                        int ungeprueft = 0;

                        for (final CidsBean bean : massnBeans) {
                            // Bestimme Positionen
                            final Integer wo = (Integer)bean.getProperty("wo.id");

                            switch (wo) {
                                case GupPlanungsabschnittEditor.GUP_SOHLE: {
                                    ++sohle;
                                    break;
                                }
                                case GupPlanungsabschnittEditor.GUP_UFER_LINKS: {
                                    ++uferLinks;
                                    break;
                                }
                                case GupPlanungsabschnittEditor.GUP_UFER_RECHTS: {
                                    ++uferRechts;
                                    break;
                                }
                                case GupPlanungsabschnittEditor.GUP_UMFELD_LINKS: {
                                    ++umfeldLinks;
                                    break;
                                }
                                case GupPlanungsabschnittEditor.GUP_UMFELD_RECHTS: {
                                    ++umfeldRechts;
                                    break;
                                }
                            }

                            // Bestimme Gueltigkeit
                            final UnterhaltungsmassnahmeValidator.ValidationResult result = validator.validate(
                                    bean,
                                    new ArrayList());

                            if (result.equals(UnterhaltungsmassnahmeValidator.ValidationResult.ok)) {
                                ++valide;
                            } else if (result.equals(UnterhaltungsmassnahmeValidator.ValidationResult.error)) {
                                ++invalide;
                            }

                            // Bestimme Entscheidungen
                            final Decision decision = getCheckResult(bean);

                            switch (decision) {
                                case accepted: {
                                    ++angenommen;
                                    break;
                                }
                                case onCondition: {
                                    ++auflagen;
                                    break;
                                }
                                case declined: {
                                    ++abgelehnt;
                                    break;
                                }
                                case notChecked: {
                                    ++ungeprueft;
                                    break;
                                }
                            }
                        }

                        hlGesMassn.setText(massnahmentext(massnBeans.size()));
                        hlUferLinks.setText(uferLinks + " Ufer links,");
                        hlUferRechts.setText(uferRechts + " Ufer rechts,");
                        hlSohle.setText(sohle + " Sohle,");
                        hlUmfeldLinks.setText(umfeldLinks + " Umfeld links,");
                        hlUmfeldRechts.setText(umfeldRechts + " Umfeld rechts");

                        hlValide.setText(valide + " valide,");
                        hlInvalide.setText(invalide + " invalide");

                        hlAbgelehnt.setText(massnahmentext(abgelehnt));
                        hlAngenommen.setText(massnahmentext(angenommen));
                        hlAuflagen.setText(massnahmentext(auflagen));
                        hlUngeprueft.setText(massnahmentext(ungeprueft));

                        if (firstInvocation) {
                            final CustomTableModel model = new CustomTableModel(massnBeans);
//                            final RowSorter sorter = jTable1.getRowSorter();
//                            jTable1.setRowSorter(new CustomTableRowSorter(model));
//                            ((TableRowSorter)jTable1.getRowSorter()).setModel(model);
                            jTable1.setModel(model);
                            jTable1.getSelectionModel().addListSelectionListener(CheckAssistent.this);
                            setTableSize();
                            ((JXTable)jTable1).getColumnExt(4).setComparator(new JCheckboxComparator());
                            ((JXTable)jTable1).getColumnExt(5).setComparator(new JCheckboxComparator());
                            ((JXTable)jTable1).getColumnExt(1).setComparator(new StationCellComparator());
                            tbAngenommen.setSelected(false);
                            tgAbgelehnt.setSelected(false);
                        }
                    } catch (Exception e) {
                        LOG.error("Error while calculating the statistics.", e);
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
    private boolean isBeanAccepted(final CidsBean bean) {
        final Decision d = getCheckResult(bean);

        return d.equals(Decision.accepted) || d.equals(Decision.onCondition);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isBeanDeclined(final CidsBean bean) {
        return getCheckResult(bean).equals(Decision.declined);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean hasBeanConditions(final CidsBean bean) {
        final String aufl = (String)bean.getProperty("auflagen");

        return ((aufl != null) && !aufl.equals(""));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Decision getCheckResult(final CidsBean bean) {
        final Boolean ang = (Boolean)bean.getProperty("angenommen");
        final Boolean abg = (Boolean)bean.getProperty("abgelehnt");
        final Boolean ung = (Boolean)bean.getProperty("geaendert_nach_pruefung");

        if ((ung != null) && ung.booleanValue()) {
            return Decision.notChecked;
        } else if ((ang != null) && ang.booleanValue()) {
            final String aufl = (String)bean.getProperty("auflagen");

            if ((aufl != null) && !aufl.equals("")) {
                return Decision.onCondition;
            } else {
                return Decision.accepted;
            }
        } else if ((abg != null) && abg.booleanValue()) {
            return Decision.declined;
        } else {
            return Decision.notChecked;
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setTableSize() {
        final TableColumnModel columnModel = jTable1.getColumnModel();
        final FontMetrics fmetrics = jTable1.getFontMetrics(jTable1.getFont());
        final TableModel model = jTable1.getModel();
        final int columnCount = model.getColumnCount();
        int totalSize = 0;

        for (int i = 0; i < columnCount; ++i) {
            final int size = (int)fmetrics.getStringBounds(model.getColumnName(i), jTable1.getGraphics()).getWidth();
            totalSize += size;
            columnModel.getColumn(i).setMinWidth(size + 30);
        }

        jTable1.setMinimumSize(new Dimension(totalSize + 20, 50));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   count  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String massnahmentext(final int count) {
        if (count == 1) {
            return count + " Maßnahme";
        } else {
            return count + " Maßnahmen";
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void dispose() {
        switchToForm("closed");
    }

    /**
     * DOCUMENT ME!
     *
     * @param  l  DOCUMENT ME!
     */
    public void addListener(final CheckAssistentListener l) {
        listener.add(l);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  l  DOCUMENT ME!
     */
    public void removeListener(final CheckAssistentListener l) {
        listener.remove(l);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean  DOCUMENT ME!
     */
    public void setSelection(final CidsBean bean) {
        if (ignoreSetSelection) {
            // the selection should be ignored, because the new selection has its origin in this component
            // and will already be handled.
            return;
        }
        final CustomTableModel model = (CustomTableModel)jTable1.getModel();

        int row = model.getRow(bean);

        if (row != -1) {
            row = jTable1.convertRowIndexToView(row);
        }

        if (row != -1) {
            jTable1.getSelectionModel().setSelectionInterval(row, row);
        }

        final List<CidsBean> beanList = new ArrayList<CidsBean>();
        beanList.add(bean);
        examinationManager.setBeans(beanList);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean  DOCUMENT ME!
     */
    private void fireSelectionChanged(final CidsBean bean) {
        ignoreSetSelection = true;
        for (final CheckAssistentListener l : listener) {
            l.onSelectionChange(bean);
        }
        ignoreSetSelection = false;
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            final int row = jTable1.getSelectedRow();
            final CustomTableModel model = (CustomTableModel)jTable1.getModel();

            if (row != -1) {
                final int index = jTable1.convertRowIndexToModel(row);
                fireSelectionChanged(model.getBean(index));
            }

            final int[] rows = jTable1.getSelectedRows();
            final List<CidsBean> beanList = new ArrayList<CidsBean>(rows.length);

            for (final int tmp : rows) {
                final int index = jTable1.convertRowIndexToModel(tmp);
                beanList.add(model.getBean(index));
            }
            examinationManager.setBeans(beanList);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refresh() {
        jTable1.repaint();
        refreshStats(false);
    }

    //~ Inner Interfaces -------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private interface TableFilter {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   bean  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        boolean check(CidsBean bean);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomTableModel implements TableModel {

        //~ Instance fields ----------------------------------------------------

        String[] columns = {
                "Kompartiment",
                "Stationierung",
                "Maßnahme",
                "valide",
                "ang.",
                "abg.",
                "..."
            };
        List<CidsBean> beanList;
        List<CidsBean> allBeans;
        List<TableModelListener> listener = new ArrayList<TableModelListener>();
        TableFilter filter;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomTableModel object.
         *
         * @param  beanList  DOCUMENT ME!
         */
        public CustomTableModel(final List<CidsBean> beanList) {
            this.allBeans = beanList;
            filterBeans();
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            return beanList.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            return columns[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            if ((columnIndex == 4) || (columnIndex == 5)) {
                return JCheckBox.class;
            } else if (columnIndex == 6) {
                return ImageIcon.class;
            } else if (columnIndex == 1) {
                return StationCell.class;
            } else {
                return String.class;
            }
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            if (readOnly) {
                return false;
            }

            if ((columnIndex == 4) || (columnIndex == 5)) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            final CidsBean bean = beanList.get(rowIndex);
            Object result = "";

            switch (columnIndex) {
                case 0: {
                    final String s = (String)bean.getProperty("wo.ort");
                    if (s != null) {
                        result = s;
                    }

                    break;
                }

                case 1: {
                    final Double von = (Double)bean.getProperty("linie.von.wert");
                    final Double bis = (Double)bean.getProperty("linie.bis.wert");

                    if ((von != null) && (bis != null)) {
//                        result = von.intValue() + "-" + bis.intValue();
                        result = new StationCell(von.intValue(), bis.intValue());
                    }
                    break;
                }
                case 2: {
                    final String m = (String)bean.getProperty("massnahme.massnahmen_id");

                    if (m != null) {
                        result = m;
                    }
                    break;
                }
                case 3: {
                    final UnterhaltungsmassnahmeValidator.ValidationResult res = validator.validate(
                            bean,
                            new ArrayList<String>());

                    if (res.equals(UnterhaltungsmassnahmeValidator.ValidationResult.ok)) {
                        result = "valide";
                    } else if (res.equals(UnterhaltungsmassnahmeValidator.ValidationResult.error)) {
                        result = "invalide";
                    }
                    break;
                }
                case 4: {
                    final boolean ang = isBeanAccepted(bean);

                    final JCheckBox box = new JCheckBox();
                    box.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                try {
                                    bean.setProperty("angenommen", box.isSelected());
                                    if (box.isSelected()) {
                                        bean.setProperty("abgelehnt", !box.isSelected());
                                    }
                                    examinationManager.refreshGUI();
                                } catch (final Exception ex) {
                                    LOG.error("Cannot set property.", ex);
                                }
                            }
                        });
                    box.setSelected(ang);
                    box.setOpaque(false);
                    box.setContentAreaFilled(false);

                    result = box;
                    break;
                }
                case 5: {
                    final boolean abg = isBeanDeclined(bean);

                    final JCheckBox box = new JCheckBox();
                    box.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                try {
                                    bean.setProperty("abgelehnt", box.isSelected());
                                    if (box.isSelected()) {
                                        bean.setProperty("angenommen", !box.isSelected());
                                    }
                                    examinationManager.refreshGUI();
                                } catch (final Exception ex) {
                                    LOG.error("Cannot set property.", ex);
                                }
                            }
                        });

                    box.setSelected(abg);
                    box.setOpaque(false);
                    box.setContentAreaFilled(false);

                    result = box;
                    break;
                }
                case 6: {
                    if (hasBeanConditions(bean)) {
                        result = new ImageIcon(getClass().getResource(DOC_ICON));
                    } else {
                        return null;
                    }

                    break;
                }
            }

            return result;
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            throw new UnsupportedOperationException("Not supported yet.");
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
         *
         * @param   row  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public CidsBean getBean(final int row) {
            return beanList.get(row);
        }

        /**
         * DOCUMENT ME!
         *
         * @param   bean  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getRow(final CidsBean bean) {
            for (int i = 0; i < beanList.size(); ++i) {
                if (beanList.get(i).getProperty("id").equals(bean.getProperty("id"))) {
                    return i;
                }
            }

            return -1;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  filter  DOCUMENT ME!
         */
        public void setFilter(final TableFilter filter) {
            this.filter = filter;
            filterBeans();
        }

        /**
         * DOCUMENT ME!
         */
        private void filterBeans() {
            if (filter == null) {
                beanList = allBeans;
            } else {
                beanList = new ArrayList<CidsBean>();

                for (final CidsBean tmp : allBeans) {
                    if (filter.check(tmp)) {
                        beanList.add(tmp);
                    }
                }
            }

            fireContentsChanged();
        }

        /**
         * DOCUMENT ME!
         */
        private void fireContentsChanged() {
            final TableModelEvent e = new TableModelEvent(this);

            for (final TableModelListener tmp : listener) {
                tmp.tableChanged(e);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class CheckBoxRenderer extends DefaultTableCellRenderer {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getTableCellRendererComponent(final JTable table,
                final Object value,
                final boolean isSelected,
                final boolean hasFocus,
                final int row,
                final int column) {
            final Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            final JPanel p = new JPanel();
            p.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            p.add((JCheckBox)value);

            p.setBackground(comp.getBackground());
            p.setForeground(comp.getForeground());
            p.setBorder(p.getBorder());
            p.setFont(comp.getFont());

            return p;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public class StationCellRenderer extends DefaultTableCellRenderer {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getTableCellRendererComponent(final JTable table,
                final Object value,
                final boolean isSelected,
                final boolean hasFocus,
                final int row,
                final int column) {
            final Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            Color bg = comp.getBackground();
            if (bg.getRGB() == -1) {
                bg = Color.WHITE;
            }
            final Color fg = comp.getForeground();
            final Font f = comp.getFont();

            ((StationCell)value).setBackground(bg);
            ((StationCell)value).setForeground(fg);
            ((StationCell)value).setFont(f);

            return (StationCell)value;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class JCheckboxComparator implements Comparator<Component> {

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final Component o1, final Component o2) {
            JCheckBox box1 = null;
            JCheckBox box2 = null;

            if (o1 instanceof JCheckBox) {
                box1 = (JCheckBox)o1;
            }

            if (o2 instanceof JCheckBox) {
                box2 = (JCheckBox)o2;
            }

            if ((box1 != null) && (box2 != null)) {
                final int v1 = (box1.isSelected() ? 1 : 0);
                final int v2 = (box2.isSelected() ? 1 : 0);

                return v1 - v2;
            } else {
                return 0;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class StationCellComparator implements Comparator<Component> {

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final Component o1, final Component o2) {
            StationCell box1 = null;
            StationCell box2 = null;

            if (o1 instanceof StationCell) {
                box1 = (StationCell)o1;
            }

            if (o2 instanceof StationCell) {
                box2 = (StationCell)o2;
            }

            if ((box1 != null) && (box2 != null)) {
                final int v1 = box1.getVon();
                final int v2 = box2.getVon();

                return v1 - v2;
            } else {
                return 0;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class KompartimentFilter implements TableFilter {

        //~ Instance fields ----------------------------------------------------

        int kompartiment;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new KompartimentFilter object.
         *
         * @param  kompartiment  DOCUMENT ME!
         */
        public KompartimentFilter(final int kompartiment) {
            this.kompartiment = kompartiment;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public boolean check(final CidsBean bean) {
            final Integer ko = (Integer)bean.getProperty("wo.id");

            return (ko != null) && ko.equals(kompartiment);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class ValidatorFilter implements TableFilter {

        //~ Instance fields ----------------------------------------------------

        UnterhaltungsmassnahmeValidator.ValidationResult validationResult;
        List<String> dummyList = new ArrayList();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ValidatorFilter object.
         *
         * @param  validationResult  DOCUMENT ME!
         */
        public ValidatorFilter(final UnterhaltungsmassnahmeValidator.ValidationResult validationResult) {
            this.validationResult = validationResult;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public boolean check(final CidsBean bean) {
            return validator.validate(bean, dummyList).equals(validationResult);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomTableRowSorter extends TableRowSorter<CustomTableModel> {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomTableRowSorter object.
         */
        public CustomTableRowSorter() {
        }

        /**
         * Creates a new CustomTableRowSorter object.
         *
         * @param  model  DOCUMENT ME!
         */
        public CustomTableRowSorter(final CustomTableModel model) {
            super(model);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void sort() {
            final List<? extends SortKey> keys = getSortKeys();
            super.sort();
        }

        @Override
        public void toggleSortOrder(final int column) {
            final List<? extends SortKey> keys = getSortKeys();
            super.toggleSortOrder(column);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class ExaminationManager {

        //~ Instance fields ----------------------------------------------------

        private final String DIFFERENT_CONDITIONS = "<verschiedene Werte>";
        private List<CidsBean> beans = new ArrayList<CidsBean>();
        private boolean sameAcceptedStatus = true;
        private boolean sameDeclinedStatus = true;
        private boolean sameConditions = true;

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param  beans  DOCUMENT ME!
         */
        public void setBeans(final List<CidsBean> beans) {
            this.beans = beans;

            refreshGUI();
        }

        /**
         * DOCUMENT ME!
         */
        private void refreshGUI() {
            boolean accepted = true;
            boolean declined = true;
            sameAcceptedStatus = true;
            sameDeclinedStatus = true;
            sameConditions = true;
            String conditions = null;

            for (final CidsBean tmp : beans) {
                String tmpAuflagen = (String)tmp.getProperty("auflagen");

                if (tmpAuflagen == null) {
                    tmpAuflagen = "";
                }

                if (conditions == null) {
                    conditions = tmpAuflagen;
                } else {
                    if (!conditions.equals(tmpAuflagen)) {
                        sameConditions = false;
                        conditions = DIFFERENT_CONDITIONS;
                    }
                }

                if (!isBeanAccepted(tmp)) {
                    accepted = false;
                } else {
                    sameAcceptedStatus = false;
                }

                if (!isBeanDeclined(tmp)) {
                    declined = false;
                } else {
                    sameDeclinedStatus = false;
                }
            }
            // if accepted = false and sameAcceptedStatus = false, the beans have different values

            if (beans.size() > 0) {
                tbAngenommen.setSelected(accepted);
                tgAbgelehnt.setSelected(declined);
                textAuflagen.setText(conditions);
            } else {
                tbAngenommen.setSelected(false);
                tgAbgelehnt.setSelected(false);
                textAuflagen.setText("");
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param  newValue  DOCUMENT ME!
         */
        public void setAccept(final boolean newValue) {
            for (final CidsBean bean : beans) {
                try {
                    bean.setProperty("angenommen", newValue);
                    if (newValue) {
                        bean.setProperty("abgelehnt", !newValue);
                        bean.setProperty("geaendert_nach_pruefung", Boolean.FALSE);
                    }
                } catch (Exception e) {
                    LOG.error("Cannot set property 'angenommen'", e);
                }
            }

            refreshGUI();
            refresh();
            lblTableHeader.setText(TABLE_HEADER_GEMISCHT);
        }

        /**
         * DOCUMENT ME!
         *
         * @param  newValue  DOCUMENT ME!
         */
        public void setDecline(final boolean newValue) {
            for (final CidsBean bean : beans) {
                try {
                    bean.setProperty("abgelehnt", newValue);
                    if (newValue) {
                        bean.setProperty("angenommen", !newValue);
                        bean.setProperty("geaendert_nach_pruefung", Boolean.FALSE);
                    }
                } catch (Exception e) {
                    LOG.error("Cannot set property 'angenommen'", e);
                }
            }
            refreshGUI();
            refresh();
            lblTableHeader.setText(TABLE_HEADER_GEMISCHT);
        }

        /**
         * DOCUMENT ME!
         *
         * @param  newValue  DOCUMENT ME!
         */
        public void setCondition(String newValue) {
            for (final CidsBean bean : beans) {
                try {
                    if (newValue == null) {
                        newValue = "";
                    }
                    bean.setProperty("auflagen", newValue);
                } catch (Exception e) {
                    LOG.error("Cannot set property 'angenommen'", e);
                }
            }
            refresh();
            lblTableHeader.setText(TABLE_HEADER_GEMISCHT);
        }
    }
}
