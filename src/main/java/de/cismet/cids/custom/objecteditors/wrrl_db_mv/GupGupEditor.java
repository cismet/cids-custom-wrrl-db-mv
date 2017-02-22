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
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.tools.MetaObjectCache;
import Sirius.navigator.types.treenode.DefaultMetaTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.newuser.User;
import Sirius.server.newuser.permission.Policy;

import org.jdesktop.beansbinding.Converter;

import org.openide.util.Exceptions;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.TreePath;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.PlanungsabschnittSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.DocumentDropList;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.GupHelper;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.StateMachine;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.commons.features.Feature;

import de.cismet.commons.concurrency.CismetConcurrency;

import de.cismet.tools.CismetThreadPool;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.TitleComponentProvider;
import de.cismet.tools.gui.WaitDialog;
import de.cismet.tools.gui.WaitingDialogThread;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupGupEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    TitleComponentProvider {

    //~ Static fields/initializers ---------------------------------------------

    public static final String WORKFLOW_STATUS_PROP = "workflow_status";

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupGupEditor.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_planungsabschnitt");
    private static final MetaClass MC_WF = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "gup_workflow_status");
    // The user and the action attributes cannot be changed while the navigator is running,
    // so the action attributes can be saved in a static context
    private static boolean actionSachbearbeiter;
    private static boolean actionBearbeiter;
    private static boolean actionWasser;
    private static boolean actionNaturschutz;
    private static final String ICON_PLANUNG = "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Draft.png";
    private static final String ICON_ANTRAG =
        "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/application_from_storage.png";
    private static final String ICON_PRUEFUNG = "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Test tubes.png";
    private static final String ICON_GENEHMIGT = "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/approve_24.png";
    private static final String ICON_GESCHLOSSEN = "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Valid_16.png";
    private static final Map<Integer, Integer[]> VIRTUAL_STAT_MAP = new HashMap<Integer, Integer[]>();
    public static final int PERMISSION_SB = 1;
    public static final int PERMISSION_NB = 2;
    public static final int PERMISSION_WB = 4;
    public static final int STAT_PLANUNG = 0;
    public static final int STAT_PLANUNG_FERTIG = 1;
    public static final int STAT_WB = 2;
    public static final int STAT_WB_ABG = 3;
    public static final int STAT_NB = 4;
    public static final int STAT_NB_WB = 5;
    public static final int STAT_NB_WB_ABG = 6;
    public static final int STAT_NB_ABG = 7;
    public static final int STAT_NB_ABG_WB = 8;
    public static final int STAT_NB_ABG_WB_ABG = 9;
    public static final int STAT_ANGENOMMEN = 10;
    public static final int ID_PLANUNG = 1;
    public static final int ID_PLANUNG_FERTIG = 2;
    public static final int ID_PRUEFUNG_DURCH_NB = 3;
    public static final int ID_PRUEFUNG_DURCH_NB_ABGESCHL = 4;
    public static final int ID_PLAN_ABGESCHLOSSEN = 5;
    public static final int ID_PRUEFUNG_DURCH_WB = 6;
    public static final int ID_PRUEFUNG_DURCH_WB_ABGESCHL = 7;
