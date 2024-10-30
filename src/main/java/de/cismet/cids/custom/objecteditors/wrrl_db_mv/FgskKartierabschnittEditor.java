/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.log4j.Logger;

import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.WeakListeners;

import java.awt.Component;
import java.awt.EventQueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.lang.reflect.InvocationTargetException;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.objectrenderer.wrrl_db_mv.FgskKartierabschnittRenderer;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.Calc;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.CalcCache;
import de.cismet.cids.custom.wrrl_db_mv.fgsk.ValidationException;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WKKSearchBySingleStation;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.wrrl_db_mv.util.TimestampConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.BeanInitializer;
import de.cismet.cids.editors.BeanInitializerForcePaste;
import de.cismet.cids.editors.BeanInitializerProvider;
import de.cismet.cids.editors.DefaultBeanInitializer;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.server.search.AbstractCidsServerSearch;
import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class FgskKartierabschnittEditor extends JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    FooterComponentProvider,
    PropertyChangeListener,
    BeanInitializerProvider {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient Logger LOG = Logger.getLogger(FgskKartierabschnittEditor.class);
    private static FgskKartierabschnittEditor lastInstance = null;
    private static CidsBean lastBean = null;
    private static boolean open = false;

    //~ Instance fields --------------------------------------------------------

    private boolean readOnly = false;

    private CidsBean cidsBean;

    private final transient ChangeListener calcL;
    private final transient PropertyChangeListener excL;
    private String wkk = null;

    // will only be changed in EDT
    private transient int selectedTabIndex;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittErgebnisse
        fgskKartierabschnittErgebnisse1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittGewaesserumfeld
        fgskKartierabschnittGewaesserumfeld1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittKartierabschnitt
        fgskKartierabschnittKartierabschnitt1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittLaengsprofil
        fgskKartierabschnittLaengsprofil1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittLaufentwicklung
        fgskKartierabschnittLaufentwicklung1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittQuerprofil
        fgskKartierabschnittQuerprofil1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittSohlenverbau
        fgskKartierabschnittSohlenverbau1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittUferstruktur
        fgskKartierabschnittUferstruktur1;
    private javax.swing.JLabel lblFoot;
    private javax.swing.JPanel panErgebnisse;
    private javax.swing.JPanel panFooter;
    private javax.swing.JPanel panGewaesserumfeld;
    private javax.swing.JPanel panKartierabschnitt;
    private javax.swing.JPanel panLaengsprofil;
    private javax.swing.JPanel panLaufentwicklung;
    private javax.swing.JPanel panQuerprofil;
    private javax.swing.JPanel panSohlenstruktur;
    private javax.swing.JPanel panUferstruktur;
    private javax.swing.JTabbedPane tpMain;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgEditor.
     */
    public FgskKartierabschnittEditor() {
        this(false);
    }

    /**
     * Creates new form WkFgEditor.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public FgskKartierabschnittEditor(final boolean readOnly) {
        this.readOnly = readOnly;

        initComponents();

        tpMain.setUI(new TabbedPaneUITransparent());

        if (readOnly) {
            fgskKartierabschnittKartierabschnitt1.setReadOnly(readOnly);
            fgskKartierabschnittLaufentwicklung1.setReadOnly(readOnly);
            fgskKartierabschnittLaengsprofil1.setReadOnly(readOnly);
            fgskKartierabschnittQuerprofil1.setReadOnly(readOnly);
            fgskKartierabschnittSohlenverbau1.setReadOnly(readOnly);
            fgskKartierabschnittUferstruktur1.setReadOnly(readOnly);
            fgskKartierabschnittGewaesserumfeld1.setReadOnly(readOnly);
        } else {
            // fully initialise the cache
            CalcCache.getInstance().init();
        }
        this.calcL = new CalcListener();
        tpMain.addChangeListener(WeakListeners.change(calcL, tpMain));

        // will only be changed in EDT
        selectedTabIndex = tpMain.getSelectedIndex();

        this.excL = new ExceptionPropertyChangeListener();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        if (EventQueue.isDispatchThread()) {
            setCidsBeanInternal(cidsBean);
        } else {
            try {
                EventQueue.invokeAndWait(new Runnable() {

                        @Override
                        public void run() {
                            setCidsBeanInternal(cidsBean);
                        }
                    });
            } catch (InterruptedException ex) {
            } catch (InvocationTargetException ex) {
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    private void setCidsBeanInternal(final CidsBean cidsBean) {
        if (this.cidsBean != null) {
            this.cidsBean.removePropertyChangeListener(this);
        }
        this.cidsBean = cidsBean;
        cidsBean.addPropertyChangeListener(this);

        if (!readOnly) {
            lastInstance = this;
            lastBean = cidsBean;
            open = true;
        }

        if (cidsBean != null) {
            fgskKartierabschnittKartierabschnitt1.setCidsBean(cidsBean);
            fgskKartierabschnittLaufentwicklung1.setCidsBean(cidsBean);
            fgskKartierabschnittLaengsprofil1.setCidsBean(cidsBean);
            fgskKartierabschnittQuerprofil1.setCidsBean(cidsBean);
            fgskKartierabschnittSohlenverbau1.setCidsBean(cidsBean);
            fgskKartierabschnittUferstruktur1.setCidsBean(cidsBean);
            fgskKartierabschnittGewaesserumfeld1.setCidsBean(cidsBean);
            fgskKartierabschnittErgebnisse1.setCidsBean(cidsBean);

//            if (isException(cidsBean)) {
            handleException();
//            }

            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        refreshLabels();
                    }
                }).start();
            this.cidsBean.addPropertyChangeListener(WeakListeners.propertyChange(excL, cidsBean));
        }

        fillFooter();

        if (!readOnly) {
            // ensure no wrong values will remain if there are rating errors due to changes
            // this should only be done in the editor and not in the renderer
            Calc.getInstance().removeAllRatings(this.cidsBean);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CidsBean getCurrentlyOpenBean() {
        if (open) {
            return lastBean;
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void fillFooter() {
        if (cidsBean != null) {
            Object avUser = cidsBean.getProperty("av_user");
            Object avTime = cidsBean.getProperty("av_time");

            if (avUser == null) {
                avUser = "(unbekannt)";
            }
            if (avTime instanceof Timestamp) {
                avTime = TimestampConverter.getInstance().convertForward((Timestamp)avTime);
            } else {
                avTime = "(unbekannt)";
            }
            lblFoot.setText("Zuletzt bearbeitet von " + avUser + " am " + avTime);
        } else {
            lblFoot.setText("");
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshLabels() {
        final CidsBean statLine = (CidsBean)cidsBean.getProperty("linie");
        final CidsBean route = (CidsBean)cidsBean.getProperty("linie.von.route");
        final Boolean isAr = (Boolean)cidsBean.getProperty("linie.von.ohne_route");

        String wkk = CidsBeanSupport.FIELD_NOT_SET;
        try {
            if ((statLine != null) && (route != null) && ((isAr == null) || !isAr)) {
                final Double vonWert = (Double)statLine.getProperty("von.wert");
                final Double bisWert = (Double)statLine.getProperty("bis.wert");
                if ((vonWert != null) && (bisWert != null)) {
                    final double wert = (bisWert + vonWert) / 2;
                    final CidsServerSearch search = new WKKSearchBySingleStation(String.valueOf(
                                route.getProperty("id")),
                            String.valueOf(wert));
                    final Collection res = SessionManager.getProxy()
                                .customServerSearch(SessionManager.getSession().getUser(), search);
                    final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

                    if ((resArray != null) && (resArray.size() > 0) && (resArray.get(0).size() > 0)) {
                        final Object o = resArray.get(0).get(0);

                        if (o instanceof String) {
                            wkk = o.toString();
                        }
                    } else {
                        LOG.error("Server error in getWk_k(). Cids server search return null. " // NOI18N
                                    + "See the server logs for further information");     // NOI18N
                    }
                }
            }
        } catch (final Exception e) {
            LOG.error("Error while determining the water body", e);
        }

        setWkk(wkk);
    }

    /**
     * DOCUMENT ME!
     */
    private void handleException() {
        if (cidsBean == null) {
            return;
        }

        final boolean exc = isException(cidsBean);

        // first tab is general tab and last tab is result tab, both shall always enabled
        for (int i = 1; i < (tpMain.getTabCount() - 1); ++i) {
            tpMain.setEnabledAt(i, !exc);
        }

        if (exc || !readOnly) {
            try {
                cidsBean.setProperty(Calc.PROP_WB_OVERALL_RATING, 0.0);
                cidsBean.setProperty(Calc.PROP_WB_BED_RATING, null);
                cidsBean.setProperty(Calc.PROP_WB_BANK_RATING, null);
                cidsBean.setProperty(Calc.PROP_WB_ENV_RATING, null);
                cidsBean.setProperty(Calc.PROP_COURSE_EVO_SUM_RATING, null);
                cidsBean.setProperty(Calc.PROP_COURSE_EVO_SUM_CRIT, null);
                cidsBean.setProperty(Calc.PROP_LONG_PROFILE_SUM_RATING, null);
                cidsBean.setProperty(Calc.PROP_LONG_PROFILE_SUM_CRIT, null);
                cidsBean.setProperty(Calc.PROP_CROSS_PROFILE_SUM_RATING, null);
                cidsBean.setProperty(Calc.PROP_CROSS_PROFILE_SUM_CRIT, null);
                cidsBean.setProperty(Calc.PROP_BED_STRUCTURE_SUM_RATING, null);
                cidsBean.setProperty(Calc.PROP_BED_STRUCTURE_SUM_CRIT, null);
                cidsBean.setProperty(Calc.PROP_BANK_STRUCTURE_SUM_RATING, null);
                cidsBean.setProperty(Calc.PROP_BANK_STRUCTURE_SUM_CRIT, null);
                cidsBean.setProperty(Calc.PROP_BANK_STRUCTURE_SUM_RATING_LE, null);
                cidsBean.setProperty(Calc.PROP_BANK_STRUCTURE_SUM_CRIT_LE, null);
                cidsBean.setProperty(Calc.PROP_BANK_STRUCTURE_SUM_RATING_RI, null);
                cidsBean.setProperty(Calc.PROP_BANK_STRUCTURE_SUM_CRIT_RI, null);
                cidsBean.setProperty(Calc.PROP_WB_ENV_SUM_RATING, null);
                cidsBean.setProperty(Calc.PROP_WB_ENV_SUM_CRIT, null);
                cidsBean.setProperty(Calc.PROP_WB_ENV_SUM_RATING_LE, null);
                cidsBean.setProperty(Calc.PROP_WB_ENV_SUM_CRIT_LE, null);
                cidsBean.setProperty(Calc.PROP_WB_ENV_SUM_RATING_RI, null);
                cidsBean.setProperty(Calc.PROP_WB_ENV_SUM_CRIT_RI, null);

                fgskKartierabschnittErgebnisse1.refreshGueteklasse();
            } catch (final Exception ex) {
                LOG.error("Error while setting a calculation property", ex); // NOI18N
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    public static boolean isException(final CidsBean kaBean) {
        if (kaBean == null) {
            throw new IllegalArgumentException("cidsBean must not be null"); // NOI18N
        }

        final CidsBean exception = (CidsBean)kaBean.getProperty(Calc.PROP_EXCEPTION);

        return ((exception != null) && !Integer.valueOf(0).equals(exception.getProperty(Calc.PROP_VALUE)));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IllegalArgumentException  DOCUMENT ME!
     */
    public static boolean isPiped(final CidsBean kaBean) {
        if (kaBean == null) {
            throw new IllegalArgumentException("cidsBean must not be null"); // NOI18N
        }

        final CidsBean exception = (CidsBean)kaBean.getProperty(Calc.PROP_EXCEPTION);

        return ((exception != null) && Integer.valueOf(1).equals(exception.getProperty(Calc.PROP_VALUE)));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   kaBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static boolean isPreFieldMapping(final CidsBean kaBean) {
        final Boolean vorkartierung = (Boolean)kaBean.getProperty("vorkatierung"); // NOI18N

        return (vorkartierung != null) && vorkartierung;
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

        panFooter = new javax.swing.JPanel();
        lblFoot = new javax.swing.JLabel();
        tpMain = new javax.swing.JTabbedPane();
        panKartierabschnitt = new javax.swing.JPanel();
        fgskKartierabschnittKartierabschnitt1 = new FgskKartierabschnittKartierabschnitt(readOnly);
        panLaufentwicklung = new javax.swing.JPanel();
        fgskKartierabschnittLaufentwicklung1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittLaufentwicklung();
        panLaengsprofil = new javax.swing.JPanel();
        fgskKartierabschnittLaengsprofil1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittLaengsprofil();
        panQuerprofil = new javax.swing.JPanel();
        fgskKartierabschnittQuerprofil1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittQuerprofil();
        panSohlenstruktur = new javax.swing.JPanel();
        fgskKartierabschnittSohlenverbau1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittSohlenverbau();
        panUferstruktur = new javax.swing.JPanel();
        fgskKartierabschnittUferstruktur1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittUferstruktur();
        panGewaesserumfeld = new javax.swing.JPanel();
        fgskKartierabschnittGewaesserumfeld1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittGewaesserumfeld();
        panErgebnisse = new javax.swing.JPanel();
        fgskKartierabschnittErgebnisse1 =
            new de.cismet.cids.custom.objecteditors.wrrl_db_mv.FgskKartierabschnittErgebnisse();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        lblFoot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoot.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(7, 25, 7, 25);
        panFooter.add(lblFoot, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1100, 780));
        setPreferredSize(new java.awt.Dimension(1100, 780));
        setLayout(new java.awt.BorderLayout());

        tpMain.setMinimumSize(new java.awt.Dimension(1100, 770));
        tpMain.setPreferredSize(new java.awt.Dimension(1100, 770));

        panKartierabschnitt.setOpaque(false);
        panKartierabschnitt.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panKartierabschnitt.add(fgskKartierabschnittKartierabschnitt1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panKartierabschnitt.TabConstraints.tabTitle"),
            panKartierabschnitt); // NOI18N

        panLaufentwicklung.setOpaque(false);
        panLaufentwicklung.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panLaufentwicklung.add(fgskKartierabschnittLaufentwicklung1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panLaufentwicklung.TabConstraints.tabTitle"),
            panLaufentwicklung); // NOI18N

        panLaengsprofil.setOpaque(false);
        panLaengsprofil.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panLaengsprofil.add(fgskKartierabschnittLaengsprofil1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panLaengsprofil.TabConstraints.tabTitle"),
            panLaengsprofil); // NOI18N

        panQuerprofil.setOpaque(false);
        panQuerprofil.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panQuerprofil.add(fgskKartierabschnittQuerprofil1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panQuerprofil.TabConstraints.tabTitle"),
            panQuerprofil); // NOI18N

        panSohlenstruktur.setOpaque(false);
        panSohlenstruktur.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panSohlenstruktur.add(fgskKartierabschnittSohlenverbau1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panSohlenstruktur.TabConstraints.tabTitle"),
            panSohlenstruktur); // NOI18N

        panUferstruktur.setOpaque(false);
        panUferstruktur.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panUferstruktur.add(fgskKartierabschnittUferstruktur1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panUferstruktur.TabConstraints.tabTitle"),
            panUferstruktur); // NOI18N

        panGewaesserumfeld.setOpaque(false);
        panGewaesserumfeld.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panGewaesserumfeld.add(fgskKartierabschnittGewaesserumfeld1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panGewaesserumfeld.TabConstraints.tabTitle"),
            panGewaesserumfeld); // NOI18N

        panErgebnisse.setOpaque(false);
        panErgebnisse.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        panErgebnisse.add(fgskKartierabschnittErgebnisse1, gridBagConstraints);

        tpMain.addTab(NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.panErgebnisse.TabConstraints.tabTitle"),
            panErgebnisse); // NOI18N

        add(tpMain, java.awt.BorderLayout.PAGE_START);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     */
    @Override
    public void dispose() {
        if (this.cidsBean != null) {
            this.cidsBean.removePropertyChangeListener(this);
        }
        fgskKartierabschnittKartierabschnitt1.dispose();
        fgskKartierabschnittLaufentwicklung1.dispose();
        fgskKartierabschnittLaengsprofil1.dispose();
        fgskKartierabschnittQuerprofil1.dispose();
        fgskKartierabschnittSohlenverbau1.dispose();
        fgskKartierabschnittUferstruktur1.dispose();
        fgskKartierabschnittGewaesserumfeld1.dispose();
        fgskKartierabschnittErgebnisse1.dispose();
        if (!readOnly) {
            open = false;
        }
    }

    @Override
    public String getTitle() {
        return NbBundle.getMessage(
                FgskKartierabschnittEditor.class,
                "FgskKartierabschnittEditor.getTitle().returnValuePart") + String.valueOf(cidsBean); // NOI18N
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        fgskKartierabschnittKartierabschnitt1.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        boolean res = fgskKartierabschnittKartierabschnitt1.prepareForSave();
        res &= fgskKartierabschnittQuerprofil1.prepareForSave();

        final Double von = (Double)cidsBean.getProperty("linie.von.wert");
        final Double bis = (Double)cidsBean.getProperty("linie.bis.wert");

        if ((von != null) && (bis != null)) {
            final double length = Math.abs(bis.doubleValue() - von.doubleValue());

            if ((length < 5)) {
                JOptionPane.showMessageDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Ein Abschnitt darf nicht kürzer als 5m sein.",
                    "Warnung",
                    JOptionPane.ERROR_MESSAGE);

                return false;
            }

            if ((length > 400) || (length < 50)) {
                JOptionPane.showMessageDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Ein Abschnitt sollte nicht kürzer als 50m und nicht länger als 400m sein.",
                    "Warnung",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        if (res && (cidsBean != null)) {
            try {
                cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());
            } catch (Exception ex) {
                LOG.error("Cannot save the current user.", ex);
            }
            try {
                cidsBean.setProperty("av_time", new java.sql.Timestamp(System.currentTimeMillis()));
            } catch (Exception ex) {
                LOG.error("Cannot save the current time.", ex);
            }
            try {
                cidsBean.setProperty("gwk", cidsBean.getProperty("linie.von.route.gwk"));
            } catch (Exception ex) {
                LOG.error("Cannot save the current gwk.", ex);
            }

            try {
                if (wkk != null) {
                    cidsBean.setProperty("wkk", wkk);
                }
            } catch (Exception ex) {
                LOG.error("Cannot save the current gwk.", ex);
            }

            performCalculations(tpMain.getComponentAt(selectedTabIndex), panErgebnisse);

            final Boolean vorkartierung = (Boolean)cidsBean.getProperty("vorkatierung");
            final Integer type = (Integer)cidsBean.getProperty("gewaessertyp_id.value");

            if ((vorkartierung == null) || !vorkartierung) {
                final List<CidsBean> subTypes = CidsBeanSupport.getBeanCollectionFromProperty(
                        cidsBean,
                        "gewaessersubtyp");

                if ((type != null) && ((type == 14) || (type == 15))) {
                    boolean found = false;

                    if (subTypes != null) {
                        for (final CidsBean tmp : subTypes) {
                            final String val = (String)tmp.getProperty("value");

                            if ((val != null) && (val.equals("M") || val.equals("S"))) {
                                found = true;
                            }
                        }
                    }

                    if (!found) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Der Gewässersubtyp muss M oder S sein, wenn der Gewässertyp 14 oder 15 ist.",
                            "Ungültiger Gewässeersubtyp",
                            JOptionPane.WARNING_MESSAGE);

                        return false;
                    }
                }

                final Double ton = (Double)cidsBean.getProperty("sohlensubstrat_ton");
                final Double sand = (Double)cidsBean.getProperty("sohlensubstrat_san");
                final Double kies = (Double)cidsBean.getProperty("sohlensubstrat_kie");
                final Double stein = (Double)cidsBean.getProperty("sohlensubstrat_ste");
                final Double block = (Double)cidsBean.getProperty("sohlensubstrat_blo");
                final Double schlamm = (Double)cidsBean.getProperty("sohlensubstrat_sch");
                final Double torf = (Double)cidsBean.getProperty("sohlensubstrat_tor");
                final Double toth = (Double)cidsBean.getProperty("sohlensubstrat_tot");
                final Double wurzeln = (Double)cidsBean.getProperty("sohlensubstrat_wur");
                final Double kuenstlich = (Double)cidsBean.getProperty("sohlensubstrat_kue");

                final double ges = coalesce(ton, 0.0) + coalesce(sand, 0.0) + coalesce(kies, 0.0) + coalesce(stein, 0.0)
                            + coalesce(block, 0.0) + coalesce(schlamm, 0.0) + coalesce(torf, 0.0)
                            + coalesce(toth, 0.0) + coalesce(wurzeln, 0.0) + coalesce(kuenstlich, 0.0);

                if ((ges != 0.0) && (ges != 100.0)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Die Summe der Sohlsubstrate muss entweder 100 oder 0 ergeben.",
                        "Ungültige Sohlsubstrate",
                        JOptionPane.WARNING_MESSAGE);

                    return false;
                }
            }

            final Object ed = cidsBean.getProperty("erfassungsdatum");

            if (ed instanceof Date) {
                final Date year2010 = new Date(110, 0, 1, 0, 0, 0);
                final Date now = new Date();
                final Date today = new Date(now.getYear(), now.getMonth(), now.getDay(), 23, 59, 59);

                if (((Date)ed).after(today) || ((Date)ed).before(year2010)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Die Erfassungszeit muss zwischen 2010 und jetzt sein.",
                        "Ungültige Erfassungszeit",
                        JOptionPane.WARNING_MESSAGE);

                    return false;
                }
            }
        }
        return res;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   value  DOCUMENT ME!
     * @param   def    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Double coalesce(final Double value, final Double def) {
        return ((value == null) ? def : value);
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkk  DOCUMENT ME!
     */
    public void setWkk(final String wkk) {
        if (wkk == null) {
            this.wkk = "";
        } else {
            this.wkk = wkk;
        }
        fgskKartierabschnittKartierabschnitt1.setWkk(wkk);
        fgskKartierabschnittGewaesserumfeld1.setWkk(wkk);
        fgskKartierabschnittLaengsprofil1.setWkk(wkk);
        fgskKartierabschnittLaufentwicklung1.setWkk(wkk);
        fgskKartierabschnittQuerprofil1.setWkk(wkk);
        fgskKartierabschnittSohlenverbau1.setWkk(wkk);
        fgskKartierabschnittUferstruktur1.setWkk(wkk);
        fgskKartierabschnittErgebnisse1.setWkk(wkk);
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
            "sb",
            "fgsk_kartierabschnitt",
            2821,
            1280,
            1024);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  leftC     DOCUMENT ME!
     * @param  enteredC  DOCUMENT ME!
     */
    private void performCalculations(final Component leftC, final Component enteredC) {
        if (readOnly || isException(cidsBean) || isPreFieldMapping(cidsBean)) {
            if (!readOnly && isException(cidsBean)) {
                Calc.getInstance().removeAllRatings(cidsBean);
            }
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("editor tab selection changed from old comp: " + leftC + "to new comp: " + enteredC); // NOI18N
        }

        // NOTE: if to slow, send to background
        String name = null;
        try {
            if (panErgebnisse.equals(enteredC)) {
                try {
                    Calc.getInstance().calcWBEnvRating(cidsBean);
                } catch (final ValidationException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("cannot calculate wb env rating", ex); // NOI18N
                    }

                    JOptionPane.showMessageDialog(
                        StaticSwingTools.getParentFrame(FgskKartierabschnittEditor.this),
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.envRatingNotPossible.message"), // NOI18N
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.envRatingNotPossible.title"), // NOI18N
                        JOptionPane.INFORMATION_MESSAGE);

                    return;
                }

                try {
                    Calc.getInstance().calcWBLongProfileRating(cidsBean);
                } catch (final ValidationException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("cannot calculate long profile rating", ex); // NOI18N
                    }

                    JOptionPane.showMessageDialog(
                        StaticSwingTools.getParentFrame(FgskKartierabschnittEditor.this),
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.longProfileRatingNotPossible.message"), // NOI18N
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.longProfileRatingNotPossible.title"), // NOI18N
                        JOptionPane.INFORMATION_MESSAGE);

                    return;
                }

                try {
                    Calc.getInstance().calcCourseEvoRating(cidsBean);
                } catch (final ValidationException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("cannot calculate course evolution rating", ex); // NOI18N
                    }

                    JOptionPane.showMessageDialog(
                        StaticSwingTools.getParentFrame(FgskKartierabschnittEditor.this),
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.courseEvoRatingNotPossible.message"), // NOI18N
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.courseEvoRatingNotPossible.title"), // NOI18N
                        JOptionPane.INFORMATION_MESSAGE);

                    return;
                }

                try {
                    Calc.getInstance().calcWBCrossProfileRating(cidsBean);
                } catch (final ValidationException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("cannot calculate cross profile rating", ex); // NOI18N
                    }

                    JOptionPane.showMessageDialog(
                        StaticSwingTools.getParentFrame(FgskKartierabschnittEditor.this),
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.crossProfileRatingNotPossible.message"), // NOI18N
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.crossProfileRatingNotPossible.title"), // NOI18N
                        JOptionPane.INFORMATION_MESSAGE);

                    return;
                }

                try {
                    Calc.getInstance().calcBedStructureRating(cidsBean);
                } catch (final ValidationException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("cannot calculate bed structure rating", ex); // NOI18N
                    }

                    JOptionPane.showMessageDialog(
                        StaticSwingTools.getParentFrame(FgskKartierabschnittEditor.this),
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.bedStructureRatingNotPossible.message"), // NOI18N
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.bedStructureRatingNotPossible.title"), // NOI18N
                        JOptionPane.INFORMATION_MESSAGE);

                    return;
                }

                try {
                    Calc.getInstance().calcBankStructureRating(cidsBean);
                } catch (final ValidationException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("cannot calculate bank structure rating", ex); // NOI18N
                    }

                    JOptionPane.showMessageDialog(
                        StaticSwingTools.getParentFrame(FgskKartierabschnittEditor.this),
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.bankStructureRatingNotPossible.message"), // NOI18N
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.bankStructureRatingNotPossible.title"), // NOI18N
                        JOptionPane.INFORMATION_MESSAGE);

                    return;
                }

                // NOTE: we run the separated topic ratings before the overall rating because this is the required input
                // to this. However, we do not care about errors as the overall rating error handling will take care of
                // this as well. This has to be kept in mind when changing the calculation procedure.
                try {
                    Calc.getInstance().calcBedRating(cidsBean);
                } catch (final ValidationException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("cannot calculate bed rating", e);
                    }
                }
                try {
                    Calc.getInstance().calcBankRating(cidsBean);
                } catch (final ValidationException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("cannot calculate bank rating", e);
                    }
                }
                try {
                    Calc.getInstance().calcEnvRating(cidsBean);
                } catch (final ValidationException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("cannot calculate env rating", e);
                    }
                }

                try {
                    Calc.getInstance().calcOverallRating(cidsBean);
                } catch (final ValidationException ex) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("cannot calculate overall rating", ex); // NOI18N
                    }

                    JOptionPane.showMessageDialog(
                        StaticSwingTools.getParentFrame(FgskKartierabschnittEditor.this),
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.overallRatingNotPossible.message"), // NOI18N
                        NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.overallRatingNotPossible.title"), // NOI18N
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                if (panGewaesserumfeld.equals(leftC)) {
                    name = NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.name.wbenv");                     // NOI18N
                    Calc.getInstance().calcWBEnvRating(cidsBean);
                    fgskKartierabschnittErgebnisse1.setCidsBean(cidsBean);
                } else if (panLaengsprofil.equals(leftC)) {
                    name = NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.name.longProfile");               // NOI18N
                    Calc.getInstance().calcWBLongProfileRating(cidsBean);
                } else if (panLaufentwicklung.equals(leftC)) {
                    name = NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.name.courseEvo");                 // NOI18N
                    Calc.getInstance().calcCourseEvoRating(cidsBean);
                } else if (panQuerprofil.equals(leftC)) {
                    name = NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.name.crossProfile");              // NOI18N
                    Calc.getInstance().calcWBCrossProfileRating(cidsBean);
                } else if (panSohlenstruktur.equals(leftC)) {
                    name = NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.name.bedStructure");              // NOI18N
                    Calc.getInstance().calcBedStructureRating(cidsBean);
                } else if (panUferstruktur.equals(leftC)) {
                    name = NbBundle.getMessage(
                            FgskKartierabschnittEditor.class,
                            "FgskKartierabschnittEditor.CalcListener.stateChanged.name.bankStructure");             // NOI18N
                    Calc.getInstance().calcBankStructureRating(cidsBean);
                }
            }
        } catch (final ValidationException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("cannot calculate rating: " + name, ex);                                                  // NOI18N
            }

            JOptionPane.showMessageDialog(
                StaticSwingTools.getParentFrame(FgskKartierabschnittEditor.this),
                NbBundle.getMessage(
                    FgskKartierabschnittEditor.class,
                    "FgskKartierabschnittEditor.CalcListener.stateChanged.ratingNotPossible.message", // NOI18N
                    name),
                NbBundle.getMessage(
                    FgskKartierabschnittEditor.class,
                    "FgskKartierabschnittEditor.CalcListener.stateChanged.ratingNotPossible.title", // NOI18N
                    name),
                JOptionPane.INFORMATION_MESSAGE);
        } catch (final Exception ex) {
            LOG.error("unexpected exception during calculation", ex);
        } finally {
            selectedTabIndex = tpMain.getSelectedIndex();
        }

        if (selectedTabIndex == 7) {
            fgskKartierabschnittErgebnisse1.refreshGueteklasse();
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("linie")) {
            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        refreshLabels();
                    }
                }).start();
        }
    }

    @Override
    public BeanInitializer getBeanInitializer() {
        return new KartierabschnittInitializer(cidsBean);
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class KartierabschnittInitializer extends DefaultBeanInitializer implements BeanInitializerForcePaste {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new KartierabschnittInitializer object.
         *
         * @param  cidsBean  DOCUMENT ME!
         */
        public KartierabschnittInitializer(final CidsBean cidsBean) {
            super(cidsBean);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void initializeBean(final CidsBean beanToInit) throws Exception {
            super.initializeBean(beanToInit);

            if (lastInstance != null) {
                lastInstance.setCidsBean(beanToInit);
            }
        }

        @Override
        protected void processSimpleProperty(final CidsBean beanToInit,
                final String propertyName,
                final Object simpleValueToProcess) throws Exception {
            if (propertyName.equalsIgnoreCase("av_user") || propertyName.equalsIgnoreCase("av_date")
                        || propertyName.equalsIgnoreCase("gewaesser_abschnitt")
                        || propertyName.equalsIgnoreCase("foto_nr")) {
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
            if (propertyName.equals("linie") || propertyName.equals("fliessrichtung_id")) {
                return;
            }

            // flat copy
            beanToInit.setProperty(propertyName, complexValueToProcess);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class ExceptionPropertyChangeListener implements PropertyChangeListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if ((evt != null) && Calc.PROP_EXCEPTION.equals(evt.getPropertyName())) {
                handleException();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class CalcListener implements ChangeListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void stateChanged(final ChangeEvent e) {
            final Component leftC = tpMain.getComponentAt(selectedTabIndex);
            final Component enteredC = tpMain.getSelectedComponent();

            performCalculations(leftC, enteredC);
        }
    }
}
