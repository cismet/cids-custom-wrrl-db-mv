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

import java.sql.Time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.TreePath;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.PlanungsabschnittSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.DocumentDropList;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.StateMachine;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.VermeidungsgruppeReadOnlyBandMember;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.CismetThreadPool;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.TitleComponentProvider;
import de.cismet.tools.gui.WaitDialog;
import de.cismet.tools.gui.jbands.SimpleBandModel;

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

    public static final String WORKFLOW_STATUS_PROPERTY = "status";

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupGupEditor.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_planungsabschnitt");
    private static final MetaClass MC_WF = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "gup_workflow_status");
    // The user and the action attributes cannot be changed while the navigator is running,
    // so the action attributes can be saved in a static context
    private static boolean actionSachbearbeiter;
    private static boolean actionPruefer;
    private static boolean actionNaturschutz;
    private static final String ICON_PLANUNG = "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Draft.png";
    private static final String ICON_ANTRAG =
        "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/application_from_storage.png";
    private static final String ICON_PRUEFUNG = "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Test tubes.png";
    private static final String ICON_GENEHMIGT = "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/approve_24.png";
    private static final String ICON_GESCHLOSSEN = "/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Valid_16.png";
    private static final int STAT_PLANUNG = 0;
    private static final int STAT_ANTRAG = 1;
    private static final int STAT_PRUEFUNG = 2;
    private static final int STAT_GENEHMIGT = 3;
    private static final int STAT_ANGENOMMEN = 4;
    public static final int ID_PLANUNG = 1;
    public static final int ID_ANTRAG = 2;
    public static final int ID_PRUEFUNG = 3;
    public static final int ID_GENEHMIGT = 4;
    public static final int ID_GESCHLOSSEN = -1;
    public static final int[][] STATE_MATRIX = {
            { 0, 1, 0, 0, 0 },
            { 1, 0, 2, 0, 0 },
            { 2, 0, 0, 2, 0 },
            { 1, 0, 0, 0, 2 },
            { 0, 0, 0, 0, 0 }
        };
    private static MetaObject[] STATE_BEANS = null;

    static {
        readActions();

        CismetThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    final String query = "SELECT "
                                + MC_WF.getID() + ", "
                                + MC_WF.getPrimaryKey()
                                + " FROM "
                                + MC_WF.getTableName();
                    try {
                        final MetaObject[] mosWkFg;
                        STATE_BEANS = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
                    } catch (Exception ex) {
                        LOG.error("error while loading wk_fgs", ex);
                    }
                }
            });
    }

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    private boolean initialReadOnly = false;
    private List<CidsBean> planToAdd = new ArrayList<CidsBean>();
    private TreePath treePath;
    private int y = 0;
    private final StateMachine stateMachine = new StateMachine(STATE_MATRIX);
    private ReentrantReadWriteLock initializedLock = new ReentrantReadWriteLock();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butNewPlan;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGenehmigungsbehoerde;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbUnterhaltungspflichtiger;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcBis;
    private de.cismet.cids.editors.DefaultBindableDateChooser dcVon;
    private javax.swing.JButton jbAdd;
    private javax.swing.JButton jbAngenommen;
    private javax.swing.JButton jbAntrag;
    private javax.swing.JButton jbDownload;
    private javax.swing.JButton jbGenehmigt;
    private javax.swing.JButton jbPlanung;
    private javax.swing.JButton jbPruefung;
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
    private javax.swing.JLabel lblSepa;
    private javax.swing.JLabel lblSepa1;
    private javax.swing.JLabel lblSepa2;
    private javax.swing.JLabel lblSepa3;
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
        scrollGewaesser.getViewport().setOpaque(false);
        butNewPlan.setVisible(!readOnly);

        jbAdd.setEnabled(!readOnly);
        jpDelete.setEnabled(!readOnly);
        jbAngenommen.setEnabled(false);
        jbAntrag.setEnabled(false);
        jbGenehmigt.setEnabled(false);
        jbPlanung.setEnabled(false);
        jbPruefung.setEnabled(false);

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
    private static void readActions() {
        final User usr = SessionManager.getSession().getUser();
        try {
            actionSachbearbeiter = SessionManager.getProxy().hasConfigAttr(usr, "gup.sachbearbeiter");
            actionPruefer = SessionManager.getProxy().hasConfigAttr(usr, "gup.genehmigungsbehoerde");
            actionNaturschutz = SessionManager.getProxy().hasConfigAttr(usr, "gup.genehmigungsbehoerde");
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
    public static boolean hasActionPruefer() {
        return actionPruefer;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static boolean hasNaturschutz() {
        return actionNaturschutz;
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
        lblErlaeuterungsberichte = new javax.swing.JLabel();
        jsObjectList = new javax.swing.JScrollPane();
        jlObjectList = new DocumentDropList(readOnly, "dokumente");
        jbDownload = new javax.swing.JButton();
        lblGenehmigungsbehoerde = new javax.swing.JLabel();
        cbGenehmigungsbehoerde = new ScrollableComboBox();
        jpDelete = new javax.swing.JButton();
        dcVon = new de.cismet.cids.editors.DefaultBindableDateChooser();
        dcBis = new de.cismet.cids.editors.DefaultBindableDateChooser();
        jbAdd = new javax.swing.JButton();
        panState = new javax.swing.JPanel();
        jbPlanung = new javax.swing.JButton();
        lblSepa = new javax.swing.JLabel();
        jbAntrag = new javax.swing.JButton();
        lblSepa1 = new javax.swing.JLabel();
        jbPruefung = new javax.swing.JButton();
        lblSepa2 = new javax.swing.JLabel();
        jbGenehmigt = new javax.swing.JButton();
        lblSepa3 = new javax.swing.JLabel();
        jbAngenommen = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        lblCurrentState = new javax.swing.JLabel();
        cbUnterhaltungspflichtiger = new ScrollableComboBox();
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

        panInfo.setMinimumSize(new java.awt.Dimension(557, 248));
        panInfo.setPreferredSize(new java.awt.Dimension(1130, 248));

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
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 5, 15);
        panInfoContent.add(lblErlaeuterungsberichte, gridBagConstraints);

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
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 15);
        panInfoContent.add(jsObjectList, gridBagConstraints);

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
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 15);
        panInfoContent.add(jbDownload, gridBagConstraints);

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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
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
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 15);
        panInfoContent.add(jpDelete, gridBagConstraints);

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
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 15);
        panInfoContent.add(jbAdd, gridBagConstraints);

        panState.setOpaque(false);
        panState.setLayout(new java.awt.FlowLayout(0));

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
        panState.add(jbPlanung);

        lblSepa.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblSepa.text")); // NOI18N
        panState.add(lblSepa);

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
        panState.add(jbAntrag);

        lblSepa1.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblSepa1.text")); // NOI18N
        panState.add(lblSepa1);

        jbPruefung.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/Test tubes.png"))); // NOI18N
        jbPruefung.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.jbPruefung.toolTipText"));                                                    // NOI18N
        jbPruefung.setMaximumSize(new java.awt.Dimension(45, 30));
        jbPruefung.setMinimumSize(new java.awt.Dimension(45, 30));
        jbPruefung.setPreferredSize(new java.awt.Dimension(45, 30));
        jbPruefung.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbPruefungActionPerformed(evt);
                }
            });
        panState.add(jbPruefung);

        lblSepa2.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblSepa2.text")); // NOI18N
        panState.add(lblSepa2);

        jbGenehmigt.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/approve_24.png"))); // NOI18N
        jbGenehmigt.setToolTipText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.jbGenehmigt.toolTipText"));                                                   // NOI18N
        jbGenehmigt.setMaximumSize(new java.awt.Dimension(45, 30));
        jbGenehmigt.setMinimumSize(new java.awt.Dimension(45, 30));
        jbGenehmigt.setPreferredSize(new java.awt.Dimension(45, 30));
        jbGenehmigt.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbGenehmigtActionPerformed(evt);
                }
            });
        panState.add(jbGenehmigt);

        lblSepa3.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblSepa3.text")); // NOI18N
        panState.add(lblSepa3);

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
        panState.add(jbAngenommen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
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
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 5, 5);
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
        setState(STAT_PLANUNG, ID_PLANUNG);
    }                                                                             //GEN-LAST:event_jbPlanungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbAntragActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbAntragActionPerformed
        setState(STAT_ANTRAG, ID_ANTRAG);
    }                                                                            //GEN-LAST:event_jbAntragActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbPruefungActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbPruefungActionPerformed
        setState(STAT_PRUEFUNG, ID_PRUEFUNG);
    }                                                                              //GEN-LAST:event_jbPruefungActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbGenehmigtActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbGenehmigtActionPerformed
        setState(STAT_GENEHMIGT, ID_GENEHMIGT);
    }                                                                               //GEN-LAST:event_jbGenehmigtActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbAngenommenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbAngenommenActionPerformed
        setState(STAT_ANGENOMMEN, ID_GESCHLOSSEN);
    }                                                                                //GEN-LAST:event_jbAngenommenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblNameMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblNameMouseClicked
        final GupGewaesserPreview prev = (GupGewaesserPreview)panGewaesserInner.getComponent(0);
        final CidsBean pl = prev.getCidsBean();

        freezePlanungsabschnitt(pl);
    } //GEN-LAST:event_lblNameMouseClicked

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
            Integer status = (Integer)cidsBean.getProperty(WORKFLOW_STATUS_PROPERTY + ".id");
            final Boolean geschlossen = (Boolean)cidsBean.getProperty("geschlossen");

            if (status == null) {
                status = STAT_PLANUNG;
            } else if ((geschlossen != null) && geschlossen.booleanValue()) {
                status = STAT_ANGENOMMEN;
            } else {
                status = status - 1;
            }

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
     * @param  readOnly  DOCUMENT ME!
     */
    private void setReadOnly(final boolean readOnly) {
        this.readOnly = readOnly;
        butNewPlan.setVisible(!readOnly);

        jbAdd.setEnabled(!readOnly);
        jpDelete.setEnabled(!readOnly);
        jbAngenommen.setEnabled(false);
        jbAntrag.setEnabled(false);
        jbGenehmigt.setEnabled(false);
        jbPlanung.setEnabled(false);
        jbPruefung.setEnabled(false);
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

        final Boolean isClosed = (Boolean)cidsBean.getProperty("geschlossen");

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
            final CidsServerSearch search = new PlanungsabschnittSearch(cidsBean.getProperty("id").toString());
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
        Integer statId = (Integer)cidsBean.getProperty("status.id");
        String statName = (String)cidsBean.getProperty("status.name");
        final Boolean closed = (Boolean)cidsBean.getProperty("geschlossen");
        String icon = "";

        if ((statName == null) || (statId == null)) {
            statName = "Planung/Abstimmung";
            statId = STAT_PLANUNG;
        }

        if ((closed != null) && closed.booleanValue()) {
            statName = "Geschlossen";
            icon = ICON_GESCHLOSSEN;
        } else {
            switch (statId) {
                case 1: {
                    icon = ICON_PLANUNG;
                    break;
                }
                case 2: {
                    icon = ICON_ANTRAG;
                    break;
                }
                case 3: {
                    icon = ICON_PRUEFUNG;
                    break;
                }
                case 4: {
                    icon = ICON_GENEHMIGT;
                    break;
                }
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
     * @param  state    DOCUMENT ME!
     * @param  stateId  DOCUMENT ME!
     */
    private void setState(final Integer state, final Integer stateId) {
        CidsBean stateObject = null;
        final int oldState = stateMachine.getState();

        if ((state == STAT_GENEHMIGT) && !isApproved()) {
            JOptionPane.showMessageDialog(
                this,
                "Es wurde noch nicht alle Maßnahmen genehmigt",
                "Prüfung unvollständig",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        stateMachine.setState(state);

        for (final MetaObject tmp : STATE_BEANS) {
            if (tmp.getBean().getProperty("id").equals(stateId)) {
                stateObject = tmp.getBean();
            }
        }

        try {
            if (stateId == ID_GESCHLOSSEN) {
                cidsBean.setProperty("geschlossen", true);
                cidsBean.setProperty("status_wechsel", new java.sql.Date(new Date().getTime()));

                CismetThreadPool.execute(new Runnable() {

                        @Override
                        public void run() {
                            freezeAllPlanungsabschnitte();
                        }
                    });
            } else {
                final Boolean closed = (Boolean)cidsBean.getProperty("geschlossen");

                if ((closed != null) && closed.booleanValue()) {
                    cidsBean.setProperty("geschlossen", false);
                }

                cidsBean.setProperty(WORKFLOW_STATUS_PROPERTY, stateObject);
                cidsBean.setProperty("status_wechsel", new java.sql.Date(new Date().getTime()));
            }
        } catch (Exception e) {
            LOG.error("Cannot change the state of the gup.", e);
            stateMachine.forceState(oldState);
        }

        activateButtons();
        setTitleStatus();
    }

    /**
     * DOCUMENT ME!
     */
    private void activateButtons() {
        int rights = 0;

        if (actionSachbearbeiter) {
            rights = 1;
        }

        if (actionPruefer) {
            rights += 2;
        }

        jbPlanung.setEnabled(false);
        jbAntrag.setEnabled(false);
        jbPruefung.setEnabled(false);
        jbGenehmigt.setEnabled(false);
        jbAngenommen.setEnabled(false);

        if ((stateMachine.getRoleForState(STAT_PLANUNG) & rights) != 0) {
            jbPlanung.setEnabled(true);
        }
        if ((stateMachine.getRoleForState(STAT_ANTRAG) & rights) != 0) {
            jbAntrag.setEnabled(true);
        }
        if ((stateMachine.getRoleForState(STAT_PRUEFUNG) & rights) != 0) {
            jbPruefung.setEnabled(true);
        }
        if ((stateMachine.getRoleForState(STAT_GENEHMIGT) & rights) != 0) {
            jbGenehmigt.setEnabled(true);
        }
        if ((stateMachine.getRoleForState(STAT_ANGENOMMEN) & rights) != 0) {
            jbAngenommen.setEnabled(true);
        }
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
                cidsBean.setProperty("eingefroren", Boolean.TRUE);
                cidsBean.setProperty("eingefroren_zeit", new java.sql.Timestamp(System.currentTimeMillis()));
            } catch (Exception ex) {
                LOG.error("Probleme beim Einfrieren des GUP", ex);
            }
        } finally {
            initializedLock.readLock().unlock();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isApproved() {
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
                    final Boolean app = (Boolean)tmp.getProperty("angenommen");
                    final Boolean changed = (Boolean)tmp.getProperty("geaendert_nach_pruefung");

                    if (((changed != null) && changed.booleanValue()) || ((app == null) || !app.booleanValue())) {
                        return false;
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
                    final MetaObject[] beanArray = GupPlanungsabschnittEditor.getSchutzgebietCache().calcValue(in);

                    final List<CidsBean> beanCollection = new ArrayList<CidsBean>();

                    for (final MetaObject bean : beanArray) {
                        beanCollection.add(bean.getBean());
                    }

                    return CidsBean.toJSONString(true, beanCollection);
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
                    final MetaObject[] beanArray = GupPlanungsabschnittEditor.getOperativeZieleCache().calcValue(in);

                    final List<CidsBean> beanCollection = new ArrayList<CidsBean>();

                    for (final MetaObject bean : beanArray) {
                        beanCollection.add(bean.getBean());
                    }

                    return CidsBean.toJSONString(true, beanCollection);
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
                    final MetaObject[] beanArray = GupPlanungsabschnittEditor.getEntwicklungszielCache().calcValue(in);

                    final List<CidsBean> beanCollection = new ArrayList<CidsBean>();

                    for (final MetaObject bean : beanArray) {
                        beanCollection.add(bean.getBean());
                    }

                    return CidsBean.toJSONString(true, beanCollection);
                }

                @Override
                protected void done() {
                    try {
                        final String entZiele = get();

                        pl.setProperty("eingefrorene_entwicklungsziele", entZiele);
                    } catch (Exception e) {
                        LOG.error("Problem beim Einfrieren der Entwicklungsziele", e);
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
                    final MetaObject[] beanArray = GupPlanungsabschnittEditor.getUmlandCache().calcValue(in);

                    final List<CidsBean> beanCollection = new ArrayList<CidsBean>();

                    for (final MetaObject bean : beanArray) {
                        beanCollection.add(bean.getBean());
                    }

                    return CidsBean.toJSONString(true, beanCollection);
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
                    final MetaObject[] mo = GupPlanungsabschnittEditor.getVerbreitungsraumCache().calcValue(in);
                    final List<CidsBean> beanCollection = new ArrayList<CidsBean>();

                    for (final MetaObject tmp : mo) {
                        final MetaObject[] vermeidungsgruppen = MetaObjectCache.getInstance()
                                    .getMetaObjectsByQuery(query + tmp.getBean().getProperty("art.id"));

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

                        pl.setProperty("eingefroren", Boolean.TRUE);
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
