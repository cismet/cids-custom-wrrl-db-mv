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
import Sirius.navigator.method.MethodManager;
import Sirius.navigator.tools.CacheException;
import Sirius.navigator.tools.MetaObjectCache;
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;
import Sirius.server.newuser.User;

import org.apache.log4j.Logger;

import org.jdesktop.beansbinding.Converter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.metal.MetalToolTipUI;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.server.search.MassnahmenartSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.MassnahmenHistoryListModel;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.UnterhaltungsmassnahmeValidator;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.AbstractCidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.commons.concurrency.CismetConcurrency;

import de.cismet.tools.CismetThreadPool;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.WaitingDialogThread;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupUnterhaltungsmassnahmeEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    PropertyChangeListener,
    CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass GERAET_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "gup_geraet");
    private static final MetaClass GEWERK_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "gup_gewerk");
    private static final MetaClass EINSATZVARIANTE_MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_einsatzvariante");
    private static final MetaClass VERBLEIB_MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_material_verbleib");
    private static final MetaClass ZEITPUNKT_MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_unterhaltungsmassnahme_ausfuehrungszeitpunkt");
    private static final MetaClass INTERVALL_MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_massnahmenintervall");
    private static final MetaClass MASSNAHMENART_MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_massnahmenart");
    public static final int KOMPARTIMENT_SOHLE = 1;
    public static final int KOMPARTIMENT_UFER = 2;
    public static final int KOMPARTIMENT_UMFELD = 3;
    private static final int INTERVAL_TWO_TIMES = 4;
    private static final int AL_ANF = 1;
    private static final int NOT_AL_ANF = 2;
    private static final Logger LOG = Logger.getLogger(GupUnterhaltungsmassnahmeEditor.class);
    private static MassnahmenHistoryListModel historyModel;
    private static int lastKompartiment;
    private static CidsBean[] massnahmnenObjects;

    //~ Instance fields --------------------------------------------------------

    private boolean noRefresh = false;

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private List<CidsBean> massnahmen = null;
    private UnterhaltungsmassnahmeValidator validator;
    private int kompartiment;
    private List<Node> massnartList = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butRefresh;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEinsatz;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGeraet;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGewerk;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbIntervall;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbJahr;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbVerbleib;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbZeitpunkt;
    private javax.swing.JPanel flowPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JScrollPane jscEval;
    private javax.swing.JLabel lblArbeitsflaecheMeas;
    private javax.swing.JLabel lblBearbeiter1;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblBlMeas;
    private javax.swing.JLabel lblBnMeas;
    private javax.swing.JLabel lblBoeschungslaenge;
    private javax.swing.JLabel lblBoeschungsneigung;
    private javax.swing.JLabel lblCbmMeas;
    private javax.swing.JLabel lblDbMeas;
    private javax.swing.JLabel lblDeichkronenbreite;
    private javax.swing.JLabel lblEinsatz;
    private javax.swing.JLabel lblGeraet;
    private javax.swing.JLabel lblGewerk;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblIntervall;
    private javax.swing.JLabel lblJahr;
    private javax.swing.JLabel lblMDreiMeas;
    private javax.swing.JLabel lblMZweiMeas;
    private javax.swing.JLabel lblMassnahme;
    private javax.swing.JLabel lblRandstreifenbreite;
    private javax.swing.JLabel lblSbMeas;
    private javax.swing.JLabel lblSchnitttiefeMeas;
    private javax.swing.JLabel lblSohlbreite;
    private javax.swing.JLabel lblStMeas;
    private javax.swing.JLabel lblStueck;
    private javax.swing.JLabel lblStundenMeas;
    private javax.swing.JLabel lblTeillaengeMeas;
    private javax.swing.JLabel lblValid;
    private javax.swing.JLabel lblValidLab;
    private javax.swing.JLabel lblVbMeas;
    private javax.swing.JLabel lblVerbleib;
    private javax.swing.JLabel lblVorlandbreite;
    private javax.swing.JLabel lblZeitpunkt;
    private javax.swing.JLabel lblZeitpunkt2;
    private javax.swing.JLabel lblrsbMeas;
    private javax.swing.JLabel lclArbeitsflaeche;
    private javax.swing.JLabel lclCbmprom;
    private javax.swing.JLabel lclMDrei;
    private javax.swing.JLabel lclMZwei;
    private javax.swing.JLabel lclSchnitttiefe;
    private javax.swing.JLabel lclStunden;
    private javax.swing.JLabel lclTeillaenge;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panArbeitsflaeche;
    private javax.swing.JPanel panBoeschungslaenge;
    private javax.swing.JPanel panBoeschungsneigung;
    private javax.swing.JPanel panCbmProM;
    private javax.swing.JPanel panDeichkronenbreite;
    private javax.swing.JPanel panMDrei;
    private javax.swing.JPanel panMZwei;
    private javax.swing.JPanel panRandstreifen;
    private javax.swing.JPanel panSchnitttiefe;
    private javax.swing.JPanel panSohlbreite;
    private javax.swing.JPanel panStueck;
    private javax.swing.JPanel panStunden;
    private javax.swing.JPanel panTeillaenge;
    private javax.swing.JPanel panValid;
    private javax.swing.JPanel panVorlandbreite;
    private javax.swing.JScrollPane spBemerkung;
    private javax.swing.JTextArea textEval;
    private javax.swing.JTextField txtArbeitsflaeche;
    private javax.swing.JTextField txtBearbeiter;
    private javax.swing.JTextField txtBoeschungslaenge;
    private javax.swing.JTextField txtBoeschungsneigung;
    private javax.swing.JTextField txtCbmProM;
    private javax.swing.JTextField txtDeichkronenbreite;
    private javax.swing.JTextField txtMDrei;
    private javax.swing.JTextField txtMZwei;
    private javax.swing.JTextField txtMassnahme;
    private javax.swing.JTextField txtRandstreifenbreite;
    private javax.swing.JTextField txtSchnitttiefe;
    private javax.swing.JTextField txtSecondTime;
    private javax.swing.JTextField txtSohlbreite;
    private javax.swing.JTextField txtStueck;
    private javax.swing.JTextField txtStunden;
    private javax.swing.JTextField txtTeillaenge;
    private javax.swing.JTextField txtVorlandbreite;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupUnterhaltungsmassnahmeEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupUnterhaltungsmassnahmeEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        linearReferencedLineEditor = (readOnly) ? new LinearReferencedLineRenderer() : new LinearReferencedLineEditor();
        linearReferencedLineEditor.setLineField("linie");
        linearReferencedLineEditor.setOtherLinesEnabled(true);
        linearReferencedLineEditor.setShowOtherInDialog(true);
        linearReferencedLineEditor.setDrawingFeaturesEnabled(true);
        initComponents();
        panValid.setVisible(false);
        RendererTools.makeReadOnly(txtBearbeiter);
        RendererTools.makeReadOnly(txtMassnahme);
        butRefresh.setVisible(!readOnly);

        RendererTools.makeReadOnly(txtSecondTime);
        if (readOnly) {
            RendererTools.makeReadOnly(cbGeraet);
            RendererTools.makeReadOnly(cbIntervall);
            RendererTools.makeReadOnly(cbVerbleib);
            RendererTools.makeReadOnly(cbJahr);
            RendererTools.makeReadOnly(txtBoeschungslaenge);
            RendererTools.makeReadOnly(txtBoeschungsneigung);
            RendererTools.makeReadOnly(txtDeichkronenbreite);
            RendererTools.makeReadOnly(txtRandstreifenbreite);
            RendererTools.makeReadOnly(txtSohlbreite);
            RendererTools.makeReadOnly(txtVorlandbreite);
            RendererTools.makeReadOnly(txtCbmProM);
            RendererTools.makeReadOnly(txtStueck);
            RendererTools.makeReadOnly(jTextArea1);
            RendererTools.makeReadOnly(cbEinsatz);
            RendererTools.makeReadOnly(cbGewerk);
            RendererTools.makeReadOnly(cbZeitpunkt);
        } else {
            try {
                new CidsBeanDropTarget(this);
            } catch (final Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
                }
            }
        }

        RendererTools.makeReadOnly(textEval);
        jscEval.setVisible(false);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  the historyModel
     */
    public static MassnahmenHistoryListModel getHistoryModel() {
        return historyModel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aHistoryModel  the historyModel to set
     */
    public static void setHistoryModel(final MassnahmenHistoryListModel aHistoryModel) {
        historyModel = aHistoryModel;
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

        lblZeitpunkt = new javax.swing.JLabel();
        lblMassnahme = new javax.swing.JLabel();
        lblJahr = new javax.swing.JLabel();
        lblBemerkung = new javax.swing.JLabel();
        spBemerkung = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        linearReferencedLineEditor = linearReferencedLineEditor;
        jPanel1 = new javax.swing.JPanel();
        lblInfo = new javax.swing.JLabel();
        flowPanel = new javax.swing.JPanel();
        panBoeschungslaenge = new javax.swing.JPanel();
        lblBoeschungslaenge = new javax.swing.JLabel();
        txtBoeschungslaenge = new javax.swing.JTextField();
        lblBlMeas = new javax.swing.JLabel();
        panRandstreifen = new javax.swing.JPanel();
        lblRandstreifenbreite = new javax.swing.JLabel();
        txtRandstreifenbreite = new javax.swing.JTextField();
        lblrsbMeas = new javax.swing.JLabel();
        panBoeschungsneigung = new javax.swing.JPanel();
        lblBoeschungsneigung = new javax.swing.JLabel();
        txtBoeschungsneigung = new javax.swing.JTextField();
        lblBnMeas = new javax.swing.JLabel();
        panSohlbreite = new javax.swing.JPanel();
        lblSohlbreite = new javax.swing.JLabel();
        txtSohlbreite = new javax.swing.JTextField();
        lblSbMeas = new javax.swing.JLabel();
        panDeichkronenbreite = new javax.swing.JPanel();
        lblDeichkronenbreite = new javax.swing.JLabel();
        txtDeichkronenbreite = new javax.swing.JTextField();
        lblDbMeas = new javax.swing.JLabel();
        panVorlandbreite = new javax.swing.JPanel();
        lblVorlandbreite = new javax.swing.JLabel();
        txtVorlandbreite = new javax.swing.JTextField();
        lblVbMeas = new javax.swing.JLabel();
        panStueck = new javax.swing.JPanel();
        lblStueck = new javax.swing.JLabel();
        txtStueck = new javax.swing.JTextField();
        lblStMeas = new javax.swing.JLabel();
        panCbmProM = new javax.swing.JPanel();
        lclCbmprom = new javax.swing.JLabel();
        txtCbmProM = new javax.swing.JTextField();
        lblCbmMeas = new javax.swing.JLabel();
        lblValidLab = new javax.swing.JLabel();
        panValid = new javax.swing.JPanel();
        lblValid = new javax.swing.JLabel();
        jscEval = new javax.swing.JScrollPane();
        textEval = new javax.swing.JTextArea();
        panStunden = new javax.swing.JPanel();
        lclStunden = new javax.swing.JLabel();
        txtStunden = new javax.swing.JTextField();
        lblStundenMeas = new javax.swing.JLabel();
        panSchnitttiefe = new javax.swing.JPanel();
        lclSchnitttiefe = new javax.swing.JLabel();
        txtSchnitttiefe = new javax.swing.JTextField();
        lblSchnitttiefeMeas = new javax.swing.JLabel();
        panArbeitsflaeche = new javax.swing.JPanel();
        lclArbeitsflaeche = new javax.swing.JLabel();
        txtArbeitsflaeche = new javax.swing.JTextField();
        lblArbeitsflaecheMeas = new javax.swing.JLabel();
        panTeillaenge = new javax.swing.JPanel();
        lclTeillaenge = new javax.swing.JLabel();
        txtTeillaenge = new javax.swing.JTextField();
        lblTeillaengeMeas = new javax.swing.JLabel();
        panMZwei = new javax.swing.JPanel();
        lclMZwei = new javax.swing.JLabel();
        txtMZwei = new javax.swing.JTextField();
        lblMZweiMeas = new javax.swing.JLabel();
        panMDrei = new javax.swing.JPanel();
        lclMDrei = new javax.swing.JLabel();
        txtMDrei = new javax.swing.JTextField();
        lblMDreiMeas = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        cbJahr = new ScrollableComboBox(INTERVALL_MC, true, false);
        lblIntervall = new javax.swing.JLabel();
        cbIntervall = new ScrollableComboBox(INTERVALL_MC, true, false);
        lblBearbeiter1 = new javax.swing.JLabel();
        txtBearbeiter = new javax.swing.JTextField();
        lblZeitpunkt2 = new javax.swing.JLabel();
        cbVerbleib = new ScrollableComboBox(VERBLEIB_MC, true, false);
        lblVerbleib = new javax.swing.JLabel();
        lblEinsatz = new javax.swing.JLabel();
        cbEinsatz = new ScrollableComboBox(EINSATZVARIANTE_MC, true, false);
        lblGeraet = new javax.swing.JLabel();
        cbGewerk = new ScrollableComboBox();
        lblGewerk = new javax.swing.JLabel();
        cbZeitpunkt = new ScrollableComboBox(ZEITPUNKT_MC, true, false);
        txtMassnahme = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        butRefresh = new javax.swing.JButton();
        txtSecondTime = new javax.swing.JTextField();
        cbGeraet = new ScrollableComboBox();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(894, 400));
        setLayout(new java.awt.GridBagLayout());

        lblZeitpunkt.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblZeitpunkt.text")); // NOI18N
        lblZeitpunkt.setMaximumSize(new java.awt.Dimension(90, 17));
        lblZeitpunkt.setMinimumSize(new java.awt.Dimension(90, 17));
        lblZeitpunkt.setPreferredSize(new java.awt.Dimension(90, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblZeitpunkt, gridBagConstraints);

        lblMassnahme.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblMassnahme.text")); // NOI18N
        lblMassnahme.setMaximumSize(new java.awt.Dimension(90, 17));
        lblMassnahme.setMinimumSize(new java.awt.Dimension(90, 17));
        lblMassnahme.setPreferredSize(new java.awt.Dimension(90, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 5);
        add(lblMassnahme, gridBagConstraints);

        lblJahr.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblJahr.text")); // NOI18N
        lblJahr.setMaximumSize(new java.awt.Dimension(100, 17));
        lblJahr.setMinimumSize(new java.awt.Dimension(100, 17));
        lblJahr.setPreferredSize(new java.awt.Dimension(100, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblJahr, gridBagConstraints);

        lblBemerkung.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblBemerkung.text")); // NOI18N
        lblBemerkung.setMaximumSize(new java.awt.Dimension(90, 40));
        lblBemerkung.setMinimumSize(new java.awt.Dimension(90, 40));
        lblBemerkung.setPreferredSize(new java.awt.Dimension(90, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblBemerkung, gridBagConstraints);

        spBemerkung.setMinimumSize(new java.awt.Dimension(280, 90));
        spBemerkung.setPreferredSize(new java.awt.Dimension(300, 90));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(2);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hinweise}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        spBemerkung.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(spBemerkung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        add(linearReferencedLineEditor, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblInfo.setFont(new java.awt.Font("Ubuntu", 1, 15));      // NOI18N
        lblInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblInfo.text")); // NOI18N
        lblInfo.setMaximumSize(new java.awt.Dimension(250, 17));
        lblInfo.setMinimumSize(new java.awt.Dimension(250, 17));
        lblInfo.setPreferredSize(new java.awt.Dimension(250, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanel1.add(lblInfo, gridBagConstraints);

        flowPanel.setMinimumSize(new java.awt.Dimension(250, 250));
        flowPanel.setOpaque(false);
        flowPanel.setPreferredSize(new java.awt.Dimension(275, 250));
        flowPanel.setLayout(new java.awt.GridBagLayout());

        panBoeschungslaenge.setOpaque(false);
        panBoeschungslaenge.setLayout(new java.awt.GridBagLayout());

        lblBoeschungslaenge.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblBoeschungslaenge.text")); // NOI18N
        lblBoeschungslaenge.setMaximumSize(new java.awt.Dimension(150, 17));
        lblBoeschungslaenge.setMinimumSize(new java.awt.Dimension(150, 17));
        lblBoeschungslaenge.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panBoeschungslaenge.add(lblBoeschungslaenge, gridBagConstraints);

        txtBoeschungslaenge.setMaximumSize(new java.awt.Dimension(100, 20));
        txtBoeschungslaenge.setMinimumSize(new java.awt.Dimension(60, 20));
        txtBoeschungslaenge.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.boeschungslaenge}"),
                txtBoeschungslaenge,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panBoeschungslaenge.add(txtBoeschungslaenge, gridBagConstraints);

        lblBlMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblBlMeas.text")); // NOI18N
        lblBlMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblBlMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblBlMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panBoeschungslaenge.add(lblBlMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panBoeschungslaenge, gridBagConstraints);

        panRandstreifen.setOpaque(false);
        panRandstreifen.setLayout(new java.awt.GridBagLayout());

        lblRandstreifenbreite.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblRandstreifenbreite.text")); // NOI18N
        lblRandstreifenbreite.setMaximumSize(new java.awt.Dimension(150, 17));
        lblRandstreifenbreite.setMinimumSize(new java.awt.Dimension(150, 17));
        lblRandstreifenbreite.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panRandstreifen.add(lblRandstreifenbreite, gridBagConstraints);

        txtRandstreifenbreite.setMaximumSize(new java.awt.Dimension(100, 20));
        txtRandstreifenbreite.setMinimumSize(new java.awt.Dimension(60, 20));
        txtRandstreifenbreite.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.randstreifenbreite}"),
                txtRandstreifenbreite,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panRandstreifen.add(txtRandstreifenbreite, gridBagConstraints);

        lblrsbMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblrsbMeas.text")); // NOI18N
        lblrsbMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblrsbMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblrsbMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panRandstreifen.add(lblrsbMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panRandstreifen, gridBagConstraints);

        panBoeschungsneigung.setOpaque(false);
        panBoeschungsneigung.setLayout(new java.awt.GridBagLayout());

        lblBoeschungsneigung.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblBoeschungsneigung.text")); // NOI18N
        lblBoeschungsneigung.setMaximumSize(new java.awt.Dimension(150, 17));
        lblBoeschungsneigung.setMinimumSize(new java.awt.Dimension(150, 17));
        lblBoeschungsneigung.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panBoeschungsneigung.add(lblBoeschungsneigung, gridBagConstraints);

        txtBoeschungsneigung.setMaximumSize(new java.awt.Dimension(100, 20));
        txtBoeschungsneigung.setMinimumSize(new java.awt.Dimension(60, 20));
        txtBoeschungsneigung.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.boeschungsbreite}"),
                txtBoeschungsneigung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panBoeschungsneigung.add(txtBoeschungsneigung, gridBagConstraints);

        lblBnMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblBnMeas.text")); // NOI18N
        lblBnMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblBnMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblBnMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panBoeschungsneigung.add(lblBnMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panBoeschungsneigung, gridBagConstraints);

        panSohlbreite.setOpaque(false);
        panSohlbreite.setLayout(new java.awt.GridBagLayout());

        lblSohlbreite.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblSohlbreite.text")); // NOI18N
        lblSohlbreite.setMaximumSize(new java.awt.Dimension(150, 17));
        lblSohlbreite.setMinimumSize(new java.awt.Dimension(150, 17));
        lblSohlbreite.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panSohlbreite.add(lblSohlbreite, gridBagConstraints);

        txtSohlbreite.setMaximumSize(new java.awt.Dimension(100, 20));
        txtSohlbreite.setMinimumSize(new java.awt.Dimension(60, 20));
        txtSohlbreite.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sohlbreite}"),
                txtSohlbreite,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panSohlbreite.add(txtSohlbreite, gridBagConstraints);

        lblSbMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblSbMeas.text")); // NOI18N
        lblSbMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblSbMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblSbMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panSohlbreite.add(lblSbMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panSohlbreite, gridBagConstraints);

        panDeichkronenbreite.setOpaque(false);
        panDeichkronenbreite.setLayout(new java.awt.GridBagLayout());

        lblDeichkronenbreite.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblDeichkronenbreite.text")); // NOI18N
        lblDeichkronenbreite.setMaximumSize(new java.awt.Dimension(150, 17));
        lblDeichkronenbreite.setMinimumSize(new java.awt.Dimension(150, 17));
        lblDeichkronenbreite.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panDeichkronenbreite.add(lblDeichkronenbreite, gridBagConstraints);

        txtDeichkronenbreite.setMaximumSize(new java.awt.Dimension(100, 20));
        txtDeichkronenbreite.setMinimumSize(new java.awt.Dimension(60, 20));
        txtDeichkronenbreite.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.deichkronenbreite}"),
                txtDeichkronenbreite,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panDeichkronenbreite.add(txtDeichkronenbreite, gridBagConstraints);

        lblDbMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblDbMeas.text")); // NOI18N
        lblDbMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblDbMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblDbMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panDeichkronenbreite.add(lblDbMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panDeichkronenbreite, gridBagConstraints);

        panVorlandbreite.setOpaque(false);
        panVorlandbreite.setLayout(new java.awt.GridBagLayout());

        lblVorlandbreite.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblVorlandbreite.text")); // NOI18N
        lblVorlandbreite.setMaximumSize(new java.awt.Dimension(150, 17));
        lblVorlandbreite.setMinimumSize(new java.awt.Dimension(150, 17));
        lblVorlandbreite.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panVorlandbreite.add(lblVorlandbreite, gridBagConstraints);

        txtVorlandbreite.setMaximumSize(new java.awt.Dimension(100, 20));
        txtVorlandbreite.setMinimumSize(new java.awt.Dimension(60, 20));
        txtVorlandbreite.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.vorlandbreite}"),
                txtVorlandbreite,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panVorlandbreite.add(txtVorlandbreite, gridBagConstraints);

        lblVbMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblVbMeas.text")); // NOI18N
        lblVbMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblVbMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblVbMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panVorlandbreite.add(lblVbMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panVorlandbreite, gridBagConstraints);

        panStueck.setOpaque(false);
        panStueck.setLayout(new java.awt.GridBagLayout());

        lblStueck.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblStueck.text")); // NOI18N
        lblStueck.setMaximumSize(new java.awt.Dimension(150, 17));
        lblStueck.setMinimumSize(new java.awt.Dimension(150, 17));
        lblStueck.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panStueck.add(lblStueck, gridBagConstraints);

        txtStueck.setMaximumSize(new java.awt.Dimension(100, 20));
        txtStueck.setMinimumSize(new java.awt.Dimension(60, 20));
        txtStueck.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stueck}"),
                txtStueck,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panStueck.add(txtStueck, gridBagConstraints);

        lblStMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblStMeas.text")); // NOI18N
        lblStMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblStMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblStMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panStueck.add(lblStMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panStueck, gridBagConstraints);

        panCbmProM.setOpaque(false);
        panCbmProM.setLayout(new java.awt.GridBagLayout());

        lclCbmprom.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lclCbmprom.text")); // NOI18N
        lclCbmprom.setMaximumSize(new java.awt.Dimension(150, 17));
        lclCbmprom.setMinimumSize(new java.awt.Dimension(150, 17));
        lclCbmprom.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panCbmProM.add(lclCbmprom, gridBagConstraints);

        txtCbmProM.setMaximumSize(new java.awt.Dimension(100, 20));
        txtCbmProM.setMinimumSize(new java.awt.Dimension(60, 20));
        txtCbmProM.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.cbmprom}"),
                txtCbmProM,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panCbmProM.add(txtCbmProM, gridBagConstraints);

        lblCbmMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblCbmMeas.text")); // NOI18N
        lblCbmMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblCbmMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblCbmMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panCbmProM.add(lblCbmMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panCbmProM, gridBagConstraints);

        lblValidLab.setFont(new java.awt.Font("Ubuntu", 1, 15));      // NOI18N
        lblValidLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValidLab.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblValidLab.text")); // NOI18N
        lblValidLab.setPreferredSize(new java.awt.Dimension(210, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        flowPanel.add(lblValidLab, gridBagConstraints);

        panValid.setPreferredSize(new java.awt.Dimension(210, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panValid, gridBagConstraints);

        lblValid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValid.setMaximumSize(new java.awt.Dimension(128, 128));
        lblValid.setMinimumSize(new java.awt.Dimension(128, 128));
        lblValid.setPreferredSize(new java.awt.Dimension(128, 128));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        flowPanel.add(lblValid, gridBagConstraints);

        jscEval.setMaximumSize(new java.awt.Dimension(235, 100));
        jscEval.setMinimumSize(new java.awt.Dimension(235, 100));
        jscEval.setPreferredSize(new java.awt.Dimension(235, 100));

        textEval.setColumns(18);
        textEval.setRows(3);
        jscEval.setViewportView(textEval);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        flowPanel.add(jscEval, gridBagConstraints);

        panStunden.setOpaque(false);
        panStunden.setLayout(new java.awt.GridBagLayout());

        lclStunden.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lclStunden.text")); // NOI18N
        lclStunden.setMaximumSize(new java.awt.Dimension(150, 17));
        lclStunden.setMinimumSize(new java.awt.Dimension(150, 17));
        lclStunden.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panStunden.add(lclStunden, gridBagConstraints);

        txtStunden.setMaximumSize(new java.awt.Dimension(100, 20));
        txtStunden.setMinimumSize(new java.awt.Dimension(60, 20));
        txtStunden.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stunden}"),
                txtStunden,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panStunden.add(txtStunden, gridBagConstraints);

        lblStundenMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblStundenMeas.text")); // NOI18N
        lblStundenMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblStundenMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblStundenMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panStunden.add(lblStundenMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panStunden, gridBagConstraints);

        panSchnitttiefe.setOpaque(false);
        panSchnitttiefe.setLayout(new java.awt.GridBagLayout());

        lclSchnitttiefe.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lclSchnitttiefe.text")); // NOI18N
        lclSchnitttiefe.setMaximumSize(new java.awt.Dimension(150, 17));
        lclSchnitttiefe.setMinimumSize(new java.awt.Dimension(150, 17));
        lclSchnitttiefe.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panSchnitttiefe.add(lclSchnitttiefe, gridBagConstraints);

        txtSchnitttiefe.setMaximumSize(new java.awt.Dimension(100, 20));
        txtSchnitttiefe.setMinimumSize(new java.awt.Dimension(60, 20));
        txtSchnitttiefe.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.arbeitsflaeche}"),
                txtSchnitttiefe,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panSchnitttiefe.add(txtSchnitttiefe, gridBagConstraints);

        lblSchnitttiefeMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblSchnitttiefeMeas.text")); // NOI18N
        lblSchnitttiefeMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblSchnitttiefeMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblSchnitttiefeMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panSchnitttiefe.add(lblSchnitttiefeMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panSchnitttiefe, gridBagConstraints);

        panArbeitsflaeche.setOpaque(false);
        panArbeitsflaeche.setLayout(new java.awt.GridBagLayout());

        lclArbeitsflaeche.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lclArbeitsflaeche.text")); // NOI18N
        lclArbeitsflaeche.setMaximumSize(new java.awt.Dimension(150, 17));
        lclArbeitsflaeche.setMinimumSize(new java.awt.Dimension(150, 17));
        lclArbeitsflaeche.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panArbeitsflaeche.add(lclArbeitsflaeche, gridBagConstraints);

        txtArbeitsflaeche.setMaximumSize(new java.awt.Dimension(100, 20));
        txtArbeitsflaeche.setMinimumSize(new java.awt.Dimension(60, 20));
        txtArbeitsflaeche.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panArbeitsflaeche.add(txtArbeitsflaeche, gridBagConstraints);

        lblArbeitsflaecheMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblArbeitsflaecheMeas.text")); // NOI18N
        lblArbeitsflaecheMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblArbeitsflaecheMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblArbeitsflaecheMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panArbeitsflaeche.add(lblArbeitsflaecheMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panArbeitsflaeche, gridBagConstraints);

        panTeillaenge.setOpaque(false);
        panTeillaenge.setLayout(new java.awt.GridBagLayout());

        lclTeillaenge.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lclTeillaenge.text")); // NOI18N
        lclTeillaenge.setMaximumSize(new java.awt.Dimension(150, 17));
        lclTeillaenge.setMinimumSize(new java.awt.Dimension(150, 17));
        lclTeillaenge.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panTeillaenge.add(lclTeillaenge, gridBagConstraints);

        txtTeillaenge.setMaximumSize(new java.awt.Dimension(100, 20));
        txtTeillaenge.setMinimumSize(new java.awt.Dimension(60, 20));
        txtTeillaenge.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.teillaenge}"),
                txtTeillaenge,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panTeillaenge.add(txtTeillaenge, gridBagConstraints);

        lblTeillaengeMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblTeillaengeMeas.text")); // NOI18N
        lblTeillaengeMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblTeillaengeMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblTeillaengeMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panTeillaenge.add(lblTeillaengeMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panTeillaenge, gridBagConstraints);

        panMZwei.setOpaque(false);
        panMZwei.setLayout(new java.awt.GridBagLayout());

        lclMZwei.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lclMZwei.text")); // NOI18N
        lclMZwei.setMaximumSize(new java.awt.Dimension(150, 17));
        lclMZwei.setMinimumSize(new java.awt.Dimension(150, 17));
        lclMZwei.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panMZwei.add(lclMZwei, gridBagConstraints);

        txtMZwei.setMaximumSize(new java.awt.Dimension(100, 20));
        txtMZwei.setMinimumSize(new java.awt.Dimension(60, 20));
        txtMZwei.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.m_zwei}"),
                txtMZwei,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panMZwei.add(txtMZwei, gridBagConstraints);

        lblMZweiMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblMZweiMeas.text")); // NOI18N
        lblMZweiMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblMZweiMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblMZweiMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panMZwei.add(lblMZweiMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panMZwei, gridBagConstraints);

        panMDrei.setOpaque(false);
        panMDrei.setLayout(new java.awt.GridBagLayout());

        lclMDrei.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lclMDrei.text")); // NOI18N
        lclMDrei.setMaximumSize(new java.awt.Dimension(150, 17));
        lclMDrei.setMinimumSize(new java.awt.Dimension(150, 17));
        lclMDrei.setPreferredSize(new java.awt.Dimension(150, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panMDrei.add(lclMDrei, gridBagConstraints);

        txtMDrei.setMaximumSize(new java.awt.Dimension(100, 20));
        txtMDrei.setMinimumSize(new java.awt.Dimension(60, 20));
        txtMDrei.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.m_drei}"),
                txtMDrei,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 2);
        panMDrei.add(txtMDrei, gridBagConstraints);

        lblMDreiMeas.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblMDreiMeas.text")); // NOI18N
        lblMDreiMeas.setMaximumSize(new java.awt.Dimension(25, 17));
        lblMDreiMeas.setMinimumSize(new java.awt.Dimension(25, 17));
        lblMDreiMeas.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panMDrei.add(lblMDreiMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        flowPanel.add(panMDrei, gridBagConstraints);

        jPanel2.setMinimumSize(new java.awt.Dimension(1, 1));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(1, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 20;
        gridBagConstraints.weighty = 0.01;
        flowPanel.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(flowPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        add(jSeparator1, gridBagConstraints);

        cbJahr.setMinimumSize(new java.awt.Dimension(200, 20));
        cbJahr.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.jahr}"),
                cbJahr,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbJahr.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbJahrItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbJahr, gridBagConstraints);

        lblIntervall.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblIntervall.text")); // NOI18N
        lblIntervall.setMaximumSize(new java.awt.Dimension(60, 17));
        lblIntervall.setMinimumSize(new java.awt.Dimension(60, 17));
        lblIntervall.setPreferredSize(new java.awt.Dimension(60, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblIntervall, gridBagConstraints);

        cbIntervall.setMinimumSize(new java.awt.Dimension(200, 20));
        cbIntervall.setPreferredSize(new java.awt.Dimension(200, 20));
        cbIntervall.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbIntervallItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbIntervall, gridBagConstraints);

        lblBearbeiter1.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblBearbeiter1.text")); // NOI18N
        lblBearbeiter1.setMaximumSize(new java.awt.Dimension(90, 17));
        lblBearbeiter1.setMinimumSize(new java.awt.Dimension(90, 17));
        lblBearbeiter1.setPreferredSize(new java.awt.Dimension(90, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblBearbeiter1, gridBagConstraints);

        txtBearbeiter.setMaximumSize(new java.awt.Dimension(200, 20));
        txtBearbeiter.setMinimumSize(new java.awt.Dimension(200, 20));
        txtBearbeiter.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bearbeiter}"),
                txtBearbeiter,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(txtBearbeiter, gridBagConstraints);

        lblZeitpunkt2.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblZeitpunkt2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblZeitpunkt2, gridBagConstraints);

        cbVerbleib.setMinimumSize(new java.awt.Dimension(100, 20));
        cbVerbleib.setPreferredSize(new java.awt.Dimension(200, 20));
        cbVerbleib.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbVerbleibItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbVerbleib, gridBagConstraints);

        lblVerbleib.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblVerbleib.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblVerbleib, gridBagConstraints);

        lblEinsatz.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblEinsatz.text")); // NOI18N
        lblEinsatz.setMaximumSize(new java.awt.Dimension(100, 17));
        lblEinsatz.setMinimumSize(new java.awt.Dimension(100, 17));
        lblEinsatz.setPreferredSize(new java.awt.Dimension(100, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblEinsatz, gridBagConstraints);

        cbEinsatz.setMinimumSize(new java.awt.Dimension(200, 20));
        cbEinsatz.setPreferredSize(new java.awt.Dimension(200, 20));
        cbEinsatz.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbEinsatzItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbEinsatz, gridBagConstraints);

        lblGeraet.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblGeraet.text")); // NOI18N
        lblGeraet.setMaximumSize(new java.awt.Dimension(60, 17));
        lblGeraet.setMinimumSize(new java.awt.Dimension(60, 17));
        lblGeraet.setPreferredSize(new java.awt.Dimension(60, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblGeraet, gridBagConstraints);

        cbGewerk.setMinimumSize(new java.awt.Dimension(200, 20));
        cbGewerk.setPreferredSize(new java.awt.Dimension(200, 20));
        cbGewerk.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbGewerkItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbGewerk, gridBagConstraints);

        lblGewerk.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblGewerk.text")); // NOI18N
        lblGewerk.setMaximumSize(new java.awt.Dimension(100, 17));
        lblGewerk.setMinimumSize(new java.awt.Dimension(100, 17));
        lblGewerk.setPreferredSize(new java.awt.Dimension(100, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblGewerk, gridBagConstraints);

        cbZeitpunkt.setMinimumSize(new java.awt.Dimension(200, 20));
        cbZeitpunkt.setPreferredSize(new java.awt.Dimension(100, 20));
        cbZeitpunkt.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbZeitpunktItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbZeitpunkt, gridBagConstraints);

        txtMassnahme.setMaximumSize(new java.awt.Dimension(200, 20));
        txtMassnahme.setMinimumSize(new java.awt.Dimension(200, 20));
        txtMassnahme.setPreferredSize(new java.awt.Dimension(200, 20));
        txtMassnahme.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    txtMassnahmeMouseClicked(evt);
                }
            });
        txtMassnahme.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    txtMassnahmeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        add(txtMassnahme, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jSeparator2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jSeparator3, gridBagConstraints);

        butRefresh.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.butRefresh.text")); // NOI18N
        butRefresh.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    butRefreshActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 2, 3, 2);
        jPanel3.add(butRefresh, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        add(jPanel3, gridBagConstraints);

        txtSecondTime.setMaximumSize(new java.awt.Dimension(200, 20));
        txtSecondTime.setMinimumSize(new java.awt.Dimension(200, 20));
        txtSecondTime.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massnahme.zweiter_ausfuehrungszeitpunkt}"),
                txtSecondTime,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        binding.setConverter(new ReadOnlyBeanConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(txtSecondTime, gridBagConstraints);

        cbGeraet.setMinimumSize(new java.awt.Dimension(200, 20));
        cbGeraet.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.geraet}"),
                cbGeraet,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGeraet.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbGeraetItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbGeraet, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbJahrItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbJahrItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                changeBearbeiter();
            }
        }
    }                                                                         //GEN-LAST:event_cbJahrItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbIntervallItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbIntervallItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
