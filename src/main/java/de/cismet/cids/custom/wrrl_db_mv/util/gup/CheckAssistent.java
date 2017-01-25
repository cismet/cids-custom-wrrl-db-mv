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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupGupEditor;
import de.cismet.cids.custom.objecteditors.wrrl_db_mv.GupPlanungsabschnittEditor;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.HTMLTools;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanStore;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.MetaObjectNodeServerSearch;

import de.cismet.cids.tools.search.clientstuff.CidsWindowSearch;

import de.cismet.security.WebAccessManager;

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
    ListSelectionListener,
    PropertyChangeListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final String DIV_TEMPLATE_START = "<div class=\"floatleft\"><a href=\"#";

    private static final String SPAN_CLASS_MWHEADLINE_ID = "<span class=\"mw-headline\" id=\"";
    private static final String DIV_CLASSPASTE_START = "<div class=\"pasteStart\">";

    private static final String TABLE_HEADER_ALL = "alle Maßnahmen";
    private static final String TABLE_HEADER_UFER_LINKS = "Ufer links";
    private static final String TABLE_HEADER_UFER_RECHTS = "Ufer rechts";
    private static final String TABLE_HEADER_SOHLE = "Sohle";
    private static final String TABLE_HEADER_UMFELD_LINKS = "Umfeld links";
    private static final String TABLE_HEADER_UMFELD_RECHTS = "Umfeld rechts";
    private static final String TABLE_HEADER_VALIDE = "valide Maßnahmen";
    private static final String TABLE_HEADER_INVALIDE = "invalide Maßnahmen";
    private static final String TABLE_HEADER_ABGELEHNT = "abgelehnte Maßnahmen";
    private static final String TABLE_HEADER_AUFLAGEN = "Maßnahmen mit Auflagen";
    private static final String TABLE_HEADER_ANGENOMMEN = "angenommene Maßnahmen";
    private static final String TABLE_HEADER_UNGEPRUEFT = "ungeprüfte Maßnahmen";
    private static final String TABLE_HEADER_GEMISCHT = "ungefiltert";
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

        accepted, onCondition, declined, notChecked, notRequired, notRequiredOnCondition
    }

    //~ Instance fields --------------------------------------------------------

    List<CheckAssistentListener> listener = new ArrayList<CheckAssistentListener>();

    private CidsBean planungsabschnitt;
    private UnterhaltungsmassnahmeValidator validator;
    private ExaminationManager examinationManager = new ExaminationManager();
    private boolean ignoreSetSelection = false;
    private boolean readOnlyNb = false;
    private boolean readOnlyWb = false;
    private boolean forceReadOnly = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbAllAcceptedNb;
    private javax.swing.JCheckBox cbAllAcceptedWb;
    private javax.swing.JCheckBox cbAllAcceptedWithoutNb;
    private javax.swing.JCheckBox cbAllDeclinedNb;
    private javax.swing.JCheckBox cbAllDeclinedWb;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTableNb;
    private javax.swing.JPanel jpEmpty;
    private javax.swing.JPanel jpMain;
    private javax.swing.JPanel jpPruefung;
    private javax.swing.JPanel jpStat;
    private javax.swing.JPanel jpStatistik;
    private javax.swing.JPanel jpTable;
    private javax.swing.JPanel jpVorpruefung;
    private javax.swing.JScrollPane jsMain;
    private javax.swing.JLabel lblAbgelehntLab;
    private javax.swing.JLabel lblAllAcceptedNb;
    private javax.swing.JLabel lblAllAcceptedWb;
    private javax.swing.JLabel lblAllAcceptedWithoutNb;
    private javax.swing.JLabel lblAllDeclinedNb;
    private javax.swing.JLabel lblAllDeclinedWb;
    private javax.swing.JLabel lblAngenommenLab;
    private javax.swing.JLabel lblAuflagen;
    private javax.swing.JLabel lblAuflagen1;
    private javax.swing.JLabel lblAuflagenLab;
    private javax.swing.JLabel lblClosed;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblStat;
    private javax.swing.JLabel lblStat2;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusPrefix;
    private javax.swing.JLabel lblTableHeaderNb;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUngeprueftLab;
    private javax.swing.JLabel lblVorpruefung;
    private de.cismet.tools.gui.RoundedPanel panEntscheidung;
    private de.cismet.tools.gui.RoundedPanel panEntscheidung1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo4;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent2;
    private de.cismet.tools.gui.RoundedPanel panMain;
    private de.cismet.tools.gui.RoundedPanel panTabelle;
    private javax.swing.JToggleButton tbAngenommenNb;
    private javax.swing.JToggleButton tbAngenommenNbP;
    private javax.swing.JToggleButton tbAngenommenWb;
    private javax.swing.JTextArea textAuflagenNb;
    private javax.swing.JTextArea textAuflagenWb;
    private javax.swing.JToggleButton tgAbgelehntNb;
    private javax.swing.JToggleButton tgAbgelehntWB;
    private javax.swing.JLabel txtHint;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenHistory.
     */
    public CheckAssistent() {
        initComponents();
        cbAllAcceptedWithoutNb.setMargin(new Insets(0, 0, 0, 0));
        cbAllAcceptedNb.setMargin(new Insets(0, 0, 0, 0));
        cbAllDeclinedNb.setMargin(new Insets(0, 0, 0, 0));
        cbAllDeclinedWb.setMargin(new Insets(0, 0, 0, 0));
        cbAllAcceptedWb.setMargin(new Insets(0, 0, 0, 0));
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
        ((JXTable)jTableNb).setHighlighters(alternateRowHighlighter);
        jTableNb.setDefaultRenderer(JCheckBox.class, new CheckBoxRenderer());
        jTableNb.setDefaultRenderer(StationCell.class, new StationCellRenderer());
        setName("Prüfassistent");
        lblClosed.setText("Es ist kein Planungsabschnitt geöffnet");
        jTableNb.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (!readOnlyNb) {
                        final int row = jTableNb.rowAtPoint(e.getPoint());
                        final int col = jTableNb.columnAtPoint(e.getPoint());
                        final CustomTableModel model = (CustomTableModel)jTableNb.getModel();
                        final int modelCol = jTableNb.convertColumnIndexToModel(col);
                        final int modelRow = jTableNb.convertRowIndexToModel(row);

                        if ((model != null)
                                    && ((modelCol == 4) || (modelCol == 5) || (modelCol == 6) || (modelCol == 8)
                                        || (modelCol == 9))) {
                            model.setValueAt(null, modelRow, modelCol);
                        }
                    }
                }
            });

        textAuflagenNb.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void insertUpdate(final DocumentEvent e) {
                    try {
                        final String insertedText = e.getDocument().getText(e.getOffset(), e.getLength());
                        if (!insertedText.startsWith("http://www.fis-wasser-mv.de/nutzerhandbuch")
                                    || (insertedText.contains("#"))) {
                            return;
                        }

                        final String part = insertedText.substring(insertedText.indexOf("#") + 1);
                        final URL url = new URL(insertedText);
                        final InputStream is = WebAccessManager.getInstance().doRequest(url);
                        final BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String tmp;
                        String htmlText = "";

                        while ((tmp = br.readLine()) != null) {
                            htmlText += tmp;
                        }

                        final String htmlPart = extractPasteText(htmlText, part);
                        final Document d = Jsoup.parse(htmlPart);
                        final String text = HTMLTools.getText(d.body()) + "\nLink zur Wiki-Seite: "
                                    + insertedText.substring(0, insertedText.indexOf("#") + 1)
                                    + toWikiLinkSyntax(extractTitle(htmlText, part));

                        EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        e.getDocument().remove(e.getOffset(), e.getLength());
                                        e.getDocument().insertString(e.getOffset(), text, null);
                                    } catch (Exception ex) {
                                        LOG.error("Error in document listener", ex);
                                    }
                                }
                            });
                    } catch (MalformedURLException ex) {
                        // nothing to do
                    } catch (Exception ex) {
                        LOG.error("Error in document listener", ex);
                    }
                }

                @Override
                public void removeUpdate(final DocumentEvent e) {
                }

                @Override
                public void changedUpdate(final DocumentEvent e) {
                }
            });

        textAuflagenWb.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void insertUpdate(final DocumentEvent e) {
                    try {
                        final String insertedText = e.getDocument().getText(e.getOffset(), e.getLength());
                        if (!insertedText.startsWith("http://www.fis-wasser-mv.de/nutzerhandbuch")
                                    || (insertedText.contains("#"))) {
                            return;
                        }

                        final String part = insertedText.substring(insertedText.indexOf("#") + 1);
                        final URL url = new URL(insertedText);
                        final InputStream is = WebAccessManager.getInstance().doRequest(url);
                        final BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String tmp;
                        String htmlText = "";

                        while ((tmp = br.readLine()) != null) {
                            htmlText += tmp;
                        }

                        final String htmlPart = extractPasteText(htmlText, part);
                        final Document d = Jsoup.parse(htmlPart);
                        final String text = HTMLTools.getText(d.body()) + "\nLink zur Wiki-Seite: "
                                    + insertedText.substring(0, insertedText.indexOf("#") + 1)
                                    + toWikiLinkSyntax(extractTitle(htmlText, part));

                        EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        e.getDocument().remove(e.getOffset(), e.getLength());
                                        e.getDocument().insertString(e.getOffset(), text, null);
                                    } catch (Exception ex) {
                                        LOG.error("Error in document listener", ex);
                                    }
                                }
                            });
                    } catch (MalformedURLException ex) {
                        // nothing to do
                    } catch (Exception ex) {
                        LOG.error("Error in document listener", ex);
                    }
                }

                @Override
                public void removeUpdate(final DocumentEvent e) {
                }

                @Override
                public void changedUpdate(final DocumentEvent e) {
                }
            });
        switchToForm("closed");
        instance = this;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the forceReadOnly
     */
    public boolean isForceReadOnly() {
        return forceReadOnly;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  forceReadOnly  the forceReadOnly to set
     */
    public void setForceReadOnly(final boolean forceReadOnly) {
        this.forceReadOnly = forceReadOnly;
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshCheckBoxes() {
        boolean allAcceptedNb = true;
        boolean allAcceptedNbP = true;
        boolean allDeclinedNb = true;
        boolean allAcceptedWb = true;
        boolean allDeclinedWb = true;

        for (int row = 0; row < jTableNb.getRowCount(); ++row) {
            if (!((JCheckBox)jTableNb.getModel().getValueAt(jTableNb.convertRowIndexToModel(row), 4)).isSelected()) {
                allAcceptedNb = false;
            }
            if (!((JCheckBox)jTableNb.getModel().getValueAt(jTableNb.convertRowIndexToModel(row), 5)).isSelected()) {
                allAcceptedNbP = false;
            }
            if (!((JCheckBox)jTableNb.getModel().getValueAt(jTableNb.convertRowIndexToModel(row), 6)).isSelected()) {
                allDeclinedNb = false;
            }
            if (!((JCheckBox)jTableNb.getModel().getValueAt(jTableNb.convertRowIndexToModel(row), 8)).isSelected()) {
                allAcceptedWb = false;
            }
            if (!((JCheckBox)jTableNb.getModel().getValueAt(jTableNb.convertRowIndexToModel(row), 9)).isSelected()) {
                allDeclinedWb = false;
            }
        }

        cbAllAcceptedWithoutNb.setSelected(allAcceptedNb);
        cbAllAcceptedNb.setSelected(allAcceptedNbP);
        cbAllDeclinedNb.setSelected(allDeclinedNb);
        cbAllAcceptedWb.setSelected(allAcceptedWb);
        cbAllDeclinedWb.setSelected(allDeclinedWb);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   text  DOCUMENT ME!
     * @param   part  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String extractTitle(final String text, final String part) {
        final String searchExp = DIV_TEMPLATE_START + part;
        String tmp = text;
        int index = text.indexOf(searchExp);
        int tmpIndex = tmp.indexOf(SPAN_CLASS_MWHEADLINE_ID);

        while ((index != -1) && (tmpIndex < index) && (tmpIndex != -1)) {
            tmp = tmp.substring(tmpIndex + SPAN_CLASS_MWHEADLINE_ID.length());
            index = tmp.indexOf(searchExp);
            tmpIndex = tmp.indexOf(SPAN_CLASS_MWHEADLINE_ID);
        }

        if ((tmp != null) && (tmpIndex != -1) && (tmp.contains(">")) && (tmp.contains("<"))
                    && (tmp.indexOf(">") < tmp.indexOf("<"))) {
            return tmp.substring(tmp.indexOf(">") + 1, tmp.indexOf("<"));
        }

        return "";
    }

    /**
     * DOCUMENT ME!
     *
     * @param   text  DOCUMENT ME!
     * @param   part  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String extractPasteText(final String text, final String part) {
        final String searchExp = DIV_TEMPLATE_START + part;
        String pasteText = text.substring(text.indexOf(searchExp) + searchExp.length());

        if (pasteText.contains(SPAN_CLASS_MWHEADLINE_ID)) {
            pasteText = pasteText.substring(0, pasteText.indexOf(SPAN_CLASS_MWHEADLINE_ID));
        }

        if (pasteText.contains(DIV_CLASSPASTE_START)) {
            // No text to paste found
            return "";
        }
        pasteText = pasteText.substring(pasteText.indexOf(DIV_CLASSPASTE_START) + DIV_CLASSPASTE_START.length());

        int divsToClose = 1;
        String tmp = pasteText;
        int totalIndex = 0;

        while (divsToClose > 0) {
            int divIndex = tmp.indexOf("<div");
            int closedDivInde = tmp.indexOf("</div");

            divIndex = ((divIndex == -1) ? Integer.MAX_VALUE : divIndex);
            closedDivInde = ((closedDivInde == -1) ? Integer.MAX_VALUE : closedDivInde);

            if (closedDivInde < divIndex) {
                --divsToClose;
                tmp = tmp.substring(closedDivInde + 1);
                totalIndex += closedDivInde;
            } else if (closedDivInde > divIndex) {
                ++divsToClose;
                tmp = tmp.substring(divIndex + 1);
                totalIndex += divIndex;
            }
        }

        pasteText = pasteText.substring(0, totalIndex);
        return pasteText;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   link  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  UnsupportedEncodingException  DOCUMENT ME!
     */
    private static String toWikiLinkSyntax(final String link) throws UnsupportedEncodingException {
        final String encodedLink = link.replace(' ', '_');

        return URLEncoder.encode(encodedLink, "UTF-8").replace('%', '.');
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        jTableNb.repaint();
    }

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
        panMain = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblTitle = new javax.swing.JLabel();
        lblStatusPrefix = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
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
        panTabelle = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblTableHeaderNb = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cbAllAcceptedWithoutNb = new javax.swing.JCheckBox();
        lblAllAcceptedWithoutNb = new javax.swing.JLabel();
        cbAllAcceptedNb = new javax.swing.JCheckBox();
        lblAllAcceptedNb = new javax.swing.JLabel();
        cbAllDeclinedNb = new javax.swing.JCheckBox();
        lblAllDeclinedNb = new javax.swing.JLabel();
        cbAllAcceptedWb = new javax.swing.JCheckBox();
        lblAllAcceptedWb = new javax.swing.JLabel();
        cbAllDeclinedWb = new javax.swing.JCheckBox();
        lblAllDeclinedWb = new javax.swing.JLabel();
        jpTable = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableNb = new org.jdesktop.swingx.JXTable();
        panEntscheidung = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        lblAuflagen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAuflagenNb = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        tbAngenommenNb = new javax.swing.JToggleButton();
        tgAbgelehntNb = new javax.swing.JToggleButton();
        tbAngenommenNbP = new javax.swing.JToggleButton();
        jSeparator4 = new javax.swing.JSeparator();
        panEntscheidung1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo4 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        lblAuflagen1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        textAuflagenWb = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        tgAbgelehntWB = new javax.swing.JToggleButton();
        tbAngenommenWb = new javax.swing.JToggleButton();
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
        jPanel2.setMinimumSize(new java.awt.Dimension(780, 600));
        jPanel2.setPreferredSize(new java.awt.Dimension(780, 673));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        panMain.setMinimumSize(new java.awt.Dimension(740, 460));
        panMain.setPreferredSize(new java.awt.Dimension(740, 460));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        panHeadInfo.add(lblTitle, gridBagConstraints);

        lblStatusPrefix.setForeground(new java.awt.Color(255, 255, 255));
        lblStatusPrefix.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblStatusPrefix.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        panHeadInfo.add(lblStatusPrefix, gridBagConstraints);

        lblStatus.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        panHeadInfo.add(lblStatus, gridBagConstraints);

        panMain.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jpStat.setOpaque(false);
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

        jpStatistik.setOpaque(false);
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
        jPanel1.setOpaque(false);
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

        jpVorpruefung.setOpaque(false);
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

        jpPruefung.setForeground(java.awt.Color.white);
        jpPruefung.setOpaque(false);
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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
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
        panInfoContent.add(jpStat, gridBagConstraints);

        panTabelle.setMinimumSize(new java.awt.Dimension(109, 74));

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.GridBagLayout());

        lblTableHeaderNb.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        panHeadInfo1.add(lblTableHeaderNb, gridBagConstraints);

        jPanel3.setMinimumSize(new java.awt.Dimension(260, 25));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        cbAllAcceptedWithoutNb.setBackground(java.awt.Color.white);
        cbAllAcceptedWithoutNb.setForeground(new java.awt.Color(255, 255, 255));
        cbAllAcceptedWithoutNb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.cbAllAcceptedWithoutNb.text",
                new Object[] {})); // NOI18N
        cbAllAcceptedWithoutNb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbAllAcceptedWithoutNbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(cbAllAcceptedWithoutNb, gridBagConstraints);

        lblAllAcceptedWithoutNb.setForeground(new java.awt.Color(255, 255, 255));
        lblAllAcceptedWithoutNb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAllAcceptedWithoutNb.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 3);
        jPanel3.add(lblAllAcceptedWithoutNb, gridBagConstraints);

        cbAllAcceptedNb.setBackground(java.awt.Color.white);
        cbAllAcceptedNb.setForeground(new java.awt.Color(255, 255, 255));
        cbAllAcceptedNb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.cbAllAcceptedNb.text",
                new Object[] {})); // NOI18N
        cbAllAcceptedNb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbAllAcceptedNbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel3.add(cbAllAcceptedNb, gridBagConstraints);

        lblAllAcceptedNb.setForeground(new java.awt.Color(255, 255, 255));
        lblAllAcceptedNb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAllAcceptedNb.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 3);
        jPanel3.add(lblAllAcceptedNb, gridBagConstraints);

        cbAllDeclinedNb.setBackground(java.awt.Color.white);
        cbAllDeclinedNb.setForeground(new java.awt.Color(255, 255, 255));
        cbAllDeclinedNb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.cbAllDeclinedNb.text",
                new Object[] {})); // NOI18N
        cbAllDeclinedNb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbAllDeclinedNbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel3.add(cbAllDeclinedNb, gridBagConstraints);

        lblAllDeclinedNb.setForeground(new java.awt.Color(255, 255, 255));
        lblAllDeclinedNb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAllDeclinedNb.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel3.add(lblAllDeclinedNb, gridBagConstraints);

        cbAllAcceptedWb.setBackground(java.awt.Color.white);
        cbAllAcceptedWb.setForeground(new java.awt.Color(255, 255, 255));
        cbAllAcceptedWb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.cbAllAcceptedWb.text",
                new Object[] {})); // NOI18N
        cbAllAcceptedWb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbAllAcceptedWbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        jPanel3.add(cbAllAcceptedWb, gridBagConstraints);

        lblAllAcceptedWb.setForeground(new java.awt.Color(255, 255, 255));
        lblAllAcceptedWb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAllAcceptedWb.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 3);
        jPanel3.add(lblAllAcceptedWb, gridBagConstraints);

        cbAllDeclinedWb.setBackground(java.awt.Color.white);
        cbAllDeclinedWb.setForeground(new java.awt.Color(255, 255, 255));
        cbAllDeclinedWb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.cbAllDeclinedWb.text",
                new Object[] {})); // NOI18N
        cbAllDeclinedWb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbAllDeclinedWbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel3.add(cbAllDeclinedWb, gridBagConstraints);

        lblAllDeclinedWb.setForeground(new java.awt.Color(255, 255, 255));
        lblAllDeclinedWb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAllDeclinedWb.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel3.add(lblAllDeclinedWb, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 5);
        panHeadInfo1.add(jPanel3, gridBagConstraints);

        panTabelle.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        jpTable.setOpaque(false);
        jpTable.setLayout(new java.awt.GridBagLayout());

        jTableNb.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {}));
        jTableNb.addFocusListener(new java.awt.event.FocusAdapter() {

                @Override
                public void focusLost(final java.awt.event.FocusEvent evt) {
                    jTableNbFocusLost(evt);
                }
            });
        jScrollPane2.setViewportView(jTableNb);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jpTable.add(jScrollPane2, gridBagConstraints);

        panTabelle.add(jpTable, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        panInfoContent.add(panTabelle, gridBagConstraints);

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblHeading1.text")); // NOI18N
        panHeadInfo2.add(lblHeading1);

        panEntscheidung.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        lblAuflagen.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAuflagen.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 0);
        panInfoContent1.add(lblAuflagen, gridBagConstraints);

        textAuflagenNb.setColumns(20);
        textAuflagenNb.setRows(5);
        textAuflagenNb.addFocusListener(new java.awt.event.FocusAdapter() {

                @Override
                public void focusLost(final java.awt.event.FocusEvent evt) {
                    textAuflagenNbFocusLost(evt);
                }
            });
        jScrollPane1.setViewportView(textAuflagenNb);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent1.add(jScrollPane1, gridBagConstraints);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        tbAngenommenNb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.tbAngenommenNb.text")); // NOI18N
        tbAngenommenNb.setMaximumSize(new java.awt.Dimension(88, 30));
        tbAngenommenNb.setMinimumSize(new java.awt.Dimension(88, 30));
        tbAngenommenNb.setPreferredSize(new java.awt.Dimension(88, 30));
        tbAngenommenNb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    tbAngenommenNbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel4.add(tbAngenommenNb, gridBagConstraints);

        tgAbgelehntNb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.tgAbgelehntNb.text")); // NOI18N
        tgAbgelehntNb.setMaximumSize(new java.awt.Dimension(88, 30));
        tgAbgelehntNb.setMinimumSize(new java.awt.Dimension(88, 30));
        tgAbgelehntNb.setPreferredSize(new java.awt.Dimension(88, 30));
        tgAbgelehntNb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    tgAbgelehntNbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel4.add(tgAbgelehntNb, gridBagConstraints);

        tbAngenommenNbP.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.tbAngenommenNbP.text")); // NOI18N
        tbAngenommenNbP.setMaximumSize(new java.awt.Dimension(88, 30));
        tbAngenommenNbP.setMinimumSize(new java.awt.Dimension(88, 30));
        tbAngenommenNbP.setPreferredSize(new java.awt.Dimension(88, 30));
        tbAngenommenNbP.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    tbAngenommenNbPActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel4.add(tbAngenommenNbP, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        panInfoContent1.add(jPanel4, gridBagConstraints);

        panEntscheidung.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        panInfoContent.add(panEntscheidung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        panInfoContent.add(jSeparator4, gridBagConstraints);

        panHeadInfo4.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo4.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblHeading2.text")); // NOI18N
        panHeadInfo4.add(lblHeading2);

        panEntscheidung1.add(panHeadInfo4, java.awt.BorderLayout.NORTH);

        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        lblAuflagen1.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.lblAuflagen1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 0);
        panInfoContent2.add(lblAuflagen1, gridBagConstraints);

        textAuflagenWb.setColumns(20);
        textAuflagenWb.setRows(5);
        textAuflagenWb.addFocusListener(new java.awt.event.FocusAdapter() {

                @Override
                public void focusLost(final java.awt.event.FocusEvent evt) {
                    textAuflagenWbFocusLost(evt);
                }
            });
        jScrollPane4.setViewportView(textAuflagenWb);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent2.add(jScrollPane4, gridBagConstraints);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridBagLayout());

        tgAbgelehntWB.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.tgAbgelehntWB.text")); // NOI18N
        tgAbgelehntWB.setMaximumSize(new java.awt.Dimension(88, 30));
        tgAbgelehntWB.setMinimumSize(new java.awt.Dimension(88, 30));
        tgAbgelehntWB.setPreferredSize(new java.awt.Dimension(88, 30));
        tgAbgelehntWB.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    tgAbgelehntWBActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 0, 0);
        jPanel5.add(tgAbgelehntWB, gridBagConstraints);

        tbAngenommenWb.setText(org.openide.util.NbBundle.getMessage(
                CheckAssistent.class,
                "CheckAssistent.tbAngenommenWb.text")); // NOI18N
        tbAngenommenWb.setMaximumSize(new java.awt.Dimension(88, 30));
        tbAngenommenWb.setMinimumSize(new java.awt.Dimension(88, 30));
        tbAngenommenWb.setPreferredSize(new java.awt.Dimension(88, 30));
        tbAngenommenWb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    tbAngenommenWbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel5.add(tbAngenommenWb, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        panInfoContent2.add(jPanel5, gridBagConstraints);

        panEntscheidung1.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        panInfoContent.add(panEntscheidung1, gridBagConstraints);

        txtHint.setText(org.openide.util.NbBundle.getMessage(CheckAssistent.class, "CheckAssistent.txtHint.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panInfoContent.add(txtHint, gridBagConstraints);

        panMain.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(panMain, gridBagConstraints);

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
    private void tbAngenommenNbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tbAngenommenNbActionPerformed
        examinationManager.setNotRequired(tbAngenommenNb.isSelected());
    }                                                                                  //GEN-LAST:event_tbAngenommenNbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlGesMassnActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlGesMassnActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(null);
        lblTableHeaderNb.setText(TABLE_HEADER_ALL);
    }                                                                              //GEN-LAST:event_hlGesMassnActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlUferLinksActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlUferLinksActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(new KompartimentFilter(
                GupPlanungsabschnittEditor.GUP_UFER_LINKS));
        lblTableHeaderNb.setText(TABLE_HEADER_UFER_LINKS);
    }                                                                               //GEN-LAST:event_hlUferLinksActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlUferRechtsActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlUferRechtsActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(new KompartimentFilter(
                GupPlanungsabschnittEditor.GUP_UFER_RECHTS));
        lblTableHeaderNb.setText(TABLE_HEADER_UFER_RECHTS);
    }                                                                                //GEN-LAST:event_hlUferRechtsActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlSohleActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlSohleActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(new KompartimentFilter(GupPlanungsabschnittEditor.GUP_SOHLE));
        lblTableHeaderNb.setText(TABLE_HEADER_SOHLE);
    }                                                                           //GEN-LAST:event_hlSohleActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlUmfeldLinksActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlUmfeldLinksActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(new KompartimentFilter(
                GupPlanungsabschnittEditor.GUP_UMFELD_LINKS));
        lblTableHeaderNb.setText(TABLE_HEADER_UMFELD_LINKS);
    }                                                                                 //GEN-LAST:event_hlUmfeldLinksActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlUmfeldRechtsActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlUmfeldRechtsActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(new KompartimentFilter(
                GupPlanungsabschnittEditor.GUP_UMFELD_RECHTS));
        lblTableHeaderNb.setText(TABLE_HEADER_UMFELD_RECHTS);
    }                                                                                  //GEN-LAST:event_hlUmfeldRechtsActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlValideActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlValideActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(new ValidatorFilter(
                UnterhaltungsmassnahmeValidator.ValidationResult.ok));
        lblTableHeaderNb.setText(TABLE_HEADER_VALIDE);
    }                                                                            //GEN-LAST:event_hlValideActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlInvalideActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlInvalideActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(new ValidatorFilter(
                UnterhaltungsmassnahmeValidator.ValidationResult.error));
        lblTableHeaderNb.setText(TABLE_HEADER_INVALIDE);
    }                                                                              //GEN-LAST:event_hlInvalideActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlAbgelehntActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlAbgelehntActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(new TableFilter() {

                @Override
                public boolean check(final CidsBean bean) {
                    Boolean abgNb = (Boolean)bean.getProperty(GupGupEditor.PROP_DECLINED_NB);
                    Boolean abgWb = (Boolean)bean.getProperty(GupGupEditor.PROP_DECLINED_WB);

                    abgNb = ((abgNb == null) ? false : abgNb);
                    abgWb = ((abgWb == null) ? false : abgWb);

                    return abgNb || abgWb;
                }
            });
        lblTableHeaderNb.setText(TABLE_HEADER_ABGELEHNT);
    } //GEN-LAST:event_hlAbgelehntActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlAuflagenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlAuflagenActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(new TableFilter() {

                @Override
                public boolean check(final CidsBean bean) {
                    Boolean angNb = (Boolean)bean.getProperty(GupGupEditor.PROP_ACCEPTED_NB);
                    Boolean keNb = (Boolean)bean.getProperty(GupGupEditor.PROP_NOT_REQUIRED_NB);
                    Boolean angWb = (Boolean)bean.getProperty(GupGupEditor.PROP_ACCEPTED_WB);
                    final String auflagenNb = (String)bean.getProperty("auflagen_nb");
                    final String auflagenWb = (String)bean.getProperty("auflagen_wb");

                    angNb = ((angNb == null) ? false : angNb);
                    keNb = ((keNb == null) ? false : keNb);
                    angWb = ((angWb == null) ? false : angWb);

                    return ((angNb || keNb) && angWb
                                    && (((auflagenNb != null) && !auflagenNb.equals(""))
                                        || ((auflagenWb != null) && !auflagenWb.equals(""))));
                }
            });
        lblTableHeaderNb.setText(TABLE_HEADER_AUFLAGEN);
    } //GEN-LAST:event_hlAuflagenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlAngenommenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlAngenommenActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(new TableFilter() {

                @Override
                public boolean check(final CidsBean bean) {
                    Boolean angNb = (Boolean)bean.getProperty(GupGupEditor.PROP_ACCEPTED_NB);
                    Boolean keNb = (Boolean)bean.getProperty(GupGupEditor.PROP_NOT_REQUIRED_NB);
                    Boolean angWb = (Boolean)bean.getProperty(GupGupEditor.PROP_ACCEPTED_WB);
                    String auflagenNb = (String)bean.getProperty("auflagen_nb");
                    String auflagenWb = (String)bean.getProperty("auflagen_wb");

                    angNb = ((angNb == null) ? false : angNb);
                    keNb = ((keNb == null) ? false : keNb);
                    angWb = ((angWb == null) ? false : angWb);
                    auflagenNb = (((auflagenNb == null) || auflagenNb.equals("")) ? null : auflagenNb);
                    auflagenWb = (((auflagenWb == null) || auflagenWb.equals("")) ? null : auflagenWb);

                    return (angNb || keNb) && angWb && (auflagenNb == null) && (auflagenWb == null);
                }
            });
        lblTableHeaderNb.setText(TABLE_HEADER_ANGENOMMEN);
    } //GEN-LAST:event_hlAngenommenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void hlUngeprueftActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_hlUngeprueftActionPerformed
        ((CustomTableModel)jTableNb.getModel()).setFilter(new TableFilter() {

                @Override
                public boolean check(final CidsBean bean) {
                    Boolean angNb = (Boolean)bean.getProperty(GupGupEditor.PROP_ACCEPTED_NB);
                    Boolean keNb = (Boolean)bean.getProperty(GupGupEditor.PROP_NOT_REQUIRED_NB);
                    Boolean angWb = (Boolean)bean.getProperty(GupGupEditor.PROP_ACCEPTED_WB);
                    Boolean abgWb = (Boolean)bean.getProperty(GupGupEditor.PROP_DECLINED_WB);
                    Boolean abgNb = (Boolean)bean.getProperty(GupGupEditor.PROP_DECLINED_NB);

                    angNb = ((angNb == null) ? false : angNb);
                    keNb = ((keNb == null) ? false : keNb);
                    angWb = ((angWb == null) ? false : angWb);
                    abgWb = ((abgWb == null) ? false : abgWb);
                    abgNb = ((abgNb == null) ? false : abgNb);

                    return (!angNb && !abgNb && !keNb) || (!angWb && !abgWb && !abgNb);
                }
            });
        lblTableHeaderNb.setText(TABLE_HEADER_UNGEPRUEFT);
    } //GEN-LAST:event_hlUngeprueftActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tgAbgelehntNbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tgAbgelehntNbActionPerformed
        examinationManager.setDecline(tgAbgelehntNb.isSelected(), GupGupEditor.PERMISSION_NB);
    }                                                                                 //GEN-LAST:event_tgAbgelehntNbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jTableNbFocusLost(final java.awt.event.FocusEvent evt) { //GEN-FIRST:event_jTableNbFocusLost
    }                                                                     //GEN-LAST:event_jTableNbFocusLost

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void textAuflagenNbFocusLost(final java.awt.event.FocusEvent evt) { //GEN-FIRST:event_textAuflagenNbFocusLost
        examinationManager.setCondition(textAuflagenNb.getText(), GupGupEditor.PERMISSION_NB);
    }                                                                           //GEN-LAST:event_textAuflagenNbFocusLost

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbAllAcceptedWithoutNbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbAllAcceptedWithoutNbActionPerformed
        final CustomTableModel model = (CustomTableModel)jTableNb.getModel();

        for (int row = 0; row < jTableNb.getRowCount(); ++row) {
            final CidsBean bean = model.getBean(jTableNb.convertRowIndexToModel(row));

            try {
                bean.setProperty(GupGupEditor.PROP_NOT_REQUIRED_NB, cbAllAcceptedWithoutNb.isSelected());
                if (cbAllAcceptedWithoutNb.isSelected()) {
                    bean.setProperty(GupGupEditor.PROP_DECLINED_NB, !cbAllAcceptedWithoutNb.isSelected());
                    bean.setProperty(GupGupEditor.PROP_ACCEPTED_NB, !cbAllAcceptedWithoutNb.isSelected());
                }
            } catch (Exception e) {
                LOG.error("Exception while changing the accept status", e);
            }
        }
        if (cbAllAcceptedWithoutNb.isSelected() && cbAllDeclinedNb.isSelected()) {
            cbAllDeclinedNb.setSelected(false);
        }
        if (cbAllAcceptedWithoutNb.isSelected() && cbAllAcceptedNb.isSelected()) {
            cbAllAcceptedNb.setSelected(false);
        }
        examinationManager.refreshGUI();
    } //GEN-LAST:event_cbAllAcceptedWithoutNbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbAllDeclinedNbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbAllDeclinedNbActionPerformed
        final CustomTableModel model = (CustomTableModel)jTableNb.getModel();

        for (int row = 0; row < jTableNb.getRowCount(); ++row) {
            final CidsBean bean = model.getBean(jTableNb.convertRowIndexToModel(row));

            try {
                bean.setProperty(GupGupEditor.PROP_DECLINED_NB, cbAllDeclinedNb.isSelected());
                if (cbAllDeclinedNb.isSelected()) {
                    bean.setProperty(GupGupEditor.PROP_ACCEPTED_NB, !cbAllDeclinedNb.isSelected());
                    bean.setProperty(GupGupEditor.PROP_NOT_REQUIRED_NB, !cbAllDeclinedNb.isSelected());
                }
            } catch (Exception e) {
                LOG.error("Exception while changing the accept status", e);
            }
        }
        if (cbAllDeclinedNb.isSelected() && cbAllAcceptedWithoutNb.isSelected()) {
            cbAllAcceptedWithoutNb.setSelected(false);
        }

        if (cbAllDeclinedNb.isSelected() && cbAllAcceptedNb.isSelected()) {
            cbAllAcceptedNb.setSelected(false);
        }

        examinationManager.refreshGUI();
    } //GEN-LAST:event_cbAllDeclinedNbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tbAngenommenWbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tbAngenommenWbActionPerformed
        examinationManager.setAccept(tbAngenommenWb.isSelected(), GupGupEditor.PERMISSION_WB);
    }                                                                                  //GEN-LAST:event_tbAngenommenWbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tgAbgelehntWBActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tgAbgelehntWBActionPerformed
        examinationManager.setDecline(tgAbgelehntWB.isSelected(), GupGupEditor.PERMISSION_WB);
    }                                                                                 //GEN-LAST:event_tgAbgelehntWBActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void textAuflagenWbFocusLost(final java.awt.event.FocusEvent evt) { //GEN-FIRST:event_textAuflagenWbFocusLost
        examinationManager.setCondition(textAuflagenWb.getText(), GupGupEditor.PERMISSION_WB);
    }                                                                           //GEN-LAST:event_textAuflagenWbFocusLost

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tbAngenommenNbPActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tbAngenommenNbPActionPerformed
        examinationManager.setAccept(tbAngenommenNbP.isSelected(), GupGupEditor.PERMISSION_NB);
    }                                                                                   //GEN-LAST:event_tbAngenommenNbPActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbAllAcceptedWbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbAllAcceptedWbActionPerformed
        final CustomTableModel model = (CustomTableModel)jTableNb.getModel();

        for (int row = 0; row < jTableNb.getRowCount(); ++row) {
            final CidsBean bean = model.getBean(jTableNb.convertRowIndexToModel(row));

            try {
                bean.setProperty(GupGupEditor.PROP_ACCEPTED_WB, cbAllAcceptedWb.isSelected());

                if (cbAllAcceptedWb.isSelected()) {
                    bean.setProperty(GupGupEditor.PROP_DECLINED_WB, !cbAllAcceptedWb.isSelected());
                }
            } catch (Exception e) {
                LOG.error("Exception while changing the accept status", e);
            }
        }
        if (cbAllAcceptedWb.isSelected() && cbAllDeclinedWb.isSelected()) {
            cbAllDeclinedWb.setSelected(false);
        }
        examinationManager.refreshGUI();
    } //GEN-LAST:event_cbAllAcceptedWbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbAllDeclinedWbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbAllDeclinedWbActionPerformed
        final CustomTableModel model = (CustomTableModel)jTableNb.getModel();

        for (int row = 0; row < jTableNb.getRowCount(); ++row) {
            final CidsBean bean = model.getBean(jTableNb.convertRowIndexToModel(row));

            try {
                bean.setProperty(GupGupEditor.PROP_DECLINED_WB, cbAllDeclinedWb.isSelected());
                if (cbAllDeclinedWb.isSelected()) {
                    bean.setProperty(GupGupEditor.PROP_ACCEPTED_WB, !cbAllDeclinedWb.isSelected());
                }
            } catch (Exception e) {
                LOG.error("Exception while changing the accept status", e);
            }
        }
        if (cbAllDeclinedWb.isSelected() && cbAllAcceptedWb.isSelected()) {
            cbAllAcceptedWb.setSelected(false);
        }
        examinationManager.refreshGUI();
    } //GEN-LAST:event_cbAllDeclinedWbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbAllAcceptedNbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbAllAcceptedNbActionPerformed
        final CustomTableModel model = (CustomTableModel)jTableNb.getModel();

        for (int row = 0; row < jTableNb.getRowCount(); ++row) {
            final CidsBean bean = model.getBean(jTableNb.convertRowIndexToModel(row));

            try {
                bean.setProperty(GupGupEditor.PROP_ACCEPTED_NB, cbAllAcceptedNb.isSelected());
                if (cbAllAcceptedNb.isSelected()) {
                    bean.setProperty(GupGupEditor.PROP_DECLINED_NB, !cbAllAcceptedNb.isSelected());
                    bean.setProperty(GupGupEditor.PROP_NOT_REQUIRED_NB, !cbAllAcceptedNb.isSelected());
                }
            } catch (Exception e) {
                LOG.error("Exception while changing the accept status", e);
            }
        }
        if (cbAllAcceptedNb.isSelected() && cbAllDeclinedNb.isSelected()) {
            cbAllDeclinedNb.setSelected(false);
        }
        if (cbAllAcceptedNb.isSelected() && cbAllAcceptedWithoutNb.isSelected()) {
            cbAllAcceptedWithoutNb.setSelected(false);
        }
        examinationManager.refreshGUI();
    } //GEN-LAST:event_cbAllAcceptedNbActionPerformed

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
        if (planungsabschnitt != null) {
            planungsabschnitt.removePropertyChangeListener(this);
        }
        planungsabschnitt = cidsBean;

        if (cidsBean != null) {
            switchToForm("open");
            readOnlyNb = isReadOnly(GupGupEditor.PERMISSION_NB);
            readOnlyWb = isReadOnly(GupGupEditor.PERMISSION_WB);

            refreshTitle();
            refreshStats(true);

            textAuflagenNb.setEnabled(!readOnlyNb);
            tgAbgelehntNb.setEnabled(!readOnlyNb);
            tbAngenommenNb.setEnabled(!readOnlyNb);
            tbAngenommenNbP.setEnabled(!readOnlyNb);
            cbAllAcceptedWithoutNb.setVisible(!readOnlyNb);
            cbAllDeclinedNb.setVisible(!readOnlyNb);
            cbAllAcceptedNb.setVisible(!readOnlyNb);
            lblAllAcceptedWithoutNb.setVisible(!readOnlyNb);
            lblAllDeclinedNb.setVisible(!readOnlyNb);
            lblAllAcceptedNb.setVisible(!readOnlyNb);

            textAuflagenWb.setEnabled(!readOnlyWb);
            tgAbgelehntWB.setEnabled(!readOnlyWb);
            tbAngenommenWb.setEnabled(!readOnlyWb);
            cbAllAcceptedWb.setVisible(!readOnlyWb);
            cbAllDeclinedWb.setVisible(!readOnlyWb);
            lblAllAcceptedWb.setVisible(!readOnlyWb);
            lblAllDeclinedWb.setVisible(!readOnlyWb);

            lblTableHeaderNb.setText(TABLE_HEADER_ALL);
            planungsabschnitt.addPropertyChangeListener(this);
        }
    }

    /**
     * It is only for user with the action attribute pruefer in the status pruefung allowed to make changes.
     *
     * @param   role  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isReadOnly(final Integer role) {
        if (forceReadOnly) {
            return true;
        }
        final Boolean isClosed = (Boolean)planungsabschnitt.getProperty("gup.geschlossen");

        if ((isClosed != null) && isClosed) {
            return true;
        }

        final Integer state = GupGupEditor.determineStatusByGupBean((CidsBean)planungsabschnitt.getProperty("gup"));

        if ((role & GupGupEditor.PERMISSION_NB) != 0) {
            if (GupGupEditor.hasActionNaturschutz()
                        && ((state == GupGupEditor.STAT_NB) || (state == GupGupEditor.STAT_NB_WB)
                            || (state == GupGupEditor.STAT_NB_WB_ABG))) {
                return false;
            }
        }

        if ((role & GupGupEditor.PERMISSION_WB) != 0) {
            if (GupGupEditor.hasActionWasser()
                        && ((state == GupGupEditor.STAT_WB) || (state == GupGupEditor.STAT_NB_WB)
                            || (state == GupGupEditor.STAT_NB_ABG_WB))) {
                return false;
            }
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
        final String status = GupGupEditor.determineStatusNameByGupBean((CidsBean)planungsabschnitt.getProperty("gup"));

        if (name == null) {
            name = "unbenannt";
        }

        if ((von != null) && (bis != null)) {
            stat = "[" + von + ", " + bis + "]";
        } else {
            stat = "[]";
        }

        lblTitle.setText(name + stat);

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
                            bean.addPropertyChangeListener(CheckAssistent.this);
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
                            final Decision decisionNb = getCheckResult(bean, GupGupEditor.PERMISSION_NB);
                            final Decision decisionWb = getCheckResult(bean, GupGupEditor.PERMISSION_WB);
                            final boolean nbOnCondition = ((decisionNb == Decision.onCondition)
                                            || (decisionNb == Decision.notRequiredOnCondition));
                            final boolean wbOnCondition = decisionWb == Decision.onCondition;

                            if (nbOnCondition
                                        && ((decisionWb == Decision.onCondition) || (decisionWb == Decision.accepted))) {
                                ++auflagen;
                            } else if (((decisionNb == Decision.accepted) || (decisionNb == Decision.notRequired))
                                        && (decisionWb == Decision.accepted)) {
                                ++angenommen;
                            } else if ((decisionNb == Decision.declined) || (decisionWb == Decision.declined)) {
                                ++abgelehnt;
                            } else {
                                ++ungeprueft;
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
                            final TableRowSorter sorter = new TableRowSorter(model);
                            sorter.setMaxSortKeys(3);
                            jTableNb.setRowSorter(sorter);
                            jTableNb.setUpdateSelectionOnSort(true);
                            jTableNb.setModel(model);
                            refreshCheckBoxes();
                            jTableNb.getSelectionModel().addListSelectionListener(CheckAssistent.this);
                            setTableSize();
                            ((JXTable)jTableNb).getColumnExt(4).setComparator(new JCheckboxComparator());
                            ((JXTable)jTableNb).getColumnExt(5).setComparator(new JCheckboxComparator());
                            ((JXTable)jTableNb).getColumnExt(1).setComparator(new StationCellComparator());
                            tbAngenommenNb.setSelected(false);
                            tbAngenommenNbP.setSelected(false);
                            tgAbgelehntNb.setSelected(false);
                            for (int i = 0; i < jTableNb.getColumnCount(); ++i) {
                                jTableNb.getColumn(jTableNb.getColumnName(i))
                                        .setHeaderRenderer(new DefaultTableHeaderCellRenderer());
                            }
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
     * @param   role  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isBeanAccepted(final CidsBean bean, final Integer role) {
        final Decision d = getCheckResult(bean, role);

        return d.equals(Decision.accepted) || d.equals(Decision.onCondition);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isBeanNotRequired(final CidsBean bean) {
        final Decision d = getCheckResult(bean, GupGupEditor.PERMISSION_NB);

        return d.equals(Decision.notRequired) || d.equals(Decision.notRequiredOnCondition);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     * @param   role  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isBeanDeclined(final CidsBean bean, final Integer role) {
        return getCheckResult(bean, role).equals(Decision.declined);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     * @param   role  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean hasBeanConditions(final CidsBean bean, final Integer role) {
        if (role == GupGupEditor.PERMISSION_NB) {
            final String aufl = (String)bean.getProperty("auflagen_nb");

            return ((aufl != null) && !aufl.equals(""));
        } else if (role == GupGupEditor.PERMISSION_WB) {
            final String aufl = (String)bean.getProperty("auflagen_wb");

            return ((aufl != null) && !aufl.equals(""));
        } else {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     * @param   type  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Decision getCheckResult(final CidsBean bean, final Integer type) {
        if (type == GupGupEditor.PERMISSION_NB) {
            final Boolean ang = (Boolean)bean.getProperty("angenommen_nb");
            final Boolean abg = (Boolean)bean.getProperty("abgelehnt_nb");
            final Boolean ne = (Boolean)bean.getProperty("nicht_erforderlich_nb");

            if ((ang != null) && ang) {
                final String aufl = (String)bean.getProperty("auflagen_nb");

                if ((aufl != null) && !aufl.equals("")) {
                    return Decision.onCondition;
                } else {
                    return Decision.accepted;
                }
            }
            if ((ne != null) && ne) {
                final String aufl = (String)bean.getProperty("auflagen_nb");

                if ((aufl != null) && !aufl.equals("")) {
                    return Decision.notRequiredOnCondition;
                } else {
                    return Decision.notRequired;
                }
            } else if ((abg != null) && abg) {
                return Decision.declined;
            } else {
                return Decision.notChecked;
            }
        } else if (type == GupGupEditor.PERMISSION_WB) {
            final Boolean ang = (Boolean)bean.getProperty("angenommen_wb");
            final Boolean abg = (Boolean)bean.getProperty("abgelehnt_wb");

            if ((ang != null) && ang) {
                final String aufl = (String)bean.getProperty("auflagen_wb");

                if ((aufl != null) && !aufl.equals("")) {
                    return Decision.onCondition;
                } else {
                    return Decision.accepted;
                }
            } else if ((abg != null) && abg) {
                return Decision.declined;
            } else {
                return Decision.notChecked;
            }
        } else {
            LOG.warn("not decision possible");
            return Decision.declined;
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setTableSize() {
        final TableColumnModel columnModel = jTableNb.getColumnModel();
        final FontMetrics fmetrics = jTableNb.getFontMetrics(jTableNb.getFont());
        final TableModel model = jTableNb.getModel();
        final int columnCount = model.getColumnCount();
        int totalSize = 0;

        for (int i = 0; i < columnCount; ++i) {
            final int size = (int)fmetrics.getStringBounds(model.getColumnName(i), jTableNb.getGraphics()).getWidth();
            totalSize += size;
            columnModel.getColumn(i).setMinWidth(size + 30);
        }

        jTableNb.setMinimumSize(new Dimension(totalSize + 20, 50));
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
        if (planungsabschnitt != null) {
            planungsabschnitt.removePropertyChangeListener(this);
        }
        if (planungsabschnitt != null) {
            final List<CidsBean> massnBeans = planungsabschnitt.getBeanCollectionProperty("massnahmen");

            if (massnBeans != null) {
                for (final CidsBean bean : massnBeans) {
                    bean.removePropertyChangeListener(this);
                }
            }
        }

        listener.clear();
        planungsabschnitt = null;
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
     * @param   l  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean containsListener(final CheckAssistentListener l) {
        return listener.contains(l);
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
        final CustomTableModel model = (CustomTableModel)jTableNb.getModel();

        int row = model.getRow(bean);

        if (row != -1) {
            row = jTableNb.convertRowIndexToView(row);
        }

        if (row != -1) {
            jTableNb.getSelectionModel().setSelectionInterval(row, row);
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
            final int row = jTableNb.getSelectedRow();
            final CustomTableModel model = (CustomTableModel)jTableNb.getModel();

            if (row != -1) {
                final int index = jTableNb.convertRowIndexToModel(row);
                EventQueue.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            fireSelectionChanged(model.getBean(index));
                        }
                    });
            }

            final int[] rows = jTableNb.getSelectedRows();
            final List<CidsBean> beanList = new ArrayList<CidsBean>(rows.length);

            for (final int tmp : rows) {
                final int index = jTableNb.convertRowIndexToModel(tmp);
                beanList.add(model.getBean(index));
            }
            examinationManager.setBeans(beanList);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refresh() {
        jTableNb.repaint();
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
                "k.A..",
                "§ja.",
                "§nein.",
                "...",
                "k.E.",
                "§ja",
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
            if ((columnIndex == 4) || (columnIndex == 5) || (columnIndex == 6) || (columnIndex == 8)
                        || (columnIndex == 9)) {
                return JCheckBox.class;
            } else if ((columnIndex == 7) || (columnIndex == 10)) {
                return ImageIcon.class;
            } else if (columnIndex == 1) {
                return StationCell.class;
            } else {
                return String.class;
            }
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            if (readOnlyNb) {
                return false;
            }

            return (columnIndex == 4) || (columnIndex == 5) || (columnIndex == 6) || (columnIndex == 8)
                        || (columnIndex == 9);
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
                    final boolean nr = isBeanNotRequired(bean);

                    final JCheckBox box = new JCheckBox();
                    box.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                try {
                                    bean.setProperty(GupGupEditor.PROP_NOT_REQUIRED_NB, box.isSelected());
                                    if (box.isSelected()) {
                                        bean.setProperty(GupGupEditor.PROP_ACCEPTED_NB, !box.isSelected());
                                        bean.setProperty(GupGupEditor.PROP_DECLINED_NB, !box.isSelected());
                                    }
                                    examinationManager.refreshGUI();
                                } catch (final Exception ex) {
                                    LOG.error("Cannot set property.", ex);
                                }
                            }
                        });

                    box.setSelected(nr);
                    box.setOpaque(false);
                    box.setContentAreaFilled(false);
                    box.setEnabled(!readOnlyNb);

                    result = box;
                    break;
                }
                case 5: {
                    final boolean ang = isBeanAccepted(bean, GupGupEditor.PERMISSION_NB);

                    final JCheckBox box = new JCheckBox();
                    box.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                try {
                                    bean.setProperty(GupGupEditor.PROP_ACCEPTED_NB, box.isSelected());
                                    if (box.isSelected()) {
                                        bean.setProperty(GupGupEditor.PROP_DECLINED_NB, !box.isSelected());
                                        bean.setProperty(GupGupEditor.PROP_NOT_REQUIRED_NB, !box.isSelected());
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
                    box.setEnabled(!readOnlyNb);

                    result = box;
                    break;
                }
                case 6: {
                    final boolean abg = isBeanDeclined(bean, GupGupEditor.PERMISSION_NB);

                    final JCheckBox box = new JCheckBox();
                    box.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                try {
                                    bean.setProperty(GupGupEditor.PROP_DECLINED_NB, box.isSelected());
                                    if (box.isSelected()) {
                                        bean.setProperty(GupGupEditor.PROP_ACCEPTED_NB, !box.isSelected());
                                        bean.setProperty(GupGupEditor.PROP_NOT_REQUIRED_NB, !box.isSelected());
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
                    box.setEnabled(!readOnlyNb);

                    result = box;
                    break;
                }
                case 7: {
                    if (hasBeanConditions(bean, GupGupEditor.PERMISSION_NB)) {
                        result = new ImageIcon(getClass().getResource(DOC_ICON));
                    } else {
                        return null;
                    }

                    break;
                }
                case 8: {
                    final boolean ang = isBeanAccepted(bean, GupGupEditor.PERMISSION_WB);

                    final JCheckBox box = new JCheckBox();
                    box.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                try {
                                    bean.setProperty(GupGupEditor.PROP_ACCEPTED_WB, box.isSelected());
                                    if (box.isSelected()) {
                                        bean.setProperty(GupGupEditor.PROP_DECLINED_WB, !box.isSelected());
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
                    box.setEnabled(!readOnlyNb);

                    result = box;
                    break;
                }
                case 9: {
                    final boolean abg = isBeanDeclined(bean, GupGupEditor.PERMISSION_WB);

                    final JCheckBox box = new JCheckBox();
                    box.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                try {
                                    bean.setProperty(GupGupEditor.PROP_DECLINED_WB, box.isSelected());
                                    if (box.isSelected()) {
                                        bean.setProperty(GupGupEditor.PROP_ACCEPTED_WB, !box.isSelected());
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
                    box.setEnabled(!readOnlyNb);

                    result = box;
                    break;
                }
                case 10: {
                    if (hasBeanConditions(bean, GupGupEditor.PERMISSION_WB)) {
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
            if ((columnIndex == 4) || (columnIndex == 5) || (columnIndex == 6) || (columnIndex == 8)
                        || (columnIndex == 9)) {
                if ((rowIndex >= 0) && (rowIndex < getRowCount())) {
                    final CidsBean bean = getBean(rowIndex);
                    String prop;

                    if (columnIndex == 4) {
                        prop = GupGupEditor.PROP_NOT_REQUIRED_NB;
                    } else if (columnIndex == 5) {
                        prop = GupGupEditor.PROP_ACCEPTED_NB;
                    } else if (columnIndex == 6) {
                        prop = GupGupEditor.PROP_DECLINED_NB;
                    } else if (columnIndex == 8) {
                        prop = GupGupEditor.PROP_ACCEPTED_WB;
                    } else {
                        prop = GupGupEditor.PROP_DECLINED_WB;
                    }

                    final Boolean val = (Boolean)bean.getProperty(prop);
                    final boolean currentValue = ((val != null) && val);

                    if (columnIndex == 4) {
                        examinationManager.setNotRequired(!currentValue);
                    } else if (columnIndex == 5) {
                        examinationManager.setAccept(!currentValue, GupGupEditor.PERMISSION_NB);
                    } else if (columnIndex == 6) {
                        examinationManager.setDecline(!currentValue, GupGupEditor.PERMISSION_NB);
                    } else if (columnIndex == 8) {
                        examinationManager.setAccept(!currentValue, GupGupEditor.PERMISSION_WB);
                    } else if (columnIndex == 9) {
                        examinationManager.setDecline(!currentValue, GupGupEditor.PERMISSION_WB);
                    }
                }
            } else {
                throw new UnsupportedOperationException("Not supported yet.");
            }
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

            refreshCheckBoxes();
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
            dummyList.clear();
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
            boolean acceptedNb = true;
            boolean declinedNb = true;
            boolean notRequiredNb = true;
            String conditionsNb = null;
            boolean acceptedWb = true;
            boolean declinedWb = true;
            String conditionsWb = null;

            for (final CidsBean tmp : beans) {
                String tmpAuflagen = (String)tmp.getProperty("auflagen_nb");
                String tmpAuflagenWb = (String)tmp.getProperty("auflagen_wb");

                if (tmpAuflagen == null) {
                    tmpAuflagen = "";
                }

                if (tmpAuflagenWb == null) {
                    tmpAuflagenWb = "";
                }

                if (conditionsNb == null) {
                    conditionsNb = tmpAuflagen;
                } else {
                    if (!conditionsNb.equals(tmpAuflagen)) {
                        conditionsNb = DIFFERENT_CONDITIONS;
                    }
                }

                if (!isBeanAccepted(tmp, GupGupEditor.PERMISSION_NB)) {
                    acceptedNb = false;
                }

                if (!isBeanNotRequired(tmp)) {
                    notRequiredNb = false;
                }

                if (!isBeanDeclined(tmp, GupGupEditor.PERMISSION_NB)) {
                    declinedNb = false;
                }

                if (conditionsWb == null) {
                    conditionsWb = tmpAuflagenWb;
                } else {
                    if (!conditionsWb.equals(tmpAuflagenWb)) {
                        conditionsWb = DIFFERENT_CONDITIONS;
                    }
                }

                if (!isBeanAccepted(tmp, GupGupEditor.PERMISSION_WB)) {
                    acceptedWb = false;
                }

                if (!isBeanDeclined(tmp, GupGupEditor.PERMISSION_WB)) {
                    declinedWb = false;
                }
            }
            // if accepted = false and sameAcceptedStatus = false, the beans have different values

            if (beans.size() > 0) {
                tbAngenommenNb.setSelected(notRequiredNb);
                tbAngenommenNbP.setSelected(acceptedNb);
                tgAbgelehntNb.setSelected(declinedNb);
                textAuflagenNb.setText(conditionsNb);
                tbAngenommenWb.setSelected(acceptedWb);
                tgAbgelehntWB.setSelected(declinedWb);
                textAuflagenWb.setText(conditionsWb);
            } else {
                tbAngenommenNb.setSelected(false);
                tbAngenommenNbP.setSelected(false);
                tgAbgelehntNb.setSelected(false);
                textAuflagenNb.setText("");
                tbAngenommenWb.setSelected(false);
                tgAbgelehntWB.setSelected(false);
                textAuflagenWb.setText("");
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param  newValue  DOCUMENT ME!
         */
        public void setNotRequired(final boolean newValue) {
            for (final CidsBean bean : beans) {
                try {
                    bean.setProperty("nicht_erforderlich_nb", newValue);
                    if (newValue) {
                        bean.setProperty("angenommen_nb", !newValue);
                        bean.setProperty("abgelehnt_nb", !newValue);
                    }
                } catch (Exception e) {
                    LOG.error("Cannot set property 'nicht_erforderlich'", e);
                }
            }

            refreshGUI();
            refresh();
            lblTableHeaderNb.setText(TABLE_HEADER_GEMISCHT);
        }

        /**
         * DOCUMENT ME!
         *
         * @param  newValue  DOCUMENT ME!
         * @param  type      DOCUMENT ME!
         */
        public void setAccept(final boolean newValue, final Integer type) {
            for (final CidsBean bean : beans) {
                try {
                    if (type == GupGupEditor.PERMISSION_NB) {
                        bean.setProperty(GupGupEditor.PROP_ACCEPTED_NB, newValue);
                        if (newValue) {
                            bean.setProperty(GupGupEditor.PROP_NOT_REQUIRED_NB, !newValue);
                            bean.setProperty(GupGupEditor.PROP_DECLINED_NB, !newValue);
                        }
                    } else if (type == GupGupEditor.PERMISSION_WB) {
                        bean.setProperty(GupGupEditor.PROP_ACCEPTED_WB, newValue);
                        if (newValue) {
                            bean.setProperty(GupGupEditor.PROP_DECLINED_WB, !newValue);
                        }
                    }
                } catch (Exception e) {
                    LOG.error("Cannot set property 'angenommen'", e);
                }
            }

            refreshGUI();
            refresh();
            lblTableHeaderNb.setText(TABLE_HEADER_GEMISCHT);
        }

        /**
         * DOCUMENT ME!
         *
         * @param  newValue  DOCUMENT ME!
         * @param  type      DOCUMENT ME!
         */
        public void setDecline(final boolean newValue, final Integer type) {
            for (final CidsBean bean : beans) {
                try {
                    if (type == GupGupEditor.PERMISSION_NB) {
                        bean.setProperty(GupGupEditor.PROP_DECLINED_NB, newValue);
                        if (newValue) {
                            bean.setProperty(GupGupEditor.PROP_NOT_REQUIRED_NB, !newValue);
                            bean.setProperty(GupGupEditor.PROP_ACCEPTED_NB, !newValue);
                        }
                    } else if (type == GupGupEditor.PERMISSION_WB) {
                        bean.setProperty(GupGupEditor.PROP_DECLINED_WB, newValue);

                        if (newValue) {
                            bean.setProperty(GupGupEditor.PROP_ACCEPTED_WB, !newValue);
                        }
                    }
                } catch (Exception e) {
                    LOG.error("Cannot set property 'abgelehnt'", e);
                }
            }
            refreshGUI();
            refresh();
            lblTableHeaderNb.setText(TABLE_HEADER_GEMISCHT);
        }

        /**
         * DOCUMENT ME!
         *
         * @param  newValue  DOCUMENT ME!
         * @param  role      DOCUMENT ME!
         */
        public void setCondition(String newValue, final Integer role) {
            if (role == GupGupEditor.PERMISSION_NB) {
                for (final CidsBean bean : beans) {
                    try {
                        final String oldValue = (String)bean.getProperty("auflagen_nb");

                        if (newValue == null) {
                            newValue = "";
                        }

                        if ((oldValue == null) || !oldValue.equals(newValue)) {
                            bean.setProperty("auflagen_nb", newValue);
                            String basic = (String)bean.getProperty("hinweise");

                            if (basic == null) {
                                basic = newValue;
                            } else {
                                String possibleOldLine = "\n" + oldValue;

                                if (basic.endsWith(possibleOldLine)) {
                                    basic = basic.substring(0, basic.lastIndexOf(possibleOldLine));
                                    basic += "\n" + newValue;
                                } else {
                                    possibleOldLine = oldValue;

                                    if (basic.equals(possibleOldLine)) {
                                        basic = newValue;
                                    } else {
                                        basic += "\n" + newValue;
                                    }
                                }
                            }
                            bean.setProperty("hinweise", basic);
                        }
                    } catch (Exception e) {
                        LOG.error("Cannot set property 'hinweise'", e);
                    }
                }
                refresh();
                lblTableHeaderNb.setText(TABLE_HEADER_GEMISCHT);
            } else if (role == GupGupEditor.PERMISSION_WB) {
                for (final CidsBean bean : beans) {
                    try {
                        final String oldValue = (String)bean.getProperty("auflagen_wb");

                        if (newValue == null) {
                            newValue = "";
                        }

                        if ((oldValue == null) || !oldValue.equals(newValue)) {
                            bean.setProperty("auflagen_wb", newValue);
                            String basic = (String)bean.getProperty("hinweise");

                            if (basic == null) {
                                basic = newValue;
                            } else {
                                String possibleOldLine = "\n" + oldValue;

                                if (basic.endsWith(possibleOldLine)) {
                                    basic = basic.substring(0, basic.lastIndexOf(possibleOldLine));
                                    basic += "\n" + newValue;
                                } else {
                                    possibleOldLine = oldValue;

                                    if (basic.equals(possibleOldLine)) {
                                        basic = newValue;
                                    } else {
                                        basic += "\n" + newValue;
                                    }
                                }
                            }
                            bean.setProperty("hinweise", basic);
                        }
                    } catch (Exception e) {
                        LOG.error("Cannot set property 'hinweise'", e);
                    }
                }
                refresh();
                lblTableHeaderNb.setText(TABLE_HEADER_GEMISCHT);
            }
        }
    }
}
/**
 * DOCUMENT ME!
 *
 * @version  $Revision$, $Date$
 */