//    public static final int ID_GESCHLOSSEN = -1;
    public static final int[][] STATE_MATRIX = {
            { 0, PERMISSION_SB, 0, 0, 0, 0, 0, 0, 0, 0, 0 },                                     // 0
            { PERMISSION_SB, 0, PERMISSION_WB, 0, PERMISSION_NB, 0, 0, 0, 0, 0, 0 },             // 1
            { PERMISSION_WB, PERMISSION_WB, 0, PERMISSION_WB, 0, PERMISSION_NB, 0, 0, 0, 0, 0 }, // 2
            { PERMISSION_WB, 0, PERMISSION_WB, 0, 0, 0, PERMISSION_NB, 0, 0, 0, 0 },             // 3
            { PERMISSION_NB, PERMISSION_NB, 0, 0, 0, PERMISSION_WB, 0, PERMISSION_NB, 0, 0, 0 }, // 4
            {
                PERMISSION_NB
                        | PERMISSION_WB,
                0,
                PERMISSION_NB,
                0,
                PERMISSION_WB,
                0,
                PERMISSION_WB,
                0,
                PERMISSION_NB,
                0,
                0
            },                                                                                   // 5
            { PERMISSION_NB
                        | PERMISSION_WB, 0, 0, 0, 0, PERMISSION_WB, 0, 0, 0, PERMISSION_NB, 0 }, // 6
            { PERMISSION_NB, 0, 0, 0, PERMISSION_NB, 0, 0, 0, PERMISSION_WB, 0, 0 },             // 7
            { PERMISSION_NB
                        | PERMISSION_WB, 0, 0, 0, 0, PERMISSION_NB, 0, 0, 0, PERMISSION_WB, 0 }, // 8
            {
                PERMISSION_NB
                        | PERMISSION_WB,
                0,
                0,
                0,
                0,
                0,
                PERMISSION_NB,
                0,
                PERMISSION_WB,
                0,
                PERMISSION_NB
                        | PERMISSION_WB
            },                                                                                   // 9
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }                                                  // 10
        };
    private static MetaObject[] STATE_BEANS = null;
    private static final Thread STATE_BEANS_LOADER = new Thread("StateBeanLoader") {

            @Override
            public void run() {
                final String query = "SELECT "
                            + MC_WF.getID()
                            + ", "
                            + MC_WF.getPrimaryKey()
                            + " FROM "
                            + MC_WF.getTableName();
                try {
                    STATE_BEANS = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
                } catch (Exception ex) {
                    LOG.error("error while loading wk_fgs", ex);
                }
            }
        };

    static {
        VIRTUAL_STAT_MAP.put(STAT_PLANUNG, new Integer[] { ID_PLANUNG });
        VIRTUAL_STAT_MAP.put(STAT_PLANUNG_FERTIG, new Integer[] { ID_PLANUNG_FERTIG });
        VIRTUAL_STAT_MAP.put(STAT_WB, new Integer[] { ID_PRUEFUNG_DURCH_WB });
        VIRTUAL_STAT_MAP.put(STAT_WB_ABG, new Integer[] { ID_PRUEFUNG_DURCH_WB_ABGESCHL });
        VIRTUAL_STAT_MAP.put(STAT_NB, new Integer[] { ID_PRUEFUNG_DURCH_NB });
        VIRTUAL_STAT_MAP.put(STAT_NB_WB, new Integer[] { ID_PRUEFUNG_DURCH_NB, ID_PRUEFUNG_DURCH_WB });
        VIRTUAL_STAT_MAP.put(STAT_NB_WB_ABG, new Integer[] { ID_PRUEFUNG_DURCH_NB, ID_PRUEFUNG_DURCH_WB_ABGESCHL });
        VIRTUAL_STAT_MAP.put(STAT_NB_ABG, new Integer[] { ID_PRUEFUNG_DURCH_NB_ABGESCHL });
        VIRTUAL_STAT_MAP.put(STAT_NB_ABG_WB, new Integer[] { ID_PRUEFUNG_DURCH_NB_ABGESCHL, ID_PRUEFUNG_DURCH_WB });
        VIRTUAL_STAT_MAP.put(
            STAT_NB_ABG_WB_ABG,
            new Integer[] { ID_PRUEFUNG_DURCH_NB_ABGESCHL, ID_PRUEFUNG_DURCH_WB_ABGESCHL });
        VIRTUAL_STAT_MAP.put(STAT_ANGENOMMEN, new Integer[] { ID_PLAN_ABGESCHLOSSEN });
        readActions();
        CismetConcurrency.getInstance("GUP").getDefaultExecutor().execute(STATE_BEANS_LOADER);
    }

    private static final String PROP_ID = "id";
    private static final String PROP_CLOSE = "geschlossen";
    private static final String PROP_FREEZE_TIME = "eingefroren_zeit";
    private static final String PROP_FREEZE = "eingefroren";
    public static final String PROP_DECLINED_WB = "abgelehnt_wb";
    public static final String PROP_ACCEPTED_WB = "angenommen_wb";
    public static final String PROP_NOT_REQUIRED_NB = "nicht_erforderlich_nb";
    public static final String PROP_DECLINED_NB = "abgelehnt_nb";
    public static final String PROP_ACCEPTED_NB = "angenommen_nb";

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private boolean initialReadOnly = false;
    private List<CidsBean> planToAdd = new ArrayList<CidsBean>();
    private TreePath treePath;
    private int y = 0;
    private final StateMachine stateMachine = new StateMachine(STATE_MATRIX);
    private ReentrantReadWriteLock initializedLock = new ReentrantReadWriteLock();
    private final Map<Integer, JButton> STAT_ID_TO_BUTTON = new HashMap<Integer, JButton>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butNewPlan;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGenehmigungsbehoerde;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbUnterhaltungspflichtiger;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcVon;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbAdd;
    private javax.swing.JButton jbAngenommen;
    private javax.swing.JButton jbAntrag;
    private javax.swing.JButton jbDownload;
    private javax.swing.JButton jbGenehmigtNb;
    private javax.swing.JButton jbGenehmigtWb;
    private javax.swing.JButton jbPlanung;
    private javax.swing.JButton jbPruefungNb;
    private javax.swing.JButton jbPruefungWb;
    private javax.swing.JList jlObjectList;
    private javax.swing.JButton jpDelete;
    private javax.swing.JScrollPane jsObjectList;
    private javax.swing.JLabel lblBis;
    private javax.swing.JLabel lblCurrentState;
    private javax.swing.JLabel lblErlaeuterungsberichte;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGenehmigungsbehoerde;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblName1;
    private javax.swing.JLabel lblSepa;
    private javax.swing.JLabel lblSepa1;
    private javax.swing.JLabel lblSepa2;
    private javax.swing.JLabel lblSepa3;
    private javax.swing.JLabel lblSepa4;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusTitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblVon;
    private javax.swing.JLabel lblZeitraum;
    private javax.swing.JLabel lblZustaendigkeit;
    private javax.swing.JPanel panFooter;
    private javax.swing.JPanel panGewaesserInner;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panState;
    private javax.swing.JPanel panTitle;
    private javax.swing.JScrollPane scrollGewaesser;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtName1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupGupEditor() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupGupEditor(final boolean readOnly) {
        this.initialReadOnly = readOnly;
        initComponents();
        setToolTips();
        scrollGewaesser.getViewport().setOpaque(false);
        butNewPlan.setVisible(!readOnly);

        // set the stat id to button mapping
        STAT_ID_TO_BUTTON.put(ID_PLANUNG, jbPlanung);
        STAT_ID_TO_BUTTON.put(ID_PLANUNG_FERTIG, jbAntrag);
        STAT_ID_TO_BUTTON.put(ID_PRUEFUNG_DURCH_NB, jbPruefungNb);
        STAT_ID_TO_BUTTON.put(ID_PRUEFUNG_DURCH_NB_ABGESCHL, jbGenehmigtNb);
        STAT_ID_TO_BUTTON.put(ID_PRUEFUNG_DURCH_WB, jbPruefungWb);
        STAT_ID_TO_BUTTON.put(ID_PRUEFUNG_DURCH_WB_ABGESCHL, jbGenehmigtWb);
        STAT_ID_TO_BUTTON.put(ID_PLAN_ABGESCHLOSSEN, jbAngenommen);

        jbAdd.setEnabled(!readOnly);
        jpDelete.setEnabled(!readOnly);
        jbAngenommen.setEnabled(false);
        jbAntrag.setEnabled(false);
        jbGenehmigtNb.setEnabled(false);
        jbGenehmigtWb.setEnabled(false);
        jbPlanung.setEnabled(false);
        jbPruefungNb.setEnabled(false);
        jbPruefungWb.setEnabled(false);

        if (initialReadOnly) {
            RendererTools.makeReadOnly(cbGenehmigungsbehoerde);
            RendererTools.makeReadOnly(dcVon);
            RendererTools.makeReadOnly(txtName);
            RendererTools.makeReadOnly(dcBis);
            RendererTools.makeReadOnly(cbUnterhaltungspflichtiger);
            lblStatus.setVisible(false);
        } else {
            try {
                new CidsBeanDropTarget(panGewaesserInner);
            } catch (final Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
                }
            }
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void setToolTips() {
        CismetConcurrency.getInstance("GUP").getDefaultExecutor().execute(new Thread("ToolTipThread") {

                @Override
                public void run() {
                    if (STATE_BEANS_LOADER.isAlive()) {
                        try {
                            STATE_BEANS_LOADER.join();
                        } catch (InterruptedException ex) {
                            // nothing to do
                        }
                    }

                    EventQueue.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                jbPlanung.setToolTipText(determineToolTipText(ID_PLANUNG));
                                jbAntrag.setToolTipText(determineToolTipText(ID_PLANUNG_FERTIG));
                                jbPruefungNb.setToolTipText(determineToolTipText(ID_PRUEFUNG_DURCH_NB));
                                jbGenehmigtNb.setToolTipText(determineToolTipText(ID_PRUEFUNG_DURCH_NB_ABGESCHL));
                                jbGenehmigtWb.setToolTipText(determineToolTipText(ID_PRUEFUNG_DURCH_WB));
                                jbGenehmigtWb.setToolTipText(determineToolTipText(ID_PRUEFUNG_DURCH_WB_ABGESCHL));
                                jbAngenommen.setToolTipText(determineToolTipText(ID_PLAN_ABGESCHLOSSEN));
                            }

                            private String determineToolTipText(final int stateId) {
                                for (final MetaObject mo : STATE_BEANS) {
                                    if (mo.getBean().getProperty(PROP_ID).equals(stateId)) {
                                        return String.valueOf(mo.getBean().getProperty("name"));
                                    }
                                }

                                return "";
                            }
                        });
                }
            });
    }

    /**
     * DOCUMENT ME!
     */
    private static void readActions() {
        final User usr = SessionManager.getSession().getUser();
        try {
            actionSachbearbeiter = SessionManager.getProxy().hasConfigAttr(usr, "gup.sachbearbeiter");
            actionNaturschutz = SessionManager.getProxy().hasConfigAttr(usr, "gup.naturschutz");
            actionBearbeiter = SessionManager.getProxy().hasConfigAttr(usr, "gup.bearbeiter");
            actionWasser = SessionManager.getProxy().hasConfigAttr(usr, "gup.wasser");
        } catch (ConnectionException e) {
            LOG.error("Connection Exception: ", e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static boolean hasActionSachbearbeiter() {
        return actionSachbearbeiter;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static boolean hasActionNaturschutz() {
        return actionNaturschutz;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static boolean hasActionWasser() {
        return actionWasser;
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
        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblStatusTitle = new javax.swing.JLabel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        lblZeitraum = new javax.swing.JLabel();
        lblZustaendigkeit = new javax.swing.JLabel();
        lblVon = new javax.swing.JLabel();
        lblBis = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblGenehmigungsbehoerde = new javax.swing.JLabel();
        cbGenehmigungsbehoerde = new ScrollableComboBox();
        dcVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        panState = new javax.swing.JPanel();
        jbPlanung = new javax.swing.JButton();
        lblSepa = new javax.swing.JLabel();
        jbAntrag = new javax.swing.JButton();
        lblSepa1 = new javax.swing.JLabel();
        jbPruefungNb = new javax.swing.JButton();
        lblSepa2 = new javax.swing.JLabel();
        jbGenehmigtNb = new javax.swing.JButton();
        lblSepa3 = new javax.swing.JLabel();
        jbAngenommen = new javax.swing.JButton();
        jbPruefungWb = new javax.swing.JButton();
        lblSepa4 = new javax.swing.JLabel();
        jbGenehmigtWb = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        lblCurrentState = new javax.swing.JLabel();
        cbUnterhaltungspflichtiger = new ScrollableComboBox();
        jPanel1 = new javax.swing.JPanel();
        jsObjectList = new javax.swing.JScrollPane();
        jlObjectList = new DocumentDropList(readOnly, "dokumente");
        lblErlaeuterungsberichte = new javax.swing.JLabel();
        jbDownload = new javax.swing.JButton();
        jpDelete = new javax.swing.JButton();
        jbAdd = new javax.swing.JButton();
        lblName1 = new javax.swing.JLabel();
        txtName1 = new javax.swing.JTextField();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        scrollGewaesser = new javax.swing.JScrollPane();
        panGewaesserInner = new PlanungsabschnittPanel();
        butNewPlan = new javax.swing.JButton();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        panTitle.setMinimumSize(new java.awt.Dimension(1050, 36));
        panTitle.setOpaque(false);
        panTitle.setPreferredSize(new java.awt.Dimension(1050, 36));
        panTitle.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTitle.add(lblTitle, gridBagConstraints);

        lblStatusTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStatusTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblStatusTitle.setText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.lblStatusTitle.text"));               // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panTitle.add(lblStatusTitle, gridBagConstraints);

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(994, 700));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(557, 305));
        panInfo.setPreferredSize(new java.awt.Dimension(1130, 305));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setMinimumSize(new java.awt.Dimension(1057, 270));
        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblZeitraum.setFont(new java.awt.Font("Ubuntu", 1, 15));                                                        // NOI18N
        lblZeitraum.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblZeitraum.text")); // NOI18N
        lblZeitraum.setMaximumSize(new java.awt.Dimension(170, 17));
        lblZeitraum.setMinimumSize(new java.awt.Dimension(170, 17));
        lblZeitraum.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblZeitraum, gridBagConstraints);

        lblZustaendigkeit.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblZustaendigkeit.setText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.lblZustaendigkeit.text"));               // NOI18N
        lblZustaendigkeit.setMaximumSize(new java.awt.Dimension(280, 17));
        lblZustaendigkeit.setMinimumSize(new java.awt.Dimension(280, 17));
        lblZustaendigkeit.setPreferredSize(new java.awt.Dimension(280, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblZustaendigkeit, gridBagConstraints);

        lblVon.setFont(new java.awt.Font("Ubuntu", 1, 15));                                                   // NOI18N
        lblVon.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblVon.text")); // NOI18N
        lblVon.setMaximumSize(new java.awt.Dimension(30, 17));
        lblVon.setMinimumSize(new java.awt.Dimension(30, 17));
        lblVon.setPreferredSize(new java.awt.Dimension(30, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblVon, gridBagConstraints);

        lblBis.setFont(new java.awt.Font("Ubuntu", 1, 15));                                                   // NOI18N
        lblBis.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblBis.text")); // NOI18N
        lblBis.setMaximumSize(new java.awt.Dimension(25, 17));
        lblBis.setMinimumSize(new java.awt.Dimension(25, 17));
        lblBis.setPreferredSize(new java.awt.Dimension(25, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblBis, gridBagConstraints);

        lblName.setFont(new java.awt.Font("Ubuntu", 1, 15));                                                    // NOI18N
        lblName.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblName.text")); // NOI18N
        lblName.setMaximumSize(new java.awt.Dimension(170, 17));
        lblName.setMinimumSize(new java.awt.Dimension(170, 17));
        lblName.setPreferredSize(new java.awt.Dimension(170, 17));
        lblName.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblNameMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 5, 5);
        panInfoContent.add(lblName, gridBagConstraints);

        txtName.setMaximumSize(new java.awt.Dimension(280, 25));
        txtName.setMinimumSize(new java.awt.Dimension(280, 25));
        txtName.setPreferredSize(new java.awt.Dimension(380, 24));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        panInfoContent.add(txtName, gridBagConstraints);

        lblGenehmigungsbehoerde.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblGenehmigungsbehoerde.setText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.lblGenehmigungsbehoerde.text"));               // NOI18N
        lblGenehmigungsbehoerde.setMaximumSize(new java.awt.Dimension(230, 17));
        lblGenehmigungsbehoerde.setMinimumSize(new java.awt.Dimension(230, 17));
        lblGenehmigungsbehoerde.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblGenehmigungsbehoerde, gridBagConstraints);

        cbGenehmigungsbehoerde.setMaximumSize(new java.awt.Dimension(290, 25));
        cbGenehmigungsbehoerde.setMinimumSize(new java.awt.Dimension(290, 25));
        cbGenehmigungsbehoerde.setPreferredSize(new java.awt.Dimension(290, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.genehmigungsbehoerde}"),
                cbGenehmigungsbehoerde,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbGenehmigungsbehoerde, gridBagConstraints);

        dcVon.setMaximumSize(new java.awt.Dimension(280, 25));
        dcVon.setMinimumSize(new java.awt.Dimension(130, 25));
        dcVon.setPreferredSize(new java.awt.Dimension(280, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.start_datum}"),
                dcVon,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(dcVon.getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        panInfoContent.add(dcVon, gridBagConstraints);

        dcBis.setMaximumSize(new java.awt.Dimension(280, 25));
        dcBis.setMinimumSize(new java.awt.Dimension(130, 25));
        dcBis.setPreferredSize(new java.awt.Dimension(280, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.end_datum}"),
                dcBis,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setConverter(dcBis.getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        panInfoContent.add(dcBis, gridBagConstraints);

        panState.setOpaque(false);
        panState.setLayout(new java.awt.GridBagLayout());

        jbPlanung.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Draft.png"))); // NOI18N
        jbPlanung.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.jbPlanung.toolTipText"));                                                // NOI18N
        jbPlanung.setMaximumSize(new java.awt.Dimension(45, 30));
        jbPlanung.setMinimumSize(new java.awt.Dimension(45, 30));
        jbPlanung.setPreferredSize(new java.awt.Dimension(45, 30));
        jbPlanung.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbPlanungActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panState.add(jbPlanung, gridBagConstraints);

        lblSepa.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblSepa.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        panState.add(lblSepa, gridBagConstraints);

        jbAntrag.setIcon(new javax.swing.ImageIcon(
                getClass().getResource(
                    "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/application_from_storage.png"))); // NOI18N
        jbAntrag.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.jbAntrag.toolTipText"));                                                 // NOI18N
        jbAntrag.setMaximumSize(new java.awt.Dimension(45, 30));
        jbAntrag.setMinimumSize(new java.awt.Dimension(45, 30));
        jbAntrag.setPreferredSize(new java.awt.Dimension(45, 30));
        jbAntrag.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbAntragActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        panState.add(jbAntrag, gridBagConstraints);

        lblSepa1.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblSepa1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        panState.add(lblSepa1, gridBagConstraints);

        jbPruefungNb.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Test tubes.png"))); // NOI18N
        jbPruefungNb.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.jbPruefungNb.toolTipText"));                                                  // NOI18N
        jbPruefungNb.setMaximumSize(new java.awt.Dimension(45, 30));
        jbPruefungNb.setMinimumSize(new java.awt.Dimension(45, 30));
        jbPruefungNb.setPreferredSize(new java.awt.Dimension(45, 30));
        jbPruefungNb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbPruefungNbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        panState.add(jbPruefungNb, gridBagConstraints);

        lblSepa2.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblSepa2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        panState.add(lblSepa2, gridBagConstraints);

        jbGenehmigtNb.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/approve_24.png"))); // NOI18N
        jbGenehmigtNb.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.jbGenehmigtNb.toolTipText"));                                                 // NOI18N
        jbGenehmigtNb.setMaximumSize(new java.awt.Dimension(45, 30));
        jbGenehmigtNb.setMinimumSize(new java.awt.Dimension(45, 30));
        jbGenehmigtNb.setPreferredSize(new java.awt.Dimension(45, 30));
        jbGenehmigtNb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbGenehmigtNbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        panState.add(jbGenehmigtNb, gridBagConstraints);

        lblSepa3.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblSepa3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        panState.add(lblSepa3, gridBagConstraints);

        jbAngenommen.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Valid_16.png"))); // NOI18N
        jbAngenommen.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.jbAngenommen.toolTipText"));                                                // NOI18N
        jbAngenommen.setMaximumSize(new java.awt.Dimension(45, 30));
        jbAngenommen.setMinimumSize(new java.awt.Dimension(45, 30));
        jbAngenommen.setPreferredSize(new java.awt.Dimension(45, 30));
        jbAngenommen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbAngenommenActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panState.add(jbAngenommen, gridBagConstraints);

        jbPruefungWb.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Test tubes.png"))); // NOI18N
        jbPruefungWb.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.jbPruefungWb.toolTipText"));                                                  // NOI18N
        jbPruefungWb.setMaximumSize(new java.awt.Dimension(45, 30));
        jbPruefungWb.setMinimumSize(new java.awt.Dimension(45, 30));
        jbPruefungWb.setPreferredSize(new java.awt.Dimension(45, 30));
        jbPruefungWb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbPruefungWbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        panState.add(jbPruefungWb, gridBagConstraints);

        lblSepa4.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblSepa4.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        panState.add(lblSepa4, gridBagConstraints);

        jbGenehmigtWb.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/approve_24.png"))); // NOI18N
        jbGenehmigtWb.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.jbGenehmigtWb.toolTipText"));                                                 // NOI18N
        jbGenehmigtWb.setMaximumSize(new java.awt.Dimension(45, 30));
        jbGenehmigtWb.setMinimumSize(new java.awt.Dimension(45, 30));
        jbGenehmigtWb.setPreferredSize(new java.awt.Dimension(45, 30));
        jbGenehmigtWb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbGenehmigtWbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        panState.add(jbGenehmigtWb, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        panInfoContent.add(panState, gridBagConstraints);

        lblStatus.setFont(new java.awt.Font("Ubuntu", 1, 15));                                                      // NOI18N
        lblStatus.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblStatus.text")); // NOI18N
        lblStatus.setMaximumSize(new java.awt.Dimension(230, 17));
        lblStatus.setMinimumSize(new java.awt.Dimension(230, 17));
        lblStatus.setPreferredSize(new java.awt.Dimension(230, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 5, 5);
        panInfoContent.add(lblStatus, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblCurrentState, gridBagConstraints);

        cbUnterhaltungspflichtiger.setMaximumSize(new java.awt.Dimension(290, 25));
        cbUnterhaltungspflichtiger.setMinimumSize(new java.awt.Dimension(290, 25));
        cbUnterhaltungspflichtiger.setPreferredSize(new java.awt.Dimension(290, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.unterhaltungspflichtiger}"),
                cbUnterhaltungspflichtiger,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbUnterhaltungspflichtiger, gridBagConstraints);

        jPanel1.setMaximumSize(new java.awt.Dimension(310, 80));
        jPanel1.setMinimumSize(new java.awt.Dimension(310, 17));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(310, 80));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        final org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.dokumente}");
        final org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        jlObjectList);
        bindingGroup.addBinding(jListBinding);

        jsObjectList.setViewportView(jlObjectList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jsObjectList, gridBagConstraints);

        lblErlaeuterungsberichte.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblErlaeuterungsberichte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErlaeuterungsberichte.setText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.lblErlaeuterungsberichte.text"));               // NOI18N
        lblErlaeuterungsberichte.setMaximumSize(new java.awt.Dimension(310, 17));
        lblErlaeuterungsberichte.setMinimumSize(new java.awt.Dimension(310, 17));
        lblErlaeuterungsberichte.setPreferredSize(new java.awt.Dimension(310, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 5, 0);
        jPanel1.add(lblErlaeuterungsberichte, gridBagConstraints);

        jbDownload.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.jbDownload.text")); // NOI18N
        jbDownload.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbDownloadActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jbDownload, gridBagConstraints);

        jpDelete.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.jpDelete.text")); // NOI18N
        jpDelete.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jpDeleteActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jpDelete, gridBagConstraints);

        jbAdd.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.jbAdd.text")); // NOI18N
        jbAdd.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbAddActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jbAdd, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 10, 15);
        panInfoContent.add(jPanel1, gridBagConstraints);

        lblName1.setFont(new java.awt.Font("Ubuntu", 1, 15));                                                     // NOI18N
        lblName1.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblName1.text")); // NOI18N
        lblName1.setMaximumSize(new java.awt.Dimension(110, 17));
        lblName1.setMinimumSize(new java.awt.Dimension(110, 17));
        lblName1.setPreferredSize(new java.awt.Dimension(110, 17));
        lblName1.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblName1MouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 5, 0);
        panInfoContent.add(lblName1, gridBagConstraints);

        txtName1.setMaximumSize(new java.awt.Dimension(280, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bearbeiter}"),
                txtName1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 5, 15);
        panInfoContent.add(txtName1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 15);
        add(panInfo, gridBagConstraints);

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo1.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        scrollGewaesser.setBorder(null);
        scrollGewaesser.setOpaque(false);

        panGewaesserInner.setBorder(null);
        panGewaesserInner.setMinimumSize(new java.awt.Dimension(100, 100));
        panGewaesserInner.setOpaque(false);
        panGewaesserInner.setLayout(new java.awt.GridBagLayout());
        scrollGewaesser.setViewportView(panGewaesserInner);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(scrollGewaesser, gridBagConstraints);

        panInfo1.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 15, 15);
        add(panInfo1, gridBagConstraints);

        butNewPlan.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.butNewPlan.text")); // NOI18N
        butNewPlan.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    butNewPlanActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        add(butNewPlan, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbDownloadActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbDownloadActionPerformed
        ((DocumentDropList)jlObjectList).downloadSelectedDocs();
    }                                                                              //GEN-LAST:event_jbDownloadActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jpDeleteActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jpDeleteActionPerformed
        if (readOnly) {
            return;
        }
        final int[] selection = jlObjectList.getSelectedIndices();
        int count = 0;

        for (final int index : selection) {
            ((DocumentDropList)jlObjectList).removeObject(index - (count++));
        }
    } //GEN-LAST:event_jpDeleteActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbAddActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbAddActionPerformed
        if (!readOnly) {
            final JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.showDialog(this, "Hinzufügen");
            final File[] files = chooser.getSelectedFiles();

            ((DocumentDropList)jlObjectList).addFiles(Arrays.asList(files));
        }
    } //GEN-LAST:event_jbAddActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void butNewPlanActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_butNewPlanActionPerformed
        GupPlanungsabschnittEditor.setLastGup(cidsBean);

        final MetaClass MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "gup_planungsabschnitt");

        try {
            final CidsBean cb = CidsBean.createNewCidsBeanFromTableName(MC.getDomain(), MC.getTableName());

            final MetaObjectNode metaObjectNode = new MetaObjectNode(
                    -1,
                    SessionManager.getSession().getUser().getDomain(),
                    cb.getMetaObject(),
                    null,
                    null,
                    true,
                    Policy.createWIKIPolicy(),
                    -1,
                    null,
                    false);
            final DefaultMetaTreeNode metaTreeNode = new ObjectTreeNode(metaObjectNode);
            ComponentRegistry.getRegistry().showComponent(ComponentRegistry.ATTRIBUTE_EDITOR);
            ComponentRegistry.getRegistry().getAttributeEditor().setTreeNode(metaTreeNode);
        } catch (Exception e) {
            LOG.error("Error while creating a new object", e);
        }
    } //GEN-LAST:event_butNewPlanActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbPlanungActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbPlanungActionPerformed
        setState(ID_PLANUNG);
    }                                                                             //GEN-LAST:event_jbPlanungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbAntragActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbAntragActionPerformed
        setState(ID_PLANUNG_FERTIG);
    }                                                                            //GEN-LAST:event_jbAntragActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbPruefungNbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbPruefungNbActionPerformed
        setState(ID_PRUEFUNG_DURCH_NB);
    }                                                                                //GEN-LAST:event_jbPruefungNbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbGenehmigtNbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbGenehmigtNbActionPerformed
        setState(ID_PRUEFUNG_DURCH_NB_ABGESCHL);
    }                                                                                 //GEN-LAST:event_jbGenehmigtNbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbAngenommenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbAngenommenActionPerformed
        final int answer = JOptionPane.showConfirmDialog(
                StaticSwingTools.getParentFrame(this),
                "Möchten Sie den Plan wirklich abschließen? Diese Aktion kann nicht rückgängig gemacht werden.",
                "Plan abschließen",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            setState(ID_PLAN_ABGESCHLOSSEN);
        }
    } //GEN-LAST:event_jbAngenommenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblNameMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblNameMouseClicked
