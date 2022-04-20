/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * BioMstStammdatenEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.Point;

import org.jdesktop.swingx.treetable.TreeTableModel;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.tree.TreePath;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.SchadstoffeSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkFromEzgSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CoordinateFromGeometryConverter;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollectionEvent;
import de.cismet.cismap.commons.features.FeatureCollectionListener;
import de.cismet.cismap.commons.features.PureNewFeature;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.BrowserLauncher;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.StaticSwingTools;

import static javax.swing.SwingConstants.BOTTOM;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.LEFT;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class KaAnlageEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    DocumentListener,
    FeatureCollectionListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KaAnlageEditor.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "ka_suevo");

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;
    private int measureNumber = 0;
    private boolean noDocumentUpdate = false;
    private List<CidsBean> beansToSave = new ArrayList<CidsBean>();
    private ConnectionContext cc = ConnectionContext.create(
            AbstractConnectionContext.Category.EDITOR,
            "KaAnlageEditor");
    private CustomTableModel treeTableModel = null;
    private DefaultComboBoxModel model = new DefaultComboBoxModel();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack1;
    private javax.swing.JButton btnForward;
    private javax.swing.JButton butCancel;
    private javax.swing.JButton butGeometry;
    private javax.swing.JButton butOk;
    private javax.swing.JComboBox<String> cbGeom;
    private javax.swing.JDialog diaGeom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.KaSuevoEditor kaSuevoEditor1;
    private javax.swing.JLabel lblAkt;
    private javax.swing.JLabel lblAktVal;
    private javax.swing.JLabel lblBehGrad;
    private javax.swing.JLabel lblBehGradVal;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblEinleitstelle;
    private javax.swing.JLabel lblEinleitstelleVal;
    private javax.swing.JLabel lblEzg;
    private javax.swing.JLabel lblEzgVal;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGenKap;
    private javax.swing.JLabel lblGenKapVal;
    private javax.swing.JLabel lblGid;
    private javax.swing.JLabel lblGidVal;
    private javax.swing.JLabel lblGk;
    private javax.swing.JLabel lblGkVal;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblInBetrieb;
    private javax.swing.JLabel lblInBetriebVal;
    private javax.swing.JLabel lblKaName;
    private javax.swing.JLabel lblKaNameVal;
    private javax.swing.JLabel lblKat;
    private javax.swing.JLabel lblKatVal;
    private javax.swing.JLabel lblObjType;
    private javax.swing.JLabel lblObjTypeVal;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusVal;
    private javax.swing.JLabel lblTableTitle;
    private javax.swing.JLabel lblUwb;
    private javax.swing.JLabel lblUwbName;
    private javax.swing.JLabel lblUwbNameVal;
    private javax.swing.JLabel lblUwbVal;
    private javax.swing.JLabel lblWbbl;
    private javax.swing.JLabel lblWbblVal;
    private javax.swing.JLabel lblWkK;
    private javax.swing.JLabel lblWkKVal;
    private javax.swing.JLabel lblZvName;
    private javax.swing.JLabel lblZvNameVal;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panScr;
    private javax.swing.JPanel panStamm;
    private javax.swing.JTable tabTable;
    private javax.swing.JTextField txtJahr;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public KaAnlageEditor() {
        this(false);
    }

    /**
     * Creates a new LawaEditor object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public KaAnlageEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();

        if (readOnly) {
            RendererTools.makeReadOnly(jTextArea1);
        }

        CismapBroker.getInstance().getMappingComponent().getFeatureCollection().addFeatureCollectionListener(this);

        refreshGeometryModel();

        cbGeom.setModel(model);
        cbGeom.setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(final JList<? extends Object> list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    final Component c = super.getListCellRendererComponent(
                            list,
                            value,
                            index,
                            isSelected,
                            cellHasFocus);

                    if ((c instanceof JLabel) && (value instanceof PureNewFeature)) {
                        ((JLabel)c).setText(((PureNewFeature)value).getName());
                    } else if ((c instanceof JLabel) && (value == null)) {
                        ((JLabel)c).setText("keine Geometrie");
                    }

                    return c;
                }
            });

        tabTable.getTableHeader().addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(final MouseEvent e) {
                    final int col = tabTable.getColumnModel().getColumnIndexAtX(e.getX());

                    if (col <= 2) {
                        if (treeTableModel instanceof CustomTableModel) {
                            treeTableModel.sort(col);

                            EventQueue.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        for (int i = 0; i < tabTable.getColumnCount(); ++i) {
                                            tabTable.getColumn(tabTable.getColumnName(i))
                                                    .setHeaderRenderer(new DefaultTableHeaderCellRenderer());
                                        }
                                    }
                                });
                        }
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                }
            });
        txtJahr.getDocument().addDocumentListener(this);

        lblWbblVal.addMouseListener(new MouseAdapter() {

                boolean isHandCursor = false;

                @Override
                public void mouseClicked(final MouseEvent e) {
                    final String wbblLink = (String)cidsBean.getProperty("wbbl_link");
                    if ((wbblLink != null) && !wbblLink.equals("")) {
                        try {
                            BrowserLauncher.openURL(wbblLink);
                        } catch (Exception ex) {
                            LOG.warn(ex, ex);
                        }
                    }
                }

                @Override
                public void mouseMoved(final MouseEvent e) {
                    if (!isHandCursor) {
                        lblWbblVal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        isHandCursor = true;
                    } else if (isHandCursor) {
                        lblWbblVal.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        isHandCursor = false;
                    }
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                    if (isHandCursor) {
                        lblWbblVal.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        isHandCursor = false;
                    }
                }
            });
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
            txtJahr.setText(String.valueOf(new GregorianCalendar().get(GregorianCalendar.YEAR)));
            refreshMeasures();
            bindingGroup.bind();

            String wbblLink = (String)cidsBean.getProperty("wbbl_link");

            if ((wbblLink != null) && !wbblLink.equals("")) {
                if (wbblLink.contains("/")) {
                    wbblLink = wbblLink.substring(wbblLink.lastIndexOf("/") + 1);

                    if (wbblLink.contains(".")) {
                        wbblLink = wbblLink.substring(0, wbblLink.lastIndexOf("."));
                        lblWbblVal.setText(wbblLink);
                    }
                }
            }

            final Thread t = new Thread("SchadstoffeRetriever") {

                    @Override
                    public void run() {
                        try {
                            final CidsServerSearch search = new SchadstoffeSearch((Integer)cidsBean.getProperty("id"));

                            final Collection res = SessionManager.getProxy()
                                        .customServerSearch(SessionManager.getSession().getUser(), search, cc);

                            EventQueue.invokeLater(new Thread("fillTreeTable") {

                                    @Override
                                    public void run() {
                                        treeTableModel = new CustomTableModel((ArrayList<ArrayList<Object>>)res);
                                        ((org.jdesktop.swingx.JXTreeTable)tabTable).setTreeTableModel(treeTableModel);

                                        for (int i = 0; i < tabTable.getColumnCount(); ++i) {
                                            tabTable.getColumn(tabTable.getColumnName(i))
                                                    .setHeaderRenderer(new DefaultTableHeaderCellRenderer());
                                        }
                                    }
                                });
                        } catch (final Exception e) {
                            LOG.error("Cannot retrieve Schadstoffe", e);
                        }
                    }
                };

            t.start();
            final Thread tWk = new Thread("WkkRetriever") {

                    @Override
                    public void run() {
                        try {
                            final CidsServerSearch search = new WkFromEzgSearch((Integer)cidsBean.getProperty("id"));

                            final ArrayList<ArrayList> res = (ArrayList<ArrayList>)SessionManager.getProxy()
                                        .customServerSearch(SessionManager.getSession().getUser(), search, cc);

                            EventQueue.invokeLater(new Thread("fillTreeTable") {

                                    @Override
                                    public void run() {
                                        lblWkKVal.setText((String)res.get(0).get(2));
                                        lblWkKVal.setToolTipText((String)res.get(0).get(2));
                                    }
                                });
                        } catch (final Exception e) {
                            LOG.error("Cannot retrieve Schadstoffe", e);
                        }
                    }
                };

            tWk.start();
        } else {
            kaSuevoEditor1.setCidsBean(null);
        }

        lblFoot.setText("");
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
        diaGeom = new javax.swing.JDialog();
        cbGeom = new javax.swing.JComboBox<>();
        butOk = new javax.swing.JButton();
        butCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        panStamm = new javax.swing.JPanel();
        lblObjType = new javax.swing.JLabel();
        lblKaName = new javax.swing.JLabel();
        lblGidVal = new javax.swing.JLabel();
        lblObjTypeVal = new javax.swing.JLabel();
        lblKaNameVal = new javax.swing.JLabel();
        lblGenKap = new javax.swing.JLabel();
        lblGk = new javax.swing.JLabel();
        lblBehGrad = new javax.swing.JLabel();
        lblGenKapVal = new javax.swing.JLabel();
        lblGkVal = new javax.swing.JLabel();
        lblBehGradVal = new javax.swing.JLabel();
        lblEzg = new javax.swing.JLabel();
        lblEzgVal = new javax.swing.JLabel();
        lblGid = new javax.swing.JLabel();
        lblUwb = new javax.swing.JLabel();
        lblUwbVal = new javax.swing.JLabel();
        lblWkK = new javax.swing.JLabel();
        lblWkKVal = new javax.swing.JLabel();
        lblEinleitstelle = new javax.swing.JLabel();
        lblKat = new javax.swing.JLabel();
        lblKatVal = new javax.swing.JLabel();
        lblUwbName = new javax.swing.JLabel();
        lblUwbNameVal = new javax.swing.JLabel();
        lblZvName = new javax.swing.JLabel();
        lblZvNameVal = new javax.swing.JLabel();
        lblBemerkung = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblStatus = new javax.swing.JLabel();
        lblStatusVal = new javax.swing.JLabel();
        lblInBetrieb = new javax.swing.JLabel();
        lblInBetriebVal = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblEinleitstelleVal = new javax.swing.JLabel();
        if (!readOnly) {
            butGeometry = new javax.swing.JButton();
        }
        lblAkt = new javax.swing.JLabel();
        lblAktVal = new javax.swing.JLabel();
        lblWbbl = new javax.swing.JLabel();
        lblWbblVal = new javax.swing.JLabel();
        panScr = new javax.swing.JPanel();
        txtJahr = new javax.swing.JTextField();
        btnBack1 = new javax.swing.JButton();
        btnForward = new javax.swing.JButton();
        kaSuevoEditor1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.KaSuevoEditor();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabTable = new org.jdesktop.swingx.JXTreeTable();
        lblTableTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        diaGeom.setTitle(org.openide.util.NbBundle.getMessage(
                KaAnlageEditor.class,
                "KaAnlageEditor.diaGeom.title",
                new Object[] {})); // NOI18N
        diaGeom.getContentPane().setLayout(new java.awt.GridBagLayout());

        cbGeom.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbGeom.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 20, 15);
        diaGeom.getContentPane().add(cbGeom, gridBagConstraints);

        butOk.setText(org.openide.util.NbBundle.getMessage(
                KaAnlageEditor.class,
                "KaAnlageEditor.butOk.text",
                new Object[] {})); // NOI18N
        butOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    butOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 15, 0);
        diaGeom.getContentPane().add(butOk, gridBagConstraints);

        butCancel.setText(org.openide.util.NbBundle.getMessage(
                KaAnlageEditor.class,
                "KaAnlageEditor.butCancel.text",
                new Object[] {})); // NOI18N
        butCancel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    butCancelActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 15);
        diaGeom.getContentPane().add(butCancel, gridBagConstraints);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(
                KaAnlageEditor.class,
                "KaAnlageEditor.jLabel1.text",
                new Object[] {})); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 0);
        diaGeom.getContentPane().add(jLabel1, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1110, 1350));
        setPreferredSize(new java.awt.Dimension(1110, 1350));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(1110, 1350));
        panInfo.setPreferredSize(new java.awt.Dimension(1110, 1350));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                KaAnlageEditor.class,
                "KaAnlageEditor.lblHeading.text",
                new Object[] {})); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        panStamm.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                org.openide.util.NbBundle.getMessage(
                    KaAnlageEditor.class,
                    "KaAnlageEditor.panStamm.border.title",
                    new Object[] {}))); // NOI18N
        panStamm.setOpaque(false);
        panStamm.setLayout(new java.awt.GridBagLayout());

        lblObjType.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblGew.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblObjType, gridBagConstraints);

        lblKaName.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblLage.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblKaName, gridBagConstraints);

        lblGidVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblGidVal.setPreferredSize(new java.awt.Dimension(150, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.id}"),
                lblGidVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblGidVal, gridBagConstraints);

        lblObjTypeVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblObjTypeVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.obj_type}"),
                lblObjTypeVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.obj_type}"),
                lblObjTypeVal,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblObjTypeVal, gridBagConstraints);

        lblKaNameVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblKaNameVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ka_name}"),
                lblKaNameVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ka_name}"),
                lblKaNameVal,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblKaNameVal, gridBagConstraints);

        lblGenKap.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblWk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblGenKap, gridBagConstraints);

        lblGk.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblHR.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblGk, gridBagConstraints);

        lblBehGrad.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblLawa.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblBehGrad, gridBagConstraints);

        lblGenKapVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblGenKapVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gen_kap}"),
                lblGenKapVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblGenKapVal, gridBagConstraints);

        lblGkVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblGkVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk}"),
                lblGkVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblGkVal, gridBagConstraints);

        lblBehGradVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblBehGradVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.beh_grad_text}"),
                lblBehGradVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.beh_grad_text}"),
                lblBehGradVal,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblBehGradVal, gridBagConstraints);

        lblEzg.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblSti.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblEzg, gridBagConstraints);

        lblEzgVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblEzgVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ezg}"),
                lblEzgVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("error");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ezg}"),
                lblEzgVal,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblEzgVal, gridBagConstraints);

        lblGid.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblMst.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblGid, gridBagConstraints);

        lblUwb.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblSti1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblUwb, gridBagConstraints);

        lblUwbVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblUwbVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.geom_uwb}"),
                lblUwbVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new CoordinateFromGeometryConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblUwbVal, gridBagConstraints);

        lblWkK.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblLawa1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblWkK, gridBagConstraints);

        lblWkKVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblWkKVal.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblWkKVal, gridBagConstraints);

        lblEinleitstelle.setText(org.openide.util.NbBundle.getMessage(
                KaAnlageEditor.class,
                "KaAnlageEditor.lblLawa2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblEinleitstelle, gridBagConstraints);

        lblKat.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblWk1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblKat, gridBagConstraints);

        lblKatVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblKatVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.merkmal}"),
                lblKatVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblKatVal, gridBagConstraints);

        lblUwbName.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblWk2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblUwbName, gridBagConstraints);

        lblUwbNameVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblUwbNameVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uwb}"),
                lblUwbNameVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uwb}"),
                lblUwbNameVal,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblUwbNameVal, gridBagConstraints);

        lblZvName.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblWk3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblZvName, gridBagConstraints);

        lblZvNameVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblZvNameVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zv_id.name}"),
                lblZvNameVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zv_id.name}"),
                lblZvNameVal,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblZvNameVal, gridBagConstraints);

        lblBemerkung.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblWk4.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblBemerkung, gridBagConstraints);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(150, 80));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(150, 80));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(3);
        jTextArea1.setTabSize(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wrrl_bemerkung}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(jScrollPane1, gridBagConstraints);

        lblStatus.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblStatus.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblStatus, gridBagConstraints);

        lblStatusVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblStatusVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.status}"),
                lblStatusVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.status}"),
                lblStatusVal,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblStatusVal, gridBagConstraints);

        lblInBetrieb.setText(org.openide.util.NbBundle.getMessage(
                KaAnlageEditor.class,
                "KaAnlageEditor.lblInBetrieb.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblInBetrieb, gridBagConstraints);

        lblInBetriebVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblInBetriebVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.inbetrieb}"),
                lblInBetriebVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblInBetriebVal, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblEinleitstelleVal.setMinimumSize(new java.awt.Dimension(150, 20));
        lblEinleitstelleVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.geom_wrrl}"),
                lblEinleitstelleVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        binding.setConverter(new CoordinateFromGeometryConverter());
        bindingGroup.addBinding(binding);

        lblEinleitstelleVal.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblEinleitstelleValMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        jPanel2.add(lblEinleitstelleVal, gridBagConstraints);

        if (!readOnly) {
            butGeometry.setIcon(new javax.swing.ImageIcon(
                    getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
            butGeometry.setToolTipText(org.openide.util.NbBundle.getMessage(
                    KaAnlageEditor.class,
                    "KaAnlageEditor.butGeometry.toolTipText",
                    new Object[] {}));                                                                             // NOI18N
            butGeometry.setPreferredSize(new java.awt.Dimension(50, 20));
            butGeometry.addActionListener(new java.awt.event.ActionListener() {

                    @Override
                    public void actionPerformed(final java.awt.event.ActionEvent evt) {
                        butGeometryActionPerformed(evt);
                    }
                });
        }
        if (!readOnly) {
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 10;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
            jPanel2.add(butGeometry, gridBagConstraints);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panStamm.add(jPanel2, gridBagConstraints);

        lblAkt.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblAkt.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblAkt, gridBagConstraints);

        lblAktVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblAktVal.setPreferredSize(new java.awt.Dimension(150, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.comment}"),
                lblAktVal,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblAktVal, gridBagConstraints);

        lblWbbl.setText(org.openide.util.NbBundle.getMessage(KaAnlageEditor.class, "KaAnlageEditor.lblWbbl.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panStamm.add(lblWbbl, gridBagConstraints);

        lblWbblVal.setForeground(new java.awt.Color(28, 72, 227));
        lblWbblVal.setMinimumSize(new java.awt.Dimension(200, 20));
        lblWbblVal.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panStamm.add(lblWbblVal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 0, 10);
        panInfoContent.add(panStamm, gridBagConstraints);

        panScr.setOpaque(false);
        panScr.setLayout(new java.awt.GridBagLayout());

        txtJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        txtJahr.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(txtJahr, gridBagConstraints);

        btnBack1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left.png")));          // NOI18N
        btnBack1.setBorder(null);
        btnBack1.setBorderPainted(false);
        btnBack1.setContentAreaFilled(false);
        btnBack1.setFocusPainted(false);
        btnBack1.setMaximumSize(new java.awt.Dimension(30, 30));
        btnBack1.setMinimumSize(new java.awt.Dimension(30, 30));
        btnBack1.setPreferredSize(new java.awt.Dimension(30, 30));
        btnBack1.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left-pressed.png")));  // NOI18N
        btnBack1.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-left-selected.png"))); // NOI18N
        btnBack1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnBack1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(btnBack1, gridBagConstraints);

        btnForward.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right.png")));          // NOI18N
        btnForward.setBorder(null);
        btnForward.setBorderPainted(false);
        btnForward.setContentAreaFilled(false);
        btnForward.setFocusPainted(false);
        btnForward.setMaximumSize(new java.awt.Dimension(30, 30));
        btnForward.setMinimumSize(new java.awt.Dimension(30, 30));
        btnForward.setPreferredSize(new java.awt.Dimension(30, 30));
        btnForward.setPressedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right-pressed.png")));  // NOI18N
        btnForward.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/arrow-right-selected.png"))); // NOI18N
        btnForward.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnForwardActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panScr.add(btnForward, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        panInfoContent.add(panScr, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        panInfoContent.add(kaSuevoEditor1, gridBagConstraints);

        jScrollPane2.setMinimumSize(new java.awt.Dimension(20, 250));
        jScrollPane2.setViewportView(tabTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        panInfoContent.add(jScrollPane2, gridBagConstraints);

        lblTableTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTableTitle.setText(org.openide.util.NbBundle.getMessage(
                KaAnlageEditor.class,
                "KaAnlageEditor.lblTableTitle.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 10);
        panInfoContent.add(lblTableTitle, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(jPanel1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnBack1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnBack1ActionPerformed
        int year = getCurrentlyEnteredYear();

        if (--measureNumber < 0) {
            measureNumber = 0;
            --year;
        }

        noDocumentUpdate = true;
        txtJahr.setText(String.valueOf(year));
        noDocumentUpdate = false;

        final int newYear = year;
        new Thread(new Runnable() {

                @Override
                public void run() {
                    synchronized (KaAnlageEditor.this) {
                        CidsBean measure = null;
                        int measureYear = newYear;

                        do {
                            txtJahr.setText(String.valueOf(measureYear));
                            measure = getDataForYear(measureYear, measureNumber);
                            showNewMeasure(measure);
                            --measureYear;
                        } while ((measure == null) && (measureYear > 2006));
                    }
                }
            }).start();
    } //GEN-LAST:event_btnBack1ActionPerformed
    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnForwardActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnForwardActionPerformed
        int year = getCurrentlyEnteredYear();

        noDocumentUpdate = true;
        txtJahr.setText(String.valueOf(year));
        ++measureNumber;
        final CidsBean measure = getDataForYear(year, measureNumber);

        if (measure == null) {
            measureNumber = 0;
            ++year;
            txtJahr.setText(String.valueOf(year));

            final int newYear = year;
            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        synchronized (KaAnlageEditor.this) {
                            CidsBean measure = null;
                            int measureYear = newYear;
                            final int currentYear = (new GregorianCalendar()).get(GregorianCalendar.YEAR);

                            do {
                                txtJahr.setText(String.valueOf(measureYear));
                                measure = getDataForYear(measureYear, measureNumber);
                                showNewMeasure(measure);
                                ++measureYear;
                            } while ((measure == null) && (measureYear <= currentYear));
                        }
                    }
                }).start();
        } else {
            showNewMeasure(measure);
        }

        noDocumentUpdate = false;
    } //GEN-LAST:event_btnForwardActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblEinleitstelleValMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblEinleitstelleValMouseClicked
    }                                                                                   //GEN-LAST:event_lblEinleitstelleValMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void butCancelActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_butCancelActionPerformed
        diaGeom.setVisible(false);
    }                                                                             //GEN-LAST:event_butCancelActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void butOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_butOkActionPerformed
        diaGeom.setVisible(false);
        final PureNewFeature selectedFeature = (PureNewFeature)cbGeom.getSelectedItem();

        try {
            if (selectedFeature == null) {
                cidsBean.setProperty("geom_wrrl", null);
                cidsBean.setProperty("geom.geo_field", null);
            } else {
                cidsBean.setProperty("geom_wrrl", selectedFeature.getGeometry());
                cidsBean.setProperty("geom.geo_field", selectedFeature.getGeometry());
            }
        } catch (Exception e) {
            LOG.error("Cannot change geometry", e);
        }
    } //GEN-LAST:event_butOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void butGeometryActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_butGeometryActionPerformed
        diaGeom.setSize(350, 150);
        StaticSwingTools.centerWindowOnScreen(diaGeom);
        diaGeom.setVisible(true);
    }                                                                               //GEN-LAST:event_butGeometryActionPerformed

    /**
     * DOCUMENT ME!
     */
    private void refreshGeometryModel() {
        model.removeAllElements();

        for (final Feature f : getAllNewFeatures()) {
            model.addElement(f);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private List<PureNewFeature> getAllNewFeatures() {
        final List<PureNewFeature> allNewFeatures = new ArrayList<PureNewFeature>();
        allNewFeatures.add(null);

        if (CismapBroker.getInstance().getMappingComponent() != null) {
            final List<Feature> allFeatures = CismapBroker.getInstance()
                        .getMappingComponent()
                        .getFeatureCollection()
                        .getAllFeatures();

            for (final Feature f : allFeatures) {
                if ((f instanceof PureNewFeature)) {
                    if (f.getGeometry() instanceof Point) {
                        allNewFeatures.add((PureNewFeature)f);
                    }
                }
            }
        } else {
            LOG.error("cismap not found. No content in the editor."); // NOI18N
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("getAllNewFeatures " + allNewFeatures);         // NOI18N
        }

        return allNewFeatures;
    }

    /**
     * shows the measure of the currently entered year.
     */
    private void refreshMeasures() {
        final int year = getCurrentlyEnteredYear();
        measureNumber = 0;

        final CidsBean measure = getDataForYear(year, measureNumber);
        showNewMeasure(measure);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  measure  DOCUMENT ME!
     */
    private void showNewMeasure(final CidsBean measure) {
        if (!readOnly) {
            saveLastMeasure();
            kaSuevoEditor1.setCidsBean(measure, cidsBean);
        } else {
            kaSuevoEditor1.setCidsBean(measure);
        }
    }

    /**
     * adds the last processed bean to the beansToSave list, if it is not in, yet.
     */
    private void saveLastMeasure() {
        if (!readOnly) {
            final CidsBean lastMeasure = kaSuevoEditor1.getCidsBean();

            if ((lastMeasure != null) && !beansToSave.contains(lastMeasure)) {
                beansToSave.add(lastMeasure);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int getCurrentlyEnteredYear() {
        try {
            return Integer.parseInt(txtJahr.getText());
        } catch (final NumberFormatException e) {
            return new GregorianCalendar().get(GregorianCalendar.YEAR);
        }
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return String.valueOf(cidsBean.getProperty("ka_name"));
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
    }

    @Override
    public boolean prepareForSave() {
        saveLastMeasure();
        for (final CidsBean tmp : beansToSave) {
            try {
                tmp.persist();
            } catch (final Exception e) {
                LOG.error("Exception ehile saving the last measure.", e);
            }
        }

        beansToSave.clear();
        return true;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   year    DOCUMENT ME!
     * @param   number  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getDataForYear(final int year, final int number) {
        try {
            String query = "select " + MC.getID() + ", m." + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
            query += " m, ka_anlage a";                                                                         // NOI18N
            query += " WHERE m.gid = a.id AND a.id = " + cidsBean.getProperty("id");                            // NOI18N
            query += " AND jahr = " + year + " order by id asc";                                                // NOI18N

            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            if ((metaObjects != null) && (number >= 0) && (number < metaObjects.length)) {
                final CidsBean retVal = metaObjects[number].getBean();
                int index = -1;

                // if the bean is already in the beansToSave list, the bean from the list should be used
                if ((index = beansToSave.indexOf(retVal)) != -1) {
                    return beansToSave.get(index);
                } else {
                    beansToSave.add(retVal);
                    return retVal;
                }
            } else {
                return null;
            }
        } catch (final ConnectionException e) {
            LOG.error("Error while trying to receive measurements.", e); // NOI18N
            return null;
        }
    }

    @Override
    public void insertUpdate(final DocumentEvent e) {
        if (!noDocumentUpdate && (e.getDocument().getLength() == 4)) {
            refreshMeasures();
        }
    }

    @Override
    public void removeUpdate(final DocumentEvent e) {
        if (!noDocumentUpdate && (e.getDocument().getLength() == 4)) {
            refreshMeasures();
        }
    }

    @Override
    public void changedUpdate(final DocumentEvent e) {
        // nothing to do
    }

    @Override
    public void featuresAdded(final FeatureCollectionEvent fce) {
        refreshGeometryModel();
    }

    @Override
    public void allFeaturesRemoved(final FeatureCollectionEvent fce) {
        refreshGeometryModel();
    }

    @Override
    public void featuresRemoved(final FeatureCollectionEvent fce) {
        refreshGeometryModel();
    }

    @Override
    public void featuresChanged(final FeatureCollectionEvent fce) {
        refreshGeometryModel();
    }

    @Override
    public void featureSelectionChanged(final FeatureCollectionEvent fce) {
        refreshGeometryModel();
    }

    @Override
    public void featureReconsiderationRequested(final FeatureCollectionEvent fce) {
        refreshGeometryModel();
    }

    @Override
    public void featureCollectionChanged() {
        refreshGeometryModel();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class CustomTableModel implements TreeTableModel {

        //~ Static fields/initializers -----------------------------------------

        private static final String[] COLUMN_NAMES = {
                "Kurzname",
                "Jahr",
                "Schadstoff",
                "Datum",
                "Messwert",
                "Einheit"
            };

        //~ Instance fields ----------------------------------------------------

        private TreeNode data;
        private List<TreeModelListener> listener = new ArrayList<TreeModelListener>();
        private ArrayList<ArrayList<Object>> rawData;
        private Map<Integer, Boolean> sortDirection = new HashMap<>();
        private RowSorter.SortKey currentSortKey = null;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomTableModel object.
         *
         * @param  rawData  DOCUMENT ME!
         */
        public CustomTableModel(final ArrayList<ArrayList<Object>> rawData) {
            this.rawData = rawData;
            initData(rawData);
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param  rawData  DOCUMENT ME!
         */
        private void initData(final ArrayList<ArrayList<Object>> rawData) {
            data = new TreeNode("root");

            if (rawData != null) {
                for (final ArrayList<Object> tmp : rawData) {
                    data.addNode(tmp);
                }
            }

            for (final TreeModelListener tmp : listener) {
                tmp.treeStructureChanged(new TreeModelEvent(this, new Object[] { data }));
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param  column  DOCUMENT ME!
         */
        public void sort(final int column) {
            Boolean direction = sortDirection.get(column);

            if (direction == null) {
                direction = Boolean.TRUE;
            } else {
                direction = !direction;
            }

            sortDirection.put(column, direction);

            rawData.sort(new RowComparator(column, direction));
            initData(rawData);

            currentSortKey = new RowSorter.SortKey(column, (direction ? SortOrder.ASCENDING : SortOrder.DESCENDING));
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public RowSorter.SortKey getSortKey() {
            return currentSortKey;
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return String.class;
        }

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public String getColumnName(final int column) {
            return COLUMN_NAMES[column];
        }

        @Override
        public int getHierarchicalColumn() {
            return 2;
        }

        @Override
        public Object getValueAt(final Object node, final int column) {
            final Object obj = ((TreeNode)node).getData(column);

            if (obj != null) {
                return String.valueOf(obj);
            } else {
                return obj;
            }
        }

        @Override
        public boolean isCellEditable(final Object node, final int column) {
            return false;
        }

        @Override
        public void setValueAt(final Object value, final Object node, final int column) {
        }

        @Override
        public Object getRoot() {
            return data;
        }

        @Override
        public Object getChild(final Object parent, final int index) {
            return ((TreeNode)parent).getChild(index);
        }

        @Override
        public int getChildCount(final Object parent) {
            return ((TreeNode)parent).getChildrenCount();
        }

        @Override
        public boolean isLeaf(final Object node) {
            return ((TreeNode)node).getChildrenCount() == 0;
        }

        @Override
        public void valueForPathChanged(final TreePath path, final Object newValue) {
        }

        @Override
        public int getIndexOfChild(final Object parent, final Object child) {
            final TreeNode node = (TreeNode)parent;

            for (int i = 0; i < node.getChildrenCount(); ++i) {
                if (node.getChild(i).equals(child)) {
                    return i;
                }
            }

            return -1;
        }

        @Override
        public void addTreeModelListener(final TreeModelListener l) {
            listener.add(l);
        }

        @Override
        public void removeTreeModelListener(final TreeModelListener l) {
            listener.remove(l);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class RowComparator implements Comparator<ArrayList<Object>> {

        //~ Instance fields ----------------------------------------------------

        private int col;
        private boolean direction;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new RowComparator object.
         *
         * @param  col        DOCUMENT ME!
         * @param  direction  DOCUMENT ME!
         */
        public RowComparator(final int col, final boolean direction) {
            this.col = col;
            this.direction = direction;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final ArrayList<Object> o1, final ArrayList<Object> o2) {
            if ((o1.get(col) instanceof Comparable) && (o2.get(col) instanceof Comparable)) {
                return considerDirection(((Comparable)o1.get(col)).compareTo(o2.get(col)));
            } else if ((o1.get(col) instanceof Comparable) && !(o2.get(col) instanceof Comparable)) {
                return considerDirection(1);
            } else if ((o2.get(col) instanceof Comparable) && !(o1.get(col) instanceof Comparable)) {
                return considerDirection(-1);
            } else {
                return 0;
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param   compareResult  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private int considerDirection(final int compareResult) {
            if (direction) {
                return compareResult;
            } else {
                return compareResult * -1;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class TreeNode {

        //~ Instance fields ----------------------------------------------------

        private List<TreeNode> nodes = new ArrayList<>();
        private String key;
        private List<Object> data = new ArrayList<>();

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new TreeNode object.
         *
         * @param  key  DOCUMENT ME!
         */
        public TreeNode(final String key) {
            this.key = key;
        }

        /**
         * Creates a new TreeNode object.
         *
         * @param  data  DOCUMENT ME!
         */
        public TreeNode(final List<Object> data) {
            this.data = data;
        }

        /**
         * Creates a new TreeNode object.
         *
         * @param  key   DOCUMENT ME!
         * @param  data  DOCUMENT ME!
         */
        public TreeNode(final String key, final List<Object> data) {
            this.key = key;
            this.data = data;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   column  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public Object getData(final int column) {
            if ((data == null) || (data.size() == 0)) {
                return "";
            }

            if ((key != null) && (column > 2)) {
                return "";
            }

            if (column == 5) {
                if ((data.get(2) != null) && ((String)data.get(2)).equalsIgnoreCase("GF")) {
                    return null;
                } else {
                    return "mg/l";
                }
            }

            return data.get(column);
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public List<TreeNode> getChildren() {
            return nodes;
        }

        /**
         * DOCUMENT ME!
         *
         * @param   index  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public TreeNode getChild(final int index) {
            return nodes.get(index);
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public int getChildrenCount() {
            return nodes.size();
        }

        /**
         * DOCUMENT ME!
         *
         * @param  obj  DOCUMENT ME!
         */
        public void addNode(final List<Object> obj) {
            final String k = createKey(obj);

            if (k.equals(key)) {
                nodes.add(new TreeNode(obj));
            } else {
                boolean nodeFound = false;

                for (final TreeNode node : nodes) {
                    if (node.key.equals(k)) {
                        node.addNode(obj);
                        nodeFound = true;
                    }
                }

                if (!nodeFound) {
                    final TreeNode newNode = new TreeNode(createKey(obj), obj);
                    newNode.addNode(obj);
                    nodes.add(newNode);
                }
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param   obj  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private String createKey(final List<Object> obj) {
            return String.valueOf(obj.get(0)) + "|" + String.valueOf(obj.get(1)) + "|" + String.valueOf(obj.get(2));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class DefaultTableHeaderCellRenderer extends DefaultTableCellRenderer {

        //~ Constructors -------------------------------------------------------

        /**
         * Constructs a <code>DefaultTableHeaderCellRenderer</code>.
         *
         * <P>The horizontal alignment and text position are set as appropriate to a table header cell, and the opaque
         * property is set to false.</P>
         */
        public DefaultTableHeaderCellRenderer() {
            setHorizontalAlignment(CENTER);
        }

        //~ Methods ------------------------------------------------------------

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
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBackground(UIManager.getColor("TableHeader.background"));
            setIcon(getIcon(table, column));
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            return this;
        }

        /**
         * Overloaded to return an icon suitable to the primary sorted column, or null if the column is not the primary
         * sort key.
         *
         * @param   table   the <code>JTable</code>.
         * @param   column  the column index.
         *
         * @return  the sort icon, or null if the column is unsorted.
         */
        protected Icon getIcon(final JTable table, final int column) {
            final RowSorter.SortKey sortKey = getSortKey(table, column);
            if ((sortKey != null)) {
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
            if (table instanceof org.jdesktop.swingx.JXTreeTable) {
                final TreeTableModel model = ((org.jdesktop.swingx.JXTreeTable)table).getTreeTableModel();
                final RowSorter.SortKey sortKey = ((CustomTableModel)model).getSortKey();

                if ((sortKey != null) && (sortKey.getColumn() == column)) {
                    return sortKey;
                }
            }
//            if (treeTableModel != null) {
//                return treeTableModel.getSortKey();
//            }

            return null;
        }
    }
}
