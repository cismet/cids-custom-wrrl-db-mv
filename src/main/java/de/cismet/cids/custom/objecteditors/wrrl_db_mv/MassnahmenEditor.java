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
import Sirius.navigator.tools.CacheException;
import Sirius.navigator.tools.MetaObjectCache;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.geom.Geometry;

import org.openide.util.NbBundle;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.LinearReferencedLineRenderer;
import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.FgskSimulationHelper;
import de.cismet.cids.custom.wrrl_db_mv.server.search.MaxWBNumberSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.StaluSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkFgMeldeInfosSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.*;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.BeanInitializer;
import de.cismet.cids.editors.BeanInitializerProvider;
import de.cismet.cids.editors.DefaultBeanInitializer;
import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;

import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.WaitingDialogThread;

import static javax.swing.SwingConstants.TOP;

/**
 * Massnahmen koennen sich auf Fliessgewaesser und Seegewaesser beziehen. Massnahmen, die sich auf Seegewaesser beziehen
 * haben keine Stationierung und als Geometrie die Geometrie des Sees. Ausser wenn sie schon zuvor eine Station hatten,
 * dann haben sie diese auch weiterhin und sie bekommen nicht die Geometrie des Sees.
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnahmenEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    CidsBeanDropListener,
    BeanInitializerProvider,
    PropertyChangeListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MassnahmenEditor.class);
    private static final MetaClass DE_MEASURE_TYPE_CODE_MC;
    private static final MetaClass PRESSURE_TYPE_CODE_MC;
    private static final MetaClass DETAIL_MC;
    private static final MetaClass MASSNAHMEN_SCHLUESSEL_MC;
    private static final String[] WB_PROPERTIES = { "wk_fg", "wk_sg", "wk_kg", "wk_gw" }; // NOI18N
    private static List<CidsBean> pressuresCountry = null;
    private static final ConnectionContext cc = ConnectionContext.create(
            AbstractConnectionContext.Category.EDITOR,
            "MassnahmenEditor");
    private static final MetaClass PROJECT_MC;

    static {
        DE_MEASURE_TYPE_CODE_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "wfd.de_measure_type_code",
                cc); // NOI18N
        DETAIL_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "detail_massnahme",
                cc); // NOI18N
        PRESSURE_TYPE_CODE_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "wfd.pressure_type_code_neu",
                cc); // NOI18N
        MASSNAHMEN_SCHLUESSEL_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "massnahmen_schluessel",
                cc); // NOI18N
        PROJECT_MC = ClassCacheMultiple.getMetaClass(
                WRRLUtil.DOMAIN_NAME,
                "projekte",
                cc); // NOI18N

        final Thread t = new Thread() {

                @Override
                public void run() {
                    try {
                        String query = "select distinct " + PRESSURE_TYPE_CODE_MC.getID() + ", p."
                                    + PRESSURE_TYPE_CODE_MC.getPrimaryKey() + " from "
                                    + PRESSURE_TYPE_CODE_MC.getTableName();
                        query += " p where p.id in (select pm.pressure from massnahmen m \n"
                                    + "join massnahmen_pressure_measure mpm on (m.pressure_measure = mpm.massnahmen_reference) \n"
                                    + "join pressure_measure pm on (mpm.pressure_measure = pm.id)\n"
                                    + "where coalesce(m.landesweit, false))";

                        final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0, cc);
                        pressuresCountry = new ArrayList<CidsBean>();

                        if (metaObjects != null) {
                            for (final MetaObject mo : metaObjects) {
                                pressuresCountry.add(mo.getBean());
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("Error while retrieving pressures", e);
                    }
                }
            };

        t.start();
    }

    //~ Instance fields --------------------------------------------------------

    private boolean showPanMelinf;
    private List<CidsBean> pressures = null;

    private CidsBean cidsBean;
    private ArrayList<CidsBean> beansToDelete = new ArrayList<CidsBean>();
    private RouteWBDropBehavior dropBehaviorListener;
    private DefaultListModel pressuresModel;
    private boolean readOnly;
    private String oldWkFg = null;
    private Timer t = null;
    private Timer tp = null;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blbSpace;
    private javax.swing.JButton btnAddDe_meas;
    private javax.swing.JButton btnAddDe_meas1;
    private javax.swing.JButton btnAddDe_meas2;
    private javax.swing.JButton btnAddPressure;
    private javax.swing.JButton btnAddPressure1;
    private javax.swing.JButton btnMeasAbort;
    private javax.swing.JButton btnMeasOk;
    private javax.swing.JButton btnPressureAbort;
    private javax.swing.JButton btnPressureAbort1;
    private javax.swing.JButton btnPressureOk;
    private javax.swing.JButton btnPressureOk1;
    private javax.swing.JButton btnRemDeMeas;
    private javax.swing.JButton btnRemDeMeas1;
    private javax.swing.JButton btnRemDeMeas2;
    private javax.swing.JButton btnRemPressure;
    private javax.swing.JButton btnRemPressure1;
    private javax.swing.JComboBox cbDetailCataloge;
    private javax.swing.JCheckBox cbErsteOrdn;
    private javax.swing.JCheckBox cbFin;
    private javax.swing.JComboBox cbGeom;
    private javax.swing.JCheckBox cbHmwb;
    private javax.swing.JCheckBox cbLandesweit;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassn_schl;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMassn_typ;
    private javax.swing.JComboBox cbMeasCataloge;
    private javax.swing.JComboBox cbPressureCataloge;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPrioritaet;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbReal;
    private javax.swing.JCheckBox cbRejected;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbRevital;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbStalu;
    private javax.swing.JCheckBox cbStarted;
    private javax.swing.JCheckBox cbZweiteOrdn;
    private javax.swing.JDialog dlgDetail;
    private javax.swing.JDialog dlgMeas;
    private javax.swing.JDialog dlgPressure;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JLabel lblGeom;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading11;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblHeading3;
    private javax.swing.JLabel lblHeading4;
    private javax.swing.JLabel lblHeading5;
    private javax.swing.JLabel lblHeading6;
    private javax.swing.JLabel lblHeading7;
    private javax.swing.JLabel lblHeading8;
    private javax.swing.JLabel lblHeading9;
    private javax.swing.JLabel lblImpact;
    private javax.swing.JLabel lblKosten;
    private javax.swing.JLabel lblMassn_Schl;
    private javax.swing.JLabel lblMassn_id;
    private javax.swing.JLabel lblMassn_typ;
    private javax.swing.JLabel lblMeasCataloge;
    private javax.swing.JLabel lblPressureCataloge;
    private javax.swing.JLabel lblPressureCataloge1;
    private javax.swing.JLabel lblPrioritaet;
    private javax.swing.JLabel lblRevital;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JLabel lblStalu;
    private javax.swing.JLabel lblSubs_typ;
    private javax.swing.JLabel lblValLfdnr;
    private javax.swing.JLabel lblValWk_k;
    private javax.swing.JLabel lblValWk_name;
    private javax.swing.JLabel lblWk_k;
    private javax.swing.JLabel lblWk_name;
    private javax.swing.JLabel lblZiele;
    private javax.swing.JLabel lbllfdnr;
    private javax.swing.JLabel lbllfdnr1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private javax.swing.JList lstImpactSrc;
    private javax.swing.JList lstPressure;
    private javax.swing.JList lstPressure1;
    private javax.swing.JList lstdeMeas;
    private javax.swing.JList lstdeMeas1;
    private javax.swing.JList lstdeMeas2;
    private javax.swing.JList lstdeMeas3;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.MassnahmenDetail massnahmenDetail1;
    private javax.swing.JPanel panAllgemein;
    private de.cismet.tools.gui.RoundedPanel panDeMeas;
    private de.cismet.tools.gui.RoundedPanel panDeMeas1;
    private de.cismet.tools.gui.RoundedPanel panDeMeas2;
    private de.cismet.tools.gui.RoundedPanel panDeMeas3;
    private javax.swing.JPanel panDe_meas;
    private javax.swing.JPanel panDe_meas1;
    private javax.swing.JPanel panDe_meas2;
    private de.cismet.tools.gui.RoundedPanel panDetail;
    private javax.swing.JPanel panFooter;
    private de.cismet.tools.gui.RoundedPanel panGeo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo3;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo4;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo5;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo6;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo7;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo8;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo9;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadQuality;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent2;
    private javax.swing.JPanel panInfoContent3;
    private javax.swing.JPanel panInfoContent4;
    private javax.swing.JPanel panInfoContent5;
    private javax.swing.JPanel panInfoContent6;
    private javax.swing.JPanel panInfoContent7;
    private javax.swing.JPanel panInfoContent8;
    private javax.swing.JPanel panInfoContent9;
    private de.cismet.tools.gui.RoundedPanel panJustification;
    private de.cismet.tools.gui.RoundedPanel panMeasDetail;
    private javax.swing.JPanel panMelInf;
    private javax.swing.JPanel panMenButtonsMeas;
    private javax.swing.JPanel panMenButtonsPressure;
    private javax.swing.JPanel panMenButtonsPressure1;
    private de.cismet.tools.gui.RoundedPanel panPressure;
    private javax.swing.JPanel panPressuresBut;
    private javax.swing.JPanel panPressuresBut1;
    private de.cismet.tools.gui.RoundedPanel panQuality;
    private javax.swing.JPanel panQualityContent;
    private javax.swing.JLabel panSpace1;
    private javax.swing.JScrollPane scpImpactSrc;
    private javax.swing.JScrollPane scpPressure;
    private javax.swing.JScrollPane scpPressure1;
    private javax.swing.JScrollPane scpdeMeas;
    private javax.swing.JScrollPane scpdeMeas1;
    private javax.swing.JScrollPane scpdeMeas2;
    private javax.swing.JScrollPane scpdeMeas3;
    private javax.swing.JTextArea taBemerkung;
    private javax.swing.JTextArea taReject;
    private org.jdesktop.swingx.JXTable tabPressure;
    private javax.swing.JTabbedPane tpMain;
    private javax.swing.JTextField txtKosten;
    private javax.swing.JTextField txtMassn_id;
    private javax.swing.JTextField txtZiele;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenEditor object.
     */
    public MassnahmenEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public MassnahmenEditor(final boolean readOnly) {
        this.readOnly = readOnly;
        showPanMelinf =
            SessionManager.getSession().getUser().getUserGroup().getName().equalsIgnoreCase("administratoren")
                    || SessionManager.getSession()
                    .getUser()
                    .getUserGroup()
                    .getName()
                    .toLowerCase()
                    .startsWith("stalu");
        initComponents();
        tpMain.setUI(new TabbedPaneUITransparent());
        massnahmenDetail1.setParent(this);
        final boolean isAdmin = SessionManager.getSession()
                    .getUser()
                    .getUserGroup()
                    .getName()
                    .equalsIgnoreCase("administratoren")
                    || SessionManager.getSession()
                    .getUser()
                    .getUserGroup()
                    .getName()
                    .toLowerCase()
                    .startsWith("stalu");

//        lblImpact.setVisible(isAdmin);
//        scpImpactSrc.setVisible(isAdmin);
        panDeMeas2.setVisible(false);
        panDeMeas3.setVisible(false);
//        panMeasDetail.setVisible(isAdmin);
        panDetail.setVisible(false);

        lstImpactSrc.setCellRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(final JList<?> list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    final Component res = super.getListCellRendererComponent(
                            list,
                            value,
                            index,
                            isSelected,
                            cellHasFocus); // To change body of generated methods, choose Tools | Templates.

                    if ((res instanceof JLabel) && (value instanceof CidsBean)) {
                        final CidsBean valueBean = (CidsBean)value;
                        final JCheckBox box = new JCheckBox(value.toString());

                        if (isPressureContained(valueBean)) {
                            box.setSelected(true);
                            box.setBackground(Color.GREEN);
                        } else if (isCountryPressureContained(valueBean)) {
                            box.setSelected(true);
                            box.setBackground(Color.GREEN);
                        } else {
                            box.setSelected(false);
                            box.setBackground(Color.YELLOW);
                        }

                        return box;
                    }

                    return res;
                }
            });
        lstdeMeas.setCellRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(final JList<?> list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    final Component res = super.getListCellRendererComponent(
                            list,
                            value,
                            index,
                            isSelected,
                            cellHasFocus); // To change body of generated methods, choose Tools | Templates.

                    if ((res instanceof JLabel) && (value instanceof CidsBean)
                                && (((CidsBean)value).getProperty("measure") != null)) {
                        ((JLabel)res).setText(((CidsBean)value).getProperty("measure").toString());
                    }

                    return res;
                }
            });

        panPressuresBut.setVisible(false);
        lblZiele.setVisible(false);
        txtZiele.setVisible(false);
        lblMassn_Schl.setVisible(false);
        cbMassn_schl.setVisible(false);
        lblRevital.setVisible(false);
        cbRevital.setVisible(false);
        lblPrioritaet.setVisible(false);
        cbPrioritaet.setVisible(false);
        panPressure.setVisible(false);

        if (readOnly) {
            panPressuresBut1.setVisible(false);
            panDe_meas.setVisible(false);
            RendererTools.makeReadOnly(txtKosten);
            RendererTools.makeReadOnly(cbReal);
            RendererTools.makeReadOnly(txtZiele);
            RendererTools.makeReadOnly(jTextArea1);
            RendererTools.makeReadOnly(cbPrioritaet);
            RendererTools.makeReadOnly(cbRevital);
            RendererTools.makeReadOnly(cbMassn_typ);
            RendererTools.makeReadOnly(cbStalu);
            RendererTools.makeReadOnly(txtMassn_id);
            RendererTools.makeReadOnly(cbMassn_schl);
            RendererTools.makeReadOnly(taBemerkung);
            RendererTools.makeReadOnly(cbHmwb);
            RendererTools.makeReadOnly(cbErsteOrdn);
            RendererTools.makeReadOnly(cbZweiteOrdn);
            RendererTools.makeReadOnly(cbLandesweit);
            lblGeom.setVisible(false);
            cbGeom.setVisible(false);
        }

        RendererTools.makeReadOnly(cbFin);
        RendererTools.makeReadOnly(cbStarted);
        deActivateGUI(false);
        dropBehaviorListener = new RouteWBDropBehavior(this);
        linearReferencedLineEditor.setDrawingFeaturesEnabled(!readOnly);
        linearReferencedLineEditor.setLineField("linie");                 // NOI18N
        linearReferencedLineEditor.setDropBehavior(dropBehaviorListener); // NOI18N
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
        jScrollPane1.setVisible(true);
        tabPressure.setRowHeight(75);                                         // 55
        tabPressure.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    setVerticalAlignment(TOP);
                    final Component c = super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column);
                    if (c instanceof JLabel) {
                        ((JLabel)c).setText("<html>" + ((JLabel)c).getText() + "</html>");
                        ((JLabel)c).setToolTipText(
                            "<html>"
                                    + WkFgPanSeven.wrapText(String.valueOf(value), 50)
                                    + "</html>");
                    }
                    return c;
                }
            });
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        if (this.cidsBean != null) {
            this.cidsBean.removePropertyChangeListener(this);
        }
        this.cidsBean = cidsBean;
        pressures = null;
        cidsBean.addPropertyChangeListener(this);

        if (cidsBean != null) {
            deActivateGUI(true);
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
            dropBehaviorListener.setWkFg((CidsBean)cidsBean.getProperty("wk_fg"));
            linearReferencedLineEditor.setCidsBean(cidsBean);
            if (!readOnly) {
                zoomToFeatures();
            }

            final CidsBean real = (CidsBean)cidsBean.getProperty("realisierung");
            final boolean justRequired = ((real != null) && (real.getProperty("name") != null)
                            && ((String)real.getProperty("name")).endsWith("2033"));
            panJustification.setVisible(justRequired);
            loadPressures();

            if (showPanMelinf) {
                final Thread thread = new Thread("retrieveAnhData") {

                        @Override
                        public void run() {
                            try {
                                final CidsServerSearch anhoerungInfo = new WkFgMeldeInfosSearch((String)
                                        cidsBean.getProperty(
                                            "massn_id"));
                                final ArrayList<ArrayList> infos = (ArrayList<ArrayList>)SessionManager
                                            .getProxy()
                                            .customServerSearch(SessionManager.getSession().getUser(), anhoerungInfo);
                                int currentRow = 0;
                                tabPressure.setModel(new WkFgPanSeven.CustomTableModel(infos));

                                for (final ArrayList row : infos) {
                                    int maxLength = 0;
                                    for (final Object col : row) {
                                        if (String.valueOf(col).length() > maxLength) {
                                            maxLength = String.valueOf(col).length();
                                        }
                                    }

                                    if (maxLength > 44) {
                                        tabPressure.setRowHeight(currentRow, maxLength / 22 * 20);
                                    }

                                    currentRow++;
                                }
                            } catch (Exception e) {
                                LOG.error("Error while retrieving anhörungs infos", e);
                            }
                        }
                    };

                thread.start();
            } else {
                tabPressure.setModel(new WkFgPanSeven.CustomTableModel(new ArrayList<ArrayList>()));
            }
        } else {
            deActivateGUI(false);
            dropBehaviorListener.setWkFg(null);
            tabPressure.setModel(new WkFgPanSeven.CustomTableModel(new ArrayList<ArrayList>()));
        }
        bindReadOnlyFields();
        refreshPressures();
        showOrHideGeometryEditors();
        taReject.setEnabled(cbRejected.isSelected());
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   pressure  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isPressureContained(final CidsBean pressure) {
        if (pressures != null) {
            for (final CidsBean mBean : pressures) {
                if ((mBean != null) && (mBean.getMetaObject().getId() == pressure.getMetaObject().getId())) {
                    return true;
                }
            }
        } else {
            if (tp == null) {
                tp = new Timer(1000, new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                tp.stop();
                                repaintImpacts();
                            }
                        });
            } else {
                tp.restart();
            }
        }

        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   pressure  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isCountryPressureContained(final CidsBean pressure) {
        if (pressuresCountry != null) {
            for (final CidsBean mBean : pressuresCountry) {
                if ((mBean != null) && (mBean.getMetaObject().getId() == pressure.getMetaObject().getId())) {
                    return true;
                }
            }
        } else {
            if (t == null) {
                t = new Timer(1000, new ActionListener() {

                            @Override
                            public void actionPerformed(final ActionEvent e) {
                                t.stop();
                                repaintImpacts();
                            }
                        });
            } else {
                t.restart();
            }
        }

        return false;
    }

    /**
     * DOCUMENT ME!
     */
    private void bindReadOnlyFields() {
        if (cidsBean == null) {
            lblValWk_k.setText("");
            lblValWk_name.setText("");
            lblFoot.setText("");
            lstImpactSrc.setModel(new DefaultListModel());
        } else {
            final String wk_k = getWk_k();
            lblValWk_k.setText(wk_k);
            lblValWk_name.setText(getWk_name());
            final DefaultListModel model = new DefaultListModel();
            final List<CidsBean> imp = getImpact();

            if (imp != null) {
                for (final CidsBean i : imp) {
                    model.addElement(i);
                }
            }
            lstImpactSrc.setModel(model);

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
    private void refreshPressures() {
        pressuresModel = new DefaultListModel();
        lstPressure.setModel(pressuresModel);

        if (cidsBean != null) {
            final List<CidsBean> meas = cidsBean.getBeanCollectionProperty("pressure_measure"); // NOI18N

            if (meas != null) {
                try {
                    final String query = "select "
                                + PRESSURE_TYPE_CODE_MC.getID()
                                + ","      // NOI18N
                                + PRESSURE_TYPE_CODE_MC.getPrimaryKey()
                                + " from " // NOI18N
                                + PRESSURE_TYPE_CODE_MC.getTableName();
                    final MetaObject[] metaObjects = MetaObjectCache.getInstance()
                                .getMetaObjectsByQuery(query, WRRLUtil.DOMAIN_NAME, false);

                    for (final CidsBean measBean : meas) {
                        final String pValue = (String)measBean.getProperty("measure.p_value");
                        final CidsBean pressureBean = getPressureByPValue(metaObjects, pValue);

                        if (pressureBean != null) {
                            if (!pressuresModel.contains(pressureBean)) {
                                pressuresModel.addElement(pressureBean);
                            }
                        }
                    }
                } catch (final CacheException ex) {
                    LOG.warn("Error while loading the pressure objects", ex); // NOI18N
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   pressures  DOCUMENT ME!
     * @param   p_value    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getPressureByPValue(final MetaObject[] pressures, final String p_value) {
        if (pressures != null) {
            for (final MetaObject mo : pressures) {
                final String pValue = (String)mo.getBean().getProperty("value");

                if ((pValue != null) && (p_value != null) && pValue.equals(p_value)) {
                    return mo.getBean();
                }
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     */
    private void loadPressures() {
        final Thread tp = new Thread() {

                @Override
                public void run() {
                    try {
                        final String wkField = getWkField();
                        final Integer wkId = getWkId();

                        if ((wkField != null) && (wkId != null)) {
                            String query = "select distinct "
                                        + PRESSURE_TYPE_CODE_MC.getID()
                                        + ", p."
                                        + PRESSURE_TYPE_CODE_MC.getPrimaryKey()
                                        + " from "
                                        + PRESSURE_TYPE_CODE_MC.getTableName();
                            query += " p where p.id in (select pm.pressure from massnahmen m \n"
                                        + "join massnahmen_pressure_measure mpm on (m.pressure_measure = mpm.massnahmen_reference) \n"
                                        + "join pressure_measure pm on (mpm.pressure_measure = pm.id)\n"
                                        + "where not coalesce(m.landesweit, false) and "
                                        + wkField
                                        + " = "
                                        + wkId
                                        + ")";

                            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);
                            pressures = new ArrayList<CidsBean>();

                            if (metaObjects != null) {
                                for (final MetaObject mo : metaObjects) {
                                    pressures.add(mo.getBean());
                                }
                            }

                            repaintImpacts();
                        }
                    } catch (Exception e) {
                        LOG.error("Error while retrieving pressures", e);
                    }
                }
            };

        tp.start();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getWk_k() {
        if (cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_fg.wk_k")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_sg.wk_k")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[2]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_kg.name")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[3]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_gw.name")); // NOI18N
        } else {
            return CidsBeanSupport.FIELD_NOT_SET;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Integer getWkId() {
        if (cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            return (Integer)(cidsBean.getProperty("wk_fg.id")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            return (Integer)(cidsBean.getProperty("wk_sg.id")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[2]) != null) {
            return (Integer)(cidsBean.getProperty("wk_kg.id")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[3]) != null) {
            return (Integer)(cidsBean.getProperty("wk_gw.id")); // NOI18N
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getWkField() {
        if (cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            return "wk_fg"; // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            return "wk_sg"; // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[2]) != null) {
            return "wk_kg"; // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[3]) != null) {
            return "wk_gw"; // NOI18N
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getWk_name() {
        if (cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_fg.wk_n"));    // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_sg.ls_name")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[2]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_kg.name"));    // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[3]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_gw.name"));    // NOI18N
        } else {
            return CidsBeanSupport.FIELD_NOT_SET;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private List<CidsBean> getImpact() {
        if (cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            final List<CidsBean> pressureImpactDriver = CidsBeanSupport.getBeanCollectionFromProperty((CidsBean)
                    cidsBean.getProperty("wk_fg"),
                    "pressure_impact_driver");
            final List<CidsBean> pressures = new ArrayList<>();

            for (final CidsBean pidBean : pressureImpactDriver) {
                final CidsBean pBean = (CidsBean)pidBean.getProperty("pressure");

                if (!pressures.contains(pBean)) {
                    pressures.add(pBean);
                }
            }

            return pressures;
        } else if (cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            return CidsBeanSupport.getBeanCollectionFromProperty((CidsBean)cidsBean.getProperty("wk_sg"), "impact_src");
        } else if (cidsBean.getProperty(WB_PROPERTIES[2]) != null) {
            return CidsBeanSupport.getBeanCollectionFromProperty((CidsBean)cidsBean.getProperty("wk_kg"),
                    "impact_srcs");
        } else if (cidsBean.getProperty(WB_PROPERTIES[3]) != null) {
            return CidsBeanSupport.getBeanCollectionFromProperty((CidsBean)cidsBean.getProperty("wk_gw"), "impact_src");
        } else {
            return new ArrayList<CidsBean>();
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
        dlgMeas = new JDialog(StaticSwingTools.getParentFrame(this));
        lblMeasCataloge = new javax.swing.JLabel();
        cbMeasCataloge = new ScrollableComboBox(DE_MEASURE_TYPE_CODE_MC, true, true, new CustomElementComparator());
        panMenButtonsMeas = new javax.swing.JPanel();
        btnMeasAbort = new javax.swing.JButton();
        btnMeasOk = new javax.swing.JButton();
        dlgPressure = new JDialog(StaticSwingTools.getParentFrame(this));
        lblPressureCataloge = new javax.swing.JLabel();
        cbPressureCataloge = new ScrollableComboBox(PRESSURE_TYPE_CODE_MC, true, false, new CustomElementComparator(1));
        panMenButtonsPressure = new javax.swing.JPanel();
        btnPressureAbort = new javax.swing.JButton();
        btnPressureOk = new javax.swing.JButton();
        dlgDetail = new JDialog(StaticSwingTools.getParentFrame(this));
        lblPressureCataloge1 = new javax.swing.JLabel();
        cbDetailCataloge = new ScrollableComboBox(DETAIL_MC, false, true, new CustomElementComparator(1));
        panMenButtonsPressure1 = new javax.swing.JPanel();
        btnPressureAbort1 = new javax.swing.JButton();
        btnPressureOk1 = new javax.swing.JButton();
        tpMain = new javax.swing.JTabbedPane();
        panAllgemein = new javax.swing.JPanel();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        blbSpace = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblZiele = new javax.swing.JLabel();
        lblMassn_typ = new javax.swing.JLabel();
        lblRevital = new javax.swing.JLabel();
        lblPrioritaet = new javax.swing.JLabel();
        lblKosten = new javax.swing.JLabel();
        lblSubs_typ = new javax.swing.JLabel();
        txtKosten = new javax.swing.JTextField();
        cbMassn_typ = new ScrollableComboBox();
        cbRevital = new ScrollableComboBox();
        cbPrioritaet = new ScrollableComboBox();
        txtZiele = new javax.swing.JTextField();
        lbllfdnr = new javax.swing.JLabel();
        lblWk_k = new javax.swing.JLabel();
        lblValWk_k = new javax.swing.JLabel();
        lblWk_name = new javax.swing.JLabel();
        lblValWk_name = new javax.swing.JLabel();
        lblMassn_id = new javax.swing.JLabel();
        lblStalu = new javax.swing.JLabel();
        txtMassn_id = new javax.swing.JTextField();
        cbStalu = new ScrollableComboBox();
        cbStarted = new javax.swing.JCheckBox();
        cbReal = new ScrollableComboBox();
        lblMassn_Schl = new javax.swing.JLabel();
        cbMassn_schl = new ScrollableComboBox(MASSNAHMEN_SCHLUESSEL_MC, true, true);
        lblBemerkung = new javax.swing.JLabel();
        cbRejected = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        lblValLfdnr = new javax.swing.JLabel();
        cbLandesweit = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        taBemerkung = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        cbFin = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taReject = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        cbErsteOrdn = new javax.swing.JCheckBox();
        cbZweiteOrdn = new javax.swing.JCheckBox();
        lbllfdnr1 = new javax.swing.JLabel();
        scpImpactSrc = new javax.swing.JScrollPane();
        lstImpactSrc = new javax.swing.JList();
        lblImpact = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        panDeMeas1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo4 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading4 = new javax.swing.JLabel();
        panInfoContent4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        cbHmwb = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        panDeMeas = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        panDe_meas = new javax.swing.JPanel();
        btnAddDe_meas = new javax.swing.JButton();
        btnRemDeMeas = new javax.swing.JButton();
        scpdeMeas = new javax.swing.JScrollPane();
        lstdeMeas = new javax.swing.JList();
        panDetail = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo5 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading5 = new javax.swing.JLabel();
        panInfoContent5 = new javax.swing.JPanel();
        panPressuresBut1 = new javax.swing.JPanel();
        btnAddPressure1 = new javax.swing.JButton();
        btnRemPressure1 = new javax.swing.JButton();
        scpPressure1 = new javax.swing.JScrollPane();
        lstPressure1 = new javax.swing.JList();
        panPressure = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo3 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading3 = new javax.swing.JLabel();
        panInfoContent3 = new javax.swing.JPanel();
        panPressuresBut = new javax.swing.JPanel();
        btnAddPressure = new javax.swing.JButton();
        btnRemPressure = new javax.swing.JButton();
        scpPressure = new javax.swing.JScrollPane();
        lstPressure = new javax.swing.JList();
        panDeMeas2 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo6 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading6 = new javax.swing.JLabel();
        panInfoContent6 = new javax.swing.JPanel();
        panDe_meas1 = new javax.swing.JPanel();
        btnAddDe_meas1 = new javax.swing.JButton();
        btnRemDeMeas1 = new javax.swing.JButton();
        scpdeMeas1 = new javax.swing.JScrollPane();
        lstdeMeas1 = new javax.swing.JList();
        panDeMeas3 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo7 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading7 = new javax.swing.JLabel();
        panInfoContent7 = new javax.swing.JPanel();
        panDe_meas2 = new javax.swing.JPanel();
        btnAddDe_meas2 = new javax.swing.JButton();
        btnRemDeMeas2 = new javax.swing.JButton();
        scpdeMeas2 = new javax.swing.JScrollPane();
        lstdeMeas2 = new javax.swing.JList();
        panMeasDetail = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo8 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading8 = new javax.swing.JLabel();
        panInfoContent8 = new javax.swing.JPanel();
        massnahmenDetail1 = new MassnahmenDetail(readOnly);
        panJustification = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo9 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading9 = new javax.swing.JLabel();
        panInfoContent9 = new javax.swing.JPanel();
        scpdeMeas3 = new javax.swing.JScrollPane();
        lstdeMeas3 = new javax.swing.JList();
        jPanel8 = new javax.swing.JPanel();
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
        if (showPanMelinf) {
            panMelInf = new javax.swing.JPanel();
        }
        panQuality = new de.cismet.tools.gui.RoundedPanel();
        panHeadQuality = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading11 = new javax.swing.JLabel();
        panQualityContent = new javax.swing.JPanel();
        lblSpace = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabPressure = new org.jdesktop.swingx.JXTable();
        panSpace1 = new javax.swing.JLabel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        dlgMeas.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblMeasCataloge.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.lblMeasCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas.getContentPane().add(lblMeasCataloge, gridBagConstraints);

        cbMeasCataloge.setMinimumSize(new java.awt.Dimension(700, 18));
        cbMeasCataloge.setPreferredSize(new java.awt.Dimension(700, 18));
        cbMeasCataloge.setRenderer(new MeasureTypeCodeRenderer());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas.getContentPane().add(cbMeasCataloge, gridBagConstraints);

        panMenButtonsMeas.setLayout(new java.awt.GridBagLayout());

        btnMeasAbort.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeasAbort.text")); // NOI18N
        btnMeasAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMeasAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas.add(btnMeasAbort, gridBagConstraints);

        btnMeasOk.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeasOk.text")); // NOI18N
        btnMeasOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMeasOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMeasOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMeasOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMeasOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsMeas.add(btnMeasOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgMeas.getContentPane().add(panMenButtonsMeas, gridBagConstraints);

        dlgPressure.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblPressureCataloge.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.lblPressureCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgPressure.getContentPane().add(lblPressureCataloge, gridBagConstraints);

        cbPressureCataloge.setMinimumSize(new java.awt.Dimension(700, 18));
        cbPressureCataloge.setPreferredSize(new java.awt.Dimension(700, 18));
        cbPressureCataloge.setRenderer(new WfdTypeCodeRenderer());
        cbPressureCataloge.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbPressureCatalogeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgPressure.getContentPane().add(cbPressureCataloge, gridBagConstraints);

        panMenButtonsPressure.setLayout(new java.awt.GridBagLayout());

        btnPressureAbort.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeas15Abort.text")); // NOI18N
        btnPressureAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnPressureAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsPressure.add(btnPressureAbort, gridBagConstraints);

        btnPressureOk.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeas15Ok.text")); // NOI18N
        btnPressureOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnPressureOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnPressureOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnPressureOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnPressureOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsPressure.add(btnPressureOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgPressure.getContentPane().add(panMenButtonsPressure, gridBagConstraints);

        dlgDetail.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblPressureCataloge1.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.lblPressureCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgDetail.getContentPane().add(lblPressureCataloge1, gridBagConstraints);

        cbDetailCataloge.setMinimumSize(new java.awt.Dimension(700, 18));
        cbDetailCataloge.setPreferredSize(new java.awt.Dimension(700, 18));
        cbDetailCataloge.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbDetailCatalogeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgDetail.getContentPane().add(cbDetailCataloge, gridBagConstraints);

        panMenButtonsPressure1.setLayout(new java.awt.GridBagLayout());

        btnPressureAbort1.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeas15Abort.text")); // NOI18N
        btnPressureAbort1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnPressureAbort1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsPressure1.add(btnPressureAbort1, gridBagConstraints);

        btnPressureOk1.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.btnMeas15Ok.text")); // NOI18N
        btnPressureOk1.setMaximumSize(new java.awt.Dimension(85, 23));
        btnPressureOk1.setMinimumSize(new java.awt.Dimension(85, 23));
        btnPressureOk1.setPreferredSize(new java.awt.Dimension(85, 23));
        btnPressureOk1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnPressureOk1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsPressure1.add(btnPressureOk1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgDetail.getContentPane().add(panMenButtonsPressure1, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1080, 1050));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1240, 1050));
        setLayout(new java.awt.BorderLayout());

        panAllgemein.setMaximumSize(new java.awt.Dimension(1380, 1100));
        panAllgemein.setMinimumSize(new java.awt.Dimension(1280, 1100));
        panAllgemein.setOpaque(false);
        panAllgemein.setPreferredSize(new java.awt.Dimension(1280, 1100));
        panAllgemein.setLayout(new java.awt.GridBagLayout());

        panInfo.setMaximumSize(new java.awt.Dimension(1350, 1200));
        panInfo.setMinimumSize(new java.awt.Dimension(1080, 1050));
        panInfo.setPreferredSize(new java.awt.Dimension(1280, 1050));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Maßnahmen");
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(blbSpace, gridBagConstraints);

        jPanel2.setMinimumSize(new java.awt.Dimension(530, 750));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(620, 750));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblZiele.setText("Entwicklungsziele");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblZiele, gridBagConstraints);

        lblMassn_typ.setText("Massnahmen-Kategorie");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblMassn_typ, gridBagConstraints);

        lblRevital.setText("Art der Maßnahme");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblRevital, gridBagConstraints);

        lblPrioritaet.setText("Priorität");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblPrioritaet, gridBagConstraints);

        lblKosten.setText("geschätzte Kosten");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblKosten, gridBagConstraints);

        lblSubs_typ.setText("Realisierung bis");
        lblSubs_typ.setPreferredSize(new java.awt.Dimension(165, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblSubs_typ, gridBagConstraints);

        txtKosten.setMinimumSize(new java.awt.Dimension(200, 25));
        txtKosten.setPreferredSize(new java.awt.Dimension(200, 25));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kosten}"),
                txtKosten,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(txtKosten, gridBagConstraints);

        cbMassn_typ.setMinimumSize(new java.awt.Dimension(200, 25));
        cbMassn_typ.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.m_category}"),
                cbMassn_typ,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbMassn_typ, gridBagConstraints);

        cbRevital.setMinimumSize(new java.awt.Dimension(200, 25));
        cbRevital.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.revital}"),
                cbRevital,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbRevital, gridBagConstraints);

        cbPrioritaet.setMinimumSize(new java.awt.Dimension(200, 25));
        cbPrioritaet.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.prioritaet}"),
                cbPrioritaet,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbPrioritaet, gridBagConstraints);

        txtZiele.setMinimumSize(new java.awt.Dimension(200, 25));
        txtZiele.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ziele}"),
                txtZiele,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(txtZiele, gridBagConstraints);

        lbllfdnr.setText("laufende Nummer im WK");
        lbllfdnr.setToolTipText("laufende Nummer im Wasserkörper");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lbllfdnr, gridBagConstraints);

        lblWk_k.setText("Wasserkörper-Kürzel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 10, 0);
        jPanel2.add(lblWk_k, gridBagConstraints);

        lblValWk_k.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValWk_k.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_fg.wk_k}"),
                lblValWk_k,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 10, 0);
        jPanel2.add(lblValWk_k, gridBagConstraints);

        lblWk_name.setText("Wasserkörper-Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblWk_name, gridBagConstraints);

        lblValWk_name.setMinimumSize(new java.awt.Dimension(200, 25));
        lblValWk_name.setPreferredSize(new java.awt.Dimension(200, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblValWk_name, gridBagConstraints);

        lblMassn_id.setText("Maßnahmen-Nummer");
        lblMassn_id.setToolTipText("laufende Nummer im Wasserkörper");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblMassn_id, gridBagConstraints);

        lblStalu.setText("Zuständiges StALU");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblStalu, gridBagConstraints);

        txtMassn_id.setMinimumSize(new java.awt.Dimension(200, 25));
        txtMassn_id.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_id}"),
                txtMassn_id,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(txtMassn_id, gridBagConstraints);

        cbStalu.setMinimumSize(new java.awt.Dimension(200, 25));
        cbStalu.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stalu}"),
                cbStalu,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbStalu, gridBagConstraints);

        cbStarted.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.cbStarted.text")); // NOI18N
        cbStarted.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_started}"),
                cbStarted,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        cbStarted.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbStartedActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbStarted, gridBagConstraints);

        cbReal.setMinimumSize(new java.awt.Dimension(200, 25));
        cbReal.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.realisierung}"),
                cbReal,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbReal, gridBagConstraints);

        lblMassn_Schl.setText("Schlüsselmaßnahme");
        lblMassn_Schl.setToolTipText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.lblMassn_typ.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblMassn_Schl, gridBagConstraints);

        cbMassn_schl.setMinimumSize(new java.awt.Dimension(200, 25));
        cbMassn_schl.setPreferredSize(new java.awt.Dimension(200, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massnahmen_schluessel}"),
                cbMassn_schl,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(cbMassn_schl, gridBagConstraints);

        lblBemerkung.setText("<html>Allgemeine Bemerkungen zur Maßnahme</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblBemerkung, gridBagConstraints);

        cbRejected.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.cbRejected.text")); // NOI18N
        cbRejected.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_verw}"),
                cbRejected,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        cbRejected.addChangeListener(new javax.swing.event.ChangeListener() {

                @Override
                public void stateChanged(final javax.swing.event.ChangeEvent evt) {
                    cbRejectedStateChanged(evt);
                }
            });
        cbRejected.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbRejectedActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(cbRejected, gridBagConstraints);

        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.GridBagLayout());

        lblValLfdnr.setMinimumSize(new java.awt.Dimension(100, 25));
        lblValLfdnr.setPreferredSize(new java.awt.Dimension(100, 25));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_wk_lfdnr}"),
                lblValLfdnr,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        jPanel6.add(lblValLfdnr, gridBagConstraints);

        cbLandesweit.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.cbLandesweit.text")); // NOI18N
        cbLandesweit.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.landesweit}"),
                cbLandesweit,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        cbLandesweit.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbLandesweitActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel6.add(cbLandesweit, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(jPanel6, gridBagConstraints);

        jScrollPane2.setMinimumSize(new java.awt.Dimension(250, 100));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(250, 100));

        taBemerkung.setColumns(15);
        taBemerkung.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung}"),
                taBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(taBemerkung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        jPanel2.add(jScrollPane2, gridBagConstraints);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        cbFin.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.cbFin.text")); // NOI18N
        cbFin.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_fin}"),
                cbFin,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        cbFin.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbFinActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(cbFin, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(jPanel4, gridBagConstraints);

        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.GridBagLayout());

        jScrollPane3.setMinimumSize(new java.awt.Dimension(262, 75));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(262, 75));

        taReject.setColumns(20);
        taReject.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massn_verw_com}"),
                taReject,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        jScrollPane3.setViewportView(taReject);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel9.add(jScrollPane3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(jPanel9, gridBagConstraints);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridBagLayout());

        cbErsteOrdn.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.cbErsteOrdn.text")); // NOI18N
        cbErsteOrdn.setContentAreaFilled(false);
        cbErsteOrdn.setMinimumSize(new java.awt.Dimension(100, 23));
        cbErsteOrdn.setPreferredSize(new java.awt.Dimension(100, 23));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.erste_ordn}"),
                cbErsteOrdn,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        cbErsteOrdn.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbErsteOrdnActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel5.add(cbErsteOrdn, gridBagConstraints);

        cbZweiteOrdn.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenEditor.class,
                "MassnahmenEditor.cbZweiteOrdn.text")); // NOI18N
        cbZweiteOrdn.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zweite_ordn}"),
                cbZweiteOrdn,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        cbZweiteOrdn.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbZweiteOrdnActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel5.add(cbZweiteOrdn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(jPanel5, gridBagConstraints);

        lbllfdnr1.setText("Gewässerordnung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lbllfdnr1, gridBagConstraints);

        scpImpactSrc.setMinimumSize(new java.awt.Dimension(300, 80));
        scpImpactSrc.setPreferredSize(new java.awt.Dimension(300, 80));

        lstImpactSrc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpImpactSrc.setViewportView(lstImpactSrc);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(scpImpactSrc, gridBagConstraints);

        lblImpact.setText("<html>Sign.&nbsp;Belastungsqu. am Wasserkörper</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblImpact, gridBagConstraints);

        jPanel7.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel7, gridBagConstraints);

        panDeMeas1.setMinimumSize(new java.awt.Dimension(480, 150));
        panDeMeas1.setPreferredSize(new java.awt.Dimension(480, 150));

        panHeadInfo4.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo4.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo4.setLayout(new java.awt.FlowLayout());

        lblHeading4.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading4.setText("Beschreibung der Maßnahme");
        panHeadInfo4.add(lblHeading4);

        panDeMeas1.add(panHeadInfo4, java.awt.BorderLayout.NORTH);

        panInfoContent4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent4.setOpaque(false);
        panInfoContent4.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(380, 100));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(380, 100));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.massnahme}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent4.add(jScrollPane1, gridBagConstraints);

        panDeMeas1.add(panInfoContent4, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanel2.add(panDeMeas1, gridBagConstraints);

        cbHmwb.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.cbHmwb.text")); // NOI18N
        cbHmwb.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.hmwb}"),
                cbHmwb,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        cbHmwb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbHmwbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(cbHmwb, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        panInfoContent.add(jPanel2, gridBagConstraints);

        jPanel3.setMinimumSize(new java.awt.Dimension(450, 540));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(620, 540));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        panDeMeas.setMinimumSize(new java.awt.Dimension(480, 150));
        panDeMeas.setPreferredSize(new java.awt.Dimension(480, 150));

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText("LAWA-Maßnahmen");
        panHeadInfo2.add(lblHeading2);

        panDeMeas.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        panDe_meas.setOpaque(false);
        panDe_meas.setLayout(new java.awt.GridBagLayout());

        btnAddDe_meas.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddDe_meas.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddDe_measActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panDe_meas.add(btnAddDe_meas, gridBagConstraints);

        btnRemDeMeas.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemDeMeas.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemDeMeasActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panDe_meas.add(btnRemDeMeas, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panInfoContent2.add(panDe_meas, gridBagConstraints);

        scpdeMeas.setMinimumSize(new java.awt.Dimension(400, 90));
        scpdeMeas.setPreferredSize(new java.awt.Dimension(400, 90));

        lstdeMeas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.pressure_measure}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstdeMeas);
        bindingGroup.addBinding(jListBinding);

        lstdeMeas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    lstdeMeasValueChanged(evt);
                }
            });
        scpdeMeas.setViewportView(lstdeMeas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent2.add(scpdeMeas, gridBagConstraints);

        panDeMeas.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 10, 0);
        jPanel3.add(panDeMeas, gridBagConstraints);

        panDetail.setMinimumSize(new java.awt.Dimension(480, 150));
        panDetail.setPreferredSize(new java.awt.Dimension(480, 150));

        panHeadInfo5.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo5.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo5.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo5.setLayout(new java.awt.FlowLayout());

        lblHeading5.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading5.setText("Detail-Maßnahmen");
        panHeadInfo5.add(lblHeading5);

        panDetail.add(panHeadInfo5, java.awt.BorderLayout.NORTH);

        panInfoContent5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent5.setOpaque(false);
        panInfoContent5.setLayout(new java.awt.GridBagLayout());

        panPressuresBut1.setOpaque(false);
        panPressuresBut1.setLayout(new java.awt.GridBagLayout());

        btnAddPressure1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddPressure1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddPressure1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panPressuresBut1.add(btnAddPressure1, gridBagConstraints);

        btnRemPressure1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemPressure1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemPressure1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panPressuresBut1.add(btnRemPressure1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panInfoContent5.add(panPressuresBut1, gridBagConstraints);

        scpPressure1.setMinimumSize(new java.awt.Dimension(400, 90));
        scpPressure1.setPreferredSize(new java.awt.Dimension(400, 90));

        lstPressure1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.detail_massnahme}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                eLProperty,
                lstPressure1);
        bindingGroup.addBinding(jListBinding);

        scpPressure1.setViewportView(lstPressure1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent5.add(scpPressure1, gridBagConstraints);

        panDetail.add(panInfoContent5, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 10, 0);
        jPanel3.add(panDetail, gridBagConstraints);

        panPressure.setMinimumSize(new java.awt.Dimension(480, 150));
        panPressure.setPreferredSize(new java.awt.Dimension(480, 150));

        panHeadInfo3.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo3.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo3.setLayout(new java.awt.FlowLayout());

        lblHeading3.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading3.setText("Belastungen");
        panHeadInfo3.add(lblHeading3);

        panPressure.add(panHeadInfo3, java.awt.BorderLayout.NORTH);

        panInfoContent3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent3.setOpaque(false);
        panInfoContent3.setLayout(new java.awt.GridBagLayout());

        panPressuresBut.setOpaque(false);
        panPressuresBut.setLayout(new java.awt.GridBagLayout());

        btnAddPressure.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddPressure.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddPressureActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panPressuresBut.add(btnAddPressure, gridBagConstraints);

        btnRemPressure.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemPressure.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemPressureActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panPressuresBut.add(btnRemPressure, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panInfoContent3.add(panPressuresBut, gridBagConstraints);

        scpPressure.setMinimumSize(new java.awt.Dimension(400, 90));
        scpPressure.setPreferredSize(new java.awt.Dimension(400, 90));

        lstPressure.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpPressure.setViewportView(lstPressure);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent3.add(scpPressure, gridBagConstraints);

        panPressure.add(panInfoContent3, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 10, 0);
        jPanel3.add(panPressure, gridBagConstraints);

        panDeMeas2.setMinimumSize(new java.awt.Dimension(480, 135));
        panDeMeas2.setPreferredSize(new java.awt.Dimension(480, 135));

        panHeadInfo6.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo6.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo6.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo6.setLayout(new java.awt.FlowLayout());

        lblHeading6.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading6.setText("Signifikante Belastungen am Wasserkörper");
        panHeadInfo6.add(lblHeading6);

        panDeMeas2.add(panHeadInfo6, java.awt.BorderLayout.NORTH);

        panInfoContent6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent6.setOpaque(false);
        panInfoContent6.setLayout(new java.awt.GridBagLayout());

        panDe_meas1.setOpaque(false);
        panDe_meas1.setLayout(new java.awt.GridBagLayout());

        btnAddDe_meas1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddDe_meas1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddDe_meas1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panDe_meas1.add(btnAddDe_meas1, gridBagConstraints);

        btnRemDeMeas1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemDeMeas1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemDeMeas1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panDe_meas1.add(btnRemDeMeas1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panInfoContent6.add(panDe_meas1, gridBagConstraints);

        scpdeMeas1.setMinimumSize(new java.awt.Dimension(400, 90));
        scpdeMeas1.setPreferredSize(new java.awt.Dimension(400, 90));

        lstdeMeas1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpdeMeas1.setViewportView(lstdeMeas1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent6.add(scpdeMeas1, gridBagConstraints);

        panDeMeas2.add(panInfoContent6, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 10, 0);
        jPanel3.add(panDeMeas2, gridBagConstraints);

        panDeMeas3.setMinimumSize(new java.awt.Dimension(480, 135));
        panDeMeas3.setPreferredSize(new java.awt.Dimension(480, 135));

        panHeadInfo7.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo7.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo7.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo7.setLayout(new java.awt.FlowLayout());

        lblHeading7.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading7.setText("Belastung mit prioritären oder flussgebietsspezifischen Schadstoffen");
        panHeadInfo7.add(lblHeading7);

        panDeMeas3.add(panHeadInfo7, java.awt.BorderLayout.NORTH);

        panInfoContent7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent7.setOpaque(false);
        panInfoContent7.setLayout(new java.awt.GridBagLayout());

        panDe_meas2.setOpaque(false);
        panDe_meas2.setLayout(new java.awt.GridBagLayout());

        btnAddDe_meas2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddDe_meas2.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddDe_meas2ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panDe_meas2.add(btnAddDe_meas2, gridBagConstraints);

        btnRemDeMeas2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemDeMeas2.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemDeMeas2ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panDe_meas2.add(btnRemDeMeas2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        panInfoContent7.add(panDe_meas2, gridBagConstraints);

        scpdeMeas2.setMinimumSize(new java.awt.Dimension(400, 90));
        scpdeMeas2.setPreferredSize(new java.awt.Dimension(400, 90));

        lstdeMeas2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpdeMeas2.setViewportView(lstdeMeas2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent7.add(scpdeMeas2, gridBagConstraints);

        panDeMeas3.add(panInfoContent7, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 10, 0);
        jPanel3.add(panDeMeas3, gridBagConstraints);

        panMeasDetail.setMinimumSize(new java.awt.Dimension(480, 250));
        panMeasDetail.setPreferredSize(new java.awt.Dimension(480, 250));

        panHeadInfo8.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo8.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo8.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo8.setLayout(new java.awt.FlowLayout());

        lblHeading8.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading8.setText("LAWA-Maßnahmen Details");
        panHeadInfo8.add(lblHeading8);

        panMeasDetail.add(panHeadInfo8, java.awt.BorderLayout.NORTH);

        panInfoContent8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent8.setOpaque(false);
        panInfoContent8.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panInfoContent8.add(massnahmenDetail1, gridBagConstraints);

        panMeasDetail.add(panInfoContent8, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel3.add(panMeasDetail, gridBagConstraints);

        panJustification.setMinimumSize(new java.awt.Dimension(480, 170));
        panJustification.setPreferredSize(new java.awt.Dimension(480, 170));

        panHeadInfo9.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo9.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo9.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo9.setLayout(new java.awt.FlowLayout());

        lblHeading9.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading9.setText("Begründung für eine verzögerte Umsetzung der Maßnahmen");
        panHeadInfo9.add(lblHeading9);

        panJustification.add(panHeadInfo9, java.awt.BorderLayout.NORTH);

        panInfoContent9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        panInfoContent9.setOpaque(false);
        panInfoContent9.setLayout(new java.awt.GridBagLayout());

        scpdeMeas3.setMinimumSize(new java.awt.Dimension(400, 90));
        scpdeMeas3.setPreferredSize(new java.awt.Dimension(400, 90));

        lstdeMeas3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.justif}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                eLProperty,
                lstdeMeas3);
        bindingGroup.addBinding(jListBinding);

        lstdeMeas3.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    lstdeMeas3ValueChanged(evt);
                }
            });
        scpdeMeas3.setViewportView(lstdeMeas3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        panInfoContent9.add(scpdeMeas3, gridBagConstraints);

        panJustification.add(panInfoContent9, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel3.add(panJustification, gridBagConstraints);

        jPanel8.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jPanel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 10, 20);
        panInfoContent.add(jPanel3, gridBagConstraints);

        panGeo.setMinimumSize(new java.awt.Dimension(640, 100));
        panGeo.setPreferredSize(new java.awt.Dimension(640, 100));

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText("Geometrie");
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
        gridBagConstraints.gridy = 1;
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
                    org.jdesktop.beansbinding.ELProperty.create("${cidsBean.additional_geom}"),
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

        lblGeom.setText(org.openide.util.NbBundle.getMessage(MassnahmenEditor.class, "WkSgPanOne.lblGeom.text")); // NOI18N
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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 20);
        panInfoContent.add(jPanel1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        panAllgemein.add(panInfo, gridBagConstraints);

        tpMain.addTab("Allgemein", panAllgemein);

        if (showPanMelinf) {
            panMelInf.setOpaque(false);
            panMelInf.setLayout(new java.awt.GridBagLayout());

            panHeadQuality.setBackground(new java.awt.Color(51, 51, 51));
            panHeadQuality.setMinimumSize(new java.awt.Dimension(109, 24));
            panHeadQuality.setPreferredSize(new java.awt.Dimension(109, 24));
            panHeadQuality.setLayout(new java.awt.FlowLayout());

            lblHeading11.setForeground(new java.awt.Color(255, 255, 255));
            lblHeading11.setText("Anhörung");
            panHeadQuality.add(lblHeading11);

            panQuality.add(panHeadQuality, java.awt.BorderLayout.NORTH);

            panQualityContent.setMinimumSize(new java.awt.Dimension(1100, 260));
            panQualityContent.setOpaque(false);
            panQualityContent.setPreferredSize(new java.awt.Dimension(1100, 260));
            panQualityContent.setLayout(new java.awt.GridBagLayout());
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 8;
            panQualityContent.add(lblSpace, gridBagConstraints);

            jScrollPane4.setViewportView(tabPressure);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
            panQualityContent.add(jScrollPane4, gridBagConstraints);

            panQuality.add(panQualityContent, java.awt.BorderLayout.CENTER);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
            panMelInf.add(panQuality, gridBagConstraints);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridwidth = 2;
            gridBagConstraints.weighty = 1.0;
            panMelInf.add(panSpace1, gridBagConstraints);

            tpMain.addTab("Anhörung", panMelInf);
        }

        add(tpMain, java.awt.BorderLayout.PAGE_START);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbFinActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbFinActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbFinActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddDe_measActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddDe_measActionPerformed
        dlgMeas.setSize(750, 150);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgMeas, true);
    }                                                                                 //GEN-LAST:event_btnAddDe_measActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemDeMeasActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemDeMeasActionPerformed
        final CidsBean selection = (CidsBean)lstdeMeas.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll die Massnahmenart '"
                            + String.valueOf(selection.getProperty("measure"))
                            + "' wirklich gelöscht werden?",
                    "Massnahmenart entfernen",
                    JOptionPane.YES_NO_OPTION);

            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("pressure_measure"); // NOI18N
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }

                    refreshPressures();
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    } //GEN-LAST:event_btnRemDeMeasActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddPressureActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddPressureActionPerformed
//        dlgPressure.setSize(750, 150);
//        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgPressure, true);
    } //GEN-LAST:event_btnAddPressureActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemPressureActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemPressureActionPerformed
