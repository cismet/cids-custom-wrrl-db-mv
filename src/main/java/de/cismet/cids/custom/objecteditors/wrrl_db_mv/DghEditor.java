/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * WkFgEditor.java
 *
 * Created on 04.08.2010, 13:13:12
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;

import org.openide.util.NbBundle;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.QbwInUseSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.*;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;

import de.cismet.commons.concurrency.CismetConcurrency;
import de.cismet.commons.concurrency.CismetExecutors;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.BrowserLauncher;
import de.cismet.tools.CismetThreadPool;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class DghEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    CidsBeanDropListener,
    PropertyChangeListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DghEditor.class);
    private static final String[] PROPERTIES_WITH_MAX_LENGTH = {
            "name",
            "pruefer",
            "bew_bem",
            "fah_hinw",
            "umsetzg",
            "ums_hinw"
        };
    private static final String[] PROPERTIES_NAME_WITH_MAX_LENGTH = {
            "Name",
            "Prüfer Effizienzkontrolle",
            "Hinweise zum Bewertungsergebnis",
            "Hinweise zur Ausführung der FAH",
            "Hinweise zu Unters./Optimierungsmaßnahmen",
            "Hinweise umgesetzte Optimierungsmaßnahmen"
        };
    private static final ConnectionContext cc = ConnectionContext.create(
            AbstractConnectionContext.Category.EDITOR,
            "DghEditor");
    private static final MetaClass WK_FG_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wk_fg");

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly;
    private Map<Integer, CidsBean> wkFgBeans = new HashMap<Integer, CidsBean>();
    private ExecutorService executor = CismetExecutors.newFixedThreadPool(
            1,
            CismetConcurrency.getInstance("dgh").createThreadFactory("dgh"));

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blbSpace;
    private javax.swing.JButton btnRemQbw;
    private javax.swing.JButton btnRemQbwGest;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbBewGes;
    private javax.swing.JCheckBox cbChecked;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEffKontr;
    private javax.swing.JComboBox cbGeom;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGutachterlicheBew;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGutachterlicheBewFische;
    private javax.swing.JComboBox<String> cbJahrKontrolle;
    private javax.swing.JComboBox<String> cbJahrNaechsteKontrolle;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbOptimierungsbedarf;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbStandardBew;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbStandardBewFische;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbUntersuchungsbedarf;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblBewGes;
    private javax.swing.JLabel lblChecked;
    private javax.swing.JLabel lblEffKontr;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGeom;
    private javax.swing.JLabel lblGutachterlicheBew;
    private javax.swing.JLabel lblGutachterlicheBewFische;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading4;
    private javax.swing.JLabel lblHeading5;
    private javax.swing.JLabel lblHinwFah;
    private javax.swing.JLabel lblHinweisBewert;
    private javax.swing.JLabel lblHinweisOptimierung;
    private javax.swing.JLabel lblHinweisUnters;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblJahrKontrolle;
    private javax.swing.JLabel lblJahrNaechsteKontrolle;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOptimierungsbedarf;
    private javax.swing.JLabel lblPruefer;
    private javax.swing.JLabel lblStandardBew;
    private javax.swing.JLabel lblStandardBewFische;
    private javax.swing.JLabel lblUntersuchungsbedarf;
    private javax.swing.JLabel lblValId;
    private javax.swing.JLabel lblValWk_bez;
    private javax.swing.JLabel lblValWk_name;
    private javax.swing.JLabel lblWk_bez;
    private javax.swing.JLabel lblWk_name;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JList lstQbw;
    private javax.swing.JList lstQbwGest;
    private javax.swing.JPanel panBew;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.RoundedPanel panGeo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo4;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo5;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent4;
    private javax.swing.JPanel panInfoContent5;
    private javax.swing.JPanel panPressuresBut1;
    private javax.swing.JPanel panPressuresBut2;
    private de.cismet.tools.gui.RoundedPanel panQbwDienen;
    private de.cismet.tools.gui.RoundedPanel panQbwDurch;
    private javax.swing.JScrollPane scpPressure1;
    private javax.swing.JScrollPane scpPressure2;
    private javax.swing.JTextArea taBemerkung;
    private javax.swing.JTextArea taHinwFah;
    private javax.swing.JTextArea taHinweisBewert;
    private javax.swing.JTextArea taHinweisOptimierung;
    private javax.swing.JTextArea taHinweisUnters;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPruefer;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenEditor object.
     */
    public DghEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public DghEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();
        panBew.addMouseListener(new MouseAdapter() {

                boolean isHandCursor = false;

                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isMouseOver(e)) {
                        try {
                            BrowserLauncher.openURL(
                                "https://www.wrrl-mv.de/static/WRRL/Dateien/Dokumente/Service/Dokumente/2013_MV_FAA_Methodenstandard_mit_Anhang_bf.pdf#page=22");
                        } catch (Exception ex) {
                            LOG.warn(ex, ex);
                        }
                    }
                }

                @Override
                public void mouseMoved(final MouseEvent e) {
                    if (!isHandCursor && isMouseOver(e)) {
                        panBew.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        isHandCursor = true;
                    } else if (isHandCursor) {
                        panBew.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        isHandCursor = false;
                    }
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                    if (isHandCursor) {
                        panBew.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        isHandCursor = false;
                    }
                }

                private boolean isMouseOver(final MouseEvent e) {
                    return ((e.getPoint().x > 10) && (e.getPoint().x < 75) && (e.getPoint().y < 18));
                }
            });
        cbEffKontr.setRenderer(new CustomCellRenderer());
        cbGutachterlicheBew.setRenderer(new CustomCellRenderer());
        cbGutachterlicheBewFische.setRenderer(new CustomCellRenderer());
        cbOptimierungsbedarf.setRenderer(new CustomCellRenderer());
        cbStandardBew.setRenderer(new CustomCellRenderer());
        cbStandardBewFische.setRenderer(new CustomCellRenderer());
        cbUntersuchungsbedarf.setRenderer(new CustomCellRenderer());
        cbBewGes.setRenderer(new CustomCellRenderer());

        if (cbGeom instanceof DefaultCismapGeometryComboBoxEditor) {
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).setAllowedGeometryTypes(
                new Class[] { LineString.class, MultiLineString.class });
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).setLocalRenderFeatureString("geom");
        }

        final List<Integer> years = new ArrayList<Integer>();
        final Integer currentYear = new GregorianCalendar().get(GregorianCalendar.YEAR);
        years.add(null);

        for (int year = 1990; year <= currentYear; ++year) {
            years.add(year);
        }

        cbJahrKontrolle.setModel(new DefaultComboBoxModel(years.toArray(new Integer[years.size()])));
        years.clear();
        years.add(null);

        for (int year = 2010; year <= 2040; ++year) {
            years.add(year);
        }
        cbJahrNaechsteKontrolle.setModel(new DefaultComboBoxModel(years.toArray(new Integer[years.size()])));

        if (readOnly) {
            panPressuresBut1.setVisible(false);
            panPressuresBut2.setVisible(false);
            RendererTools.makeReadOnly(txtPruefer);
            RendererTools.makeReadOnly(txtName);
            RendererTools.makeReadOnly(taHinwFah);
            RendererTools.makeReadOnly(taHinweisBewert);
            RendererTools.makeReadOnly(taHinweisOptimierung);
            RendererTools.makeReadOnly(taHinweisUnters);
            RendererTools.makeReadOnly(cbBewGes);
            RendererTools.makeReadOnly(cbEffKontr);
            RendererTools.makeReadOnly(cbGutachterlicheBew);
            RendererTools.makeReadOnly(cbGutachterlicheBewFische);
            RendererTools.makeReadOnly(cbJahrKontrolle);
            RendererTools.makeReadOnly(cbJahrNaechsteKontrolle);
            RendererTools.makeReadOnly(cbOptimierungsbedarf);
            RendererTools.makeReadOnly(cbStandardBew);
            RendererTools.makeReadOnly(cbStandardBewFische);
            RendererTools.makeReadOnly(cbUntersuchungsbedarf);
            RendererTools.makeReadOnly(taBemerkung);
            RendererTools.makeReadOnly(cbChecked);
            lblGeom.setVisible(false);
            cbGeom.setVisible(false);
        } else {
            lblId.setVisible(false);
            lblValId.setVisible(false);
            new CidsBeanDropTarget(lstQbw);
            new CidsBeanDropTarget(lstQbwGest);
        }

        linearReferencedLineEditor.setDrawingFeaturesEnabled(!readOnly);
        linearReferencedLineEditor.setLineField("linie"); // NOI18N
        linearReferencedLineEditor.setOtherLinesEnabled(false);

        if (!readOnly) {
            try {
                new CidsBeanDropTarget(this);
            } catch (final Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
                }
            }
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        if (this.cidsBean != null) {
            this.cidsBean.removePropertyChangeListener(this);
        }
        this.cidsBean = cidsBean;
        cidsBean.addPropertyChangeListener(this);

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
            linearReferencedLineEditor.setCidsBean(cidsBean);
            if (!readOnly) {
                zoomToFeatures();
            }
        }

        bindReadOnlyFields();
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   id  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getWkFgById(final int id) {
        CidsBean bean = wkFgBeans.get(id);

        if (bean != null) {
            return bean;
        } else {
            try {
                final MetaObject mo = SessionManager.getProxy()
                            .getMetaObject(SessionManager.getSession().getUser(),
                                id,
                                WK_FG_MC.getID(),
                                WRRLUtil.DOMAIN_NAME,
                                cc);

                if (mo != null) {
                    if (wkFgBeans.size() > 2) {
                        wkFgBeans.clear();
                    }
                    bean = mo.getBean();
                    wkFgBeans.put(id, bean);

                    return bean;
                }
            } catch (ConnectionException e) {
                LOG.error("Error while retrieving wk fg for dgh object", e);
            }
        }
        return null;
    }

    /**
     * DOCUMENT ME!
     */
    private void bindReadOnlyFields() {
        if (cidsBean == null) {
            lblValWk_name.setText("");
            lblValWk_bez.setText("");
            lblFoot.setText("");
        } else {
            final SwingWorker<CidsBean, Void> sw = new SwingWorker<CidsBean, Void>() {

                    @Override
                    protected CidsBean doInBackground() throws Exception {
                        final Integer wkId = (Integer)cidsBean.getProperty("wk_fg");

                        if (wkId != null) {
                            return getWkFgById(wkId);
                        }

                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            final CidsBean bean = get();

                            if (bean != null) {
                                lblValWk_name.setText(String.valueOf(bean.getProperty("wk_k")));
                                lblValWk_bez.setText(String.valueOf(bean.getProperty("wk_n")));
                            } else {
                                lblValWk_name.setText(CidsBeanSupport.FIELD_NOT_SET);
                                lblValWk_bez.setText(CidsBeanSupport.FIELD_NOT_SET);
                            }
                        } catch (Exception ex) {
                            LOG.error("Error while retrieving wk-fg object", ex);
                            lblValWk_name.setText(CidsBeanSupport.FIELD_NOT_SET);
                            lblValWk_bez.setText(CidsBeanSupport.FIELD_NOT_SET);
                        }
                    }
                };

            executor.submit(sw);

            // refresh footer
            Object avUser = cidsBean.getProperty("av_user"); // NOI18N
            Object avTime = cidsBean.getProperty("av_time"); // NOI18N
            if (avUser == null) {
                avUser = "(unbekannt)";
            }
            if (avTime instanceof Timestamp) {
                avTime = TimestampConverter.getInstance().convertForward((Timestamp)avTime);
            } else {
                avTime = "(unbekannt)";
            }
            lblFoot.setText("Zuletzt bearbeitet von " + avUser + " am " + avTime);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        MapUtil.zoomToFeatureCollection(linearReferencedLineEditor.getZoomFeatures());
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
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        blbSpace = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panQbwDurch = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo4 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading4 = new javax.swing.JLabel();
        panInfoContent4 = new javax.swing.JPanel();
        panPressuresBut2 = new javax.swing.JPanel();
        btnRemQbw = new javax.swing.JButton();
        scpPressure2 = new javax.swing.JScrollPane();
        lstQbw = new CidsBeanDropList("qbw");
        panQbwDienen = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo5 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading5 = new javax.swing.JLabel();
        panInfoContent5 = new javax.swing.JPanel();
        panPressuresBut1 = new javax.swing.JPanel();
        btnRemQbwGest = new javax.swing.JButton();
        scpPressure1 = new javax.swing.JScrollPane();
        lstQbwGest = new CidsBeanDropList("qbw_gest");
        jPanel2 = new javax.swing.JPanel();
        lblHinwFah = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taHinwFah = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        lblUntersuchungsbedarf = new javax.swing.JLabel();
        cbUntersuchungsbedarf = new ScrollableComboBox(new CidsBeanComparator());
        lblOptimierungsbedarf = new javax.swing.JLabel();
        cbOptimierungsbedarf = new ScrollableComboBox(new CidsBeanComparator());
        lblHinweisOptimierung = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taHinweisOptimierung = new javax.swing.JTextArea();
        lblHinweisUnters = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        taHinweisUnters = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        taBemerkung = new javax.swing.JTextArea();
        lblBemerkung = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        lblValId = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        lblWk_name = new javax.swing.JLabel();
        lblValWk_name = new javax.swing.JLabel();
        lblWk_bez = new javax.swing.JLabel();
        lblValWk_bez = new javax.swing.JLabel();
        cbEffKontr = new ScrollableComboBox(new CidsBeanComparator());
        lblEffKontr = new javax.swing.JLabel();
        lblJahrKontrolle = new javax.swing.JLabel();
        txtPruefer = new javax.swing.JTextField();
        lblPruefer = new javax.swing.JLabel();
        cbJahrKontrolle = new javax.swing.JComboBox<>();
        lblJahrNaechsteKontrolle = new javax.swing.JLabel();
        cbJahrNaechsteKontrolle = new javax.swing.JComboBox<>();
        lblChecked = new javax.swing.JLabel();
        cbChecked = new javax.swing.JCheckBox();
        panGeo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        linearReferencedLineEditor = (readOnly
                ? new LinearReferencedLineRenderer(true)
                : new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor());
        jPanel1 = new javax.swing.JPanel();
        cbGeom = readOnly ? new JComboBox() : new DefaultCismapGeometryComboBoxEditor();
        lblGeom = new javax.swing.JLabel();
        panBew = new javax.swing.JPanel();
        lblStandardBew = new javax.swing.JLabel();
        lblGutachterlicheBew = new javax.swing.JLabel();
        lblStandardBewFische = new javax.swing.JLabel();
        lblGutachterlicheBewFische = new javax.swing.JLabel();
        lblBewGes = new javax.swing.JLabel();
        lblHinweisBewert = new javax.swing.JLabel();
        cbStandardBew = new ScrollableComboBox(new CidsBeanComparator());
        cbGutachterlicheBew = new ScrollableComboBox(new CidsBeanComparator());
        cbStandardBewFische = new ScrollableComboBox(new CidsBeanComparator());
        cbGutachterlicheBewFische = new ScrollableComboBox(new CidsBeanComparator());
        cbBewGes = new ScrollableComboBox(new CidsBeanComparator());
        jScrollPane5 = new javax.swing.JScrollPane();
        taHinweisBewert = new javax.swing.JTextArea();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1080, 900));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1240, 900));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMaximumSize(new java.awt.Dimension(1350, 790));
        panInfo.setMinimumSize(new java.awt.Dimension(1080, 770));
        panInfo.setPreferredSize(new java.awt.Dimension(1280, 770));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHeading.text",
                new Object[] {})); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(blbSpace, gridBagConstraints);

        jPanel3.setMinimumSize(new java.awt.Dimension(450, 405));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(620, 405));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        panQbwDurch.setMinimumSize(new java.awt.Dimension(480, 135));
        panQbwDurch.setPreferredSize(new java.awt.Dimension(480, 135));

        panHeadInfo4.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo4.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setLayout(new java.awt.FlowLayout());

        lblHeading4.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading4.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHeading4.text",
                new Object[] {})); // NOI18N
        panHeadInfo4.add(lblHeading4);

        panQbwDurch.add(panHeadInfo4, java.awt.BorderLayout.NORTH);

        panInfoContent4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent4.setOpaque(false);
        panInfoContent4.setLayout(new java.awt.GridBagLayout());

        panPressuresBut2.setOpaque(false);
        panPressuresBut2.setLayout(new java.awt.GridBagLayout());

        btnRemQbw.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemQbw.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemQbwActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panPressuresBut2.add(btnRemQbw, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panInfoContent4.add(panPressuresBut2, gridBagConstraints);

        scpPressure2.setMinimumSize(new java.awt.Dimension(400, 90));
        scpPressure2.setPreferredSize(new java.awt.Dimension(400, 90));

        lstQbw.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.qbw}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstQbw);
        bindingGroup.addBinding(jListBinding);

        scpPressure2.setViewportView(lstQbw);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent4.add(scpPressure2, gridBagConstraints);

        panQbwDurch.add(panInfoContent4, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel3.add(panQbwDurch, gridBagConstraints);

        panQbwDienen.setMinimumSize(new java.awt.Dimension(480, 135));
        panQbwDienen.setPreferredSize(new java.awt.Dimension(480, 135));

        panHeadInfo5.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo5.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo5.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo5.setLayout(new java.awt.FlowLayout());

        lblHeading5.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading5.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHeading5.text",
                new Object[] {})); // NOI18N
        panHeadInfo5.add(lblHeading5);

        panQbwDienen.add(panHeadInfo5, java.awt.BorderLayout.NORTH);

        panInfoContent5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent5.setOpaque(false);
        panInfoContent5.setLayout(new java.awt.GridBagLayout());

        panPressuresBut1.setOpaque(false);
        panPressuresBut1.setLayout(new java.awt.GridBagLayout());

        btnRemQbwGest.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemQbwGest.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemQbwGestActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panPressuresBut1.add(btnRemQbwGest, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panInfoContent5.add(panPressuresBut1, gridBagConstraints);

        scpPressure1.setMinimumSize(new java.awt.Dimension(400, 90));
        scpPressure1.setPreferredSize(new java.awt.Dimension(400, 90));

        lstQbwGest.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.qbw_gest}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                eLProperty,
                lstQbwGest);
        bindingGroup.addBinding(jListBinding);

        scpPressure1.setViewportView(lstQbwGest);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent5.add(scpPressure1, gridBagConstraints);

        panQbwDienen.add(panInfoContent5, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanel3.add(panQbwDienen, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 0, 20);
        panInfoContent.add(jPanel3, gridBagConstraints);

        jPanel2.setMinimumSize(new java.awt.Dimension(530, 405));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(620, 405));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblHinwFah.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHinwFah.text",
                new Object[] {})); // NOI18N
        lblHinwFah.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHinwFah.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblHinwFah, gridBagConstraints);

        taHinwFah.setColumns(15);
        taHinwFah.setRows(2);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fah_hinw}"),
                taHinwFah,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(taHinwFah);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(jScrollPane2, gridBagConstraints);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(jPanel4, gridBagConstraints);

        lblUntersuchungsbedarf.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblUntersuchungsbedarf.text",
                new Object[] {})); // NOI18N
        lblUntersuchungsbedarf.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblUntersuchungsbedarf.toolTipText",
                new Object[] {})); // NOI18N
        lblUntersuchungsbedarf.setMaximumSize(new java.awt.Dimension(250, 180));
        lblUntersuchungsbedarf.setMinimumSize(new java.awt.Dimension(250, 54));
        lblUntersuchungsbedarf.setPreferredSize(new java.awt.Dimension(250, 54));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblUntersuchungsbedarf, gridBagConstraints);

        cbUntersuchungsbedarf.setMinimumSize(new java.awt.Dimension(200, 25));
        cbUntersuchungsbedarf.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bd_unters}"),
                cbUntersuchungsbedarf,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bd_unters.description}"),
                cbUntersuchungsbedarf,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbUntersuchungsbedarf, gridBagConstraints);

        lblOptimierungsbedarf.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblOptimierungsbedarf.text",
                new Object[] {})); // NOI18N
        lblOptimierungsbedarf.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblOptimierungsbedarf.toolTipText",
                new Object[] {})); // NOI18N
        lblOptimierungsbedarf.setMaximumSize(new java.awt.Dimension(250, 180));
        lblOptimierungsbedarf.setMinimumSize(new java.awt.Dimension(250, 54));
        lblOptimierungsbedarf.setPreferredSize(new java.awt.Dimension(250, 54));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblOptimierungsbedarf, gridBagConstraints);

        cbOptimierungsbedarf.setMinimumSize(new java.awt.Dimension(200, 25));
        cbOptimierungsbedarf.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bd_optim}"),
                cbOptimierungsbedarf,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bd_optim.description}"),
                cbOptimierungsbedarf,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbOptimierungsbedarf, gridBagConstraints);

        lblHinweisOptimierung.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHinweisOptimierung.text",
                new Object[] {})); // NOI18N
        lblHinweisOptimierung.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHinweisOptimierung.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblHinweisOptimierung, gridBagConstraints);

        taHinweisOptimierung.setColumns(15);
        taHinweisOptimierung.setRows(2);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.umsetzg}"),
                taHinweisOptimierung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane3.setViewportView(taHinweisOptimierung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(jScrollPane3, gridBagConstraints);

        lblHinweisUnters.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHinweisUnters.text",
                new Object[] {})); // NOI18N
        lblHinweisUnters.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHinweisUnters.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanel2.add(lblHinweisUnters, gridBagConstraints);

        taHinweisUnters.setColumns(15);
        taHinweisUnters.setRows(2);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ums_hinw}"),
                taHinweisUnters,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane4.setViewportView(taHinweisUnters);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(jScrollPane4, gridBagConstraints);

        jPanel6.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 20;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel6, gridBagConstraints);

        jScrollPane6.setMinimumSize(new java.awt.Dimension(200, 100));
        jScrollPane6.setPreferredSize(new java.awt.Dimension(200, 80));

        taBemerkung.setColumns(15);
        taBemerkung.setRows(2);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bd_bemerk}"),
                taBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane6.setViewportView(taBemerkung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanel2.add(jScrollPane6, gridBagConstraints);

        lblBemerkung.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblBemerkung.text",
                new Object[] {})); // NOI18N
        lblBemerkung.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblBemerkung.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanel2.add(lblBemerkung, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 0, 20);
        panInfoContent.add(jPanel2, gridBagConstraints);

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridBagLayout());

        lblId.setText(org.openide.util.NbBundle.getMessage(DghEditor.class, "DghEditor.lblId.text", new Object[] {})); // NOI18N
        lblId.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblId.toolTipText",
                new Object[] {}));                                                                                     // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(lblId, gridBagConstraints);

        lblValId.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValId.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.id}"),
                lblValId,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(lblValId, gridBagConstraints);

        txtName.setMinimumSize(new java.awt.Dimension(200, 25));
        txtName.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(txtName, gridBagConstraints);

        lblName.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblName.text",
                new Object[] {})); // NOI18N
        lblName.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblName.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(lblName, gridBagConstraints);

        lblWk_name.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblWk_name.text",
                new Object[] {})); // NOI18N
        lblWk_name.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblWk_name.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(lblWk_name, gridBagConstraints);

        lblValWk_name.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValWk_name.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_fg.wk_k}"),
                lblValWk_name,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(lblValWk_name, gridBagConstraints);

        lblWk_bez.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblWk_bez.text",
                new Object[] {})); // NOI18N
        lblWk_bez.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblWk_bez.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(lblWk_bez, gridBagConstraints);

        lblValWk_bez.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValWk_bez.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(lblValWk_bez, gridBagConstraints);

        cbEffKontr.setMinimumSize(new java.awt.Dimension(200, 25));
        cbEffKontr.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eff_kontr}"),
                cbEffKontr,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(cbEffKontr, gridBagConstraints);

        lblEffKontr.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblEffKontr.text",
                new Object[] {}));                     // NOI18N
        lblEffKontr.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblEffKontr.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(lblEffKontr, gridBagConstraints);

        lblJahrKontrolle.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblJahrKontrolle.text",
                new Object[] {})); // NOI18N
        lblJahrKontrolle.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblJahrKontrolle.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        jPanel7.add(lblJahrKontrolle, gridBagConstraints);

        txtPruefer.setMinimumSize(new java.awt.Dimension(200, 25));
        txtPruefer.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.pruefer}"),
                txtPruefer,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(txtPruefer, gridBagConstraints);

        lblPruefer.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblPruefer.text",
                new Object[] {})); // NOI18N
        lblPruefer.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblPruefer.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(lblPruefer, gridBagConstraints);

        cbJahrKontrolle.setMinimumSize(new java.awt.Dimension(200, 25));
        cbJahrKontrolle.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eff_jahr}"),
                cbJahrKontrolle,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(cbJahrKontrolle, gridBagConstraints);

        lblJahrNaechsteKontrolle.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblJahrNaechsteKontrolle.text",
                new Object[] {})); // NOI18N
        lblJahrNaechsteKontrolle.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblJahrNaechsteKontrolle.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        jPanel7.add(lblJahrNaechsteKontrolle, gridBagConstraints);

        cbJahrNaechsteKontrolle.setMinimumSize(new java.awt.Dimension(200, 25));
        cbJahrNaechsteKontrolle.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eff_jahr_geplant}"),
                cbJahrNaechsteKontrolle,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(cbJahrNaechsteKontrolle, gridBagConstraints);

        lblChecked.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblChecked.text",
                new Object[] {})); // NOI18N
        lblChecked.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblChecked.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(lblChecked, gridBagConstraints);

        cbChecked.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.cbChecked.text",
                new Object[] {})); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.checked}"),
                cbChecked,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(cbChecked, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 10, 20);
        panInfoContent.add(jPanel7, gridBagConstraints);

        panGeo.setMinimumSize(new java.awt.Dimension(640, 100));
        panGeo.setPreferredSize(new java.awt.Dimension(640, 100));

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHeading1.text",
                new Object[] {})); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panGeo.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        panInfoContent1.add(linearReferencedLineEditor, gridBagConstraints);

        panGeo.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 10, 20);
        panInfoContent.add(panGeo, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        if (!readOnly) {
            cbGeom.setMinimumSize(new java.awt.Dimension(300, 20));
            cbGeom.setPreferredSize(new java.awt.Dimension(300, 20));

            binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                    org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                    this,
                    org.jdesktop.beansbinding.ELProperty.create("${cidsBean.geom}"),
                    cbGeom,
                    org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
            binding.setConverter(((DefaultCismapGeometryComboBoxEditor)cbGeom).getConverter());
            bindingGroup.addBinding(binding);
        }
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel1.add(cbGeom, gridBagConstraints);

        lblGeom.setText(org.openide.util.NbBundle.getMessage(DghEditor.class, "WkSgPanOne.lblGeom.text")); // NOI18N
        lblGeom.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblGeom.toolTipText",
                new Object[] {}));                                                                         // NOI18N
        lblGeom.setMaximumSize(new java.awt.Dimension(350, 20));
        lblGeom.setMinimumSize(new java.awt.Dimension(250, 20));
        lblGeom.setPreferredSize(new java.awt.Dimension(180, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel1.add(lblGeom, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 20);
        panInfoContent.add(jPanel1, gridBagConstraints);

        panBew.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2),
                org.openide.util.NbBundle.getMessage(DghEditor.class, "DghEditor.panBew.border.title", new Object[] {
                    }),
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Dialog", 0, 12),
                new java.awt.Color(28, 72, 227))); // NOI18N
        panBew.setOpaque(false);
        panBew.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    panBewMouseClicked(evt);
                }
                @Override
                public void mouseEntered(final java.awt.event.MouseEvent evt) {
                    panBewMouseEntered(evt);
                }
            });
        panBew.setLayout(new java.awt.GridBagLayout());

        lblStandardBew.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblStandardBew.text",
                new Object[] {}));                        // NOI18N
        lblStandardBew.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblStandardBew.toolTipText")); // NOI18N
        lblStandardBew.setPreferredSize(new java.awt.Dimension(250, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 10, 0);
        panBew.add(lblStandardBew, gridBagConstraints);

        lblGutachterlicheBew.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblGutachterlicheBew.text",
                new Object[] {}));                              // NOI18N
        lblGutachterlicheBew.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblGutachterlicheBew.toolTipText")); // NOI18N
        lblGutachterlicheBew.setPreferredSize(new java.awt.Dimension(250, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panBew.add(lblGutachterlicheBew, gridBagConstraints);

        lblStandardBewFische.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblStandardBewFische.text",
                new Object[] {}));                              // NOI18N
        lblStandardBewFische.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblStandardBewFische.toolTipText")); // NOI18N
        lblStandardBewFische.setPreferredSize(new java.awt.Dimension(250, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panBew.add(lblStandardBewFische, gridBagConstraints);

        lblGutachterlicheBewFische.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblGutachterlicheBewFische.text",
                new Object[] {}));                                    // NOI18N
        lblGutachterlicheBewFische.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblGutachterlicheBewFische.toolTipText")); // NOI18N
        lblGutachterlicheBewFische.setPreferredSize(new java.awt.Dimension(250, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panBew.add(lblGutachterlicheBewFische, gridBagConstraints);

        lblBewGes.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblBewGes.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblBewGes.text",
                new Object[] {}));                             // NOI18N
        lblBewGes.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblBewGes.toolTipText"));           // NOI18N
        lblBewGes.setPreferredSize(new java.awt.Dimension(250, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panBew.add(lblBewGes, gridBagConstraints);

        lblHinweisBewert.setText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHinweisBewert.text",
                new Object[] {})); // NOI18N
        lblHinweisBewert.setToolTipText(org.openide.util.NbBundle.getMessage(
                DghEditor.class,
                "DghEditor.lblHinweisBewert.toolTipText",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panBew.add(lblHinweisBewert, gridBagConstraints);

        cbStandardBew.setMinimumSize(new java.awt.Dimension(200, 25));
        cbStandardBew.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bew1_ths}"),
                cbStandardBew,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bew1_ths.description}"),
                cbStandardBew,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 10, 0);
        panBew.add(cbStandardBew, gridBagConstraints);

        cbGutachterlicheBew.setMinimumSize(new java.awt.Dimension(200, 25));
        cbGutachterlicheBew.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bew2_ths}"),
                cbGutachterlicheBew,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panBew.add(cbGutachterlicheBew, gridBagConstraints);

        cbStandardBewFische.setMinimumSize(new java.awt.Dimension(200, 25));
        cbStandardBewFische.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bew1_fisch}"),
                cbStandardBewFische,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bew1_fisch.description}"),
                cbStandardBewFische,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panBew.add(cbStandardBewFische, gridBagConstraints);

        cbGutachterlicheBewFische.setMinimumSize(new java.awt.Dimension(200, 25));
        cbGutachterlicheBewFische.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bew2_fisch}"),
                cbGutachterlicheBewFische,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panBew.add(cbGutachterlicheBewFische, gridBagConstraints);

        cbBewGes.setMinimumSize(new java.awt.Dimension(200, 25));
        cbBewGes.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bew_ges}"),
                cbBewGes,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        panBew.add(cbBewGes, gridBagConstraints);

        taHinweisBewert.setColumns(15);
        taHinweisBewert.setRows(2);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bew_bem}"),
                taHinweisBewert,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane5.setViewportView(taHinweisBewert);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panBew.add(jScrollPane5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 10, 20);
        panInfoContent.add(panBew, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panInfo, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemQbwGestActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemQbwGestActionPerformed
        final Object selection = lstQbwGest.getSelectedValue();
        if (selection instanceof CidsBean) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll das Querbauwerk '"
                            + selection.toString()
                            + "' wirklich aus der Liste entfernt werden?",
                    "Querbauwerk entfernen",
                    JOptionPane.YES_NO_OPTION);

            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("qbw_gest"); // NOI18N
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                         //GEN-LAST:event_btnRemQbwGestActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemQbwActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemQbwActionPerformed
        final Object selection = lstQbw.getSelectedValue();
        if (selection instanceof CidsBean) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll das Querbauwerk '"
                            + selection.toString()
                            + "' wirklich aus der Liste entfernt werden?",
                    "Querbauwerk entfernen",
                    JOptionPane.YES_NO_OPTION);

            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("qbw"); // NOI18N
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                    //GEN-LAST:event_btnRemQbwActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void panBewMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_panBewMouseClicked
    }                                                                      //GEN-LAST:event_panBewMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void panBewMouseEntered(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_panBewMouseEntered
    }                                                                      //GEN-LAST:event_panBewMouseEntered

    @Override
    public void dispose() {
        if (cbGeom instanceof DefaultCismapGeometryComboBoxEditor) {
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).dispose();
        }
        if (this.cidsBean != null) {
            this.cidsBean.removePropertyChangeListener(this);
        }
        linearReferencedLineEditor.dispose();
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return (String.valueOf(cidsBean).equals("null") ? "unbenanntes DGH-Objekt" : String.valueOf(cidsBean));
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        linearReferencedLineEditor.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        if (cidsBean != null) {
            cidsBean.getMetaObject().setAllClasses();
        }

        try {
            cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());   // NOI18N
            cidsBean.setProperty("av_time", new java.sql.Timestamp(System.currentTimeMillis())); // NOI18N
        } catch (final Exception ex) {
            LOG.error("Error in prepareForSave.", ex);                                           // NOI18N
        }

        for (int i = 0; i < PROPERTIES_WITH_MAX_LENGTH.length; ++i) {
            final String propVal = (String)cidsBean.getProperty(PROPERTIES_WITH_MAX_LENGTH[i]);
            if ((propVal != null) && (propVal.length() > 254)) {
                JOptionPane.showMessageDialog(
                    this,
                    NbBundle.getMessage(
                        DghEditor.class,
                        "DghEditor.prepareForSave().formatError.message",
                        new Object[] { PROPERTIES_NAME_WITH_MAX_LENGTH[i] }),
                    NbBundle.getMessage(DghEditor.class, "DghEditor.prepareForSave().formatError.title"),
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        final Integer wkId = (Integer)cidsBean.getProperty("wk_fg");

        if (wkId != null) {
            final CidsBean wk = getWkFgById(wkId);
            final CidsBean currentLine = (CidsBean)cidsBean.getProperty("linie");

            if ((currentLine != null) && (wk != null)) {
                final long gwk = (Long)currentLine.getProperty("von.route.gwk");
                final double from = (Double)currentLine.getProperty("von.wert");
                final double to = (Double)currentLine.getProperty("bis.wert");
                double lengthOnWK = 0.0;

                for (final CidsBean teil : CidsBeanSupport.getBeanCollectionFromProperty(wk, "teile")) {
                    final CidsBean line = (CidsBean)(teil.getProperty("linie"));

                    if (line != null) {
                        final double wkFrom = (Double)line.getProperty("von.wert");
                        final double wkTo = Math.round((Double)line.getProperty("bis.wert"));

                        if (((Long)line.getProperty("von.route.gwk") == gwk) && ((int)wkFrom <= ((int)from))
                                    && ((int)wkTo >= (int)to)) {
                            lengthOnWK += Math.max((int)wkFrom, ((int)from)) - Math.min((int)wkTo, (int)to);
                        }
                    }
                }

                if (lengthOnWK >= 1.0) {
                    JOptionPane.showMessageDialog(
                        DghEditor.this,
                        NbBundle.getMessage(
                            DghEditor.class,
                            "DghEditor.prepareForSave.wkfg.message",
                            new Object[] { wk.getProperty("wk_k") }),
                        NbBundle.getMessage(DghEditor.class, "DghEditor.prepareForSave.wkfg.title"),
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }

        boolean save = true;
        save &= linearReferencedLineEditor.prepareForSave();
        return save;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        if ((cidsBean != null) && !readOnly) {
            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_fg")) { // NOI18N
                    bindToWb(bean);
                }
            }
            bindReadOnlyFields();
        }
    }

    /**
     * binds the given water body to the current CidsBean object.
     *
     * @param  propertyEntry  the water body object
     */
    private void bindToWb(final CidsBean propertyEntry) {
        try {
            cidsBean.setProperty("wk_fg", propertyEntry.getMetaObject().getId());
            bindReadOnlyFields();
        } catch (final Exception ex) {
            LOG.error("Error while binding a water body", ex); // NOI18N
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("wk_fg")) {
            bindReadOnlyFields();
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CidsBeanDropList extends JList implements CidsBeanDropListener {

        //~ Instance fields ----------------------------------------------------

        private String property;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CidsBeanDropList object.
         *
         * @param  property  DOCUMENT ME!
         */
        public CidsBeanDropList(final String property) {
            this.property = property;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void beansDropped(final ArrayList<CidsBean> al) {
            if (cidsBean != null) {
                final SwingWorker<List<CidsBean>, Void> sw = new SwingWorker<List<CidsBean>, Void>() {

                        @Override
                        protected List<CidsBean> doInBackground() throws Exception {
                            final ArrayList<CidsBean> beansToAdd = new ArrayList<CidsBean>();

                            for (final CidsBean bean : al) {
                                if ((bean != null)
                                            && bean.getMetaObject().getMetaClass().getName().equals("Querbauwerke")) {
                                    try {
                                        final QbwInUseSearch search = new QbwInUseSearch(bean.getPrimaryKeyValue());
                                        search.initWithConnectionContext(cc);

                                        final ArrayList<ArrayList> res = (ArrayList<ArrayList>)SessionManager
                                                    .getProxy()
                                                    .customServerSearch(SessionManager.getSession().getUser(),
                                                            search,
                                                            cc);

                                        if ((res != null) && (res.size() > 0)) {
                                            JOptionPane.showMessageDialog(
                                                DghEditor.this,
                                                NbBundle.getMessage(
                                                    DghEditor.class,
                                                    "DghEditor.CidsBeanDropList.beansDropped().doInBackground().message",
                                                    new Object[] {
                                                        bean.toString(),
                                                        res.get(0).get(0),
                                                        res.get(0).get(0)
                                                    }),
                                                NbBundle.getMessage(
                                                    DghEditor.class,
                                                    "DghEditor.CidsBeanDropList.beansDropped().doInBackground().title"),
                                                JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            beansToAdd.add(bean);
                                        }
                                    } catch (ConnectionException e) {
                                        LOG.error("Error while checking qbw usage");
                                    }
                                }
                            }

                            return beansToAdd;
                        }

                        @Override
                        protected void done() {
                            try {
                                final List<CidsBean> beansToAdd = get();

                                for (final CidsBean bean : beansToAdd) {
                                    final Collection<CidsBean> qbws = CidsBeanSupport.getBeanCollectionFromProperty(
                                            cidsBean,
                                            property);

                                    qbws.add(bean);
                                }
                            } catch (Exception ex) {
                                LOG.error("Error while adding qbws", ex);
                            }
                        }
                    };

                executor.submit(sw);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class CustomCellRenderer extends DefaultListCellRenderer {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getListCellRendererComponent(final JList<?> list,
                final Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            final Component result = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if ((result instanceof JLabel) && (value instanceof CidsBean)) {
                final CidsBean bean = (CidsBean)value;

                final String text = bean.getProperty("value") + " - " + bean.getProperty("name");
                ((JLabel)result).setText(text);
            }

            return result;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class CidsBeanComparator implements Comparator<CidsBean> {

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final CidsBean o1, final CidsBean o2) {
            if ((o1 == null) && (o2 == null)) {
                return 0;
            } else if (o1 == null) {
                return -1;
            } else if (o2 == null) {
                return 1;
            } else {
                return o1.getPrimaryKeyValue().compareTo(o2.getPrimaryKeyValue());
            }
        }
    }
}