class DefaultTableHeaderCellRenderer extends DefaultTableCellRenderer {

    //~ Constructors -----------------------------------------------------------

    /**
     * Constructs a <code>DefaultTableHeaderCellRenderer</code>.
     *
     * <P>The horizontal alignment and text position are set as appropriate to a table header cell, and the opaque
     * property is set to false.</P>
     */
    public DefaultTableHeaderCellRenderer() {
        setHorizontalAlignment(CENTER);
        setHorizontalTextPosition(LEFT);
        setVerticalAlignment(BOTTOM);
        setOpaque(false);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Returns the default table header cell renderer.
     *
     * <P>If the column is sorted, the approapriate icon is retrieved from the current Look and Feel, and a border
     * appropriate to a table header cell is applied.</P>
     *
     * <P>Subclasses may overide this method to provide custom content or formatting.</P>
     *
     * @param   table       the <code>JTable</code>.
     * @param   value       the value to assign to the header cell
     * @param   isSelected  This parameter is ignored.
     * @param   hasFocus    This parameter is ignored.
     * @param   row         This parameter is ignored.
     * @param   column      the column of the header cell to render
     *
     * @return  the default table header cell renderer
     */
    @Override
    public Component getTableCellRendererComponent(final JTable table,
            final Object value,
            final boolean isSelected,
            final boolean hasFocus,
            final int row,
            final int column) {
        super.getTableCellRendererComponent(table, value,
            isSelected, hasFocus, row, column);
        final JTableHeader tableHeader = table.getTableHeader();
        if (tableHeader != null) {
            setForeground(tableHeader.getForeground());
        }
        setIcon(getIcon(table, column));
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        return this;
    }

    /**
     * Overloaded to return an icon suitable to the primary sorted column, or null if the column is not the primary sort
     * key.
     *
     * @param   table   the <code>JTable</code>.
     * @param   column  the column index.
     *
     * @return  the sort icon, or null if the column is unsorted.
     */
    protected Icon getIcon(final JTable table, final int column) {
        final RowSorter.SortKey sortKey = getSortKey(table, column);
        if ((sortKey != null) && (table.convertColumnIndexToView(sortKey.getColumn()) == column)) {
            switch (sortKey.getSortOrder()) {
                case ASCENDING: {
                    return UIManager.getIcon("Table.ascendingSortIcon");
                }
                case DESCENDING: {
                    return UIManager.getIcon("Table.descendingSortIcon");
                }
            }
        }
        return null;
    }

    /**
     * Returns the current sort key, or null if the column is unsorted.
     *
     * @param   table   the table
     * @param   column  the column index
     *
     * @return  the SortKey, or null if the column is unsorted
     */
    protected RowSorter.SortKey getSortKey(final JTable table, final int column) {
        final RowSorter rowSorter = table.getRowSorter();
        if (rowSorter == null) {
            return null;
        }

        final List sortedColumns = rowSorter.getSortKeys();
        if (sortedColumns.size() > 0) {
            return (RowSorter.SortKey)sortedColumns.get(0);
        }
        return null;
    }
}
