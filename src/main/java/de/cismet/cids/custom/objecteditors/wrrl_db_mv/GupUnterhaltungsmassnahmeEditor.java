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
import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.newuser.User;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingWorker;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.commons.linearreferencing.LinearReferencingConstants;
import de.cismet.cids.custom.wrrl_db_mv.server.search.MassnahmenartSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.UnterhaltungsmaßnahmeValidator;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.AbstractCidsServerSearch;
import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.CismetThreadPool;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupUnterhaltungsmassnahmeEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    PropertyChangeListener {

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
    private static final Logger LOG = Logger.getLogger(GupUnterhaltungsmassnahmeEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private List<CidsBean> massnahmen = null;
    private UnterhaltungsmaßnahmeValidator validator;
    private int kompartiment;
    private MetaObject[] massnahmnenObjects;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEinsatz;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGeraet;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGewerk;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbIntervall;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbJahr;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbVerbleib;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbZeitpunkt;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbZeitpunkt2;
    private javax.swing.JPanel flowPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblBearbeiter1;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblBoeschungslaenge;
    private javax.swing.JLabel lblBoeschungsneigung;
    private javax.swing.JLabel lblDeichkronenbreite;
    private javax.swing.JLabel lblEinsatz;
    private javax.swing.JLabel lblGeraet;
    private javax.swing.JLabel lblGewerk;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblIntervall;
    private javax.swing.JLabel lblJahr;
    private javax.swing.JLabel lblMassnahme;
    private javax.swing.JLabel lblRandstreifenbreite;
    private javax.swing.JLabel lblSohlbreite;
    private javax.swing.JLabel lblStueck;
    private javax.swing.JLabel lblValid;
    private javax.swing.JLabel lblValidLab;
    private javax.swing.JLabel lblVerbleib;
    private javax.swing.JLabel lblVorlandbreite;
    private javax.swing.JLabel lblZeitpunkt;
    private javax.swing.JLabel lblZeitpunkt2;
    private javax.swing.JLabel lclCbmprom;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JPanel panBoeschungslaenge;
    private javax.swing.JPanel panBoeschungsneigung;
    private javax.swing.JPanel panCbmProM;
    private javax.swing.JPanel panDeichkronenbreite;
    private javax.swing.JPanel panRandstreifen;
    private javax.swing.JPanel panSohlbreite;
    private javax.swing.JPanel panStueck;
    private javax.swing.JPanel panValid;
    private javax.swing.JPanel panVorlandbreite;
    private javax.swing.JScrollPane spBemerkung;
    private javax.swing.JTextField txtBearbeiter;
    private javax.swing.JTextField txtBoeschungslaenge;
    private javax.swing.JTextField txtBoeschungsneigung;
    private javax.swing.JTextField txtCbmProM;
    private javax.swing.JTextField txtDeichkronenbreite;
    private javax.swing.JTextField txtMassnahme;
    private javax.swing.JTextField txtRandstreifenbreite;
    private javax.swing.JTextField txtSohlbreite;
    private javax.swing.JTextField txtStueck;
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
//        linearReferencedLineEditor.setOtherLinesQueryAddition("gup_massnahme_sonstige gms", "gms.linie = ");
        linearReferencedLineEditor.setDrawingFeaturesEnabled(true);
        initComponents();
        panValid.setVisible(false);
//        lblValid.setVisible(false);
        RendererTools.makeReadOnly(txtBearbeiter);
        RendererTools.makeReadOnly(txtMassnahme);

        if (readOnly) {
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
            RendererTools.makeReadOnly(cbGeraet);
            RendererTools.makeReadOnly(cbGewerk);
            RendererTools.makeReadOnly(cbZeitpunkt);
            RendererTools.makeReadOnly(cbZeitpunkt2);
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
        panRandstreifen = new javax.swing.JPanel();
        lblRandstreifenbreite = new javax.swing.JLabel();
        txtRandstreifenbreite = new javax.swing.JTextField();
        panBoeschungsneigung = new javax.swing.JPanel();
        lblBoeschungsneigung = new javax.swing.JLabel();
        txtBoeschungsneigung = new javax.swing.JTextField();
        panSohlbreite = new javax.swing.JPanel();
        lblSohlbreite = new javax.swing.JLabel();
        txtSohlbreite = new javax.swing.JTextField();
        panDeichkronenbreite = new javax.swing.JPanel();
        lblDeichkronenbreite = new javax.swing.JLabel();
        txtDeichkronenbreite = new javax.swing.JTextField();
        panVorlandbreite = new javax.swing.JPanel();
        lblVorlandbreite = new javax.swing.JLabel();
        txtVorlandbreite = new javax.swing.JTextField();
        panStueck = new javax.swing.JPanel();
        lblStueck = new javax.swing.JLabel();
        txtStueck = new javax.swing.JTextField();
        panCbmProM = new javax.swing.JPanel();
        lclCbmprom = new javax.swing.JLabel();
        txtCbmProM = new javax.swing.JTextField();
        lblValidLab = new javax.swing.JLabel();
        panValid = new javax.swing.JPanel();
        lblValid = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        cbJahr = new ScrollableComboBox();
        lblIntervall = new javax.swing.JLabel();
        cbIntervall = new ScrollableComboBox(INTERVALL_MC, true, false);
        lblBearbeiter1 = new javax.swing.JLabel();
        txtBearbeiter = new javax.swing.JTextField();
        lblZeitpunkt2 = new javax.swing.JLabel();
        cbZeitpunkt2 = new ScrollableComboBox(ZEITPUNKT_MC, true, false);
        cbVerbleib = new ScrollableComboBox(VERBLEIB_MC, true, false);
        lblVerbleib = new javax.swing.JLabel();
        lblEinsatz = new javax.swing.JLabel();
        cbEinsatz = new ScrollableComboBox(EINSATZVARIANTE_MC, true, false);
        lblGeraet = new javax.swing.JLabel();
        cbGeraet = new ScrollableComboBox(GERAET_MC, true, false);
        cbGewerk = new ScrollableComboBox(GEWERK_MC, true, false);
        lblGewerk = new javax.swing.JLabel();
        cbZeitpunkt = new ScrollableComboBox(ZEITPUNKT_MC, true, false);
        txtMassnahme = new javax.swing.JTextField();

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
        gridBagConstraints.gridx = 3;
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
        gridBagConstraints.gridy = 4;
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
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblBemerkung, gridBagConstraints);

        spBemerkung.setMaximumSize(new java.awt.Dimension(280, 60));
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
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(spBemerkung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 20, 10);
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
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanel1.add(lblInfo, gridBagConstraints);

        flowPanel.setMinimumSize(new java.awt.Dimension(250, 250));
        flowPanel.setOpaque(false);
        flowPanel.setPreferredSize(new java.awt.Dimension(250, 250));

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
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panBoeschungslaenge.add(txtBoeschungslaenge, gridBagConstraints);

        flowPanel.add(panBoeschungslaenge);

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
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panRandstreifen.add(txtRandstreifenbreite, gridBagConstraints);

        flowPanel.add(panRandstreifen);

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
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panBoeschungsneigung.add(txtBoeschungsneigung, gridBagConstraints);

        flowPanel.add(panBoeschungsneigung);

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
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panSohlbreite.add(txtSohlbreite, gridBagConstraints);

        flowPanel.add(panSohlbreite);

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
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panDeichkronenbreite.add(txtDeichkronenbreite, gridBagConstraints);

        flowPanel.add(panDeichkronenbreite);

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
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panVorlandbreite.add(txtVorlandbreite, gridBagConstraints);

        flowPanel.add(panVorlandbreite);

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panStueck.add(txtStueck, gridBagConstraints);

        flowPanel.add(panStueck);

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panCbmProM.add(txtCbmProM, gridBagConstraints);

        flowPanel.add(panCbmProM);

        lblValidLab.setFont(new java.awt.Font("Ubuntu", 1, 15));      // NOI18N
        lblValidLab.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValidLab.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblValidLab.text")); // NOI18N
        lblValidLab.setPreferredSize(new java.awt.Dimension(210, 24));
        flowPanel.add(lblValidLab);

        panValid.setPreferredSize(new java.awt.Dimension(210, 24));
        flowPanel.add(panValid);

        lblValid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValid.setMaximumSize(new java.awt.Dimension(128, 128));
        lblValid.setMinimumSize(new java.awt.Dimension(128, 128));
        lblValid.setPreferredSize(new java.awt.Dimension(128, 128));
        flowPanel.add(lblValid);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(flowPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
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

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1.0;
        add(jPanel2, gridBagConstraints);

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
        gridBagConstraints.gridy = 4;
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
        gridBagConstraints.gridy = 2;
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
        gridBagConstraints.gridy = 2;
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
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblBearbeiter1, gridBagConstraints);

        txtBearbeiter.setMaximumSize(new java.awt.Dimension(200, 20));
        txtBearbeiter.setMinimumSize(new java.awt.Dimension(200, 20));
        txtBearbeiter.setPreferredSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblZeitpunkt2, gridBagConstraints);

        cbZeitpunkt2.setMinimumSize(new java.awt.Dimension(100, 20));
        cbZeitpunkt2.setPreferredSize(new java.awt.Dimension(200, 20));
        cbZeitpunkt2.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbZeitpunkt2ItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbZeitpunkt2, gridBagConstraints);

        cbVerbleib.setMinimumSize(new java.awt.Dimension(100, 20));
        cbVerbleib.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.verbleib}"),
                cbVerbleib,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbVerbleib.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbVerbleibItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
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
        gridBagConstraints.gridx = 1;
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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        add(lblGeraet, gridBagConstraints);

        cbGeraet.setMinimumSize(new java.awt.Dimension(200, 20));
        cbGeraet.setPreferredSize(new java.awt.Dimension(200, 20));
        cbGeraet.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbGeraetItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbGeraet, gridBagConstraints);

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
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        add(cbGewerk, gridBagConstraints);

        lblGewerk.setText(org.openide.util.NbBundle.getMessage(
                GupUnterhaltungsmassnahmeEditor.class,
                "GupUnterhaltungsmassnahmeEditor.lblGewerk.text")); // NOI18N
        lblGewerk.setMaximumSize(new java.awt.Dimension(100, 17));
        lblGewerk.setMinimumSize(new java.awt.Dimension(100, 17));
        lblGewerk.setPreferredSize(new java.awt.Dimension(100, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 5);
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        add(txtMassnahme, gridBagConstraints);

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
                final CidsBean bean = (CidsBean)evt.getItem();
                cbZeitpunkt2.setEnabled((bean != null)
                            && bean.getProperty("id").equals(INTERVAL_TWO_TIMES));
                refreshMassnahme();
            }
        }
    }                                                                              //GEN-LAST:event_cbIntervallItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbZeitpunkt2ItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbZeitpunkt2ItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                refreshMassnahme();
            }
        }
    }                                                                               //GEN-LAST:event_cbZeitpunkt2ItemStateChanged

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
    private void cbGeraetItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbGeraetItemStateChanged
        if (evt.getItem() != null) {
            if (((Component)evt.getSource()).hasFocus()) {
                refreshMassnahme();
            }
        }
    }                                                                           //GEN-LAST:event_cbGeraetItemStateChanged

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
            final List<CidsBean> linieBeans = new ArrayList<CidsBean>();

            if (getMassnahmen() != null) {
                for (final CidsBean b : getMassnahmen()) {
                    final CidsBean tmp = (CidsBean)b.getProperty("linie");

                    if ((tmp != null) && (!tmp.getProperty("id").equals(cidsBean.getProperty("linie.id")))) {
                        linieBeans.add(tmp);
                    }
                }
            }
            linearReferencedLineEditor.setOtherLines(linieBeans);
            linearReferencedLineEditor.setCidsBean(cidsBean);
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

            final CidsBean massnBean = (CidsBean)cidsBean.getProperty("massnahme");
            txtMassnahme.setOpaque(false);

            if (massnBean != null) {
                final CidsBean bean = (CidsBean)massnBean.getProperty("intervall");
                cbZeitpunkt2.setEnabled((bean != null) && bean.getProperty("id").equals(INTERVAL_TWO_TIMES));
                ((ScrollableComboBox)cbEinsatz).setSelectedItem(
                    massnBean.getProperty("einsatzvariante"));
                ((ScrollableComboBox)cbGeraet).setSelectedItem(massnBean.getProperty("geraet"));
                ((ScrollableComboBox)cbGewerk).setSelectedItem(massnBean.getProperty("gewerk"));
                ((ScrollableComboBox)cbIntervall).setSelectedItem(massnBean.getProperty("intervall"));
                ((ScrollableComboBox)cbVerbleib).setSelectedItem(massnBean.getProperty("verbleib"));
                ((ScrollableComboBox)cbZeitpunkt).setSelectedItem(massnBean.getProperty("ausfuehrungszeitpunkt"));
                ((ScrollableComboBox)cbZeitpunkt2).setSelectedItem(massnBean.getProperty(
                        "zweiter_ausfuehrungszeitpunkt"));

                txtMassnahme.setText(String.valueOf(massnBean.getProperty("massnahmen_id")));
            }

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

            deActivateAdditionalAttributes((CidsBean)cidsBean.getProperty("massnahme"));

            // validiere Maßnahme
            CismetThreadPool.execute(new SwingWorker<UnterhaltungsmaßnahmeValidator.ValidationResult, Void>() {

                    @Override
                    protected UnterhaltungsmaßnahmeValidator.ValidationResult doInBackground() throws Exception {
                        if (validator == null) {
                            return null;
                        } else {
                            return validator.validate(cidsBean);
                        }
                    }

                    @Override
                    protected void done() {
                        try {
                            final UnterhaltungsmaßnahmeValidator.ValidationResult res = get();

                            if (res == UnterhaltungsmaßnahmeValidator.ValidationResult.ok) {
                                lblValid.setIcon(
                                    new javax.swing.ImageIcon(
                                        getClass().getResource(
                                            "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/ok.png")));
//                                lblValid.setText("Gültig");
                            } else if (res == UnterhaltungsmaßnahmeValidator.ValidationResult.warning) {
                                lblValid.setIcon(
                                    new javax.swing.ImageIcon(
                                        getClass().getResource(
                                            "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/ok_auflagen.png")));
//                                lblValid.setText("Warnung");
                            } else if (res == UnterhaltungsmaßnahmeValidator.ValidationResult.error) {
                                lblValid.setIcon(
                                    new javax.swing.ImageIcon(
                                        getClass().getResource(
                                            "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/stop.png")));
//                                lblValid.setText("Ungültig");
                            }
                        } catch (Exception e) {
                            LOG.error("Exception while validating.", e);
                        }
                    }
                });
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void refreshMassnahme() {
        txtMassnahme.setText("Suche ...");

        if (massnahmnenObjects != null) {
            CismetThreadPool.execute(new Runnable() {

                    @Override
                    public void run() {
                        final long startTime = System.currentTimeMillis();
                        try {
                            MetaObject validMetaObject = null;
                            int validCount = 0;

                            for (final MetaObject tmp : massnahmnenObjects) {
                                if (isValidMassnahmenart(tmp.getBean())) {
                                    validMetaObject = tmp;
                                    ++validCount;
                                }
                            }

                            final int count = validCount;
                            final MetaObject metaObject = validMetaObject;

                            EventQueue.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        if (count == 1) {
                                            txtMassnahme.setBackground(new Color(54, 196, 165));
                                            txtMassnahme.setOpaque(true);
                                            txtMassnahme.setText(
                                                String.valueOf(metaObject.getBean().getProperty("massnahmen_id")));
                                            try {
                                                cidsBean.setProperty("massnahme", metaObject.getBean());
                                            } catch (Exception e) {
                                                LOG.error("Error while saving the new massnahme property", e);
                                            }
                                        } else if (count > 1) {
                                            txtMassnahme.setOpaque(true);
                                            txtMassnahme.setBackground(new Color(237, 218, 16));
                                            txtMassnahme.setText(count + " Treffer");
                                        } else {
                                            txtMassnahme.setOpaque(true);
                                            txtMassnahme.setBackground(new Color(237, 16, 42));
                                            txtMassnahme.setText("ungültige Kombination");
                                        }
                                    }
                                });
                        } catch (Exception e) {
                            LOG.error("Cannot determine the valid objects of the type massnahmenart.", e);
                        }
                        LOG.error("time: " + (System.currentTimeMillis() - startTime));
                    }
                });
        } else {
            CismetThreadPool.execute(new Runnable() {

                    @Override
                    public void run() {
                        final long startTime = System.currentTimeMillis();
                        try {
                            int validCount = 0;
                            final String intervall = ((cbIntervall.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbIntervall.getSelectedItem()).getProperty("id")) : null);
                            final String einsatzvariante = ((cbEinsatz.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbEinsatz.getSelectedItem()).getProperty("id")) : null);
                            final String geraet = ((cbGeraet.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbGeraet.getSelectedItem()).getProperty("id")) : null);
                            final String ausfuehrungszeitpunkt = ((cbZeitpunkt.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbZeitpunkt.getSelectedItem()).getProperty("id")) : null);
                            final String zweiter_ausfuehrungszeitpunkt = ((cbZeitpunkt2.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbZeitpunkt2.getSelectedItem()).getProperty("id")) : null);
                            final String gewerk = ((cbGewerk.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbGewerk.getSelectedItem()).getProperty("id")) : null);
                            final String verbleib = ((cbVerbleib.getSelectedItem() != null)
                                    ? String.valueOf(
                                        ((CidsBean)cbVerbleib.getSelectedItem()).getProperty("id")) : null);

                            final AbstractCidsServerSearch search = new MassnahmenartSearch(
                                    intervall,
                                    einsatzvariante,
                                    geraet,
                                    ausfuehrungszeitpunkt,
                                    zweiter_ausfuehrungszeitpunkt,
                                    gewerk,
                                    verbleib);
                            final Collection res = SessionManager.getProxy()
                                        .customServerSearch(SessionManager.getSession().getUser(), search);
                            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

                            if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                                final Object o = resArray.get(0).get(0);

                                validCount = ((Long)o).intValue();
                            }

                            MetaObject validMetaObject = null;

                            if (validCount == 1) {
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

                                if ((geraet != null) && !geraet.equals("null")) {
                                    if (conditions == 0) {
                                        newQuery += " WHERE geraet = " + geraet;
                                    } else {
                                        newQuery += " AND geraet = " + geraet;
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

                                if ((zweiter_ausfuehrungszeitpunkt != null)
                                            && !zweiter_ausfuehrungszeitpunkt.equals("null")) {
                                    if (conditions == 0) {
                                        newQuery += " WHERE zweiter_ausfuehrungszeitpunkt = "
                                                    + zweiter_ausfuehrungszeitpunkt;
                                    } else {
                                        newQuery += " AND zweiter_ausfuehrungszeitpunkt = "
                                                    + zweiter_ausfuehrungszeitpunkt;
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

                                final MetaObject[] mo = MetaObjectCache.getInstance().getMetaObjectsByQuery(newQuery);

                                if ((mo != null) && (mo.length == 1)) {
                                    validMetaObject = mo[0];
                                }
                            }

                            final int count = validCount;
                            final MetaObject metaObject = validMetaObject;

                            EventQueue.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        if (count == 1) {
                                            txtMassnahme.setBackground(new Color(54, 196, 165));
                                            txtMassnahme.setOpaque(true);
                                            txtMassnahme.setText(
                                                String.valueOf(metaObject.getBean().getProperty("massnahmen_id")));
                                            try {
                                                cidsBean.setProperty("massnahme", metaObject.getBean());
                                            } catch (Exception e) {
                                                LOG.error("Error while saving the new massnahme property", e);
                                            }
                                        } else if (count > 1) {
                                            txtMassnahme.setOpaque(true);
                                            txtMassnahme.setBackground(new Color(237, 218, 16));
                                            txtMassnahme.setText(count + " Treffer");
                                        } else {
                                            txtMassnahme.setOpaque(true);
                                            txtMassnahme.setBackground(new Color(237, 16, 42));
                                            txtMassnahme.setText("ungültige Kombination");
                                        }
                                    }
                                });
                        } catch (Exception e) {
                            LOG.error("Cannot determine the valid objects of the type massnahmenart.", e);
                        }
                        LOG.error("time: " + (System.currentTimeMillis() - startTime));
                    }
                });
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   bean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isValidMassnahmenart(final CidsBean bean) {
        final Object geraet = cbGeraet.getSelectedItem();
        final Object gewerk = cbGewerk.getSelectedItem();
        final Object einsatz = cbEinsatz.getSelectedItem();
        final Object intervall = cbIntervall.getSelectedItem();
        final Object verbleib = cbVerbleib.getSelectedItem();
        final Object zeitpunkt = cbZeitpunkt.getSelectedItem();
        final Object zeitpunkt2 = cbZeitpunkt2.getSelectedItem();

        return ((geraet == null) || geraet.equals(bean.getProperty("geraet")))
                    && ((gewerk == null) || gewerk.equals(bean.getProperty("gewerk")))
                    && ((einsatz == null) || einsatz.equals(bean.getProperty("einsatzvariante")))
                    && ((intervall == null) || intervall.equals(bean.getProperty("intervall")))
                    && ((verbleib == null) || verbleib.equals(bean.getProperty("verbleib")))
                    && ((zeitpunkt == null) || zeitpunkt.equals(bean.getProperty("ausfuehrungszeitpunkt")))
                    && ((zeitpunkt2 == null) || zeitpunkt2.equals(bean.getProperty("zweiter_ausfuehrungszeitpunkt")));
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

            panBoeschungslaenge.setVisible((bl != null) && ((Boolean)bl).booleanValue());
            panBoeschungsneigung.setVisible((bn != null) && ((Boolean)bn).booleanValue());
            panDeichkronenbreite.setVisible((db != null) && ((Boolean)db).booleanValue());
            panRandstreifen.setVisible((rs != null) && ((Boolean)rs).booleanValue());
            panSohlbreite.setVisible((sb != null) && ((Boolean)sb).booleanValue());
            panVorlandbreite.setVisible((vb != null) && ((Boolean)vb).booleanValue());
            panCbmProM.setVisible((cm != null) && ((Boolean)cm).booleanValue());
            panStueck.setVisible((st != null) && ((Boolean)st).booleanValue());
        } else {
            panBoeschungslaenge.setVisible(false);
            panBoeschungsneigung.setVisible(false);
            panDeichkronenbreite.setVisible(false);
            panRandstreifen.setVisible(false);
            panSohlbreite.setVisible(false);
            panVorlandbreite.setVisible(false);
            panCbmProM.setVisible(false);
            panStueck.setVisible(false);
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
//        ((MassnahmenComboBox)cbMassnahme).setKompartiment(kompartiment);
//        cbMassnahme.setSelectedItem(null);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        // Die Comboboxen loesen auch PropertyChangeEvents aus, ohne dass der Nutzer etwas tut.
        // Diese muessen also gesondert behandelt werden
        if (!readOnly && !evt.getPropertyName().equals("bearbeiter")
                    && !evt.getPropertyName().equals("intervall")
                    && !evt.getPropertyName().equals("ausfuehrungszeitpunkt")
                    && !evt.getPropertyName().equals("verbleib") && !evt.getPropertyName().equals("massnahme")) {
            changeBearbeiter();

            if (evt.getPropertyName().equals("von") || evt.getPropertyName().equals("bis")) {
                ((CidsBean)evt.getOldValue()).removePropertyChangeListener(this);
                ((CidsBean)evt.getNewValue()).addPropertyChangeListener(this);
            }
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
    public UnterhaltungsmaßnahmeValidator getValidator() {
        return validator;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  validator  the validator to set
     */
    public void setValidator(final UnterhaltungsmaßnahmeValidator validator) {
        this.validator = validator;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the massnahmnenObjects
     */
    public MetaObject[] getMassnahmnenObjects() {
        return massnahmnenObjects;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  massnahmnenObjects  the massnahmnenObjects to set
     */
    public void setMassnahmnenObjects(final MetaObject[] massnahmnenObjects) {
        this.massnahmnenObjects = massnahmnenObjects;
    }
}