//                final CidsBean bean = (CidsBean)evt.getItem();
//                if (!readOnly) {
//                    cbZeitpunkt2.setEnabled((bean != null)
//                                && bean.getProperty("id").equals(INTERVAL_TWO_TIMES));
//                }
                refreshMassnahme();
            }
        }
    } //GEN-LAST:event_cbIntervallItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbVerbleibItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbVerbleibItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                refreshMassnahme();
            }
        }
    }                                                                             //GEN-LAST:event_cbVerbleibItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbEinsatzItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbEinsatzItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                refreshMassnahme();
            }
        }
    }                                                                            //GEN-LAST:event_cbEinsatzItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbGewerkItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbGewerkItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                refreshMassnahme();
            }
        }
    }                                                                           //GEN-LAST:event_cbGewerkItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbZeitpunktItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbZeitpunktItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                refreshMassnahme();
            }
        }
    }                                                                              //GEN-LAST:event_cbZeitpunktItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtMassnahmeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_txtMassnahmeActionPerformed
    }                                                                                //GEN-LAST:event_txtMassnahmeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void txtMassnahmeMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_txtMassnahmeMouseClicked
        if ((txtMassnahme.getCursor().getType() == Cursor.HAND_CURSOR)) {
            if (massnartList != null) {
                MethodManager.getManager()
                        .showSearchResults(null, massnartList.toArray(new Node[massnartList.size()]), false);
                MethodManager.getManager().showSearchResults();
            } else {
                final WaitingDialogThread<List<Node>> t = new WaitingDialogThread<List<Node>>(StaticSwingTools
                                .getParentFrame(this),
                        true,
                        "Lade Maßnahmen",
                        null,
                        100) {

                        @Override
                        protected List<Node> doInBackground() throws Exception {
                            final String intervall = ((cbIntervall.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbIntervall.getSelectedItem()).getProperty("id")) : null);
                            final String einsatzvariante = ((cbEinsatz.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbEinsatz.getSelectedItem()).getProperty("id")) : null);
                            final String ausfuehrungszeitpunkt = ((cbZeitpunkt.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbZeitpunkt.getSelectedItem()).getProperty("id")) : null);
                            final String gewerk = ((cbGewerk.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbGewerk.getSelectedItem()).getProperty("id")) : null);
                            final String verbleib = ((cbVerbleib.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbVerbleib.getSelectedItem()).getProperty("id")) : null);

                            // Geraet und zweiter Ausfuehrungszeitpunkt soll ignoriert werden
                            final AbstractCidsServerSearch search = new MassnahmenartSearch(
                                    intervall,
                                    einsatzvariante,
                                    null,
                                    ausfuehrungszeitpunkt,
                                    null,
                                    gewerk,
                                    verbleib,
                                    String.valueOf(kompartiment),
                                    false);
                            final Collection res = SessionManager.getProxy()
                                        .customServerSearch(SessionManager.getSession().getUser(), search);
                            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;
                            final List<Node> validMassnartList = new ArrayList<Node>();

                            if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                                final int classId = MASSNAHMENART_MC.getId();

                                for (final ArrayList massnData : resArray) {
                                    validMassnartList.add(new MetaObjectNode(
                                            WRRLUtil.DOMAIN_NAME,
                                            (Integer)massnData.get(0),
                                            classId,
                                            (String)massnData.get(1),
                                            null,
                                            null));
                                }
                            }

                            return validMassnartList;
                        }

                        @Override
                        protected void done() {
                            try {
                                final List<Node> validMassnartList = get();
                                MethodManager.getManager()
                                        .showSearchResults(
                                            null,
                                            validMassnartList.toArray(
                                                new Node[validMassnartList.size()]),
                                            false);
                                MethodManager.getManager().showSearchResults();
                            } catch (Exception e) {
                                LOG.error("Error while retrieving Massnahmen objects.", e);
                            }
                        }
                    };

                t.start();
            }
        }
    } //GEN-LAST:event_txtMassnahmeMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void butRefreshActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_butRefreshActionPerformed
        refreshFilter();
    }                                                                              //GEN-LAST:event_butRefreshActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbGeraetItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbGeraetItemStateChanged
        // TODO add your handling code here:
    } //GEN-LAST:event_cbGeraetItemStateChanged

    /**
     * DOCUMENT ME!
     */
    private void refreshFilter() {
        try {
            noRefresh = true;
            cbGewerk.setSelectedIndex(0);
            cbEinsatz.setSelectedIndex(0);
            cbVerbleib.setSelectedIndex(0);
            cbIntervall.setSelectedIndex(0);
            cbZeitpunkt.setSelectedIndex(0);
        } finally {
            noRefresh = false;
        }
        refreshMassnahme();
    }

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
            final List<CidsBean> linieBeans = getOtherLineBeansInNeighbourhood();

            linearReferencedLineEditor.setOtherLines(linieBeans);
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        linearReferencedLineEditor.setCidsBean(cidsBean);
                    }
                });

            final CidsBean line = (CidsBean)cidsBean.getProperty("linie");

            if (line != null) {
                line.addPropertyChangeListener(this);
                final CidsBean von = (CidsBean)line.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_FROM);
                final CidsBean bis = (CidsBean)line.getProperty(LinearReferencingConstants.PROP_STATIONLINIE_TO);

                if (von != null) {
                    von.addPropertyChangeListener(this);
                }
                if (bis != null) {
                    bis.addPropertyChangeListener(this);
                }
            }
            cidsBean.addPropertyChangeListener(this);

            txtMassnahme.setOpaque(false);
            refreshGeraeteCombo();