//        final Object selection = lstPressure.getSelectedValue();
//        if (selection != null) {
//            final int answer = JOptionPane.showConfirmDialog(
//                    StaticSwingTools.getParentFrame(this),
//                    "Soll die Belastung '"
//                            + selection.toString()
//                            + "' wirklich gelöscht werden?",
//                    "Belastung entfernen",
//                    JOptionPane.YES_NO_OPTION);
//            if (answer == JOptionPane.YES_OPTION) {
//                try {
//                    final CidsBean beanToDelete = (CidsBean)selection;
//                    final Object beanColl = cidsBean.getProperty("pressures");       // NOI18N
//                    if (beanColl instanceof Collection) {
//                        ((Collection)beanColl).remove(beanToDelete);
//                    }
//                } catch (final Exception e) {
//                    UIUtil.showExceptionToUser(e, this);
//                }
//            }
//        }
    } //GEN-LAST:event_btnRemPressureActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMeasAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMeasAbortActionPerformed
        dlgMeas.setVisible(false);
    }                                                                                //GEN-LAST:event_btnMeasAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMeasOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMeasOkActionPerformed
        final Object selection = cbMeasCataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final Thread t = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(
                                    cidsBean,
                                    "pressure_measure"); // NOI18N
                            try {
                                final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName(
                                        "pressure_measure");
                                newBean.setProperty("measure", selection);

                                if (colToAdd != null) {
                                    if (!colToAdd.contains(newBean)) {
                                        colToAdd.add(newBean);
                                    }
                                }
                            } catch (Exception e) {
                                LOG.error("Cannot add measure", e);
                            }

                            EventQueue.invokeLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        refreshPressures();
                                    }
                                });
                        }
                    });

            t.start();
        }

        dlgMeas.setVisible(false);
    } //GEN-LAST:event_btnMeasOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnPressureAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnPressureAbortActionPerformed
        dlgPressure.setVisible(false);
    }                                                                                    //GEN-LAST:event_btnPressureAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnPressureOkActionPerformed(final java.awt.event.ActionEvent evt) {                                   //GEN-FIRST:event_btnPressureOkActionPerformed
        final Object selection = cbPressureCataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean)selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "pressures"); // NOI18N
            if (colToAdd != null) {
                if (!colToAdd.contains(selectedBean)) {
                    colToAdd.add(selectedBean);
                }
            }
        }
        dlgPressure.setVisible(false);
    }                                                                                                                   //GEN-LAST:event_btnPressureOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbStartedActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbStartedActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbStartedActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbHmwbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbHmwbActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbHmwbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbPressureCatalogeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbPressureCatalogeActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbPressureCatalogeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbDetailCatalogeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbDetailCatalogeActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbDetailCatalogeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnPressureAbort1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnPressureAbort1ActionPerformed
        dlgDetail.setVisible(false);
    }                                                                                     //GEN-LAST:event_btnPressureAbort1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnPressureOk1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnPressureOk1ActionPerformed
        final Object selection = cbDetailCataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final Thread t = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            final CidsBean selectedBean = (CidsBean)selection;
                            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(
                                    cidsBean,
                                    "detail_massnahme"); // NOI18N
                            if (colToAdd != null) {
                                if (!colToAdd.contains(selectedBean)) {
                                    colToAdd.add(selectedBean);
                                }
                            }
                        }
                    });

            t.start();
        }

        dlgDetail.setVisible(false);
    } //GEN-LAST:event_btnPressureOk1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddPressure1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddPressure1ActionPerformed
        dlgDetail.setSize(750, 150);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgDetail, true);
    }                                                                                   //GEN-LAST:event_btnAddPressure1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemPressure1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemPressure1ActionPerformed
        final Object selection = lstPressure1.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll die Maßnahme '"
                            + selection.toString()
                            + "' wirklich gelöscht werden?",
                    "Maßnahme entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("detail_massnahme");   // NOI18N
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (final Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                   //GEN-LAST:event_btnRemPressure1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbErsteOrdnActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbErsteOrdnActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbErsteOrdnActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbZweiteOrdnActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbZweiteOrdnActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbZweiteOrdnActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddDe_meas1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddDe_meas1ActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_btnAddDe_meas1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemDeMeas1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemDeMeas1ActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_btnRemDeMeas1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddDe_meas2ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddDe_meas2ActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_btnAddDe_meas2ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemDeMeas2ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemDeMeas2ActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_btnRemDeMeas2ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstdeMeasValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_lstdeMeasValueChanged
        final Object selectedObject = lstdeMeas.getSelectedValue();

        if (selectedObject instanceof CidsBean) {
            massnahmenDetail1.setCidsBean((CidsBean)selectedObject);
            massnahmenDetail1.setImpacts(getImpact());
        } else {
            massnahmenDetail1.setCidsBean(null);
        }
    } //GEN-LAST:event_lstdeMeasValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbLandesweitActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbLandesweitActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbLandesweitActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lstdeMeas3ValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_lstdeMeas3ValueChanged
        // TODO add your handling code here:
    } //GEN-LAST:event_lstdeMeas3ValueChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbRejectedActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbRejectedActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbRejectedActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbRejectedStateChanged(final javax.swing.event.ChangeEvent evt) { //GEN-FIRST:event_cbRejectedStateChanged
        taReject.setEnabled(cbRejected.isSelected());
    }                                                                              //GEN-LAST:event_cbRejectedStateChanged

    /**
     * DOCUMENT ME!
     */
    public void repaintImpacts() {
        lstImpactSrc.repaint();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    private void deActivateGUI(final boolean enable) {
        if (!readOnly) {
            txtKosten.setEnabled(enable);
            cbReal.setEnabled(enable);
            txtZiele.setEnabled(enable);
            jTextArea1.setEnabled(enable);
            cbPrioritaet.setEnabled(enable);
            cbRevital.setEnabled(enable);
            cbMassn_typ.setEnabled(enable);
        }
    }

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
        return String.valueOf(cidsBean);
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
            if (cbRejected.isSelected()) {
                try {
                    String query = "select "
                                + PROJECT_MC.getID()
                                + ", m."
                                + PROJECT_MC.getPrimaryKey()
                                + " from "
                                + PROJECT_MC.getTableName();  // NOI18N
                    query +=
                        " p join projekte_massnahmen_umsetzung pmu on (p.umsetzung = pmu.projekte_reference) join massnahmen_umsetzung mu on (pmu.massnahmen_umsetzung = mu.id) where mu.massnahme = "
                                + cidsBean.getProperty("id"); // NOI18N

                    final MetaObject[] mos = SessionManager.getProxy().getMetaObjectByQuery(query, 0, cc);

                    if ((mos != null) && (mos.length > 0)) {
                        JOptionPane.showMessageDialog(StaticSwingTools.getParentFrame(this),
                            "Die Maßnahme kann nur als verworfen gekennzeichnet werden, wenn sie in keinem Projekt verwendet wird.\nIm Moment wird sie aber noch von folgendem Projekt genutzt:\n"
                                    + mos[0].getBean().getProperty("projekt_bez"),
                            "In Verwendung",
                            HEIGHT);
                        return false;
                    }
                } catch (Exception e) {
                    LOG.error("Cannot check, if massnahme is still used within a project", e);
                }
            }

            cidsBean.getMetaObject().setAllClasses();
            if (dropBehaviorListener.isRouteChanged() && !linearReferencedLineEditor.hasChangedSinceDrop()) {
                final int ans = JOptionPane.showConfirmDialog(
                        StaticSwingTools.getParentFrame(this),
                        "Sie haben die Stationen nicht geändert, nachdem Sie eine "
                                + "neue Route ausgewählt haben. Möchten Sie die Stationen ändern?",
                        "Keine Änderung der Stationen",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (ans == JOptionPane.YES_OPTION) {
                    return false;
                }
            }

            try {
                cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());   // NOI18N
                cidsBean.setProperty("av_time", new java.sql.Timestamp(System.currentTimeMillis())); // NOI18N

                for (final CidsBean tmp : beansToDelete) {
                    tmp.persist();
                }
            } catch (final Exception ex) {
                LOG.error("Error in prepareForSave.", ex); // NOI18N
            }

            try {
                final CidsBean stalu = (CidsBean)cidsBean.getProperty("stalu");
                Geometry geom = (Geometry)cidsBean.getProperty("linie.geom.geo_field");

                if (geom == null) {
                    geom = (Geometry)cidsBean.getProperty("additional_geom.geo_field");
                }

                if ((stalu == null) && (geom != null)) {
                    final String staluName = determineStalu(geom);

                    if (staluName != null) {
                        for (int i = 0; i < cbStalu.getModel().getSize(); ++i) {
                            final CidsBean bean = (CidsBean)cbStalu.getModel().getElementAt(i);
                            if (bean.getProperty("value").equals(staluName)) {
                                cidsBean.setProperty("stalu", bean);
                                break;
                            }
                        }
                    }
                }
            } catch (final Exception ex) {
                LOG.error("Error in prepareForSave.", ex); // NOI18N
            }

            final int answ = JOptionPane.showConfirmDialog(StaticSwingTools.getParentFrame(this),
                    NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.prepareForSave().message", this),
                    NbBundle.getMessage(MassnahmenEditor.class, "MassnahmenEditor.prepareForSave().title", this),
                    JOptionPane.YES_NO_OPTION);

            if (answ == JOptionPane.YES_OPTION) {
                final WaitingDialogThread wdt = new WaitingDialogThread(StaticSwingTools.getParentFrame(this),
                        false,
                        "Aktualisiere Simulationen",
                        null,
                        0) {

                        @Override
                        protected Object doInBackground() throws Exception {
                            final String wkk = (String)cidsBean.getProperty("wk_fg.wk_k");

                            if ((oldWkFg != null)
                                        && ((wkk == null)
                                            || !oldWkFg.equals(wkk))) {
                                FgskSimulationHelper.reCreateSimulation(oldWkFg, true);
                                FgskSimulationHelper.reCreateSimulation(oldWkFg, false);
                            }

                            if (wkk != null) {
                                FgskSimulationHelper.reCreateSimulation(wkk, true);
                                FgskSimulationHelper.reCreateSimulation(wkk, false);
                            }

                            return null;
                        }
                    };

                wdt.start();
            }
        }

        boolean save = true;
        save &= linearReferencedLineEditor.prepareForSave();
        return save;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   geom  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String determineStalu(final Geometry geom) {
        try {
            final String geometryText = "SRID="
                        + CismapBroker.getInstance().getDefaultCrsAlias()
                        + ";"
                        + geom.toText();
            final CidsServerSearch search = new StaluSearch(geometryText);
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

            if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                final Object o = resArray.get(0).get(0);

                if (o instanceof String) {
                    return o.toString();
                }
            } else {
                LOG.error("Server error in getWk_k(). Cids server search return null. " // NOI18N
                            + "See the server logs for further information");     // NOI18N
            }
        } catch (ConnectionException e) {
            LOG.error("Exception during a cids server search.", e);               // NOI18N
        }

        return null;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        if ((cidsBean != null) && !readOnly) {
            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_fg")) {        // NOI18N
                    if (oldWkFg == null) {
                        oldWkFg = (String)bean.getProperty("wk_fg.wk_k");
                    }
                    bindToWb(WB_PROPERTIES[0], bean);
                    dropBehaviorListener.setWkFg(bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_sg")) { // NOI18N
                    bindToWb(WB_PROPERTIES[1], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_kg")) { // NOI18N
                    bindToWb(WB_PROPERTIES[2], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_gw")) { // NOI18N
                    bindToWb(WB_PROPERTIES[3], bean);
                }
            }
            bindReadOnlyFields();
            pressures = null;
            loadPressures();
        }
    }

    /**
     * binds the given water body to the current CidsBean object.
     *
     * @param  propertyName   the name of the water body property. (The allowed values are stored in the array
     *                        WB_PROPERTIES)
     * @param  propertyEntry  the water body object
     */
    private void bindToWb(final String propertyName, final CidsBean propertyEntry) {
        try {
            cidsBean.setProperty(propertyName, propertyEntry);

            for (final String propName : WB_PROPERTIES) {
                if (!propName.equals(propertyName)) {
                    cidsBean.setProperty(propName, null);
                }
            }

            if (propertyName.equals(WB_PROPERTIES[0]) || (cidsBean.getProperty("linie") == null)) {
                copyGeometries(String.valueOf(propertyEntry.getProperty("id"))); // NOI18N
            } else {
                setWBValues(String.valueOf(propertyEntry.getProperty("id")));
            }
            showOrHideGeometryEditors();
            bindReadOnlyFields();
        } catch (final Exception ex) {
            LOG.error("Error while binding a water body", ex);                   // NOI18N
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkId  DOCUMENT ME!
     */
    private void copyGeometries(final String wkId) {
        // delete old geometries
        try {
            CidsBeanSupport.deleteStationlineIfExists(cidsBean, "linie", beansToDelete); // NOI18N
            cidsBean.setProperty("linie", null);                                         // NOI18N
        } catch (final Exception e) {
            LOG.error("Cannot delete cids bean.", e);                                    // NOI18N
        }

        // delete additional_geom if exists
        try {
            CidsBeanSupport.deletePropertyIfExists(cidsBean, "additional_geom", beansToDelete); // NOI18N
            cidsBean.setProperty("additional_geom", null);                                      // NOI18N
        } catch (final Exception e) {
            LOG.error("Cannot delete cids bean.", e);                                           // NOI18N
        }

        setWBValues(wkId);

        // copy new geometries
        if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[0]) == null)) {
            CidsBean wk_sg = null;

            for (final String propName : WB_PROPERTIES) {
                if (cidsBean.getProperty(propName) != null) {
                    wk_sg = (CidsBean)cidsBean.getProperty(propName);
                }
            }

            try {
                CidsBean geoBean = (CidsBean)wk_sg.getProperty("geom");
                if (geoBean == null) {
                    geoBean = (CidsBean)wk_sg.getProperty("the_geom");
                }

                final CidsBean geom = CidsBeanSupport.cloneCidsBean(geoBean); // NOI18N
                cidsBean.setProperty("additional_geom", geom);                // NOI18N
            } catch (final Exception e) {
                LOG.error("Cannot copy the new geometry.", e);                // NOI18N
            }
        } else if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[0]) != null)) {
            // wk_fg
            final CidsBean wk_fg = (CidsBean)cidsBean.getProperty(WB_PROPERTIES[0]);
            final List<CidsBean> teile = CidsBeanSupport.getBeanCollectionFromProperty(wk_fg, "teile"); // NOI18N

            if ((teile != null) && (teile.size() > 0)) {
                final CidsBean teil = teile.get(0);

                try {
                    final CidsBean lineBean = (CidsBean)teil.getProperty("linie");
                    final CidsBean clonedLineBean = CidsBeanSupport.cloneStationline(lineBean); // NOI18N
                    cidsBean.setProperty("linie", clonedLineBean);
                } catch (final Exception e) {
                    LOG.error("Cannot copy the new geometry.", e);                              // NOI18N
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkId  DOCUMENT ME!
     */
    private void setWBValues(final String wkId) {
        try {
            int lfdnr = 1;
            String wkTable;
            String massReferencedField;

            if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[1]) != null)) {
                wkTable = "wk_sg";             // NOI18N
                massReferencedField = "wk_sg"; // NOI18N
            } else if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[2]) != null)) {
                wkTable = "wk_kg";             // NOI18N
                massReferencedField = "wk_kg"; // NOI18N
            } else if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[3]) != null)) {
                wkTable = "wk_gw";             // NOI18N
                massReferencedField = "wk_gw"; // NOI18N
            } else {
                wkTable = "wk_fg";             // NOI18N
                massReferencedField = "wk_fg"; // NOI18N
            }

            final CidsServerSearch search = new MaxWBNumberSearch(wkTable, wkId, massReferencedField);
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;
            if ((resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                final Object o = resArray.get(0).get(0);
                if (o instanceof java.math.BigDecimal) {
                    lfdnr = ((java.math.BigDecimal)o).intValue();
                    ++lfdnr;
                }
            }

            cidsBean.setProperty("massn_wk_lfdnr", BigDecimal.valueOf(lfdnr));                  // NOI18N
            cidsBean.setProperty("massn_id", getWk_k() + "_M_" + convertNumberToString(lfdnr)); // NOI18N
        } catch (final Exception e) {
            LOG.error(e, e);
        }
    }
    /**
     * laut Herr Rahmlow in der EMail vom 28.3.2012 muss die Nummer zwei Stellen haben.
     *
     * @param   number  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String convertNumberToString(final int number) {
        if (number > 9) {
            return ""
                        + number;
        } else {
            return "0"
                        + number;
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void showOrHideGeometryEditors() {
        if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[1]) != null)
                    && (cidsBean.getProperty("linie") == null)) {
            panGeo.setVisible(false);
            cbGeom.setVisible(true);
            lblGeom.setVisible(true);
        } else {
            panGeo.setVisible(true);
            cbGeom.setVisible(true);
            lblGeom.setVisible(true);
        }

        if (readOnly) {
            cbGeom.setVisible(false);
            lblGeom.setVisible(false);
        }
    }

    @Override
    public BeanInitializer getBeanInitializer() {
        return new DefaultBeanInitializer(cidsBean) {

                @Override
                protected void processSimpleProperty(final CidsBean beanToInit,
                        final String propertyName,
                        final Object simpleValueToProcess) throws Exception {
                    if (propertyName.equalsIgnoreCase("av_user") || propertyName.equalsIgnoreCase("av_date")
                                || propertyName.equalsIgnoreCase("massn_wk_lfdnr")
                                || propertyName.equalsIgnoreCase("kosten")) {
                        return;
                    }
                    super.processSimpleProperty(beanToInit, propertyName, simpleValueToProcess);
                }

                @Override
                protected void processArrayProperty(final CidsBean beanToInit,
                        final String propertyName,
                        final Collection<CidsBean> arrayValueToProcess) throws Exception {
                    final List<CidsBean> beans = CidsBeanSupport.getBeanCollectionFromProperty(
                            beanToInit,
                            propertyName);
                    beans.clear();

                    for (final CidsBean tmp : arrayValueToProcess) {
                        beans.add(tmp);
                    }
                }

                @Override
                protected void processComplexProperty(final CidsBean beanToInit,
                        final String propertyName,
                        final CidsBean complexValueToProcess) throws Exception {
                    if (propertyName.equals("linie") || propertyName.equals("additional_geom")) {
                        return;
                    }

                    // flat copy
                    beanToInit.setProperty(propertyName, complexValueToProcess);
                }
            };
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("wk_fg")
                    || evt.getPropertyName().equalsIgnoreCase("massn_wk_lfdnr")) {
            bindReadOnlyFields();
        }

        if (evt.getPropertyName().equalsIgnoreCase(WB_PROPERTIES[1])
                    || evt.getPropertyName().equalsIgnoreCase("linie")) {
            showOrHideGeometryEditors();
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class WfdTypeCodeRenderer extends DefaultListCellRenderer {

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getListCellRendererComponent(final JList list,
                final Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            final Component result = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if ((result instanceof JLabel) && (value instanceof CidsBean)) {
                final CidsBean bean = (CidsBean)value;

                final String text = bean.getProperty("value")
                            + " - "
                            + bean.getProperty("name");
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
    private class CustomElementComparator implements Comparator<CidsBean> {

        //~ Instance fields ----------------------------------------------------

        private int integerIndex = 0;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new CustomElementComparator object.
         */
        public CustomElementComparator() {
        }

        /**
         * Creates a new CustomElementComparator object.
         *
         * @param  integerIndex  DOCUMENT ME!
         */
        public CustomElementComparator(final int integerIndex) {
            this.integerIndex = integerIndex;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final CidsBean o1, final CidsBean o2) {
            if ((o1 != null) && (o2 != null)) {
                final String strValue1 = (String)o1.getProperty("value");
                final String strValue2 = (String)o2.getProperty("value");

                if ((strValue1 != null) && (strValue2 != null)) {
                    try {
                        final Integer value1 = Integer.parseInt(strValue1.substring(integerIndex));
                        final Integer value2 = Integer.parseInt(strValue2.substring(integerIndex));

                        return value1.intValue()
                                    - value2.intValue();
                    } catch (NumberFormatException e) {
                        // nothing to do, because not every 'value'-property contains a integer
                    }

                    return strValue1.compareTo(strValue2);
                } else {
                    if ((strValue1 == null) && (strValue2 == null)) {
                        return 0;
                    } else {
                        return ((strValue1 == null) ? -1 : 1);
                    }
                }
            } else {
                if ((o1 == null) && (o2 == null)) {
                    return 0;
                } else {
                    return ((o1 == null) ? -1 : 1);
                }
            }
        }
    }
}