//        final GupGewaesserPreview prev = (GupGewaesserPreview)panGewaesserInner.getComponent(0);
//        final CidsBean pl = prev.getCidsBean();
//
//        freezePlanungsabschnitt(pl);
    } //GEN-LAST:event_lblNameMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblName1MouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblName1MouseClicked
        // TODO add your handling code here:
    } //GEN-LAST:event_lblName1MouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbPruefungWbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbPruefungWbActionPerformed
        setState(ID_PRUEFUNG_DURCH_WB);
    }                                                                                //GEN-LAST:event_jbPruefungWbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbGenehmigtWbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbGenehmigtWbActionPerformed
        setState(ID_PRUEFUNG_DURCH_WB_ABGESCHL);
    }                                                                                 //GEN-LAST:event_jbGenehmigtWbActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        try {
            EventQueue.invokeAndWait(new Runnable() {

                    @Override
                    public void run() {
                        initializedLock.writeLock().lock();
                    }
                });
        } catch (Exception ex) {
            LOG.error("Cannot set lock", ex);
        }
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            setReadOnly(isReadOnly());
            treePath = ComponentRegistry.getRegistry().getCatalogueTree().getSelectionPath();
            if (treePath != null) {
                treePath = treePath.getParentPath();
            }
            ((DocumentDropList)jlObjectList).setCidsBean(cidsBean);
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            // set status
            final Integer status = determineStatusByGupBean(cidsBean);

            stateMachine.forceState(status);
            setTitleStatus();

            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        setCidsBeans();
                    }
                }).start();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   gup  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Integer determineStatusByGupBean(final CidsBean gup) {
        if (gup == null) {
            return STAT_PLANUNG;
        }
        final List<CidsBean> statusList = gup.getBeanCollectionProperty(WORKFLOW_STATUS_PROP);
        final Boolean geschlossen = (Boolean)gup.getProperty(PROP_CLOSE);
        Integer status = STAT_PLANUNG;

        if ((geschlossen != null) && geschlossen) {
            status = STAT_ANGENOMMEN;
        } else {
            for (final Integer stat : VIRTUAL_STAT_MAP.keySet()) {
                final Integer[] ids = VIRTUAL_STAT_MAP.get(stat);
                Arrays.sort(ids);

                if (ids.length == statusList.size()) {
                    boolean match = true;
                    for (final CidsBean bean : statusList) {
                        final Integer i = (Integer)bean.getProperty(PROP_ID);

                        if (Arrays.binarySearch(ids, i) < 0) {
                            match = false;
                        }
                    }

                    if (match) {
                        status = stat;
                        break;
                    }
                }
            }
        }

        return status;
    }

    /**
     * Determines the name of the status of the given gup bean.
     *
     * @param   gup  a gup CidsBean
     *
     * @return  The name of the status
     */
    public static String determineStatusNameByGupBean(final CidsBean gup) {
        if (gup == null) {
            return "Planung";
        }
        final List<CidsBean> statusList = gup.getBeanCollectionProperty(WORKFLOW_STATUS_PROP);
        String statName = "";
        final Boolean closed = (Boolean)gup.getProperty(PROP_CLOSE);

        if ((closed != null) && closed) {
            statName = "Geschlossen";
        } else if ((statusList == null) || statusList.isEmpty()) {
            statName = "Planung";
        } else {
            for (final CidsBean stat : statusList) {
                if (statName.equals("")) {
                    statName = String.valueOf(stat.getProperty("name"));
                } else {
                    statName += "/"
                                + String.valueOf(stat.getProperty("name"));
                }
            }
        }

        return statName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  readOnly  DOCUMENT ME!
     */
    private void setReadOnly(final boolean readOnly) {
        this.readOnly = readOnly;
        butNewPlan.setVisible(!readOnly);

        jbAdd.setEnabled(!readOnly);
        jpDelete.setEnabled(!readOnly);
        jbAngenommen.setEnabled(false);
        jbAntrag.setEnabled(false);
        jbPlanung.setEnabled(false);
        jbPruefungNb.setEnabled(false);
        jbPruefungNb.setEnabled(false);
//        lblStatus.setVisible(!readOnly);

        if (readOnly) {
            RendererTools.makeReadOnly(cbGenehmigungsbehoerde);
            RendererTools.makeReadOnly(dcVon);
            RendererTools.makeReadOnly(txtName);
            RendererTools.makeReadOnly(dcBis);
            RendererTools.makeReadOnly(cbUnterhaltungspflichtiger);
        } else {
            RendererTools.makeWritable(cbGenehmigungsbehoerde);
            RendererTools.makeWritable(dcVon);
            RendererTools.makeWritable(txtName);
            RendererTools.makeWritable(dcBis);
            RendererTools.makeWritable(cbUnterhaltungspflichtiger);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isReadOnly() {
        if (initialReadOnly) {
            return true;
        }

        final Boolean isClosed = (Boolean)cidsBean.getProperty(PROP_CLOSE);

        if ((isClosed != null) && isClosed.booleanValue()) {
            return true;
        }

        Integer statId = (Integer)cidsBean.getProperty("status.id");

        if (statId == null) {
            statId = GupGupEditor.ID_PLANUNG;
        }

        if (GupGupEditor.hasActionSachbearbeiter() && (statId == GupGupEditor.ID_PLANUNG)) {
            return false;
        }

        return true;
    }

    /**
     * DOCUMENT ME!
     */
    private void setCidsBeans() {
        try {
            final CidsServerSearch search = new PlanungsabschnittSearch(cidsBean.getProperty(PROP_ID).toString());
            final ArrayList<ArrayList> list = (ArrayList<ArrayList>)SessionManager.getProxy()
                        .customServerSearch(search);

            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        if ((list != null) && (list.size() > 0)) {
                            for (final ArrayList mo : list) {
                                addPa((Integer)mo.get(0), (String)mo.get(1));
                            }
                        }

                        initializedLock.writeLock().unlock();
                    }
                });

            if (!initialReadOnly) {
                activateButtons();
            }
        } catch (final ConnectionException e) {
            LOG.error("Error while trying to receive measurements.", e); // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setTitleStatus() {
        final Integer statId = determineStatusByGupBean(cidsBean);
        final String statName = determineStatusNameByGupBean(cidsBean);
        final Boolean closed = (Boolean)cidsBean.getProperty(PROP_CLOSE);
        String icon;

        switch (statId) {
            case GupGupEditor.STAT_PLANUNG: {
                icon = ICON_PLANUNG;
                break;
            }
            case GupGupEditor.STAT_PLANUNG_FERTIG: {
                icon = ICON_ANTRAG;
                break;
            }
            case GupGupEditor.STAT_NB:
            case GupGupEditor.STAT_WB:
            case GupGupEditor.STAT_NB_ABG:
            case GupGupEditor.STAT_WB_ABG:
            case GupGupEditor.STAT_NB_ABG_WB:
            case GupGupEditor.STAT_NB_WB_ABG:
            case GupGupEditor.STAT_NB_WB: {
                icon = ICON_PRUEFUNG;
                break;
            }
            case GupGupEditor.STAT_NB_ABG_WB_ABG: {
                icon = ICON_GENEHMIGT;
                break;
            }
            case GupGupEditor.STAT_ANGENOMMEN: {
                if ((closed != null) && closed) {
                    icon = ICON_GESCHLOSSEN;
                } else {
                    icon = ICON_GENEHMIGT;
                }
                break;
            }
            default: {
                // should never happen
                icon = ICON_PLANUNG;
                break;
            }
        }

        lblStatusTitle.setText(statName);
        lblStatusTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource(icon)));
        lblCurrentState.setText(statName);
        lblCurrentState.setIcon(new javax.swing.ImageIcon(getClass().getResource(icon)));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stateId  DOCUMENT ME!
     */
    private void setState(final Integer stateId) {
        final WaitingDialogThread<Integer> wdt = new WaitingDialogThread<Integer>(StaticSwingTools.getParentFrame(this),
                true,
                "Ändere Status",
                null,
                200) {

                @Override
                protected Integer doInBackground() throws Exception {
                    CidsBean stateObject = null;
                    final int oldState = stateMachine.getState();
                    final Integer state = determineNewState(oldState, stateId);
                    final Integer[] stateIds = VIRTUAL_STAT_MAP.get(state);

                    if ((((state == STAT_NB_ABG) || (state == STAT_NB_ABG_WB)) && !isApproved(STAT_NB_ABG))
                                || (((state == STAT_WB_ABG) || (state == STAT_NB_WB_ABG)) && !isApproved(STAT_WB_ABG))
                                || ((state == STAT_NB_ABG_WB_ABG)
                                    && (!isApproved(STAT_NB_ABG) || !isApproved(STAT_WB_ABG)))) {
                        return 1;
                    }

                    if ((state == STAT_ANGENOMMEN) && !isApproved(STAT_ANGENOMMEN)) {
                        return 2;
                    }

                    // todo: Status in Bean eintragen und m:n Beziehung erstellen
                    stateMachine.setState(state);

                    for (final MetaObject tmp : STATE_BEANS) {
                        if (tmp.getBean().getProperty(PROP_ID).equals(stateId)) {
                            stateObject = tmp.getBean();
                        }
                    }

                    try {
                        if (stateId == ID_PLAN_ABGESCHLOSSEN) {
                            cidsBean.setProperty(PROP_CLOSE, true);
                            cidsBean.setProperty("status_wechsel", new java.sql.Date(new Date().getTime()));
                            final List<CidsBean> stateList = cidsBean.getBeanCollectionProperty(WORKFLOW_STATUS_PROP);
                            stateList.clear();
                            stateList.add(stateObject);

                            CismetThreadPool.execute(new Runnable() {

                                    @Override
                                    public void run() {
                                        freezeAllPlanungsabschnitte();
                                    }
                                });
                        } else {
                            final Boolean closed = (Boolean)cidsBean.getProperty(PROP_CLOSE);

                            if ((closed != null) && closed) {
                                cidsBean.setProperty(PROP_CLOSE, false);
                            }

                            final List<CidsBean> stateList = cidsBean.getBeanCollectionProperty(WORKFLOW_STATUS_PROP);

                            if (stateList != null) {
                                for (final CidsBean bean : new ArrayList<CidsBean>(stateList)) {
                                    final Integer id = (Integer)bean.getProperty(PROP_ID);

                                    if (Arrays.binarySearch(stateIds, id) < 0) {
                                        stateList.remove(bean);
                                    }
                                }

                                if (!stateList.contains(stateObject)) {
                                    stateList.add(stateObject);
                                }
                            }

                            cidsBean.setProperty("status_wechsel", new java.sql.Date(new Date().getTime()));
                        }
                    } catch (Exception e) {
                        LOG.error("Cannot change the state of the gup.", e);
                        stateMachine.forceState(oldState);
                    }

                    return 0;
                }

                @Override
                protected void done() {
                    try {
                        final Integer errorCode = get();

                        if (errorCode == 1) {
                            JOptionPane.showMessageDialog(
                                GupGupEditor.this,
                                "Es wurden noch nicht alle Maßnahmen bearbeitet",
                                "Prüfung unvollständig",
                                JOptionPane.WARNING_MESSAGE);
                        }
                        if (errorCode == 2) {
                            JOptionPane.showMessageDialog(
                                GupGupEditor.this,
                                "Es wurden noch nicht alle Maßnahmen angenommen",
                                "Prüfung unvollständig",
                                JOptionPane.WARNING_MESSAGE);
                        } else {
                            activateButtons();
                            setTitleStatus();
                        }
                    } catch (Exception e) {
                        LOG.error("Error while change status", e);
                    }
                }
            };

        wdt.start();
    }

    /**
     * Determines the new state. The new state contains the additional stateId and the maximum number of the old state
     * ids. It is assumed that only one state fulfits the requirements from the last sentence. If new states will be
     * added, this method must probably changed.
     *
     * @param   oldState           DOCUMENT ME!
     * @param   additionalStateId  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Integer determineNewState(final Integer oldState, final Integer additionalStateId) {
        final Map<Integer, Integer[]> possibleMatches = new HashMap<Integer, Integer[]>();

        for (final Integer state : VIRTUAL_STAT_MAP.keySet()) {
            final Integer[] stateIds = VIRTUAL_STAT_MAP.get(state);

            Arrays.sort(stateIds);

            if (Arrays.binarySearch(stateIds, additionalStateId) >= 0) {
                possibleMatches.put(state, stateIds);
            }
        }

        final Integer[] idsFromOldState = VIRTUAL_STAT_MAP.get(oldState);
        int maxMatchCount = -1;
        int newState = 0;

        for (final Integer state : possibleMatches.keySet()) {
            final Integer[] stateIds = VIRTUAL_STAT_MAP.get(state);
            int matchCount = 0;
            Arrays.sort(stateIds);

            for (final Integer id : idsFromOldState) {
                if (Arrays.binarySearch(stateIds, id) >= 0) {
                    ++matchCount;
                }
            }

            if (matchCount > maxMatchCount) {
                maxMatchCount = matchCount;
                newState = state;
            }
        }

        return newState;
    }

    /**
     * DOCUMENT ME!
     */
    private void activateButtons() {
        int rights = 0;

        if (actionSachbearbeiter) {
            rights = rights
                        | PERMISSION_SB;
        }

        if (actionNaturschutz) {
            rights = rights
                        | PERMISSION_NB;
        }

        if (actionWasser) {
            rights = rights
                        | PERMISSION_WB;
        }

        if (actionBearbeiter) {
            // It is not allowed to change the state for the role bearbeiter
            return;
        }

        final HashSet<JButton> buttonsToActivate = new HashSet<JButton>();
        final HashSet<JButton> buttonsToDeActivate = new HashSet<JButton>();

        for (final Integer stat : VIRTUAL_STAT_MAP.keySet()) {
            if ((stateMachine.getRoleForState(stat) & rights) != 0) {
                final Integer[] ids = VIRTUAL_STAT_MAP.get(stat);

                if (ids != null) {
                    for (final int statId : ids) {
                        buttonsToActivate.add(STAT_ID_TO_BUTTON.get(statId));
                    }
                }
            }
        }

        final Integer currentState = stateMachine.getState();

        if (currentState != -1) {
            final Integer[] ids = VIRTUAL_STAT_MAP.get(currentState);

            if (ids != null) {
                for (final int statId : ids) {
                    buttonsToDeActivate.add(STAT_ID_TO_BUTTON.get(statId));
                }
            }
        }

        jbPlanung.setEnabled(buttonsToActivate.contains(jbPlanung) && !buttonsToDeActivate.contains(jbPlanung));
        jbAntrag.setEnabled(buttonsToActivate.contains(jbAntrag) && !buttonsToDeActivate.contains(jbAntrag));
        jbPruefungNb.setEnabled(buttonsToActivate.contains(jbPruefungNb)
                    && !buttonsToDeActivate.contains(jbPruefungNb));
        jbPruefungWb.setEnabled(buttonsToActivate.contains(jbPruefungWb)
                    && !buttonsToDeActivate.contains(jbPruefungWb));
        jbGenehmigtNb.setEnabled(buttonsToActivate.contains(jbGenehmigtNb)
                    && !buttonsToDeActivate.contains(jbGenehmigtNb));
        jbGenehmigtWb.setEnabled(buttonsToActivate.contains(jbGenehmigtWb)
                    && !buttonsToDeActivate.contains(jbGenehmigtWb));
        jbAngenommen.setEnabled(buttonsToActivate.contains(jbAngenommen)
                    && !buttonsToDeActivate.contains(jbAngenommen));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  id    mo DOCUMENT ME!
     * @param  name  DOCUMENT ME!
     */
    private void addPa(final int id, final String name) {
        final GupGewaesserPreview comp = new GupGewaesserPreview(initialReadOnly);
        final GridBagConstraints constraint = new GridBagConstraints(
                0,
                y++,
                1,
                1,
                1,
                0,
                GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(20, 10, 10, 10),
                0,
                0);
        panGewaesserInner.add(comp, constraint);
        comp.setBeanName(name);
        comp.loadCidsBean(id);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mo  DOCUMENT ME!
     */
    private void addPa(final MetaObject mo) {
        final GupGewaesserPreview comp = new GupGewaesserPreview(readOnly);
        final GridBagConstraints constraint = new GridBagConstraints(
                0,
                y++,
                1,
                1,
                1,
                0,
                GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(20, 10, 10, 10),
                0,
                0);
        panGewaesserInner.add(comp, constraint);
        comp.setBeanName(String.valueOf(mo.getBean().getProperty("name")));
        comp.setCidsBean(mo.getBean());
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
            "gup_gup",
            3,
            1280,
            1024);
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
    }

    /**
     * DOCUMENT ME!
     */
    private void freezeAllPlanungsabschnitte() {
        // do not invoke from the edt
        // initializedLock ensures that the planungsabschnitt objects were loaded
        initializedLock.readLock().lock();
        try {
            for (int i = 0; i < panGewaesserInner.getComponentCount(); ++i) {
                final GupGewaesserPreview prev = (GupGewaesserPreview)panGewaesserInner.getComponent(i);
                CidsBean paBean = null;

                while (paBean == null) {
                    paBean = prev.getCidsBean();

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        // nothing to do
                    }
                }

                freezePlanungsabschnitt(paBean);
            }
            try {
                cidsBean.setProperty(PROP_FREEZE, Boolean.TRUE);
                cidsBean.setProperty(PROP_FREEZE_TIME, new java.sql.Timestamp(System.currentTimeMillis()));
            } catch (Exception ex) {
                LOG.error("Probleme beim Einfrieren des GUP", ex);
            }
        } finally {
            initializedLock.readLock().unlock();
        }
    }

    /**
     * Determines, if all massnahmen objects are ready for the given status.
     *
     * @param   status  The new status
     *
     * @return  DOCUMENT ME!
     */
    private boolean isApproved(final Integer status) {
        // todo: Wartendialog anzeigen
        initializedLock.readLock().lock();
        try {
            for (int i = 0; i < panGewaesserInner.getComponentCount(); ++i) {
                final GupGewaesserPreview prev = (GupGewaesserPreview)panGewaesserInner.getComponent(i);
                CidsBean paBean = null;

                while (paBean == null) {
                    paBean = prev.getCidsBean();

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        // nothing to do
                    }
                }

                final List<CidsBean> maBeans = paBean.getBeanCollectionProperty("massnahmen");

                for (final CidsBean tmp : maBeans) {
                    if (status == STAT_NB_ABG) {
                        final Boolean approved = (Boolean)tmp.getProperty(PROP_ACCEPTED_NB);
                        final Boolean refused = (Boolean)tmp.getProperty(PROP_DECLINED_NB);
                        final Boolean notRequired = (Boolean)tmp.getProperty(PROP_NOT_REQUIRED_NB);

                        if (((approved == null) || !approved) && ((refused == null) || !refused)
                                    && ((notRequired == null) || !notRequired)) {
                            return false;
                        }
                    } else if (status == STAT_WB_ABG) {
                        final Boolean approved = (Boolean)tmp.getProperty(PROP_ACCEPTED_WB);
                        final Boolean refused = (Boolean)tmp.getProperty(PROP_DECLINED_WB);

                        if (((approved == null) || !approved) && ((refused == null) || !refused)) {
                            return false;
                        }
                    } else {
                        // STAT_ANGENOMMEN will be assumed
                        final Boolean approvedNb = (Boolean)tmp.getProperty(PROP_ACCEPTED_NB);
                        final Boolean refusedNb = (Boolean)tmp.getProperty(PROP_DECLINED_NB);
                        final Boolean notRequired = (Boolean)tmp.getProperty(PROP_NOT_REQUIRED_NB);
                        final Boolean approvedWb = (Boolean)tmp.getProperty(PROP_ACCEPTED_WB);
                        final Boolean refusedWb = (Boolean)tmp.getProperty(PROP_DECLINED_WB);

                        // proposal was refused not completely checked
                        if (((approvedNb == null) || !approvedNb) && ((refusedNb == null) || !refusedNb)
                                    && ((notRequired == null) || !notRequired)) {
                            return false;
                        }
                        if (((approvedWb == null) || !approvedWb) && ((refusedWb == null) || !refusedWb)) {
                            return false;
                        }

                        // proposal was refused
                        if (((refusedNb != null) && refusedNb) || ((refusedWb != null) && refusedWb)) {
                            return false;
                        }
                    }
                }
            }

            return true;
        } finally {
            initializedLock.readLock().unlock();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  pl  DOCUMENT ME!
     */
    private void freezePlanungsabschnitt(final CidsBean pl) {
        final CidsBean route = (CidsBean)pl.getProperty("linie.von.route");
        final Double min = (Double)pl.getProperty("linie.von.wert");
        final Double max = (Double)pl.getProperty("linie.bis.wert");
        try {
            pl.setProperty("eingefrorene_schutzgebiete", null);
            pl.setProperty("eingefrorene_operative_ziele", null);
            pl.setProperty("eingefrorene_entwicklungsziele", null);
            pl.setProperty("eingefrorene_verbreitungsraeume", null);
            pl.setProperty("eingefrorene_umlandnutzung", null);
        } catch (Exception ex) {
            LOG.error("Problem beim Einfrieren eines Planungsabschnittes", ex);
        }

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<String, Void>() {

                @Override
                protected String doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(min);
                    in.add(max);
                    in.add(route.getProperty("gwk"));
                    final MetaObject[] beanArray = GupHelper.schutzgebietCache.calcValue(in);

                    final CidsBean route = CidsBeanSupport.createNewCidsBeanFromTableName("schutzgebiet_route");
                    final List<CidsBean> beanCollection = route.getBeanCollectionProperty("schutzgebiete");

                    for (final MetaObject bean : beanArray) {
                        beanCollection.add(bean.getBean());
                    }

                    return route.toJSONString(true);
                }

                @Override
                protected void done() {
                    try {
                        final String schutzgebiete = get();

                        pl.setProperty("eingefrorene_schutzgebiete", schutzgebiete);
                    } catch (Exception e) {
                        LOG.error("Problem beim Einfrieren der Schutzgebiete", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<String, Void>() {

                @Override
                protected String doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(min);
                    in.add(max);
                    in.add(route.getProperty("gwk"));
                    final MetaObject[] beanArray = GupHelper.operativeZieleCache.calcValue(in);
                    final CidsBean route = CidsBeanSupport.createNewCidsBeanFromTableName("gup_operatives_ziel_route");

                    final List<CidsBean> beanCollection = route.getBeanCollectionProperty("ziele");

                    for (final MetaObject bean : beanArray) {
                        beanCollection.add(bean.getBean());
                    }

                    return route.toJSONString(true);
                }

                @Override
                protected void done() {
                    try {
                        final String opZiele = get();

                        pl.setProperty("eingefrorene_operative_ziele", opZiele);
                    } catch (Exception e) {
                        LOG.error("Problem beim Einfrieren der PflegezielenZiele", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<String, Void>() {

                @Override
                protected String doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(min);
                    in.add(max);
                    in.add(route.getProperty("gwk"));
                    final MetaObject[] beanArray = GupHelper.entwicklungszielCache.calcValue(in);

                    final CidsBean route = CidsBeanSupport.createNewCidsBeanFromTableName("entwicklungsziel_route");
                    final List<CidsBean> beanCollection = route.getBeanCollectionProperty("entwicklungsziele");

                    for (final MetaObject bean : beanArray) {
                        beanCollection.add(bean.getBean());
                    }

                    return route.toJSONString(true);
                }

                @Override
                protected void done() {
                    try {
                        final String entZiele = get();

                        pl.setProperty("eingefrorene_entwicklungsziele", entZiele);
                    } catch (Exception e) {
                        LOG.error("Problem beim Einfrieren der WRRL-Massnahmen (frueher Entwicklungsziele)", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<String, Void>() {

                @Override
                protected String doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(min);
                    in.add(max);
                    in.add(route.getProperty("gwk"));
                    final MetaObject[] beanArray = GupHelper.umlandCache.calcValue(in);

                    final CidsBean route = CidsBeanSupport.createNewCidsBeanFromTableName("umlandnutzung_route");
                    final List<CidsBean> beanCollection = route.getBeanCollectionProperty("umlandnutzung");

                    for (final MetaObject bean : beanArray) {
                        beanCollection.add(bean.getBean());
                    }

                    return route.toJSONString(true);
                }

                @Override
                protected void done() {
                    try {
                        final String entZiele = get();

                        pl.setProperty("eingefrorene_umlandnutzung", entZiele);
                    } catch (Exception e) {
                        LOG.error("Problem beim Einfrieren der Umlandnutzung", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new javax.swing.SwingWorker<String, Void>() {

                private final MetaClass VERMEIDUNGSGRUPPE = ClassCacheMultiple.getMetaClass(
                        WRRLUtil.DOMAIN_NAME,
                        "VERMEIDUNGSGRUPPE");

                private final String query = "select " + VERMEIDUNGSGRUPPE.getID() + ", v."
                            + VERMEIDUNGSGRUPPE.getPrimaryKey()
                            + " from "
                            + VERMEIDUNGSGRUPPE.getTableName()
                            + " v join VERMEIDUNGSGRUPPE_GESCHUETZTE_ART vga on v.arten = vga.vermeidungsgruppe_reference "
                            + "join geschuetzte_art ga on vga.art = ga.id where ga.id = ";

                @Override
                protected String doInBackground() throws Exception {
                    final List in = new ArrayList(3);
                    in.add(min);
                    in.add(max);
                    in.add(route.getProperty("gwk"));
                    final MetaObject[] mo = GupHelper.verbreitungsraumCache.calcValue(in);
                    final List<CidsBean> beanCollection = new ArrayList<CidsBean>();

                    for (final MetaObject tmp : mo) {
                        final MetaObject[] vermeidungsgruppen = MetaObjectCache.getInstance()
                                    .getMetaObjectsByQuery(
                                        query
                                        + tmp.getBean().getProperty("art.id"),
                                        WRRLUtil.DOMAIN_NAME);

                        if (vermeidungsgruppen != null) {
                            for (final MetaObject bean : vermeidungsgruppen) {
                                final CidsBean newBean = CidsBean.createNewCidsBeanFromTableName(
                                        WRRLUtil.DOMAIN_NAME,
                                        "gup_vermeidungsgruppe_art");
                                newBean.setProperty("art", tmp.getBean());
                                newBean.setProperty("vermeidungsgruppe", bean.getBean());
                                beanCollection.add(newBean);
                            }
                        }
                    }

                    return CidsBean.toJSONString(true, beanCollection);
                }

                @Override
                protected void done() {
                    try {
                        final String entZiele = get();

                        pl.setProperty("eingefrorene_verbreitungsraeume", entZiele);
                    } catch (Exception e) {
                        LOG.error("Problem beim Einfrieren der Verbreitungsräume", e);
                    }
                }
            });

        de.cismet.tools.CismetThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        boolean isReady = false;

                        while (!isReady) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                // nothing to do
                            }
                            isReady = (pl.getProperty("eingefrorene_schutzgebiete") != null)
                                        && (pl.getProperty("eingefrorene_operative_ziele") != null)
                                        && (pl.getProperty("eingefrorene_verbreitungsraeume") != null)
                                        && (pl.getProperty("eingefrorene_umlandnutzung") != null)
                                        && (pl.getProperty("eingefrorene_entwicklungsziele") != null);
                        }

                        pl.setProperty(PROP_FREEZE, Boolean.TRUE);
                        pl.persist();
                    } catch (Exception e) {
                        LOG.error("Problem beim Speichern des Beans.", e);
                    }
                }
            });
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        ((DocumentDropList)jlObjectList).editorClosed(event);

        if (event.getStatus() == EditorSaveStatus.SAVE_SUCCESS) {
            // saves the references between the gup_planungsabschnitt objects and the gup object
            if (planToAdd.size() > 0) {
                final int deps = planToAdd.size();
                final WaitDialog wd = new WaitDialog(
                        StaticSwingTools.getParentFrame(this),
                        true,
                        "Speichere Abhängigkeit 1 von "
                                + deps,
                        null);

                new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                int n = 0;
                                synchronized (wd.getTreeLock()) {
                                    wd.setMax(deps);
                                }
                                while (planToAdd.size() > 0) {
                                    final CidsBean bean = planToAdd.get(0);
                                    try {
                                        bean.setProperty("gup", event.getSavedBean());
                                        bean.persist();
                                        planToAdd.remove(0);
                                        synchronized (wd.getTreeLock()) {
                                            wd.setProgress(++n);
                                            wd.setText("Speichere Abhängigkeit " + (n + 1) + " von " + deps);
                                        }
                                    } catch (Exception e) {
                                        LOG.error("Error while saving a gup object.", e);
                                    }
                                }
                            } catch (Exception e) {
                                LOG.error("Error while saving a gup object.", e);
                            } finally {
                                waitUntilVisible();
                                wd.setVisible(false);
                                wd.dispose();
                            }
                        }

                        private void waitUntilVisible() {
                            while (!wd.isVisible()) {
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    // nothing to do
                                }
                            }
                        }
                    }).start();

                StaticSwingTools.showDialog(wd);

                EventQueue.invokeLater(new Thread(new Runnable() {

                            @Override
                            public void run() {
                                if (treePath != null) {
                                    UIUtil.refreshTree(treePath);
                                }
                            }
                        }));
            }
        }
    }
    @Override
    public boolean prepareForSave() {
        return true;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    @Override
    public JComponent getTitleComponent() {
        return panTitle;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * This list shows the planungsabschnitte objects.
     *
     * @version  $Revision$, $Date$
     */
    private class PlanungsabschnittPanel extends JPanel implements CidsBeanDropListener {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param  beans  DOCUMENT ME!
         */
        @Override
        public void beansDropped(final ArrayList<CidsBean> beans) {
            if (readOnly) {
                // ignore the drop action
                return;
            }

            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Gup_planungsabschnitt")) {
                    planToAdd.add(bean);
                    cidsBean.setArtificialChangeFlag(true);
                    addPa(bean.getMetaObject());
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class CidsbeanToStringConverter extends Converter<CidsBean, String> {

        //~ Methods ------------------------------------------------------------

        @Override
        public String convertForward(final CidsBean s) {
            return getTitle();
        }

        @Override
        public CidsBean convertReverse(final String t) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