//            new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        final Object item = cidsBean.getProperty("massnahme");
//                        if (item != null) {
//                            cbMassnahme.setSelectedItem(item);
//                        }
//                    }
//                }).start();

            refreshMassnahmenFields();
//            validateMassnahme();
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshGeraeteCombo() {
        final CidsBean massnBean = (CidsBean)cidsBean.getProperty("massnahme");

        if (massnBean != null) {
            String critField = null;
            boolean crit = true;
            final CidsBean erlaubt = (CidsBean)massnBean.getProperty("erlaubte_geraete");

            if (erlaubt != null) {
                if (erlaubt.getMetaObject().getId() == AL_ANF) {
                    critField = "erfuellt_all_anf";
                    crit = true;
                } else if (erlaubt.getMetaObject().getId() == NOT_AL_ANF) {
                    critField = "erfuellt_all_anf";
                    crit = false;
                }
            }

            final ModelLoader ml = new ModelLoader("gup_geraet", "geraet", cbGeraet, critField, crit, true);
            CismetConcurrency.getInstance("GUP").getDefaultExecutor().execute(ml);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshGewerkCombo() {
        String geraetCritField = null;

        switch (kompartiment) {
            case KOMPARTIMENT_SOHLE: {
                geraetCritField = "sohle";
                break;
            }
            case KOMPARTIMENT_UFER: {
                geraetCritField = "ufer";
                break;
            }
            case KOMPARTIMENT_UMFELD: {
                geraetCritField = "umfeld";
                break;
            }
        }

        final ModelLoader gewerkMl = new ModelLoader(
                "gup_gewerk",
                "massnahme.gewerk",
                cbGewerk,
                geraetCritField,
                true,
                true);
        CismetConcurrency.getInstance("GUP").getDefaultExecutor().execute(gewerkMl);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<CidsBean> getOtherLineBeansInNeighbourhood() {
        final List<CidsBean> linieBeans = new ArrayList<CidsBean>();

        if (getMassnahmen() != null) {
            CidsBean before = null;
            CidsBean after = null;
            final Double from = (Double)cidsBean.getProperty("linie.von.wert");
            final Double until = (Double)cidsBean.getProperty("linie.bis.wert");

            if ((from == null) || (until == null)) {
                return linieBeans;
            }

            for (final CidsBean b : getMassnahmen()) {
                final CidsBean tmp = (CidsBean)b.getProperty("linie");

                if ((tmp != null) && (!tmp.getProperty("id").equals(cidsBean.getProperty("linie.id")))) {
                    final Double tmpFrom = (Double)tmp.getProperty("von.wert");
                    final Double tmpUntil = (Double)tmp.getProperty("bis.wert");

                    if ((tmpFrom != null) && (tmpFrom >= until)) {
                        if (after == null) {
                            after = tmp;
                        } else {
                            if (((Double)after.getProperty("von.wert")) > tmpFrom) {
                                after = tmp;
                            }
                        }
                    }

                    if ((tmpUntil != null) && (tmpUntil <= from)) {
                        if (before == null) {
                            before = tmp;
                        } else {
                            if (((Double)before.getProperty("bis.wert")) < tmpUntil) {
                                before = tmp;
                            }
                        }
                    }

//                    linieBeans.add(tmp);
                }
            }

            if (before != null) {
                linieBeans.add(before);
            }
            if (after != null) {
                linieBeans.add(after);
            }
        }

        return linieBeans;
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        if (readOnly) {
            return;
        }

        if (cidsBean != null) {
            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Gup_massnahmenart")) { // NOI18N
                    setNewMassnahme(1, bean.getMetaObject());
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setComboboxes() {
        final CidsBean massnBean = (CidsBean)cidsBean.getProperty("massnahme");

        if (massnBean != null) {
            ((ScrollableComboBox)cbEinsatz).setSelectedItem(
                massnBean.getProperty("einsatzvariante"));
            ((ScrollableComboBox)cbGewerk).setSelectedItem(massnBean.getProperty("gewerk"));
            ((ScrollableComboBox)cbIntervall).setSelectedItem(massnBean.getProperty("intervall"));
            ((ScrollableComboBox)cbVerbleib).setSelectedItem(massnBean.getProperty("verbleib"));
            ((ScrollableComboBox)cbZeitpunkt).setSelectedItem(massnBean.getProperty("ausfuehrungszeitpunkt"));

            txtMassnahme.setText(String.valueOf(massnBean.getProperty("name")));
        } else {
            ((ScrollableComboBox)cbEinsatz).setSelectedItem(null);
            ((ScrollableComboBox)cbGewerk).setSelectedItem(null);
            ((ScrollableComboBox)cbIntervall).setSelectedItem(null);
            ((ScrollableComboBox)cbVerbleib).setSelectedItem(null);
            ((ScrollableComboBox)cbZeitpunkt).setSelectedItem(null);

            txtMassnahme.setText("");
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void validateMassnahme() {
        lblValid.setVisible(false);
        // validiere Maßnahme
        CismetThreadPool.execute(new SwingWorker<UnterhaltungsmassnahmeValidator.ValidationResult, Void>() {

                List<String> errors = new ArrayList<String>();

                @Override
                protected UnterhaltungsmassnahmeValidator.ValidationResult doInBackground() throws Exception {
                    if (validator == null) {
                        return null;
                    } else {
                        return validator.validate(cidsBean, errors);
                    }
                }

                @Override
                protected void done() {
                    try {
                        final UnterhaltungsmassnahmeValidator.ValidationResult res = get();

                        if (res != null) {
                            lblValid.setVisible(true);
                        }
                        if (res == UnterhaltungsmassnahmeValidator.ValidationResult.ok) {
                            lblValid.setIcon(
                                new javax.swing.ImageIcon(
                                    getClass().getResource(
                                        "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/ok.png")));
                            lblValid.setToolTipText("OK");
                            textEval.setText("");
                            jscEval.setVisible(false);
                        } else if (res == UnterhaltungsmassnahmeValidator.ValidationResult.warning) {
                            lblValid.setIcon(
                                new javax.swing.ImageIcon(
                                    getClass().getResource(
                                        "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/ok_auflagen.png")));
                            lblValid.setToolTipText("");
                            textEval.setText("");
                            jscEval.setVisible(false);
                        } else if (res == UnterhaltungsmassnahmeValidator.ValidationResult.error) {
                            lblValid.setIcon(
                                new javax.swing.ImageIcon(
                                    getClass().getResource(
                                        "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/stop.png")));
                            final StringBuilder errorText = new StringBuilder("<html>");
                            final StringBuilder errorT = new StringBuilder("");

                            for (final String tmp : errors) {
                                errorText.append(tmp).append("<br />");
                                errorT.append(tmp).append("\n");
                            }

                            errorText.append("</html>");
                            lblValid.setToolTipText(errorText.toString());
                            textEval.setText(errorT.toString());
                            jscEval.setVisible(true);
                        }
                    } catch (Exception e) {
                        LOG.error("Exception while validating.", e);
                    }
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hideValidation  DOCUMENT ME!
     */
    public void hideValidation(final boolean hideValidation) {
        lblValidLab.setVisible(!hideValidation);
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshMassnahme() {
        if (noRefresh) {
            return;
        }
        txtMassnahme.setText("Suche ...");
        txtMassnahme.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        if (massnahmnenObjects != null) {
            CismetThreadPool.execute(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            MetaObject validMetaObject = null;
                            final List<Node> validMassnartList = new ArrayList<Node>();
                            int validCount = 0;

                            for (final CidsBean tmp : massnahmnenObjects) {
                                if (isValidMassnahmenart(tmp)) {
                                    validMetaObject = tmp.getMetaObject();
                                    validMassnartList.add(new MetaObjectNode(tmp));
                                    ++validCount;
                                }
                            }

                            final int count = validCount;
                            final MetaObject metaObject = validMetaObject;

                            EventQueue.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        if (validMassnartList.size() > 1) {
                                            massnartList = validMassnartList;
                                        } else {
                                            massnartList = null;
                                        }
                                        setNewMassnahme(count, metaObject);
                                    }
                                });
                        } catch (Exception e) {
                            LOG.error("Cannot determine the valid objects of the type massnahmenart.", e);
                        }
                    }
                });
        } else {
            CismetThreadPool.execute(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            int validCount = 0;
                            final String intervall = ((cbIntervall.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbIntervall.getSelectedItem()).getProperty("id")) : null);
                            final String einsatzvariante = ((cbEinsatz.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbEinsatz.getSelectedItem()).getProperty("id")) : null);
                            final String ausfuehrungszeitpunkt = ((cbZeitpunkt.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbZeitpunkt.getSelectedItem()).getProperty("id")) : null);
                            final String gewerk = ((cbGewerk.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbGewerk.getSelectedItem()).getProperty("id")) : null);
                            final String verbleib = ((cbVerbleib.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbVerbleib.getSelectedItem()).getProperty("id")) : null);

                            // Geraet und zweiter Ausfuehrungszeitpunkt soll ignoriert werden
                            final AbstractCidsServerSearch search = new MassnahmenartSearch(
                                    intervall,
                                    einsatzvariante,
                                    null,
                                    ausfuehrungszeitpunkt,
                                    null,
                                    gewerk,
                                    verbleib,
                                    String.valueOf(kompartiment));
                            final Collection res = SessionManager.getProxy()
                                        .customServerSearch(SessionManager.getSession().getUser(), search);
                            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

                            if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                                final Object o = resArray.get(0).get(0);

                                validCount = ((Long)o).intValue();
                            }

                            MetaObject validMetaObject = null;

                            if (validCount == 1) {
                                final MetaObject[] mo = getValidMassnahmenObjects();

                                if ((mo != null) && (mo.length == 1)) {
                                    validMetaObject = mo[0];
                                }
                            }

                            final int count = validCount;
                            final MetaObject metaObject = validMetaObject;

                            EventQueue.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        setNewMassnahme(count, metaObject);
                                    }
                                });
                        } catch (Exception e) {
                            LOG.error("Cannot determine the valid objects of the type massnahmenart.", e);
                        }
                    }
                });
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  CacheException  DOCUMENT ME!
     */
    private MetaObject[] getValidMassnahmenObjects() throws CacheException {
        final String intervall = ((cbIntervall.getSelectedItem() != null)
                ? String.valueOf(
                    ((CidsBean)cbIntervall.getSelectedItem()).getProperty("id")) : null);
        final String einsatzvariante = ((cbEinsatz.getSelectedItem() != null)
                ? String.valueOf(
                    ((CidsBean)cbEinsatz.getSelectedItem()).getProperty("id")) : null);
        final String ausfuehrungszeitpunkt = ((cbZeitpunkt.getSelectedItem() != null)
                ? String.valueOf(
                    ((CidsBean)cbZeitpunkt.getSelectedItem()).getProperty("id")) : null);
        final String gewerk = ((cbGewerk.getSelectedItem() != null)
                ? String.valueOf(
                    ((CidsBean)cbGewerk.getSelectedItem()).getProperty("id")) : null);
        final String verbleib = ((cbVerbleib.getSelectedItem() != null)
                ? String.valueOf(
                    ((CidsBean)cbVerbleib.getSelectedItem()).getProperty("id")) : null);

        String newQuery = "select " + MASSNAHMENART_MC.getID() + ","
                    + MASSNAHMENART_MC.getPrimaryKey()
                    + " from " + MASSNAHMENART_MC.getTableName();

        int conditions = 0;

        if ((intervall != null) && !intervall.equals("null")) {
            if (conditions == 0) {
                newQuery += " WHERE intervall = " + intervall;
            } else {
                newQuery += " AND intervall = " + intervall;
            }
            ++conditions;
        }

        if ((einsatzvariante != null) && !einsatzvariante.equals("null")) {
            if (conditions == 0) {
                newQuery += " WHERE einsatzvariante = " + einsatzvariante;
            } else {
                newQuery += " AND einsatzvariante = " + einsatzvariante;
            }
            ++conditions;
        }

        if ((ausfuehrungszeitpunkt != null) && !ausfuehrungszeitpunkt.equals("null")) {
            if (conditions == 0) {
                newQuery += " WHERE ausfuehrungszeitpunkt = " + ausfuehrungszeitpunkt;
            } else {
                newQuery += " AND ausfuehrungszeitpunkt = " + ausfuehrungszeitpunkt;
            }
            ++conditions;
        }

        if ((gewerk != null) && !gewerk.equals("null")) {
            if (conditions == 0) {
                newQuery += " WHERE gewerk = " + gewerk;
            } else {
                newQuery += " AND gewerk = " + gewerk;
            }
            ++conditions;
        }

        if ((verbleib != null) && !verbleib.equals("null")) {
            if (conditions == 0) {
                newQuery += " WHERE verbleib = " + verbleib;
            } else {
                newQuery += " AND verbleib = " + verbleib;
            }
            ++conditions;
        }

        if (conditions == 0) {
            newQuery += " WHERE ";
        } else {
            newQuery += " AND ";
        }

        if (kompartiment == KOMPARTIMENT_SOHLE) {
            newQuery += " sohle";
        }

        if (kompartiment == KOMPARTIMENT_UFER) {
            newQuery += " ufer";
        }

        if (kompartiment == KOMPARTIMENT_UMFELD) {
            newQuery += " umfeld";
        }

        return MetaObjectCache.getInstance().getMetaObjectsByQuery(newQuery, WRRLUtil.DOMAIN_NAME);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  count       DOCUMENT ME!
     * @param  metaObject  DOCUMENT ME!
     */
    private void setNewMassnahme(final int count, final MetaObject metaObject) {
        txtMassnahme.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if (count == 1) {
            if (supportsKompartiment(metaObject.getBean(), kompartiment)) {
                txtMassnahme.setBackground(new Color(54, 196, 165));
                txtMassnahme.setOpaque(true);
                txtMassnahme.setText(
                    String.valueOf(metaObject.getBean().getProperty("name")));
                try {
                    cidsBean.setProperty("massnahme", metaObject.getBean());
                    getHistoryModel().addElement(metaObject.getBean());
                } catch (Exception e) {
                    LOG.error("Error while saving the new massnahme property", e);
                }
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Die ausgewählte Maßnahme ist für das aktuelle Kompartiment nicht gültig.",
                    "Ungültige Maßnahme",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (count > 1) {
            txtMassnahme.setOpaque(true);
            txtMassnahme.setBackground(new Color(237, 218, 16));
            txtMassnahme.setText(count + " Treffer");
            txtMassnahme.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            txtMassnahme.setOpaque(true);
            txtMassnahme.setBackground(new Color(237, 16, 42));
            txtMassnahme.setText("ungültige Kombination");
        }

        refreshGeraeteCombo();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isValidMassnahmenart(final CidsBean bean) {
        final Object gewerk = cbGewerk.getSelectedItem();
        final Object einsatz = cbEinsatz.getSelectedItem();
        final Object intervall = cbIntervall.getSelectedItem();
        final Object verbleib = cbVerbleib.getSelectedItem();
        final Object zeitpunkt = cbZeitpunkt.getSelectedItem();

        return ((gewerk == null) || gewerk.equals(bean.getProperty("gewerk")))
                    && ((einsatz == null) || einsatz.equals(bean.getProperty("einsatzvariante")))
                    && ((intervall == null) || intervall.equals(bean.getProperty("intervall")))
                    && ((verbleib == null) || verbleib.equals(bean.getProperty("verbleib")))
                    && ((zeitpunkt == null) || zeitpunkt.equals(bean.getProperty("ausfuehrungszeitpunkt")))
                    && supportsKompartiment(bean, kompartiment);
    }

    /**
     * Checks, if the given massnahmenart object supports the given kompartiment.
     *
     * @param   bean          a massnahmenart object
     * @param   kompartiment  DOCUMENT ME!
     *
     * @return  true, iff the given massnahmenart object supports the given kompartiment
     */
    public static boolean supportsKompartiment(final CidsBean bean, final int kompartiment) {
        final Boolean sohle = (Boolean)bean.getProperty("sohle");
        final Boolean ufer = (Boolean)bean.getProperty("ufer");
        final Boolean umfeld = (Boolean)bean.getProperty("umfeld");

        if ((kompartiment == KOMPARTIMENT_SOHLE) && (sohle != null) && sohle) {
            return true;
        }

        if ((kompartiment == KOMPARTIMENT_UFER) && (ufer != null) && ufer) {
            return true;
        }

        if ((kompartiment == KOMPARTIMENT_UMFELD) && (umfeld != null) && umfeld) {
            return true;
        }

        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  metaObject  DOCUMENT ME!
     */
    public void setNewMassnahme(final MetaObject metaObject) {
        setNewMassnahme(1, metaObject);
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshMassnahmenFields() {
        txtMassnahme.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        deActivateAdditionalAttributes((CidsBean)cidsBean.getProperty("massnahme"));
        setComboboxes();
        validateMassnahme();
        refreshGeraeteCombo();

//        if (cidsBean != null) {
//            String url = (String) cidsBean.getProperty(url);
//
//            if (url != null) {
//                txtMassnahme.cre
//            }
//        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  massnahme  DOCUMENT ME!
     */
    private void deActivateAdditionalAttributes(final CidsBean massnahme) {
        if (massnahme != null) {
            final Object bl = massnahme.getProperty("boeschungslaenge");
            final Object bn = massnahme.getProperty("boeschungsneigung");
            final Object db = massnahme.getProperty("deichkronenbreite");
            final Object rs = massnahme.getProperty("randstreifenbreite");
            final Object sb = massnahme.getProperty("sohlbreite");
            final Object vb = massnahme.getProperty("vorlandbreite");
            final Object cm = massnahme.getProperty("cbmprom");
            final Object st = massnahme.getProperty("stueck");
            final Object stu = massnahme.getProperty("stunden");
            final Object sch = massnahme.getProperty("schnitttiefe");

            panBoeschungslaenge.setVisible((bl != null) && ((Boolean)bl).booleanValue());
            panBoeschungsneigung.setVisible((bn != null) && ((Boolean)bn).booleanValue());
            panDeichkronenbreite.setVisible((db != null) && ((Boolean)db).booleanValue());
            panRandstreifen.setVisible((rs != null) && ((Boolean)rs).booleanValue());
            panSohlbreite.setVisible((sb != null) && ((Boolean)sb).booleanValue());
            panVorlandbreite.setVisible((vb != null) && ((Boolean)vb).booleanValue());
            panCbmProM.setVisible((cm != null) && ((Boolean)cm).booleanValue());
            panStueck.setVisible((st != null) && ((Boolean)st).booleanValue());
            panStunden.setVisible((stu != null) && ((Boolean)stu).booleanValue());
            panSchnitttiefe.setVisible((sch != null) && ((Boolean)sch).booleanValue());
        } else {
            panBoeschungslaenge.setVisible(false);
            panBoeschungsneigung.setVisible(false);
            panDeichkronenbreite.setVisible(false);
            panRandstreifen.setVisible(false);
            panSohlbreite.setVisible(false);
            panVorlandbreite.setVisible(false);
            panCbmProM.setVisible(false);
            panStueck.setVisible(false);
            panStunden.setVisible(false);
            panSchnitttiefe.setVisible(false);
        }
    }

//    /**
//     * DOCUMENT ME!
//     */
//    public void refresh() {
//        linearReferencedLineEditor.refreshOtherLines();
//    }
    @Override
    public void dispose() {
        if (cidsBean != null) {
            cidsBean.removePropertyChangeListener(this);
            final CidsBean line = (CidsBean)cidsBean.getProperty("linie");

            if (line != null) {
                line.removePropertyChangeListener(this);
                final CidsBean von = (CidsBean)line.getProperty("von");
                final CidsBean bis = (CidsBean)line.getProperty("bis");

                if (von != null) {
                    von.removePropertyChangeListener(this);
                }
                if (bis != null) {
                    bis.removePropertyChangeListener(this);
                }
            }
        }
        bindingGroup.unbind();
        linearReferencedLineEditor.dispose();
    }

    @Override
    public String getTitle() {
        return "Maßnahme";
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

    /**
     * DOCUMENT ME!
     *
     * @return  the massnahmen
     */
    public List<CidsBean> getMassnahmen() {
        return massnahmen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  massnahmen  the massnahmen to set
     */
    public void setMassnahmen(final List<CidsBean> massnahmen) {
        this.massnahmen = massnahmen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  kompartiment  the kompartiment to set
     */
    public void setKompartiment(final int kompartiment) {
        this.kompartiment = kompartiment;
        if (!readOnly) {
            lastKompartiment = kompartiment;
        }
        refreshGewerkCombo();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static int getLastKompartiment() {
        return lastKompartiment;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        // Die Comboboxen loesen auch PropertyChangeEvents aus, ohne dass der Nutzer etwas tut.
        // Diese muessen also gesondert behandelt werden
        if (!readOnly && !evt.getPropertyName().equals("bearbeiter")
                    && !evt.getPropertyName().equals("intervall")
                    && !evt.getPropertyName().equals("jahr")
                    && !evt.getPropertyName().equals("ausfuehrungszeitpunkt")
                    && !evt.getPropertyName().equals("verbleib")
                    && !evt.getPropertyName().equals(GupGupEditor.PROP_ACCEPTED_WB)
                    && !evt.getPropertyName().equals(GupGupEditor.PROP_DECLINED_WB)
                    && !evt.getPropertyName().equals("auflagen_wb")
                    && !evt.getPropertyName().equals(GupGupEditor.PROP_ACCEPTED_NB)
                    && !evt.getPropertyName().equals(GupGupEditor.PROP_DECLINED_NB)
                    && !evt.getPropertyName().equals("auflagen_nb")
                    && !evt.getPropertyName().equals(GupGupEditor.PROP_NOT_REQUIRED_NB)
                    && !evt.getPropertyName().equals("massnahme")) {
            changeBearbeiter();

            if (evt.getPropertyName().equals("von") || evt.getPropertyName().equals("bis")) {
                final CidsBean oldVal = (CidsBean)evt.getOldValue();
                final CidsBean newVal = (CidsBean)evt.getNewValue();

                if (oldVal != null) {
                    oldVal.removePropertyChangeListener(this);
                }
                if (newVal != null) {
                    newVal.addPropertyChangeListener(this);
                }
            }
        } else if (evt.getPropertyName().equals("massnahme")) {
            refreshMassnahmenFields();
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void changeBearbeiter() {
        if (cidsBean != null) {
            cidsBean.getMetaObject().setAllClasses();
            try {
                final User user = SessionManager.getSession().getUser();
                cidsBean.setProperty("bearbeiter", user.getName() + "@" + user.getDomain());
                cidsBean.setProperty(GupGupEditor.PROP_ACCEPTED_NB, Boolean.FALSE);
                cidsBean.setProperty(GupGupEditor.PROP_DECLINED_NB, Boolean.FALSE);
                cidsBean.setProperty(GupGupEditor.PROP_NOT_REQUIRED_NB, Boolean.FALSE);
                cidsBean.setProperty(GupGupEditor.PROP_ACCEPTED_WB, Boolean.FALSE);
                cidsBean.setProperty(GupGupEditor.PROP_DECLINED_WB, Boolean.FALSE);
            } catch (Exception ex) {
                LOG.error(ex, ex);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the validator
     */
    public UnterhaltungsmassnahmeValidator getValidator() {
        return validator;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  validator  the validator to set
     */
    public void setValidator(final UnterhaltungsmassnahmeValidator validator) {
        this.validator = validator;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the massnahmnenObjects
     */
    public static CidsBean[] getMassnahmnenObjects() {
        return massnahmnenObjects;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  massnahmnenObjs  the massnahmnenObjects to set
     */
    public static void setMassnahmnenObjects(final CidsBean[] massnahmnenObjs) {
        massnahmnenObjects = massnahmnenObjs;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class JToolTipWithIcon extends JToolTip {

        //~ Instance fields ----------------------------------------------------

        ImageIcon icon;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new JToolTipWithIcon object.
         *
         * @param  icon  DOCUMENT ME!
         */
        public JToolTipWithIcon(final ImageIcon icon) {
            this.icon = icon;
            setUI(new IconToolTipUI());
        }

        /**
         * Creates a new JToolTipWithIcon object.
         *
         * @param  toolTipUI  DOCUMENT ME!
         */
        public JToolTipWithIcon(final MetalToolTipUI toolTipUI) {
            setUI(toolTipUI);
        }

        //~ Inner Classes ------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @version  $Revision$, $Date$
         */
        private class IconToolTipUI extends MetalToolTipUI {

            //~ Methods --------------------------------------------------------

            @Override
            public void paint(final Graphics g, final JComponent c) {
                final Dimension size = c.getSize();
                g.setColor(c.getBackground());
                g.fillRect(0, 0, size.width, size.height);
                if (icon != null) {
                    icon.paintIcon(c, g, 0, 0);
                }
                g.setColor(c.getForeground());
            }

            @Override
            public Dimension getPreferredSize(final JComponent c) {
                final FontMetrics metrics = c.getFontMetrics(c.getFont());
                final String tipText = "";

                int width = SwingUtilities.computeStringWidth(metrics, tipText);
                int height = metrics.getHeight();
                if (icon != null) {
                    width += icon.getIconWidth() + 1;
                    height = (icon.getIconHeight() > height) ? icon.getIconHeight() : (height + 4);
                }
                return new Dimension(width + 6, height);
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @version  $Revision$, $Date$
         */
        private class IconTooltipLabel extends JLabel {

            //~ Methods --------------------------------------------------------

            @Override
            public JToolTip createToolTip() {
                JToolTipWithIcon tip = null;
                try {
                    tip = new JToolTipWithIcon(new ImageIcon(
                                new URL(
                                    "http://tango.freedesktop.org/static/cvs/tango-icon-theme/22x22/actions/go-home.png")));
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                tip.setComponent(this);
                return tip;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class ReadOnlyBeanConverter extends Converter<CidsBean, String> {

        //~ Methods ------------------------------------------------------------

        @Override
        public String convertForward(final CidsBean s) {
            if (s != null) {
                return s.toString();
            } else {
                return "";
            }
        }

        @Override
        public CidsBean convertReverse(final String t) {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class ModelLoader extends SwingWorker<CidsBean[], Void> {

        //~ Instance fields ----------------------------------------------------

        private final String catalogueName;
        private final JComboBox cBox;
        private final boolean criterium;
        private final String critField;
        private final String objectProperty;
        private final boolean nullable;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ModelLoader object.
         *
         * @param  catalogueName   DOCUMENT ME!
         * @param  objectProperty  DOCUMENT ME!
         * @param  cBox            DOCUMENT ME!
         * @param  critField       DOCUMENT ME!
         * @param  criterium       DOCUMENT ME!
         * @param  nullable        DOCUMENT ME!
         */
        public ModelLoader(final String catalogueName,
                final String objectProperty,
                final JComboBox cBox,
                final String critField,
                final boolean criterium,
                final boolean nullable) {
            this.catalogueName = catalogueName;
            this.cBox = cBox;
            this.critField = critField;
            this.criterium = criterium;
            this.objectProperty = objectProperty;
            this.nullable = nullable;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected CidsBean[] doInBackground() throws Exception {
            final MetaClass lstMc = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, catalogueName);

            if (lstMc != null) {
                final List<CidsBean> beans = new ArrayList<CidsBean>();
                String queryRk = "select " + lstMc.getID() + ", " + lstMc.getPrimaryKey() + " from "
                            + lstMc.getTableName(); // NOI18N

                if (critField != null) {
                    queryRk += " where "
                                + ((!criterium) ? (critField + " is null or not ") : (critField + " is not null and "))
                                + critField;
                }

                final MetaObject[] mos = MetaObjectCache.getInstance()
                            .getMetaObjectsByQuery(queryRk, WRRLUtil.DOMAIN_NAME);

                if (nullable) {
                    beans.add(null);
                }

                if ((mos != null)) {
                    for (final MetaObject mo : mos) {
                        beans.add(mo.getBean());
                    }
                }

                Collections.sort(beans, new BeanToStringComparator());
                return beans.toArray(new CidsBean[beans.size()]);
            } else {
                return new CidsBean[0];
            }
        }

        @Override
        protected void done() {
            try {
                Object selectedProperty = null;

                if (objectProperty != null) {
                    selectedProperty = cidsBean.getProperty(objectProperty);
                }

                cBox.setModel(new DefaultComboBoxModel(get()));

                if (objectProperty != null) {
                    cBox.setSelectedItem(selectedProperty);
                }
            } catch (final Exception e) {
                LOG.error("Error while initializing the model of the catalogue " + catalogueName, e); // NOI18N
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static final class BeanToStringComparator implements Comparator<CidsBean> {

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final CidsBean o1, final CidsBean o2) {
            final String s1 = (o1 == null) ? "" : o1.toString(); // NOI18N
            final String s2 = (o2 == null) ? "" : o2.toString(); // NOI18N

            return (s1).compareToIgnoreCase(s2);
        }
    }
}
