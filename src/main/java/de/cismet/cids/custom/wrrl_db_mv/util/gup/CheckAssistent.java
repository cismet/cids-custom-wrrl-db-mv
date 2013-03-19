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

import java.awt.CardLayout;
import java.awt.EventQueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupPlanungsabschnittEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

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
    PropertyChangeListener,
    ListSelectionListener {

    //~ Static fields/initializers ---------------------------------------------

    private static Logger LOG = Logger.getLogger(CheckAssistent.class);
    private static CheckAssistent instance;

    //~ Instance fields --------------------------------------------------------

    List<CheckAssistentListener> listener = new ArrayList<CheckAssistentListener>();

    private CidsBean planungsabschnitt;
    private UnterhaltungsmassnahmeValidator validator;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel jpEmpty;
    private javax.swing.JPanel jpEntscheidung;
    private javax.swing.JPanel jpMain;
    private javax.swing.JPanel jpPruefung;
    private javax.swing.JPanel jpStat;
    private javax.swing.JPanel jpStatistik;
    private javax.swing.JPanel jpTable;
    private javax.swing.JPanel jpVorpruefung;
    private javax.swing.JLabel lblAbgelehnt;
    private javax.swing.JLabel lblAbgelehntLab;
    private javax.swing.JLabel lblAngenommen;
    private javax.swing.JLabel lblAngenommenLab;
    private javax.swing.JLabel lblAuflagen;
    private javax.swing.JLabel lblAuflagenLab;
    private javax.swing.JLabel lblClosed;
    private javax.swing.JLabel lblGesMassn;
    private javax.swing.JLabel lblInvalide;
    private javax.swing.JLabel lblSohle;
    private javax.swing.JLabel lblStat;
    private javax.swing.JLabel lblStat2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUferLinks;
    private javax.swing.JLabel lblUferRechts;
    private javax.swing.JLabel lblUmfeldLinks;
    private javax.swing.JLabel lblUmfeldRechts;
    private javax.swing.JLabel lblUngeprueftLab;
    private javax.swing.JLabel lblValide;
    private javax.swing.JLabel lblVorpruefung;
    private javax.swing.JLabel lblungeprueft;
    private javax.swing.JToggleButton tbAngenommen;
    private javax.swing.JToggleButton tgAbgelehnt;
    private javax.swing.JLabel txtHint;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenHistory.
     */
    public CheckAssistent() {
        initComponents();
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jpEmpty = new javax.swing.JPanel();
        lblClosed = new javax.swing.JLabel();
        jpMain = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jpStat = new javax.swing.JPanel();
        lblStat = new javax.swing.JLabel();
        lblVorpruefung = new javax.swing.JLabel();
        lblStat2 = new javax.swing.JLabel();
        jpStatistik = new javax.swing.JPanel();
        lblGesMassn = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblUferLinks = new javax.swing.JLabel();
        lblUferRechts = new javax.swing.JLabel();
        lblSohle = new javax.swing.JLabel();
        lblUmfeldLinks = new javax.swing.JLabel();
        lblUmfeldRechts = new javax.swing.JLabel();
        jpVorpruefung = new javax.swing.JPanel();
        lblValide = new javax.swing.JLabel();
        lblInvalide = new javax.swing.JLabel();
        jpPruefung = new javax.swing.JPanel();
        lblAbgelehnt = new javax.swing.JLabel();
        lblAbgelehntLab = new javax.swing.JLabel();
        lblAuflagen = new javax.swing.JLabel();
        lblAuflagenLab = new javax.swing.JLabel();
        lblAngenommen = new javax.swing.JLabel();
        lblAngenommenLab = new javax.swing.JLabel();
        lblungeprueft = new javax.swing.JLabel();
        lblUngeprueftLab = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jpTable = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSeparator3 = new javax.swing.JSeparator();
        jpEntscheidung = new javax.swing.JPanel();
        tbAngenommen = new javax.swing.JToggleButton();
        tgAbgelehnt = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        txtHint = new javax.swing.JLabel();

        addComponentListener(new java.awt.event.ComponentAdapter() {

                @Override
                public void componentResized(final java.awt.event.ComponentEvent evt) {
                    formComponentResized(evt);
                }
            });
        setLayout(new java.awt.CardLayout());

        jpEmpty.setLayout(new java.awt.GridBagLayout());

        lblClosed.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.lblClosed.text")); // NOI18N
        jpEmpty.add(lblClosed, new java.awt.GridBagConstraints());

        add(jpEmpty, "closed");

        jpMain.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jpMain.add(lblTitle, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jpMain.add(jSeparator1, gridBagConstraints);

        jpStat.setLayout(new java.awt.GridBagLayout());

        lblStat.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.lblStat.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
        jpStat.add(lblStat, gridBagConstraints);

        lblVorpruefung.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblVorpruefung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        jpStat.add(lblVorpruefung, gridBagConstraints);

        lblStat2.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.lblStat2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
        jpStat.add(lblStat2, gridBagConstraints);

        jpStatistik.setLayout(new java.awt.GridBagLayout());

        lblGesMassn.setForeground(new java.awt.Color(71, 112, 164));
        lblGesMassn.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblGesMassn.text")); // NOI18N
        lblGesMassn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jpStatistik.add(lblGesMassn, gridBagConstraints);

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblUferLinks.setForeground(new java.awt.Color(71, 112, 164));
        lblUferLinks.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblUferLinks.text")); // NOI18N
        lblUferLinks.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(lblUferLinks);

        lblUferRechts.setForeground(new java.awt.Color(71, 112, 164));
        lblUferRechts.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblUferRechts.text")); // NOI18N
        lblUferRechts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(lblUferRechts);

        lblSohle.setForeground(new java.awt.Color(71, 112, 164));
        lblSohle.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.lblSohle.text")); // NOI18N
        lblSohle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(lblSohle);

        lblUmfeldLinks.setForeground(new java.awt.Color(71, 112, 164));
        lblUmfeldLinks.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblUmfeldLinks.text")); // NOI18N
        lblUmfeldLinks.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(lblUmfeldLinks);

        lblUmfeldRechts.setForeground(new java.awt.Color(71, 112, 164));
        lblUmfeldRechts.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblUmfeldRechts.text")); // NOI18N
        lblUmfeldRechts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(lblUmfeldRechts);

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
        gridBagConstraints.insets = new java.awt.Insets(5, 40, 5, 0);
        jpStat.add(jpStatistik, gridBagConstraints);

        jpVorpruefung.setLayout(new java.awt.GridBagLayout());

        lblValide.setForeground(new java.awt.Color(71, 112, 164));
        lblValide.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.lblValide.text")); // NOI18N
        lblValide.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jpVorpruefung.add(lblValide, gridBagConstraints);

        lblInvalide.setForeground(new java.awt.Color(71, 112, 164));
        lblInvalide.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblInvalide.text")); // NOI18N
        lblInvalide.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jpVorpruefung.add(lblInvalide, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 45, 5, 0);
        jpStat.add(jpVorpruefung, gridBagConstraints);

        jpPruefung.setLayout(new java.awt.GridBagLayout());

        lblAbgelehnt.setForeground(new java.awt.Color(71, 112, 164));
        lblAbgelehnt.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAbgelehnt.text")); // NOI18N
        lblAbgelehnt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jpPruefung.add(lblAbgelehnt, gridBagConstraints);

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

        lblAuflagen.setForeground(new java.awt.Color(71, 112, 164));
        lblAuflagen.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAuflagen.text")); // NOI18N
        lblAuflagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jpPruefung.add(lblAuflagen, gridBagConstraints);

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

        lblAngenommen.setForeground(new java.awt.Color(71, 112, 164));
        lblAngenommen.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAngenommen.text")); // NOI18N
        lblAngenommen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jpPruefung.add(lblAngenommen, gridBagConstraints);

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

        lblungeprueft.setForeground(new java.awt.Color(71, 112, 164));
        lblungeprueft.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblungeprueft.text")); // NOI18N
        lblungeprueft.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jpPruefung.add(lblungeprueft, gridBagConstraints);

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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 45, 0, 0);
        jpStat.add(jpPruefung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jpMain.add(jpStat, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jpMain.add(jSeparator2, gridBagConstraints);

        jpTable.setLayout(new java.awt.GridBagLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {}));
        jScrollPane2.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
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
        jpMain.add(jpTable, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jpMain.add(jSeparator3, gridBagConstraints);

        jpEntscheidung.setLayout(new java.awt.GridBagLayout());

        tbAngenommen.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.tbAngenommen.text")); // NOI18N
        tbAngenommen.setMaximumSize(new java.awt.Dimension(88, 30));
        tbAngenommen.setMinimumSize(new java.awt.Dimension(88, 30));
        tbAngenommen.setPreferredSize(new java.awt.Dimension(88, 30));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${planungsabschnitt.angenommen}"),
                tbAngenommen,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${planungsabschnitt.abgelehnt}"),
                tgAbgelehnt,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 0, 0);
        jpEntscheidung.add(tgAbgelehnt, gridBagConstraints);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${planungsabschnitt.auflagen}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jpEntscheidung.add(jScrollPane1, gridBagConstraints);

        jButton1.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.jButton1.text")); // NOI18N
        jButton1.setMaximumSize(new java.awt.Dimension(88, 30));
        jButton1.setMinimumSize(new java.awt.Dimension(88, 30));
        jButton1.setPreferredSize(new java.awt.Dimension(88, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        jpEntscheidung.add(jButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.2;
        jpMain.add(jpEntscheidung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jpMain.add(jSeparator4, gridBagConstraints);

        txtHint.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.txtHint.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jpMain.add(txtHint, gridBagConstraints);

        add(jpMain, "open");

        bindingGroup.bind();
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
        // TODO add your handling code here:
    } //GEN-LAST:event_tbAngenommenActionPerformed

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
        bindingGroup.unbind();
        planungsabschnitt = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            switchToForm("open");
            refreshTitle();
            refreshStats();
        }
    }

    /**
     * Refreshes the title label.
     */
    private void refreshTitle() {
        String name = (String)planungsabschnitt.getProperty("name");
        final Double von = (Double)planungsabschnitt.getProperty("linie.von.wert");
        final Double bis = (Double)planungsabschnitt.getProperty("linie.bis.wert");
        String stat = null;

        if (name == null) {
            name = "unbenannt";
        }

        if ((von != null) && (bis != null)) {
            stat = "[" + von + ", " + bis + "]";
        } else {
            stat = "[]";
        }

        lblTitle.setText(name + stat);
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
     */
    private void refreshStats() {
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
                            final Boolean ang = (Boolean)bean.getProperty("angenommen");
                            final Boolean abg = (Boolean)bean.getProperty("abgelehnt");
                            final Boolean ung = (Boolean)bean.getProperty("geaendert_nach_pruefung");

                            if ((ung != null) && ung.booleanValue()) {
                                ++ungeprueft;
                            } else if ((ang != null) && ang.booleanValue()) {
                                final String aufl = (String)bean.getProperty("auflagen");

                                if ((aufl != null) && !aufl.equals("")) {
                                    ++auflagen;
                                } else {
                                    ++angenommen;
                                }
                            } else if ((abg != null) && abg.booleanValue()) {
                                ++abgelehnt;
                            } else {
                                ++ungeprueft;
                            }
                        }

                        lblGesMassn.setText(massnahmentext(massnBeans.size()));
                        lblUferLinks.setText(uferLinks + " Ufer links,");
                        lblUferRechts.setText(uferRechts + " Ufer rechts,");
                        lblSohle.setText(sohle + " Sohle,");
                        lblUmfeldLinks.setText(umfeldLinks + " Umfeld links,");
                        lblUmfeldRechts.setText(umfeldRechts + " Umfeld rechts");

                        lblValide.setText(valide + " valide,");
                        lblInvalide.setText(invalide + " invalide");

                        lblAbgelehnt.setText(massnahmentext(abgelehnt));
                        lblAngenommen.setText(massnahmentext(angenommen));
                        lblAuflagen.setText(massnahmentext(auflagen));
                        lblungeprueft.setText(massnahmentext(ungeprueft));
                        jTable1.setModel(new CustomTableModel(massnBeans));
                        jTable1.getSelectionModel().addListSelectionListener(CheckAssistent.this);
                    } catch (Exception e) {
                        LOG.error("Error while calculating the statistics.", e);
                    }
                }
            }).start();
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

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if ((evt.getNewValue() != null) && evt.getNewValue().equals(Boolean.TRUE)) {
            if (evt.getPropertyName().equals("angenommen")) {
                final Boolean abgelehnt = (Boolean)planungsabschnitt.getProperty("abgelehnt");

                if ((abgelehnt != null) && abgelehnt.booleanValue()) {
                    try {
                        planungsabschnitt.setProperty("abgelehnt", Boolean.FALSE);
                    } catch (Exception e) {
                        LOG.error("Cannot set property.", e);
                    }
                }
            } else if (evt.getPropertyName().equals("abgelehnt")) {
                final Boolean abgelehnt = (Boolean)planungsabschnitt.getProperty("angenommen");

                if ((abgelehnt != null) && abgelehnt.booleanValue()) {
                    try {
                        planungsabschnitt.setProperty("angenommen", Boolean.FALSE);
                    } catch (Exception e) {
                        LOG.error("Cannot set property.", e);
                    }
                }
            }
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
        final CustomTableModel model = (CustomTableModel)jTable1.getModel();

        final int row = model.getRow(bean);

        if (row != -1) {
            jTable1.getSelectionModel().setSelectionInterval(row, row);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean  DOCUMENT ME!
     */
    private void fireSelectionChanged(final CidsBean bean) {
        for (final CheckAssistentListener l : listener) {
            l.onSelectionChange(bean);
        }
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            final int row = jTable1.getSelectedRow();
            final CustomTableModel model = (CustomTableModel)jTable1.getModel();

            fireSelectionChanged(model.getBean(row));
        }
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
                "Maßnahmenart",
                "valide/invalide",
                "angenommen",
                "abgelehnt",
                "Anhang"
            };
        List<CidsBean> beanList;
        List<TableModelListener> listener = new ArrayList<TableModelListener>();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomTableModel object.
         *
         * @param  beanList  DOCUMENT ME!
         */
        public CustomTableModel(final List<CidsBean> beanList) {
            this.beanList = beanList;
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
            return String.class;
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            final CidsBean bean = beanList.get(rowIndex);
            String result = "";

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
                        result = von.intValue() + "-" + bis.intValue();
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
         */
        private void fireContentsChanged() {
            final TableModelEvent e = new TableModelEvent(this);

            for (final TableModelListener tmp : listener) {
                tmp.tableChanged(e);
            }
        }
    }
}
